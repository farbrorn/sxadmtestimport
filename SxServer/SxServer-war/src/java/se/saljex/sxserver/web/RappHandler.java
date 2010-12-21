/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */
public class RappHandler {
		public static final int TYP_STRING = 1;
		public static final int TYP_INT = 2;
		public static final int TYP_BIGDECIMAL = 3;
		public static final int TYP_FLOAT = 4;
		public static final int TYP_DOUBLE = 5;
		public static final int TYP_DATE = 6;
		public static final int TYP_TIME = 7;
		public static final int TYP_DATETIME = 8;
		
		protected int noColumns = 0;
		protected RappColumn[] colArr;
		protected ResultSet rs;
		protected ArrayList<SqlFilterField> sqlFilters = new ArrayList();
		protected ArrayList<Sum> sums = new ArrayList();
		protected Connection con;
		protected String reportRubrik = null;

		private boolean firstRow = true;
		
	public RappHandler(Connection con) throws SQLException{
		this.con = con;
	}
	
	public void prepareFromSQLRepository(int rappId) throws SQLException {
		Statement s1 = con.createStatement();
		StringBuilder sb = new StringBuilder();
		try {
			ResultSet rs1;

			rs1 =  s1.executeQuery("select reportrubrik, sqlfrom, isdistinct from rapphuvud where rappid = " + rappId);
			if (!rs1.next()) throw new SQLException("Rappid " + rappId + " finns inte.");
			this.setReportRubrik(rs1.getString(1));
			String sqlFrom = rs1.getString(2);
			sb.append("select");
			if (rs1.getInt(3) != 0) sb.append(" distinct");


			ArrayList<RappColumn> tempCol= new ArrayList(); 
			rs1 = s1.executeQuery("select col, sqllabel, label, groupby, decimaler, hidden, groupbyheadertext, groupbyfootertext from rappcolumns where rappid = " + rappId + " order by col");
			boolean first = true;
			int cn = 0;
			while(rs1.next()) {
				cn++;
				// Skapa temporär columnstruktur eftersom den riktiga skapåas i prepareQuery - vi får sedan läsa in värdena dit
				tempCol.add(new RappColumn(cn, rs1.getString(3), rs1.getInt(4), rs1.getInt(5), rs1.getInt(6), rs1.getString(7), rs1.getString(8)));
				if (!first) {
					sb.append(" ,");
				} else {
					first = false;
				}
				sb.append(" ");
				sb.append(rs1.getString(2));
			}
			sb.append(" from ");
			sb.append(sqlFrom);
			// Ta fram filtren
			rs1 = s1.executeQuery("select type, sumcolumn, resetcolumn, sumtype, sumtext, wherepos, javatype, name, label, hidden, defaultvalue from rappprops where rappid = " + rappId + " order by wherepos, rad");
			while(rs1.next()) {
				if ("Filter".equals(rs1.getString(1))) {
					this.addSqlFilterField(rs1.getInt(6), rs1.getString(7), rs1.getString(8), rs1.getString(9), rs1.getInt(10) != 0, stringToJavaTypeObject(rs1.getString(7),rs1.getString(11)), stringToJavaTypeObject(rs1.getString(7),getFilterValue(rs1.getString(8))));
				}
			}
			prepareQuery(sb.toString());
			// Nu är den riktiga arrayen skapad, och vi läser in de temporära datan i den
			for (RappColumn r : tempCol) {
				this.colArr[r.getPos()].setDecimaler(r.getDecimaler());
				this.colArr[r.getPos()].setGroupBy(r.isGroupBy());
				this.colArr[r.getPos()].setGroupByFooterText(r.getGroupByFooterTextString());
				this.colArr[r.getPos()].setGroupByHeaderText(r.getGroupByHeaderTextString());
				this.colArr[r.getPos()].setHidden(r.isHidden());
				this.colArr[r.getPos()].setLabel(r.getLabel());
			}
			// Ta fram summorna
			rs1 = s1.executeQuery("select type, sumcolumn, resetcolumn, sumtype, sumtext, wherepos, javatype, name, label, hidden, defaultvalue from rappprops where rappid = " + rappId + " order by wherepos, rad");
			while(rs1.next()) {
				if ("Sum".equals(rs1.getString(1))) {
					this.addSum(rs1.getInt(2), rs1.getInt(3), rs1.getString(4), rs1.getString(5));
				}
			}
		} finally {
			try { s1.close(); } catch (Exception e) {}
		}
		ServerUtil.logDebug("SQL-Sats för rapport: " + sb.toString());
	}
	




