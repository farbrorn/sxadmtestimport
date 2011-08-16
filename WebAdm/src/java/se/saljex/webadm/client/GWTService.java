/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
	public void putKund(Kund newValues, Kund oldValues) throws ServerErrorException;
	public void deleteKund(String kundnr) throws ServerErrorException;

}
