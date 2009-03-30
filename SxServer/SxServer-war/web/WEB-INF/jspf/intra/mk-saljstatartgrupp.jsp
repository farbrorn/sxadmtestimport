<%-- 
    Document   : mk-saljstatartgrupp
    Created on : 2009-mar-27, 20:15:17
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
	String jspName = "mk-saljstatartgrupp";
	SXSession sx = WebUtil.getSXSession(session);
	request.setAttribute("anvandare", sx.getIntraAnvandare());
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
%>
</table>
