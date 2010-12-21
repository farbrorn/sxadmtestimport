
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<% 
SXSession sxSession = WebSupport.getSXSession(session);
String divInfo = (String)request.getAttribute("divinfo");
RappEdit r = (RappEdit)request.getAttribute("rappedit");
Integer rappSession = r.getRappSession();
//Integer rappSession = Integer.parseInt(request.getParameter("rappsession"));
//RappEdit r = sxSession.getArrRappEdit().get(rappSession);
if (divInfo == null) divInfo = "";
int cn;
RappEdit.RappColumn rc;
%>

<div <%= divInfo %>>
<h1>Ändra Rapport</h1>

<% if(r.getIsChanged()) out.print("Osparade ändringar finns! "); %>
<a href="?id=persist&rappsession=<%= rappSession %>">Spara</a>

<br/>
<br/>
<div id="rapphuvud">
<table>
<tr><td>Kategori:</td><td><%= SXUtil.toHtml(r.getHuvud().kategori) %></td></tr>
<tr><td>Undergrupp:</td><td><%= SXUtil.toHtml(r.getHuvud().undergrupp) %></td></tr>
<tr><td>Kortbeskrivning:</td><td><%= SXUtil.toHtml(r.getHuvud().kortbeskrivning) %></td></tr>
<tr><td>SQL from-sats:</td><td><%= SXUtil.toHtml(r.getHuvud().sqlfrom) %></td></tr>
<tr><td colspan="2"><td><% if(r.getHuvud().markedForDelete) out.print("Kommer att raderas"); %></td></td>
</table>
<a href="?id=edithuvud&rappsession=<%= rappSession %>">Ändra</a>
</div>
<br/>

<div id="rappcolumn">
<table><tr><th>Sortering</th><th>Kolumnrubrik</th><th>Sql-namn</th></tr>
<%
for (cn=0; cn<r.getArrColumn().size(); cn++) {
rc = r.getArrColumn().get(cn);
%>
<tr><td><%= rc.sortOrder %></td><td><%= SXUtil.toHtml(rc.label) %></td><td><%= SXUtil.toHtml(rc.sqlLabel) %></td><td><a href="?id=editcolumn&rappsession=<%= rappSession %>&pos=<%= cn %>">Ändra</a></td><td><% if(rc.markedForDelete) out.print("Ja"); %></td></tr>
<%
}
	
%>
</table>
<a href="?id=newcolumn&rappsession=<%= rappSession %>">Ny kolumn</a>
</div>
<br/>



<div id="rappfilter">
<table><tr><th>Position i where</th><th>Namn</th><th>Javatype</th></tr>
<%
RappEdit.RappFilter rf;
for (cn=0; cn<r.getArrFilter().size(); cn++) {
rf = r.getArrFilter().get(cn);
%>
<tr><td><%= rf.wherepos %></td><td><%= SXUtil.toHtml(rf.label) %></td><td><%= SXUtil.toHtml(rf.javatype) %></td><td><a href="?id=editfilter&rappsession=<%= rappSession %>&pos=<%= cn %>">Ändra</a></td><td><% if(rf.markedForDelete) out.print("Ja"); %></td></tr>
<%
}	
%>
</table>
<a href="?id=newfilter&rappsession=<%= rappSession %>">Nytt filter</a>
</div>


<br/>
<div id="rappsum">
	<table><tr><th>Summeringskolumn</th><th>Nollställningskolumn</th><th>Typ</th><th>Tag bort</th></tr>
<%
RappEdit.RappSum rs;
for (cn=0; cn<r.getArrSum().size(); cn++) {
rs = r.getArrSum().get(cn);
%>
<tr><td><%= rs.sumcolumn %></td><td><%= rs.resetcolumn %></td><td><%= SXUtil.toHtml(rs.sumtype) %></td><td><a href="?id=editsum&rappsession=<%= rappSession %>&pos=<%= cn %>">Ändra</a></td><td><% if(rs.markedForDelete) out.print("Ja"); %></td></tr>
<%
}	
%>
</table>
<a href="?id=newsum&rappsession=<%= rappSession %>">Ny summa</a>
</div>


</div>