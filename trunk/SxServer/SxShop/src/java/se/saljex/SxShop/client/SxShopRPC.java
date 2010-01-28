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
import java.sql.SQLException;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.Anvandare;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderList;
import se.saljex.SxShop.client.rpcobject.FakturaInfo;
import se.saljex.SxShop.client.rpcobject.FelaktigtAntalException;
import se.saljex.SxShop.client.rpcobject.IncorrectLogInException;
import se.saljex.SxShop.client.rpcobject.LagerSaldo;
import se.saljex.SxShop.client.rpcobject.NotLoggedInException;
import se.saljex.SxShop.client.rpcobject.OrderHeader;
import se.saljex.SxShop.client.rpcobject.OrderHeaderList;
import se.saljex.SxShop.client.rpcobject.OrderInfo;
import se.saljex.SxShop.client.rpcobject.ServerErrorException;
import se.saljex.SxShop.client.rpcobject.SxShopKreditSparrException;

/**
 *
 * @author ulf
 */
public interface SxShopRPC extends RemoteService{
	public String myMethod(String s);
	public ArrayList<ArtGrupp> getArtikelTrad();
	public ArtSida getArtSida(int grpid);
	public ArrayList<VaruKorgRad> updateVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException;
	public ArrayList<VaruKorgRad> addVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException;
	public ArrayList<VaruKorgRad> getVaruKorg() throws NotLoggedInException;
	public ArrayList<VaruKorgRad> deleteVaruKorg(String artnr) throws NotLoggedInException;
	public ArrayList<Integer> skickaOrder(String marke) throws NotLoggedInException, SxShopKreditSparrException;
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
}