/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
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


	
	// Returnerar datumsträngfrån några olika format till yyy-mm-dd
	public static String parseDateStringToString(String d) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(parseDateStringToDate(d));	
	}

	
	// Returnerar datumsträngfrån några olika format till Date
	public static java.util.Date parseDateStringToDate(String d) throws ParseException{
		if (d==null) d = "";
		d = d.trim();
		String tempDat = "";
		if (d.length() == 10) { //yyyy-mm-dd
			tempDat = d;
		} else if (d.length() == 8) {
			tempDat = d.substring(0, 4) + "-" + d.substring(4, 6) + "-" + d.substring(6, 8);
		} else if (d.length() == 6) {
			tempDat = "20" + d.substring(0, 2) + "-" + d.substring(2, 4) + "-" + d.substring(4, 6);					
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date testDate = sdf.parse(tempDat);
		if (sdf.format(testDate).equals(tempDat)) {		// sdf parsar dagar och månader större än tillåtna  (mån 13 = mån 1 nästa år) Vi jämför om återparsat datum stämmer överens
			d = sdf.format(testDate);
		} else {
			throw new java.text.ParseException(d, 0);
		}		
		return testDate;
	}
	
	
	public static Calendar getTodaySQLDate()  {
		// Returns todys date as a Calendar and WITH TIME PART SET TO 0
		return getSQLCalendarDateFromDate(new Date());
	}
	
	public static Calendar getSQLCalendarDateFromDate(Date d) {
		if (d == null) return null;
		Calendar idag = Calendar.getInstance();
		idag.setTime(d);
		idag.set( Calendar.HOUR_OF_DAY, 0 );
		idag.set( Calendar.MINUTE, 0 );
		idag.set( Calendar.SECOND, 0 );
		idag.set( Calendar.MILLISECOND, 0 );
		return idag;		
	}
	public static java.sql.Date getSQLDate(java.util.Date d) {
		if (d == null) return null;
		return new java.sql.Date(getSQLCalendarDateFromDate(d).getTimeInMillis()); 
	}
	public static java.sql.Date getSQLDate() {
		return getSQLDate(new java.util.Date());
	}
	
	public static String getSXReg(EntityManager em, String id)  {
		return getSXReg(em,id,"");
	}

	public static java.sql.Time getSQLTime(java.util.Date d) {
	// returns date as a sql time with date part set to 0
		if (d == null) return null;
		Calendar idag = Calendar.getInstance();
		idag.setTime(d);
		idag.set( Calendar.YEAR, 1970 );		// sätt detta darum enligt java dokumentationen
		idag.set( Calendar.MONTH, 1 );
		idag.set( Calendar.DAY_OF_MONTH, 1 ); 
		return new java.sql.Time(idag.getTimeInMillis());			
	}
	public static java.sql.Time getSQLTime() {
		return getSQLTime(new java.util.Date());
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

	 public static String toStr(String s) {
		 if (s == null) return ""; else return s;
	 }	 
	 
	public static String toHtml(String string) {
		// Baserat på kod från http://www.rgagnon.com/javadetails/java-0306.html av S. Bayer
		if (string == null) return "";
		StringBuffer sb = new StringBuffer(string.length());
		// true if last char was blank
		boolean lastWasBlankChar = false;
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++)	{
			c = string.charAt(i);
			if (c == ' ') {
				// blank gets extra work,
				// this solves the problem you get if you replace all
				// blanks with &nbsp;, if you do that you loss 
				// word breaking
				if (lastWasBlankChar) {
					 lastWasBlankChar = false;
					 sb.append("&nbsp;");
				 } else {
					 lastWasBlankChar = true;
					 sb.append(' ');
				}
			} else {
            lastWasBlankChar = false;
				// HTML Special Chars
				if (c == '"') sb.append("&quot;");
				else if (c == '&') sb.append("&amp;");
				else if (c == '<') sb.append("&lt;");
				else if (c == '>') sb.append("&gt;");
				else if (c == '\n') sb.append("&lt;br/&gt;");
				else sb.append(c);
			}
		}
		return sb.toString();
	}
}