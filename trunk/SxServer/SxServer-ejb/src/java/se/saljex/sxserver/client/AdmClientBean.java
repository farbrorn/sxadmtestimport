/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.client;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.SXSecurityException;
import se.saljex.sxlibrary.exceptions.SxSQLOperationDeniedException;
import se.saljex.sxserver.tables.*;

/**
 *
 * @author Ulf
 */
@Stateful
public class AdmClientBean implements AdmClientBeanRemote {
	@Resource(mappedName = "saljexse")
	private DataSource saljexse;
	@Resource(mappedName = "sxadm")
	private DataSource sxadm;
	@Resource SessionContext ctx;
    @PersistenceContext(unitName="SxServer-ejbPU") private EntityManager em;
    @PersistenceContext(unitName="SxServer-ejbPU-Main") private EntityManager emMain;
	
	private String v;
	
	@Override
	public void setTest(String v) {
		this.v=v;
	}

	@Override
	public String getTest() {
		
		return v + " Principl name: " + ctx.getCallerPrincipal().getName();
	}

	@Override
	public TablePageReply<TableKund> getKundRowList(String sokStr, int limit, int offset) {
	//Query q = em.createNativeQuery("select nummer as Nummer, namn as namn from artikel where nummer>'1' order by nummer",KundRow.class);		
		TablePageReply<TableKund> tablePageReply = new TablePageReply();
		Query q = em.createQuery("select t from TableKund t where t.nummer > :nummer order by t.nummer");
		q.setParameter("nummer", sokStr);
		setupTablePageReply(tablePageReply, q, limit, offset);
		return tablePageReply;
	}
	
	private void doUpdateKund(String anvandareKort, TableKund kund) {
		TableKund old = em.find(TableKund.class, kund.getNummer());
		em.detach(old);
		em.merge(kund);
		
		TableKunhand hand = new TableKunhand(kund.getNummer(), new java.util.Date(), new java.util.Date());
		hand.setAnvandare(anvandareKort);
		hand.setHandelse(SXConstant.HANDELSE_ANDRAD);
		em.persist(hand);
		
	}
	
	private void doDeleteKund(String anvandareKort, String kundnr, boolean forceDelete) throws SxSQLOperationDeniedException{
		TableKund kund = em.find(TableKund.class, kundnr);
		if (kund!=null) {
			if (!forceDelete) {	//Check om det är ok att radera kunden
				Integer cn;
				String o = ""; 
				if (((Integer)em.createQuery("select count(a) from TableKundres a where a.kundnr = :kundnr").setParameter("kundnr", kundnr).getSingleResult()) > 0) o = o + " Obetalda fakturor finns.";
				if (((Integer)em.createQuery("select count(a) from TableOrder1 a where a.kundnr = :kundnr").setParameter("kundnr", kundnr).getSingleResult()) > 0) o = o + " Det finns order.";
				if (((Integer)em.createQuery("select count(a) from TableBonus a where a.kundnr = :kundnr").setParameter("kundnr", kundnr).getSingleResult()) > 0) o = o + " Det finns ej utbetald bonus.";
				if (((Integer)em.createQuery("select count(a) from TableRanta a where a.kundnr = :kundnr").setParameter("kundnr", kundnr).getSingleResult()) > 0) o = o + " Det finns ofakturerad ränta.";
				if (o.length() > 0) throw new SxSQLOperationDeniedException("kunden kan inte raderas. Orsak: " + o);
			}
//			TableRanta t;  t.getTableRantaPK();
			TableKunhand hand = new TableKunhand(kundnr, new java.util.Date(), new java.util.Date());
			hand.setAnvandare(anvandareKort);
			hand.setHandelse(SXConstant.HANDELSE_RADERAD);
			em.persist(hand);
			
			em.createQuery("delete from TableBonus a where a.kund = :kundnr").setParameter("kundnr", kundnr).executeUpdate();
//			em.createQuery("delete from TableKundlogin a where exists (select 't' from TableKundkontakt k where a.kontaktid=k.kontaktid and k.kundnr = :kundnr)").setParameter("kundnr", kundnr).executeUpdate();
			em.createQuery("delete from TableKundlogin a where a.kontaktid in (select k.kontaktid from TableKundkontakt k where k.kundnr = :kundnr)").setParameter("kundnr", kundnr).executeUpdate();
			em.createQuery("delete from TableKundkontakt a where a.kundnr = :kundnr").setParameter("kundnr", kundnr).executeUpdate();
			
			em.createQuery("delete from TableKunrab a where a.tableKunrabPKkundnr = :kundnr").setParameter("kundnr", kundnr).executeUpdate();
			em.createQuery("delete from TableKunstat a where a.tableKunstatPK.kundnr = :kundnr").setParameter("kundnr", kundnr).executeUpdate();
			em.createQuery("delete from TableRanta a where a.tableRantaPK.kundnr = :kundnr").setParameter("kundnr", kundnr).executeUpdate();
			em.remove(kund);
		}
		
	}
	
	private void setupTablePageReply(TablePageReply tablePageReply, Query q, int limit, int offset) {
		tablePageReply.setLimit(limit);
		tablePageReply.setOffset(offset);
		if (limit > 0) q.setMaxResults(limit+1);
		if (offset > 0) q.setFirstResult(offset);
		List list = q.getResultList();
		if (list.size() > limit && limit >0) {
			list.remove(list.size()-1);
			tablePageReply.setHasMoreRows(true);
		}
		tablePageReply.setRows(list);
		
	}


	
	
}
