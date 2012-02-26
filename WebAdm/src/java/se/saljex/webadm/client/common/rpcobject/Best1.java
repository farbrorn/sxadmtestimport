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
public class Best1 implements IsSerializable, IsSQLTable{

	@Override
	public Best1 newInstance() { return new Best1(); }
	
	public Best1() {
	}

	@Override
	public  String getSQLTableName() {return "best1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"bestnr"};
	}
	
	public Integer bestnr;
	public String levnr;
	public String levnamn;
	public String levadr1;
	public String levadr2;
	public String levadr3;
	public String var_Ref;
	public String er_Ref;
	public String leverans;
	public String marke;
	public Date datum;
	public double summa;
	public Date bekrdat;
	public double fraktfritt;
	public short mottagarfrakt;
	public String levvillkor1;
	public String levvillkor2;
	public String levvillkor3;
	public short bestejpris;
	public short lagernr;
	public String levadr0;
	public int ordernr;
	public short autobestalld;
	public String skickasom;
	public String status;
	public String meddelande;
	public int sakerhetskod;
	public int antalfelinloggningar;
	public short sxservsandforsok;
	public Date pamindat;
	public int antalpamin;
	public Date sanddat;

}
