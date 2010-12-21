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

List<TableOrder1> ol = (List)request.getAttribute("listtableorder1");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<h1>Order</h1>

<%
if (ol == null) { out.println("Inga data"); } else { %>
<table id="doclist">
	<tr>
		<th class="tdknapp"></th>
		<th class="tds15">Ordernr</th>
		<th class="tddatum">Datum</th>
		<th class="tds10">Status</th>
		<th class="tds30">Märke</th>
		 <th></th>
	</tr>
<%
int radcn = 0;
for (TableOrder1 t : ol) {
radcn++;
if (radcn % 2 > 0) { %> <tr id="tr<%= radcn %>" class="trdocodd"> <%} else { %><tr id="tr<%= radcn %>" class="trdoceven"> <%}

%>
<td class="tdknapp"><a href="JavaScript:showorder(<%= radcn %>, <%= t.getOrdernr() %>)" name="a<%= radcn %>">Visa</a></td>
<td class="tds15"><%= t.getOrdernr() %></td>
<td class="tddatum"><%= SXUtil.getFormatDate(t.getDatum()) %></td>
<td class="tds10"><%= SXUtil.toHtml(t.getStatus()) %></td>
<td class="tds30"><%= SXUtil.toHtml(t.getMarke()) %></td>
<td><a href="?id=editorder&ordernr=<%= t.getOrdernr() %>">Redigera</a></td>
</tr>
<tr><td colspan="6"><div id="f<%= radcn %>" class="docgrupp"></div</td></tr>	
<%
}
%>
</table>
<%
}
%>
	
</div>