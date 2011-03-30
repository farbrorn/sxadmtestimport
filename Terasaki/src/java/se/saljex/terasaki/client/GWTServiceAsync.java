/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Ulf
 */
public interface GWTServiceAsync {
    public void myMethod(String s, AsyncCallback<String> callback);
	public void getKundList(AsyncCallback<KundList> callback);
	public void getKundInfo(String kundnr, AsyncCallback<KundInfo> callback);
	public void getFaktura2(int faktnr, AsyncCallback<Faktura2List> callback);

	public void getArtikelList(AsyncCallback<ArtikelList> callback);
	public void getArtikelInfo(String artnr, AsyncCallback<ArtikelInfo> callback);

	public void logIn(String anvandareKort, String losen, AsyncCallback<Boolean> callback);
	public void isLoggedIn(AsyncCallback<Boolean> callback);
	public void logOut(AsyncCallback<Boolean> callback);
	
}
