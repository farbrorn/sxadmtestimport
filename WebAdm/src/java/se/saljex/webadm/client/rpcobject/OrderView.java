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
public class OrderView implements IsSerializable, IsSQLTable{

	@Override
	public OrderView newInstance() { return new OrderView(); }

	public OrderView() {
	}

	@Override
	public  String getSQLTableName() {return "orderview"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr","pos"};
	}

	public String kundnr;
	public String kundnamn;

	public Integer ordernr;
	public short dellev;
	public String marke;
	public Date datum;
	public String status;
	public Date orderlevdat;
	public short lagernr;
	public int direktlevnr;
	public int kundordernr;

	public short pos;

	public String artnr;
	public String artnamn;
	public String levnr;
	public double best;
	public double rab;
	public double lev;
	public String text;
	public double pris;
	public double summa;
	public double netto;
	public String enh;
	public int stjid;

}
