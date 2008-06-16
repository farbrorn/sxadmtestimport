<%-- 
    Document   : leftsidebar
    Created on : 2008-jun-16, 20:43:50
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>

<% 
SXSession sxSession = (SXSession)session.getAttribute("sxsession");
TableKund k = sxSession.kun;
%>

<div>
Inloggad <%= k.getNamn() %>
<a href="?id=logout">Logga ut</a><br/>

</div>