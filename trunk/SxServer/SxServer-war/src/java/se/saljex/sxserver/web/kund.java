package se.saljex.sxserver.web;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.websupport.WebUtil;
import se.saljex.sxlibrary.SXSession;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableKundres;
import se.saljex.sxserver.tables.TableOrder2;
import java.io.*;
import se.saljex.sxlibrary.WebSupport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import se.saljex.sxserver.*;

/**
 *
 * @author ulf
 */
public class kund extends HttpServlet {
	
	@EJB
	private LocalWebSupportLocal LocalWebSupportBean;
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
@PersistenceUnit
private EntityManagerFactory emf;
	

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
	 KundHandler k = new KundHandler(request,response); // Här händer allt jobb direkt
		k = null;
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


	 private class KundHandler {
			private Connection con = null;

			private SXSession sxSession;

			private PrintWriter out;

			private ServletOutputStream outStream;
			private HttpServletRequest request;
			private HttpServletResponse response;
			private EntityManager em;

			public KundHandler(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				request = req;
				response = res;

				con = WebUtil.getConnection(sxadm);
				em = emf.createEntityManager();


				String get = request.getParameter("get");
				String id = request.getParameter("id");
				if (id == null) { id = "1"; }

				try {
					sxSession = WebSupport.getSXSession(request.getSession());

					// Två rader som fixaar automatisk inloggning för test
		/*			if (!sxSession.getInloggad()) {
						sxSession.setInloggad(true);
						sxSession.setKundnr("0555");
						sxSession.setKundnamn("Grums rör");
						sxSession.setAnvandare("Lokalnvändare");
						sxSession.setIntrauser(true);
					}
		*/
					if (!sxSession.getInloggad()) {
						response.setStatus(response.SC_MOVED_TEMPORARILY);
						response.setHeader("Location", "login?refpage=kund&logintype=kund");
						return;
					}

					if (!sxSession.checkBehorighetKund()) {
						out = response.getWriter();
						out.println("Ingen behörighet");
						out.close();
						return;
					}

					if (sxSession.getKundnr() == null && sxSession.checkIntraBehorighetKund() && !id.equals("setkund")) {
						id = "kundlista";
					}

					if (get != null) {
						handleGet(get);
					} else {
						handleId(id);
					}
				} finally {
					try { out.close();	 } catch (Exception e) {}
					try { outStream.close();	 } catch (Exception e) {}
					try { con.close();} catch (Exception e ){}
					try { em.close();} catch (Exception e ){}
				}

			}





		private void handleGet(String get) throws IOException, ServletException {
			// Kolla först om vi har en request för pdf-fil
			if (get.equals("pdffaktura")) {
				outStream = response.getOutputStream();
			} else {
				out = response.getWriter();
			}

			if (get.equals("faktura")) {
				int nr = 0;
				try {
					nr = Integer.parseInt(request.getParameter("faktnr"));
				} catch (Exception e) {}
				printFakturaInfo("", sxSession.getKundnr(), nr);
			} else if (get.equals("order")) {
				int nr = 0;
				try {
					nr = Integer.parseInt(request.getParameter("ordernr"));
				} catch (Exception e) {}
				printOrderInfo("", sxSession.getKundnr(), nr);
			} else if (get.equals("kundlista")) {
				if (sxSession.checkIntraBehorighetKund()) {
					printKundLista("");
				}
			} else if (get.equals("fakturalista")) {
				printFakturaLista("", sxSession.getKundnr());
			} else if (get.equals("betjourlista")) {
				printBetjourLista("", sxSession.getKundnr());
			} else if (get.equals("statfaktura2")) {
				printStatFaktura2("", sxSession.getKundnr());
			} else if (get.equals("statfaktura12")) {
				printStatFaktura12("", sxSession.getKundnr());
			} else if (get.equals("utlev1")) {
				printUtlev1("", sxSession.getKundnr());
			} else if (get.equals("pdffaktura")) {
				int nr = 0;
				try {
					nr = Integer.parseInt(request.getParameter("faktnr"));
				} catch (Exception e) {}
				try {
					WebSupport.sendPdf(LocalWebSupportBean.getPdfFaktura(nr, sxSession.getKundnr()), outStream, response);
				} catch (com.lowagie.text.DocumentException e) {ServerUtil.log(e.toString());}

			} else if (get.equals("utlev2")) {
				int nr = 0;
				try {
					nr = Integer.parseInt(request.getParameter("ordernr"));
				} catch (Exception e) {}
				printUtlev2("", sxSession.getKundnr(), nr);
			} else {
				out.println("Inga data tillgängliga!");
			}
		}

