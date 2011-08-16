/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

/**
 *
 * @author Ulf
 */
public class ErrorConvertingFromResultsetException extends java.lang.Exception{
	public ErrorConvertingFromResultsetException() {
		super("Fel vid konvertering av SQL ResultSet till class");
	}
	public ErrorConvertingFromResultsetException (String s) {
		super(s);
	}

}
