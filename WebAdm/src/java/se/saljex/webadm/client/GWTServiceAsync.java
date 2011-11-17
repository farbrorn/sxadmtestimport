/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.Epost;
import se.saljex.webadm.client.rpcobject.InitialData;
import se.saljex.webadm.client.rpcobject.InloggadAnvandare;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.SqlSelectParameters;
import se.saljex.webadm.client.rpcobject.WelcomeData;

/**
 *
 * @author Ulf
 */
public interface GWTServiceAsync { 
    public void myMethod(String s, AsyncCallback<String> callback);
	public void getArtikel(String artnr, AsyncCallback<String> callback);
	public void getKund(String kundnr, AsyncCallback<Kund> callback);
	public void getTableList(IsSQLTable table, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback);
	public void getTableList(IsSQLTable table, Integer sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback);
	public void getTableList(IsSQLTable table, java.sql.Date sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback);
	public void getTableList(IsSQLTable table, Double sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback);
	public void getTableList(IsSQLTable table, SqlSelectParameters sqlSelectParameters, int offset, int limit, AsyncCallback<SQLTableList> callback);
	public void putTableRow(String anvandare, IsSQLTable newValues, IsSQLTable oldValues, AsyncCallback callback);
	public void deleteKund(String kundnr, AsyncCallback callback);
	public void getKundEpostLista(String kundnr, AsyncCallback<ArrayList<Epost>> callback);
	public void sendOffertEpost(String anvandare, String epost, int id, AsyncCallback<Integer> callback);
	public void sendFakturaEpost(String anvandare, String epost, int id, AsyncCallback<Integer> callback);
	public void getChartKund(String kundnr, int width, int height, AsyncCallback<ArrayList<String>> callback);
	public void serverUpdateWebArtikel(AsyncCallback<String> callback);
	public void serverUpdateWebArtikelTrad(AsyncCallback<String> callback);
	public void serverUpdateLagersaldon(AsyncCallback<String> callback);
	public void serverGetStatus(AsyncCallback<String> callback);
	public void overforOrder(int ordernr, String anvandare, short lagernr, AsyncCallback<Integer> callback);
	public void logIn(String anvandare, String losen, AsyncCallback<InloggadAnvandare> callback);
	public void logout(AsyncCallback callback);
	public void getInitialData(AsyncCallback<InitialData> callback);
	public void getWelcomeData(short lagernr, AsyncCallback<WelcomeData> callback);

}
