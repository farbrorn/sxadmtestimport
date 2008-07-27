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
public class PageListKalender extends PageList {

	public PageListKalender(DataSource ds) throws SQLException{
		super(ds);
		super.setPageSize(0);
	}
	
	
	@Override 
	public void getPage(int page) {
		try {
			super.initSql("select f_dat, f_tid, kmemo from kalender order by f_dat desc, f_tid desc"); 
			super.getPage(page);
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Date getF_Dat() {
		return (java.util.Date)super.getColumn(1);
	}
	public Date getF_Tid() {
		return (java.util.Date)super.getColumn(2);
	}
	public String getKmemo() {
		return (String)super.getColumn(3);
	}
	
}
