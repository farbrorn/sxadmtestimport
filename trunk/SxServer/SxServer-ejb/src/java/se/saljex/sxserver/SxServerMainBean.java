package se.saljex.sxserver;

import se.saljex.sxlibrary.exceptions.SxOrderLastException;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxlibrary.SxServerMainRemote;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.exceptions.KreditSparrException;
import se.saljex.sxserver.tables.TableBest1;
import se.saljex.sxserver.tables.TableSxservjobb;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.annotation.PostConstruct;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import com.lowagie.text.*;//.text.Document;
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
import java.util.logging.Level;
import java.util.logging.Logger;
  
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import se.saljex.sxlibrary.exceptions.SxInfoException;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableOrder2;
import se.saljex.sxserver.tables.TableVarukorg;



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
 *												Stäng av transaktionshantering så den inte blandas ihop med sxadm transaktioner
 * * Registrera mailhanterare i glassfish
 * 
 */

@Stateless
public class SxServerMainBean implements SxServerMainLocal, SxServerMainRemote {
	@Resource(mappedName = "saljexse")
	private DataSource saljexse;
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
/*	@Resource(mappedName = "sxadmkundbv")
	private DataSource bvadm;
        */
	@Resource TimerService timerService;
	@Resource EJBContext context;
	@Resource(name="sxmail", mappedName="sxmail") private Session mailsxmail;
    @PersistenceContext(unitName="SxServer-ejbPU") private EntityManager em;
//    @PersistenceContext(unitName="SxServer-ejbPU-BV") private EntityManager embv;
    @PersistenceContext(unitName="SxServer-ejbPU-Main") private EntityManager emMain;
	
		@EJB		SxServerMainLocal sxServerMainBean;// = new JobbHandlerBean();
	
