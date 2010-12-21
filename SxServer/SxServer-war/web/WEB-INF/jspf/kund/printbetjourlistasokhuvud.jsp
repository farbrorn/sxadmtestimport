<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<% 
	String tidat = request.getParameter("tidat");
	if (tidat == null) tidat = "";

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
	$("input[@name=bsok]").click( function() { $("input[@name=page]").val(1); loadsokres(); return false;});
	loadsokres();
});
		 
 </script> 

<div <%= divInfo %>>
<div id="divdocsok">

<h1>Betalningar</h1>
<form id="sokform" action="">
<input type="hidden" name="get" value="betjourlista"/>
<table id="doclist"><tr>
<td colspan="3" id="d1">
Till datum: (ееее-mm-dd) <input id="tidat" type="text" name="tidat" value="<%= tidat %>"/>
<span id="inputpage">Sida: <input type="text" name="page" value="<%= page1 %>"/></span>
<input type="submit" value="Sцk" name="bsok" />

</td></tr><tr><td>
<a href="javascript:toggleD1();" id="aToggleD1" >Alternativ</a>
<td align="left">Sida: <span id="sida"></span></td>
<td align="right"><a href="javascript:loadPreviousPage();" id="getpreviouspage" ></a>&nbsp;<a href="javascript:loadNextPage();" id="getnextpage" ></a></td>
</tr></table></form>
</div>
<div id="divdoclist"></div>
</div>