/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ulf
 */
@Embeddable
public class TableFaktura2PK implements Serializable {
	@Column(name = "FAKTNR", nullable = false)
	private int faktnr;
	@Column(name = "POS", nullable = false)
	private short pos;

	public TableFaktura2PK() {
	}

	public TableFaktura2PK(int faktnr, short pos) {
		this.faktnr = faktnr;
		this.pos = pos;
	}

	public int getFaktnr() {
		return faktnr;
	}

	public void setFaktnr(int faktnr) {
		this.faktnr = faktnr;
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
		hash += (int) faktnr;
		hash += (int) pos;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableFaktura2PK)) {
			return false;
		}
		TableFaktura2PK other = (TableFaktura2PK) object;
		if (this.faktnr != other.faktnr) {
			return false;
		}
		if (this.pos != other.pos) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableFaktura2PK[faktnr=" + faktnr + ", pos=" + pos + "]";
	}

}
