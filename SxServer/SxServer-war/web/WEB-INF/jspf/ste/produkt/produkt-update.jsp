<%--
    Document   : produkt-new
    Created on : 2009-jul-18, 07:26:42
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
FormHandlerSteprodukt f = (FormHandlerSteprodukt)request.getAttribute("FormHandlerSteprodukt");
boolean d = false; //Disable i inoutfälten
String ds="disabled=\"disabled\"";
String es="";

%>
 <script type="text/javascript">
$(document).ready(function() {
		$("input[@name=<%= f.K_SN %>]").focus();
});

function setAll(kundnr, kundnamn, artnr, artnamn, faktnr) {
	$("input[@name=<%= f.K_INSTALLATORKUNDNR %>]").val(kundnr);
	$("input[@name=<%= f.K_INSTALLATORNAMN %>]").val(kundnamn);
	$("input[@name=<%= f.K_FAKTNR %>]").val(faktnr);
	setPump(artnr, artnamn);
}
function setPump(artnr, artnamn) {
	$("input[@name=<%= f.K_ARTNR %>]").val(artnr);
	$("input[@name=<%= f.K_MODELL %>]").val(artnamn);
}
		 
 </script>

<% if (f.isMainActionNew()) { %>
<h1>Registrera ny produkt</h1>
<% } else if (f.isMainActionUpdate()) { %>
<h1>Ändra produkt</h1>
<% } else {
	d = true; %>
<h1>Visa produkt</h1>
<% } %>

<table>
	<tr><td valign="top" style="width: 500px;">
			<% if (f.isFormError()) { %> <div id="errortext"> <%= f.getFormError() %> </div> <% } %>
				<form action="?id=produkt&<%= f.K_ACTION %>=<%= f.getNextFormAction() %>" method="post">
			<table>
				<tr><th colspan="2">Grunduppgiter</th></tr>
				<tr>
					<td>Serienr:</td>
					<td><input type="<%= d || f.isMainActionUpdate() ? "hidden" : "text" %>" name="<%= f.K_SN %>" value="<%= SXUtil.toStr(f.t.getSn()) %>" ><%= d || f.isMainActionUpdate() ? f.t.getSn() : "" %></td>
				</tr>
				<tr>
					<td>Artikelnr:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_ARTNR %>" value="<%= SXUtil.toStr(f.t.getArtnr()) %>" ></td>
				</tr>
				<tr>
					<td>Modell:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_MODELL %>" value="<%= SXUtil.toStr(f.t.getModell()) %>" ></td>
				</tr>
				<tr>
					<td>Installationsdatum:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_INSTDATUM %>" value="<%= SXUtil.getFormatDate(f.t.getInstdatum()) %>" ></td>
				</tr>

				<tr><th colspan="2">Uppgiter om installatör</th></tr>
				<tr>
					<td>Kundnr:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_INSTALLATORKUNDNR %>" value="<%= SXUtil.toStr(f.t.getInstallatorkundnr()) %>" ></td>
				</tr>
				<tr>
					<td>Namn:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_INSTALLATORNAMN %>" value="<%= SXUtil.toStr(f.t.getInstallatornamn()) %>" ></td>
				</tr>
				<tr>
					<td>Fakturanr:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_FAKTNR %>" value="<%= f.t.getFaktnr() == null ? "" : f.t.getFaktnr() %>" ></td>
				</tr>

				<tr><th colspan="2">Uppgiter om slutkunden</th></tr>
				<tr>
					<td>Namn</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_NAMN %>" value="<%= SXUtil.toStr(f.t.getNamn()) %>" ></td>
				</tr>
				<tr>
					<td>Adress</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_ADR1 %>" value="<%= SXUtil.toStr(f.t.getAdr1()) %>" ></td>
				</tr>
				<tr>
					<td></td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_ADR2 %>" value="<%= SXUtil.toStr(f.t.getAdr2()) %>" ></td>
				</tr>
				<tr>
					<td></td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_ADR3 %>" value="<%= SXUtil.toStr(f.t.getAdr3()) %>" ></td>
				</tr>
				<tr>
					<td>Referens</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_REFERENS %>" value="<%= SXUtil.toStr(f.t.getReferens()) %>" ></td>
				</tr>
				<tr>
					<td>Tel:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_TEL %>" value="<%= SXUtil.toStr(f.t.getTel()) %>" ></td>
				</tr>
				<tr>
					<td>Mobil:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_MOBIL %>" value="<%= SXUtil.toStr(f.t.getMobil()) %>" ></td>
				</tr>
			<tr>
					<td>E-post:</td>
					<td><input <%= d ? ds : es %> type="text" name="<%= f.K_EPOST %>" value="<%= SXUtil.toStr(f.t.getEpost()) %>" ></td>
				</tr>
				<% if (!f.isMainActionView()) { %>
				<tr><td colspan="2"><input type="submit" name="OK" value="Spara"></td></tr>
				<% } %>
			</table>
			</form>

	</td>
	<td valign="top"  style="width: 300px;">

