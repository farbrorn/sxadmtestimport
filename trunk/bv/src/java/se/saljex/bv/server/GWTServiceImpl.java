/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.sql.DataSource;
import se.saljex.bv.client.GWTService;
import se.saljex.bv.client.NotLoggedInException;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.OrderLookupResp;
import se.saljex.bv.client.OverforBVOrderResp;
import se.saljex.bv.client.ServerErrorException;
import se.saljex.sxlibrary.SxServerMainRemote;
import se.saljex.sxlibrary.WebSupport;

/**
 *
 * @author ulf
 */
@RunAs("admin")
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {



	@EJB
	private SxServerMainRemote sxServerMainBean;
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




	public OrderLookupResp getBvOrderLookup(int bvOrdernr) throws NotLoggedInException {
		ensureLoggedIn();
		return serviceImpl.getBvOrderLookup(bvOrdernr);
	}


	 public Order1List getBvOrder1List(int filter) throws ServerErrorException, NotLoggedInException {
		 ensureLoggedIn();
		 return serviceImpl.getBvOrder1List(filter);
	 }

	public OverforBVOrderResp overforBVOrder(int bvOrdernr, short lagernr, Integer callerId) throws NotLoggedInException {
		ensureLoggedIn();
		return serviceImpl.overforBVOrder(bvOrdernr, lagernr, callerId);
	}

	private void ensureLoggedIn() throws NotLoggedInException {
		if (WebSupport.getSXSession(getThreadLocalRequest().getSession()).getInloggad()) System.out.print("Inloggad"); else System.out.print("inte Inloggad");
		if (WebSupport.getSXSession(getThreadLocalRequest().getSession()).isSuperuser()) System.out.print("superuser"); else System.out.print("inte superuser");
		if (!WebSupport.getSXSession(getThreadLocalRequest().getSession()).checkIntraBehorighetIntraWebApp()) throw new NotLoggedInException();
	}

	public String logIn(String anvandare, String losen) throws NotLoggedInException, ServerErrorException {
		Connection sxCon=null;
		try {
			sxCon = sxDataSource.getConnection();
			if (WebSupport.loginIntra(sxCon, getThreadLocalRequest().getSession(), anvandare, losen)) return "Inloggad";
			else throw new NotLoggedInException("Felaktig användare/Lösen");
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new ServerErrorException());
		} finally {
			try { sxCon.close(); } catch(Exception e) {}
		}
	}

	public String logOut() {
		Connection sxCon=null;
		try {
			sxCon = sxDataSource.getConnection();
			WebSupport.logOutIntra(sxCon, getThreadLocalRequest().getSession());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { sxCon.close(); } catch(Exception e) {}
			//Säkerställ utloggning med brutalt våld
			if (WebSupport.getSXSession(getThreadLocalRequest().getSession()).getInloggad()) getThreadLocalRequest().getSession().invalidate();
		}
		return "Utloggad";
	}

	public int skapaBvForskattsbetalning(int ordernr, double belopp, String anvandare, char betalSatt, java.util.Date betalDatum, int talongLopnr) throws NotLoggedInException, ServerErrorException {
		throw new ServerErrorException("Ge tusan i den knappen!");
		//ensureLoggedIn();
		//return serviceImpl.skapaBvForskattsbetalning(ordernr, belopp, anvandare, betalSatt, betalDatum, talongLopnr);

	}


}
