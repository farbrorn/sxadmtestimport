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
public class Login extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(); 
		SXSession sxSession = (SXSession)session.getAttribute("sxsession");
		if (sxSession == null) { session.setAttribute("sxsession", new SXSession()); }
		RequestDispatcher dispatcher;
		
		String kundnr = request.getParameter("kundnr");
		
		if (kundnr != null) {
			sxSession.kun = LocalWebSupportBean.getTableKund(kundnr);
			if (sxSession.kun != null) {
				sxSession.setInloggad(true);
			}
		}
		if (sxSession.getInloggad()) {
			String refPage = (String)request.getAttribute("loginrefpage");
			if (refPage == null) { refPage = "kund"; }
			dispatcher = request.getRequestDispatcher(refPage);
			dispatcher.forward(request, response);
		} else {
			dispatcher = request.getRequestDispatcher("WEB-INF/jspf/login/loginpage.jsp");
			dispatcher.forward(request, response);
			
		}
		
		
/*		PrintWriter out = response.getWriter();
		try {
		} finally { 
			out.close();
		}
 * */
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
