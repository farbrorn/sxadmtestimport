/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableKundkontakt;
import se.saljex.sxserver.tables.TableOrder1;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableKundres;
import se.saljex.sxserver.tables.TableKundlogin;
import se.saljex.sxserver.tables.TableOrder2;
import se.saljex.sxserver.tables.TableRorder;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author ulf
 */
@Local
public interface LocalWebSupportLocal {

	  TableKund getTableKund(String kundnr);

	  TableFaktura1 getTableFaktura1(int faktnr);

	  List<TableFaktura2> getListTableFaktura2(int faktnr);

	  List<TableOrder2> getListTableOrder2(int ordernr);

	  List<TableOrder1> getListTableOrder1(String kundnr);

	  List<TableKundres> getListTableKundres(String kundnr);

	  TableOrder1 getTableOrder1(int ordernr);

	  List<TableRorder> getListTableRorder(String kundnr);

	  ByteArrayOutputStream getPdfFaktura(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException;
	  ByteArrayOutputStream getPdfBest(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException;

	  ByteArrayOutputStream getPdfFaktura(Integer nr, String kundnr) throws DocumentException, IOException;

	TableKundkontakt getTableKundkontakt(Integer kontaktid);

	TableKundlogin getTableKundlogin(String loginnamn);

	String updateWebArtikelTradWithHTMLResponse();

	String getHTMLStatus();

	EntityManagerFactory getEmf();

	//UserTransaction getUserTransaction();

	ByteArrayOutputStream getPdfSteServiceorder(Integer id) throws DocumentException, IOException;

	String updateWebArtikelWithHTMLResponse();

	String updateLagerSaldonWithHTMLResponse();

	ByteArrayOutputStream getPdfOffert(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException ;
	ByteArrayOutputStream getPdfOffertInkMoms(Integer nr) throws com.lowagie.text.DocumentException, java.io.IOException ;

//	public void deleteOrder(int ordernr);

//	public void changeOrderRowAntal(int ordernr,short pos, double antal);

}
