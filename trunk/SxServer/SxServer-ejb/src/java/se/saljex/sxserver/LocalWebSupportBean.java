/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.LocalWebSupportRemote;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableKundkontakt;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableKundlogin;
import se.saljex.sxserver.tables.TableKundres;
import se.saljex.sxserver.tables.TableOrder2;
import se.saljex.sxserver.tables.TableRorder;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Timer;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableLager;
import se.saljex.sxserver.tables.TableLagerPK;
import se.saljex.sxserver.tables.TableOrder2PK;
import se.saljex.sxserver.tables.TableStjarnrad;

/**
 *
 * @author ulf
 */
@Stateless
public class LocalWebSupportBean implements LocalWebSupportLocal, LocalWebSupportRemote {
	@Resource(mappedName = "saljexse")
	private DataSource saljexse;
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
	@Resource TimerService timerService;
	@Resource EJBContext context;
	@Resource(name="sxmail", mappedName="sxmail") private Session mailsxmail;
	@PersistenceContext(unitName="SxServer-ejbPU") private EntityManager em;
//	@Resource private UserTransaction utx;

	@PersistenceUnit(unitName="SxServer-ejbPU") private EntityManagerFactory emf;

	  public TableKund getTableKund(String kundnr) {
			 if (kundnr != null) return em.find(TableKund.class, kundnr); else return null;
	  }

	  public TableFaktura1 getTableFaktura1(int faktnr) {
			return em.find(TableFaktura1.class, faktnr);
	  }

	public TableKundkontakt getTableKundkontakt(Integer kontaktid) {
		if (kontaktid != null) return em.find(TableKundkontakt.class, kontaktid); else return null;
	}

	public TableKundlogin getTableKundlogin(String loginnamn) {
		if (loginnamn != null) return em.find(TableKundlogin.class, loginnamn); else return null;
	}


	  public List<TableFaktura2> getListTableFaktura2(int faktnr) {
		  Query q = em.createNamedQuery("TableFaktura2.findByFaktnr");
		  q.setParameter("faktnr", faktnr);
		  List l = q.getResultList();
		  return l;
	  }

	  public List<TableOrder2> getListTableOrder2(int ordernr) {
		  Query q = em.createNamedQuery("TableOrder2.findByOrdernr");
		  q.setParameter("ordernr", ordernr);
		  List l = q.getResultList();
		  return l;
	  }

	  public List<TableOrder1> getListTableOrder1(String kundnr) {
		  Query q;
		  if (kundnr != null) {
			  q = em.createNamedQuery("TableOrder1.findByKundnr");
			  q.setParameter("kundnr", kundnr);
		  } else {
			   q = em.createNamedQuery("TableOrder1.findAll");			  
		  }
		  List l = q.getResultList();
		  return l;
	  }

	  public List<TableKundres> getListTableKundres(String kundnr) {
		  Query q;
		  if (kundnr != null) {
			  q = em.createNamedQuery("TableKundres.findByKundnr");
			  q.setParameter("kundnr", kundnr);
		  } else {
			   q = em.createNamedQuery("TableKundres.findAll");			  
		  }
		  List l = q.getResultList();
		  return l;
	  }

	  public TableOrder1 getTableOrder1(int ordernr) {
			 return em.find(TableOrder1.class, ordernr);
	  }

	  public List<TableRorder> getListTableRorder(String kundnr) {
		  Query q;
		  if (kundnr != null) {
			  q = em.createNamedQuery("TableRorder.findByKundnr");
			  q.setParameter("kundnr", kundnr);
		  } else {
			   q = em.createNamedQuery("TableRorder.findAll");			  
		  }
		  List l = q.getResultList();
		  return l;
	  }

