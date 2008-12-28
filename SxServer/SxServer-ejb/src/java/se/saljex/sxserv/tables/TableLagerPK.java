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
public class TableLagerPK implements Serializable {
	@Column(name = "ARTNR", nullable = false)
	private String artnr;
	@Column(name = "LAGERNR", nullable = false)
	private short lagernr;

	public TableLagerPK() {
	}

	public TableLagerPK(String artnr, short lagernr) {
		this.artnr = artnr;
		this.lagernr = lagernr;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public short getLagernr() {
		return lagernr;
	}

	public void setLagernr(short lagernr) {
		this.lagernr = lagernr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (artnr != null ? artnr.hashCode() : 0);
		hash += (int) lagernr;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableLagerPK)) {
			return false;
		}
		TableLagerPK other = (TableLagerPK) object;
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		if (this.lagernr != other.lagernr) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableLagerPK[artnr=" + artnr + ", lagernr=" + lagernr + "]";
	}

}
