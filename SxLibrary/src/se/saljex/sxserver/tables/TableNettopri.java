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
@Table(name = "NETTOPRI")
@NamedQueries({})
public class TableNettopri implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableNettopriPK tableNettopriPK;
	@Column(name = "PRIS", nullable = false)
	private double pris;
	@Column(name = "VALUTA")
	private String valuta;
	@Column(name = "DATUM")
	@Temporal(TemporalType.DATE)
	private Date datum;

	public TableNettopri() {
	}

	public TableNettopri(TableNettopriPK tableNettopriPK) {
		this.tableNettopriPK = tableNettopriPK;
	}

	public TableNettopri(TableNettopriPK tableNettopriPK, double pris) {
		this.tableNettopriPK = tableNettopriPK;
		this.pris = pris;
	}

	public TableNettopri(String lista, String artnr) {
		this.tableNettopriPK = new TableNettopriPK(lista, artnr);
	}

	public TableNettopriPK getTableNettopriPK() {
		return tableNettopriPK;
	}

	public void setTableNettopriPK(TableNettopriPK tableNettopriPK) {
		this.tableNettopriPK = tableNettopriPK;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public String getValuta() {
		return valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
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
		hash += (tableNettopriPK != null ? tableNettopriPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableNettopri)) {
			return false;
		}
		TableNettopri other = (TableNettopri) object;
		if ((this.tableNettopriPK == null && other.tableNettopriPK != null) || (this.tableNettopriPK != null && !this.tableNettopriPK.equals(other.tableNettopriPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableNettopri[tableNettopriPK=" + tableNettopriPK + "]";
	}

}
