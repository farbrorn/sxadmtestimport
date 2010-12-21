/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author ulf
 */
public class FormHandler {
	public static final String K_ACTION = "action";
	public static final String K_PAGE = "page";
	public static final String K_PAGESIZE = "pagesize";
	public static final String K_SOKSTR = "sokstr";
	public static final String K_GETFILE = "getfile";

	public static final String ACTION_LIST = "list";
	public static final String ACTION_NEW = "new";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_DELETE = "delete";
	public static final String ACTION_VIEW = "view";
	public static final String ACTION_DONEW = "donew";
	public static final String ACTION_DOUPDATE = "doupdate";
	public static final String ACTION_DODELETE = "dodelete";
	public static final String ACTION_DONEW_DONE = "donewdone";
	public static final String ACTION_DOUPDATE_DONE = "doupdatedone";
	public static final String ACTION_DODELETE_DONE = "dodeletedone";

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected String jspPrefix;
	protected String action;

	protected String mainAction = null;

	protected EntityManager em;

	protected UserTransaction utx;
	private boolean idRequest;


	private String formError=null;

	public FormHandler(EntityManager em, UserTransaction utx, String jspPrefix, HttpServletRequest request, HttpServletResponse response) {
		//this.em=lookupLocalWebSupportBean().getEmf().createEntityManager();
		//this.em = emf.createEntityManager();
		this.em=em;
		this.utx=utx;
//		this.utx=lookupLocalWebSupportBean().getUserTransaction();
		this.request = request;
		this.response = response;
		this.jspPrefix = jspPrefix;
		action = request.getParameter(K_ACTION);

		if (action==null) action=ACTION_LIST;	//Default action
		if (SXUtil.isEmpty(request.getParameter("get"))) idRequest = true; else idRequest=false;
	}

	/* Dessa metoder skall overridas för att hantera actionsatserna */
	protected void getJsp(String jsp) throws ServletException, IOException {
		try {
			request.getRequestDispatcher(jspPrefix + "-" + jsp + ".jsp").include(request, response);
		} catch (IOException e) {
			throw new IOException("Metodanropet ej implementerad");
		}
	}

	protected void handleList() throws ServletException, IOException { getJsp(ACTION_LIST); }
	protected void handleNew() throws ServletException, IOException  { getJsp(ACTION_NEW); }
	protected void handleUpdate() throws ServletException, IOException { getJsp(ACTION_UPDATE); }
	protected void handleDelete() throws ServletException, IOException { getJsp(ACTION_DELETE); }
	protected void handleView() throws ServletException, IOException { getJsp(ACTION_VIEW); }
	protected void handleDoNew() throws ServletException, IOException { getJsp(ACTION_DONEW); }
	protected void handleDoUpdate() throws ServletException, IOException { getJsp(ACTION_DOUPDATE); }
	protected void handleDoDelete() throws ServletException, IOException { getJsp(ACTION_DODELETE); }
	protected void handleDoNewDone() throws ServletException, IOException { getJsp(ACTION_DONEW_DONE); }
	protected void handleDoUpdateDone() throws ServletException, IOException { getJsp(ACTION_DOUPDATE_DONE); }
	protected void handleDoDeleteDone() throws ServletException, IOException { getJsp(ACTION_DODELETE_DONE); }
	protected void handleOtherAction() throws ServletException, IOException { getJsp(ACTION_LIST); }

	protected void formToEntity() {}
	protected void entityToForm() {}

	public String getFormParameter(String param) {
		return request.getParameter(param);
	}


	public boolean isMainActionNew() { return ACTION_NEW.equals(mainAction); }
	public boolean isMainActionUpdate() { return ACTION_UPDATE.equals(mainAction); }
	public boolean isMainActionDelete() { return ACTION_DELETE.equals(mainAction); }
	public boolean isMainActionView() { return ACTION_VIEW.equals(mainAction); }
	public boolean isMainActionList() { return ACTION_LIST.equals(mainAction); }
	public String getMainAction() { return mainAction; }

	public boolean isIdRequest() { return idRequest; }
	// Returnerar det actionattribut som webbformulär använder när det ska skickas vidare
	public String getNextFormAction() {
		if (isMainActionDelete()) return ACTION_DODELETE;
		else if (isMainActionUpdate()) return ACTION_DOUPDATE;
		else if (isMainActionNew()) return ACTION_DONEW;
		else return "";
	}

	public void setFormError(String s) { formError = s; }
	public void addFormError(String s) { formError = formError+"<br/>"+s; }
	public String getFormError() { return formError; }

	public boolean isFormError() { return formError != null; }

	public void handleRequest() throws ServletException, IOException {
		if (ACTION_LIST.equals(action)) {
			mainAction = ACTION_LIST;
			handleList();
		} else if (ACTION_NEW.equals(action)) {
			mainAction = ACTION_NEW;
			handleNew();
		} else if (ACTION_UPDATE.equals(action)) {
			mainAction = ACTION_UPDATE;
			handleUpdate();
		} else if (ACTION_DELETE.equals(action)) {
			mainAction = ACTION_DELETE;
			handleDelete();
		} else if (ACTION_VIEW.equals(action)) {
			mainAction = ACTION_VIEW;
			handleView();
		} else if (ACTION_DONEW.equals(action)) {
			mainAction = ACTION_NEW;
			handleDoNew();
		} else if (ACTION_DOUPDATE.equals(action)) {
			mainAction = ACTION_UPDATE;
			handleDoUpdate();
		} else if (ACTION_DODELETE.equals(action)) {
			mainAction = ACTION_DELETE;
			handleDoDelete();
		} else if (ACTION_DONEW_DONE.equals(action)) {
			mainAction = ACTION_NEW;
			handleDoNewDone();
		} else if (ACTION_DOUPDATE_DONE.equals(action)) {
			mainAction = ACTION_UPDATE;
			handleDoUpdateDone();
		} else if (ACTION_DODELETE_DONE.equals(action)) {
			mainAction = ACTION_DELETE;
			handleDoDeleteDone();
		} else {
			mainAction = action;
			handleOtherAction();
		}
	}

	public String getAction() {return action; }

	private LocalWebSupportLocal lookupLocalWebSupportBean() {
		try {
			Context c = new InitialContext();
			return (LocalWebSupportLocal) c.lookup("java:comp/env/LocalWebSupportBean");
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		}
	}

}
