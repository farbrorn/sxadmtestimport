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
public class TableRabkoderPK implements Serializable {
	@Column(name = "RABKOD", nullable = false)
	private String rabkod;
	@Column(name = "KOD1", nullable = false)
	private String kod1;

	public TableRabkoderPK() {
	}

	public TableRabkoderPK(String rabkod, String kod1) {
		this.rabkod = rabkod;
		this.kod1 = kod1;
	}

	public String getRabkod() {
		return rabkod;
	}

	public void setRabkod(String rabkod) {
		this.rabkod = rabkod;
	}

	public String getKod1() {
		return kod1;
	}

	public void setKod1(String kod1) {
		this.kod1 = kod1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (rabkod != null ? rabkod.hashCode() : 0);
		hash += (kod1 != null ? kod1.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableRabkoderPK)) {
			return false;
		}
		TableRabkoderPK other = (TableRabkoderPK) object;
		if ((this.rabkod == null && other.rabkod != null) || (this.rabkod != null && !this.rabkod.equals(other.rabkod))) {
			return false;
		}
		if ((this.kod1 == null && other.kod1 != null) || (this.kod1 != null && !this.kod1.equals(other.kod1))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableRabkoderPK[rabkod=" + rabkod + ", kod1=" + kod1 + "]";
	}

}
