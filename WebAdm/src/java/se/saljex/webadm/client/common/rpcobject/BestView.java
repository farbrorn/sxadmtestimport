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
public class BestView implements IsSerializable, IsSQLTable{

	@Override
	public BestView newInstance() { return new BestView(); }

	public BestView() {
	}

	@Override
	public  String getSQLTableName() {return "bestview"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"bestnr","rad"};
	}


	public Integer bestnr;
	public String levnr;
	public String levnamn;
	public String levadr0;
	public String levadr1;
	public String levadr2;
	public String levadr3;
	public String var_ref;
	public String er_ref;
	public Date datum;
	public String leverans;
	public String marke;
	public double bekrdat;
	public short lagernr;
	public short ordernr;

	public int rad;
	public String artnr;
	public String artnamn;
	public String bartnr;
	public String enh;
	public double best;
	public double pris;
	public double rab;
	public int stjid;

}
