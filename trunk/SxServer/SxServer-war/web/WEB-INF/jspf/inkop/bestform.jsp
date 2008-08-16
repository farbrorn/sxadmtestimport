<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>

<% 
SXSession sxSession = WebUtil.getSXSession(session);

PageListBest1 be1 = (PageListBest1)request.getAttribute("pagelistbest1");
PageListBest2 be2 = (PageListBest2)request.getAttribute("pagelistbest2");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
be1.next();
%>
<div <%= divInfo %>>

<h1>Beställning</h1>
<form action="?id=2" method="post">
<table>
<tr>
	<td>Beställningsnummer</td><td><%= be1.getBestnr() %></td>
	<td>Datum</td><td><%= be1.getDatum() %></td>
</tr>
<tr>
	<td>Vår referens</td><td><%= be1.getVarRef() %></td>
	<td>Er referens</td><td><%= be1.getErRef() %></td>
</tr>
<tr>
	<td>Leveransadress</td><td><%= be1.getLevadr0() %></td>
	<td></td><td><%= be1 %></td>
</tr>
<tr>
	<td>&nbsp;</td><td><%= be1.getLevadr1() %><br/></td>
	<td></td><td><%= be1 %></td>
</tr>
<tr>
	<td>&nbsp;</td><td><%= be1.getLevadr2() %></td>
	<td></td><td><%= be1 %></td>
</tr>
<tr>
	<td>&nbsp;</td><td><%= be1.getLevadr3() %></td>
	<td></td><td><%= be1 %></td>
</tr>
<tr>
	<td>Leveransdatum</td><td><%= be1.getLeverans() %></td>
	<td>Märke</td><td><%= be1.getMarke() %></td>
</tr>
<tr>
	<td>Meddelande</td><td colspan="3"><%= be1.getMeddelande() %></td>
</tr>
</table>
<table>
<% while (be2.next()) {%>
<tr>
	<td><%= SXUtil.toHtml(be2.getArtnr()) %></td>
	<td><%= SXUtil.toHtml(be2.getBartnr()) %></td>
	<td><%= SXUtil.toHtml(be2.getArtnamn()) %></td>
	<td><%= be2.getBest() %></td>
	<td><%= SXUtil.toHtml(be2.getEnh()) %></td>
	<td><%= SXUtil.getFormatNumber(be2.getPris()) %></td>
	<td><%= SXUtil.getFormatNumber(be2.getRab()) %></td>
	<td><input type="text" name="be2bekrdat<%= be2.getRad() %>" value="<%= SXUtil.getFormatDate(be2.getBekrdat()) %>"></td>
</tr>	
<% }{%>
</table>
</form>
</div>
