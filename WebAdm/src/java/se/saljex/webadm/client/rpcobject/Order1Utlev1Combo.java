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

	//Detta är bara en placeholder för gemnsamma data till order1 och utlev1. Denna klass är egentligen abstract, men det verkar fungera dåligt med GWT rpc

public class Order1Utlev1Combo implements IsSerializable, IsSQLTable{

	public Order1Utlev1Combo() {
	}

	@Override
	public Order1Utlev1Combo newInstance() { return null; }

	@Override
	public  String getSQLTableName() {return null; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return null;
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
	public Date tid;
	public short veckolevdag;
	public short annanlevadress;
	public String ordermeddelande;
	public int wordernr;
	public String linjenr1 = "";
	public String linjenr2 = "";
	public String linjenr3 = "";
	public int kundordernr;
	public short forskatt;
	public short forskattbetald;
	public String betalsatt;

}
