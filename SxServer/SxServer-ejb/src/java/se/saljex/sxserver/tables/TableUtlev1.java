/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import se.saljex.sxserver.OrderHandler;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "utlev1")
@NamedQueries({
	@NamedQuery(name = "TableUtlev1.findAll", query = "SELECT t FROM TableUtlev1 t"),
	@NamedQuery(name = "TableUtlev1.findByOrdernr", query = "SELECT t FROM TableUtlev1 t where t.ordernr = :ordernr")
})
public class TableUtlev1 implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @Column(name = "ordernr")
	private Integer ordernr;
	@Column(name = "dellev")
	private Short dellev;
	@Column(name = "namn")
	private String namn;
	@Column(name = "adr1")
	private String adr1;
	@Column(name = "adr2")
	private String adr2;
	@Column(name = "adr3")
	private String adr3;
	@Column(name = "levadr1")
	private String levadr1;
	@Column(name = "levadr2")
	private String levadr2;
	@Column(name = "levadr3")
	private String levadr3;
	@Column(name = "saljare")
	private String saljare;
	@Column(name = "referens")
	private String referens;
	@Basic(optional = false)
    @Column(name = "kundnr")
	private String kundnr;
	@Column(name = "marke")
	private String marke;
	@Column(name = "datum")
    @Temporal(TemporalType.DATE)
	private Date datum;
	@Basic(optional = false)
    @Column(name = "moms")
	private short moms;
	@Basic(optional = false)
    @Column(name = "status")
	private String status;
	@Basic(optional = false)
    @Column(name = "ktid")
	private short ktid;
	@Basic(optional = false)
    @Column(name = "bonus")
	private short bonus;
	@Basic(optional = false)
    @Column(name = "faktor")
	private short faktor;
	@Column(name = "levdat")
    @Temporal(TemporalType.DATE)
	private Date levdat;
	@Column(name = "levvillkor")
	private String levvillkor;
	@Basic(optional = false)
    @Column(name = "mottagarfrakt")
	private short mottagarfrakt;
	@Column(name = "fraktkundnr")
	private String fraktkundnr;
	@Column(name = "fraktbolag")
	private String fraktbolag;
	@Basic(optional = false)
    @Column(name = "fraktfrigrans")
	private double fraktfrigrans;
	@Basic(optional = false)
    @Column(name = "lagernr")
	private short lagernr;
	@Basic(optional = false)
    @Column(name = "returorder")
	private short returorder;
	@Basic(optional = false)
    @Column(name = "direktlevnr")
	private int direktlevnr;
	@Column(name = "tid")
    @Temporal(TemporalType.TIME)
	private Date tid;
	@Basic(optional = false)
    @Column(name = "faktnr")
	private int faktnr;
	@Basic(optional = false)
    @Column(name = "veckolevdag")
	private short veckolevdag;
	@Column(name = "annanlevadress")
	private Short annanlevadress;
	@Column(name = "ordermeddelande")
	private String ordermeddelande;
	@Column(name = "wordernr")
	private Integer wordernr;
	@Column(name = "linjenr1")
	private String linjenr1;
	@Column(name = "linjenr2")
	private String linjenr2;
	@Column(name = "linjenr3")
	private String linjenr3;
	@Basic(optional = false)
    @Column(name = "kundordernr")
	private int kundordernr;
	@Basic(optional = false)
    @Column(name = "forskatt")
	private short forskatt;
	@Basic(optional = false)
    @Column(name = "forskattbetald")
	private short forskattbetald;
	@Column(name = "betalsatt")
	private String betalsatt;

	public TableUtlev1() {
	}

	public TableUtlev1(Integer ordernr) {
		this.ordernr = ordernr;
	}

	public TableUtlev1(TableOrder1 o) {
		order12Utlev1(o);
	}


	public final void order12Utlev1(TableOrder1 o) {
		setAdr1(o.getAdr1());
		setAdr2(o.getAdr2());
		setAdr3(o.getAdr3());
		setAnnanlevadress(o.getAnnanlevadress());
		setBetalsatt(o.getBetalsatt());
		setBonus(o.getBonus());
		setDatum(o.getDatum());
		setDellev(o.getDellev());
		setDirektlevnr(o.getDellev());
		setFaktor(o.getFaktor());
		setForskatt(o.getForskatt());
		setForskattbetald(o.getForskattbetald());
		setFraktbolag(o.getFraktbolag());
		setFraktfrigrans(o.getFraktfrigrans());
		setFraktkundnr(o.getFraktkundnr());
		setKtid(o.getKtid());
		setKundnr(o.getKundnr());
		setKundordernr(o.getKundordernr());
		setLagernr(o.getLagernr());
		setLevadr1(o.getLevadr1());
		setLevadr2(o.getLevadr2());
		setLevadr3(o.getLevadr3());
		setLevdat(o.getLevdat());
		setLevvillkor(o.getLevvillkor());
		setLinjenr1(o.getLinjenr1());
		setLinjenr2(o.getLinjenr2());
		setLinjenr3(o.getLinjenr3());
		setMarke(o.getMarke());
		setMoms(o.getMoms());
		setMottagarfrakt(o.getMottagarfrakt());
		setNamn(o.getNamn());
		setOrdermeddelande(o.getOrdermeddelande());
		setOrdernr(o.getOrdernr());
		setReferens(o.getReferens());
		setReturorder(o.getReturorder());
		setSaljare(o.getSaljare());
		setStatus(o.getStatus());
		setTid(o.getTid());
		setVeckolevdag(o.getVeckolevdag());
		setWordernr(o.getWordernr());
		
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

	public short getReturorder() {
		return returorder;
	}

	public void setReturorder(short returorder) {
		this.returorder = returorder;
	}

	public int getDirektlevnr() {
		return direktlevnr;
	}

	public void setDirektlevnr(int direktlevnr) {
		this.direktlevnr = direktlevnr;
	}

	public Date getTid() {
		return tid;
	}

	public void setTid(Date tid) {
		this.tid = tid;
	}

	public int getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(int faktnr) {
		this.faktnr = faktnr;
	}

	public short getVeckolevdag() {
		return veckolevdag;
	}

	public void setVeckolevdag(short veckolevdag) {
		this.veckolevdag = veckolevdag;
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

	public int getKundordernr() {
		return kundordernr;
	}

	public void setKundordernr(int kundordernr) {
		this.kundordernr = kundordernr;
	}

	public boolean getForskatt() {
		return forskatt!=0;
	}

	public void setForskatt(boolean forskatt) {
		if (forskatt) this.forskatt = 1; else this.forskatt = 0;
	}

	public boolean getForskattbetald() {
		return forskattbetald!=0;
	}

	public void setForskattbetald(boolean forskattbetald) {
		if (forskattbetald) this.forskattbetald = 1; else this.forskattbetald=0;
	}

	public String getBetalsatt() {
		return betalsatt;
	}

	public void setBetalsatt(String betalsatt) {
		this.betalsatt = betalsatt;
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
		if (!(object instanceof TableUtlev1)) {
			return false;
		}
		TableUtlev1 other = (TableUtlev1) object;
		if ((this.ordernr == null && other.ordernr != null) || (this.ordernr != null && !this.ordernr.equals(other.ordernr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableUtlev1[ordernr=" + ordernr + "]";
	}

}
