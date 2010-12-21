/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import se.saljex.sxserver.websupport.WebUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.WebSupport;

import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableSteprodukt;

/**
 *
 * @author ulf
 */
public class FormHandlerSteprodukt extends FormHandler {

	public TableSteprodukt t = new TableSteprodukt();


	public static final String K_SN = "sn";
	public static final String K_INSTDATUM = "instdatum";
	public static final String K_ARTNR = "artnr";
	public static final String K_MODELL = "modell";
	public static final String K_NAMN = "namn";
	public static final String K_ADR1 = "adr1";
	public static final String K_ADR2 = "adr2";
	public static final String K_ADR3 = "adr3";
	public static final String K_REFERENS = "referens";
	public static final String K_TEL = "tel";
	public static final String K_MOBIL = "mobil";
	public static final String K_EPOST = "epost";
	public static final String K_INSTALLATORNAMN = "installatornamn";
	public static final String K_INSTALLATORKUNDNR = "installatorkundnr";
	public static final String K_FAKTNR = "faktnr";

	public static final String ACTION_FOLJUPPLIST = "foljupplist";

	
	public FormHandlerSteprodukt(EntityManager em, UserTransaction utx, String jspPrefix, HttpServletRequest request, HttpServletResponse response) {
		super(em, utx, jspPrefix, request, response);
	}

	public WebTable getWebTable()  {
		Integer page = null;
		try { page = Integer.parseInt(request.getParameter(K_PAGE)); } catch (NumberFormatException e) { page=1; }
		if (page < 1) { page = 1; }
		Integer pageSize = null;
		try { pageSize = Integer.parseInt(request.getParameter(K_PAGESIZE)); } catch (NumberFormatException e) {  }
		if (pageSize != null && pageSize < 0) { pageSize = null; }

		WebTable<TableSteprodukt> wt = new WebTable(em);
		String sqlWhere = "";
		String sokstr = request.getParameter(K_SOKSTR);
		if (!SXUtil.isEmpty(sokstr)) {
			sokstr = "%"+sokstr.toUpperCase()+"%";
			sqlWhere = "where (upper(t.sn) like :sokstr or upper(t.artnr) like :sokstr or upper(t.modell) like :sokstr or upper(t.installatorkundnr) like :sokstr or" +
					  " upper(t.installatornamn) like :sokstr or upper(t.namn) like :sokstr or upper(t.adr1) like :sokstr or upper(t.adr2) like :sokstr or" +
					  " upper(t.tel) like :sokstr or upper(t.mobil) like :sokstr )";
		}

		wt.createQuery("select t from TableSteprodukt t " + sqlWhere + " order by t.sn desc");
		if (!SXUtil.isEmpty(sokstr)) {
			wt.setQueryParameter("sokstr", sokstr);
		}

		if (pageSize != null) { wt.setPageSize(pageSize); }
		wt.setPage(page);
		return wt;
	}

	public WebTable getWebTableForUppfoljning()  {
		Integer page = null;
		try { page = Integer.parseInt(request.getParameter(K_PAGE)); } catch (NumberFormatException e) { page=1; }
		if (page < 1) { page = 1; }
		Integer pageSize = null;
		try { pageSize = Integer.parseInt(request.getParameter(K_PAGESIZE)); } catch (NumberFormatException e) {  }
		if (pageSize != null && pageSize < 0) { pageSize = null; }

		WebTable<TableSteprodukt> wt = new WebTable(em);
		String sqlWhere = " where t.sn in (select n.sn from TableSteproduktnot n where n.foljuppdatum <= :datum)";
		String sokstr = request.getParameter(K_SOKSTR);
		if (!SXUtil.isEmpty(sokstr)) {
			sokstr = "%"+sokstr.toUpperCase()+"%";
			sqlWhere = sqlWhere + " and (upper(t.sn) like :sokstr or upper(t.artnr) like :sokstr or upper(t.modell) like :sokstr or upper(t.installatorkundnr) like :sokstr or" +
					  " upper(t.installatornamn) like :sokstr or upper(t.namn) like :sokstr or upper(t.adr1) like :sokstr or upper(t.adr2) like :sokstr or" +
					  " upper(t.tel) like :sokstr or upper(t.mobil) like :sokstr )";
		}

		wt.createQuery("select t from TableSteprodukt t " + sqlWhere + " order by t.sn desc");
		if (!SXUtil.isEmpty(sokstr)) {
			wt.setQueryParameter("sokstr", sokstr);
		}
		wt.setQueryParameter("datum", new Date());
		if (pageSize != null) { wt.setPageSize(pageSize); }
		wt.setPage(page);
		return wt;
	}

