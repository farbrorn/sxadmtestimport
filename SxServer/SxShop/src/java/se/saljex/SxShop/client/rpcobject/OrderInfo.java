/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class OrderInfo implements IsSerializable{
	public OrderInfo() {}
	public OrderHeader header = new OrderHeader();
	public ArrayList<OrderRow> rows = new ArrayList();

}
