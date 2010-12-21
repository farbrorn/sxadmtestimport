<%-- 
    Document   : rapp-filialforsaljning
    Created on : 2009-feb-17, 19:08:36
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>


<%
Integer lagernr = null;
try {
	lagernr = Integer.parseInt(request.getParameter("lagernr"));
} catch (NumberFormatException e) {}
int frar = 0;
try {
	frar = Integer.parseInt(request.getParameter("frar"));
} catch (NumberFormatException e) {}

Calendar cal = Calendar.getInstance() ;
int iAr = cal.get(Calendar.YEAR);
if (frar < iAr-40 || frar > iAr) frar = iAr-2;

Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

if (lagernr != null && !"true".equals(request.getParameter("inputform"))) {

	// Lageruppgifter
	rs = con.createStatement().executeQuery("select bnamn  from lagerid where lagernr=" + lagernr);
	if (!rs.next()) {
		out.print("Ogiltigt lager");
		return;
	}
	String lagerNamn = rs.getString(1);
	rs.close();



	PreparedStatement p = con.prepareStatement(
		"SELECT YEAR(DATUM), MONTH(DATUM), f1.LAGERNR, li.bnamn, round(SUM(T_NETTO)/100)*100, round(SUM(T_NETTO-T_INNETTO)/100)*100"+
		" from faktura1 f1 left outer join lagerid li on li.lagernr = f1.lagernr"+
		" where year(datum) >= ? and" +
		" (f1.lagernr = ? or trim(substring(saljare,1,30)) in (select namn from saljare where saljare.lagernr=?))"+
		" group by YEAR(DATUM), MONTH(DATUM), f1.LAGERNR, li.bnamn"+
		" order by YEAR(DATUM) desc, MONTH(DATUM) desc, f1.LAGERNR"
			  );
	p.setInt(1, frar);
	p.setInt(2, lagernr);
	p.setInt(3, lagernr);
	rs = p.executeQuery();

	%>
				<h1>Försäljning för <%= lagerNamn %></h1>
				Rappporten visar nettoförsäljning för <%= lagerNamn %>, samt försäljning från andra
				filialer där säljaren är från <%= lagerNamn %>
				<table id="doclist">
					<tr>
						<th class="tds10">År</th>
						<th class="tds3">Mån</th>
						<th class="tds30">Filial</th>
						<th class="tdn12">Netto</th>
						<th class="tdn12">TB</th>
						<th></th>
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
<%
	} else {
	//Skiriv inputform
	String p = null;
	%>
<h1>Försäljning för lager</h1>
<form action="" method="get">
		<table><tr><td>Lager:</td>
		<td>
			<select name="lagernr" style="width: 200px;">
			<%
				if (lagernr==null) lagernr=0;
				ResultSet rsk = con.createStatement().executeQuery("SELECT lagernr, bnamn FROM lagerid");
				while( rsk.next() ) {
					out.print("<option value=\"" + rsk.getInt(1) + "\"");
					if (lagernr.equals(rsk.getInt(1))) out.print(" selected=\"selected\"");
					out.print(">" + SXUtil.toHtml(rsk.getString(2)) + "</option>");
				}
			%>
			</select> <br/>
		</td></tr><tr>
		<td>Från år:</td><td><input type="text" name="frar" maxlength="4" value="<%= frar %>" style="width: 200px;"/></td>
		<tr><td colspan="2">
		<%
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				p = (String)e.nextElement();
				if (p!=null && !"inputform".equals(p)) {
					%><input type="hidden" name="<%= p %>" value="<%= request.getParameter(p) %>"/><%
				}
			}
	%>
   <br/><input type="submit" value="Visa" />
	</td></tr></table>
</form>

<% } %>
