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
public class StatInkopHeader implements IsSerializable{
	public  StatInkopHeader() {}
	public ArrayList<StatInkopRow> rows = new ArrayList();
	public String chartUrl=null;
}
