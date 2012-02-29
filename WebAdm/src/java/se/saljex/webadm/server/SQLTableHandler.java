/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.webadm.client.common.rpcobject.ErrorConvertingFromResultsetException;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.ServerErrorException;

/**
 *
 * @author Ulf
 */
public class SQLTableHandler {

	public static final Double dummyDouble = (double)0;
	public static final String dummyString = "";
	public static final Integer dummyInteger = 0;
	public static final Short dummyShort = (short)0;
	public static final java.sql.Date dummySQLDate = new java.sql.Date(0);


	public static String getSelectColumns(Class c) {
		StringBuilder sqlField = new StringBuilder();
		Field[] fields = c.getFields();
		for (Field f : fields) {
			if (sqlField.length()>1) sqlField.append(", ");
			sqlField.append(f.getName());
		}
		return sqlField.toString();
	}

	//Hämtar värdet från angiven column
	public static Object getColumnValue(IsSQLTable table, String column) throws IllegalAccessException {
		Field[] fields = table.getClass().getFields();
		for (Field f : fields) {
			if (f.getName().equals(column)) return f.get(table);
		}
		throw new IllegalAccessException("Kolumn " + column + " finns inte i tabellen.");
	}


	public static String getSelectStatement(IsSQLTable o) {
		return "select " + getSelectColumns(o.getClass()) + " from " + o.getSQLTableName();
	}

	public static void fillSQLTable(Connection con, IsSQLTable o, SQLTableList list, String SQLWhere, String SQLOrderBy, String extraSQL, Object[] parameters, boolean addPrimaryKeySort, boolean descandentSort, int offset, int limit) throws SQLException, ErrorConvertingFromResultsetException{
		ResultSet rs = getResultSet(con, o, SQLWhere, SQLOrderBy, extraSQL, parameters, addPrimaryKeySort, descandentSort, offset, limit);
		fetchFromResultSet(rs, o, list, offset, limit);
	}
	
	public static ResultSet getResultSet(Connection con, IsSQLTable o, String SQLWhere, String SQLOrderBy, String extraSQL, Object[] parameters, boolean addPrimaryKeySort, boolean descandentSort, int offset, int limit) throws SQLException{
		String sql = getSelectStatement(o);
		if (SQLWhere==null) SQLWhere="";
		if (SQLOrderBy==null) SQLOrderBy="";
		if (extraSQL==null) extraSQL="";

		if (!SXUtil.isEmpty(SQLWhere)) {
			sql = sql + " where " + SQLWhere;
		}

		if (addPrimaryKeySort) {
			String primarayKey = getPrimaryKeyOrderByString(o, descandentSort);
			if (!SXUtil.isEmpty(primarayKey)) {
				if (!SXUtil.isEmpty(SQLOrderBy)) SQLOrderBy = SQLOrderBy + ", ";
				SQLOrderBy = SQLOrderBy + " " + primarayKey;
			}
		}


		if (!SXUtil.isEmpty(SQLOrderBy)) {
			sql = sql + " order by " + SQLOrderBy;
		}

		if (offset>0) {
			extraSQL = extraSQL + " offset " + offset;
		}
		if (limit>0) {
			extraSQL = extraSQL + " limit " + (limit+1);
		}

		if (!SXUtil.isEmpty(extraSQL)) {
			sql = sql + " " + extraSQL;
		}

		PreparedStatement stm = con.prepareStatement(sql);
		if (parameters.length>0) {
			int cn=0;
			for (Object p : parameters) {
				cn++;
				stm.setObject(cn, p);
			}
		}
		return stm.executeQuery();
	}

