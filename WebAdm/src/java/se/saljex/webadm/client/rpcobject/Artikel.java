/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

/**
 *
 * @author Ulf
 */
public class Artikel implements IsSerializable, IsSQLTable{

	@Override
	public Artikel newInstance() { return new Artikel(); }
	
	public Artikel() {
	}

	public Artikel(Artikel a) {
		this.nummer = a.nummer;
		this.namn = a. namn;
		this.lev = a. lev;
		this.bestnr = a. bestnr;
		this.enhet = a. enhet;
		this.utpris = a.utpris;
		this.staf_Pris1 = a.staf_Pris1;
		this.staf_Pris2 = a.staf_Pris2;
		this.staf_Pris1_Dat = a. staf_Pris1_Dat;
		this.staf_Pris2_Dat = a. staf_Pris2_Dat;
		this.staf_Antal1 = a.staf_Antal1;
		this.staf_Antal2 = a.staf_Antal2;
		this.inpris = a.inpris;
		this.rab = a.rab;
		this.utrab = a.utrab;
		this.inp_Pris = a.inp_Pris;
		this.inp_Rab = a.inp_Rab;
		this.inp_Fraktproc = a.inp_Fraktproc;
		this.inp_Valuta = a. inp_Valuta;
		this.inp_Datum = a. inp_Datum;
		this.konto = a. konto;
		this.rabkod = a. rabkod;
		this.kod1 = a. kod1;
		this.prisdatum = a. prisdatum;
		this.inpdat = a. inpdat;
		this.refnr = a. refnr;
		this.vikt = a.vikt;
		this.volym = a.volym;
		this.struktnr = a. struktnr;
		this.forpack = a.forpack;
		this.kop_Pack = a.kop_Pack;
		this.kampfrdat = a. kampfrdat;
		this.kamptidat = a. kamptidat;
		this.kamppris = a.kamppris;
		this.kampprisstaf1 = a.kampprisstaf1;
		this.kampprisstaf2 = a.kampprisstaf2;
		this.inp_Miljo = a.inp_Miljo;
		this.inp_Frakt = a.inp_Frakt;
		this.anvisnr = a. anvisnr;
		this.staf_Pris1ny = a.staf_Pris1ny;
		this.staf_Pris2ny = a.staf_Pris2ny;
		this.inprisny = a.inprisny;
		this.inprisnydat = a. inprisnydat;
		this.inprisnyrab = a.inprisnyrab;
		this.utprisny = a.utprisny;
		this.utprisnydat = a. utprisnydat;
		this.rsk = a. rsk;
		this.enummer = a. enummer;
		this.kampkundartgrp = a.kampkundartgrp;
		this.kampkundgrp = a.kampkundgrp;
		this.cn8 = a. cn8;
		this.fraktvillkor = a.fraktvillkor;
		this.dagspris = a.dagspris;
		this.hindraexport = a.hindraexport;
		this.utgattdatum = a. utgattdatum;
		this.minsaljpack = a.minsaljpack;
		this.storpack = a.storpack;
		this.prisgiltighetstid = a.prisgiltighetstid;
		this.onskattb = a.onskattb;
		this.onskattbstaf1 = a.onskattbstaf1;
		this.onskattbstaf2 = a.onskattbstaf2;
		this.direktlev = a.direktlev;
		this.katnamn = a. katnamn;
		this.bildartnr = a. bildartnr;
		this.plockinstruktion = a. plockinstruktion;
		this.inp_Enh = a. inp_Enh;
	}

	@Override
	public  String getSQLTableName() {return "artikel"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"nummer"};
	}
	
	public String nummer;
	public String namn;
	public String lev;
	public String bestnr;
	public String enhet;
	public double utpris;
	public double staf_Pris1;
	public double staf_Pris2;
	public Date staf_Pris1_Dat;
	public Date staf_Pris2_Dat;
	public double staf_Antal1;
	public double staf_Antal2;
	public double inpris;
	public double rab;
	public double utrab;
	public double inp_Pris;
	public double inp_Rab;
	public double inp_Fraktproc;
	public String inp_Valuta;
	public Date inp_Datum;
	public String konto;
	public String rabkod;
	public String kod1;
	public Date prisdatum;
	public Date inpdat;
	public String refnr;
	public double vikt;
	public double volym;
	public String struktnr;
	public double forpack;
	public double kop_Pack;
	public Date kampfrdat;
	public Date kamptidat;
	public double kamppris;
	public double kampprisstaf1;
	public double kampprisstaf2;
	public double inp_Miljo;
	public double inp_Frakt;
	public String anvisnr;
	public double staf_Pris1ny;
	public double staf_Pris2ny;
	public double inprisny;
	public Date inprisnydat;
	public double inprisnyrab;
	public double utprisny;
	public Date utprisnydat;
	public String rsk;
	public String enummer;
	public short kampkundartgrp;
	public short kampkundgrp;
	public String cn8;
	public short fraktvillkor;
	public short dagspris;
	public short hindraexport;
	public Date utgattdatum;
	public double minsaljpack;
	public double storpack;
	public int prisgiltighetstid;
	public int onskattb;
	public int onskattbstaf1;
	public int onskattbstaf2;
	public short direktlev;
	public String katnamn;
	public String bildartnr;
	public String plockinstruktion;
	public String inp_Enh;
	//public java.math.BigDecimal inp_Enhetsfaktor;

}
