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
public class Artikel implements IsSerializable{
	public Artikel() {}
	public String artnr=null;
	public String namn=null;
	public String enhet=null;
	public String bestnr=null;
	public double utpris=0;
	public double inpris=0;
	public double rab=0;
	public double ilager=0;
	public double bestpunkt=0;
	public double maxlager=0;
	public double iorder=0;
	public double best=0;

}
