<%-- 
    Document   : produktnot-update
    Created on : 2009-jul-24, 15:17:52
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="se.saljex.sxserver.tables.*" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="se.saljex.sxlibrary.*" %>

<%
FormHandlerSteproduktnot f = (FormHandlerSteproduktnot)request.getAttribute("FormHandlerSteproduktnot");
boolean d = false; //Disable i vid view
boolean e = false; //Disable i fält vid update
String ds="disabled=\"disabled\"";
String es="";
%>
 <script type="text/javascript">
	$(document).ready(function() {
		$("textarea[@name=<%= f.K_FRAGA %>]").focus();
		$("input[@name=<%= f.K_SERVICEOMBUDKUNDNR %>]").keyup( function() {  loadsokreslocal(); 	});
	});

	function loadsokreslocal() {
		$("input[@name=sokstr]").val($("input[@name=<%= f.K_SERVICEOMBUDKUNDNR %>]").val());
		$("td[id=tdinfo]").load("?" + $("#sokformsomb").serialize(), function() {  } );
	}
	function setSomb(nr,na) {
		$("input[@name=<%= f.K_SERVICEOMBUDKUNDNR %>]").val(nr);
		$("input[@name=<%= f.K_SERVICEOMBUDNAMN %>]").val(na);
		$("td[id=tdinfo]").val('');
	}

 </script>


<% if (f.isMainActionNew()) { %>
<h1>Registrera ny notering</h1>
<% } else if (f.isMainActionUpdate()) {
	e = true; %>
<h1>Ändra notering</h1>
<% } else {
	d = true; %>
<h1>Visa notering</h1>
<% } %>

<% if (f.isFormError()) { %> <div id="errortext"> <%= f.getFormError() %> </div> <% } %>
<form action="?id=produktnot&<%= f.K_ACTION %>=<%= f.getNextFormAction() %>&<%= f.K_SN %>=<%= request.getParameter(f.K_SN) %>&<%= f.K_ID %>=<%= f.t.getId() %>" method="post" enctype="multipart/form-data">
<table>
	<tr>
		<td>Serienr:</td>
		<td colspan="2"><%= SXUtil.toHtml(request.getParameter(f.K_SN)) %></td>
	</tr>
	<tr>
		<td colspan="3" valign="top">
			Fråga:<br/><span id="fotnot">Vid serviceorder anges felbeskrivning för serviceombud.</span>
			<br/><textarea <%= d || e ? ds : es %> rows="10" cols="80" name="<%= f.K_FRAGA %>"><%= SXUtil.toStr(f.t.getFraga()) %></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="3" valign="top">
			Svar:<br/><span id="fotnot">Vid serviceorder anges svar från serviceombud.</span>
			<br/><textarea <%= d ? ds : es %> rows="10" cols="80" name="<%= f.K_SVAR %>"><%= SXUtil.toStr(f.t.getSvar()) %></textarea>
		</td>
	</tr>
	<tr>
		<td>Ärendetyp</td>
		<td>
			<select <%= d ? ds : es %> name="<%= f.K_ARENDETYP %>">
				<%= f.getArendetypHtmlOptions() %>
			</select>
		</td>
		<td rowspan="7" id="tdinfo"></td>
	</tr>
	<tr>
		<td>Felorsak</td>
		<td>
			<select <%= d ? ds : es %> name="<%= f.K_FELORSAK %>">
				<%= f.getFelorsakHtmlOptions() %>
			</select>
		</td>
	</tr>
	<tr>
		<td>Serviceombud Kundnr:</td>
		<td><input <%= d ? ds : es %> type="text" name="<%= f.K_SERVICEOMBUDKUNDNR %>" value="<%= SXUtil.toStr(f.t.getServiceombudkundnr()) %>"/></td>	</tr>
	<tr>
		<td>Serviceombud Namn:</td>
		<td><input <%= d ? ds : es %> type="text" name="<%= f.K_SERVICEOMBUDNAMN %>" value="<%= SXUtil.toStr(f.t.getServiceombudnamn()) %>"/></td>
	</tr>
	<tr>
		<td>Följ upp datum:</td>
		<td><input <%= d ? ds : es %> type="text" name="<%= f.K_FOLJUPPDATUM %>" value="<%= SXUtil.getFormatDate(f.t.getFoljuppdatum()) %>"/><br/><span id="fotnot">(Blankt fält för att ta bort uppföljning)</span></td>
	</tr>
	<tr>
		<td>Publicera som Q&A:</td>
		<td><input <%= d ? ds : es %> type="checkbox" name="<%= f.K_PUBLICERASOMQA %>" <%= f.t.getPublicerasomqa() > 0 ? "checked" : "" %> /></td>
	</tr>
	<tr>
		<td>Bifogad fil:</td>
		<td><input <%= d || !SXUtil.isEmpty(f.t.getFilnamn()) ? ds : es %> type="file" name="<%= f.K_FILNAMN %>" value=""/></td>
	</tr>
	<tr><td colspan="2"><input type="submit" value="Spara"/></td></tr>
</table>
</form>

<a href="?id=produktnot&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>
<form id="sokformsomb" action="">
	<input type="hidden" name="get" value="sokserviceombud"/>
	<input type="hidden" name="sokstr" value=""/>
	<input type="hidden" name="rows" value="7"/>
</form>