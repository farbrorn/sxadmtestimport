/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */
public class PageListKalender extends PageList {

	public PageListKalender(Connection con) throws SQLException{
		super(con);
		super.setPageSize(0);
	}
	
	
	@Override 
	public void getPage(int page) {
		try {
			super.initSql("select f_dat, f_tid, kmemo from kalender order by f_dat desc, f_tid desc"); 
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Date getF_Dat() {		return super.getDateColumn(1);	}
	public Date getF_Tid() {		return super.getDateColumn(2);	}
	public String getKmemo() {		return super.getStringColumn(3);	}
	
}
