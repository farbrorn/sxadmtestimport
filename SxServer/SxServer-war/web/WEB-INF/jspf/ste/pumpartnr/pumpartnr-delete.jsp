<%-- 
    Document   : pumpartnr-delete
    Created on : 2009-jul-23, 13:10:13
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
FormHandlerStepumpartnr f = (FormHandlerStepumpartnr)request.getAttribute("FormHandlerStepumpartnr");
%>
<h1>Radera artikel?</h1>
<form action="?id=pumpartnr&<%= f.K_ACTION %>=<%= f.getNextFormAction() %>" method="post">
<input type="hidden" name="<%= f.K_ARTNR %>" value="<%= SXUtil.toHtml(f.t.getArtnr()) %>"/>
<table>
	<tr>
		<td>Artikelnr:</td>
		<td><%= SXUtil.toHtml(f.t.getArtnr()) %></td>
	</tr>
	<tr><td colspan="2"><input type="submit" value="Radera artikel"/></td></tr>
</table>
</form>

<a href="?id=pumpartnr&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>