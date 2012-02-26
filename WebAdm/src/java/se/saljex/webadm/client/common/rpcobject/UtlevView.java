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
public class UtlevView implements IsSerializable, IsSQLTable{

	@Override
	public UtlevView newInstance() { return new UtlevView(); }

	public UtlevView() {
	}

	@Override
	public  String getSQLTableName() {return "utlevview"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr","pos"};
	}

	public Integer ordernr;
	public String kundnr;
	public String kundnamn;
	public short dellev;
	public String marke;
	public Date datum;
	public String status;
	public Date levdat;
	public short lagernr;
	public int direktlevnr;
	public int kundordernr;

	public Integer faktnr;
	public short pos;
	public String artnr;
	public String artnamn;
	public String levnr;
	public double rab;
	public double lev;
	public String text;
	public double pris;
	public double summa;
	public double netto;
	public String enh;
	public int stjid;


}
