/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "FAKTURA2")
@NamedQueries({@NamedQuery(name = "TableFaktura2.findByFaktnr", query = "SELECT t FROM TableFaktura2 t WHERE t.tableFaktura2PK.faktnr = :faktnr order by t.tableFaktura2PK.pos")})

public class TableFaktura2 implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableFaktura2PK tableFaktura2PK;
	@Column(name = "PRISNR", nullable = false)
	private short prisnr;
	@Column(name = "ARTNR")
	private String artnr;
	@Column(name = "RAB", nullable = false)
	private double rab;
	@Column(name = "LEV", nullable = false)
	private double lev;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "PRIS", nullable = false)
	private double pris;
	@Column(name = "SUMMA", nullable = false)
	private double summa;
	@Column(name = "KONTO")
	private String konto;
	@Column(name = "NETTO", nullable = false)
	private double netto;
	@Column(name = "ENH")
	private String enh;
	@Column(name = "NAMN")
	private String namn;
	@Column(name = "BON_NR")
	private Integer bonNr;
	@Column(name = "ORDERNR", nullable = false)
	private int ordernr;
	@Column(name = "RANTAFAKTURANR")
	private Integer rantafakturanr;
	@Column(name = "RANTAFALLDATUM")
	@Temporal(TemporalType.DATE)
	private Date rantafalldatum;
	@Column(name = "RANTABETALDATUM")
	@Temporal(TemporalType.DATE)
	private Date rantabetaldatum;
	@Column(name = "RANTABETALBELOPP")
	private Double rantabetalbelopp;
	@Column(name = "RANTAPROC")
	private Double rantaproc;
	@Column(name = "STJID")
	private Integer stjid;

	public TableFaktura2() {
	}

	public TableFaktura2(TableFaktura2PK tableFaktura2PK) {
		this.tableFaktura2PK = tableFaktura2PK;
	}

	public TableFaktura2(TableFaktura2PK tableFaktura2PK, short prisnr, double rab, double lev, double pris, double summa, double netto, int ordernr) {
		this.tableFaktura2PK = tableFaktura2PK;
		this.prisnr = prisnr;
		this.rab = rab;
		this.lev = lev;
		this.pris = pris;
		this.summa = summa;
		this.netto = netto;
		this.ordernr = ordernr;
	}

	public TableFaktura2(int faktnr, short pos) {
		this.tableFaktura2PK = new TableFaktura2PK(faktnr, pos);
	}

	public TableFaktura2PK getTableFaktura2PK() {
		return tableFaktura2PK;
	}

	public void setTableFaktura2PK(TableFaktura2PK tableFaktura2PK) {
		this.tableFaktura2PK = tableFaktura2PK;
	}

	public short getPrisnr() {
		return prisnr;
	}

	public void setPrisnr(short prisnr) {
		this.prisnr = prisnr;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public double getRab() {
		return rab;
	}

	public void setRab(double rab) {
		this.rab = rab;
	}

	public double getLev() {
		return lev;
	}

	public void setLev(double lev) {
		this.lev = lev;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}

	public String getKonto() {
		return konto;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public String getEnh() {
		return enh;
	}

	public void setEnh(String enh) {
		this.enh = enh;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public Integer getBonNr() {
		return bonNr;
	}

	public void setBonNr(Integer bonNr) {
		this.bonNr = bonNr;
	}

	public int getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(int ordernr) {
		this.ordernr = ordernr;
	}

	public Integer getRantafakturanr() {
		return rantafakturanr;
	}

	public void setRantafakturanr(Integer rantafakturanr) {
		this.rantafakturanr = rantafakturanr;
	}

	public Date getRantafalldatum() {
		return rantafalldatum;
	}

	public void setRantafalldatum(Date rantafalldatum) {
		this.rantafalldatum = rantafalldatum;
	}

	public Date getRantabetaldatum() {
		return rantabetaldatum;
	}

	public void setRantabetaldatum(Date rantabetaldatum) {
		this.rantabetaldatum = rantabetaldatum;
	}

	public Double getRantabetalbelopp() {
		return rantabetalbelopp;
	}

	public void setRantabetalbelopp(Double rantabetalbelopp) {
		this.rantabetalbelopp = rantabetalbelopp;
	}

	public Double getRantaproc() {
		return rantaproc;
	}

	public void setRantaproc(Double rantaproc) {
		this.rantaproc = rantaproc;
	}

	public Integer getStjid() {
		return stjid;
	}

	public void setStjid(Integer stjid) {
		this.stjid = stjid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableFaktura2PK != null ? tableFaktura2PK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableFaktura2)) {
			return false;
		}
		TableFaktura2 other = (TableFaktura2) object;
		if ((this.tableFaktura2PK == null && other.tableFaktura2PK != null) || (this.tableFaktura2PK != null && !this.tableFaktura2PK.equals(other.tableFaktura2PK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableFaktura2[tableFaktura2PK=" + tableFaktura2PK + "]";
	}

}
