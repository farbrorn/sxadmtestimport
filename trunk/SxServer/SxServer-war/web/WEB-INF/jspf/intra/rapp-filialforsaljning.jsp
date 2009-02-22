<%-- 
    Document   : rapp-filialforsaljning
    Created on : 2009-feb-17, 19:08:36
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>

<%
RapportLista jr = new RapportLista();
jr.printJspRapport(request, response, "filialforsaljning");
%>
