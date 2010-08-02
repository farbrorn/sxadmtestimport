/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class Order implements IsSerializable{
	public  Order() {}

	public Order1 order1=null;
	public ArrayList<Order2> order2List = null;
	public ArrayList<OrderHand> orderHandList = null;


}
