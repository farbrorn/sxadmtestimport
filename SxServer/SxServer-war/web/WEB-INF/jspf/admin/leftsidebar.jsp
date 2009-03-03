<%-- 
    Document   : leftsidebar
    Created on : 2009-feb-09, 11:44:05
    Author     : ulf
--%>

<%
//String intraUser = (String)request.getAttribute("intrauser");
String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<a href="?id=status">Serverstatus</a><br/>
<a href="?id=updateartikeltrad">Uppdatera Artikelträd</a><br/>
<a href="?id=inlagg">Hantera inlägg</a><br/>

</div>