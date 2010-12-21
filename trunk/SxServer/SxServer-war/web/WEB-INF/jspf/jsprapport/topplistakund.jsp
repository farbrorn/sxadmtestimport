<%-- 
    Document   : topplistakund
    Created on : 2009-feb-22, 07:53:20
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
Connection con = (Connection)request.getAttribute("con");
ResultSet rs;

final String OB_SUMMA = "summa";
final String OB_SALDA = "salda";
final String OB_RADER = "rader";
final String OB_TB = "tb";
final String checkedStr = "checked=\"checked\"";

String lagerTyp = request.getParameter("lagertyp");
if (lagerTyp==null) lagerTyp = "lager";

String kundNr = request.getParameter("kundnr");

int frar = 0;
int tiar = 0;
int frman = 0;
int timan = 0;
int maxrader = 100;
int sida = 0;
Integer lagerNr = null;

if (!"alla".equals(request.getParameter("lagernr"))) {
	lagerNr = 0;
	try {
		lagerNr = Integer.parseInt(request.getParameter("lagernr"));
	} catch (NumberFormatException e) {}
}

String sqlOrder;
String orderForklaring;
String orderby = request.getParameter("orderby");

if (OB_SALDA.equals(orderby)) {
	sqlOrder = "sum(f2.lev) desc";
	orderForklaring = "Antal sålda artiklar";
} else if (OB_RADER.equals(orderby)) {
	sqlOrder = "count(*) desc";
	orderForklaring = "Antal orderrader";
} else if (OB_TB.equals(orderby)) {
	sqlOrder = "sum(f2.summa-f2.netto*f2.lev) desc";
	orderForklaring = "Täckninsbidrag";
} else {
	sqlOrder = "sum(f2.summa) desc";
	orderForklaring = "Fakturabelopp";
	orderby = OB_SUMMA;
}


try {	frar = Integer.parseInt(request.getParameter("frar"));} catch (NumberFormatException e) {}
try {	tiar = Integer.parseInt(request.getParameter("tiar"));} catch (NumberFormatException e) {}

try {	frman = Integer.parseInt(request.getParameter("frman"));} catch (NumberFormatException e) {}
try {	timan = Integer.parseInt(request.getParameter("timan"));} catch (NumberFormatException e) {}
try {	maxrader = Integer.parseInt(request.getParameter("maxrader"));} catch (NumberFormatException e) {}

try {	sida = Integer.parseInt(request.getParameter("sida")); } catch (NumberFormatException e) {}
if (sida > 0) sida--; else sida=0; // Sida 1 = offest 0, dvs minska sidan med 1 för att få rätt offset

Calendar cal = Calendar.getInstance() ;

if (frar == 0) frar = cal.get(Calendar.YEAR);
if (tiar == 0) tiar = cal.get(Calendar.YEAR);
if (frman == 0) frman = cal.get(Calendar.MONTH);
if (timan == 0) timan = cal.get(Calendar.MONTH);

if (frar > tiar) frar = tiar;
if (frar == tiar && frman > timan) frman = timan;

if (maxrader < 0) maxrader = 100;

String lagerNamn = "Alla filialer";

