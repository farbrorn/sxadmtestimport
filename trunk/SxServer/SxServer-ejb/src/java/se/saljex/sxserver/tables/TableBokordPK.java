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
public class TableBokordPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "konto")
	private String konto;
	@Basic(optional = false)
   @Column(name = "faktnr")
	private int faktnr;
	@Basic(optional = false)
   @Column(name = "typ")
	private String typ;
	@Basic(optional = false)
   @Column(name = "datum")
   @Temporal(TemporalType.DATE)
	private Date datum;

	public TableBokordPK() {
	}

	public TableBokordPK(String konto, int faktnr, String typ, Date datum) {
		this.konto = konto;
		this.faktnr = faktnr;
		this.typ = typ;
		this.datum = datum;
	}

	public String getKonto() {
		return konto;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}

	public int getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(int faktnr) {
		this.faktnr = faktnr;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
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
		hash += (konto != null ? konto.hashCode() : 0);
		hash += (int) faktnr;
		hash += (typ != null ? typ.hashCode() : 0);
		hash += (datum != null ? datum.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBokordPK)) {
			return false;
		}
		TableBokordPK other = (TableBokordPK) object;
		if ((this.konto == null && other.konto != null) || (this.konto != null && !this.konto.equals(other.konto))) {
			return false;
		}
		if (this.faktnr != other.faktnr) {
			return false;
		}
		if ((this.typ == null && other.typ != null) || (this.typ != null && !this.typ.equals(other.typ))) {
			return false;
		}
		if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableBokordPK[konto=" + konto + ", faktnr=" + faktnr + ", typ=" + typ + ", datum=" + datum + "]";
	}

}
