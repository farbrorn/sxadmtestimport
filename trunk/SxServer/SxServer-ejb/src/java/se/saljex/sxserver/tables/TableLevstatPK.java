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
public class TableLevstatPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "levnr")
	private String levnr;
	@Basic(optional = false)
   @Column(name = "ar")
	private short ar;
	@Basic(optional = false)
   @Column(name = "man")
	private short man;

	public TableLevstatPK() {
	}

	public TableLevstatPK(String levnr, short ar, short man) {
		this.levnr = levnr;
		this.ar = ar;
		this.man = man;
	}

	public String getLevnr() {
		return levnr;
	}

	public void setLevnr(String levnr) {
		this.levnr = levnr;
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
		hash += (levnr != null ? levnr.hashCode() : 0);
		hash += (int) ar;
		hash += (int) man;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableLevstatPK)) {
			return false;
		}
		TableLevstatPK other = (TableLevstatPK) object;
		if ((this.levnr == null && other.levnr != null) || (this.levnr != null && !this.levnr.equals(other.levnr))) {
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
		return "se.saljex.sxserver.tables.TableLevstatPK[levnr=" + levnr + ", ar=" + ar + ", man=" + man + "]";
	}

}
