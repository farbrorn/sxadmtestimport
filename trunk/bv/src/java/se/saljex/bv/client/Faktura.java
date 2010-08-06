/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class Faktura implements IsSerializable{

	public Faktura() {
	}

	public Faktura1 faktura1;									//Fakturahuvud
	public ArrayList<Faktura2> faktura2List = null;		//Fakturarader
	

}
