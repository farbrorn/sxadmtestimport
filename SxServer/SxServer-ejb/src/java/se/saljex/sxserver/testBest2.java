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
@NamedQueries({@NamedQuery(name = "testBest2.findByBestnr", query = "SELECT t FROM testBest2 t WHERE t.testBest2PK.bestnr = :bestnr"), @NamedQuery(name = "testBest2.findByRad", query = "SELECT t FROM testBest2 t WHERE t.testBest2PK.rad = :rad"), @NamedQuery(name = "testBest2.findByEnh", query = "SELECT t FROM testBest2 t WHERE t.enh = :enh"), @NamedQuery(name = "testBest2.findByArtnr", query = "SELECT t FROM testBest2 t WHERE t.artnr = :artnr"), @NamedQuery(name = "testBest2.findByArtnamn", query = "SELECT t FROM testBest2 t WHERE t.artnamn = :artnamn"), @NamedQuery(name = "testBest2.findByBartnr", query = "SELECT t FROM testBest2 t WHERE t.bartnr = :bartnr"), @NamedQuery(name = "testBest2.findByBest", query = "SELECT t FROM testBest2 t WHERE t.best = :best"), @NamedQuery(name = "testBest2.findByPris", query = "SELECT t FROM testBest2 t WHERE t.pris = :pris"), @NamedQuery(name = "testBest2.findByRab", query = "SELECT t FROM testBest2 t WHERE t.rab = :rab"), @NamedQuery(name = "testBest2.findBySumma", query = "SELECT t FROM testBest2 t WHERE t.summa = :summa"), @NamedQuery(name = "testBest2.findByText", query = "SELECT t FROM testBest2 t WHERE t.text = :text"), @NamedQuery(name = "testBest2.findByBekrdat", query = "SELECT t FROM testBest2 t WHERE t.bekrdat = :bekrdat"), @NamedQuery(name = "testBest2.findByInpMiljo", query = "SELECT t FROM testBest2 t WHERE t.inpMiljo = :inpMiljo"), @NamedQuery(name = "testBest2.findByInpFrakt", query = "SELECT t FROM testBest2 t WHERE t.inpFrakt = :inpFrakt"), @NamedQuery(name = "testBest2.findByInpFraktproc", query = "SELECT t FROM testBest2 t WHERE t.inpFraktproc = :inpFraktproc"), @NamedQuery(name = "testBest2.findByStjid", query = "SELECT t FROM testBest2 t WHERE t.stjid = :stjid")})
public class testBest2 implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected testBest2PK testBest2PK;
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

	public testBest2() {
	}

	public testBest2(testBest2PK testBest2PK) {
		this.testBest2PK = testBest2PK;
	}

	public testBest2(testBest2PK testBest2PK, String artnr, double best, double pris, double rab, double summa, double inpMiljo, double inpFrakt, double inpFraktproc, int stjid) {
		this.testBest2PK = testBest2PK;
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

	public testBest2(int bestnr, short rad) {
		this.testBest2PK = new testBest2PK(bestnr, rad);
	}

	public testBest2PK getTestBest2PK() {
		return testBest2PK;
	}

	public void setTestBest2PK(testBest2PK testBest2PK) {
		this.testBest2PK = testBest2PK;
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
		hash += (testBest2PK != null ? testBest2PK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof testBest2)) {
			return false;
		}
		testBest2 other = (testBest2) object;
		if ((this.testBest2PK == null && other.testBest2PK != null) || (this.testBest2PK != null && !this.testBest2PK.equals(other.testBest2PK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.testBest2[testBest2PK=" + testBest2PK + "]";
	}

}
