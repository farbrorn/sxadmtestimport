/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.Epost;
import se.saljex.webadm.client.rpcobject.InitialData;
import se.saljex.webadm.client.rpcobject.InloggadAnvandare;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.NotLoggedInException;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.ServerErrorException;
import se.saljex.webadm.client.rpcobject.SqlSelectParameters;
import se.saljex.webadm.client.rpcobject.WelcomeData;

/**
 *
 * @author Ulf
 */
@RemoteServiceRelativePath("gwtservice")
public interface GWTService extends RemoteService {
    public String myMethod(String s);
	public String getArtikel(String artnr);
	public Kund getKund(String kundnr) throws ServerErrorException, NotLoggedInException;
	public SQLTableList getTableList(IsSQLTable table, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException, NotLoggedInException;
	public SQLTableList getTableList(IsSQLTable table, Integer sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException, NotLoggedInException;
	public SQLTableList getTableList(IsSQLTable table, java.sql.Date sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException, NotLoggedInException;
	public SQLTableList getTableList(IsSQLTable table, Double sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException, NotLoggedInException;
	public SQLTableList getTableList(IsSQLTable table, SqlSelectParameters sqlSelectParameters, int offset, int limit) throws ServerErrorException, NotLoggedInException;
	public void putTableRow(String anvandare, IsSQLTable newValues, IsSQLTable oldValues) throws ServerErrorException, NotLoggedInException;
	public void deleteKund(String kundnr) throws ServerErrorException, NotLoggedInException;
	public ArrayList<Epost> getKundEpostLista(String kundnr) throws ServerErrorException, NotLoggedInException;
	public Integer sendOffertEpost(String anvandare, String epost, int id) throws ServerErrorException, NotLoggedInException;
	public Integer sendFakturaEpost(String anvandare, String epost, int id) throws ServerErrorException, NotLoggedInException;
	public ArrayList<String> getChartKund(String kundnr, int width, int height) throws ServerErrorException, NotLoggedInException;

	public String serverUpdateWebArtikel() throws ServerErrorException, NotLoggedInException;
	public String serverUpdateWebArtikelTrad() throws ServerErrorException, NotLoggedInException;
	public String serverUpdateLagersaldon() throws ServerErrorException, NotLoggedInException;
	public String serverGetStatus() throws ServerErrorException, NotLoggedInException;
	public Integer overforOrder(int ordernr, String anvandare, short lagernr) throws ServerErrorException, NotLoggedInException;

	public InloggadAnvandare logIn(String anvandare, String losen) throws NotLoggedInException, ServerErrorException;
	public void logout() throws ServerErrorException;
	public InitialData getInitialData() throws ServerErrorException;
	public WelcomeData getWelcomeData(short lagernr) throws ServerErrorException, NotLoggedInException;

	public String verifyAnvandareKort(String anvandareKort) throws ServerErrorException, NotLoggedInException;

}
