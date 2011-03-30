/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class ArtikelBest implements IsSerializable{
	public ArtikelBest() {}

	public int bestnr;
	public java.util.Date datum=null;
	public double antal;

}
