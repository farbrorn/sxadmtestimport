/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import java.util.regex.*;
import javax.persistence.Query;

/**
 *
 * @author Ulf
 */
public class SXUtil {
	
	public static boolean isValidEmailAddress(String aEmailAddress){
		if (aEmailAddress == null) return false;
		boolean result = false;
		try {
			InternetAddress emailAddr = new InternetAddress(aEmailAddress);	//Gör en standard validering, men tar inte hänsyn till om det är med @
			if (aEmailAddress.contains("@")) {	//Finns @?
				String[] tokens = aEmailAddress.split("@");	//Kollar så att det finns tecken på båda sidor om @
				if (tokens.length == 2 && tokens[0].length() > 0 && tokens[1].length() > 3) {
					result = true;
				}
			}
		}	catch (AddressException ex){  }  // Gör inget särksilt
		return result;
	}

	
	
	
	public static Calendar getTodayDate()  {
		// Returns todys date as a Calendar and WITH TIME PART SET TO 0
		return getCalendarFromDate(new Date());
	}
	
	public static Calendar getCalendarFromDate(Date d) {
		Calendar idag = Calendar.getInstance();
		idag.setTime(d);
		idag.set( idag.HOUR_OF_DAY, 0 );
		idag.set( idag.MINUTE, 0 );
		idag.set( idag.SECOND, 0 );
		idag.set( idag.MILLISECOND, 0 );
		return idag;
		
	}
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

	public static TableFuppg getFuppg(EntityManager em) {
		Query q = em.createNamedQuery("TableFuppg.find");
		return (TableFuppg)q.getSingleResult();
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
	
    public static String getFormatDate(Date d) {
		 if (d != null) {
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 return simpleDateFormat.format(d);
		 } else { return ""; }
    }
	 
    public static String getFormatDate() {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       return simpleDateFormat.format(new Date());
    }
    
    public static String getFormatNumber(double tal, int decimaler) {
        int x;
        String format = "###0";
        if (decimaler > 0) {
            format = format + ".";
            for (x=0;x<decimaler;x++) {
                format = format + "0";
            }
        }
        
        DecimalFormat df = new DecimalFormat(format);
        String s = df.format(tal);
        return s;
    }
    public static String getFormatNumber(double tal) {
        return getFormatNumber(tal,2);
    }

	public static Double getRoundedDecimal(Double a) {	
		//Returnerar värdet avrundat till två decimaler
		return Math.round(a*100.0) / 100.0;
	}
    
    public static Date addDate(Date d, int dagar) {
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(d);
       calendar.add(Calendar.DATE, dagar);
       return calendar.getTime();
    }
    
}
