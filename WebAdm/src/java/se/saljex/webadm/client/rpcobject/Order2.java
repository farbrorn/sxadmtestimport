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
public class Order2 implements IsSerializable, IsSQLTable{

	@Override
	public Order2 newInstance() { return new Order2(); }
	
	public Order2() {
	}

	@Override
	public  String getSQLTableName() {return "order2"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr","pos"};
	}

	public int ordernr;
	public short pos;

	public short prisnr;
	public short dellev;
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
	public Date utskrivendatum;
	public Date utskriventid;
	public int stjid;

}
