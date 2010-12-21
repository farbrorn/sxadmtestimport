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
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="se.saljex.sxlibrary.*" %>


<%
int ar=0;
int man=0;
try {
	ar = Integer.parseInt(request.getParameter("ar"));
} catch (Exception e) {}
try {
	man = Integer.parseInt(request.getParameter("man"));
} catch (Exception e) {}

java.util.Calendar idag = java.util.Calendar.getInstance();
idag.setTime(new java.util.Date());
if (ar==0) {
	ar = idag.get(java.util.Calendar.YEAR);
}
if (man==0 || man>12 || man<1 ) {
	man = idag.get(java.util.Calendar.MONTH);
}

FormHandlerStepumpartnr f = (FormHandlerStepumpartnr)request.getAttribute("FormHandlerStepumpartnr");
Connection con = (Connection)request.getAttribute("con");
PreparedStatement ps = con.prepareStatement("select p.sn, p.artnr, p.modell, p.instdatum, p.namn, p.adr1, p.adr2, p.adr3 from steproduktnot n, steprodukt p where p.sn = n.sn and n.arendetyp=? and year(n.crdt)=? and month(n.crdt)=? order by n.sn");
ps.setString(1, FormHandlerSteproduktnot.ARENDETYP_DRIFTSATTPROT);
ps.setInt(2, ar);
ps.setInt(3, man);
ResultSet rs = ps.executeQuery();
%>

<form action="" method="get">
	År: <input name="ar" type="text" value="<%= ar %>"> Månad: <input name="man" type="text" value="<%= man %>">
	<input type="hidden" name="id" value="forsakringslista">
	<input type="submit" value="Visa">
</form>

<b>Lista på inrapporterade uppstartsprotokoll för period
<%= "År: " + ar + " Månad: " + man %></b>

<table id="doclist">
	<tr>
		<th class="tds10">SN</th>
		<th class="tds10">Artnr</th>
		<th class="tds30">Modell</th>
		<th class="tds10">Instdat</th>
		<th class="tds30">Namn</th>
		<th class="tds30">Adr1</th>
		<th class="tds30">Adr2</th>
		<th class="tds30">Adr3</th>
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
	<td class="tds10"><%= SXUtil.toHtml(rs.getString(2)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(3)) %></td>
	<td class="tds10"><%= SXUtil.getFormatDate(rs.getDate(4)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(5)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(6)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(7)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(8)) %></td>
	<td></td>
</tr>
<% } %>
</table>
