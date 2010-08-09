
package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class Betaljournal implements IsSerializable{

	public Betaljournal() {}
	public int faktnr;									//Fakturanummer
	public String kundnr=null;							//Kundnummer
	public double betalt;								//Betalt belopp
	public java.util.Date betaldatum=null;			//Betalt datum
	public String betalsatt=null;						//Betalsätt, oftast 'P', 'B', eller 'K'
	public short bokforingsar;							//Bokfört år
	public short bokforinsmanad;						//Bokfört månad

}
