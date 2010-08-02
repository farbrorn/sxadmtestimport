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

	public String artnr =null;
	public String namn =null;
	public double antal=0;
	public String enh=null;
	public double pris=0;
	public double rab=0;
	public double lagerTillgangliga=0;
	public double lagerTillgangligaFilialer=0;
	public double lagerBest=0;


}