	public void test() {
		System.out.println("Test startad");
//		samfaktByEmail();
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Timeout
	public void handleTimer(Timer timer)   throws Exception{

		ServerUtil.log("handleTimer startad: " + timer.getInfo());
		try {
			if (timer.getInfo().equals("JobbTimer")) handleJobbTimer();
			else if (timer.getInfo().equals("KvartTimer")) handleKvartTimer();
			else if (timer.getInfo().equals("TimTimer")) handleTimTimer();
			else if (timer.getInfo().equals("DygnsTimer")) handleDygnsTimer();
			else if (timer.getInfo().equals("VeckoTimer")) handleVeckoTimer();
			else if (timer.getInfo().equals("SamfaktTimer")) handleSamfaktTimer();
		} catch (Exception e) {
			ServerUtil.log("Undantag i handleTimer " + timer.getInfo() + ": " + e.toString());
			throw(e);
		}
	}

	private void handleSamfaktTimer() {
		try {
			if (ServerUtil.getSXReg(em, SXConstant.SXREG_SXSERVSAMFAKTAKTIVERAD, SXConstant.SXREG_SXSERVSAMFAKTAKTIVERAD_DEFAULT).equals("Ja")) {
				samfaktByEmail();
			}
		}finally {
			startSamfaktTimer();
		}
	}
	private void handleKvartTimer() {
		try {
			//samfaktByEmail();
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
		try {
			sxServerMainBean.doUpdateWebArtikel();
			sxServerMainBean.doUpdateLagersaldo();
		} catch (Exception e) {
			ServerUtil.log("Undantag i handleDygnsTimer: " + e.toString());
		} finally {
			startDygnsTimer();
		}
	}



	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void doUpdateWebArtikel() throws SQLException{
		Connection conSe = null;
		try {
			conSe = saljexse.getConnection();
			WebArtikelUpdater w = new WebArtikelUpdater(em, conSe);
			w.updateWArt();

		} catch (SQLException e) {
			ServerUtil.log("Undantag i doUpdateWebArtikel: " + e.toString());
			throw(e);
		} finally {
			try { conSe.close(); } catch (Exception e) {}
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void doUpdateLagersaldo() throws SQLException{
		Connection con = null;
		try {
			con = sxadm.getConnection();
			LagerCheck lagerCheck = new LagerCheck(con);
			lagerCheck.run();

		} catch (SQLException e) {
			ServerUtil.log("Undantag i doUpdateLagersaldo: " + e.toString());
			throw(e);
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}

	private void handleVeckoTimer() {
		// Uppdatera trädstrukturen
		try {
			sxServerMainBean.doUpdateWebArtikelTrad();
		} catch (Exception e) {
			ServerUtil.log("Undantag i handleVeckoTimer: " + e.toString());
		} finally {
			startVeckoTimer();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void doUpdateWebArtikelTrad() throws SQLException{
		Connection conSe = null;
		try {
			conSe = saljexse.getConnection();
			WebArtikelUpdater w = new WebArtikelUpdater(em, conSe);
			w.updateWArtGrp();
			w.updateWArtGrpLank();
			w.updateWArtKlase();
			w.updateWArtKlaseLank();

		} catch (SQLException e) {
			ServerUtil.log("Undantag i doUpdateArtikelTrad: " + e.toString());
			throw(e);
		} finally {
			try { conSe.close(); } catch (Exception e) {}
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
						try {
							t.setBearbetar(new Date());		// Om vi får en exception på detta jobb försöker vi sätt bearbetningstiden
							em.flush();								// så att det dröjer ett tag (fn 1 timme) innan försök sker igen
						} catch (Exception e2) {}				// Ignorera ev. exception eftersom funktionen inte är kritisk
						ServerUtil.log("Ett undantagsfel uppstod vid bearbetning av jobb." + t.getJobbid() + ". Försöker fortsätta med nästa " + e.toString() + " Message: " + e.getMessage()); e.printStackTrace();
					}
				}
				
				java.util.List<TableBest1> lbe1;
				//Sänd alla osända beställningar
				lbe1 = jobbHandler.getSandBestEpostList();
				for (TableBest1 be1 : lbe1) {
					try {
						sxServerMainBean.handleSandBestEpost(be1);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						ServerUtil.log("Ett undantagsfel uppstod vid bearbetning av sänd best1 epost." + be1.getBestnr() + ". Försöker fortsätta med nästa " + e.toString()); 
					}
				} 

				//Sänd påminnelser på beställning
				lbe1 = jobbHandler.getSandBestPaminEpostList();
				for (TableBest1 be1 : lbe1) {
					try {
						sxServerMainBean.handleSandBestPaminEpost(be1);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						ServerUtil.log("Ett undantagsfel uppstod vid bearbetning av sänd best1 påminnelse epost." + be1.getBestnr() + ". Försöker fortsätta med nästa. " + e.toString() +" " + e.getMessage());
					}
				} 
				
			} catch (Exception e) { 
				ServerUtil.log("Ett undantagsfel uppstod vid bearbetning av jobb: " + e.toString() + " " + e.getMessage());
				context.setRollbackOnly();
			}
			finally { 
				startJobbTimer();	
			} 
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleJobb(TableSxservjobb t) throws DocumentException, IOException, MessagingException, NamingException {
		try {
			JobbHandler jobbHandler = new JobbHandler(em,mailsxmail);		//Vi gör detta kryptiska anrop för att ny transaktion ska startas för varje jobb
			ServerUtil.log("Behandlar jobbid " + t.getJobbid() + " Uppgift: " + t.getUppgift() + " Typ: " + t.getDokumenttyp() + " Sändsätt: " + t.getSandsatt() + " Dokumentid: " + SXUtil.toStr(t.getExternidstring()) + t.getExternidint());
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
				} else {
					ServerUtil.log("Jobbid: " + t.getJobbid() +  "Dokumenttyp '" + t.getDokumenttyp() + "' är okänd. Ingen åtgärd utförd" );
					
				}


			} else {
				ServerUtil.log("Jobbid: " + t.getJobbid() +  " Uppgift '" + t.getUppgift() + "' är okänd. Ingen åtgärd utförd" );
			}
		} 
		catch (NamingException ne) {
			ServerUtil.log("Undantag i handleJobb: " + ne.toString() + " " + ne.getMessage());
			context.setRollbackOnly();
			sendAdminMail("Fel vid SxServerMainBean.handleJobb Undantag: NamingException " + ne.toString() + " " + ne.getMessage() + "<br/> Jobbid: " + t.getJobbid() + " <br/>E-postaddress: " + t.getEpost() + " <br/> Dokumenttyp: " + t.getDokumenttyp() + " <br/> Dokumentid: " +SXUtil.toStr(t.getExternidstring()) + t.getExternidint() );
			throw ne;
		}
		catch (MessagingException me) { 
			ServerUtil.log("Undantag i handleJobb: " + me.toString() + " " + me.getMessage());
			context.setRollbackOnly();
			sendAdminMail("Fel vid SxServerMainBean.handleJobb Undantag: MessagingException " + me.toString() + " " + me.getMessage() + "<br/> Jobbid: " + t.getJobbid() + " <br/>E-postaddress: " + t.getEpost() + " <br/> Dokumenttyp: " + t.getDokumenttyp() + " <br/> Dokumentid: " +SXUtil.toStr(t.getExternidstring()) + t.getExternidint() );
			throw me;
		}
		catch (DocumentException de) { 
			ServerUtil.log("Undantag i handleJobb: " + de.toString() + " " + de.getMessage());
			context.setRollbackOnly();
			sendAdminMail("Fel vid SxServerMainBean.handleJobb Undantag: DocumentException " + de.toString() + " " + de.getMessage() + "<br/> Jobbid: " + t.getJobbid() + " <br/>E-postaddress: " + t.getEpost() + " <br/> Dokumenttyp: " + t.getDokumenttyp() + " <br/> Dokumentid: " +SXUtil.toStr(t.getExternidstring()) + t.getExternidint() );
			throw de;
		}
		catch (IOException ie) { 
			ServerUtil.log("Undantag i handleJobb: " + ie.toString() + " " + ie.getMessage());
			context.setRollbackOnly();
			sendAdminMail("Fel vid SxServerMainBean.handleJobb Undantag: IOException " + ie.toString() + " " + ie.getMessage() + "<br/> Jobbid: " + t.getJobbid() + " <br/>E-postaddress: " + t.getEpost() + " <br/> Dokumenttyp: " + t.getDokumenttyp() + " <br/> Dokumentid: " +SXUtil.toStr(t.getExternidstring()) + t.getExternidint() );
			throw ie;
		}

	}

	private void sendAdminMail(String message) {
		try {
			SendMail m = new SendMail(em, mailsxmail);
			m.sendAdminSimpleMail(em, message);
		}
		catch (Exception e) { ServerUtil.log("Undantag vid sendAdminMail. Message; " + message + " " + e.toString() + " " + e.getMessage()); }
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
		startSamfaktTimer();
		startKvartTimer();
		startTimTimer();
		startDygnsTimer();
		startVeckoTimer();
	}
	
	private void startJobbTimer() {
		sxServerMainBean.startTimer(3*60*1000,"JobbTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startSamfaktTimer() {
		sxServerMainBean.startTimer(60*60*1000,"SamfaktTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startKvartTimer() {
		sxServerMainBean.startTimer(15*60*1000,"KvartTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startTimTimer() {
		sxServerMainBean.startTimer(60*60*1000,"TimTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startDygnsTimer() {
		sxServerMainBean.startTimer(24*60*60*1000,"DygnsTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
//		sxServerMainBean.startTimer(3*60*1000,"DygnsTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
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
	
	

	@RolesAllowed("admin")
	public ByteArrayOutputStream getPdfFaktura(int faktnr) throws IOException {		
		PdfFaktura sx;
		try {
			sx = new PdfFaktura(em);
			return sx.getPDF(faktnr);
		} catch (DocumentException e ) {e.printStackTrace();}
		catch (NoResultException e) {}
		return null;
	}

	public void main() {
		/*Detta är uppstartsproceduren, som skall köras en gång vid systemstart, och därefter aldrig mer
		 * Initieringen av denna metod sköts från en Listener som finns i Webb-delen och gör anropet hit */
		if (Logger.getLogger("sx").getHandlers().length < 1) {	//Har vi ingen handler, så ska vi lägga till deb.  Vi måste kolla detta så att inte flera file-handlers blir registrerade
																//Detta ifall applicationen startas om, och vi riskerar fler handlers
			try {
				Logger.getLogger("sx").addHandler(new FileHandler("%h/sxserver-%u-%g.log",1024*1024,3,true));
			} catch (Exception e) { System.out.println("**** Undantagsfel i se.saljex.sxserver.main när java.util.logger försöker skapa FileHandler." + e.toString()); }
		}
		Logger.getLogger("sx").setLevel(Level.FINER); // Om vi sätter Level.FINER, så stänger vi av nivån finest som vi använder för debuginfo

		Logger.getLogger("sx").info("se.saljex.sxserver.main startad.");
		System.out.println("Loggning sker till filen sxserver-n-n.log i ägarens hemkatalog (t.ex. /root)");
		
		startTimers();
	 }


	@RolesAllowed("admin")
	public String getHTMLStatus() {
		String ret = "<h1>Statusrapport från SXServer</h1><br/>" + new Date().getTime() + "<br>";
		ret = ret + "<h2>Timers</h2><br/>";
		ret = ret+"<table><tr><th>Timer namn</th><th>Nästa tid</th></tr>";
		Collection<Timer> c = timerService.getTimers();
		for (Timer timer : c) {
			ret = ret + "<tr><td>" + timer.getInfo() + "</td><td>" + timer.getNextTimeout().toString() + "</td></tr>";
		}
		ret = ret + "</table>";
		return ret;
	}

	private void samfaktByEmail() {
		String anvandare = ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT);
		try {
			SamfaktByEpostHandler samfak = new SamfaktByEpostHandler(sxadm, anvandare);
			while (samfak.hasNextFakturaOrder()) {
				sxServerMainBean.doSamfaktByEmail(anvandare, samfak);
			}
		} catch (SQLException se) { ServerUtil.log("Fel vid samfaktbyEMail: " + se.toString()); }

	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void doSamfaktByEmail(String anvandare, SamfaktByEpostHandler samfak) throws SQLException {
		if (samfak.nextFakturaOrder(em)) {
			FakturaHandler fakturaHandler = new FakturaHandler(em, sxadm, anvandare);
			fakturaHandler.samlingsfaktureraSandEpost(samfak.getFakturaOrder(), samfak.getOrderPaFaktura());
		}
	}

	private void checkWorder()  {
		Connection conSe = null;
		ServerUtil.log("Startar checkWorder");
		try {
			conSe = saljexse.getConnection();
			WebOrderHandler woh = new WebOrderHandler(em,conSe,ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE,SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
			ArrayList<Integer> orderList = woh.getSkickadWorderList();
			for (Integer o : orderList) {
				try {
					ArrayList<Integer> sparadeOrderArr;
					sparadeOrderArr = sxServerMainBean.saveWorder(o);
					String logStr = "Weborder " + o + " sparad som order/ordrar: ";
					for (Integer nr : sparadeOrderArr) {			//Skapa sträng med alla ordernummer som är sparade
						logStr = logStr + nr + " ";
					}
					ServerUtil.log(logStr);
				}catch (SxInfoException ie) {
					ServerUtil.log("Info undantag " + ie.getMessage() + ". Ordern sparas inte." );
				} catch (KreditSparrException ke) {
					ServerUtil.log("Kreditspärr vid Weborder " + o + ". Ordern sparas inte." );
					String mailTo = "";
					Statement st = conSe.createStatement();
					ResultSet r = st.executeQuery("select u.epost from webuser u, weborder1 o where u.loginnamn = o.loginnamn and o.wordernr = " + o);
					if (r.next()) {
						try {
							String testlage  = ServerUtil.getSXReg(em,SXConstant.SXREG_TESTLAGE, SXConstant.SXREG_TESTLAGE_DEFAULT );

							if (!"Nej".equals(testlage)) {
								mailTo = "ulf.hemma@saljex.se";
							} else {
								mailTo = r.getString(1);
							}

							SendMail m = new SendMail(em, mailsxmail);
							m.sendSimpleMail(	em,
													mailTo,
													ServerUtil.getSXReg(em,SXConstant.SXREG_WORDER_SPARRAD_ORDER_SUBJECT, SXConstant.SXREG_WORDER_SPARRAD_ORDER_SUBJECT_DEFAULT),
													ServerUtil.getSXReg(em,SXConstant.SXREG_WORDER_SPARRAD_ORDER_BODY, SXConstant.SXREG_WORDER_SPARRAD_ORDER_BODY_DEFAULT));
						} catch (Exception e) {
							ServerUtil.log("Kunde inte skicka epost om kreditspärr till " + mailTo + " Weborder " + o + ". " + e.toString());
						}
					} else {
						ServerUtil.log("Kunde inte hitta kontaktinfo för weborder " + o + " vid försök att skicka epost om kreditspärr.");
					}
					r.close();
					st.executeUpdate("update weborder1 set status='Spärrad', kreditsparr=1 where wordernr = " + o);

				} catch (SQLException se) {
					ServerUtil.log("Fel vid spara webordernr " + o + ": " + se.toString());
				}
			}
		} catch (SQLException se1) {
			ServerUtil.log("Fel " + se1.toString()); 
		}	finally {
			try { conSe.close(); } catch (Exception e) {}
			ServerUtil.log("checkWorder slutförd");
		}
	}



	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ArrayList<Integer> saveWorder(int worderNr) throws java.sql.SQLException, KreditSparrException, SxInfoException {
		// Spara angiven weborder i egen transaktion
		// returnerar array med ordernumren som webordern sparades som
		Connection conSe = null;
		ArrayList<Integer> ret;
		try {
			conSe = saljexse.getConnection();
			WebOrderHandler woh = new WebOrderHandler(em,conSe,ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE,SXConstant.SXREG_SERVERANVANDARE_DEFAULT));
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
		samfaktByEmail();
		return("Samfakt startad");
/*		Connection conSe = null;
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
		}*/
	}

	@RolesAllowed("admin")
	public ArrayList<Integer> saveSxShopOrder(int kontaktId, String kundnr, String kontaktNamn, short lagerNr, String marke) throws KreditSparrException, SxInfoException {
		//Spara angiven användares varukorg som en riktig order
		//Returnerar en lista över de 'riktiga' order somskapas (om flera som t.ex. vid direktleverans)
		//Returnerar null om ingen order registrerats (mest troligtom varukorgen var tom)
		SimpleOrderHandler sord = new SimpleOrderHandler(em, kundnr, kontaktNamn, lagerNr, "00", marke);
		Iterator i = em.createNamedQuery("TableVarukorg.findByKontaktidVK").setParameter("kontaktid", kontaktId).getResultList().iterator();
		int antalRader=0;
		while (i.hasNext()) {
			antalRader++;
			TableVarukorg v = (TableVarukorg)i.next();
			sord.addRow(v.getTableVarukorgPK().getArtnr(),v.getAntal());
		}
		if (antalRader==0) return null;	//Vi har inga rader i varukorgen. returnera null. Bra att göra retur redan här så vi inte går vidare och försöker spara
		if (sord.getOrdreg().size() < 0) return null;
		ArrayList<Integer> orderList = sord.saveAsOrder();
		if (orderList.size()>0) {
			em.createNamedQuery("TableVarukorg.deleteByKontaktidVK").setParameter("kontaktid", kontaktId).executeUpdate();
			return orderList;
		} else return null;
	}


	
	

	//Returnerar true vid error
	@RolesAllowed("admin")
	public boolean sendSimpleMail(String adress, String header, String bodytext) {
		try {
			SendMail m = new SendMail(em, mailsxmail);
			m.sendSimpleMail(	em, adress, header, bodytext);
		}
		catch (Exception e) { e.printStackTrace(); return true; }
		return false;
	}


	//Utför sparandet av SxOrder i ny transaktion eftersom det inte gåår att göra uppdateringar i emSx och emBv i samma transaktion
/*	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void overforBVOrderSaveSxOrder(BvOrder bvOrder) throws SXEntityNotFoundException , SxInfoException{
		bvOrder.saveSxOrder();
	}
*/
	// Hämtar en BV order till SX order
	//Returnerar SX ordernr
/*	public int overforBVOrder(String sxKundnr, int bvOrdernr, String bvAnvandare, String sxAnvandare, short sxLagernr) throws SXEntityNotFoundException, SxInfoException {
		BvOrder bvOrder = new BvOrder(em, embv, bvOrdernr, sxKundnr, bvAnvandare, sxAnvandare, sxLagernr );
		bvOrder.loadBvOrder();
		sxServerMainBean.overforBVOrderSaveSxOrder(bvOrder);
		bvOrder.updateBvOrder();
		return bvOrder.getSxOrdernr();
	}
*/


	//Utför sparandet av MainOrder i ny transaktion eftersom det inte gåår att göra uppdateringar i emLocal och emMain i samma transaktion
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void overforOrderSaveMainOrder(OverforOrder localOrder) throws SXEntityNotFoundException, SxInfoException {
		localOrder.saveMainOrder();
	}
	//Utför själva hanterandet i ny transaktion eftersom det inte gåår att göra uppdateringar i emLocal och emMain i samma transaktion
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void doOverforOrder(OverforOrder localOrder) throws SXEntityNotFoundException, SxInfoException {
		localOrder.loadLocalOrder();
		sxServerMainBean.overforOrderSaveMainOrder(localOrder);
		localOrder.updateLocalOrder();
	}



	// Hämtar en Local order till Main order
	//Returnerar Main ordernr
	// Själva hanteringen sker i annat bean-anrop (i ny transaktion)för att det inte går att blanda emLocal och emMain i samma transaktion
	public int overforOrder(int localOrdernr, String localAnvandare, short mainLagernr) throws SXEntityNotFoundException, SxInfoException {
		String mainKundnr = ServerUtil.getSXReg(em, SXConstant.SXREG_OVERFOR_ORDER_KUNDNR, SXConstant.SXREG_OVERFOR_ORDER_KUNDNR_DEFAULT);
		String mainAnvandare = ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT);
		String allowUseBestnrString = ServerUtil.getSXReg(em, SXConstant.SXREG_OVERFOR_ORDER_ALLOWUSEBESTNR, SXConstant.SXREG_OVERFOR_ORDER_ALLOWUSEBESTNR_DEFAULT);
		boolean allowUseBestnr = "true".equals(allowUseBestnrString);
		OverforOrder localOrder = new OverforOrder(emMain, em, localOrdernr, mainKundnr, localAnvandare, mainAnvandare, mainLagernr, allowUseBestnr );
		sxServerMainBean.doOverforOrder(localOrder);
		return localOrder.getMainOrdernr();
	}


	public String getSXReg(String nyckel, String defaultVarde) {
		return ServerUtil.getSXReg(em, nyckel, defaultVarde);
	}






	public int faktureraOrder(int ordernr) throws SxOrderLastException {
		return faktureraOrderMedAnvandare(ordernr, "00");
	}

	public int faktureraOrderMedAnvandare(int ordernr, String anvandare) throws SxOrderLastException {
		FakturaHandler fh = new FakturaHandler(em, sxadm, anvandare);
		return fh.faktureraOrder(ordernr);
	}

/*	public int faktureraBvOrder(int ordernr) throws SxOrderLastException {
		return faktureraBvOrderMedAnvandare(ordernr, "00");
	}

	public int faktureraBvOrderMedAnvandare(int ordernr, String anvandare) throws SxOrderLastException {
		FakturaHandler fh = new FakturaHandler(embv, bvadm, anvandare);
		return fh.faktureraOrder(ordernr);
	}
*/
	public ByteArrayOutputStream getTestPdf() throws IOException {
		PdfTest sx;
		try {
			sx = new PdfTest();
			return sx.getPDF();
		} catch (DocumentException e ) {e.printStackTrace();}
		catch (NoResultException e) {}
		return null;
	}

	public void sendOffertEpost(String anvandare, String epost, int id) throws SXEntityNotFoundException{
		SxServJobbHandler.sendOffertEpost(em, anvandare, id, epost);
	}
	public void sendFakturaEpost(String anvandare, String epost, int id) throws SXEntityNotFoundException{
		SxServJobbHandler.sendFakturaEpost(em, anvandare, id, epost);
	}

	
	public String getHtmlOffert (int offertnr, boolean inkMoms, String logoUrl) throws SXEntityNotFoundException {
		return HtmlOffertHandler.getHtmlOffert(em, offertnr, inkMoms, logoUrl);
		
	}
	public String getHtmlOffert (int offertnr, boolean inkMoms, String logoUrl, String headerHTML, String meddelandeHTML, String footerHTML) throws SXEntityNotFoundException {
		return HtmlOffertHandler.getHtmlOffert(em, offertnr, inkMoms, logoUrl, headerHTML, meddelandeHTML, footerHTML);
		
	}

	private OrderHandler prepareOrderHandler(String anvandare, TableOrder1 copyFromTableOrder1, ArrayList<OrderHandlerRad> orderRader) throws SXEntityNotFoundException{
		boolean radHittad = false;
		if (orderRader!=null) {
			for (OrderHandlerRad ohr : orderRader) {
				if (!SXUtil.isEmpty(ohr.artnr) || !SXUtil.isEmpty(ohr.text)) {
					radHittad=true;
					break;
				}
			}
		}
		if (!radHittad || orderRader== null || orderRader.size()<1) throw new SXEntityNotFoundException("Det finns inga artikelrader.");
		OrderHandler ord = new OrderHandler(em, anvandare, copyFromTableOrder1);
		
		for (OrderHandlerRad rad : orderRader) {
			ord.addRowNoArtikelLookup(rad.artnr, rad.namn, rad.best, rad.enh, rad.pris, rad.rab, rad.levnr, rad.konto, rad.netto, 0, 0, 0, 0, rad.stjAutobestall, rad.stjFinnsILager, rad.text);
		}		
		return ord;
	}
	
	@Override
	public int saveOrder(String anvandare, TableOrder1 copyFromTableOrder1, ArrayList<OrderHandlerRad> orderRader) throws SXEntityNotFoundException, KreditSparrException, SxInfoException{
		OrderHandler oh = prepareOrderHandler(anvandare, copyFromTableOrder1, orderRader);
		oh.checkKreditvardighetOrThrow();
		return oh.persistOrder();
	}

	@Override
	public int saveOffert(String anvandare, TableOrder1 copyFromTableOrder1, ArrayList<OrderHandlerRad> orderRader) throws SXEntityNotFoundException{
		return prepareOrderHandler(anvandare, copyFromTableOrder1, orderRader).persistOffert();
	}


}
