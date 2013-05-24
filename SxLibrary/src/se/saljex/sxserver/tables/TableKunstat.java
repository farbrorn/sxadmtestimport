/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "kunstat")
@NamedQueries({
	@NamedQuery(name = "TableKunstat.findAll", query = "SELECT t FROM TableKunstat t")})
public class TableKunstat implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableKunstatPK tableKunstatPK;
	@Basic(optional = false)
   @Column(name = "tot")
	private double tot;
	@Basic(optional = false)
   @Column(name = "tbidrag")
	private double tbidrag;
	@Basic(optional = false)
   @Column(name = "btid")
	private int btid;
	@Basic(optional = false)
   @Column(name = "fakturor")
	private int fakturor;
	@Basic(optional = false)
   @Column(name = "betal")
	private short betal;
	@Basic(optional = false)
   @Column(name = "totbet")
	private double totbet;
	@Basic(optional = false)
   @Column(name = "ranta")
	private double ranta;

	public TableKunstat() {
	}

	public TableKunstat(TableKunstatPK tableKunstatPK) {
		this.tableKunstatPK = tableKunstatPK;
	}

	public TableKunstat(String kundnr, short ar, short man) {
		this.tableKunstatPK = new TableKunstatPK(kundnr, ar, man);
	}

	public TableKunstatPK getTableKunstatPK() {
		return tableKunstatPK;
	}

	public void setTableKunstatPK(TableKunstatPK tableKunstatPK) {
		this.tableKunstatPK = tableKunstatPK;
	}

	public double getTot() {
		return tot;
	}

	public void setTot(double tot) {
		this.tot = tot;
	}

	public double getTbidrag() {
		return tbidrag;
	}

	public void setTbidrag(double tbidrag) {
		this.tbidrag = tbidrag;
	}

	public int getBtid() {
		return btid;
	}

	public void setBtid(int btid) {
		this.btid = btid;
	}

	public int getFakturor() {
		return fakturor;
	}

	public void setFakturor(int fakturor) {
		this.fakturor = fakturor;
	}

	public short getBetal() {
		return betal;
	}

	public void setBetal(short betal) {
		this.betal = betal;
	}

	public double getTotbet() {
		return totbet;
	}

	public void setTotbet(double totbet) {
		this.totbet = totbet;
	}

	public double getRanta() {
		return ranta;
	}

	public void setRanta(double ranta) {
		this.ranta = ranta;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableKunstatPK != null ? tableKunstatPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKunstat)) {
			return false;
		}
		TableKunstat other = (TableKunstat) object;
		if ((this.tableKunstatPK == null && other.tableKunstatPK != null) || (this.tableKunstatPK != null && !this.tableKunstatPK.equals(other.tableKunstatPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableKunstat[tableKunstatPK=" + tableKunstatPK + "]";
	}

}
