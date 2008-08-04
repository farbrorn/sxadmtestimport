/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ulf
 */
public class RappEdit {
	private boolean isLoaded = false;
	
	protected ArrayList<RappColumn> arrColumn = new ArrayList();
	protected ArrayList<RappFilter> arrFilter = new ArrayList();
	protected ArrayList<RappSum> arrSum = new ArrayList();
	protected RappHuvud huvud;
	protected int rappSession;
	protected Integer rappId = null;
	
	
	public RappEdit(Connection con, int rappSession, Integer rappId) throws SQLException , RappEdit.RappException{
		this.rappSession = rappSession;
		this.rappId = rappId;
		huvud = new RappHuvud(rappSession);
		if (rappId != null) load(con);
	}

	public int getRappSession() { return rappSession; }
	public ArrayList<RappColumn> getArrColumn () { return arrColumn; }
	public ArrayList<RappFilter> getArrFilter () { return arrFilter; }
	public ArrayList<RappSum> getArrSum () { return arrSum; }

//	public String getLastMessageStr() { if(lastMessage != null) return lastMessage; else return "";}
	
	private void load(Connection con) throws SQLException, RappException{
		if (this.rappId == null) { throw new RappException("RappId är inte satt"); }	// Vi har en ny rapport
		Statement s;
		ResultSet rs;
		s = con.createStatement();
		
		rs = s.executeQuery("select behorighet, kategori, undergrupp,kortbeskrivning, reportrubrik, sqlfrom, isdistinct, crtime from rapphuvud where rappid = " + rappId);
		if (rs.next()) {
			huvud.behorighet = rs.getString(1);
			huvud.kategori = rs.getString(2);
			huvud.undergrupp = rs.getString(3);
			huvud.kortbeskrivning = rs.getString(4);
			huvud.reportrubrik = rs.getString(5);
			huvud.sqlfrom = rs.getString(6);
			huvud.isdistinct = rs.getInt(7) != 0;
			huvud.crtime = rs.getTimestamp(8);
			huvud.toFormData();
			
			rs = s.executeQuery("select col, sqllabel, label, groupby, decimaler, hidden, groupbyheadertext, groupbyfootertext from rappcolumns where rappid = " + rappId + " order by col");
			while(rs.next()) {
				RappColumn c = new RappColumn(rappSession);
				c.sortOrder = rs.getInt(1);
				c.sqlLabel = rs.getString(2);
				c.label = rs.getString(3);
				c.groupby = rs.getInt(4) != 0;
				c.decimaler = rs.getInt(5);
				c.hidden = rs.getInt(6) != 0;
				c.groupbyheadertext = rs.getString(7);
				c.groupbyfootertext = rs.getString(8);
				c.toFormData();
				arrColumn.add(c);
			}
			
			rs = s.executeQuery("select wherepos, javatype, name, label, hidden, defaultvalue from rappprops where type = 'Filter' and rappid = " + rappId + " order by wherepos");
			while(rs.next()) {
				RappFilter f = new RappFilter(rappSession);
				f.wherepos = rs.getInt(1);
				f.javatype = rs.getString(2);
				f.name = rs.getString(3);
				f.label = rs.getString(4);
				f.hidden = rs.getInt(5) != 0;
				f.defaultvalue = rs.getString(6);
				f.toFormData();
				arrFilter.add(f);
			}

			rs = s.executeQuery("select sumcolumn, resetcolumn, sumtype, sumtext from rappprops where type = 'Sum' and rappid = " + rappId + " order by rad");
			while(rs.next()) {
				RappSum sum = new RappSum(rappSession);
				sum.sumcolumn = rs.getInt(1);
				sum.resetcolumn = rs.getInt(2);
				sum.sumtype = rs.getString(3);
				sum.sumtext = rs.getString(4);
				sum.toFormData();
				arrSum.add(sum);
			}
			this.isLoaded = true;				// Allt laddat och klart
		
		} else {
			//lastMessage = "Hittar inte rapportid " + rappId;
			throw new RappException("Felaktigt rappId: " + rappId);
		}
		s.close();
	}
	
