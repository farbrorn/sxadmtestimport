/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.ejb.EJB;
import se.saljex.sxserver.SxServerMainLocal;
import com.lowagie.text.DocumentException;
import javax.annotation.security.RunAs;
import se.saljex.sxlibrary.SXSession;
import se.saljex.sxlibrary.SxServerMainRemote;
import se.saljex.sxlibrary.WebSupport;
import se.saljex.sxserver.LocalWebSupportLocal;

/**
 *
 * @author Ulf
 */
@RunAs("admin")
public class getPDF extends HttpServlet {
    @EJB
    private SxServerMainLocal SxServerMainBean;
    @EJB
    private SxServerMainRemote SxServerMainRemote;
    @EJB
    private LocalWebSupportLocal LocalWebSupportLocal;

	private OutputStream out;
	private HttpServletResponse response;
		private SXSession sxSession;
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		this.response = response;

		 out = response.getOutputStream();

			sxSession = WebSupport.getSXSession(request.getSession());
			if (!sxSession.getInloggad()) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "login?refpage=intra&logintype=intra");
				return;
			}
			if (!sxSession.isIntrauser()) {
				return;
			}

//		 request.getSession().setAttribute("test", "Ja");
		 String typ = request.getParameter("typ");
		 if (typ==null) typ="";

		 Integer nr = null;
		 try {
			nr = new Integer(request.getParameter("nr"));
		} catch (NumberFormatException e) {  }

		if (nr != null) {
			try {
				if ("faktura".equals(typ)) {
					send(LocalWebSupportLocal.getPdfFaktura(nr));
				} else if ("best".equals(typ)) {
					send(LocalWebSupportLocal.getPdfBest(nr));
				} else if ("offert".equals(typ)) {
					if ("true".equals(request.getParameter("inkmoms"))) {
						send(LocalWebSupportLocal.getPdfOffertInkMoms(nr));
					} else {
						send(LocalWebSupportLocal.getPdfOffert(nr));
					}
				}
			} catch (DocumentException e) {}

		}

    }

	private void send(ByteArrayOutputStream bs) throws IOException{
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(bs.size());
            bs.writeTo(out);
            out.flush();

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
