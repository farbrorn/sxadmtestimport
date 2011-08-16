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
public class Bonus implements IsSerializable, IsSQLTable{

	@Override
	public Bonus newInstance() { return new Bonus(); }
	
	public Bonus() {
	}

	@Override
	public  String getSQLTableName() {return "bonus"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"faktura", "id"};
	}
	
	public int faktura;
	public short id;

	public String kund;
	public Date datum;
	public double bonus;

}
