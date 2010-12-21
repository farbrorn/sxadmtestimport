/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableMeddel;
import se.saljex.sxserver.tables.TableSxreg;

/**
 *
 * @author Ulf
 */
public class ServerUtil {
	public static String getSXReg(EntityManager em, String id)  {
		return getSXReg(em,id,"");
	}
	
	public static String getSXReg(EntityManager em, String id, String defaultVarde)  {
		// Hitta med default värde
		String ret ;
		TableSxreg sxr;
		sxr = em.find(TableSxreg.class, id);
		if (sxr == null) {
			ret = defaultVarde;
			try {
				sxr = new TableSxreg();
				sxr.setId(id);
				sxr.setVarde(defaultVarde);
				em.persist(sxr);
			} catch (Exception e) {
				log("Kunde inte spara SXReg ID: " + id + " Undantag: " + e.toString());
			}
		} else { ret = sxr.getVarde(); }

		return ret;
	}
	public static String getSXReg(Connection con, String id, String defaultVarde) throws SQLException {
		// Hitta med default värde
		String ret;
		PreparedStatement s = con.prepareStatement("select varde from sxreg where id=?");
		s.setString(1, id);
		ResultSet r = s.executeQuery();
		if (r.next()) return r.getString(1);
		else return defaultVarde;
	}

	public static TableFuppg getFuppg(EntityManager em) {
		Query q = em.createNamedQuery("TableFuppg.findAll");
		return (TableFuppg)q.getSingleResult();
	}


	public static void logDebug(String s) {
		Logger.getLogger("sx").finest(s);
	}

	public static void log(String s) {
		Logger.getLogger("sx").info(s);
	}

	public static void logFinest(String s) {
		Logger.getLogger("sx").finest(s);
	}

	public static void sendMessage(EntityManager em, String m1, String m2, String mottagare) {
		try {
			TableMeddel med = new TableMeddel(mottagare, m1, m2 );
			em.persist(med);
		} catch (Exception e) {
			Logger.getLogger("sx").info("Kunde inte skapa meddelande " + m1 + " " + m2 + " till " + mottagare + ". Undantag: " + e.toString());
		}
	}

	public static void sendAdmMessage(EntityManager em, String m1, String m2) {
		sendMessage(em, m1, m2, getSXReg(em, "AdminNamn", "Ulf Berg"));
		sendMessage(em, m1, m2, getSXReg(em, "AdminNamn1", "Fredrik Johansson"));
		sendMessage(em, m1, m2, getSXReg(em, "AdminNamn2", "Sven Berg"));
	}



}
