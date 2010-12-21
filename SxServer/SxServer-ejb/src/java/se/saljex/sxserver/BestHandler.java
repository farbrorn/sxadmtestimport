/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableLev;
import se.saljex.sxserver.tables.TableFaktdat;
import se.saljex.sxserver.tables.TableSaljare;
import se.saljex.sxserver.tables.TableBesthand;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableLagerid;
import se.saljex.sxserver.tables.TableLagerPK;
import se.saljex.sxserver.tables.TableBest2;
import se.saljex.sxserver.tables.TableBest1;
import se.saljex.sxserver.tables.TableLager;
import se.saljex.sxserver.tables.TableStjarnrad;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
			 bestLaddad = false;
			 em = e;
			 be1 = new TableBest1();
			 fup = (TableFuppg) em.createNamedQuery("TableFuppg.findAll").getResultList().get(0);
			 setLev(levNr);
			 setLagerNr(lagerNr);
			 setAnvandare(anvandare);
			 TableLagerid lid;
			 lid = (TableLagerid) em.find(TableLagerid.class, lagerNr);
			 setLevAdr(lid.getBnamn(), lid.getLevadr1(), lid.getLevadr2(), lid.getLevadr3());
			 TableSaljare anv = em.find(TableSaljare.class, anvandare);
			 if (anv != null) {
					be1.setVarRef(anv.getNamn());
			 } else {
					be1.setVarRef(null);
			 }

			 be1.setDatum(new Date());
			 be1.setAutobestalld((short) 0);

