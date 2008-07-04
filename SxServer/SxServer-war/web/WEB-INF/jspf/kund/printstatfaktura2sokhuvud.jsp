<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<% 
	String frdat = request.getParameter("frdat");
	if (frdat == null) frdat = "";
	String tidat = request.getParameter("tidat");
	if (tidat == null) tidat = "";
	String orderBy = request.getParameter("orderby");
	if (orderBy == null) orderBy = "summa";
	final String checkedStr = "checked=\"checked\"";
	if (!"summa".equals(orderBy) && !"antalkopta".equals(orderBy) && !"antalkop".equals(orderBy)) orderBy = "summa";
	
	String divInfo = (String)request.getAttribute("divinfo");
	if (divInfo == null) divInfo = "";

%>
 <script type="text/javascript">
$(document).ready(function() {
	$("#d1").hide();
	 loadsokres(1);
});
var cur;
var next;
var prev;
function loadsokres(page) {
	frdat = $("#frdat").val();
	tidat = $("#tidat").val();
	orderBy = $("input[@name=orderby]:checked").val();
	$("#divdoclist").load("?", {get: "statfaktura2", frdat: frdat , tidat: tidat, orderby: orderBy, page: page }, function() { updateNextPrev(); } );
	}
	
function updateNextPrev() {
	cur = $("input[@name=currentpage]").val();
	 next = $("input[@name=nextpage]").val();
	 prev = $("input[@name=previouspage]").val();
	 if (prev < cur) { $("#getpreviouspage").html("Föregående"); } else {$("#getpreviouspage").html("");}
	 if (next > cur) { $("#getnextpage").html("Nästa"); } else {$("#getnextpage").html("");}
	 $("#sida").html(cur);
}
function loadsokres2(e) {
	loadsokres();
}
function loadNextPage() {
	loadsokres(next);
}
function loadPreviousPage() {
	loadsokres(prev);
}
function toggleD1() {
	$("#d1").toggle();
	}
		 
 </script> 

<div <%= divInfo %>>
<span id="divdocsok">

<h1>Inköpsstatistik</h1>
<form><table id="doclist"><tr>
<td colspan="3">
Sortera efter
<input id="orderbysumma" type="radio" name="orderby" value="summa" <% if ("summa".equals(orderBy)) out.print(checkedStr); %> onclick="loadsokres(1);"/>Varuvärde
<input id=orderbyantalkopta" type="radio" name="orderby" value="antalkopta" <% if ("antalkopta".equals(orderBy)) out.print(checkedStr); %> onclick="loadsokres(1);"/>Antal köpta
<input id="orderbyantalkop" type="radio" name="orderby" value="antalkop" <% if ("antalkop".equals(orderBy)) out.print(checkedStr); %> onclick="loadsokres(1);" />Antal köptillfällen
</td></tr><tr><td colspan="3" id="d1">
Från datum: (åååå-mm-dd) <input id="frdat" type="text" name="frdat" value="<%= frdat %>"/>
Till datum: (åååå-mm-dd) <input id="tidat" type="text" name="tidat" value="<%= tidat %>"/>
<input type="button" value="Sök" name="bsok" onclick="loadsokres(1);" />

</td></tr><tr><td>
<a href="javascript:toggleD1();" id="aToggleD1" >Fler sökalternativ</a>
<td align="left">Sida: <span id="sida"></span></td>
<td align="right"><a href="javascript:loadPreviousPage();" id="getpreviouspage" ></a>&nbsp;<a href="javascript:loadNextPage();" id="getnextpage" ></a></td>
</tr></table></form>
</span>
<div id="divdoclist"></div>
</div>