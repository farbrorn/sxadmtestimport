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
//		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(); 
		SXSession sxSession = WebUtil.getSXSession(session);

		RequestDispatcher dispatcher;
		LoginFormData f = new LoginFormData();
		
		f.kundnr = request.getParameter("kundnr");
		String action = request.getParameter("action");
		if (action == null) { action = "login"; }
		
		if (action.equals("logout")) {
				session.invalidate();
				dispatcher = request.getRequestDispatcher("WEB-INF/jspf/login/logoutpage.jsp");
				dispatcher.forward(request, response);
		} else {		//Default action = login
			if (f.kundnr != null) {
				TableKund kun = LocalWebSupportBean.getTableKund(f.kundnr);
				if (kun != null) {
					sxSession.setInloggad(true);
					sxSession.setKundnr(kun.getNummer());
					sxSession.setKundnamn(kun.getNamn());
				} else {
					f.kundnrErr = "Felaktigt kundnr";
					request.setAttribute("loginformdata", f);
				}
			}
			if (sxSession.getInloggad()) {
				String refPage = request.getParameter("refpage");
				if (refPage == null) { refPage = "kund"; }
	//			dispatcher = request.getRequestDispatcher(refPage);
	//			dispatcher.forward(request, response);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", refPage);
			} else {
				dispatcher = request.getRequestDispatcher("WEB-INF/jspf/login/loginpage.jsp");
				dispatcher.forward(request, response);

			}
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
