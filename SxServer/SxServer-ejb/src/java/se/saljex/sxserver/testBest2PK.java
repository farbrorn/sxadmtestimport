/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ulf
 */
@Embeddable
public class testBest2PK implements Serializable {
	@Column(name = "BESTNR", nullable = false)
	private int bestnr;
	@Column(name = "RAD", nullable = false)
	private short rad;

	public testBest2PK() {
	}

	public testBest2PK(int bestnr, short rad) {
		this.bestnr = bestnr;
		this.rad = rad;
	}

	public int getBestnr() {
		return bestnr;
	}

	public void setBestnr(int bestnr) {
		this.bestnr = bestnr;
	}

	public short getRad() {
		return rad;
	}

	public void setRad(short rad) {
		this.rad = rad;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) bestnr;
		hash += (int) rad;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof testBest2PK)) {
			return false;
		}
		testBest2PK other = (testBest2PK) object;
		if (this.bestnr != other.bestnr) {
			return false;
		}
		if (this.rad != other.rad) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.testBest2PK[bestnr=" + bestnr + ", rad=" + rad + "]";
	}

}
