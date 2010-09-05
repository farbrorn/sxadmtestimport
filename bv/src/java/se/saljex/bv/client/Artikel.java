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
public class Artikel implements IsSerializable{
	public Artikel() {}

	public String nummer=null;					//Artikelnmummer
	public String namn=null;					//Benämning
	public String lev=null;						//Leverantörsnummer
	public String bestnr=null;					//Beställningsnummer
	public String enhet=null;					//Enhet
	public double utpris;						//Utpris exkl. moms
	public double inpris;						//Inköpspris
	public double inrab;							//Rabatt på inköpspriset i procent*100
	public double inp_fraktproc;				//Frakttillägg på inköpspriset, uttryckt i procent*100 - 20%=20 (ej 0,2)
	public double inp_miljo;					//Miljöavgift som tillkommer på inköpspriset
	public double inp_frakt;					//Frakttillägg på inköpspriset i kronor
	public String konto=null;					//Kontonummer som försäljningen bokförs på
	public String rabkod=null;					//Huvudrabattgrupp
	public String rabkod1=null;					//Underrabattgrupp till rabkod
	public java.util.Date utprisdatum=null;	//Ändringsdatum för utpriset
	public java.util.Date inprisdatum=null;		//Ändringsdatum för inpriset
	public java.util.Date utgattdatum=null;//Datum då artikeln utgick ur sortimentet, ananrs null
	public int onskattb;							//Önskat täckningsbidrag på utpris i procent*100
	public double inp_enhetsfaktor;			/* Hur många av leverantörens enheter som motsvarar försäljningsenheten
														ex. leverantör har 6 m rör och säljer som m, men vi har enhet st så blir faktorn 6
														*/
	public String struktnr=null;				//Anger vilken struktur artikeln är kopplad till. En struktur innehåller flera artikelnummer och aritkeln översätts då till samtliga artiklar i strukturen
	public double forpack;						//Förpackning
	public double nettoNetto;					//Inköpspriset netto netto, dvs mad alla rabatter dragna och alla tillägg pålagda.
}
