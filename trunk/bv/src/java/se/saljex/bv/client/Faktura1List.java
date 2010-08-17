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
public class Faktura1List implements IsSerializable {
	public Faktura1List() {}

	public int offset=0;
	public int limit=0;
	public Boolean hasMoreRows=null;
	public ArrayList<Faktura1> faktura1List = new ArrayList();
}
