/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.loginservice;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 *
 * @author Ulf
 */
public class LoginServiceFilter implements Filter {
	@EJB
	private se.saljex.loginservice.LoginServiceBeanRemote loginServiceBean;

	private FilterConfig filterConfig = null;
	
	public LoginServiceFilter() {
	}	
	

	/**
	 *
	 * @param request The servlet request we are processing
	 * @param response The servlet response we are creating
	 * @param chain The filter chain we are processing
	 *
	 * @exception IOException if an input/output error occurs
	 * @exception ServletException if a servlet error occurs
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpReq = null;
		HttpServletResponse httpRes = null;
		try { httpReq = (HttpServletRequest) request; } catch (Exception e) {}
		try { httpRes = (HttpServletResponse) response; } catch (Exception e) {}
		
		Throwable problem = null;
		try { 
			
			User user = null;
			
			if (httpReq!=null && httpRes!=null) {
				
				User tempUser = null;
				User sessionUser = null;
				
				try { sessionUser = (User)httpReq.getSession().getAttribute(LoginServiceConstants.REQUEST_PARAMETER_SESSION_USER); } catch (Exception e) {}
				
				if (sessionUser!=null) {
					user = sessionUser;
					//Förläng kakan
					httpRes.addCookie(Util.createLoginCookie(user.getUuid()));
				} else {
					Cookie co = Util.getLoginCookie(httpReq.getCookies());
					if (co!=null) {
						tempUser = loginServiceBean.loginByUUID(co.getValue());
						if (tempUser!=null) {
							user = tempUser;
						} else {
							//Redirect to loginpage
							httpRes.sendRedirect(LoginServiceConstants.URI_PAGE_LOGIN + "?ref=" + URLEncoder.encode(httpReq.getScheme() + "://" + httpReq.getServerName() + ":" + httpReq.getLocalPort() + httpReq.getRequestURI(),"UTF-8"));
						}
					} else {
						httpRes.sendRedirect(LoginServiceConstants.URI_PAGE_LOGIN + "?ref=" + URLEncoder.encode(httpReq.getScheme() + "://" + httpReq.getServerName() + ":" + httpReq.getLocalPort() + httpReq.getRequestURI(),"UTF-8"));
					}
				}
				
				if (user!=null) {
					httpReq.getSession().setAttribute(LoginServiceConstants.REQUEST_PARAMETER_SESSION_USER, user);
				} 
			}
			
			
			chain.doFilter(request, response);
		} catch (Throwable t) {
			// If an exception is thrown somewhere down the filter chain,
			// we still want to execute our after processing, and then
			// rethrow the problem after that.
			problem = t;
			t.printStackTrace();
		} 
		

		// If there was a problem, we want to rethrow it if it is
		// a known type, otherwise log it.
		if (problem != null) {
			if (problem instanceof ServletException) {
				throw (ServletException) problem;
			}
			if (problem instanceof IOException) {
				throw (IOException) problem;
			}
			sendProcessingError(problem, response);
		}
	}

	/**
	 * Return the filter configuration object for this filter.
	 */
	public FilterConfig getFilterConfig() { 
		return (this.filterConfig);
	}

	/**
	 * Set the filter configuration object for this filter.
	 *
	 * @param filterConfig The filter configuration object
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * Destroy method for this filter
	 */
	@Override
	public void destroy() {		
	}

	/**
	 * Init method for this filter
	 */
	@Override
	public void init(FilterConfig filterConfig) {		
		this.filterConfig = filterConfig;
		if (filterConfig != null) {
		}
	}

	/**
	 * Return a String representation of this object.
	 */
	@Override
	public String toString() {
		if (filterConfig == null) {
			return ("MainFilter()");
		}
		StringBuffer sb = new StringBuffer("MainFilter(");
		sb.append(filterConfig);
		sb.append(")");
		return (sb.toString());
	}
	
	private void sendProcessingError(Throwable t, ServletResponse response) {
		String stackTrace = getStackTrace(t);		
		
		if (stackTrace != null && !stackTrace.equals("")) {
			try {
				response.setContentType("text/html");
				PrintStream ps = new PrintStream(response.getOutputStream());
				PrintWriter pw = new PrintWriter(ps);				
				pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

				// PENDING! Localize this for next official release
				pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");				
				pw.print(stackTrace);				
				pw.print("</pre></body>\n</html>"); //NOI18N
				pw.close();
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		} else {
			try {
				PrintStream ps = new PrintStream(response.getOutputStream());
				t.printStackTrace(ps);
				ps.close();
				response.getOutputStream().close();
			} catch (Exception ex) {
			}
		}
	}
	
	public static String getStackTrace(Throwable t) {
		String stackTrace = null;
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			sw.close();
			stackTrace = sw.getBuffer().toString();
		} catch (Exception ex) {
		}
		return stackTrace;
	}

	/*
	private LoginServiceBeanRemote lookupLoginServiceBeanRemote() {
		try {
			Context c = new InitialContext();
			return (LoginServiceBeanRemote) c.lookup("java:global/LoginServiceApp/LoginService-ejb/LoginServiceBean!se.saljex.loginserviceapp.LoginServiceBeanRemote");
		} catch (NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}
	}
	*/
}
