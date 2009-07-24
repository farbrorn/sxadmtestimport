/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "stepumpartnr")
@NamedQueries({@NamedQuery(name = "TableStepumpartnr.findAll", query = "SELECT t FROM TableStepumpartnr t"), @NamedQuery(name = "TableStepumpartnr.findByArtnr", query = "SELECT t FROM TableStepumpartnr t WHERE t.artnr = :artnr")})
public class TableStepumpartnr implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "artnr")
	private String artnr;

	public TableStepumpartnr() {
	}

	public TableStepumpartnr(String artnr) {
		this.artnr = artnr;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (artnr != null ? artnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableStepumpartnr)) {
			return false;
		}
		TableStepumpartnr other = (TableStepumpartnr) object;
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableStepumpartnr[artnr=" + artnr + "]";
	}

}
