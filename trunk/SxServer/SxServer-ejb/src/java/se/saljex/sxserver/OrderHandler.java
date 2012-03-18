/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.exceptions.SxOrderLastException;
import se.saljex.sxlibrary.exceptions.SXToManyIterationsException;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxserver.tables.TableKunrabPK;
import se.saljex.sxserver.tables.TableFdordernr;
import se.saljex.sxserver.tables.TableLager;
import se.saljex.sxserver.tables.TableNettopriPK;
import se.saljex.sxserver.tables.TableOrder2;
import se.saljex.sxserver.tables.TableNettopri;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableKunrab;
import se.saljex.sxserver.tables.TableLagerPK;
import se.saljex.sxserver.tables.TableStjarnrad;
import se.saljex.sxserver.tables.TableOrderhand;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import se.saljex.sxlibrary.exceptions.*;
import se.saljex.sxserver.tables.TableArtstrukt;
import se.saljex.sxserver.tables.TableBonus;
import se.saljex.sxserver.tables.TableFaktdat;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableOffert1;
import se.saljex.sxserver.tables.TableOffert2;
import se.saljex.sxserver.tables.TableRanta;

/**
 *
 * @author Ulf
 */
public class OrderHandler {
	private EntityManager em;
	private TableOrder1 or1;
	private TableOrder2 or2;
	private TableKund kun;
	private TableArtikel art;
	private OrderHandlerRad ord;
	private TableNettopri net;

	private boolean orderLaddad = false;			// Signalerar om aktuell order är laddad från order1 oh order2

	
	private ArrayList<OrderHandlerRad> ordreg = new ArrayList<OrderHandlerRad>();  //Holds all rows in the order
	
	private String anvandare;
	

	public OrderHandler(EntityManager e, String kundNr, short lagerNr, String anvandare) {
		em = e;
		or1 = new TableOrder1();
		setAnvandare(anvandare);		//V iktigt att setAnvandare anropas före setKund() eftersom setKund() använder aktuell användare
		setKund(kundNr);
		setLagerNr(lagerNr);
		orderLaddad = false;
	}
	
	public OrderHandler(EntityManager e,  String anvandare, TableOrder1 copyFromTableOrder1) throws SXEntityNotFoundException{
		em = e;
		if (copyFromTableOrder1==null) throw new SXEntityNotFoundException("Inget orderhuvud");
		if (anvandare==null)  throw new SXEntityNotFoundException("Ingen användare");
		//Kolla om kunden finns
		kun = em.find(TableKund.class, copyFromTableOrder1.getKundnr());
		if (kun == null || SXUtil.isEmpty(copyFromTableOrder1.getKundnr())) { throw new SXEntityNotFoundException("Kan inte hitta kund " + SXUtil.toStr(copyFromTableOrder1.getKundnr()) +  " för order."); }
		
		or1 = new TableOrder1();
		setAnvandare(anvandare);		//V iktigt att setAnvandare anropas före setKund() eftersom setKund() använder aktuell användare
		orderLaddad = false;
		setOrder1FromCopy(copyFromTableOrder1);
	}

	//Hämtar in en order utan att låsa upp om den är låst
	public OrderHandler(EntityManager e, int ordernr, String anvandare) throws SxOrderLastException, EntityNotFoundException {
		this(e,ordernr,  anvandare, null);
	}


	//Hämta in en order
	//Om ordern är låst så låses den automatiskt upp om låset är satt för angivet antal dagar sedan. Nullvärde ger ingen upplåsning
	public OrderHandler(EntityManager e, int ordernr, String anvandare, Integer autoLasUppDagar) throws SxOrderLastException, EntityNotFoundException {
		em = e;
		setAnvandare(anvandare);
		or1 = em.find(TableOrder1.class, ordernr);
		if (or1 == null) throw new EntityNotFoundException("Ordernummer " + ordernr + " hittades inte.");
		if (or1.getLastdatum() != null)	{
			if (autoLasUppDagar != null &&
						(autoLasUppDagar.equals(0) || or1.getLastdatum().before(SXUtil.addDate(new java.util.Date(), -1*(autoLasUppDagar-1))))	) {
				lasUppOrder();
			} else {
				throw new SxOrderLastException("Ordernr " + ordernr + " kundnr " + or1.getKundnr() + " är låst av annan användare.");
			}
		}

		if (or1.getLastdatum() != null) throw new SxOrderLastException();
		loadKund(or1.getKundnr());
		List<TableOrder2> o = em.createNamedQuery("TableOrder2.findByOrdernr").setParameter("ordernr", ordernr).getResultList();
		OrderHandlerRad or;
		for (TableOrder2 o2 : o) {
			or = new OrderHandlerRad();
			or.artnr = o2.getArtnr();
			or.best = o2.getBest();
			or.dellev = o2.getDellev();
			or.enh = o2.getEnh();
			or.konto = o2.getKonto();
			or.lev = o2.getLev();
			or.levdat = o2.getLevdat();
			or.levnr = o2.getLevnr();
			or.namn = o2.getNamn();
			or.netto = o2.getNetto();
			or.ordernr = o2.getTableOrder2PK().getOrdernr();
			or.pos = o2.getTableOrder2PK().getPos();
			or.pris = o2.getPris();
			or.prisnr = o2.getPrisnr();
			or.rab = o2.getRab();
			or.stjid = o2.getStjid();
			or.summa = o2.getSumma();
			or.text = o2.getText();
			or.utskrivendatum = o2.getUtskrivendatum();
			or.utskriventid = o2.getUtskriventid();
			ordreg.add(or);
		}

		orderLaddad = true;
	}
	
	private void setOrder1FromCopy(TableOrder1 copyFromTableOrder1) {
		or1.setAdr1(copyFromTableOrder1.getAdr1());
		or1.setAdr2(copyFromTableOrder1.getAdr2());
		or1.setAdr3(copyFromTableOrder1.getAdr3());
		or1.setAnnanlevadress(copyFromTableOrder1.getAnnanlevadress());
		or1.setBetalsatt(copyFromTableOrder1.getBetalsatt());
		or1.setBonus(copyFromTableOrder1.getBonus());
		or1.setDoljdatum(copyFromTableOrder1.getDoljdatum());
		or1.setFaktor(copyFromTableOrder1.getFaktor());
		or1.setForskatt(copyFromTableOrder1.getForskatt());
		or1.setForskattbetald(copyFromTableOrder1.getForskattbetald());
		or1.setFraktbolag(copyFromTableOrder1.getFraktbolag());
		or1.setFraktfrigrans(copyFromTableOrder1.getFraktfrigrans());
		or1.setFraktkundnr(copyFromTableOrder1.getFraktkundnr());
		or1.setKtid(copyFromTableOrder1.getKtid());
		or1.setKundnr(copyFromTableOrder1.getKundnr());
		or1.setKundordernr(copyFromTableOrder1.getKundordernr());
		or1.setLagernr(copyFromTableOrder1.getLagernr());
		or1.setLevadr1(copyFromTableOrder1.getLevadr1());
		or1.setLevadr2(copyFromTableOrder1.getLevadr2());
		or1.setLevadr3(copyFromTableOrder1.getLevadr3());
		or1.setLevdat(copyFromTableOrder1.getLevdat());
		or1.setLevvillkor(copyFromTableOrder1.getLevvillkor());
		or1.setLinjenr1(copyFromTableOrder1.getLinjenr1());
		or1.setLinjenr2(copyFromTableOrder1.getLinjenr2());
		or1.setLinjenr3(copyFromTableOrder1.getLinjenr3());
		or1.setMarke(copyFromTableOrder1.getMarke());
		or1.setMoms(copyFromTableOrder1.getMoms());
		or1.setMottagarfrakt(copyFromTableOrder1.getMottagarfrakt());
		or1.setNamn(copyFromTableOrder1.getNamn());
		or1.setOrdermeddelande(copyFromTableOrder1.getOrdermeddelande());
		or1.setReferens(copyFromTableOrder1.getReferens());
		or1.setReturorder(copyFromTableOrder1.getReturorder());
		or1.setSaljare(copyFromTableOrder1.getSaljare());
		or1.setTidigastfaktdatum(copyFromTableOrder1.getTidigastfaktdatum());
		or1.setTillannanfilial(copyFromTableOrder1.getTillannanfilial());
		or1.setUtlevbokad(copyFromTableOrder1.getUtlevbokad());
		or1.setVeckolevdag(copyFromTableOrder1.getVeckolevdag());
		or1.setWordernr(copyFromTableOrder1.getWordernr());
	}
	
	public final void setLagerNr(short lagernr) {
		// Sätter nytt lager och läser om alla lagersaldon till det nya lagret
		// Kan även användas med samma lagernummer för att uppdatera lagersaldona
		or1.setLagernr(lagernr);

		// Läs in nya lagervärden
		for (OrderHandlerRad o : ordreg) {
			setLagerToOrderRad(o);
		}
	}
	
	public void lasOrder() {
		or1.setLastdatum(SXUtil.getSQLDate());
		or1.setLasttid(SXUtil.getSQLTime());
		or1.setLastav(ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
		em.flush();
	}
	public final void lasUppOrder() {
		or1.setLastav(null);
		or1.setLastdatum(null);
		or1.setLasttid(null);
		em.flush();
	}


	private void setLagerToOrderRad(OrderHandlerRad o) {
		// Hämtar lagervärden för aktuellt lager, och lägger in dem i orderhandlerrad
		TableLager l;
		l = em.find(TableLager.class, new TableLagerPK(o.artnr, or1.getLagernr()));
		if (l == null) {
			o.artLagerplats = null;
			o.artBest = null;
			o.artIlager = null;
			o.artIorder = null;
		} else {
			o.artLagerplats = l.getLagerplats();
			o.artBest = l.getBest();
			o.artIorder = l.getIorder();
			o.artIlager = l.getIlager();
			
		}
		
	}
	
	public final void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
		if (this.anvandare != null) if (this.anvandare.length() > 3) {		//Max 3 tecken i användaren
			this.anvandare = this.anvandare.substring(0, 4);	//Tar de tre första tecknen
		}
	}
	public void setWordernr(int wordernr) {
		or1.setWordernr(wordernr);
	}