	public void persist(Connection con) throws SQLException,RappException {
		Statement s = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		sortColumns();
		
		try {
			con.setAutoCommit(false);
			s = con.createStatement();
			// Vi raderar alla gamla och startar på nytt
			s.executeUpdate("delete from rapphuvud where rappid = " + rappId);
			s.executeUpdate("delete from rappcolumns where rappid = " + rappId);
			s.executeUpdate("delete from rappprops where rappid = " + rappId);
			if (rappId == null) {
				rs = s.executeQuery("select max(rappid)+1 from rapphuvud"); 
				if (rs.next()) {
					rappId = rs.getInt(1);
				} else {
					throw new RappException("Kan inte få nytt rappid - inget svar från SQL-Query");
				}
			}

			ps = con.prepareStatement("insert into rapphuvud (behorighet, kategori, undergrupp, kortbeskrivning, reportrubrik, sqlfrom, isdistinct, rappid) values (?,?,?,?,?,?,?,?)");
			ps.setString(1, huvud.behorighet);
			ps.setString(2, huvud.kategori);
			ps.setString(3, huvud.undergrupp);
			ps.setString(4, huvud.kortbeskrivning);
			ps.setString(5, huvud.reportrubrik);
			ps.setString(6, huvud.sqlfrom);
			ps.setInt(7, huvud.getIsdistinctToInt());
			ps.setInt(8, rappId);
			ps.executeUpdate();
			ps.close();

			this.sortColumns();
			ps = con.prepareStatement("insert into rappcolumns (rappid, col, sqllabel, label, groupby, decimaler, hidden, groupbyheadertext, groupbyfootertext) values (?,?,?,?,?,?,?,?,?)");
			int colcn = 0;
			for (RappColumn rc : this.arrColumn) {
				colcn++;
				ps.setInt(1, rappId);
				ps.setInt(2, colcn);
				ps.setString(3, rc.sqlLabel);
				ps.setString(4, rc.label);
				ps.setInt(5, rc.getGroupbyToInt());
				ps.setInt(6, rc.decimaler);
				ps.setInt(7, rc.getHiddenToInt());
				ps.setString(8, rc.groupbyheadertext);
				ps.setString(9, rc.groupbyfootertext);
				ps.executeUpdate();
			}
			ps.close();

			int propsCn = 0;
			ps = con.prepareStatement("insert into rappprops (rappid, rad, type, sumcolumn, sumtype, sumtext, wherepos, javatype, name, label, hidden, defaultvalue) values (?,?,?,?,?,?,?,?,?,?,?,?)");
			for (RappFilter rf : this.arrFilter) {
				propsCn++;
				ps.setInt(1, rappId);
				ps.setInt(2, propsCn);
				ps.setString(3, "Filter");
				ps.setInt(4,0);
				ps.setString(5, null);
				ps.setString(6, null);
				ps.setInt(7, rf.wherepos);
				ps.setString(8, rf.javatype);
				ps.setString(9, "rappfilter" + propsCn);
				ps.setString(10, rf.label);
				ps.setInt(11, rf.getHiddenToInt());
				ps.setString(12, rf.defaultvalue);
				ps.executeUpdate();
			}
			for (RappSum rsu : this.arrSum) {
				propsCn++;
				ps.setInt(1, rappId);
				ps.setInt(2, propsCn);
				ps.setString(3, "Sum");
				ps.setInt(4,rsu.sumcolumn);
				ps.setString(5, rsu.sumtype);
				ps.setString(6, rsu.sumtext);
				ps.setInt(7, 0);
				ps.setString(8, null);
				ps.setString(9, null);
				ps.setString(10, null);
				ps.setInt(11, 0);
				ps.setString(12, null);
				ps.executeUpdate();
			}
			ps.close();

			con.commit();
		} finally {
			con.setAutoCommit(true);
			s.close();
		}
	}
	
	
	// Tar formdata, validerar och returnerar
	public void updateHuvud(HttpServletRequest request) throws RappEdit.RappException{
		huvud.rappHuvudFormData.setFromForm(request);
		if (huvud.rappHuvudFormData.isFormOK()) {
			huvud.behorighet = huvud.rappHuvudFormData.behorighet;
			if (huvud.rappHuvudFormData.isdistinct != null)	huvud.isdistinct = "true".equals(huvud.rappHuvudFormData.isdistinct); else huvud.isdistinct = false;
			huvud.kategori = huvud.rappHuvudFormData.kategori;
			huvud.undergrupp = huvud.rappHuvudFormData.undergrupp;
			huvud.kortbeskrivning = huvud.rappHuvudFormData.kortbeskrivning;
			huvud.reportrubrik = huvud.rappHuvudFormData.reportrubrik;
			huvud.sqlfrom = huvud.rappHuvudFormData.sqlfrom;
			//lastMessage = "Sparat OK!";
		} else {
			//lastMessage = "Fwl i formuläret";
			throw new RappEdit.RappException("Fel i formuläret");
		}
	}
	
	public String editHuvud() {
		return huvud.rappHuvudFormData.getHtmlForm();
	}
	
	public int newColumn() {
		RappColumn r = new RappColumn(rappSession);
		this.arrColumn.add(r);
		return this.arrColumn.size();
	}

	public String editColumn(HttpServletRequest request) {
		int x;
		try {
			x = Integer.parseInt(request.getParameter("pos"));
		} catch (Exception e) { return "Något gick fel - ogiltig kolumn"; }
		return editColumn(x);
	}

