/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import java.net.*;

import java.sql.SQLException;
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
public class intra extends HttpServlet {

	 /** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	@Resource(name = "sxadm")
	private DataSource sxadm;
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String get = request.getParameter("get");
		String id = request.getParameter("id");
		if (id == null) { id = "1"; }
			
		RequestDispatcher dispatcher;
		
		HttpSession session = request.getSession();
		SXSession sxSession = WebUtil.getSXSession(session);
		
		// Två rader som fixaar automatisk inloggning för test
		if (!sxSession.getInloggad()) {
			sxSession.setInloggad(true);
			sxSession.setKundnr("0555");
			sxSession.setKundnamn("Grums rör");
			sxSession.setIntraAnvandareKort("UB");
			sxSession.setIntrauser(true);
		}
		
		if (!sxSession.getInloggad()) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "login?refpage=intra");
			return;
		} 
		if (!sxSession.isIntrauser()) {
			out.println("Ingen behörighet");
			return;
		}
		
		try {
			if (get != null) {			//Vi har en get-request som bara skickar en del av sidan
				if (get.equals("faktura")) {
				} else if (get.equals("order")) {
				} else {
					out.println("Inga data tillgängliga!");
				}
			} else {			// Om vi inte har någon annan request så antar vi en id-request som ritar en hel sida
				request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
				printTopBar(request,response,"id=\"top\"");
				printLeftSideBar(request,response,"id=\"leftbar\"");
				out.println("<div id=\"body\">");

				if (id.equals("1")) {
					//this.printKalender(request, response, "id=\"leftbar\"");
					this.printRappTest(request, response, "id=\"leftbar\"");
					
				} else {	
					out.println("Felaktigt id");
				}

				out.println("</div>");
				request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
			}
		} finally { 
			out.close();
		}
 } 

	private void printKalender(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		try {
			PageListKalender pl = new PageListKalender(sxadm); 
			pl.getPage(1);
			request.setAttribute("pagelistkalender", pl);
		} catch (SQLException e) { SXUtil.log("Undantag vid printKalender: " + e.toString()); }
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/intra/printkalender.jsp").include(request, response);
	}
	private void printRappTest(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		try {
			RappHTML r = new RappHTML(sxadm, request);
			response.getWriter().println(r.print());
		} catch (SQLException e) { SXUtil.log("Undantag vid rapp: " + e.toString()); response.getWriter().println(e.toString());}
	}


	private void printLeftSideBar(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/intra/leftsidebar.jsp").include(request, response);		
	}

	private void printTopBar(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/intra/topbar.jsp").include(request, response);		
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
