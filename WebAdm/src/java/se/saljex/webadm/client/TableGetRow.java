/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.ServerErrorException;

/**
 *
 * @author Ulf
 */
public class TableGetRow<T extends IsSQLTable> {
	private boolean blockRPC = false;
	private final T tableObject;
	private final TableRowLoadCallback<T> callback;

	public TableGetRow(T tableObject, TableRowLoadCallback<T> callback) {
		this.tableObject=tableObject;
		this.callback=callback;
	}


	public void getRow(String sokField, String sokString) {
		if (!blockRPC) {
			blockRPC=true;
			MainEntryPoint.getService().getTableList(tableObject, sokString, sokField , "", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING, 0, 1, asyncCallback);
		} else {
			callback.onFailure(new ServerErrorException("Servern bearbetar en fråga och kan inte svara nu. Försök igen senare. (RPC Blocked)"));
		}
	}

	final AsyncCallback asyncCallback = new AsyncCallback<SQLTableList<T>>() {
		public void onSuccess(SQLTableList<T> result) {
			blockRPC=false;
			if (result.lista.isEmpty()) callback.onSuccess(null);
			else callback.onSuccess(result.lista.get(0));
		}

		public void onFailure(Throwable caught) {
			blockRPC=false;
			callback.onFailure(caught);
		}
	};


}
