/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class UtlevInfo implements IsSerializable{
	public UtlevInfo() {}
	public UtlevRow utlev;
	public ArrayList<FakturaRow> artikelrader = new ArrayList();
}
