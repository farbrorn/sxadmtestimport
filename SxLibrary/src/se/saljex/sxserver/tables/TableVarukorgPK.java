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
public class TableVarukorgPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "kontaktid")
	private int kontaktid;
	@Basic(optional = false)
	@Column(name = "typ")
	private String typ;
	@Basic(optional = false)
	@Column(name = "artnr")
	private String artnr;

	public TableVarukorgPK() {
	}

	public TableVarukorgPK(int kontaktid, String typ, String artnr) {
		this.kontaktid = kontaktid;
		this.typ = typ;
		this.artnr = artnr;
	}

	public int getKontaktid() {
		return kontaktid;
	}

	public void setKontaktid(int kontaktid) {
		this.kontaktid = kontaktid;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
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
		hash += (int) kontaktid;
		hash += (typ != null ? typ.hashCode() : 0);
		hash += (artnr != null ? artnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableVarukorgPK)) {
			return false;
		}
		TableVarukorgPK other = (TableVarukorgPK) object;
		if (this.kontaktid != other.kontaktid) {
			return false;
		}
		if ((this.typ == null && other.typ != null) || (this.typ != null && !this.typ.equals(other.typ))) {
			return false;
		}
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableVarukorgPK[kontaktid=" + kontaktid + ", typ=" + typ + ", artnr=" + artnr + "]";
	}

}
