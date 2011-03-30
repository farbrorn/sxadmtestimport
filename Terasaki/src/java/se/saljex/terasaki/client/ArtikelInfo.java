/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class ArtikelInfo implements IsSerializable{
	public ArtikelInfo() {}
	public String artnr=null;
	public ArrayList<ArtikelOrder> artikelOrderList = new ArrayList();
	public ArrayList<ArtikelBest> artikelBestList = new ArrayList();
	public ArrayList<ArtikelStatistik> artikelStatistikList = new ArrayList();


}
