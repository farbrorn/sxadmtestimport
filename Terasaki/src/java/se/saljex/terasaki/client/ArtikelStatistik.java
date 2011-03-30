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
public class ArtikelStatistik implements IsSerializable {
	public ArtikelStatistik() {}

	public int ar;
	public ArrayList<ArtikelStatistikDetalj> artikelStatistikDetalj = new ArrayList();

}
