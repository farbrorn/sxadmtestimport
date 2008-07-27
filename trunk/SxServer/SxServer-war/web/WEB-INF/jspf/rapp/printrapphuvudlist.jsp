<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.ArrayList" %>

<% 
ArrayList<RappHTML.RappHuvudList> rl = (ArrayList<RappHTML.RappHuvudList>)request.getAttribute("rapphuvudlist"); 
String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>

<div <%= divInfo %>>
<h1>Rapporter</h1>
<table>
<%
String tempKategori = "";
String tempUndergrupp = "";
boolean kategoriFirstRow = true;
boolean undergruppFirstRow = true;
for (RappHTML.RappHuvudList r : rl) {
	if (r.kategori == null) { r.kategori = ""; }
	if (r.undergrupp == null) { r.undergrupp = ""; }
	if (kategoriFirstRow || !tempKategori.equals(r.kategori)) {
		out.print("<tr><td><h2>" + SXUtil.toHtml(r.kategori) + "</h2></td></tr>");
		tempKategori = r.kategori;
		kategoriFirstRow = false;
	}
	if (undergruppFirstRow || !tempUndergrupp.equals(r.undergrupp)) {
		out.print("<tr><td><h3>" + SXUtil.toHtml(r.undergrupp) + "</h3></td></tr>");
		tempUndergrupp = r.undergrupp;
		undergruppFirstRow = false;
	}
%>
<tr>
<td><%= "<a href=\"?id=2&rappid=" + r.rappid + "\">" + SXUtil.toHtml(r.kortbeskrivning) + "</a>" %></td>
</tr>
<%
}
%>
</table>
</div>