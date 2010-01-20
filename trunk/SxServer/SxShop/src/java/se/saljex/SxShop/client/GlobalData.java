/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import se.saljex.SxShop.client.rpcobject.Anvandare;

/**
 *
 * @author ulf
 */
public class GlobalData {
	public SxShopRPCAsync service=null;
	public Anvandare anvandare=null;
	public boolean isLoggedIn() {
		return !anvandare.gastlogin;
	}

}
