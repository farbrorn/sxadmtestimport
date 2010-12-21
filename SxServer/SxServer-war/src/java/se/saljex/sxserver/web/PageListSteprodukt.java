package se.saljex.sxserver.web;

import java.sql.Connection;
import java.sql.SQLException;
import se.saljex.sxserver.ServerUtil;

/**
 *
 * @author ulf
 */
public class PageListSteprodukt extends PageList {
private String sokStr;
private String sokStrSn;

	public PageListSteprodukt(Connection con) throws SQLException{
		super(con);
		super.setPageSize(40);
	}

	public void setFilter(String s) { sokStr = s; }
	public void setFilterSn(String sn) { sokStrSn = sn; }

	@Override
	public void getPage(int page) {
		String sqlSelect =	"select sn, crdat, instdatum, anvandare, artnr, modell, namn, adr1, adr2, adr3, ref, tel, mobil, epost, installatornamn, installatorkundnr, faktnr " +
							" from steprodukt";
		String sqlOrderBy = "order by sn desc";
		String sqlWhere = null;

		try {
			if (sokStrSn != null) {			//Har vi satt sn så går det före övrigt filter
				super.initSql(sqlSelect + " where sn=? " + sqlOrderBy);
				super.query.setString(1, sokStrSn);
			} else if (sokStr == null) {
				super.initSql(sqlSelect + " " + sqlOrderBy);
			} else {
				sqlWhere = " where (" +
						  "ucase(sn) like ucase(?)" +
						  " or ucase(artnr) like ucase(?)" +
						  " or ucase(modell) like ucase(?)" +
						  " or ucase(namn) like ucase(?)" +
						  " or ucase(adr1) like ucase(?)" +
						  " or ucase(adr2) like ucase(?)" +
						  " or ucase(adr3) like ucase(?)" +
						  " or ucase(referens) like ucase(?)" +
						  " or ucase(tel) like ucase(?)" +
						  " or ucase(mobil) like ucase(?)" +
						  " or ucase(epost) like ucase(?)" +
						  " or ucase(installatornamn) like ucase(?)" +
						  " or ucase(installatorkundnr) like ucase(?)" +
						  ")";
				super.initSql(sqlSelect + " " + sqlWhere + " " + sqlOrderBy);
				super.query.setString(1, sokStr);
				super.query.setString(2, sokStr);
				super.query.setString(3, sokStr);
				super.query.setString(4, sokStr);
				super.query.setString(5, sokStr);
				super.query.setString(6, sokStr);
				super.query.setString(7, sokStr);
				super.query.setString(8, sokStr);
				super.query.setString(9, sokStr);
				super.query.setString(10, sokStr);
				super.query.setString(11, sokStr);
				super.query.setString(12, sokStr);
				super.query.setString(13, sokStr);
			}
			super.getPage(page);
		} catch (SQLException sqe) { ServerUtil.log("Exception i getPage" + sqe.toString()); }
	}

	public Integer getSn() {		return super.getIntColumn(1);	}
	public java.util.Date getCrdat() {		return super.getTimestampColumn(2); }
	public java.util.Date getInstdatum() {		return super.getDateColumn(3);	}
	public String getAnvandare() {return super.getStringColumn(4);	}
	public String getArtnr() {return super.getStringColumn(5);	}
	public String getModell() {return super.getStringColumn(6);	}
	public String getNamn() {return super.getStringColumn(7);	}
	public String getAdr1() {return super.getStringColumn(8);	}
	public String getAdr2() {return super.getStringColumn(9);	}
	public String getAdr3() {return super.getStringColumn(10);	}
	public String getRef() {return super.getStringColumn(11);	}
	public String getTel() {return super.getStringColumn(12);	}
	public String getMobil() {return super.getStringColumn(13);	}
	public String getEpost() {return super.getStringColumn(14);	}
	public String getInstallatornamn() {return super.getStringColumn(15);	}
	public String getInstallatorkundnr() {return super.getStringColumn(16);	}
	public Integer getFaktNr() {return super.getIntColumn(17);	}

}