	@Override
	protected void handleUpdate() throws IOException, ServletException {
		if (request.getParameter(K_SN) != null) {
			t = em.find(TableSteprodukt.class, request.getParameter(K_SN));
		} else t=null;

		if (t!=null) {
			super.getJsp(ACTION_UPDATE);
		} else {
			t = new TableSteprodukt();
			super.addFormError("Serienummret finns inte registrerat");
			handleList();
		}
	}

	@Override
	protected void handleDoNewDone() throws ServletException, IOException { getJsp(ACTION_DOUPDATE_DONE); }
	@Override
	protected void handleDoUpdateDone() throws ServletException, IOException { getJsp(ACTION_DOUPDATE_DONE); }

	@Override
	protected void handleDoUpdate() throws IOException, ServletException {
		try {
			utx.begin();
			if (request.getParameter(K_SN) != null) {
				t = em.find(TableSteprodukt.class, request.getParameter(K_SN));
			} else {
				t=null;
			}
			if (t==null) {
				utx.rollback();
				super.addFormError("Kan inte uppdatera - Serienumret finns inte");
				t = new TableSteprodukt();
				handleUpdate();
			} else {
				formToEntity();
				if (isFormError()) {
					utx.rollback();
					handleUpdate();
				} else {
					em.flush();
					utx.commit();
					handleDoUpdateDone();
				}
			}
		} catch (IOException eio) { throw eio; 
		} catch (ServletException esl) { throw esl; 
		} catch (Exception e) {
			try { utx.rollback(); } catch (Exception e2) {}
			throw new RuntimeException(e);
		}
	}


	@Override
	protected void handleDoNew() throws IOException, ServletException {
		formToEntity();
		if (!super.isFormError()) {
			try {
				utx.begin();
				t.setAnvandare(WebSupport.getSXSession(request.getSession()).getIntraAnvandareKort());
				em.persist(t);
				utx.commit();
			} catch (EntityExistsException e1) {
				super.addFormError("Serienumret finns redan registrerat");
				try { utx.rollback(); } catch (Exception e) {}
				handleNew();
			} catch (Exception er) {
				super.addFormError(SXUtil.toHtml(er.toString()));
				try { utx.rollback(); } catch (Exception e) {e.printStackTrace();}
				er.printStackTrace();
				handleNew();
			}
			handleDoNewDone();
		} else {
			handleNew();
		}
	}

	@Override
	protected void handleNew() throws ServletException, IOException  {
		getJsp(ACTION_UPDATE);
	}


	@Override
	protected void handleDoDelete() throws IOException, ServletException {
		//Vi ska inte kunna radera
	}

	@Override
	protected void handleOtherAction() throws IOException, ServletException {
		if (ACTION_FOLJUPPLIST.equals(action)) {
			mainAction = ACTION_LIST;
			handleList();
		} else {
			handleOtherAction();
		}
	}