	public String editColumn(int pos) {
		
		RappColumn r;
		try {
			r = this.arrColumn.get(pos);
		} catch (IndexOutOfBoundsException e) { return "Nu gick något fel - Angiven kolumn finns inte."; }
		return r.rappColumnFormData.getHtmlForm(pos);
	}
	
	public void updateColumn(HttpServletRequest request) throws RappEdit.RappException {
		RappColumn r;
		int pos;
		try {
			pos = Integer.parseInt(request.getParameter("pos"));
		} catch (Exception e) { throw new RappEdit.RappException("Felaktig kolumn"); }
		
		try {
			r = this.arrColumn.get(pos);
			
		} catch (IndexOutOfBoundsException e) { throw new RappEdit.RappException("Angiven kolumn finns inte.");  }
		
		RappColumnFormData columnFormData = new RappColumnFormData(this.rappSession);
		columnFormData.setFromForm(request);
		if (columnFormData.isFormOK()) {
			try {
				r.sortOrder = Integer.parseInt(columnFormData.sortOrder);
				r.label = columnFormData.label;
				r.sqlLabel = columnFormData.sqlLabel;
				r.decimaler = Integer.parseInt(columnFormData.decimaler);
				if (columnFormData.groupby != null)	r.groupby = "true".equals(columnFormData.groupby); else r.groupby = false;
				if (columnFormData.hidden != null)	r.hidden = "true".equals(columnFormData.hidden); else r.hidden = false;
				r.groupbyfootertext = columnFormData.groupbyfootertext;
				r.groupbyheadertext = columnFormData.groupbyheadertext;
			} catch (Exception e) { throw new RappEdit.RappException("Något gick fel: " + e.toString());}
		} else {
			throw new RappEdit.RappException("Fel i formuläret");
		}		
	}


	
	public int newFilter() {
		RappFilter r = new RappFilter(rappSession);
		this.arrFilter.add(r);
		return this.arrFilter.size(); 
	}

	public String editFilter(HttpServletRequest request) {
		int x;
		try {
			x = Integer.parseInt(request.getParameter("pos"));
		} catch (Exception e) { return "Något gick fel - ogiltig filterpos"; }
		return editFilter(x);
	}

	public String editFilter(int pos) {
		
		RappFilter r;
		try {
			r = this.arrFilter.get(pos);
		} catch (IndexOutOfBoundsException e) { return "Nu gick något fel - Angivet filterpos finns inte."; }
		return r.rappFilterFormData.getHtmlForm(pos);
	}
	
	public void updateFilter(HttpServletRequest request) throws RappEdit.RappException{
		RappFilter r;
		int pos;
		try {
			pos = Integer.parseInt(request.getParameter("pos"));
		} catch (Exception e) { throw new RappEdit.RappException("Felaktig filterpos"); }
		
		try {
			r = this.arrFilter.get(pos);
			
		} catch (IndexOutOfBoundsException e) { throw new RappEdit.RappException("Angiven filterpos finns inte.");}
		
		RappFilterFormData filterFormData = new RappFilterFormData(this.rappSession);
		filterFormData.setFromForm(request);
		if (filterFormData.isFormOK()) {
			try {
				r.defaultvalue = filterFormData.defaultvalue;
				if (filterFormData.hidden != null) r.hidden ="true".equals(filterFormData.hidden); else r.hidden = false;
				r.javatype = filterFormData.javatype;
				r.label = filterFormData.label;
				r.wherepos = Integer.parseInt(filterFormData.wherepos);
			} catch (Exception e) { throw new RappEdit.RappException("Något gick fel: " + e.toString()); }
		} else {
			throw new RappEdit.RappException("Fel i formuläret");
		}
		
	}

	

	
	
	public int newSum() {
		RappSum r = new RappSum(rappSession);
		this.arrSum.add(r);
		return this.arrSum.size(); 
	}

	public String editSum(HttpServletRequest request) {
		int x;
		try {
			x = Integer.parseInt(request.getParameter("pos"));
		} catch (Exception e) { return "Något gick fel - ogiltig sumpos"; }
		return editSum(x);
	}

	public String editSum(int pos) {
		
		RappSum r;
		try {
			r = this.arrSum.get(pos);
		} catch (IndexOutOfBoundsException e) { return "Nu gick något fel - Angivet sumpos finns inte."; }
		return r.rappSumFormData.getHtmlForm(pos);
	}
	
