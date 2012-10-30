<%-- 
    Document   : welcome
    Created on : 2009-feb-16, 16:31:48
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<%
Connection con = (Connection)request.getAttribute("con");

// Statistikuppgifter
PreparedStatement p = con.prepareStatement("select year(datum), month(datum), sum(t_netto) from faktura1 " +
							" where year(datum) in (?,?) and month(datum)=? " +
							" group by year(datum), month(datum) order by year(datum)");
Calendar cal = Calendar.getInstance() ;
int iAr = cal.get(Calendar.YEAR);
int fgAr = iAr - 1;
p.setInt(1, iAr);
p.setInt(2, fgAr);
p.setInt(3, cal.get(Calendar.MONTH)+1);
ResultSet rs = p.executeQuery();
Double sumFgAr = 0.0;
Double sumIAr = 0.0;
if (rs.next()) sumFgAr  = rs.getDouble(3);
if (rs.next()) sumIAr = rs.getDouble(3);
rs.close();

InlaggHandler inh  = new InlaggHandler(con);
ArrayList<InlaggHandler.IntraKanal> arrKan = inh.getKanalerOnStartPage();
ArrayList<InlaggHandler.IntraInlagg> arrInl = inh.getInlaggListByKanalId(null);
%>

<table class="midtable"width="780px">
	<tr>
		<td width="580px">
			<div class="midgroup" style="overflow: scroll; height: 300px">
				<h1>Kalender</h1>
				<table><tr><th>Datum</th><th>Tid</th><th>Händelse</th><th></th></tr>
				<%
				rs = con.createStatement().executeQuery("select f_dat, f_tid, kmemo from kalender order by f_dat, f_tid");
				while (rs.next()) {
				%>
				<tr>
					<td><%= SXUtil.getFormatDate(rs.getDate(1)) %></td>
					<td><%= rs.getTime(2) %></td>
					<td><%= SXUtil.toHtml(rs.getString(3)) %></td>
				</tr>

				<%
				}
				%>
				</table>
			</div>
		</td>
		<td width="200px">
			<div class="rgroupforsalj">
				<h4>Försäljning</h4>
				<%= iAr %>: <%= SXUtil.getFormatNumber(sumIAr, 0) %><br/>
				<%= fgAr %>: <%= SXUtil.getFormatNumber(sumFgAr, 0) %>
			</div>
			<div class="rgroup">
				<h4>Kreditupplysning</h4>
				<a href="http:www.shj.se">Till Justitia</a>
			</div>
		</td>
	</tr>

</table>
