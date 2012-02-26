/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class Kund  implements IsSerializable, IsSQLTable{
	public static int getFieldNummer() { return  0; }
	public static int getFieldNamn() { return  1; }
	public Kund newInstance() { return new Kund(); }


	public Kund() {
	}
	public Kund(Kund kund) {
		if (kund!=null) {
			nummer=kund.nummer;
			namn=kund.namn;
			adr1=kund.adr1;
			adr2=kund.adr2;
			adr3=kund.adr3;
			lnamn=kund.lnamn;
			ladr2=kund.ladr2;
			ladr3=kund.ladr3;
			ref=kund.ref;
			saljare=kund.saljare;
			tel=kund.tel;
			biltel=kund.biltel;
			fax=kund.fax;
			sokare=kund.sokare;
			email=kund.email;
			hemsida=kund.hemsida;
			k_Dag=kund.k_Dag;
			k_Tid=kund.k_Tid;
			k_Datum=kund.k_Datum;
			regnr=kund.regnr;
			rantfakt=kund.rantfakt;
			faktor=kund.faktor;
			kgrans=kund.kgrans;
			ktid=kund.ktid;
			nettolst=kund.nettolst;
			bonus=kund.bonus;
			elkund=kund.elkund;
			vvskund=kund.vvskund;
			ovrigkund=kund.ovrigkund;
			installator=kund.installator;
			butik=kund.butik;
			industri=kund.industri;
			oem=kund.oem;
			grossist=kund.grossist;
			levvillkor=kund.levvillkor;
			mottagarfrakt=kund.mottagarfrakt;
			fraktbolag=kund.fraktbolag;
			fraktkundnr=kund.fraktkundnr;
			fraktfrigrans=kund.fraktfrigrans;
			ant1=kund.ant1;
			ant2=kund.ant2;
			ant3=kund.ant3;
			distrikt=kund.distrikt;
			vakund=kund.vakund;
			fastighetskund=kund.fastighetskund;
			basrab=kund.basrab;
			golvkund=kund.golvkund;
			ejfakturerbar=kund.ejfakturerbar;
			skrivfakturarskenr=kund.skrivfakturarskenr;
			sarfaktura=kund.sarfaktura;
			momsfri=kund.momsfri;
			kgransforfall30=kund.kgransforfall30;
			kravordermarke=kund.kravordermarke;
			linjenr1=kund.linjenr1;
			linjenr2=kund.linjenr2;
			linjenr3=kund.linjenr3;
			skickafakturaepost=kund.skickafakturaepost;
			samfakgrans=kund.samfakgrans;
		}
	}

	@Override
	public  String getSQLTableName() {return "kund"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"nummer"};
	}


	public String nummer;
	public String namn;
	public String adr1;
	public String adr2;
	public String adr3;
	public String lnamn;
	public String ladr2;
	public String ladr3;
	public String ref;
	public String saljare;
	public String tel;
	public String biltel;
	public String fax;
	public String sokare;
	public String email;
	public String hemsida;
	public int k_Dag;
	public java.util.Date k_Tid;
	public java.util.Date k_Datum;
	public String regnr;
	public short rantfakt;
	public short faktor;
	public double kgrans;
	public short ktid;
	public String nettolst;
	public short bonus;
	public short elkund;
	public short vvskund;
	public short ovrigkund;
	public short installator;
	public short butik;
	public short industri;
	public short oem;
	public short grossist;
	public String levvillkor;
	public short mottagarfrakt;
	public String fraktbolag;
	public String fraktkundnr;
	public double fraktfrigrans;
	public String ant1;
	public String ant2;
	public String ant3;
	public short distrikt;
	public short vakund;
	public short fastighetskund;
	public double basrab;
	public short golvkund;
	public short ejfakturerbar;
	public short skrivfakturarskenr;
	public short sarfaktura;
	public short momsfri;
	public Double kgransforfall30;
	public Short kravordermarke;
	public String linjenr1;
	public String linjenr2;
	public String linjenr3;
	public short skickafakturaepost;
	public double samfakgrans;


}

