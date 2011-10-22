/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;
import se.saljex.sxlibrary.exceptions.SxOrderLastException;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxlibrary.exceptions.KreditSparrException;
import se.saljex.sxserver.tables.TableBest1;
import se.saljex.sxserver.tables.TableSxservjobb;
import java.sql.SQLException;
import javax.ejb.Local;
import javax.naming.NamingException;
import javax.ejb.Timer;
import javax.servlet.http.*;
import com.lowagie.text.DocumentException;
import java.io.*;
import java.util.ArrayList;
import javax.mail.MessagingException;


/**
 *
 * @author Ulf
 */
@Local
public interface SxServerMainLocal {
    void handleTimer(Timer timer) throws Exception;

    void startTimers();
//    public ByteArrayOutputStream getPdfFaktura(int faktnr) throws IOException;

	void main();

	void test();

	void startTimer(int time, String timerName);

//	String getHTMLStatus();

	void handleJobb(TableSxservjobb t) throws Exception;

	void handleSandBestEpost(TableBest1 be1) throws DocumentException, IOException,  NamingException, MessagingException;
	void handleSandBestPaminEpost(TableBest1 be1) throws DocumentException, IOException,  NamingException, MessagingException;

	  String tester(String testTyp);

	  ArrayList<Integer> saveWorder(int worderNr) throws SQLException, KreditSparrException;

	void doSamfaktByEmail(String anvandare, SamfaktByEpostHandler samfak) throws SQLException;

	void doUpdateWebArtikel() throws SQLException;
	void doUpdateWebArtikelTrad() throws SQLException;
	void doUpdateLagersaldo() throws SQLException;
//	ArrayList<Integer> saveSxShopOrder(int kontaktId, String kundnr, String kontaktNamn, short lagerNr, String marke) throws KreditSparrException;

//	boolean sendSimpleMail(String adress, String header, String bodytext);

//	int overforBVOrder(String sxKundnr, int bvOrdernr, String bvAnvandare, String sxAnvandare, short sxLagernr) throws SXEntityNotFoundException;
	void overforBVOrderSaveSxOrder(BvOrder bvOrder) throws SXEntityNotFoundException;

	int faktureraOrder(int ordernr) throws SxOrderLastException;

	int faktureraOrderMedAnvandare(int ordernr, String anvandare) throws SxOrderLastException;

	int faktureraBvOrder(int ordernr) throws SxOrderLastException;

	int faktureraBvOrderMedAnvandare(int ordernr, String anvandare) throws SxOrderLastException;

	ByteArrayOutputStream getTestPdf() throws IOException;

	void sendOffertEpost(String anvandare, String epost, int id) throws SXEntityNotFoundException;
	void sendFakturaEpost(String anvandare, String epost, int id) throws SXEntityNotFoundException;

	
}
