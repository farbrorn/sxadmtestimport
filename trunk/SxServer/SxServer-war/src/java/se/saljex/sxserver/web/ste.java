
package se.saljex.sxserver.web;

import se.saljex.sxserver.websupport.WebUtil;
import se.saljex.sxlibrary.SXSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.WebSupport;

/**
 *
 * @author ulf
 */
@PersistenceContext(name="sxem", unitName="SxServer-warPU")
public class ste extends HttpServlet {
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
@Resource UserTransaction utx;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
//HtmlFileUpload htmlFileUpload = new HtmlFileUpload(request);
/*ServletInputStream in = request.getInputStream();
		Vector vBytes = new Vector();
		Vector vFileBytes = new Vector();
		byte[] b = new byte[1];
		byte[] a = new byte[request.getContentLength()+1000];
		int cn = 0;
while(in.read(b) > -1) { a[cn] = b[0]; cn++; }
		String s = new String(a);
System.out.print("zzzzz-"+s);
*/
		EntityManager em = null;
		try {
			Context ic = new InitialContext();
			em = (EntityManager) ic.lookup("java:comp/env/sxem");
		} catch (NamingException e) { e.printStackTrace(); }
		Handler h = new Handler(em, request,response);
		h = null;
    }
	private class Handler {
		private static final String jspPath = "ste/";

		private Connection con;
		private PrintWriter out;

		private HttpServletRequest request;
		private HttpServletResponse response;

		private String id;
		private String get;
		private String getfile;
		private SXSession sxSession;

		public Handler(EntityManager em, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
			request = req;
			response = res;

			get = request.getParameter("get");
			getfile = request.getParameter("getfile");
			id = request.getParameter("id");
			if (id == null) { id = "welcome"; }
			

			sxSession = WebSupport.getSXSession(req.getSession());
			if (!sxSession.getInloggad()) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "login?refpage=ste&logintype=intra");
				return;
			}
			if (!sxSession.isIntrauser() && !sxSession.isBehorighet(SXConstant.BEHORIGHET_STE_TEKNIK)) {

				out = response.getWriter();
				out.println("Ingen behörighet");
				return;
			}


			request.setAttribute("em", em);
			request.setAttribute("utx", utx);

			if (SXUtil.isEmpty(getfile)) {
				con = WebUtil.getConnection(sxadm);
				try {
					request.setAttribute("con", con);			// Skicka alltid med connection så vi slipper slå upp den i jsp-filen
					if (!SXUtil.isEmpty(get)) {
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
			} else {
				// Vi efterfrågar en fil, och vill därför inte öppna någon getWriter eller annat konstigt
				if ("produktnot".equals(getfile)) {
					FormHandlerSteproduktnot f = new FormHandlerSteproduktnot(em, utx, "", request, response);
					f.handleOtherAction();
				}
				if (FormHandlerSteproduktnot.K_GETPDFSERVICEORDER.equals(getfile)) {
					FormHandlerSteproduktnot f = new FormHandlerSteproduktnot(em, utx, "", request, response);
					f.handleOtherAction();
				}
			}
		}


		private void print(String s) throws ServletException{
				try {
					request.getRequestDispatcher("WEB-INF/jspf/" + s).include(request, response);
				} catch (IOException e) { out.println("Ogiltig sida"); }
		}

		private void printWSide(String s) throws ServletException, IOException{
			out = response.getWriter();
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
