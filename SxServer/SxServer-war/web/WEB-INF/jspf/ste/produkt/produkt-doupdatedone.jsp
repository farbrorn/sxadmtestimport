<%--
    Document   : produkt-newdodone
    Created on : 2009-jul-18, 07:27:52
    Author     : ulf
--%>


<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
FormHandlerSteprodukt f = (FormHandlerSteprodukt)request.getAttribute("FormHandlerSteprodukt");

if (f.t.getSn() != null) {
%>
<%= SXUtil.toHtml(f.t.getSn()) %><br/>
<%= SXUtil.toHtml(f.t.getModell()) %><br/>
Produkten sparad!
<% } else { %>
Inget sparat
<%}%>
<br/><a href="?id=produkt&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>
