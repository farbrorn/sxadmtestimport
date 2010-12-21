<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>

<% 
	String sokstr = request.getParameter("sokstr");
	if (sokstr == null) sokstr = "";

	Integer page1 = 1;
	try { page1 = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {  }
	if (page1 < 1) page1 = 1;

	String divInfo = (String)request.getAttribute("divinfo");
	if (divInfo == null) divInfo = "";
	
 %>
 <script type="text/javascript" src="sxdoclib.js"></script>
 <script type="text/javascript">
$(document).ready(function() {
	$("input[@name=bsok]").click( function() {  loadsokres(); return false;});
	$("input[@name=sokstr]").keyup( function() {  loadsokres(); });
	$("#inputpage").hide();
	loadsokres();
	 
});
 </script> 

<div <%= divInfo %>>
<div id="divdocsok">

<h1>Sök kund</h1>
<form id="sokform" action="">
<input type="hidden" name="get" value="kundlista"/>
<table id="doclist"><tr>
<td colspan="3" id="d1">
Sök: <input id="sokstr" type="text" name="sokstr" value="" />
<span id="inputpage">Sida: <input type="text" name="page" value="<%= page1 %>"/></span>
<input type="submit" value="Sök" name="bsok"/>
</td></tr><tr><td>
<a href="javascript:toggleD1();" id="aToggleD1" >Alternativ</a>
<td align="left">Sida: <span id="sida"></span></td>
<td align="right"><a href="javascript:loadPreviousPage();" id="getpreviouspage" ></a>&nbsp;<a href="javascript:loadNextPage();" id="getnextpage" ></a></td>
</tr></table></form>
</div>
<div id="divdoclist"></div>
</div>
