/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class ArtikelList implements IsSerializable {
	public ArtikelList() {}

	public int offset=0;
	public int limit=0;
	public Boolean hasMoreRows=null;
	public String query=null;
	public ArrayList<Artikel> artikelList = new ArrayList();

}