	public void updateSum(HttpServletRequest request) throws RappEdit.RappException {
		RappSum r;
		int pos;
		try {
			pos = Integer.parseInt(request.getParameter("pos"));
		} catch (Exception e) { throw new RappEdit.RappException("Felaktig sumpos"); }
		
		try {
			r = this.arrSum.get(pos);
			
		} catch (IndexOutOfBoundsException e) { throw new RappEdit.RappException("Angiven sumpos finns inte.");}
		
		RappSumFormData sumFormData = new RappSumFormData(this.rappSession);
		sumFormData.setFromForm(request);
		if (sumFormData.isFormOK()) {
			try {
				r.resetcolumn = Integer.parseInt(sumFormData.resetcolumn);
				r.sumcolumn = Integer.parseInt(sumFormData.sumcolumn);
				r.sumtext = sumFormData.sumtext;
				r.sumtype = sumFormData.sumtype;
			} catch (Exception e) { throw new RappEdit.RappException("Något gick fel: " + e.toString()); }
		} else {
			throw new RappEdit.RappException("Fel i formuläret");
		}
		
	}

	
	
	public void sortColumns() {
		java.util.Collections.sort(this.arrColumn, new ColumnComparator());

	}	

	 class ColumnComparator implements java.util.Comparator {
		public int compare (java.lang.Object o1, java.lang.Object o2) {
			int ret;
			Integer l1 = ((RappColumn)o1).sortOrder;
			Integer l2 = ((RappColumn)o2).sortOrder;
			if (l1 == null && l2 == null) {
				ret= 0;
			} else if (l1 == null && l2 != null) {
				ret= -1;
			} else if (l1 != null && l2 == null) {
				ret= 1;
			} else {
				ret= l1.compareTo(l2);
			}
			return ret;
		}
	}
	

	public class RappHuvudFormData {
		public String behorighet = null;
		public String kategori = null;
		public String undergrupp = null;
		public String kortbeskrivning = null;
		public String reportrubrik = null;
		public String sqlfrom = null;
		public String isdistinct = null;
		public String behorighetErr = null;
		public String kategoriErr = null;
		public String undergruppErr = null;
		public String kortbeskrivningErr = null;
		public String reportrubrikErr = null;
		public String sqlfromErr = null;
		public String isdistinctErr = null;
		
		private int rappSession;
		
		public RappHuvudFormData(int rappSession) {
			this.rappSession = rappSession;
		}

		
		public void resetErr() {
			behorighetErr = null;
			kategoriErr = null;
			undergruppErr = null;
			kortbeskrivningErr = null;
			reportrubrikErr = null;
			sqlfromErr = null;
			isdistinctErr = null;
		}
		
		public boolean isFormOK() {
			boolean ret = true;
			if (kategori == null)  {
				kategoriErr = "Värde måste anges";
				ret = false;
			} else if (kategori.isEmpty()) {
				kategoriErr = "Värde måste anges";
				ret = false;				
			}
			if (undergrupp == null) {
				undergruppErr = "Värde måste anges";
				ret = false;
			} else if (undergrupp.isEmpty()) {
				undergruppErr = "Värde måste anges";
				ret = false;				
			}
			if (kortbeskrivning == null) {
				kortbeskrivningErr = "Värde måste anges";
				ret = false;
			} else if (kortbeskrivning.isEmpty()) {
				kortbeskrivningErr = "Värde måste anges";
				ret = false;				
			}
			if (sqlfrom == null) {
				sqlfromErr = "Värde måste anges";
				ret = false;
			} else if (sqlfrom.isEmpty()) {
				sqlfromErr = "Värde måste anges";
				ret = false;				
			}
			if (isdistinct == null) {
				isdistinctErr = "Värde måste anges";
				ret = false;
			} else {
				if (!"true".equals(isdistinct) && !"false".equals(isdistinct)) {
					isdistinctErr = "Värde måste vara sant eller falskt";
					ret = false;					
				}
			}
			return ret;
		}
		
		public void setFromForm(HttpServletRequest request) {
			resetErr();
			behorighet = request.getParameter("behorighet");
			kategori = request.getParameter("kategori");
			undergrupp = request.getParameter("undergrupp");
			kortbeskrivning = request.getParameter("kortbeskrivning");
			reportrubrik = request.getParameter("reportrubrik");
			sqlfrom = request.getParameter("sqlfrom");
			isdistinct = request.getParameter("isdistinct");
		}

		
		public String getHtmlForm() {
			String checked = "";
			StringBuilder sb = new StringBuilder();
			sb.append("<form action=\"\">");
			sb.append("<input type=\"hidden\" name=\"id\" value=\"updatehuvud\">");
			sb.append("<input type=\"hidden\" name=\"rappsession\" value=\"" + rappSession + "\">");
			sb.append("<table>");
			sb.append("<tr><td>Kategori:</td><td><input type=\"text\" name=\"kategori\" value=\"" + kategori + "\"></td><td>" + kategoriErr + "</td></tr>");
			sb.append("<tr><td>Undergrupp:</td><td><input type=\"text\" name=\"undergrupp\" value=\"" + undergrupp + "\"></td><td>" + undergruppErr + "</td></tr>");
			sb.append("<tr><td>Kortbeskrivning:</td><td><input type=\"text\" name=\"kortbeskrivning\" value=\"" + kortbeskrivning + "\"></td><td>" + kortbeskrivningErr + "</td></tr>");
			sb.append("<tr><td>Rubrik:</td><td><input type=\"text\" name=\"reportrubrik\" value=\"" + reportrubrik + "\"></td><td>" + reportrubrikErr + "</td></tr>");
			sb.append("<tr><td>SQL from-sats:</td><td><input type=\"text\" name=\"sqlfrom\" value=\"" + sqlfrom + "\"></td><td>" + sqlfromErr + "</td></tr>");
			if ("true".equals(isdistinct))  checked = "checked"; else checked = "";
			sb.append("<tr><td colspan=\"2\">SQL distinct: <input type=\"checkbox\" name=\"sqlfrom\" value=\"true\" " + checked + "></td><td>" + isdistinctErr + "</td></tr>");
			sb.append("<tr><td><input type=\"submit\" name=\"submit\" value=\"OK\"></td></tr>");
			sb.append("</table></form>");
			return sb.toString();
		}	
	}
	
	

	
	
