<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>

<% 
SXSession sxSession = WebUtil.getSXSession(session);
TableKund k = sxSession.kun;

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
</div>