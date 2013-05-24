/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulf
 */
@Embeddable
public class TableKunhandPK implements Serializable {
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "kundnr")
	private String kundnr;
	@Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
	private Date datum;
	@Basic(optional = false)
    @NotNull
    @Column(name = "tid")
    @Temporal(TemporalType.TIME)
	private Date tid;

	public TableKunhandPK() {
	}

	public TableKunhandPK(String kundnr, Date datum, Date tid) {
		this.kundnr = kundnr;
		this.datum = datum;
		this.tid = tid;
	}

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
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
		hash += (kundnr != null ? kundnr.hashCode() : 0);
		hash += (datum != null ? datum.hashCode() : 0);
		hash += (tid != null ? tid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKunhandPK)) {
			return false;
		}
		TableKunhandPK other = (TableKunhandPK) object;
		if ((this.kundnr == null && other.kundnr != null) || (this.kundnr != null && !this.kundnr.equals(other.kundnr))) {
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
		return "se.saljex.sxserver.tables.TableKunhandPK[ kundnr=" + kundnr + ", datum=" + datum + ", tid=" + tid + " ]";
	}
	
}
