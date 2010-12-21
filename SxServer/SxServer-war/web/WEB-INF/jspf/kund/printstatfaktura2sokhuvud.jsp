<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
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
	
	Integer page1 = 1;
	try { page1 = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {  }
	if (page1 < 1) page1 = 1;
	
	String divInfo = (String)request.getAttribute("divinfo");
	if (divInfo == null) divInfo = "";

%>
 <script type="text/javascript" src="sxdoclib.js"></script>
 <script type="text/javascript">
$(document).ready(function() {
	$("#d1").hide();
	$("#inputpage").hide();
	$("input[@name=page]").hide();
	$("input[@name=orderby]").click( function() { $("input[@name=page]").val(1); loadsokres(); });
	$("input[@name=bsok]").click( function() { $("input[@name=page]").val(1); loadsokres(); return false;});
	loadsokres();
});
 </script> 

<div <%= divInfo %>>
<div id="divdocsok">

<h1>Inköpsstatistik</h1>
<form id="sokform" action="">
<input type="hidden" name="get" value="statfaktura2"/>
<table id="doclist"><tr>
<td colspan="3">
Sortera efter
<input id="orderbysumma" type="radio" name="orderby" value="summa" <% if ("summa".equals(orderBy)) out.print(checkedStr); %> />Varuvärde
<input id="orderbyantalkopta" type="radio" name="orderby" value="antalkopta" <% if ("antalkopta".equals(orderBy)) out.print(checkedStr); %> />Antal köpta
<input id="orderbyantalkop" type="radio" name="orderby" value="antalkop" <% if ("antalkop".equals(orderBy)) out.print(checkedStr); %>  />Antal köptillfällen
</td></tr><tr><td colspan="3" id="d1">
Från datum: (åååå-mm-dd) <input id="frdat" type="text" name="frdat" value="<%= frdat %>"/>
Till datum: (åååå-mm-dd) <input id="tidat" type="text" name="tidat" value="<%= tidat %>"/>
<input type="button" value="Sök" name="bsok"/>

</td></tr><tr><td>
<a href="javascript:toggleD1();" id="aToggleD1" >Fler sökalternativ</a>
<td align="left">Sida: <span id="sida"></span></td>
<td align="right"><a href="javascript:loadPreviousPage();" id="getpreviouspage" ></a>&nbsp;<a href="javascript:loadNextPage();" id="getnextpage" ></a></td>
</tr></table></form>
</div>
<div id="divdoclist"></div>
</div>