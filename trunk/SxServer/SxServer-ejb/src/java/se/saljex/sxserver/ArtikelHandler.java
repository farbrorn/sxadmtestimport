/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxserver.tables.TableLagerPK;
import se.saljex.sxserver.tables.TableLager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManager;

/**
 *
 * @author ulf
 */
public class ArtikelHandler {
	private EntityManager em;
	private Connection con;
	
	
	public ArtikelHandler(EntityManager e, Connection c) {
			em = e;
			con = c;
	}

	public int checkLagersaldo() throws SQLException {
		// Returnerar antalet uppdateringar
		int cn = 0;
		try {
			// Först rensa lagertabellen
			cn = cn + em.createNativeQuery("update lager set best = 0, iorder = 0").executeUpdate();

			// Kollar över ordrarna
			cn = cn + checkLagerSetLager("select artnr, lagernr, sum(best),0 from order1 o1, order2 o2, artikel a where " +
						" o1.ordernr = o2.ordernr and a.nummer = o2.artnr group by artnr, lagernr");
			// Kollar restorder
			cn = cn + checkLagerSetLager("select artnr, lagernr, sum(rest),0 from rorder r, artikel a where a.nummer = r.artnr group by artnr, lagernr");

			//Kollar beställningar
			cn = cn + checkLagerSetLager("select artnr, lagernr, 0, sum(best) from best1 b1, best2 b2, artikel a where" +
										" b1.bestnr = b2.bestnr and a.nummer = b2.artnr group by artnr, lagernr");
			
		} catch (SQLException se) { throw new SQLException("Kan inte köra checkLagersaldo Undantag: " + se.toString()); }
		
		return cn;
	}
	
	/* 
	 * Sätter lagersaldon för given SQL-sats
	 * Observera att SQL-satsen måste följa en given syntax: pos 1=artnr, 2=lagernr, 3=iorder, 4=best
	 */
	private int checkLagerSetLager(String sql) throws SQLException {
		int cn = 0;
		TableLager lag;
		ResultSet r = con.createStatement().executeQuery(sql);	
		while (r.next()) {
			lag = em.find(TableLager.class, new TableLagerPK(r.getString(1), r.getShort(2)));
			if (lag == null) {
				lag = new TableLager(r.getString(1), r.getShort(2), r.getDouble(3), r.getDouble(4));
			} else {
				lag.setIorder(lag.getIorder()+r.getDouble(3));
				lag.setBest(lag.getBest()+r.getDouble(4));
			}
			em.persist(lag);
			cn++;
		}
		return cn;
	}

}