	private void getStruktur(ArtikelStruktur strukt, String struktnr, double antal, int iteratorCnt) throws SXToManyIterationsException {
		TableArtikel art;
		ArtikelForStruktur afs;
		iteratorCnt++;
		if (iteratorCnt > 100) {			//Vi itererar för mycket, måste vara fel
			throw new SXToManyIterationsException("För många iterationer vid hämtning av struktur");
		}
		List<TableArtstrukt> rader = em.createNamedQuery("TableArtstrukt.findByNummer").setParameter("nummer", struktnr).getResultList();
		for (TableArtstrukt rad : rader){
			art = em.find(TableArtikel.class, rad.getTableArtstruktPK().getArtnr());
			if (!SXUtil.isEmpty(art.getStruktnr())) {		//Det finns struktur på artikeln
				getStruktur(strukt, art.getStruktnr(), rad.getAntal(), iteratorCnt);
			} else {
				strukt.sumInpris = strukt.sumInpris+art.getKalkyleratInprisNetto()*antal*rad.getAntal();
				afs = new ArtikelForStruktur();
				afs.artnr = art.getNummer();
				afs.antal = antal*rad.getAntal();
				afs.inpris = art.getKalkyleratInprisNetto();
				strukt.strukt.add(afs);
			}

		}

	}

	public void setSaljare(String saljare) { or1.setSaljare(saljare); }
	public String getSaljare() { return or1.getSaljare(); }
	public java.util.Date getDatum() { return or1.getDatum(); }
	public void setMoms(short moms) { or1.setMoms(moms); }
	public short getMoms() { return or1.getMoms(); }
	public void setBonus(boolean bonus) { if (bonus) or1.setBonus((short)1); else or1.setBonus((short)0); }
	public boolean isBonus() { return or1.getBonus() > 0; }

	public void addBonus(TableBonus bonus) {
		ord = new OrderHandlerRad();

		ord.text = null;
		ord.best = 0.0;
		ord.lev = 1.0;
		ord.artnr = "*BONUS*";
		ord.namn = "Faktura: " + bonus.getTableBonusPK().getFaktura();
		ord.konto = "3011";
		ord.enh = null;
		ord.levnr = null;
		ord.artDirektlev = 0;
		ord.artFraktvillkor = 0;
		ord.prisnr = (short)1;

		ord.pris =  SXUtil.getRoundedDecimal(-bonus.getBonus());
		ord.summa = ord.pris;
		ord.rab = 0.0;
		ord.netto = 0.01;
		ord.stjid=0;

		ord.tableBonus = bonus;

		ordreg.add(ord);
		
	}


	public void addRanta(TableRanta tableRanta) {
		TableFuppg fup = (TableFuppg)em.createNamedQuery("TableFuppg.findAll").getSingleResult();

		ord = new OrderHandlerRad();

		ord.text = null;
		ord.best = 0.0;
		ord.lev = 1.0;
		ord.artnr = "*RÄNTA*";
		ord.namn = "Faktura: " + tableRanta.getTableRantaPK().getFaktnr();
		ord.konto = fup.getRantak();
		ord.enh = null;
		ord.levnr = null;
		ord.artDirektlev = 0;
		ord.artFraktvillkor = 0;
		ord.prisnr = (short)1;

		ord.pris = SXUtil.getRoundedDecimal(tableRanta.getRanta());
		ord.summa = ord.pris;
		ord.rab = 0.0;
		ord.netto = 0.01;
		ord.stjid=0;

		ord.tableRanta = tableRanta;

		ordreg.add(ord);

	}



	public OrderHandlerRad addTextRow(String text) {
		return addTextRow(text, 0.0, 0.0);
	}
	public OrderHandlerRad addTextRow(String text, double inpris, double utpris) {
		ord = new OrderHandlerRad();

		ord.text = text;
		ord.best = 0.0;
		ord.lev = 0.0;
		ord.artnr = "";
		ord.namn = "";
		ord.konto = "3011";
		ord.enh = "";
		ord.levnr = "";
		ord.artDirektlev = 0;
		ord.artFraktvillkor = 0;
		ord.prisnr = (short)1;

		ord.pris = 0.0;
		ord.summa = utpris;
		ord.rab = 0.0;
		ord.netto = inpris;
		ord.stjid=0;

		ordreg.add(ord);
		return ord;
	}

	public OrderHandlerRad addStjRow(String artnr, String namn, String levnr, double antal, String enh, double inpris, double utpris, double utrab) {
		return addStjRow(artnr, namn, levnr, antal, enh, inpris, utpris, utrab,(short)0,(short)0);
	}
	
	public OrderHandlerRad addStjRow(String artnr, String namn, String levnr, double antal, String enh, double inpris, double utpris, double utrab, short stjAutobestall, short stjFinnsILager) {
		ord = new OrderHandlerRad();
		if (!artnr.startsWith("*")) artnr="*"+artnr;
		ord.best = antal;
		ord.lev = antal;
		ord.artnr = artnr;
		ord.namn = namn;
		ord.konto = "3011";
		ord.enh = enh;
		ord.levnr = levnr;
		ord.artDirektlev = 0;
		ord.artFraktvillkor = 0;
		ord.prisnr = (short)1;

		ord.pris = utpris;
		ord.rab = utrab;

		ord.summa = ord.pris * antal * (1-ord.rab/100);
		ord.netto = inpris;

		//Gör iordning TableStjrad
		TableStjarnrad stj;
		Integer maxstjid = (Integer)em.createNamedQuery("TableStjarnrad.getMaxId").getSingleResult();
		maxstjid++;
		stj = new TableStjarnrad(maxstjid);
		stj.setAnvandare(anvandare);
		stj.setRegdatum(new Date());
		stj.setAntal(antal);
		stj.setArtnr(artnr);
		stj.setAutobestall(stjAutobestall);
		stj.setFinnsilager(stjFinnsILager);
		stj.setEnh(enh);
		stj.setInpris(inpris);
		stj.setKundnr(or1.getKundnr());
		stj.setLagernr(or1.getLagernr());
		stj.setLevnr(levnr);
		stj.setNamn(namn);
		em.persist(stj);

		ord.stjid=stj.getStjid();

		ordreg.add(ord);

		return ord;
	}

	public OrderHandlerRad addRowNoArtikelLookup(String artNr, String artNamn, double antal, String enhet, double pris, double rab, String levNr, String konto, double inpris) throws SXEntityNotFoundException{
		return addRowNoArtikelLookup(artNr, artNamn, antal, enhet, pris, rab, levNr, konto, inpris, 0, 0, 0, 0);
	}
	public OrderHandlerRad addRowNoArtikelLookup(String artNr, String artNamn, double antal, String enhet, double pris, double rab, String levNr, String konto, double inpris, double inrab, double inpFraktproc, double inpFrakt, double inpMiljo) throws SXEntityNotFoundException{
		return addRowNoArtikelLookup(artNr, artNamn, antal, enhet, pris, rab, levNr, konto, inpris, inrab, inpFraktproc, inpFrakt, inpMiljo, (short)0, (short)0);
	}
	
	public OrderHandlerRad addRowNoArtikelLookup(String artNr, String artNamn, double antal, String enhet, double pris, double rab, String levNr, String konto, double inpris, double inrab, double inpFraktproc, double inpFrakt, double inpMiljo, short stjAutobestall, short stjFinnsILager) throws SXEntityNotFoundException{
		return addRowNoArtikelLookup(artNr, artNamn, antal, enhet, pris, rab, levNr, konto, inpris, inrab, inpFraktproc, inpFrakt, inpMiljo, (short)0, (short)0, null);
		
	}
	//Lägger till en rad utan att vverifiera om artikelnumret finns som artikel, eller slå upp artikeldata
	//Om det är en stjärnrad så läggs den till som *-rad
	//Om det är en textrad så läggs den in
	public OrderHandlerRad addRowNoArtikelLookup(String artNr, String artNamn, double antal, String enhet, double pris, double rab, String levNr, String konto, double inpris, double inrab, double inpFraktproc, double inpFrakt, double inpMiljo, short stjAutobestall, short stjFinnsILager, String textrad) throws SXEntityNotFoundException{
		ord = new OrderHandlerRad();
		if (artNr!=null && artNr.startsWith("*")) {
			ord = addStjRow(artNr, artNamn, levNr, antal, enhet, (inpris * (1-inrab/100) * (1+inpFraktproc/100)) + inpFrakt + inpMiljo, pris, rab, stjAutobestall, stjFinnsILager);
		} else if (SXUtil.isEmpty(artNr) && !SXUtil.isEmpty(textrad)) {
			ord = addTextRow(textrad, (inpris * (1-inrab/100) * (1+inpFraktproc/100)) + inpFrakt + inpMiljo, pris*(1-rab/100));
		} else {
			if (!SXUtil.isEmpty(artNr)) {
				TableArtikel art = em.find(TableArtikel.class, artNr);
				if (art==null) throw new SXEntityNotFoundException("Artikel " + artNr + " finns inte.");
			} else if (!SXUtil.isEmpty(artNr) || antal != 0 || pris != 0 || rab!=0) throw new EntityNotFoundException("En artikelrad saknar artikelnummer men innnehåller annan data.");
			
			ord.best = antal;
			ord.lev = antal;
			ord.artnr = artNr;
			ord.namn = artNamn;
			ord.konto = konto;
			ord.enh = enhet;
			ord.levnr = levNr;
			ord.prisnr = (short)1;

			setLagerToOrderRad(ord); // Sätt lagersaldon

			ord.pris = pris;
			ord.rab = rab;

			ord.summa = pris * antal * (1-rab/100);
			ord.netto = (inpris * (1-inrab/100) * (1+inpFraktproc/100)) + inpFrakt + inpMiljo;
			ord.netto = inpris;
			ordreg.add(ord);
		}
		return ord;
	}

