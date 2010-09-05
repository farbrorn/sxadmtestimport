/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author ulf
 */
public interface GWTServiceAsync {
    public void myMethod(String s, AsyncCallback<String> callback);
 	 public void getBvOrder1List(int filter, AsyncCallback<Order1List> callback);
 	 public void overforBVOrder(int bvOrdernr, short lagernr, Integer callerId, AsyncCallback<OverforBVOrderResp> callback);
	 public void getBvOrderLookup(int bvOrdernr, AsyncCallback<OrderLookupResp> callback) ;
	public void logIn(String anvandare, String losen, AsyncCallback<String> callback);
	public void logOut(AsyncCallback<String> callback);
	public void skapaBvForskattsbetalning(int ordernr, double belopp, String anvandare, char betalSatt, java.util.Date betalDatum, int talongLopnr, AsyncCallback<Integer> callback);

}
