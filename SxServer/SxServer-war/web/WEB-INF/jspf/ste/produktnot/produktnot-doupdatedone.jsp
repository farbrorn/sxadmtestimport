<%-- 
    Document   : produktnot-doupdatedone
    Created on : 2009-jul-24, 15:19:16
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
FormHandlerSteproduktnot f = (FormHandlerSteproduktnot)request.getAttribute("FormHandlerSteproduktnot");

if (f.t.getSn() != null) {
%>
<%= SXUtil.toHtml(f.t.getFraga()) %><p/>
<%= SXUtil.toHtml(f.t.getSvar()) %><p/>
Notering sparad!
<p/><a href="?id=produktnot&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>&<%= f.K_SN + "=" + f.t.getSn() %>">Tillbaka</a>
<% } else { %>
Inget sparat
<%}%>