	public class RappColumnFormData {
		public String sortOrder = null;	
		public String sqlLabel = null;
		public String label = null;
		public String groupby = null;
		public String decimaler = null;
		public String hidden = null;
		public String groupbyheadertext = null;
		public String groupbyfootertext = null;
		
		public String sortOrderErr = null;	
		public String sqlLabelErr = null;
		public String labelErr = null;
		public String groupbyErr = null;
		public String decimalerErr = null;
		public String hiddenErr = null;
		public String groupbyheadertextErr = null;
		public String groupbyfootertextErr = null;

		
		private int rappSession;
		
		public RappColumnFormData(int rappSession) {
			this.rappSession = rappSession;
		}
		
		public void resetErr() {
			sortOrderErr = null;	
			sqlLabelErr = null;
			labelErr = null;
			groupbyErr = null;
			decimalerErr = null;
			hiddenErr = null;
			groupbyheadertextErr = null;
			groupbyfootertextErr = null;			
		}
		
		public boolean isFormOK() {
			boolean ret = true;
			if (sqlLabel == null)  {
				sqlLabelErr = "Värde måste anges";
				ret = false;
			}
			if (label == null) {
				labelErr = "Värde måste anges";
				ret = false;
			}
			
			if (groupby == null) {
				groupby = "false";
			} else {
				if (!"true".equals(groupby) && !"false".equals(groupby)) {
					groupbyErr = "Värde måste vara sant eller falskt";
					ret = false;
				}
			}
			
			if (decimaler == null) {
				decimaler = "0";
			} else {
				try {
					Integer.parseInt(decimaler);
				} catch (Exception e) { 
					decimalerErr = "Ett heltal måste anges"; 
					ret = false;
				}
			}
			
			if (hidden == null) {
				hidden = "false";
			} else {
				if (!"true".equals(hidden) && !"false".equals(hidden)) {
					hiddenErr = "Värde måste vara sant eller falskt";
					ret = false;
				}
			}
			return ret;
		}
		
		public void setFromForm(HttpServletRequest request) {
			resetErr();
			sortOrder = request.getParameter("sortorder");
			sqlLabel = request.getParameter("sqllabel");
			label = request.getParameter("label");
			groupby = request.getParameter("groupby");
			decimaler = request.getParameter("decimaler");
			hidden = request.getParameter("hidden");
			groupbyheadertext = request.getParameter("groupbyheadertext");
			groupbyfootertext = request.getParameter("groupbyfootertext");
		}

		
		public String getHtmlForm(int pos) {
			String checked = "";
			StringBuilder sb = new StringBuilder();
			sb.append("<form action=\"\">");
			sb.append("<input type=\"hidden\" name=\"id\" value=\"updatecolumn\">");
			sb.append("<input type=\"hidden\" name=\"rappsession\" value=\"" + rappSession + "\">");
			sb.append("<input type=\"hidden\" name=\"pos\" value=\"" + pos + "\">");
			sb.append("<table>");
			sb.append("<tr><td>Sorteringsordning:</td><td><input type=\"text\" name=\"sortorder\" value=\"" + sortOrder + "\"></td><td>" + sortOrderErr + "</td></tr>");
			sb.append("<tr><td>SQL Namn:</td><td><input type=\"text\" name=\"sqllabel\" value=\"" + sqlLabel + "\"></td><td>" + sqlLabelErr + "</td></tr>");
			sb.append("<tr><td>Kolumnrubrik:</td><td><input type=\"text\" name=\"label\" value=\"" + label + "\"></td><td>" + labelErr + "</td></tr>");
			sb.append("<tr><td>Antal decimaler:</td><td><input type=\"text\" name=\"decimaler\" value=\"" + decimaler + "\"></td><td>" + decimalerErr + "</td></tr>");
			if ("true".equals(groupby))  checked = "checked"; else checked = "";
			sb.append("<tr><td colspan=\"2\">SQL distinct: <input type=\"checkbox\" name=\"groupby\" value=\"true\" " + checked + "></td><td>" + groupbyErr + "</td></tr>");
			if ("true".equals(hidden))  checked = "checked"; else checked = "";
			sb.append("<tr><td colspan=\"2\">SQL distinct: <input type=\"checkbox\" name=\"hidden\" value=\"true\" " + hidden + "></td><td>" + hiddenErr + "</td></tr>");
			sb.append("<tr><td>Grupphuvudtext:</td><td><input type=\"text\" name=\"groupbyheadertext\" value=\"" + groupbyheadertext + "\"></td><td>" + groupbyheadertextErr + "</td></tr>");
			sb.append("<tr><td>Gruppfottext:</td><td><input type=\"text\" name=\"groupbyfootertext\" value=\"" + groupbyfootertext + "\"></td><td>" + groupbyfootertext + "</td></tr>");
			sb.append("<tr><td><input type=\"submit\" name=\"submit\" value=\"OK\"></td></tr>");
			sb.append("</table></form>");

			return sb.toString();
		}	
	}
	
	

	
	
