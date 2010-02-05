/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class AnvandareUppgifter implements IsSerializable{
	public AnvandareUppgifter() {}
	public String kundnr=null;
	public String kontaktNamn=null;
	public String kontaktTel=null;
	public String kontaktMobil=null;
	public String kontaktFax=null;
	public String kontaktAdr1=null;
	public String kontaktAdr2=null;
	public String kontaktAdr3=null;
	public String kontaktEpost=null;
	public boolean kontaktEkonomiFlagga;
	public boolean kontaktInfoFlagga;
}