	public void addRow(String artnr, double antal, double pris, double rab) throws SXEntityNotFoundException {
		ord = new OrderHandlerRad();
		art = em.find(TableArtikel.class, artnr);
		if (art == null) {
			ServerUtil.log("OrderHandler-addRow-Kan inte hitta artikel " + artnr + " för order.");
			throw new SXEntityNotFoundException("Kan inte hitta artikel  " + artnr );
		}
		if (!SXUtil.isEmpty(art.getStruktnr())) {		//Det finns struktur på artikeln
			ArtikelStruktur strukt = new ArtikelStruktur();
			try {
				getStruktur(strukt, art.getStruktnr(), antal, 0);
				for (ArtikelForStruktur a : strukt.strukt) {
					addRow(a.artnr, a.antal, SXUtil.getRoundedDecimal(a.inpris/strukt.sumInpris*pris), rab);
				}
			} catch (SXToManyIterationsException e) { throw new SXEntityNotFoundException("För många iterationer på strukturartikel " + artnr); }

		} else {
			addRowNoArtikelLookup(art.getNummer(), art.getNamn(), antal, art.getEnhet(), pris, rab, art.getLev(), art.getKonto(), art.getInpris(), art.getRab(), art.getInpFraktproc(), art.getInpFrakt(), art.getInpMiljo());
		}

	}


	public void addRow(OrderHandlerRad rad) {
		ordreg.add(rad);
	}

	public void addRow(String artnr, double antal) throws SXEntityNotFoundException{
		ord = new OrderHandlerRad();
		art = em.find(TableArtikel.class, artnr); 
		if (art == null) { 
			ServerUtil.log("OrderHandler-addRow-Kan inte hitta artikel " + artnr + " för order.");
			throw new SXEntityNotFoundException("Kan inte hitta artikel  " + artnr );
		}
		if (!SXUtil.isEmpty(art.getStruktnr())) {		//Det finns struktur på artikeln
			ArtikelStruktur strukt = new ArtikelStruktur();
			try {
				getStruktur(strukt, art.getStruktnr(), antal, 0);
				for (ArtikelForStruktur a : strukt.strukt) {
					addRow(a.artnr, a.antal);
				}
			} catch (SXToManyIterationsException e) { throw new SXEntityNotFoundException("För många iterationer på strukturartikel " + artnr); }

		} else {
			ord.best = antal;
			ord.lev = antal;
			ord.artnr = art.getNummer();
			ord.namn = art.getNamn();
			ord.konto = art.getKonto();
			ord.enh = art.getEnhet();
			ord.levnr = art.getLev();
			ord.artDirektlev = art.getDirektlev();
			ord.artFraktvillkor = art.getFraktvillkor();
			ord.prisnr = (short)1;

			setLagerToOrderRad(ord); // Sätt lagersaldon

			// Börja ta fram det bästa priset

			// Ta fram bästa rabatten
			double bastaRab = 0.0;
			double bastaBruttoPris = 0.0;
			double bastaNettoPris = 0.0;
			TableKunrab kra;
			if (!SXUtil.isEmpty(art.getRabkod())) {	// Om rabkoden är tom så gäller ingen rabatt
				if (!"NTO".equals(art.getRabkod())) {	// Vi kan inte ha rabatter för hela NTO-gruppen
					kra = em.find(TableKunrab.class, new TableKunrabPK(kun.getNummer(),art.getRabkod(),""));	// Ta först fram rabatten för huvudgruppen
					if (kra != null) { if (kra.getRab() > bastaRab) { bastaRab = kra.getRab(); } }
				}
				if (!SXUtil.isEmpty(art.getKod1())) {				// KOlla nu om vi har undergrupp, och ta fram rabatten till den
															//  Här tillåter vi att huvudgruppen är av NTO, så vi kan få rabatt på undergrupp till den
					kra = em.find(TableKunrab.class, new TableKunrabPK(kun.getNummer(),art.getRabkod(),art.getKod1()));
					if (kra != null) { if (kra.getRab() > bastaRab) { bastaRab = kra.getRab(); } }
				}
				// Kolla nu hur det är med basrabatten, ändra inte om det är frågan om nettopris
				// Vi tillåter inte basrabatt på NTO-Gruppen
				if (kun.getBasrab() > bastaRab && !"NTO".equals(art.getRabkod())) { bastaRab = kun.getBasrab(); }
			}
			// Nu ska vi ha bastaRab laddat och klart

			// Kolla om vi har en kampanj, och i så fall om den gäller för kunde

			boolean kampanj = false;

			try {	// Fångar null-pointer ifall något kampanjdatum är felaktigt

				//Skapa ett datum i SQL.Date format
				Calendar idag = SXUtil.getTodaySQLDate();  // Returns a calendar with time set  to 0
				// Skapa calendarobjekt med kampanjperioden
				Calendar kampfrdat = Calendar.getInstance();
				kampfrdat.setTime(art.getKampfrdat());
				Calendar kamptidat = Calendar.getInstance();
				kamptidat.setTime(art.getKamptidat());


				if (idag.compareTo(kampfrdat) >= 0 && idag.compareTo(kamptidat) <= 0) {	// Det finns kampanj på artikeln, nu ska vi kolla om den gäller för kunden
					if (art.getKampkundartgrp() == 0 && art.getKampkundgrp() == 0) {
						kampanj = true;
					} else {
						int q1 = kun.getElkund()*SXConstant.KAMPBIT_ELKUND + kun.getVvskund()*SXConstant.KAMPBIT_VVSKUND + kun.getVakund()*SXConstant.KAMPBIT_VAKUND + kun.getGolvkund()*SXConstant.KAMPBIT_GOLVKUND + kun.getFastighetskund()*SXConstant.KAMPBIT_FASTIGHETSKUND;
						int q2 = kun.getInstallator()*SXConstant.KAMPBIT_INSTALLATOR + kun.getButik()*SXConstant.KAMPBIT_BUTIK + kun.getIndustri()*SXConstant.KAMPBIT_INDUSTRI + kun.getOem()*SXConstant.KAMPBIT_OEM + kun.getGrossist()*SXConstant.KAMPBIT_GROSSIST;
						if ((art.getKampkundartgrp() & q1) > 0 && (art.getKampkundgrp() & q2) > 0) {
							kampanj = true;
						}
					}
				}
			} catch (NullPointerException e) {}


			// Dax att ta fram bästa bruttopris och nettopris med avseende på antal
			bastaBruttoPris = art.getUtpris();
			bastaNettoPris  = bastaBruttoPris; // Sätter startv'rde för b'sta netto

			if (kampanj && art.getKamppris() != 0 && art.getKamppris() < bastaNettoPris) { bastaNettoPris = art.getKamppris(); }
			if (art.getStafAntal1() > 0.0 && antal >= art.getStafAntal1())  {
				 if (art.getStafPris1() != 0.0) {
					 if (art.getStafPris1() < bastaBruttoPris ) { bastaBruttoPris = art.getStafPris1(); }
				 }
				 if (kampanj && art.getKampprisstaf1() != 0.0 && art.getKampprisstaf1() < bastaNettoPris) { bastaNettoPris = art.getKampprisstaf1(); }
			}
			if (art.getStafAntal2() > 0.0 && antal >= art.getStafAntal2())  {
				 if (art.getStafPris2() != 0.0) {
					 if (art.getStafPris2() < bastaBruttoPris ) { bastaBruttoPris = art.getStafPris2(); }
				 }
				 if (kampanj && art.getKampprisstaf2() != 0.0 && art.getKampprisstaf2() < bastaNettoPris) { bastaNettoPris = art.getKampprisstaf2(); }
			}

			// Kolla om det finns ett nettopris som offert
			if (!SXUtil.isEmpty(kun.getNettolst())) {
				net = em.find(TableNettopri.class, new TableNettopriPK(kun.getNettolst(), artnr));
				if (net != null) {
					if (net.getPris() > 0.0 &&
						net.getPris() < bastaNettoPris) { bastaNettoPris = net.getPris(); }
				 }
			}

			// Kolla vilket pris som är läögst - brutto-rab eller netto
			if (bastaNettoPris != 0.0 && bastaNettoPris < bastaBruttoPris * (1-bastaRab/100)) {	//Vi har ett nettopris
				 ord.pris = bastaNettoPris;
				 ord.rab = 0.0;
			} else {	    //Vi har brutto - rabatt
				 ord.pris = bastaBruttoPris;
				 ord.rab = bastaRab;
			}
			// Nu ska bästa priset vara satt

			ord.summa = ord.pris * antal * (1-ord.rab/100);
			ord.netto = (art.getInpris() * (1-art.getRab()/100) * (1+art.getInpFraktproc()/100)) + art.getInpFrakt() + art.getInpMiljo();
			ordreg.add(ord);
		}
	}
	
	
	public OrderHandlerRad getRow(int rad) {
		OrderHandlerRad or;
		try {
			or = ordreg.get(rad);
		} catch (IndexOutOfBoundsException e) { or = null; }
		return or;
	}
	public int getRowCount() {
		int rows = 0;
		if (ordreg!=null) rows=ordreg.size();
		return rows;
	}
	public OrderHandlerRad getLastRow() {
		return getRow(getRowCount()-1);
	}

	public ArrayList<OrderHandlerRad> getOrdreg() {
		return ordreg;
	}

	public TableOrder1 getTableOrder1() {
		return or1;
	}

	public String getKundNr() {
		return or1.getKundnr();
	}

	public Short getLagerNr() {
		return or1.getLagernr();
	}
	
	public String getLevadr1() {
		return or1.getLevadr1();
	}
	public String getLevadr2() {
		return or1.getLevadr2();
	}
	public String getLevadr3() {
		return or1.getLevadr3();
	}
	public String getNamn() {
		return or1.getNamn();
	}
	public String getAdr1() {
		return or1.getAdr1();
	}
	public String getAdr2() {
		return or1.getAdr2();
	}
	public String getAdr3() {
		return or1.getAdr3();
	}

