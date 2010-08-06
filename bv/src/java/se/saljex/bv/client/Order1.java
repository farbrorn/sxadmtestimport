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
	public String kundnr = null;			//Kundnummer
	public String namn = null;				//Kundens namn
	public String status=null;				//Orderstatus
	public Date datum=null;					//Orer skapad datum
	public double summaInkMoms;			//Ordersumma inkl. moms
	public boolean isOverforbar=false;	//Är ordern tillgänglig för överföring till sx?

}
