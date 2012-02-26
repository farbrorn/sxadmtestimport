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
public class Order1 implements IsSerializable, IsSQLTable{

	@Override
	public Order1 newInstance() { return new Order1(); }
	
	public Order1() {
	}

	@Override
	public  String getSQLTableName() {return "order1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr"};
	}

	public Integer ordernr;
	public short dellev;
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
	public int direktlevnr;
	public short returorder;
	public String lastav;
	public Date lastdatum;
	public Date lasttid;
	public Date tid;
	public short veckolevdag;
	public Date doljdatum;
	public short tillannanfilial;
	public short utlevbokad;
	public short annanlevadress;
	public String ordermeddelande;
	public Date tidigastfaktdatum;
	public int wordernr;
	public String linjenr1 = "";
	public String linjenr2 = "";
	public String linjenr3 = "";
	public int kundordernr;
	public short forskatt;
	public short forskattbetald;
	public String betalsatt;

}
