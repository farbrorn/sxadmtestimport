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
import javax.sql.DataSource;
import se.saljex.bv.client.Artikel;
import se.saljex.bv.client.ArtikelList;
import se.saljex.bv.client.Betaljournal;
import se.saljex.bv.client.BetaljournalList;
import se.saljex.bv.client.Bokord;
import se.saljex.bv.client.BokordList;
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
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SxServerMainRemote;

/**
 *
 * @author ulf
 */
public class ServiceImpl {
	private SxServerMainRemote sxServerMainBean;
	private DataSource bvDataSource;
	private DataSource sxDataSource;

	private final static String BVKUNDNR="0855115291";
	private final static String BVKUNDNR2="0855115290";
	 public final static int FILTER_ALLA=0;				//Alla order
	 public final static int FILTER_FOROVERFORING=1;	//Order som är klara att föras över till sx
	 public final static int FILTER_OVERFORDA=2;			//Order som är överförda till sx
	 public final static int FILTER_AVVAKTANDE=3;		//Order med status avvaktande
 	 public final static int FILTER_FORSKOTT=4;			//Order som skall betalas förskott


	public ServiceImpl(SxServerMainRemote sxServerMainBean, DataSource sxDataSource, DataSource bvDataSource) {
		this.sxServerMainBean = sxServerMainBean;
		this.sxDataSource=sxDataSource;
		this.bvDataSource=bvDataSource;
	}



	 private ArrayList<Order2> fillOrder2(Connection con, int ordernr) throws SQLException{
			PreparedStatement stm = con.prepareStatement(
"select o1.lagernr, o2.artnr, o2.namn, o2.best, o2.enh, o2.pris, o2.rab, l.ilager - l.iorder + o2.best as tillgangliga, l2.tillgangliga-(l.ilager-l.iorder+o2.best) as tillgangligaovriga, l.best, o2.netto " +
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
				order2.nettonetto = rs.getDouble(11);
				order2List.add(order2);
			}
			return order2List;
	 }

