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
public class TableArtstatPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "artnr")
	private String artnr;
	@Basic(optional = false)
   @Column(name = "ar")
	private short ar;
	@Basic(optional = false)
   @Column(name = "man")
	private short man;

	public TableArtstatPK() {
	}

	public TableArtstatPK(String artnr, short ar, short man) {
		this.artnr = artnr;
		this.ar = ar;
		this.man = man;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public short getAr() {
		return ar;
	}

	public void setAr(short ar) {
		this.ar = ar;
	}

	public short getMan() {
		return man;
	}

	public void setMan(short man) {
		this.man = man;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (artnr != null ? artnr.hashCode() : 0);
		hash += (int) ar;
		hash += (int) man;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableArtstatPK)) {
			return false;
		}
		TableArtstatPK other = (TableArtstatPK) object;
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		if (this.ar != other.ar) {
			return false;
		}
		if (this.man != other.man) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableArtstatPK[artnr=" + artnr + ", ar=" + ar + ", man=" + man + "]";
	}

}
