/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ulf
 */
public class WebUtil {
	public static SXSession getSXSession (HttpSession session) {
		SXSession s = (SXSession)session.getAttribute("sxsession");
		if (s == null) { 
			s = new SXSession(); 
			session.setAttribute("sxsession", s);
		}
		return s;
	}
	
	
	/* Tar en pdf-stream och skickar den till ett ServletResponse med korrekta header */
	public static void sendPdf(ByteArrayOutputStream pdfStream, ServletOutputStream outStream, HttpServletResponse response )	throws IOException		 {
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.setContentLength(pdfStream.size()); 
				pdfStream.writeTo(outStream);
				outStream.flush();		
	}
	
	
}
