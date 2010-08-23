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
public class Order2 implements IsSerializable {
	public Order2() {}

	public String artnr =null;							//Artikelnummer. Inledande * anger artikel som inte finns i artikelregistret
	public String namn =null;							//Artikelbenämning
	public double antal=0;								//Antal i order
	public String enh=null;								//Enhet
	public double pris=0;								//Pris / enhet
	public double rab=0;									//Rabatt i procent. 20%=20 (ev 0,2)
	public double lagerTillgangliga=0;				//Antall tillgängliga i det lager ordern är registrerad på
	public double lagerTillgangligaFilialer=0;	//Totalt antal tillgängliga på andra lager
	public double lagerBest=0;							//Antal beställda
	public double nettonetto=0;						//Inköpspris, nettonetto


}
