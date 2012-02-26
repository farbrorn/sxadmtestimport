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
public class Kundkontakt implements IsSerializable, IsSQLTable{

	@Override
	public Kundkontakt newInstance() { return new Kundkontakt(); }
	
	public Kundkontakt() {
	}

	@Override
	public  String getSQLTableName() {return "kundkontakt"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"kontaktid"};
	}
	
	public Integer kontaktid;
	
	public String kundnr;
	public String namn;
	public String tel;
	public String mobil;
	public String fax;
	public String adr1;
	public String adr2;
	public String adr3;
	public String epost;
	public Short ekonomi;
	public Short info;

}