//		Random rgen = new Random(10000); // Mellan 0 och 9999
//		int r = rgen.nex .nextInt();
			 int r = (int) (Math.random() * 10000);
			 if (r < 1000) {
					r = r + 1000;
			 }	// Minsta värdet måste vara minst 1000, ger obalans i slumptalen men det spelar mindre roll
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
			 this.anvandare = anvandare;
	  }

	  public BestHandlerRad addRow(String artnr, double antal) {
			 bord = new BestHandlerRad();
			 art = em.find(TableArtikel.class, artnr);
			 if (art == null) {
					ServerUtil.log("BestHandler-addRow-Kan inte hitta artikel " + artnr + " för best.");
					return null;
			 }
			 bord.best = antal;
			 bord.lev = antal;
			 bord.artnr = art.getNummer();
			 bord.artnamn = art.getNamn();
			 bord.enh = art.getEnhet();
			 bord.pris = art.getInpris();
			 bord.rab = art.getRab();
			 bord.bartnr = art.getBestnr();
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
			 b.summa = b.pris * b.best * (1 - b.rab / 100);
			 return b.summa;
	  }

	  private void calcTotalSumma() {
			 double s = 0.0;
			 for (BestHandlerRad b : bordreg) {
					s = s + calcRadSumma(b);
			 }
			 be1.setSumma(s);
	  }

	  public BestHandlerRad getRow(int rad) {
			 BestHandlerRad b;
			 try {
					b = bordreg.get(rad);
			 } catch (IndexOutOfBoundsException e) {
					b = null;
			 }
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
			 be1.setStatus(status);
	  }

	  public Double getBestSumma() {
			 double summa = 0;
			 for (BestHandlerRad b : bordreg) {
					summa = summa + calcRadSumma(b);
			 }
			 return summa;
	  }

	  public void setOrdernr(Integer ordernr) {
			 be1.setOrdernr(ordernr);
	  }

	  public Integer getOrdernr() {
			 return be1.getOrdernr();
	  }

	  public void setAutobestalld(short autobest) {
			 be1.setAutobestalld(autobest);
	  }

	  public short getAutobestalld() {
			 return be1.getAutobestalld();
	  }

	  public void setLev(String levNr) {
			 // Hämta lev och sätt standardvärden för lev
			 lev = em.find(TableLev.class, levNr);
			 if (lev == null) {
					throw new EntityNotFoundException("Kan inte hitta leverantör " + levNr + " för ny beställning.");
			 }

			 be1.setLevnr(lev.getNummer());
			 be1.setLevnamn(lev.getNamn());
			 be1.setErRef(lev.getRef());

			 if (!SXUtil.isEmpty(lev.getLevvillkor1())) {
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

	  public void setSkickasom(String skickasom) {
			 be1.setSkickasom(skickasom);
	  }

	  public String getSkickasom() {
			 return be1.getSkickasom();
	  }

	  public void setMarke(String marke) {
			 be1.setMarke(marke);
	  }

	  public String getMarke() {
			 return be1.getMarke();
	  }

	  public void setLevAdr(String adr0, String adr1, String adr2, String adr3) {
			 be1.setLevadr0(adr0);
			 be1.setLevadr1(adr1);
			 be1.setLevadr2(adr2);
			 be1.setLevadr3(adr3);
	  }

	  public void prepareNextBestNr() {
			 // Ta fram ett nytt bestnr och räkna upp räknaren i databasen
			 short scn;
			 TableLager lag;

			 // Hämtaa nytt irdernt och räkna upp
			 TableFaktdat fdt;
			 scn = 0;
			 while (true) {
					scn++;			//Räkna antalet loopar för att avgöra när fel skall skapass
					fdt = (TableFaktdat) em.find(TableFaktdat.class, SXConstant.DEFAULT_FT);
					if (em.createNamedQuery("TableFaktdat.updateBestnrBy1").setParameter("bestnr", fdt.getBestnr()).setParameter("ft", SXConstant.DEFAULT_FT).executeUpdate() > 0) {
						// Uppdatering lyckades, avbryt while/loopen
						break;
					} else {
						if (scn > 10) {				// Har vi provat så många gånger så att vi får ge upp?
							  throw new PersistenceException("Kunde inte uppdatera faktdat för best " + be1.getLevnamn());
						}
					}
			 }
			 // OK Allt klart, vi har fått ett bestnr
			 be1.setBestnr(fdt.getBestnr());
	  }

	  private void deleteFromBest2AndUpdateLager() {
			 // Radera alla rader ur best2 samt uppdatera lager
			 // Observera att beställnikngsnumret tas ur be1.bestnr
			TableLager lag;
			Iterator i = em.createNamedQuery("TableBest2.findByBestnr").setParameter("bestnr", be1.getBestnr()).getResultList().iterator();
			while (i.hasNext()) {
				TableBest2 b = (TableBest2)i.next();
				if (b.getArtnr() != null) { 
					if (!b.getArtnr().startsWith("*")) {
						lag = em.find(TableLager.class, new TableLagerPK(b.getArtnr(), be1.getLagernr()));
						if (lag == null) {
							// Normalt skulle vi lägga till raden i lager-tqbellen här, men då vi bara raderar en befintlig best så struntar vi i det 
							// för om vi minskar best-antalet får vi ännu konstigare resultt
						} else {
							lag.setBest(lag.getBest() - b.getBest());
						}
					} else { // Vi har en *-rad
						// Vid *-rad skall beställd-markeringen tas bort från stjarnrad
						TableStjarnrad stj = em.find(TableStjarnrad.class, b.getStjid());
						if (stj != null) {
							stj.setBestdat(null);
						}
					}
				}
			}
			em.createNamedQuery("TableBest2.deleteByBestnr").setParameter("bestnr", be1.getBestnr()).executeUpdate();	// Radera alla rader på best2
	  }

	  public Integer persistBest() {
			 //Sparar som ny best
			 // Returnerar bestnrnumret
			TableLager lag;
			 if (!bestLaddad) {
					if (be1.getBestnr() == null || be1.getBestnr() == 0) {
						prepareNextBestNr();
					}	// Se till att få ett nytt bestnr


					if (be1.getStatus() == null) {		// Om vi inte satt status sätter vi förvald nu
						be1.setStatus(SXConstant.BEST_STATUS_SKAPAD);
					}
			 } else {
					// Om beställningen är laddad måste vi radera alla gamla rader i beställningen, och därefter lägga till de nya
					deleteFromBest2AndUpdateLager();
			 }

			 calcTotalSumma();

			 if (!bestLaddad) em.persist(be1);
			 short scn = 0;
			 TableBest2 be2;
			 for (BestHandlerRad b : bordreg) {
					scn++;
					b.bestnr = be1.getBestnr();
					b.rad = scn;
					be2 = b.getBest2();
					em.persist(be2);

					// Uppdatera lagersaldo
					if (b.artnr != null) {
						if (!b.artnr.startsWith("*")) {
							lag = em.find(TableLager.class, new TableLagerPK(b.artnr, be1.getLagernr()));
							if (lag == null) {
								lag = new TableLager(b.artnr, be1.getLagernr(), 0, b.best);
								em.persist(lag);
							} else {
								lag.setBest(lag.getBest() + b.best);
							}
						} else { // Vi har en *-rad, och behöver då uppdatera att den är beställd
							TableStjarnrad stj = em.find(TableStjarnrad.class, b.stjid);
							if (stj != null) {
								stj.setBestdat(new Date());
							}
						}
					}
			 }
			 em.persist(new TableBesthand(be1.getBestnr(), anvandare, SXConstant.BESTHAND_SKAPAD, 0));
			 bestLaddad = true;		//Signalera att beställning är sparad, och därför att betrakta som laddad
			 return be1.getBestnr();
	  }
}
