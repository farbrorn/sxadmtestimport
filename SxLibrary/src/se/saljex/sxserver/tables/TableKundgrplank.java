/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "kundgrplank")
@NamedQueries({
	@NamedQuery(name = "TableKundgrplank.findAll", query = "SELECT t FROM TableKundgrplank t"),
	@NamedQuery(name = "TableKundgrplank.findByKundnr", query = "SELECT t FROM TableKundgrplank t WHERE t.tableKundgrplankPK.kundnr = :kundnr"),
	@NamedQuery(name = "TableKundgrplank.findByGrpnr", query = "SELECT t FROM TableKundgrplank t WHERE t.tableKundgrplankPK.grpnr = :grpnr")})
public class TableKundgrplank implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableKundgrplankPK tableKundgrplankPK;

	public TableKundgrplank() {
	}


	public TableKundgrplank(String kundnr, String grpnr) {
		this.tableKundgrplankPK = new TableKundgrplankPK(kundnr, grpnr);
	}

	public TableKundgrplankPK getTableKundgrplankPK() {
		return tableKundgrplankPK;
	}

	public void setTableKundgrplankPK(TableKundgrplankPK tableKundgrplankPK) {
		this.tableKundgrplankPK = tableKundgrplankPK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableKundgrplankPK != null ? tableKundgrplankPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKundgrplank)) {
			return false;
		}
		TableKundgrplank other = (TableKundgrplank) object;
		if ((this.tableKundgrplankPK == null && other.tableKundgrplankPK != null) || (this.tableKundgrplankPK != null && !this.tableKundgrplankPK.equals(other.tableKundgrplankPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableKundgrplank[ tableKundgrplankPK=" + tableKundgrplankPK + " ]";
	}
	
}