	 private ArrayList<Order2> fillOrder2FromFaktura(Connection con, int ordernr) throws SQLException{
			PreparedStatement stm = con.prepareStatement(
"select f1.lagernr, f2.artnr, f2.namn, f2.lev, f2.enh, f2.pris, f2.rab, l.ilager - l.iorder as tillgangliga, l2.tillgangliga-(l.ilager-l.iorder) as tillgangligaovriga, l.best, f2.netto " +
" from faktura1 f1 join faktura2 f2 on (f1.faktnr=f2.faktnr) "+
" left outer join lager l on (l.artnr=f2.artnr and l.lagernr=f1.lagernr) " +
" left outer join ( " +
	" select artnr as artnr , sum(ilager - iorder) as tillgangliga from lager " +
	" group by artnr " +
" ) l2 on (l2.artnr=f2.artnr) " +
" where f2.ordernr=? " +
" order by f2.pos"
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
				order2.nettonetto = rs.getDouble(11);
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



	 //Returnerar order. Om ordern är fakturerad returneras utleveransen och isFakturerad=true
	 public Order getBvOrder(int ordernr) throws ServerErrorException {
		 return getOrder(bvDataSource, ordernr);
	 }

	 //Returnerar order. Om ordern är fakturerad returneras utleveransen och isFakturerad=true
	 public Order getSxOrder(int ordernr) throws ServerErrorException {
		 return getOrder(sxDataSource, ordernr);
	 }

	 private Order getOrder(DataSource dataSource, int ordernr) throws ServerErrorException {
		 Order response=new Order();
		 Connection con=null;

		 try {
			con = dataSource.getConnection();
			PreparedStatement stm;
			ResultSet rs;

			stm = getOrder1PreparedStatement(con, "o1.ordernr=?");
			stm.setInt(1, ordernr);
			rs = stm.executeQuery();
			if (rs.next()) {
				response.order1 = getOrder1FromResultset(rs);
				response.order2List = fillOrder2(con, ordernr);
			} else {
				stm = getUtlev1PreparedStatement(con, "u1.ordernr=?");
				stm.setInt(1, ordernr);
				rs = stm.executeQuery();
				if (rs.next()) {
					response.order1 = getOrder1FromResultset(rs);
					response.order1.isFakturerad=true;
					response.order2List = fillOrder2FromFaktura(con, ordernr);
				} else {
					throw new ServerErrorException("Order saknas");
				}
			}

			//bvOrderHand
			response.orderHandList = fillOrderHand(con, ordernr);

		} catch (SQLException e) { e.printStackTrace(); throw new ServerErrorException("SQL undantagsfel");}
		finally {
			try { con.close(); } catch (Exception e) {}
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
" , o1.adr1, o1.adr2, o1.adr3, o1.levadr1, o1.levadr2, o1.levadr3, o1.kundordernr, o1.forskatt, o1.forskattbetald, o1.betalsatt "	+
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
" , o1.adr1, o1.adr2, o1.adr3, o1.levadr1, o1.levadr2, o1.levadr3, o1.kundordernr, o1.forskatt, o1.forskattbetald, o1.betalsatt "	+
" from utlev1 o1 left outer join " +
" (select f2.ordernr as ordernr, f2.faktnr as faktnr, round(sum(f2.summa),2) as summaexmoms, round(sum(f2.summa*(1+f1.momsproc/100)),2) as summainkmoms from faktura2 f2 join faktura1 f1 on f1.faktnr=f2.faktnr group by f2.faktnr, f2.ordernr) o3 on o3.ordernr=o1.ordernr and o3.faktnr=o1.faktnr "+
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
		order1.summaInkMoms = SXUtil.getRoundedDecimal(rs.getDouble(8));
		order1.dellev = rs.getInt(9);
		order1.faktnr=rs.getInt(10);
		order1.adr1 = rs.getString(11);
		order1.adr2 = rs.getString(12);
		order1.adr3 = rs.getString(13);
		order1.levadr1 = rs.getString(14);
		order1.levadr2 = rs.getString(15);
		order1.levadr3 = rs.getString(16);
		order1.kundordernr = rs.getInt(17);
		order1.forskatt = rs.getInt(18)!=0;
		order1.forskattBetalt = rs.getInt(19)!=0;
		order1.betalsatt= rs.getString(20);
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
		} catch (se.saljex.sxlibrary.exceptions.SXEntityNotFoundException e) {
			 resp.error = e.getMessage();
			 resp.overfordOK=false;
		}
		return resp;
	}

	 public Order1List getSxOrder1ListFromFaktnr(int faktnr) throws ServerErrorException {
		 return getOrder1ListFromFaktnr(sxDataSource, true, faktnr);
	 }
	 public Order1List getBvOrder1ListFromFaktnr(int faktnr) throws ServerErrorException {
		 return getOrder1ListFromFaktnr(bvDataSource, false, faktnr);
	 }

	 private Order1List getOrder1ListFromFaktnr(DataSource dataSource, boolean includeBvKundnrFilter, int faktnr) throws ServerErrorException {
		Connection con=null;
		Order1List order1List = new Order1List();
		String sql =
"select f1.lagernr, f2.ordernr, u1.status, f1.kundnr, f1.namn, u1.datum, u1.dellev, f1.faktnr, u1.levadr1, u1.levadr2, u1.levadr3, u1.kundordernr, "+
" sum(f2.summa), sum(f2.summa*(1+f1.momsproc/100)) "+
" from faktura1 f1 join faktura2 f2 on f1.faktnr=f2.faktnr left outer join utlev1 u1 on u1.ordernr=f2.ordernr "+
" where f2.faktnr = ? ";
		if (includeBvKundnrFilter) sql = sql + " and f1.kundnr in (?,?) ";
		sql=sql + " group by f1.lagernr, f2.ordernr, u1.status, f1.kundnr, f1.namn, u1.datum, u1.dellev, f1.faktnr, u1.levadr1, u1.levadr2, u1.levadr3, u1.kundordernr "+
					" order by f2.ordernr";

		try {
			con = dataSource.getConnection();
			PreparedStatement stm = con.prepareStatement(sql);

			stm.setInt(1, faktnr);
			if (includeBvKundnrFilter) {
				stm.setString(2, BVKUNDNR);
				stm.setString(3, BVKUNDNR2);
			}

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




	 public Faktura1List getSxFaktura1List(int ar, int man, int offset, int limit) throws ServerErrorException {
		Connection con=null;
		Faktura1List faktura1List = new Faktura1List();
		try {
			con = sxDataSource.getConnection();
			String sqlWhere="";
			String sqlOffset="";
			if (ar!=0) sqlWhere=sqlWhere + " and year(datum) = " + ar;
			if (man!=0) sqlWhere=sqlWhere + " and month(datum) = " + man;
			if (offset>0) sqlOffset=" offset " + offset;
			faktura1List.offset=offset;
			faktura1List.limit=limit;

			String sql=
				"select faktnr, datum, namn, adr1, adr2, adr3, saljare, referens, momsproc, ktid, "+
				" ranta, bonus, t_netto, t_moms, t_orut, t_attbetala "+
				" from faktura1 f1 "+
				" where kundnr in (?,?) "	+ sqlWhere +
				" order by f1.faktnr desc " + sqlOffset;
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, BVKUNDNR);
			stm.setString(2, BVKUNDNR2);

			ResultSet rs = stm.executeQuery();

			Faktura1 faktura1;
			int cn=0;
			while (rs.next() && (cn < limit || limit==0)) {
				cn++;
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
			if (limit!=0) if (rs.next()) faktura1List.hasMoreRows=true; else faktura1List.hasMoreRows=false;

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
"select faktnr, kundnr, bet, betdat, betsatt, ar, man from betjour " +
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

		String sqlWhere = "year(datum)=? and month(datum)=?";
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
		return getBetaljournalList(bvDataSource, null, bokforingsar, bokforingsmanad);
	}


	// Fakturajournal för angiven bokföringsperiod, alla fakturor i bv
	public FakturajournalList getBvFakturajurnalList(int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		return getFakturajournalList(bvDataSource, null, bokforingsar, bokforingsmanad);
	 }



	public BokordList getBvBokordList(int bokforingsar, int bokforingsmanad) throws ServerErrorException {
		Connection con=null;
		BokordList bokordList = new BokordList();

		try {
			con = bvDataSource.getConnection();
			PreparedStatement stm = con.prepareStatement(
"select konto, faktnr, typ, summa, datum from bokord " +
" where year(datum)=? and month(datum)=?"+
" order by faktnr, typ, konto"
					  );

			stm.setInt(1, bokforingsar);
			stm.setInt(2, bokforingsmanad);

			ResultSet rs = stm.executeQuery();
			Bokord bokord;
			while (rs.next()) {
				bokord = new Bokord();
				bokord.konto = rs.getString(1);
				bokord.faktnr = rs.getInt(2);
				bokord.typ = rs.getString(3);
				bokord.summa = rs.getDouble(4);
				bokord.datum = rs.getDate(5);
				bokordList.bokordList.add(bokord);
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return bokordList;
	}


	private PreparedStatement getArtikelListPreparedStatement(Connection con, String sqlWhere, int offset) throws SQLException{
		if (sqlWhere==null) sqlWhere="1=1";
		String sql =
"select nummer, namn, lev, bestnr, enhet, utpris, inpris, rab, inp_fraktproc, " +
" inp_miljo, inp_frakt, konto, rabkod, kod1, prisdatum, inpdat, utgattdatum, onskattb, inp_enhetsfaktor , struktnr, forpack " +
" from artikel " +
" where " + sqlWhere +
" order by nummer";

		if (offset > 0) sql=sql + " offset " + offset;

		return con.prepareStatement(sql);
	}

	private Artikel getArtikelFromResultSet(ResultSet rs) throws SQLException{
		Artikel artikel = new Artikel();
		artikel.nummer = rs.getString(1);
		artikel.namn = rs.getString(2);
		artikel.lev = rs.getString(3);
		artikel.bestnr = rs.getString(4);
		artikel.enhet = rs.getString(5);
		artikel.utpris = rs.getDouble(6);
		artikel.inpris = rs.getDouble(7);
		artikel.inrab = rs.getDouble(8);
		artikel.inp_fraktproc = rs.getDouble(9);
		artikel.inp_miljo = rs.getDouble(10);
		artikel.inp_frakt = rs.getDouble(11);
		artikel.konto = rs.getString(12);
		artikel.rabkod = rs.getString(13);
		artikel.rabkod1 = rs.getString(14);
		artikel.utprisdatum = rs.getDate(15);
		artikel.inprisdatum = rs.getDate(16);
		artikel.utgattdatum = rs.getDate(17);
		artikel.onskattb = rs.getInt(18);
		artikel.inp_enhetsfaktor = rs.getBigDecimal(19).doubleValue();
		artikel.struktnr = rs.getString(20);
		artikel.forpack = rs.getDouble(21);
		artikel.nettoNetto = artikel.inpris*(1-artikel.inrab/100)*(1+artikel.inp_fraktproc/100) + artikel.inp_frakt + artikel.inp_miljo;
		return artikel;
	}

	private ArtikelList getArtikelList(DataSource dataSource, String query, int offset, int limit) throws ServerErrorException {
		Connection con=null;
		ArtikelList artikelList = new ArtikelList();
		String sqlWhere=null;
		String s=null;
		if (!SXUtil.isEmpty(query)) {
			s = "%" + query.trim().replace(' ', '%') + "%";
			sqlWhere = "upper(nummer) like upper(?) or "
					  + " upper(namn) like upper(?) or "
					  + " upper(lev) like upper(?) or "
					  + " upper(bestnr) like upper(?) or "
					  + " upper(refnr) like upper(?) "
					  ;
		}

		try {
			con = dataSource.getConnection();
			PreparedStatement stm = getArtikelListPreparedStatement(con, sqlWhere, offset);
			if (!SXUtil.isEmpty(query)) {
				stm.setString(1, s);
				stm.setString(2, s);
				stm.setString(3, s);
				stm.setString(4, s);
				stm.setString(5, s);
			}

			ResultSet rs = stm.executeQuery();
			int cn=0;
			while (rs.next() && (cn < limit || limit==0)) {
				cn++;
				artikelList.artikelList.add(getArtikelFromResultSet(rs));
			}
			if (limit!=0) if (rs.next()) artikelList.hasMoreRows=true; else artikelList.hasMoreRows=false;
			artikelList.limit=limit;
			artikelList.offset=offset;
			artikelList.query=query;

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return artikelList;
	}


	public ArtikelList getSxArtikelList(String query, int offset, int limit) throws ServerErrorException {
		return getArtikelList(sxDataSource, query, offset, limit);
	}
	public ArtikelList getBvArtikelList(String query, int offset, int limit) throws ServerErrorException {
		return getArtikelList(bvDataSource, query, offset, limit);
	}


	private Artikel getArtikel(DataSource dataSource, String nummer) throws ServerErrorException {
		Connection con=null;
		try {
			con = dataSource.getConnection();
			PreparedStatement stm = getArtikelListPreparedStatement(con, "nummer=?", 0);
			stm.setString(1, nummer);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) return getArtikelFromResultSet(rs); else return null;

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}

	}
	public Artikel getSxArtikel(String nummer) throws ServerErrorException {
		return getArtikel(sxDataSource, nummer);
	}
	public Artikel getBvArtikel(String nummer) throws ServerErrorException {
		return getArtikel(bvDataSource, nummer);
	}

	public int skapaBvForskattsbetalning(int ordernr, double belopp, String anvandare, char betalSatt, java.util.Date betalDatum, int talongLopnr) throws ServerErrorException {
	 throw new ServerErrorException("Metoden för förskott är borttAGEN!" );
//		try {
//			return sxServerMainBean.skapaBvForskattFaktura(ordernr, belopp, "11", anvandare, betalSatt, betalDatum, talongLopnr);
//		} catch (Exception e) { throw new ServerErrorException(e.getMessage()); }
	}

}
