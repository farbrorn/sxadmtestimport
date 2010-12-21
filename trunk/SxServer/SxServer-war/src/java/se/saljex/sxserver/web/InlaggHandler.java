package se.saljex.sxserver.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author ulf
 */
public class InlaggHandler {
	private Connection con;
	public static final String K_INLAGGID = "inlaggid";
	public static final String K_KANALID = "kanalid";
	public static final String K_RUBRIK = "rubrik";
	public static final String K_INGRESS = "ingress";
	public static final String K_BRODTEXT = "brodtext";
	public static final String K_VISATILL = "visatill";
	public static final String K_ORIGINALFILENAME = "originalfilename";

	public static final String K_ACTION = "action";

	public static final String ACTION_LIST = "list";
	public static final String ACTION_NEW = "new";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_DELETE = "delete";
	public static final String ACTION_DONEW = "donew";
	public static final String ACTION_DOUPDATE = "doupdate";
	public static final String ACTION_DODELETE = "dodelete";
	public static final String ACTION_DONEW_DONE = "donewdone";
	public static final String ACTION_DOUPDATE_DONE = "doupdatedone";
	public static final String ACTION_DODELETE_DONE = "dodeletedone";

	public static final String FILEPREFIX = "sxdoc";
//	public static final String FILEPATH = "dokument/";
	public static final String FILEPATH = "";

	private HtmlFileUpload htmlFileUpload;
	private String formErrorString = null;

	private String action=null;
	private Integer inlaggId=null;
	private Integer kanalId=null;
	private String rubrik=null;
	private String ingress=null;
	private String brodtext=null;
	private String originalFilename=null;
	private java.sql.Date visaTill=null;

	private HttpServletRequest request = null;

	public InlaggHandler(Connection con) {
		this.con = con;
	}

/*	protected ResultSet getKanalerResultSet() throws SQLException {
		return con.createStatement().executeQuery("select kanalid, rubrik, beskrivning, showonstartpage from intrakanaler order by rubrik");
	}
*/
	public ArrayList<IntraKanal> getKanaler() throws SQLException {
		return getKanaler(null, null);
	}
	public ArrayList<IntraKanal> getKanalerOnStartPage() throws SQLException {
		return getKanaler("showonstartpage>0", "showonstartpage, rubrik");
	}

