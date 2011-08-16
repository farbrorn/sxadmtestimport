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
public class Offert2 implements IsSerializable, IsSQLTable{

	@Override
	public Offert2 newInstance() { return new Offert2(); }
	
	public Offert2() {
	}

	@Override
	public  String getSQLTableName() {return "offert2"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"offertnr","pos"};
	}
	
	  public int offertnr;
	  public short pos;

	  public short prisnr;
	  public String artnr;
	  public String namn;
	  public String levnr;
	  public double best;
	  public double rab;
	  public double lev;
	  public String text;
	  public double pris;
	  public double summa;
	  public String konto;
	  public double netto;
	  public String enh;
	  public Date levdat;

}
