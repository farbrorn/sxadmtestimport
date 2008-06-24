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
public class PageListKund {
private Connection con;
private int pageSize = 40;
private ResultSet rs;
private int fetchedRowsCount = 0;
private String sokStr;

	public PageListKund(DataSource ds, String sokStr) throws SQLException{
		this.con = ds.getConnection();
		this.sokStr = sokStr;
	}
	
	public void close() throws SQLException{
		if (rs!=null) rs.close();
		if (con!=null) con.close();
	}
	
	public void getPage(int page) {
		try {
			PreparedStatement query;
			if (sokStr == null) {
				query =  con.prepareStatement("select nummer, namn, adr1, adr2, adr3, tel, biltel, email, ref from kund order by nummer");  
			} else {
				sokStr = "%" + sokStr + "%";
				sokStr.replace(" ", "%");
				query =  con.prepareStatement("select nummer, namn, adr1, adr2, adr3, tel, biltel, email, ref from kund " +
					" where nummer like ? or namn like ? or adr1 like ? or adr2 like ? or adr3 like ? or ref like ? or tel like ? or biltel like ? order by nummer");  
				query.setString(1, sokStr); 
				query.setString(2, sokStr);
				query.setString(3, sokStr);
				query.setString(4, sokStr);
				query.setString(5, sokStr);
				query.setString(6, sokStr);
				query.setString(7, sokStr);
				query.setString(8, sokStr);
			}
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
		} catch (SQLException sqe) { }
		return ret;
	}
	public String getNummer() {
		String ret = null;
		try {
			ret = rs.getString(1);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getNamn() {
		String ret = null;
		try {
			ret = rs.getString(2);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getAdr1() {
		String ret = null;
		try {
			ret = rs.getString(3);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getAdr2() {
		String ret = null;
		try {
			ret = rs.getString(4);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getAdr3() {
		String ret = null;
		try {
			ret = rs.getString(5);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getTel() {
		String ret = null;
		try {
			ret = rs.getString(6);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getBiltel() {
		String ret = null;
		try {
			ret = rs.getString(7);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getEmail() {
		String ret = null;
		try {
			ret = rs.getString(8);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getRef() {
		String ret = null;
		try {
			ret = rs.getString(9);
		} catch (SQLException sqe) {}
		return ret;
	}
	
}
