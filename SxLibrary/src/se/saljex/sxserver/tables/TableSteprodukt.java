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

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "steprodukt")
@NamedQueries({@NamedQuery(name = "TableSteprodukt.findAll", query = "SELECT t FROM TableSteprodukt t")})
public class TableSteprodukt implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "sn")
	private String sn;
	@Column(name = "crdt", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdt;
	@Column(name = "instdatum")
	@Temporal(TemporalType.DATE)
	private Date instdatum;
	@Column(name = "anvandare")
	private String anvandare;
	@Column(name = "artnr")
	private String artnr;
	@Column(name = "modell")
	private String modell;
	@Column(name = "namn")
	private String namn;
	@Column(name = "adr1")
	private String adr1;
	@Column(name = "adr2")
	private String adr2;
	@Column(name = "adr3")
	private String adr3;
	@Column(name = "referens")
	private String referens;
	@Column(name = "tel")
	private String tel;
	@Column(name = "mobil")
	private String mobil;
	@Column(name = "epost")
	private String epost;
	@Column(name = "installatornamn")
	private String installatornamn;
	@Column(name = "installatorkundnr")
	private String installatorkundnr;
	@Column(name = "faktnr")
	private Integer faktnr;

	public TableSteprodukt() {
	}

	public TableSteprodukt(String sn) {
		this.sn = sn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getCrdt() {
		return crdt;
	}

	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}

	public Date getInstdatum() {
		return instdatum;
	}

	public void setInstdatum(Date instdatum) {
		this.instdatum = instdatum;
	}

	public String getAnvandare() {
		return anvandare;
	}

	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public String getModell() {
		return modell;
	}

	public void setModell(String modell) {
		this.modell = modell;
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

	public String getReferens() {
		return referens;
	}

	public void setReferens(String referens) {
		this.referens = referens;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public String getEpost() {
		return epost;
	}

	public void setEpost(String epost) {
		this.epost = epost;
	}

	public String getInstallatornamn() {
		return installatornamn;
	}

	public void setInstallatornamn(String installatornamn) {
		this.installatornamn = installatornamn;
	}

	public String getInstallatorkundnr() {
		return installatorkundnr;
	}

	public void setInstallatorkundnr(String installatorkundnr) {
		this.installatorkundnr = installatorkundnr;
	}

	public Integer getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(Integer faktnr) {
		this.faktnr = faktnr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (sn != null ? sn.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSteprodukt)) {
			return false;
		}
		TableSteprodukt other = (TableSteprodukt) object;
		if ((this.sn == null && other.sn != null) || (this.sn != null && !this.sn.equals(other.sn))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableSteprodukt[sn=" + sn + "]";
	}

}
