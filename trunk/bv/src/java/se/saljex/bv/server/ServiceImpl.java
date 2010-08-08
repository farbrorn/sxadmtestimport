/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.sql.DataSource;
import se.saljex.bv.client.Betaljournal;
import se.saljex.bv.client.BetaljournalList;
import se.saljex.bv.client.Faktura1;
import se.saljex.bv.client.Faktura1List;
import se.saljex.bv.client.Fakturajournal;
import se.saljex.bv.client.FakturajournalList;
import se.saljex.bv.client.Order;
import se.saljex.bv.client.Order1;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.Order2;
import se.saljex.bv.client.OrderHand;
import se.saljex.bv.client.OrderLookupResp;
import se.saljex.bv.client.OverforBVOrderResp;
import se.saljex.bv.client.ServerErrorException;
import se.saljex.sxserver.SXConstant;
import se.saljex.sxserver.SXUtil;
import se.saljex.sxserver.SxServerMainLocal;

/**
 *
 * @author ulf
 */
public class ServiceImpl {
	private SxServerMainLocal sxServerMainBean;
	private DataSource bvDataSource;
	private DataSource sxDataSource;

	private final static String BVKUNDNR="0855115291";
	 public final static int FILTER_ALLA=0;
	 public final static int FILTER_FOROVERFORING=1;
	 public final static int FILTER_OVERFORDA=2;
	 public final static int FILTER_AVVAKTANDE=3;
 	 public final static int FILTER_FORSKOTT=4;


	public ServiceImpl(SxServerMainLocal sxServerMainBean, DataSource sxDataSource, DataSource bvDataSource) {
		this.sxServerMainBean = sxServerMainBean;
		this.sxDataSource=sxDataSource;
		this.bvDataSource=bvDataSource;
	}



	 private ArrayList<Order2> fillOrder2(Connection con, int ordernr) throws SQLException{
			PreparedStatement stm = con.prepareStatement(
"select o1.lagernr, o2.artnr, o2.namn, o2.best, o2.enh, o2.pris, o2.rab, l.ilager - l.iorder + o2.best as tillgangliga, l2.tillgangliga-(l.ilager-l.iorder+o2.best) as tillgangligaovriga, l.best " +
" from order1 o1 join order2 o2 on (o1.ordernr=o2.ordernr) "+
" left outer join lager l on (l.artnr=o2.artnr and l.lagernr=o1.lagernr) " +
" left outer join ( " +
	" select artnr as artnr , sum(ilager - iorder) as tillgangliga from lager " +
	" group by artnr " +
" ) l2 on (l2.artnr=o2.artnr) " +
" where o2.ordernr=? " +
" order by o2.pos"
					  );

			stm.setInt(1, ordernr);
			ResultSet rs = stm.executeQuery();
			ArrayList<Order2> order2List = new ArrayList();

			Order2 order2;
			while (rs.next()) {
				order2 = new Order2();
				order2.artnr = rs.getString(2);
				order2.namn = rs.getString(3);
				order2.antal = rs.getDouble(4);
				order2.enh = rs.getString(5);
				order2.pris = rs.getDouble(6);
				order2.rab = rs.getDouble(7);
				order2.lagerTillgangliga = rs.getDouble(8);
				order2.lagerTillgangligaFilialer = rs.getDouble(9);
				order2.lagerBest = rs.getDouble(10);
				order2List.add(order2);
			}
			return order2List;
	 }

	 private ArrayList<OrderHand> fillOrderHand(Connection con, int ordernr) throws SQLException{
		ArrayList<OrderHand> orderHandList = new ArrayList();
		PreparedStatement stm = con.prepareStatement("select serverdatum, anvandare, handelse, transportor, fraktsedelnr from orderhand where ordernr=? order by datum, tid");
		stm.setInt(1, ordernr);
		ResultSet rs = stm.executeQuery();

		OrderHand orderHand;
		while (rs.next()) {
			orderHand = new OrderHand();
			orderHand.datum = rs.getDate(1);
			orderHand.anvandare = rs.getString(2);
			orderHand.handelse = rs.getString(3);
			orderHand.transportor = rs.getString(4);
			orderHand.fraktsedelnr = rs.getString(5);
			orderHandList.add(orderHand);
		}

		return orderHandList;
	 }


