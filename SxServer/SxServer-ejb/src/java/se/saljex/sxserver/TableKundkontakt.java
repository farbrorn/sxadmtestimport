/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "KUNDKONTAKT")
@NamedQueries({@NamedQuery(name = "TableKundkontakt.findByKontaktid", query = "SELECT t FROM TableKundkontakt t WHERE t.kontaktid = :kontaktid"), @NamedQuery(name = "TableKundkontakt.findByKundnr", query = "SELECT t FROM TableKundkontakt t WHERE t.kundnr = :kundnr order by t.namn")})
public class TableKundkontakt implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "KONTAKTID")
	private Integer kontaktid;
	@Basic(optional = false)
	@Column(name = "KUNDNR")
	private String kundnr;
	@Column(name = "NAMN")
	private String namn;
	@Column(name = "TEL")
	private String tel;
	@Column(name = "MOBIL")
	private String mobil;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "ADR1")
	private String adr1;
	@Column(name = "ADR2")
	private String adr2;
	@Column(name = "ADR3")
	private String adr3;
	@Column(name = "EPOST")
	private String epost;
	@Column(name = "EKONOMI")
	private Short ekonomi;
	@Column(name = "INFO")
	private Short info;

	public TableKundkontakt() {
	}

	public Integer getKontaktid() {
		return kontaktid;
	}

	public void setKontaktid(Integer kontaktid) {
		this.kontaktid = kontaktid;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getEpost() {
		return epost;
	}

	public void setEpost(String epost) {
		this.epost = epost;
	}

	public Short getEkonomi() {
		return ekonomi;
	}

	public void setEkonomi(Short ekonomi) {
		this.ekonomi = ekonomi;
	}

	public Short getInfo() {
		return info;
	}

	public void setInfo(Short info) {
		this.info = info;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kontaktid != null ? kontaktid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKundkontakt)) {
			return false;
		}
		TableKundkontakt other = (TableKundkontakt) object;
		if ((this.kontaktid == null && other.kontaktid != null) || (this.kontaktid != null && !this.kontaktid.equals(other.kontaktid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableKundkontakt[kontaktid=" + kontaktid + "]";
	}

}
