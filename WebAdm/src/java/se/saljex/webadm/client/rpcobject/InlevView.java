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
public class InlevView implements IsSerializable, IsSQLTable{

	@Override
	public InlevView newInstance() { return new InlevView(); }

	public InlevView() {
	}

	@Override
	public  String getSQLTableName() {return "inlevview"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"id","rad"};
	}


	public Integer id;
	public Integer bestnr;
	public String levnr;
	public String levnamn;
	public String levadr0;
	public String levadr1;
	public String levadr2;
	public String levadr3;
	public String marke;
	public Date datum;
	public short lagernr;
	public int ordernr;

	public int rad;
	public String artnr;
	public String artnamn;
	public double antal;
	public double pris;
	public double rab;
	public String enh;
	public int stjid;

}