		private void handleId(String id) throws IOException, ServletException{
			out = response.getWriter();
			request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
			//printTopBar(request,response,"id=\"top\"");
			printLeftSideBar("id=\"leftbar\"");
			out.println("<div id=\"body\">");

			if (id.equals("1")) {
				printKundinfo("id=\"midbar\"");
			} else if (id.equals("2")) {
				printFakturaListaSokHuvud("id=\"midbar\"");
			} else if (id.equals("3")) {
				printOrderLista("id=\"midbar\"", sxSession.getKundnr());
			} else if (id.equals("editorder")) {
				printEditOrder("id=\"midbar\"", sxSession.getKundnr());
			} else if (id.equals("4")) {
				printKundresLista("id=\"midbar\"", sxSession.getKundnr());
			} else if (id.equals("5")) {
				printBetjourListaSokHuvud("id=\"midbar\"");
			} else if (id.equals("6")) {
				printUtlev1SokHuvud("id=\"midbar\"");
			} else if (id.equals("7")) {
				printStatFaktura2SokHuvud("id=\"midbar\"");
			} else if (id.equals("kundlista")) {
				if (sxSession.checkIntraBehorighetKund()) {
					printKundListaSokHuvud("id=\"midbar\"");
				}
			} else if (id.equals("setkund")) {
				if (sxSession.checkIntraBehorighetKund()) {
					setSXSessionKund(request.getParameter("kundnr"), sxSession);
					printKundinfo("id=\"midbar\"");
				}
			} else {
				out.println("Felaktigt id");
			}

			out.println("</div>");
			request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);

		}

		private void setSXSessionKund(String kundnr, SXSession sxSession) throws ServletException, IOException{
			TableKund kun = LocalWebSupportBean.getTableKund(kundnr);
			if (kun != null) {
				sxSession.setKundnr(kun.getNummer());
				sxSession.setKundnamn(kun.getNamn());
			}
		}

