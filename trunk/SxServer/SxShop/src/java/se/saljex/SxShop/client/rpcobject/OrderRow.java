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
public class OrderRow implements IsSerializable{
	public OrderRow() {}
	public int ordernr;
	public int pos;
	public String artnr;
	public String namn;
	public String text;
	public double antal;
	public String enh;
	public double pris;
	public double rab;
	public double summa;
	public double tillgangligtAntal;
	public boolean isChangeable=false;



}
