<%-- 
    Document   : filialstat1
    Created on : 2009-mar-21, 15:59:09
    Author     : ulf
--%>

<%@ page import="se.saljex.sxserver.SXUtil" %>
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
if (frar < iAr-40 || frar > iAr) frar = iAr-3;

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
"select year(f1.datum), month(f1.datum), count(distinct f2.ordernr) as order, count(distinct f1.faktnr) as fakturor, sum(case when f2.lev <> 0 then 1 else 0 end) as orderrader, coalesce(sum(f2.summa),0), coalesce(sum(f2.summa - case when f2.netto = 0 then f2.summa else f2.lev*f2.netto end),0) " +
" from faktura1 f1 join faktura2 f2 on f1.faktnr = f2.faktnr " +
" where lagernr = ? and year(f1.datum) between ? and ? " +
" group by year(f1.datum), month(f1.datum) " +
" order by  year(f1.datum) desc, month(f1.datum) "
			  );
	p.setInt(1, lagernr);
	p.setInt(2, frar);
	p.setInt(3, iAr);
	rs = p.executeQuery();

	int arrSize = iAr - frar + 1;
	int[][] antalOrder = new int[arrSize][12];
	int[][] antalFakturor = new int[arrSize][12];
	int[][] antalOrderRader = new int[arrSize][12];
	double[][] summa = new double[arrSize][12];
	double[][] tb = new double[arrSize][12];
	while (rs.next()) {
		antalOrder[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(3);
		antalFakturor[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(4);
		antalOrderRader[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getInt(5);
		summa[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getDouble(6);
		tb[iAr - rs.getInt(1)][rs.getInt(2)-1] = rs.getDouble(7);
	}

	%>
				<h1>Försäljning för <%= lagerNamn %></h1>
				Rappporten försäljningsstatistik för <%= lagerNamn %> utan att räkna med försäljning från andra filialer.
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
					%><tr><td colspan="13"><b>Nettoförsäljning</b></td></tr><%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= SXUtil.getFormatNumber(summa[cn][mn],0) %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b>Täckning</b></td></tr><%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= SXUtil.getFormatNumber(tb[cn][mn],0) %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b>Antal fakturor</b></td></tr><%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalFakturor[cn][mn] %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b>Antal Order</b></td></tr><%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalOrder[cn][mn] %></td><%
						}
						%></tr><%
					}

					odd = false;
					%><tr><td colspan="13"><b>Antal Orderrader</b></td></tr><%
					for (int cn=0; cn <= iAr-frar; cn++) {
						odd = !odd;
						%><tr class="trdoc<%= odd ? "odd" : "even" %>"><td><%= iAr-cn %></td><%
						for (int mn = 0; mn < 12; mn++) {
						%><td class="tdn12"><%= antalOrderRader[cn][mn] %></td><%
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
