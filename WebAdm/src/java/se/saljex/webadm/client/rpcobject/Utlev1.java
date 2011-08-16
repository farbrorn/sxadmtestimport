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
public class Utlev1 implements IsSerializable, IsSQLTable{

	@Override
	public Utlev1 newInstance() { return new Utlev1(); }
	
	public Utlev1() {
	}

	@Override
	public  String getSQLTableName() {return "utlev1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr"};
	}
	
	public Integer ordernr;

	public Short dellev;
	public String namn;
	public String adr1;
	public String adr2;
	public String adr3;
	public String levadr1;
	public String levadr2;
	public String levadr3;
	public String saljare;
	public String referens;
	public String kundnr;
	public String marke;
	public Date datum;
	public short moms;
	public String status;
	public short ktid;
	public short bonus;
	public short faktor;
	public Date levdat;
	public String levvillkor;
	public short mottagarfrakt;
	public String fraktkundnr;
	public String fraktbolag;
	public double fraktfrigrans;
	public short lagernr;
	public short returorder;
	public int direktlevnr;
	public Date tid;
	public int faktnr;
	public short veckolevdag;
	public Short annanlevadress;
	public String ordermeddelande;
	public Integer wordernr;
	public String linjenr1;
	public String linjenr2;
	public String linjenr3;
	public int kundordernr;
	public short forskatt;
	public short forskattbetald;
	public String betalsatt;

}
