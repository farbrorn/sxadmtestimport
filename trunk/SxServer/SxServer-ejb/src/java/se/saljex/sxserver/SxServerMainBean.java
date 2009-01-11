package se.saljex.sxserver;

import se.saljex.sxserver.tables.TableBest1;
import se.saljex.sxserver.tables.TableSxservjobb;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.annotation.PostConstruct;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import com.lowagie.text.*;//.text.Document;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.mail.Session;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
  
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;



/**
 *
 * @author Ulf
 * 
 * Setup: 
 * Registrera IText som library i både(?) web och bean
 * Registrera en listener i web som anropar main() i sxservermainbean
 * Database jar lägges i sun/sdk/domains/domain?????/lib/ext
 * Registrera databas och pool i glassfish -	datasource classname = com.sap.dbtech.jdbcext.DataSourceSapDB
 *												pool property password=????, user=sxfakt, url=jdbc:sapdb://localhost/sxtest
 *												Connection validation: Checked
 * registrera pool saljexsepool					datasource classname = com.mysql.jdbc.jdbc2.optional.MysqlDataSource
 *												pool property	port=3306, databasename = , userName = , serverName =, password = 
 *												Connection validation: Checked
 *												Allow non component callers: checked (Vet inte om det behövs, men stod så i ett exepel)
 *												Transaction isolation: read-commited
 * * Registrera mailhanterare i glassfish
 * 
 */

@Stateless
public class SxServerMainBean implements SxServerMainLocal {
	@Resource(name = "saljexse")
	private DataSource saljexse;
	@Resource(name = "sxadm")
	private DataSource sxadm;
	@Resource TimerService timerService;
	@Resource EJBContext context;
	@Resource(name="sxmail", mappedName="sxmail") private Session mailsxmail;
    @PersistenceContext private EntityManager em;  
	
		@EJB		SxServerMainLocal sxServerMainBean;// = new JobbHandlerBean();
	
		@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void test() {
		System.out.println("Test startad");
		Integer maxid;
		TableSxservjobb j;
		maxid = (Integer)em.createQuery("select max(j.jobbid) from TableSxservjobb j").getSingleResult();
		System.out.println("Test max " + maxid);
				j = new TableSxservjobb((int)maxid+1,"Test");
		System.out.println("Test max 2" + maxid);
				em.persist(j);
		System.out.println("Test max 3-" + maxid);
		maxid = (Integer)em.createQuery("select max(j.jobbid) from TableSxservjobb j").getSingleResult();
		System.out.println("Test max 4" + maxid);
		//context.setRollbackOnly();
		
		}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Timeout
	public void handleTimer(Timer timer)   {
		System.out.println("handleTimer startad: " + timer.getInfo());		

		if (timer.getInfo().equals("JobbTimer")) handleJobbTimer();
		else if (timer.getInfo().equals("KvartTimer")) handleKvartTimer();
		else if (timer.getInfo().equals("TimTimer")) handleTimTimer();
		else if (timer.getInfo().equals("DygnsTimer")) handleDygnsTimer();
		else if (timer.getInfo().equals("VeckoTimer")) handleVeckoTimer();
	}

	private void handleKvartTimer() {
		try {
			checkWorder();
		}finally {
			startKvartTimer();
		}
	}
	
	private void handleTimTimer() {
		try {
		}finally {
			startTimTimer();
		}
		
	}
	
	private void handleDygnsTimer() {
		//Uppdatera priser mm på nätet
		Connection con = null;
		Connection conSe = null;
		try {
			con = sxadm.getConnection();
			conSe = saljexse.getConnection();
			try {
				WebArtikelUpdater w = new WebArtikelUpdater(em, conSe);
				w.updateWArt();
			} catch (SQLException e) { SXUtil.log("Undantag i handleDygnsTimer vid WebArtikelUpdater: " + e.toString()); }

			// Räkna om alla lagervärden
			try {
				LagerCheck lagerCheck = new LagerCheck(con);
				lagerCheck.run();
			} catch (SQLException e) { SXUtil.log("Undantag i handleDygnsTimer vid LagerCheck: " + e.toString()); }
		} catch (SQLException e) {
			SXUtil.log("Undantag i handleDygnsTimer: " + e.toString());
		} finally {
			try { con.close(); } catch (Exception e) {}
			try { conSe.close(); } catch (Exception e) {}
			startDygnsTimer();
		}
	}
	
