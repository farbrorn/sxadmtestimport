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
public class Faktura2 implements IsSerializable{

	public Faktura2() {
	}
	public int faktnr = 0;
	public int pos;								//Radens position i fakturan
	public String artnr;							//Artikelnummer om arnr är tomt är det troligen en textrad
	public String namn;							//Artikelbenämning
	public double lev;							//Levererat antal
	public String enh;							//Enhet
	public double pris;							//Pris per enhet
	public double rab;							//Rabatt i procent uttrykt som 20%=20 (ej 0,2)
	public int stjid;								//Id för *-rad (=temporär artikel då artikelnummer ej finns i artikelregistret)
	public int ordernr;							//ORdernr som raden kommer ifrån
	public String text;							//Textrad. Om text är satt bör artnr vara tomt

	

}
