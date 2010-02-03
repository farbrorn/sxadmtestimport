/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class StatArtikelList implements IsSerializable{
	public StatArtikelList() {}
	public ArrayList<StatArtikelRow> rader = new ArrayList();
	public int nextRow;
	public int pageSize;
	public boolean hasMoreRows;
	public String frdat;
	public String tidat;
	public String sokstr;
	public String orderBy;
	public static final String ORDER_BY_ARTNR="artnr";
	public static final String ORDER_BY_ANTAL="antal";
	public static final String ORDER_BY_SUMMA="summa";
	public static final String ORDER_BY_KOPTILLFALLEN="koptillfalle";

}
