
package se.saljex.sxserver.web;

import se.saljex.sxserver.websupport.WebUtil;
import se.saljex.sxlibrary.SXSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import se.saljex.sxlibrary.WebSupport;

/**
 *
 * @author ulf
 */
public class intra extends HttpServlet {
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		Handler h = new Handler(request,response);
		h = null;
    }
	private class Handler {
		private static final String jspPath = "intra/";

		private Connection con=null;
		private PrintWriter out;

		private HttpServletRequest request;
		private HttpServletResponse response;

		private String id;
		private String get;
		private SXSession sxSession;

		public Handler(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
			request = req;
			response = res;
			out = response.getWriter();

/*		Gammal inloggning
			sxSession = WebSupport.getSXSession(req.getSession());
			if (!sxSession.getInloggad()) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "login?refpage=intra&logintype=intra");
				return;
			}
			if (!sxSession.isIntrauser()) {
				out.println("Ingen behörighet");
				return;
			}

			con = WebUtil.getConnection(sxadm);
*/
			try { con = (Connection)request.getAttribute("sxconnection"); } catch (Exception e) {}
			get = request.getParameter("get");
			id = request.getParameter("id");
			if (id == null) { id = "welcome"; }

			try {
				request.setAttribute("con", con);			// Skicka alltid med connection så vi slipper slå upp den i jsp-filen
				if (get != null) {
					print(jspPath + get + ".jsp");
				} else {
					request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
					printWSide(jspPath + id + ".jsp");
					request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
				}
			} finally {
				try { out.close();	 } catch (Exception e) {}
				try { con.close();} catch (Exception e ){}
			}
		}


		private void print(String s) throws ServletException{
				try {
					request.getRequestDispatcher("WEB-INF/jspf/" + s).include(request, response);
				} catch (IOException e) { out.println("Ogiltig sida"); }
		}

		private void printWSide(String s) throws ServletException, IOException{
			out.print("<div id=\"leftbar\">");
			request.getRequestDispatcher("WEB-INF/jspf/" + jspPath +"leftsidebar.jsp").include(request, response);
			out.print(("</div>"));
			
			out.println("<div id=\"body\">");
			out.print("<div id=\"midbar\">");
			print(s);
			out.print(("</div>"));
			out.print(("</div>"));
		}

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
