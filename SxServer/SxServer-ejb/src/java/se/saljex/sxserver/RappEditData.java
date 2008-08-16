/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ulf
 */
public class RappEditData {
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
		
		public boolean verifyForm(HttpServletRequest request) {
			boolean err = false;
			if (kategori == null)  {
				kategoriErr = "Värde måste anges";
				err = true;
			}
			if (undergrupp == null) {
				undergruppErr = "Värde måste anges";
				err = true;
			}
			if (kortbeskrivning == null) {
				kortbeskrivningErr = "Värde måste anges";
				err = true;
			}
			if (sqlfrom == null) {
				sqlfromErr = "Värde måste anges";
				err = true;
			}
			if (isdistinct == null) {
				isdistinctErr = "Värde måste anges";
				err = true;
			} else {
				if (!"true".equals(isdistinct) && "false".equals(isdistinct)) {
					isdistinctErr = "Värde måste vara sant eller falskt";
					err = true;					
				}
			}
			return err;
		}
		public void setFromForm(HttpServletRequest request) {
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
			sb.append("<input type=\"hidden\" name=\"id\" value=\"edithuvudform\">");
			sb.append("<input type=\"hidden\" name=\"rappsession\" value=\"" + rappSession + "\">");
			sb.append("<table>");
			sb.append("<tr><td>Kategori:</td><td><input type=\"text\" name=\"kategori\" value=\"" + kategori + "\"></td><td>" + kategoriErr + "</td></tr>");
			sb.append("<tr><td>Undergrupp:</td><td><input type=\"text\" name=\"undergrupp\" value=\"" + undergrupp + "\"></td><td>" + undergruppErr + "</td></tr>");
			sb.append("<tr><td>Kortbeskrivning:</td><td><input type=\"text\" name=\"kortbeskrivning\" value=\"" + kortbeskrivning + "\"></td><td>" + kortbeskrivningErr + "</td></tr>");
			sb.append("<tr><td>Rubrik:</td><td><input type=\"text\" name=\"reportrubrik\" value=\"" + reportrubrik + "\"></td><td>" + reportrubrikErr + "</td></tr>");
			sb.append("<tr><td>SQL from-sats:</td><td><input type=\"text\" name=\"sqlfrom\" value=\"" + sqlfrom + "\"></td><td>" + sqlfromErr + "</td></tr>");
			if ("true".equals(isdistinct))  checked = "checked"; else checked = "";
			sb.append("<tr><td colspan=\"2\">SQL distinct: <input type=\"checkbox\" name=\"sqlfrom\" value=\"true\" " + checked + "></td><td>" + isdistinctErr + "</td></tr>");
			return sb.toString();
		}
		
	}
	
	public class RappHuvud {
		public int rappid = 0;
		public String behorighet = null;
		public String kategori = null;
		public String undergrupp = null;
		public String kortbeskrivning = null;
		public String reportrubrik = null;
		public String sqlfrom = null;
		public boolean isdistinct = false;
		public java.sql.Timestamp crtime = null;
		public int getIsdistinctToInt() { if (isdistinct) return 1; else return 0; }
	}
	
 
	public class RappColumn{
		public int col;
		public String sqlLabel = null;
		public String label = null;
		public boolean groupby = false;
		public Integer decimaler = null;
		public boolean hidden = false;
		public String groupbyheadertext = null;
		public String groupbyfootertext = null;
		public int getGroupbyToInt() { if (groupby) return 1; else return 0; }
		public int getHiddenToInt() { if (hidden) return 1; else return 0; }
	}
	
	public class RappFilter {
		public Integer pos = null;
		public String javatype = null;
		public String name = null;
		public String label = null;
		public boolean hidden = false;
		public String defaultvalue = null;
		public int getHiddenToInt() { if (hidden) return 1; else return 0; }
	}
	
	
	public class RappSum {
		public Integer sumcolumn = null;
		public Integer resetcolumn = null;
		public String sumtype = null;
		public String sumtext = null;
	}

}
