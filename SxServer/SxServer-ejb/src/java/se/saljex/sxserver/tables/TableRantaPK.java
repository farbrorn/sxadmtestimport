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
public class TableRantaPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "KUNDNR")
	private String kundnr;
	@Basic(optional = false)
   @Column(name = "FAKTNR")
	private int faktnr;
	@Basic(optional = false)
   @Column(name = "BETDAT")
   @Temporal(TemporalType.DATE)
	private Date betdat;

	public TableRantaPK() {
	}

	public TableRantaPK(String kundnr, int faktnr, Date betdat) {
		this.kundnr = kundnr;
		this.faktnr = faktnr;
		this.betdat = betdat;
	}

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
	}

	public int getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(int faktnr) {
		this.faktnr = faktnr;
	}

	public Date getBetdat() {
		return betdat;
	}

	public void setBetdat(Date betdat) {
		this.betdat = betdat;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kundnr != null ? kundnr.hashCode() : 0);
		hash += (int) faktnr;
		hash += (betdat != null ? betdat.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableRantaPK)) {
			return false;
		}
		TableRantaPK other = (TableRantaPK) object;
		if ((this.kundnr == null && other.kundnr != null) || (this.kundnr != null && !this.kundnr.equals(other.kundnr))) {
			return false;
		}
		if (this.faktnr != other.faktnr) {
			return false;
		}
		if ((this.betdat == null && other.betdat != null) || (this.betdat != null && !this.betdat.equals(other.betdat))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableRantaPK[kundnr=" + kundnr + ", faktnr=" + faktnr + ", betdat=" + betdat + "]";
	}

}
