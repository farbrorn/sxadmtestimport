/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.sql.DataSource;
import se.saljex.bv.client.Order1List;
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
//	@EJB
//	private LocalWebSupportLocal localWebSupportBean;
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

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "testString")
	public String testString(@WebParam(name = "id")
	int testId) {
		if(testId==1)
			return "testar svenska tecken -å-ä-ö-Å-Ä-Ö";
		else if (testId==2)
			return "testar högerhake - > ";
		else if (testId==3)
			return "testar vänsterhake - < ";
		else if (testId==4)
			return "testar en h1-tag - <h1> ";
		else if (testId==5)
			return "testar en h1-tag - <h1> ";
		else
			return "Testa med olika filter fran 1 och uppat och fa olika teststringar";	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "testStringArray")
	public ArrayList<String> testStringArray() {
		ArrayList<String> a = new ArrayList();
		a.add("Rad 1");
		a.add("Rad 2");
		a.add("Rad 3");

		return a;
	}

	
}
