/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * @author Ulf
 */
public class LagerCheck {

	private Connection c;
	
	public LagerCheck(Connection con) throws SQLException {
		c = con;
		
	}

	public void run() throws SQLException {
		ResultSet r;
		Statement s;
		int cn;
		int cn2 = 0;
		
		s = c.createStatement();
		Logger.getLogger("sx").info("LagerCheck startar");
		
		s.executeUpdate("update lager set best = 0, iorder = 0");

		r = s.executeQuery("select artnr, lagernr, sum(best),0 from order1 o1, order2 o2, artikel a where" +
				" o1.ordernr = o2.ordernr and a.nummer = o2.artnr group by artnr, lagernr");
		cn = setLager(r);
		cn2 = cn2 + cn;
		Logger.getLogger("sx").finest("Antal uppdaterat för order: " + cn);

		 
		r = s.executeQuery("select artnr, lagernr, sum(rest),0 from rorder r, artikel a where a.nummer = r.artnr group by artnr, lagernr");
		cn = setLager(r);
		cn2 = cn2 + cn;
		Logger.getLogger("sx").finest("Antal uppdaterat för restorder: " + cn);
		 
		r = s.executeQuery("select artnr, lagernr, 0, sum(best) from best1 b1, best2 b2, artikel a where " +
					" b1.bestnr = b2.bestnr and a.nummer = b2.artnr group by artnr, lagernr");
		cn = setLager(r);
		cn2 = cn2 + cn;
		Logger.getLogger("sx").finest("Antal uppdaterat för beställning: " + cn);
		
		Logger.getLogger("sx").info("LagerCheck slutförd. Totalt antal rader: " + cn2);
	}
	
	private int setLager(ResultSet r) throws SQLException {
		Statement s;
		s = c.createStatement();
		int a;
		int cn = 0;
		while (r.next()) {
			cn++;
			a = s.executeUpdate("update lager set iorder = iorder + " + r.getFloat(3) + ", best = best + " + r.getFloat(4) + " where artnr = '" + r.getString(1) + "' and lagernr = " + r.getInt(2));
			if (a < 1) {		//Om vi inte fick något uppdaterat så saknas raden och vi får lägga till den
				a = s.executeUpdate("insert into lager (artnr, lagernr, ilager, bestpunkt, maxlager, best, iorder)" +
						" values('" + r.getString(1) + "', " + r.getInt(2) + ", 0, 0, 0, " + r.getFloat(4) + ", " + r.getFloat(3) + ")" );
				if (a < 1) {		//Vi fick inget tillagd 
					ServerUtil.log("Det gick inte att lägga till i lager. Artikelnr: " + r.getString(1) + ", lagernr: " + r.getInt(2));
				}
			}
		}
		return cn;
	}
}
