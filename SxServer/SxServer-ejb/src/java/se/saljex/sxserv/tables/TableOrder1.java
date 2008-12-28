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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "ORDER1")
@NamedQueries({
	@NamedQuery(name = "TableOrder1.findAll", query = "SELECT o FROM TableOrder1 o order by o.ordernr desc"), 
	@NamedQuery(name = "TableOrder1.findByKundnr", query = "SELECT o FROM TableOrder1 o where o.kundnr = :kundnr order by o.ordernr desc")
})
public class TableOrder1 implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ORDERNR", nullable = false)
	private Integer ordernr;
	@Column(name = "DELLEV")
	private Short dellev;
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
	@Column(name = "SALJARE")
	private String saljare;
	@Column(name = "REFERENS")
	private String referens;
	@Column(name = "KUNDNR", nullable = false)
	private String kundnr;
	@Column(name = "MARKE")
	private String marke;
	@Column(name = "DATUM")
	@Temporal(TemporalType.DATE)
	private Date datum;
	@Column(name = "MOMS", nullable = false)
	private short moms;
	@Column(name = "STATUS", nullable = false)
	private String status;
	@Column(name = "KTID", nullable = false)
	private short ktid;
	@Column(name = "BONUS", nullable = false)
	private short bonus;
	@Column(name = "FAKTOR", nullable = false)
	private short faktor;
	@Column(name = "LEVDAT")
	@Temporal(TemporalType.DATE)
	private Date levdat;
	@Column(name = "LEVVILLKOR")
	private String levvillkor;
	@Column(name = "MOTTAGARFRAKT", nullable = false)
	private short mottagarfrakt;
	@Column(name = "FRAKTKUNDNR")
	private String fraktkundnr;
	@Column(name = "FRAKTBOLAG")
	private String fraktbolag;
	@Column(name = "FRAKTFRIGRANS", nullable = false)
	private double fraktfrigrans;
	@Column(name = "LAGERNR", nullable = false)
	private short lagernr;
	@Column(name = "DIREKTLEVNR", nullable = false)
	private int direktlevnr;
	@Column(name = "RETURORDER", nullable = false)
	private short returorder;
	@Column(name = "LASTAV")
	private String lastav;
	@Column(name = "LASTDATUM")
	@Temporal(TemporalType.DATE)
	private Date lastdatum;
	@Column(name = "LASTTID")
	@Temporal(TemporalType.TIME)
	private Date lasttid;
	@Column(name = "TID")
	@Temporal(TemporalType.TIME)
	private Date tid;
	@Column(name = "VECKOLEVDAG", nullable = false)
	private short veckolevdag;
	@Column(name = "DOLJDATUM")
	@Temporal(TemporalType.DATE)
	private Date doljdatum;
	@Column(name = "TILLANNANFILIAL", nullable = false)
	private short tillannanfilial;
	@Column(name = "UTLEVBOKAD")
	private Short utlevbokad;
	@Column(name = "ANNANLEVADRESS")
	private Short annanlevadress;
	@Column(name = "ORDERMEDDELANDE")
	private String ordermeddelande;
	@Column(name = "TIDIGASTFAKTDATUM")
	@Temporal(TemporalType.DATE)
	private Date tidigastfaktdatum;
	@Column(name = "WORDERNR")
	private Integer wordernr;
	@Column(name = "LINJENR1")
	private String linjenr1;
	@Column(name = "LINJENR2")
	private String linjenr2;
	@Column(name = "LINJENR3")
	private String linjenr3;

	public TableOrder1() {
	}

	public TableOrder1(Integer ordernr) {
		this.ordernr = ordernr;
	}


	public Integer getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(Integer ordernr) {
		this.ordernr = ordernr;
	}

	public Short getDellev() {
		return dellev;
	}

	public void setDellev(Short dellev) {
		this.dellev = dellev;
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

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
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

	public short getMoms() {
		return moms;
	}

	public void setMoms(short moms) {
		this.moms = moms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public short getKtid() {
		return ktid;
	}

	public void setKtid(short ktid) {
		this.ktid = ktid;
	}

	public short getBonus() {
		return bonus;
	}

	public void setBonus(short bonus) {
		this.bonus = bonus;
	}

	public short getFaktor() {
		return faktor;
	}

	public void setFaktor(short faktor) {
		this.faktor = faktor;
	}

	public Date getLevdat() {
		return levdat;
	}

	public void setLevdat(Date levdat) {
		this.levdat = levdat;
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

	public short getReturorder() {
		return returorder;
	}

	public void setReturorder(short returorder) {
		this.returorder = returorder;
	}

	public String getLastav() {
		return lastav;
	}

	public void setLastav(String lastav) {
		this.lastav = lastav;
	}

	public Date getLastdatum() {
		return lastdatum;
	}

	public void setLastdatum(Date lastdatum) {
		this.lastdatum = lastdatum;
	}

	public Date getLasttid() {
		return lasttid;
	}

	public void setLasttid(Date lasttid) {
		this.lasttid = lasttid;
	}

	public Date getTid() {
		return tid;
	}

	public void setTid(Date tid) {
		this.tid = tid;
	}

	public short getVeckolevdag() {
		return veckolevdag;
	}

	public void setVeckolevdag(short veckolevdag) {
		this.veckolevdag = veckolevdag;
	}

	public Date getDoljdatum() {
		return doljdatum;
	}

	public void setDoljdatum(Date doljdatum) {
		this.doljdatum = doljdatum;
	}

	public short getTillannanfilial() {
		return tillannanfilial;
	}

	public void setTillannanfilial(short tillannanfilial) {
		this.tillannanfilial = tillannanfilial;
	}

	public Short getUtlevbokad() {
		return utlevbokad;
	}

	public void setUtlevbokad(Short utlevbokad) {
		this.utlevbokad = utlevbokad;
	}

	public Short getAnnanlevadress() {
		return annanlevadress;
	}

	public void setAnnanlevadress(Short annanlevadress) {
		this.annanlevadress = annanlevadress;
	}

	public String getOrdermeddelande() {
		return ordermeddelande;
	}

	public void setOrdermeddelande(String ordermeddelande) {
		this.ordermeddelande = ordermeddelande;
	}

	public Date getTidigastfaktdatum() {
		return tidigastfaktdatum;
	}

	public void setTidigastfaktdatum(Date tidigastfaktdatum) {
		this.tidigastfaktdatum = tidigastfaktdatum;
	}

	public Integer getWordernr() {
		return wordernr;
	}

	public void setWordernr(Integer wordernr) {
		this.wordernr = wordernr;
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
		hash += (ordernr != null ? ordernr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableOrder1)) {
			return false;
		}
		TableOrder1 other = (TableOrder1) object;
		if ((this.ordernr == null && other.ordernr != null) || (this.ordernr != null && !this.ordernr.equals(other.ordernr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableOrder1[ordernr=" + ordernr + "]";
	}

}
