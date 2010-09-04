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

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "bokord")
@NamedQueries({
	@NamedQuery(name = "TableBokord.findAll", query = "SELECT t FROM TableBokord t")})
public class TableBokord implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableBokordPK tableBokordPK;
	@Basic(optional = false)
   @Column(name = "summa")
	private double summa;
	@Column(name = "kundnr")
	private String kundnr;
	@Column(name = "namn")
	private String namn;
	@Basic(optional = false)
   @Column(name = "ar")
	private short ar;
	@Basic(optional = false)
   @Column(name = "man")
	private short man;

	public TableBokord() {
	}

	public TableBokord(TableBokordPK tableBokordPK) {
		this.tableBokordPK = tableBokordPK;
	}

	public TableBokord(String konto, int faktnr, String typ, Date datum) {
		this.tableBokordPK = new TableBokordPK(konto, faktnr, typ, datum);
	}

	public TableBokordPK getTableBokordPK() {
		return tableBokordPK;
	}

	public void setTableBokordPK(TableBokordPK tableBokordPK) {
		this.tableBokordPK = tableBokordPK;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}

	public String getKundnr() {
		return kundnr;
	}

	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public short getAr() {
		return ar;
	}

	public void setAr(short ar) {
		this.ar = ar;
	}

	public short getMan() {
		return man;
	}

	public void setMan(short man) {
		this.man = man;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableBokordPK != null ? tableBokordPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBokord)) {
			return false;
		}
		TableBokord other = (TableBokord) object;
		if ((this.tableBokordPK == null && other.tableBokordPK != null) || (this.tableBokordPK != null && !this.tableBokordPK.equals(other.tableBokordPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableBokord[tableBokordPK=" + tableBokordPK + "]";
	}

}
