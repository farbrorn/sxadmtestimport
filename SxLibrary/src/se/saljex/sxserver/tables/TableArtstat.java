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
@Table(name = "artstat")
@NamedQueries({
	@NamedQuery(name = "TableArtstat.findAll", query = "SELECT t FROM TableArtstat t")})
public class TableArtstat implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableArtstatPK tableArtstatPK;
	@Basic(optional = false)
   @Column(name = "salda")
	private double salda;
	@Basic(optional = false)
   @Column(name = "tbidrag")
	private double tbidrag;

	public TableArtstat() {
	}

	public TableArtstat(TableArtstatPK tableArtstatPK) {
		this.tableArtstatPK = tableArtstatPK;
	}

	public TableArtstat(TableArtstatPK tableArtstatPK, double salda, double tbidrag) {
		this.tableArtstatPK = tableArtstatPK;
		this.salda = salda;
		this.tbidrag = tbidrag;
	}

	public TableArtstat(String artnr, short ar, short man) {
		this.tableArtstatPK = new TableArtstatPK(artnr, ar, man);
	}

	public TableArtstatPK getTableArtstatPK() {
		return tableArtstatPK;
	}

	public void setTableArtstatPK(TableArtstatPK tableArtstatPK) {
		this.tableArtstatPK = tableArtstatPK;
	}

	public double getSalda() {
		return salda;
	}

	public void setSalda(double salda) {
		this.salda = salda;
	}

	public double getTbidrag() {
		return tbidrag;
	}

	public void setTbidrag(double tbidrag) {
		this.tbidrag = tbidrag;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableArtstatPK != null ? tableArtstatPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableArtstat)) {
			return false;
		}
		TableArtstat other = (TableArtstat) object;
		if ((this.tableArtstatPK == null && other.tableArtstatPK != null) || (this.tableArtstatPK != null && !this.tableArtstatPK.equals(other.tableArtstatPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableArtstat[tableArtstatPK=" + tableArtstatPK + "]";
	}

}
