/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.sql.DataSource;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.SxOrderLastException;

/**
 *
 * @author Ulf
 */
public class SamfaktByEpostHandler {
	FakturaHandler fakturaHandler = null;
	DataSource sxDataSource;
	ArrayList<ArrayList<Integer>> orderList = new ArrayList();
	ArrayList<Integer> orderPaKund = null;
	String anvandare;
	ArrayList<OrderHandler> orderPaFaktura;		//Lista på ordrarna som är på en faktura
	OrderHandler fakturaOrder;					// Ordern som ska bli faktura - inneller alla ordrar i orderPaFaktura
	Iterator<ArrayList<Integer>> orderListIterator;

	public SamfaktByEpostHandler(DataSource sxDataSource, String anvandare) throws SQLException {
		this.sxDataSource = sxDataSource;
		this.anvandare=anvandare;
		Connection con=null;
		try {
			con = sxDataSource.getConnection();
			doPrepare(con);
		}
		finally {
			try { con.close(); } catch (Exception e) {}
		}

	}



	// Gör iordning komplett lista med order grupperad efter kundnr och andra group break
	// Varje ArrayLiost i orderLista innehåller samtliga order som ska med på en faktura
	// check för låsta order och felaktiga priser görs ej
	private void doPrepare(Connection con) throws SQLException{
		PreparedStatement stm;
		ResultSet rs;
		String q =
				"select a.kundnr, a.lagernr, a.bonus, a.saljare, a.ordernr, a.dellev, a.moms, kn.sarfaktura from order1 a, kund kn "+
				" where " +
				" kn.nummer = a.kundnr and kn.skickafakturaepost > 0 and kn.ejfakturerbar = 0" +
					" and a.status in (?, ?) " +
					" and (a.tidigastfaktdatum is null or a.tidigastfaktdatum <= current_date) " +
					" and (lastdatum is null or lastdatum <= current_date-?) " +
					" and a.kundnr in (select kundnr from order1 o1, order2 o2, kund k1 " +
						" where o1.ordernr = o2.ordernr  and k1.nummer=o1.kundnr" +
						" and o1.status in (?,?) " +
						" and (o1.tidigastfaktdatum is null or o1.tidigastfaktdatum <= current_date) " +
						" and (lastdatum is null or lastdatum <= current_date-?) " +
						" group by o1.kundnr, k1.samfakgrans " +
						" having (sum(abs(o2.summa)) > k1.samfakgrans and k1.samfakgrans > 0) or (sum(abs(o2.summa)) > ? and k1.samfakgrans <= 0)  or min(o1.datum) < current_date-? " +
						" ) "+
//					" and a.ordernr not in (select oo2.ordernr from order2 oo2 "+
//						" where lev > 0 and pris = 0 and artnr not like '*UD%') "+
//					" and a.kundnr in ( select nummer from kund k  where k.skickafakturaepost > 0 ) " +
				" order by a.kundnr, a.lagernr, a.bonus, a.saljare, a.ordernr, a.dellev";
		int maxDagar = 21;
		double minBelopp = 5000;
		try {
			maxDagar = new Integer(ServerUtil.getSXReg(con, SXConstant.SXREG_SXSERVSAMFAKTMAXDAGAR, SXConstant.SXREG_SXSERVSAMFAKTMAXDAGAR_DEFAULT));
		} catch (Exception e) { ServerUtil.log("Felaktigt MaxDagar för samfakt i sxreg." +SXConstant.SXREG_SXSERVSAMFAKTMAXDAGAR );}
		try {
			minBelopp = new Double(ServerUtil.getSXReg(con, SXConstant.SXREG_SXSERVSAMFAKTMINBELOPP, SXConstant.SXREG_SXSERVSAMFAKTMINBELOPP_DEFAULT));
		} catch (Exception e) { ServerUtil.log("Felaktigt minBelopp för samfakt i sxreg." +SXConstant.SXREG_SXSERVSAMFAKTMINBELOPP );}

		stm = con.prepareStatement(q);
		stm.setString(1, SXConstant.ORDER_STATUS_HAMT);
		stm.setString(2, SXConstant.ORDER_STATUS_SAMFAK);
		stm.setInt(3, 2);	//Dagar som ordern kan vara låst utan att vi automatiskt låser upp den. Lämpligt att ha 2 dagar så det inte blir problem vid för tidig upplåsning runt middnatt
		stm.setString(4, SXConstant.ORDER_STATUS_HAMT);
		stm.setString(5, SXConstant.ORDER_STATUS_SAMFAK);
		stm.setInt(6, 2);	//Dagar som ordern kan vara låst utan att vi automatiskt låser upp den. Lämpligt att ha 2 dagar så det inte blir problem vid för tidig upplåsning runt middnatt
		stm.setDouble(7, minBelopp); //Minsta belopp för att samfakturera
		stm.setInt(8, maxDagar);	//Högsta antalet dagar som order får ligga ofakturerat även om inte min.beloppet är uppnått
		rs = stm.executeQuery();

		String tempKundnr="";
		Short tempLagernr=0;
		Short tempBonus=0;
		String tempSaljare="";
		String avrundadSaljare="";
		Short tempMoms = 0;

		OrderHandler oh = null;

		while (rs.next()) {
			avrundadSaljare=getAvrundadSaljare(rs.getString(4));

			//orderPaKund skall vara null vid första loopen. temp-variablerna får inte vara null
			// Om det är första loopen (orderPaKund==null), kunden vill ha särfaktura eller dax för group break
			if (orderPaKund==null || rs.getShort(8)!=0 || !(tempKundnr.equals(rs.getString(1)) && tempLagernr.equals(rs.getShort(2)) && tempBonus.equals(rs.getShort(3)) && tempSaljare.equals(avrundadSaljare) && tempMoms.equals(rs.getShort(7)))	) {
				tempKundnr = rs.getString(1);		//Sätt nya variabler för group break
				tempLagernr = rs.getShort(2);
				tempBonus = rs.getShort(3);
				tempSaljare = avrundadSaljare;
				tempMoms = rs.getShort(7);

				orderPaKund = new ArrayList();		// Gör en ny orderlista
				orderList.add(orderPaKund);			// och lägg till den direkt
			}

			orderPaKund.add(rs.getInt(5));
		}
		orderListIterator = orderList.iterator();

		
	}

