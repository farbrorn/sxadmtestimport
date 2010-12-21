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


<% 
SXSession sxSession = WebSupport.getSXSession(session);

//TableOrder1 o1 = (TableOrder1)request.getAttribute("tableorder1");
PageListUtlev2 pl = (PageListUtlev2)request.getAttribute("pagelistutlev2");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>

<table id="doc">
<tr>
<th class="tds15">Art.nr.</th>
<th class="tds30">Benämning</th>
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
<td class="tds15"><%= pl.getArtnr() %></td>
<td class="tds30"><%= SXUtil.toHtml(pl.getNamn()) %></td>
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
</div>
