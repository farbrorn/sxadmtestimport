/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ulf
 */
@Embeddable
public class TableArtstruktPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "nummer")
	private String nummer;
	@Basic(optional = false)
	@Column(name = "artnr")
	private String artnr;

	public TableArtstruktPK() {
	}

	public TableArtstruktPK(String nummer, String artnr) {
		this.nummer = nummer;
		this.artnr = artnr;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (nummer != null ? nummer.hashCode() : 0);
		hash += (artnr != null ? artnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableArtstruktPK)) {
			return false;
		}
		TableArtstruktPK other = (TableArtstruktPK) object;
		if ((this.nummer == null && other.nummer != null) || (this.nummer != null && !this.nummer.equals(other.nummer))) {
			return false;
		}
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableArtstruktPK[nummer=" + nummer + ", artnr=" + artnr + "]";
	}

}
