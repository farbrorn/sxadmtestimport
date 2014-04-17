/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import se.saljex.sxlibrary.exceptions.SXSecurityException;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.websupport.WebUtil;
import se.saljex.sxlibrary.SXSession;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import java.io.*;
import java.net.*;
import se.saljex.sxlibrary.WebSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import se.saljex.sxserver.*;

/**
 *
 * @author ulf
 */
public class inkop extends HttpServlet {

	 /** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
	

	protected void processRequest(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		InkopLocalHandler r = new InkopLocalHandler(req, res);
		r = null;
	}	
		



	public class InkopLocalHandler {
		private Connection con;
		private HttpServletRequest request;
		private HttpServletResponse response;
		private PrintWriter out;
		private ServletOutputStream outStream;
		private SXSession sxSession;
		private	Integer bnr = null;
		private	Integer skd = null;
		private BestForm bestForm = null;

		private LoginForm loginForm = null;

		private boolean siteHeaderPrinted = false;
		private boolean bodyHeaderPrinted = false;
		private boolean midbarHeaderPrinted = false;

		public InkopLocalHandler(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
			request = req;
			response = res;
			sxSession = WebSupport.getSXSession(req.getSession());
			con = WebUtil.getConnection(sxadm);

			String get = request.getParameter("get");
			String id = request.getParameter("id");
			if (id == null) { id = "1"; }

			try { bnr = Integer.parseInt(request.getParameter("bnr")); } catch (Exception e) {}
			try { skd = Integer.parseInt(request.getParameter("skd")); } catch (Exception e) {}


			loginForm = new LoginForm();
			loginForm.setBnr(request.getParameter("bnr"));

			try {
				if (get != null) {
					handleGet(get);
				} else {
					handleId(id);
				}
			} catch (com.lowagie.text.DocumentException ep) {
				ServerUtil.log(ep.toString()); ep.printStackTrace();
				throw new ServletException("Fel vid skapande av pdf");
			} catch (SQLException se) {
			} catch (IOException ie) {
				ServerUtil.log(ie.toString()); ie.printStackTrace();
				throw new ServletException("IOException.");
			} finally {
				loginForm = null;
				try { bestForm.be1.close(); } catch (Exception e) {}
				try { out.close();	 } catch (Exception e) {}
				try { outStream.close();	 } catch (Exception e) {}
				try { con.close();} catch (SQLException e ){}
			}

		}
			private void handleGet(String get) throws IOException, ServletException, SQLException, com.lowagie.text.DocumentException {
				if ("pdf".equals(get)) {
					outStream = response.getOutputStream();

					if (!login()) { throw  new ServletException("Inte inloggad" ); }
					bnr = sxSession.getInkopInloggatBestNr();		///// Göres om!! ***********************************

					WebSupport.sendPdf(LocalWebSupportBean.getPdfBest(bnr), outStream, response);

				}
			}

			private void handleId(String id) throws IOException, ServletException, SQLException{
				this.out = response.getWriter();
				printSiteHeader();
				printTopBar();

				if (id.equals("0") || id.equals("logout")) {
					sxSession.setInkopInloggatBestNr(bnr);
					printLogin();
			PageListBest1 p = new PageListBest1(con);
			p.getPage(1);
			while (p.next()) {
			out.print("<br/><a href=\"?bnr="+ p.getBestnr() +"&skd="+p.getSakerhetskod()+"\">" + p.getBestnr() + "-" + p.getSakerhetskod() + "</a>");
			}
			//	p.close();

				} else if (id.equals("1")) {
					if (!login()) {
						printLogin();
					} else {
						printLeftSideBar();
						printBodyHeader();
						printMidbarHeader();
						printBest();
					}
				} else if (id.equals("2")) {
					if (!login()) {
						printLogin();
					} else {
						printLeftSideBar();
						printBodyHeader();
						printMidbarHeader();
						updateForm();
					}
				} else {
					printBodyHeader();
					printMidbarHeader();
					out.println("Felaktigt id");
				}

				printFooter();
			}


			// Logga in och läs in pagelisterna
			private boolean login() throws SQLException {
				try {
					if (skd != null && bnr != null)  {
						bestForm = new BestForm(con, bnr, skd);
						this.setBestMottagen();

						sxSession.setInkopInloggatBestNr(bnr);
					} else {
						if (sxSession.getInkopInloggatBestNr() == null) return false;
						bestForm = new BestForm(con, sxSession.getInkopInloggatBestNr());
					}
					} catch (SXEntityNotFoundException e) {
					sxSession.setInkopInloggatBestNr(null);
					loginForm.setLoginError(true);
					return false;
				} catch (SXSecurityException e) {
					sxSession.setInkopInloggatBestNr(null);
					loginForm.setSecurityError(true);
					return false;
				}
				return true;
			}



			private void updateForm() throws ServletException, IOException, SQLException{
				boolean levDatumAngivet = false;
				bestForm.readForm(request);		// Läs in och validera
				if (bestForm.isParseError()) {
					printBest();
				} else {
					try {
						PreparedStatement s;

						con.setAutoCommit(false);
						s = con.prepareStatement("update best1 set status=?, bekrdat=? where bestnr = " + bnr);
						s.setString(1, SXConstant.BEST_STATUS_MOTTAGEN);
						try {
							s.setDate(2, SXUtil.getSQLDate(SXUtil.parseDateStringToDate(bestForm.getFormBekrdat())));
							levDatumAngivet = true;
						} catch (java.text.ParseException e) {
							s.setDate(2, null);
						}
						s.executeUpdate();

						s = con.prepareStatement("update best2 set bekrdat=? where bestnr = " + bnr + " and rad=?");

						for (BestFormRad b : bestForm.rader) {
							s.setInt(2, b.rad);
							try {
								s.setDate(1, SXUtil.getSQLDate(SXUtil.parseDateStringToDate(b.formBekrdat)));
								levDatumAngivet = true;
							} catch (java.text.ParseException e) {
								s.setDate(1, null);
							}
							s.executeUpdate();
						}


						WebUtil.insertBesthand(con, bnr, SXConstant.BESTHAND_LEVERANSBESKED);
						con.commit();
						bestForm.setSavedOK();

						bestForm.setLevDatumBekraftat(levDatumAngivet);

						printBest();
					} catch (SQLException e) {
						bestForm.setSaveError();
						throw(e);
					}
				}
			}


			private void setBestMottagen() throws SQLException{
				con.setAutoCommit(false);
				try {
					PreparedStatement s;
					s = con.prepareStatement("update best1 set status=? where bestnr = " + bnr);
					s.setString(1, SXConstant.BEST_STATUS_MOTTAGEN);
					if (s.executeUpdate() < 1) { throw new SQLException("Hittar inte beställning " + bnr + " vid update status"); }
					WebUtil.insertBesthand(con, bnr, SXConstant.BEST_STATUS_MOTTAGEN);
					con.commit();
				} catch (Exception e) {
					con.rollback();
					throw new SQLException(e.toString());
				} finally {
					con.setAutoCommit(true);
				}
			}


			private void printLogin() throws ServletException, IOException {
				printBodyHeader();
				printMidbarHeader();
				request.setAttribute("loginform", loginForm);
				request.getRequestDispatcher("/WEB-INF/jspf/inkop/login.jsp").include(request, response);
			}

			private void printBest() throws ServletException, IOException {
				request.setAttribute("bestform", bestForm);
				request.getRequestDispatcher("/WEB-INF/jspf/inkop/bestform.jsp").include(request, response);
			}

			private void printSiteHeader()  throws ServletException, IOException{
					request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
					siteHeaderPrinted = true;
			}
			private void printBodyHeader()  throws ServletException, IOException{
					out.println("<div id=\"body\">");
					bodyHeaderPrinted = true;
			}
			private void printMidbarHeader()  throws ServletException, IOException{
					out.println("<div id=\"midbar\">");
					midbarHeaderPrinted = true;
			}


			private void printFooter()  throws ServletException, IOException {
					if (midbarHeaderPrinted) out.println("</div>");
					if (bodyHeaderPrinted) out.println("</div>");
					if (siteHeaderPrinted) request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
			}


			private void printLeftSideBar() throws ServletException, IOException{
				out.print("<div id=\"leftbar\">");
				request.getRequestDispatcher("WEB-INF/jspf/inkop/leftsidebar.jsp").include(request, response);
				out.print("</div>");
			}

			private void printTopBar() throws ServletException, IOException{
				out.print("<div id=\"top\">");
				request.getRequestDispatcher("WEB-INF/jspf/inkop/topbar.jsp").include(request, response);
				out.print("</div>");
			}
	}

	
	public class BestForm {
		private static final int FORM_PARSE_NOT_PARSED = 0;
		private static final int FORM_PARSE_ERROR = 1;
		private static final int FORM_PARSE_OK = 2;
		private static final int FORM_SAVE_NULL = 0;
		private static final int FORM_SAVE_ERROR = 1;
		private static final int FORM_SAVE_OK = 2;
		private int formParseStatus = FORM_PARSE_NOT_PARSED;
		private int formSaveStatus = FORM_SAVE_NULL;
		private String formParseText = null;
		private String formSaveText = null;
		private boolean mottagen = false;
		private boolean levDatumBekraftat = false;
		
		public PageListBest1 be1 = null;
		private PageListBest2 be2 = null;
		private String formBekrdat = "";
		private String formBekrdatErr = "";
		public ArrayList<BestFormRad> rader = new ArrayList();
		
		public BestForm(Connection con, Integer bnr) throws SQLException, SXEntityNotFoundException {
			be1 = new PageListBest1(con, bnr);
			loadBest(con, bnr);
		}
		public BestForm(Connection con, Integer bnr, Integer skd) throws SQLException, SXEntityNotFoundException, SXSecurityException	{
			int antalFelinloggningar;
			be1 = new PageListBest1(con, bnr, skd);
			try { 
				loadBest(con, bnr);
				antalFelinloggningar = SXUtil.noNull(be1.getAntalfelinloggningar());
			
				if (antalFelinloggningar > 5) {
					// För många felinloggningar, rensa skiten
					rader = null;
					throw new SXSecurityException("Login failed - Too many incorrect logins.");
				}
			} catch (SXEntityNotFoundException e) {	
				// Antingen finns inte beställningen, eller så är fel kod inslagen
				// Vi provar genom att göra en ny pagelist be1 utan säkerhetskod
				try { be1.close(); } catch (Exception e2) {}
				be1 = new PageListBest1(con, bnr);
				if (be1.next()) {
					// Beställningsnumret fanns, då vet vi att fel kod var angiven
					antalFelinloggningar = SXUtil.noNull(be1.getAntalfelinloggningar());
					antalFelinloggningar++;
					con.prepareStatement("update best1 set antalfelinloggningar = " + antalFelinloggningar + " where bestnr = " + bnr).executeUpdate();
					WebUtil.insertBesthand(con, bnr, SXConstant.BESTHAND_FELINLOGGNING);
				}
				try { be1.close(); } catch (Exception e2) {}
				be1 = null;
				throw(e);
			}
		}
		
		private void loadBest(Connection con, Integer bnr) throws SQLException, SXEntityNotFoundException {
			BestFormRad b;
			be1.getPage(1);
			if (be1.next()) { //Vi har en träff,
				this.formBekrdat = SXUtil.getFormatDate(be1.getBekrdat());
				be2 = new PageListBest2(con,bnr); 
				try {
					be2.getPage(0);
					if (SXConstant.BEST_STATUS_MOTTAGEN.equals(be1.getStatus())) mottagen = true;
					if (be1.getBekrdat() != null) levDatumBekraftat = true;
					while (be2.next()) {		//Läs in rader från best2
						if (be2.getBekrdat()!= null) { levDatumBekraftat = true; }
						b = new BestFormRad();
						b.rad = be2.getRad();
						b.artnr = be2.getArtnr();
						b.bartnr = be2.getBartnr();
						b.artnamn = be2.getArtnamn();
						b.best = be2.getBest();
						b.pris = be2.getPris();
						b.rab = be2.getRab();
						b.enh = be2.getEnh();
						b.bekrdat = be2.getBekrdat();
						rader.add(b);	
						b.formBekrdat = SXUtil.getFormatDate(be2.getBekrdat());
						if (be2.getBekrdat() != null) levDatumBekraftat = true;
						PreparedStatement stmt = null;
						try {
							stmt = con.prepareStatement("select b.namn, c.tel, c.email from stjarnrad a left outer join saljare b on (b.forkortning = a.anvandare) left outer join lagerid c on (c.lagernr = b.lagernr) where a.stjid = " + be2.getStjid());
							ResultSet res = stmt.executeQuery();
							if (res.next()) {
								b.stjSaljare = res.getString(1);
								b.stjSaljareTel = res.getString(2);
								b.stjSaljareEpost = res.getString(3);
							}
						} finally {
							try { stmt.close(); } catch (Exception e) {}
						}
						
					}
				}
				finally {
					be2.close();
					be2 = null;
				}
			} else { 				
				try { be1.close(); } catch (Exception e2) {}
				be1 = null; 
				throw new SXEntityNotFoundException();
			}
		}

		public String getParseText()	{	return this.formParseText; }
		public String getSaveText()	{	return this.formSaveText; }
		public void setParseError(String s)	{	this.formParseText = s; this.formParseStatus = this.FORM_PARSE_ERROR;}
		public void setParseError()	{ setParseError(null);	}
		public void setParsedOK(String s)	{	this.formParseText = s; this.formParseStatus = this.FORM_PARSE_OK;}
		public void setParsedOK()	{ setParsedOK(null);	}
		public void setSaveError(String s)	{	this.formSaveText = s; this.formSaveStatus = this.FORM_SAVE_ERROR;}
		public void setSaveError()	{ setSaveError(null);	}
		public void setSavedOK(String s)	{	this.formParseText = s; this.formSaveStatus = this.FORM_SAVE_OK;}
		public void setSavedOK()	{ setSavedOK(null);	}

		public boolean isParsed() { return this.formParseStatus != this.FORM_PARSE_NOT_PARSED; }
		public boolean isParseError() { return this.formParseStatus == this.FORM_PARSE_ERROR; }
		public boolean isParseOK() { return this.formParseStatus == this.FORM_PARSE_OK; }

		public boolean isSaveTried() { return this.formSaveStatus != this.FORM_SAVE_NULL; }
		public boolean isSaveError() { return this.formSaveStatus == this.FORM_SAVE_ERROR; }
		public boolean isSavedOK() { return this.formSaveStatus == this.FORM_SAVE_OK; }
		public boolean isMottagen() { return mottagen; }
		public boolean isLevDatumBekraftat() { return levDatumBekraftat; }
		public void setLevDatumBekraftat(boolean l) { levDatumBekraftat = l; }
		public void setMottagen(boolean m) { mottagen = m;  }
		
		public String getNameBekrdat() {			return "be1bekrdat";		}
		public String getFormBekrdat() {			return this.formBekrdat;		}
		public String getFormBekrdatErr() {		return this.formBekrdatErr;		}
		
		public void readForm(HttpServletRequest req) {
			this.setParsedOK();		// Initiera f;rst parsedOK f;r att sedan s'tta error om fel uppst[r
			this.formBekrdat = SXUtil.toStr(req.getParameter(this.getNameBekrdat()));
			try {
				if (!this.formBekrdat.isEmpty()) {
					this.formBekrdat = SXUtil.parseDateStringToString(this.formBekrdat);
				}
			} catch (ParseException e) {
				this.setParseError();
				this.formBekrdatErr = "*";				//Felaktigt datum
			}
			
			for (BestFormRad b : rader) {
				b.formBartnr = SXUtil.toStr(req.getParameter(b.getNameBartnr())).trim();			// Få en sträng även om värdet är null
				b.formBekrdat = SXUtil.toStr(req.getParameter(b.getNameBekrdat()));
				try {
					if (!b.formBekrdat.isEmpty()) b.formBekrdat = SXUtil.parseDateStringToString(b.formBekrdat);
				} catch (ParseException e) {
					this.setParseError();
					b.formBekrdatErr = "*";				//Felaktigt datum
				}
			}
		}
	}

	
	
public class BestFormRad {
	public int rad = 0;
	public String artnr = null;
	public String bartnr = null;
	public String artnamn = null;
	public Double best = null;
	public Double pris = null;
	public Double rab = null;
	public String enh = null;
	public java.util.Date bekrdat = null;
	
	public String formBekrdat = null;
	public String formBekrdatErr = null;
	
	public String formBartnr = null;
	public String formBartnrErr = null;
	
	public String stjSaljare = null;
	public String stjSaljareTel = null;
	public String stjSaljareEpost = null;
	
	public String getNameBekrdat() {						// Returnerar name att använda vid html form input
		return "be2bekrdat[" + rad + "}";
	}
	public String getNameBartnr() {						// Returnerar name att använda vid html form input
		return "be2bartnr[" + rad + "}";
	}
}
	
public class LoginForm	 {
	private String bnr = null;
	private boolean loginError = false;
	private boolean securityError = false;

	public boolean isLoginError() {		return loginError;			 }
	public void setLoginError(boolean loginError) {		this.loginError = loginError;			 }
	public boolean isSecurityError() {		return securityError;			 }
	public void setSecurityError(boolean securityError) {		this.securityError = securityError;		 }
	
	public String getBnr() {			return bnr;			 }
	public void setBnr(String bnr) {	this.bnr = bnr; }
	
}
	
	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
