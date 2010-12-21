<%-- 
    Document   : mk-statistik
    Created on : 2009-mar-26, 19:04:16
    Author     : ulf
--%>

<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<%
	String jspName = "mk-statistik";
	SXSession sx = WebSupport.getSXSession(session);
	request.setAttribute("anvandare", sx.getIntraAnvandare());
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>
