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
public class TableStatistikPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "ar")
	private short ar;
	@Basic(optional = false)
   @Column(name = "man")
	private short man;

	public TableStatistikPK() {
	}

	public TableStatistikPK(short ar, short man) {
		this.ar = ar;
		this.man = man;
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
		hash += (int) ar;
		hash += (int) man;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableStatistikPK)) {
			return false;
		}
		TableStatistikPK other = (TableStatistikPK) object;
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
		return "se.saljex.sxserver.tables.TableStatistikPK[ar=" + ar + ", man=" + man + "]";
	}

}
