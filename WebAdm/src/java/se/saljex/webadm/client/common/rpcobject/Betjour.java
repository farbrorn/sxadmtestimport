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
public class Betjour implements IsSerializable, IsSQLTable{

	@Override
	public Betjour newInstance() { return new Betjour(); }
	
	public Betjour() {
	}

	@Override
	public  String getSQLTableName() {return "betjour"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"faktnr","betsatt","tallopnr","taldatum"};
	}
	
	  public int faktnr;
	  public char betsatt;
	  public int tallopnr;
	  public Date taldatum;


	  public Short rantfakt;
	  public String kundnr;
	  public String namn;
	  public double bet;
	  public Date betdat;
	  public double bonsumma;
	  public short ar;
	  public short man;
	  public Short pantsatt;
	  public Short betsattkonto;
	  public String inkassostatus;
	  public String felbettyp;
	  public Date felbetavbokaddatum;

}
