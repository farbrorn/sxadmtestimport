package se.saljex.sxserver.web;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author ulf
 */

public class SparradeKunderHandler {
	private Connection con;
	public SparradeKunderHandler(Connection con) {
		this.con = con;
	}

	public ArrayList<SparrKund> getSparrList() throws SQLException{
		return getSparrList("1=1");
	}
	public ArrayList<SparrKund> getSparrList(int lagerNr) throws SQLException{
		return getSparrList("f1.lagernr = " + lagerNr);
	}

	private ArrayList<SparrKund> getSparrList(String lagerFilter) throws SQLException {
		if (lagerFilter==null) lagerFilter = "1=1";
		String q =
		"select kk.kundnr, kk.namn, kk.kgrans, kk.totalt, kk.totaltgammalt from " +
		" (select kr.kundnr as kundnr, k.namn as namn, k.kgrans as kgrans, sum(kr.tot) as totalt, sum( case when kr.falldat+60 < current_date " +
		" then kr.tot else 0 end ) as totaltgammalt  from kundres kr join kund k on k.nummer=kr.kundnr " +
		" group by kr.kundnr, k.namn, k.kgrans) kk " +
		" where (kk.totaltgammalt > 500 or (kk.kgrans>0 and kk.totalt>kk.kgrans)) " +
		" and kk.kundnr in (select kundnr from faktura1 f1 where f1.datum > current_date-150 and " +
		lagerFilter +	" ) order by kk.totaltgammalt desc ";

		ResultSet rs = con.createStatement().executeQuery(q);
		ArrayList<SparrKund> arr = new ArrayList();
		while (rs.next()) {
			arr.add(new SparrKund(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5)));
		}
		return arr;
	}

	public class SparrKund {
		public SparrKund(String kundNr, String namn, Double kgrans, Double totalReskontra, Double totaltForfallet60) {
			this.kundNr = kundNr;
			this.namn = namn;
			this.kgrans = kgrans;
			this.totalReskontra = totalReskontra;
			this.totaltForfallet60 = totaltForfallet60;
		}

		public String kundNr=null;
		public String namn = null;
		public Double kgrans = null;
		public Double totalReskontra = null;
		public Double totaltForfallet60 = null;
	}
}
