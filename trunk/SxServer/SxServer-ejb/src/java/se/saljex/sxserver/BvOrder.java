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
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableOrder2;

/**
 *
 * @author ulf
 */
public class BvOrder {
	EntityManager emSx;
	EntityManager emBv;
	private int bvOrdernr = 0;	//Måste sättas till 0 för att indikera att saveSxOrder inte har utförts
	private String sxKundnr;
	private String sxAnvandare;
	private String bvAnvandar;
	private short sxLagernr;
	private TableOrder1 bvOrder1=null; //Skall sättas till null här för att indiker att loadBvOrder inte är utförd
	private ArrayList<BvOrderRad> bvOrderRader = null;
	private int sxOrdernr = 0;

	public BvOrder(EntityManager emSx, EntityManager emBv, int bvOrdernr, String sxKundnr, String bvAnvandare, String sxAnvandare, short sxLagernr) {
		this.emBv = emBv;
		this.emSx = emSx;
		this.bvOrdernr = bvOrdernr;
		this.sxKundnr = sxKundnr;
		this.bvAnvandar = bvAnvandare;
		this.sxAnvandare = sxAnvandare;
		this.sxLagernr = sxLagernr;
	}



	public void loadBvOrder() throws SXEntityNotFoundException  {
		// Kolla så att vi inte har sparat en order oh inte avslutat hanteringe av bvordern
		if (bvOrder1!=null) throw new SXEntityNotFoundException("Internt fel: loadBvOrder är redan utförd, och kan inte utföras igen.");

		bvOrderRader = new ArrayList();
		bvOrder1 = emBv.find(TableOrder1.class, bvOrdernr);
		if (bvOrder1==null) { throw new SXEntityNotFoundException("Ordernr " + bvOrdernr + " finns inte."); }
		if (bvOrder1.getLastdatum()!=null) { throw new SXEntityNotFoundException("Ordernr " + bvOrdernr + " är låst av användare '" + bvOrder1.getLastav() + "' " + bvOrder1.getLastdatum() + " " + bvOrder1.getLasttid() + " och kan inte behandlas." ); }
		if (!SXConstant.ORDER_STATUS_SPARAD.equals(bvOrder1.getStatus())) { throw new SXEntityNotFoundException("Ordernr " + bvOrdernr + " har statusen " + bvOrder1.getStatus() + " som inte medger överföring. Du måste ändra orderns status till 'Sparad'."); }
		List<TableOrder2> bvOrder2List = emBv.createNamedQuery("TableOrder2.findByOrdernr").setParameter("ordernr", bvOrdernr).getResultList();
		TableArtikel bvArtikel;

		double nyttAntal=0;
		double nyttPris=0;
		double enhetsfaktor;
		String sxArtnr;

		for (TableOrder2 bvOrder2 : bvOrder2List) {
			if(!SXUtil.isEmpty(bvOrder2.getArtnr())) {	//Hoppa över tomma rader
				nyttAntal = bvOrder2.getBest();
				nyttPris = bvOrder2.getPris();
				sxArtnr=bvOrder2.getArtnr();

				bvArtikel = emBv.find(TableArtikel.class, bvOrder2.getArtnr());
				if (bvArtikel!=null) {
					enhetsfaktor = bvArtikel.getInpEnhetsfaktor().doubleValue();
					if (bvArtikel.getInpEnhetsfaktor().compareTo(new BigDecimal(0))!=0) {
						nyttAntal = bvOrder2.getBest() * enhetsfaktor;
						nyttPris = bvOrder2.getPris() / enhetsfaktor;
					}
					if (!SXUtil.isEmpty(bvArtikel.getBestnr())) sxArtnr=bvArtikel.getBestnr();
				}
				bvOrderRader.add(new BvOrderRad(bvOrder2, sxArtnr, nyttAntal, nyttPris));
			}
		}
	}

	public int saveSxOrder() throws SXEntityNotFoundException, SxInfoException{
		// Kolla om loadbvorder är utförd korrekt innan denna funktion körs
		if (bvOrder1==null) throw new SXEntityNotFoundException("Internt fel: BV Order är inte laddad vid försök att spara. Är loadBvOrder()utförd korrekt?");

		OrderHandler sxOrderHandler = new OrderHandler(emSx, sxKundnr, sxLagernr, sxAnvandare);
		if (!SXUtil.isEmpty(bvOrder1.getLevadr1())) {
			sxOrderHandler.setLevAdr(bvOrder1.getLevadr1(), bvOrder1.getLevadr2(), bvOrder1.getLevadr3());
		} else {
			sxOrderHandler.setLevAdr(bvOrder1.getNamn(), bvOrder1.getAdr1(), bvOrder1.getAdr3());
		}
		sxOrderHandler.setMarke(bvOrder1.getMarke());
		sxOrderHandler.setStatus(SXConstant.ORDER_STATUS_AVVAKT);
		sxOrderHandler.setKundordernr(bvOrder1.getOrdernr());
		sxOrderHandler.setForskatt(bvOrder1.getForskatt());
		sxOrderHandler.setForskattbetald(bvOrder1.getForskattbetald());
		for (BvOrderRad bvOrderRad : bvOrderRader) {
			try {
				sxOrderHandler.addRow(bvOrderRad.sxArtnr, bvOrderRad.nyttAntal);
			} catch (SXEntityNotFoundException e) {
				//Artikeln finns inte. Vi måste lägga in en *-rad
				sxOrderHandler.addStjRow(bvOrderRad.order2.getArtnr(), bvOrderRad.order2.getNamn(), bvOrderRad.order2.getLevnr(), bvOrderRad.nyttAntal, bvOrderRad.order2.getEnh(), bvOrderRad.order2.getNetto(), bvOrderRad.order2.getPris(), bvOrderRad.order2.getRab());
			}

		}
		sxOrdernr = sxOrderHandler.persistOrder();
		return sxOrdernr;

	}

	public void updateBvOrder() throws SXEntityNotFoundException{
		// Kolla så att vi har fått ett sxordernr innan denna funktion körs
		if (sxOrdernr==0) throw new SXEntityNotFoundException("Internt fel: SXordernr finns inte vid försök att uppdatera bvorder. Är saveSxOrder()utförd korrekt?");

		bvOrder1.setWordernr(sxOrdernr);
		bvOrder1.setStatus(SXConstant.ORDER_STATUS_OVERFORD);
		emBv.flush();
	}

	public int getSxOrdernr() { return sxOrdernr; }


	class BvOrderRad {
		public TableOrder2 order2=null;
		public double nyttAntal=0;
		public double nyttPris=0;
		public String sxArtnr=null;
		public BvOrderRad(TableOrder2 order2, String sxArtnr, double nyttAntal, double nyttPris) {
			this.order2 = order2;
			this.sxArtnr=sxArtnr;
			this.nyttAntal=nyttAntal;
			this.nyttPris=nyttPris;
		}
	}
}
