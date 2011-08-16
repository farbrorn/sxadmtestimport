/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class Levstat implements IsSerializable, IsSQLTable{

	@Override
	public Levstat newInstance() { return new Levstat(); }
	
	public Levstat() {
	}

	@Override
	public  String getSQLTableName() {return "levstat"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"levnr", "ar", "man"};
	}
	

	public String levnr;
	public short ar;
	public short man;

	public double ftot;
	public double ftbidrag;
	public double tot;

}
