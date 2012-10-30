<%-- 
    Document   : rapp1
    Created on : 2012-okt-30, 10:57:37
    Author     : Ulf
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="se.saljex.sxlibrary.SXUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Connection"%>
<%@page import="se.saljex.loginservice.User"%>
<%@page import="se.saljex.loginservice.LoginServiceConstants"%>
<%@page import="se.saljex.sxlibrary.SXConstant"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<%
		User user=null;
		Connection con=null;
		try { user  = (User)request.getSession().getAttribute(LoginServiceConstants.REQUEST_PARAMETER_SESSION_USER); } catch (Exception e) {}
		try { con = (Connection)request.getAttribute("sxconnection"); } catch (Exception e) {}
%>			

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><sx-rubrik>Borlänge</sx-rubrik></h1>
<b>Utveckling av lager på H-Nummer</b><br>
Lagervärde, räknat på riktiga inköpspriser. Beloppen är korrigerade för kundbonus. Alla belopp i 1000-tal kronor<br>
<table style="table-layout: fixed"><tr style="background-color: #eeeeee;"><td style="width: 6em;">Datum</td><td style="width: 6em;">Lagervärde</td><td style="width: 6em;">Dagsförsäljning</td><td style="width: 6em;">Acc. försäljning</td></tr>
<%
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select sum(ilager*a.inpris) from artikel a , lager l where a.nummer=l.artnr and a.nummer like 'H%'");

		double startLagerVarde=0;
		double lopandeLagerVarde=0;
		double lopandeForsaljning=0;
		
		if (rs.next()) {
			startLagerVarde=rs.getDouble(1);
			
			out.print("<tr><td>Nuvarande</td><td>" + SXUtil.getFormatNumber(startLagerVarde/1000,0) + "</td><td>-</td><td>-</td></tr>");
		}


		lopandeLagerVarde=startLagerVarde;
			
		rs = st.executeQuery("select "
			+" case when f.ar is null then lv.ar else f.ar end,"
			+" case when f.ar is null then lv.man else f.man end,"
			+" lv.summa, f.summa from " 
			+ " (select year(datum) as ar, month(datum) as man, sum(summa - case when f1.bonus>0 then summa*0.05 else 0 end) as summa from faktura1 f1, faktura2 f2 where f1.faktnr=f2.faktnr and f1.datum>'2012-09-01' and f2.artnr like 'H%' group by year(datum), month(datum) order by year(datum), month(datum)) f "
			+ " full join "
			+ " (select year(datum) as ar, month(datum) as man, sum(forandring*a.inpris) as summa from lagerhand l , artikel a where a.nummer=l.artnr and artnr like 'H%' and datum >'2012-09-01' group by year(datum), month(datum) having sum(forandring*a.inpris) <> 0 order by year(datum), month(datum) desc) lv "
			+ " on lv.ar=f.ar and lv.man=f.man "
			+ " order by case when f.ar is null then lv.ar else f.ar end desc, case when f.ar is null then lv.man else f.man end desc");
		while (rs.next()) {
			lopandeLagerVarde = lopandeLagerVarde-rs.getDouble(3);
			lopandeForsaljning = lopandeForsaljning + rs.getDouble(4);	
			out.print("<tr><td>" + rs.getString(1) + "-" + rs.getString(2) + "</td><td>" + SXUtil.getFormatNumber(lopandeLagerVarde/1000,0) + "</td><td>" + SXUtil.getFormatNumber(rs.getDouble(4)/1000,0) + "</td><td>" + SXUtil.getFormatNumber(lopandeForsaljning/1000,0) + "</td></tr>");
		}
	

%>	
</table>


