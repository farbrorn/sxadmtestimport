package se.saljex.sxserver;

import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.annotation.Resource;
import javax.annotation.PostConstruct;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import com.lowagie.text.*;//.text.Document;
import com.lowagie.text.pdf.*;
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
	String anvandare = "00";	//Användanamnet för sxserv
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
		
		System.out.println("************************'handleTimer startad");
		Integer maxid;
		TableSxservjobb j;
		boolean ss;
		

		if (timer.getInfo().equals("JobbTimer")) {
			JobbHandler jobbHandler;
			LagerCheck	lagerCheck;
			String q;
			int r = 0;

			try {
				jobbHandler = new JobbHandler(em,mailsxmail);
//				jobbHandler.checkSandBestallningEpost();
//				lagerCheck = new LagerCheck(sxadm);
//				lagerCheck.run();
//				SXUtil.sendAdmMessage(em, "test1", "test2");
				
				//Behandlar jobbkö från lista
				java.util.List<TableSxservjobb> ljob = jobbHandler.getJobbList();
				for (TableSxservjobb t : ljob) {
					try {
						sxServerMainBean.handleJobb(t);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						SXUtil.log("Ett undantagsfel uppstod vid bearbetning av jobb." + t.getJobbid() + ". Försöker fortsätta med nästa " + e.toString()); 
					}
				}
				
				//Behandlar best1 kö från lista
				java.util.List<TableBest1> lbe1 = jobbHandler.getSandBestEpostList();
				for (TableBest1 be1 : lbe1) {
					try {
						sxServerMainBean.handleSandBestEpost(be1);		//aNROPAS SÅ HÄR FÖR ATT STARTA NY TRANSAKTION
					} catch (Exception e) {
						SXUtil.log("Ett undantagsfel uppstod vid bearbetning av sänd best1 epost." + be1.getBestnr() + ". Försöker fortsätta med nästa " + e.toString()); 
					}
				} 
				
			} catch (Exception e) { 
				System.out.println("Ett undantagsfel uppstod vid bearbetning av jobb: " + e.toString()); 
				context.setRollbackOnly();
			}
			finally { 
				//startJobbTimer();	
			} 
		}
		 
	}

	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleJobb(TableSxservjobb t) throws  DocumentException, IOException, NamingException, MessagingException {
		JobbHandler jobbHandler = new JobbHandler(em,mailsxmail);		//Vi gör detta kryptiska anrop för att ny transaktion ska startas för varje jobb
			System.out.println("Behandlar jobbid " + t.getJobbid());
			if (t.getUppgift().equals("sänd")) {
				if (t.getDokumenttyp().equals("faktura")) {
					if (t.getSandsatt().equals("epost")) {
						jobbHandler.handleSandFakturaEpost(t); 
					}
				} else if (t.getDokumenttyp().equals("best")) {
					if (t.getSandsatt().equals("epost")) {
						jobbHandler.handleSandBestEpost(t); 
					}				
				}
			}
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleSandBestEpost(TableBest1 be1) throws  DocumentException, IOException, NamingException, MessagingException {
		JobbHandler jobbHandler = new JobbHandler(em,mailsxmail);		//Vi gör detta kryptiska anrop för att ny transaktion ska startas för varje jobb
		jobbHandler.sandBestEpost(be1);
	}
	
	
	
	
	@PostConstruct
	public void sxinit()  {
	}

	public void startTimers() {
		startJobbTimer();
		startKvartTimer();
	}
	
	private void startJobbTimer() {
		sxServerMainBean.startTimer(7000,"JobbTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
	}
	private void startKvartTimer() {
		sxServerMainBean.startTimer(63000,"KvartTimer");	//Måste startas som EJB-anrop för att new transaction skall funka
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

/*		SendMail m = new SendMail(mailsxmail, SXUtil.getSXReg(con,"SxServSMTPUser"), SXUtil.getSXReg(con,"SxServSMTPPassword"));
		try {
			m.sendMail(new InternetAddress(SXUtil.getSXReg(con,"SxServMailFakturaFromAddress"),SXUtil.getSXReg(con,"SxServMailFakturaFromName")),
							"ulf.hemma@gmail.com", SXUtil.getSXReg(con,"SxServMailFakturaSubjectPrefix")
							+ " " + faktnr + " " + SXUtil.getSXReg(con,"SxServMailFakturaSubjectSuffix")
z							, SXUtil.getSXReg(con,"SxServMailFakturaBodyPrefix") + SXUtil.getSXReg(con,"SxServMailFakturaBodySuffix")
							, sx.getPDF(faktnr)) ; 
		} catch (Exception e) { throw new SQLException("Exception från mail: " + e.toString());}
*/		
		
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

		try {
			Connection con = saljexse.getConnection();
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select * from weborder1 order by wordernr");
			ret = ret + "<table>";
			while (r.next()) {
				ret = ret + "<tr><td>" + r.getString(1) + "</td><td>" + r.getString(2) + "</td><td>" + r.getString(3) + "</td><td>" + r.getString(4) + "</td><td>" + r.getString(5) + "</td><td>" + r.getString(6) + "</td><td>" + r.getString(7) + "</td><td>" + r.getString(8) + "</td><td>" + r.getString(9);
				ret = ret + "</td></tr>";
			}
			ret = ret + "</table>";
			
		} catch (Exception e) { ret = ret + e.toString(); }
		return ret;
	}

	private void checkWorder()  {
		try {
			WebOrderHandler woh = new WebOrderHandler(em,saljexse.getConnection(),anvandare);
			ArrayList<Integer> orderList = woh.getSkickadWorderList();
			for (Integer o : orderList) {
				try {
					if (sxServerMainBean.saveWorder(o) == null) {
						SXUtil.log("Kunde inte spara weborder " + o + " Av okänd anledning.");
					} else {
						SXUtil.log("Weborder " + o + " sparad som order!");
					}

				} catch (SQLException se) {
					SXUtil.log("Fel vid spara order: " + se.toString());
				}
			}
		} catch (SQLException se1) {
			SXUtil.log("Fel " + se1.toString()); 
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ArrayList<Integer> saveWorder(int worderNr) throws java.sql.SQLException {
		// Spara angiven weborder i egen transaktion
		// returnerar array med ordernumren som webordern sparades som
		WebOrderHandler woh = new WebOrderHandler(em,saljexse.getConnection(),anvandare);
		ArrayList<Integer> ret = woh.loadWorderAndSaveSkickadAsOrder(worderNr);
		if (ret == null) { context.setRollbackOnly(); }
		return ret;
	}
	
	
	public String tester(String testTyp) {
			System.out.println("tester - 0");
		String ret = "";
		
		if (testTyp.equals("OrderHandler")) {
			System.out.println("tester OrderHandler startad");
			ret = ret + "Skapar OrderHandler instans<br>";
			OrderHandler oh = new OrderHandler(em,"0555",(short)0,"tes");
			oh.addRow("EA153", 1.0);
			oh.addRow("Q006", 2.0);
			oh.addRow("Q001", 2.0);
			oh.addRow("Q055", 2.0);
			oh.addRow("EG1021", 2.0);
			oh.addRow("99SB210", 2.0);
			
			ret = ret + "hämtar tillbaka rader<br>";
			System.out.println("tester OrderHandler rader adderade ");
			
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Originalorder");
			oh.sortLagerPlatsArtNr();
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Efter sortering Lagerplats+artnr");
			oh.sortLevNr();
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Efter sortering Lev");
			
			oh.setLagerNr((short)1);
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Efter byte till lager 1");
			

		} else if (testTyp.equals("SimpleOrderHandler"))	 {
			System.out.println("tester SimpleOrderHandler - 0");
			ret = ret + "Skapar SimpleOrderHandler instans<br>";
			SimpleOrderHandler soh = new SimpleOrderHandler(em,"0555",(short)0,"tes",1,"märket");
			soh.addRow("EA153", 1.0);
			soh.addRow("Q006", 2.0);
			soh.addRow("Q001", 2.0);
			soh.addRow("Q055", 2.0);
			soh.addRow("EG1021", 2.0);
			soh.addRow("99SB210", 2.0);
			soh.addRow("EA7232", 2.0);
			soh.addRow("EA6901", 2.0);
			soh.addRow("WA1008", 2.0);
			System.out.println("tester SimpleOrderHandler - 1");
			soh.addRow("felaktig", 2.0);
			System.out.println("tester SimpleOrderHandler - 2");
			ret = ret + testerPrintOrder(soh.getTableOrder1(),soh.getOrdreg(),"Original SimpleOrder");
			System.out.println("tester SimpleOrderHandler - 3");

			OrderHandler oh  = soh.testSplitOrderSetUp();
			System.out.println("tester SimpleOrderHandler - 4");
			
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Original Order med priser (efter testsplitordersetup) som ska splittas upp");
			System.out.println("tester SimpleOrderHandler - 5");
			
			OrderHandler splitoh;
			int cn = 0; 
			while (( splitoh = soh.getNextSplitOrder(oh)) != null ) {
				
				cn++;
				ret = ret + testerPrintOrder(splitoh.getTableOrder1(),splitoh.getOrdreg(),"splitad delOrder " + cn);
				
			}
			ret = ret + "<br> totalt antal spliorder = " + cn + "<br>";
			
		} else { ret = "<br>Ogigiltig test: " + testTyp + "<br>"; }
		return ret;
		
	}

	private String testerPrintOrder(TableOrder1 t, ArrayList<OrderHandlerRad> or, String rubrik) {
		String ret;
		ret = "<br><b>" + rubrik + "</b><br>" + "<table><tr>"
			+ "<td>Ordernr</td><td>" + t.getOrdernr() + "</td><td>Datum</td><td>" + t.getDatum() + "</td><td>KundNr</td><td>" + t.getKundnr() + "</td><td>Lagernr</td><td>" + t.getLagernr() + "</td></tr>"
			+ "<td>Adress</td><td>" + t.getAdr1() + "</td><td> </td><td>" + t.getAdr2() + "</td><td> </td><td>" + t.getAdr3() + "</td></tr>"
			+ "<td>Fraktbolag</td><td>" + t.getFraktbolag() + "</td><td>FraktKundnr</td><td>" + t.getFraktkundnr() + "</td><td>Linjenr</td><td>" + t.getLinjenr1() + "</td></tr>"
			+ "<td>Levadress</td><td>" + t.getLevadr1() + "</td><td> </td><td>" + t.getLevadr2() + "</td><td> </td><td>" + t.getLevadr3() + "</td></tr>"
			+ "<td>Märke</td><td colspan)\"5\">" + t.getMarke() +  "</td></tr>"
			+ "<td>Säljare</td><td>" + t.getSaljare() + "</td><td>Status</td><td>" + t.getStatus() + "</td><td>AnnanLevAdr</td><td>" + t.getAnnanlevadress() + "</td></tr>"
			+ "<td>Direktlevnr</td><td>" + t.getDirektlevnr() + "</td><td>Moms</td><td>" + t.getMoms() + "</td><td>Webordernr</td><td>" + t.getWordernr() + "</td></tr>"
			+ "</table>"
			
			+ "<table><tr><th>Rad</th><th>Artikelnr</th><th>Namn</th><th>Best</th><th>Lev</th><th>Enhet</th><th>Pris</th><th>Rab</th><th>Summa</th><th>Netto</th>" + 
					"<th>Stjid</th><th>Levdat</th><th>Lev</th><th>ilager</th><th>iorder</th><th>beställda</th><th>Lagerplats</th></tr>";
		for (OrderHandlerRad ohr : or) { 
			ret = ret + "<tr><td>" + ohr.pos + "</td><td>" + ohr.artnr + "</td><td>" + ohr.namn + "</td><td>" + ohr.best + "</td><td> " + ohr.lev + "</td><td>"
				+ ohr.enh + "</td><td>" + ohr.pris + "</td><td>" + ohr.rab + "</td><td>" + ohr.summa
				+ "</td><td>" + ohr.netto + "</td><td>" + ohr.stjid + "</td><td>" + ohr.levdat + "</td><td>" + ohr.levnr
				+ "</td><td>" + ohr.artIlager + "</td><td>" + ohr.artIorder + "</td><td>" + ohr.artBest + "</td><td>" + ohr.artLagerplats + "</td></tr>";
		}
		ret = ret + "</table><br>";
		return ret;
			
	}




	

 
}
