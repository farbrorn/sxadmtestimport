/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

/**
 *
 * @author Ulf
 */
@WebFilter(filterName = "IntraFilter", servletNames = {"intra"})
public class IntraFilter extends se.saljex.loginservice.LoginServiceFilter {
	
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
	
	public IntraFilter() {
		super();
	}	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection con=null;
		try {
			con = sxadm.getConnection();
			request.setAttribute("sxconnection", con);
			super.doFilter(request,response,chain);
		} catch (SQLException e) {
			Logger.getLogger("sx-logger").severe("SQL-Fel:" + e.getMessage()); e.printStackTrace();
		} finally { try {con.close(); } catch (Exception eee) {}}		
	}
	
}
