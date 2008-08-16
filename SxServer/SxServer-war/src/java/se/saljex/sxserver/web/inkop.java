/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import java.net.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
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
	
	private PageListBest1 plBest1 = null;
	private PageListBest2 plBest2 = null;

	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
				
		try {	con = sxadm.getConnection(); } catch (SQLException e) { SXUtil.log(e.toString());}
		session = request.getSession();
		sxSession = WebUtil.getSXSession(session);
		this.request = req;
		this.response = res;
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
			if (sxSession.getInkopInloggatBestNr() == null) { // Vi måste logga in, och ignorerar alla parametrar utom inloggning
				printHeader();
				if (login()) { printBest(); } else { printLogin(); }
				printFooter();
			} else if (get != null) {			//Vi har en get-request som bara skickar en del av sidan
				if (get.equals("")) {
				} else {
					out.println("Inga data tillgängliga!");
				}
			} else {			// Om vi inte har någon annan request så antar vi en id-request som ritar en hel sida
				printHeader();

				if (id.equals("1")) {
					printBest();
				} else if (id.equals("2")) {
					updateForm();
				} else {	
					out.println("Felaktigt id");
				}

				printFooter();
			}
			
		} 
		catch (SQLException se) { out.print("<b>Ett oväntat undantagsfel uppstod - fel vid kommunikation med databasen.</b>"); SXUtil.log(se.toString());}
		finally { 
			out.close();
			try {con.close();} catch (SQLException e ){}
		}
 } 


// Logga in och läs in pagelisterna
private boolean login() throws SQLException {
	boolean ret = false;
	if (skd != null && bnr != null)  {
		plBest1 = new PageListBest1(sxadm, bnr, skd);
		if (plBest1.next()) { ret = true; }
		plBest1.getPage(1);					// Återställ så att vi får en nyinitierad pagelist att skicka med anrop, och direkt kan köra i while(pl.next())
	}
	if (ret) {
		sxSession.setInkopInloggatBestNr(bnr);
		plBest2 = new PageListBest2(sxadm, bnr);
	}
	return ret;
}

private void updateForm() throws SQLException{
	PreparedStatement s;
	request.getParameter("be1bekrdat");
//	request.getp
	
	con.setAutoCommit(false);
	s = con.prepareStatement("update best1 set status='"+SXConstant.BEST_STATUS_MOTTAGEN+"', bekrdat=? where bestnr = " + bnr);
	s = con.prepareStatement("update best2 set bekrdat=? where bestnr = " + bnr);
	s = con.prepareStatement("insert into besthand () values ()");
	con.commit();
	con.setAutoCommit(true);
}


private void printLogin() throws ServletException, IOException {
	request.getRequestDispatcher("/WEB-INF/jspf/inkop/login.jsp").include(request, response);
}

private void printBest() throws ServletException, IOException {
	request.setAttribute("pagelistbest1", plBest1);
	request.setAttribute("pagelistbest2", plBest2);
	request.getRequestDispatcher("/WEB-INF/jspf/inkop/bestform.jsp").include(request, response);
}

private void printHeader()  throws ServletException, IOException{
				request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
				printTopBar("id=\"top\"");
				printLeftSideBar("id=\"leftbar\"");
				out.println("<div id=\"body\">");	
}

private void printFooter()  throws ServletException, IOException {
				out.println("</div>");
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

	
public class InkopBestFormData {
	private ArrayList<InkopBest2FormData> rader = new ArrayList();
	private InkopBest2FormData be2 = null;
	private PageListBest1 be1 = null;
	private Iterator i = null;
	
	private String formBekrdat = null;
	private String formErrBekrdat = null;

	public PageListBest1 getBe1() {	return be1;	 }
	public void setBe1(PageListBest1 be1) {	this.be1 = be1;	 }

	public inkop.InkopBest2FormData getBe2() {	return be2;	 }
	public void setBe2(inkop.InkopBest2FormData be2) {	this.be2 = be2;	 }

	public String getBe1FormBekrdat() {	return formBekrdat;	 }
	public void setBe1FormBekrdat(String formBekrdat) {	this.formBekrdat = formBekrdat;	 }

	public String getBe1FormErrBekrdat() {	return formErrBekrdat;	 }
	public void setBe1FormErrBekrdat(String formErrBekrdat) {	this.formErrBekrdat = formErrBekrdat;	 }

	public int getBe2Rad() { return be2.rad; }
	public String getBe2ArtnrHtmlStr() { return SXUtil.toHtml(be2.artnr); }
	public String getBe2ArtnamnHtmlStr() { return SXUtil.toHtml(be2.artnamn); }
	public Double getBe2Best() { if (be2.best==null) return 0.0; else return be2.best; }
	public Double getBe2Pris() { if (be2.pris==null) return 0.0; else return be2.pris; }
	public Double getBe2Rab() { if (be2.rab==null) return 0.0; else return be2.rab; }
	public String getBe2Enh() { return SXUtil.toHtml(be2.enh); }
	public String getBe2BekrdatStr() { return SXUtil.getFormatDate(be2.bekrdat); }
	
	public String getBe2FormBekrdatHtmlStr() { return SXUtil.toHtml(be2.formBekrdat); }
	public String getBe2FormErrBekrdatHtmlStr() { return SXUtil.toHtml(be2.formErrBekrdat); }
	
	public String getBe2FormBartnrHtmlStr() { return SXUtil.toHtml(be2.formBartnr); }
	public String getBe2FormErrBartnrHtmlStr() { return SXUtil.toHtml(be2.formErrBartnr); }

	
	public boolean nextRad() {
		boolean ret = false;
		if (i==null) i = rader.iterator();
		if (i.hasNext()) {
			ret = true;
			be2 = (InkopBest2FormData)i.next();
		} else { be2 = null; }
		return ret;
	}
	  }

public class InkopBest2FormData {
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
	public String formErrBekrdat = null;
	
	public String formBartnr = null;
	public String formErrBartnr = null;
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