	public String getBetalsatt() {
		return or1.getBetalsatt();
	}

	public void setBetalsatt(String betalsatt) {
		or1.setBetalsatt(betalsatt);
	}

	public boolean getForskatt() {
		return or1.getForskatt();
	}

	public void setForskatt(boolean forskatt) {
		or1.setForskatt(forskatt);
	}

	public boolean getForskattbetald() {
		return or1.getForskattbetald();
	}

	public void setForskattbetald(boolean forskattbetald) {
		or1.setForskattbetald(forskattbetald);
	}

	public int getKundordernr() {
		return or1.getKundordernr();
	}

	public void setKundordernr(int kundordernr) {
		or1.setKundordernr(kundordernr);
	}
	
	
	
	public TableKund getTableKund() {
		return kun;
	}
	
	public String getStatus() {
		return or1.getStatus();
	}
	public void setStatus(String status) {
		or1.setStatus(status);
	}
	public Double getOrderSumma() {
		double summa = 0;
		for (OrderHandlerRad o: ordreg) {
			summa = summa + o.summa;
		}
		return summa;
	}
	
	public Integer getOrdernr() {
		return or1.getOrdernr();
	}

	private void loadKund(String kundnr) {
		kun = em.find(TableKund.class, kundnr);
		if (kun == null) { throw new EntityNotFoundException("Kan inte hitta kund " + kundnr + " för order."); }
	}

	public final void setKund(String kundnr) {
		// Hämta kund och sätt standardvärden för or1
		loadKund(kundnr);
		or1.setKundnr(kun.getNummer());
		or1.setNamn(kun.getNamn());
		or1.setAdr1(kun.getAdr1());
		or1.setAdr2(kun.getAdr2());
		or1.setAdr3(kun.getAdr3());
		setLevAdr(kun.getLnamn(), kun.getLadr2(), kun.getLadr3());

		//lägga till info sist i Säljar-strängen
		or1.setSaljare(java.lang.String.format( "%-30s%3s", kun.getSaljare(), "/" + anvandare ));
		
		or1.setReferens(kun.getRef());
		setKreditTid(kun.getKtid());
		or1.setBonus(kun.getBonus());
		or1.setFaktor(kun.getFaktor());
		or1.setLevvillkor(kun.getLevvillkor());
		or1.setMottagarfrakt(kun.getMottagarfrakt());
		or1.setFraktkundnr(kun.getFraktkundnr());
		or1.setFraktbolag(SXUtil.toStr(kun.getFraktbolag()));			//Säkerställ att inte spara null, då de kan orsaka problem vid dagens order
		or1.setFraktfrigrans(kun.getFraktfrigrans());
		if (kun.getMomsfri() > 0) { 
			or1.setMoms((short)0);
		} else {
			or1.setMoms((short)1);
		}
		or1.setLinjenr1(SXUtil.toStr(kun.getLinjenr1()));			//Säkerställ att inte nullvärden sparas, för de kan orsaka problem med dagens order
		or1.setLinjenr2(SXUtil.toStr(kun.getLinjenr2()));
		or1.setLinjenr3(SXUtil.toStr(kun.getLinjenr3()));
		or1.setWordernr(0);
		or1.setUtlevbokad((short)0);
		or1.setTillannanfilial((short)0);
		or1.setAnnanlevadress((short)0);
	}

	public boolean checkKreditvardighet() {
		try {
			checkKreditvardighetOrThrow();
		} catch (KreditSparrException e) { return false; }
		return true;
	}
	
	public void checkKreditvardighetOrThrow() throws KreditSparrException{
		if (getOrderSumma() > 0)	{		//Om det inte är en kredit
			Double b;
			b = (Double)em.createNamedQuery("TableKundres.findSumForKreditTest")
				.setParameter("kundnr", kun.getNummer())
				.setParameter("falldat", SXUtil.addDate(new Date(), -60))
				.getSingleResult();
			if (b != null)	if (b.compareTo(new Double(1000)) > 0) { throw new KreditSparrException("Det finns fakturor som är förfallna längre än 60 dagar."); }

				b = (Double)em.createNamedQuery("TableKundres.findSumForKreditTest")
					.setParameter("kundnr", kun.getNummer())
					.setParameter("falldat", SXUtil.addDate(new Date(), -30))
					.getSingleResult();
			if ( kun.getKgransforfall30() > 0) {
				if (b != null) if (b.compareTo(kun.getKgransforfall30()) > 0) { throw new KreditSparrException("Det finns för stort belopp fakturor som är förfallna längre än 30 dagar."); }
			} else {
				if (b != null) if (b.compareTo(new Double(5000)) > 0) { throw new KreditSparrException("Det finns fakturor som är förfallna längre än 30 dagar."); }
			}

			if (kun.getKgrans() > 0) {
				b = (Double)em.createNamedQuery("TableKundres.findSumForKund")
					.setParameter("kundnr", kun.getNummer())
					.getSingleResult();
				if (b != null) if (b.compareTo(kun.getKgrans() - getOrderSumma()) > 0) { throw new KreditSparrException("Kreditgränsen är överskriden. Aktuell kreditgräns: " + SXUtil.getFormatNumber(kun.getKgrans()) + " Belopp inklusive denna faktura: " + SXUtil.getFormatNumber(getOrderSumma() + b)); }		// Jämför mot nuvarande orderns värde om kunden är kredivärdig
			}
		}
			// Har vi kommit hit så har alla spärrar passerats, och det är grönt för kunden att handla
	}

	public void setMarke(String marke) {
		or1.setMarke(marke);
	}	public String getMarke() {
		return or1.getMarke();
	}
	
	public void setLevAdr(String adr1, String adr2, String adr3) {
		or1.setLevadr1(adr1);
		or1.setLevadr2(adr2);
		or1.setLevadr3(adr3);
	}
	
	public void setAnnanLevAdr(String adr1, String adr2, String adr3) {
		//Sätter levadress, samt flaggan för att levadress har ändrats
		setLevAdr(adr1, adr2, adr3);
		or1.setAnnanlevadress((short)1);
	}

	
	public void setKreditTid(short ktid) {
		or1.setKtid(ktid);
	}
	
	public void setDirektlevnr(int direktlevnr) {
		or1.setDirektlevnr(direktlevnr);
	}
	public int getDirektlevnr() {
		return or1.getDirektlevnr();
	}

	public int prepareNextOrderNr() {
		//	 Tar fram nästa ordernr och sätter det i aktuell order , samt uppdaterar räknaren
		TableFdordernr fdo;
		int scn = 0;
		while (true) {
			scn++;			//Räkna antalet loopar för att avgöra när fel skall skapass
			fdo = (TableFdordernr)em.createNamedQuery("TableFdordernr.findAll").getResultList().get(0);
			if (em.createNamedQuery("TableFdordernr.updateOrdernrBy1").setParameter("ordernr", fdo.getOrdernr()).executeUpdate() > 0) {
				// Uppdatering lyckades, avbryt while/loopen
				break;
			} else {
				if (scn > 10) {				// Har vi provat så många gånger så att vi får ge upp?
					throw new PersistenceException("Kunde inte uppdatera fdordernr för order " + or1.getNamn());
				}
			}
		}
		// OK Allt klart, vi har fått ett ordernr

		or1.setOrdernr(fdo.getOrdernr());
		return(fdo.getOrdernr());

	}


	public int prepareNextOffertNr() {
		//	 Tar fram nästa offertnr , samt uppdaterar räknaren
		TableFaktdat fdt;
		int scn = 0;
		while (true) {
			scn++;			//Räkna antalet loopar för att avgöra när fel skall skapass
			fdt = (TableFaktdat) em.find(TableFaktdat.class, SXConstant.DEFAULT_FT);

			if (em.createNamedQuery("TableFaktdat.updateBestnrBy1").setParameter("bestnr", fdt.getBestnr()).setParameter("ft", SXConstant.DEFAULT_FT).executeUpdate() > 0) {
				// Uppdatering lyckades, avbryt while/loopen
				break;
			} else {
				if (scn > 10) {				// Har vi provat så många gånger så att vi får ge upp?
					throw new PersistenceException("Kunde inte uppdatera faktdat för offert " + or1.getNamn());
				}
			}
		}
		// OK Allt klart, vi har fått ett offertnr

		return(fdt.getOffertnr());

	}


	public void deleteOrder(boolean skapaOrderHandRaderad) {
		if (!orderLaddad) throw new EntityNotFoundException("Ordern är inte laddad och kan inte raderas.");
		deleteFromOrder2AndUpdateLager();
		if (skapaOrderHandRaderad) em.persist( new TableOrderhand(or1.getOrdernr(), anvandare, SXConstant.ORDERHAND_RADERAD));
		em.remove(or1);
		em.flush();
	}

	  private void deleteFromOrder2AndUpdateLager() {
			 // Radera alla rader ur order2 samt uppdatera lager
			 // Observera att ordernumret tas ur or1.ordernr
			TableLager lag;
			Iterator i = em.createNamedQuery("TableOrder2.findByOrdernr").setParameter("ordernr", or1.getOrdernr()).getResultList().iterator();
			while (i.hasNext()) {
				TableOrder2 o = (TableOrder2)i.next();
				if (o.getArtnr() != null) { 
					if (!o.getArtnr().startsWith("*")) {
						lag = em.find(TableLager.class, new TableLagerPK(o.getArtnr(), or1.getLagernr()));
						if (lag == null) {
							// Normalt skulle vi lägga till raden i lager-tqbellen här, men då vi bara raderar en befintlig best så struntar vi i det 
							// för om vi minskar best-antalet får vi ännu konstigare resultt
						} else {
							lag.setIorder(lag.getIorder() - o.getBest());
						}
					} else { // Vi har en *-rad
						// Vi gör inget med TableStjarnrad, utan låter uppdatera om något ändrats
						// om raden har tagits bort från ordern låter vi deta bara passera
					}
				}
				em.remove(o);
			}
			//em.createNamedQuery("TableOrder2.deleteByOrdernr").setParameter("ordernr", or1.getOrdernr()).executeUpdate();	// Radera alla rader
	  }

	
	public Integer persistOrder() throws SxInfoException{
		return persistOrder(false);
	}
	
