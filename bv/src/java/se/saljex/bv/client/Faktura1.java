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
public class Faktura1 implements IsSerializable{

	public Faktura1() {
	}

	public int faktnr = 0;
	public java.util.Date datum=null;		//Fakturadatum
	public String namn;							//Kundens namn
	public String adr1;							//Fakturaadress
	public String adr2;
	public String adr3;
	public String saljare;						//Vår kontaktperson
	public String referens;						//Kundens kontaktperson
	public double momsprocent;					//Momssats på fakturan 25% = 25 (ej 0,25)
	public short ktid;							//Kredittid
	public double ranta;							//Ränta i procent uttrykt som 20% = 20 (ej 0,20)
	public boolean bonus;						//Är det bonus på fakturan? Används förmodligen aldrig i BV
	public double nettobelopp;					//Nettobelopp exkl. moms
	public double momsbelopp;					//Momsbelopp
	public double oresutjamning;				//Belopp för öresutjämning
	public double attbetala;					//Slutsumman på fakturan

}
