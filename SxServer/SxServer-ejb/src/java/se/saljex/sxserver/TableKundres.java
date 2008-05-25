/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "KUNDRES")
@NamedQueries({@NamedQuery(name = "TableKundres.findSumForKreditTest", query = "select sum(k.tot) from TableKundres k where k.kundnr=:kundnr and (k.falldat < :falldat or k.tot < 0)")})
public class TableKundres implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Column(name = "RANTFAKT", nullable = false)
	  private short rantfakt;
	  @Id
	  @Column(name = "FAKTNR", nullable = false)
	  private Integer faktnr;
	  @Column(name = "KUNDNR", nullable = false)
	  private String kundnr;
	  @Column(name = "NAMN", nullable = false)
	  private String namn;
	  @Column(name = "TOT", nullable = false)
	  private double tot;
	  @Column(name = "NETTO", nullable = false)
	  private double netto;
	  @Column(name = "DATUM")
	  @Temporal(TemporalType.DATE)
	  private Date datum;
	  @Column(name = "FALLDAT", nullable = false)
	  @Temporal(TemporalType.DATE)
	  private Date falldat;
	  @Column(name = "PDAT1")
	  @Temporal(TemporalType.DATE)
	  private Date pdat1;
	  @Column(name = "PDAT2")
	  @Temporal(TemporalType.DATE)
	  private Date pdat2;
	  @Column(name = "PDAT3")
	  @Temporal(TemporalType.DATE)
	  private Date pdat3;
	  @Column(name = "FAKTOR", nullable = false)
	  private short faktor;
	  @Column(name = "FAKTORDAT")
	  @Temporal(TemporalType.DATE)
	  private Date faktordat;
	  @Column(name = "BONUS", nullable = false)
	  private short bonus;
	  @Column(name = "MEDELMOMSPROC", nullable = false)
	  private double medelmomsproc;
	  @Column(name = "INKASSODATUM")
	  @Temporal(TemporalType.DATE)
	  private Date inkassodatum;
	  @Column(name = "ANTALPAMINNELSER")
	  private Integer antalpaminnelser;
	  @Column(name = "INKASSOSTATUS")
	  private String inkassostatus;
	  @Column(name = "PANTSATT")
	  private Short pantsatt;

	  public TableKundres() {
	  }

	  public TableKundres(Integer faktnr) {
			 this.faktnr = faktnr;
	  }


	  public short getRantfakt() {
			 return rantfakt;
	  }

	  public void setRantfakt(short rantfakt) {
			 this.rantfakt = rantfakt;
	  }

	  public Integer getFaktnr() {
			 return faktnr;
	  }

	  public void setFaktnr(Integer faktnr) {
			 this.faktnr = faktnr;
	  }

	  public String getKundnr() {
			 return kundnr;
	  }

	  public void setKundnr(String kundnr) {
			 this.kundnr = kundnr;
	  }

	  public String getNamn() {
			 return namn;
	  }

	  public void setNamn(String namn) {
			 this.namn = namn;
	  }

	  public double getTot() {
			 return tot;
	  }

	  public void setTot(double tot) {
			 this.tot = tot;
	  }

	  public double getNetto() {
			 return netto;
	  }

	  public void setNetto(double netto) {
			 this.netto = netto;
	  }

	  public Date getDatum() {
			 return datum;
	  }

	  public void setDatum(Date datum) {
			 this.datum = datum;
	  }

	  public Date getFalldat() {
			 return falldat;
	  }

	  public void setFalldat(Date falldat) {
			 this.falldat = falldat;
	  }

	  public Date getPdat1() {
			 return pdat1;
	  }

	  public void setPdat1(Date pdat1) {
			 this.pdat1 = pdat1;
	  }

	  public Date getPdat2() {
			 return pdat2;
	  }

	  public void setPdat2(Date pdat2) {
			 this.pdat2 = pdat2;
	  }

	  public Date getPdat3() {
			 return pdat3;
	  }

	  public void setPdat3(Date pdat3) {
			 this.pdat3 = pdat3;
	  }

	  public short getFaktor() {
			 return faktor;
	  }

	  public void setFaktor(short faktor) {
			 this.faktor = faktor;
	  }

	  public Date getFaktordat() {
			 return faktordat;
	  }

	  public void setFaktordat(Date faktordat) {
			 this.faktordat = faktordat;
	  }

	  public short getBonus() {
			 return bonus;
	  }

	  public void setBonus(short bonus) {
			 this.bonus = bonus;
	  }

	  public double getMedelmomsproc() {
			 return medelmomsproc;
	  }

	  public void setMedelmomsproc(double medelmomsproc) {
			 this.medelmomsproc = medelmomsproc;
	  }

	  public Date getInkassodatum() {
			 return inkassodatum;
	  }

	  public void setInkassodatum(Date inkassodatum) {
			 this.inkassodatum = inkassodatum;
	  }

	  public Integer getAntalpaminnelser() {
			 return antalpaminnelser;
	  }

	  public void setAntalpaminnelser(Integer antalpaminnelser) {
			 this.antalpaminnelser = antalpaminnelser;
	  }

	  public String getInkassostatus() {
			 return inkassostatus;
	  }

	  public void setInkassostatus(String inkassostatus) {
			 this.inkassostatus = inkassostatus;
	  }

	  public Short getPantsatt() {
			 return pantsatt;
	  }

	  public void setPantsatt(Short pantsatt) {
			 this.pantsatt = pantsatt;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (faktnr != null ? faktnr.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableKundres)) {
					return false;
			 }
			 TableKundres other = (TableKundres) object;
			 if ((this.faktnr == null && other.faktnr != null) || (this.faktnr != null && !this.faktnr.equals(other.faktnr))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableKundres[faktnr=" + faktnr + "]";
	  }

}
