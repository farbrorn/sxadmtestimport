/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class PageLoadKund extends PageLoad<Kund> {

	public PageLoadKund(int pageSize, int prefetchPageSize, int maxBufferSize, PageLoadCallback<Kund> callback) {
		super(pageSize, prefetchPageSize, maxBufferSize, callback);
	}

	@Override
	protected void getList(GWTServiceAsync service, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback) {
			service.getTableList(new Kund(), sokString, sokField , sortField, compareType, sortOrder, offset, limit, callback);
	}
}
