/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "FAKTURA1")
@NamedQueries({})
public class TableFaktura1 implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "FAKTNR", nullable = false)
	private Integer faktnr;
	@Column(name = "KUNDNR", nullable = false)
	private String kundnr;
	@Column(name = "NAMN")
	private String namn;
	@Column(name = "ADR1")
	private String adr1;
	@Column(name = "ADR2")
	private String adr2;
	@Column(name = "ADR3")
	private String adr3;
	@Column(name = "LEVADR1")
	private String levadr1;
	@Column(name = "LEVADR2")
	private String levadr2;
	@Column(name = "LEVADR3")
	private String levadr3;
	@Column(name = "DATUM", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datum;
	@Column(name = "SALJARE")
	private String saljare;
	@Column(name = "REFERENS")
	private String referens;
	@Column(name = "MARKE")
	private String marke;
	@Column(name = "MOMS", nullable = false)
	private short moms;
	@Column(name = "KTID", nullable = false)
	private short ktid;
	@Column(name = "RANTA", nullable = false)
	private double ranta;
	@Column(name = "BONUS", nullable = false)
	private short bonus;
	@Column(name = "MOTTAGARFRAKT", nullable = false)
	private short mottagarfrakt;
	@Column(name = "LEVVILLKOR")
	private String levvillkor;
	@Column(name = "FRAKTKUNDNR")
	private String fraktkundnr;
	@Column(name = "FRAKTBOLAG")
	private String fraktbolag;
	@Column(name = "FRAKTFRIGRANS", nullable = false)
	private double fraktfrigrans;
	@Column(name = "LEVDAT")
	@Temporal(TemporalType.DATE)
	private Date levdat;
	@Column(name = "ORDERDAT")
	@Temporal(TemporalType.DATE)
	private Date orderdat;
	@Column(name = "ORDERNR", nullable = false)
	private int ordernr;
	@Column(name = "FAKTOR", nullable = false)
	private short faktor;
	@Column(name = "TEXT1")
	private String text1;
	@Column(name = "TEXT2")
	private String text2;
	@Column(name = "TEXT3")
	private String text3;
	@Column(name = "TEXT4")
	private String text4;
	@Column(name = "TEXT5")
	private String text5;
	@Column(name = "FAKTORTEXT1")
	private String faktortext1;
	@Column(name = "FAKTORTEXT2")
	private String faktortext2;
	@Column(name = "FAKTORTEXT3")
	private String faktortext3;
	@Column(name = "RANTFAKT", nullable = false)
	private short rantfakt;
	@Column(name = "T_NETTO", nullable = false)
	private double tNetto;
	@Column(name = "T_MOMS", nullable = false)
	private double tMoms;
	@Column(name = "T_ORUT", nullable = false)
	private double tOrut;
	@Column(name = "T_ATTBETALA", nullable = false)
	private double tAttbetala;
	@Column(name = "T_INNETTO", nullable = false)
	private double tInnetto;
	@Column(name = "LAGERNR", nullable = false)
	private short lagernr;
	@Column(name = "DIREKTLEVNR", nullable = false)
	private int direktlevnr;
	@Column(name = "MOMSPROC", nullable = false)
	private double momsproc;
	@Column(name = "INKASSOSTATUS")
	private String inkassostatus;
	@Column(name = "INKASSODATUM")
	@Temporal(TemporalType.DATE)
	private Date inkassodatum;

	public TableFaktura1() {
	}


	public Integer getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(Integer faktnr) {
		this.faktnr = faktnr;
	}

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
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

	public String getLevadr1() {
		return levadr1;
	}

	public void setLevadr1(String levadr1) {
		this.levadr1 = levadr1;
	}

	public String getLevadr2() {
		return levadr2;
	}

	public void setLevadr2(String levadr2) {
		this.levadr2 = levadr2;
	}

	public String getLevadr3() {
		return levadr3;
	}

	public void setLevadr3(String levadr3) {
		this.levadr3 = levadr3;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getSaljare() {
		return saljare;
	}

	public void setSaljare(String saljare) {
		this.saljare = saljare;
	}

	public String getReferens() {
		return referens;
	}

	public void setReferens(String referens) {
		this.referens = referens;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public short getMoms() {
		return moms;
	}

	public void setMoms(short moms) {
		this.moms = moms;
	}

	public short getKtid() {
		return ktid;
	}

	public void setKtid(short ktid) {
		this.ktid = ktid;
	}

	public double getRanta() {
		return ranta;
	}

	public void setRanta(double ranta) {
		this.ranta = ranta;
	}

	public short getBonus() {
		return bonus;
	}

	public void setBonus(short bonus) {
		this.bonus = bonus;
	}

	public short getMottagarfrakt() {
		return mottagarfrakt;
	}

	public void setMottagarfrakt(short mottagarfrakt) {
		this.mottagarfrakt = mottagarfrakt;
	}

	public String getLevvillkor() {
		return levvillkor;
	}

	public void setLevvillkor(String levvillkor) {
		this.levvillkor = levvillkor;
	}

	public String getFraktkundnr() {
		return fraktkundnr;
	}

	public void setFraktkundnr(String fraktkundnr) {
		this.fraktkundnr = fraktkundnr;
	}

	public String getFraktbolag() {
		return fraktbolag;
	}

	public void setFraktbolag(String fraktbolag) {
		this.fraktbolag = fraktbolag;
	}

	public double getFraktfrigrans() {
		return fraktfrigrans;
	}

	public void setFraktfrigrans(double fraktfrigrans) {
		this.fraktfrigrans = fraktfrigrans;
	}

	public Date getLevdat() {
		return levdat;
	}

	public void setLevdat(Date levdat) {
		this.levdat = levdat;
	}

	public Date getOrderdat() {
		return orderdat;
	}

	public void setOrderdat(Date orderdat) {
		this.orderdat = orderdat;
	}

	public int getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(int ordernr) {
		this.ordernr = ordernr;
	}

	public short getFaktor() {
		return faktor;
	}

	public void setFaktor(short faktor) {
		this.faktor = faktor;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public String getText4() {
		return text4;
	}

	public void setText4(String text4) {
		this.text4 = text4;
	}

	public String getText5() {
		return text5;
	}

	public void setText5(String text5) {
		this.text5 = text5;
	}

	public String getFaktortext1() {
		return faktortext1;
	}

	public void setFaktortext1(String faktortext1) {
		this.faktortext1 = faktortext1;
	}

	public String getFaktortext2() {
		return faktortext2;
	}

	public void setFaktortext2(String faktortext2) {
		this.faktortext2 = faktortext2;
	}

	public String getFaktortext3() {
		return faktortext3;
	}

	public void setFaktortext3(String faktortext3) {
		this.faktortext3 = faktortext3;
	}

	public short getRantfakt() {
		return rantfakt;
	}

	public void setRantfakt(short rantfakt) {
		this.rantfakt = rantfakt;
	}

	public double getTNetto() {
		return tNetto;
	}

	public void setTNetto(double tNetto) {
		this.tNetto = tNetto;
	}

	public double getTMoms() {
		return tMoms;
	}

	public void setTMoms(double tMoms) {
		this.tMoms = tMoms;
	}

	public double getTOrut() {
		return tOrut;
	}

	public void setTOrut(double tOrut) {
		this.tOrut = tOrut;
	}

	public double getTAttbetala() {
		return tAttbetala;
	}

	public void setTAttbetala(double tAttbetala) {
		this.tAttbetala = tAttbetala;
	}

	public double getTInnetto() {
		return tInnetto;
	}

	public void setTInnetto(double tInnetto) {
		this.tInnetto = tInnetto;
	}

	public short getLagernr() {
		return lagernr;
	}

	public void setLagernr(short lagernr) {
		this.lagernr = lagernr;
	}

	public int getDirektlevnr() {
		return direktlevnr;
	}

	public void setDirektlevnr(int direktlevnr) {
		this.direktlevnr = direktlevnr;
	}

	public double getMomsproc() {
		return momsproc;
	}

	public void setMomsproc(double momsproc) {
		this.momsproc = momsproc;
	}

	public String getInkassostatus() {
		return inkassostatus;
	}

	public void setInkassostatus(String inkassostatus) {
		this.inkassostatus = inkassostatus;
	}

	public Date getInkassodatum() {
		return inkassodatum;
	}

	public void setInkassodatum(Date inkassodatum) {
		this.inkassodatum = inkassodatum;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (faktnr != null ? faktnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableFaktura1)) {
			return false;
		}
		TableFaktura1 other = (TableFaktura1) object;
		if ((this.faktnr == null && other.faktnr != null) || (this.faktnr != null && !this.faktnr.equals(other.faktnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.Faktura1[faktnr=" + faktnr + "]";
	}

}
