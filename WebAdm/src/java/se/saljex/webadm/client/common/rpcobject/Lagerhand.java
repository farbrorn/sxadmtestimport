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
public class Lagerhand implements IsSerializable, IsSQLTable{

	@Override
	public Lagerhand newInstance() { return new Lagerhand(); }
	
	public Lagerhand() {
	}

	@Override
	public  String getSQLTableName() {return "lagerhand"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"artnr","lagernr", "datum", "tid"};
	}
	
	public String artnr;
	public short lagernr;
	public Date datum;
	public Date tid;

	public String anvandare;
	public String handelse;
	public int ordernr;
	public int stjid;
	public double gammaltilager;
	public double nyttilager;
	public double forandring;
	public int bestnr;

}
