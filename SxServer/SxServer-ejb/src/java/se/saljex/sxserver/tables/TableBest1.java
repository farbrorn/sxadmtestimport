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
@Table(name = "BEST1")
@NamedQueries({@NamedQuery(name = "TableBest1.findAllSendEpost", query = "SELECT b FROM TableBest1 b WHERE b.status = 'Skapad' and b.skickasom = 'epost' order by b.bestnr desc"), @NamedQuery(name = "TableBest1.findAllSendPaminEpost", query = "SELECT b FROM TableBest1 b WHERE b.status = 'Skickad' and b.skickasom = 'epost' and b.bekrdat is null and (b.pamindat is null or b.pamindat < :pamindat) and b.datum < :datum order by b.bestnr desc")})
public class TableBest1 implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BESTNR", nullable = false)
	private Integer bestnr;
	@Column(name = "LEVNR", nullable = false)
	private String levnr;
	@Column(name = "LEVNAMN")
	private String levnamn;
	@Column(name = "LEVADR1")
	private String levadr1;
	@Column(name = "LEVADR2")
	private String levadr2;
	@Column(name = "LEVADR3")
	private String levadr3;
	@Column(name = "VAR_REF")
	private String varRef;
	@Column(name = "ER_REF")
	private String erRef;
	@Column(name = "LEVERANS")
	private String leverans;
	@Column(name = "MARKE")
	private String marke;
	@Column(name = "DATUM")
	@Temporal(TemporalType.DATE)
	private Date datum;
	@Column(name = "SUMMA", nullable = false)
	private double summa;
	@Column(name = "BEKRDAT")
	@Temporal(TemporalType.DATE)
	private Date bekrdat;
	@Column(name = "FRAKTFRITT")
	private Double fraktfritt;
	@Column(name = "MOTTAGARFRAKT", nullable = false)
	private short mottagarfrakt;
	@Column(name = "LEVVILLKOR1")
	private String levvillkor1;
	@Column(name = "LEVVILLKOR2")
	private String levvillkor2;
	@Column(name = "LEVVILLKOR3")
	private String levvillkor3;
	@Column(name = "BESTEJPRIS", nullable = false)
	private short bestejpris;
	@Column(name = "LAGERNR", nullable = false)
	private short lagernr;
	@Column(name = "LEVADR0")
	private String levadr0;
	@Column(name = "ORDERNR", nullable = false)
	private int ordernr;
	@Column(name = "AUTOBESTALLD", nullable = false)
	private short autobestalld;
	@Column(name = "SKICKASOM")
	private String skickasom;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "MEDDELANDE")
	private String meddelande;
	@Column(name = "SAKERHETSKOD")
	private Integer sakerhetskod;
	@Column(name = "ANTALFELINLOGGNINGAR")
	private Integer antalfelinloggningar;
	@Column(name = "SXSERVSANDFORSOK")
	private Short sxservsandforsok;
	@Column(name = "PAMINDAT")
	@Temporal(TemporalType.DATE)
	private Date pamindat;
	@Column(name = "ANTALPAMIN")
	private Integer antalpamin;

	public TableBest1() {
	}

	public TableBest1(Integer bestnr) {
		this.bestnr = bestnr;
	}

	public TableBest1(Integer bestnr, String levnr, double summa, short mottagarfrakt, short bestejpris, short lagernr, int ordernr, short autobestalld) {
		this.bestnr = bestnr;
		this.levnr = levnr;
		this.summa = summa;
		this.mottagarfrakt = mottagarfrakt;
		this.bestejpris = bestejpris;
		this.lagernr = lagernr;
		this.ordernr = ordernr;
		this.autobestalld = autobestalld;
	}

	public Integer getBestnr() {
		return bestnr;
	}

	public void setBestnr(Integer bestnr) {
		this.bestnr = bestnr;
	}

	public String getLevnr() {
		return levnr;
	}

	public void setLevnr(String levnr) {
		this.levnr = levnr;
	}

	public String getLevnamn() {
		return levnamn;
	}

	public void setLevnamn(String levnamn) {
		this.levnamn = levnamn;
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

	public String getVarRef() {
		return varRef;
	}

	public void setVarRef(String varRef) {
		this.varRef = varRef;
	}

	public String getErRef() {
		return erRef;
	}

	public void setErRef(String erRef) {
		this.erRef = erRef;
	}

	public String getLeverans() {
		return leverans;
	}

	public void setLeverans(String leverans) {
		this.leverans = leverans;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}

	public Date getBekrdat() {
		return bekrdat;
	}

	public void setBekrdat(Date bekrdat) {
		this.bekrdat = bekrdat;
	}

	public Double getFraktfritt() {
		return fraktfritt;
	}

	public void setFraktfritt(Double fraktfritt) {
		this.fraktfritt = fraktfritt;
	}

	public short getMottagarfrakt() {
		return mottagarfrakt;
	}

	public void setMottagarfrakt(short mottagarfrakt) {
		this.mottagarfrakt = mottagarfrakt;
	}

	public String getLevvillkor1() {
		return levvillkor1;
	}

	public void setLevvillkor1(String levvillkor1) {
		this.levvillkor1 = levvillkor1;
	}

	public String getLevvillkor2() {
		return levvillkor2;
	}

	public void setLevvillkor2(String levvillkor2) {
		this.levvillkor2 = levvillkor2;
	}

	public String getLevvillkor3() {
		return levvillkor3;
	}

	public void setLevvillkor3(String levvillkor3) {
		this.levvillkor3 = levvillkor3;
	}

	public short getBestejpris() {
		return bestejpris;
	}

	public void setBestejpris(short bestejpris) {
		this.bestejpris = bestejpris;
	}

	public short getLagernr() {
		return lagernr;
	}

	public void setLagernr(short lagernr) {
		this.lagernr = lagernr;
	}

	public String getLevadr0() {
		return levadr0;
	}

	public void setLevadr0(String levadr0) {
		this.levadr0 = levadr0;
	}

	public int getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(int ordernr) {
		this.ordernr = ordernr;
	}

	public short getAutobestalld() {
		return autobestalld;
	}

	public void setAutobestalld(short autobestalld) {
		this.autobestalld = autobestalld;
	}

	public String getSkickasom() {
		return skickasom;
	}

	public void setSkickasom(String skickasom) {
		this.skickasom = skickasom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMeddelande() {
		return meddelande;
	}

	public void setMeddelande(String meddelande) {
		this.meddelande = meddelande;
	}

	public Integer getSakerhetskod() {
		return sakerhetskod;
	}

	public void setSakerhetskod(Integer sakerhetskod) {
		this.sakerhetskod = sakerhetskod;
	}

	public Integer getAntalfelinloggningar() {
		return antalfelinloggningar;
	}

	public void setAntalfelinloggningar(Integer antalfelinloggningar) {
		this.antalfelinloggningar = antalfelinloggningar;
	}

	public Short getSxservsandforsok() {
		return sxservsandforsok;
	}

	public void setSxservsandforsok(Short sxservsandforsok) {
		this.sxservsandforsok = sxservsandforsok;
	}

	public Date getPamindat() {
		return pamindat;
	}

	public void setPamindat(Date pamindat) {
		this.pamindat = pamindat;
	}

	public Integer getAntalpamin() {
		return antalpamin;
	}

	public void setAntalpamin(Integer antalpamin) {
		this.antalpamin = antalpamin;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (bestnr != null ? bestnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBest1)) {
			return false;
		}
		TableBest1 other = (TableBest1) object;
		if ((this.bestnr == null && other.bestnr != null) || (this.bestnr != null && !this.bestnr.equals(other.bestnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableBest1[bestnr=" + bestnr + "]";
	}

}
