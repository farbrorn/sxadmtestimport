/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.saljex.sxlibrary.SXConstant;

/**
 *
 * @author ulf
 */
public class RapportLista {
	private ArrayList<RapportListaHuvud> huvuden = new ArrayList();
	private boolean filledFromDatabase = false;

	public RapportLista() {
		ArrayList<String> arEkonomi = new ArrayList();
		arEkonomi.add("Ekonomi");
		// Sätt upp lista på alla registrerade rapporter
		huvuden.add(new RapportListaHuvud("kontored", "Ekonomi", "Resultat", "Resultat per kostnadsställe", "", null, arEkonomi));
		huvuden.add(new RapportListaHuvud("reskontraanalys", "Ekonomi", "Reskontra", "Reskontra för olika månader", "", null, arEkonomi));
		huvuden.add(new RapportListaHuvud("filialforsaljning", "Filialrapporter", "Försäljning", "Försäljning för filial per mån", "", null, null));
		huvuden.add(new RapportListaHuvud("topplistaartikel", "Filialrapporter", "Topplistor", "Artiklar", "", null, null));
		huvuden.add(new RapportListaHuvud("topplistakund", "Filialrapporter", "Topplistor", "Kunder", "", null, null));
		huvuden.add(new RapportListaHuvud("topplistaartgrupp", "Filialrapporter", "Topplistor", "Artikelgrupper", "", null, null));
		huvuden.add(new RapportListaHuvud("topplistalagervarde", "Filialrapporter", "Lager", "Lagervärde", "", null, null));
		huvuden.add(new RapportListaHuvud("filialstat1", "Filialrapporter", "Lager", "Order/försäljningsstatistik", "", null, null));
		huvuden.add(new RapportListaHuvud("saljstatartgrupp", "Filialrapporter", "Försäljning", "Försäljningsstatistik varugrupper", "", null, null));
		huvuden.add(new RapportListaHuvud("mk-statistik", "Ekonomi", "Försäljning", "Statistik på säljare", "", null, arEkonomi));
		huvuden.add(new RapportListaHuvud("mk-saljstatartgrupp", "Ekonomi", "Försäljning", "Artikelgruppstatistik på säljare", "", null, arEkonomi));

		java.util.Collections.sort(huvuden, new RapportListaHuvudComparator());
	}

