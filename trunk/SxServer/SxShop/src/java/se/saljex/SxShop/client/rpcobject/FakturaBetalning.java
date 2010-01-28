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
public class FakturaBetalning implements IsSerializable {
	public int faktnr;
	public double summa;
	public java.sql.Date betDat = null;
	public String betSatt=null;
	public FakturaBetalning() {}

}
