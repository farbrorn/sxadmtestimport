
package se.saljex.sxserver.web;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.sql.DataSource;
import se.saljex.sxserver.SXUtil;

/**
 *
 * @author ulf
 */
public class PageListStatFaktura2  extends PageList{
private String kundnr;
private Date frdat;
private Date tidat;
private String orderByStr;
private String orderBy;
private static final String ORDERBYSUMMA = "sum(summa) desc";
private static final String ORDERBYANTALKOPTA = "sum(lev) desc";
private static final String ORDERBYANTALKOP = "count(*) desc";
private PreparedStatement qart;

	public PageListStatFaktura2(DataSource ds, String kundnr) throws SQLException{
		super(ds);
		this.kundnr = kundnr;
		setOrderBySumma();
		qart =  super.con.prepareStatement("select namn from artikel where nummer = ?");  
		setPeriod((Date)null,(Date)null);
	}
	
	public void setOrderBySumma() { orderBy = "summa"; orderByStr = ORDERBYSUMMA; }
	public void setOrderByAntalKopta() { orderBy = "antalkopta"; orderByStr = ORDERBYANTALKOPTA; }
	public void setOrderByAntalKop() { orderBy = "antalkop"; orderByStr = ORDERBYANTALKOP; }
	
	public String getOrderBy() {
		return this.orderBy;
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
			cal = SXUtil.getTodayDate();
			cal.add(Calendar.YEAR, -1);
			fr = cal.getTime();
		}
		
		if (ti == null) {
			cal = SXUtil.getTodayDate();
			ti = cal.getTime(); 
		}
		this.frdat = fr;
		this.tidat = ti;
		
	}
	
	public String getFrdatStr() {
		return SXUtil.getFormatDate(frdat);
	}
	public String getTidatStr() {
		return SXUtil.getFormatDate(tidat);
	}
	
	
	@Override 
	public void getPage(int page) {
		try {
			super.initSql("select f2.artnr, sum(f2.summa), sum(f2.lev), count(*) from faktura1 f1, faktura2 f2 where f1.faktnr = f2.faktnr and f1.kundnr = ? and f1.datum between ? and ? and f2.artnr not like '*%' and f2.lev <> 0 group by f2.artnr order by " + orderByStr);
			setupQueryParameter(super.query);
			super.getPage(page);		
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	
	
	private void setupQueryParameter(PreparedStatement q) throws SQLException {
		q.setString(1, kundnr);
		q.setDate(2, new java.sql.Date(frdat.getTime() ));
		q.setDate(3, new java.sql.Date(tidat.getTime() ));		
	}
	
	public String getArtnr() {
		return (String)super.getColumn(1);
	}
	
	public String getNamn() {
		String ret = null;
		ResultSet r;
		try {
			qart.setString(1, getArtnr());
			r = qart.executeQuery();
			r.next();
			ret = r.getString(1);
			if (ret == null) ret = "";
		} catch (SQLException sqe) { System.out.println(sqe.toString()) ;}
		return ret;
	}
	
	public Double getSumma() {
		return (Double)super.getColumn(2);
	}
	public Double getAntalKopta() {
		return (Double)super.getColumn(3);
	}
	
	public Integer getAntalKop() {
		return ((BigDecimal)super.getColumn(4)).intValue();
	}
	
}
