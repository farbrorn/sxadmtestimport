/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.websupport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
			clearSxSession(session);
			s = (SXSession)session.getAttribute("sxsession");
		}
		return s;
	}

	public static void clearSxSession(HttpSession session) {
		session.setAttribute("sxsession", new SXSession());
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

	public static void sendFile(byte[] fil, String contentType, ServletOutputStream outStream, HttpServletResponse response )	throws IOException		 {
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				response.setContentType(contentType);
				response.setContentLength(fil.length);
				outStream.write(fil);
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

	//Loggar in kund
	public static boolean logInKund(HttpServletRequest request, Connection con, String anvandare, String losen) throws java.sql.SQLException {
		return logInKund(request, con, anvandare, losen, false);
	}
	public static boolean logInKund(HttpServletRequest request, Connection con, String anvandare, String losen, boolean stayLogggedin) throws java.sql.SQLException {
		if (anvandare != null) {
			clearSxSession(request.getSession());
			SXSession sxSession = getSXSession(request.getSession());
			PreparedStatement st = con.prepareStatement("select k.nummer, k.namn, kk.namn, kk.kontaktid, kl.loginnamn, kl.autologinid, kl.autologinexpire from kund k, kundkontakt kk, kundlogin kl where k.nummer = kk.kundnr and kk.kontaktid = kl.kontaktid and kl.loginnamn=? and kl.loginlosen=?");
			st.setString(1, anvandare);
			st.setString(2, losen);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				PreparedStatement u;
				if (stayLogggedin) {
					Random r = new Random();
					//Slumpmässig sträng med upp till 26 tecken
					sxSession.setKundAutoLogInId((Long.toString(Math.abs(r.nextLong()), 36) + Long.toString(Math.abs(r.nextLong()), 36)).trim());

					u = con.prepareStatement("update kundlogin set autologinid=?, autologinexpire=current_date+20 where loginnamn=?");
					u.setString(1, sxSession.getKundAutoLogInId());
					u.setString(2, anvandare);
					u.executeUpdate();
				} else {
					setKundLoginNull(con, anvandare);
				}

				sxSession.setInloggad(true);
				sxSession.setKundnr(rs.getString(1));
				sxSession.setKundnamn(rs.getString(2));
				sxSession.setKundKontaktNamn(rs.getString(3));
				sxSession.setKundKontaktId(rs.getInt(4));
				sxSession.setKundLoginNamn(rs.getString(5));
				sxSession.setGastLogin(false);
				sxSession.setKundStayLoggedIn(stayLogggedin);

				return true;
			} else {
				// LoginError
				logAnvandarhandelse(request, con, anvandare, "Felaktig inloggning");
				return false;
			}
		}
		return false;

	}


	public static boolean autoLogInKund(HttpServletRequest request, Connection con, String anvandare, String autoLogInId) throws java.sql.SQLException {
		if (anvandare != null && autoLogInId != null) {
			clearSxSession(request.getSession());
			SXSession sxSession = getSXSession(request.getSession());
			PreparedStatement st = con.prepareStatement("select k.nummer, k.namn, kk.namn, kk.kontaktid, kl.loginnamn, kl.autologinid, kl.autologinexpire from kund k, kundkontakt kk, kundlogin kl where k.nummer = kk.kundnr and kk.kontaktid = kl.kontaktid and kl.loginnamn=? and kl.autologinid=? and kl.autologinexpire >= current_date");
			st.setString(1, anvandare);
			st.setString(2, autoLogInId);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				sxSession.setInloggad(true);
				sxSession.setKundnr(rs.getString(1));
				sxSession.setKundnamn(rs.getString(2));
				sxSession.setKundKontaktNamn(rs.getString(3));
				sxSession.setKundKontaktId(rs.getInt(4));
				sxSession.setKundLoginNamn(rs.getString(5));
				sxSession.setGastLogin(false);
				sxSession.setKundStayLoggedIn(true);
				sxSession.setKundAutoLogInId(autoLogInId);
				return true;
			} else {
				setKundLoginNull(con, anvandare);
				logAnvandarhandelse(request, con, anvandare, "Autologin misslyckad");
				return false;
			}
		}
		return false;

	}

	public static void logOutKund(HttpServletRequest request, Connection con) throws SQLException {
		String anvandare = WebUtil.getSXSession(request.getSession()).getKundLoginNamn();
		request.getSession().invalidate();
		setKundLoginNull(con, anvandare);
		logAnvandarhandelse(request, con, anvandare, "Logout");
	}


	public static void logOutIntra(Connection con, HttpSession session) throws SQLException {
		session.invalidate();
	}


	public static boolean loginIntra(Connection con, HttpSession session, String anvandare, String losen) throws SQLException {
		SXSession sxSession = getSXSession(session);
		if (anvandare != null && !sxSession.getInloggad()) {

			PreparedStatement st = con.prepareStatement("select forkortning, namn, a.behorighet, lagernr from saljare s, anvbehorighet a where a.anvandare = s.namn " +
							" and s.forkortning=? and s.losen=?");
			st.setString(1, anvandare);
			st.setString(2, losen);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if (SXConstant.BEHORIGHET_INTRA_LOGIN.equals(rs.getString(3))
						|| SXConstant.BEHORIGHET_INTRA_SUPERUSER.equals(rs.getString(3))
						|| SXConstant.BEHORIGHET_INTRA_ADMIN.equals(rs.getString(3))	  ) {
					sxSession.setInloggad(true);
					sxSession.setIntrauser(true);
					sxSession.setIntraAnvandare(rs.getString(2));
					sxSession.setIntraAnvandareKort(rs.getString(1));
					sxSession.setIntraAnvandareLagerNr(rs.getInt(4));
				}
				if (SXConstant.BEHORIGHET_INTRA_SUPERUSER.equals(rs.getString(3)))	sxSession.setSuperuser(true);
				if (SXConstant.BEHORIGHET_INTRA_ADMIN.equals(rs.getString(3)))			sxSession.setAdminuser(true);
                                sxSession.addBehorighet(rs.getString(3));
			}
			if (sxSession.getInloggad()) {
				return true;
			} else {
				// LoginError
				sxSession.setSuperuser(false);
				sxSession.setAdminuser(false);
				return false;
			}
		}
		return false;
	}




	private static void setKundLoginNull(Connection con, String anvandare) throws SQLException{
		PreparedStatement u = con.prepareStatement("update kundlogin set autologinid=null, autologinexpire=null where loginnamn=?");
		u.setString(1, anvandare);
		u.executeUpdate();
	}

	public static void logAnvandarhandelse(HttpServletRequest request, Connection con, String anvandare, String handelse) throws SQLException {
		PreparedStatement u = con.prepareStatement("insert into kundloginhandelse (loginnamn, handelse, ip) values (?,?,?)");
		u.setString(1, anvandare);
		u.setString(2, handelse);
		u.setString(3, request.getRemoteAddr());
		u.executeUpdate();
	}

}
