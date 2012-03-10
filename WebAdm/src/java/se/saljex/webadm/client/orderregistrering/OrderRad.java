/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.user.client.rpc.IsSerializable;
import se.saljex.webadm.client.common.rpcobject.VArtKundOrder;

/**
 *
 * @author Ulf
 */
public class OrderRad implements IsSerializable {

	public OrderRad() {
	}
	public OrderRad(String artnr, String namn) {
		this.artnr=artnr;
		this.namn=namn;
	}
	public OrderRad(OrderRad or) {
		this.artnr=or.artnr;
		this.namn=or.namn;
		this.antal = or.antal;
		this.enh = or.enh;
		this.pris=or.pris;
		this.rab = or.rab;
	}

	public String artnr = null;
	public String namn = null;
	public double antal = 0;
	public String enh = null;
	public double pris = 0;
	public double rab = 0;
	public double summa=0;
	
	public String konto = null;
	public String levnr = null;
	public double netto=0;
	public short stjAutobestall=0;
	public short stjFinnsILager = 0;
	public String textrad=null;
	
	
//	public VArtKundOrder vArtKundOrder = null;
}