	@Override
	protected void formToEntity() {
		if (!isMainActionUpdate())	{			// Om vi har en update ska vi ignorera primary key.
			t.setSn(request.getParameter(K_SN));
			if (t.getSn()==null) super.addFormError("S/N saknas");
		}

		if (!SXUtil.isEmpty(request.getParameter(K_INSTDATUM))) {
			try {
				t.setInstdatum(SXUtil.parseDateStringToDate(request.getParameter(K_INSTDATUM)));
			} catch (ParseException e ) {
				super.addFormError("Felaktigt datum");
				t.setInstdatum(null);
			}
		} else {
			t.setInstdatum(null);
		}


		if (SXUtil.isEmpty(request.getParameter(K_MODELL))) {
			t.setModell(request.getParameter(null));
		} else {
			t.setModell(request.getParameter(K_MODELL));
		}

		if (!SXUtil.isEmpty(request.getParameter(K_ARTNR))) {
			t.setArtnr(request.getParameter(K_ARTNR));
			TableArtikel art = em.find(TableArtikel.class, request.getParameter(K_ARTNR));
			if (art == null) {
				super.addFormError("Artikelnummer saknas");
			} else {
				if (t.getModell() == null) t.setModell(art.getNamn());
			}
		} else {
			t.setArtnr(null);
		}

		t.setNamn(!SXUtil.isEmpty(request.getParameter(K_NAMN)) ? request.getParameter(K_NAMN) : null);
		t.setAdr1(!SXUtil.isEmpty(request.getParameter(K_ADR1)) ? request.getParameter(K_ADR1) : null);
		t.setAdr2(!SXUtil.isEmpty(request.getParameter(K_ADR2)) ? request.getParameter(K_ADR2) : null);
		t.setAdr3(!SXUtil.isEmpty(request.getParameter(K_ADR3)) ? request.getParameter(K_ADR3) : null);
		t.setReferens(!SXUtil.isEmpty(request.getParameter(K_REFERENS)) ? request.getParameter(K_REFERENS) : null);
		t.setTel(!SXUtil.isEmpty(request.getParameter(K_TEL)) ? request.getParameter(K_TEL) : null);
		t.setMobil(!SXUtil.isEmpty(request.getParameter(K_MOBIL)) ? request.getParameter(K_MOBIL) : null);
		t.setEpost(!SXUtil.isEmpty(request.getParameter(K_EPOST)) ? request.getParameter(K_EPOST) : null);

		if (SXUtil.isEmpty(request.getParameter(K_INSTALLATORNAMN))) {
			t.setInstallatornamn(null);
		} else {
			t.setInstallatornamn(request.getParameter(K_INSTALLATORNAMN));
		}

		if (!SXUtil.isEmpty(request.getParameter(K_INSTALLATORKUNDNR))) {
			t.setInstallatorkundnr(request.getParameter(K_INSTALLATORKUNDNR));
			TableKund kun = em.find(TableKund.class, request.getParameter(K_INSTALLATORKUNDNR));
			if (kun == null) {
				super.addFormError("Kundnummer saknas");
			} else {
				if (t.getInstallatornamn() == null) t.setInstallatornamn(kun.getNamn());
			}
		} else {
			t.setInstallatorkundnr(null);
		}


		if (SXUtil.isEmpty(request.getParameter(K_FAKTNR))) {
			t.setFaktnr(null);
		} else {
			try {
				t.setFaktnr(Integer.parseInt(request.getParameter(K_FAKTNR)));
				if (t.getArtnr() != null) {
					Query q = em.createQuery("select sum(fa2.lev) from TableFaktura2 fa2 where fa2.tableFaktura2PK.faktnr = :faktnr and fa2.artnr = :artnr");
					q.setParameter("faktnr", t.getFaktnr());
					q.setParameter("artnr", t.getArtnr());
					Double antalPumpar = (Double)q.getSingleResult();
					if (antalPumpar <= 0) {
						super.addFormError("Inga pumpar på angiven faktura");
					}
					q = em.createQuery("select count(sp) from TableSteprodukt sp where sp.faktnr = :faktnr and sp.sn <> :sn");
					q.setParameter("faktnr", t.getFaktnr());
					q.setParameter("sn", t.getSn());
					Long antalProdukter = (Long)q.getSingleResult();
					if (antalProdukter >= antalPumpar) {
						super.addFormError("Produkten på angiven faktura finns redan registrerad");
					}
				} else {
					TableFaktura1 fa1 = em.find(TableFaktura1.class, t.getFaktnr());
					if (fa1 == null) {
						super.addFormError("Fakturanummer finns inte");
					}
				}
			} catch (NumberFormatException e) {
				super.addFormError("Felaktigt fakturanummer");
			}
		}

	}
}