if (!"true".equals(request.getParameter("inputform"))) {


	// Lageruppgifter
	if (lagerNr != null) {
		rs = con.createStatement().executeQuery("select bnamn  from lagerid where lagernr=" + lagerNr);
		if (!rs.next()) {
			out.print("Ogiltigt lager");
			return;
		}
		lagerNamn = rs.getString(1);
		rs.close();
	}

	java.util.Date frdat;
	java.util.Date tidat;
	cal.clear();
	cal.set(frar,frman-1,1);	// Ftåndatumer blrjar dag 1 i perioden
	frdat = cal.getTime();
	cal.set(tiar,timan-1,1);
	cal.add(Calendar.MONTH, 1);	// Tilldatumet är sista datumet i månaden. Ta först fram 1:a i efterföljande månad
	cal.add(Calendar.DAY_OF_MONTH, -1);   // minska sedan med 1 för att få sista i aktuell perios
	tidat = cal.getTime();

	PreparedStatement p = con.prepareStatement(
		"select f1.kundnr, k.namn, round(sum(f2.summa)/100)*100, round(sum(case f2.netto when 0 then 0 else f2.summa-f2.lev*f2.netto end)/100)*100, count(*), sum(f2.lev) "
		+" from faktura1 f1, faktura2 f2, kund k where k.nummer=f1.kundnr and f1.faktnr = f2.faktnr "
		+" and (f1.lagernr = ? or 0=? or (rtrim(substring(f1.saljare,1,30)) in (select namn from saljare where lagernr=?)) and 1=? ) "
		+" and  (f1.kundnr = ? or 0=?) "
		+" and f1.datum between ? and ?"
		+" and f2.artnr <> '*RÄNTA*' and f2.artnr <> '*BONUS*' and f2.lev<>0"
		+" group by f1.kundnr, k.namn"
		+" order by " + sqlOrder
		+" limit " + maxrader
		+" offset " + maxrader*sida
			  );
	p.setInt(1, lagerNr!=null ? lagerNr : 0);
	p.setInt(2, lagerNr==null ? 0 : 1);
	p.setInt(3, lagerNr!=null ? lagerNr : 0);
	p.setInt(4, (lagerNr==null || !"team".equals(lagerTyp)) ? 0 : 1);
	p.setString(5, kundNr!=null ? kundNr : "");
	p.setInt(6, kundNr==null || kundNr.isEmpty() ? 0 : 1);
	p.setDate(7, new java.sql.Date(frdat.getTime()));
	p.setDate(8, new java.sql.Date(tidat.getTime()));
	rs = p.executeQuery();

	%>
				<h1>Kundtoppen för <%= lagerNamn %></h1>
				<% if (lagerNr!=null) {
						if ("team".equals(lagerTyp)) {
							%>Rapporten inkluderar teamförsäljning från andra filialer<%
						} else {
							%>Rapporten visar endast försäljning över vald filial.<%
						}
					}
					if (kundNr!=null && !kundNr.isEmpty()) { %> Filtrerat på kundnr <%= kundNr %><% }
				%>
				<table>
					<tr><td>Period</td><td><%= SXUtil.getFormatDate(frdat) + "-" + SXUtil.getFormatDate(tidat) %></td></tr>
					<tr><td>Sortering</td><td><%= orderForklaring %></td></tr>
				</table>
				<table id="doclist">
					<tr>
						<th class="tds30">Kund</th>
						<th class="tdn12">Fakturabelopp</th>
						<th class="tdn12">Täckning</th>
						<th class="tdn12">Fakturarader</th>
						<th class="tdn12">Antal sålda</th>
						<th></th>
					</tr>
					<%
					boolean oddRow = true;
					while (rs.next()) {

						if (oddRow) out.print("<tr class=\"trdocodd\">"); else out.print("<tr class=\"trdoceven\">");
						oddRow = !oddRow;
						%>
						<td class="tds30"><a href="kund?id=setkund&kundnr=<%= rs.getString(1) %>"><%= SXUtil.toHtml(rs.getString(2)) %></a></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(3),0) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(4),0) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(5),0) %></td>
						<td class="tdn12"><%= SXUtil.getFormatNumber(rs.getDouble(6),0) %></td>
					</tr>
						<%
					}
					%>
				</table>
<%
	} else {
	//Skiriv inputform
	String p = null;
	%>
<h1>Kundtoppen</h1>
<form action="" method="get">
		<table><tr><td>Lager:</td>
		<td>
			<select name="lagernr" style="width: 200px;">
			<%
				if (lagerNr==null) lagerNr=0;
				ResultSet rsk = con.createStatement().executeQuery("SELECT lagernr, bnamn FROM lagerid");
				while( rsk.next() ) {
					out.print("<option value=\"" + rsk.getInt(1) + "\"");
					if (lagerNr != null && lagerNr.equals(rsk.getInt(1))) out.print(" selected=\"selected\"");
					out.print(">" + SXUtil.toHtml(rsk.getString(2)) + "</option>");
				}
				%> <option value="alla" <%
				if (lagerNr == null) { %>  selected="selected" <% }
				%> >Alla</option>


			</select> <br/>
		</td></tr>
		<tr>
			<td>Från år/man:</td><td>
				<input type="text" name="frar" maxlength="4" value="<%= frar %>" style="width: 50px;"/>
				<input type="text" name="frman" maxlength="2" value="<%= frman %>" style="width: 50px;"/>
			</td>
		</tr>
		<tr>
			<td>Till år/man:</td><td>
				<input type="text" name="tiar" maxlength="4" value="<%= tiar %>" style="width: 50px;"/>
				<input type="text" name="timan" maxlength="2" value="<%= timan %>" style="width: 50px;"/>
			</td>
		</tr>
		<tr>
			<td>Sortera</td>
			<td>
				<input type="radio" name="orderby" value="<%= OB_SUMMA %>" <% if (OB_SUMMA.equals(orderby)) out.print(checkedStr); %>/>Fakturabelopp<br/>
				<input type="radio" name="orderby" value="<%= OB_TB %>" <% if (OB_TB.equals(orderby)) out.print(checkedStr); %>/>Täckning<br/>
				<input type="radio" name="orderby" value="<%= OB_RADER %>" <% if (OB_RADER.equals(orderby)) out.print(checkedStr); %>/>Fakturarader<br/>
				<input type="radio" name="orderby" value="<%= OB_SALDA %>" <% if (OB_SALDA.equals(orderby)) out.print(checkedStr); %>/>Anal sålda<br/>
			</td>
		</tr>
		<tr>
			<td>Antal rader:</td><td>
				<input type="text" name="maxrader" maxlength="6" value="<%= maxrader %>" style="width: 50px;"/>
			</td>
		</tr>
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
   <input type="submit" value="Visa" />
	</td></tr></table>
</form>

<% } %>
