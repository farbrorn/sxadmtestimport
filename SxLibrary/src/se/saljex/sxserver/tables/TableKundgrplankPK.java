/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulf
 */
@Embeddable
public class TableKundgrplankPK implements Serializable {
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "kundnr")
	private String kundnr;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "grpnr")
	private String grpnr;

	public TableKundgrplankPK() {
	}

	public TableKundgrplankPK(String kundnr, String grpnr) {
		this.kundnr = kundnr;
		this.grpnr = grpnr;
	}

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
	}

	public String getGrpnr() {
		return grpnr;
	}

	public void setGrpnr(String grpnr) {
		this.grpnr = grpnr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kundnr != null ? kundnr.hashCode() : 0);
		hash += (grpnr != null ? grpnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKundgrplankPK)) {
			return false;
		}
		TableKundgrplankPK other = (TableKundgrplankPK) object;
		if ((this.kundnr == null && other.kundnr != null) || (this.kundnr != null && !this.kundnr.equals(other.kundnr))) {
			return false;
		}
		if ((this.grpnr == null && other.grpnr != null) || (this.grpnr != null && !this.grpnr.equals(other.grpnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableKundgrplankPK[ kundnr=" + kundnr + ", grpnr=" + grpnr + " ]";
	}
	
}
