/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "RANTA")
@NamedQueries({
	@NamedQuery(name = "TableRanta.findAll", query = "SELECT t FROM TableRanta t")
	, @NamedQuery(name = "TableRanta.findByKund", query = "SELECT t FROM TableRanta t where t.tableRantaPK.kundnr=:kundnr order by t.tableRantaPK.faktnr")
})
public class TableRanta implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableRantaPK tableRantaPK;
	@Column(name = "FALLDAT")
   @Temporal(TemporalType.DATE)
	private Date falldat;
	@Basic(optional = false)
   @Column(name = "TOT")
	private double tot;
	@Basic(optional = false)
   @Column(name = "RANTA")
	private double ranta;
	@Basic(optional = false)
   @Column(name = "DAGAR")
	private int dagar;
	@Basic(optional = false)
   @Column(name = "RANTAPROC")
	private double rantaproc;

	public TableRanta() {
	}

	public TableRanta(TableRantaPK tableRantaPK) {
		this.tableRantaPK = tableRantaPK;
	}


	public TableRanta(String kundnr, int faktnr, Date betdat) {
		this.tableRantaPK = new TableRantaPK(kundnr, faktnr, betdat);
	}

	public TableRantaPK getTableRantaPK() {
		return tableRantaPK;
	}

	public void setTableRantaPK(TableRantaPK tableRantaPK) {
		this.tableRantaPK = tableRantaPK;
	}

	public Date getFalldat() {
		return falldat;
	}

	public void setFalldat(Date falldat) {
		this.falldat = falldat;
	}

	public double getTot() {
		return tot;
	}

	public void setTot(double tot) {
		this.tot = tot;
	}

	public double getRanta() {
		return ranta;
	}

	public void setRanta(double ranta) {
		this.ranta = ranta;
	}

	public int getDagar() {
		return dagar;
	}

	public void setDagar(int dagar) {
		this.dagar = dagar;
	}

	public double getRantaproc() {
		return rantaproc;
	}

	public void setRantaproc(double rantaproc) {
		this.rantaproc = rantaproc;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableRantaPK != null ? tableRantaPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableRanta)) {
			return false;
		}
		TableRanta other = (TableRanta) object;
		if ((this.tableRantaPK == null && other.tableRantaPK != null) || (this.tableRantaPK != null && !this.tableRantaPK.equals(other.tableRantaPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableRanta[tableRantaPK=" + tableRantaPK + "]";
	}

}
