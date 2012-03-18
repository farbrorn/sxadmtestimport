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
import se.saljex.sxlibrary.exceptions.SxInfoException;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableOrder2;
import se.saljex.sxserver.tables.TableUtlev1;

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
	private LocalOrder localOrder=null; //Skall sättas till null här för att indiker att loadLocalOrder inte är utförd
	private ArrayList<LocalOrderRad> localOrderRader = null;
	private int mainOrdernr = 0;
	private boolean allowUseBestnr=true;

	public OverforOrder(EntityManager emMain, EntityManager emLocal, int localOrdernr, String mainKundnr, String localAnvandare, String mainAnvandare, short mainLagernr, boolean allowUseBestnr) {
		this.emLocal = emLocal;
		this.emMain = emMain;
		this.localOrdernr = localOrdernr;
		this.mainKundnr = mainKundnr;
		this.localAnvandar = localAnvandare;
		this.mainAnvandare = mainAnvandare;
		this.mainLagernr = mainLagernr;
		this.allowUseBestnr=allowUseBestnr;
	}



	private void loadLocalUtlev() throws SXEntityNotFoundException  {
		// Kolla så att vi inte har sparat en order oh inte avslutat hanteringe av bvordern
		if (localOrder!=null) throw new SXEntityNotFoundException("Internt fel: loadLocalOrder är redan utförd, och kan inte utföras igen.");

		localOrderRader = new ArrayList();
		List<TableUtlev1> localUtlev1List = emLocal.createNamedQuery("TableUtlev1.findByOrdernr").setParameter("ordernr", localOrdernr).getResultList();
		if (localUtlev1List.size()>1) throw new SXEntityNotFoundException("Det finns flera utleveranser på ordernr " + localOrdernr + ". Kan inte göra överföring.");
		else if (localUtlev1List.isEmpty()) throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " finns inte.");
		else {
			TableUtlev1 utlev1 = localUtlev1List.get(0);
			localOrder = new LocalOrder();
			localOrder.utlev1 = utlev1;
	//		if (localOrder1.getLastdatum()!=null) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " är låst av användare '" + localOrder1.getLastav() + "' " + localOrder1.getLastdatum() + " " + localOrder1.getLasttid() + " och kan inte behandlas." ); }
			if (SXConstant.ORDER_STATUS_OVERFORD.equals(localOrder.utlev1.getStatus())) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " är redan överförd. Du måste ändra orderns status till 'Sparad' om du vill överföra igen."); }
			List<TableFaktura2> localOrder2List = emLocal.createNamedQuery("TableFaktura2.findByOrdernr").setParameter("ordernr", localOrdernr).getResultList();

			for (TableFaktura2 localFaktura2 : localOrder2List) {
				addLocalOrderWithArtikelLookup(localFaktura2.getLev(),localFaktura2.getArtnr(), localFaktura2.getNamn(), "", localFaktura2.getEnh(), localFaktura2.getNetto(), localFaktura2.getPris(), localFaktura2.getRab() );
			}
		}
	}


	//Kolla först om det finns en order. Om inte så kollar vi efter en utleverans
	public void loadLocalOrder() throws SXEntityNotFoundException  {
		// Kolla så att vi inte har sparat en order oh inte avslutat hanteringe av bvordern
		if (localOrder!=null) throw new SXEntityNotFoundException("Internt fel: loadLocalOrder är redan utförd, och kan inte utföras igen.");

		TableOrder1 order1 = emLocal.find(TableOrder1.class, localOrdernr);
		if (order1==null) {
			//Om inte ordern finns så kolla istället Utlev
			loadLocalUtlev();
		} else {
			localOrderRader = new ArrayList();
			localOrder = new LocalOrder();
			localOrder.order1=order1;
			if (localOrder.order1.getLastdatum()!=null) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " är låst av användare '" + localOrder.order1.getLastav() + "' " + localOrder.order1.getLastdatum() + " " + localOrder.order1.getLasttid() + " och kan inte behandlas." ); }
			if (SXConstant.ORDER_STATUS_OVERFORD.equals(localOrder.order1.getStatus())) { throw new SXEntityNotFoundException("Ordernr " + localOrdernr + " är redan överförd. Du måste ändra orderns status till 'Sparad' om du vill överföra igen."); }
			List<TableOrder2> localOrder2List = emLocal.createNamedQuery("TableOrder2.findByOrdernr").setParameter("ordernr", localOrdernr).getResultList();

			for (TableOrder2 localOrder2 : localOrder2List) {
				addLocalOrderWithArtikelLookup(localOrder2.getBest(),localOrder2.getArtnr(), localOrder2.getNamn(), localOrder2.getLevnr(), localOrder2.getEnh(), localOrder2.getNetto(), localOrder2.getPris(), localOrder2.getRab());
			}
		}
	}



	//Lägg till i LocalOrder och slå upp artikel för att räkna om inköpsenheter
	private void addLocalOrderWithArtikelLookup(double lev, String artnr, String namn, String levnr, String enh, double netto, double pris, double rab) {
		double nyttAntal=0;
		double nyttPris=0;
		double enhetsfaktor;
		String mainArtnr = null;
		TableArtikel localArtikel;

		if(!SXUtil.isEmpty(artnr)) {	//Hoppa över tomma rader
			nyttAntal = lev;
			nyttPris = pris;
			mainArtnr=artnr;

			localArtikel = emLocal.find(TableArtikel.class, artnr);
			if (localArtikel!=null) {
				enhetsfaktor = localArtikel.getInpEnhetsfaktor().doubleValue();
				if (localArtikel.getInpEnhetsfaktor().compareTo(new BigDecimal(0))!=0) {
					nyttAntal = lev * enhetsfaktor;
					nyttPris = pris / enhetsfaktor;
				}
				if (!SXUtil.isEmpty(localArtikel.getBestnr()) && allowUseBestnr) mainArtnr=localArtikel.getBestnr();
			}
			localOrderRader.add(new LocalOrderRad(mainArtnr, nyttAntal, nyttPris, artnr, namn, levnr, enh, netto, pris, rab));
		}

	}




	public int saveMainOrder() throws SXEntityNotFoundException, SxInfoException{
		// Kolla om loadlocalorder är utförd korrekt innan denna funktion körs
		if (localOrder==null) throw new SXEntityNotFoundException("Internt fel: Local Order är inte laddad vid försök att spara. Är loadLocalOrder()utförd korrekt?");

		OrderHandler mainOrderHandler = new OrderHandler(emMain, mainKundnr, mainLagernr, mainAnvandare);
		if (!SXUtil.isEmpty(localOrder.getLevadr1())) {
			mainOrderHandler.setLevAdr(localOrder.getLevadr1(), localOrder.getLevadr2(), localOrder.getLevadr3());
		} else {
			mainOrderHandler.setLevAdr(localOrder.getNamn(), localOrder.getAdr1(), localOrder.getAdr3());
		}
		mainOrderHandler.setMarke(localOrder.getMarke());
		mainOrderHandler.setStatus(SXConstant.ORDER_STATUS_AVVAKT);
		mainOrderHandler.setKundordernr(localOrder.getOrdernr());
		mainOrderHandler.setForskatt(localOrder.getForskatt());
		mainOrderHandler.setForskattbetald(localOrder.getForskattbetald());
		for (LocalOrderRad localOrderRad : localOrderRader) {
			try {
				mainOrderHandler.addRow(localOrderRad.mainArtnr, localOrderRad.nyttAntal);
			} catch (SXEntityNotFoundException e) {
				//Artikeln finns inte. Vi måste lägga in en *-rad
				mainOrderHandler.addStjRow(localOrderRad.artnr, localOrderRad.namn, localOrderRad.levnr, localOrderRad.nyttAntal, localOrderRad.enh, localOrderRad.netto, localOrderRad.pris, localOrderRad.rab);
			}

		}
		mainOrdernr = mainOrderHandler.persistOrder();
		return mainOrdernr;

	}




	public void updateLocalOrder() throws SXEntityNotFoundException{
		// Kolla så att vi har fått ett sxordernr innan denna funktion körs
		if (mainOrdernr==0) throw new SXEntityNotFoundException("Internt fel: MainOrdernr finns inte vid försök att uppdatera localorder. Är saveMainOrder()utförd korrekt?");

		if (localOrder.order1!=null) {
			localOrder.order1.setWordernr(mainOrdernr);
			localOrder.order1.setStatus(SXConstant.ORDER_STATUS_OVERFORD);
		} else if (localOrder.utlev1!=null){
			localOrder.utlev1.setWordernr(mainOrdernr);
			localOrder.utlev1.setStatus(SXConstant.ORDER_STATUS_OVERFORD);
		}
		emLocal.flush();
	}

	public int getMainOrdernr() { return mainOrdernr; }


	class LocalOrder {
		TableUtlev1 utlev1=null;
		TableOrder1 order1=null;
		public String getLevadr1() { return utlev1==null ? order1.getLevadr1() : utlev1.getLevadr1(); }
		public String getLevadr2() { return utlev1==null ? order1.getLevadr2() : utlev1.getLevadr2(); }
		public String getLevadr3() { return utlev1==null ? order1.getLevadr3() : utlev1.getLevadr3(); }
		public String getAdr1() { return utlev1==null ? order1.getAdr1() : utlev1.getAdr1(); }
		public String getAdr3() { return utlev1==null ? order1.getAdr3() : utlev1.getAdr3(); }
		public String getNamn() { return utlev1==null ? order1.getNamn() : utlev1.getNamn(); }
		public String getMarke() { return utlev1==null ? order1.getMarke() : utlev1.getMarke(); }
		public boolean getForskatt() { return utlev1==null ? order1.getForskatt() : utlev1.getForskatt(); }
		public boolean getForskattbetald() { return utlev1==null ? order1.getForskattbetald() : utlev1.getForskattbetald(); }
		public int getOrdernr() { return utlev1==null ? order1.getOrdernr() : utlev1.getOrdernr(); }
	}


	class LocalOrderRad {
//		public TableOrder2 order2=null;
		public String artnr=null;
		public String namn=null;
		public String levnr=null;
		public String enh=null;
		public Double netto=null;
		public Double pris=null;
		public Double rab=null;

		public double nyttAntal=0;
		public double nyttPris=0;
		public String mainArtnr=null;
		public LocalOrderRad(String mainArtnr, double nyttAntal, double nyttPris, String artnr,String namn, String levnr, String enh, Double netto, Double pris, Double rab ) {
//			this.order2 = order2;
			this.artnr=artnr;
			this.namn=namn;
			this.levnr=levnr;
			this.enh=enh;
			this.netto=netto;
			this.pris=pris;
			this.rab=rab;

			this.mainArtnr=mainArtnr;
			this.nyttAntal=nyttAntal;
			this.nyttPris=nyttPris;
		}
	}
}