	public Integer persistOrder(boolean isDirektleverans) throws SxInfoException {
		//Sparar som ny order
		// Returnerar ordernumret
		short scn;
		TableLager lag;
		
		// Hämtaa nytt irdernt och räkna upp
		if (!orderLaddad) {
			if (or1.getOrdernr() == null || or1.getOrdernr() == 0) {
				prepareNextOrderNr();
			}
			or1.setDatum(new Date());
			or1.setTid(new Date());
		} else {
			deleteFromOrder2AndUpdateLager();
		}
	
		if (or1.getStatus() == null ) {		// Om vi inte satt status sätter vi förvald nu
			or1.setStatus(SXConstant.ORDER_STATUS_SPARAD);
		}
		if (or1.getDellev() == 0) { or1.setDellev((short)1); }

		
		if (!orderLaddad) em.persist(or1);
		scn = 0;
		
		//Ta bort tomma sista rader
		OrderHandlerRad tempRad;
		while (ordreg.size() > 0) {
			tempRad = ordreg.get(ordreg.size()-1);
			if (SXUtil.isEmpty(tempRad.artnr) && SXUtil.isEmpty(tempRad.text) && SXUtil.noNull(tempRad.summa)== 0) {
				ordreg.remove(ordreg.size()-1);
			} else { break; }
		}
		boolean sammaLeverantor = true;
		String tempLev=null;
		for (OrderHandlerRad o : ordreg) {
			scn++;
			o.artnr = SXUtil.toStr(o.artnr); //Se till så att det inte är null eftersom det inte är tillåtet i databasen
			o.ordernr = or1.getOrdernr();
			o.dellev = or1.getDellev();
			o.pos = scn;
			or2 = o.getOrder2();
			if (o.stjid == null) { o.stjid = 0; }
			em.persist(or2);
			
			//Kollar om det är samma leverantör på alla rader med aritkelnummer. Används för direktleveranser
			//Efter loopen geneom orderraderna är klar kollas om templev==null - då fanns det ingen leverantör alls på ordern och sammaLeverantor=false
			if (!SXUtil.isEmpty(o.artnr)) {
				if (tempLev==null && !SXUtil.isEmpty(o.levnr)) tempLev = o.levnr;
				if (tempLev!=null ) if (!tempLev.equals(o.levnr)) sammaLeverantor=false;
			}
			
			
			// Uppdatera lagersaldo
			if (o.artnr != null) {
				if (!o.artnr.startsWith("*")) {
					lag = em.find(TableLager.class, new TableLagerPK(o.artnr, or1.getLagernr()));
					if (lag == null) {
						lag = new TableLager(o.artnr, or1.getLagernr(),o.best,0);
						em.persist(lag);
					} else {
						lag.setIorder(lag.getIorder()+o.best);
					}
				} else { // Vi har *-rad
					TableStjarnrad stj = null;

					if (o.stjid > 0) {
						stj = (TableStjarnrad)em.find(TableStjarnrad.class, o.stjid);
					}

					// Denna if-sats är beroende av resultatet från stj = em.find
					// samt av värdet i o.stjid
					// Så var försiktig vid ändringar
					if (stj == null) {
						Integer maxstjid = (Integer)em.createNamedQuery("TableStjarnrad.getMaxId").getSingleResult();
						maxstjid++;
						stj = new TableStjarnrad(maxstjid);
						stj.setAnvandare(anvandare);
						stj.setRegdatum(new Date());
						em.persist(stj);
					}
					stj.setAntal(o.best);
					stj.setArtnr(o.artnr);
					stj.setAutobestall(o.stjAutobestall);
					stj.setFinnsilager(o.stjFinnsILager);
					stj.setEnh(o.enh);
					stj.setInpris(o.netto);
					stj.setKundnr(or1.getKundnr());
					stj.setLagernr(or1.getLagernr());
					stj.setLevnr(o.levnr);
					stj.setNamn(o.namn);
				}
			}
		}
		if (tempLev==null) sammaLeverantor=false;		//Om tmepLev==null så fanns ingen leverntör alls på ordern, och sammaLeverantor=false
		if (!sammaLeverantor && isDirektleverans) throw new SxInfoException("Ordern är markerad för direktleverans, men innehåller olika (eller ingen) leverantör.");
		em.persist( new TableOrderhand(or1.getOrdernr(), anvandare, SXConstant.ORDERHAND_SKAPAD));
		em.flush();
		orderLaddad = true;			// Signalera att ordern nu finns sparad, och aktuell order därför betraktas som laddad
		return or1.getOrdernr();
	}


	public Integer persistOffert() {
		//Sparar som ny offert
		// Returnerar offertnumret
		short scn;
		TableLager lag;

		int offertnr =	prepareNextOffertNr();
		or1.setDatum(new Date());
		or1.setTid(new Date());


		TableOffert1 of1 = new TableOffert1();
		TableOffert2 of2;
		of1.setOffertnr(offertnr);
		of1.setAdr1(or1.getAdr1());
		of1.setAdr2(or1.getAdr2());
		of1.setAdr3(or1.getAdr3());
		of1.setAnnanlevadress(or1.getAnnanlevadress());
		of1.setBonus(or1.getBonus());
		of1.setDatum(new Date());
		of1.setFaktor(or1.getFaktor());
		of1.setFraktbolag(or1.getFraktbolag());
		of1.setFraktfrigrans(or1.getFraktfrigrans());
		of1.setFraktkundnr(or1.getFraktkundnr());
		of1.setKtid(or1.getKtid());
		of1.setKundnr(or1.getKundnr());
		of1.setLagernr(or1.getLagernr());
		of1.setLevadr1(or1.getLevadr1());
		of1.setLevadr2(or1.getLevadr2());
		of1.setLevadr3(or1.getLevadr3());
		of1.setLevdat(or1.getLevdat());
		of1.setLevvillkor(or1.getLevvillkor());
		of1.setMarke(or1.getMarke());
		of1.setMoms(or1.getMoms());
		of1.setMottagarfrakt(or1.getMottagarfrakt());
		of1.setNamn(or1.getNamn());
		of1.setOrdermeddelande(or1.getOrdermeddelande());
		of1.setReferens(or1.getReferens());
		of1.setSaljare(or1.getSaljare());
		em.persist(of1);
		scn = 0;
		for (OrderHandlerRad o : ordreg) {
			scn++;
			o.offertnr = offertnr;
			o.dellev = or1.getDellev();
			o.pos = scn;
			if (o.stjid == null) { o.stjid = 0; }
			of2 = o.getOffert2();
			em.persist(of2);
		}
		em.flush();
		return of1.getOffertnr();
	}


	public void sortLevNr() {
		java.util.Collections.sort(ordreg, new OrderHandlerComparatorLevNr());

	}	

	public void sortLagerPlatsArtNr() {
		java.util.Collections.sort(ordreg, new OrderHandlerComparatorLagerPlatsArtNr());

	}
	
	}
	
	 class OrderHandlerComparatorLevNr implements java.util.Comparator {
		public int compare (java.lang.Object o1, java.lang.Object o2) {
			try {
				String l1 = ((OrderHandlerRad)o1).levnr;
				String l2 = ((OrderHandlerRad)o2).levnr;
				if (l1 == null) {
					return 0;
				} else {
					return l1.compareTo(l2);
				}
			} catch (Exception e) { return 0; }
		}
	}


	 	class OrderHandlerComparatorLagerPlatsArtNr implements java.util.Comparator {
		public int compare (java.lang.Object o1, java.lang.Object o2) {
			try {
				String l1 = ((OrderHandlerRad)o1).artLagerplats;
				String l2 = ((OrderHandlerRad)o2).artLagerplats;
				String a1 = ((OrderHandlerRad)o1).artnr;
				String a2 = ((OrderHandlerRad)o2).artnr;
				if (l1 == null) {
					return 0;
				} else {
					if (l1.equals(l2)) {
						if (a1 == null) {
							return 0;
						} else {
							return a1.compareTo(a2);
						}
					}	else {
						return l1.compareTo(l2);
					}
				}
			} catch (Exception e) { return 0; }
		}
	}

	class ArtikelStruktur {
		ArrayList<ArtikelForStruktur> strukt = new ArrayList();
		double sumInpris=0;
	}
	class ArtikelForStruktur {
		String artnr = null;
		Double antal=null;
		Double inpris=null;
	}



