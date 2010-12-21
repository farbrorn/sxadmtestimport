<%-- 
    Document   : filialstat1
    Created on : 2009-mar-21, 15:59:09
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

Integer lagerNr = null;
try {
	lagerNr = Integer.parseInt(request.getParameter("lagernr"));
} catch (NumberFormatException e) {}
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

	// Lageruppgifter
	String lagerNamn = "Alla filialer";
	if (lagerNr!=null) {
		rs = con.createStatement().executeQuery("select bnamn  from lagerid where lagernr=" + lagerNr);
		if (!rs.next()) {
			out.print("Ogiltigt lager");
			return;
		}
		lagerNamn = rs.getString(1);
		rs.close();
	}

	PreparedStatement p = con.prepareStatement(
"select year(f1.datum), month(f1.datum), count(distinct f2.ordernr) as order, count(distinct f1.faktnr) as fakturor, sum(case when f2.lev <> 0 then 1 else 0 end) as orderrader, coalesce(sum(f2.summa),0), " +
" coalesce(sum(f2.summa - case when f2.netto = 0 then f2.summa else f2.lev*f2.netto + case when f1.bonus<>0 then f2.summa*fu.bonusproc2/100 else 0 end end ),0), " +
" count(distinct f1.kundnr), count(distinct f2.artnr) " +
" from faktura1 f1 join faktura2 f2 on f1.faktnr = f2.faktnr, fuppg fu " +
" where (f1.lagernr = ? or 0=? or (rtrim(substring(f1.saljare,1,30)) in (select namn from saljare where lagernr=?)) and 1=? ) " +
" and  (f1.kundnr = ? or 0=?) " +
" and year(f1.datum) between ? and ?  " +
" group by year(f1.datum), month(f1.datum) " +
" order by  year(f1.datum) desc, month(f1.datum) "
			  );
	p.setInt(1, lagerNr!=null ? lagerNr : 0);
	p.setInt(2, lagerNr==null ? 0 : 1);
	p.setInt(3, lagerNr!=null ? lagerNr : 0);
	p.setInt(4, (lagerNr==null || !"team".equals(lagerTyp)) ? 0 : 1);
	p.setString(5, kundNr!=null ? kundNr : "");
	p.setInt(6, kundNr==null || kundNr.isEmpty() ? 0 : 1);
	p.setInt(7, frar);
	p.setInt(8, iAr);
	rs = p.executeQuery();
	
	int arrSize = iAr - frar + 1;
	int[][] antalOrder = new int[arrSize][12];
	int[][] antalFakturor = new int[arrSize][12];
	int[][] antalOrderRader = new int[arrSize][12];
	int[][] antalUnikaKunder = new int[arrSize][12];
	int[][] antalUnikaArtiklar = new int[arrSize][12];
	double[][] summa = new double[arrSize][12];
	double[][] tb = new double[arrSize][12];
	while (rs.next()) {
		antalOrder[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(3);
		antalFakturor[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(4);
		antalOrderRader[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(5);
		summa[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getDouble(6)/1000;
		tb[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getDouble(7)/1000;
		antalUnikaKunder[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(8);
		antalUnikaArtiklar[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(9);
	}
	GoogleChartHandler gch = new GoogleChartHandler();
	GoogleChartHandler gch2 = new GoogleChartHandler();
	gch.setEtiketterJanToDec();
	gch2.setEtiketterJanToDec();
	gch2.setScaleMax(100.0);
	gch.setSize(350, 200);
	gch2.setSize(350, 200);
	%>
				<h1>Försäljning för <%= lagerNamn %></h1>
				<% if (lagerNr!=null) {
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
					boolean odd = false;
					%><tr><td colspan="13"><b><br/>Nettoförsäljning (tusental kronor)</b></td></tr><%
					gch.clearSerier();
					gch2.clearSerier();
					gch2.setScaleMax(100.0);
					for (int cn=0; cn <= iAr-frar; cn++) {
						gch.addSerie("" + (iAr-cn), summa[cn]);
						GoogleChartHandler.SerieInfo si = gch2.addSerie("TB " + (iAr-cn));
						for (int man = 0; man < 11; man++) {
							si.addValue(summa[cn][man]!=0.0 ? tb[cn][man]/summa[cn][man]*100 : 0.0 );
						}
					}
					%>	<tr><td colspan="13"><img src="<%= gch.getURL() %>"/><img src="<%= gch2.getURL() %>"/></td></tr>		<%

					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= Math.round(summa[cn][mn]) %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b><br/>Täckning (tusental kronor)</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {	gch.addSerie("" + (iAr-cn), tb[cn]);	}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= Math.round(tb[cn][mn]) %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b><br/>Antal fakturor</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {	gch.addSerie("" + (iAr-cn), antalFakturor[cn]);	}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalFakturor[cn][mn] %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b><br/>Antal Order</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {	gch.addSerie("" + (iAr-cn), antalOrder[cn]);	}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalOrder[cn][mn] %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b><br/>Antal Orderrader</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {	gch.addSerie("" + (iAr-cn), antalOrderRader[cn]);	}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalOrderRader[cn][mn] %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b><br/>Medeltalet orderrader/order</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {
						GoogleChartHandler.SerieInfo sf = gch.addSerie("" + (iAr-cn));
						for (int mn = 0; mn < 12; mn++) {
						sf.addValue(antalOrder[cn][mn]!=0 ? Math.round(antalOrderRader[cn][mn]/antalOrder[cn][mn]) : 0.0);
						}
					}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalOrder[cn][mn]!=0 ? Math.round(antalOrderRader[cn][mn]/antalOrder[cn][mn]) : 0 %></td><%
						}
						%></tr><%
					}


					odd = false;
					%><tr><td colspan="13"><b><br/>Medelbelopp per order (kronor)</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {
						GoogleChartHandler.SerieInfo sf = gch.addSerie("" + (iAr-cn));
						for (int mn = 0; mn < 12; mn++) {
							sf.addValue(antalOrder[cn][mn]!=0 ? Math.round(summa[cn][mn]*1000/antalOrder[cn][mn]) : 0.0);
						}
					}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalOrder[cn][mn]!=0 ? Math.round(summa[cn][mn]/antalOrder[cn][mn]) : 0 %></td><%
						}
						%></tr><%
					}



					odd = false;
					%><tr><td colspan="13"><b><br/>Antal unika kunder</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {	gch.addSerie("" + (iAr-cn), antalUnikaKunder[cn]);	}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalUnikaKunder[cn][mn] %></td><%
						}
						%></tr><%
					}


					odd = false;
					%><tr><td colspan="13"><b><br/>Antal unika artiklar</b></td></tr><%
					gch.clearSerier();
					for (int cn=0; cn <= iAr-frar; cn++) {	gch.addSerie("" + (iAr-cn), antalUnikaArtiklar[cn]);	}
					%>
					<tr><td colspan="13"><img src="<%= gch.getURL() %>"/></td></tr>
					<%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalUnikaArtiklar[cn][mn] %></td><%
						}
						%></tr><%
					}
					%>
				</table>
<%
	} else {
	//Skiriv inputform
	String p = null;
	%>
<h1>Statistik för lager</h1>
<form action="" method="get">
		<table><tr><td>Lager:</td>
		<td>
			<select name="lagernr" style="width: 200px;">
			<%
				if (lagerNr==null) lagerNr=0;
				ResultSet rsk = con.createStatement().executeQuery("SELECT lagernr, bnamn FROM lagerid");
				while( rsk.next() ) {
					out.print("<option value=\"" + rsk.getInt(1) + "\"");
					if (lagerNr.equals(rsk.getInt(1))) out.print(" selected=\"selected\"");
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
