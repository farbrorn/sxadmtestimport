/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import java.net.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class rapp extends HttpServlet {

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
		try {	con = sxadm.getConnection(); } catch (SQLException e) { SXUtil.log(e.toString());}
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String get = request.getParameter("get");
		String id = request.getParameter("id");
		if (id == null) { id = "1"; }
		RequestDispatcher dispatcher;
		
		HttpSession session = request.getSession();
		SXSession sxSession = WebUtil.getSXSession(session);

		Integer rappId = null;
		try {
			rappId = Integer.parseInt(request.getParameter("rappid"));
		} catch (Exception e) {}

		Integer rappSession = null;
		RappEdit currentRappEdit = null;
		try {
			rappSession = Integer.parseInt(request.getParameter("rappsession"));
			currentRappEdit = sxSession.getArrRappEdit().get(rappSession);
		} catch (Exception e) { SXUtil.log(e.toString());}

		
		// Två rader som fixaar automatisk inloggning för test
		if (!sxSession.getInloggad()) {
			sxSession.setInloggad(true);
			sxSession.setKundnr("0555");
			sxSession.setKundnamn("Grums rör");
			sxSession.setAnvandare("Lokalnvändare");
			sxSession.setIntrauser(true);
			sxSession.setIntraAnvandareKort("UB");
		}
		
		if (!sxSession.getInloggad()) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "login?refpage=rapp");
			return;
		} 
		if (!sxSession.checkIntraBehorighetRapp()) {
			out.println("Ingen behörighet");
			return;
		}
		
		try {
			if (get != null) {			//Vi har en get-request som bara skickar en del av sidan
				if (get.equals("viewrapp")) {
					printRapp(request, response,"", sxSession.getIntraAnvandareKort(), rappId);
				} else {
					out.println("Inga data tillgängliga!");
				}
			} else {			// Om vi inte har någon annan request så antar vi en id-request som ritar en hel sida
				request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
				printTopBar(request,response,"id=\"top\"");
				printLeftSideBar(request,response,"id=\"leftbar\"");
				out.println("<div id=\"body\">");

				if (id.equals("1")) {
					printRappHuvudList(request, response,"id=\"midbar\"");
				} else if (id.equals("2")) {
					printRappInput(request, response,"id=\"midbar\"", sxSession.getIntraAnvandareKort(), rappId);
//					printKundinfo(request, response,"id=\"midbar\"");
//					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("edithuvud")) {
				} else if (id.equals("editcolumn")) {
					out.print("<div id=\"midbar\">" + currentRappEdit.editColumn(request) + "</div>");
				} else if (id.equals("editfilter")) {
					out.print("<div id=\"midbar\">" + currentRappEdit.editFilter(request) + "</div>");
				} else if (id.equals("editsum")) {
					out.print("<div id=\"midbar\">" + currentRappEdit.editSum(request) + "</div>");
				} else if (id.equals("updatehuvud")) {
					out.print("<div id=\"midbar\">");
					try {
						currentRappEdit.updateHuvud(request);
						out.print("Sparat ok!");
						//printRappInput(request, response,"", sxSession.getIntraAnvandareKort(), currentRappEdit.rappId);
					} catch (RappEdit.RappException e) {
						out.print("<b>Något gick fel - " + e.toString() + "</b>");
					}
					printRappEdit(request, response,"", currentRappEdit);
					out.print("</div>");					
				} else if (id.equals("updatecolumn")) {
					out.print("<div id=\"midbar\">");
					try {
						currentRappEdit.updateColumn(request);
						out.print("Sparat ok!");
						printRappEdit(request, response,"", currentRappEdit);
					} catch (RappEdit.RappException e) {
						out.print("<b>Något gick fel - " + e.toString() + "</b>");
						out.print(currentRappEdit.editColumn(request));
					}
					out.print("</div>");
				} else if (id.equals("updatefilter")) {
					out.print("<div id=\"midbar\">");
					try {
						currentRappEdit.updateFilter(request);
						out.print("Sparat ok!");
						printRappEdit(request, response,"", currentRappEdit);
					} catch (RappEdit.RappException e) {
						out.print("<b>Något gick fel - " + e.toString() + "</b>");
						out.print(currentRappEdit.editFilter(request));
					}
					out.print("</div>");
				} else if (id.equals("updatesum")) {
					out.print("<div id=\"midbar\">");
					try {
						currentRappEdit.updateSum(request);
						out.print("Sparat ok!");
						printRappEdit(request, response,"", currentRappEdit);	
					
					} catch (RappEdit.RappException e) {
						out.print("<b>Något gick fel - " + e.toString() + "</b>");
						out.print(currentRappEdit.editSum(request));
					}
					out.print("</div>");

				} else if (id.equals("newcolumn")) {
					out.print("<div id=\"midbar\">");
					out.print(currentRappEdit.editColumn(currentRappEdit.newColumn()));
					out.print("</div>");
				} else if (id.equals("newfilter")) {
					out.print("<div id=\"midbar\">");
					out.print(currentRappEdit.editFilter(currentRappEdit.newFilter()));
					out.print("</div>");
				} else if (id.equals("newsum")) {
					out.print("<div id=\"midbar\">");
					out.print(currentRappEdit.editSum(currentRappEdit.newSum()));
					out.print("</div>");

				} else if (id.equals("deltecolumn")) {
				} else if (id.equals("deletefilter")) {
				} else if (id.equals("deletesum")) {
					
				} else if (id.equals("edit")) {
					printRappEdit(request, response,"id=\"midbar\"", currentRappEdit);
				} else if (id.equals("editrappid")) {
					if (rappId != null) {
						//Null-ställ
						currentRappEdit = null;
						rappSession = null;
						
						// Kollar om det redan finns en session förr ngivet id
						for (RappEdit re : sxSession.getArrRappEdit()) {
							if (re.rappId == rappId) {
								currentRappEdit = re;
								rappSession = re.rappSession;
								break;
							}
						}
						
						// Om vi inte har en rappsession på vald id, såskapar vi en ny
						if (currentRappEdit == null) {
							rappSession = sxSession.getArrRappEdit().size();
							try {
								currentRappEdit = new RappEdit(con,rappSession,rappId);
								sxSession.getArrRappEdit().add(currentRappEdit);
							} catch (Exception e) { rappSession=null; currentRappEdit = null; out.print(e.toString()); }
						}
						printRappEdit(request, response,"id=\"midbar\"", currentRappEdit);
					} else {
						printRappHuvudList(request, response,"id=\"midbar\"");
					}
					
				} else if (id.equals("new")) {
					rappId = null;
					rappSession = sxSession.getArrRappEdit().size();
					try {
						currentRappEdit = new RappEdit(con,rappSession,null);
						sxSession.getArrRappEdit().add(currentRappEdit);
					} catch (Exception e) { rappSession=null; currentRappEdit = null; out.print(e.toString()); }
					printRappEdit(request, response,"id=\"midbar\"", currentRappEdit);
					
				} else if (id.equals("persist")) {
					out.print("<div id=\"midbar\">");
					try {
						currentRappEdit.persist(con);
						out.print("Sparad OK!");
					} catch (Exception e) { out.print(e.toString()); }

					out.print(currentRappEdit.editHuvud());
					out.print("</div>");
				} else if (id.equals("delete")) {

				} else {	
					out.println("Felaktigt id");
				}

				out.println("</div>");
				request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
			}
		} finally { 
			out.close();
			try {con.close();} catch (SQLException e ){}
		}
 } 


