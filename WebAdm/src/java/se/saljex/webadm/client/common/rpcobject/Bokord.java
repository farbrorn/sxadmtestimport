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
public class Bokord implements IsSerializable, IsSQLTable{

	@Override
	public Bokord newInstance() { return new Bokord(); }
	
	public Bokord() {
	}

	@Override
	public  String getSQLTableName() {return "bokord"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"konto","faktnr","typ","datum"};
	}
	
	public String konto;
	public int faktnr;
	public String typ;
	public Date datum;


	public double summa;
	public String kundnr;
	public String namn;
	public short ar;
	public short man;

}
