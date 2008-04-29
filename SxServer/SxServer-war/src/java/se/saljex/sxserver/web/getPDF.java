/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.ejb.EJB;
import se.saljex.sxserver.SxServerMainLocal;
import com.lowagie.text.DocumentException;
import java.sql.SQLException;

/**
 *
 * @author Ulf
 */
public class getPDF extends HttpServlet {
    @EJB
    private SxServerMainLocal SxServerMainBean;
	
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
  /*     // PrintWriter out = response.getWriter();
		File file = new File("C:\\dum\\test.pdf"); 
		int bytes= (int)file.length(); 

		FileInputStream fis = new FileInputStream(file); 
		byte[] buff = new byte[2048]; 
		response.setContentLength(bytes); 
		response.setContentType("application/pdf"); 
		response.setHeader("Content-disposition", "filename=List.pdf"); 
		while(fis.read(buff, 0, buff.length)!=-1) 
		{ 
			response.getOutputStream().write(buff, 0, buff.length); 
		} 

//		response.flushBuffer();
		fis.close();
		
*/	
        OutputStream out = response.getOutputStream();
        
        try {
            ByteArrayOutputStream bs = SxServerMainBean.getPDF(response);
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.setContentLength(bs.size());
            bs.writeTo(out);
            out.flush();
        } catch (DocumentException ep) { 
            throw new ServletException("Exception from PDF: DocumentException " + ep.toString());
        }
		catch (SQLException es) {
			throw new ServletException("Exception from PDF: SQLException: " + es.toString());
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