private void printRappInput(HttpServletRequest request, HttpServletResponse response, String divInfo, String anvandareKort, int rappId) throws ServletException, IOException{
		if (anvandareKort != null) {
			try {
				RappHTML rh = new RappHTML(sxadm, request);
				response.getWriter().println(rh.printHTMLInputForm(rappId));
			} catch (SQLException e) { SXUtil.log("Undantag vid rapport:" + e.toString()); response.getWriter().println("Undantag vid rapport: " + e.toString()); }
		}
	}
private void printRapp(HttpServletRequest request, HttpServletResponse response, String divInfo, String anvandareKort, int rappId) throws ServletException, IOException{
		request.getRequestDispatcher("/WEB-INF/jspf/rapp/rappheadervisa.jsp").include(request, response);
		if (anvandareKort != null) {
			try {
				RappHTML rh = new RappHTML(sxadm, request);
				rh.prepareFromSQLRepository(rappId);
				response.getWriter().println(rh.print());
			} catch (SQLException e) { SXUtil.log("Undantag vid rapport:" + e.toString()); response.getWriter().println("Undantag vid rapport: " + e.toString()); }
		}
		request.getRequestDispatcher("/WEB-INF/jspf/rapp/rappfootervisa.jsp").include(request, response);
	}

	private void printRappHuvudList(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		try {
			RappHTML r = new RappHTML(sxadm, request);
			ArrayList<RappHTML.RappHuvudList> rl = r.getRappHuvudList();
			request.setAttribute("divinfo", divInfo);
			request.setAttribute("rapphuvudlist", rl);
			request.getRequestDispatcher("WEB-INF/jspf/rapp/printrapphuvudlist.jsp").include(request, response);
		} catch (SQLException e) { SXUtil.log("Undantag vid rapp: " + e.toString()); response.getWriter().println(e.toString());}
	}

	private void printRappEdit(HttpServletRequest request, HttpServletResponse response, String divInfo, RappEdit rappEdit) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.setAttribute("rappedit", rappEdit);
		request.getRequestDispatcher("WEB-INF/jspf/rapp/edit.jsp").include(request, response);
	}
	
	private void printRappColumnEdit(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/rapp/editcolumn.jsp").include(request, response);
	}

	private void printRappFilterEdit(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/rapp/editfilter.jsp").include(request, response);
	}
	private void printRappSumEdit(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/rapp/editsum.jsp").include(request, response);
	}

	private void printLeftSideBar(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/rapp/leftsidebar.jsp").include(request, response);		
	}
	private void printRightSideBar(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/rightsidebar.jsp").include(request, response);		
	}
	private void printTopBar(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/topbar.jsp").include(request, response);		
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
