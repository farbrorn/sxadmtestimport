/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import se.saljex.sxserver.SXEntityNotFoundException;
import java.io.*;
import java.net.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
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
	@Resource(name = "sxadm")
	private DataSource sxadm;
	
	private Connection con;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private PrintWriter out;
	private HttpSession session;
	private SXSession sxSession;
	private	Integer bnr = null;
	private	Integer skd = null;
	private BestForm bestForm = null;

	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		this.request = req;
		this.response = res;
				
		try {	con = sxadm.getConnection(); } catch (SQLException e) { SXUtil.log(e.toString());}
		session = req.getSession();
		sxSession = WebUtil.getSXSession(session);
		this.out = response.getWriter();
		
		//request.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		String get = request.getParameter("get");
		String id = request.getParameter("id");
		if (id == null) { id = "1"; }

		try { bnr = Integer.parseInt(request.getParameter("bnr")); } catch (Exception e) {}
		try { skd = Integer.parseInt(request.getParameter("skd")); } catch (Exception e) {}
		
		if ((bnr==null || skd==null) && sxSession.getInkopInloggatBestNr()==null) { id = "1"; }

		
		try {
			if (!login()) { id = "0"; }
			printHeader();

			if (id.equals("0")) {
				printLogin();
/*	PageListBest1 p = new PageListBest1(sxadm);
	p.getPage(1);
	while (p.next()) {
		out.print("<br/>" + p.getBestnr() + "-" + p.getSakerhetskod() + "<br/>");
	} 
	p.close();
	*/
			} else if (id.equals("1")) {
				printBest();
			} else if (id.equals("2")) {
				updateForm();
			} else {	
				out.println("Felaktigt id");
			}

			printFooter();
			
		} 
		catch (SQLException se) { out.print("<b>Ett oväntat undantagsfel uppstod - fel vid kommunikation med databasen.</b>"); SXUtil.log(se.toString()); se.printStackTrace();}
		finally { 
			try { bestForm.be1.close(); } catch (Exception e) {}
			out.close();
			try {con.close();} catch (SQLException e ){}
		}
 } 


// Logga in och läs in pagelisterna
private boolean login() throws SQLException {
	try {
		if (skd != null && bnr != null)  {
			bestForm = new BestForm(sxadm, bnr, skd);
			this.setBestMottagen();
			
			sxSession.setInkopInloggatBestNr(bnr);
		} else {
			if (sxSession.getInkopInloggatBestNr() == null) return false;
			bestForm = new BestForm(sxadm, sxSession.getInkopInloggatBestNr());
		}
	} catch (SXEntityNotFoundException e) {
		sxSession.setInkopInloggatBestNr(null);
		return false;
	}
	
	return true;
}



private void updateForm() throws ServletException, IOException, SQLException{
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
			} catch (java.text.ParseException e) {
				s.setDate(2, null);			
			}
			s.executeUpdate();

			s = con.prepareStatement("update best2 set bekrdat=? where bestnr = " + bnr + " and rad=?");
			
			for (BestFormRad b : bestForm.rader) {
				s.setInt(2, b.rad);
				try {
					s.setDate(1, SXUtil.getSQLDate(SXUtil.parseDateStringToDate(b.formBekrdat)));
				} catch (java.text.ParseException e) {
					s.setDate(1, null);			
				}
				s.executeUpdate();
			}

			s = con.prepareStatement("insert into besthand (bestnr, datum, tid, anvandare, handelse) values (?,?,?,?,?)");
			java.util.Date d = new java.util.Date();
			s.setInt(1, bnr);
			s.setString(4, SXUtil.getSXReg(con, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
			s.setString(5, SXConstant.BESTHAND_LEVERANSBESKED);
			int cn = 0;
			do {
				s.setDate(2, SXUtil.getSQLDate(d));
				s.setTime(3, SXUtil.getSQLTime(d));
				cn++;
				if (cn > 10){ throw new SQLException("Creates duplicate key in besthand"); }  // Avbryt med en Exception om vi har försökt för många gånger
				d = new java.util.Date(d.getTime()+1000);	// Öka med 1 s för att vara förbered ifall dubbel key skapas
			} while (s.executeUpdate() < 1);  // Loopa ända tills det är sparat

			con.commit();
			bestForm.setSavedOK();
			printBest();
		} catch (SQLException e) {
			bestForm.setSaveError();
			throw(e);
		}
	}
}
private void setBestMottagen() throws SQLException{
	PreparedStatement s;
	s = con.prepareStatement("update best1 set status=? where bestnr = " + bnr);
	s.setString(1, SXConstant.BEST_STATUS_MOTTAGEN);
	if (s.executeUpdate() < 1) { throw new SQLException("Hittar inte beställning " + bnr + " vid update status"); }
}


