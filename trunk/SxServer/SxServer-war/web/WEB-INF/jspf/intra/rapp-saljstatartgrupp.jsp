<%-- 
    Document   : rapp-saljstatartgrupp
    Created on : 2009-mar-22, 18:24:38
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="java.sql.*" %>

<%
String jspName = "saljstatartgrupp";
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>

