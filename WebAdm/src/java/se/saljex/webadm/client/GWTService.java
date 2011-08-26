/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.Epost;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.ServerErrorException;

/**
 *
 * @author Ulf
 */
@RemoteServiceRelativePath("gwtservice")
public interface GWTService extends RemoteService {
    public String myMethod(String s);
	public String getArtikel(String artnr);
	public Kund getKund(String kundnr) throws ServerErrorException;
	public SQLTableList getTableList(IsSQLTable table, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException;
	public SQLTableList getTableList(IsSQLTable table, Integer sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException;
	public SQLTableList getTableList(IsSQLTable table, java.sql.Date sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException;
	public SQLTableList getTableList(IsSQLTable table, Double sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException;
	public void putKund(Kund newValues, Kund oldValues) throws ServerErrorException;
	public void deleteKund(String kundnr) throws ServerErrorException;
	public ArrayList<Epost> getKundEpostLista(String kundnr) throws ServerErrorException;
	public Integer sendOffertEpost(String anvandare, String epost, int id) throws ServerErrorException;
	public Integer sendFakturaEpost(String anvandare, String epost, int id) throws ServerErrorException;

}
