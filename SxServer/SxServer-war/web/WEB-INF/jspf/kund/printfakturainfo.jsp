<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.TableFaktura1" %>
<%@ page import="se.saljex.sxserver.tables.TableFaktura2" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<%@ page import="java.util.List" %>
 <script type="text/javascript">
	$(document).ready(function() {

	 });
				
 </script> 

<% 
SXSession sxSession = WebSupport.getSXSession(session);

TableFaktura1 f1 = (TableFaktura1)request.getAttribute("tablefaktura1");
List<TableFaktura2> lf2 = (List<TableFaktura2>)request.getAttribute("listtablefaktura2");

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<%
if (f1 == null) { out.println("Inga data föra fakturahuvud"); } else { %>
<table id="dochead">
<tr>
<td class="tddocheadrubrik">Fakturanr</td>
<td class="tds30"><%= f1.getFaktnr() %></td>
<td class="tddocheadrubrik">Datum</td>
<td class="tds30"><%= SXUtil.getFormatDate(f1.getDatum()) %></td>
</tr>
<tr>
<td class="tddocheadrubrik">Kredittid</td>
<td class="tds30"><%= f1.getKtid() %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"></td>
</tr>
<tr>
<td class="tddocheadrubrik">Fakturaadress</td>
<td class="tds30"><%= SXUtil.toHtml(f1.getNamn()) %></td>
<td class="tddocheadrubrik">Leveransadress</td>
<td class="tds30"><%= SXUtil.toHtml(f1.getLevadr1()) %></td>
</tr>
<tr>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= SXUtil.toHtml(f1.getAdr1()) %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= SXUtil.toHtml(f1.getLevadr2()) %></td>
</tr>
<tr>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= SXUtil.toHtml(f1.getAdr2()) %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"></td>
</tr>
<tr>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= SXUtil.toHtml(f1.getAdr3()) %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= SXUtil.toHtml(f1.getLevadr3()) %></td>
</tr>
</table>

<table id="doc">
<tr>
<th class="tds15">Art.nr.</th>
<th class="tds30">Benämning</th>
<th class="tdn12">Antal</th>
<th class="tds3">Enh</th>
<th class="tdn12">Pris</th>
<th class="tdn4">Rab</th>
<th class="tdn12">Summa</th>
</tr>
<%
for (TableFaktura2 f2 : lf2) {
	String text = f2.getText();
	if (text == null) text = "";
	if (!text.isEmpty()) {
%>
<tr>
<td colspan="6"><%= SXUtil.toHtml(text) %></td>
<td class="tdn12"><%= SXUtil.getFormatNumber(f2.getSumma()) %></td>
</tr>
<%
	} else {
%>
<tr>
<td class="tds15"><%= SXUtil.toHtml(f2.getArtnr()) %></td>
<td class="tds30"><%= SXUtil.toHtml(f2.getNamn()) %></td>
<td class="tdn12"><%= f2.getLev() %></td>
<td class="tds3"><%= SXUtil.toHtml(f2.getEnh()) %></td>
<td class="tdn12"><%= SXUtil.getFormatNumber(f2.getPris()) %></td>
<td class="tdn4"><%= SXUtil.getFormatNumber(f2.getRab(),1) %></td>
<td class="tdn12"><%= SXUtil.getFormatNumber(f2.getSumma()) %></td>
</tr>
<%
	}
}
%>
</table>
<%
}
%>
</div>
