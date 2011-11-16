package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SqlOrderByParameter implements IsSerializable {

	public String column = null; // kolumnnamn
	public int sortorder = 0; // asc/desc sort order

	public SqlOrderByParameter() {
	}


	// Create with ascending sort
	public SqlOrderByParameter(String column) {
		this(column, SQLTableList.SORT_ASCENDING);
	}

	public SqlOrderByParameter(String column, int sortorder) {
		this.column = column;
		this.sortorder = sortorder;
	}
}
