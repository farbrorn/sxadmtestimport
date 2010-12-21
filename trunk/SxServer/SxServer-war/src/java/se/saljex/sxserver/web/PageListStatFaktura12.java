
package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */

public class PageListStatFaktura12 extends PageList {
private String kundnr;
private Date frdat;
private Date tidat;
private String artnr;

	public PageListStatFaktura12(Connection con, String kundnr, String artnr) throws SQLException{
		super(con);
		super.setPageSize(0);
		this.kundnr = kundnr;
		this.artnr = artnr;
		setPeriod((Date)null,(Date)null);
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
	
	public String getFrdatStr() {
		return SXUtil.getFormatDate(frdat);
	}
	public String getTidatStr() {
		return SXUtil.getFormatDate(tidat);
	}
	public void setPeriod(Date fr, Date ti) {
		Calendar cal;
		if (fr == null) {
			cal = SXUtil.getTodaySQLDate();
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
	
	@Override 
	public void getPage(int page) {
		try {
			super.initSql("select f1.faktnr, f1.datum, f2.lev, f2.enh, f2.pris, f2.rab, f2.summa from faktura1 f1, faktura2 f2 where f1.faktnr = f2.faktnr and f1.kundnr = ? and f1.datum between ? and ? and f2.artnr = ? and f2.lev <> 0 order by f2.faktnr desc, f2.pos");
			setupQueryParameter(super.query);
			
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getArtdetaljer" + sqe.toString()); }
	}	
	
	private void setupQueryParameter(PreparedStatement q) throws SQLException {
		q.setString(1, kundnr);
		q.setDate(2, new java.sql.Date(frdat.getTime() ));
		q.setDate(3, new java.sql.Date(tidat.getTime() ));
		q.setString(4, artnr);
	}
	

	public Integer getFaktnr() {		return super.getIntColumn(1);	}
	public java.util.Date getDatum() {return super.getDateColumn(2);	}
	public Double getLev() {			return super.getDoubleColumn(3);	}
	public String getEnh() {			return super.getStringColumn(4);	}
	public Double getPris() {			return super.getDoubleColumn(5);	}
	public Double getRab() {			return super.getDoubleColumn(6);	}
	public Double getSumma() {			return super.getDoubleColumn(7);	}

	
}

