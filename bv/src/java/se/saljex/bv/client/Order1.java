/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ulf
 */
public class Order1 implements IsSerializable, Serializable{
	public Order1() {}

	public short lagernr;					//Vilket lager ordern avser. Lager 0 är default
	public int ordernr;						// ordernr
	public int dellev;						//Anger vilken delleverans ordern avser. Varje delleverans får ett nytt ordernr men behåller räknaren
	public int kundordernr;					//Anger kundens ordernummer. Avsett för länkning mellan SX och BV
	public Integer faktnr=null;				// Fakturanummer om ordern är fakturerad, och därmed fysiskt, i databasen, är flyttad från ordertabellen till utleveranstabellen
	public String kundnr = null;			//Kundnummer
	public String namn = null;				//Kundens namn
	public String adr1=null;				//Fakturaadress
	public String adr2=null;
	public String adr3=null;
	public String levadr1=null;			//Leveransadress om den avviker från faktureaadressen
	public String levadr2=null;
	public String levadr3=null;
	public String status=null;				//Orderstatus
	public String marke=null;				// Orderns märke
	public Date datum=null;					//Orer skapad datum
	public double summaInkMoms;			//Ordersumma inkl. moms
	public boolean isOverforbar=false;	//Är ordern tillgänglig för överföring till sx?
	public boolean isFakturerad=false;	//Är ordern fakturerad.
	public boolean forskatt=false;		//Är det en order som ska betalas förskott
	public boolean forskattBetalt=false;//Är förskottsordern betald
	public String betalsatt=null;			//Betalsätt, valfritt, kan .t.ex vara 'Faktura' eller 'Betalkort'


}
