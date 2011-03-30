/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class KundInfo implements IsSerializable{

	public KundInfo() {
	}

	public String kundnr;
	public KundOrderList orderRader = new KundOrderList();
	public KundFaktura1List fakturor = new KundFaktura1List();

}
