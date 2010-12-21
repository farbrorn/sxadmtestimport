<%-- 
    Document   : pumpartnr-new
    Created on : 2009-jul-23, 13:09:45
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
<h1>Registrera ny artikel</h1>
<% if (f.isFormError()) { %> <div id="errortext"> <%= f.getFormError() %> </div> <% } %>
<form action="?id=pumpartnr&<%= f.K_ACTION %>=<%= f.getNextFormAction() %>" method="post">
<table>
	<tr>
		<td>Artikelnr:</td>
		<td><input type="text" name="<%= f.K_ARTNR %>" value="<%= SXUtil.toHtml(f.t.getArtnr()) %>"/></td>
	</tr>
	<tr><td colspan="2"><input type="submit" value="Spara"/></td></tr>
</table>
</form>

<a href="?id=pumpartnr&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>