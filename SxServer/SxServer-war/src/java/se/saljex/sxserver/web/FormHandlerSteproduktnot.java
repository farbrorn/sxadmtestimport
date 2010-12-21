/**
 *
 * @author ulf
 */
package se.saljex.sxserver.web;

import se.saljex.sxserver.websupport.WebUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.WebSupport;
import se.saljex.sxserver.ServerUtil;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableSteprodukt;
import se.saljex.sxserver.tables.TableSteproduktnot;

public class FormHandlerSteproduktnot extends FormHandler {

	public TableSteproduktnot t = new TableSteproduktnot();


	public static final String K_GETPDFSERVICEORDER = "getpdfserviceorder";
	public static final String K_SN = "sn";
	public static final String K_ID = "kid";
	public static final String K_FRAGA = "fraga";
	public static final String K_SVAR = "svar";
	public static final String K_ARENDETYP = "arendetyp";
	public static final String K_FELORSAK = "felorsak";
	public static final String K_PUBLICERASOMQA = "publicerasomqa";
	public static final String K_FOLJUPPDATUM = "foljuppdatum";
	public static final String K_BILAGA = "bilaga";
	public static final String K_FILNAMN = "filnamn";
	public static final String K_SERVICEOMBUDKUNDNR = "sombkundnr";
	public static final String K_SERVICEOMBUDNAMN = "sombnamn";

	public static final String ARENDETYP_DRIFTSATTPROT = "Driftsättningsprotokoll";
	public static final String ARENDETYP_SERVICEORDER = "Serviceorder";
	public static final String[] ARENDETYPER = {"","Teknik", ARENDETYP_DRIFTSATTPROT,ARENDETYP_SERVICEORDER, "Övrigt"};

	public static final String[] FELORSAKER = {"","Installationsfel", "Inställningsfel", "Handhavandefel", "Produktfel garanti", "Produktfel ej garanti", "Annat" };

	private HtmlFileUpload htmlFileUpload;

	private TableSteprodukt tableSteprodukt;
	public FormHandlerSteproduktnot(EntityManager em, UserTransaction utx, String jspPrefix, HttpServletRequest request, HttpServletResponse response) throws IOException {
		super(em,utx,jspPrefix, request, response);
		htmlFileUpload = new HtmlFileUpload(request);
		tableSteprodukt = null;
		if (!SXUtil.isEmpty(request.getParameter(K_SN))) {
			tableSteprodukt = em.find(TableSteprodukt.class, request.getParameter(K_SN));
		}
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
		wt.createQuery("select t from TableSteproduktnot t where t.sn = :sn order by t.crdt desc");
		wt.setQueryParameter("sn", sn);
		if (pageSize != null) { wt.setPageSize(pageSize); }
		wt.setPage(page);
		return wt;
	}

	public TableSteprodukt getSteprodukt() {
		return tableSteprodukt;
	}


	public String getArendetypHtmlOptions() {
		StringBuilder sb = new StringBuilder();
		boolean selectedSatt = false;
		boolean sel = false;
		for (String s : ARENDETYPER) {
			if (s!=null) {
				sel = s.equals(t.getArendetyp());
				sb.append("<option" + (sel ? " selected=\"selected\"" : "") + ">" + s + "</option>");
				if (sel) selectedSatt = true;
			}
		}
		if (!selectedSatt && !SXUtil.isEmpty(t.getArendetyp())) sb.append("<option selected=\"selected\">" + t.getArendetyp() + "</option>");
		return sb.toString();
	}
	public String getFelorsakHtmlOptions() {
		StringBuilder sb = new StringBuilder();
		boolean selectedSatt = false;
		boolean sel = false;
		for (String s : FELORSAKER) {
			if (s!=null) {
				sel = s.equals(t.getFelorsak());
				sb.append("<option" + (sel ? " selected=\"selected\"" : "") + ">" + s + "</option>");
				if (sel) selectedSatt = true;
			}
		}
		if (!selectedSatt && !SXUtil.isEmpty(t.getFelorsak())) sb.append("<option selected=\"selected\">" + t.getFelorsak() + "</option>");
		return sb.toString();
	}

	@Override
	protected void handleOtherAction() throws IOException, ServletException {
		if (K_GETFILE.equals(action)) {
			getEntityByRequestAndThrowNotFoundException();
			WebSupport.sendFile(t.getBilaga(), t.getContenttype(), response.getOutputStream(), response);

		} else if (K_GETPDFSERVICEORDER.equals(action)) {
			getEntityByRequestAndThrowNotFoundException();
			try {
				if (ARENDETYP_SERVICEORDER.equals(t.getArendetyp())) {
					WebSupport.sendPdf(lookupLocalWebSupportBean().getPdfSteServiceorder(t.getId()), response.getOutputStream(), response);
				} else {
					response.getWriter().print("Inte en serviceorder");
				}
			} catch (com.lowagie.text.DocumentException e) {ServerUtil.log(e.toString()); e.printStackTrace();}
		} else {
			getJsp(ACTION_LIST);
		}
	}

	private void getEntityByRequest() {
		Integer id=null;
		try { id=Integer.parseInt(request.getParameter(K_ID)); } catch (NumberFormatException e) {}
		t = null;
		if (id!=null ) {
			t = em.find(TableSteproduktnot.class, id);
		}
	}

