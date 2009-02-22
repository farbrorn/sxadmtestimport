<%--
    Document   : rapp-topplistaartikel
    Created on : 2009-feb-21, 16:14:59
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="java.sql.*" %>

<%
String jspName = "topplistaartikel";
if (!"true".equals(request.getParameter("sokform"))) {
	RapportLista jr = new RapportLista();
	jr.printJspRapport(request, response, jspName);
} else {



Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

final String OB_SUMMA = "summa";
final String OB_SALDA = "salda";
final String OB_RADER = "rader";
final String OB_TB = "tb";
final String checkedStr = "checked=\"checked\"";

String frar = SXUtil.toStr(request.getParameter("frar"));
String lagernr = SXUtil.toStr(request.getParameter("lagernr"));
String orderby = SXUtil.toStr(request.getParameter("orderby"));
if (frar==null) frar = SXUtil.getFormatDate().substring(0, 4);
if (orderby==null) orderby=OB_SUMMA;
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
	Från år: <input type="text" name="frar" maxlength="4" value="<%= frar %>" style="width: 50px;"/>
	Sortera:
	<input type="radio" name="orderby" value="<%= OB_SUMMA %>" <% if (OB_SUMMA.equals(orderby)) out.print(checkedStr); %>/>Fakturabelopp &nbsp;
	<input type="radio" name="orderby" value="<%= OB_TB %>" <% if (OB_TB.equals(orderby)) out.print(checkedStr); %>/>Täckning &nbsp;
	<input type="radio" name="orderby" value="<%= OB_RADER %>" <% if (OB_RADER.equals(orderby)) out.print(checkedStr); %>/>Fakturarader &nbsp;
	<input type="radio" name="orderby" value="<%= OB_SALDA %>" <% if (OB_SALDA.equals(orderby)) out.print(checkedStr); %>/>Anal sålda &nbsp;
   <input type="submit" name="bsok" value="Visa" />
	<input type="hidden" name="get" value="rapp-<%= jspName %>"/>
	<input type="hidden" name="lagernr" value="<%= lagernr %>"/>
</form>
</div>
<div id="divdoclist"></div>

<% } %>