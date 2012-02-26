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
public class Rabkoder implements IsSerializable, IsSQLTable{

	@Override
	public Rabkoder newInstance() { return new Rabkoder(); }
	
	public Rabkoder() {
	}

	@Override
	public  String getSQLTableName() {return "rabkoder"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"rabkod","kod1"};
	}
	
	public String rabkod;
	public String kod1;

	public String beskrivning;

}
