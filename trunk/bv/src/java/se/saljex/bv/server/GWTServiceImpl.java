/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import se.saljex.bv.client.GWTService;
import se.saljex.bv.client.Order;
import se.saljex.bv.client.Order1;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.Order2;
import se.saljex.bv.client.OrderHand;
import se.saljex.bv.client.OrderLookupResponse;
import se.saljex.bv.client.OverforBVOrderResponse;
import se.saljex.bv.client.ServerErrorException;
import se.saljex.sxserver.SXConstant;
import se.saljex.sxserver.SXEntityNotFoundException;
import se.saljex.sxserver.SxServerMainLocal;

/**
 *
 * @author ulf
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {



	@EJB
	private SxServerMainLocal sxServerMainBean;
//	@EJB
//	private LocalWebSupportLocal localWebSupportBean;
	@javax.annotation.Resource(name = "sxadmkundbv")
	private DataSource bvDataSource;
	@javax.annotation.Resource(name = "sxadm")
	private DataSource sxDataSource;

	private final static String BVKUNDNR="0855115290";

	public GWTServiceImpl() {
	}


    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
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


	 public OrderLookupResponse getOrderLookup(int bvOrdernr)  {
		 OrderLookupResponse response=new OrderLookupResponse();
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
				sxOrder.order1 = getOrder1FromResultset(bvRs);
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

	 private PreparedStatement getOrder1PreparedStatement(Connection con, String sqlWhere) throws SQLException{
		if (sqlWhere==null) sqlWhere="1=1";
		return con.prepareStatement(
"select o1.lagernr, o1.ordernr, o1.status, o1.kundnr, o1.namn, o1.datum, o3.summa, " +
" case when o1.moms=1 then (1+f.moms1/100)*o3.summa else case when o1.moms=2 then (1+f.moms2/100)*o3.summa else case when o1.moms=3 then (1+f.moms3/100)*o3.summa else o3.summa end end end "+
" from order1 o1 left outer join " +
" (select ordernr as ordernr, sum(o2.summa) as summa from order2 o2 group by ordernr) o3 on o3.ordernr=o1.ordernr "+
" left outer join fuppg f on 1=1 "+
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
		order1.datum = rs.getDate(6);
		order1.summaInkMoms = rs.getDouble(7);
		if (SXConstant.ORDER_STATUS_SPARAD.equals(order1.status)) order1.isOverforbar=true; else order1.isOverforbar=false;
		return order1;
	 }

	 public Order1List getOrder1List(int filter) throws ServerErrorException {
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
				sqlWhere="* TO-DO *";
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

	public OverforBVOrderResponse overforBVOrder(int bvOrdernr, short lagernr, Integer callerId)  {
		OverforBVOrderResponse resp= new OverforBVOrderResponse();
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

}
