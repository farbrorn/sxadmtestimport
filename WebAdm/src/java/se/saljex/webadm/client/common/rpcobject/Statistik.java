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
public class Statistik implements IsSerializable, IsSQLTable{

	@Override
	public Statistik newInstance() { return new Statistik(); }
	
	public Statistik() {
	}

	@Override
	public  String getSQLTableName() {return "statistik"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ar","man"};
	}
	
	public short ar;
	public short man;

	public double fak_Netto;
	public double fak_Moms;
	public double fak_Attbetala;
	public double fak_Innetto;
	public short fak_Antal;

}