	private void getEntityByRequestAndThrowNotFoundException() {
		getEntityByRequest();
		if (t==null) throw new EntityNotFoundException("ID-nummer saknas");
	}

	private void finnsProduktAnnarsException() throws EntityNotFoundException {
		if (tableSteprodukt==null && !SXUtil.isEmpty(request.getParameter(K_SN))) {
			tableSteprodukt = em.find(TableSteprodukt.class, request.getParameter(K_SN));
		}
		if (tableSteprodukt == null) throw new EntityNotFoundException("Serienummer saknas");
	}

	@Override
	protected void handleNew() throws IOException, ServletException {
		finnsProduktAnnarsException();
		getJsp(ACTION_UPDATE);
	}

	@Override
	protected void handleDoNew() throws IOException, ServletException {
		finnsProduktAnnarsException();
		formToEntity();
		if (!isFormError()) {
			try {
				utx.begin();
				em.joinTransaction();
				Integer maxId = (Integer)em.createQuery("select max(t.id) from TableSteproduktnot t").getSingleResult();
				if (maxId==null) maxId=0;
				maxId++;
				t.setId(maxId);
				t.setAnvandare(WebSupport.getSXSession(request.getSession()).getIntraAnvandareKort());
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
	protected void handleDoNewDone() throws ServletException, IOException { getJsp(ACTION_DOUPDATE_DONE); }

	@Override
	protected void handleDoDelete() throws IOException, ServletException {
		//Vi ska inte kunna radera
	}

	@Override
	protected void handleUpdate() throws IOException, ServletException {
		getEntityByRequestAndThrowNotFoundException();
		super.handleUpdate();
	}

	@Override
	protected void handleDoUpdate() throws IOException, ServletException {
		try {
			utx.begin();
			getEntityByRequest();
			if (t==null) {
				utx.rollback();
				super.addFormError("Kan inte uppdatera - Serienumret finns inte");
				t = new TableSteproduktnot();
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
	protected void handleDoUpdateDone() throws ServletException, IOException { getJsp(ACTION_DOUPDATE_DONE); }



	@Override
	protected void formToEntity() {

			if (!isMainActionUpdate())	{			// Dessa rader kan inte ändras vid update
				t.setFraga(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_FRAGA)) ? htmlFileUpload.getFieldValue(K_FRAGA) : null);

				String sn = request.getParameter(K_SN);
				t.setSn(sn);
				if (sn==null) addFormError("S/N saknas");
			}

			if (SXUtil.isEmpty(t.getFilnamn())) {  //Kan ändras vid update om ingen fil tidigare är uppladdad
				t.setBilaga(htmlFileUpload.getFile().length > 0 ? htmlFileUpload.getFile() : new byte[0]);
				t.setFilnamn(!SXUtil.isEmpty(htmlFileUpload.getOriginalFileName()) ? htmlFileUpload.getOriginalFileName() : null);
				t.setContenttype(!SXUtil.isEmpty(htmlFileUpload.getContentType()) ? htmlFileUpload.getContentType() : null);
			}

			//Följande kan ändras vid update
			t.setSvar(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_SVAR)) ? htmlFileUpload.getFieldValue(K_SVAR) : null);
			t.setArendetyp(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_ARENDETYP)) ? htmlFileUpload.getFieldValue(K_ARENDETYP) : null);
			t.setFelorsak(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_FELORSAK)) ? htmlFileUpload.getFieldValue(K_FELORSAK) : null);
			t.setServiceombudkundnr(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_SERVICEOMBUDKUNDNR)) ? htmlFileUpload.getFieldValue(K_SERVICEOMBUDKUNDNR) : null);
			t.setServiceombudnamn(!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_SERVICEOMBUDNAMN)) ? htmlFileUpload.getFieldValue(K_SERVICEOMBUDNAMN) : null);


			if (!SXUtil.isEmpty(t.getServiceombudkundnr())) {
				TableKund tk = em.find(TableKund.class, t.getServiceombudkundnr());
				if (tk!=null) {
					t.setServiceombudnamn(tk.getNamn());
				} else {
					addFormError("Kundnummer saknas i kundregistret");
				}
			}
			java.util.Date datum = null;
			try {
				if (!SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_FOLJUPPDATUM)))	datum = SXUtil.parseDateStringToDate(htmlFileUpload.getFieldValue(K_FOLJUPPDATUM));
				if (datum==null && !SXUtil.isEmpty(htmlFileUpload.getFieldValue(K_FOLJUPPDATUM))) throw new ParseException("",0);
			} catch (ParseException e) {
				addFormError("Felaktigt uppföljningsdatum");
			}
			t.setFoljuppdatum(datum);

			t.setPublicerasomqa(htmlFileUpload.getFieldValue(K_PUBLICERASOMQA ) != null ? (short)1 : (short)0);
	}

	private LocalWebSupportLocal lookupLocalWebSupportBean() {
		try {
			Context c = new InitialContext();
			return (LocalWebSupportLocal) c.lookup("java:comp/env/LocalWebSupportBean");
		} catch (NamingException ne) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
			throw new RuntimeException(ne);
		}
	}
}

