/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
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
@Table(name = "levstat")
@NamedQueries({
	@NamedQuery(name = "TableLevstat.findAll", query = "SELECT t FROM TableLevstat t")})
public class TableLevstat implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableLevstatPK tableLevstatPK;
	@Basic(optional = false)
   @Column(name = "ftot")
	private double ftot;
	@Basic(optional = false)
   @Column(name = "ftbidrag")
	private double ftbidrag;
	@Basic(optional = false)
   @Column(name = "tot")
	private double tot;

	public TableLevstat() {
	}

	public TableLevstat(TableLevstatPK tableLevstatPK) {
		this.tableLevstatPK = tableLevstatPK;
	}

	public TableLevstat(TableLevstatPK tableLevstatPK, double ftot, double ftbidrag, double tot) {
		this.tableLevstatPK = tableLevstatPK;
		this.ftot = ftot;
		this.ftbidrag = ftbidrag;
		this.tot = tot;
	}

	public TableLevstat(String levnr, short ar, short man) {
		this.tableLevstatPK = new TableLevstatPK(levnr, ar, man);
	}

	public TableLevstatPK getTableLevstatPK() {
		return tableLevstatPK;
	}

	public void setTableLevstatPK(TableLevstatPK tableLevstatPK) {
		this.tableLevstatPK = tableLevstatPK;
	}

	public double getFtot() {
		return ftot;
	}

	public void setFtot(double ftot) {
		this.ftot = ftot;
	}

	public double getFtbidrag() {
		return ftbidrag;
	}

	public void setFtbidrag(double ftbidrag) {
		this.ftbidrag = ftbidrag;
	}

	public double getTot() {
		return tot;
	}

	public void setTot(double tot) {
		this.tot = tot;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableLevstatPK != null ? tableLevstatPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableLevstat)) {
			return false;
		}
		TableLevstat other = (TableLevstat) object;
		if ((this.tableLevstatPK == null && other.tableLevstatPK != null) || (this.tableLevstatPK != null && !this.tableLevstatPK.equals(other.tableLevstatPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableLevstat[tableLevstatPK=" + tableLevstatPK + "]";
	}

}
