/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */
public class PageListBetjour extends PageList {
private String kundnr;
private Date tidat;

	public PageListBetjour(Connection con, String kundnr) throws SQLException{
		super(con);
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
	public String getTidatStr() {
		if (tidat == null) {
			return "Alla";
		} else {
			return SXUtil.getFormatDate(tidat);
		}
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
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getFaktnr() {	return super.getIntColumn(1);	}
	public Date getBetdat() {		return (java.util.Date)super.getDateColumn(2);	}
	public String getBetsatt() {	return super.getStringColumn(3);	}
	public Double getBet() {		return super.getDoubleColumn(4);	}
	
}