	public static void getValues(IsSQLTable o, ResultSet rs) throws ErrorConvertingFromResultsetException {
		Field[] fields = o.getClass().getFields();
		for ( Field f : fields) {
			try {
//				System.out.print("----" + f.getName() + " " + f.getType().getName());// + " " + rs.getObject(f.getName()).getClass().getName());
				if (  f.get(o) instanceof Integer)  f.set(o, rs.getInt(f.getName()));
				else if (  f.get(o) instanceof Short || f.getType()==Short.class)  f.set(o, rs.getShort(f.getName()));
				else if (  f.get(o) instanceof Double || f.getType()==Double.class)  f.set(o, rs.getDouble(f.getName()));
				else if (  f.get(o) instanceof Float || f.getType()==Float.class)  f.set(o, rs.getFloat(f.getName()));
				else if (  f.get(o) instanceof BigDecimal || f.getType()==BigDecimal.class)  f.set(o, rs.getBigDecimal(f.getName()));
				else if (  f.get(o) instanceof Byte || f.getType()==Byte.class)  f.set(o, new Byte(rs.getByte(f.getName())));
				else f.set(o, f.getType().cast(rs.getObject(f.getName())));
//				System.out.print("set   ----" + f.getName() + " " + f.getType().getName());// + " " + rs.getObject(f.getName()).getClass().getName());

			}catch (Exception e) {  e.printStackTrace(); throw new ErrorConvertingFromResultsetException("Fel vid konvertering från SQL Redsultset till class: " + e.toString() + " " + e.getMessage());}
		}
	}

	public static void fetchFromResultSet(ResultSet rs, IsSQLTable sqlTable, SQLTableList list, int offset, int limit) throws SQLException, ErrorConvertingFromResultsetException{
		IsSQLTable row;
		int cn = 0;
		list.hasMoreRows = false;
		while (rs.next()) {
			cn++;
			if (cn > limit && limit > 0) {
				list.hasMoreRows=true;
				break;
			}
			row = sqlTable.newInstance();
			getValues(row, rs);
			list.lista.add(row);
		}
		list.limit = limit;
		list.offset = offset;

	}

	public static String getPrimaryKeyOrderByString(IsSQLTable o, boolean descandingSortOrder) {
		//Lägg till grundsortering på primary key
		String[] primaryKeyLabels = o.getPrimaryKeyLabels();
		String orderBy="";
		for (String label : primaryKeyLabels) {
			if (orderBy.length()>0) orderBy = orderBy + ", ";
			orderBy = orderBy + " " + label;
			if (descandingSortOrder) orderBy = orderBy + " desc ";
		}
		return orderBy;

	}

	//Hittar angiven kolumnnamn och konverteraar en string till ett object av samma typ
	//Returnerar värdet som ett object av rätt typ, eller null m kolumnen inte finns
	public static Object getObjectAsColumnType(IsSQLTable table, String columnName, String varde) throws ParseException{
		try {
		Field[] fields = table.getClass().getFields();
		for (Field field : fields) {
			if (field.getName().equals(columnName)) {
				if (field.getType().isInstance(dummyString ) || field.get(table) instanceof String){
//System.out.print("Kolumn " + columnName + " är String");
					return varde;
				} else if (field.getType().isInstance(dummySQLDate) || field.get(table) instanceof java.sql.Date ) {

//System.out.print("Kolumn " + columnName + " är Date");
					java.util.Date d = DateFormat.getInstance().parse(varde);
					if (d!=null) {
						return new java.sql.Date(d.getTime());
					}
				} else if (field.get(table) instanceof Integer || field.getType().isInstance(dummyInteger)) {
//System.out.print("Kolumn " + columnName + " är Integer");
					return new Integer(varde);
				} else if (field.get(table) instanceof Short || field.getType().isInstance(dummyShort)) {
//System.out.print("Kolumn " + columnName + " är Short");
					return new Short(varde);
				} else if (field.get(table) instanceof Double || field.getType().isInstance(dummyDouble)) {
//7System.out.print("Kolumn " + columnName + " är Double");
					return new Double(varde);
				} else {
					throw new ParseException("Kan inte konvertera kolumn " + columnName + " till den typ som är definerad i sql-tabellen. Omvandlingsfunktion saknas eller är inte implementerad. Kontrollera i tabellen vilken datatyp det är och gör ev. ändring av typ till någon vanlig typ.", 0);
				}

			}
		}
		}  catch (IllegalAccessException e)  { e.printStackTrace();
		}
		return null;

	}

	//Kolla om angivet column-name är giltigt i tabelle,. Används när client sänder data om vilken kolumn han vill använda och skyddar mot sql-injection
	public static boolean isColumnNameValid(String columnName, IsSQLTable table) {
		Field[] fields = table.getClass().getFields();
		for (Field field : fields) {
			if (field.getName().equals(columnName)) return true;
		}
		return false;	//Vi hittade ingen giltig column
	}

