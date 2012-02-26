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

// Inutial data som hämtas när applikationen startas
public class InitialData implements IsSerializable{

	public InitialData() {}

	public InloggadAnvandare inloggadAnvandare=null;

	public String foretagNamn=null;
	

}
