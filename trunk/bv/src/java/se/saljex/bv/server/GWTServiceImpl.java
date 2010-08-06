/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import se.saljex.bv.client.GWTService;
import se.saljex.bv.client.Order;
import se.saljex.bv.client.Order1;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.Order2;
import se.saljex.bv.client.OrderHand;
import se.saljex.bv.client.OrderLookupResponse;
import se.saljex.bv.client.OverforBVOrderResponse;
import se.saljex.bv.client.ServerErrorException;
import se.saljex.sxserver.SXConstant;
import se.saljex.sxserver.SXEntityNotFoundException;
import se.saljex.sxserver.SxServerMainLocal;

/**
 *
 * @author ulf
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {



	@EJB
	private SxServerMainLocal sxServerMainBean;
//	@EJB
//	private LocalWebSupportLocal localWebSupportBean;
	@javax.annotation.Resource(name = "sxadmkundbv")
	private DataSource bvDataSource;
	@javax.annotation.Resource(name = "sxadm")
	private DataSource sxDataSource;

	private final static String BVKUNDNR="0855115290";

	private  ServiceImpl serviceImpl;

	@PostConstruct private void initServiceImpl() {
		serviceImpl = new ServiceImpl(sxServerMainBean, sxDataSource, bvDataSource);
	}



    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }




	 public OrderLookupResponse getOrderLookup(int bvOrdernr)  {
		 return serviceImpl.getOrderLookup(bvOrdernr);
	 }


	 public Order1List getOrder1List(int filter) throws ServerErrorException {
		 return serviceImpl.getOrder1List(filter);
	 }

	public OverforBVOrderResponse overforBVOrder(int bvOrdernr, short lagernr, Integer callerId)  {
		return serviceImpl.overforBVOrder(bvOrdernr, lagernr, callerId);
	}

}