private void printLogin() throws ServletException, IOException {
	request.getRequestDispatcher("/WEB-INF/jspf/inkop/login.jsp").include(request, response);
}

private void printBest() throws ServletException, IOException {
	request.setAttribute("bestform", bestForm);
	request.getRequestDispatcher("/WEB-INF/jspf/inkop/bestform.jsp").include(request, response);
}

private void printHeader()  throws ServletException, IOException{
				request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
				printTopBar("id=\"top\"");
				printLeftSideBar("id=\"leftbar\"");
				out.println("<div id=\"body\">");	
				out.println("<div id=\"midbar\">");
}

private void printFooter()  throws ServletException, IOException {
				out.println("</div></div>");
				request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);	
}


	private void printLeftSideBar(String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/inkop/leftsidebar.jsp").include(request, response);		
	}

	private void printTopBar(String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/inkop/topbar.jsp").include(request, response);		
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
		
		public PageListBest1 be1 = null;
		private PageListBest2 be2 = null;
		private String formBekrdat = "";
		private String formBekrdatErr = "";
		public ArrayList<BestFormRad> rader = new ArrayList();
		
		public BestForm(DataSource ds, Integer bnr) throws SQLException, SXEntityNotFoundException {
			be1 = new PageListBest1(ds, bnr);
			loadBest(ds, bnr);
		}
		public BestForm(DataSource ds, Integer bnr, Integer skd) throws SQLException, SXEntityNotFoundException	{
			be1 = new PageListBest1(ds, bnr, skd);
			loadBest(ds, bnr);
		}
		private void loadBest(DataSource ds, Integer bnr) throws SQLException, SXEntityNotFoundException {
			BestFormRad b;
			be1.getPage(1);
			if (be1.next()) { //Vi har en träff,
				this.formBekrdat = SXUtil.getFormatDate(be1.getBekrdat());
				be2 = new PageListBest2(ds,bnr); 
				be2.getPage(0);
				if (be1.getStatus().equals(SXConstant.BEST_STATUS_MOTTAGEN)) mottagen = true;
				while (be2.next()) {		//Läs in rader från best2
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
				}
				be2.close();
				be2 = null;
			} else { be1.close(); be1 = null; throw new SXEntityNotFoundException();}
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
		public void setMottagen(boolean m) { mottagen = m;  }
		
		public String getNameBekrdat() {			return "be1bekrdat";		}
		public String getFormBekrdat() {			return this.formBekrdat;		}
		public String getFormBekrdatErr() {		return this.formBekrdatErr;		}
		
		public void readForm(HttpServletRequest req) {
			this.setParsedOK();		// Initiera f;rst parsedOK f;r att sedan s'tta error om fel uppst[r
			this.formBekrdat = SXUtil.toStr(req.getParameter(this.getNameBekrdat()));
			try {
				if (!this.formBekrdat.isEmpty()) this.formBekrdat = SXUtil.parseDateStringToString(this.formBekrdat);
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
	
	public String getNameBekrdat() {						// Returnerar name att använda vid html form input
		return "be2bekrdat[" + rad + "}";
	}
	public String getNameBartnr() {						// Returnerar name att använda vid html form input
		return "be2bartnr[" + rad + "}";
	}
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
