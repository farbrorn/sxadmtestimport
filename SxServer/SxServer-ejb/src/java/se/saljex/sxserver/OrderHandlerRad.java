/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxserver.tables.TableOrder2PK;
import se.saljex.sxserver.tables.TableOrder2;
import java.util.Date;
import se.saljex.sxserver.tables.TableBonus;
import se.saljex.sxserver.tables.TableOffert2;
import se.saljex.sxserver.tables.TableOffert2PK;
import se.saljex.sxserver.tables.TableRanta;

/**
 *
 * @author ulf
 */
public class OrderHandlerRad {
	public Integer ordernr = 0;
	public Integer offertnr = 0;
	public Short pos = 0;
	public Short prisnr = 0;
	public Short dellev = 0;
	public String artnr;
	public String namn;
	public String levnr; 
	public Double best = 0.0;
	public Double rab = 0.0;
	public Double lev = 0.0;
	public String text;
	public Double pris = 0.0;
	public Double summa = 0.0;
	public String konto;
	public Double netto = 0.0;
	public String enh;
	public Date levdat;
	public Date utskrivendatum;
	public Date utskriventid;
	public Integer stjid = 0;
	
	
	public Short stjAutobestall = 0;
	public Short stjFinnsILager = 0;
	

	public Short artDirektlev = null;		// Signalerar om det är direkleverans från leverantör på denna artikel, behöver inte initieras 
	public Short artFraktvillkor = null;	// Anger fraktvillkoret på artikeln, behöver inte initieras
	public String artLagerplats = null;
	public Double artIorder = null;
	public Double artBest = null;
	public Double artIlager = null;

	public TableBonus tableBonus = null;

	public TableRanta tableRanta = null;

	public OrderHandlerRad() {	}

	// Skapar en kopia på en befintlig rad
	public OrderHandlerRad(OrderHandlerRad radAttKopiera) {
		setAll(radAttKopiera.getOrder2());
	}

	public void setAll(TableOrder2 o) {
		ordernr = o.getTableOrder2PK().getOrdernr();
		pos = o.getTableOrder2PK().getPos();
		prisnr = o.getPrisnr();
		dellev = o.getDellev();
		artnr = o.getArtnr();
		namn = o.getNamn();
		levnr = o.getLevnr();
		best = o.getBest();
		rab = o.getRab();
		lev = o.getLev();
		text = o.getText();
		pris = o.getPris();
		summa = o.getSumma();
		konto = o.getKonto();
		netto = o.getNetto();
		enh = o.getEnh();
		levdat = o.getLevdat();
		utskrivendatum = o.getUtskrivendatum();
		utskriventid = o.getUtskriventid();
		stjid = o.getStjid();
	}
	public void setAll(TableOffert2 o) {
		offertnr = o.getTableOffert2PK().getOffertnr();
		pos = o.getTableOffert2PK().getPos();
		prisnr = o.getPrisnr();
		dellev = 0;
		artnr = o.getArtnr();
		namn = o.getNamn();
		levnr = o.getLevnr();
		best = o.getBest();
		rab = o.getRab();
		lev = o.getLev();
		text = o.getText();
		pris = o.getPris();
		summa = o.getSumma();
		konto = o.getKonto();
		netto = o.getNetto();
		enh = o.getEnh();
		levdat = o.getLevdat();
		utskrivendatum = null;
		utskriventid = null;
		stjid = null;
	}
	
	//Returnera raden som ett TabelOrder2 objekt.
	public TableOrder2 getOrder2() {
		TableOrder2 o = new TableOrder2();

		o.setTableOrder2PK(new TableOrder2PK(ordernr,pos));
		o.setPrisnr(prisnr);
		o.setDellev(dellev);
		o.setArtnr(artnr);
		o.setNamn(namn);
		o.setLevnr(levnr);
		o.setBest(best);
		o.setRab(rab);
		o.setLev(lev);
		o.setText(text);
		o.setPris(pris);
		o.setSumma(summa);
		o.setKonto(konto);
		o.setNetto(netto);
		o.setEnh(enh);
		o.setLevdat(levdat);
		o.setUtskrivendatum(utskrivendatum);
		o.setUtskriventid(utskriventid);
		o.setStjid(stjid);

		return o;
	}


	//Returnera raden som ett TabelOffert2 objekt.
	public TableOffert2 getOffert2() {
		TableOffert2 o = new TableOffert2();

		o.setTableOffert2PK(new TableOffert2PK(offertnr,pos));
		o.setPrisnr(prisnr);
		o.setArtnr(artnr);
		o.setNamn(namn);
		o.setLevnr(levnr);
		o.setBest(best);
		o.setRab(rab);
		o.setLev(lev);
		o.setText(text);
		o.setPris(pris);
		o.setSumma(summa);
		o.setKonto(konto);
		o.setNetto(netto);
		o.setEnh(enh);
		o.setLevdat(levdat);

		return o;
	}
}
