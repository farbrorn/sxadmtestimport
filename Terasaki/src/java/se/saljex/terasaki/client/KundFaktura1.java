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
public class KundFaktura1 implements IsSerializable{
	public KundFaktura1() {}

	public int faktnr;
	public java.util.Date datum;
	public double t_attebtala;

}
