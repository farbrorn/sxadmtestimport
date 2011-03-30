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
public class KundFaktura1List implements IsSerializable{
	public KundFaktura1List() {}

	public ArrayList<KundFaktura1> fakturor = new ArrayList();
	public int maxAntalFakturor;
	public boolean flerFakturorFinns=false;

}
