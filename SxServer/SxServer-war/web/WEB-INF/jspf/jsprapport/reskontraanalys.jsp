<%-- 
    Document   : reskontraanalys
    Created on : 2009-feb-23, 21:35:44
    Author     : ulf
--%>

<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Calendar" %>

<h1>Reskontra vid olika månader</h1>
<table>
<%
	Connection con = (java.sql.Connection)request.getAttribute("con");
	PreparedStatement ps = con.prepareStatement(
			  "select round(sum(belopp)/100)*100, round(sum(case when falldat < ? then belopp else 0 end )/100)*100 as forfallet, " +
			  " round(sum(case when falldat < ?::date-30 then belopp else 0 end )/100)*100 as forfallet30  from" +
			  " ( SELECT f1.faktnr, F1.datum+f1.ktid as falldat, f1.t_attbetala - sum(coalesce(b.bet,0)) as belopp" +
			  "   FROM faktura1 f1 left outer join betjour b on b.faktnr = f1.faktnr AND b.betdat <= ?" +
			  "   where  f1.datum <= ? " +
			  "   group by f1.faktnr, F1.datum+f1.ktid, f1.t_attbetala" +
			  "   having f1.t_attbetala - sum(coalesce(b.bet,0)) <> 0 " +
			  " ) kk "
			  );
	Calendar cal = Calendar.getInstance();
	int ar = cal.get(Calendar.YEAR);
	int man = cal.get(Calendar.MONTH);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	cal.set(Calendar.MONTH, man+1); //Första datumet i efterföljande månad
	java.sql.Date dat;
	ResultSet rs;
	for (int cn=0; cn < 24+1+man; cn++) {
		cal.add(Calendar.DAY_OF_MONTH, -1); // Sista datumet i månaden

		dat = new java.sql.Date(cal.getTimeInMillis());
		ps.setDate(1, dat);
		ps.setDate(2, dat);
		ps.setDate(3, dat);
		ps.setDate(4, dat);
		rs = ps.executeQuery();
		if (rs.next()) {
			out.print("<tr><td>" + SXUtil.getFormatDate(dat) + "</td><td>"
					  + SXUtil.getFormatNumber(rs.getDouble(1), 0) + "</td><td>"
					  + SXUtil.getFormatNumber(rs.getDouble(2), 0) + "</td><td>"
					  + SXUtil.getFormatNumber(rs.getDouble(3), 0) + "</td></tr>");
		}
		cal.add(Calendar.DAY_OF_MONTH, 1);	// Återställ  till första datumet i efterföljande månad.
														//Vi gör detta för att vi vet vad vi får när vi sedan ändrar hela datumet
														// sista datumet varierar varierar från mån till mån
		cal.add(Calendar.MONTH, -1);

	}
%>
</table>