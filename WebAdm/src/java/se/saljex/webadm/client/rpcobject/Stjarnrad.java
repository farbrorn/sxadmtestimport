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
public class Stjarnrad implements IsSerializable, IsSQLTable{

	@Override
	public Stjarnrad newInstance() { return new Stjarnrad(); }
	
	public Stjarnrad() {
	}

	@Override
	public  String getSQLTableName() {return "stjarnrad"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"stjid"};
	}
	
	  public Integer stjid;
	  
	  public String artnr;
	  public String levnr;
	  public String kundnr;
	  public String namn;
	  public short lagernr;
	  public double antal;
	  public String enh;
	  public double inpris;
	  public double inrab;
	  public Date regdatum;
	  public short autobestall;
	  public Date bestdat;
	  public int bestnr;
	  public String anvandare;
	  public short finnsilager;
	  public Date inkomdatum;
	  public int fakturanr;

}
