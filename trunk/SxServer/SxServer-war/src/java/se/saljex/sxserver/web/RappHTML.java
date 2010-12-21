/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author ulf
 */
public class RappHTML extends RappHandler {
	StringBuilder sbGlobal = new StringBuilder();
	private HttpServletRequest request;
	private int antalColumns;		//Antal columner som finns i tabellen
	
	public RappHTML(Connection con, HttpServletRequest request) throws SQLException {
		super(con);
		this.request = request;
	}
	
	public String print() throws SQLException {
		int cn = 0; 
		sbGlobal.append("<h1>" + SXUtil.toHtml(super.getReportRubrikString()) + "</h1>");
		sbGlobal.append("<table>");
		for ( SqlFilterField f :super.sqlFilters) {
			if (!f.hidden) {
				sbGlobal.append("<tr><td>" + SXUtil.toHtml(f.label) + "</td><td>" + SXUtil.toHtml(f.getValueString()) + "</td></tr>");
			}
		}
		sbGlobal.append("</table>");
		sbGlobal.append("<table id=\"rapp\"><tr>");
		for (cn = 1; cn<=super.noColumns; cn++) {
			sbGlobal.append("<th>");
			sbGlobal.append(super.colArr[cn].getLabel());
			sbGlobal.append("</th>");			
		}
		antalColumns = cn;		//Antal columner som finns i tabellen

		sbGlobal.append("</tr>");
		while (super.next()) {
			sbGlobal.append("<tr>");
			for (cn = 1; cn<=super.noColumns; cn++) {
				if (!super.colArr[cn].isHidden()) {
					if (super.colArr[cn].getJavaType() == TYP_INT || super.colArr[cn].getJavaType() == TYP_BIGDECIMAL || super.colArr[cn].getJavaType() == TYP_FLOAT || super.colArr[cn].getJavaType() == TYP_DOUBLE) {
						sbGlobal.append("<td class=\"tdn\">"); 
					} else {
						sbGlobal.append("<td>"); 
					}
					sbGlobal.append(super.colArr[cn].getCurrValueString());
					sbGlobal.append("</td>");
				}
			}
			sbGlobal.append("</tr>");
		}
		sbGlobal.append("</table>");
		return sbGlobal.toString();
	}
	
	@Override
	protected String getFilterValue(String name) { 
		if (name == null) return null;
		return request.getParameter(name);
	}

	@Override
	protected void printGroupFooter(int col) {
		sbGlobal.append("<tr class=\"trrappgroupfooter\"><td colspan=\"" + antalColumns + "\" class=\"tdrappgroupfooter\">" + super.colArr[col].getGroupByFooterTextString() + "</td></tr>");
	}
	@Override
	protected void printGroupHeader(int col) {
		sbGlobal.append("<tr class=\"trrappgroupheader\"><td colspan=\"" + antalColumns + "\" class=\"tdrappgroupheader\">" + super.colArr[col].getGroupByHeaderTextString() + "</td></tr>");
	}
	@Override
	protected void printSum(Sum s) {
		int x1 = antalColumns-1;		//Kommer att räknas ut till antalet kolumner för texten
		int x2;							//Kommer att räknas ut till antalet kolumner för summan
		int m = x1%2;
		if (x1 > 1) {
			x1 = x1/2;
			x2 = x1;
			if (m > 0) x1++;		// Om det är udda antal kolumner så ökar vi den ena så det går jämt ut
		} else {	//Om vi bara har en kolumn i rapporten så kommer summan att skrivas i en extra kolumn vilket kan tolkas som en bug men vi bortser från det
			x1 = 1;
			x2 = 1;
		}
		sbGlobal.append("<tr class=\"trrappsum\"><td colspan=\"" + x1 + "\" class=\"tdrappsum\">" + s.getText() + "</td><td colspan=\"" + x2 + "\" class=\"tdrappsum tdn\">" + s.getSumString() + "</td></tr>");
	}

	public String printHTMLInputForm(Integer rappId) throws SQLException {
		StringBuilder sb = new StringBuilder();
		
		if (rappId == null) rappId = 0;
		Statement s1;
		ResultSet rs1;
		s1 = con.createStatement();
		sb.append("<form action=\"\">");
		sb.append("<input type=\"hidden\" name=\"rappid\" value=\"" + rappId + "\"/>");
		sb.append("<input type=\"hidden\" name=\"get\" value=\"viewrapp\"/>");
		sb.append("<table>");
		
		rs1 = s1.executeQuery("select reportrubrik, kortbeskrivning from rapphuvud where rappid = " + rappId);
		if (rs1.next()) {
			sb.append("<tr><td colspan=\"2\">" + SXUtil.toHtml(rs1.getString(1)) + "</td></tr>");
			sb.append("<tr><td colspan=\"2\">" + SXUtil.toHtml(rs1.getString(2)) + "</td></tr>");
		}
		rs1 = s1.executeQuery("select name, label, hidden, defaultvalue from rappprops where rappid = " + rappId + " and type='Filter' and (hidden = 0 or hidden is null) order by wherepos");
		while(rs1.next()) {
			sb.append("<tr><td>" + SXUtil.toHtml(rs1.getString(2)) + "</td><td><input type=\"text\" name=\"" + rs1.getString(1) + "\" value=\"" + rs1.getString(4) + "\"/></td></tr>");
		}
		sb.append("<tr><td><input type=\"submit\" value=\"Skriv rapport\" name=\"sok\"/></td></tr></table></form>");
		s1.close();
		return sb.toString();
	}
}
