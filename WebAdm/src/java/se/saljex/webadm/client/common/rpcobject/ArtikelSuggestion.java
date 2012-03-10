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
public class ArtikelSuggestion implements IsSerializable, IsSQLTable{

	@Override
	public ArtikelSuggestion newInstance() { return new ArtikelSuggestion(); }
	
	public ArtikelSuggestion() {
	}

	@Override
	public  String getSQLTableName() {return "artikel"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"nummer"};
	}
	
	public String nummer;
	public String namn;

}
