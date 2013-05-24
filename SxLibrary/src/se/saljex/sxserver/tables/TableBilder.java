/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "BILDER")
@NamedQueries({@NamedQuery(name = "TableBilder.findByNamn", query = "SELECT t FROM TableBilder t WHERE t.namn = :namn"), @NamedQuery(name = "TableBilder.findByTyp", query = "SELECT t FROM TableBilder t WHERE t.typ = :typ")})
public class TableBilder implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "NAMN", nullable = false)
	private String namn;
	@Column(name = "TYP")
	private String typ;
	@Lob
	@Column(name = "BILDDATA")
	private byte[] bilddata;

	public TableBilder() {
	}

	public TableBilder(String namn) {
		this.namn = namn;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public byte[] getBilddata() {
		return bilddata;
	}

	public void setBilddata(byte[] bilddata) {
		this.bilddata = bilddata;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (namn != null ? namn.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBilder)) {
			return false;
		}
		TableBilder other = (TableBilder) object;
		if ((this.namn == null && other.namn != null) || (this.namn != null && !this.namn.equals(other.namn))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableBilder[namn=" + namn + "]";
	}

}
