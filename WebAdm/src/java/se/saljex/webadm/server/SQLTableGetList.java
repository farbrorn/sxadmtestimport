/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.webadm.client.rpcobject.ErrorConvertingFromResultsetException;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.ServerErrorException;

/**
 *
 * @author Ulf
 */
public class SQLTableGetList {
	protected DataSource sxadm;

	public SQLTableGetList(DataSource sxadm) {
		this.sxadm=sxadm;
	}

	public void fillList(SQLTableList list, IsSQLTable table, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		String where = "";
		String compareString="";
		String orderBy = "";
		Field[] fields;
		ArrayList<Object> parameters = new ArrayList();

		switch (compareType) {
			case SQLTableList.COMPARE_EQUALS: { compareString = "="; break; }
			case SQLTableList.COMPARE_GREATER: { compareString = ">"; break; }
			case SQLTableList.COMPARE_GREATER_EQUALS: { compareString = ">="; break; }
			case SQLTableList.COMPARE_LESS: { compareString = "<"; break; }
			case SQLTableList.COMPARE_LESS_EQUALS: { compareString = "<="; break; }
			case SQLTableList.COMPARE_SUPERSOK: { compareString = "like"; break; }
			case SQLTableList.COMPARE_NONE: { compareString = ""; break; }
			default:	throw (new ServerErrorException("Felaktig compare-värde: " + compareType));
		}
		Object retTerm;
		StringBuilder b = new StringBuilder();
		StringBuilder bsub = new StringBuilder();

		if (!compareString.isEmpty() && sokString!=null) {
			if (compareType == SQLTableList.COMPARE_SUPERSOK) {
				List<String> cols = SQLTableHandler.getStringColumnNames(table);
				String[] terms = ((String)sokString).split(" ");
				for (String term : terms) {
					bsub = new StringBuilder();
					for (String col : cols) {
						if (bsub.length()>0) bsub.append(" or ");
						bsub.append("upper(");
						bsub.append(col);
						bsub.append(")");
						bsub.append(" like upper(?)");
						parameters.add("%" + term + "%");
					}
					if (b.length()>0 && bsub.length()>0) b.append(" and ");
					if (bsub.length()>0) b.append(" (" + bsub.toString() + ") ");
				}
				where = b.toString();
System.out.print(where);
				if (where==null) where = "";
			} else {
				if (SQLTableHandler.isColumnNameValid(sokField, table)) {
					where = where + " "+ sokField + compareString + " ?";
				} else throw new ServerErrorException("Ogiltigt kolumnnamn: " + sokField);
				try {
					retTerm = SQLTableHandler.getObjectAsColumnType(table, sokField, sokString);
				}catch (ParseException e) { throw new ServerErrorException("Fel vid omvandling av sökvärde till typ definerad i datatabell. Kolumn " + sokField + " värde: " + sokString); }
				parameters.add(retTerm);
			}
		}
		


		if (sortField!=null) if (!sortField.isEmpty()){
			if (SQLTableHandler.isColumnNameValid(sortField, table)) {
				orderBy = orderBy + " " + sortField + (sortOrder == SQLTableList.SORT_DESCANDING ? " desc" : "");
			} else throw new ServerErrorException("Ogiltigt kolumnnamn för Order By: " + sortField);
		}

		//Lägg till grundsortering på primary key
		String primaryKeyOrderBy = SQLTableHandler.getPrimaryKeyOrderByString(table, sortOrder==SQLTableList.SORT_DESCANDING);
		if (!SXUtil.isEmpty(orderBy) && !SXUtil.isEmpty(primaryKeyOrderBy)) orderBy = orderBy + ", ";
		orderBy = orderBy + " " + primaryKeyOrderBy;

		Connection con=null;


		try {
			con = sxadm.getConnection();
			SQLTableHandler.fillSQLTable(con, table, list, where, orderBy, null, parameters.toArray(), true, sortOrder==SQLTableList.SORT_DESCANDING, offset, limit);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} catch (ErrorConvertingFromResultsetException ee) {
			throw new ServerErrorException();
		} finally {
			try { con.close(); } catch (Exception e) {}

		}
	}


}
