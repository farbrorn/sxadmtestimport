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
public class Best2 implements IsSerializable, IsSQLTable{

	@Override
	public Best2 newInstance() { return new Best2(); }
	
	public Best2() {
	}

	@Override
	public  String getSQLTableName() {return "best2"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"bestnr","rad"};
	}

	public int bestnr;
	public short rad;

	public String enh;
	public String artnr;
	public String artnamn;
	public String bartnr;
	public double best;
	public double pris;
	public double rab;
	public double summa;
	public String text;
	public Date bekrdat;
	public double inp_Miljo;
	public double inp_Frakt;
	public double inp_Fraktproc;
	public int stjid;

}
