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
public class Bonusbet implements IsSerializable, IsSQLTable{

	@Override
	public Bonusbet newInstance() { return new Bonusbet(); }
	
	public Bonusbet() {
	}

	@Override
	public  String getSQLTableName() {return "bonusbet"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"kund","faktura", "id"};
	}
	
	public String kund;
	public int faktura;
	public short id;

	public Date utdatum;
	public double bonus;
	public Integer utfaktura;

}
