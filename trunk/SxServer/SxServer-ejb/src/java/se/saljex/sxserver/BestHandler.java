/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

/**
 *
 * @author Ulf
 */
public class BestHandler {
	
	private EntityManager em;
	private TableArtikel art;
	private BestHandlerRad bord;
	private TableBest1 be1;
	private TableLev lev;
	private TableFuppg fup;
	
	private ArrayList<BestHandlerRad> bordreg = new ArrayList<BestHandlerRad>();  //Holds all rows in the order
	
	private String anvandare;
	
	private boolean bestLaddad = false;
	

	public BestHandler(EntityManager e, String levNr, short lagerNr, String anvandare) {
		em = e;
		be1 = new TableBest1();
		fup = (TableFuppg)em.createNamedQuery("TableFuppg.findAll").getResultList().get(0);
		setLev(levNr);
		setLagerNr(lagerNr);
		setAnvandare(anvandare);
		TableLagerid lid;
		lid = (TableLagerid)em.find(TableLagerid.class, lagerNr);
		setLevAdr(lid.getLevadr1(),lid.getLevadr2(), lid.getLevadr3());
		be1.setVarRef("");
		try {
			TableSaljare anv = em.find(TableSaljare.class, anvandare);
			be1.setVarRef(anv.getNamn());
		} catch (EntityNotFoundException en) { }
		
		be1.setDatum(new Date());
		be1.setAutobestalld((short)0);
		Random rgen = new Random(10000); // Mellan 0 och 9999
		int r = rgen.nextInt();
		if (r < 1000) { r = r+1000; }	// Minsta värdet måste vara minst 1000, ger obalans i slumptalen men det spelar mindre roll
		be1.setSakerhetskod(r);  // 1000-9999
		be1.setSkickasom("");	// Initiera med tomt värde
	}
	
	public void setLagerNr(short lagernr) {
		// Sätter nytt lager och läser om alla lagersaldon till det nya lagret
		// Kan även användas med samma lagernummer för att uppdatera lagersaldona
		be1.setLagernr(lagernr);

		// Läs in nya lagervärden
		for (BestHandlerRad o : bordreg) {
			setLagerToOrderRad(o);
		}
	}
	
	private void setLagerToOrderRad(BestHandlerRad o) {
		// Hämtar lagervärden för aktuellt lager, och lägger in dem i orderhandlerrad
		TableLager l;
		l = em.find(TableLager.class, new TableLagerPK(o.artnr, be1.getLagernr()));
		if (l == null) {
			o.artLagerplats = null;
			o.artBest = 0;
			o.artIlager = 0;
			o.artIorder = 0;
		} else {
			o.artLagerplats = l.getLagerplats();
			o.artBest = l.getBest();
			o.artIorder = l.getIorder();
			o.artIlager = l.getIlager();
			
		}
		
	}
	
	public void setAnvandare(String anvandare) {
		this.anvandare = new String(anvandare);  // Skapar en ny Stringclass ifall användaren kommer att ändras i någon annan class
	}
	
	public BestHandlerRad addRow(String artnr, double antal) {
		bord = new BestHandlerRad();
		art = em.find(TableArtikel.class, artnr); 
		if (art == null) { 
			SXUtil.log("OrderHandler-addRow-Kan inte hitta artikel " + artnr + " för order."); 
			return null;
		}
		bord.best = antal;
		bord.lev = antal;
		bord.artnr = art.getNummer();
		bord.artnamn = art.getNamn();
		bord.enh = art.getEnhet();
		bord.artDirektlev = art.getDirektlev();
		bord.artFraktvillkor = art.getFraktvillkor();
		bord.inpFrakt = art.getInpFrakt();
		bord.inpFraktproc = art.getInpFraktproc();
		bord.inpMiljo = art.getInpMiljo();
		
		setLagerToOrderRad(bord); // Sätt lagersaldon
		calcRadSumma(bord);

		bordreg.add(bord);
		return bord;
	}
	
	private double calcRadSumma(BestHandlerRad b) {
		b.summa = b.pris * b.best * (1-b.rab/100);
		return b.summa;
	}
	
	public BestHandlerRad getRow(int rad) {
		BestHandlerRad b;
		try {
			b = bordreg.get(rad);
		} catch (IndexOutOfBoundsException e) { b = null; }
		return b;
	}

	public ArrayList getBordreg() {
		return bordreg;
	}

	public TableBest1 getTableBest1() {
		return be1;
	}

