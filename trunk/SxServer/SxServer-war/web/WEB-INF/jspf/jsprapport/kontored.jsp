<%-- 
    Document   : rapp-kontored
    Created on : 2009-jan-24, 21:31:04
    Author     : ulf
--%>
<%@ page import="se.saljex.sxlibrary.*" %>
<%@ page import="se.saljex.sxserver.web.*" %>
<%@ page import="java.sql.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<h1>Resultatrapport</h1>
Visar resultat per kostnadsställe, baserat på bokföring och täckningsbidrag på försäljning.<p/>
<%
	Integer year = 0;
	Integer periodFrom = 0;
	Integer periodTo = 0;
	String kstString = null;
	Integer kstInt = null;
	String kstName = "";
	String qAcc;
	Double sum;
	Double tb;
	Connection con = (java.sql.Connection)request.getAttribute("con");

	java.sql.Date frDat;
	java.sql.Date toDat;

	try {  year = java.lang.Integer.parseInt(request.getParameter("year"));  } catch (java.lang.NumberFormatException e) {}
	try {  periodFrom = java.lang.Integer.parseInt(request.getParameter("period_from"));  } catch (java.lang.NumberFormatException e) {}
	try {  periodTo = java.lang.Integer.parseInt(request.getParameter("period_to"));  } catch (java.lang.NumberFormatException e) {}

	kstString = SXUtil.toStr(request.getParameter("kst"));
	try {  kstInt = java.lang.Integer.parseInt(kstString);  } catch (java.lang.NumberFormatException e) {}


	if (!(year == 0 || periodFrom == 0 || periodTo == 0 || periodFrom > periodTo || "true".equals(request.getAttribute("inputform")))) {

		toDat = SXUtil.getSQLDate(SXUtil.createDate(year, periodTo+1, 1));
		frDat = SXUtil.getSQLDate(SXUtil.createDate(year, periodFrom, 1));


		String qTB = " SELECT SUM(T_NETTO-T_INNETTO) FROM FAKTURA1 " +
							" WHERE DATUM >= '" + SXUtil.getFormatDate(frDat) + "'" +
							" AND DATUM < '" + SXUtil.getFormatDate(toDat) + "'" +
							" AND YEAR(DATUM) = " + year +
							" AND (" +
							"      (" +
									"  LAGERNR = " + kstInt +
									 " AND SUBSTR( SALJARE, 1, 30 ) NOT IN ( "+
									 "   SELECT NAMN FROM SALJARE WHERE LAGERNR <> 0 AND LAGERNR <> " + kstInt +
									 " ) "+
								 " OR ("+
										" SUBSTR( SALJARE, 1, 30) IN ("+
											" SELECT NAMN FROM SALJARE WHERE LAGERNR = " + kstInt +
										  ")" +
									 " )"+
									" )"+
							")";

		String kstFilter = "";

		if (kstString.isEmpty()) {					// Visa ospecificerade kostnadsställen
			kstName = "Ospecificerat kostnadsställe";
			kstFilter = "KST = '' OR KST IS NULL";
			qTB = "select 0 ";			//Vi har inga ospecificerade fakturor och 0 i TB
		} else if ("all".equals(kstString)) {	// Visa alla kostnadsställen
			kstName = "Alla kostnadsställe";
			kstFilter = "1=1";
			// alla kostnadsställen kräver en annorlunda uträkning av TB
			qTB = "  SELECT SUM(T_NETTO-T_INNETTO) AS TÄCKNINGSBIDRAG FROM FAKTURA1"
					+ " WHERE DATUM >= '" +SXUtil.getFormatDate(frDat) + "'"
					+ " AND DATUM < '" + SXUtil.getFormatDate(toDat) + "'"
					+ " AND YEAR(DATUM) = " + year;
		} else if (kstInt != null) {				// Visa angivet kostnadsställe, men bara om det är numeriskt
			kstName = "Kostnadsställe " + kstInt;
			kstFilter = "kst = '" + kstString + "'";
		} else {											// Fel
			out.print("Felaktigt kostnadsställe!");
			return;
		}
		qAcc = " SELECT KONTONR AS KONTO, NAMN, SUM(DEBET+KREDIT) AS SUMMA"
					+ " FROM BOKVER2 LEFT OUTER JOIN KONTOREG ON KONTO = KONTONR"
					+ " WHERE FT = 1"
					+ " AND AR = " + year
					+ " AND VER > 0"
					+ " AND KONTONR > '5'"
					+ " AND ( " + kstFilter + " )"
					+ " AND PER BETWEEN " + periodFrom + " AND " + periodTo
					+ " GROUP BY KONTONR, NAMN"
					+ " ORDER BY KONTONR";

		java.sql.ResultSet rs_qTB = con.createStatement().executeQuery(qTB);
		if (rs_qTB.next()) {
			tb = rs_qTB.getDouble(1);
		} else tb = 0.0;

		java.sql.ResultSet rs_qAcc = con.createStatement().executeQuery(qAcc);
		out.print("<b>Kostnadsställe:</b> " + kstName
			+ "<br/><b>Period:</b> " + periodFrom + " - " + periodTo
			+ "<br/><b>År:</b> " + year
			+ "<p/><table><tr><th>Kontonr</th><th>Namn</th><th>Belopp</th></tr>"
			);
		sum = 0.0;
		while (rs_qAcc.next()) {
			out.print("<tr>"
				+ "<td>" + rs_qAcc.getString(1) + "</td>"
				+ "<td>" + rs_qAcc.getString(2) + "</td>"
				+ "<td align=\"right\">" + SXUtil.getFormatNumber(rs_qAcc.getDouble(3)) + "</td>"
				+ "</tr>"
				);
			sum = sum + rs_qAcc.getDouble(3);
		}
		out.print("<tr><td colspan=\"2\">Summa kostnader:</td><td align=\"right\">" + SXUtil.getFormatNumber(sum) + "</td></tr>");
		out.print("<tr><td colspan=\"2\">Täckningsbidrag (inkl. Grumskunder)</td><td align=\"right\">" + SXUtil.getFormatNumber(tb) + "</td></tr>");
		out.print("<tr><td colspan=\"2\">Resultat: </td><td align=\"right\">" + SXUtil.getFormatNumber(tb-sum) + "</td></tr>");
		out.print("</table>");
	} else {
%>


        <form action="" method="get">
			 <input type="hidden" name="id" value="printjsprapport"/>
			 <input type="hidden" name="jsp" value="kontored"/>
          År:<br />
			 <input type="text" maxlength="4" name="year" value="<%= SXUtil.getFormatDate().substring(0, 4) %>" style="width: 200px;" />

          <p />
          Fr.o.m. Period/Månad:<br />
          <select name="period_from" style="width: 200px;">
            <option value="01">Januari</option>
            <option value="02">Februari</option>
            <option value="03">Mars</option>
            <option value="04">April</option>
            <option value="05">Maj</option>
            <option value="06">Juni</option>
            <option value="07">Juli</option>
            <option value="08">Augusti</option>
            <option value="09">September</option>
            <option value="10">Oktober</option>
            <option value="11">November</option>
            <option value="12">December</option>
          </select>

          <p />
          T.o.m. Period/Månad:<br />
          <select name="period_to" style="width: 200px;">
            <option value="01">Januari</option>
            <option value="02">Februari</option>
            <option value="03">Mars</option>
            <option value="04">April</option>
            <option value="05">Maj</option>
            <option value="06">Juni</option>
            <option value="07">Juli</option>
            <option value="08">Augusti</option>
            <option value="09">September</option>
            <option value="10">Oktober</option>
            <option value="11">November</option>
            <option value="12">December</option>
          </select>

          <p />
          Kostnadsställe:<br />
          <select name="kst" style="width: 200px;">
            <%

				  ResultSet rsk = con.createStatement().executeQuery("SELECT KST, NAMN FROM BOKKST WHERE FT = 1");

              while( rsk.next() ) {
					 out.print("<option value=\"" + rsk.getString(1) + "\">" + SXUtil.toHtml(rsk.getString(2)) + " (" + SXUtil.toHtml(rsk.getString(1)) + ")</option>");
              }

            %>
            <option value="">Ospec. kostnadsställe</option>
            <option value="all">Alla kostnadsställen</option>
          </select>

          <p />
          <input type="submit" value="Visa" />
        </form>
<% } %>