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
public class Kund implements IsSerializable{
	public Kund() {}

	public String kundnr;
	public String namn;
	public String ref;
	public String adr1;
	public String adr2;
	public String adr3;
	public String tel;
	public String biltel;
	public double omsattning;


}
