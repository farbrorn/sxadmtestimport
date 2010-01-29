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
public class UtlevRow implements IsSerializable{
	public UtlevRow() {}
	public int ordernr;
	public String lavadr1=null;
	public String lavadr2=null;
	public String lavadr3=null;
	public String referens = null;
	public String marke=null;
	public java.sql.Date orderdatum=null;
	public int lagernr;
	public int faktnr;
	public java.sql.Date faktdatum;

}
