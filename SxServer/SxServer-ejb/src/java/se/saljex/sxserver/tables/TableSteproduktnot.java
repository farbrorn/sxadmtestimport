/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "steproduktnot")
@NamedQueries({@NamedQuery(name = "TableSteproduktnot.findAll", query = "SELECT t FROM TableSteproduktnot t")})
public class TableSteproduktnot implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableSteproduktnotPK tableSteproduktnotPK;
	@Column(name = "crdt", insertable = false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdt;
	@Column(name = "anvandare")
	private String anvandare;
	@Column(name = "notering")
	private String notering;
	@Lob
	@Column(name = "bilaga")
	private byte[] bilaga;
	@Column(name = "filnamn")
	private String filnamn;
	@Column(name = "contenttype")
	private String conttenttype;

	public TableSteproduktnot() {
	}

	public TableSteproduktnot(TableSteproduktnotPK tableSteproduktnotPK) {
		this.tableSteproduktnotPK = tableSteproduktnotPK;
	}

	public TableSteproduktnot(String sn, int id) {
		this.tableSteproduktnotPK = new TableSteproduktnotPK(sn, id);
	}

	public TableSteproduktnotPK getTableSteproduktnotPK() {
		return tableSteproduktnotPK;
	}

	public void setTableSteproduktnotPK(TableSteproduktnotPK tableSteproduktnotPK) {
		this.tableSteproduktnotPK = tableSteproduktnotPK;
	}

	public Date getCrdt() {
		return crdt;
	}

	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}

	public String getAnvandare() {
		return anvandare;
	}

	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public String getNotering() {
		return notering;
	}

	public void setNotering(String notering) {
		this.notering = notering;
	}

	public byte[] getBilaga() {
		return bilaga;
	}

	public void setBilaga(byte[] bilaga) {
		this.bilaga = bilaga;
	}

	public String getFilnamn() {
		return filnamn;
	}

	public void setFilnamn(String filnamn) {
		this.filnamn = filnamn;
	}


	public void setConttenttype(String conttenttype) {
		this.conttenttype = conttenttype;
	}
	public String getConttenttype() {
		return conttenttype;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableSteproduktnotPK != null ? tableSteproduktnotPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSteproduktnot)) {
			return false;
		}
		TableSteproduktnot other = (TableSteproduktnot) object;
		if ((this.tableSteproduktnotPK == null && other.tableSteproduktnotPK != null) || (this.tableSteproduktnotPK != null && !this.tableSteproduktnotPK.equals(other.tableSteproduktnotPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableSteproduktnot[tableSteproduktnotPK=" + tableSteproduktnotPK + "]";
	}

}
