/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import java.util.Calendar;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import se.saljex.sxserver.tables.TableBetjour;
import se.saljex.sxserver.tables.TableBokord;
import se.saljex.sxserver.tables.TableBonus;
import se.saljex.sxserver.tables.TableBonusPK;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableKundres;
import se.saljex.sxserver.tables.TableKunstat;
import se.saljex.sxserver.tables.TableKunstatPK;
import se.saljex.sxserver.tables.TableRanta;
import se.saljex.sxserver.tables.TableRantaPK;

/**
 *
 * @author ulf
 */
public class KundresHandler {
	public static final String FELBET_FAKTORING = "Faktoring";
	public static final String FELBET_INKASSO = "Inkasso";
	public static final String INKASSO_STATUS_OVERFORD = "Överförd";

	public static void bokaBetalning(EntityManager em, int faktnr, double belopp, java.util.Date betdat, boolean bokaInkassoSomFelbetald, char betalSatt, int talongLopnr, java.util.Date talongDatum, boolean sparaRanta, boolean betalningAvserPantsattFaktura) {
		Calendar calendar = Calendar.getInstance();

		TableKund kund;
		TableKundres kundres;
		TableBetjour betjour;
		TableBokord bokord;
		TableFuppg fup;
		TableKunstat kunstat;
		TableKunstatPK kunstatPK;
		TableBonus bonus;
		TableBonusPK bonusPK;
		TableRanta ranta;
		TableRantaPK rantaPK;

		fup = (TableFuppg)em.createNamedQuery("TableFuppg.findAll").getSingleResult();

		String felbet=null;
		kundres = em.find(TableKundres.class, faktnr);
		if (kundres==null) throw new EntityNotFoundException("Faktura " + faktnr + " saknas i kundreskontran");

		kund = em.find(TableKund.class, kundres.getKundnr());

		//Sätt felbetalningsstatus
		if (kundres.getFaktor()!=0) {
			ServerUtil.log("Från KundresHandler.bokaBetaning: Faktura " + kundres.getFaktnr() + " är lämnad till faktoring och registreras som felbetald");
			felbet = FELBET_FAKTORING;
		} else if (kundres.getInkassodatum() != null && INKASSO_STATUS_OVERFORD.equals(kundres.getInkassostatus()) && bokaInkassoSomFelbetald) {
			ServerUtil.log("Från KundresHandler.bokaBetaning: Faktura " + kundres.getFaktnr() + " är lämnad till inkasso och registreras som felbetald");
			felbet = FELBET_FAKTORING;
		}

		if (talongDatum==null) { talongDatum = betdat; }

		calendar.setTime(betdat);
		short betdatAr = (short)calendar.get(Calendar.YEAR);
		short betdatMan = (short)calendar.get(Calendar.MONTH);


		betjour = new TableBetjour(faktnr, betalSatt, talongLopnr, talongDatum);
		betjour.setKundnr(kundres.getKundnr());
		betjour.setNamn(kundres.getNamn());
		betjour.setBet(belopp);
		betjour.setBetdat(betdat);
		betjour.setAr(betdatAr);
		betjour.setMan(betdatMan);
		betjour.setBonsumma(0.0);
		betjour.setPantsatt(kundres.getPantsatt());
		betjour.setBetsattkonto(betalningAvserPantsattFaktura ? (short)1 : (short)0);
		em.persist(betjour);

		String konto;

		konto = fup.getKundfk();
		if (SXUtil.isEmpty(konto)) konto = "1210";
		bokord = new TableBokord(konto, faktnr, "B", betjour.getBetdat());
		bokord.setKundnr(kundres.getKundnr());
		bokord.setNamn(kundres.getNamn());
		bokord.setSumma(-belopp);
		bokord.setAr(betdatAr);
		bokord.setMan(betdatMan);
		em.persist(bokord);

		if (betalSatt=='K') konto = fup.getKassa();
		else if(betalSatt == 'B') konto = fup.getBank();
		else if(betalSatt == 'P') konto = fup.getPost();
		if (SXUtil.isEmpty(konto)) konto = "1000";
		bokord = new TableBokord(konto, faktnr, "B", betjour.getBetdat());
		bokord.setKundnr(kundres.getKundnr());
		bokord.setNamn(kundres.getNamn());
		bokord.setSumma(belopp);
		bokord.setAr(betdatAr);
		bokord.setMan(betdatMan);
		em.persist(bokord);

		Double tot =  kundres.getTot();
		if (tot.equals(betjour.getBet())) { //Är hela summan betald?
			em.remove(kundres);
		} else {
			kundres.setTot(kundres.getTot() - betjour.getBet());
		}

		final int betalTid = (int)((betjour.getBetdat().getTime() - kundres.getFalldat().getTime()) / (24 * 60 * 60 * 1000));


		//SPARA BONUS OM BETALD I TID, OCH DET INTE ŽR EN KREDITFAKTURA
		//OCH VI SKA SPARA BONUS

		java.util.Date fallDatumMedKrangeldagar;
		calendar.setTime(kundres.getFalldat());
		calendar.add(Calendar.DAY_OF_MONTH, fup.getKrangel());
		fallDatumMedKrangeldagar = calendar.getTime();

		if (!fallDatumMedKrangeldagar.before(betjour.getBetdat()) && ((Double)betjour.getBet()).compareTo(0.0) > 0 && kundres.getBonus() != (short)0) {
			bonus = new TableBonus(kundres.getFaktnr(), (short)0);
			bonus.setKund(kundres.getKundnr());
			bonus.setDatum(betjour.getBetdat());
			calendar.setTime(kundres.getDatum());
			calendar.add(Calendar.DAY_OF_MONTH, fup.getBonTid()-fup.getKrangel()); //Innehåller nu datumet som är godkänt för hög bonus
			if (calendar.getTime().after(betjour.getBetdat())) {
				bonus.setBonus((betjour.getBet() * fup.getBonusproc2()/100) / (kundres.getMedelmomsproc()/100+1));
			} else {
				bonus.setBonus((betjour.getBet() * fup.getBonusproc1()/100) / (kundres.getMedelmomsproc()/100+1));
			}

			TableBonus tempBonus=null;
			bonusPK = bonus.getTableBonusPK();
			for (short cn=0 ; cn <200 ; cn++) {
				bonusPK.setId(cn);
				tempBonus = em.find(TableBonus.class, bonusPK);
				if (tempBonus==null) break;
			}
			if (tempBonus!=null) throw new EntityExistsException("Kan inte spara bonus till betalning av faktura " + kundres.getFaktnr() + " bonus id är för högt");

			em.persist(bonus);

			betjour.setBonsumma(bonus.getBonus());

		}

		Double ranteBelopp = SXUtil.getRoundedDecimal(betalTid * betjour.getBet() * fup.getDroj() /365/100);

		if (fallDatumMedKrangeldagar.before(betjour.getBetdat())
				  && kundres.getFaktor() == 0
				  && (ranteBelopp.compareTo(fup.getMinranta()) > 0)
				  && sparaRanta
				  ) {
			ranta = new TableRanta(kundres.getKundnr(), kundres.getFaktnr(), betjour.getBetdat());
			ranta.setFalldat(kundres.getFalldat());
			ranta.setTot(betjour.getBet());
			ranta.setDagar(betalTid);
			ranta.setRanta(ranteBelopp);
			ranta.setRantaproc(fup.getDroj());
			em.persist(ranta);

		}

		betjour.setFelbettyp(felbet);
		betjour.setInkassostatus(kundres.getInkassostatus());

//		if (kund!=null) {
//			kund.setObet(kund.getObet() - betjour.getBet());
//			kund.setBtid(kund.getBtid() + betalTid);
//		}

		//   !**** Spara Kundstatistik
		kunstatPK = new TableKunstatPK(kundres.getKundnr(), betdatAr, betdatMan);
		kunstat = em.find(TableKunstat.class, kunstatPK);
		if (kunstat == null) {
			kunstat = new TableKunstat(kunstatPK);
			em.persist(kunstat);
		}
		kunstat.setTotbet(kunstat.getTotbet() + betjour.getBet());
		kunstat.setBtid(kunstat.getBtid() + betalTid);
		kunstat.setRanta(kunstat.getRanta() + ranteBelopp);

	}




}
