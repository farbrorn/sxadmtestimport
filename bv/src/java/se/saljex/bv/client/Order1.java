/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class Order1 implements IsSerializable{
	public Order1() {}

	public short lagernr;
	public int ordernr;
	public String kundnr = null;
	public String namn = null;
	public String status=null;
	public java.sql.Date datum=null;
	public double summaInkMoms;
	public boolean isOverforbar=false;	//Är ordern tillgänglig för överföring till sx?

}
