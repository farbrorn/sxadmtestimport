<%-- 
    Document   : index
    Created on : 2012-feb-22, 19:42:40
    Author     : Ulf
--%>

<%@page import="se.saljex.webadm.server.RequestHandler"%>
<%@page import="se.saljex.sxserver.SxServerMainBean"%>
<%@page import="se.saljex.sxserver.SxServerMainLocal"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="javax.naming.InitialContext" %>
<%@page import="javax.naming.Context" %>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<%
try {
	SxServerMainLocal sx = RequestHandler.getSxServerMainLocal(request);
	
	Integer offertnr;
	try {
		offertnr = new Integer(request.getParameter("offertnr"));
	} catch (NumberFormatException e) {
		offertnr=null;
	}
	boolean inkMoms = "true".equals(request.getParameter("inkmoms"));
	String logoUrl = request.getParameter("logourl");
	String headerHTML = request.getParameter("headerhtml");
	String meddelandeHTML = request.getParameter("meddelandehtml");
	String footerHTML = request.getParameter("footerhtml");
	out.print(sx.getHtmlOffert(offertnr, inkMoms, logoUrl, headerHTML, meddelandeHTML, footerHTML));
}
catch(Exception e) {
	%>Fel: <%= e.getMessage() %> <% e.printStackTrace(); %>
<% }%>
