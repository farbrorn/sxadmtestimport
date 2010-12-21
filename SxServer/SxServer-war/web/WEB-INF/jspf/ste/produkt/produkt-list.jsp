<%-- 
    Document   : produkt-list
    Created on : 2009-jul-18, 07:22:49
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.util.List" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
FormHandlerSteprodukt f = (FormHandlerSteprodukt)request.getAttribute("FormHandlerSteprodukt");
WebTable wt;
String sokAction;	//Action som utförs för att få söklistan
if (f.ACTION_FOLJUPPLIST.equals(f.getAction())) {
	wt = f.getWebTableForUppfoljning();
	sokAction = f.ACTION_FOLJUPPLIST;
} else {
	wt = f.getWebTable();
	sokAction = f.ACTION_LIST;
}
List<TableSteprodukt> l = wt.getPage();
%>

<% if (f.isIdRequest()) { // Skriv ut sökhuvud om det är en id-request, annars antar vi get och bara skriver listan%>
 <script type="text/javascript">
	$(document).ready(function() {
		$("input[@name=bsok]").click( function() {  loadsokreslocal(); return false;});
		$("input[@name=sokstr]").keyup( function() {  loadsokreslocal(); 	});
		$("input[@name=sokstr]").focus();
		updateNextPrev();
	});

	function loadsokreslocal() {
		$("input[@name=get]").val("produkt");	//Sätt temporärt för get-anrop
		$("#divdoclist").load("?" + $("#sokform").serialize(), function() { updateNextPrev(); } );
	}
	function updateNextPrev() {
		$("input[@name=get]").val("");	//Reset för at anrop utan javascript ska fungera
		cur = $("input[@name=p_currentpage]").val();
		 next = $("input[@name=p_nextpage]").val();
		 prev = $("input[@name=p_previouspage]").val();
		 if (prev < cur) { $("#getpreviouspage").html("Föregående"); } else {$("#getpreviouspage").html("");}
		 if (next > cur) { $("#getnextpage").html("Nästa"); } else {$("#getnextpage").html("");}
		 $("#sida").html(cur);
	 }

	function loadNextPage() {
		$("input[@name=page]").val(next);
		loadsokreslocal();
	}
	function loadPreviousPage() {
		$("input[@name=page]").val(prev);
		loadsokreslocal();
	}


	  var sxglobrad;
	 function show(rad, nr, parameterdata) {
		  sxglobrad = rad;
		if ($("a[name='a"+rad+"']").val() == "Dölj") {
			$("a[name='a"+rad+"']").val("Visa").html("Visa");
			$("#f" + rad).slideUp("fast", function() { $(this).html(""); })
			$("#tr"+rad).removeClass("trhighlite");
		} else {
			$("a[name='a"+rad+"']").val("Dölj").html("Dölj");
			$("#tr"+rad).addClass("trhighlite");;
		  $.get("?", parameterdata , function(data) {$("#f" + sxglobrad).append(data).slideDown("fast");});
		}
	}
		function shownotering(rad,sn) {
			 show(rad,sn, { get: "produktnot", action: "<%= f.ACTION_LIST %>", sn: sn });
		 }


 </script>
	<div id="divdocsok">

<% if (f.ACTION_FOLJUPPLIST.equals(f.getAction())) { %>
	<h1>Produkter för uppföljning</h1>
<% } else { %>
	<h1>Produkter</h1>
<% } %>
	<form id="sokform" action="">
	<input type="hidden" name="id" value="produkt"/>
	<input type="hidden" name="get" value=""/>
	<input type="hidden" name="action" value="<%= sokAction %>"/>
	<input type="hidden" name="pagesize" value="<%= SXUtil.toStr(request.getParameter(f.K_PAGESIZE)) %>"/>
	<input type="hidden" name="page" value="1"/>
	<table id="doclist">
	<tr>
		<td colspan="3" id="d1">
			Sök: <input id="sokstr" type="text" name="sokstr" value="<%= SXUtil.toStr(request.getParameter(f.K_SOKSTR)) %>" />
			<input type="submit" value="Sök" name="bsok"/>
		</td>
	</tr><tr>
		<td>&nbsp;</td>
		<td align="left">Sida: <span id="sida"><%= wt.getCurrentPageNo() %></span></td>
		<td align="right">
			<a href="javascript:loadPreviousPage();" id="getpreviouspage" ></a>
			&nbsp;
			<a href="javascript:loadNextPage();" id="getnextpage" ></a>
		</td>
	</tr></table></form>
	</div>

<div id="divdoclist">
<% } %>



<table id="doclist">
	<tr>
		<th class="tdknapp"></th>
		<th class="tds10"></th>
		<th class="tds10">Serienr</th>
		<th class="tds10">Inst.datum</th>
		<th class="tds30">Modell</th>
		<th class="tds30">Installatör/Slutkund</th>
		<th></th>
	</tr>
<%
int radcn=0;
for (TableSteprodukt t : l) {
	radcn++;
	if (radcn % 2 > 0) {
		%><tr id="tr<%= radcn %>" class="trdocodd"> <%
	} else {
		%><tr id="tr<%= radcn %>" class="trdoceven"> <%
	}

%>
<td class="tdknapp"><a  href="?id=produkt&<%= f.K_ACTION %>=<%= f.ACTION_UPDATE %>&<%= f.K_SN %>=<%= SXUtil.toHtml(t.getSn()) %>" onclick="shownotering(<%= radcn %>,'<%= t.getSn() %>'); return false;" name="a<%= radcn %>">Visa</a></td>
	<td class="tds10"><a href="?id=produkt&<%= f.K_ACTION %>=<%= f.ACTION_UPDATE %>&<%= f.K_SN %>=<%= SXUtil.toHtml(t.getSn()) %>">Ändra</a></td>
	<td class="tds10"><%= SXUtil.toHtml(t.getSn()) %></td>
	<td class="tds10"><%= SXUtil.getFormatDate(t.getInstdatum()) %></td>
	<td class="tds30"><%= SXUtil.toHtml(t.getModell()) %></td>
	<td class="tds30"><%= SXUtil.toHtml(t.getInstallatornamn()) %>
			<br/><%= SXUtil.toHtml(t.getNamn()) %> <%= SXUtil.toHtml(t.getAdr1()) %>
	</td>
	<td></td>
</tr>
<tr><td colspan="7"><div id="f<%= radcn %>" class="docgrupp" ></div></td></tr>
<%
}
%>
</table>
<form id="parametrar" action="">
	<input type="hidden" name="p_currentpage" value="<%= wt.getCurrentPageNo() %>"/>
	<input type="hidden" name="p_nextpage" value="<%= wt.getNextPageNo() %>"/>
	<input type="hidden" name="p_previouspage" value="<%= wt.getPreviousPageNo() %>"/>
</form>

<% if (f.isIdRequest()) { // Skriv ut sökhuvud om det är en id-request, annars antar vi get och bara skriver listan%>
</div>
<% } %>
