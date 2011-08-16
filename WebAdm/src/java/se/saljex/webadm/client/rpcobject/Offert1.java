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
public class Offert1 implements IsSerializable, IsSQLTable {

	@Override
	public Offert1 newInstance() { return new Offert1(); }
	
	public Offert1() {
	}

	@Override
	public  String getSQLTableName() {return "offert1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"offertnr"};
	}

	  public Integer offertnr;

	  public String namn;
	  public String adr1;
	  public String adr2;
	  public String adr3;
	  public String levadr1;
	  public String levadr2;
	  public String levadr3;
	  public String saljare;
	  public String referens;
	  public String kundnr;
	  public String marke;
	  public Date datum;
	  public short moms;
	  public String status;
	  public short ktid;
	  public short bonus;
	  public short faktor;
	  public Date levdat;
	  public String levvillkor;
	  public short mottagarfrakt;
	  public String fraktkundnr;
	  public String fraktbolag;
	  public double fraktfrigrans;
	  public short skrivejpris;
	  public short lagernr;
	  public short annanlevadress;
	  public String ordermeddelande;


}
