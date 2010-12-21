/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.SQLException;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */
public class PageListKund extends PageList{
private String sokStr;

	public PageListKund(Connection con, String sokStr) throws SQLException{
		super(con);
		super.setPageSize(40);
		this.sokStr = sokStr;
	}
	
	@Override 
	public void getPage(int page) {
		try {
			if (sokStr == null) {
				super.initSql("select nummer, namn, adr1, adr2, adr3, tel, biltel, email, ref from kund order by nummer");  
			} else {
				sokStr = "%" + sokStr + "%";
				sokStr.replace(" ", "%");
				super.initSql("select nummer, namn, adr1, adr2, adr3, tel, biltel, email, ref from kund " +
					" where nummer like ? or namn like ? or adr1 like ? or adr2 like ? or adr3 like ? or ref like ? or tel like ? or biltel like ? order by nummer");  
				super.query.setString(1, sokStr); 
				super.query.setString(2, sokStr);
				super.query.setString(3, sokStr);
				super.query.setString(4, sokStr);
				super.query.setString(5, sokStr);
				super.query.setString(6, sokStr);
				super.query.setString(7, sokStr);
				super.query.setString(8, sokStr);
			}
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public String getNummer() {	return super.getStringColumn(1);	}
	public String getNamn() {		return super.getStringColumn(2);	}
	public String getAdr1() {		return super.getStringColumn(3);	}
	public String getAdr2() {		return super.getStringColumn(4);	}
	public String getAdr3() {		return super.getStringColumn(5);	}
	public String getTel() {		return super.getStringColumn(6);	}
	public String getBiltel() {	return super.getStringColumn(7);	}
	public String getEmail() {		return super.getStringColumn(8);	}
	public String getRef() {		return super.getStringColumn(9);	}
	
}
