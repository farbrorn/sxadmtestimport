/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

public class NotLoggedInException extends java.lang.Exception{
	public NotLoggedInException() {
		super("Inte inloggad");
	}
	public NotLoggedInException (String s) {
		super(s);
	}

}