	public void fillFromDatabase(Connection con) throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("select rappid, kategori, undergrupp, kortbeskrivning, reportrubrik, behorighet from rapphuvud");
		ArrayList<String> gb = null;
		while (rs.next()) {
			gb = null;
			if (rs.getString(6) != null) if (!rs.getString(6).isEmpty()) {
					gb = new ArrayList();
					gb.add(rs.getString(6));
			}
			huvuden.add(new RapportListaHuvud(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), null, gb));
		}
		java.util.Collections.sort(huvuden, new RapportListaHuvudComparator());
		filledFromDatabase = true;
	}

	public ArrayList<RapportListaHuvud> getHuvuden() {
		return huvuden;
	}

	public void printJspRapport(HttpServletRequest request, HttpServletResponse response, String jsp) throws ServletException, IOException {
			try {
				request.getRequestDispatcher("/WEB-INF/jspf/jsprapport/" + jsp + ".jsp").include(request, response);
			} catch (IOException e) { response.getWriter().print("Felaktigt rapportfil: " + jsp); }
	}

	public void printRapport(HttpServletRequest request, HttpServletResponse response, Integer rappid, Connection con ) throws ServletException, IOException {
			try {
				RappHTML rh = new RappHTML(con, request);
				rh.prepareFromSQLRepository(rappid);
				response.getWriter().println(rh.print());
			}
			catch (SQLException e) { response.getWriter().print("Undantagsfel " + e.toString()); }
	}

	public boolean isBehorig(RapportListaHuvud j, String anvandare, Connection con) throws SQLException{
			//Om inga restriktioner finns - tillåt alltid
			if (j.behorighetsgrupper == null && j.behorigaAnvandare == null) return true;

			//Kolla användare
			if (j.behorigaAnvandare != null) {
				for (String s : j.behorigaAnvandare) {
					if (anvandare.equals(s)) return true;
				}
			}


			//Bygg sträng för behörighetsgrupper
			String bstr;
			bstr = "'" + SXConstant.BEHORIGHET_INTRA_ADMIN + "'";
			bstr = bstr + ", '" + SXConstant.BEHORIGHET_INTRA_SUPERUSER + "'";
			if (j.behorighetsgrupper != null) {
				for (String a : j.behorighetsgrupper) {
					bstr = bstr + ", '" + a + "'";
				}
			}
			bstr = "b.behorighet in (" + bstr + ")";


			PreparedStatement ps = con.prepareStatement("select count(*) from anvbehorighet b where anvandare = ? and  " + bstr);
//System.out.println("select count(*) from anvbehorighet b where b.anvandare = ? and  " + bstr);
//System.out.println("anv: " + anvandare);
			ps.setString(1, anvandare);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
//System.out.println("rs getint:; " + rs.getInt(1));
				if (rs.getInt(1) > 0) return true;
			}
		return false;
	}

	public boolean isBehorig(Integer rappid, String anvandare, Connection con) throws SQLException{
		if (rappid == null || anvandare == null) return false;
		if (!filledFromDatabase) fillFromDatabase(con);
		for(RapportListaHuvud j : huvuden) {
			if (rappid.equals(j.rappid)) {
				return isBehorig(j, anvandare, con);
			}
		}
		return false;
	}

	public boolean isBehorig(String jsp, String anvandare, Connection con) throws SQLException{
		if (jsp == null || anvandare == null) return false;

		for(RapportListaHuvud j : huvuden) {
			if (jsp.equals(j.jsp)) {
				return isBehorig(j, anvandare, con);
			}
		}
		return false;
	}


	 class RapportListaHuvudComparator implements java.util.Comparator {
		public int compare (java.lang.Object o1, java.lang.Object o2) {
			try {
				String l1 = ((RapportListaHuvud)o1).kategori;
				String l2 = ((RapportListaHuvud)o2).kategori;
				String a1 = ((RapportListaHuvud)o1).undergrupp;
				String a2 = ((RapportListaHuvud)o2).undergrupp;
				if (l1 == null) {
					return 0;
				} else {
					if (l1.equals(l2)) {
						if (a1 == null) {
							return 0;
						} else {
							return a1.compareTo(a2);
						}
					}	else {
						return l1.compareTo(l2);
					}
				}
			} catch (Exception e) { return 0; }
		}
	}


	public class RapportListaHuvud {
		public String jsp = null;
		public Integer rappid = null;
		public String kategori;
		public String undergrupp;
		public String kortbeskrivning;
		public String reportrubrik;
		public ArrayList<String> behorigaAnvandare;
		public ArrayList<String> behorighetsgrupper;
		public RapportListaHuvud (String jsp, String kategori, String undergrupp, String kortbeskrivning, String reportrubrik, ArrayList<String> behorigaAnvandare, ArrayList<String> behorighetsgrupper) {
			this.jsp = jsp;
			this.kategori = kategori;
			this.undergrupp = undergrupp;
			this.kortbeskrivning = kortbeskrivning;
			this.reportrubrik = reportrubrik;
			this.behorigaAnvandare = behorigaAnvandare;
			this.behorighetsgrupper = behorighetsgrupper;
		}
		public RapportListaHuvud (Integer rappid, String kategori, String undergrupp, String kortbeskrivning, String reportrubrik, ArrayList<String> behorigaAnvandare, ArrayList<String> behorighetsgrupper) {
			this.rappid = rappid;
			this.kategori = kategori;
			this.undergrupp = undergrupp;
			this.kortbeskrivning = kortbeskrivning;
			this.reportrubrik = reportrubrik;
			this.behorigaAnvandare = behorigaAnvandare;
			this.behorighetsgrupper = behorighetsgrupper;
		}
	}

}
