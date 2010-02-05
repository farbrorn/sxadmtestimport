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
public class StatInkopRow implements IsSerializable{
	public StatInkopRow() {}
	public int ar;
	public double[] summa = new double[12]; //Summor per månad
	public String chartURL=null;
}
