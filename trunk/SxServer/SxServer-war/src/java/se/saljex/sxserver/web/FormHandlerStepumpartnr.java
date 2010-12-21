/**
 *
 * @author ulf
 */
package se.saljex.sxserver.web;

import java.io.IOException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.tables.TableStepumpartnr;

public class FormHandlerStepumpartnr extends FormHandler {

	public TableStepumpartnr t = new TableStepumpartnr(null);


	public static final String K_ARTNR = "artnr";

	//private TableStepumpartnr tableStepumpartnr;
	public FormHandlerStepumpartnr(EntityManager em, UserTransaction utx, String jspPrefix, HttpServletRequest request, HttpServletResponse response) throws IOException {
		super(em,utx,jspPrefix, request, response);
	}

	public WebTable getWebTable()  {
		Integer page = null;
		try { page = Integer.parseInt(request.getParameter(K_PAGE)); } catch (NumberFormatException e) { page=1; }
		if (page < 1) { page = 1; }
		Integer pageSize = null;
		try { pageSize = Integer.parseInt(request.getParameter(K_PAGESIZE)); } catch (NumberFormatException e) {  }
		if (pageSize != null && pageSize < 0) { pageSize = null; }

		WebTable<TableStepumpartnr> wt = new WebTable(em);
		wt.createQuery("select t from TableStepumpartnr t order by t.artnr");
		if (pageSize != null) { wt.setPageSize(pageSize); }
		wt.setPage(page);
		return wt;
	}



	@Override
	protected void handleDoNew() throws IOException, ServletException {
		formToEntity();
		if (!isFormError()) {
			Long i = (Long)em.createQuery("select count(t) from TableArtikel t where t.nummer=:nr").setParameter("nr", t.getArtnr()).getSingleResult();

			if (i!=null && i> 0) {
				try {
					utx.begin();
					em.joinTransaction();
					em.persist(t);
					utx.commit();
				} catch (EntityExistsException e1) {
					addFormError("Artikelnummer finns redan registrerat");
					try { utx.rollback(); } catch (Exception e) {}
					handleNew();
				} catch (Exception er) {
					addFormError(SXUtil.toHtml(er.toString()));
					try { utx.rollback(); } catch (Exception e) {e.printStackTrace();}
					er.printStackTrace();
					handleNew();
				}
				handleDoNewDone();
			} else {
				addFormError("Artikelnummer saknas");
				handleNew();
			}
		} else {
			handleNew();
		}
	}

	@Override
	protected void handleDelete() throws IOException, ServletException {
		formToEntity();
		super.handleDelete();
	}

	@Override
	protected void handleDoDelete() throws IOException, ServletException {
		formToEntity();
		if (!SXUtil.isEmpty(request.getParameter(K_ARTNR))) {
			try {
				utx.begin();
				em.joinTransaction();
				t = em.find(TableStepumpartnr.class, request.getParameter(K_ARTNR));
				em.remove(t);
				utx.commit();
			} catch (Exception er) {
				addFormError(SXUtil.toHtml(er.toString()));
				try { utx.rollback(); } catch (Exception e) {e.printStackTrace();}
				er.printStackTrace();
				handleNew();
			}
			handleDoDeleteDone();
		} else {
			handleDelete();
		}
	}


	@Override
	protected void formToEntity() {
		if (SXUtil.isEmpty(request.getParameter(K_ARTNR))) {
			addFormError("Artikelnummer saknas");
		} else {
			t.setArtnr(request.getParameter(K_ARTNR));
		}
	}
}

