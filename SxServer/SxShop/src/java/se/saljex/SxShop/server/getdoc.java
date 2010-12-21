/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.saljex.sxlibrary.SxServerMainRemote;

/**
 *
 * @author ulf
 */
@RunAs("admin")
public class getdoc extends HttpServlet {
//      @EJB
//    private SxServerMainRemote SxServerMainBean;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
/*        OutputStream outStream=null;
		  PrintWriter outWriter=null;
		  Integer docId=null;

		  try {
				docId = Integer.parseInt(request.getParameter("docid"));
		  } catch (NumberFormatException e) {}

		  String docType=request.getParameter("doctype");
		  if (docType==null || docType.isEmpty()) docType="faktura";

		  ByteArrayOutputStream bs=null;

        try {
			  try {
					if ("faktura".equals(docType) && docId!=null) {
						bs = SxServerMainBean.getPdfFaktura(docId);
					}
			  } catch (Exception e) {
				  bs=null;
			  }
			  if (bs!=null) {
					response.setHeader("Expires", "0");
					response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
					response.setHeader("Pragma", "public");
					response.setContentType("application/pdf");
					response.setContentLength(bs.size());
					outStream = response.getOutputStream();
					bs.writeTo(outStream);
					outStream.flush();
			  } else {
				  outWriter=response.getWriter();
		        response.setContentType("text/html;charset=UTF-8");
				  outWriter.print("Dokumentet hittades inte." + docType + " " + docId);
			  }
			} catch (IOException es) {
				throw new ServletException("Exception from PDF: SQLException: " + es.toString());
			} finally {
				try { outStream.close(); } catch (Exception e) {}
				try { outWriter.close(); } catch (Exception e) {}
        }*/
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