	public class RappFilterFormData {
		public String wherepos = null;	
		public String javatype = null;
		public String label = null;
		public String hidden = null;
		public String defaultvalue = null;

		public String whereposErr = null;	
		public String javatypeErr = null;
		public String labelErr = null;
		public String hiddenErr = null;
		public String defaultvalueErr = null;
		
		private int rappSession;
		
		public RappFilterFormData(int rappSession) {
			this.rappSession = rappSession;
		}

		public void resetErr() {
			whereposErr = null;	
			javatypeErr = null;
			labelErr = null;
			hiddenErr = null;
			defaultvalueErr = null;
		}
		
		public boolean isFormOK() {
			boolean ret = true;
			if (wherepos == null) {
				whereposErr = "Värde måste anges";
				ret = false;
			} else {
				try {
					Integer.parseInt(wherepos);
				} catch (Exception e) { 
					whereposErr = "Ett heltal måste anges"; 
					ret = false;
				}
			}
			
			if (javatype == null)  {
				javatypeErr = "Värde måste anges";
				ret = false;
			}
			if (!("String".equals(javatype) ||	"Int".equals(javatype) || "BigDecimal".equals(javatype) || 	"Float".equals(javatype) || "Double".equals(javatype) || "Date".equals(javatype) ||
					"Time".equals(javatype) ||	"DateTime".equals(javatype))) {
				javatypeErr = "javatyp måste vara något av: String, Int, BigDecimal, Float, Double, Date, Time, DateTime";
				ret = false;
			}
			
			if (label == null) {
				labelErr = "Värde måste anges";
				ret = false;
			}
						
			if (hidden == null) {
				hidden = "false";
			} else {
				if (!"true".equals(hidden) && !"false".equals(hidden)) {
					hiddenErr = "Värde måste vara sant eller falskt";
					ret = false;
				}
			}
			return ret;
		}
		
		public void setFromForm(HttpServletRequest request) {
			resetErr();
			wherepos = request.getParameter("wherepos");
			javatype = request.getParameter("javatype");
			label = request.getParameter("label");
			hidden = request.getParameter("hidden");
			defaultvalue = request.getParameter("defaultvalue");
		}

		
		public String getHtmlForm(int pos) {
			String checked = "";
			StringBuilder sb = new StringBuilder();
			sb.append("<form action=\"\">");
			sb.append("<input type=\"hidden\" name=\"id\" value=\"updatefilter\">");
			sb.append("<input type=\"hidden\" name=\"rappsession\" value=\"" + rappSession + "\">");
			sb.append("<input type=\"hidden\" name=\"pos\" value=\"" + pos + "\">");
			sb.append("<table>");
			sb.append("<tr><td>Position i SQL-filtret:</td><td><input type=\"text\" name=\"wherepos\" value=\"" + wherepos + "\"></td><td>" + whereposErr + "</td></tr>");
			sb.append("<tr><td>Beskrivning:</td><td><input type=\"text\" name=\"label\" value=\"" + label + "\"></td><td>" + labelErr + "</td></tr>");
			sb.append("<tr><td>JavaType:</td><td>");
				sb.append("<select name=\"javatype\">");
				sb.append("<option value=\"String\">String (Text)</option>");
				sb.append("<option value=\"Int\">Integer (Heltal)</option>");
				sb.append("<option value=\"BigDecimal\">BigDecimal (Decimaltal)</option>");
				sb.append("<option value=\"Double\">Double (Normala belopp)</option>");
				sb.append("<option value=\"Float\">Float (Ovanligt belopp)</option>");
				sb.append("<option value=\"Date\">Datum</option>");
				sb.append("<option value=\"Time\">Tid</option>");
				sb.append("<option value=\"DateTime\">DateTime (Kombinerat datum+tid)</option>");
				sb.append("</select>");
				sb.append("</td><td>" + javatypeErr + "</td></tr>");
			sb.append("<tr><td>Standardvärde:</td><td><input type=\"text\" name=\"defaultvalue\" value=\"" + defaultvalue + "\"></td><td>" + defaultvalueErr + "</td></tr>");
			if ("true".equals(hidden))  checked = "checked"; else checked = "";
			sb.append("<tr><td colspan=\"2\">Hidden: <input type=\"checkbox\" name=\"hidden\" value=\"true\" " + hidden + "></td><td>" + hiddenErr + "</td></tr>");
			sb.append("<tr><td><input type=\"submit\" name=\"submit\" value=\"OK\"></td></tr>");
			sb.append("</table></form>");

			return sb.toString();
		}	
	}
	

	
	

	
	
