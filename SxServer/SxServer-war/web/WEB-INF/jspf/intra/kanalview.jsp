<%-- 
    Document   : kanalview
    Created on : 2009-mar-20, 13:47:54
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
Connection con = (Connection)request.getAttribute("con");
InlaggHandler inh  = new InlaggHandler(con);
int kanalId = 0;
try {
	kanalId = Integer.parseInt(request.getParameter("kanalid"));
}
catch (NumberFormatException e) {}
catch (NullPointerException e) {}

ArrayList<InlaggHandler.IntraInlagg> inlArr = inh.getInlaggListByKanalId(kanalId);
if (inlArr.isEmpty() ) {	out.print("Inga inlägg i kanalId " + kanalId); return; }
boolean firstRow = true;
%>
<div class="inlagg">
<% for (InlaggHandler.IntraInlagg inl : inlArr) { %>
	<% if (firstRow) { firstRow = false; %> <h1><%= inl.kanal_rubrik %></h1> <% } %>
	<a href="?id=inlaggview&inlaggid=<%= inl.inlaggId %>"><h2><%= inl.rubrik %></h2></a>
	<i><%= inl.ingress %></i><p/>
	<%= inl.brodText %><p/>
	Filnamn: <%= inl.originalFileName %><br/>
	Visas till: <%= SXUtil.getFormatDate(inl.visaTill) %>
	<h4>Av: <%= inl.anvandareKort %> <%= inl.crTime %></h4><p/>
<% } %>
</div>