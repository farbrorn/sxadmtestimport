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

	public PageListBetjour(DataSource ds, String kundnr) throws SQLException{
		super(ds);
		super.setPageSize(40);
		this.kundnr = kundnr;
		
	}
	
	@Override 
	public void getPage(int page) {
		try {
			super.initSql("select faktnr, betdat, betsatt, bet from betjour where kundnr = ? order by betdat desc, betsatt, faktnr"); 
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
