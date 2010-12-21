<%-- 
    Document   : loginform
    Created on : 2008-jun-16, 21:59:23
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>
<%@ page import="se.saljex.sxlibrary.WebSupport" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SXSession sxSession = WebSupport.getSXSession(session);
request.getRequestDispatcher("/WEB-INF/jspf/siteheader.jsp").include(request, response);
%>
<div id="body">
  <div id="midbar">
		<h2>
		<% if (sxSession.getInloggad()) { 
				out.println("Något blev fel och du är inte utloggad");
			} else {
				out.println("Du är utloggad");
			}
		%>
		</h2>
	</div>
</div>
<%
request.getRequestDispatcher("/WEB-INF/jspf/sitefooter.jsp").include(request, response);
%>
