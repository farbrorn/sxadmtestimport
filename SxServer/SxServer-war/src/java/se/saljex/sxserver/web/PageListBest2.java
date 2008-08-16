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
public class PageListBest2 extends PageList {
private Integer bestnrFilter = null;
private String orderByStr;

	public PageListBest2(DataSource ds) throws SQLException{
		super(ds);
		super.setPageSize(0);
		setOrderByBestnr();
	}
	
	public PageListBest2(DataSource ds, Integer bestnr) throws SQLException{
		this(ds);
		this.bestnrFilter = bestnr;
	}
	
	
	public void setOrderByBestnr() { orderByStr = "bestnr, rad"; }
	public void setOrderByArtnr() { orderByStr = "artnr, bestnr, rad"; }
	public String getOrderBy() { return orderByStr;	}
	
	@Override 
	public void getPage(int page) {
		String selectStr = "select bestnr, rad, enh, artnr, artnamn, bartnr, best, pris, rab, bekrdat, stjid from best1";
		try {
			if (this.bestnrFilter!=null) {
				super.initSql(selectStr + " where bestnr = ? order by " + orderByStr); 
				super.query.setInt(1, this.bestnrFilter);
			} else {
				super.initSql(selectStr + " order by " + orderByStr); 
			}
			super.getPage(page);
		} catch (SQLException sqe) { SXUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getBestnr() { return (Integer)super.getColumn(1);	}
	public Integer getRad()		{ return (Integer)super.getColumn(2);	}
	public String getEnh()		{ return (String)super.getColumn(3);	}
	public String getArtnr()	{ return (String)super.getColumn(4);	}
	public String getArtnamn() { return (String)super.getColumn(5);	}
	public String getBartnr() { return (String)super.getColumn(6);	}
	public Double getBest()		{ return (Double)super.getColumn(7);	}
	public Double getPris()		{ return (Double)super.getColumn(8);	}
	public Double getRab()		{ return (Double)super.getColumn(9);	}
	public Date getBekrdat()	{ return (java.util.Date)super.getColumn(10);	}
	public Integer getStjid()		{ return (Integer)super.getColumn(11);	}	
}