	 public OrderLookupResp getBvOrderLookup(int bvOrdernr)  {
		 OrderLookupResp response=new OrderLookupResp();
		 Connection sxCon=null;
		 Connection bvCon=null;

		 try {
			sxCon = sxDataSource.getConnection();
			bvCon = bvDataSource.getConnection();
			PreparedStatement bvStm;
			PreparedStatement sxStm;
			ResultSet bvRs;
			ResultSet sxRs;

			//Hämta bvOrder
			//bvorder1
			bvStm = getOrder1PreparedStatement(bvCon, "o1.ordernr=?");
			bvStm.setInt(1, bvOrdernr);
			bvRs = bvStm.executeQuery();
			response.bvOrder = new Order();
			if (bvRs.next()) {
				response.bvOrder.order1 = getOrder1FromResultset(bvRs);
			} else {
				response.errorMessage = "Ordernr " + bvOrdernr + " saknas.";
				return response;
			}

			//bvorder2
			response.bvOrder.order2List = fillOrder2(bvCon, bvOrdernr);

			//bvOrderHand
			response.bvOrder.orderHandList = fillOrderHand(bvCon, bvOrdernr);

			//sxorder
			sxStm = getOrder1PreparedStatement(sxCon, "o1.kundordernr=? and o1.kundnr=?");
			sxStm.setInt(1, bvOrdernr);
			sxStm.setString(2, BVKUNDNR);
			sxRs = sxStm.executeQuery();
			Order sxOrder;
			response.sxOrderList = new ArrayList();
			while (sxRs.next()) {
				sxOrder = new Order();
				sxOrder.order1 = getOrder1FromResultset(sxRs);
				sxOrder.order2List = fillOrder2(sxCon, sxOrder.order1.ordernr);
				sxOrder.orderHandList = fillOrderHand(sxCon, sxOrder.order1.ordernr);
				response.sxOrderList.add(sxOrder);
			}

		} catch (SQLException e) { e.printStackTrace(); response.errorMessage="Undantag " + e.getMessage();}
		finally {
			try { sxCon.close(); } catch (Exception e) {}
			try { bvCon.close(); } catch (Exception e) {}
		}

		 return response;
	 }





	 //OBS Håll selectsatsenn i synk mellan getOrder1PreparedStatement och getUtlev1PreparedStatement
	 private PreparedStatement getOrder1PreparedStatement(Connection con, String sqlWhere) throws SQLException{
		if (sqlWhere==null) sqlWhere="1=1";
		return con.prepareStatement(
"select o1.lagernr, o1.ordernr, o1.status, o1.kundnr, o1.namn, o1.datum, o3.summa, " +
" case when o1.moms=1 then (1+f.moms1/100)*o3.summa else case when o1.moms=2 then (1+f.moms2/100)*o3.summa else case when o1.moms=3 then (1+f.moms3/100)*o3.summa else o3.summa end end end "+
" , o1.dellev, null " +
" , o1.adr1, o1.adr2, o1.adr3, o1.levadr1, o1.levadr2, o1.levadr3 "	+
" from order1 o1 left outer join " +
" (select ordernr as ordernr, sum(o2.summa) as summa from order2 o2 group by ordernr) o3 on o3.ordernr=o1.ordernr "+
" left outer join fuppg f on 1=1 "+
" where " + sqlWhere +
" order by o1.ordernr"
		  );

	 }

	 //OBS Håll selectsatsenn i synk mellan getOrder1PreparedStatement och getUtlev1PreparedStatement
	 private PreparedStatement getUtlev1PreparedStatement(Connection con, String sqlWhere) throws SQLException{
		if (sqlWhere==null) sqlWhere="1=1";
		return con.prepareStatement(
"select o1.lagernr, o1.ordernr, o1.status, o1.kundnr, o1.namn, o1.datum, o3.summaexmoms, " +
" o3.summainkmoms "+
" , o1.dellev, o1.faktnr " +
" , o1.adr1, o1.adr2, o1.adr3, o1.levadr1, o1.levadr2, o1.levadr3 "	+
" from utlev1 o1 left outer join " +
" (select f2.ordernr as ordernr, f2.faktnr as faktnr, sum(f2.summa) as summaexmoms, sum(f2.summa*(1+f1.momsproc/100)) as summainkmoms from faktura2 f2 join faktura1 f1 on f1.faktnr=f2.faktnr group by f2.faktnr, f2.ordernr) o3 on o3.ordernr=o1.ordernr and o3.faktnr=o1.faktnr "+
" where " + sqlWhere +
" order by o1.ordernr"
		  );

	 }



