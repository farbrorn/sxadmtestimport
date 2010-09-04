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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "bonusbet")
@NamedQueries({
	@NamedQuery(name = "TableBonusbet.findAll", query = "SELECT t FROM TableBonusbet t")})
public class TableBonusbet implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableBonusbetPK tableBonusbetPK;
	@Column(name = "utdatum")
   @Temporal(TemporalType.DATE)
	private Date utdatum;
	@Column(name = "bonus")
	private double bonus;
	@Column(name = "utfaktura")
	private Integer utfaktura;

	public TableBonusbet() {
	}

	public TableBonusbet(TableBonusbetPK tableBonusbetPK) {
		this.tableBonusbetPK = tableBonusbetPK;
	}

	public TableBonusbet(String kund, int faktura, short id) {
		this.tableBonusbetPK = new TableBonusbetPK(kund, faktura, id);
	}

	public TableBonusbetPK getTableBonusbetPK() {
		return tableBonusbetPK;
	}

	public void setTableBonusbetPK(TableBonusbetPK tableBonusbetPK) {
		this.tableBonusbetPK = tableBonusbetPK;
	}

	public Date getUtdatum() {
		return utdatum;
	}

	public void setUtdatum(Date utdatum) {
		this.utdatum = utdatum;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public Integer getUtfaktura() {
		return utfaktura;
	}

	public void setUtfaktura(Integer utfaktura) {
		this.utfaktura = utfaktura;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableBonusbetPK != null ? tableBonusbetPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBonusbet)) {
			return false;
		}
		TableBonusbet other = (TableBonusbet) object;
		if ((this.tableBonusbetPK == null && other.tableBonusbetPK != null) || (this.tableBonusbetPK != null && !this.tableBonusbetPK.equals(other.tableBonusbetPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableBonusbet[tableBonusbetPK=" + tableBonusbetPK + "]";
	}

}
