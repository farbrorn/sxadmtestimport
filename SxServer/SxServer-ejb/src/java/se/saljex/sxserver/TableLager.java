/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "LAGER")
@NamedQueries({})
public class TableLager implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableLagerPK tableLagerPK;
	@Column(name = "ILAGER", nullable = false)
	private double ilager;
	@Column(name = "BESTPUNKT", nullable = false)
	private double bestpunkt;
	@Column(name = "MAXLAGER", nullable = false)
	private double maxlager;
	@Column(name = "BEST", nullable = false)
	private double best;
	@Column(name = "IORDER", nullable = false)
	private double iorder;
	@Column(name = "LAGERPLATS")
	private String lagerplats;
	@Column(name = "HINDRAFILIALBEST")
	private Short hindrafilialbest;

	public TableLager() {
	}

	public TableLager(TableLagerPK tableLagerPK) {
		this.tableLagerPK = tableLagerPK;
	}

	public TableLager(TableLagerPK tableLagerPK, double ilager, double bestpunkt, double maxlager, double best, double iorder) {
		this.tableLagerPK = tableLagerPK;
		this.ilager = ilager;
		this.bestpunkt = bestpunkt;
		this.maxlager = maxlager;
		this.best = best;
		this.iorder = iorder;
	}

	public TableLager(String artnr, short lagernr) {
		this.tableLagerPK = new TableLagerPK(artnr, lagernr);
	}

	public TableLagerPK getTableLagerPK() {
		return tableLagerPK;
	}

	public void setTableLagerPK(TableLagerPK tableLagerPK) {
		this.tableLagerPK = tableLagerPK;
	}

	public double getIlager() {
		return ilager;
	}

	public void setIlager(double ilager) {
		this.ilager = ilager;
	}

	public double getBestpunkt() {
		return bestpunkt;
	}

	public void setBestpunkt(double bestpunkt) {
		this.bestpunkt = bestpunkt;
	}

	public double getMaxlager() {
		return maxlager;
	}

	public void setMaxlager(double maxlager) {
		this.maxlager = maxlager;
	}

	public double getBest() {
		return best;
	}

	public void setBest(double best) {
		this.best = best;
	}

	public double getIorder() {
		return iorder;
	}

	public void setIorder(double iorder) {
		this.iorder = iorder;
	}

	public String getLagerplats() {
		return lagerplats;
	}

	public void setLagerplats(String lagerplats) {
		this.lagerplats = lagerplats;
	}

	public Short getHindrafilialbest() {
		return hindrafilialbest;
	}

	public void setHindrafilialbest(Short hindrafilialbest) {
		this.hindrafilialbest = hindrafilialbest;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableLagerPK != null ? tableLagerPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableLager)) {
			return false;
		}
		TableLager other = (TableLager) object;
		if ((this.tableLagerPK == null && other.tableLagerPK != null) || (this.tableLagerPK != null && !this.tableLagerPK.equals(other.tableLagerPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableLager[tableLagerPK=" + tableLagerPK + "]";
	}

}
