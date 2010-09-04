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
@Table(name = "sljstat")
@NamedQueries({
	@NamedQuery(name = "TableSljstat.findAll", query = "SELECT t FROM TableSljstat t")})
public class TableSljstat implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableSljstatPK tableSljstatPK;
	@Basic(optional = false)
   @Column(name = "totalt")
	private double totalt;
	@Basic(optional = false)
   @Column(name = "tbidrag")
	private double tbidrag;

	public TableSljstat() {
	}

	public TableSljstat(TableSljstatPK tableSljstatPK) {
		this.tableSljstatPK = tableSljstatPK;
	}

	public TableSljstat(TableSljstatPK tableSljstatPK, double totalt, double tbidrag) {
		this.tableSljstatPK = tableSljstatPK;
		this.totalt = totalt;
		this.tbidrag = tbidrag;
	}

	public TableSljstat(String saljare, short ar, short man) {
		this.tableSljstatPK = new TableSljstatPK(saljare, ar, man);
	}

	public TableSljstatPK getTableSljstatPK() {
		return tableSljstatPK;
	}

	public void setTableSljstatPK(TableSljstatPK tableSljstatPK) {
		this.tableSljstatPK = tableSljstatPK;
	}

	public double getTotalt() {
		return totalt;
	}

	public void setTotalt(double totalt) {
		this.totalt = totalt;
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
		hash += (tableSljstatPK != null ? tableSljstatPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSljstat)) {
			return false;
		}
		TableSljstat other = (TableSljstat) object;
		if ((this.tableSljstatPK == null && other.tableSljstatPK != null) || (this.tableSljstatPK != null && !this.tableSljstatPK.equals(other.tableSljstatPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableSljstat[tableSljstatPK=" + tableSljstatPK + "]";
	}

}
