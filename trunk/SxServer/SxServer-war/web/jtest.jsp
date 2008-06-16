<%-- 
    Document   : jtest
    Created on : 2008-jun-13, 10:29:08
    Author     : ulf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>JSP Page</title>
		 </head>
    <body>
		 <jsp:include page="WEB-INF/jspf/header.jspf" />
        <h2>Hello World är !</h2>
		  <% 
			java.util.Enumeration en = pageContext.getServletContext().getAttributeNames();  
			while (en.hasMoreElements()) {
				out.println(en.nextElement().toString());
				out.println("<br>");
			}
			%>
			
    </body>
</html>
