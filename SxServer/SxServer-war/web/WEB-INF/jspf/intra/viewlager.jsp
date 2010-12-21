<%-- 
    Document   : lagerlista
    Created on : 2009-feb-17, 15:37:28
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="se.saljex.sxlibrary.*" %>


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


// Statistikuppgifter
PreparedStatement p = con.prepareStatement("select year(datum), month(datum), sum(t_netto) from faktura1 " +
							" where year(datum) in (?,?) and month(datum)=? and lagernr = " + lagernr +
							" group by year(datum), month(datum) order by year(datum)");
Calendar cal = Calendar.getInstance() ;
int iAr = cal.get(Calendar.YEAR);
int fgAr = iAr - 1;
p.setInt(1, iAr);
p.setInt(2, fgAr);
p.setInt(3, cal.get(Calendar.MONTH)+1);
rs = p.executeQuery();
Double sumFgAr = 0.0;
Double sumIAr = 0.0;
if (rs.next()) sumFgAr  = rs.getDouble(3);
if (rs.next()) sumIAr = rs.getDouble(3);
rs.close();
SparradeKunderHandler sph = new SparradeKunderHandler(con);
ArrayList<SparradeKunderHandler.SparrKund> spka = sph.getSparrList(lagernr);
%>
<table class="midtable"width="780px">
	<tr>
		<td width="580px">
			<div class="midgroup">
				<h1> <%= lagerNamn %></h1>

			</div>
			<div class="midgroup">
				<h1>Spärrlista</h1>
				Lista på de vanligaste kunderna i <%= lagerNamn %> som har överskridit sin kreditgräns.<br/>
				Observera att listan inte är komplett, och kreditkoll måste utföras även på kunder som inte står med på listan.
				<table>
					<tr><th>Kundnr</th><th>Namn</th><th class="tdn12">Kreditgräns</th><th class="tdn12">Total reskontra</th><th class="tdn12">Långtidsförfallet</th></tr>
<%						for (SparradeKunderHandler.SparrKund spk : spka) { %>
						<tr>
							<td><%= SXUtil.toHtml(spk.kundNr) %></td>
							<td><%= SXUtil.toHtml(spk.namn) %></td>
							<td class="tdn12"><%= SXUtil.getFormatNumber(spk.kgrans) %></td>
							<td class="tdn12"><%= SXUtil.getFormatNumber(spk.totalReskontra) %></td>
							<td class="tdn12"><%= SXUtil.getFormatNumber(spk.totaltForfallet60) %></td>
						</tr>
				<% } %>
				</table>
			</div>
		</td>
		<td width="200px">
			<div class="rgroupforsalj">
				<h4>Försäljning</h4>
				<%= lagerNamn %>
				<%= iAr %>: <%= SXUtil.getFormatNumber(sumIAr, 0) %><br/>
				<%= fgAr %>: <%= SXUtil.getFormatNumber(sumFgAr, 0) %>
			</div>
		</td>
	</tr>

</table>
