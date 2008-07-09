/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import se.saljex.sxserver.SXUtil;

/**
 *
 * @author ulf
 */
public class PageListBetjour extends PageList {
private String kundnr;
private Date tidat;

	public PageListBetjour(DataSource ds, String kundnr) throws SQLException{
		super(ds);
		super.setPageSize(40);
		this.kundnr = kundnr;
		setTidat((Date)null);
		
	}
	
	public void setTidat(String tidatStr) {
		java.util.Date ti = null;
		try {
			ti = java.sql.Date.valueOf(tidatStr);
		} catch (Exception e1) {}
		setTidat(ti);
	}
	
	
	public void setTidat(Date ti) {
		this.tidat = ti;
	}
	
	@Override 
	public void getPage(int page) {
		String whereStr;
		if (tidat == null) {
			whereStr = "kundnr = ?";
		} else {
			whereStr = "kundnr = ? and betdat <= '" + SXUtil.getFormatDate(tidat) + "'";
		}
		try {
			super.initSql("select faktnr, betdat, betsatt, bet from betjour where " + whereStr + " order by betdat desc, betsatt, faktnr"); 
			super.query.setString(1, kundnr);
			super.getPage(page);
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getFaktnr() {
		return (Integer)super.getColumn(1);
	}
	public Date getBetdat() {
		return (java.util.Date)super.getColumn(2);
	}
	public String getBetsatt() {
		return (String)super.getColumn(3);
	}
	public Double getBet() {
		return (Double)super.getColumn(4);
	}
	
}