/*
                      MAP
GetOrdFromOS   PROCEDURE(),LONG              !Returnerar TRU om ORD är laddad med nya orderdel
Art_2_OS        PROCEDURE
OS_2_Ord        PROCEDURE
Set_Pris         PROCEDURE(real)                !Antal
Get_OrderNr       PROCEDURE(),BYTE
Get_BEstNr        PROCEDURE()
Spara_Order2         PROCEDURE(),BYTE
SparaDirektBest      PROCEDURE(STRING),BYTE
GallerKampanj        PROCEDURE(),BYTE
CheckArtAntal      PROCEDURE(REAL,BYTE = 1),REAL   !Föreslaget antal, tystläge
SendWebSQL     PROCEDURE(String),BYTE,PROC
AddOErr        PROCEDURE(string)
GetArtikelLager PROCEDURE(STRING,BYTE)
CheckKreditVardig    PROCEDURE(),BYTE
                     END

KampBitElKund           EQUATE(1)
KampBitVVSKund          EQUATE(2)
KampBitVAKund           EQUATE(4)
KampBitGolvKund         EQUATE(8)
KampBitFastighetskund   EQUATE(16)

KampBitInstallator      EQUATE(1)
KampBitButik            EQUATE(2)
KampBitIndustri         EQUATE(4)
KampBitOEM              EQUATE(8)
KampBitGrossist         EQUATE(16)

!VeckoLevDagArr  BYTE,DIM(7)

qwo1  QUEUE,PRE(qwo1)
record   group
wordernr LONG
kundnr   like(KUN:Nummer)
marke    like(OR1:Marke)
lagernr  LIKE(OR1:LagerNr)
antalrader  LONG
KreditSparr BYTE
 . .


ORDREG      QUEUE,PRE(ORD)
RECORD  GROUP
Nummer   LIKE(art:nummer)
Namn     LIKE(ART:Namn)
LevNr    LIKE(LEV:Nummer)
FraktVillkor   LIKE(art:Fraktvillkor)
Antal    REAL
Enh      LIKE(ART:Enhet)
Pris     REAL
Rab      REAL
Summa    REAL
Netto    REAL
Konto    CSTRING(7)
Lager    REAL
Lagerplats LIKE(LAG:Lagerplats)
OrderNr     LONG
DirektLev   BYTE
        . .


OS          QUEUE,PRE(OS)                 !Håller hela weborderinnehållet, därefter flyttas det till ORD för att spara order
RECORD  GROUP
Nummer   LIKE(art:nummer)
Namn     LIKE(ART:Namn)
LevNr    LIKE(LEV:Nummer)
FraktVillkor   LIKE(art:Fraktvillkor)
Antal    REAL
Enh      LIKE(ART:Enhet)
Pris     REAL
Rab      REAL
Summa    REAL
Netto    REAL
Konto    CSTRING(7)
Lager    REAL
Lagerplats LIKE(LAG:Lagerplats)
OrderNr     LONG
DirektLev   BYTE
        . .



Err   BYTE
ordercn  LONG


AntalRader  LONG

savmarke  like(OR1:Marke)
OrderSumma  REAL

seowner  cstring(61)

BestBehov   BYTE

 CODE

 relate:artikel.open()
 relate:lager.open()
 relate:kund.open()
 relate:order1.open()
 relate:order2.open()
 relate:orderhand.open()
 relate:fdordernr.open()
 relate:nettopri.open()
 relate:kunrab.open()
 relate:best1.open()
 relate:best2.open()
 relate:faktdat.open()
 relate:lev.open()
 relate:sxreg.open()


 AddQInfo('Startar sök efter ny WebOrder',FALSE)
 FREE(qwo1)
 IF SXR:Get(SEOwner,'SxServWebODBCOwnerStr','sxadm,?,?')
    Err = TRUE
    AddOErr('SXR:Get WebODBCOwnerStr')
 .
 SaljexSEOwner = CLIP(SEOwner)

 IF NOT Err
    open(websqlfile)
    IF ErrorCode()
    message(Error() & ' - ' & FileError())
       AddOErr('open(websqlfile)')
       Err = TRUE
    .
    IF NOT Err
       IF SendWebSQL('select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where status = ''Skickad''')
          Err = TRUE
          AddOErr('Select from weborder')
       .
    .
 .

 IF NOT Err
    LOOP
       CLEAR(qwo1:record)
       NEXT(WebSQLFile)
       IF ErrorCode()
          IF ErrorCode() <> 33  !Record not availab le
             Err = TRUE
             AddOErr('NEXT(WebSQLFile')
          .
          BREAK
       .
       qwo1:wordernr = websqlfile.f1
       qwo1:KundNr = CLIP(websqlfile.f2)
       qwo1:Marke = CLIP(websqlfile.f3)
       qwo1:LagerNr = websqlfile.f4
       qwo1:AntalRader = websqlfile.f5
       qwo1:KreditSparr = websqlfile.f6
       ADD(qwo1)
    .
 .

 CLOSE(WebSQLFile)

 IF NOT Err AND RECORDS(qwo1)
    LOOP 
       Err = FALSE
       CLEAR(OR1:Record)
       GET(qwo1,1)
       IF ErrorCode() THEN BREAK.

       Logout(5,order1)
       IF ErrorCode()
          Err = TRUE
          AddOErr('Logout')
       .
       IF NOT Err
          OR1:wordernr = qwo1:wordernr
          KUN:Nummer = qwo1:kundnr
          OR1:Marke = qwo1:marke
          SavMarke = qwo1:marke
          OR1:LagerNr = qwo1:lagernr
          OR1:Datum = TODAY()

          AntalRader = qwo1:AntalRader
          GET(Kund,KUN:K_Nummer)
          IF ErrorCode()
             Err = TRUE
             AddOErr('Kunden saknas ' & qwo1:kundnr)
          ELSE
             DO KUN_2_OR1
             DO LasWebOrder2
             IF NOT Err
                IF CheckKreditVardig()
                   LOOP WHILE GetOrdFromOS()       !OR1:Status sätts av GetOrdFromOS
                      IF Get_Ordernr()
                         AddOErr('Get_ordernr')
                         Err = TRUE
                      ELSE
                         IF OR1:Status = 'Direkt'
                            IF SparaDirektBest(LEV:Nummer)
                               Err = TRUE
                               AddOErr('SparaDirektBest()')
                            ELSE
                               OR1:DirektLevNr = BE1:BestNr
                               OR1:Marke = CLIP(SavMarke) & ' ' & BE1:BEstNr
                               OR1:Status = 'Väntar'        !Både order och beställning blir flaggade som avvaktande för godk'nnande
                            .
                         ELSE
                            OR1:DirektLevNr = 0
                            OR1:Marke = SavMarke
                         .

                         IF NOT Err
                            IF Spara_Order2()
                               AddOErr('Spara order')
                               Err = TRUE
                            ELSE
                               AddQInfo('WebOrder ' & OR1:wordernr & ' sparad som ' & OR1:OrderNr,TRUE)
                            .
                         .
                      .
                   .

                   IF NOT Err
                      OPEN(WebSQLFile)
                      IF SendWebSQL('update weborder1 set status = ''Mottagen'', kreditsparr = 0, MottagenDatum = ''' & FORMAT(Today(),@D17) & ''' where wordernr = ' & OR1:wordernr)
                         AddOErr('update weborder1')
                         Err = TRUE
                      .
                      CLOSE(WebSQLFile)
                   .
                ELSE   !Kunden är inte kreditvärdig
                   Err = TRUE    !Signalera att vi inte sparar något
                   IF NOT qwo1:Kreditsparr      !Om den tidigare har varit kreditspärrad ska vi inte updatera och meddela på nytt
                      AddQInfo('Kund: ' & KUN:Nummer & ' stoppad pga kreditgräns',TRUE)
                      OPEN(WebSQLFile)
                      IF SendWebSQL('update weborder1 set kreditsparr=1 where wordernr = ' & OR1:wordernr)
                        Err = TRUE
                        AddOErr('update weborder')
                      .
                      CLOSE(WebSQLFile)
                   .
                .
             .
          .
       .
       IF NOT Err
          commit()
          IF ErrorCode()
             Err = TRUE
             AddOErr('Commit')
          .
       .
       IF Err
         ROLLBACK
       .
       DELETE(qwo1)        !Ta bort den och fortsätt med nästa
    .
 .


 AddQInfo('Sök efter ny WebOrder utförd',FALSE)

 relate:artikel.close()
 relate:lager.close()
 relate:kund.close()
 relate:order1.close()
 relate:order2.close()
 relate:orderhand.close()
 relate:fdordernr.close()
 relate:nettopri.close()
 relate:kunrab.close()
 relate:best1.close()
 relate:best2.close()
 relate:faktdat.close()
 relate:lev.close()
 relate:sxreg.close()





LasWebOrder2   ROUTINE
 DATA
FraktTillkommer BYTE
 CODE
 FraktTillkommer = 0
 FREE(os)
 OrderSumma = 0

 open(websqlfile)
 IF SendWebSQL('select artnr, antal from weborder2 where wordernr = ' & OR1:wordernr)
    AddOErr('Select from weborder2')
    Err = TRUE
 ELSE
    LOOP
       CLEAR(OS:Record)
       NEXT(WebSQLFile)
       IF ErrorCode()
          IF ErrorCode() = 33  !Record not availab le
             BREAK
          ELSE
             AddOerr('Next(WebSQLFile)')
             Err = TRUE
             BREAK
          .
       ELSE
          ART:Nummer =  CLIP(WebSQLFile.f1)
          GET(Artikel,ART:K_Nummer)
          IF ErrorCode()
             AddOerr('GET(artikel)')
             Err = TRUE
             BREAK
          .
          Art_2_OS()
          OS:Antal = WebSQLFile.f2
          OS:Antal = CheckArtAntal(OS:Antal)
          Set_Pris(OS:Antal)
          OS:Summa = ROUND(ROUND(OS:Pris,0.01) * OS:Antal * (1-OS:Rab/100),0.01)
          OrderSumma += OS:Summa
          ADD(OS)
       .
    .


 .
 close(websqlfile)








KUN_2_OR1   ROUTINE
  OR1:Kundnr = KUN:Nummer
  OR1:Namn = KUN:Namn
  OR1:Adr1 = KUN:Adr1
  OR1:Adr2 = KUN:Adr2
  OR1:Adr3 = KUN:Adr3
  OR1:LevAdr1 = KUN:LNamn
  OR1:LevAdr2 = KUN:LAdr2
  OR1:Levadr3 = KUN:LAdr3
  OR1:Saljare = Format(KUN:Saljare,@S30) & '/00'
  OR1:Referens = KUN:Ref
  OR1:KTid = KUN:KTid
  OR1:Bonus = KUN:Bonus
  OR1:Faktor = KUN:Faktor
  OR1:LevVillkor = KUN:LevVillkor
  OR1:Mottagarfrakt = KUN:Mottagarfrakt
  OR1:FraktKundNr = KUN:FraktKundNr
  OR1:Fraktbolag = KUN:Fraktbolag
  OR1:Fraktfrigrans = KUN:Fraktfrigrans
  OR1:Moms = 1
  IF KUN:Momsfri THEN OR1:Moms = 0.
  !OR1:VeckoLevDag = KUN:VeckoLevDag
  OR1:LinjeNr1 = KUN:LinjeNr1
  OR1:LinjeNr2 = KUN:LinjeNr2
  OR1:LinjeNr3 = KUN:LinjeNr3






GetOrdFromOS   PROCEDURE()
FraktTillkommer BYTE
cn          LONG
TempLevNr   LIKE(LEV:Nummer)

 CODE
 FraktTillkommer = 0

 TempLevNr = ''
 FREE(Ordreg)

 SORT(OS,ORD:LevNr)
 LOOP CN = 1 TO RECORDS(OS)      !Nu ska vi se om det finns någon leverantör med direktlevartikel
    GET(OS,cn)
    IF OS:DirektLev
       TempLevNr = OS:LevNr
       BREAK
    .
 .

 LEV:Nummer = TempLevNr          !Skicka med vilken leverantör det rör sig om i denna globala variabel

 cn = 1
 BestBehov = FALSE
 LOOP    !Nu loopar vi igenom och filtrerar ifall det fanns leverantören med direktlev, de rader vi hittar raderas
    GET(OS,cn)
    IF ErrorCode() THEN BREAK.
    IF TempLevNr AND OS:LevNr <> TempLevNr
       cn += 1                   !Vi lämnar kvar denna rad, och fortsätter framåt i kön
       CYCLE
    .
    IF (OS:Fraktvillkor = 1 AND KUN:Distrikt <> 1) OR OS:Fraktvillkor = 2           !(Fritt turbil AND Kunden utanför turbilsdistriktet) OR Fritt lev.lager
       FraktTillkommer = TRUE
    .
    IF NOT TempLevNr AND (LAG:ILager-LAG:IOrder-OS:Antal+LAG:Best < 0) !Vi måste beställa, men meddela bara om det inte är direktleverans
       BestBehov = TRUE
    .
    OS_2_ORD()
    ADD(Ordreg)
    DELETE(OS)       !När raden raderas, kommer nästa rad fram vid GET utan att cn behöver ökas
 .
 IF BestBehov
    SendAdmMessage('Vi behöver beställa grejer till Webborder!','Kund: ' & OR1:KundNr & ' Lev ' & ART:Lev)
 .



 IF FraktTillkommer
    CLEAR(ORD:Record)
    ORD:Nummer = '0000' !Frakt
    ORD:NAMN = 'FRAKT'
    ORD:LevNr = ''
    ORD:ENH = 'ST'
    ORD:Lagerplats = 'ZZ'
    ADD(Ordreg)
 .
 IF TempLevNr                 !Vi har en direktleverans
    OR1:Status = 'Direkt'
 ELSE
    OR1:Status = 'Sparad'
 .

 SORT(Ordreg,ORD:Lagerplats,ORD:Nummer)

 RETURN(Records(Ordreg))




SendWebSQL   PROCEDURE(SQLText)
 CODE
 RETURN(SendSQL(websqlfile,SQLText))











ART_2_OS    PROCEDURE
 CODE
   GetArtikelLager(ART:Nummer, OR1:LagerNr)
   OS:NUMMER = ART:NUMMER
   OS:NAMN = ART:NAMN
   OS:LevNr = ART:Lev
   OS:ENH = ART:ENHET
   OS:PRIS = ART:UTPRIS
   OS:NETTO = (ART:INPRIS * (1-ART:RAB/100) * (1+ART:INP_FraktProc/100)) + ART:INP_Frakt + ART:INP_Miljo
   OS:Konto = ART:Konto
   OS:LagerPlats = LAG:LagerPlats
   OS:FraktVillkor = art:Fraktvillkor
   OS:DirektLev = ART:Direktlev


OS_2_Ord PROCEDURE
 CODE
 ORD:Record :=: OS:Record


Set_Pris PROCEDURE (Antal)

Lagsta_Pris   REAL
Lagsta_Rab    REAL
Sav_Rab       REAL      !Håller reda på rabattsatsen vi fick från Set_Rab så den kan återanvändas även vid kampanjpriser
D_Pris        REAL
D_Antal       REAL
D_Rab         REAL
 CODE
    D_Antal = Antal         !Rutinen är från början skriven för att hantera D_Antal, därför sätter vi D_Antal med en gång
    DO Set_Rab
    Sav_Rab = D_Rab
    D_Pris = ART:Utpris
    Lagsta_Pris = D_Pris
    Lagsta_Rab = D_Rab
    IF GallerKampanj()
       IF ART:KampPris                !Sätt bara kampanjpris om det är angivet - annars ordinarie
          D_Pris = ART:KampPris
          D_Rab = 0
          DO SetLagsta
       .
    .

    IF ART:Staf_Antal1 AND D_Antal >= ART:Staf_Antal1          !Har vi stafflingspriser
       D_Pris = ART:Staf_Pris1
       D_Rab = Sav_Rab
       DO SetLagsta
                                                        !Kolla om vi har kampanjpriser
       IF GallerKampanj()
          IF ART:KampPrisStaf1
             D_Pris = ART:KampPrisStaf1
             D_Rab = 0
             DO SetLagsta
          ELSIF ART:KampPris
             D_Pris = ART:KampPris
             D_Rab = 0
             DO SetLagsta
          .
       .
    .
    IF ART:Staf_Antal2 AND D_Antal >= ART:Staf_Antal2
       D_Pris = ART:Staf_Pris2
       D_Rab = Sav_Rab
       DO SetLagsta
                                                       !Kolla om vi har kampanjpriser
       IF GallerKampanj()
          IF ART:KampPrisStaf2
             D_Pris = ART:KampPrisStaf2
             D_Rab = 0
             DO SetLagsta
          ELSIF ART:KampPris
             D_Pris = ART:KampPris
             D_Rab = 0
             DO SetLagsta
          .
       .
    .
    IF KUN:NettoLst
       DO SetNettoPris
       DO SetLagsta
    .

    D_Pris = Lagsta_Pris
    D_Rab = Lagsta_Rab
    OS:Pris = Lagsta_Pris
    OS:Rab = Lagsta_Rab

SetLagsta   ROUTINE
 IF D_Pris * (1-D_Rab/100) < Lagsta_Pris * (1-Lagsta_Rab/100)
    Lagsta_Pris = D_Pris
    Lagsta_Rab = D_Rab
 .

Set_Rab     ROUTINE
   D_RAB = 0
   IF NOT KUN:Nummer OR NOT ART:RabKod OR ART:RABKOD = 'NTO' THEN EXIT.   !Om ingen rabatt är angiven ska vi inte göra något

   KRA:KundNr = KUN:Nummer
   KRA:RabKod = ART:Rabkod
   KRA:Kod1 = ''
   GET(KunRab,KRA:K_NRKOD)
   IF NOT ErrorCode()
      IF KRA:Rab > D_Rab THEN D_Rab = KRA:Rab.
   .
   IF ART:Kod1
      KRA:KundNr = KUN:Nummer
      KRA:RabKod = ART:Rabkod
      KRA:Kod1 = ART:Kod1
      GET(KunRab,KRA:K_NRKOD)
      IF NOT ErrorCode()
         IF KRA:Rab > D_Rab THEN D_Rab = KRA:Rab.
      .
   .

   IF KUN:Basrab > D_Rab THEN D_Rab = KUN:Basrab.
                        !Sätt först Rabattgruppskoden och sedan basrabatten längst ned


SetNettoPris     ROUTINE
  NET:Lista = KUN:NettoLst
  NET:ArtNr = ART:Nummer
  GET(Nettopri,NET:K_LiArt)
  IF NOT ErrorCode()
     D_Pris = NET:Pris
     D_Rab = 0
!    IF NET:Valuta
!       VAL:Valuta = NET:Valuta
!       GET(Valuta,VAL:K_Valuta)
!       IF NOT ErrorCode()
!          D_Pris = ORD:Pris * VAL:Kurs
!       ELSE
!          IF Disp_Medd('Värde för valuta: ' & FORMAT(NET:Valuta,@S3) & ' saknas!','Lägg till i valutaregistret','och kontrollera priset',TRUE,FALSE,FALSE).
!       .
!    .
  .



GallerKampanj   PROCEDURE           !Tar reda på om kampanj gäller för aktuell kund
g1      SHORT
g2      SHORT
ret     BYTE

 CODE
 ret = FALSE
 IF Today() >= ART:KampFrDat AND Today() <= ART:KampTiDat
    IF ART:KampKundArtGrp = 0 AND ART:KampKundGrp = 0
       ret = TRUE        !Har vi inte angivit grupp gäller det för alla
    ELSE
       g1 = KUN:Elkund*KampBitElkund + KUN:VVSkund*KampBitVVSKund + KUN:VAKund*KampBitVAKund + KUN:Golvkund*KampBitGolvKund + KUN:Fastighetskund*KampBitFastighetskund
       g2 = KUN:Installator*KampBitInstallator + KUN:Butik*KampBitButik + KUN:Industri*KampBitIndustri + KUN:OEM*KampBitOEM + KUN:Grossist*KampBitGrossist
       IF BAND(ART:KampKundArtGrp,g1) AND BAND(ART:KampKundGrp,g2) THEN ret = TRUE.
    .
 .
 RETURN(ret)



Get_Ordernr     PROCEDURE                 !Hämta ett färskt ordernr, och öka
ChangedCN   BYTE(0)
RetErr      BYTE(0)
 CODE
  LOOP
     SET(fdordernr)
     WATCH(fdordernr)
     NEXT(fdordernr)

     OR1:Ordernr = FDO:Ordernr
     OR1:Dellev = 1
     FDO:Ordernr += 1
     PUT(fdordernr)
     IF ErrorCode()
        CASE ErrorCode()
        OF RecordChangedErr
           ChangedCN += 1
           IF ChangedCN >= 5
              RetErr = TRUE
              OR1:OrderNr = 0
              AddOErr('Skriva till FDORDERNR. Posten ändrad av annan användare')
           ELSE
              CYCLE
           .
        ELSE
           RetErr = TRUE
           OR1:OrderNr = 0
           AddOErr('Skriva till FDORDERNR')
        .
     .
     BREAK
  .
  RETURN(RetErr)




Spara_Order2    PROCEDURE()
cn      LONG
RetErr  BYTE

 CODE
     RetErr = FALSE
     IF OR1:OrderNr = 0
        AddOErr('Fel vid tilldelning av ordernr. Kan inte spara.')
        RetErr = TRUE
        RETURN(RetErr)
     .

     CN = 0
     LOOP
        CN += 1
        GET(ORDREG,CN)
        IF ERRORCODE() THEN BREAK.
        OR2:POS = CN
        OR2:PrisNr = 1
        OR2:OrderNr = OR1:OrderNr
        OR2:Dellev = OR1:Dellev
        OR2:ArtNr = ORD:Nummer
        OR2:Namn = ORD:Namn
        OR2:Best = ORD:Antal
        OR2:Lev = ORD:Antal
        OR2:Best = ORD:Antal
        OR2:Pris = ORD:Pris
        OR2:Rab = ORD:Rab
        OR2:Summa = ORD:Summa
        OR2:Konto = ORD:Konto
        OR2:Netto = ORD:Netto
        OR2:Enh = ORD:Enh
        OR2:LevNr = ORD:LevNr

        ADD(ORDER2)
        IF ERRORCODE()
           AddOErr('ADD(ORDER2) Spara order ORDER2 - Försök igen!')
           RetErr = TRUE
           BREAK
        .
        IF ORD:Nummer AND ORD:Nummer[1] <> '*' 
           GET(Artikel,0)
           ART:Nummer = CLIP(ORD:Nummer)
           IF Duplicate(ART:K_Nummer)        !Endast om posten finns
              IF SQLUpdateLager(ORD:Nummer,OR1:LagerNr,'iorder = iorder+ ' & ORD:Antal)
                 RetErr = TRUE
                 BREAK
              .
           .
        .
     .
     IF NOT RetErr
        SetNull(Or1:LastAv)         !Rensa ev. låst flagga
        SetNull(OR1:LastDatum)
        SetNull(OR1:LastTid)
        ADD(Order1)
        IF ErrorCOde() THEN
           RetErr = TRUE
           AddOErr('Add(Order1)')
        .
        IF ORH:Add(OR1:OrderNr, '00', 'Skapad')
           RetErr = TRUE
        .
     .
     RETURN(RetErr)








SparaDirektBest   PROCEDURE(LevNr)
RetErr   BYTE(0)
cn       long
 CODE
    CLEAR(BE1:Record)
    Get_BestNr
    IF BE1:BestNr = 0
       RetErr = TRUE
    .
    LEV:Nummer = LevNR
    GET(Lev,LEV:K_Nummer)
    IF ErrorCode()
       AddOErr('Leverantör saknas ' & LevNr)
       RetErr = TRUE
    .
    IF Not RetErr
       BE1:Levnr = LEV:Nummer
       BE1:LevNamn = LEV:Namn
       BE1:Er_Ref = LEV:Ref
       IF LEV:LevVillkor1
          BE1:LevVillkor1 = LEV:LevVillkor1
          BE1:LevVillkor2 = LEV:LevVillkor2
          BE1:LevVillkor3 = LEV:LevVillkor3
       ELSE
          BE1:LevVillkor1 = FUP:Best_Text1
          BE1:LevVillkor2 = FUP:Best_Text2
          BE1:LevVillkor3 = FUP:Best_Text3
       .
       BE1:BestEjPris = LEV:BestEjPris
       BE1:Mottagarfrakt = LEV:Mottagarfrakt
       BE1:Fraktfritt = LEV:Fraktfritt
       BE1:Var_Ref = 'Automatisk beställning'
       BE1:LevAdr0 = OR1:LevAdr1
       BE1:LevAdr1 = OR1:LevAdr2
       BE1:LevAdr2 = OR1:LevAdr3
       BE1:Marke = OR1:Marke
       !BE1:Leverans = FORMAT(OR1:LevDat,@D17)
       BE1:Datum = OR1:Datum
       BE1:LagerNr =     OR1:LagerNr
       BE1:AutoBestalld = FALSE     !Avser leverans mellan filialer

       BE1:OrderNr = OR1:OrderNr
       BE1:Status = 'Väntar'               !Både order och beställning blir flaggade som avvaktande för godk'nnande
       BE1:Sakerhetskod = RANDOM(1000,9999)
       IF ValidateEPostAdress(LEV:Emailorder1)
          BE1:SkickaSom = 'epost'
       ELSE
          BE1:SkickaSom = ''
       .

       LOOP cn = 1 TO RECORDS(Ordreg)
          GET(Ordreg,cn)
          ART:Nummer = ORD:Nummer
          GET(Artikel,ART:K_Nummer)
          IF ErrorCode()
             CLEAR(ART:Record)
          .
          CLEAR(BE2:Record)
          BE2:BestNr = BE1:BestNr
          BE2:Rad = cn
          BE2:ArtNr = ORD:Nummer
          BE2:ArtNamn = ORD:Namn
          BE2:BArtNr = ART:BestNr
          BE2:Best = ORD:Antal
          BE2:Pris = ART:Inpris
          BE2:Rab =  ART:Rab
          BE2:Summa = BE2:Pris * (1-BE2:Rab/100) * BE2:Best
          BE2:Enh = ORD:Enh
          BE1:Summa += BE2:Summa
          ADD(Best2)
          IF ErrorCode()
             AddOErr('Fel: Add(Best2)')
             RetErr = TRUE
             BREAK
          .
          IF OR2:ArtNr AND SUB(OR2:ArtNr,1,1) <> '*'
             GET(Artikel,0)
             ART:Nummer = OR2:ArtNr
             IF Duplicate(ART:K_Nummer)
                IF SQLUpdateLager(OR2:ArtNr,BE1:LagerNr,'best = best+ ' & OR2:best)
                   AddOErr('update lager')
                   Err = TRUE
                   BREAK
                .
             .
          .
       .
       ADD(Best1)
       IF ErrorCode()
          RetErr = TRUE
       .
       IF BEH:Add(BE1:BestNr,'Skapad automatiskt')
          RetErr = TRUE
       .
    .



 RETURN(RetErr)














Get_Bestnr     PROCEDURE                 !Hämta ett färskt bestnr, och öka
 CODE
  LOOP
     SET(Faktdat)
     WATCH(Faktdat)
     NEXT(Faktdat)
     BE1:Bestnr = FDT:Bestnr
     FDT:Bestnr += 1
     PUT(Faktdat)
     IF ErrorCode()
        CASE ErrorCode()
        OF RecordChangedErr
           CYCLE
        ELSE
           AddOErr('PUT(FaktDat)')
           BE1:BestNr = 0
        .
     .
     BREAK
  .




AddOerr  PROCEDURE(str)
CODE
 AddQinfo('Fel: ' & str,TRUE)



CheckArtAntal  PROCEDURE(InAntal,TystLage)
! Kontrollerar antalet mot minsta försäljningsförpackning, och erbjuder användaren möjlighet att justera
! Artikeln måste vara laddad

RetAntal   REAL

 CODE
 RetAntal = InAntal
 IF ART:MinSaljPack > 0 AND InAntal
    IF InAntal % ART:MinSaljpack        !Vi har udda
       DO CalcAntal
       IF NOT TystLage
          IF Message('Antalet stämmer inte mot Minsta försäljningsförpackning.|Artikel: ' & ART:Nummer & ' ' & ART:Namn & '|Antal: '|
                     & InAntal & '|Minsta förpackning: ' & ART:MinSaljPack & '|Justerat antal: ' & RetAntal |
                     & '| |Vill du ändra antalet?','Fel antal',,'Ja|Nej',1) <> 1
             RetAntal = InAntal
          .
       .
    .
 .
 RETURN(RetAntal)

CalcAntal   ROUTINE
 IF ART:MinSaljPack >= 50
    IF InAntal >= 25
       RetAntal = (INT(InAntal / ART:MinSaljPack) + 1) * ART:MinSaljPack            !Avrunda till närmast större förpackning
    ELSE
       RetAntal = InAntal * ART:MinSaljPack
    .
 ELSE
    RetAntal = InAntal * ART:MinSaljPack
 .


GetArtikelLager PROCEDURE(ArtNr,LagerNr)
 CODE
 LAG:ArtNr = ArtNr
 LAG:LagerNr = LagerNr
 GET(Lager,LAG:K_ArtNrLagerNr)
 IF ErrorCode()
    CLEAR(LAG:Record)
 .







CheckKreditVardig   PROCEDURE           !Returnerar kreditkod

RetVarde BYTE 

 CODE
    RetVarde = TRUE
    LOOP            !Används bara som hållare för att enkelt ta sig ur strukturen med break
       GetSQLFile('select sum(tot) from kundres where kundnr = ''' & CLIP(KUN:Nummer) & ''' and (falldat < ''' & FORMAT(Today()-60,@D17) & ''' or tot < 0)')
       IF SQLFile.F1 > 1000
          RetVarde = FALSE
          BREAK
       .
       GetSQLFile('select sum(tot) from kundres where kundnr = ''' & CLIP(KUN:Nummer) & ''' and (falldat < ''' & FORMAT(Today()-30,@D17) & ''' or tot < 0)')
       IF SQLFile.F1 > KUN:KGransForfall30
          RetVarde = FALSE
          BREAK
       .
       IF KUN:KGrans > 0
          GetSQLFile('select sum(tot) from kundres where kundnr = ''' & CLIP(KUN:Nummer) & '''')
          IF SQLFile.f1 > KUN:KGrans - (OrderSumma*0.95)  !Vi minskar kreditgränsen med aktuell order, och tillåter 5% marginal
             RetVarde = FALSE
             BREAK
          .
       .
       BREAK            !Avsluta alltid loopen
    .


    RETURN(RetVarde)


OMIT('***CODE I DATABLOCKET***')

  
*/ 