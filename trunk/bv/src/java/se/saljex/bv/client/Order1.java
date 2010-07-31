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

	public int ordernr;
	public String kundnr = null;
	public String Status=null;
}
