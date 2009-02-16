<%-- 
    Document   : welcome
    Created on : 2009-feb-16, 16:31:48
    Author     : ulf
--%>
<%@ page import="se.saljex.sxserver.SXUtil" %>
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
%>

<table width="780px">
	<tr>
		<td width="580px" align="top">
			<div class="midgroup">
				<h4>Aktuellt</h4>
				Test
			</div>
			<div class="midgroup midgroupkalender">
				<h4>Kalender</h4>
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
		<td width="200px" align="top">
			<div class="rgroupforsalj">
				<h4>Försäljning</h4>
				<%= iAr %>: <%= SXUtil.getFormatNumber(sumIAr, 0) %><br/>
				<%= fgAr %>: <%= SXUtil.getFormatNumber(sumFgAr, 0) %>
			</div>
			<div class="rgroup">
				<h4>Kreditupplysning</h4>
				<form name="loginForm" method="post" action="https://webapp.shj.se/Justitia/login.do" target="_new">
					<input type="hidden" name="product" value="product3">
					<input type="hidden" name="username" value="556409006" id="logintextfield">
					<input type="hidden" name="password" value="123456" id="logintextfield">
					<input name="submit" id="submitButton" type="submit" value="Justitia" class="submitbutton">
				</form>
			</div>
			<div class="rgroup">
				<h4>Sök</h4>
				<form action="http://www.google.com/cse" id="cse-search-box">
				  <div>
					 <input type="hidden" name="cx" value="001183810265035031341:q46cpi9zlmm" />
					 <input type="hidden" name="ie" value="ISO-8859-1" />
					 <input type="text" name="q" size="31" />
					 <input type="submit" name="sa" value="Sök" />
				  </div>
				</form>
				<script type="text/javascript" src="http://www.google.com/coop/cse/brand?form=cse-search-box&lang=sv"></script>
			</div>
		</td>
	</tr>

</table>
