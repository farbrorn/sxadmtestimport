/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author Ulf
 */
@RemoteServiceRelativePath("gwtservice")
public interface GWTService extends RemoteService {

    public String myMethod(String s);
	public KundList getKundList() throws ServerErrorException, NotLoggedInException;
	public KundInfo getKundInfo(String kundnr) throws ServerErrorException, NotLoggedInException;
	public Faktura2List getFaktura2(int faktnr) throws ServerErrorException, NotLoggedInException;


	public ArtikelList getArtikelList() throws ServerErrorException, NotLoggedInException;
	public ArtikelInfo getArtikelInfo(String artnr) throws ServerErrorException, NotLoggedInException;

	public Boolean logIn(String anvandareKort, String losen) throws ServerErrorException;
	public Boolean isLoggedIn() throws ServerErrorException;
	public void logOut();

}
