<%-- 
    Document   : pumpartnr-list
    Created on : 2009-jul-23, 13:09:58
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="se.saljex.sxlibrary.*" %>


<%
FormHandlerStepumpartnr f = (FormHandlerStepumpartnr)request.getAttribute("FormHandlerStepumpartnr");
Connection con = (Connection)request.getAttribute("con");
ResultSet rs = con.createStatement().executeQuery("select s.artnr, a.namn from stepumpartnr s left outer join artikel a on a.nummer=s.artnr order by s.artnr");
%>



<table id="doclist">
	<tr>
		<th class="tds10">Artikelnr</th>
		<th class="tds30">Benämning</th>
		<th class="tds10"></th>
		<th></th>
	</tr>
<%
int radcn=0;
while (rs.next()) {
	radcn++;
	if (radcn % 2 > 0) {
		%><tr id="tr<%= radcn %>" class="trdocodd"> <%
	} else {
		%><tr id="tr<%= radcn %>" class="trdoceven"> <%
	}

%>
	<td class="tds10"><%= SXUtil.toHtml(rs.getString(1)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(2)) %></td>
	<td class="tds10"><a href="?id=pumpartnr&<%= f.K_ACTION + "=" + f.ACTION_DELETE + "&" + f.K_ARTNR + "=" + SXUtil.urlEncode(rs.getString(1)) %>">Radera</a></td>
	<td></td>
</tr>
<% } %>
</table>
<a href="?id=pumpartnr&<%= f.K_ACTION + "=" + f.ACTION_NEW  %>">Ny artikel</a>