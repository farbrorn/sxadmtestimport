/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Embeddable
public class TableLagerhandPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "artnr")
	private String artnr;
	@Basic(optional = false)
   @Column(name = "lagernr")
	private short lagernr;
	@Basic(optional = false)
   @Column(name = "datum")
   @Temporal(TemporalType.DATE)
	private Date datum;
	@Basic(optional = false)
   @Column(name = "tid")
   @Temporal(TemporalType.TIME)
	private Date tid;

	public TableLagerhandPK() {
	}

	public TableLagerhandPK(String artnr, short lagernr, Date datum, Date tid) {
		this.artnr = artnr;
		this.lagernr = lagernr;
		this.datum = datum;
		this.tid = tid;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public short getLagernr() {
		return lagernr;
	}

	public void setLagernr(short lagernr) {
		this.lagernr = lagernr;
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
		hash += (artnr != null ? artnr.hashCode() : 0);
		hash += (int) lagernr;
		hash += (datum != null ? datum.hashCode() : 0);
		hash += (tid != null ? tid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableLagerhandPK)) {
			return false;
		}
		TableLagerhandPK other = (TableLagerhandPK) object;
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		if (this.lagernr != other.lagernr) {
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
		return "se.saljex.sxserver.tables.TableLagerhandPK[artnr=" + artnr + ", lagernr=" + lagernr + ", datum=" + datum + ", tid=" + tid + "]";
	}

}
