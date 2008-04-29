/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "RABKODER")
@NamedQueries({})
public class TableRabkoder implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableRabkoderPK tableRabkoderPK;
	@Column(name = "BESKRIVNING")
	private String beskrivning;

	public TableRabkoder() {
	}

	public TableRabkoder(TableRabkoderPK tableRabkoderPK) {
		this.tableRabkoderPK = tableRabkoderPK;
	}

	public TableRabkoder(String rabkod, String kod1) {
		this.tableRabkoderPK = new TableRabkoderPK(rabkod, kod1);
	}

	public TableRabkoderPK getTableRabkoderPK() {
		return tableRabkoderPK;
	}

	public void setTableRabkoderPK(TableRabkoderPK tableRabkoderPK) {
		this.tableRabkoderPK = tableRabkoderPK;
	}

	public String getBeskrivning() {
		return beskrivning;
	}

	public void setBeskrivning(String beskrivning) {
		this.beskrivning = beskrivning;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableRabkoderPK != null ? tableRabkoderPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableRabkoder)) {
			return false;
		}
		TableRabkoder other = (TableRabkoder) object;
		if ((this.tableRabkoderPK == null && other.tableRabkoderPK != null) || (this.tableRabkoderPK != null && !this.tableRabkoderPK.equals(other.tableRabkoderPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableRabkoder[tableRabkoderPK=" + tableRabkoderPK + "]";
	}

}
