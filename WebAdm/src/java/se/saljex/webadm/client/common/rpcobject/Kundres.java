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
public class Kundres implements IsSerializable, IsSQLTable{

	@Override
	public Kundres newInstance() { return new Kundres(); }
	
	public Kundres() {
	}

	@Override
	public  String getSQLTableName() {return "kundres"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"faktnr"};
	}

	  public Integer faktnr;

	  public String kundnr;
	  public String namn;
	  public double tot;
	  public double netto;
	  public Date datum;
	  public Date falldat;
	  public Date pdat1;
	  public Date pdat2;
	  public Date pdat3;
	  public short faktor;
	  public Date faktordat;
	  public short bonus;
	  public double medelmomsproc;
	  public Date inkassodatum;
	  public int antalpaminnelser;
	  public String inkassostatus;
	  public short pantsatt;

}
