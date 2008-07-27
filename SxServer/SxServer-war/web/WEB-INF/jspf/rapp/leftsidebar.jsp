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
<a href="?id=1">Rapportlista</a><br/>
</div>