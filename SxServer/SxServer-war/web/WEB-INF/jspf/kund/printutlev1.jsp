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

PageListUtlev1 pl = (PageListUtlev1)request.getAttribute("pagelistutlev1");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<%
if (pl == null) { out.println("Inga data"); } else { %>
<table id="doclist">
	<tr>
		<th class="tdknapp"></th>
		<th class="tdn12">Ordernr</th>
		<th class="tddatum">Datum</th>
		<th class="tds30">Märke</th>
		 <th></th>
	</tr>
<tr><td colspan="5">Visar leveranser för period : <%= pl.getFrdatStr() + " - " + pl.getTidatStr() %></td></tr>
<%
int radcn = 0;
while (pl.next()) {
	radcn++;
	if (radcn % 2 > 0) { %> <tr id="tr<%= radcn %>" class="trdocodd"> <%} else { %><tr id="tr<%= radcn %>" class="trdoceven"> <%}
%>

<td class="tdknapp"><a href="JavaScript:showutlev2(<%= radcn %>,<%= pl.getOrdernr() %>)" name="a<%= radcn %>">Visa</a></td>
<td class="tdn12"><%= pl.getOrdernr() %></td>
<td class="tddatum"><%= SXUtil.getFormatDate(pl.getDatum()) %></td>
<td class="tds30"><%= SXUtil.toHtml(pl.getMarke()) %></td>
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
	 <input name="datafrdat" value="<%= pl.getFrdatStr() %>" type="hidden" />
	 <input name="datatidat" value="<%= pl.getTidatStr() %>" type="hidden" />
	 <input name="datamarke" value="<%= pl.getOriginalMarke() %>" type="hidden" />
	 <input name="dataorderby" value="<%= pl.getOrderBy() %>" type="hidden" />
</form>

</div>