	public boolean hasNextFakturaOrder() { return orderListIterator.hasNext(); }

	public boolean nextFakturaOrder(EntityManager em) {
		// Nu är alla order inlästa, och vi ska scanna dem för fel pris och göra faktura
		// Strategi - varje anrop hit gör iordning en ny uppsättning orderPaFaktura och fakturaOrder som sedan hämtas med respektive get-anrop

		boolean orderFakturerbar;
		OrderHandler forstaOrder;
		ArrayList<Integer> a;
		
		while (true) {
			if (!orderListIterator.hasNext()) {
				orderPaFaktura=null;
				fakturaOrder=null;
				return false;
			} else {
				a = orderListIterator.next();	// Hämta lista på ordrar som ska med på fakturan (forutsatt inget fel på ordrarna)
				ArrayList<OrderHandler> oList = new ArrayList();
				for (Integer ordernr : a) {
					try {
						oList.add(new OrderHandler(em, ordernr, anvandare, 2));
					} catch (SxOrderLastException e) {}
				}
				try {
					forstaOrder = oList.get(0);
				} catch (IndexOutOfBoundsException e) { continue; }
				if (forstaOrder==null) continue;

				fakturaOrder = new OrderHandler(em, forstaOrder.getKundNr(), forstaOrder.getLagerNr(), anvandare);
				fakturaOrder.setSaljare(getAvrundadSaljare(forstaOrder.getSaljare()));
				orderPaFaktura = new ArrayList();
				for (OrderHandler o : oList) {
					orderFakturerbar = true;
					for (OrderHandlerRad or :o.getOrdreg()) {		//Kolla så det inte är fel på ordern
						if ( (or.lev > 0.0 && or.pris.equals(0.0) && !or.artnr.startsWith("*UD"))) {
							orderFakturerbar = false;
						}
					}
					if (orderFakturerbar) {		//Lägg till ordern till faktureringsordern
						o.lasOrder();
						orderPaFaktura.add(o);	//Lägg till listan på vilka ordrar som är på fakturan
						if (fakturaOrder.getOrdreg().size() > 0) fakturaOrder.addTextRow("");	//Lägg till tomrad mellan ordrarna, men bara om det är förjsta ordern
						fakturaOrder.addTextRow("Följesedel: " + o.getOrdernr() + " Datum: " + SXUtil.getFormatDate(o.getDatum()));
						if (!SXUtil.isEmpty(o.getMarke())) fakturaOrder.addTextRow("Märke: " + o.getMarke());

						for (OrderHandlerRad or :o.getOrdreg()) {
							fakturaOrder.addRow(or);
						}

					}
				}

				if (fakturaOrder==null || orderPaFaktura.isEmpty() || fakturaOrder.getTableKund().getEjfakturerbar() > 0) continue;


			}
			break;
		}
		return true;
		
	}

	//Returnerar aktuell order som ska faktureras. Nästa order laddas vid anrop till nextFakturaOrder
	public OrderHandler getFakturaOrder() {return fakturaOrder; }

	//Returnerar lista på de order som är med i aktuell order som ska faktureras. Nästa order laddas vid anrop till nextFakturaOrder
	public ArrayList<OrderHandler> getOrderPaFaktura() { return orderPaFaktura; }

	private String getAvrundadSaljare(String s) {
		if (s!= null) {
			if (s.length() > 30) s = s.substring(0,30).trim(); else s=s.trim();
		}
		return s;
	}
}
