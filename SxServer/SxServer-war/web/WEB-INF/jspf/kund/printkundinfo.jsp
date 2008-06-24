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
<td><%= k.getNummer() %></td>
</tr>
<tr>
<td>Namn</td>
<td><%= k.getNamn() %></td>
</tr>
<tr>
<td>Fakturaadress</td>
<td><%= k.getAdr1() %></td>
</tr>
<tr>
<td></td>
<td><%= k.getAdr2() %></td>
</tr>
<tr>
<td></td>
<td><%= k.getAdr3() %></td>
</tr>
<tr>
<td>Leveransadress</td>
<td><%= k.getLnamn() %></td>
</tr>
<tr>
<td></td>
<td><%= k.getLadr2() %></td>
</tr>
<tr>
<td></td>
<td><%= k.getLadr3() %></td>
</tr>
</tbody>
</table>
</div>