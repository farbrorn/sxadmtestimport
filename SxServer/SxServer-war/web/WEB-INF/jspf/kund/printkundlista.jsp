<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

 <script type="text/javascript" src="sxdoclib.js"></script>

 <script type="text/javascript">
$(document).ready(function() {
		 $(".docgrupp").hide();
});
		 
 </script> 

<% 
SXSession sxSession = WebSupport.getSXSession(session);

PageListKund pl = (PageListKund)request.getAttribute("pagelistkund");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<h1>Kundlista</h1>

<%
if (pl == null) { out.println("Inga data"); } else { %>
<table id="doclist">
	<tr>
		<th class="tdknapp"></th>
		<th class="tds15">Kundnr</th>
		<th class="tds30">Namn</th>
		<th class="tds30">Ort</th>
		<th class="tds30">Referens</th>
		 <th></th>
	</tr>
<%
int radcn = 0;
while (pl.next()) {
	radcn++;
	if (radcn % 2 > 0) { %> <tr id="tr<%= radcn %>" class="trdocodd"> <%} else { %><tr id="tr<%= radcn %>" class="trdoceven"> <%}
%>

<td class="tdknapp"><a href="JavaScript:void()" name="a<%= radcn %>">Visa</a></td>
<td class="tds15"><a href="?id=setkund&kundnr=<%= pl.getNummer() %>"><%= pl.getNummer() %></a></td>
<td class="tds30"><%= SXUtil.toHtml(pl.getNamn()) %></td>
<td class="tds30"><%= SXUtil.toHtml(pl.getAdr3()) %></td>
<td class="tds30"><%= SXUtil.toHtml(pl.getRef()) %></td>
<td></td>
</tr>
<tr><td colspan="5"><div id="f<%= radcn %>" class="docgrupp" ></div></td></tr>	
<%
}
%>
</table>
<%
}
%>
<form name="data">
	 <input name="currentpage" value="<%= pl.getCurrentPage() %>" type="hidden" />
	 <input name="nextpage" value="<%= pl.getNextPage() %>" type="hidden" />
	 <input name="previouspage" value="<%= pl.getPreviousPage() %>" type="hidden" />
</form>
	
</div>