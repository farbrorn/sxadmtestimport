<%-- 
    Document   : rapp-filialforsaljning
    Created on : 2009-feb-17, 19:08:36
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
int lagernr = 0;
try {
	lagernr = Integer.parseInt(request.getParameter("lagernr"));
} catch (NumberFormatException e) {}
Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

// Lageruppgifter
rs = con.createStatement().executeQuery("select bnamn  from lagerid where lagernr=" + lagernr);
if (!rs.next()) {
	out.print("Ogiltigt lager");
	return;
}
String lagerNamn = rs.getString(1);
rs.close();


Calendar cal = Calendar.getInstance() ;
int iAr = cal.get(Calendar.YEAR);

PreparedStatement p = con.prepareStatement(
	"SELECT YEAR(DATUM), MONTH(DATUM), f1.LAGERNR, li.bnamn, SUM(T_NETTO), SUM(T_NETTO-T_INNETTO)"+
	" from faktura1 f1 left outer join lagerid li on li.lagernr = f1.lagernr"+
	" where year(datum) > ? and" +
	" (f1.lagernr = ? or trim(substring(saljare,1,30)) in (select namn from saljare where saljare.lagernr=?))"+
	" group by YEAR(DATUM), MONTH(DATUM), f1.LAGERNR, li.bnamn"+
	" order by YEAR(DATUM) desc, MONTH(DATUM) desc, f1.LAGERNR"
		  );
p.setInt(1, iAr-3);
p.setInt(2, lagernr);
p.setInt(3, lagernr);
rs = p.executeQuery();

%>
<table class="midtable">
	<tr>
		<td width="100%">
			<h4>Försäljning för <%= lagerNamn %></h4>
			Rappporten visar nettoförsäljning för <%= lagerNamn %>, samt försäljning från andra
			filialer där säljaren är från <%= lagerNamn %>
		</td>
	</tr>
	<tr>
		<td>
			<table id="doclist">
				<tr>
					<th class="tds10">År</th>
					<th class="tds3">Mån</th>
					<th class="tds30">Filial</th>
					<th class="tdn12">Netto</th>
					<th class="tdn12">TB</th>
				</tr>
				<%
				Integer tempAr = null;
				Integer tempMan = null;
				Double sumTotAr = 0.0;
				Double sumTBAr = 0.0;
				Double sumTotMan = 0.0;
				Double sumTBMan = 0.0;
				boolean oddRow = true;
				while (true) {
					boolean res = rs.next();
					if (	(res && tempAr != null && (tempAr != rs.getInt(1) || tempMan != rs.getInt(2)))
							  || (!res && tempMan != null)
							  ) {
						// Skriv månadssumma
					%>
							<tr class="trrappsum">
								<td colspan="3">Totalt för månad <%= tempMan %></td>
								<td class="tdn12"><%= SXUtil.getFormatNumber(sumTotMan,0) %></td>
								<td class="tdn12"><%= SXUtil.getFormatNumber(sumTBMan,0) %></td>
							</tr>
					<%
						sumTotMan = 0.0;
						sumTBMan = 0.0;
					}
					if (	(res && tempAr != null && tempAr != rs.getInt(1)) || (!res && tempMan != null) ) {
						%>
							<tr class="trrappsum">
								<td colspan="3">Totalt för år <%= tempAr %></td>
								<td class="tdn12"><%= SXUtil.getFormatNumber(sumTotAr,0) %></td>
								<td class="tdn12"><%= SXUtil.getFormatNumber(sumTBAr,0) %></td>
							</tr>
						<%
						sumTotAr = 0.0;
						sumTBAr = 0.0;
					}
					if (!res) break;		// Avsluta loopen om det inte finns mer rader
					if (tempAr == null) tempAr = 0;
					if (tempMan == null) tempMan = 0;

					if (oddRow) out.print("<tr class=\"trdocodd\">"); else out.print("<tr class=\"trdoceven\">");
					oddRow = !oddRow;
					if (!tempAr.equals(rs.getInt(1)) || !tempMan.equals(rs.getInt(2))) {	// Skriv Årsrubrik
						tempAr = rs.getInt(1);
						tempMan = rs.getInt(2);
						%>
							<td><b><%= rs.getInt(1) %></b></td>
							<td><b><%= rs.getInt(2) %></b></td>
						<%
					} else {	%>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
					<% } %>

					<td><%= rs.getString(4) %></td>
					<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(5),0) %></td>
					<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(6),0) %></td>
				</tr>
					<%
					sumTotAr += rs.getDouble(5);
					sumTBAr += rs.getDouble(6);
					sumTotMan += rs.getDouble(5);
					sumTBMan += rs.getDouble(6);
				}
				%>
			</table>
		</td>
	</tr>
</table>
