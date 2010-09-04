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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "lagerhand")
@NamedQueries({
	@NamedQuery(name = "TableLagerhand.findAll", query = "SELECT t FROM TableLagerhand t")})
public class TableLagerhand implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableLagerhandPK tableLagerhandPK;
	@Column(name = "anvandare")
	private String anvandare;
	@Column(name = "handelse")
	private String handelse;
	@Column(name = "ordernr")
	private int ordernr;
	@Column(name = "stjid")
	private int stjid;
	@Column(name = "gammaltilager")
	private double gammaltilager;
	@Column(name = "nyttilager")
	private double nyttilager;
	@Column(name = "forandring")
	private double forandring;
	@Column(name = "bestnr")
	private int bestnr;

	public TableLagerhand() {
	}

	public TableLagerhand(TableLagerhandPK tableLagerhandPK) {
		this.tableLagerhandPK = tableLagerhandPK;
	}

	public TableLagerhand(String artnr, short lagernr, Date datum, Date tid) {
		this.tableLagerhandPK = new TableLagerhandPK(artnr, lagernr, datum, tid);
	}

	public TableLagerhandPK getTableLagerhandPK() {
		return tableLagerhandPK;
	}

	public void setTableLagerhandPK(TableLagerhandPK tableLagerhandPK) {
		this.tableLagerhandPK = tableLagerhandPK;
	}

	public String getAnvandare() {
		return anvandare;
	}

	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public String getHandelse() {
		return handelse;
	}

	public void setHandelse(String handelse) {
		this.handelse = handelse;
	}

	public int getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(int ordernr) {
		this.ordernr = ordernr;
	}

	public int getStjid() {
		return stjid;
	}

	public void setStjid(int stjid) {
		this.stjid = stjid;
	}

	public double getGammaltilager() {
		return gammaltilager;
	}

	public void setGammaltilager(double gammaltilager) {
		this.gammaltilager = gammaltilager;
	}

	public double getNyttilager() {
		return nyttilager;
	}

	public void setNyttilager(double nyttilager) {
		this.nyttilager = nyttilager;
	}

	public double getForandring() {
		return forandring;
	}

	public void setForandring(double forandring) {
		this.forandring = forandring;
	}

	public int getBestnr() {
		return bestnr;
	}

	public void setBestnr(int bestnr) {
		this.bestnr = bestnr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableLagerhandPK != null ? tableLagerhandPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableLagerhand)) {
			return false;
		}
		TableLagerhand other = (TableLagerhand) object;
		if ((this.tableLagerhandPK == null && other.tableLagerhandPK != null) || (this.tableLagerhandPK != null && !this.tableLagerhandPK.equals(other.tableLagerhandPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableLagerhand[tableLagerhandPK=" + tableLagerhandPK + "]";
	}

}
