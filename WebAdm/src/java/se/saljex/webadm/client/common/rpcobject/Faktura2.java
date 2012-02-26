/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

/**
 *
 * @author Ulf
 */
public class Faktura2 implements IsSerializable, IsSQLTable{

	@Override
	public Faktura2 newInstance() { return new Faktura2(); }
	
	public Faktura2() {
	}

	@Override
	public  String getSQLTableName() {return "faktura2"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"faktnr", "pos"};
	}
	
	public int faktnr;
	public short pos;

	public short prisnr;
	public String artnr;
	public double rab;
	public double lev;
	public String text;
	public double pris;
	public double summa;
	public String konto;
	public double netto;
	public String enh;
	public String namn;
	public int bon_Nr;
	public int ordernr;
	public int rantafakturanr;
	public Date rantafalldatum;
	public Date rantabetaldatum;
	public double rantabetalbelopp;
	public double rantaproc;
	public int stjid;

}
