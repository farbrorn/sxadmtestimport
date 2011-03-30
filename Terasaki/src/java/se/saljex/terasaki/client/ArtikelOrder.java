/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class ArtikelOrder implements IsSerializable{
	public ArtikelOrder() {}

	public int ordernr;
	public java.util.Date datum;
	public String kundnr=null;
	public String kundnamn=null;
	public double antal;

}
