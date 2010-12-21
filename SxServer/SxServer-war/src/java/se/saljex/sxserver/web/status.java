/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import javax.annotation.security.RunAs;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxlibrary.LocalWebSupportRemote;
import se.saljex.sxserver.SxServerMainLocal;
import se.saljex.sxlibrary.SxServerMainRemote;
/**
 *
 * @author Ulf
 */
@RunAs("admin")
public class status extends HttpServlet {
	@EJB
	private LocalWebSupportLocal localWebSupportBean;
      @EJB
    private SxServerMainLocal SxServerMainBean;
      @EJB
    private SxServerMainRemote SxServerMainRemote;
 
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
			out.println(SxServerMainRemote.getHTMLStatus());
			out.println("<br/>nytt<br/>");
			out.println(localWebSupportBean.getHTMLStatus());
			
            /* output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet status</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet status at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
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
