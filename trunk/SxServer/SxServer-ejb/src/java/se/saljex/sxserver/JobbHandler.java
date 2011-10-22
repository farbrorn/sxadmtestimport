/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxserver.tables.TableLev;
import se.saljex.sxserver.tables.TableBesthand;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableBest2;
import se.saljex.sxserver.tables.TableBest1;
import se.saljex.sxserver.tables.TableSxservjobb;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Ulf
 */
public class JobbHandler {
	private Session mailsxmail;
    private EntityManager em;  

	
    public JobbHandler(EntityManager e, Session m)  {
		em = e;
		mailsxmail  = m;
		//s = new SqlTableSxServJobb(con);
	}
	
	public void markSlutfort(int id)  {
		Query q = em.createQuery("update TableSxservjobb s set s.slutford = ?1 where s.jobbid = " + id);
		q.setParameter(1, new Timestamp(new Date().getTime())); 
		q.executeUpdate();
		//s.setTimestamp(1, new Timestamp(new Date().getTime()));
	}
	
	public int markBearbetar(int id, Date t)  {
		Query q;
		if (t == null) {
			q = em.createQuery("update TableSxservjobb s set s.bearbetar = ?1, s.antalforsok = s.antalforsok+1 where s.jobbid = " + id + " and s.bearbetar is null");
		} else {
			q = em.createQuery("update TableSxservjobb s set s.bearbetar = ?1, s.antalforsok = s.antalforsok+1 where s.jobbid = " + id + " and s.bearbetar = ?2");
			q.setParameter(2, new Timestamp(t.getTime())); 
		}
		q.setParameter(1, new Timestamp(new Date().getTime())); 
		return q.executeUpdate();
		//s.setTimestamp(1, new Timestamp(new Date().getTime()));
	}

	public List<TableSxservjobb> getJobbList() throws  DocumentException, IOException, NamingException, MessagingException {
		Query q = em.createQuery("SELECT t FROM TableSxservjobb t WHERE t.slutford is null and (t.bearbetar < :bearbetar or t.bearbetar is null) order by t.jobbid");
		// Ta fram 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, -1);	//Minska en timme
		q.setParameter("bearbetar", new Timestamp(calendar.getTime().getTime()));
		//List<TableSxservjobb> l = q.getResultList();
		return q.getResultList();
	/*	
		for (TableSxservjobb t : l) {
			System.out.println("Behandlar jobbid " + t.getJobbid());
			if (t.getUppgift().equals("sänd")) {
				if (t.getDokumenttyp().equals("faktura")) {
					if (t.getSandsatt().equals("epost")) {
						newTransactionJobbHandler = new JobbHandler(em,mailsxmail);		//Skapa en ny jobbhandler för att köra separata transactioner
						newTransactionJobbHandler.handleSandFakturaEpost(t);
					}
				} else if (t.getDokumenttyp().equals("best")) {
					if (t.getSandsatt().equals("epost")) {
						newTransactionJobbHandler = new JobbHandler(em,mailsxmail);		//Skapa en ny jobbhandler för att köra separata transactioner
						newTransactionJobbHandler.handleSandBestEpost(t);
					}				
				}
			}
		}
	 * */
	}

	public List<TableBest1> getSandBestEpostList() throws DocumentException, IOException, NamingException, MessagingException {
		Query q;
		q = em.createNamedQuery("TableBest1.findAllSendEpost");
		return q.getResultList();
	}

	public List<TableBest1> getSandBestPaminEpostList() throws DocumentException, IOException, NamingException, MessagingException {
		Query q;
		q = em.createNamedQuery("TableBest1.findAllSendPaminEpost");
		q.setParameter("pamindat", SXUtil.addDate(new Date(), -SXConstant.BEST_PAMIN_DAGAR_MELLAN));
		Date datum = new Date();
		q.setParameter("sanddat", SXUtil.addDate(datum,-SXConstant.BEST_PAMIN_DAGAR_EFTER_SAND));

		
		return q.getResultList();
	}
	
	
	public void handleSandFakturaEpost(TableSxservjobb t) throws  DocumentException, IOException, NamingException, MessagingException {
		if (markBearbetar(t.getJobbid(), t.getBearbetar()) > 0) {	// Ingen annan har låst denna för bearbetning
			String epost;
			if (!ServerUtil.getSXReg(em,"SxServTestlage","Ja" ).equals("Nej")) {
				epost = SXConstant.EMAIL_VID_TESTLAGE;
			} else {
				epost=t.getEpost();
			}

			sendFakturaEpost(t.getExternidint(), epost);
			ServerUtil.log("Faktura " + t.getExternidint() +  " skickad e-post");
			markSlutfort(t.getJobbid());
		}
	}

