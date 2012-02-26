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
public class Orderhand implements IsSerializable, IsSQLTable{

	@Override
	public Orderhand newInstance() { return new Orderhand(); }
	
	public Orderhand() {
	}

	@Override
	public  String getSQLTableName() {return "orderhand"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr", "datum", "tid"};
	}
	
	public int ordernr;
	public Date datum;
	public Date tid;

	public String anvandare;
	public String handelse;
	public String transportor;
	public String fraktsedelnr;
	public int nyordernr;
	public int antalkolli;
	public String kollislag;
	public int totalvikt;
	public Date serverdatum;

}
