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
public class OffertHeader implements IsSerializable{
	public OffertHeader() {}
	public int offertnr;
	public java.sql.Date datum=null;
	public String marke;
}