	 private Order1 getOrder1FromResultset(ResultSet rs) throws SQLException{
		Order1 order1 = new Order1();
		order1.lagernr = rs.getShort(1);
		order1.ordernr = rs.getInt(2);
		order1.status = rs.getString(3);
		order1.kundnr = rs.getString(4);
		order1.namn = rs.getString(5);
		order1.datum = new java.util.Date(rs.getDate(6).getTime());
		order1.summaInkMoms = rs.getDouble(8);
		order1.dellev = rs.getInt(9);
		order1.faktnr=rs.getInt(10);
		order1.adr1 = rs.getString(11);
		order1.adr2 = rs.getString(12);
		order1.adr3 = rs.getString(13);
		order1.levadr1 = rs.getString(14);
		order1.levadr2 = rs.getString(15);
		order1.levadr3 = rs.getString(16);
		if (order1.faktnr==0) order1.faktnr=null;
		if (SXConstant.ORDER_STATUS_SPARAD.equals(order1.status)) order1.isOverforbar=true; else order1.isOverforbar=false;
		return order1;
	 }




	 public Order1List getBvOrder1List(int filter) throws ServerErrorException {
		 Order1List order1List = new Order1List();
		 Connection con=null;
		 String sqlWhere=null;
		 switch (filter) {
			case FILTER_FOROVERFORING:
				sqlWhere="status='" + SXConstant.ORDER_STATUS_SPARAD + "'";
				break;
			case FILTER_AVVAKTANDE:
				sqlWhere="status='" + SXConstant.ORDER_STATUS_AVVAKT + "'";
				break;
			case FILTER_FORSKOTT:
				sqlWhere="forskatt=1" ;
				break;
			case FILTER_OVERFORDA:
				sqlWhere="status='" + SXConstant.ORDER_STATUS_OVERFORD + "'";
				break;
			default:
				sqlWhere="1=1";
				break;
		 }



		try {
			con = bvDataSource.getConnection();
			PreparedStatement stm = getOrder1PreparedStatement(con, sqlWhere);


			//stm.setString(1, sxSession.getKundnr());
			ResultSet rs = stm.executeQuery();
			Order1 order1;

			while (rs.next()) {
				order1List.orderLista.add(getOrder1FromResultset(rs));
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return order1List;
	 }






	 	 public Order1List getBvOrder1ListByLevAdr(String levAdr) throws ServerErrorException {
		 Order1List order1List = new Order1List();
		 Connection con=null;
		 if (levAdr==null) levAdr="";
		 String sokStr="%" + levAdr.toUpperCase().replace(' ', '%') + "%";
		 String sqlWhere="(upper(o1.levadr1) like ? or upper(o1.levadr2) like ? or upper(o1.levadr3) like ?"
					+ " or upper(o1.namn) like ? or upper(o1.adr1) like ? or upper(o1.adr2) like ? or upper(o1.adr3) like ?)";
		try {
			con = bvDataSource.getConnection();
			PreparedStatement stm;
			ResultSet rs;

			stm = getOrder1PreparedStatement(con, sqlWhere);
			stm.setString(1, sokStr);
			stm.setString(2, sokStr);
			stm.setString(3, sokStr);
			stm.setString(4, sokStr);
			stm.setString(5, sokStr);
			stm.setString(6, sokStr);
			stm.setString(7, sokStr);
			rs = stm.executeQuery();
			while (rs.next()) {
				order1List.orderLista.add(getOrder1FromResultset(rs));
			}

			stm = getUtlev1PreparedStatement(con, sqlWhere);
			stm.setString(1, sokStr);
			stm.setString(2, sokStr);
			stm.setString(3, sokStr);
			stm.setString(4, sokStr);
			stm.setString(5, sokStr);
			stm.setString(6, sokStr);
			stm.setString(7, sokStr);
			rs = stm.executeQuery();
			while (rs.next()) {
				order1List.orderLista.add(getOrder1FromResultset(rs));
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return order1List;
	 }












	 public OverforBVOrderResp overforBVOrder(int bvOrdernr, short lagernr, Integer callerId)  {
		OverforBVOrderResp resp= new OverforBVOrderResp();
		resp.callerId=callerId;
		try {
			resp.sxOrdernr = sxServerMainBean.overforBVOrder(BVKUNDNR, bvOrdernr, "BV", "00", lagernr);
			resp.bvOrdernr = bvOrdernr;
			resp.overfordOK=true;
		} catch (se.saljex.sxserver.SXEntityNotFoundException e) {
			 resp.error = e.getMessage();
			 resp.overfordOK=false;
		}
		return resp;
	}

	 public Order1List getSxOrder1ListFromFaktnr(int sxFaktnr) throws ServerErrorException {
		Connection con=null;
		Order1List order1List = new Order1List();
		try {
			con = sxDataSource.getConnection();
			PreparedStatement stm = con.prepareStatement(
"select f1.lagernr, f2.ordernr, u1.status, f1.kundnr, f1.namn, u1.datum, u1.dellev, f1.faktnr, u1.levadr1, u1.levadr2, u1.levadr3, u1.kundordernr, "+
" sum(f2.summa), sum(f2.summa)*(1+f1.momsproc/100) "+
" from faktura1 f1 join faktura2 f2 on f1.faktnr=f2.faktnr left outer join utlev1 u1 on u1.ordernr=f2.ordernr "+
" where f2.faktnr = ? and f1.kundnr=? " +
" group by f1.lagernr, f2.ordernr, u1.status, f1.kundnr, f1.namn, u1.datum, u1.dellev, f1.faktnr, u1.levadr1, u1.levadr2, u1.levadr3, u1.kundordernr "+
" order by f2.ordernr");

			stm.setInt(1, sxFaktnr);
			stm.setString(2, BVKUNDNR);

			ResultSet rs = stm.executeQuery();

			Order1 order1;
			while (rs.next()) {
				order1 = new Order1();
				order1.lagernr = rs.getShort(1);
				order1.ordernr = rs.getInt(2);
				order1.status = rs.getString(3);
				order1.kundnr = rs.getString(4);
				order1.namn = rs.getString(5);
				order1.datum = rs.getDate(6);
				order1.dellev = rs.getInt(7);
				order1.faktnr = rs.getInt(8);
				order1.levadr1 = rs.getString(9);
				order1.levadr2 = rs.getString(10);
				order1.levadr3 = rs.getString(11);
				order1.kundordernr = rs.getInt(12);
				order1.summaInkMoms = rs.getDouble(14);
				order1List.orderLista.add(order1);
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return order1List;

	 }



	 public Faktura1List getSxFaktura1List() throws ServerErrorException {
		Connection con=null;
		Faktura1List faktura1List = new Faktura1List();
		try {
			con = sxDataSource.getConnection();
			PreparedStatement stm = con.prepareStatement(
"select faktnr, datum, namn, adr1, adr2, adr3, saljare, referens, momsproc, ktid, "+
" ranta, bonus, t_netto, t_moms, t_orut, t_attbetala "+
" from faktura1 f1 "+
" where kundnr=? "	+
" order by f1.faktnr desc"
					  );
			stm.setString(1, BVKUNDNR);

			ResultSet rs = stm.executeQuery();

			Faktura1 faktura1;
			while (rs.next()) {
		System.out.print("**********************H3");
				faktura1 = new Faktura1();
				faktura1.faktnr = rs.getInt(1);
				faktura1.datum = rs.getDate(2);
				faktura1.namn = rs.getString(3);
				faktura1.adr1 = rs.getString(4);
				faktura1.adr2 = rs.getString(5);
				faktura1.adr3 = rs.getString(6);
				faktura1.saljare = rs.getString(7);
				faktura1.referens = rs.getString(8);
				faktura1.momsprocent = rs.getShort(9);
				faktura1.ktid = rs.getShort(10);
				faktura1.ranta = rs.getDouble(11);
				faktura1.bonus = rs.getShort(12)!=0;
				faktura1.nettobelopp = rs.getDouble(13);
				faktura1.momsbelopp = rs.getDouble(14);
				faktura1.oresutjamning = rs.getDouble(15);
				faktura1.attbetala = rs.getDouble(16);
				faktura1List.faktura1List.add(faktura1);
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return faktura1List;

	 }







	 private BetaljournalList getBetaljournalList(DataSource dataSource, String kundnr, int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		Connection con=null;
		BetaljournalList betaljournalList = new BetaljournalList();
		String sqlWhere = "ar=? and man=?";
		if (!SXUtil.isEmpty(kundnr)) sqlWhere = sqlWhere + " and kundnr=?";

		try {
			con = dataSource.getConnection();
			PreparedStatement stm = con.prepareStatement(
"select faktnr, kundnr, bet, betdat, betsatt, ar, man from sxfakt.betjour " +
" where " + sqlWhere +
" order by betdat, betsatt, faktnr"
					  );
			stm.setInt(1, bokforingsar);
			stm.setInt(2, bokforingsmanad);
			if (!SXUtil.isEmpty(kundnr)) stm.setString(3, BVKUNDNR);

			ResultSet rs = stm.executeQuery();
			Betaljournal betaljournal;
			while (rs.next()) {
				betaljournal = new Betaljournal();
				betaljournal.faktnr = rs.getInt(1);
				betaljournal.kundnr= rs.getString(2);
				betaljournal.betalt = rs.getDouble(3);
				betaljournal.betaldatum = rs.getDate(4);
				betaljournal.betalsatt = rs.getString(5);
				betaljournal.bokforingsar = rs.getShort(6);
				betaljournal.bokforinsmanad = rs.getShort(7);
				betaljournalList.betaljournalList.add(betaljournal);
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return betaljournalList;
	 }


	private FakturajournalList getFakturajournalList(DataSource dataSource, String kundnr, int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		Connection con=null;
		FakturajournalList fakturajournalList = new FakturajournalList();

		String sqlWhere = "year(datum)=? and mont(datum)=?";
		if (!SXUtil.isEmpty(kundnr)) sqlWhere = sqlWhere + " and kundnr=?";

		try {
			con = dataSource.getConnection();
			PreparedStatement stm = con.prepareStatement(
"select faktnr, kundnr, t_netto, t_moms, t_orut, t_attbetala from faktura1  " +
" where " + sqlWhere +
" order by faktnr"
					  );

			stm.setInt(1, bokforingsar);
			stm.setInt(2, bokforingsmanad);
			if (!SXUtil.isEmpty(kundnr)) stm.setString(3, BVKUNDNR);

			ResultSet rs = stm.executeQuery();
			Fakturajournal fakturajournal;
			while (rs.next()) {
				fakturajournal = new Fakturajournal();
				fakturajournal.faktnr = rs.getInt(1);
				fakturajournal.kundnr = rs.getString(2);
				fakturajournal.nettobelopp = rs.getDouble(3);
				fakturajournal.momsbelopp = rs.getDouble(4);
				fakturajournal.oresutjamningsbelopp = rs.getDouble(5);
				fakturajournal.attbetalabelopp = rs.getDouble(6);
				fakturajournalList.fakturajournalList.add(fakturajournal);
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return fakturajournalList;
	}

	// Betaljournal för angiven bokföringsperiod, endast betalningar som bv har gjort
	public BetaljournalList getSxBetaljournalList(int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		return getBetaljournalList(sxDataSource, BVKUNDNR, bokforingsar, bokforingsmanad);
	}


	// Fakturajournal för angiven bokföringsperiod, endast fakturor som bv har fått
	public FakturajournalList getSxFakturajurnalList(int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		return getFakturajournalList(sxDataSource, BVKUNDNR, bokforingsar, bokforingsmanad);
	 }

	// Betaljournal för angiven bokföringsperiod, alla betalningar i bv
	public BetaljournalList getBvBetaljournalList(int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		return getBetaljournalList(bvDataSource, BVKUNDNR, bokforingsar, bokforingsmanad);
	}


	// Fakturajournal för angiven bokföringsperiod, alla fakturor i bv
	public FakturajournalList getBvFakturajurnalList(int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		return getFakturajournalList(bvDataSource, BVKUNDNR, bokforingsar, bokforingsmanad);
	 }


}
