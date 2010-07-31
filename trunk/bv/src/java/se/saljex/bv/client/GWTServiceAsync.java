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
 	 public void getOrder1ListForOverforing(AsyncCallback<Order1List> callback);
 	 public void overforBVOrder(int bvOrdernr, short lagernr, AsyncCallback<OverforBVOrderResponse> callback);
}
