<%-- 
    Document   : pumpartnr
    Created on : 2009-jul-23, 13:08:50
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>

<%

	FormHandlerStepumpartnr f = new FormHandlerStepumpartnr((javax.persistence.EntityManager)request.getAttribute("em"),(javax.transaction.UserTransaction)request.getAttribute("utx"),"/WEB-INF/jspf/ste/pumpartnr/pumpartnr", request, response);
request.setAttribute("FormHandlerStepumpartnr", f);
f.handleRequest();

%>