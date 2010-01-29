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
public class FakturaInfo implements IsSerializable {
	public FakturaHeader header = new FakturaHeader();
	public ArrayList<FakturaRow> rows = new ArrayList();
	public ArrayList<BetalningRow> betalningar = new ArrayList();
	public FakturaInfo() {}
}
