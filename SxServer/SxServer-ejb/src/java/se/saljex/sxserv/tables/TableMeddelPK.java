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
public class TableMeddelPK implements Serializable {
	@Column(name = "SALJARE", nullable = false)
	private String saljare;
	@Column(name = "AVLAST", nullable = false)
	private short avlast;
	@Column(name = "DATUM", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datum;
	@Column(name = "TID", nullable = false)
	@Temporal(TemporalType.TIME)
	private Date tid;

	public TableMeddelPK() {
	}

	public TableMeddelPK(String saljare, short avlast, Date datum, Date tid) {
		this.saljare = saljare;
		this.avlast = avlast;
		this.datum = datum;
		this.tid = tid;
	}

	public String getSaljare() {
		return saljare;
	}

	public void setSaljare(String saljare) {
		this.saljare = saljare;
	}

	public short getAvlast() {
		return avlast;
	}

	public void setAvlast(short avlast) {
		this.avlast = avlast;
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
		hash += (saljare != null ? saljare.hashCode() : 0);
		hash += (int) avlast;
		hash += (datum != null ? datum.hashCode() : 0);
		hash += (tid != null ? tid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableMeddelPK)) {
			return false;
		}
		TableMeddelPK other = (TableMeddelPK) object;
		if ((this.saljare == null && other.saljare != null) || (this.saljare != null && !this.saljare.equals(other.saljare))) {
			return false;
		}
		if (this.avlast != other.avlast) {
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
		return "se.saljex.sxserver.TableMeddelPK[saljare=" + saljare + ", avlast=" + avlast + ", datum=" + datum + ", tid=" + tid + "]";
	}

}
