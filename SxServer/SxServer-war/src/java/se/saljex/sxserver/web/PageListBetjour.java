/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import se.saljex.sxserver.SXUtil;

/**
 *
 * @author ulf
 */
public class PageListBetjour {
private Connection con;
private int pageSize = 40;
private ResultSet rs;
private int fetchedRowsCount = 0;
private String kundnr;

	public PageListBetjour(DataSource ds, String kundnr) throws SQLException{
		this.con = ds.getConnection();
		this.kundnr = kundnr;
		
	}
	public void close() throws SQLException{
		if (rs!=null) rs.close();
		if (con!=null) con.close();
	}
	
	public void getPage(int page) {
		try {
			PreparedStatement query =  con.prepareStatement("select faktnr, betdat, betsatt, bet from betjour where kundnr = ? order by betdat desc, betsatt, faktnr");  
			query.setString(1, kundnr);
			rs = query.executeQuery();  
			if (page > 1) {
				for (int cn=1; cn<=(page-1)*pageSize; cn++) {
					if (!rs.next()) { break; }
				}
			}
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
		fetchedRowsCount = 0;
	}	
	public boolean next() {
		boolean ret = false;
		if (rs == null) { getPage(1); }
		fetchedRowsCount++;
		if (fetchedRowsCount > pageSize)  { return false; }
		try {
			ret = rs.next();
		} catch (SQLException sqe) {}
		return ret;
	}
	public Integer getFaktnr() {
		Integer ret = null;
		try {
			ret = rs.getInt(1);
		} catch (SQLException sqe) {}
		return ret;
	}
	public Date getBetdat() {
		Date ret = null;
		try {
			ret = rs.getDate(2);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getBetsatt() {
		String ret = null;
		try {
			ret = rs.getString(3);
		} catch (SQLException sqe) {}
		return ret;
	}
	public Double getBet() {
		Double ret = null;
		try {
			ret = rs.getDouble(4);
		} catch (SQLException sqe) {}
		return ret;
	}
	
}
