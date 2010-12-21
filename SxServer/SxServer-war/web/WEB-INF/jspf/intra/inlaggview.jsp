<%--
    Document   : inlaggview
    Created on : 2009-mar-20, 13:47:54
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
Connection con = (Connection)request.getAttribute("con");
InlaggHandler inh  = new InlaggHandler(con);
int inlaggId = 0;
try {
	inlaggId = Integer.parseInt(request.getParameter("inlaggid"));
}
catch (NumberFormatException e) {}
catch (NullPointerException e) {}

InlaggHandler.IntraInlagg inl = inh.getInlagg(inlaggId);
if (inl == null) {	out.print("Ogiltigt inläggsid: " + inlaggId); return; }
%>
<div class="inlagg">
<h1><%= inl.kanal_rubrik %></h1>
<h2><%= inl.rubrik %></h2>
<i><%= inl.ingress %></i><p/>
<%= inl.brodText %><p/>
Filnamn: <%= inl.originalFileName %><br/>
Visas till: <%= SXUtil.getFormatDate(inl.visaTill) %>
<h4>Av: <%= inl.anvandareKort %> <%= inl.crTime %></h4>
</div>