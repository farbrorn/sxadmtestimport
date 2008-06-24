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
public class PageListFaktura1 {
private Connection con;
private int pageSize = 40;
private ResultSet rs;
private int fetchedRowsCount = 0;
private String kundnr;

	public PageListFaktura1(DataSource ds, String kundnr) throws SQLException{
		this.con = ds.getConnection();
		this.kundnr = kundnr;
	}
	
	public void close() throws SQLException{
		if (rs!=null) rs.close();
		if (con!=null) con.close();
	}
	
	public void getPage(int page) {
		try {
			PreparedStatement query =  con.prepareStatement("select f1.faktnr, f1.datum, f1.namn, f1.t_attbetala, u1.ordernr, u1.marke from faktura1 f1 left outer join utlev1 u1 on (u1.faktnr=f1.faktnr) where f1.kundnr = ? order by f1.faktnr desc");  
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
	public Date getDatum() {
		Date ret = null;
		try {
			ret = rs.getDate(2);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getNamn() {
		String ret = null;
		try {
			ret = rs.getString(3);
		} catch (SQLException sqe) {}
		return ret;
	}
	public Double getAttbetala() {
		Double ret = null;
		try {
			ret = rs.getDouble(4);
		} catch (SQLException sqe) {}
		return ret;
	}
	public Integer getOrdernr() {
		Integer ret = null;
		try {
			ret = rs.getInt(5);
		} catch (SQLException sqe) {}
		return ret;
	}
	public String getMarke() {
		String ret = null;
		try {
			ret = rs.getString(6);
		} catch (SQLException sqe) {}
		return ret;
	}
	
}