	private ArrayList<IntraKanal> getKanaler(String filter, String orderBy) throws SQLException {
		ArrayList<IntraKanal> a = new ArrayList();
		if (filter==null || filter.isEmpty()) filter = "1=1";
		if (orderBy==null || orderBy.isEmpty()) orderBy = "rubrik";
		ResultSet rs = con.createStatement().executeQuery("select kanalid, rubrik, beskrivning, showonstartpage from intrakanaler where " + filter + " order by " + orderBy);
		while (rs.next()){
			a.add(new IntraKanal(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)!=0 ));
		}
		return a;

	}
	/* Returnerar inlägg ur en kanal
	 * kanalId: Önskad kanal, om null returneras för alla kanaler
	 * page: Sida att börja från, börjar på 1, mindre värden tolkas som 1
	 * pageSize: Sidstorlek, eller antal rader att visa 0=alla inlägg
	 * filterByDatum: Skall vi filtrera efter visaTill-datumet?
	 */
	public ArrayList<IntraInlagg> getInlaggListByKanalId(Integer kanalId, int page, int pageSize, boolean filterByDatum) throws SQLException {
		ArrayList<IntraInlagg> a = new ArrayList();
		
		String sqlSuffix = null;
		if (page < 1) page = 1;
		if (pageSize > 0) sqlSuffix = "limit " + pageSize + " offset " + (page-1)*pageSize;
		
		String filterStr = null;
		if (kanalId == null) filterStr = "1=1"; else filterStr = "i.kanalid = " + kanalId;
		
		if (filterByDatum) filterStr = filterStr + " and (i.visatill is null or i.visatill >= '" + SXUtil.getFormatDate() + "')";

		ResultSet rs = getInlaggResultSet(filterStr, sqlSuffix);
		IntraInlagg inl;
		while (rs.next()) {
			inl = inlaggResultSetToIntraInlagg(rs);
			a.add(inl);
		}
		return a;
	}

	public ArrayList<IntraInlagg> getInlaggListByKanalId(Integer kanalId, int page, int pageSize) throws SQLException {
		return getInlaggListByKanalId(kanalId, page, pageSize, true);
	}
	public ArrayList<IntraInlagg> getInlaggListByKanalId(Integer kanalId) throws SQLException {
		return getInlaggListByKanalId(kanalId, 1, 0, true);
	}

	public IntraInlagg getInlagg(int inlaggId) throws SQLException {
		ResultSet rs = getInlaggResultSet("i.inlaggid = " + inlaggId, null);
		if (rs.next()) {
			return inlaggResultSetToIntraInlagg(rs);
		} else return null;
	}

	public byte[] readFile(int inlaggId) throws SQLException, FileNotFoundException, IOException {
		IntraInlagg inl = getInlagg(inlaggId);
		FileInputStream fis = new FileInputStream(inl.fileName);
		byte[] a = new byte[fis.available()];
		fis.read(a);
		fis.close();
		return a;
	}


	private ResultSet getInlaggResultSet(String filterStr, String sqlSuffix) throws SQLException {
		if (sqlSuffix==null) sqlSuffix = "";
		return con.createStatement().executeQuery("select i.inlaggid, i.kanalid, k.rubrik as kanalrubrik, i.rubrik, i.ingress, i.brodtext, i.filename, i.originalfilename, i.contenttype, i.visatill, i.anvandarekort, i.crtime  from intrainlagg i left outer join intrakanaler k on k.kanalid  = i.kanalid where " + filterStr + " order by k.rubrik, i.inlaggid" + " " + sqlSuffix);
	}
	
	private IntraInlagg inlaggResultSetToIntraInlagg(ResultSet rs) throws SQLException {
			IntraInlagg inl = new IntraInlagg();
			inl.inlaggId = rs.getInt(1);
			inl.kanalId = rs.getInt(2);
			inl.kanal_rubrik = rs.getString(3);
			inl.rubrik = rs.getString(4);
			inl.ingress = rs.getString(5);
			inl.brodText = rs.getString(6);
			inl.fileName = rs.getString(7);
			inl.originalFileName = rs.getString(8);
			inl.contentType = rs.getString(9);
			inl.visaTill = rs.getDate(10);
			inl.anvandareKort = rs.getString(11);
			inl.crTime = rs.getTimestamp(12);
			return inl;
	}

	public String getKanalerOptionList() throws SQLException {
		boolean selected = false;
		ResultSet rs = con.createStatement().executeQuery("select kanalid, rubrik, beskrivning, showonstartpage from intrakanaler order by rubrik");

		StringBuilder sb = new StringBuilder();
		while (rs.next()) {
			sb.append("<option value=\"" + rs.getInt(1) + "\"");
			if (kanalId != null && kanalId.equals(rs.getInt(1))) {
				sb.append(" selected=\"selected\"");
				selected = true;
			}
			sb.append(">" + SXUtil.toHtml(rs.getString(2))+ "</option>");
		}
		if (!selected) sb.append("<option value=\"\" selected=\"selected\">Välj kanal</option>" );
		return sb.toString();
	}

	public boolean processDoAction(String anvandare) {
		if (ACTION_DODELETE.equals(action)) {

		} else if (ACTION_DONEW.equals(action)) {
			if (saveNewInlagg(anvandare)) {
				action = ACTION_DONEW_DONE;
				return true;
			} else {
				action = ACTION_NEW;
				return false;
			}
		} else if (ACTION_DOUPDATE.equals(action)) {
		}

		return false;
	}

	public boolean setupFromRequest(HttpServletRequest request) throws IOException{
		this.request = request;
		formErrorString = "";
		boolean dispError = true;

		htmlFileUpload = new HtmlFileUpload(request);

		action = request.getParameter(K_ACTION);
		if (action==null) action=ACTION_LIST;
		
		// Spara inte felinformation om det är en ny, eller om vi har en update, eller delete
		if (ACTION_NEW.equals(action) || ACTION_UPDATE.equals(action) || ACTION_DELETE.equals(action) || ACTION_LIST.equals(action)) dispError = false;

		if (!ACTION_NEW.equals(action)) {	//Om vi har en ny request, så initierar vi inte inlaggid
			try {  inlaggId = java.lang.Integer.parseInt(htmlFileUpload.getFieldValue(K_INLAGGID));
			} catch (java.lang.NumberFormatException e) {
				if (dispError) formErrorString = formErrorString + "Ogiltigt inläggsid.<br/>";
			}
		}

		try {  kanalId = java.lang.Integer.parseInt(htmlFileUpload.getFieldValue(K_KANALID));
		} catch (java.lang.NumberFormatException e) {
			if (dispError) formErrorString = formErrorString + "Ogiltigt kanalid.<br/>";
		}
		rubrik = htmlFileUpload.getFieldValue(K_RUBRIK);
		ingress = htmlFileUpload.getFieldValue(K_INGRESS);
		brodtext = htmlFileUpload.getFieldValue(K_BRODTEXT);
		originalFilename = htmlFileUpload.getFieldValue(K_ORIGINALFILENAME);
		try { visaTill = new java.sql.Date(SXUtil.parseDateStringToDate(htmlFileUpload.getFieldValue(K_VISATILL)).getTime());
		} catch (java.text.ParseException e) {
			if (dispError) formErrorString = formErrorString + "Ogiltigt visningsdatum.<br/>";
		}

		if (formErrorString.isEmpty()) return true; else return false;

	}

	//Spara  nytt inlägg,, och skapa därför ny inlaggId
	public boolean saveNewInlagg(String anvandareKort)  {
		boolean ret = true;
		String savedFileName = null;
		if (kanalId == null || rubrik==null || anvandareKort==null) return false;
		try {
			con.setAutoCommit(false);
			// Ta först fram ett nytt inläggsid
			ResultSet rs = con.createStatement().executeQuery("select coalesce(max(inlaggid),0) + 1 from intrainlagg");
			if (rs.next()) inlaggId=rs.getInt(1); else throw new SQLException("Kan inte få inläggid från tabell intrainlägg");

			// Om det finns en uppladdad fil - Spara den!
			// Om vi får SQLException så finn filen sparad, därför måste vi försöka radera den i catch-satsen

			savedFileName = FILEPREFIX+inlaggId;

			if (htmlFileUpload.getOriginalFileName() != null) {
				htmlFileUpload.saveFile(FILEPATH + savedFileName);
//				htmlFileUpload.setSavePath("dokument/");
//				htmlFileUpload.doUploadFile(request,FILEPREFIX+inlaggId);
			}


			PreparedStatement ps = con.prepareStatement("insert into intrainlagg (inlaggid, kanalid, rubrik, ingress, brodtext, filename, originalfilename, contenttype, visatill, anvandarekort) values (?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, inlaggId);
			ps.setInt(2, kanalId);
			ps.setString(3, rubrik);
			ps.setString(4, ingress);
			ps.setString(5, brodtext);
			ps.setString(6, savedFileName);
			ps.setString(7, htmlFileUpload.getOriginalFileName());
			ps.setString(8, htmlFileUpload.getContentType());
			ps.setDate(9, visaTill);
			ps.setString(10, anvandareKort);
			if (ps.executeUpdate() < 1) throw new SQLException("Kan inte spara inlägg id " + inlaggId );

			con.commit();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ret = false;
			try {
				con.rollback(); if (savedFileName != null) deleteFil(FILEPATH + savedFileName); formErrorString = formErrorString + SXUtil.toHtml(e.toString()); e.printStackTrace();
			} catch (SQLException e2) { e2.printStackTrace(); }
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
			ret = false;
			try {
				con.rollback();  formErrorString = formErrorString + SXUtil.toHtml(e.toString()); e.printStackTrace();
			} catch (SQLException e2) { e2.printStackTrace(); }
		}
		finally { 
			try {
				con.setAutoCommit(true);
			} catch (SQLException e2) { e2.printStackTrace(); }
		}

		return ret;
	}

	public boolean updateInlagg(int inlaggId) throws SQLException {
		PreparedStatement ps = con.prepareStatement("update intrainlagg set kanalid=?, rubrik=?, ingress=?, brodtext=?, filnamn=?, visatill=? where inlaggid=?");
		ps.setInt(1, kanalId);
		ps.setString(2, rubrik);
		ps.setString(3, ingress);
		ps.setString(4, brodtext);
		ps.setString(5, originalFilename);
		ps.setDate(6, visaTill);
		ps.setInt(7, inlaggId);
		if (ps.executeUpdate() < 1) throw new SQLException("Kan inte uppdatera inlägg id " + inlaggId );
		return true;
	}

	public boolean deleteInlagg(int inlaggId) throws SQLException {
		PreparedStatement ps = con.prepareStatement("delete from intrainlagg where inlaggid=?");
		ps.setInt(1, inlaggId);
		if (ps.executeUpdate() < 1) throw new SQLException("Kan inte radera inlägg id " + inlaggId );
		return true;
	}

	private void deleteFil(String fil) {
		if (fil==null) return;
		File f = new File(fil);
		f.delete();
	}

	public String getFormStringRubrik() {		return SXUtil.toHtml(rubrik);	}
	public String getRubrik() {		return rubrik;	}
	public void setRubrik(String rubrik) {		this.rubrik = rubrik;	}

	public String getFormStringBrodtext() {		return SXUtil.toHtml(brodtext);	}
	public String getBrodtext() {		return brodtext;	}
	public void setBrodtext(String brodtext) {		this.brodtext = brodtext;	}

	public String getFormStringIngress() {		return SXUtil.toHtml(ingress);	}
	public String getIngress() {		return ingress;	}
	public void setIngress(String ingress) {		this.ingress = ingress;	}

	public String getFormStringInlaggId() {	return SXUtil.toHtml(inlaggId!=null ? inlaggId.toString() : request.getParameter(K_INLAGGID)); 	}
	public Integer getInlaggId() {		return inlaggId;	}
	public void setInlaggId(Integer inlaggId) {		this.inlaggId = inlaggId;	}

	public String getFormStringKanalId() {		return SXUtil.toHtml(kanalId!=null ? kanalId.toString() : request.getParameter(K_KANALID));	}
	public Integer getKanalId() {		return kanalId;	}
	public void setKanalId(Integer kanalId) {		this.kanalId = kanalId;	}

	public String getFormStringOriginalFilname() {		return SXUtil.toHtml(originalFilename);	}
	public String getOriginalFilname() {		return originalFilename;	}
	public void setOriginalFilname(String originalFilnamn) {		this.originalFilename = originalFilnamn;	}

	public String getFormStringVisaTill() {		return SXUtil.toHtml(visaTill!=null ? SXUtil.getFormatDate(visaTill) : request.getParameter(K_VISATILL)); 	}
	public Date getVisaTill() {		return visaTill;	}
	public void setVisaTill(Date visaTill) {	this.visaTill = visaTill;	}

	public String getFormErrorString() {	return SXUtil.toStr(formErrorString); }

	public String getAction() {	return action;	}


	public class IntraInlagg {
		public int inlaggId;
		public int kanalId;
		public String kanal_rubrik = null;
		public String rubrik = null;
		public String ingress = null;
		public String brodText = null;
		public String fileName = null;
		public String contentType = null;
		public String originalFileName = null;
		public java.sql.Date visaTill = null;
		public String anvandareKort = null;
		public java.sql.Timestamp crTime = null;
	}
	public class IntraKanal {
		public IntraKanal(int kanalId, String rubrik, String beskrivning, boolean showOnStartPage) {
			this.kanalId = kanalId;
			this.beskrivning = beskrivning;
			this.rubrik = rubrik;
			this.showOnStartPage = showOnStartPage;
		}
		public int kanalId;
		public String rubrik = null;
		public String beskrivning = null;
		public boolean showOnStartPage;
	}
}