	public void prepareQuery(String sql) throws SQLException {
		PreparedStatement st = con.prepareStatement(sql);
		if (sqlFilters != null) {
			for (SqlFilterField s : sqlFilters) {
				st.setObject(s.pos, s.getValue());
			}
		}
		rs = st.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		this.noColumns = md.getColumnCount();
		this.colArr = new RappColumn[this.noColumns + 1];		// Öka med 1 för att inte behöva använda position 0
		colArr[0] = new RappColumn("Rapport",0);	// Sätt upp en column med rubrik för rapporttotaler
		for (int cn=1; cn<=noColumns; cn++) {
			colArr[cn] = new RappColumn(md, cn);
		}
		firstRow = true;
	}
	
	protected void addSqlFilterField(int pos, String javaTypeStr, String name, String label, boolean hidden, Object defaultValue, Object value) {
		int javaType = 0;
		if ("String".equals(javaTypeStr)) javaType = TYP_STRING;
		else if ("Int".equals(javaTypeStr)) javaType = TYP_INT;
		else if ("BigDecimal".equals(javaTypeStr)) javaType = TYP_BIGDECIMAL;
		else if ("Float".equals(javaTypeStr)) javaType = TYP_FLOAT;
		else if ("Double".equals(javaTypeStr)) javaType = TYP_DOUBLE;
		else if ("Date".equals(javaTypeStr)) javaType = TYP_DATE;
		else if ("Time".equals(javaTypeStr)) javaType = TYP_TIME;
		else if ("DateTime".equals(javaTypeStr)) javaType = TYP_DATETIME;
		sqlFilters.add(new SqlFilterField(pos, javaType, name, label, hidden, defaultValue, value));
	}

	protected Object stringToJavaTypeObject(String javaTypeStr, String s) {
		if ("String".equals(javaTypeStr)) {
			String r = s;
			return r;
		} else if ("Int".equals(javaTypeStr)) {
			Integer r = null;
			try {
				r = Integer.parseInt(s);
			} catch (Exception e) { }
			return r;
		}
		else if ("BigDecimal".equals(javaTypeStr)) {
			BigDecimal r = null;
			try {
				r = new BigDecimal(s);
			} catch (Exception e) { }
			return r;
		}
		else if ("Float".equals(javaTypeStr)) {
			Float r = null;
			try {
				r = new Float(s);
			} catch (Exception e) { }
			return r;
		}
		else if ("Double".equals(javaTypeStr)) {
			Double r = null;
			try {
				r = new Double(s);
			} catch (Exception e) { }
			return r;
		}
		else if ("Date".equals(javaTypeStr)) {
			java.sql.Date r = null;
			try {
				r = new java.sql.Date(DateFormat.getDateInstance().parse(s).getTime());
			} catch (Exception e) { }
			return r;
		}
		else if ("Time".equals(javaTypeStr)) {
			java.sql.Time r = null;
			try {
				r = new java.sql.Time(DateFormat.getDateInstance().parse(s).getTime());
			} catch (Exception e) { }
			return r;			
		}
		else if ("DateTime".equals(javaTypeStr)) {
			java.sql.Timestamp r = null;
			try {
				r = new java.sql.Timestamp(DateFormat.getDateInstance().parse(s).getTime());
			} catch (Exception e) { }
			return r;			
		} else return null;
		
	}
	
	protected void addSum(int sumColumn, int resetColumn, String sumType, String text) {
		sums.add(new Sum(sumColumn, resetColumn, sumType, text));
	}
	
