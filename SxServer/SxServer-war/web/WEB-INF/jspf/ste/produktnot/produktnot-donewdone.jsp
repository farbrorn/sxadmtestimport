<%-- 
    Document   : produktnot-donewdone
    Created on : 2009-jul-21, 20:54:34
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>

<%
FormHandlerSteproduktnot f = (FormHandlerSteproduktnot)request.getAttribute("FormHandlerSteproduktnot");

if (f.t.getTableSteproduktnotPK().getSn() != null) {
%>
Notering sparad!
<% } else { %>
Inget sparat
<%}%>
<br/><a href="?id=produktnot&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>
