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
	 public final static int FILTER_ALLA=0;
	 public final static int FILTER_FOROVERFORING=1;
	 public final static int FILTER_OVERFORDA=2;
	 public final static int FILTER_AVVAKTANDE=3;
 	 public final static int FILTER_FORSKOTT=4;

    public String myMethod(String s);
 	 public Order1List getOrder1List(int filter)  throws ServerErrorException;
 	 public OverforBVOrderResponse overforBVOrder(int bvOrdernr, short lagernr, Integer callerId);
	 public OrderLookupResponse getOrderLookup(int bvOrdernr);
}
