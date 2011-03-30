/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class Faktura2List implements IsSerializable{
	public Faktura2List() {}

	public ArrayList<Faktura2> rader = new ArrayList();

}
