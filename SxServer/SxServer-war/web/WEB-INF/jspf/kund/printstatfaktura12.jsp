<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

 <script type="text/javascript">
	$(document).ready(function() {

	 });
				
 </script> 

<% 
SXSession sxSession = WebSupport.getSXSession(session);

PageListStatFaktura12 pl = (PageListStatFaktura12)request.getAttribute("pageliststatfaktura12");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<%
if (pl == null) { out.println("Inga data"); } else { %>

<table id="doc">
<tr>
<th class="tds15">Datum</th>
<th class="tdn12">Fakturanr</th>
<th class="tdn12">Antal</th>
<th class="tds3">Enh</th>
<th class="tdn12">Pris</th>
<th class="tdn4">Rab</th>
<th class="tdn12">Summa</th>
<th></th>
</tr>
<%
while (pl.next()) {
%>
<tr>
<td class="tds15"><%= pl.getDatum() %></td>
<td class="tdn12"><%= pl.getFaktnr() %></td>
<td class="tdn12"><%= pl.getLev() %></td>
<td class="tds3"><%= SXUtil.toHtml(pl.getEnh()) %></td>
<td class="tdn12"><%= SXUtil.getFormatNumber(pl.getPris()) %></td>
<td class="tdn4"><%= SXUtil.getFormatNumber(pl.getRab(),1) %></td>
<td class="tdn12"><%= SXUtil.getFormatNumber(pl.getSumma()) %></td>
<td></td>
</tr>
<%
	}
%>
</table>
<%
}
%>
</div>
