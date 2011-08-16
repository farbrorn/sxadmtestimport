<%--
    Document   : updateartikeltrad
    Created on : 2009-feb-09, 12:03:42
    Author     : ulf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="se.saljex.sxserver.LocalWebSupportLocal" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="javax.naming.*" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
	<%
				Context c = new InitialContext();
				LocalWebSupportLocal lc = (LocalWebSupportLocal) c.lookup("java:comp/env/LocalWebSupportBean");
	%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
		 <%
		 out.print(lc.updateLagerSaldonWithHTMLResponse());
		 %>
    </body>
</html>