	private void handleVeckoTimer() {
		// Uppdatera trädstrukturen
		Connection con = null;
		Connection conSe = null;
		try {
			con = sxadm.getConnection();
			conSe = saljexse.getConnection();
			try {
				WebArtikelUpdater w = new WebArtikelUpdater(em, conSe);
				w.updateWArtGrp();
				w.updateWArtGrp();
				w.updateWArtKlase();
				w.updateWArtKlaseLank();
			} catch (SQLException e) { SXUtil.log("Undantag i handleVeckoTimer vid WebArtikelUpdater: " + e.toString()); }
		} catch (SQLException e) {
			SXUtil.log("Undantag i handleVeckoTimer: " + e.toString());
		} finally {
			try { con.close(); } catch (Exception e) {}
			try { conSe.close(); } catch (Exception e) {}
			startVeckoTimer();
		}
	}
	
	private void handleJobbTimer() {
			JobbHandler jobbHandler;

			try {
				jobbHandler = new JobbHandler(em,mailsxmail);
				
				//Behandlar jobbkö från lista
				java.util.List<TableSxservjobb> ljob = jobbHandler.getJobbList();
				for (TableSxservjobb t : ljob) {
					try {
						sxServerMainBean.handleJobb(t);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						SXUtil.log("Ett undantagsfel uppstod vid bearbetning av jobb." + t.getJobbid() + ". Försöker fortsätta med nästa " + e.toString()); e.printStackTrace();
					}
				}
				
				java.util.List<TableBest1> lbe1;
				//Sänd alla osända beställningar
				lbe1 = jobbHandler.getSandBestEpostList();
				for (TableBest1 be1 : lbe1) {
					try {
						sxServerMainBean.handleSandBestEpost(be1);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						SXUtil.log("Ett undantagsfel uppstod vid bearbetning av sänd best1 epost." + be1.getBestnr() + ". Försöker fortsätta med nästa " + e.toString()); 
					}
				} 

				//Sänd påminnelser på beställning
				lbe1 = jobbHandler.getSandBestEpostList();
				for (TableBest1 be1 : lbe1) {
					try {
						sxServerMainBean.handleSandBestPaminEpost(be1);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						SXUtil.log("Ett undantagsfel uppstod vid bearbetning av sänd best1 påminnelse epost." + be1.getBestnr() + ". Försöker fortsätta med nästa " + e.toString()); 
					}
				} 
				
			} catch (Exception e) { 
				System.out.println("Ett undantagsfel uppstod vid bearbetning av jobb: " + e.toString()); 
				context.setRollbackOnly();
			}
			finally { 
				startJobbTimer();	
			} 
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleJobb(TableSxservjobb t) throws  DocumentException, IOException, NamingException, MessagingException {
		JobbHandler jobbHandler = new JobbHandler(em,mailsxmail);		//Vi gör detta kryptiska anrop för att ny transaktion ska startas för varje jobb
			System.out.println("Behandlar jobbid " + t.getJobbid());
			if (t.getUppgift().equals(SXConstant.SERVJOBB_UPPGIFT_SAND)) {
				if (t.getDokumenttyp().equals(SXConstant.SERVJOBB_DOKUMENTTYP_FAKTURA)) {	//Faktura
					if (t.getSandsatt().equals(SXConstant.SERVJOBB_SANDSATT_EPOST)) {
						jobbHandler.handleSandFakturaEpost(t); 
					}
				} else if (t.getDokumenttyp().equals(SXConstant.SERVJOBB_DOKUMENTTYP_BEST)) {	// Beställning
					if (t.getSandsatt().equals(SXConstant.SERVJOBB_SANDSATT_EPOST)) {
						jobbHandler.handleSandBestEpost(t); 
					}				
				} else if (t.getDokumenttyp().equals(SXConstant.SERVJOBB_DOKUMENTTYP_OFFERT)) {	//Offert
					if (t.getSandsatt().equals(SXConstant.SERVJOBB_SANDSATT_EPOST)) {
						jobbHandler.handleSandOffertEpost(t);
					}
				}
			}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleSandBestEpost(TableBest1 be1) throws  DocumentException, IOException, NamingException, MessagingException {
		JobbHandler jobbHandler = new JobbHandler(em,mailsxmail);		//Vi gör detta kryptiska anrop för att ny transaktion ska startas för varje jobb
		jobbHandler.sandBestEpost(be1);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleSandBestPaminEpost(TableBest1 be1) throws  DocumentException, IOException, NamingException, MessagingException {
		JobbHandler jobbHandler = new JobbHandler(em,mailsxmail);		//Vi gör detta kryptiska anrop för att ny transaktion ska startas för varje jobb
		jobbHandler.sandBestPaminEpost(be1);
	}
	
	
	
	@PostConstruct
	public void sxinit()  {
	}

	public void startTimers() {
		startJobbTimer();
		startKvartTimer();
		startTimTimer();
		startDygnsTimer();
		startVeckoTimer();
	}
	
	private void startJobbTimer() {
		sxServerMainBean.startTimer(7000,"JobbTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startKvartTimer() {
		sxServerMainBean.startTimer(15*60*1000,"KvartTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startTimTimer() {
		sxServerMainBean.startTimer(60*60*1000,"TimTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startDygnsTimer() {
		sxServerMainBean.startTimer(24*60*60*1000,"DygnsTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startVeckoTimer() {
		sxServerMainBean.startTimer(7*24*60*60*1000,"VeckoTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) public void startTimer(int time, String timerName) {
	// Vi använder oss av engångstimers, så vi inte får köade utlösningar vid långa processtider 
	// Vi annoterar med new transaction, så att timern verkligen blir satt även om den yttre transactionen kör rollback
		Timer timer;
		Collection c = timerService.getTimers();
		Iterator timers = timerService.getTimers().iterator();
		int antalTimers = 0;
		while (timers.hasNext()) {
			timer = (Timer) timers.next();
			if (timer.getInfo().equals(timerName)) { antalTimers++; }
		}
		if (antalTimers <= 1) {			//Om det hittas en timer så är det den aktuella, som har utlöst timereventet, därför ska vi starta ny
			timer = timerService.createTimer(time,timerName);
		}
		
	}
	
	

	public ByteArrayOutputStream getPDF(HttpServletResponse response) throws IOException, DocumentException{
		
		PdfFaktura sx;
		sx = new PdfFaktura(em);

		int faktnr = 149804;

		
		return sx.getPDF(faktnr);
	}

	public void main() {
		/*Detta är uppstartsproceduren, som skall köras en gång vid systemstart, och därefter aldrig mer
		 * Initieringen av denna metod sköts från en Listener som finns i Webb-delen och gör anropet hit */
		startTimers();
		if (Logger.getLogger("sx").getHandlers().length < 1) {	//Har vi ingen handler, så ska vi lägga till deb.  Vi måste kolla detta så att inte flera file-handlers blir registrerade
																//Detta ifall applicationen startas om, och vi riskerar fler handlers
			try {
				Logger.getLogger("sx").addHandler(new FileHandler("%h/sxserver-%u-%g.log",1024*1024,3,true));
			} catch (Exception e) { System.out.println("**** Undantagsfel i se.saljex.sxserver.main när java.util.logger försöker skapa FileHandler." + e.toString()); }
		}
		Logger.getLogger("sx").info("se.saljex.sxserver.main startad.");
/*		
		try {
			RandomAccessFile rf = new RandomAccessFile("c:\\dum\\sxlogo.png", "r");
            int size = (int)rf.length();
            byte imext[] = new byte[size];
            rf.readFully(imext);
            rf.close();
			SerialBlob b = new SerialBlob(imext);
			//b.setBytes(1,imext);
			PreparedStatement pstmt = con.prepareStatement("insert into bilder (namn,typ,bilddata) values ('SxLogo','png',?) ");
			pstmt.setBlob(1, b);
			System.out.println ("Insert SQL " + pstmt.executeUpdate());
			
		} catch (Exception e) { Logger.getLogger("sx").warning("Exception cid " + e.toString()); }
*/		
	 }


	public String getHTMLStatus() {
		String ret = "<h1>Statusrapport från SXServer</h1><br/>" + new Date().getTime() + "<br>";
		ret = ret + "<h2>Timers</h2><br/>";
		ret = ret+"<table><tr><th>Timer namn</th><th>Nästa tid</th></tr>";
		Collection<Timer> c = timerService.getTimers();
		for (Timer timer : c) {
			ret = ret + "<tr><td>" + timer.getInfo() + "</td><td>" + timer.getNextTimeout().toString() + "</td></tr>";
		}
		ret = ret + "</table>";

		Connection conSe = null;
		try {
			conSe = saljexse.getConnection();
			Statement s = conSe.createStatement();
			ResultSet r = s.executeQuery("select * from weborder1 order by wordernr");
			ret = ret + "<table>";
			while (r.next()) {
				ret = ret + "<tr><td>" + r.getString(1) + "</td><td>" + r.getString(2) + "</td><td>" + r.getString(3) + "</td><td>" + r.getString(4) + "</td><td>" + r.getString(5) + "</td><td>" + r.getString(6) + "</td><td>" + r.getString(7) + "</td><td>" + r.getString(8) + "</td><td>" + r.getString(9);
				ret = ret + "</td></tr>";
			}
			ret = ret + "</table>";
			
		} catch (Exception e) { ret = ret + e.toString(); }
		finally { try { conSe.close(); } catch (Exception e) {} }
		return ret;
	}

	private void checkWorder()  {
		Connection conSe = null;
		SXUtil.log("Startar checkWorder");
		try {
			conSe = saljexse.getConnection();
			WebOrderHandler woh = new WebOrderHandler(em,conSe,SXUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE,SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
			ArrayList<Integer> orderList = woh.getSkickadWorderList();
			for (Integer o : orderList) {
				try {
					ArrayList<Integer> sparadeOrderArr;
					sparadeOrderArr = sxServerMainBean.saveWorder(o);
					String logStr = "Weborder " + o + " sparad som order/ordrar: ";
					for (Integer nr : sparadeOrderArr) {			//Skapa sträng med alla ordernummer som är sparade
						logStr = logStr + nr + " ";
					}
					SXUtil.log(logStr);
				} catch (KreditSparrException ke) {
					SXUtil.log("Kreditspärr vid Weborder " + o + ". Ordern sparas inte." );
					String mailTo = "";
					Statement st = conSe.createStatement();
					ResultSet r = st.executeQuery("select epost from webuser u, weborder1 o where u.loginnamn = o.loginnamn and o.wordernr = " + o);
					if (r.next()) {
						try {
							String testlage  = SXUtil.getSXReg(em,"SxServTestlage","Ja" );

							if (!testlage.equals("Nej")) {
								mailTo = "ulf.hemma@saljex.se";
							} else {
								mailTo = r.getString(1);
							}

							SendMail m = new SendMail(mailsxmail, SXUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPUSER), SXUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPPASSWORD),
																SXUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPSERVERPORT));
							m.sendSimpleMail(	em,
													mailTo,
													SXUtil.getSXReg(em,SXConstant.SXREG_WORDER_SPARRAD_ORDER_SUBJECT, SXConstant.SXREG_WORDER_SPARRAD_ORDER_SUBJECT_DEFAULT),
													SXUtil.getSXReg(em,SXConstant.SXREG_WORDER_SPARRAD_ORDER_BODY, SXConstant.SXREG_WORDER_SPARRAD_ORDER_BODY_DEFAULT));
						} catch (Exception e) {
							SXUtil.log("Kunde inte skicka epost om kreditspärr till " + mailTo + " Weborder " + o + ". " + e.toString());
						}
					} else {
						SXUtil.log("Kunde inte hitta kontaktinfo för weborder " + o + " vid försök att skicka epost om kreditspärr.");
					}
					r.close();
					st.executeUpdate("update worder1 set status='Spärrad' where wordernr = " + o);

				} catch (SQLException se) {
					SXUtil.log("Fel vid spara webordernr " + o + ": " + se.toString());
				}
			}
		} catch (SQLException se1) {
			SXUtil.log("Fel " + se1.toString()); 
		}	finally {
			try { conSe.close(); } catch (Exception e) {}
			SXUtil.log("checkWorder slutförd");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ArrayList<Integer> saveWorder(int worderNr) throws java.sql.SQLException, KreditSparrException {
		// Spara angiven weborder i egen transaktion
		// returnerar array med ordernumren som webordern sparades som
		Connection conSe = null;
		ArrayList<Integer> ret;
		try {
			conSe = saljexse.getConnection();
			WebOrderHandler woh = new WebOrderHandler(em,conSe,SXUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE,SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
			ret = woh.loadWorderAndSaveSkickadAsOrder(worderNr);
			if (ret == null) { throw new SQLException("Kunde inte spara order. Webordernr: " + worderNr); }
		} catch (KreditSparrException ke) {
			throw ke;
		} finally {
			try { conSe.close(); } catch (Exception e) {}
		}
		return ret;
	}

	public String tester(String testTyp) {
		Connection conSe = null;
		Connection con = null;
		try {
			conSe = saljexse.getConnection();
			con = sxadm.getConnection();
			SXTester t = new SXTester(em, con ,conSe);
			return t.tester(testTyp);
		}	catch (SQLException se) { return "SQLException inträffade: " + se.toString();
		} finally {
			try { con.close(); } catch (Exception e) {}
			try { conSe.close(); } catch (Exception e) {}
		}
	}
	




	

 
}
