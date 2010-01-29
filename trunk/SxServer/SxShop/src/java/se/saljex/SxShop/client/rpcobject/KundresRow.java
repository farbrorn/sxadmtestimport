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
public class KundresRow implements IsSerializable{
	public KundresRow() {}
	public int faktnr;
	public String kundnr=null;
	public double tot;
	public java.sql.Date datum=null;
	public java.sql.Date falldat=null;
	public java.sql.Date pdat1=null;
	public java.sql.Date pdat2=null;
	public java.sql.Date pdat3=null;
	public java.sql.Date inkassodatum=null;
}
