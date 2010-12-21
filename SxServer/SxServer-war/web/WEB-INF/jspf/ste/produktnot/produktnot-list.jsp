<%--
    Document   : produkt-list
    Created on : 2009-jul-18, 07:22:49
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.util.List" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
FormHandlerSteproduktnot f = (FormHandlerSteproduktnot)request.getAttribute("FormHandlerSteproduktnot");
WebTable wt = f.getWebTable();
List<TableSteproduktnot> l = wt.getPage();
TableSteprodukt sp = f.getSteprodukt();
%>

<div id="divdoclist" style="padding-left: 4px;">
	<table id="doclist">
		<tr>
			<td class="tddocheadrubrik">Serienr</td>
			<td><%= SXUtil.toHtml(sp.getSn()) %></td>
			<td class="tddocheadrubrik">Inst.datum</td>
			<td><%= SXUtil.getFormatDate(sp.getInstdatum()) %></td>
		</tr>
		<tr>
			<td class="tddocheadrubrik">Artikelnr</td>
			<td><%= SXUtil.toHtml(sp.getArtnr()) %></td>
			<td class="tddocheadrubrik">Modell</td>
			<td><%= SXUtil.toHtml(sp.getModell()) %></td>
		</tr>
		<tr>
			<td class="tddocheadrubrik">Installatör</td>
			<td><%= SXUtil.toHtml(sp.getInstallatornamn()) %></td>
			<td class="tddocheadrubrik">Slutkund</td>
			<td><%= SXUtil.toHtml(sp.getNamn()) %></td>
		</tr>
	</table>
	<p/>

<table id="doclist">
<%
for (TableSteproduktnot t : l) {
%>
<tr><td>
		<b>
	<%= SXUtil.getFormatDate(t.getCrdt()) %>&nbsp;
	av <%= SXUtil.toHtml(t.getAnvandare()) %>&nbsp;
	Typ:&nbsp;<%= SXUtil.toHtml(t.getArendetyp()) %>&nbsp;
	<% if (t.getFoljuppdatum() != null) { %>
		Följupp:&nbsp;<%= SXUtil.getFormatDate(t.getFoljuppdatum()) %>&nbsp;
	<% } %>
	Q&A: <input disabled="disabled" type="checkbox" <%= t.getPublicerasomqa() > 0 ? "Checked" : "" %>/></b>
	&nbsp;<a href="?getfile=produktnot&<%=f.K_ACTION + "=" + f.K_GETFILE +"&" + f.K_ID + "=" + t.getId() %>" target="_blank"><%= SXUtil.toHtml(t.getFilnamn()) %></a>
	<br/><a href="?id=produktnot&<%= f.K_ACTION + "=" + f.ACTION_UPDATE + "&" + f.K_ID + "=" + t.getId() %>">Ändra</a>
	<% if (f.ARENDETYP_SERVICEORDER.equals(t.getArendetyp())) { %>
	&nbsp;&nbsp;<a href="?getfile=produktnot&<%= f.K_ACTION %>=<%= f.K_GETPDFSERVICEORDER %>&<%= f.K_ID %>=<%= t.getId() %>" target="_blank">Serviceorder som pdf</a>
	<% } %>
	</td></tr>
<tr><td style="padding-left: 8px;">
	<%= SXUtil.toHtml(t.getFraga()) %><p/>
	<%= SXUtil.toHtml(t.getSvar()) %><p/>
</td</tr>
<%
}
%>
</table>
<a href="?id=produktnot&<%= f.K_ACTION + "=" + f.ACTION_NEW + "&" + f.K_SN + "=" + SXUtil.toHtml(sp.getSn()) %>">Ny notering</a>
</div>
