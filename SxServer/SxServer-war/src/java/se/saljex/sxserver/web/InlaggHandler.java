package se.saljex.sxserver.web;

import java.io.File;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import se.saljex.sxserver.SXUtil;

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
	public static final String K_FILNAMN = "filnamn";

	public static final String K_ACTION = "action";

	public static final String ACTION_LIST = "list";
	public static final String ACTION_NEW = "new";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_DELETE = "delete";
	public static final String ACTION_DONEW = "donew";
	public static final String ACTION_DOUPDATE = "doupdate";
	public static final String ACTION_DODELETE = "dodelete";

	private String action=null;
	private Integer inlaggId=null;
	private Integer kanalId=null;
	private String rubrik=null;
	private String ingress=null;
	private String brodtext=null;
	private String filnamn=null;
	private java.sql.Date visaTill=null;

	private HttpServletRequest request = null;

	public InlaggHandler(Connection con) {
		this.con = con;
	}

	public ResultSet getKanaler() throws SQLException {
		return con.createStatement().executeQuery("select kanalid, rubrik, beskrivning from intrakanaler order by rubrik");
	}


	public  ResultSet getInlaggResultSetByKanalId(int kanalId) throws SQLException {
		return getInlaggResultSet("i.kanalid=" + kanalId);
	}
	public  ResultSet getInlaggResultSetByInlaggId(int inlaggId) throws SQLException {
		return getInlaggResultSet("i.inlaggid=" + inlaggId);
	}
	public  ResultSet getInlaggResultSet() throws SQLException {
		return getInlaggResultSet("1=1");
	}
	private ResultSet getInlaggResultSet(String filterStr) throws SQLException {
		return con.createStatement().executeQuery("select i.inlaggid, i.kanalid, k.rubrik as kanalrubrik, i.rubrik, i.ingress, i.brodtext, i.filnamn, i.visatill, i.anvandarekort, i.crtime  from intrainlagg i left outer join intrakanaler k where " + filterStr + " order by i.kanalid, i.inlaggid");
	}
	
	public String getKanalerOptionList() throws SQLException {
		boolean selected = false;
		ResultSet rs = getKanaler();
		StringBuilder sb = new StringBuilder();
		while (rs.next()) {
			sb.append("<option value=\"" + rs.getInt(1) + "\"");
			if (kanalId != null && kanalId.equals(rs.getInt(1))) {
				sb.append(" selected=\"selected\"");
				selected = true;
			}
			sb.append(">" + SXUtil.toHtml(rs.getString(2)+ "</option>"));
		}
		if (!selected) sb.append("<option value=\"\" selected=\"selected\">Välj kanal</option>" );
		return sb.toString();
	}


	public void setupFromRequest(HttpServletRequest request) {
		this.request = request;

		action = request.getParameter(K_ACTION);
		if (action==null) action=ACTION_LIST;
		if (!ACTION_NEW.equals(action)) {	//Om vi har en ny request, så initierar vi inte några variabler utan lämnar tomt för input
			try {  inlaggId = java.lang.Integer.parseInt(request.getParameter(K_INLAGGID));  } catch (java.lang.NumberFormatException e) {}
			try {  kanalId = java.lang.Integer.parseInt(request.getParameter(K_KANALID));  } catch (java.lang.NumberFormatException e) {}
			rubrik = request.getParameter(K_RUBRIK);
			ingress = request.getParameter(K_INGRESS);
			brodtext = request.getParameter(K_BRODTEXT);
			filnamn = request.getParameter(K_FILNAMN);
			try { visaTill = new java.sql.Date(SXUtil.parseDateStringToDate(request.getParameter(K_VISATILL)).getTime()); } catch (java.text.ParseException e) {}
		}
	}

	//Spara  nytt inlägg,, och skapa därför ny inlaggId
	public boolean saveNewInlagg(String anvandareKort) throws SQLException, java.io.IOException {
		if (kanalId == null || rubrik==null || anvandareKort==null) return false;
		HtmlFileUpload h = new HtmlFileUpload();
		try {
			con.setAutoCommit(false);
			// Ta först fram ett nytt inläggsid
			ResultSet rs = con.createStatement().executeQuery("select coalesce(max(inlaggid),0) + 1 from intrainlagg");
			if (rs.next()) inlaggId=rs.getInt(1); else throw new SQLException("Kan inte få inläggid från tabell intrainlägg");

			// Om det finns en uppladdad fil - Spara den!
			// Om vi får SQLException så finn filen sparad, därför måste vi försöka radera den i catch-satsen
			if (request != null) {
				h.setSavePath("dokument/");
				h.doUpload(request);
			}


			PreparedStatement ps = con.prepareStatement("insert into intrainlagg (inlaggid, kanalid, rubrik, ingress, brodtext, filnamn, visatill, anvandarekort) values (?,?,?,?,?,?,?,?)");
			ps.setInt(1, inlaggId);
			ps.setInt(2, kanalId);
			ps.setString(3, rubrik);
			ps.setString(4, ingress);
			ps.setString(5, brodtext);
			ps.setString(6, h.getFilename());
			ps.setDate(7, visaTill);
			ps.setString(8, anvandareKort);
			if (ps.executeUpdate() < 1) throw new SQLException("Kan inte spara inlägg id " + inlaggId );


			con.commit();
		} 
		catch (SQLException e) { con.rollback(); deleteFil(h.getFilePathAndName()); throw e; }
		catch (java.io.IOException e) { con.rollback();  throw e; }
		finally { con.setAutoCommit(true); }

		return true;
	}

	public boolean updateInlagg(int inlaggId) throws SQLException {
		PreparedStatement ps = con.prepareStatement("update intrainlagg set kanalid=?, rubrik=?, ingress=?, brodtext=?, filnamn=?, visatill=? where inlaggid=?");
		ps.setInt(1, kanalId);
		ps.setString(2, rubrik);
		ps.setString(3, ingress);
		ps.setString(4, brodtext);
		ps.setString(5, filnamn);
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

	public String getBrodtext() {		return brodtext;	}
	public void setBrodtext(String brodtext) {		this.brodtext = brodtext;	}

	public String getIngress() {		return ingress;	}
	public void setIngress(String ingress) {		this.ingress = ingress;	}

	public Integer getInlaggId() {		return inlaggId;	}
	public void setInlaggId(Integer inlaggId) {		this.inlaggId = inlaggId;	}

	public Integer getKanalId() {		return kanalId;	}
	public void setKanalId(Integer kanalId) {		this.kanalId = kanalId;	}

	public String getRubrik() {		return rubrik;	}
	public void setRubrik(String rubrik) {		this.rubrik = rubrik;	}

	public String getFilnamn() {		return filnamn;	}
	public void setFilnamn(String filnamn) {		this.filnamn = filnamn;	}

	public Date getVisaTill() {		return visaTill;	}
	public void setVisaTill(Date visaTill) {	this.visaTill = visaTill;	}

	public String getAction() {	return action;	}


}
