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
public class Faktura2 implements IsSerializable {
	public Faktura2() {}

	public String artnr;
	public String namn;
	public double antal;
	public String enh;
	public double pris;
	public double rab;
	public double summa;

}
