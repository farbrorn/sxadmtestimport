/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 *
 * @author ulf
 */
@Stateless
public class LocalWebSupportBean implements LocalWebSupportLocal {
	@Resource(name = "saljexse")
	private DataSource saljexse;
	@Resource(name = "sxadm")
	private DataSource sxadm;
	@Resource TimerService timerService;
	@Resource EJBContext context;
	@Resource(name="sxmail", mappedName="sxmail") private Session mailsxmail;
	@PersistenceContext private EntityManager em;

	  public TableKund getTableKund(String kundnr) {
			 return em.find(TableKund.class, kundnr);
	  }

	  public TableFaktura1 getTableFaktura1(int faktnr) {
			return em.find(TableFaktura1.class, faktnr);
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

	public ByteArrayOutputStream getPdfBest(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException {
		if (nr == null) return null;
		PdfBest pdf = new PdfBest(em);
		return pdf.getPDF(nr);
	}

    
	  
}
