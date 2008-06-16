/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import java.net.*;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import se.saljex.sxserver.*;

/**
 *
 * @author ulf
 */
public class kund extends HttpServlet {

	 /** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		String get = request.getParameter("get");
		String id = request.getParameter("id");
			
		RequestDispatcher dispatcher;
		
		HttpSession session = request.getSession();
		SXSession sxSession = (SXSession)session.getAttribute("sxsession");
		if (sxSession == null) { session.setAttribute("sxsession", new SXSession()); }
		if (!sxSession.getInloggad()) {
			dispatcher = request.getRequestDispatcher("login");
			request.setAttribute("loginrefpage", "kund");
			dispatcher.forward(request, response);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			
			
			if (get != null) {			//Vi har en get-request som bara skickar en del av sidan
				if (get.equals("faktura")) {
					getFaktura(request, response);
				} else {
					out.println("Inga data tillgängliga!");
				}
			} else {			// Om vi inte har någon annan request så antar vi en id-request som ritar en hel sida
				if (id == null) { id = "1"; }
					dispatcher = request.getRequestDispatcher("WEB-INF/jspf/header.jspf");
					dispatcher.include(request, response);
					
					out.println("<div id=\"top\">");
					printTopBar(request,response);
					out.println("</div>");

					out.println("<div id=\"body\">");

					out.println("<div id=\"leftbar\">");
					printLeftSideBar(request,response);
					out.println("</div>");

					out.println("<div id=\"midbar\">");
					printKundinfo(request, response);
					out.println("</div>");

					out.println("<div id=\"rightbar\">");
					printRightSideBar(request,response);
					out.println("</div>");

					out.println("</div>");

					dispatcher = request.getRequestDispatcher("WEB-INF/jspf/footer.jspf");
					dispatcher.include(request, response);
				if (id.equals("1")) {
				} else if (id.equals("logout")) {
					session.invalidate();
					out.println("Utloggad!");
				} else {
					out.println("Felaktigt id");
				}
			}
		} finally { 
			out.close();
		}
 } 

	private void getFaktura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("WEB-INF/jspf/fak.jsp");
		//dispatcher = request.getRequestDispatcher("/jtest.jsp");
		FakturaBean fak = new FakturaBean();
		fak.addRow("10","artikel10");
		fak.addRow("20","artikel20");
		request.setAttribute("fak", fak);
		dispatcher.include(request, response);
		
	}
	private void printKundinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("WEB-INF/jspf/kund/printkundinfo.jsp");
		dispatcher.include(request, response);
		
	}
	private void printLeftSideBar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("WEB-INF/jspf/kund/leftsidebar.jsp");
		dispatcher.include(request, response);		
	}
	private void printRightSideBar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("WEB-INF/jspf/kund/rightsidebar.jsp");
		dispatcher.include(request, response);		
	}
	private void printTopBar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher;
		dispatcher = request.getRequestDispatcher("WEB-INF/jspf/kund/topbar.jsp");
		dispatcher.include(request, response);		
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
