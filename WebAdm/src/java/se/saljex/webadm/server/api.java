/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.server;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import se.saljex.sxlibrary.*;
import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxserver.SxServerMainLocal;
import se.saljex.webadm.client.rpcobject.NotLoggedInException;

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
	@Resource(name = "sxadm")
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

		RequestHandler.setLocalWebSupportLocal(request, LocalWebSupportLocal);
		RequestHandler.setSxServerMainRemote(request, SxServerMainRemote);
		RequestHandler.setSxServerMainLocal(request, SxServerMainBean);
		RequestHandler.setSxadm(request, sxadm);
		
		SXSession sxSession = WebSupport.getSXSession(request.getSession());
		
		if (sxSession.checkIntraBehorighetIntraWebApp())
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String path = SXUtil.toStr(request.getPathInfo());
		if (path.length() < 1) path = "/";
		else if (path.charAt(path.length()-1) != '/')  path = path + "/";
		
		try {
			ensureLoggedIn(request);
			try{
				request.getRequestDispatcher("/WEB-INF/jsp/api" + path +"index.jsp").include(request, response);
			} catch (IOException e) { out.println("Ogiltig sida"); }

		
		} catch (NotLoggedInException e) {
			out.print("Ingen behörighet");
		} finally {			
			out.close();
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
