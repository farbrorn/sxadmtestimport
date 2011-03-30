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
public class KundOrder implements IsSerializable{
	public KundOrder() {}

	public String kundnr;
	public int ordernr;
	public java.util.Date datum;
	public String artnr;
	public String artnamn;
	public double antal;
	public double pris;
	public double rab;

}
