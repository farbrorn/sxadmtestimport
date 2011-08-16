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
public class Ranta implements IsSerializable, IsSQLTable{

	@Override
	public Ranta newInstance() { return new Ranta(); }
	
	public Ranta() {
	}

	@Override
	public  String getSQLTableName() {return "ranta"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"kundnr","faktnr","betdat"};
	}
	
	public String kundnr;
	public int faktnr;
	public Date betdat;

	public Date falldat;
	public double tot;
	public double ranta;
	public int dagar;
	public double rantaproc;

}
