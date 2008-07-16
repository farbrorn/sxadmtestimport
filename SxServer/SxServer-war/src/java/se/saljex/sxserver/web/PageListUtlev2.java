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
public class PageListUtlev2 extends PageList {
private int ordernr;
private String kundnr;

	public PageListUtlev2(DataSource ds, String kundnr, int ordernr) throws SQLException{
		super(ds);
		super.setPageSize(0);
		this.ordernr = ordernr;
		this.kundnr = kundnr;
	}
	
	
	
	@Override 
	public void getPage(int page) {
		try {
			super.initSql("select f1.faktnr, f1.datum, f2.artnr, f2.namn, f2.lev, f2.enh, f2.pris, f2.rab, f2.summa from faktura2 f2, faktura1 f1 where f1.faktnr = f2.faktnr and f1.kundnr = ? and f2.ordernr = ? order by f2.faktnr, f2.pos"); 
			super.query.setString(1, kundnr);
			super.query.setInt(2, ordernr);
			super.getPage(page);
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getFaktnr() {
		return (Integer)super.getColumn(1);
	}
	public java.util.Date getFa1Datum() {
		return (java.util.Date)super.getColumn(2);
	}
	public String getArtnr() {
		return (String)super.getColumn(3);
	}
	public String getNamn() {
		return (String)super.getColumn(4);
	}
	public Double getLev() {
		return (Double)super.getColumn(5);
	}
	public String getEnh() {
		return (String)super.getColumn(6);
	}
	public Double getPris() {
		return (Double)super.getColumn(7);
	}
	public Double getRab() {
		return (Double)super.getColumn(8);
	}
	public Double getSumma() {
		return (Double)super.getColumn(9);
	}
	
}
