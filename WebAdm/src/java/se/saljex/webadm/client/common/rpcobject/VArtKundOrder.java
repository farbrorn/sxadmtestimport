/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class VArtKundOrder implements IsSerializable, IsSQLTable{
	@Override
	public VArtKundOrder newInstance() { return new VArtKundOrder(); }

	@Override
	public  String getSQLTableName() {return "vartkundorder"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"artnr","kundnr", "lagernr"};
	}

	public VArtKundOrder() {
	}

	public String kundnr;
	public String artnr;
	public String artnamn;
	public String lev;
	public String bestnr;
	public String rsk;
	public String enummer;
	public String refnr;
	public String enhet;
	public double utpris;
	public double staf_pris1;
	public double staf_pris2;
	public double staf_antal1;
	public double staf_antal2;
	public String rabkod;
	public String kod1;
	public double inpris;
	public java.util.Date prisdatum;
//	public int prisgiltighetstid;
	public String konto;
	public String struktnr;
//	public double forpack;
//	public double kop_pack;
//	public java.util.Date kampfrdat;
//	public java.util.Date kamptidat;
//	public double kamppris;
//	public double kampprisstaf1;
//	public double kampprisstaf2;
//	public double inp_miljo;
//	public double inp_frakt;
//	public double inp_fraktproc;
//	public int kampkundartgrp;
//	public int kampkundgrp;
//	public short fraktvillkor;
//	public short dagspris;
//	public double minsaljpack;
	public String anvisnr;
//	public java.util.Date utgattdatum;
	public short lagernr;
	public double ilager;
	public double iorder;
	public double best;
	public String lagerplats;
//	public double basrab;
	public double gruppbasrab;
	public double undergrupprab;
	public String nettolst;
	public double nettopris;
	public String anvisning;

}
