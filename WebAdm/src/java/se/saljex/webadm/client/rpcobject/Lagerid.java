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
public class Lagerid implements IsSerializable, IsSQLTable{

	@Override
	public Lagerid newInstance() { return new Lagerid(); }
	
	public Lagerid() {
	}

	@Override
	public  String getSQLTableName() {return "lagerid"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"lagernr"};
	}
	
	  public Short lagernr;

	  public String bnamn;
	  public String namn;
	  public String adr1;
	  public String adr2;
	  public String adr3;
	  public String levadr1;
	  public String levadr2;
	  public String levadr3;
	  public String tel;
	  public String fax;
	  public String email;
	  public int checkmeddelidletime;
	  public short skrivfoljesedel;
	  public double maxlagervarde;

}
