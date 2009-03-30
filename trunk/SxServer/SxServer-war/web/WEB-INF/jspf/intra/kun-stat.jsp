<%-- 
    Document   : kun-stat
    Created on : 2009-mar-28, 12:40:23
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
	String jspName = "kun-stat";
	SXSession sx = WebUtil.getSXSession(session);
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>
