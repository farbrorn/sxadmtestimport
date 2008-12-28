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
public class TableNettopriPK implements Serializable {
	@Column(name = "LISTA", nullable = false)
	private String lista;
	@Column(name = "ARTNR", nullable = false)
	private String artnr;

	public TableNettopriPK() {
	}

	public TableNettopriPK(String lista, String artnr) {
		this.lista = lista;
		this.artnr = artnr;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
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
		hash += (lista != null ? lista.hashCode() : 0);
		hash += (artnr != null ? artnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableNettopriPK)) {
			return false;
		}
		TableNettopriPK other = (TableNettopriPK) object;
		if ((this.lista == null && other.lista != null) || (this.lista != null && !this.lista.equals(other.lista))) {
			return false;
		}
		if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableNettopriPK[lista=" + lista + ", artnr=" + artnr + "]";
	}

}