		private void printStatFaktura2SokHuvud(String divInfo) throws ServletException, IOException{
				request.setAttribute("divinfo", divInfo);
				request.getRequestDispatcher("WEB-INF/jspf/kund/printstatfaktura2sokhuvud.jsp").include(request, response);
		}
		private void printStatFaktura2(String divInfo, String kundnr) throws ServletException, IOException{
			PageListStatFaktura2 pl = null;
				int page;
				try {
					page = Integer.parseInt(request.getParameter("page"));
				} catch (Exception e) {page = 1; }
				if (page < 1) page = 1;
				try {
					pl = new PageListStatFaktura2(con, kundnr);
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

			private void printStatFaktura12(String divInfo, String kundnr) throws ServletException, IOException{
			PageListStatFaktura12 pl = null;
			int page;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {page = 1; }
			if (page < 1) page = 1;
			try {
				pl = new PageListStatFaktura12(con, kundnr, request.getParameter("artnr"));
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

		private void printKundinfo(String divInfo) throws ServletException, IOException{
			request.setAttribute("kund", LocalWebSupportBean.getTableKund(sxSession.getKundnr()));
			request.setAttribute("kundkontakt", LocalWebSupportBean.getTableKundkontakt(sxSession.getKundKontaktId()));
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printkundinfo.jsp").include(request, response);
		}
		private void printFakturaInfo(String divInfo, String kundnr, int faktnr) throws ServletException, IOException{
			if (faktnr > 0 && kundnr != null) {
				TableFaktura1 tableFaktura1 = em.find(TableFaktura1.class,faktnr);// = LocalWebSupportBean.getTableFaktura1(faktnr);
				if (tableFaktura1 != null) if (tableFaktura1.getKundnr().equals(kundnr)) {
					//listTableFaktura2 = LocalWebSupportBean.getListTableFaktura2(faktnr);
					List<TableFaktura2> listTableFaktura2 = em.createNamedQuery("TableFaktura2.findByFaktnr").setParameter("faktnr", faktnr).getResultList();
					request.setAttribute("tablefaktura1", tableFaktura1);
					request.setAttribute("listtablefaktura2", listTableFaktura2);
				}
			}
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printfakturainfo.jsp").include(request, response);
		}

	private void printOrderLista(String divInfo, String kundnr) throws ServletException, IOException{
			if (kundnr != null) {
				List<TableOrder1> to =  LocalWebSupportBean.getListTableOrder1(kundnr);
				request.setAttribute("listtableorder1", to);
			}
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printorderlista.jsp").include(request, response);
		}

	private void printOrderInfo(String divInfo, String kundnr, int ordernr) throws ServletException, IOException{
			TableOrder1 tableOrder1 = LocalWebSupportBean.getTableOrder1(ordernr);
			if (tableOrder1 != null && kundnr != null) if (tableOrder1.getKundnr().equals(kundnr)) {
				List<TableOrder2> to =  LocalWebSupportBean.getListTableOrder2(ordernr);
				request.setAttribute("tableorder1", tableOrder1);
				request.setAttribute("listtableorder2", to);
			}
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printorderinfo.jsp").include(request, response);
		}

	private void printEditOrder(String divInfo, String kundnr) throws ServletException, IOException{
			int ordernr;
			try {
				ordernr = Integer.parseInt(request.getParameter("ordernr"));
			} catch (Exception e) {ordernr = 0; }
			TableOrder1 tableOrder1 = LocalWebSupportBean.getTableOrder1(ordernr);
			if (tableOrder1 != null && kundnr != null) if (tableOrder1.getKundnr().equals(kundnr)) {
				List<TableOrder2> to =  LocalWebSupportBean.getListTableOrder2(ordernr);
				request.setAttribute("tableorder1", tableOrder1);
				request.setAttribute("listtableorder2", to);
			}
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/editorder.jsp").include(request, response);
		}


	private void printKundresLista(String divInfo, String kundnr) throws ServletException, IOException{
			if (kundnr != null) {
				List<TableKundres> tk =  LocalWebSupportBean.getListTableKundres(kundnr);
				request.setAttribute("listtablekundres", tk);
			}

			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printkundreslista.jsp").include(request, response);
		}

	private void printBetjourListaSokHuvud(String divInfo) throws ServletException, IOException{
				request.setAttribute("divinfo", divInfo);
				request.getRequestDispatcher("WEB-INF/jspf/kund/printbetjourlistasokhuvud.jsp").include(request, response);
		}

	private void printBetjourLista(String divInfo, String kundnr) throws ServletException, IOException{
			PageListBetjour pl = null;
			if (kundnr != null) {
				int page;
				try {
					page = Integer.parseInt(request.getParameter("page"));
				} catch (Exception e) {page = 1; }
				if (page < 1) page = 1;
				try {
					pl = new PageListBetjour(con, kundnr);
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
	private void printUtlev1SokHuvud(String divInfo) throws ServletException, IOException{
				request.setAttribute("divinfo", divInfo);
				request.getRequestDispatcher("WEB-INF/jspf/kund/printutlev1sokhuvud.jsp").include(request, response);
		}

	private void printUtlev1(String divInfo, String kundnr) throws ServletException, IOException{
			PageListUtlev1 pl = null;
			if (kundnr != null) {
				int page;
				try {
					page = Integer.parseInt(request.getParameter("page"));
				} catch (Exception e) {page = 1; }
				if (page < 1) page = 1;
				try {
					pl = new PageListUtlev1(con, kundnr);
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

	private void printUtlev2(String divInfo, String kundnr, int ordernr) throws ServletException, IOException{
			PageListUtlev2 pl = null;
			if (kundnr != null && ordernr > 0) {
				try {
					pl = new PageListUtlev2(con, kundnr, ordernr);
					pl.getPage(1);
					request.setAttribute("pagelistutlev2", pl);
				} catch (SQLException sqe) {}
			}

			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printutlev2.jsp").include(request, response);
			try {
				if (pl!=null) pl.close();
			} catch (SQLException sqe) {}
		}

	private void printKundListaSokHuvud(String divInfo) throws ServletException, IOException{
				request.setAttribute("divinfo", divInfo);
				request.getRequestDispatcher("WEB-INF/jspf/kund/printkundlistasokhuvud.jsp").include(request, response);
		}

	private void printKundLista(String divInfo) throws ServletException, IOException{
			PageListKund pl = null;
				int page;
				try {
					page = Integer.parseInt(request.getParameter("page"));
				} catch (Exception e) {page = 1; }
				if (page < 1) page = 1;
				try {
					pl = new PageListKund(con, request.getParameter("sokstr"));
					pl.getPage(page);
					request.setAttribute("pagelistkund", pl);
				} catch (SQLException sqe) {}
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/printkundlista.jsp").include(request, response);
			try {
				if (pl!=null) pl.close();
			} catch (SQLException sqe) {}
		}

		private void printFakturaListaSokHuvud(String divInfo) throws ServletException, IOException{
				request.setAttribute("divinfo", divInfo);
				request.getRequestDispatcher("WEB-INF/jspf/kund/printfakturalistasokhuvud.jsp").include(request, response);
		}

		private void printFakturaLista(String divInfo, String kundnr) throws ServletException, IOException{
			PageListFaktura1 pl = null;
			if (kundnr != null) {
				int page;
				try {
					page = Integer.parseInt(request.getParameter("page"));
				} catch (Exception e) {page = 1; }
				if (page < 1) page = 1;
				try {
					pl = new PageListFaktura1(con, kundnr);
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
		private void printLeftSideBar(String divInfo) throws ServletException, IOException{
			if (sxSession.isIntrauser()) {
				request.setAttribute("intrauser", "ja");
			}
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/leftsidebar.jsp").include(request, response);
		}
		private void printRightSideBar(String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/rightsidebar.jsp").include(request, response);
		}
		private void printTopBar(String divInfo) throws ServletException, IOException{
			request.setAttribute("divinfo", divInfo);
			request.getRequestDispatcher("WEB-INF/jspf/kund/topbar.jsp").include(request, response);
		}
	}

}
