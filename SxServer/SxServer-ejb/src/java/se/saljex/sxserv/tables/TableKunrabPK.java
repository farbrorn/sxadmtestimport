/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ulf
 */
@Embeddable
public class TableKunrabPK implements Serializable {
	@Column(name = "KUNDNR", nullable = false)
	private String kundnr;
	@Column(name = "RABKOD", nullable = false)
	private String rabkod;
	@Column(name = "KOD1", nullable = false)
	private String kod1;

	public TableKunrabPK() {
	}

	public TableKunrabPK(String kundnr, String rabkod, String kod1) {
		this.kundnr = kundnr;
		this.rabkod = rabkod;
		this.kod1 = kod1;
	}

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
	}

	public String getRabkod() {
		return rabkod;
	}

	public void setRabkod(String rabkod) {
		this.rabkod = rabkod;
	}

	public String getKod1() {
		return kod1;
	}

	public void setKod1(String kod1) {
		this.kod1 = kod1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kundnr != null ? kundnr.hashCode() : 0);
		hash += (rabkod != null ? rabkod.hashCode() : 0);
		hash += (kod1 != null ? kod1.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKunrabPK)) {
			return false;
		}
		TableKunrabPK other = (TableKunrabPK) object;
		if ((this.kundnr == null && other.kundnr != null) || (this.kundnr != null && !this.kundnr.equals(other.kundnr))) {
			return false;
		}
		if ((this.rabkod == null && other.rabkod != null) || (this.rabkod != null && !this.rabkod.equals(other.rabkod))) {
			return false;
		}
		if ((this.kod1 == null && other.kod1 != null) || (this.kod1 != null && !this.kod1.equals(other.kod1))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableKunrabPK[kundnr=" + kundnr + ", rabkod=" + rabkod + ", kod1=" + kod1 + "]";
	}

}
