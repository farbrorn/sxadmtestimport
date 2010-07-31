/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author ulf
 */
@RemoteServiceRelativePath("gwtservice")
public interface GWTService extends RemoteService {
    public String myMethod(String s);
 	 public Order1List getOrder1ListForOverforing()  throws ServerErrorException;
 	 public OverforBVOrderResponse overforBVOrder(int bvOrdernr, short lagernr);
}
