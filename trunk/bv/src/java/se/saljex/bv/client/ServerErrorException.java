/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;
/**
 *
 * @author ulf
 */
public class ServerErrorException extends java.lang.Exception{

	public ServerErrorException() {}
	public ServerErrorException (String s) {
		super(s);
	}
}
