<%-- 
    Document   : produktnot
    Created on : 2009-jul-21, 16:38:28
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>

<%
try {
	FormHandlerSteproduktnot f = new FormHandlerSteproduktnot((javax.persistence.EntityManager)request.getAttribute("em"),(javax.transaction.UserTransaction)request.getAttribute("utx"),"/WEB-INF/jspf/ste/produktnot/produktnot", request, response);
	request.setAttribute("FormHandlerSteproduktnot", f);
	f.handleRequest();
} catch (javax.persistence.EntityNotFoundException e) { %> Serienummer saknas. <%}

%>