/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public interface GWTServiceAsync {
    public void myMethod(String s, AsyncCallback<String> callback);
	public void getArtikel(String artnr, AsyncCallback<String> callback);
	public void getKund(String kundnr, AsyncCallback<Kund> callback);
	public void getTableList(IsSQLTable table, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback);
	public void putKund(Kund newValues, Kund oldValues, AsyncCallback callback);
	public void deleteKund(String kundnr, AsyncCallback callback);
}
