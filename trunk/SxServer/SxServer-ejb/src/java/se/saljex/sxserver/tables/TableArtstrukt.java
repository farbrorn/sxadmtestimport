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
@Table(name = "artstrukt")
@NamedQueries({@NamedQuery(name = "TableArtstrukt.findByNummer", query = "SELECT t FROM TableArtstrukt t WHERE t.tableArtstruktPK.nummer = :nummer order by t.tableArtstruktPK.artnr")})
public class TableArtstrukt implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableArtstruktPK tableArtstruktPK;
	@Column(name = "namn")
	private String namn;
	@Basic(optional = false)
	@Column(name = "pris")
	private float pris;
	@Basic(optional = false)
	@Column(name = "rab")
	private float rab;
	@Column(name = "enh")
	private String enh;
	@Basic(optional = false)
	@Column(name = "antal")
	private float antal;
	@Basic(optional = false)
	@Column(name = "netto")
	private float netto;
	@Column(name = "datum")
	@Temporal(TemporalType.DATE)
	private Date datum;

	public TableArtstrukt() {
	}

	public TableArtstrukt(TableArtstruktPK tableArtstruktPK) {
		this.tableArtstruktPK = tableArtstruktPK;
	}

	public TableArtstrukt(TableArtstruktPK tableArtstruktPK, float pris, float rab, float antal, float netto) {
		this.tableArtstruktPK = tableArtstruktPK;
		this.pris = pris;
		this.rab = rab;
		this.antal = antal;
		this.netto = netto;
	}

	public TableArtstrukt(String nummer, String artnr) {
		this.tableArtstruktPK = new TableArtstruktPK(nummer, artnr);
	}

	public TableArtstruktPK getTableArtstruktPK() {
		return tableArtstruktPK;
	}

	public void setTableArtstruktPK(TableArtstruktPK tableArtstruktPK) {
		this.tableArtstruktPK = tableArtstruktPK;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public float getPris() {
		return pris;
	}

	public void setPris(float pris) {
		this.pris = pris;
	}

	public float getRab() {
		return rab;
	}

	public void setRab(float rab) {
		this.rab = rab;
	}

	public String getEnh() {
		return enh;
	}

	public void setEnh(String enh) {
		this.enh = enh;
	}

	public float getAntal() {
		return antal;
	}

	public void setAntal(float antal) {
		this.antal = antal;
	}

	public float getNetto() {
		return netto;
	}

	public void setNetto(float netto) {
		this.netto = netto;
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
		hash += (tableArtstruktPK != null ? tableArtstruktPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableArtstrukt)) {
			return false;
		}
		TableArtstrukt other = (TableArtstrukt) object;
		if ((this.tableArtstruktPK == null && other.tableArtstruktPK != null) || (this.tableArtstruktPK != null && !this.tableArtstruktPK.equals(other.tableArtstruktPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableArtstrukt[tableArtstruktPK=" + tableArtstruktPK + "]";
	}

}
