/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
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
@Table(name = "varukorg")
@NamedQueries({@NamedQuery(name = "TableVarukorg.findByKontaktidVK", query = "SELECT t FROM TableVarukorg t WHERE t.tableVarukorgPK.kontaktid = :kontaktid and t.tableVarukorgPK.typ='VK'"),
@NamedQuery(name = "TableVarukorg.deleteByKontaktidVK", query = "DELETE FROM TableVarukorg t WHERE t.tableVarukorgPK.kontaktid = :kontaktid and t.tableVarukorgPK.typ='VK'")})
public class TableVarukorg implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableVarukorgPK tableVarukorgPK;
	@Column(name = "antal")
	private Double antal;

	public TableVarukorg() {
	}


	public TableVarukorgPK getTableVarukorgPK() {
		return tableVarukorgPK;
	}

	public void setTableVarukorgPK(TableVarukorgPK tableVarukorgPK) {
		this.tableVarukorgPK = tableVarukorgPK;
	}

	public Double getAntal() {
		return antal;
	}

	public void setAntal(Double antal) {
		this.antal = antal;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableVarukorgPK != null ? tableVarukorgPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableVarukorg)) {
			return false;
		}
		TableVarukorg other = (TableVarukorg) object;
		if ((this.tableVarukorgPK == null && other.tableVarukorgPK != null) || (this.tableVarukorgPK != null && !this.tableVarukorgPK.equals(other.tableVarukorgPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableVarukorg[tableVarukorgPK=" + tableVarukorgPK + "]";
	}

}
