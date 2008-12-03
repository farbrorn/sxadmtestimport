<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<%
String intraUser = (String)request.getAttribute("intrauser");
String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<a href="?id=1">Kundinfo</a><br/>
<a href="?id=2">Fakturor</a><br/>
<a href="?id=3">Order</a><br/>
<a href="?id=4">Reskontra</a><br/>
<a href="?id=5">Betalningar</a><br/>
<a href="?id=6">Levererade order</a><br/>
<a href="?id=7">Statistik</a><br/>
<% if (intraUser != null) { %>
<p/>Administration<br/>
<a href="?id=kundlista">Välj kund</a><br/>

<% } %>
</div>