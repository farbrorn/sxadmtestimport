<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<% 
String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<a href="?id=1">Kundinfo</a><br/>
<a href="?id=1">Fakturor</a><br/>
<a href="?id=1">Order</a><br/>
<a href="?id=1">Reskontra</a><br/>
<a href="?id=1">Betalningar</a><br/>

</div>