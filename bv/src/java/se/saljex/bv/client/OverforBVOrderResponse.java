/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class OverforBVOrderResponse implements IsSerializable {
	public OverforBVOrderResponse() {}

	public Integer sxOrdernr=null;
	public Integer bvOrdernr=null;

	public String error=null;	//sätts enbart om det var ett fel, annars null
	
}
