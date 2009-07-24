<%-- 
    Document   : produktnot-new
    Created on : 2009-jul-21, 16:41:07
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>

<%
FormHandlerSteproduktnot f = (FormHandlerSteproduktnot)request.getAttribute("FormHandlerSteproduktnot");
%>
<h1>Registrera ny produkt</h1>
<% if (f.isFormError()) { %> <div id="errortext"> <%= f.getFormError() %> </div> <% } %>
<form action="?id=produktnot&<%= f.K_ACTION %>=<%= f.getNextFormAction() %>&<%= f.K_SN %>=<%= request.getParameter(f.K_SN) %>" method="post" enctype="multipart/form-data">
	<input type="hidden" name="dummy" value="test"/>
<table>
	<tr>
		<td>Serienr:</td>
		<td><%= SXUtil.toHtml(request.getParameter(f.K_SN)) %></td>
	</tr>
	<tr>
		<td valign="top">Notering:</td>
		<td><textarea rows="20" cols="80" name="<%= f.K_NOTERING %>"><%= SXUtil.toHtml(f.t.getNotering()) %></textarea></td>
	</tr>
	<tr>
		<td>Bifogad fil:</td>
		<td><input type="file" name="<%= f.K_FILNAMN %>" value=""/></td>
	</tr>
	<tr><td colspan="2"><input type="submit" value="Spara"/></td></tr>
</table>
</form>

<a href="?id=produktnot&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>