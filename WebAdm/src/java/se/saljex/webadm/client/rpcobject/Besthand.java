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
public class Besthand implements IsSerializable, IsSQLTable{

	@Override
	public Besthand newInstance() { return new Besthand(); }
	
	public Besthand() {
	}

	@Override
	public  String getSQLTableName() {return "besthand"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"bestnr","datum", "tid"};
	}
	
	public int bestnr;
	public Date datum;
	public Date tid;

	public String anvandare;
	public String handelse;
	public int inlevid;

}
