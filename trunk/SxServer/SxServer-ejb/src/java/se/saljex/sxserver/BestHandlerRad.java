/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxserver.tables.TableBest2PK;
import se.saljex.sxserver.tables.TableBest2;
import java.util.Date;

/**
 *
 * @author ulf
 */
public class BestHandlerRad {
	public Integer bestnr = 0;
	public Short rad = 0;
	public String artnr = new String();
	public String artnamn = new String();
	public String bartnr = new String();
	public double best = 0.0;
	public double lev = 0.0;
	public double pris = 0.0;
	public double rab = 0.0;
	public double summa = 0.0;
	public String enh = new String();
	public int stjid = 0;
	public Date bekrdat = null;
	public double inpMiljo = 0.0;
	public double inpFrakt = 0.0;
	public double inpFraktproc = 0.0;
	
	public short artDirektlev = 0;		// Signalerar om det är direkleverans från leverantör på denna artikel, behöver inte initieras 
	public short artFraktvillkor = 0;	// Anger fraktvillkoret på artikeln, behöver inte initieras
	public String artLagerplats = null;
	public double artIorder = 0;
	public double artBest = 0;
	public double artIlager = 0;
		
	public void setAll(TableBest2 o) {
		bestnr = o.getTableBest2PK().getBestnr();
		rad = o.getTableBest2PK().getRad();
		artnr = o.getArtnr();
		artnamn = o.getArtnamn();
		bartnr = o.getBartnr();
		best = o.getBest();
		lev = o.getBest();
		pris = o.getPris();
		rab = o.getRab();
		summa = o.getSumma();
		enh = o.getEnh();
		bekrdat = o.getBekrdat();
		inpMiljo = o.getInpMiljo();
		inpFrakt = o.getInpFrakt();
		inpFraktproc = o.getInpFraktproc();
		stjid = o.getStjid();
	}
	
	//Returnera raden som ett TabelOrder2 objekt.
	public TableBest2 getBest2() {
		TableBest2 o = new TableBest2();

		o.setTableBest2PK(new TableBest2PK(bestnr,rad));
		o.setArtnr(artnr);
		o.setArtnamn(artnamn);
		o.setBartnr(bartnr);
		o.setBest(best);
		o.setPris(pris);
		o.setRab(rab);
		o.setSumma(summa);
		o.setEnh(enh);
		o.setBekrdat(bekrdat);
		o.setInpMiljo(inpMiljo);
		o.setInpFrakt(inpFrakt);
		o.setInpFraktproc(inpFraktproc);
		o.setStjid(stjid);
		
		return o;
	}
}