<b>Försäljning på filial 10 - Borlänge</b><br>
Täckningen visas dels som täckning baserat på lägre inköpspris för konkurslager, samt som beräknad täckning vid fullvärdesinköp. Beloppen är korrigerade för kundbonus. Alla belopp i 1000-tal kronor<br>
<table style="table-layout: fixed"><tr style="background-color: #eeeeee;"><td style="width: 6em;">Månad</td><td style="width: 6em;">Försäljning</td><td style="width: 6em;">Täckning kk-lager</td><td style="width: 6em;">Täckning nyköp</td></tr>
<%
		rs = st.executeQuery("select year(f1.datum), month(f1.datum), sum(summa*1) as forsaljning, sum((summa-netto*lev) - case when f1.bonus>0 then summa*0.05 else 0 end ) as tb,  sum(summa-netto*lev*case when f2.artnr like 'H%' then 2 else 1 end) as tbNormalkop"
			+ " from faktura1 f1, faktura2 f2"
			+ " where f1.faktnr=f2.faktnr and f1.lagernr=10"
			+ " group by year(f1.datum), month(f1.datum)"
			+ " order by year(f1.datum) desc, month(f1.datum) desc");
		double tbproc1;
		double tbproc2;
		double tb1;
		double tb2;
		double forsaljning;
		
		while (rs.next()) {
			forsaljning = rs.getDouble(3);
			tb1 = rs.getDouble(4);
			tb2 = rs.getDouble(5);
			if (forsaljning!=0) {
				tbproc1 = tb1/forsaljning*100;
				tbproc2 = tb2/forsaljning*100;
			} else {
				tbproc1=0;
				tbproc2=0;
			}
			out.print("<tr><td>" + rs.getInt(1) + "-" + rs.getInt(2) + "</td><td>" + SXUtil.getFormatNumber(forsaljning/1000,0) + "</td><td>" + SXUtil.getFormatNumber(tb1 /1000,0) + " (" + SXUtil.getFormatNumber(tbproc1,0) + "%)</td><td>" + SXUtil.getFormatNumber(tb2 /1000,0) + " (" + SXUtil.getFormatNumber(tbproc2,0) + "%)</td></tr>");
		}
		

%>
</table>



<b>Försäljning Titania</b><br>
Täckningen visas dels som täckning baserat på lägre inköpspris för konkurslager, samt som beräknad täckning vid fullvärdesinköp. Statisiken visar värden för alla Titanias konton som börjar med 081386. Alla belopp i 1000-tal kronor<br>
<table style="table-layout: fixed"><tr style="background-color: #eeeeee;"><td style="width: 6em;">Månad</td><td style="width: 6em;">Försäljning</td><td style="width: 6em;">Täckning kk-lager</td><td style="width: 6em;">Täckning nyköp</td></tr>
<%
		rs = st.executeQuery("select year(f1.datum), month(f1.datum), sum(f2.summa*1), sum(summa-netto*lev), sum(summa-netto*lev*case when f2.artnr like 'H%' then 2 else 1 end) as tbNormalkop"
			+ " from faktura1 f1 , faktura2 f2"
			+ " where f1.faktnr=f2.faktnr and datum >'2012-09-01' and kundnr like '081336%' "
			+ " group by year(datum), month(datum)"
			+ "order by year(datum) desc, month(datum) desc");
		
		while (rs.next()) {
			forsaljning = rs.getDouble(3);
			tb1 = rs.getDouble(4);
			tb2 = rs.getDouble(5);
			if (forsaljning!=0) {
				tbproc1 = tb1/forsaljning*100;
				tbproc2 = tb2/forsaljning*100;
			} else {
				tbproc1=0;
				tbproc2=0;
			}
			out.print("<tr><td>" + rs.getInt(1) + "-" + rs.getInt(2) + "</td><td>" + SXUtil.getFormatNumber(forsaljning/1000,0) + "</td><td>" + SXUtil.getFormatNumber(tb1 /1000,0) + " (" + SXUtil.getFormatNumber(tbproc1,0) + "%)</td><td>" + SXUtil.getFormatNumber(tb2 /1000,0) + " (" + SXUtil.getFormatNumber(tbproc2,0) + "%)</td></tr>");
		}
		

%>
</table>


    </body>
</html>
