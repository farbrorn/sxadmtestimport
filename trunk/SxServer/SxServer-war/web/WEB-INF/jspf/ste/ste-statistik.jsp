<%-- 
    Document   : ste-statistik
    Created on : 2009-jul-27, 15:03:50
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>

<%
	String jspName = "ste-statistik";
	SXSession sx = WebUtil.getSXSession(session);
	request.setAttribute("anvandare", sx.getIntraAnvandare());
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>
