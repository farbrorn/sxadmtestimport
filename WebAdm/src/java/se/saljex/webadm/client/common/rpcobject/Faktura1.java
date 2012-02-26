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
public class Faktura1 implements IsSerializable, IsSQLTable {

	@Override
	public Faktura1 newInstance() { return new Faktura1(); }
	
	public Faktura1() {
	}

	@Override
	public  String getSQLTableName() {return "faktura1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"faktnr"};
	}


	public Integer faktnr;
	public String kundnr;
	public String namn;
	public String adr1;
	public String adr2;
	public String adr3;
	public String levadr1;
	public String levadr2;
	public String levadr3;
	public Date datum;
	public String saljare;
	public String referens;
	public String marke;
	public short moms;
	public short ktid;
	public double ranta;
	public short bonus;
	public short faktor;
	public double t_Netto;
	public double t_Moms;
	public double t_Orut;
	public double t_Attbetala;
	public double t_Innetto;
	public short lagernr;
	public int direktlevnr;
	public double momsproc;
	public String inkassostatus;
	public Date inkassodatum;

}
