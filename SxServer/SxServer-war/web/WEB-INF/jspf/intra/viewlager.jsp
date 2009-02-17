<%-- 
    Document   : lagerlista
    Created on : 2009-feb-17, 15:37:28
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
%>
<table class="midtable"width="780px">
	<tr>
		<td width="580px">
			<div class="midgroup">
				<h4> <%= lagerNamn %></h4>
				Test
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
