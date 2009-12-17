/*
 * SxShopRPCAsync.java
 *
 * Created on den 3 december 2009, 10:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author ulf
 */
public interface SxShopRPCAsync {
	public void myMethod(String s, AsyncCallback callback);
	public void getArtikelTrad(AsyncCallback callback);
	public void getArtSida(int grpid,AsyncCallback callback);
	public void updateVaruKorg(String artnr, double antal,AsyncCallback callback);
	public void addVaruKorg(String artnr, double antal,AsyncCallback callback);
	public void getVaruKorg(AsyncCallback callback);
	public void deleteVaruKorg(String artnr, AsyncCallback callback);
	public void skickaOrder(String marke, AsyncCallback callback);
	public void getSokResult(String sokStr, int maxRader, AsyncCallback callback);
	public void getSokArtikel(String sokStr, int maxRader, AsyncCallback callback);
	public void getInloggadAnvandare(AsyncCallback callback);
}
