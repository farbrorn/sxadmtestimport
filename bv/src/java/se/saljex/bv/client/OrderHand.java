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
public class OrderHand implements IsSerializable{
	public OrderHand() {}

	public java.sql.Date datum=null;
	public String anvandare=null;
	public String handelse=null;
	public String transportor=null;
	public String fraktsedelnr=null;

}
