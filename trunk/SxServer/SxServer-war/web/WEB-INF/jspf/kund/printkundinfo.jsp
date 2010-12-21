<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<% 
SXSession sxSession = WebSupport.getSXSession(session);
TableKund k = (TableKund)request.getAttribute("kund");
TableKundkontakt kk = (TableKundkontakt)request.getAttribute("kundkontakt");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<h1>Kontoinformation</h1>

<table class="art" border="1">
<tbody>
<tr>
<td>Kundnr</td>
<td><%= SXUtil.toHtml(k.getNummer()) %></td>
</tr>
<tr>
<td>Namn</td>
<td><%= SXUtil.toHtml(k.getNamn()) %></td>
</tr>
<tr>
<td>Fakturaadress</td>
<td><%= SXUtil.toHtml(k.getAdr1()) %></td>
</tr>
<tr>
<td></td>
<td><%= SXUtil.toHtml(k.getAdr2()) %></td>
</tr>
<tr>
<td></td>
<td><%= SXUtil.toHtml(k.getAdr3()) %></td>
</tr>
<tr>
<td>Leveransadress</td>
<td><%= SXUtil.toHtml(k.getLnamn()) %></td>
</tr>
<tr>
<td></td>
<td><%= SXUtil.toHtml(k.getLadr2()) %></td>
</tr>
<tr>
<td></td>
<td><%= SXUtil.toHtml(k.getLadr3()) %></td>
</tr>
</tbody>
</table>
<% if (kk != null) { %>
<p/>
<h1>Användaruppgifter</h1>
<table>
	<tr>
	<td>Namn</td><td><%= SXUtil.toHtml(kk.getNamn()) %></td>
	</tr>
	<tr>
	<td>Adress</td><td><%= SXUtil.toHtml(kk.getAdr1()) %></td>
	</tr>
	<tr>
	<td></td><td><%= SXUtil.toHtml(kk.getAdr2()) %></td>
	</tr>
	<tr>
	<td></td><td><%= SXUtil.toHtml(kk.getAdr3()) %></td>
	</tr>
	<tr>
	<td>Tel</td><td><%= SXUtil.toHtml(kk.getTel()) %></td>
	</tr>
	<tr>
	<td>Mobil</td><td><%= SXUtil.toHtml(kk.getMobil()) %></td>
	</tr>
	<tr>
	<td>Fax</td><td><%= SXUtil.toHtml(kk.getFax()) %></td>
	</tr>
	<tr>
	<td>E-post</td><td><%= SXUtil.toHtml(kk.getEpost()) %></td>
	</tr>
	<tr>
		<td>Mottagare av ekonomiinformation</td><td><% if (kk.getEkonomi() > 0) out.print("Ja"); else out.print("Nej"); %></td>
	</tr>
	<tr>
	<td>Motagare av allmän info</td><td><% if (kk.getInfo() > 0) out.print("Ja"); else out.print("Nej"); %></td>
	</tr>
</table>
<% } %>
</div>