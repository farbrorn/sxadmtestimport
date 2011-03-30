/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class KundOrderList implements IsSerializable{
	public KundOrderList() {}

	public ArrayList<KundOrder> order = new ArrayList();

}
