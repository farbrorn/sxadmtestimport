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
