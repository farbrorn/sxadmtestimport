/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulf
 */
@Embeddable
public class TableOrderhandPK implements Serializable {
	@Column(name = "ORDERNR", nullable = false)
	private int ordernr;
	@Column(name = "DATUM", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datum;
	@Column(name = "TID", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date tid;

	public TableOrderhandPK() {
	}

	public TableOrderhandPK(int ordernr, Date datum, Date tid) {
		this.ordernr = ordernr;
		this.datum = datum;
		this.tid = tid;
	}

	public int getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(int ordernr) {
		this.ordernr = ordernr;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Date getTid() {
		return tid;
	}

	public void setTid(Date tid) {
		this.tid = tid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) ordernr;
		hash += (datum != null ? datum.hashCode() : 0);
		hash += (tid != null ? tid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableOrderhandPK)) {
			return false;
		}
		TableOrderhandPK other = (TableOrderhandPK) object;
		if (this.ordernr != other.ordernr) {
			return false;
		}
		if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum))) {
			return false;
		}
		if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableOrderhandPK[ordernr=" + ordernr + ", datum=" + datum + ", tid=" + tid + "]";
	}

}
