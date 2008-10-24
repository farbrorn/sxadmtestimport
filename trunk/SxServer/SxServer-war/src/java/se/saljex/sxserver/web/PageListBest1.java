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
public class PageListBest1 extends PageList {
private String levnrFilter = null;
private Integer bestnrFilter = null;
private Integer sakerhetskodFilter = null;
private String orderByStr;

	public PageListBest1(DataSource ds) throws SQLException{
		super(ds);
		super.setPageSize(0);
		setOrderByBestnr();
	}
	
	public PageListBest1(DataSource ds, String levnr) throws SQLException{
		this(ds);
		this.levnrFilter = levnr;
	}
	public PageListBest1(DataSource ds, Integer bestnr) throws SQLException{
		this(ds);
		this.bestnrFilter = bestnr;
	}
	public PageListBest1(DataSource ds, Integer bestnr, Integer sakerhetskod) throws SQLException{
		this(ds);
		this.bestnrFilter = bestnr;
		this.sakerhetskodFilter = sakerhetskod;
	}
	
	
	public void setOrderByBestnr() { orderByStr = "bestnr desc"; }
	public void setOrderByLevnr() { orderByStr = "levnr, bestnr desc"; }
	public String getOrderBy() { return orderByStr;	}
	
	@Override 
	public void getPage(int page) {
		String selectStr = "select bestnr, datum, levnr, levadr0, levadr1, levadr2, levadr3, var_ref, er_ref, leverans, marke, bekrdat, status, meddelande, sakerhetskod from best1";
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
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getBestnr() { return (Integer)super.getColumn(1);	}
	public Date getDatum()		{ return (java.util.Date)super.getColumn(2);	}
	public String getLevnr()	{ return (String)super.getColumn(3);	}
	public String getLevadr0() { return (String)super.getColumn(4);	}
	public String getLevadr1() { return (String)super.getColumn(5);	}
	public String getLevadr2() { return (String)super.getColumn(6);	}
	public String getLevadr3() { return (String)super.getColumn(7);	}
	public String getVarRef()	{ return (String)super.getColumn(8);	}
	public String getErRef()	{ return (String)super.getColumn(9);	}
	public String getLeverans(){ return (String)super.getColumn(10);	}
	public String getMarke()	{ return (String)super.getColumn(11);	}
	public Date getBekrdat()	{ return (java.util.Date)super.getColumn(12);	}
	public String getStatus()	{ return (String)super.getColumn(13);	}
	public String getMeddelande()	{ return (String)super.getColumn(14);	}
	public Integer getSakerhetskod()	{ return (Integer)super.getColumn(15);	}
}
