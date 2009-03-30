<%-- 
    Document   : mk-statistik
    Created on : 2009-mar-26, 19:04:16
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
	String jspName = "mk-statistik";
	SXSession sx = WebUtil.getSXSession(session);
	request.setAttribute("anvandare", sx.getIntraAnvandare());
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>