<%
if (f.isMainActionNew() || f.isMainActionUpdate()) {

Connection con = (Connection)request.getAttribute("con");
Statement stm = con.createStatement();
Statement stm2 = con.createStatement();
ResultSet rs =stm.executeQuery("select k.nummer, k.namn, f2.artnr, f2.namn, f1.faktnr, f1.datum from kund k, faktura1 f1, faktura2 f2, stepumpartnr sa " +
		  " where k.nummer = f1.kundnr and f1.faktnr=f2.faktnr and sa.artnr=f2.artnr and f2.lev > 0" +
		  " and f1.datum > current_date-365" +
		  " and f1.faktnr not in (select faktnr from steprodukt where faktnr is not null)" +
		  " order by f1.faktnr desc"
		  );
ResultSet rsPumpar = stm2.executeQuery("select a.nummer, a.namn from artikel a, stepumpartnr b where b.artnr=a.nummer order by a.nummer");
%>
Oregistrerade produkter
<table id="doclist" style="width: 320px; font-size: 10px;">
	<tr><th style="width: 50px;">Kundnr<br/>Artikelnr</th><th style="width: 200px;">Kund<br/>Artikel</th><th style="width: 70px;">Fakt.datum<br/>Fakturanr</th></tr>
<%
int radcn = 0;
while (rs.next()) {
	radcn++;
	if (radcn % 2 > 0) { %> <tr class="trdocodd"> <%} else { %><tr class="trdoceven"> <%}
%>

<td style="width: 50px;"><a href="javascript:setAll('<%= rs.getString(1) %>', '<%= rs.getString(2) %>', '<%= rs.getString(3) %>', '<%= rs.getString(4) %>', '<%= rs.getInt(5) %>');"><%= SXUtil.toHtml(rs.getString(1)) %></a><br/><%= SXUtil.toHtml(rs.getString(3)) %></td>
	<td style="width: 200px;"><%= SXUtil.toHtml(rs.getString(2)) %><br/><%= SXUtil.toHtml(rs.getString(4)) %></td>
	<td style="width: 70px;"><%= rs.getString(6) %><br/><%= rs.getInt(5) %></td>
</tr>
<%
}
%>
</table>

Pumpar
<table id="doclist" style="width: 320px; font-size: 10px;">
	<tr><th style="width: 50px;">Artikelnr</th><th style="width: 200px;">Artikel</th></tr>
<%
radcn = 0;
while (rsPumpar.next()) {
	radcn++;
	if (radcn % 2 > 0) { %> <tr class="trdocodd"> <%} else { %><tr class="trdoceven"> <%}
%>

<td style="width: 50px;"><a href="javascript:setPump('<%= rsPumpar.getString(1) %>', '<%= rsPumpar.getString(2) %>');"><%= SXUtil.toHtml(rsPumpar.getString(1)) %></a></td>
	<td style="width: 200px;"><%= SXUtil.toHtml(rsPumpar.getString(2)) %></td>
</tr>
<%
}
%>
</table>
<% } %>
	</td><td></td></tr>
</table>
<a href="?id=produkt&<%= f.K_ACTION %>=<%= f.ACTION_LIST %>">Tillbaka</a>