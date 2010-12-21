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
	String marke = request.getParameter("marke");
	if (marke == null) marke = "";

	Integer page1 = 1;
	try { page1 = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {  }
	if (page1 < 1) page1 = 1;
	
	String orderBy = request.getParameter("orderby");
	if (orderBy == null) orderBy = "ordernr";
	final String checkedStr = "checked=\"checked\"";
	if (!"ordernr".equals(orderBy) && !"marke".equals(orderBy)) orderBy = "ordernr";
	
	String divInfo = (String)request.getAttribute("divinfo");
	if (divInfo == null) divInfo = "";

%>
 <script type="text/javascript" src="sxdoclib.js"></script>
 <script type="text/javascript">
$(document).ready(function() {
	$("#d1").hide();
	$("#inputpage").hide();
	$("input[@name=bsok]").click( function() { $("input[@name=page]").val(1); loadsokres(); return false;});
	$("input[@name=orderby]").click( function() {loadsokres(); });
	$("#marke").keyup(function() {loadsokres(); });
	loadsokres();
});

		 
 </script> 

<div <%= divInfo %>>
<div id="divdocsok">

<h1>Levererade order</h1>
<form id="sokform" action="">
<input type="hidden" name="get" value="utlev1"/>
<table id="doclist"><tr>
<td colspan="3" id="d1">
Sortera efter
<input id="orderbyordernr" type="radio" name="orderby" value="ordernr" <% if ("ordernr".equals(orderBy)) out.print(checkedStr); %> />Ordernr
<input id="orderbymarke" type="radio" name="orderby" value="marke" <% if ("marke".equals(orderBy)) out.print(checkedStr); %> />Märke
<br/>	
Från datum: (åååå-mm-dd) <input id="frdat" type="text" name="frdat" value="<%= frdat %>"/>
Till datum: (åååå-mm-dd) <input id="tidat" type="text" name="tidat" value="<%= tidat %>"/>
<br/>Märke: <input id="marke" type="text" name="marke" value="<%= marke %>"/>
<span id="inputpage">Sida: <input type="text" name="page" value="<%= page1 %>"/></span>
<input type="submit" value="Sök" name="bsok"  />

</td></tr><tr><td>
<a href="javascript:toggleD1();" id="aToggleD1" >Alternativ</a>
<td align="left">Sida: <span id="sida"></span></td>
<td align="right"><a href="javascript:loadPreviousPage();" id="getpreviouspage" ></a>&nbsp;<a href="javascript:loadNextPage();" id="getnextpage" ></a></td>
</tr></table></form>
</div>
<div id="divdoclist"></div>
</div>