/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class WelcomeData implements IsSerializable{

	public WelcomeData() {}

	public String forsaljningChartURLFilial;
	public String forsaljningChartURLTotalt;

}
