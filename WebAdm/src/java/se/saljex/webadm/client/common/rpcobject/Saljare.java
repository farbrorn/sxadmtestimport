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
public class Saljare implements IsSerializable, IsSQLTable{

	@Override
	public Saljare newInstance() { return new Saljare(); }
	
	public Saljare() {
	}

	@Override
	public  String getSQLTableName() {return "saljare"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"forkortning"};
	}
	
	  public String forkortning;

	  public String namn;
	  public String adr1;
	  public String adr2;
	  public String adr3;
	  public String tel;
	  public String mobil;
	  public String fax;
	  public double totalt;
	  public double tbidrag;
	  public String ant1;
	  public String ant2;
	  public String ant3;
	  public short behorighet;
	  public String losen;
	  public short lagernr;
	  public Date losengiltigtdatum;

}
