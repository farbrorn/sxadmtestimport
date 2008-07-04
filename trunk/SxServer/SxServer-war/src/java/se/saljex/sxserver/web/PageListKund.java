/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.SQLException;
import javax.sql.DataSource;
import se.saljex.sxserver.SXUtil;

/**
 *
 * @author ulf
 */
public class PageListKund extends PageList{
private String sokStr;

	public PageListKund(DataSource ds, String sokStr) throws SQLException{
		super(ds);
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
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public String getNummer() {
		return (String)super.getColumn(1);
	}
	public String getNamn() {
		return (String)super.getColumn(2);
	}
	public String getAdr1() {
		return (String)super.getColumn(3);
	}
	public String getAdr2() {
		return (String)super.getColumn(4);
	}
	public String getAdr3() {
		return (String)super.getColumn(5);
	}
	public String getTel() {
		return (String)super.getColumn(6);
	}
	public String getBiltel() {
		return (String)super.getColumn(7);
	}
	public String getEmail() {
		return (String)super.getColumn(8);
	}
	public String getRef() {
		return (String)super.getColumn(9);
	}
	
}
