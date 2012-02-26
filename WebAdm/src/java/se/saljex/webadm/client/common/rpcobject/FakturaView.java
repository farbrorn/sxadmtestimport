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
public class FakturaView implements IsSerializable, IsSQLTable{

	@Override
	public FakturaView newInstance() { return new FakturaView(); }

	public FakturaView() {
	}

	@Override
	public  String getSQLTableName() {return "fakturaview"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"faktnr","pos"};
	}

	public String kundnr;
	public String kundnamn;

	public Integer faktnr;
	public Date datum;
	public short lagernr;

	public short pos;

	public String artnr;
	public String artnamn;
	public double rab;
	public double lev;
	public String text;
	public double pris;
	public double summa;
	public double netto;
	public String enh;
	public int stjid;

}
