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
public class TableSljstatPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "saljare")
	private String saljare;
	@Basic(optional = false)
   @Column(name = "ar")
	private short ar;
	@Basic(optional = false)
   @Column(name = "man")
	private short man;

	public TableSljstatPK() {
	}

	public TableSljstatPK(String saljare, short ar, short man) {
		this.saljare = saljare;
		this.ar = ar;
		this.man = man;
	}

	public String getSaljare() {
		return saljare;
	}

	public void setSaljare(String saljare) {
		this.saljare = saljare;
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
		hash += (saljare != null ? saljare.hashCode() : 0);
		hash += (int) ar;
		hash += (int) man;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSljstatPK)) {
			return false;
		}
		TableSljstatPK other = (TableSljstatPK) object;
		if ((this.saljare == null && other.saljare != null) || (this.saljare != null && !this.saljare.equals(other.saljare))) {
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
		return "se.saljex.sxserver.tables.TableSljstatPK[saljare=" + saljare + ", ar=" + ar + ", man=" + man + "]";
	}

}
