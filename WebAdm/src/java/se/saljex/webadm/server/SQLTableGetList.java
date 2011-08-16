/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String compareString;
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
			default:	throw (new ServerErrorException("Felaktig compare-värde: " + compareType));
		}

		if (compareType == SQLTableList.COMPARE_SUPERSOK) {
			List<String> cols = SQLTableHandler.getStringColumnNames(table);
			String[] terms = sokString.split(" ");
			StringBuilder b = new StringBuilder();
			for (String term : terms) {
				for (String col : cols) {
					if (b.length()>0) b.append(" or ");
					b.append("upper(");
					b.append(col);
					b.append(")");
					b.append(" like upper(?)");
					parameters.add("%" + term + "%");
				}
			}
			where = b.toString();
			if (where==null) where = "";
		} else {
			if (SQLTableHandler.isColumnNameValid(sokField, table)) {
				where = where + " "+ sokField + compareString + " ?";
			} else throw new ServerErrorException("Ogiltigt kolumnnamn: " + sokField);
			parameters.add(sokString);
		}


		if (SQLTableHandler.isColumnNameValid(sortField, table)) {
			orderBy = orderBy + " " + sortField + (sortOrder == SQLTableList.SORT_DESCANDING ? " desc" : "");
		} else throw new ServerErrorException("Ogiltigt kolumnnamn: " + sortField);

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
