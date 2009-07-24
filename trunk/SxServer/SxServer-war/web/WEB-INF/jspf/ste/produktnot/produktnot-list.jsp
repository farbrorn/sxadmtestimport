<%--
    Document   : produkt-list
    Created on : 2009-jul-18, 07:22:49
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.util.List" %>
sddsd
<%
FormHandlerSteproduktnot f = (FormHandlerSteproduktnot)request.getAttribute("FormHandlerSteproduktnot");
WebTable wt = f.getWebTable();
List<TableSteproduktnot> l = wt.getPage();
TableSteprodukt sp = f.getSteprodukt();
%>

<div id="divdoclist">
	<table id="doclist">
		<tr>
			<td>Serienr</td>
			<td><%= SXUtil.toHtml(sp.getSn()) %></td>
			<td>Inst.datum</td>
			<td><%= SXUtil.getFormatDate(sp.getInstdatum()) %></td>
		</tr>
		<tr>
			<td>Artikelnr</td>
			<td><%= SXUtil.toHtml(sp.getArtnr()) %></td>
			<td>Modell</td>
			<td><%= SXUtil.toHtml(sp.getModell()) %></td>
		</tr>
		<tr>
			<td>Installatör</td>
			<td><%= SXUtil.toHtml(sp.getInstallatornamn()) %></td>
			<td>Slutkund</td>
			<td><%= SXUtil.toHtml(sp.getNamn()) %></td>
		</tr>
	</table>

<table id="doclist">
	<tr>
		<th class="tds10">Datum</th>
		<th class="tds10">Anvandare</th>
		<th class="tds60">Notering</th>
		<th class="tds30">Bilaga</th>
	</tr>
<%
for (TableSteproduktnot t : l) {
%>
<tr>
	<td class="tds10"><%= SXUtil.getFormatDate(t.getCrdt()) %></td>
	<td class="tds10"><%= SXUtil.toHtml(t.getAnvandare()) %></td>
	<td class="tds60"><%= SXUtil.toHtml(t.getNotering()) %></td>
	<td class="tds30"><a href="?getfile=produktnot&<%=f.K_ACTION + "=" + f.ACTION_GETFILE +"&" + f.K_SN + "=" + SXUtil.toHtml(t.getTableSteproduktnotPK().getSn())+"&" + f.K_ID + "=" + t.getTableSteproduktnotPK().getId() %>" target="_blank"><%= SXUtil.toHtml(t.getFilnamn()) %></a></td>
</tr>
<%
}
%>
</table>
<a href="?id=produktnot&<%= f.K_ACTION + "=" + f.ACTION_NEW + "&" + f.K_SN + "=" + SXUtil.toHtml(sp.getSn()) %>">Ny notering</a>
</div>
