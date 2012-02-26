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
public class Kunstat implements IsSerializable, IsSQLTable{

	@Override
	public Kunstat newInstance() { return new Kunstat(); }
	
	public Kunstat() {
	}

	@Override
	public  String getSQLTableName() {return "kunstat"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"kundnr", "ar", "man"};
	}
	
	public String kundnr;
	public short ar;
	public short man;

	public double tot;
	public double tbidrag;
	public int btid;
	public int fakturor;
	public short betal;
	public double totbet;
	public double ranta;

}
