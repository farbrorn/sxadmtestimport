/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class Fakturajournal implements IsSerializable{

	public Fakturajournal() {}
	public int faktnr;								//Fakturanummer
	public String kundnr=null;						//Kundnummer
	public double nettobelopp;						//Fakturans nettobelopp
	public double momsbelopp;						//Fakturans momsbelopp
	public double oresutjamningsbelopp;			//Fakturans öresutjämning
	public double attbetalabelopp;				//Fakturans belopp att betala

}
