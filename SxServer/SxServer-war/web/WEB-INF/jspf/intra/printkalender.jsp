<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>

<% 
PageListKalender pl = (PageListKalender)request.getAttribute("pagelistkalender"); 
String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>

<div <%= divInfo %>>
<table>
<tr><th>Datum</th><th>Tid</th><th>Händelse</th><th></th></tr>
<%
while (pl.next()) {
%>
<tr>
<td><%= SXUtil.getFormatDate(pl.getF_Dat()) %></td>
<td><%= SXUtil.getFormatDate(pl.getF_Tid()) %></td>
<td><%= SXUtil.toHtml(pl.getKmemo()) %></td>
</tr>
<%
}
%>
</table>
</div>