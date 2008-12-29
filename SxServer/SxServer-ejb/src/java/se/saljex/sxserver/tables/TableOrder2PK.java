/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ulf
 */
@Embeddable
public class TableOrder2PK implements Serializable {
	@Column(name = "ORDERNR", nullable = false)
	private int ordernr;
	@Column(name = "POS", nullable = false)
	private short pos;

	public TableOrder2PK() {
	}

	public TableOrder2PK(int ordernr, short pos) {
		this.ordernr = ordernr;
		this.pos = pos;
	}

	public int getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(int ordernr) {
		this.ordernr = ordernr;
	}

	public short getPos() {
		return pos;
	}

	public void setPos(short pos) {
		this.pos = pos;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) ordernr;
		hash += (int) pos;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableOrder2PK)) {
			return false;
		}
		TableOrder2PK other = (TableOrder2PK) object;
		if (this.ordernr != other.ordernr) {
			return false;
		}
		if (this.pos != other.pos) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableOrder2PK[ordernr=" + ordernr + ", pos=" + pos + "]";
	}

}
