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
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import se.saljex.bv.client.GWTService;
import se.saljex.bv.client.Order1;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.OverforBVOrderResponse;
import se.saljex.bv.client.ServerErrorException;
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
	//@javax.annotation.Resource(mappedName = "sxadm")

	//Vet inte varför, men Resource injecton funkar int. Den returnerar för det mesta (men inte alltid) null
	private DataSource sxadm;


	public GWTServiceImpl() {
	}

	private void injectJNDI() {
	System.out.print("*!!!!!!!!!!!***************** injectJNDI");

			try {
		InitialContext ic = new InitialContext();
		 sxadm =
			 (DataSource) ic.lookup("sxadmkundbv");
		 sxServerMainBean = (SxServerMainLocal) ic.lookup("java:comp/env/ejb/SxServerMainBean");
		} catch (NamingException ex){
		throw new IllegalStateException(ex);
		}
	}

    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }

	 public Order1List getOrder1ListForOverforing() throws ServerErrorException {
	//	 injectJNDI();
		 Order1List order1List = new Order1List();
		 Connection con=null;

		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement(
				"select ordernr, status, namn from order1 "+
				" where status=status "+
				" order by ordernr"
			);
			//stm.setString(1, sxSession.getKundnr());
			ResultSet rs = stm.executeQuery();
			Order1 order1;

			while (rs.next()) {
				order1 = new Order1();
				order1.ordernr = rs.getInt(1);
				order1.Status = rs.getString(2);
				order1.kundnr = rs.getString(3);
				order1List.orderLista.add(order1);
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return order1List;
	 }

	 public OverforBVOrderResponse overforBVOrder(int bvOrdernr, short lagernr)  {
		 OverforBVOrderResponse resp= new OverforBVOrderResponse();
		 try {
			 if (sxServerMainBean==null) System.out.print("*************fel");
			 //Integer r =
					  sxServerMainBean.overforBVOrder("1", bvOrdernr, "BV", "00", lagernr);
			 	//		resp.sxOrdernr = r;

			resp.bvOrdernr = bvOrdernr;
		 } catch (se.saljex.sxserver.SXEntityNotFoundException e) {
			 resp.error = "Fel: " + e.getMessage();
		 }
		 return resp;
	 }

}
