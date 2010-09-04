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
@Table(name = "faktorut")
@NamedQueries({
	@NamedQuery(name = "TableFaktorut.findAll", query = "SELECT t FROM TableFaktorut t")})
public class TableFaktorut implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
   @Basic(optional = false)
   @Column(name = "faktnr")
	private Integer faktnr;
	@Column(name = "datum")
   @Temporal(TemporalType.DATE)
	private Date datum;
	@Basic(optional = false)
   @Column(name = "tot")
	private double tot;
	@Basic(optional = false)
   @Column(name = "kundnr")
	private String kundnr;
	@Column(name = "namn")
	private String namn;
	@Column(name = "falldat")
   @Temporal(TemporalType.DATE)
	private Date falldat;

	public TableFaktorut() {
	}

	public TableFaktorut(Integer faktnr) {
		this.faktnr = faktnr;
	}


	public Integer getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(Integer faktnr) {
		this.faktnr = faktnr;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public double getTot() {
		return tot;
	}

	public void setTot(double tot) {
		this.tot = tot;
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

	public Date getFalldat() {
		return falldat;
	}

	public void setFalldat(Date falldat) {
		this.falldat = falldat;
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
		if (!(object instanceof TableFaktorut)) {
			return false;
		}
		TableFaktorut other = (TableFaktorut) object;
		if ((this.faktnr == null && other.faktnr != null) || (this.faktnr != null && !this.faktnr.equals(other.faktnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableFaktorut[faktnr=" + faktnr + "]";
	}

}
