/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "KUNRAB")
@NamedQueries({})
public class TableKunrab implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableKunrabPK tableKunrabPK;
	@Column(name = "RAB", nullable = false)
	private double rab;
	@Column(name = "DATUM")
	@Temporal(TemporalType.DATE)
	private Date datum;

	public TableKunrab() {
	}

	public TableKunrab(TableKunrabPK tableKunrabPK) {
		this.tableKunrabPK = tableKunrabPK;
	}

	public TableKunrab(TableKunrabPK tableKunrabPK, double rab) {
		this.tableKunrabPK = tableKunrabPK;
		this.rab = rab;
	}

	public TableKunrab(String kundnr, String rabkod, String kod1) {
		this.tableKunrabPK = new TableKunrabPK(kundnr, rabkod, kod1);
	}

	public TableKunrabPK getTableKunrabPK() {
		return tableKunrabPK;
	}

	public void setTableKunrabPK(TableKunrabPK tableKunrabPK) {
		this.tableKunrabPK = tableKunrabPK;
	}

	public double getRab() {
		return rab;
	}

	public void setRab(double rab) {
		this.rab = rab;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableKunrabPK != null ? tableKunrabPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKunrab)) {
			return false;
		}
		TableKunrab other = (TableKunrab) object;
		if ((this.tableKunrabPK == null && other.tableKunrabPK != null) || (this.tableKunrabPK != null && !this.tableKunrabPK.equals(other.tableKunrabPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableKunrab[tableKunrabPK=" + tableKunrabPK + "]";
	}

}