	public ByteArrayOutputStream getPdfFaktura(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException {
		if (nr == null) return null;
		PdfFaktura pdf = new PdfFaktura(em);
		return pdf.getPDF(nr);
	}
	public ByteArrayOutputStream getPdfFaktura(Integer nr, String kundnr) throws DocumentException, IOException {
		if (nr == null || kundnr == null) return null;
		TableFaktura1 fa1 = em.find(TableFaktura1.class, nr);
		if (fa1.getKundnr().equals(kundnr)) {
			return getPdfFaktura(nr);
		} else {
			return null;
		}
	}

	public ByteArrayOutputStream getPdfBest(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException {
		if (nr == null) return null;
		PdfBest pdf = new PdfBest(em);
		return pdf.getPDF(nr);
	}

	public ByteArrayOutputStream getPdfOffert(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException {
		return getPdfOffert(nr, false);
	}
	public ByteArrayOutputStream getPdfOffertInkMoms(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException {
		return getPdfOffert(nr, true);
	}

	public ByteArrayOutputStream getPdfOffert(Integer nr, boolean skrivArtikelradInkMoms) throws com.lowagie.text.DocumentException, java.io.IOException {
		if (nr == null) return null;
		PdfOffert pdf = new PdfOffert(em);
		return pdf.getPDF(nr, skrivArtikelradInkMoms);
	}



	public String updateLagerSaldonWithHTMLResponse() {
		String ret = "";
		java.sql.Connection con = null;
		try {
			con = sxadm.getConnection();
			LagerCheck l = new LagerCheck(con);
			try {
				ret = ret + "<br/>Startar updateLagerSaldon";
				l.run();
				ret = ret + " - Klart!";
			} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid updateLagerSaldon " + e.toString(); }
		} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid getConnection " + e.toString(); }
		finally { try { con.close(); } catch (Exception e) {} }
		return ret;
	}



	public String updateWebArtikelWithHTMLResponse() {
		String ret = "";
		java.sql.Connection conSe = null;
		int cn;
		try {
			conSe = saljexse.getConnection();
			WebArtikelUpdater w = new WebArtikelUpdater(em, conSe);
			try {
				ret = ret + "<br/>Startar updateWArtikel";
				cn = w.updateWArt();
				ret = ret + " - Klart! Antal rader: " + cn;
			} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid updateWArtikel " + e.toString(); }
			 catch (Exception e2) { ret = ret + "<br/>Okänt undantag " + e2.toString() + " Mess:" + e2.getMessage(); }
		} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid getConnection " + e.toString(); }
		finally { try { conSe.close(); } catch (Exception e) {} }
		return ret;
	}


	public String updateWebArtikelTradWithHTMLResponse() {
		String ret = "";
		java.sql.Connection conSe = null;
		int cn;
		try {
			conSe = saljexse.getConnection();
			WebArtikelUpdater w = new WebArtikelUpdater(em, conSe);
			try {
				ret = ret + "<br/>Startar updateWArtGrp";
				cn = w.updateWArtGrp();
				ret = ret + " - Klart! Antal rader: " + cn;
			} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid updateWArtGrp " + e.toString(); }
			try {
				ret = ret + "<br/>Startar updateWArtGrpLank";
				cn = w.updateWArtGrpLank();
				ret = ret + " - Klart! Antal rader: " + cn;
			} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid updateWArtGrpLank " + e.toString(); }
			try {
				ret = ret + "<br/>Startar updateWArtKlase";
				cn = w.updateWArtKlase();
				ret = ret + " - Klart! Antal rader: " + cn;
			} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid updateWArtKlase " + e.toString(); }
			try {
				ret = ret + "<br/>Startar updateWArtKlaseLank";
				cn = w.updateWArtKlaseLank();
				ret = ret + " - Klart! Antal rader: " + cn;
			} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid updateWArtKlaseLank " + e.toString(); }
		} catch (java.sql.SQLException e) { ret = ret + "<br/>Undantag vid getConnection " + e.toString(); }
		finally { try { conSe.close(); } catch (Exception e) {} }
		return ret;
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
		return ret;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

/*	public UserTransaction getUserTransaction() {
		return utx;
	}
*/
	public ByteArrayOutputStream getPdfSteServiceorder(Integer id) throws DocumentException, IOException {
		if (id==null) return null;
		PdfSteServiceorder pdf = new PdfSteServiceorder(em);
		return pdf.getPDF(id);
	}

	//Innan anrop måste säkerhetsspärrar utföras - behörighet, är ordern lås osv
	@RolesAllowed("admin")
	public void deleteOrder(int ordernr) {
		TableOrder1 o1 = em.find(TableOrder1.class, ordernr);
		if (o1!=null) {
			List<TableOrder2> l = em.createNamedQuery("TableOrder2.findByOrdernr").setParameter("ordernr", ordernr).getResultList();
			TableArtikel art;
			for (TableOrder2 o2 :  l) {
				if (o2.getArtnr().startsWith("*") && o2.getStjid()>0) {
					TableStjarnrad stj = em.find(TableStjarnrad.class, o2.getStjid());
					if (stj != null) {
						stj.setAutobestall((short)0);
					}
				} else {
					art = em.find(TableArtikel.class, o2.getArtnr());
					if (art!=null) {
						TableLager lag = em.find(TableLager.class, new TableLagerPK(art.getNummer(), o1.getLagernr()));
						if (lag!=null) {
							lag.setIorder(lag.getIorder()-o2.getBest());
						}
					}
				}
			}
			em.createNamedQuery("TableOrder2.deleteByOrdernr").setParameter("ordernr", o1.getOrdernr()).executeUpdate();
			em.remove(o1);
			em.flush();
		}
	}

	//Innan anrop måste säkerhetsspärrar utföras - behörighet, är ordern lås osv
	@RolesAllowed("admin")
	public void changeOrderRowAntal(int ordernr,short pos, double antal) {
		TableOrder1 o1 = em.find(TableOrder1.class, ordernr);
		if (o1!=null) {
			TableOrder2 o2 = em.find(TableOrder2.class, new TableOrder2PK(ordernr, pos));
			if (o2!=null) {
				if (o2.getArtnr().startsWith("*") && o2.getStjid()>0) {
					TableStjarnrad stj = em.find(TableStjarnrad.class, o2.getStjid());
					if (stj != null) {
						stj.setAntal(antal);
						if (antal==0.0) stj.setAutobestall((short)0);
					}
				} else {
					TableLager lag = em.find(TableLager.class, new TableLagerPK(o2.getArtnr(), o1.getLagernr()));
					if (lag!=null) {
						lag.setIorder(lag.getIorder()-o2.getBest()+antal);
					}
				}
				if (antal==0.0) {			//Om antaket är noll så tar vi bort raden.
					em.remove(o2);			//Förmodligen behöver inte pos-kolumnn i databasen uppdateras för övriga rader
					em.flush();
					//Har vi tagit bort sista raden på ordern så tar vi också bort huvudet
					Long kvaravarandeRader = (Long)(em.createQuery("select count(o) from TableOrder2 o where o.tableOrder2PK.ordernr=:ordernr").setParameter("ordernr", ordernr).getSingleResult());
					if (kvaravarandeRader!=null && kvaravarandeRader.equals((long)0)) em.remove(o1);
				} else {
					o2.setBest(antal);
					o2.setLev(antal);
				}
				em.flush();
			}
		}
	}






    
	  
}
