/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kundres;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class PageLoadKundres extends PageLoad<Kundres>{

	public PageLoadKundres(int pageSize, int prefetchPageSize, int maxBufferSize, PageLoadCallback<Kundres> callback) {
		super(pageSize, prefetchPageSize, maxBufferSize, callback);
	}

	@Override
	protected void getList(GWTServiceAsync service, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback) {
			service.getTableList(new Kundres(), sokString, sokField , sortField, compareType, sortOrder, offset, limit, callback);
	}

}