	//Returnerar lista på alla kolumner som är av text. Används t.ex. i supersök
	public static List<String> getStringColumnNames(IsSQLTable table) {
		List<String> list = new ArrayList<String>();
		Field[] fields = table.getClass().getFields();
		for (Field field : fields) {
			try {
				if (field.getType().isInstance(dummyString ) || field.get(table) instanceof String) list.add(field.getName());
			} catch (IllegalAccessException e) {}
		}
		return list;
	}


	public static void  updateTableRow(Connection con, String anvandare, IsSQLTable newRow, IsSQLTable oldRow) throws ServerErrorException, SQLException {
		try {
			if (newRow != null) {
				if (oldRow== null) {
					//OldRow är tom - indikerar en insert
					throw new ServerErrorException("Insert är inte implementerat");
				} else {	//Vi har två issqltable-object
					if(newRow.getClass().getCanonicalName().equals(oldRow.getClass().getCanonicalName())) {	//Kolla så det är samma typ
						StringBuilder updateSB = new StringBuilder();
						StringBuilder whereSB = new StringBuilder();
						ArrayList<Object> updateParams = new ArrayList();
						ArrayList<Object> whereParams = new ArrayList();
						Object newValue;
						Object oldValue;

						//Tillåt inte ändring av primary key, ej heller null primary key
						String[] pKeys = newRow.getPrimaryKeyLabels();
						for (String pKey : pKeys) {
							newValue = getColumnValue(newRow, pKey);
							oldValue = getColumnValue(oldRow, pKey);

							//Lägg till wheresats på primary key
							if (whereSB.length()>0) whereSB.append(" and ");
							whereSB.append(pKey+" = ?");
							whereParams.add(oldValue);

							//Kolla så värdena på primary key är giltiga
							if (newValue != null) {
								if (!newValue.equals(oldValue))  throw new ServerErrorException("Kan inte ändra primary key");
							} else {
								throw new ServerErrorException("Kan inte ändra primary key till null");
							}
						}

						// Gå nu igenom vilka kolumner som är ändrade och lägg till dem i sql update.
						// Lägg till gamla värdet i sql where för att se så ingen annan har uppdaterat under tiden
						Field[] fields = newRow.getClass().getFields();
						for (Field field : fields) {
							newValue = field.get(newRow);
							oldValue = field.get(oldRow);
							//Kolumnen ska uppdateras om den har ändrats
							if ((newValue!=null && !newValue.equals(oldValue)) || (newValue==null && oldValue!=null)) {
								if (updateSB.length()>0) updateSB.append(", ");
								updateSB.append(field.getName());
								if (newValue==null) {
									updateSB.append("=null");
								} else {
									updateSB.append("=?");
									if (newValue instanceof String) newValue=SXUtil.rightTrim((String)newValue);	//Ta bort avslutande blanksteg
									updateParams.add(newValue);
								}

								if (whereSB.length()>0) whereSB.append(" and ");

								whereSB.append(field.getName());
								if (oldValue==null) {
									whereSB.append(" is null");
								} else {
									whereSB.append("=?");
									whereParams.add(oldValue);
								}

							}

						}

						if (updateSB.length()>0) {
							String sql = " update " + newRow.getSQLTableName() + " set " + updateSB.toString() + " where " + whereSB.toString();
							updateParams.addAll(whereParams);
							PreparedStatement stm = con.prepareStatement(sql);

							int cn=0;
							for (Object p : updateParams) {
								cn++;
								stm.setObject(cn, p);
							}
							int result = stm.executeUpdate();
							if (result > 1) throw new ServerErrorException("Internt serverfel: Servern försöker uppdatera fler kunder.");
							if (result < 1) throw new ServerErrorException("Kunde inte spara. Kunden kan vara ändrad av annan användare. Prova att söka kunden på nytt, gör ändringarna och spara igen");
	//	System.out.print(sql);
	//	for (Object o : updateParams) { System.out.print(o.toString()); }

						}

					} else {
						throw new ServerErrorException("Hopblandning av objekt. Försöker uppdatera " + oldRow.getClass().getCanonicalName() + " med " + newRow.getClass().getCanonicalName());
					}
				}
			} else {
				throw new ServerErrorException("Raden med nya värden är tom. Kan inte uppdatera.");
			}
		} catch(IllegalAccessException e) {throw new ServerErrorException("IllegalAcces: " + e.getMessage());}

	}

}
