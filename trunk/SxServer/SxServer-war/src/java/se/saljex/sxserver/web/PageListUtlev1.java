/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */
public class PageListUtlev1 extends PageList {
private String kundnr;
private String marke = null;
private String originalMarke;
private Date frdat;
private Date tidat;
private String orderByStr;

	public PageListUtlev1(Connection con, String kundnr) throws SQLException{
		super(con);
		super.setPageSize(40);
		this.kundnr = kundnr;
		setOrderByOrdernr();
		setPeriod((Date)null,(Date)null);
	}
	
	
	public void setOrderBy(String orderBy) { 
		if ("ordernr".equals(orderBy)) setOrderByOrdernr();
		else if ("marke".equals(orderBy)) setOrderByMarke();
		else setOrderByOrdernr();
	}
	public void setOrderByOrdernr() { orderByStr = "ordernr desc"; }
	public void setOrderByMarke() { orderByStr = "marke, ordernr desc"; }
	
	public void setMarkeFilter(String marke) {
		this.originalMarke = marke;
		this.marke = "%"+marke.replace(" ", "%")+"%";
	}
	
	public void setPeriod(String frdatStr, String tidatStr) {
		java.util.Date fr = null;
		try {
			fr = java.sql.Date.valueOf(frdatStr);
		} catch (Exception e1) {}

		java.util.Date ti = null;
		try {
			ti = java.sql.Date.valueOf(tidatStr);
		} catch (Exception e1) {}
		setPeriod(fr, ti);
	}
	
	public void setPeriod(Date fr, Date ti) {
		Calendar cal;
		if (fr == null) {
			fr = getMaxDatum();
			if (fr == null) {
				cal = SXUtil.getTodaySQLDate();
			} else { 
				cal = SXUtil.getSQLCalendarDateFromDate(fr); 
			}
			cal.add(Calendar.YEAR, -1);
			fr = cal.getTime();
		}
		
		if (ti == null) {
			cal = SXUtil.getTodaySQLDate();
			ti = cal.getTime(); 
		}
		this.frdat = fr;
		this.tidat = ti;
		
	}
	
	// Hämtar datumet för senaste utleveransen
	public Date getMaxDatum() {
		Date retdat = null;
		try {
			PreparedStatement q =  super.con.prepareStatement("select max(datum) from utlev1 where kundnr = ?");  
			q.setString(1, this.kundnr);
			ResultSet r = q.executeQuery();
			r.next();
			retdat = r.getDate(1);
		} catch (Exception e) {}
		return retdat;

	}
	
	public String getFrdatStr() {
		return SXUtil.getFormatDate(frdat);
	}
	public String getTidatStr() {
		return SXUtil.getFormatDate(tidat);
	}
	public String getOriginalMarke() {
		return originalMarke;
	}
	public String getOrderBy() {
		return orderByStr;
	}
	
	@Override 
	public void getPage(int page) {
		String whereStr = "";
		if (marke != null) {
			whereStr = " and marke like ?";
		}
		try {
			super.initSql("select ordernr, datum, marke from utlev1 where kundnr = ? and datum between ? and ? " + whereStr + " order by " + orderByStr); 
			super.query.setString(1, kundnr);
			super.query.setDate(2, new java.sql.Date(frdat.getTime()));
			super.query.setDate(3, new java.sql.Date(tidat.getTime()));
			if (marke != null) {
				super.query.setString(4, marke);
			}
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getOrdernr() {	return super.getIntColumn(1);	}
	public Date getDatum() {		return super.getDateColumn(2);	}
	public String getMarke() {		return super.getStringColumn(3);	}
}