	public class RappSumFormData {
		public String sumcolumn = null;
		public String resetcolumn = null;
		public String sumtype = null;
		public String sumtext = null;

		public String sumcolumnErr = null;
		public String resetcolumnErr = null;
		public String sumtypeErr = null;
		public String sumtextErr = null;
		
		private int rappSession;
		
		public RappSumFormData(int rappSession) {
			this.rappSession = rappSession;
		}

		public void resetErr() {
			sumcolumnErr = null;
			resetcolumnErr = null;
			sumtypeErr = null;
			sumtextErr = null;			
		}
		
		public boolean isFormOK() {
			boolean ret = true;
			if (sumcolumn == null) {
				sumcolumnErr = "Värde måste anges";
				ret = false;
			} else {
				try {
					Integer.parseInt(sumcolumn);
				} catch (Exception e) { 
					sumcolumnErr = "Ett heltal måste anges"; 
					ret = false;
				}
			}
			if (resetcolumn == null) {
				resetcolumnErr = "Värde måste anges";
				ret = false;
			} else {
				try {
					Integer.parseInt(resetcolumn);
				} catch (Exception e) { 
					resetcolumnErr = "Ett heltal måste anges"; 
					ret = false;
				}
			}
			
			if (sumtype == null)  {
				sumtypeErr = "Värde måste anges";
				ret = false;
			}
			if (!("Sum".equals(sumtype) ||	"Medel".equals(sumtype) || "Min".equals(sumtype) || 	"Max".equals(sumtype) || "Antal".equals(sumtype))) {
				sumtypeErr = "Måste vara något av: Sum, Medel, Min, Max, Antal";
				ret = false;
			}
			return ret;
		}
		
