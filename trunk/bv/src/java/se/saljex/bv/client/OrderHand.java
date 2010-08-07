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
public class OrderHand implements IsSerializable{
	public OrderHand() {}

	public java.util.Date datum=null;					//Datum för händelsen
	public String anvandare=null;						//Användare
	public String handelse=null;						//Händelse
	public String transportor=null;					//Fraktbolag - anges om händelsen avser en utleverans
	public String fraktsedelnr=null;					//Fraktsedelnummer - anges om händelsen avser en utleverans

}
