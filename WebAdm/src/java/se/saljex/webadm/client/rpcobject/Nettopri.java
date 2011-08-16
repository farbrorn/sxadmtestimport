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
public class Nettopri implements IsSerializable, IsSQLTable{

	@Override
	public Nettopri newInstance() { return new Nettopri(); }
	
	public Nettopri() {
	}

	@Override
	public  String getSQLTableName() {return "nettopri"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"lista", "artnr"};
	}
	
	public String lista;
	public String artnr;

	public double pris;
	public String valuta;
	public Date datum;

}
