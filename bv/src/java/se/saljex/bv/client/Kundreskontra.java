/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class Kundreskontra implements IsSerializable{
	public Kundreskontra(){}
	public int faktnr;						//Fakturanummer
	public String kundnr = null;			//Kundnummer
	public String namn = null;				//Namn
	public double belopp;					//Obetalt belopp
	public java.util.Date datum = null;	//Fakturadatum
	public java.util.Date forfalldatum = null;	//Fakturans förfallodatum
	public java.util.Date paminnelse1datum = null;	//Datum för påminnelse1
	public java.util.Date paminnelse2datum = null;	//Datum för påminnelse2
	public java.util.Date paminnelse3datum = null;	//Datum för påminnelse3
	public java.util.Date inkassodatum = null;	//Datum för överföring till inkasso
	public String inkassostatus = null;				//Status för inkasso

}
