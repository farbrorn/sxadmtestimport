/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class Lev implements IsSerializable, IsSQLTable{

	@Override
	public Lev newInstance() { return new Lev(); }
	
	public Lev() {
	}

	@Override
	public  String getSQLTableName() {return "lev"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"nummer"};
	}
	
	public String nummer;
	
	public String namn;
	public String adr1;
	public String adr2;
	public String adr3;
	public String tel;
	public String biltel;
	public String fax;
	public String hemsida;
	public String email;
	public String inkopare;
	public String ref;
	public String refadr1;
	public String refadr2;
	public String refadr3;
	public String reftel;
	public String refbiltel;
	public String reffax;
	public String refhemsida;
	public String refemail;
	public short ktid;
	public double fraktfritt;
	public short mottagarfrakt;
	public String levvillkor1;
	public String levvillkor2;
	public String levvillkor3;
	public String levbestmedd1;
	public String levbestmedd2;
	public String levbestmedd3;
	public short bestejpris;
	public String valuta;
	public double tot;
	public double obet;
	public String post;
	public String bank;
	public String knummer;
	public double frakt;
	public String ant1;
	public String ant2;
	public String ant3;
	public String ant;
	public String landskod;
	public String emailorder1;
	public String emailorder2;

}
