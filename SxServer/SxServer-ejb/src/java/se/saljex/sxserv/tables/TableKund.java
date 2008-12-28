/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "KUND")
@NamedQueries({})
public class TableKund implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "NUMMER", nullable = false)
	private String nummer;
	@Column(name = "NAMN", nullable = false)
	private String namn;
	@Column(name = "ADR1")
	private String adr1;
	@Column(name = "ADR2")
	private String adr2;
	@Column(name = "ADR3")
	private String adr3;
	@Column(name = "LNAMN")
	private String lnamn;
	@Column(name = "LADR2")
	private String ladr2;
	@Column(name = "LADR3")
	private String ladr3;
	@Column(name = "REF")
	private String ref;
	@Column(name = "SALJARE", nullable = false)
	private String saljare;
	@Column(name = "TEL")
	private String tel;
	@Column(name = "BILTEL")
	private String biltel;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "SOKARE")
	private String sokare;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "HEMSIDA")
	private String hemsida;
	@Column(name = "GRUPP")
	private String grupp;
	@Column(name = "GRUPP2")
	private String grupp2;
	@Column(name = "K_DAG", nullable = false)
	private short kDag;
	@Column(name = "K_TID")
	@Temporal(TemporalType.TIME)
	private Date kTid;
	@Column(name = "K_DATUM")
	@Temporal(TemporalType.DATE)
	private Date kDatum;
	@Column(name = "REGNR")
	private String regnr;
	@Column(name = "RANTFAKT", nullable = false)
	private short rantfakt;
	@Column(name = "FAKTOR", nullable = false)
	private short faktor;
	@Column(name = "KGRANS", nullable = false)
	private double kgrans;
	@Column(name = "TOT", nullable = false)
	private double tot;
	@Column(name = "NTOT", nullable = false)
	private double ntot;
	@Column(name = "OBET", nullable = false)
	private double obet;
	@Column(name = "NETTO", nullable = false)
	private double netto;
	@Column(name = "BTID", nullable = false)
	private int btid;
	@Column(name = "FAKTUROR", nullable = false)
	private int fakturor;
	@Column(name = "TBIDRAG", nullable = false)
	private double tbidrag;
	@Column(name = "KTID", nullable = false)
	private short ktid;
	@Column(name = "FAKTDAT")
	@Temporal(TemporalType.DATE)
	private Date faktdat;
	@Column(name = "NETTOLST")
	private String nettolst;
	@Column(name = "BONUS", nullable = false)
	private short bonus;
	@Column(name = "ELKUND", nullable = false)
	private short elkund;
	@Column(name = "VVSKUND", nullable = false)
	private short vvskund;
	@Column(name = "OVRIGKUND", nullable = false)
	private short ovrigkund;
	@Column(name = "INSTALLATOR", nullable = false)
	private short installator;
	@Column(name = "BUTIK", nullable = false)
	private short butik;
	@Column(name = "INDUSTRI", nullable = false)
	private short industri;
	@Column(name = "OEM", nullable = false)
	private short oem;
	@Column(name = "GROSSIST", nullable = false)
	private short grossist;
	@Column(name = "FLAGGA1", nullable = false)
	private short flagga1;
	@Column(name = "FLAGGA2", nullable = false)
	private short flagga2;
	@Column(name = "FLAGGA3", nullable = false)
	private short flagga3;
	@Column(name = "FLAGGA4", nullable = false)
	private short flagga4;
	@Column(name = "FLAGGA5", nullable = false)
	private short flagga5;
	@Column(name = "FLAGGA6", nullable = false)
	private short flagga6;
	@Column(name = "FLAGGA7", nullable = false)
	private short flagga7;
	@Column(name = "FLAGGA8", nullable = false)
	private short flagga8;
	@Column(name = "FLAGGA9", nullable = false)
	private short flagga9;
	@Column(name = "FLAGGA10", nullable = false)
	private short flagga10;
	@Column(name = "LEVVILLKOR")
	private String levvillkor;
	@Column(name = "MOTTAGARFRAKT", nullable = false)
	private short mottagarfrakt;
	@Column(name = "FRAKTBOLAG")
	private String fraktbolag;
	@Column(name = "FRAKTKUNDNR")
	private String fraktkundnr;
	@Column(name = "FRAKTFRIGRANS", nullable = false)
	private double fraktfrigrans;
	@Column(name = "FAKTORINGDAGAR", nullable = false)
	private short faktoringdagar;
	@Column(name = "ANT1")
	private String ant1;
	@Column(name = "ANT2")
	private String ant2;
	@Column(name = "ANT3")
	private String ant3;
	@Column(name = "DISTRIKT", nullable = false)
	private short distrikt;
	@Column(name = "VAKUND", nullable = false)
	private short vakund;
	@Column(name = "FASTIGHETSKUND", nullable = false)
	private short fastighetskund;
	@Column(name = "BASRAB", nullable = false)
	private double basrab;
	@Column(name = "REGDAT")
	@Temporal(TemporalType.DATE)
	private Date regdat;
	@Column(name = "GOLVKUND", nullable = false)
	private short golvkund;
	@Column(name = "EJFAKTURERBAR", nullable = false)
	private short ejfakturerbar;
	@Column(name = "SKRIVFAKTURARSKENR", nullable = false)
	private short skrivfakturarskenr;
	@Column(name = "SARFAKTURA", nullable = false)
	private short sarfaktura;
	@Column(name = "MOMSFRI", nullable = false)
	private short momsfri;
	@Column(name = "VECKOLEVDAG", nullable = false)
	private short veckolevdag;
	@Column(name = "KGRANSFORFALL30")
	private Double kgransforfall30;
	@Column(name = "KRAVORDERMARKE")
	private Short kravordermarke;
	@Column(name = "LINJENR1")
	private String linjenr1;
	@Column(name = "LINJENR2")
	private String linjenr2;
	@Column(name = "LINJENR3")
	private String linjenr3;

	public TableKund() {
	}

	public TableKund(String nummer) {
		this.nummer = nummer;
	}

	public TableKund(String nummer, String namn, String saljare, short kDag, short rantfakt, short faktor, double kgrans, double tot, double ntot, double obet, double netto, int btid, int fakturor, double tbidrag, short ktid, short bonus, short elkund, short vvskund, short ovrigkund, short installator, short butik, short industri, short oem, short grossist, short flagga1, short flagga2, short flagga3, short flagga4, short flagga5, short flagga6, short flagga7, short flagga8, short flagga9, short flagga10, short mottagarfrakt, double fraktfrigrans, short faktoringdagar, short distrikt, short vakund, short fastighetskund, double basrab, short golvkund, short ejfakturerbar, short skrivfakturarskenr, short sarfaktura, short momsfri, short veckolevdag) {
		this.nummer = nummer;
		this.namn = namn;
		this.saljare = saljare;
		this.kDag = kDag;
		this.rantfakt = rantfakt;
		this.faktor = faktor;
		this.kgrans = kgrans;
		this.tot = tot;
		this.ntot = ntot;
		this.obet = obet;
		this.netto = netto;
		this.btid = btid;
		this.fakturor = fakturor;
		this.tbidrag = tbidrag;
		this.ktid = ktid;
		this.bonus = bonus;
		this.elkund = elkund;
		this.vvskund = vvskund;
		this.ovrigkund = ovrigkund;
		this.installator = installator;
		this.butik = butik;
		this.industri = industri;
		this.oem = oem;
		this.grossist = grossist;
		this.flagga1 = flagga1;
		this.flagga2 = flagga2;
		this.flagga3 = flagga3;
		this.flagga4 = flagga4;
		this.flagga5 = flagga5;
		this.flagga6 = flagga6;
		this.flagga7 = flagga7;
		this.flagga8 = flagga8;
		this.flagga9 = flagga9;
		this.flagga10 = flagga10;
		this.mottagarfrakt = mottagarfrakt;
		this.fraktfrigrans = fraktfrigrans;
		this.faktoringdagar = faktoringdagar;
		this.distrikt = distrikt;
		this.vakund = vakund;
		this.fastighetskund = fastighetskund;
		this.basrab = basrab;
		this.golvkund = golvkund;
		this.ejfakturerbar = ejfakturerbar;
		this.skrivfakturarskenr = skrivfakturarskenr;
		this.sarfaktura = sarfaktura;
		this.momsfri = momsfri;
		this.veckolevdag = veckolevdag;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public String getAdr1() {
		return adr1;
	}

	public void setAdr1(String adr1) {
		this.adr1 = adr1;
	}

	public String getAdr2() {
		return adr2;
	}

	public void setAdr2(String adr2) {
		this.adr2 = adr2;
	}

	public String getAdr3() {
		return adr3;
	}

	public void setAdr3(String adr3) {
		this.adr3 = adr3;
	}

	public String getLnamn() {
		return lnamn;
	}

	public void setLnamn(String lnamn) {
		this.lnamn = lnamn;
	}

	public String getLadr2() {
		return ladr2;
	}

	public void setLadr2(String ladr2) {
		this.ladr2 = ladr2;
	}

	public String getLadr3() {
		return ladr3;
	}

	public void setLadr3(String ladr3) {
		this.ladr3 = ladr3;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getSaljare() {
		return saljare;
	}

	public void setSaljare(String saljare) {
		this.saljare = saljare;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBiltel() {
		return biltel;
	}

	public void setBiltel(String biltel) {
		this.biltel = biltel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSokare() {
		return sokare;
	}

	public void setSokare(String sokare) {
		this.sokare = sokare;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHemsida() {
		return hemsida;
	}

	public void setHemsida(String hemsida) {
		this.hemsida = hemsida;
	}

	public String getGrupp() {
		return grupp;
	}

	public void setGrupp(String grupp) {
		this.grupp = grupp;
	}

	public String getGrupp2() {
		return grupp2;
	}

	public void setGrupp2(String grupp2) {
		this.grupp2 = grupp2;
	}

	public short getKDag() {
		return kDag;
	}

	public void setKDag(short kDag) {
		this.kDag = kDag;
	}

	public Date getKTid() {
		return kTid;
	}

	public void setKTid(Date kTid) {
		this.kTid = kTid;
	}

	public Date getKDatum() {
		return kDatum;
	}

	public void setKDatum(Date kDatum) {
		this.kDatum = kDatum;
	}

	public String getRegnr() {
		return regnr;
	}

	public void setRegnr(String regnr) {
		this.regnr = regnr;
	}

	public short getRantfakt() {
		return rantfakt;
	}

	public void setRantfakt(short rantfakt) {
		this.rantfakt = rantfakt;
	}

	public short getFaktor() {
		return faktor;
	}

	public void setFaktor(short faktor) {
		this.faktor = faktor;
	}

	public double getKgrans() {
		return kgrans;
	}

	public void setKgrans(double kgrans) {
		this.kgrans = kgrans;
	}

	public double getTot() {
		return tot;
	}

	public void setTot(double tot) {
		this.tot = tot;
	}

	public double getNtot() {
		return ntot;
	}

	public void setNtot(double ntot) {
		this.ntot = ntot;
	}

	public double getObet() {
		return obet;
	}

	public void setObet(double obet) {
		this.obet = obet;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public int getBtid() {
		return btid;
	}

	public void setBtid(int btid) {
		this.btid = btid;
	}

	public int getFakturor() {
		return fakturor;
	}

	public void setFakturor(int fakturor) {
		this.fakturor = fakturor;
	}

	public double getTbidrag() {
		return tbidrag;
	}

	public void setTbidrag(double tbidrag) {
		this.tbidrag = tbidrag;
	}

	public short getKtid() {
		return ktid;
	}

	public void setKtid(short ktid) {
		this.ktid = ktid;
	}

	public Date getFaktdat() {
		return faktdat;
	}

	public void setFaktdat(Date faktdat) {
		this.faktdat = faktdat;
	}

	public String getNettolst() {
		return nettolst;
	}

	public void setNettolst(String nettolst) {
		this.nettolst = nettolst;
	}

	public short getBonus() {
		return bonus;
	}

	public void setBonus(short bonus) {
		this.bonus = bonus;
	}

	public short getElkund() {
		return elkund;
	}

	public void setElkund(short elkund) {
		this.elkund = elkund;
	}

	public short getVvskund() {
		return vvskund;
	}

	public void setVvskund(short vvskund) {
		this.vvskund = vvskund;
	}

	public short getOvrigkund() {
		return ovrigkund;
	}

	public void setOvrigkund(short ovrigkund) {
		this.ovrigkund = ovrigkund;
	}

	public short getInstallator() {
		return installator;
	}

	public void setInstallator(short installator) {
		this.installator = installator;
	}

	public short getButik() {
		return butik;
	}

	public void setButik(short butik) {
		this.butik = butik;
	}

	public short getIndustri() {
		return industri;
	}

	public void setIndustri(short industri) {
		this.industri = industri;
	}

	public short getOem() {
		return oem;
	}

	public void setOem(short oem) {
		this.oem = oem;
	}

	public short getGrossist() {
		return grossist;
	}

	public void setGrossist(short grossist) {
		this.grossist = grossist;
	}

	public short getFlagga1() {
		return flagga1;
	}

	public void setFlagga1(short flagga1) {
		this.flagga1 = flagga1;
	}

	public short getFlagga2() {
		return flagga2;
	}

	public void setFlagga2(short flagga2) {
		this.flagga2 = flagga2;
	}

	public short getFlagga3() {
		return flagga3;
	}

	public void setFlagga3(short flagga3) {
		this.flagga3 = flagga3;
	}

	public short getFlagga4() {
		return flagga4;
	}

	public void setFlagga4(short flagga4) {
		this.flagga4 = flagga4;
	}

	public short getFlagga5() {
		return flagga5;
	}

	public void setFlagga5(short flagga5) {
		this.flagga5 = flagga5;
	}

	public short getFlagga6() {
		return flagga6;
	}

	public void setFlagga6(short flagga6) {
		this.flagga6 = flagga6;
	}

	public short getFlagga7() {
		return flagga7;
	}

	public void setFlagga7(short flagga7) {
		this.flagga7 = flagga7;
	}

	public short getFlagga8() {
		return flagga8;
	}

	public void setFlagga8(short flagga8) {
		this.flagga8 = flagga8;
	}

	public short getFlagga9() {
		return flagga9;
	}

	public void setFlagga9(short flagga9) {
		this.flagga9 = flagga9;
	}

	public short getFlagga10() {
		return flagga10;
	}

	public void setFlagga10(short flagga10) {
		this.flagga10 = flagga10;
	}

	public String getLevvillkor() {
		return levvillkor;
	}

	public void setLevvillkor(String levvillkor) {
		this.levvillkor = levvillkor;
	}

	public short getMottagarfrakt() {
		return mottagarfrakt;
	}

	public void setMottagarfrakt(short mottagarfrakt) {
		this.mottagarfrakt = mottagarfrakt;
	}

	public String getFraktbolag() {
		return fraktbolag;
	}

	public void setFraktbolag(String fraktbolag) {
		this.fraktbolag = fraktbolag;
	}

	public String getFraktkundnr() {
		return fraktkundnr;
	}

	public void setFraktkundnr(String fraktkundnr) {
		this.fraktkundnr = fraktkundnr;
	}

	public double getFraktfrigrans() {
		return fraktfrigrans;
	}

	public void setFraktfrigrans(double fraktfrigrans) {
		this.fraktfrigrans = fraktfrigrans;
	}

	public short getFaktoringdagar() {
		return faktoringdagar;
	}

	public void setFaktoringdagar(short faktoringdagar) {
		this.faktoringdagar = faktoringdagar;
	}

	public String getAnt1() {
		return ant1;
	}

	public void setAnt1(String ant1) {
		this.ant1 = ant1;
	}

	public String getAnt2() {
		return ant2;
	}

	public void setAnt2(String ant2) {
		this.ant2 = ant2;
	}

	public String getAnt3() {
		return ant3;
	}

	public void setAnt3(String ant3) {
		this.ant3 = ant3;
	}

	public short getDistrikt() {
		return distrikt;
	}

	public void setDistrikt(short distrikt) {
		this.distrikt = distrikt;
	}

	public short getVakund() {
		return vakund;
	}

	public void setVakund(short vakund) {
		this.vakund = vakund;
	}

	public short getFastighetskund() {
		return fastighetskund;
	}

	public void setFastighetskund(short fastighetskund) {
		this.fastighetskund = fastighetskund;
	}

	public double getBasrab() {
		return basrab;
	}

	public void setBasrab(double basrab) {
		this.basrab = basrab;
	}

	public Date getRegdat() {
		return regdat;
	}

	public void setRegdat(Date regdat) {
		this.regdat = regdat;
	}

	public short getGolvkund() {
		return golvkund;
	}

	public void setGolvkund(short golvkund) {
		this.golvkund = golvkund;
	}

	public short getEjfakturerbar() {
		return ejfakturerbar;
	}

	public void setEjfakturerbar(short ejfakturerbar) {
		this.ejfakturerbar = ejfakturerbar;
	}

	public short getSkrivfakturarskenr() {
		return skrivfakturarskenr;
	}

	public void setSkrivfakturarskenr(short skrivfakturarskenr) {
		this.skrivfakturarskenr = skrivfakturarskenr;
	}

	public short getSarfaktura() {
		return sarfaktura;
	}

	public void setSarfaktura(short sarfaktura) {
		this.sarfaktura = sarfaktura;
	}

	public short getMomsfri() {
		return momsfri;
	}

	public void setMomsfri(short momsfri) {
		this.momsfri = momsfri;
	}

	public short getVeckolevdag() {
		return veckolevdag;
	}

	public void setVeckolevdag(short veckolevdag) {
		this.veckolevdag = veckolevdag;
	}

	public Double getKgransforfall30() {
		return kgransforfall30;
	}

	public void setKgransforfall30(Double kgransforfall30) {
		this.kgransforfall30 = kgransforfall30;
	}

	public Short getKravordermarke() {
		return kravordermarke;
	}

	public void setKravordermarke(Short kravordermarke) {
		this.kravordermarke = kravordermarke;
	}

	public String getLinjenr1() {
		return linjenr1;
	}

	public void setLinjenr1(String linjenr1) {
		this.linjenr1 = linjenr1;
	}

	public String getLinjenr2() {
		return linjenr2;
	}

	public void setLinjenr2(String linjenr2) {
		this.linjenr2 = linjenr2;
	}

	public String getLinjenr3() {
		return linjenr3;
	}

	public void setLinjenr3(String linjenr3) {
		this.linjenr3 = linjenr3;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (nummer != null ? nummer.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKund)) {
			return false;
		}
		TableKund other = (TableKund) object;
		if ((this.nummer == null && other.nummer != null) || (this.nummer != null && !this.nummer.equals(other.nummer))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableKund[nummer=" + nummer + "]";
	}

}
