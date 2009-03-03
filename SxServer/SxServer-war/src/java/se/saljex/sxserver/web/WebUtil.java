/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import se.saljex.sxserver.SXConstant;
import se.saljex.sxserver.SXUtil;

/**
 *
 * @author ulf
 */
public class WebUtil {
	public static SXSession getSXSession (HttpSession session) {
		SXSession s = (SXSession)session.getAttribute("sxsession");
		if (s == null) { 
			s = new SXSession(); 
			session.setAttribute("sxsession", s);
		}
		return s;
	}

	public static Connection getConnection() throws javax.naming.NamingException, SQLException {
			InitialContext ic = new InitialContext();
			return ((DataSource)ic.lookup("sxadm")).getConnection();
	}
	
	/* Tar en pdf-stream och skickar den till ett ServletResponse med korrekta header */
	public static void sendPdf(ByteArrayOutputStream pdfStream, ServletOutputStream outStream, HttpServletResponse response )	throws IOException		 {
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.setContentLength(pdfStream.size()); 
				pdfStream.writeTo(outStream);
				outStream.flush();		
	}

	public static java.sql.Connection getConnection(javax.sql.DataSource ds) throws javax.servlet.ServletException {
			try {
				return ds.getConnection();
			} catch (java.sql.SQLException se) {
				SXUtil.log(se.toString()); se.printStackTrace();
				throw new javax.servlet.ServletException("Fel vid kommunikation med databasen.");
			}

	}

	
	// Lägger till händelse i besthand
	public static void insertBesthand(Connection con, Integer bnr, String handelse) throws java.sql.SQLException {
		PreparedStatement s = con.prepareStatement("insert into besthand (bestnr, datum, tid, anvandare, handelse) values (?,?,?,?,?)");
		java.util.Date d = new java.util.Date();
		s.setInt(1, bnr);
		s.setString(4, SXUtil.getSXReg(con, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
		s.setString(5, handelse);
		int cn = 0;
		do {
			s.setDate(2, SXUtil.getSQLDate(d));
			s.setTime(3, SXUtil.getSQLTime(d));
			cn++;
			if (cn > 10){ throw new SQLException("Creates duplicate key in besthand"); }  // Avbryt med en Exception om vi har försökt för många gånger
			d = new java.util.Date(d.getTime()+1000);	// Öka med 1 s för att vara förbered ifall dubbel key skapas
		} while (s.executeUpdate() < 1);  // Loopa ända tills det är sparat

	}

}