		public void setFromForm(HttpServletRequest request) {
			resetErr();
			sumcolumn = request.getParameter("sumcolumn");
			sumtype = request.getParameter("sumtype");
			resetcolumn = request.getParameter("resetcolumn");
			sumtext = request.getParameter("sumtext");
		}

		
		public String getHtmlForm(int pos) {
			String checked = "";
			StringBuilder sb = new StringBuilder();
			sb.append("<form action=\"\">");
			sb.append("<input type=\"hidden\" name=\"id\" value=\"updatesum\">");
			sb.append("<input type=\"hidden\" name=\"rappsession\" value=\"" + rappSession + "\">");
			sb.append("<input type=\"hidden\" name=\"pos\" value=\"" + pos + "\">");
			sb.append("<table>");
			sb.append("<tr><td>Kolumnnr som ska summeras:</td><td><input type=\"text\" name=\"sumcolumn\" value=\"" + sumcolumn + "\"></td><td>" + sumcolumnErr + "</td></tr>");
			sb.append("<tr><td>Kolumn för nollställning (vanligen en grupperad kolumn):</td><td><input type=\"text\" name=\"resetcolumn\" value=\"" + resetcolumn + "\"></td><td>" + resetcolumnErr + "</td></tr>");
			sb.append("<tr><td>Summatyp:</td><td>");
				sb.append("<select name=\"sumtype\">");
				sb.append("<option value=\"Sum\">Summa</option>");
				sb.append("<option value=\"Min\">Minvärde</option>");
				sb.append("<option value=\"Max\">Maxvärde</option>");
				sb.append("<option value=\"Medel\">Medelvärde</option>");
				sb.append("<option value=\"Antal\">Antal</option>");
				sb.append("</select>");
				sb.append("</td><td>" + sumtypeErr + "</td></tr>");
			sb.append("<tr><td>Text att visa vid summan:</td><td><input type=\"text\" name=\"sumtext\" value=\"" + sumtext + "\"></td><td>" + sumtextErr + "</td></tr>");
			sb.append("<tr><td><input type=\"submit\" name=\"submit\" value=\"OK\"></td></tr>");
			sb.append("</table></form>");

			return sb.toString();
		}	
	}
	

	
	
	
	
	
	
	
	public class RappHuvud {
		private int rappSession;
		public RappHuvud(int rappSession) {
			this.rappSession = rappSession;
		}
		public RappHuvudFormData rappHuvudFormData  = new RappHuvudFormData(rappSession); 
		public String behorighet = null;
		public String kategori = null;
		public String undergrupp = null;
		public String kortbeskrivning = null;
		public String reportrubrik = null;
		public String sqlfrom = null;
		public boolean isdistinct = false;
		public java.sql.Timestamp crtime = null;
		public int getIsdistinctToInt() { if (isdistinct) return 1; else return 0; }
		public void toFormData() {
			rappHuvudFormData.resetErr();
			rappHuvudFormData.behorighet = behorighet;
			if (isdistinct) rappHuvudFormData.isdistinct = "true"; else rappHuvudFormData.isdistinct = "false";
			rappHuvudFormData.kategori = kategori;
			rappHuvudFormData.kortbeskrivning = kortbeskrivning;
			rappHuvudFormData.reportrubrik = reportrubrik;
			rappHuvudFormData.sqlfrom = sqlfrom;
			rappHuvudFormData.undergrupp = undergrupp;
		}
	}
	

	
	
	
	public class RappColumn{
		private int rappSession;
		public RappColumn(int rappSession) {
			this.rappSession = rappSession;
		}
		public RappColumnFormData rappColumnFormData = new RappColumnFormData(rappSession);
		public Integer sortOrder = null;	//Anger vilken ordning colimnerna önskas. Sparas inte i databasen, utan endast för internt ändamål
		public String sqlLabel = null;
		public String label = null;
		public boolean groupby = false;
		public Integer decimaler = null;
		public boolean hidden = false;
		public String groupbyheadertext = null;
		public String groupbyfootertext = null;
		public int getGroupbyToInt() { if (groupby) return 1; else return 0; }
		public int getHiddenToInt() { if (hidden) return 1; else return 0; }
		public void toFormData() {
			rappColumnFormData.resetErr();
			if (sortOrder != null) rappColumnFormData.sortOrder = sortOrder.toString(); else rappColumnFormData.sortOrder = null;
			rappColumnFormData.sqlLabel = sqlLabel;
			rappColumnFormData.label = label;
			if (groupby) rappColumnFormData.groupby = "true"; else rappColumnFormData.groupby = "false";
			if (decimaler != null) rappColumnFormData.decimaler = decimaler.toString(); else rappColumnFormData.decimaler = null;
			if (hidden) rappColumnFormData.hidden = "true"; else rappColumnFormData.hidden = "false";
			rappColumnFormData.groupbyheadertext = groupbyheadertext;
			rappColumnFormData.groupbyfootertext = groupbyfootertext;
		}
	}
	
	public class RappFilter {
		private int rappSession;
		public RappFilter(int rappSession) {
			this.rappSession = rappSession;
		}
		public RappFilterFormData rappFilterFormData = new RappFilterFormData(rappSession);
		public Integer wherepos = null;
		public String javatype = null;
		public String name = null;
		public String label = null;
		public boolean hidden = false;
		public String defaultvalue = null;
		public int getHiddenToInt() { if (hidden) return 1; else return 0; }

		public void toFormData() {
			rappFilterFormData.resetErr();
			if (wherepos != null) rappFilterFormData.wherepos = wherepos.toString(); else rappFilterFormData.wherepos = null;
			rappFilterFormData.javatype = javatype;
			rappFilterFormData.label = label;
			if (hidden) rappFilterFormData.hidden="true"; else rappFilterFormData.hidden = "false";
			rappFilterFormData.defaultvalue = defaultvalue;
			
		}
	}
	
	
	public class RappSum {
		private int rappSession;
		public RappSum(int rappSession) {
			this.rappSession = rappSession;
		}
		public RappSumFormData rappSumFormData = new RappSumFormData(rappSession);
		public Integer sumcolumn = null;
		public Integer resetcolumn = null;
		public String sumtype = null;
		public String sumtext = null;
		public void toFormData() {
			rappSumFormData.resetErr();
			if (sumcolumn != null) rappSumFormData.sumcolumn = sumcolumn.toString(); else rappSumFormData.sumcolumn = null;
			if (resetcolumn != null) rappSumFormData.resetcolumn = resetcolumn.toString(); else rappSumFormData.resetcolumn = null;
			rappSumFormData.sumtype = sumtype;
			rappSumFormData.sumtext = sumtext;
			
		}
	}
	
	public class RappException extends java.lang.Exception {
		public RappException (String s) {
			super(s);
		}
	}
}
	

