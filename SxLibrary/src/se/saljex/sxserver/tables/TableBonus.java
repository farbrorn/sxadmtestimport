/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "bonus")
@NamedQueries({
	@NamedQuery(name = "TableBonus.findAll", query = "SELECT t FROM TableBonus t")
	, @NamedQuery(name = "TableBonus.findByKund", query = "SELECT t FROM TableBonus t where t.kund=:kund order by t.tableBonusPK.faktura")
})
public class TableBonus implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableBonusPK tableBonusPK;
	@Basic(optional = false)
   @Column(name = "kund")
	private String kund;
	@Column(name = "datum")
   @Temporal(TemporalType.DATE)
	private Date datum;
	@Basic(optional = false)
   @Column(name = "bonus")
	private double bonus;

	public TableBonus() {
	}

	public TableBonus(TableBonusPK tableBonusPK) {
		this.tableBonusPK = tableBonusPK;
	}


	public TableBonus(int faktura, short id) {
		this.tableBonusPK = new TableBonusPK(faktura, id);
	}

	public TableBonusPK getTableBonusPK() {
		return tableBonusPK;
	}

	public void setTableBonusPK(TableBonusPK tableBonusPK) {
		this.tableBonusPK = tableBonusPK;
	}

	public String getKund() {
		return kund;
	}

	public void setKund(String kund) {
		this.kund = kund;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableBonusPK != null ? tableBonusPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBonus)) {
			return false;
		}
		TableBonus other = (TableBonus) object;
		if ((this.tableBonusPK == null && other.tableBonusPK != null) || (this.tableBonusPK != null && !this.tableBonusPK.equals(other.tableBonusPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableBonus[tableBonusPK=" + tableBonusPK + "]";
	}

}
