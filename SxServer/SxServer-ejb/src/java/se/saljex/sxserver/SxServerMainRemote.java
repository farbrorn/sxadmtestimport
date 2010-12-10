/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author ulf
 */
@Remote
public interface SxServerMainRemote {
    public ByteArrayOutputStream getPdfFaktura(int faktnr) throws IOException;
	String getHTMLStatus();
	ArrayList<Integer> saveSxShopOrder(int kontaktId, String kundnr, String kontaktNamn, short lagerNr, String marke) throws KreditSparrException;
	boolean sendSimpleMail(String adress, String header, String bodytext);


}
