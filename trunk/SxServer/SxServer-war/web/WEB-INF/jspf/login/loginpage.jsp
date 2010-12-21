<%-- 
    Document   : loginform
    Created on : 2008-jun-16, 21:59:23
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.WebSupport" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<%
LoginFormData f = (LoginFormData)request.getAttribute("loginformdata");
if (f == null) { f = new LoginFormData(); }
String refPage = request.getParameter("refpage");
if (refPage == null) { refPage = "kund"; }
String loginType = request.getParameter("logintype");
if (loginType == null) { loginType = "kund"; }
			  
SXSession sxSession = WebSupport.getSXSession(session);
request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
%>
	<div id="body">
	  <div id="midbar">
		<h2>Logga in</h2>
		<form name="loginfr" action="login?refpage=<%= refPage %>&logintype=<%= loginType %>" method="POST">
			<table>
				<tr><td>Användare</td><td><input type="text" name="anvandare" value="<%= f.anvandare %>" size="20" /></td><td><span id="errtext"><%= f.anvandareErr %></span></td></tr>
				<tr><td>Lösen</td><td><input type="password" name="losen" value="" size="20" /></td><td></td></tr>
				<tr><td colspan="3"><input type="submit" value="Login" name="loginbt" /></td></tr>
			</table>
		</form>
	 </div>
  </div>
<%
request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
%>
