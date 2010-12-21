<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<%
Connection con = (Connection)request.getAttribute("con");
SXSession sxSession = (SXSession)request.getAttribute("sxsession");
RapportLista rj = new RapportLista();
rj.fillFromDatabase(con);
ArrayList<RapportLista.RapportListaHuvud> rl = rj.getHuvuden();
%>

<h1>Rapporter</h1>
<table>
<%
String tempKategori = "";
String tempUndergrupp = "";
boolean kategoriFirstRow = true;
boolean undergruppFirstRow = true;
for (RapportLista.RapportListaHuvud r : rl) {
	if (rj.isBehorig(r, sxSession.getIntraAnvandare(), con)) {
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
		out.print("<tr>");
		
		if (r.rappid != null) {
			out.print("<td><a href=\"?id=2&rappid=" + r.rappid + "\">" + SXUtil.toHtml(r.kortbeskrivning) + "</a></td>");
			if (sxSession.isAdminuser()) {
				out.print("<td><a href=\"?id=editrappid&rappid=" + r.rappid + "\">Ändra</a></td>");
			}
		} else {
			out.print("<td><a href=\"?id=printjsprapport&inputform=true&jsp=" + r.jsp + "\">" + SXUtil.toHtml(r.kortbeskrivning) + "</a></td>");
		}
	}
	out.print("</tr>");
}
%>
</table>
<% if (sxSession.isAdminuser()) { %> <a href="?id=new">Ny rapport</a> <% } %>