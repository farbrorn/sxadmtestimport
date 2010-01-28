/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class OrderHeader implements IsSerializable{
	public OrderHeader() {}
	public int ordernr;
	public String marke=null;
	public java.sql.Date datum = null;
	public int lagernr;
	public String status=null;
	public String levadr1=null;
	public String levadr2=null;
	public String levadr3=null;
	public String referens=null;
	public int direktlevnr;
	public boolean isDeletable=false;

}
