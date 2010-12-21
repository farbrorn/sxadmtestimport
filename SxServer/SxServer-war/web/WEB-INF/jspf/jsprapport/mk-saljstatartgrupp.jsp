<%-- 
    Document   : mk-saljstatartgrupp
    Created on : 2009-mar-27, 20:08:21
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.websupport.GoogleChartHandler" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
int frar = 0;
try {
	frar = Integer.parseInt(request.getParameter("frar"));
} catch (NumberFormatException e) {}

Calendar cal = Calendar.getInstance() ;
int iAr = cal.get(Calendar.YEAR);
if (frar < iAr-40 || frar > iAr) frar = iAr-3;

Connection con = (Connection)request.getAttribute("con");
ResultSet rs;
String anvandare = null;

// Om vi har skickat med anvandare som attribut betyder det att vi har låst rapporten till den
// användaren via någon typ av behörighetskontroll. Användaren ska inte kunnaa ändras via parameter
boolean lasTillAnvandre = true;
anvandare = (String)request.getAttribute("anvandare");
if (anvandare==null) { //  Bars om vi inte skickat med som attrubyr
	anvandare=request.getParameter("anvandare");
	lasTillAnvandre = false;
}

if (lasTillAnvandre || (anvandare != null && !"true".equals(request.getParameter("inputform")))) {


	PreparedStatement p = con.prepareStatement(
"select r.rabkod, r.beskrivning, b.ar, b.man, b.summa, b.tb " +
" from rabkoder r left outer join " +
" (select year(f1.datum) as ar, month(f1.datum) as man, a.rabkod as rabkod, sum(f2.summa) as summa, " +
" coalesce(sum(f2.summa - case when f2.netto = 0 then f2.summa else f2.lev*f2.netto + case when f1.bonus<>0 then f2.summa*fu.bonusproc2/100 else 0 end end ),0) as tb " +
" from faktura1 f1 join faktura2 f2 on f1.faktnr=f2.faktnr  join artikel a on a.nummer=f2.artnr, fuppg fu  " +
" where f1.saljare like ? and year(f1.datum) between ? and ?  " +
" group by year(f1.datum), month(f1.datum), a.rabkod) b on b.rabkod = r.rabkod " +
" where coalesce(r.kod1,'') = '' " +
" order by r.rabkod, b.ar, b.man, r.rabkod "
			  		  );
	p.setString(1, anvandare+"%");
	p.setInt(2, frar);
	p.setInt(3, iAr);
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
				<h1>Försäljning per artikelgrupp för <%= anvandare %></h1>
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
		<table><tr><td>Användare:</td>
		<td>
			<select name="anvandare" style="width: 200px;">
			<%
				ResultSet rsk = con.createStatement().executeQuery("SELECT namn from saljare order by namn");
				while( rsk.next() ) {
					if (anvandare==null) anvandare=rsk.getString(1);
					out.print("<option value=\"" + rsk.getString(1) + "\"");
					if (anvandare.equals(rsk.getString(1))) out.print(" selected=\"selected\"");
					out.print(">" + SXUtil.toHtml(rsk.getString(1)) + "</option>");
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
