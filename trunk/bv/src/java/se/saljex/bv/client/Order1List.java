/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import se.saljex.bv.client.Order1;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class Order1List implements IsSerializable{
	public Order1List() {}

	public ArrayList<Order1> orderLista = new ArrayList();
}
