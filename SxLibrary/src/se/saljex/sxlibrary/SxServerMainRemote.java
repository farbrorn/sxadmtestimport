/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxlibrary;

import se.saljex.sxlibrary.exceptions.KreditSparrException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.ejb.Remote;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;

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
	int overforBVOrder(String sxKundnr, int bvOrdernr, String bvAnvandare, String sxAnvandare, short sxLagernr) throws SXEntityNotFoundException;


}
