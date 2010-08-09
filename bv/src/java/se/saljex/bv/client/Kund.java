/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class Kund implements IsSerializable {
	public Kund() {}

	public String kundnr = null;					//Kundnummer
	public String namn = null;						//Namn
	public String adr1 = null;						//Adress
	public String adr2 = null;						//
	public String adr3 = null;						//
	public String levnamn = null;					//Leveransadress
	public String levadr2 = null;					//
	public String levadr3 = null;					//
	public String ref = null;						//Kundens referensperson
	public String saljare = null;					//Vår kontaktperson/ansvarig säljare
	public String tel = null;						//Telefon
	public String biltel = null;					//Mobiltel
	public String fax = null;						//Fax
	public String email = null;					//E-Post
	public String regnr = null;					//Organisationsnr
	public boolean rantfak;							//Skall ränta faktureras på kunden?
	public double kgrans;							//Kreditgräns
	public double ktid;								//Kredittid
	public boolean momsfri;							//Om kunden ska ha fakturor utan moms, t.ex. vid export

}
