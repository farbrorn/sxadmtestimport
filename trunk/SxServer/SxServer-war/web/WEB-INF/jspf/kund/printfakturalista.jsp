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

PageListFaktura1 pl = (PageListFaktura1)request.getAttribute("pagelistfaktura1");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<%
if (pl == null) { out.println("Inga data"); } else { %>
<table id="doclist">
	<tr>
		<th class="tdknapp">Fakturanr</th>
		<th class="tdknapp">Pdf</th>
		<th class="tdn12">Fakturanr</th>
		<th class="tddatum">Datum</th>
		<th class="tdn16">Belopp</th>
		<th class="tdn12">Ordernr</th>
		<th class="tds30">Märke</th>
		 <th></th>
	</tr>
<tr><td colspan="6">Visar fakturor fram till : <%= pl.getTidatStr() %></td></tr>
<%
int radcn = 0;
int tempfaktnr = 0;
String ordernrstr;
String markestr;
String tempordernr;
String tempmarke;
if (pl.next()) {
	boolean plrowactive = true;
	while (plrowactive) {
		radcn++;
		if (radcn % 2 > 0) { %> <tr id="tr<%= radcn %>" class="trdocodd"> <%} else { %><tr id="tr<%= radcn %>" class="trdoceven"> <%}
		
%>
<td class="tdknapp"><a href="JavaScript:showfaktura(<%= radcn %>,<%= pl.getFaktnr() %>)" name="a<%= radcn %>">Visa</a></td>
<td class="tdknapp"><a href="?get=pdffaktura&faktnr=<%= pl.getFaktnr() %>" target="_blank">Pdf</a></td>
<td class="tdn12"><%= pl.getFaktnr() %></td>
<td class="tddatum"><%= SXUtil.getFormatDate(pl.getDatum()) %></td>
<td class="tdn16"><%= pl.getAttbetala() %></td>
<%
	tempfaktnr = pl.getFaktnr();
	ordernrstr = "";
	markestr = "";
	while (true) {
		if (pl.getOrdernr() == null || pl.getOrdernr() == 0) tempordernr = ""; else tempordernr = pl.getOrdernr().toString();
		if (pl.getMarke() == null) tempmarke = ""; else tempmarke = SXUtil.toHtml(pl.getMarke());
		if (!ordernrstr.isEmpty()) ordernrstr = ordernrstr + "<br/>";
		if (!markestr.isEmpty()) markestr = markestr + "<br/>";
		ordernrstr = ordernrstr + tempordernr;
		markestr = markestr + tempmarke;
		if (pl.next()) {
			if (tempfaktnr != pl.getFaktnr()) { plrowactive = true; break; }	
		} else { plrowactive = false; break; }
	}
%>
<td class="tdn12"><%= ordernrstr %></td>
<td class="tds30"><%= markestr %></td>
<td></td>
</tr>
<tr><td colspan="7"><div id="f<%= radcn %>" class="docgrupp" ></div></td></tr>	
<%
}
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
	 <input name="datatidat" value="<%= pl.getTidatStr() %>" type="hidden" />
</form>
	
</div>