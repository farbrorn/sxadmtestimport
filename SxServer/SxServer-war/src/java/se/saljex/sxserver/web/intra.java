/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import se.saljex.sxserver.*;

/**
 *
 * @author ulf
 */
public class intra extends HttpServlet {

	 /** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	@Resource(name = "sxadm")
	private DataSource sxadm;
	
	private Connection con;
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		IntraHandler ih = new IntraHandler(request,response);
		ih = null;
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



	private class IntraHandler {
		private Connection con = null;

		private SXSession sxSession;

		private PrintWriter out;

		private ServletOutputStream outStream;
		private HttpServletRequest request;
		private HttpServletResponse response;

		public IntraHandler(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
			request = req;
			response = res;

			con = WebUtil.getConnection(sxadm);

			out = response.getWriter();
			String get = request.getParameter("get");
			String id = request.getParameter("id");
			if (id == null) { id = "1"; }

			sxSession = WebUtil.getSXSession(request.getSession());

			try {
				if (!sxSession.getInloggad()) {
					response.setStatus(response.SC_MOVED_TEMPORARILY);
					response.setHeader("Location", "login?refpage=intra&logintype=intra");
					return;
				}
				if (!sxSession.isIntrauser()) {
					out.println("Ingen behörighet");
					return;
				}

				if (get != null) {			//Vi har en get-request som bara skickar en del av sidan
				} else {			// Om vi inte har någon annan request så antar vi en id-request som ritar en hel sida
					request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
					printTopBar("id=\"top\"");
					printLeftSideBar("id=\"leftbar\"");
					out.println("<div id=\"body\">");

					if (id.equals("1")) {
						//this.printKalender(request, response, "id=\"leftbar\"");
						printRappTest("id=\"leftbar\"");

					} else {
						out.println("Felaktigt id");
					}

					out.println("</div>");
					request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
				}
			} finally {
				try { out.close();	 } catch (Exception e) {}
				try { con.close();} catch (SQLException e ){}
			}
		}


		private void printKalender(String divInfo) throws ServletException, IOException{
			try {
				PageListKalender pl = new PageListKalender(con);
				pl.getPage(1);
				request.setAttribute("pagelistkalender", pl);
			} catch (SQLException e) { SXUtil.log("Undantag vid printKalender: " + e.toString()); }
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/intra/printkalender.jsp").include(request, response);
		}

		private void printRappTest(String divInfo) throws ServletException, IOException{
			try {
				RappHTML r = new RappHTML(con, request);
				response.getWriter().println(r.print());
			} catch (SQLException e) { SXUtil.log("Undantag vid rapp: " + e.toString()); out.println(e.toString());}
		}


		private void printLeftSideBar(String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/intra/leftsidebar.jsp").include(request, response);
		}

		private void printTopBar(String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/intra/topbar.jsp").include(request, response);
		}

	 }

	}