	public void handleSandBestEpost(TableSxservjobb t) throws  DocumentException, IOException, NamingException, MessagingException {
		TableBest1	be1;
		if (markBearbetar(t.getJobbid(), t.getBearbetar()) > 0) {	// Ingen annan har låst denna för bearbetning
			be1 = em.find(TableBest1.class, t.getExternidint());
			if (be1 == null) {
				ServerUtil.log("Beställning " + t.getExternidint() + " hittades inte och kan inte skickas som epost");
			} else {
				sandBestEpost(be1);
			}
			markSlutfort(t.getJobbid());		//Markera som slutförd även om det inte fanns något att skicka
		}
	}
	
	
	private void sendFakturaEpost(int faktnr, String epost) throws DocumentException, IOException, NamingException, MessagingException
	{
		PdfFaktura sx;
      sx = new PdfFaktura(em);
		String bodyHtml = ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFAKTURABODYPREFIX, SXConstant.SXREG_SXSERVMAILFAKTURABODYSUFFIX_DEFAULT)
								+ ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFAKTURABODYSUFFIX, SXConstant.SXREG_SXSERVMAILFAKTURABODYSUFFIX_DEFAULT);
		
		SendMail m = new SendMail(em, mailsxmail);
		m.sendMailTextHtmlPdf(
							new InternetAddress(ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFAKTURAFROMADRESS, SXConstant.SXREG_SXSERVMAILFAKTURAFROMADRESS_DEFAULT),
								ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFAKTURAFROMNAME, SXConstant.SXREG_SXSERVMAILFAKTURAFROMNAME_DEFAULT)),
							epost, 
							ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFAKTURASUBJEKTPREFIX, SXConstant.SXREG_SXSERVMAILFAKTURASUBJEKTPREFIX_DEFAULT)
								+ " " + faktnr + " "
								+ ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFAKTURASUBJEKTSUFFIX, SXConstant.SXREG_SXSERVMAILFAKTURASUBJEKTSUFFIX_DEFAULT),
							bodyHtml,
							bodyHtml, 
							sx.getPDF(faktnr),
							"faktura" + faktnr + ".pdf") ;
		sx = null;
	}

	
	public void handleSandOffertEpost(TableSxservjobb t) throws  DocumentException, IOException, NamingException, MessagingException {
		if (markBearbetar(t.getJobbid(), t.getBearbetar()) > 0) {	// Ingen annan har låst denna för bearbetning
			String epost;
			if (!ServerUtil.getSXReg(em,"SxServTestlage","Ja" ).equals("Nej")) {
				epost = SXConstant.EMAIL_VID_TESTLAGE;
			} else {
				epost=t.getEpost();
			}
			sendOffertEpost(t.getExternidint(), epost);
			ServerUtil.log("Offert " + t.getExternidint() +  " skickad e-post");
			markSlutfort(t.getJobbid());
		}
	}
	
	private void sendOffertEpost(int offertnr, String epost) throws DocumentException, IOException, NamingException, MessagingException
	{
		PdfOffert sx;
      sx = new PdfOffert(em);
		String bodyHtml = ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILOFFERTBODYPREFIX, SXConstant.SXREG_SXSERVMAILOFFERTBODYSUFFIX_DEFAULT)
								+ ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILOFFERTBODYSUFFIX, SXConstant.SXREG_SXSERVMAILOFFERTBODYSUFFIX_DEFAULT);

		SendMail m = new SendMail(em, mailsxmail);
		m.sendMailTextHtmlPdf(
							new InternetAddress(ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILOFFERTFROMADRESS, SXConstant.SXREG_SXSERVMAILOFFERTFROMADRESS_DEFAULT),
								ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILOFFERTFROMNAME, SXConstant.SXREG_SXSERVMAILOFFERTFROMNAME_DEFAULT)),
							epost, 
							ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILOFFERTSUBJEKTPREFIX, SXConstant.SXREG_SXSERVMAILOFFERTSUBJEKTPREFIX_DEFAULT)
								+ " " + offertnr + " "
								+ ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILOFFERTSUBJEKTSUFFIX, SXConstant.SXREG_SXSERVMAILOFFERTSUBJEKTSUFFIX_DEFAULT),
							bodyHtml,
							bodyHtml,
							sx.getPDF(offertnr),
							"offert" + offertnr + ".pdf") ;
		sx = null;
	}
	
	
	
	
	
	public void sandBestEpost(TableBest1 be1) throws  DocumentException, IOException, NamingException, MessagingException {
		
		String ht;
		TableFuppg fup = ServerUtil.getFuppg(em);
		TableLev lev;
		lev = em.find(TableLev.class, be1.getLevnr());
		if (lev == null) {
			ServerUtil.log("Leverantör " + be1.getLevnr() + " på beställning " + be1.getBestnr() + " finns inte i registret. Kan inte skicka beställning epost.");
			return;
		}
		
		String burl  = ServerUtil.getSXReg(em,"SxServBestURL","http://www.saljex.se/inkop/" );
		String burltext  = ServerUtil.getSXReg(em, "SxServBestURLText", "Klicka här för att bekräfta mottagande!" );
		String testlage  = ServerUtil.getSXReg(em,"SxServTestlage","Ja" );

		String epost;

		if (!testlage.equals("Nej")) {
			epost = SXConstant.EMAIL_VID_TESTLAGE;
		} else {
			epost = lev.getEmailorder1();
			if (!SXUtil.toStr(lev.getEmailorder2()).isEmpty()) { epost = epost + "," + lev.getEmailorder2(); }
		}

		
		ht =	"<table border=\"1\" cellspacing=\"0\" width=\"100%\">" +
				"<tr><th>Beställning till<br>Purchase order to</th><th colspan=\"3\">" + SXUtil.toHtml(be1.getLevnamn()) + "</th></tr>" +
				"<tr><td><font size=\"1\">Beställare / Buyer</font></td><td>" + SXUtil.toHtml(fup.getNamn()) + "</td><td><font size=\"1\">KundNr / Customer#</font></td><td>" + SXUtil.toHtml(lev.getKnummer()) + "</td></tr>" +
				"<tr><td rowspan=\"4\"><font size=\"1\">Lev.adress<br />Delivery address</font></td><td rowspan=\"4\">" + SXUtil.toHtml(be1.getLevadr0()) + "<br />" + SXUtil.toHtml(be1.getLevadr1()) + "<br />" + SXUtil.toHtml(be1.getLevadr2()) + "<br />" + SXUtil.toHtml(be1.getLevadr3()) +
				"</td><td><font size=\"1\">Datum / Date</font></td><td>" + SXUtil.getFormatDate(be1.getDatum()) + "</td></tr>" +
				"<tr><td><font size=\"1\">Beställningsnr / Order#</font></td><td>" + be1.getBestnr() + "</td></tr>" +
				"<tr><td><font size=\"1\">Säkerhetskod / Safety code</font></td><td>" + be1.getSakerhetskod() + "</td></tr>" +
				"<tr><td><font size=\"1\">Märke</font></td><td>" + SXUtil.toHtml(be1.getMarke()) + "</td></tr>" +
				"<tr><td><font size=\"1\">Leveranstid / Delivery date</font></td><td>" + SXUtil.toHtml(be1.getLeverans()) + "</td><td>&nbsp;</td><td>&nbsp;</td></tr>" +
				"<tr><td><font size=\"1\">Vår ref. / Our contact</font></td><td>" + SXUtil.toHtml(be1.getVarRef()) + "</td><td><font size=\"1\">Er ref. / Your contact</font></td><td>" + SXUtil.toHtml(be1.getErRef()) + "</td></tr>" +
				"<tr><td colspan=\"4\"><b>Viktigt / Important</b><br /><a href=\"" + burl + "?bnr=" + be1.getBestnr() + "&skd=" + be1.getSakerhetskod() + "\">" + SXUtil.toHtml(burltext) + "</a></td></table>" +
				"<br /><br />" +
				"<table border=\"1\" cellspacing=\"0\" width=\"100%\"><tr><th>ArtNr<br />Our code</th><th>BestNr<br />Your code</th><th>Benämning<br />Item</th><th>Antal<br />Quantity</th><th>Enhet<br />Unit</th></tr>";

		Query qbe2 = em.createNamedQuery("TableBest2.findByBestnr");
		qbe2.setParameter("bestnr", be1.getBestnr());
		List<TableBest2> lbe2 =  qbe2.getResultList();
		for (TableBest2 be2 : lbe2) {
			ht = ht + "<tr><td>" + be2.getArtnr() + "</td><td>" + be2.getBartnr() + "</td><td>" + be2.getArtnamn() + "</td><td align=\"right\">" + SXUtil.getFormatNumber(be2.getBest(),2) + "</td><td>" + be2.getEnh() + "</d></tr>";
		}

		if(!be1.getMeddelande().isEmpty()) {
			ht = ht + "<tr><td colspan=\"5\">" + be1.getMeddelande() + "</td></tr>";
		}
		
		ht = ht + "</table><br/><br/>Vid avvikande leveransadress ber vi Er kontrollera riktigheten<br/><br/>Vänligen ange alltid beställningsnummer på följesedlar och fakturor.";
		boolean err = false;
		
		try {
			SendMail m = new SendMail(em,mailsxmail);
			
			m.sendMailTextHtml(new InternetAddress(ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILBESTFROMADRESS,SXConstant.SXREG_SXSERVMAILBESTFROMADRESS_DEFAULT),ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILBESTFROMNAME,SXConstant.SXREG_SXSERVMAILBESTFROMNAME_DEFAULT)),
							epost,"Beställning " + be1.getBestnr() + " från " + fup.getNamn()
							, "Beställningen är i HTML-format. Din e-postläsare verkar inte stödja HTML, och vi ber dig kontakta oss." , ht);
		} catch (Exception e) {
			err = true;
			ServerUtil.log("Beställning " + be1.getBestnr() + " till " + be1.getLevnr() + ", epostadress " + epost + " kunde inte skickas epost. " + e.toString());
			em.persist(new TableBesthand(be1.getBestnr(),ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT), "E-Post sändning misslyckad",0));
			be1.setSxservsandforsok((short)(be1.getSxservsandforsok() + 1));
			if (be1.getSxservsandforsok() > 4 ) {
				ServerUtil.sendMessage(em, "Beställning " + be1.getBestnr() + " till " + be1.getLevnr() + " kunde inte skickas epost.",
									"Skriv ut en kopia och faxa!", be1.getVarRef());
				ServerUtil.log("Beställning " + be1.getBestnr() + " till " + be1.getLevnr() + " kunde inte skickas epost. Max antal försök uppnått, flaggar som fel " + e.toString());
				be1.setStatus("Fel");
				em.persist(new TableBesthand(be1.getBestnr(),ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT), "E-Post sändning misslyckad - flaggar som fel",0));
				em.flush();
			}
		}
		if (!err) {
			ServerUtil.log("Beställning"  + be1.getBestnr() + " till " + be1.getLevnr() + " skickad!");
			try {
				be1.setStatus("Skickad");
				be1.setSanddat(new Date());
				em.persist(new TableBesthand(be1.getBestnr(),ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT),"Sänd E-Post",0));
				em.flush();
			} catch (Exception e) {
				ServerUtil.log("Fel vid beställning " + be1.getBestnr() + ". E-post skickad, men kunde inte uppdatera status/händelse. " + e.toString());
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void sandBestPaminEpost(TableBest1 be1) throws  DocumentException, IOException, NamingException, MessagingException {
		
		String ht;
		TableFuppg fup = ServerUtil.getFuppg(em);
		TableLev lev;
		lev = em.find(TableLev.class, be1.getLevnr());
		if (lev == null) {
			ServerUtil.log("Leverantör " + be1.getLevnr() + " på beställning " + be1.getBestnr() + " finns inte i registret. Kan inte skicka beställning epost.");
			return;
		}
		
		String burl  = ServerUtil.getSXReg(em,"SxServBestURL","http://www.saljex.se/inkop/" );
		String burltext  = ServerUtil.getSXReg(em, "SxServBestURLText", "Klicka här för att bekräfta mottagande!" );
		String testlage  = ServerUtil.getSXReg(em,"SxServTestlage","Ja" );

		String epost;

		if (!testlage.equals("Nej")) {
			epost = SXConstant.EMAIL_VID_TESTLAGE;
		} else {
			epost = lev.getEmailorder1();
			if (!SXUtil.toStr(lev.getEmailorder2()).isEmpty()) { epost = epost + "," + lev.getEmailorder2(); }
		}

		
		ht =	"<b>Påminnelse / Reminder<br/></b>Vi har inte mottagit kvittens på vår beställning. <br/>Var vänlig klcka på längen nedan för att kvittera. (Ursprunglig beställning visas i ett nytt fönster.)<br/>" +
				"We haven´t received confirmation of reception on our purchase order<br/>Please klick the link below to confirm reception.(Original order will be displayed in a new window.)<br/>" +
				"<table border=\"1\" cellspacing=\"0\" width=\"100%\">" +
				"<tr><th>Beställning till<br>Purchase order to</th><th colspan=\"3\">" + be1.getLevnamn() + "</th></tr>" +
				"<tr><td><font size=\"1\">Beställare / Buyer</font></td><td>" + fup.getNamn() + "</td><td><font size=\"1\">KundNr / Customer#</font></td><td>" + lev.getKnummer() + "</td></tr>" +
				"<tr><td rowspan=\"4\"><font size=\"1\">Lev.adress<br />Delivery address</font></td><td rowspan=\"4\">" + be1.getLevadr0() + "<br />" + be1.getLevadr1() + "<br />" + be1.getLevadr2() + "<br />" + be1.getLevadr3() +
				"</td><td><font size=\"1\">Datum / Date</font></td><td>" + SXUtil.getFormatDate(be1.getDatum()) + "</td></tr>" +
				"<tr><td><font size=\"1\">Beställningsnr / Order#</font></td><td>" + be1.getBestnr() + "</td></tr>" +
				"<tr><td><font size=\"1\">Säkerhetskod / Safety code</font></td><td>" + be1.getSakerhetskod() + "</td></tr>" +
				"<tr><td><font size=\"1\">Märke</font></td><td>" + be1.getMarke() + "</td></tr>" +
				"<tr><td><font size=\"1\">Leveranstid / Delivery date</font></td><td>" + be1.getLeverans() + "</td><td>&nbsp;</td><td>&nbsp;</td></tr>" +
				"<tr><td><font size=\"1\">Vår ref. / Our contact</font></td><td>" + be1.getVarRef() + "</td><td><font size=\"1\">Er ref. / Your contact</font></td><td>" + be1.getErRef() + "</td></tr>" +
				"<tr><td colspan=\"4\"><b>Viktigt / Important</b><br /><a href=\"" + burl + "?bnr=" + be1.getBestnr() + "&skd=" + be1.getSakerhetskod() + "\">" + burltext + "</a></td></table>" +
				"<br /><br />";

		boolean err = false;
		
		try {
			SendMail m = new SendMail(em, mailsxmail);
			
			m.sendMailTextHtml(new InternetAddress(ServerUtil.getSXReg(em,"SxServMailBestFromAddress","inkop@saljex.se"),ServerUtil.getSXReg(em,"SxServMailBestFromName","Säljex inköp")),
							epost,"Påminnelse om kvittens på beställning " + be1.getBestnr() + " från " + fup.getNamn()
							, "Meddelandet är i HTML-format. Din e-postläsare verkar inte stödja HTML, och vi ber dig kontakta oss." , ht);
		} catch (Exception e) {
			err = true;
			ServerUtil.log("Påminnelse på beställning " + be1.getBestnr() + " till " + be1.getLevnr() + " kunde inte skickas epost. " + e.toString());
		}
		if (!err) {
			ServerUtil.log("Påminnelse på beställning"  + be1.getBestnr() + " till " + be1.getLevnr() + " skickad!");
			be1.setAntalpamin(be1.getAntalpamin() + 1);
			be1.setPamindat(new Date());
			ServerUtil.sendMessage(em, "Beställning " + be1.getBestnr() + " till " + be1.getLevnr() + " är inte mottagen.",
								"Kontrollera mottagandet!", be1.getVarRef());
			try {
				if (be1.getAntalpamin() >= 2 ) {
					be1.setStatus("Fel");
				}
				TableBesthand beh = new TableBesthand(be1.getBestnr(),ServerUtil.getSXReg(em, SXConstant.SXREG_SERVERANVANDARE, SXConstant.SXREG_SERVERANVANDARE_DEFAULT),"Påminnelse skickad",0);
				em.persist(beh);
				em.flush();
			} catch (Exception e) {
				ServerUtil.log("Fel vid påminnelse på beställning " + be1.getBestnr() + ". E-post skickad, men kunde inte uppdatera status/Händelse. " + e.toString());
				e.printStackTrace();
			}
		}
	}
	
}
