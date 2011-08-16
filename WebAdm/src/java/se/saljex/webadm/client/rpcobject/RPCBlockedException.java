/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

/**
 *
 * @author Ulf
 */
public class RPCBlockedException extends java.lang.Exception {

	public RPCBlockedException() {
		super("Servern är upptagen. Var vänlig försök senare.");
	}
	public RPCBlockedException(String s) {
		super(s);
	}


}