	public String getLevNr() {
		return be1.getLevnr();
	}

	public Short getLagerNr() {
		return be1.getLagernr();
	}
	
	public TableLev getTableLev() {
		return lev;
	}
	
	public String getStatus() {
		return be1.getStatus();
	}
	public void setStatus(String status) {
		be1.setStatus(new String(status));
	}
	public Double getBestSumma() {
		double summa = 0;
		for (BestHandlerRad b: bordreg) {
			summa = calcRadSumma(b);
		}
		return summa;
	}
	
	public void setLev(String levNr) {
		// Hämta lev och sätt standardvärden för lev
		lev = em.find(TableLev.class, levNr);
		if (lev == null) { throw new EntityNotFoundException("Kan inte hitta leverantör " + levNr + " för ny beställning."); }
		
		be1.setLevnr(lev.getNummer());
		be1.setLevnamn(lev.getNamn());
		be1.setErRef(lev.getRef());

		if (lev.getLevvillkor1().isEmpty()) {
			be1.setLevvillkor1(lev.getLevvillkor1());
			be1.setLevvillkor2(lev.getLevvillkor2());
			be1.setLevvillkor3(lev.getLevvillkor3());
		} else {
			be1.setLevvillkor1(fup.getBestText1());
			be1.setLevvillkor2(fup.getBestText2());
			be1.setLevvillkor3(fup.getBestText3());
		}
		
		be1.setBestejpris(lev.getBestejpris());
		be1.setMottagarfrakt(lev.getMottagarfrakt());
		be1.setFraktfritt(lev.getFraktfritt());
		
	}


	public Integer getSakerhetskod() {
		return be1.getSakerhetskod();
	}
	
	public void setSkickasom (String skickasom) {
		be1.setSkickasom(skickasom);
	}
	
	public String getSkickasom() {
		return be1.getSkickasom();
	}
	
	public void setMarke(String marke) {
		be1.setMarke(new String(marke));
	}
	public String getMarke() {
		return be1.getMarke();
	}
	
	public void setLevAdr(String adr1, String adr2, String adr3) {
		be1.setLevadr1(new String(adr1));
		be1.setLevadr2(new String(adr2));
		be1.setLevadr3(new String(adr3));
	}
	

	
	
	public Integer persistBest() {
		//Sparar som ny best
		// Returnerar bestnrnumret
		short scn;
		TableLager lag;
		
		// Hämtaa nytt irdernt och räkna upp
		TableFaktdat fdt;
		scn = 0;
		while (true) {
			scn++;			//Räkna antalet loopar för att avgöra när fel skall skapass
			fdt = (TableFaktdat)em.find(TableFaktdat.class, SXConstant.DEFAULT_FT);
			if (em.createNamedQuery("TableFaktdat.updateBestnrBy1").setParameter("bestnr", fdt.getBestnr()).setParameter("ft", SXConstant.DEFAULT_FT) .executeUpdate() > 0) {
				// Uppdatering lyckades, avbryt while/loopen
				break;
			} else {						
				if (scn > 10) {				// Har vi provat så många gånger så att vi får ge upp?
					throw new PersistenceException("Kunde inte uppdatera faktdat för best " + be1.getLevnamn());
				}
			}
		}	
		// OK Allt klart, vi har fått ett ordernr och kan fortsätta spara
		
		be1.setBestnr(fdt.getBestnr());
	
		if (be1.getStatus() == null ) {		// Om vi inte satt status sätter vi förvald nu
			be1.setStatus(SXConstant.BEST_STATUS_SKAPAD);
		}
		
		em.persist(be1); 
		scn = 0;
		TableBest2 be2;
		for (BestHandlerRad b : bordreg) {
			scn++;
			b.bestnr = be1.getBestnr();
			b.rad = scn;
			be2 = b.getBest2();
			em.persist(be2);
			
			// Uppdatera lagersaldo
			lag = em.find(TableLager.class, new TableLagerPK(b.artnr, be1.getLagernr()));
			if (lag == null) {
				lag = new TableLager(b.artnr, be1.getLagernr(),0,b.best); 
			} else {
				lag.setBest(lag.getBest()+b.best);
			}
			em.persist(lag);				
		}
		em.persist( new TableBesthand(be1.getBestnr(), anvandare, SXConstant.BESTHAND_SKAPAD, 0));
		em.flush();
		bestLaddad = true;		//Signalera att beställning är sparad, och därför att betrakta som laddad
		return be1.getBestnr();
	}
	
	
	}
	
	
