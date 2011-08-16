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
public class Kunrab implements IsSerializable, IsSQLTable{

	@Override
	public Kunrab newInstance() { return new Kunrab(); }
	
	public Kunrab() {
	}

	@Override
	public  String getSQLTableName() {return "kunrab"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"kundnr", "rabkod", "kod1"};
	}
	
	public String kundnr;
	public String rabkod;
	public String kod1;

	public double rab;
	public Date datum;

}
