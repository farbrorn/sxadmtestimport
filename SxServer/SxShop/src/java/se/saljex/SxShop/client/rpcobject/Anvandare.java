/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class Anvandare implements IsSerializable {

	public boolean gastlogin=true;
	public String kundnamn=null;
	public String kontaktnamn=null;
	public String loginnamn=null;
	public String autoLoginId=null;	//id för automatisk inloggning
	public Anvandare() {}

}
