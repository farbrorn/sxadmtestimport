/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.webadm.client.common.rpcobject.ErrorConvertingFromResultsetException;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.ServerErrorException;
import se.saljex.webadm.client.common.rpcobject.SqlOrderByParameter;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;
import se.saljex.webadm.client.common.rpcobject.SqlWhereParameter;

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
		SqlSelectParameters sqlSelectParameters = new SqlSelectParameters();
		sqlSelectParameters.addWhereParameter(SQLTableList.BOOL_CONNECTOR_NONE, 0, sokField, compareType, sokString, 0);
		sqlSelectParameters.addOrderByParameter(sortField, sortOrder);
		fillList(list, table, sqlSelectParameters, offset, limit);
	}
	public void fillList(SQLTableList list, IsSQLTable table, SqlSelectParameters sqlSelectParameters, int offset, int limit) throws ServerErrorException{
		String where = "";
		String orderBy = "";
		ArrayList<Object> parameters = new ArrayList();



//			where = "";
//			switch (compareType) {
//				case SQLTableList.COMPARE_EQUALS: { compareString = "="; break; }
//				case SQLTableList.COMPARE_GREATER: { compareString = ">"; break; }
//				case SQLTableList.COMPARE_GREATER_EQUALS: { compareString = ">="; break; }
//				case SQLTableList.COMPARE_LESS: { compareString = "<"; break; }
//				case SQLTableList.COMPARE_LESS_EQUALS: { compareString = "<="; break; }
//				case SQLTableList.COMPARE_SUPERSOK: { compareString = "like"; break; }
//				case SQLTableList.COMPARE_NONE: { compareString = ""; break; }
//				default:	throw (new ServerErrorException("Felaktig compare-värde: " + compareType));
//			}
//
//			if (!compareString.isEmpty() && sokString!=null) {
//				if (compareType == SQLTableList.COMPARE_SUPERSOK) {
//					List<String> cols = SQLTableHandler.getStringColumnNames(table);
//					String[] terms = ((String)sokString).split(" ");
//					b = new StringBuilder();
//					for (String term : terms) {
//						bsub = new StringBuilder();
//						for (String col : cols) {
//							if (bsub.length()>0) bsub.append(" or ");
//							bsub.append("upper(");
//							bsub.append(col);
//							bsub.append(")");
//							bsub.append(" like upper(?)");
//							parameters.add("%" + term + "%");
//						}
//						if (b.length()>0 && bsub.length()>0) b.append(" and ");
//						if (bsub.length()>0) b.append(" (" + bsub.toString() + ") ");
//					}
//					where = b.toString();
//					if (where==null) where = "";
//				} else {
//					if (SQLTableHandler.isColumnNameValid(sokField, table)) {
//						where = where + " "+ sokField + compareString + " ?";
//					} else throw new ServerErrorException("Ogiltigt kolumnnamn: " + sokField);
//					try {
//						retTerm = SQLTableHandler.getObjectAsColumnType(table, sokField, sokString);
//					}catch (ParseException e) { throw new ServerErrorException("Fel vid omvandling av sökvärde till typ definerad i datatabell. Kolumn " + sokField + " värde: " + sokString); }
//					parameters.add(retTerm);
//				}
//			}
//
//
//
//
//
//		if (sortField!=null) if (!sortField.isEmpty()){
//			if (SQLTableHandler.isColumnNameValid(sortField, table)) {
//				orderBy = orderBy + " " + sortField + (sortOrder == SQLTableList.SORT_DESCANDING ? " desc" : "");
//			} else throw new ServerErrorException("Ogiltigt kolumnnamn för Order By: " + sortField);
//		}
//
//		//Lägg till grundsortering på primary key
//		String primaryKeyOrderBy = SQLTableHandler.getPrimaryKeyOrderByString(table, sortOrder==SQLTableList.SORT_DESCANDING);
//		if (!SXUtil.isEmpty(orderBy) && !SXUtil.isEmpty(primaryKeyOrderBy)) orderBy = orderBy + ", ";
//		orderBy = orderBy + " " + primaryKeyOrderBy;

		Connection con=null;


		try {
			con = sxadm.getConnection();
			where = buildWhere(sqlSelectParameters, table, parameters);
			orderBy = buildOrderBy(sqlSelectParameters, table);
			SQLTableHandler.fillSQLTable(con, table, list, where, orderBy, null, parameters.toArray(), true, false, offset, limit);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel (SQLTableGetList.fillist): " + e.getMessage());
		} catch (ErrorConvertingFromResultsetException ee) {
			throw new ServerErrorException();
		} finally {
			try { con.close(); } catch (Exception e) {}

		}
	}


	private String buildWhere(SqlSelectParameters sqlSelectParameters, IsSQLTable table, ArrayList<Object> parameters) throws ServerErrorException {
		Object retTerm;
		StringBuilder where = new StringBuilder(sqlSelectParameters.sqlWhereParameters.size()*20);
		StringBuilder b;
		StringBuilder bsub;

		for (SqlWhereParameter wp : sqlSelectParameters.sqlWhereParameters) {
			if (wp.boolConnector==SQLTableList.BOOL_CONNECTOR_AND) where.append(" and ");
			else if(wp.boolConnector == SQLTableList.BOOL_CONNECTOR_OR) where.append(" or ");
			else if (wp.boolConnector!=SQLTableList.BOOL_CONNECTOR_NONE) throw new ServerErrorException("Felaktig boolsk operator: " + wp.boolConnector + " kolumn " + wp.column);

			for (int cn=0 ; cn<wp.noStartPar; cn++) {
				where.append("(");
			}

			if (wp.operator == SQLTableList.COMPARE_SUPERSOK) {
				if (!SXUtil.isEmpty(wp.value)) {
					b = new StringBuilder();
					List<String> cols = SQLTableHandler.getStringColumnNames(table);
					String[] terms = ((String)wp.value).split(" ");
					b = new StringBuilder();
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
					if (b.length()>0) {
						where.append("(");
						where.append(b.toString());
						where.append(")");
					}
				}
			} else {
				if (!SXUtil.isEmpty(wp.column) && wp.operator!=SQLTableList.COMPARE_NONE) {
					if (SQLTableHandler.isColumnNameValid(wp.column, table)) {
						where.append(" ");
						where.append(wp.column);
						if (wp.value==null && wp.operator==SQLTableList.COMPARE_EQUALS ) {
							where.append(" is null ");
						} else if(wp.value == null && wp.operator == SQLTableList.COMPARE_NOTEQUALS) {
							where.append(" is not null ");
						} else if (wp.value!=null) {
							switch (wp.operator) {
								case SQLTableList.COMPARE_EQUALS: { where.append(" = "); break; }
								case SQLTableList.COMPARE_NOTEQUALS: { where.append(" <> "); break; }
								case SQLTableList.COMPARE_GREATER: { where.append(" > "); break; }
								case SQLTableList.COMPARE_GREATER_EQUALS: { where.append(" >= "); break; }
								case SQLTableList.COMPARE_LESS: { where.append(" > "); break; }
								case SQLTableList.COMPARE_LESS_EQUALS: { where.append(" >= "); break; }
								case SQLTableList.COMPARE_SUPERSOK: { where.append(" like "); break; }
								default:	throw (new ServerErrorException("Felaktig compare-värde: " + wp.operator));
							}
							where.append(" ? ");
							try {
								retTerm = SQLTableHandler.getObjectAsColumnType(table, wp.column, wp.value);
							}catch (ParseException e) { throw new ServerErrorException("Fel vid omvandling av sökvärde till typ definerad i datatabell. Kolumn " + wp.column + " värde: " + wp.value); }
							parameters.add(retTerm);
						} else throw new ServerErrorException("Felaktigt nullvärde för filtrering på: " + wp.column);
					} else throw new ServerErrorException("Ogiltigt kolumnnamn: " + wp.column);
				}
			}

			for (int cn=0 ; cn<wp.noEndPar; cn++) {
				where.append(")");
			}
		}

		return where.length()>0 ? where.toString() : "";
	}

	private String buildOrderBy(SqlSelectParameters sqlSelectParameters, IsSQLTable table) throws ServerErrorException {
		StringBuilder orderBy = new StringBuilder();
		for (SqlOrderByParameter op : sqlSelectParameters.sqlOrderByParameters) {
			if (!SXUtil.isEmpty(op.column)){
				if (SQLTableHandler.isColumnNameValid(op.column, table)) {
					if (orderBy.length()>0) orderBy.append(" , ");
					orderBy.append(op.column);
					if (op.sortorder== SQLTableList.SORT_DESCANDING) orderBy.append(" desc");
				} else throw new ServerErrorException("Ogiltigt kolumnnamn för Order By: " + op.column);
			}

		}
		//Lägg till grundsortering på primary key
		String primaryKeyOrderBy = SQLTableHandler.getPrimaryKeyOrderByString(table, false);
		if (!SXUtil.isEmpty(primaryKeyOrderBy)) {
			if (orderBy.length()>0 ) orderBy.append( " , ");
			orderBy.append(primaryKeyOrderBy);
		}

		return orderBy.length()>0 ? orderBy.toString() : "";

	}
}
