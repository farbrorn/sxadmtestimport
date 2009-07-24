/**
 *
 * @author ulf
 */
package se.saljex.sxserver.web;

import java.io.IOException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import se.saljex.sxserver.SXUtil;
import se.saljex.sxserver.tables.TableSteprodukt;
import se.saljex.sxserver.tables.TableSteproduktnot;
import se.saljex.sxserver.tables.TableSteproduktnotPK;

public class FormHandlerSteproduktnot extends FormHandler {

	public TableSteproduktnot t = new TableSteproduktnot(new TableSteproduktnotPK());


	public static final String ACTION_GETFILE = "getfile";
	public static final String K_SN = "sn";
	public static final String K_ID = "kid";
	public static final String K_NOTERING = "notering";
	public static final String K_BILAGA = "bilaga";
	public static final String K_FILNAMN = "filnamn";
	private HtmlFileUpload htmlFileUpload;

	private TableSteprodukt tableSteprodukt;
	public FormHandlerSteproduktnot(EntityManager em, UserTransaction utx, String jspPrefix, HttpServletRequest request, HttpServletResponse response) throws IOException {
		super(em,utx,jspPrefix, request, response);
		htmlFileUpload = new HtmlFileUpload(request);
		tableSteprodukt = null;
//		try {
//			utx.begin();
			if (!SXUtil.isEmpty(request.getParameter(K_SN))) {
				tableSteprodukt = em.find(TableSteprodukt.class, request.getParameter(K_SN));
			}
//			utx.rollback();
//		} catch (NotSupportedException e) {e.printStackTrace();
//		} catch (SystemException e2) {e2.printStackTrace();}
		if (tableSteprodukt == null) throw new EntityNotFoundException("Serienummer saknas");
	}

	public WebTable getWebTable()  {
		Integer page = null;
		try { page = Integer.parseInt(request.getParameter(K_PAGE)); } catch (NumberFormatException e) { page=1; }
		if (page < 1) { page = 1; }
		Integer pageSize = null;
		try { pageSize = Integer.parseInt(request.getParameter(K_PAGESIZE)); } catch (NumberFormatException e) {  }
		if (pageSize != null && pageSize < 0) { pageSize = null; }

		WebTable<TableSteproduktnot> wt = new WebTable(em);
		String sn = request.getParameter(K_SN);
		wt.createQuery("select t from TableSteproduktnot t where t.tableSteproduktnotPK.sn = :sn order by t.tableSteproduktnotPK.sn, t.tableSteproduktnotPK.id desc");
		wt.setQueryParameter("sn", sn);
		if (pageSize != null) { wt.setPageSize(pageSize); }
		wt.setPage(page);
		return wt;
	}

	public TableSteprodukt getSteprodukt() {
		return tableSteprodukt;
	}

	@Override
	protected void handleOtherAction() throws IOException, ServletException {
		Integer id=null;
		if (ACTION_GETFILE.equals(action)) {
			try { id=Integer.parseInt(request.getParameter(K_ID)); } catch (NumberFormatException e) {}
			t = null;
			if (id!=null && !SXUtil.isEmpty(request.getParameter(K_SN))) {
				t = em.find(TableSteproduktnot.class, new TableSteproduktnotPK(request.getParameter(K_SN),id));
			}
			if (t==null) throw new EntityNotFoundException("ID-nummer saknas");
			System.out.print("Content type: " + t.getConttenttype());
			WebUtil.sendFile(t.getBilaga(), t.getConttenttype(), response.getOutputStream(), response);

		} else {
			getJsp(ACTION_LIST);
		}
	}

	@Override
	protected void handleDoNew() throws IOException, ServletException {
		formToEntity();
		if (!isFormError()) {
			try {
				utx.begin();
				em.joinTransaction();
				Integer maxId = (Integer)em.createQuery("select max(t.tableSteproduktnotPK.id) from TableSteproduktnot t where t.tableSteproduktnotPK.sn=" + t.getTableSteproduktnotPK().getSn()).getSingleResult();
				if (maxId==null) maxId=0;
				maxId++;
				t.getTableSteproduktnotPK().setId(maxId);
				t.setAnvandare(WebUtil.getSXSession(request.getSession()).getIntraAnvandareKort());
				em.persist(t);
				utx.commit();
			} catch (EntityExistsException e1) {
				addFormError("Notering finns redan registrerat");
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
			handleNew();
		}
	}

	@Override
	protected void handleDoDelete() throws IOException, ServletException {
		//Vi ska inte kunna radera
	}

	@Override
	protected void formToEntity() {
			t.setBilaga(htmlFileUpload.getFile().length > 0 ? htmlFileUpload.getFile() : new byte[0]);
			t.setFilnamn(!SXUtil.isEmpty(htmlFileUpload.getOriginalFileName()) ? htmlFileUpload.getOriginalFileName() : null);
			t.setConttenttype(!SXUtil.isEmpty(htmlFileUpload.getContentType()) ? htmlFileUpload.getContentType() : null);
			if (!isMainActionUpdate())	{			// Om vi har en update ska vi ignorera primary key.
				String sn = request.getParameter(K_SN);
				int id = 0;
				try { id = Integer.parseInt(htmlFileUpload.getFieldValue(K_ID)); } catch ( NumberFormatException e) {}
				t.getTableSteproduktnotPK().setSn(sn);
				t.getTableSteproduktnotPK().setId(id);
				if (sn==null) addFormError("S/N saknas");
			}
			t.setNotering(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_NOTERING)) ? htmlFileUpload.getFieldValue(K_NOTERING) : null);
	}
}

