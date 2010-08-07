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
public class OverforBVOrderResp implements IsSerializable {
	public OverforBVOrderResp() {}

	public Integer sxOrdernr=null;
	public Integer bvOrdernr=null;

	public boolean overfordOK=false;

	public String error=null;	//sätts enbart om det var ett fel, annars null

	public Integer callerId=null; //Id sänt från anroparen för att skilja ut svar från flera asynkrona anrop
}
