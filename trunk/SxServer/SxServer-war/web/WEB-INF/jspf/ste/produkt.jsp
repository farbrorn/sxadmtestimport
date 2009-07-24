<%-- 
    Document   : produkt
    Created on : 2009-jul-18, 07:16:50
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>

<%

	FormHandlerSteprodukt f = new FormHandlerSteprodukt((javax.persistence.EntityManager)request.getAttribute("em"),(javax.transaction.UserTransaction)request.getAttribute("utx"),"/WEB-INF/jspf/ste/produkt/produkt", request, response);
request.setAttribute("FormHandlerSteprodukt", f);
f.handleRequest();

%>