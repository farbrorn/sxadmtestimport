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
public class PageListBest1 extends PageList {
private String levnrFilter = null;
private Integer bestnrFilter = null;
private Integer sakerhetskodFilter = null;
private String orderByStr;

	public PageListBest1(Connection con) throws SQLException{
		super(con);
		super.setPageSize(0);
		setOrderByBestnr();
	}
	
	public PageListBest1(Connection con, String levnr) throws SQLException{
		this(con);
		this.levnrFilter = levnr;
	}
	public PageListBest1(Connection con, Integer bestnr) throws SQLException{
		this(con);
		this.bestnrFilter = bestnr;
	}
	public PageListBest1(Connection con, Integer bestnr, Integer sakerhetskod) throws SQLException{
		this(con);
		this.bestnrFilter = bestnr;
		this.sakerhetskodFilter = sakerhetskod;
	}
	
	
	public void setOrderByBestnr() { orderByStr = "bestnr desc"; }
	public void setOrderByLevnr() { orderByStr = "levnr, bestnr desc"; }
	public String getOrderBy() { return orderByStr;	}
	
	@Override 
	public void getPage(int page) {
		String selectStr = "select bestnr, datum, levnr, levadr0, levadr1, levadr2, levadr3, var_ref, er_ref, leverans, marke, bekrdat, status, meddelande, sakerhetskod, antalfelinloggningar from best1";
		try {
			if (this.sakerhetskodFilter!=null) {
				super.initSql(selectStr + " where bestnr = ? and sakerhetskod = ? order by " + orderByStr); 
				super.query.setInt(1, this.bestnrFilter);				
				super.query.setInt(2, this.sakerhetskodFilter);				
			} else if (this.bestnrFilter!=null) {
				super.initSql(selectStr + " where bestnr = ? order by " + orderByStr); 
				super.query.setInt(1, this.bestnrFilter);
			} else if (this.levnrFilter!=null) {
				super.initSql(selectStr + " where levnr = ? order by " + orderByStr); 
				super.query.setString(1, this.levnrFilter);
			} else {
				super.initSql(selectStr + " order by " + orderByStr); 
			}
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getBestnr() { return super.getIntColumn(1);	}
	public Date getDatum()		{ return super.getDateColumn(2);	}
	public String getLevnr()	{ return super.getStringColumn(3);	}
	public String getLevadr0() { return super.getStringColumn(4);	}
	public String getLevadr1() { return super.getStringColumn(5);	}
	public String getLevadr2() { return super.getStringColumn(6);	}
	public String getLevadr3() { return super.getStringColumn(7);	}
	public String getVarRef()	{ return super.getStringColumn(8);	}
	public String getErRef()	{ return super.getStringColumn(9);	}
	public String getLeverans(){ return super.getStringColumn(10);	}
	public String getMarke()	{ return super.getStringColumn(11);	}
	public Date getBekrdat()	{ return super.getDateColumn(12);	}
	public String getStatus()	{ return super.getStringColumn(13);	}
	public String getMeddelande()	{ return super.getStringColumn(14);	}
	public Integer getSakerhetskod()	{ return super.getIntColumn(15);	}
	public Integer getAntalfelinloggningar()	{ return super.getIntColumn(16);	}
}
