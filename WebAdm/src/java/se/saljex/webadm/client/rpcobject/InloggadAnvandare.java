/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class InloggadAnvandare implements IsSerializable{

	public InloggadAnvandare() {}

	public String anvandare=null;
	public String anvandareKort=null;
	public int defaultLagernr=0;
	public ArrayList<String> arrBehorighet = null;

}
