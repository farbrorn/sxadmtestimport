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
public class Bokord implements IsSerializable {
	public Bokord() {}

	public String konto=null;				//Kontonummer
	public int faktnr;					//Fakturanummer
	public String typ=null;				//Vad konteringen avser - 'F'=Faktura, 'B'=Betalning
	public double summa;					//Belopp
	public java.util.Date datum;		//Bokföringsdatum
}