	public boolean next() throws SQLException{
		if (rs.next()) {
			for (int cn=1; cn<=noColumns; cn++) {
				colArr[cn].setNewValue(rs.getObject(cn));
			}
			//Skriv först uta alla group footer och summor, men bara om det inte är första raden
			if (!firstRow) {
				// Om en summa har GroupBreak, så ska alla efterföljande också ha det, förutsatt att det inte är en totalsumma
				int forstaGroupBreak = 0;
				int groupBreakCn = 0;
				for (Sum s : sums) {
					groupBreakCn++;
					if (colArr[s.resetColumn].hasGroupBreak()) {
						forstaGroupBreak = groupBreakCn;
						break;
					}
				}

				// Skriv GroupFooter för alla columner med groupBreak
				for (int cn=noColumns; cn>0; cn--) {			// Tagroup by i omvänd ordning
					if (colArr[cn].isGroupBy() && colArr[cn].hasGroupBreak()) printGroupFooter(cn);
				}

				//Skriv alla summor i omvänd ordning
				//Sum s;
				for (int cn = sums.size()-1; cn>=0; cn--) {
					Sum s = (Sum)sums.get(cn);
					//groupBreakCn++;
					// Gör inte group break om det saknas resetColumn, eller om vi inte hade någon forstaGroupBreak
					if (forstaGroupBreak > 0 && cn >= forstaGroupBreak-1 && s.resetColumn > 0) {
						printSum(s);
						s.reset();
					}
				}
			}
			// Skriv sedan alla group headers
			for (int cn=1; cn<=noColumns; cn++) {
				if (colArr[cn].hasGroupBreak() && colArr[cn].isGroupBy()) printGroupHeader(cn);
			}
			//Summera alla summor
			for (Sum s : sums) {
				s.add();
			}
			firstRow = false;
		} else {		//Slut på rapporten
			if (!firstRow) {
				//  Ta nu fram alla avslutande footers
				// Börja att sätta tomma värden på alla columner
				for (int cn=1; cn<=noColumns; cn++) {
					colArr[cn].setNewValue(null);
				}
				// Ta nu alla footers och summor
				for (int cn=noColumns; cn>0; cn--) {			// Tagroup by i omvänd ordning
					if (colArr[cn].isGroupBy()) printGroupFooter(cn);
					//Kolla om det finns rapporttotaler, och skriv då ut 
					for (Sum s : sums) {
						if (s.resetColumn == cn) {
							printSum(s);
							s.reset();
						}
					}
				}
				// Ta nu alla rapporttotaler
				// Kolla först om det finns rapporttotaler, och skriv då ut rapportfooter
				for (Sum s : sums) {
					if (s.resetColumn == 0) {
						printGroupFooter(0); //Skriv footer för hela rapporten							
						break;
					}
				}
				// Skriv nu ut alla rapportsummor
				for (Sum s : sums) {
					if (s.resetColumn == 0) {
						printSum(s);
						s.reset();
					}
				}				
			}
			con.close();
			return false;
		}
		return true;
	}
	
	protected void printGroupHeader(int col) {}	// Override this to print report structure
	protected void printGroupFooter(int col) {}	// Override this to print report structure
	protected void printSum(Sum s) {}	// Override this to print report structure
	protected String getFilterValue(String name) { return null;}	// Override this to get filtervaluefor given name

	public String getReportRubrik() { return reportRubrik; }
	public String getReportRubrikString() {
		if (reportRubrik == null) return "";
		else return reportRubrik;
	}

