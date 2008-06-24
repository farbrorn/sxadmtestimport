<%-- 
    Document   : loginform
    Created on : 2008-jun-16, 21:59:23
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
LoginFormData f = (LoginFormData)request.getAttribute("loginformdata");
if (f == null) { f = new LoginFormData(); }
String refPage = request.getParameter("refpage");
if (refPage == null) { refPage = ""; } else { refPage = "?refpage=" + refPage; }
			  
SXSession sxSession = WebUtil.getSXSession(session);
request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
%>
	<div id="body">
	  <div id="midbar">
		<h2>Logga in</h2>
		<form name="loginfr" action="login<%= refPage %>" method="POST">
			Kundnr: <input type="text" name="kundnr" value="<%= f.kundnr %>" size="20" />
			<div id="errtext"><%= f.kundnrErr %></div>
			<input type="submit" value="Login" name="loginbt" />
		</form>
	 </div>
  </div>
<%
request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
%>
