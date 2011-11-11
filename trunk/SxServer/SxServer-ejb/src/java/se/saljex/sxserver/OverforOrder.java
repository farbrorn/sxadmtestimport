/*
 * Funktioner för att överföra en BVOrder till SXOrder
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SXConstant;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableOrder2;

/**
 *
 * @author ulf
 */
public class OverforOrder {
	EntityManager emMain;
	EntityManager emLocal;
	private int localOrdernr = 0;	//Måste sättas till 0 för att indikera att saveSxOrder inte har utförts
	private String mainKundnr;
	private String mainAnvandare;
	private String localAnvandar;
	private short mainLagernr;
	private TableOrder1 localOrder1=null; //Skall sättas till null här för att indiker att loadBvOrder inte är utförd
	private ArrayList<LocalOrderRad> localOrderRader = null;
	private int mainOrdernr = 0;

	public OverforOrder(EntityManager emMain, EntityManager emLocal, int localOrdernr, String mainKundnr, String localAnvandare, String mainAnvandare, short mainLagernr) {
		this.emLocal = emLocal;
		this.emMain = emMain;
		this.localOrdernr = localOrdernr;
		this.mainKundnr = mainKundnr;
		this.localAnvandar = localAnvandare;
		this.mainAnvandare = mainAnvandare;
		this.mainLagernr = mainLagernr;
	}



	public void loadLocalOrder() throws SXEntityNotFoundException  {
		// Kolla så att vi inte har sparat en order oh inte avslutat hanteringe av bvordern
		if (localOrder1!=null) throw new SXEntityNotFoundException("Internt fel: loadLocalOrder är redan utförd, och kan inte utföras igen.");

		localOrderRader = new ArrayList();
		localOrder1 = emLocal.find(TableOrder1.class, localOrdernr);
		if (localOrder1==null) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " finns inte."); }
		if (localOrder1.getLastdatum()!=null) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " är låst av användare '" + localOrder1.getLastav() + "' " + localOrder1.getLastdatum() + " " + localOrder1.getLasttid() + " och kan inte behandlas." ); }
		if (SXConstant.ORDER_STATUS_OVERFORD.equals(localOrder1.getStatus())) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " är redan överförd. Du måste ändra orderns status till 'Sparad' om du vill överföra igen."); }
		List<TableOrder2> localOrder2List = emLocal.createNamedQuery("TableOrder2.findByOrdernr").setParameter("ordernr", localOrdernr).getResultList();
		TableArtikel localArtikel;

		double nyttAntal=0;
		double nyttPris=0;
		double enhetsfaktor;
		String mainArtnr;

		for (TableOrder2 localOrder2 : localOrder2List) {
			if(!SXUtil.isEmpty(localOrder2.getArtnr())) {	//Hoppa över tomma rader
				nyttAntal = localOrder2.getBest();
				nyttPris = localOrder2.getPris();
				mainArtnr=localOrder2.getArtnr();

				localArtikel = emLocal.find(TableArtikel.class, localOrder2.getArtnr());
				if (localArtikel!=null) {
					enhetsfaktor = localArtikel.getInpEnhetsfaktor().doubleValue();
					if (localArtikel.getInpEnhetsfaktor().compareTo(new BigDecimal(0))!=0) {
						nyttAntal = localOrder2.getBest() * enhetsfaktor;
						nyttPris = localOrder2.getPris() / enhetsfaktor;
					}
					if (!SXUtil.isEmpty(localArtikel.getBestnr())) mainArtnr=localArtikel.getBestnr();
				}
				localOrderRader.add(new LocalOrderRad(localOrder2, mainArtnr, nyttAntal, nyttPris));
			}
		}
	}

	public int saveMainOrder() throws SXEntityNotFoundException{
		// Kolla om loadlocalorder är utförd korrekt innan denna funktion körs
		if (localOrder1==null) throw new SXEntityNotFoundException("Internt fel: Local Order är inte laddad vid försök att spara. Är loadLocalOrder()utförd korrekt?");

		OrderHandler mainOrderHandler = new OrderHandler(emMain, mainKundnr, mainLagernr, mainAnvandare);
		if (!SXUtil.isEmpty(localOrder1.getLevadr1())) {
			mainOrderHandler.setLevAdr(localOrder1.getLevadr1(), localOrder1.getLevadr2(), localOrder1.getLevadr3());
		} else {
			mainOrderHandler.setLevAdr(localOrder1.getNamn(), localOrder1.getAdr1(), localOrder1.getAdr3());
		}
		mainOrderHandler.setMarke(localOrder1.getMarke());
		mainOrderHandler.setStatus(SXConstant.ORDER_STATUS_AVVAKT);
		mainOrderHandler.setKundordernr(localOrder1.getOrdernr());
		mainOrderHandler.setForskatt(localOrder1.getForskatt());
		mainOrderHandler.setForskattbetald(localOrder1.getForskattbetald());
		for (LocalOrderRad localOrderRad : localOrderRader) {
			try {
				mainOrderHandler.addRow(localOrderRad.mainArtnr, localOrderRad.nyttAntal);
			} catch (SXEntityNotFoundException e) {
				//Artikeln finns inte. Vi måste lägga in en *-rad
				mainOrderHandler.addStjRow(localOrderRad.order2.getArtnr(), localOrderRad.order2.getNamn(), localOrderRad.order2.getLevnr(), localOrderRad.nyttAntal, localOrderRad.order2.getEnh(), localOrderRad.order2.getNetto(), localOrderRad.order2.getPris(), localOrderRad.order2.getRab());
			}

		}
		mainOrdernr = mainOrderHandler.persistOrder();
		return mainOrdernr;

	}

	public void updateLocalOrder() throws SXEntityNotFoundException{
		// Kolla så att vi har fått ett sxordernr innan denna funktion körs
		if (mainOrdernr==0) throw new SXEntityNotFoundException("Internt fel: MainOrdernr finns inte vid försök att uppdatera localorder. Är saveMainOrder()utförd korrekt?");

		localOrder1.setWordernr(mainOrdernr);
		localOrder1.setStatus(SXConstant.ORDER_STATUS_OVERFORD);
		emLocal.flush();
	}

	public int getMainOrdernr() { return mainOrdernr; }


	class LocalOrderRad {
		public TableOrder2 order2=null;
		public double nyttAntal=0;
		public double nyttPris=0;
		public String mainArtnr=null;
		public LocalOrderRad(TableOrder2 order2, String mainArtnr, double nyttAntal, double nyttPris) {
			this.order2 = order2;
			this.mainArtnr=mainArtnr;
			this.nyttAntal=nyttAntal;
			this.nyttPris=nyttPris;
		}
	}
}
