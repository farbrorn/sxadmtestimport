<%-- 
    Document   : index
    Created on : 2012-okt-29, 10:43:41
    Author     : Ulf
--%>

<%@page import="java.util.Scanner"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Rapporter - Säljex</title>
    </head>
    <body>
        <h1>Rapporter</h1>
		<%
		final  String relPath = "rapp/";
		String path = request.getServletContext().getRealPath(relPath);
		try {
			java.io.File d = new java.io.File(path);
			java.io.File[] files = d.listFiles();

			String rubrik;
			for(java.io.File f : files) {
				if (f.getName()!= null && f.getName().endsWith(".jsp") && !f.getName().equals("index.jsp")) {
					Scanner scanner = new Scanner(f);
					scanner.useDelimiter("\\z");
					String text = scanner.next();
					scanner.close();
					int start = text.indexOf("<sx-rubrik>");
					int end = text.indexOf("</sx-rubrik>");
					if (start>-1 && end>1) {
						rubrik = text.substring(start, end);
					} else {
						rubrik=f.getName();
					}
					out.print("<a href=\"" + relPath + f.getName() + "\">" + rubrik + "</a><br>");
				}
			}
		} catch (NullPointerException e) { e.printStackTrace();}

		
%>		
    </body>
</html>
