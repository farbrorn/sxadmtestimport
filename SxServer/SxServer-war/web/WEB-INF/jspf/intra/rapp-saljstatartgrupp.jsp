<%-- 
    Document   : rapp-saljstatartgrupp
    Created on : 2009-mar-22, 18:24:38
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="java.sql.*" %>

<%
String jspName = "saljstatartgrupp";
%>

 <script type="text/javascript">
$(document).ready(function() {
	$("input[@name=bsok]").click( function() {  loadsokres(); return false;});
/*	$("input[@name=sokstr]").keyup( function() {  loadsokres(); }); */
	loadsokres();
});

function loadsokres() {
	$("#divdoclist").load("rapp?" + $("#sokform").serialize(), function() {  } );
}

 </script>

<div id="divdocsok">

<h1>Statistik artikelgrupper</h1>
<form id="sokform" action="">
<input type="hidden" name="get" value="printjsprapport"/>
<input type="hidden" name="jsp" value="<%= jspName %>"/>
<table id="doclist">
<tr>
	<td>Lager</td><td><input type="text" name="lagernr" value="<%= SXUtil.toStr(request.getParameter("lagernr")) %>"/></td>
</tr>
<tr>
	<td>Lagertyp</td><td><input type="text" name="lagertyp" value="<%= SXUtil.toStr(request.getParameter("lagertyp")) %>"/></td>
</tr>
<tr>
	<td colspan="3" id="d1">
		<input type="submit" value="Sök" name="bsok"/>
	</td>
</tr>
</table>
</form>
</div>

<div id="divdoclist"></div>

