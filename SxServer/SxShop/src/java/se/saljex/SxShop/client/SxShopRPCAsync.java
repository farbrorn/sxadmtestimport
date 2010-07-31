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
import se.saljex.SxShop.client.rpcobject.AnvandareUppgifter;

/**
 *
 * @author ulf
 */
public interface SxShopRPCAsync {
	public void getArtikelTrad(int maxBilder, AsyncCallback callback);
	public void getArtSida(int grpid,AsyncCallback callback);
	public void updateVaruKorg(String artnr, double antal,AsyncCallback callback);
	public void addVaruKorg(String artnr, double antal,AsyncCallback callback);
	public void getVaruKorg(AsyncCallback callback);
	public void deleteVaruKorg(String artnr, AsyncCallback callback);
	public void skickaOrder(String marke, AsyncCallback callback);
	public void getSokResult(String sokStr, int maxRader, AsyncCallback callback);
	public void getSokArtikel(String sokStr, int maxRader, AsyncCallback callback);
	public void getInloggadAnvandare(AsyncCallback callback);
	public void autoLogin(String anvandare, String autoLogInId, AsyncCallback callback);
	public void logIn(String anvandare, String losen, boolean stayLoggedIn, AsyncCallback callback);
	public void logOut(AsyncCallback callback);
	public void skickaInloggningsuppgifter(String anvandareOrEpost, AsyncCallback callback);
	public void getLagerSaldon(String artnr, AsyncCallback callback);
	public void getFakturaHeaders(int page, int pageSize, AsyncCallback callback);
	public void getFakturaInfo(int faktnr, AsyncCallback callback);
	public void getOrderHeaders(AsyncCallback callback);
	public void getOrderInfo(int ordernr, AsyncCallback callback);
	public void deleteOrder(int ordernr, AsyncCallback callback);
	public void changeOrderRow(int ordernr, int pos, String antal, AsyncCallback callback);
	public void getKundresLista(AsyncCallback callback);
	public void getBetalningList(int startRow, int pageSize, AsyncCallback callback);
	public void getUtlevList(int startRow, int pageSize, String frdat, String tidat, String sokstr, AsyncCallback callback);
	public void getUtlevInfo(int  ordernr, AsyncCallback callback);
	public void getStatArtikelList(int startRow, int pageSize, String frdat, String tidat, String sokstr, String orderBy, AsyncCallback callback);
	public void getStatArtikelFakturaRows(String artnr, String frdat, String tidat, AsyncCallback callback);
	public void getStatInkopRows(int antalArBakat, AsyncCallback callback);
	public void updateAnvandareUppgifter(AnvandareUppgifter a, AsyncCallback callback);
	public void getAnvandareUppgifter(AsyncCallback callback);
	public void updateLosen(String nyttLosen, String upprepaLosen, String gammaltLosen, AsyncCallback callback);
	public void getOffertInfo(int offertnr, AsyncCallback callback);
	public void getOffertHeaders(int startRow, int pageSize, AsyncCallback callback);
	public void getKampanjartiklar(AsyncCallback callback);
//	public void getBilderForArtGrpNodes(int grpid, int maxbilder, AsyncCallback callback);

	}
