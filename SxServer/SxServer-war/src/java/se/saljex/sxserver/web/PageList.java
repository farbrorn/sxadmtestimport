
package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import se.saljex.sxserver.ServerUtil;

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

	public PageList(Connection con) throws SQLException{
		this.con = con;
	}
		
	
	public void close() throws SQLException{
		if (query!=null) query.close(); 
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
		} catch (SQLException sqe) { ServerUtil.log("Exception i getArtdetaljer" + sqe.toString()); }
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
	
	public Integer getIntColumn(int id) {
		Integer ret = null;
		try {
			ret = rs.getInt(id);
		} catch (SQLException sqe) {}
		return ret;
	}
	public Double getDoubleColumn(int id) {
		Double ret = null;
		try {
			ret = rs.getDouble(id);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getStringColumn(int id) {
		String ret = null;
		try {
			ret = rs.getString(id);
		} catch (SQLException sqe) {}
		return ret;
	}
	public java.sql.Date getDateColumn(int id) {
		java.sql.Date ret = null;
		try {
			ret = rs.getDate(id); 
		} catch (SQLException sqe) {}
		return ret;
	}
	public java.sql.Time getTimeColumn(int id) {
		java.sql.Time ret = null;
		try {
			ret = rs.getTime(id);
		} catch (SQLException sqe) {}
		return ret;
	}
	public java.sql.Timestamp getTimestampColumn(int id) {
		java.sql.Timestamp ret = null;
		try {
			ret = rs.getTimestamp(id);
		} catch (SQLException sqe) {}
		return ret;
	}


}
