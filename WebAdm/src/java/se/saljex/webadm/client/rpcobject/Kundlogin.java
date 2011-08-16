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
public class Kundlogin implements IsSerializable, IsSQLTable{

	@Override
	public Kundlogin newInstance() { return new Kundlogin(); }
	
	public Kundlogin() {
	}

	@Override
	public  String getSQLTableName() {return "kundlogin"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"kontaktid"};
	}
	
	public Integer kontaktid;
	
	public String loginnamn;
	public String loginlosen;

}
