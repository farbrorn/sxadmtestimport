/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

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
public class TableBesthandPK implements Serializable {
	@Column(name = "BESTNR", nullable = false)
	private int bestnr;
	@Column(name = "DATUM", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datum;
	@Column(name = "TID", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date tid;

	public TableBesthandPK() {
	}

	public TableBesthandPK(int bestnr, Date datum, Date tid) {
		this.bestnr = bestnr;
		this.datum = datum;
		this.tid = tid;
	}

	public int getBestnr() {
		return bestnr;
	}

	public void setBestnr(int bestnr) {
		this.bestnr = bestnr;
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
		hash += (int) bestnr;
		hash += (datum != null ? datum.hashCode() : 0);
		hash += (tid != null ? tid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBesthandPK)) {
			return false;
		}
		TableBesthandPK other = (TableBesthandPK) object;
		if (this.bestnr != other.bestnr) {
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
		return "se.saljex.sxserver.TableBesthandPK[bestnr=" + bestnr + ", datum=" + datum + ", tid=" + tid + "]";
	}

}
