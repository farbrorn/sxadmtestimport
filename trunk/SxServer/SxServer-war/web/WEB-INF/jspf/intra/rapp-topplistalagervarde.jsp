<%-- 
    Document   : rapp-lagervarde
    Created on : 2009-feb-22, 20:34:39
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>


<%
String jspName = "topplistalagervarde";
if (!"true".equals(request.getParameter("sokform"))) {
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
} else {



Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

final String OB_VARDE = "varde";
final String OB_TID = "tid";
final String OB_LAGERKOSTNAD = "kostnad";
final String checkedStr = "checked=\"checked\"";

String lagernr = SXUtil.toStr(request.getParameter("lagernr"));
String orderby = SXUtil.toStr(request.getParameter("orderby"));
if (orderby==null) orderby=OB_LAGERKOSTNAD;
%>
 <script type="text/javascript" src="sxdoclib.js"></script>
 <script type="text/javascript">
 $(document).ready(function() {
	$("input[@name=orderby]").click( function() { $("input[@name=page]").val(1); loadsokreslocal(); });
	$("input[@name=bsok]").click( function() { $("input[@name=page]").val(1); loadsokreslocal(); return false;});
	loadsokres();
});
function loadsokreslocal() {
	$("#divdoclist").load("?" + $("#sokform").serialize(), function() {  } );
}

 </script>

<div id="divdocsok">
<form id="sokform" action="">
	Sortera:
	<input type="radio" name="orderby" value="<%= OB_LAGERKOSTNAD %>" <% if (OB_LAGERKOSTNAD.equals(orderby)) out.print(checkedStr); %>/>Lagerkostnad &nbsp;
	<input type="radio" name="orderby" value="<%= OB_VARDE %>" <% if (OB_VARDE.equals(orderby)) out.print(checkedStr); %>/>Lagervärde &nbsp;
	<input type="radio" name="orderby" value="<%= OB_TID %>" <% if (OB_TID.equals(orderby)) out.print(checkedStr); %>/>Omsättningstid &nbsp;
   <input type="submit" name="bsok" value="Visa" />
	<input type="hidden" name="get" value="rapp-<%= jspName %>"/>
	<input type="hidden" name="lagernr" value="<%= lagernr %>"/>
</form>
</div>
<div id="divdoclist"></div>

<% } %>