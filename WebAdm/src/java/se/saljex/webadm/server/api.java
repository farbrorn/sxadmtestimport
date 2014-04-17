/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.server;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.sql.DataSource;
import se.saljex.sxlibrary.*;
import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxserver.SxServerMainLocal;
import se.saljex.webadm.client.common.rpcobject.NotLoggedInException;

/**
 *
 * @author Ulf
 */
@WebServlet(name = "api", urlPatterns = {"/api/*"})
public class api extends HttpServlet {
    @EJB
    private SxServerMainLocal SxServerMainBean;
    @EJB
    private SxServerMainRemote SxServerMainRemote;
    @EJB
    private LocalWebSupportLocal LocalWebSupportLocal;
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		TextResponse textResponse = new TextResponse(response);
		
		response.setContentType("text/html;charset=UTF-8");
		
		String path = SXUtil.toStr(request.getPathInfo());
		if (path.length() < 1) path = "/";
		else if (path.charAt(path.length()-1) != '/')  path = path + "/";
		
		Connection sxConnection=null;
		PrintWriter out= null;
		try {
			out = response.getWriter();
			sxConnection = sxadm.getConnection();
			
			ensureLoggedIn(request);
			
			RequestHandler.setLocalWebSupportLocal(request, LocalWebSupportLocal);
			RequestHandler.setSxServerMainRemote(request, SxServerMainRemote);
			RequestHandler.setSxServerMainLocal(request, SxServerMainBean);
			RequestHandler.setSxadm(request, sxadm);
			RequestHandler.setSxConnection(request, sxConnection);
			
			try{
				request.getRequestDispatcher("/WEB-INF/jsp/api" + path +"index.jsp").include(request, response);
//				request.getRequestDispatcher("/WEB-INF/jsp/api" + path +"index.jsp").include(request, textResponse);
//				System.out.print(textResponse.getOutput());
			} catch (IOException e) { out.println("Ogiltig sida"); }

		
		}  catch (SQLException es) { 
			out.print("Fel vid anslutning till databas: " + es.getMessage());
		} catch (NotLoggedInException e) {
			out.print("Ingen behörighet");
		} finally {			
			try { out.close();  } catch (Exception e) {}
			try { sxConnection.close(); } catch (Exception e) {}
		}
		
	}
	
	private void ensureLoggedIn(HttpServletRequest request) throws NotLoggedInException{
		String testlage = (String)request.getSession().getAttribute(SXConstant.SXREG_TESTLAGE);
		if (testlage!=null && testlage.equals("Ja")) {
		} else {
			SXSession sxSession = WebSupport.getSXSession(request.getSession());
			if (!sxSession.checkIntraBehorighetIntraWebApp()) {
				throw new NotLoggedInException();
			}
		}
	}
	
	class TextResponse extends HttpServletResponseWrapper {

		public TextResponse(HttpServletResponse response) {
			super(response);
		}

		private final CharArrayWriter charArray = new CharArrayWriter();
		
		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(charArray);
		}

		public String getOutput() {
			return charArray.toString();
		}	
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
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
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
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
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
