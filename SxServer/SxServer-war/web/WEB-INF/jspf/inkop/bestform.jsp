<%-- 
    Document   : printkundinfo
    Created on : 2008-jun-16, 19:42:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.util.List" %>
<%@ page import="se.saljex.sxserver.websupport.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxlibrary.SXSession" %>


<% 
SXSession sxSession = WebSupport.getSXSession(session);

se.saljex.sxserver.web.inkop.BestForm b = (se.saljex.sxserver.web.inkop.BestForm)request.getAttribute("bestform");
String errstr;
String errinfo;
%>
<div>
	<form action="?id=2" method="post">
<h1></h1>
<% if (b.isParseError()) { %>
<div id="errortext">Fel i formuläret. Var vänlig korrigera markerade fält.<br/>Error in form. Please correct marked fields
<%= SXUtil.toHtml(b.getParseText()) %>
</div>
<% } %>

<% if (b.isSaveError()) { %>
<div id="errortext">Oväntat fel vid bearbetning av data. Var vänlig försök igen.<p/>Error processing data. Please retry.
<%= SXUtil.toHtml(b.getSaveText()) %>
</div>
<% } %>

<div id="infotext">
<% if (b.isSavedOK()) { if (b.isLevDatumBekraftat()) {%>
Sparat OK! Vi har registrerat leveransbekräftelsen enligt nedan. Tack för din hjälp.<p/>
Saved OK! Order confirmation is received. Thanks for your help.<p/>
<% } else  { %>
Leveransbekräftelsen är mottagen, men inget datum är angivet. Vi uppskattar om du har möjlighet att lämna leveransdatum.<p/>
Confirmation is received, but no delivery date is supplied. We'd appreciate it if you also can conirm delivery date.<p/>
<% } %>

<% } else if (b.isLevDatumBekraftat()) { %>
Vi har mottagit leveransbekräftelse. Du kan ändra leveransdatum nedan.<p/>
We have received your order confirmation. You may change delivery date in the inpout field below.<p/>
<%} else if (b.isMottagen()) { %> 
Beställningen är kvitterad. Vi uppskattar om Ni har möjlighet att ange leveransdatum i rutan nedan. Om någon artikelrad har avvikande leveransdatum kan det anges för varje rad.<p/>
Receiption of order is confirmed. We would appreciate it if you also confirm date of delivery in the input field below. If one or more lines have a different delivery dates, dates can be given for each line.<p/>
<% } else { %>
Beställningen är inte kvitterad av mottagaren.<p/>
Order is not confirmed by receiver.
<% }%>	 
<table width="100%">
	<tr>
	<td class="tddocheadrubrik" colspan="2">Leveransdatum. Anges i formen åååå-mm-dd.<br/>Date of delivery. Please enter as yyyy-mm-dd.</td>
	 <% if (SXUtil.toStr(b.getFormBekrdatErr()).isEmpty()) { errstr=""; errinfo=""; } else {errstr="error"; errinfo=SXUtil.toHtml(b.getFormBekrdatErr());} %>
	<td class="tds30"><input class="tdinput<%= errstr %>" type="text" size="10" name="<%= b.getNameBekrdat() %>" value="<%= SXUtil.toHtml(b.getFormBekrdat()) %>">
		<%= errinfo %>
	</td>
	<td><input type="submit" name="skicka" value="Sänd bekräftelse"></td>
 </tr>
</table>
</div>

<table id="dochead">
<tr><th colspan="4">Inköpsorder</th></tr>
<tr>
	<td class="tddocheadrubrik">Beställningsnummer</td>
	<td class="tds30"><%= b.be1.getBestnr() %></td>
	<td class="tddocheadrubrik">Datum</td>
	<td class="tddatum"><%= b.be1.getDatum() %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Vår referens</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getVarRef()) %></td>
	<td class="tddocheadrubrik">Er referens</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getErRef()) %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Leveransadress</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr0()) %></td>
	<td class="tddocheadrubrik"></td>
	<td class="tds30"><%  %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">&nbsp;</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr1()) %><br/></td>
	<td class="tddocheadrubrik"></td>
	<td class="tds30"><%  %></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr2()) %></td>
	<td></td>
	<td class="tds30"><%  %></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLevadr3()) %></td>
	<td></td>
	<td class="tds30"><% %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Leveransdatum</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getLeverans()) %></td>
	<td class="tddocheadrubrik">Märke</td>
	<td class="tds30"><%= SXUtil.toHtml(b.be1.getMarke()) %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Märke</td>
	<td colspan="3"><%= SXUtil.toHtml(b.be1.getMarke()) %></td>
</tr>
<tr>
	<td class="tddocheadrubrik">Meddelande</td>
	<td colspan="3"><%= SXUtil.toHtml(b.be1.getMeddelande()) %></td>
</tr>
<tr>
</tr>
</table>

<table id="doc">
<tr>
<th class="tds15">Art.nr.</th>
<th class="tds15">Ert art.nr</th>
<th class="tds30">Benämning</th>
<th class="tdn12">Antal</th>
<th class="tds3">Enh</th>
<th class="tdn12">Pris</th>
<th class="tdn4">Rab</th>
<th class="tds10">Leveransdatum</th>
<th></th>
</tr>
<% for (se.saljex.sxserver.web.inkop.BestFormRad r : b.rader) {%>
	 <% if (SXUtil.toStr(r.formBekrdatErr).isEmpty()) { errstr=""; errinfo=""; } else {errstr="error"; errinfo=SXUtil.toHtml(r.formBekrdatErr);} %>
<tr>
	<td class="tds15"><%= SXUtil.toHtml(r.artnr) %></td>
	<td class="tds15"><%= SXUtil.toHtml(r.bartnr) %></td>
	<td class="tds30"><%= SXUtil.toHtml(r.artnamn) %></td>
	<td class="tdn12"><%= r.best %></td>
	<td class="tds3"><%= SXUtil.toHtml(r.enh) %></td>
	<td class="tdn12"><%= SXUtil.getFormatNumber(r.pris) %></td>
	<td class="tdb4"><%= SXUtil.getFormatNumber(r.rab) %></td>
	<td class="tds10"><input class="tdinput<%= errstr %>" type="text" size="10" name="<%= r.getNameBekrdat() %>" value="<%= SXUtil.toHtml(r.formBekrdat)%>">
		<%= errinfo %>
	 </td>
</tr>
	<% if (r.stjSaljare != null) { %>
	 <tr>
		  <td>&nbsp;</td>
			<td colspan="7">Detta är en specialartikel. Vid frågor vänligen kontakta <%= SXUtil.toHtml(r.stjSaljare) %> Tel: <%= SXUtil.toHtml(r.stjSaljareTel) %> E-post: <%= SXUtil.toHtml(r.stjSaljareEpost) %> </td>
	 </tr>
<%		}
	 }%>   
</table>
</form>
</div>