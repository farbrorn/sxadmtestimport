/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

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
@Table(name = "OFFERT1")
@NamedQueries({@NamedQuery(name = "TableOffert1.findByOffertnr", query = "SELECT t FROM TableOffert1 t WHERE t.offertnr = :offertnr")})
public class TableOffert1 implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @Column(name = "OFFERTNR", nullable = false)
	  private Integer offertnr;
	  @Column(name = "NAMN")
	  private String namn;
	  @Column(name = "ADR1")
	  private String adr1;
	  @Column(name = "ADR2")
	  private String adr2;
	  @Column(name = "ADR3")
	  private String adr3;
	  @Column(name = "LEVADR1")
	  private String levadr1;
	  @Column(name = "LEVADR2")
	  private String levadr2;
	  @Column(name = "LEVADR3")
	  private String levadr3;
	  @Column(name = "SALJARE")
	  private String saljare;
	  @Column(name = "REFERENS")
	  private String referens;
	  @Column(name = "KUNDNR", nullable = false)
	  private String kundnr;
	  @Column(name = "MARKE")
	  private String marke;
	  @Column(name = "DATUM")
	  @Temporal(TemporalType.DATE)
	  private Date datum;
	  @Column(name = "MOMS", nullable = false)
	  private short moms;
	  @Column(name = "STATUS")
	  private String status;
	  @Column(name = "KTID", nullable = false)
	  private short ktid;
	  @Column(name = "BONUS", nullable = false)
	  private short bonus;
	  @Column(name = "FAKTOR", nullable = false)
	  private short faktor;
	  @Column(name = "LEVDAT")
	  @Temporal(TemporalType.DATE)
	  private Date levdat;
	  @Column(name = "LEVVILLKOR")
	  private String levvillkor;
	  @Column(name = "MOTTAGARFRAKT", nullable = false)
	  private short mottagarfrakt;
	  @Column(name = "FRAKTKUNDNR")
	  private String fraktkundnr;
	  @Column(name = "FRAKTBOLAG")
	  private String fraktbolag;
	  @Column(name = "FRAKTFRIGRANS", nullable = false)
	  private double fraktfrigrans;
	  @Column(name = "SKRIVEJPRIS", nullable = false)
	  private short skrivejpris;
	  @Column(name = "LAGERNR", nullable = false)
	  private short lagernr;
	  @Column(name = "ANNANLEVADRESS")
	  private Short annanlevadress;
	  @Column(name = "ORDERMEDDELANDE")
	  private String ordermeddelande;

	  public TableOffert1() {
	  }

	  public TableOffert1(Integer offertnr) {
			 this.offertnr = offertnr;
	  }


	  public Integer getOffertnr() {
			 return offertnr;
	  }

	  public void setOffertnr(Integer offertnr) {
			 this.offertnr = offertnr;
	  }

	  public String getNamn() {
			 return namn;
	  }

	  public void setNamn(String namn) {
			 this.namn = namn;
	  }

	  public String getAdr1() {
			 return adr1;
	  }

	  public void setAdr1(String adr1) {
			 this.adr1 = adr1;
	  }

	  public String getAdr2() {
			 return adr2;
	  }

	  public void setAdr2(String adr2) {
			 this.adr2 = adr2;
	  }

	  public String getAdr3() {
			 return adr3;
	  }

	  public void setAdr3(String adr3) {
			 this.adr3 = adr3;
	  }

	  public String getLevadr1() {
			 return levadr1;
	  }

	  public void setLevadr1(String levadr1) {
			 this.levadr1 = levadr1;
	  }

	  public String getLevadr2() {
			 return levadr2;
	  }

	  public void setLevadr2(String levadr2) {
			 this.levadr2 = levadr2;
	  }

	  public String getLevadr3() {
			 return levadr3;
	  }

	  public void setLevadr3(String levadr3) {
			 this.levadr3 = levadr3;
	  }

	  public String getSaljare() {
			 return saljare;
	  }

	  public void setSaljare(String saljare) {
			 this.saljare = saljare;
	  }

	  public String getReferens() {
			 return referens;
	  }

	  public void setReferens(String referens) {
			 this.referens = referens;
	  }

	  public String getKundnr() {
			 return kundnr;
	  }

	  public void setKundnr(String kundnr) {
			 this.kundnr = kundnr;
	  }

	  public String getMarke() {
			 return marke;
	  }

	  public void setMarke(String marke) {
			 this.marke = marke;
	  }

	  public Date getDatum() {
			 return datum;
	  }

	  public void setDatum(Date datum) {
			 this.datum = datum;
	  }

	  public short getMoms() {
			 return moms;
	  }

	  public void setMoms(short moms) {
			 this.moms = moms;
	  }

	  public String getStatus() {
			 return status;
	  }

	  public void setStatus(String status) {
			 this.status = status;
	  }

	  public short getKtid() {
			 return ktid;
	  }

	  public void setKtid(short ktid) {
			 this.ktid = ktid;
	  }

	  public short getBonus() {
			 return bonus;
	  }

	  public void setBonus(short bonus) {
			 this.bonus = bonus;
	  }

	  public short getFaktor() {
			 return faktor;
	  }

	  public void setFaktor(short faktor) {
			 this.faktor = faktor;
	  }

	  public Date getLevdat() {
			 return levdat;
	  }

	  public void setLevdat(Date levdat) {
			 this.levdat = levdat;
	  }

	  public String getLevvillkor() {
			 return levvillkor;
	  }

	  public void setLevvillkor(String levvillkor) {
			 this.levvillkor = levvillkor;
	  }

	  public short getMottagarfrakt() {
			 return mottagarfrakt;
	  }

	  public void setMottagarfrakt(short mottagarfrakt) {
			 this.mottagarfrakt = mottagarfrakt;
	  }

	  public String getFraktkundnr() {
			 return fraktkundnr;
	  }

	  public void setFraktkundnr(String fraktkundnr) {
			 this.fraktkundnr = fraktkundnr;
	  }

	  public String getFraktbolag() {
			 return fraktbolag;
	  }

	  public void setFraktbolag(String fraktbolag) {
			 this.fraktbolag = fraktbolag;
	  }

	  public double getFraktfrigrans() {
			 return fraktfrigrans;
	  }

	  public void setFraktfrigrans(double fraktfrigrans) {
			 this.fraktfrigrans = fraktfrigrans;
	  }

	  public short getSkrivejpris() {
			 return skrivejpris;
	  }

	  public void setSkrivejpris(short skrivejpris) {
			 this.skrivejpris = skrivejpris;
	  }

	  public short getLagernr() {
			 return lagernr;
	  }

	  public void setLagernr(short lagernr) {
			 this.lagernr = lagernr;
	  }

	  public Short getAnnanlevadress() {
			 return annanlevadress;
	  }

	  public void setAnnanlevadress(Short annanlevadress) {
			 this.annanlevadress = annanlevadress;
	  }

	  public String getOrdermeddelande() {
			 return ordermeddelande;
	  }

	  public void setOrdermeddelande(String ordermeddelande) {
			 this.ordermeddelande = ordermeddelande;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (offertnr != null ? offertnr.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableOffert1)) {
					return false;
			 }
			 TableOffert1 other = (TableOffert1) object;
			 if ((this.offertnr == null && other.offertnr != null) || (this.offertnr != null && !this.offertnr.equals(other.offertnr))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableOffert1[offertnr=" + offertnr + "]";
	  }

}
