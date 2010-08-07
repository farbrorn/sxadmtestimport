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
public class OrderLookupResp implements IsSerializable{
	public OrderLookupResp() {}

	public Order bvOrder;
	public ArrayList<Order> sxOrderList=null;

	public String errorMessage=null;	//sätts enbart om det var ett fel, annars null

}
