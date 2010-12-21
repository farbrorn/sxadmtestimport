<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>
<%@ page import="se.saljex.sxlibrary.*" %>

 <script type="text/javascript" src="sxdoclib.js"></script>
 <script type="text/javascript">
	$(document).ready(function() {
		 $(".docgrupp").hide();
	 });	  
 </script> 

<% 
SXSession sxSession = WebSupport.getSXSession(session);

List<TableKundres> ok = (List)request.getAttribute("listtablekundres");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<h1>Reskontra</h1>

<%
if (ok == null) { out.println("Inga data"); } else { %>
<table id="doclist">
	<tr>
		<th class="tdknapp"></th>
		<th class="tds15">Fakturanr</th>
		<th class="tddatum">Datum</th>
		<th class="tddatum">Förfallodatum</th>
		<th class="tds10">Totalt</th>
		<th></th>
	</tr>
<%
int radcn = 0;
for (TableKundres k : ok) {
	radcn++;
	if (radcn % 2 > 0) { %> <tr id="tr<%= radcn %>" class="trdocodd"> <%} else { %><tr id="tr<%= radcn %>" class="trdoceven"> <%}

%>
<td class="tdknapp"><a href="JavaScript:showfaktura(<%= radcn %>, <%= k.getFaktnr() %>)" name="a<%= radcn %>">Visa</a></td>
<td class="tds15"><%= k.getFaktnr() %></td>
<td class="tddatum"><%= SXUtil.getFormatDate(k.getDatum()) %></td>
<td class="tddatum"><%= SXUtil.getFormatDate(k.getFalldat()) %></td>
<td class="tdn14"><%= SXUtil.getFormatNumber(k.getTot()) %></td>
<td></td>
</tr>
<tr><td colspan="6"><span id="f<%= radcn %>"></span></td></tr>

<%
}
%>
</table>
<%
}
%>
	
</div>