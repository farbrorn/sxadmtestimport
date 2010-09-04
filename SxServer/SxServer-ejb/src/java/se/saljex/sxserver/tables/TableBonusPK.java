/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ulf
 */
@Embeddable
public class TableBonusPK implements Serializable {
	@Basic(optional = false)
   @Column(name = "faktura")
	private int faktura;
	@Basic(optional = false)
   @Column(name = "id")
	private short id;

	public TableBonusPK() {
	}

	public TableBonusPK(int faktura, short id) {
		this.faktura = faktura;
		this.id = id;
	}

	public int getFaktura() {
		return faktura;
	}

	public void setFaktura(int faktura) {
		this.faktura = faktura;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) faktura;
		hash += (int) id;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBonusPK)) {
			return false;
		}
		TableBonusPK other = (TableBonusPK) object;
		if (this.faktura != other.faktura) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableBonusPK[faktura=" + faktura + ", id=" + id + "]";
	}

}
