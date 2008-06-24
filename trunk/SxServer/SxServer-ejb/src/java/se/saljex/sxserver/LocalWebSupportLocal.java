/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.util.List;
import javax.ejb.Local;

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

}
