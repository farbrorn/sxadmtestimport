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
	@WebMethod(operationName = "test")
	public String test() {
		//TODO write your implementation code here:
		return "Testresult";
	}
	/**
	 * Web service operation
	 */
	
	@WebMethod(operationName = "getBvOrder1List")
	public Order1List getBvOrder1List(@WebParam(name = "filter")
	int filter) throws ServerErrorException {
		  return serviceImpl.getBvOrder1List(filter);
	}

	@WebMethod(operationName = "getBvOrder1ListByLevAdr")
	public Order1List getBvOrder1ListByLevAdr(@WebParam(name = "levAdr")
	String levAdr) throws ServerErrorException {
		  return serviceImpl.getBvOrder1ListByLevAdr(levAdr);
	}


	@WebMethod(operationName = "getBvOrderLookup")
	 public OrderLookupResp getBvOrderLookup(@WebParam(name = "bvOrdernr") int bvOrdernr)  {
		 return serviceImpl.getBvOrderLookup(bvOrdernr);
	 }


/*
	@WebMethod(operationName = "overforBVOrder")
	public OverforBVOrderResp overforBVOrder(@WebParam(name = "bvOrdernr")int bvOrdernr, @WebParam(name = "sxLagernr") short sxLagernr,  @WebParam(name = "callerId") Integer callerId)  {
		return serviceImpl.overforBVOrder(bvOrdernr, sxLagernr, callerId);
	}
*/

}
