<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.TableOrder1" %>
<%@ page import="se.saljex.sxserver.tables.TableOrder2" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>

<%@ page import="java.util.List" %>
 <script type="text/javascript">
	$(document).ready(function() {

	 });
				
 </script> 

<% 
SXSession sxSession = WebSupport.getSXSession(session);

TableOrder1 o1 = (TableOrder1)request.getAttribute("tableorder1");
List<TableOrder2> lo2 = (List<TableOrder2>)request.getAttribute("listtableorder2");
boolean redigerbar;

String divInfo = (String)request.getAttribute("divinfo");
if (divInfo == null) divInfo = "";
%>
<div <%= divInfo %>>
<%
if (o1 == null) { out.println("Inga data"); } else { %>
<table id="dochead">
<tr><th colspan="5">Orderinfo</th></tr
<tr>
<td class="tddocheadrubrik">Ordernr</td>
<td class="tds30"><%= o1.getOrdernr() %></td>
<td class="tddocheadrubrik">Datum</td>
<td class="tds30"><%= SXUtil.getFormatDate(o1.getDatum()) %></td>
<td></td>
</tr>
<tr>
<td class="tddocheadrubrik">Kredittid</td>
<td class="tds30"><%= o1.getKtid() %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"></td>
</tr>
<tr>
<td class="tddocheadrubrik">Fakturaadress</td>
<td class="tds30"><%= o1.getNamn() %></td>
<td class="tddocheadrubrik">Leveransadress</td>
<td class="tds30"><%= o1.getLevadr1() %></td>
</tr>
<tr>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= o1.getAdr1() %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= o1.getLevadr2() %></td>
</tr>
<tr>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= o1.getAdr2() %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"></td>
</tr>
<tr>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= o1.getAdr3() %></td>
<td class="tddocheadrubrik"></td>
<td class="tds30"><%= o1.getLevadr3() %></td>
</tr>
</table>

<form>
	<input type="hidden" name="get" value="updateorderantal"/>
	<input type="hidden" name="ordernr" value="<%= o1.getOrdernr() %>"/>
<table id="doc">
<tr>
<th class="tds15">Art.nr.</th>
<th class="tds30">Benämning</th>
<th class="tdn12">Antal</th>
<th class="tds3">Enh</th>
<th class="tdn12">Nytt antal</th>
<th></th>
</tr>
<%
for (TableOrder2 o2 : lo2) {
	if ("Sparad".equals(o1.getStatus()) && o1.getLastdatum() == null && !o2.getArtnr().startsWith("*")) {
		redigerbar = true;
	} else {
		redigerbar = false;
	}
	String text = o2.getText();
	if (text == null) text = "";
	if (!text.isEmpty()) { 
%>
<tr>
<td colspan="7"><%= text %></td>
<td class="tdn12"><%= SXUtil.getFormatNumber(o2.getSumma()) %></td>
</tr>
<%
	} else {
%>
<tr>
<td class="tds15"><%= SXUtil.toHtml(o2.getArtnr()) %></td>
<td class="tds30"><%= SXUtil.toHtml(o2.getNamn()) %></td>
<td class="tdn12"><%= o2.getBest() %></td>
<td class="tds3"><%= SXUtil.toHtml(o2.getEnh()) %></td>
<td> <% if (redigerbar) {%>
<input name="nantal<%= o2.getTableOrder2PK().getPos() %>" value="<%= o2.getBest() %>" size="6"/>
<% } %>
</td>
<td></td>
</tr>
<%
	}
}
%>
</table></form>
<%
}
%>
</div>
