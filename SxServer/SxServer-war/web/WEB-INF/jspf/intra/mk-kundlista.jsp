<%-- 
    Document   : mk-kundlista
    Created on : 2009-mar-25, 18:24:39
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>


<%
SXSession sx = WebSupport.getSXSession(session);
Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

PreparedStatement p = con.prepareStatement(
"select k.nummer, k.namn, k.adr3, k.ref, count(f1.faktnr), coalesce(sum(f1.t_netto),0) from kund k " +
" left outer join faktura1 f1 on f1.kundnr=k.nummer and f1.datum > current_date-365 " +
" where k.saljare = ? " +
" group by k.nummer, k.namn, k.adr3, k.ref " +
" order by k.namn, k.nummer"
				  );
p.setString(1, sx.getIntraAnvandare());
rs = p.executeQuery();
%>
<h1>Kunder för <%= SXUtil.toHtml(sx.getIntraAnvandare()) %> </h1>
Listar alla kunder på vald säljare samt antal faktuor och fakturerat nettobelopp senaste året
<table id="doclist">
	<tr>
		<th class="tds15">Kundnr</th>
		<th class="tds30">Namn</th>
		<th class="tds30">Ort</th>
		<th class="tds30">Referens</th>
		<th class="tdn12">Fakturor</th>
		<th class="tdn12">Nettofakt</th>
		<th class="tds15">Statistik</th>
		 <th></th>
	</tr>

<%
boolean odd = false;
while (rs.next()) {
	odd=!odd;
%><tr class="<%= odd ? "trdocodd" : "trdoceven" %>">
	<td class="tds15"><a href="kund?id=setkund&kundnr=<%= rs.getString(1) %>"><%= SXUtil.toHtml(rs.getString(1)) %></a></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(2)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(3)) %></td>
	<td class="tds30"><%= SXUtil.toHtml(rs.getString(4)) %></td>
	<td class="tdn12"><%= rs.getInt(5) %></td>
	<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(6),0) %></td>
	<td><a href="?id=rapp-filialstat1kundnr=<%= rs.getString(1) %>">Stat</a>&nbsp;<a href="?id=rapp-saljstatartgrupp&kundnr=<%= rs.getString(1) %>">Grupp</a></td>
<td></td></tr>
<% }
%>
</table>