	public void setReportRubrik(String reportRubrik) {	this.reportRubrik = reportRubrik; }
	
	
	public ArrayList<RappHuvudList> getRappHuvudList() throws SQLException{
		ArrayList<RappHuvudList> rl = new ArrayList();
		Statement s1;
		ResultSet rs1;
		s1 = con.createStatement();

		rs1 = s1.executeQuery("select rappid, behorighet, kategori, undergrupp, kortbeskrivning, crtime from rapphuvud order by kategori, undergrupp, rappid");
		while (rs1.next()) {
			rl.add(new RappHuvudList(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5) , rs1.getTimestamp(6)));
		}
		return rl;
	}
	
	
	
	
	
	
	public class RappHuvudList {
		public int rappid;
		public String behorighet;
		public String kategori;
		public String undergrupp;
		public String kortbeskrivning;
		public java.sql.Timestamp crtime;
		public RappHuvudList (int rappid, String behorighet, String kategori, String undergrupp, String kortbeskrivning, java.sql.Timestamp crtime) {
			this.rappid = rappid;
			this.behorighet = behorighet;
			this.kategori = kategori;
			this.undergrupp = undergrupp;
			this.kortbeskrivning = kortbeskrivning;
			this.crtime = crtime;
		}
	}
	
	
	public class SqlFilterField {
		public SqlFilterField(int pos, int javaType, String name, String label,  boolean hidden, Object defaultValue, Object value) {
			if (javaType != TYP_STRING && javaType != TYP_INT 	&& javaType != TYP_BIGDECIMAL && javaType != TYP_FLOAT 
				&& javaType != TYP_DOUBLE 	&& javaType != TYP_DATE && javaType != TYP_TIME && javaType != TYP_DATETIME) {
				throw new NullPointerException("Ogiltig java Type: " + javaType); 
			}
			if (pos < 1) { throw new NullPointerException("Ogiltig sql-position" + pos); }
			
			this.pos = pos;
			this.javaType = javaType;
			this.name = name;
			this.hidden = hidden;
			this.defaultValue = defaultValue;
			this.value = value;
			this.label = label;
		}
		public int pos;	// position i Where
		public int javaType;
		public String name;			// Namn på filter, används vid http get
		public String label;		// Input label
		public boolean hidden = false;	// Visa inte i input formulär
		public Object defaultValue = null;
		public Object value = null;
		
		public Object getValue() {
			if (value == null) return defaultValue;
			else return value;
		}
		public String getValueString() {
			if (value == null) {
				if (defaultValue == null) return "";
				else return defaultValue.toString();
			} else {
				if (value == null) return "";
				else return value.toString();
			}
		}
	}
	
	public class RappColumn {
		private int pos = 0;
		private String label = null;
		private boolean groupBy = false;
		private Integer decimaler = null; //Antal decimaler att skriva ut vid numeriska data
		private int javaType = 0;
		private Object currValue = null;
		private Object prevValue = null;
		private boolean hidden = false;			//Dölj vid utskrift
		private String groupByHeaderText = null;
		private String groupByFooterText = null;

		public RappColumn(ResultSetMetaData md, int pos) throws SQLException {
				int ctype = md.getColumnType(pos);
				if (ctype == java.sql.Types.VARCHAR || ctype == java.sql.Types.CHAR) { 
					javaType = TYP_STRING;
				} else if (ctype == java.sql.Types.INTEGER || ctype == java.sql.Types.TINYINT || ctype == java.sql.Types.SMALLINT ) {
					javaType = TYP_INT;
				} else if (ctype == java.sql.Types.DECIMAL || ctype == java.sql.Types.NUMERIC ) {
					javaType = TYP_BIGDECIMAL;
				} else if (ctype == java.sql.Types.REAL) {
					javaType = TYP_FLOAT;
				} else if (ctype == java.sql.Types.DOUBLE  || ctype == java.sql.Types.FLOAT) {
					javaType = TYP_DOUBLE;
				} else if (ctype == java.sql.Types.DATE) {
					javaType = TYP_DATE;
				} else if (ctype == java.sql.Types.TIME) {
					javaType = TYP_TIME;
				} else if (ctype == java.sql.Types.TIMESTAMP) {
					javaType = TYP_DATETIME;
				}
				label = md.getColumnLabel(pos);
				this.pos = pos;
		}
		
		public RappColumn(String label, int pos) {
			this.label = label;
			this.pos = pos;
			this.javaType = TYP_STRING;
		}
		public RappColumn(int pos, String label, int groupBy, Integer decimaler, int hidden, String groupByHeaderText, String groupByFooterText) {
			this.pos = pos;
			this.label = label;
			this.groupBy = groupBy != 0;
			this.decimaler = decimaler;
			this.hidden = hidden != 0;
			this.groupByHeaderText = groupByHeaderText;
			this.groupByFooterText = groupByFooterText;
			
		}

		public String getGroupByFooterTextString() { 
			if (groupByFooterText == null) return "Totaler för " + getLabelString() + ": " + getPrevValueString();
			return groupByFooterText; 
		}
		public void setGroupByFooterText(String groupByFooterText) { this.groupByFooterText = groupByFooterText; }

		public String getGroupByHeaderTextString() { 
			if (groupByHeaderText == null) return "Gruppering på " + getLabelString() + ": " + getCurrValueString();
			return groupByHeaderText; 
		}
		public void setGroupByHeaderText(String groupByHeaderText) { this.groupByHeaderText = groupByHeaderText; }
		
		public Integer getDecimaler() {	return decimaler;	 }
		public void setDecimaler(Integer decimaler) {	this.decimaler = decimaler;	 }

		public boolean isGroupBy() {	return groupBy;	 }
		public void setGroupBy(boolean groupBy) {	this.groupBy = groupBy;	 }

		public boolean isHidden() {	return hidden;	 }
		public void setHidden(boolean hidden) {	this.hidden = hidden;	 }

		public int getJavaType() {	return javaType;	 }
		public void setJavaType(int javaType) {	this.javaType = javaType;	 }

		public int getPos() {	return pos;	 }
		public void setPos(int pos) { this.pos = pos;	 }

		public String getLabel() {	return label;	 }
		public String getLabelString() {	
			if (label == null) return "";
			else return label;	 
		}
		public void setLabel(String label) { this.label = label;	 }

		public Object getCurrValue() { return currValue; }
		public void setCurrValue(Object currValue) { this.currValue = currValue; }

		public Object getPrevValue() {	return prevValue;	 }
		public void setPrevValue(Object prevValue) {	this.prevValue = prevValue;	 }		
		
		//Skicka endaast in object från currValue eller prevValue för att undvika casting exception
		private String getValueString(Object o) {
			String ret;
			if (o == null) {
				ret = "";
			} else if (javaType == TYP_DOUBLE) {
				ret = SXUtil.getFormatNumber((Double)o, decimaler);
			} else if (javaType == TYP_FLOAT) {
				ret = SXUtil.getFormatNumber(new Double((Float)o), decimaler);
			} else if (javaType == TYP_BIGDECIMAL) {
				ret = SXUtil.getFormatNumber(((BigDecimal)o).doubleValue(), decimaler);
			} else if (javaType == TYP_DATE) {
				ret = SXUtil.getFormatDate((java.sql.Date)o);
			} else {
				ret = o.toString();
			}
			return ret;
			
		}
		public String getCurrValueString() {
			return getValueString(currValue);
		}
		public String getPrevValueString() {
			return getValueString(prevValue);
		}
		
		public void setNewValue(Object o) {
			setPrevValue(getCurrValue());
			setCurrValue(o);
		}
		
		public boolean hasGroupBreak() {
			boolean ret = false;
			if (currValue == null && prevValue == null) ret=false;
			else if ((currValue == null && prevValue != null) || (currValue != null && prevValue == null)) ret = true; // Group break
			else if (javaType  == TYP_STRING) { if (!((String)currValue).equals((String)prevValue)) ret = true; }
			else if (javaType  == TYP_INT) {if (!((Integer)currValue).equals((Integer)prevValue)) ret=true;}
			else if (javaType  == TYP_BIGDECIMAL) {if (!((BigDecimal)currValue).equals((BigDecimal)prevValue)) ret=true;}
			else if (javaType  == TYP_FLOAT) {if (!((Float)currValue).equals((Float)prevValue)) ret=true;}
			else if (javaType  == TYP_DOUBLE) {	if (!((Double)currValue).equals((Double)prevValue)) ret=true; }
			else if (javaType  == TYP_DATE) {if (!((java.sql.Date)currValue).equals((java.sql.Date)prevValue)) ret=true;}
			else if (javaType  == TYP_TIME) {if (!((java.sql.Time)currValue).equals((java.sql.Time)prevValue)) ret=true;}
			else if (javaType  == TYP_DATETIME) {if (!((java.sql.Timestamp)currValue).equals((java.sql.Timestamp)prevValue)) ret=true;}
			return ret;
		}
	}
	
	public class Sum {
		public Sum(int sumColumn, int resetColumn, String sumType, String text) {
			this.sumColumn = sumColumn;
			this.resetColumn = resetColumn;
			if (sumType == null) sumType = "Sum";
			this.sumType = sumType;
			if (text == null) {
				if (sumType.equals("Sum")) text = "Summa";
				else if (sumType.equals("Min")) text = "Min";
				else if (sumType.equals("Max")) text = "Max";
				else if (sumType.equals("Medel")) text = "Medelvärde";
				else if (sumType.equals("Antal")) text = "Antal";
				text = text + " " + colArr[sumColumn].getLabel() + ":";
			}
			this.text = text;
			reset();
		}
		
		private int sumColumn;
		private String sumType;
		private int resetColumn; // 0 = aldrig reset
		private Object sum;
		private Integer antal = 0;
		private String text;	// Text att skriva för summan
		
		public void add() {
			if (colArr[sumColumn].getCurrValue() != null) {
				if (sumType.equals("Sum") || sumType.equals("Medel")) {
					if (colArr[sumColumn].javaType == TYP_INT) {
						if (sum == null) sum = new Integer(0);
						sum = (Integer)sum + (Integer)colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_BIGDECIMAL) {
						if (sum == null) sum = new BigDecimal(0);
						sum = ((BigDecimal)sum).add((BigDecimal)colArr[sumColumn].getCurrValue());
					}
					else if (colArr[sumColumn].javaType == TYP_FLOAT) {
						if (sum == null) sum = new Float(0);
						sum = (Float)sum + (Float)colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_DOUBLE) {
						if (sum == null) sum = new Double(0);
						sum = (Double)sum + (Double)colArr[sumColumn].getCurrValue();
					}
				} else if (sumType.equals("Min")) {
					if (colArr[sumColumn].javaType == TYP_INT) {
						if (sum == null) sum = (Integer)colArr[sumColumn].getCurrValue();
						else if ((Integer)colArr[sumColumn].getCurrValue() < (Integer)sum) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_BIGDECIMAL) {
						if (sum == null) sum = (BigDecimal)colArr[sumColumn].getCurrValue();
						else if (((BigDecimal)colArr[sumColumn].getCurrValue()).compareTo((BigDecimal)sum) == -1) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_FLOAT) {
						if (sum == null) sum = (Float)colArr[sumColumn].getCurrValue();
						else if ((Float)colArr[sumColumn].getCurrValue() < (Float)sum) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_DOUBLE) {
						if (sum == null) sum = (Double)colArr[sumColumn].getCurrValue();
						else if ((Double)colArr[sumColumn].getCurrValue() < (Double)sum) sum = colArr[sumColumn].getCurrValue();						
					}
					else if (colArr[sumColumn].javaType == TYP_DATE ) {
						if (sum == null) sum = (java.sql.Date)colArr[sumColumn].getCurrValue();
						else if (((java.sql.Date)colArr[sumColumn].getCurrValue()).compareTo((java.sql.Date)sum) == -1) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_TIME ) {
						if (sum == null) sum = (java.sql.Time)colArr[sumColumn].getCurrValue();
						else if (((java.sql.Time)colArr[sumColumn].getCurrValue()).compareTo((java.sql.Time)sum) == -1) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_DATETIME ) {
						if (sum == null) sum = (java.sql.Timestamp)colArr[sumColumn].getCurrValue();
						else if (((java.sql.Timestamp)colArr[sumColumn].getCurrValue()).compareTo((java.sql.Timestamp)sum) == -1) sum = colArr[sumColumn].getCurrValue();
					}
				} else if (sumType.equals("Max")) {
					if (colArr[sumColumn].javaType == TYP_INT) {
						if (sum == null) sum = (Integer)colArr[sumColumn].getCurrValue();
						else if ((Integer)colArr[sumColumn].getCurrValue() > (Integer)sum) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_BIGDECIMAL) {
						if (sum == null) sum = (BigDecimal)colArr[sumColumn].getCurrValue();
						else if (((BigDecimal)colArr[sumColumn].getCurrValue()).compareTo((BigDecimal)sum) == 1) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_FLOAT) {
						if (sum == null) sum = (Float)colArr[sumColumn].getCurrValue();
						else if ((Float)colArr[sumColumn].getCurrValue() > (Float)sum) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_DOUBLE) {
						if (sum == null) sum = (Double)colArr[sumColumn].getCurrValue();
						else if ((Double)colArr[sumColumn].getCurrValue() > (Double)sum) sum = colArr[sumColumn].getCurrValue();						
					}
					else if (colArr[sumColumn].javaType == TYP_DATE ) {
						if (sum == null) sum = (java.sql.Date)colArr[sumColumn].getCurrValue();
						else if (((java.sql.Date)colArr[sumColumn].getCurrValue()).compareTo((java.sql.Date)sum) == 1) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_TIME ) {
						if (sum == null) sum = (java.sql.Time)colArr[sumColumn].getCurrValue();
						else if (((java.sql.Time)colArr[sumColumn].getCurrValue()).compareTo((java.sql.Time)sum) == 1) sum = colArr[sumColumn].getCurrValue();
					}
					else if (colArr[sumColumn].javaType == TYP_DATETIME ) {
						if (sum == null) sum = (java.sql.Timestamp)colArr[sumColumn].getCurrValue();
						else if (((java.sql.Timestamp)colArr[sumColumn].getCurrValue()).compareTo((java.sql.Timestamp)sum) == 1) sum = colArr[sumColumn].getCurrValue();
					}
				}
			}
			antal++;
		}
		
		public void reset() {
			antal = 0;
			sum = null;
		}
		
		public String getText() { return text; }
		
		public String getSumString() {
			String ret = "";
			if (sumType.equals("Antal")) {
				ret = antal.toString();
			} else if (sum == null) { 
				ret = ""; 
			} else if (sumType.equals("Medel") ) {
				if (antal > 0) {
					if (colArr[sumColumn].javaType == TYP_DOUBLE || colArr[sumColumn].javaType == TYP_FLOAT  || colArr[sumColumn].javaType == TYP_INT) {
						ret = SXUtil.getFormatNumber((Double)sum / antal, colArr[sumColumn].decimaler);
					} else if (colArr[sumColumn].javaType == TYP_BIGDECIMAL) {
						ret = SXUtil.getFormatNumber(((BigDecimal)sum).doubleValue() / antal, colArr[sumColumn].decimaler);
					}
				}
			} else if (sumType.equals("Sum") || sumType.equals("Min") || sumType.equals("Max")) {
				if (colArr[sumColumn].javaType == TYP_DOUBLE) {
					ret = SXUtil.getFormatNumber((Double)sum, colArr[sumColumn].decimaler);
				} else if (colArr[sumColumn].javaType == TYP_FLOAT) {
					ret = SXUtil.getFormatNumber((Float)sum, colArr[sumColumn].decimaler);
				} else if (colArr[sumColumn].javaType == TYP_BIGDECIMAL) {
					ret = SXUtil.getFormatNumber(((BigDecimal)sum).doubleValue(), colArr[sumColumn].decimaler);
				} else if (colArr[sumColumn].javaType == TYP_DATE) {
					ret = SXUtil.getFormatDate((java.sql.Date)sum);
				} else {
					ret = sum.toString();
				}
			}
			return ret;
		}
	}

}
