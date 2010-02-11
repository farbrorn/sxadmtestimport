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
public class OffertInfo implements IsSerializable{
	public OffertInfo() {}
	public OffertHeader offertHeader=null;
	public ArrayList<OffertRow> artikelrader = new ArrayList();


}
