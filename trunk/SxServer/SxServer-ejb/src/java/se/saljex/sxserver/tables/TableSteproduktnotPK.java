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
public class TableSteproduktnotPK implements Serializable {
	@Basic(optional = false)
	@Column(name = "sn")
	private String sn;
	@Basic(optional = false)
	@Column(name = "id")
	private int id;

	public TableSteproduktnotPK() {
	}
	
	public TableSteproduktnotPK(String sn, int id) {
		this.sn = sn;
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (sn != null ? sn.hashCode() : 0);
		hash += (int) id;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSteproduktnotPK)) {
			return false;
		}
		TableSteproduktnotPK other = (TableSteproduktnotPK) object;
		if ((this.sn == null && other.sn != null) || (this.sn != null && !this.sn.equals(other.sn))) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableSteproduktnotPK[sn=" + sn + ", id=" + id + "]";
	}

}
