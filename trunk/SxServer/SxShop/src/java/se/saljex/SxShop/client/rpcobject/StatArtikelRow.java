/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class StatArtikelRow implements IsSerializable{
	public StatArtikelRow() {}
	public String artnr=null;
	public String namn=null;
	public double antal;
	public String enh=null;
	public double summa;
	public int koptillfallen;
}
