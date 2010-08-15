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
import se.saljex.bv.client.ArtikelList;
import se.saljex.bv.client.BetaljournalList;
import se.saljex.bv.client.BokordList;
import se.saljex.bv.client.Faktura1List;
import se.saljex.bv.client.FakturajournalList;
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
	@WebMethod(operationName = "getSxOrder1ListFromFaktnr")
	 public Order1List getSxOrder1ListFromFaktnr(@WebParam(name = "sxFaktnr") int sxFaktnr) throws ServerErrorException  {
		 return serviceImpl.getSxOrder1ListFromFaktnr(sxFaktnr);
	 }
	@WebMethod(operationName = "getSxFaktura1List")
	 public Faktura1List getSxFaktura1List() throws ServerErrorException  {
		 return serviceImpl.getSxFaktura1List();
	 }

	// Betaljournal för angiven bokföringsperiod, endast betalningar som bv har gjort
	@WebMethod(operationName = "getSxBetaljournalList")
	public BetaljournalList getSxBetaljournalList(@WebParam(name = "bokforingsar")
	short bokforingsar, @WebParam(name = "bokforingsmanad")
	short bokforingsmanad) throws ServerErrorException {
		 return serviceImpl.getSxBetaljournalList(bokforingsar, bokforingsmanad);
	}
	// Fakturajournal för angiven bokföringsperiod, endast fakturor som bv har fått
	@WebMethod(operationName = "getSxFakturajurnalList")
	public FakturajournalList getSxFakturajurnalList(@WebParam(name = "bokforingsar")
	short bokforingsar, @WebParam(name = "bokforingsmanad")
	short bokforingsmanad) throws ServerErrorException {
		 return serviceImpl.getSxFakturajurnalList(bokforingsar, bokforingsmanad);
	}

	// Betaljournal för angiven bokföringsperiod, alla betalningar i bv
	@WebMethod(operationName = "getBvBetaljournalList")
	public BetaljournalList getBvBetaljournalList(@WebParam(name = "bokforingsar")
	short bokforingsar, @WebParam(name = "bokforingsmanad")
	short bokforingsmanad) throws ServerErrorException {
		 return serviceImpl.getBvBetaljournalList(bokforingsar, bokforingsmanad);
	}
	// Fakturajournal för angiven bokföringsperiod, alla fakturor i bv
	@WebMethod(operationName = "getBvFakturajurnalList")
	public FakturajournalList getBvFakturajurnalList(@WebParam(name = "bokforingsar")
	short bokforingsar, @WebParam(name = "bokforingsmanad")
	short bokforingsmanad) throws ServerErrorException {
		 return serviceImpl.getBvFakturajurnalList(bokforingsar, bokforingsmanad);
	}

	// Bokföringsorder för angiven bokföringsperiod i bv avseende kundfakturor och kundbetalningar
	@WebMethod(operationName = "getBvBokordList")
	public BokordList getBvBokordList(@WebParam(name = "bokforingsar")
	short bokforingsar, @WebParam(name = "bokforingsmanad")
	short bokforingsmanad) throws ServerErrorException {
		 return serviceImpl.getBvBokordList(bokforingsar, bokforingsmanad);
	}


	//Artikellista för sx. 
	@WebMethod(operationName = "getSxArtikelList")
	public ArtikelList getSxArtikelList(@WebParam(name = "query")
	String query, @WebParam(name = "offset")
	int offset, @WebParam(name = "limit")
	int limit) throws ServerErrorException {
		return serviceImpl.getSxArtikelList(query, offset, limit);
	}

	//Artikellista för bv
	@WebMethod(operationName = "getBvArtikelList")
	public ArtikelList getBvArtikelList(@WebParam(name = "query")
	String query, @WebParam(name = "offset")
	int offset, @WebParam(name = "limit")
	int limit) throws ServerErrorException {
		return serviceImpl.getBvArtikelList(query, offset, limit);
	}
	


/*
	@WebMethod(operationName = "overforBVOrder")
	public OverforBVOrderResp overforBVOrder(@WebParam(name = "bvOrdernr")int bvOrdernr, @WebParam(name = "sxLagernr") short sxLagernr,  @WebParam(name = "callerId") Integer callerId)  {
		return serviceImpl.overforBVOrder(bvOrdernr, sxLagernr, callerId);
	}
*/

}
