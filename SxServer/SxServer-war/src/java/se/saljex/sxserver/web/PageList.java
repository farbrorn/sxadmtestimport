
package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import se.saljex.sxserver.SXUtil;

/**
 *
 * @author ulf
 */
public class PageList {
protected Connection con;
private int pageSize = 20;
protected ResultSet rs;
private int fetchedRowsCount = 0;
protected PreparedStatement query;
private int currentPage;
private boolean hasMoreRows = true;

	public PageList(DataSource ds) throws SQLException{
		this.con = ds.getConnection();
	}
		
	
	public void close() throws SQLException{
		if (rs!=null) rs.close();
		if (con!=null) con.close();
	}


	protected PreparedStatement getQuery() {
		return query;
	}
	
	public void setPageSize(int ps) {
		pageSize = ps;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public int getNextPage() {
		// Returnerar nästa sidnummer om hasMoreRows = tru.
		// Fungerar bara efter att sista raden för aktuell sida är inläst med next()
		if (hasMoreRows) { return currentPage+1; } else { return 0; }
	}
	public int getPreviousPage() {
		if (currentPage > 1) { return currentPage-1; } else { return currentPage; }
	}
	
	protected ResultSet getRs() {
		return rs;
	}
	
	public void initSql(String sql) throws SQLException {
		query =  con.prepareStatement(sql);
	}
	
	
	public void getPage(int page) {
		int cn = 0;
		if (page <= 0) { page = 1; }
		try {
			rs = query.executeQuery();  
			if (page > 1) {
				for (cn=1; cn<=(page-1)*pageSize; cn++) {
					if (!rs.next()) { hasMoreRows = false; break; }
				}
			}
		} catch (SQLException sqe) { SXUtil.log("Exception i getArtdetaljer" + sqe.toString()); }
		if (pageSize == 0) { 
			currentPage = 1; 
		} else {
			currentPage = (int)Math.floor(cn / pageSize)+1;
		}
		fetchedRowsCount = 0;
	}	
	
	
	public boolean next() {
		boolean ret = false;
		if (rs == null) { getPage(1); }
		fetchedRowsCount++;
		if (fetchedRowsCount > pageSize && pageSize != 0)  { return false; }
		try {
			ret = rs.next();
			if (!ret) { hasMoreRows = false; }
		} catch (Exception sqe) {}
		return ret;
	}
	
	public Object getColumn(int id) {
		Object ret = null;
		try {
			ret = rs.getObject(id);
		} catch (SQLException sqe) {}
		return ret;
	}


}
