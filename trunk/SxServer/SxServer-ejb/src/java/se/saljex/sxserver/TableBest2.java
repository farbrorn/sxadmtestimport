/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

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
 * @author Ulf
 */
@Entity
@Table(name = "BEST2")
@NamedQueries({@NamedQuery(name = "TableBest2.findByBestnr", query = "SELECT b FROM TableBest2 b WHERE b.tableBest2PK.bestnr = :bestnr order by b.tableBest2PK.rad"),@NamedQuery(name = "TableBest2.deleteByBestnr", query = "DELETE FROM TableBest2 b WHERE b.tableBest2PK.bestnr = :bestnr")})
public class TableBest2 implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableBest2PK tableBest2PK;
	@Column(name = "ENH")
	private String enh;
	@Column(name = "ARTNR", nullable = false)
	private String artnr;
	@Column(name = "ARTNAMN")
	private String artnamn;
	@Column(name = "BARTNR")
	private String bartnr;
	@Column(name = "BEST", nullable = false)
	private double best;
	@Column(name = "PRIS", nullable = false)
	private double pris;
	@Column(name = "RAB", nullable = false)
	private double rab;
	@Column(name = "SUMMA", nullable = false)
	private double summa;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "BEKRDAT")
	@Temporal(TemporalType.DATE)
	private Date bekrdat;
	@Column(name = "INP_MILJO", nullable = false)
	private double inpMiljo;
	@Column(name = "INP_FRAKT", nullable = false)
	private double inpFrakt;
	@Column(name = "INP_FRAKTPROC", nullable = false)
	private double inpFraktproc;
	@Column(name = "STJID", nullable = false)
	private int stjid;

	public TableBest2() {
	}

	public TableBest2(TableBest2PK tableBest2PK) {
		this.tableBest2PK = tableBest2PK;
	}

	public TableBest2(TableBest2PK tableBest2PK, String artnr, double best, double pris, double rab, double summa, double inpMiljo, double inpFrakt, double inpFraktproc, int stjid) {
		this.tableBest2PK = tableBest2PK;
		this.artnr = artnr;
		this.best = best;
		this.pris = pris;
		this.rab = rab;
		this.summa = summa;
		this.inpMiljo = inpMiljo;
		this.inpFrakt = inpFrakt;
		this.inpFraktproc = inpFraktproc;
		this.stjid = stjid;
	}

	public TableBest2(int bestnr, short rad) {
		this.tableBest2PK = new TableBest2PK(bestnr, rad);
	}

	public TableBest2PK getTableBest2PK() {
		return tableBest2PK;
	}

	public void setTableBest2PK(TableBest2PK tableBest2PK) {
		this.tableBest2PK = tableBest2PK;
	}

	public String getEnh() {
		return enh;
	}

	public void setEnh(String enh) {
		this.enh = enh;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public String getArtnamn() {
		return artnamn;
	}

	public void setArtnamn(String artnamn) {
		this.artnamn = artnamn;
	}

	public String getBartnr() {
		return bartnr;
	}

	public void setBartnr(String bartnr) {
		this.bartnr = bartnr;
	}

	public double getBest() {
		return best;
	}

	public void setBest(double best) {
		this.best = best;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public double getRab() {
		return rab;
	}

	public void setRab(double rab) {
		this.rab = rab;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getBekrdat() {
		return bekrdat;
	}

	public void setBekrdat(Date bekrdat) {
		this.bekrdat = bekrdat;
	}

	public double getInpMiljo() {
		return inpMiljo;
	}

	public void setInpMiljo(double inpMiljo) {
		this.inpMiljo = inpMiljo;
	}

	public double getInpFrakt() {
		return inpFrakt;
	}

	public void setInpFrakt(double inpFrakt) {
		this.inpFrakt = inpFrakt;
	}

	public double getInpFraktproc() {
		return inpFraktproc;
	}

	public void setInpFraktproc(double inpFraktproc) {
		this.inpFraktproc = inpFraktproc;
	}

	public int getStjid() {
		return stjid;
	}

	public void setStjid(int stjid) {
		this.stjid = stjid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableBest2PK != null ? tableBest2PK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBest2)) {
			return false;
		}
		TableBest2 other = (TableBest2) object;
		if ((this.tableBest2PK == null && other.tableBest2PK != null) || (this.tableBest2PK != null && !this.tableBest2PK.equals(other.tableBest2PK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableBest2[tableBest2PK=" + tableBest2PK + "]";
	}

}
