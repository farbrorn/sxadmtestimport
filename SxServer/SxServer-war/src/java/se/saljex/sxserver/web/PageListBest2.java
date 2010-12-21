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
public class PageListBest2 extends PageList {
private Integer bestnrFilter = null;
private String orderByStr;

	public PageListBest2(Connection con) throws SQLException{
		super(con);
		super.setPageSize(0);
		setOrderByBestnr();
	}
	
	public PageListBest2(Connection con, Integer bestnr) throws SQLException{
		this(con);
		this.bestnrFilter = bestnr;
	}
	
	
	public void setOrderByBestnr() { orderByStr = "bestnr, rad"; }
	public void setOrderByArtnr() { orderByStr = "artnr, bestnr, rad"; }
	public String getOrderBy() { return orderByStr;	}
	
	@Override 
	public void getPage(int page) {
		String selectStr = "select bestnr, rad, enh, artnr, artnamn, bartnr, best, pris, rab, bekrdat, stjid from best2";
		try {
			if (this.bestnrFilter!=null) {
				super.initSql(selectStr + " where bestnr = ? order by " + orderByStr); 
				super.query.setInt(1, this.bestnrFilter);
			} else {
				super.initSql(selectStr + " order by " + orderByStr); 
			}
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}	

	public Integer getBestnr() { return super.getIntColumn(1);	}
	public Integer getRad()		{ return super.getIntColumn(2);	}
	public String getEnh()		{ return super.getStringColumn(3);	}
	public String getArtnr()	{ return super.getStringColumn(4);	}
	public String getArtnamn() { return super.getStringColumn(5);	}
	public String getBartnr() { return super.getStringColumn(6);	}
	public Double getBest()		{ return super.getDoubleColumn(7);	}
	public Double getPris()		{ return super.getDoubleColumn(8);	}
	public Double getRab()		{ return super.getDoubleColumn(9);	}
	public Date getBekrdat()	{ return super.getDateColumn(10);	}
	public Integer getStjid()		{ return super.getIntColumn(11);	}	
}
