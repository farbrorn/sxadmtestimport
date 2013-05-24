/*
 * SxShopRPC.java
 *
 * Created on den 3 december 2009, 10:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import se.saljex.SxShop.client.rpcobject.VaruKorgRad;
import se.saljex.SxShop.client.rpcobject.ArtSida;
import se.saljex.SxShop.client.rpcobject.ArtGrupp;
import se.saljex.SxShop.client.rpcobject.SokResult;
import com.google.gwt.user.client.rpc.RemoteService;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.Anvandare;
import se.saljex.SxShop.client.rpcobject.AnvandareUppgifter;
import se.saljex.SxShop.client.rpcobject.BetalningList;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderList;
import se.saljex.SxShop.client.rpcobject.FakturaInfo;
import se.saljex.SxShop.client.rpcobject.FelaktigtAntalException;
import se.saljex.SxShop.client.rpcobject.IncorrectLogInException;
import se.saljex.SxShop.client.rpcobject.KundresRow;
import se.saljex.SxShop.client.rpcobject.LagerSaldo;
import se.saljex.SxShop.client.rpcobject.NotLoggedInException;
import se.saljex.SxShop.client.rpcobject.OffertHeaderList;
import se.saljex.SxShop.client.rpcobject.OffertInfo;
import se.saljex.SxShop.client.rpcobject.OrderHeaderList;
import se.saljex.SxShop.client.rpcobject.OrderInfo;
import se.saljex.SxShop.client.rpcobject.ServerErrorException;
import se.saljex.SxShop.client.rpcobject.StatArtikelFakturaRow;
import se.saljex.SxShop.client.rpcobject.StatArtikelList;
import se.saljex.SxShop.client.rpcobject.StatInkopHeader;
import se.saljex.SxShop.client.rpcobject.SxShopKreditSparrException;
import se.saljex.SxShop.client.rpcobject.UtlevInfo;
import se.saljex.SxShop.client.rpcobject.UtlevList;

/**
 *
 * @author ulf
 */
public interface SxShopRPC extends RemoteService{
	public ArrayList<ArtGrupp> getArtikelTrad(int maxBilder);
	public ArtSida getArtSida(int grpid);
	public ArrayList<VaruKorgRad> updateVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException;
	public ArrayList<VaruKorgRad> addVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException, ServerErrorException;
	public ArrayList<VaruKorgRad> getVaruKorg() throws NotLoggedInException;
	public ArrayList<VaruKorgRad> deleteVaruKorg(String artnr) throws NotLoggedInException;
	public ArrayList<Integer> skickaOrder(String marke) throws NotLoggedInException, SxShopKreditSparrException, ServerErrorException;
	public SokResult getSokResult(String sokStr, int maxRader);
	public SokResult getSokArtikel(String sokStr, int maxRader);
	public Anvandare getInloggadAnvandare();
	public Anvandare autoLogin(String anvandare, String autoLogInId) throws IncorrectLogInException, ServerErrorException;
	public Anvandare logIn(String anvandare, String losen, boolean stayLoggedIn) throws IncorrectLogInException, ServerErrorException;
	public Anvandare logOut();
	public void skickaInloggningsuppgifter(String anvandareOrEpost) throws IncorrectLogInException;
	public LagerSaldo getLagerSaldon(String artnr) throws NotLoggedInException, ServerErrorException;
	public FakturaHeaderList getFakturaHeaders(int page, int pageSize) throws ServerErrorException, NotLoggedInException;
	public FakturaInfo getFakturaInfo(int faktnr) throws ServerErrorException, NotLoggedInException;
	public OrderHeaderList getOrderHeaders() throws ServerErrorException, NotLoggedInException;
	public OrderInfo getOrderInfo(int ordernr) throws ServerErrorException, NotLoggedInException;
	public void deleteOrder(int ordernr) throws ServerErrorException, NotLoggedInException;
	public void changeOrderRow(int ordernr, int pos, String antal) throws ServerErrorException, NotLoggedInException;
	public ArrayList<KundresRow> getKundresLista() throws ServerErrorException, NotLoggedInException;
	public BetalningList getBetalningList(int startRow, int pageSize) throws ServerErrorException, NotLoggedInException;
	public UtlevList getUtlevList(int startRow, int pageSize, String frdat, String tidat, String sokstr) throws ServerErrorException, NotLoggedInException;
	public UtlevInfo getUtlevInfo(int  ordernr) throws ServerErrorException, NotLoggedInException;
	public StatArtikelList getStatArtikelList(int startRow, int pageSize, String frdat, String tidat, String sokstr, String orderBy) throws ServerErrorException, NotLoggedInException;
	public ArrayList<StatArtikelFakturaRow> getStatArtikelFakturaRows(String artnr, String frdat, String tidat) throws ServerErrorException, NotLoggedInException;
	public StatInkopHeader getStatInkopRows(int antalArBakat) throws ServerErrorException, NotLoggedInException;
	public void updateAnvandareUppgifter(AnvandareUppgifter a) throws ServerErrorException, NotLoggedInException;
	public AnvandareUppgifter getAnvandareUppgifter() throws ServerErrorException, NotLoggedInException;
	public void updateLosen(String nyttLosen, String upprepaLosen, String gammaltLosen) throws ServerErrorException, NotLoggedInException;
	public OffertInfo getOffertInfo(int offertnr) throws ServerErrorException, NotLoggedInException;
	public OffertHeaderList getOffertHeaders(int startRow, int pageSize) throws ServerErrorException, NotLoggedInException;
	public SokResult getKampanjartiklar() throws ServerErrorException, NotLoggedInException;
//	public ArrayList<ArtGrpBilder> getBilderForArtGrpNodes(int grpid, int maxbilder) throws ServerErrorException;
}