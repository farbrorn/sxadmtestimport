/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.sql.DataSource;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.OrderLookupResp;
import se.saljex.bv.client.OverforBVOrderResp;
import se.saljex.sxserver.SxServerMainLocal;
import se.saljex.bv.client.ServerErrorException;

/**
 *
 * @author ulf
 */
@WebService()
public class BvWebService {
	@EJB
	private SxServerMainLocal sxServerMainBean;
	@javax.annotation.Resource(name = "sxadmkundbv")
	private DataSource bvDataSource;
	@javax.annotation.Resource(name = "sxadm")
	private DataSource sxDataSource;

	private  ServiceImpl serviceImpl;

	@PostConstruct private void init() {
		serviceImpl = new ServiceImpl(sxServerMainBean, sxDataSource, bvDataSource);
	}
	/**
	 * Web service operation
	 */

	@WebMethod(operationName = "getOrder1List")
	public Order1List getOrder1List(@WebParam(name = "filter")
	int filter) throws ServerErrorException {
		  return serviceImpl.getOrder1List(filter);
	}


	@WebMethod(operationName = "getOrderLookup")
	 public OrderLookupResp getOrderLookup(@WebParam(name = "bvOrdernr") int bvOrdernr)  {
		 return serviceImpl.getOrderLookup(bvOrdernr);
	 }


	@WebMethod(operationName = "overforBVOrder")
	public OverforBVOrderResp overforBVOrder(@WebParam(name = "bvOrdernr")int bvOrdernr, @WebParam(name = "sxLagernr") short sxLagernr,  @WebParam(name = "callerId") Integer callerId)  {
		return serviceImpl.overforBVOrder(bvOrdernr, sxLagernr, callerId);
	}

	
}
