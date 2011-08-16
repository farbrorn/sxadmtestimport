/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class SQLTableList<T extends IsSQLTable> implements IsSerializable {

	public static final int SORT_ASCENDING = 0;
	public static final int SORT_DESCANDING = 1;

	public static final int COMPARE_LESS = 0;
	public static final int COMPARE_LESS_EQUALS = 1;
	public static final int COMPARE_GREATER = 2;
	public static final int COMPARE_GREATER_EQUALS = 3;
	public static final int COMPARE_EQUALS = 4;
	public static final int COMPARE_SUPERSOK = 5;


	public SQLTableList() {
	}

	public ArrayList<T> lista = new ArrayList();
	public String sokString = null;
	public Integer offset = null;	//Offset in i resultsetet
	public Integer limit = null;	//Max antal rader returnerade
	public Boolean hasMoreRows = null; //Sökfrågan har fler resultatrader
	public int sortField;
	public int sokField;
	public int sortOrder;
	public int compareType;

}
