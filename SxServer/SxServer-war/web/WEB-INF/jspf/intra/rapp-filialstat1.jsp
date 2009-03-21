<%-- 
    Document   : rapp-filialstat1
    Created on : 2009-mar-21, 15:50:12
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="java.sql.*" %>

<%
String jspName = "filialstat1";
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>

