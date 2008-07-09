/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.*;
import java.net.*;

import java.sql.SQLException;
import java.util.List;
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
public class kund extends HttpServlet {

	 /** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	@Resource(name = "sxadm")
	private DataSource sxadm;
	
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String get = request.getParameter("get");
		String id = request.getParameter("id");
		if (id == null) { id = "1"; }
			
		RequestDispatcher dispatcher;
		
		HttpSession session = request.getSession();
		SXSession sxSession = WebUtil.getSXSession(session);
		
		// Två rader som fixaar automatisk inloggning för test
		if (!sxSession.getInloggad()) {
			sxSession.setInloggad(true);
			sxSession.setKundnr("0555");
			sxSession.setKundnamn("Grums rör");
			sxSession.setAnvandare("Lokalnvändare");
			sxSession.setInternuser(true);
		}
		
		if (!sxSession.getInloggad()) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "login?refpage=kund");
			return;
		} 
		if (!sxSession.checkBehorighetKund()) {
			out.println("Ingen behörighet");
			return;
		}
		
		try {
			if (get != null) {			//Vi har en get-request som bara skickar en del av sidan
				if (get.equals("faktura")) {
					int nr = 0;
					try {
						nr = Integer.parseInt(request.getParameter("faktnr"));
					} catch (Exception e) {}
					printFakturaInfo(request, response,"", sxSession.getKundnr(), nr);
				} else if (get.equals("order")) {
					int nr = 0;
					try {
						nr = Integer.parseInt(request.getParameter("ordernr"));
					} catch (Exception e) {}
					printOrderInfo(request, response,"", sxSession.getKundnr(), nr);
				} else if (get.equals("kundlista")) {
					if (sxSession.checkInternBehorighetKund()) {
						printKundLista(request, response,"");
					}
				} else if (get.equals("fakturalista")) {
					printFakturaLista(request, response,"", sxSession.getKundnr());
				} else if (get.equals("betjourlista")) {
					printBetjourLista(request, response,"", sxSession.getKundnr());
				} else if (get.equals("statfaktura2")) {
					printStatFaktura2(request, response,"", sxSession.getKundnr());
				} else if (get.equals("statfaktura12")) {
					printStatFaktura12(request, response,"", sxSession.getKundnr());
				} else if (get.equals("utlev1")) {
					printUtlev1(request, response,"", sxSession.getKundnr());
				} else if (get.equals("utlev2")) {
					printUtlev2(request, response,"", sxSession.getKundnr());
				} else {
					out.println("Inga data tillgängliga!");
				}
			} else {			// Om vi inte har någon annan request så antar vi en id-request som ritar en hel sida
				request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
				printTopBar(request,response,"id=\"top\"");
				printLeftSideBar(request,response,"id=\"leftbar\"");
				out.println("<div id=\"body\">");

				if (id.equals("1")) {
					printKundinfo(request, response,"id=\"midbar\"");
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("2")) {
					printFakturaListaSokHuvud(request, response,"id=\"midbar\"");
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("3")) {
					printOrderLista(request, response,"id=\"midbar\"", sxSession.getKundnr());
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("4")) {
					printKundresLista(request, response,"id=\"midbar\"", sxSession.getKundnr());
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("5")) {
					printBetjourListaSokHuvud(request, response,"id=\"midbar\"");
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("6")) {
					printUtlev1SokHuvud(request, response,"id=\"midbar\"");
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("7")) {
					printStatFaktura2SokHuvud(request, response,"id=\"midbar\"");
					printRightSideBar(request,response,"id=\"rightbar\"");
				} else if (id.equals("kundlista")) {
					if (sxSession.checkInternBehorighetKund()) {
						printKundListaSokHuvud(request, response,"id=\"midbar\"");
						printRightSideBar(request,response,"id=\"rightbar\"");
					}
				} else if (id.equals("setkund")) {
					if (sxSession.checkInternBehorighetKund()) {
						setSXSessionKund(request, response,request.getParameter("kundnr"), sxSession);
						printKundinfo(request, response,"id=\"midbar\"");
						printRightSideBar(request,response,"id=\"rightbar\"");				
					}
				} else {	
					out.println("Felaktigt id");
				}

				out.println("</div>");
				request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
			}
		} finally { 
			out.close();
		}
 } 

	private void setSXSessionKund(HttpServletRequest request, HttpServletResponse response, String kundnr, SXSession sxSession) throws ServletException, IOException{
		TableKund kun = LocalWebSupportBean.getTableKund(kundnr);
		if (kun != null) {
			sxSession.setKundnr(kun.getNummer());
			sxSession.setKundnamn(kun.getNamn());
		}
	}
	
	private void printStatFaktura2SokHuvud(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printstatfaktura2sokhuvud.jsp").include(request, response);
	}
	private void printStatFaktura2(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		PageListStatFaktura2 pl = null;
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {page = 1; }
			if (page < 1) page = 1;
			try {
				pl = new PageListStatFaktura2(sxadm, kundnr); 
				pl.setPeriod(request.getParameter("frdat"), request.getParameter("tidat"));
				
				if ("summa".equals(request.getParameter("orderby"))) pl.setOrderBySumma();
				else if ("antalkopta".equals(request.getParameter("orderby"))) pl.setOrderByAntalKopta();
				else if ("antalkop".equals(request.getParameter("orderby"))) pl.setOrderByAntalKop();
				
				pl.getPage(page);
				request.setAttribute("pageliststatfaktura2", pl);
			} catch (SQLException sqe) {}
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printstatfaktura2.jsp").include(request, response);
		try {
			if (pl!=null) pl.close();
		} catch (SQLException sqe) {}
	}

		private void printStatFaktura12(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		PageListStatFaktura12 pl = null;
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {page = 1; }
		if (page < 1) page = 1;
		try {
			pl = new PageListStatFaktura12(sxadm, kundnr, request.getParameter("artnr")); 
			pl.setPeriod(request.getParameter("frdat"), request.getParameter("tidat"));

			pl.getPage(page);
			request.setAttribute("pageliststatfaktura12", pl);
		} catch (SQLException sqe) {}
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printstatfaktura12.jsp").include(request, response);

		try {
			if (pl!=null) pl.close();
		} catch (SQLException sqe) {}
	}

	private void printKundinfo(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printkundinfo.jsp").include(request, response);
	}
	private void printFakturaInfo(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr, int faktnr) throws ServletException, IOException{
		if (faktnr > 0 && kundnr != null) {
			List<TableFaktura2> listTableFaktura2;
			TableFaktura1 tableFaktura1 = LocalWebSupportBean.getTableFaktura1(faktnr);
			if (tableFaktura1 != null) if (tableFaktura1.getKundnr().equals(kundnr)) {
				listTableFaktura2 = LocalWebSupportBean.getListTableFaktura2(faktnr);
				request.setAttribute("tablefaktura1", tableFaktura1);
				request.setAttribute("listtablefaktura2", listTableFaktura2);
			} 
		}
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printfakturainfo.jsp").include(request, response);
	}

private void printOrderLista(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		if (kundnr != null) {
			List<TableOrder1> to =  LocalWebSupportBean.getListTableOrder1(kundnr);
			request.setAttribute("listtableorder1", to);
		}
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printorderlista.jsp").include(request, response);
	}

private void printOrderInfo(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr, int ordernr) throws ServletException, IOException{
		TableOrder1 tableOrder1 = LocalWebSupportBean.getTableOrder1(ordernr);
		if (tableOrder1 != null && kundnr != null) if (tableOrder1.getKundnr().equals(kundnr)) {
			List<TableOrder2> to =  LocalWebSupportBean.getListTableOrder2(ordernr);
			request.setAttribute("tableorder1", tableOrder1);
			request.setAttribute("listtableorder2", to);
		}
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printorderinfo.jsp").include(request, response);
	}

private void printKundresLista(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		if (kundnr != null) {
			List<TableKundres> tk =  LocalWebSupportBean.getListTableKundres(kundnr);
			request.setAttribute("listtablekundres", tk);
		}
		
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printkundreslista.jsp").include(request, response);
	}

private void printBetjourListaSokHuvud(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printbetjourlistasokhuvud.jsp").include(request, response);
	}

private void printBetjourLista(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		PageListBetjour pl = null;
		if (kundnr != null) {
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {page = 1; }
			if (page < 1) page = 1;
			try {
				pl = new PageListBetjour(sxadm, kundnr); 
				pl.setTidat(request.getParameter("tidat"));
				pl.getPage(page);
				request.setAttribute("pagelistbetjour", pl);
			} catch (SQLException sqe) {}
		}
		
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printbetjourlista.jsp").include(request, response);
		try {
			if (pl!=null) pl.close();
		} catch (SQLException sqe) {}
	}
private void printUtlev1SokHuvud(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printutlev1sokhuvud.jsp").include(request, response);
	}

private void printUtlev1(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		PageListUtlev1 pl = null;
		if (kundnr != null) {
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {page = 1; }
			if (page < 1) page = 1;
			try {
				pl = new PageListUtlev1(sxadm, kundnr); 
				pl.setPeriod(request.getParameter("frdat"),request.getParameter("tidat"));
				pl.setOrderBy(request.getParameter("orderby"));
				pl.setMarkeFilter(request.getParameter("marke"));
				pl.getPage(page);
				request.setAttribute("pagelistutlev1", pl);
			} catch (SQLException sqe) {}
		}
		
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printutlev1.jsp").include(request, response);
		try {
			if (pl!=null) pl.close();
		} catch (SQLException sqe) {}
	}

private void printUtlev2(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
	}

private void printKundListaSokHuvud(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printkundlistasokhuvud.jsp").include(request, response);
	}

private void printKundLista(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		PageListKund pl = null;
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {page = 1; }
			if (page < 1) page = 1;
			try {
				pl = new PageListKund(sxadm, request.getParameter("sokstr")); 
				pl.getPage(page);
				request.setAttribute("pagelistkund", pl);
			} catch (SQLException sqe) {}
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printkundlista.jsp").include(request, response);
		try {
			if (pl!=null) pl.close();
		} catch (SQLException sqe) {}
	}

	private void printFakturaListaSokHuvud(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printfakturalistasokhuvud.jsp").include(request, response);
	}
	
	private void printFakturaLista(HttpServletRequest request, HttpServletResponse response, String divInfo, String kundnr) throws ServletException, IOException{
		PageListFaktura1 pl = null;
		if (kundnr != null) {
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {page = 1; }
			if (page < 1) page = 1;
			try {
				pl = new PageListFaktura1(sxadm, kundnr); 
				pl.setTidat(request.getParameter("tidat"));
				pl.getPage(page);
				request.setAttribute("pagelistfaktura1", pl);
			} catch (SQLException sqe) {}
		}
		
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/printfakturalista.jsp").include(request, response);
		try {
			if (pl!=null) pl.close();
		} catch (SQLException sqe) {}
	}
	private void printLeftSideBar(HttpServletRequest request, HttpServletResponse response, String divInfo) throws ServletException, IOException{
		request.setAttribute("divinfo", divInfo);
		request.getRequestDispatcher("WEB-INF/jspf/kund/leftsidebar.jsp").include(request, response);		
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
