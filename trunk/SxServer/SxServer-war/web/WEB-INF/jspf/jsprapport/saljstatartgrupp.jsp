<%-- 
    Document   : saljstatartgrupp
    Created on : 2009-mar-22, 14:26:08
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.websupport.GoogleChartHandler" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
String lagerTyp = request.getParameter("lagertyp");
if (lagerTyp==null) lagerTyp = "lager";

String kundNr = request.getParameter("kundnr");

Integer lagernr = null;	//Null betyder alla
if(!"alla".equals(request.getParameter("lagernr"))) {
	try {
		lagernr = Integer.parseInt(request.getParameter("lagernr"));
	} catch (NumberFormatException e) {}
}

int frar = 0;
try {
	frar = Integer.parseInt(request.getParameter("frar"));
} catch (NumberFormatException e) {}

Calendar cal = Calendar.getInstance() ;
int iAr = cal.get(Calendar.YEAR);
if (frar < iAr-40 || frar > iAr) frar = iAr-3;

Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

if (!"true".equals(request.getParameter("inputform"))) {

	String lagerNamn = "Alla filialer";
	if (lagernr!=null) {
		// Lageruppgifter
		rs = con.createStatement().executeQuery("select bnamn  from lagerid where lagernr=" + lagernr);
		if (!rs.next()) {
			out.print("Ogiltigt lager");
			return;
		}
		lagerNamn = rs.getString(1);
		rs.close();
	}



	PreparedStatement p = con.prepareStatement(
"select r.rabkod, r.beskrivning, b.ar, b.man, b.summa, b.tb " +
" from rabkoder r left outer join " +
" (select year(f1.datum) as ar, month(f1.datum) as man, a.rabkod as rabkod, sum(f2.summa) as summa, " +
" coalesce(sum(f2.summa - case when f2.netto = 0 then f2.summa else f2.lev*f2.netto + case when f1.bonus<>0 then f2.summa*fu.bonusproc2/100 else 0 end end ),0) as tb " +
" from faktura1 f1 join faktura2 f2 on f1.faktnr=f2.faktnr  join artikel a on a.nummer=f2.artnr, fuppg fu  " +
" where (f1.lagernr = ? or 0=? or (rtrim(substring(f1.saljare,1,30)) in (select namn from saljare where lagernr=?)) and 1=? ) " +
" and  (f1.kundnr = ? or 0=?) " +
" and year(f1.datum) between ? and ?  " +
" group by year(f1.datum), month(f1.datum), a.rabkod) b on b.rabkod = r.rabkod " +
" where coalesce(r.kod1,'') = '' " +
" order by r.rabkod, b.ar, b.man, r.rabkod "
			  		  );
	p.setInt(1, lagernr!=null ? lagernr : 0);
	p.setInt(2, lagernr==null ? 0 : 1);
	p.setInt(3, lagernr!=null ? lagernr : 0);
	p.setInt(4, (lagernr==null || !"team".equals(lagerTyp)) ? 0 : 1);
	p.setString(5, kundNr!=null ? kundNr : "");
	p.setInt(6, kundNr==null || kundNr.isEmpty() ? 0 : 1);
	p.setInt(7, frar);
	p.setInt(8, iAr);
	rs = p.executeQuery();

	int arrSize = iAr - frar + 1;
	double[][] summa = new double[arrSize][12];
	double[][] tbproc = new double[arrSize][12];

	GoogleChartHandler gch = new GoogleChartHandler();
	GoogleChartHandler gch2 = new GoogleChartHandler();
	gch.setEtiketterJanToDec();
	gch2.setEtiketterJanToDec();
	gch.setSize(350, 200);
	gch2.setSize(350, 200);
%>
				<h1>Försäljning per artikelgrupp för <%= lagerNamn %></h1>
				<% if (lagernr!=null) {
						if ("team".equals(lagerTyp)) {
							%>Rapporten inkluderar teamförsäljning från andra filialer<%
						} else {
							%>Rapporten visar endast försäljning över vald filial.<%
						}
					}
					if (kundNr!=null && !kundNr.isEmpty()) { %> Filtrerat på kundnr <%= kundNr %><% }
				%>
				<table id="doclist">
					<tr>
						<th>År</th>
						<th class="tdn12">Jan</th>
						<th class="tdn12">Feb</th>
						<th class="tdn12">Mar</th>
						<th class="tdn12">Apr</th>
						<th class="tdn12">Maj</th>
						<th class="tdn12">Jun</th>
						<th class="tdn12">Jul</th>
						<th class="tdn12">Aug</th>
						<th class="tdn12">Sep</th>
						<th class="tdn12">Okt</th>
						<th class="tdn12">Nov</th>
						<th class="tdn12">Dec</th>
						<th></th>
					</tr>
					<%
					boolean firstRow = true;
					String tempRabkod = null;
					String tempRabnamn = null;
					boolean rsnext;
					while (true) {
						rsnext = rs.next();
						if (firstRow) {
							firstRow = false;
							tempRabkod = rs.getString(1);
							tempRabnamn = rs.getString(2);
						}
						if (!rsnext || !rs.getString(1).equals(tempRabkod)) {
							%><tr><td colspan="13"><b><br/><%= SXUtil.toHtml(tempRabkod) + " - " + SXUtil.toHtml(tempRabnamn) %></b></td></tr><%
							gch.clearSerier();
							gch2.clearSerier();
							gch2.setScaleMax(100.0);
							for (int cn=0; cn <= iAr-frar; cn++) {
								gch.addSerie("Netto " + (iAr-cn), summa[cn]);
								gch2.addSerie("TB  " + (iAr-cn), tbproc[cn]);
							}
							%>	<tr><td colspan="13"><img src="<%= gch.getURL() %>"/><img src="<%= gch2.getURL() %>"/></td></tr>		<%

							boolean odd = false;
							for (int cn=0; cn <= iAr-frar; cn++) {
								odd = !odd;
								%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
								for (int mn = 0; mn < 12; mn++) {
								%><td class="tdn12"><%= Math.round(summa[cn][mn]) %></td><%
								}
								%></tr><%
							}
							summa = new double[arrSize][12];
							tbproc = new double[arrSize][12];
							if (rsnext) {
								tempRabkod = rs.getString(1);
								tempRabnamn = rs.getString(2);
							}
						}
						if (!rsnext) break;
						if (rs.getInt(3) > 0) {
							summa[iAr - rs.getInt(3)][rs.getInt(4)-1] = rs.getDouble(5)/1000;
							tbproc[iAr - rs.getInt(3)][rs.getInt(4)-1] = rs.getDouble(5) != 0 ? rs.getDouble(6)/rs.getDouble(5)*100 : 0.0;
						}
					}
					%>
				</table>
<%
	} else {
	//Skiriv inputform
	String p = null;
	%>
<h1>Försäljning per artikelgrupp</h1>
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
