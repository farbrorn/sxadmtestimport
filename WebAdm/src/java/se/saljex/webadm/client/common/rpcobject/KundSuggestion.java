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
public class KundSuggestion implements IsSerializable, IsSQLTable{
	@Override
	public KundSuggestion newInstance() { return new KundSuggestion(); }
	
	public KundSuggestion() {
	}

	@Override
	public  String getSQLTableName() {return "kund"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"nummer"};
	}
	
	public String nummer;
	public String namn;
	
}
