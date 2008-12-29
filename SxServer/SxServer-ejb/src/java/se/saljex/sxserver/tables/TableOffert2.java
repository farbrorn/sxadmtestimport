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
@Table(name = "OFFERT2")
@NamedQueries({@NamedQuery(name = "TableOffert2.findByOffertnr", query = "SELECT t FROM TableOffert2 t WHERE t.tableOffert2PK.offertnr = :offertnr order by t.tableOffert2PK.pos")})
public class TableOffert2 implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @EmbeddedId
	  protected TableOffert2PK tableOffert2PK;
	  @Column(name = "PRISNR", nullable = false)
	  private short prisnr;
	  @Column(name = "ARTNR", nullable = false)
	  private String artnr;
	  @Column(name = "NAMN")
	  private String namn;
	  @Column(name = "LEVNR")
	  private String levnr;
	  @Column(name = "BEST", nullable = false)
	  private double best;
	  @Column(name = "RAB", nullable = false)
	  private double rab;
	  @Column(name = "LEV", nullable = false)
	  private double lev;
	  @Column(name = "TEXT")
	  private String text;
	  @Column(name = "PRIS", nullable = false)
	  private double pris;
	  @Column(name = "SUMMA", nullable = false)
	  private double summa;
	  @Column(name = "KONTO")
	  private String konto;
	  @Column(name = "NETTO", nullable = false)
	  private double netto;
	  @Column(name = "ENH")
	  private String enh;
	  @Column(name = "LEVDAT")
	  @Temporal(TemporalType.DATE)
	  private Date levdat;

	  public TableOffert2() {
	  }

	  public TableOffert2(int offertnr, short pos) {
			 this.tableOffert2PK = new TableOffert2PK(offertnr, pos);
	  }

	  public TableOffert2PK getTableOffert2PK() {
			 return tableOffert2PK;
	  }

	  public void setTableOffert2PK(TableOffert2PK tableOffert2PK) {
			 this.tableOffert2PK = tableOffert2PK;
	  }

	  public short getPrisnr() {
			 return prisnr;
	  }

	  public void setPrisnr(short prisnr) {
			 this.prisnr = prisnr;
	  }

	  public String getArtnr() {
			 return artnr;
	  }

	  public void setArtnr(String artnr) {
			 this.artnr = artnr;
	  }

	  public String getNamn() {
			 return namn;
	  }

	  public void setNamn(String namn) {
			 this.namn = namn;
	  }

	  public String getLevnr() {
			 return levnr;
	  }

	  public void setLevnr(String levnr) {
			 this.levnr = levnr;
	  }

	  public double getBest() {
			 return best;
	  }

	  public void setBest(double best) {
			 this.best = best;
	  }

	  public double getRab() {
			 return rab;
	  }

	  public void setRab(double rab) {
			 this.rab = rab;
	  }

	  public double getLev() {
			 return lev;
	  }

	  public void setLev(double lev) {
			 this.lev = lev;
	  }

	  public String getText() {
			 return text;
	  }

	  public void setText(String text) {
			 this.text = text;
	  }

	  public double getPris() {
			 return pris;
	  }

	  public void setPris(double pris) {
			 this.pris = pris;
	  }

	  public double getSumma() {
			 return summa;
	  }

	  public void setSumma(double summa) {
			 this.summa = summa;
	  }

	  public String getKonto() {
			 return konto;
	  }

	  public void setKonto(String konto) {
			 this.konto = konto;
	  }

	  public double getNetto() {
			 return netto;
	  }

	  public void setNetto(double netto) {
			 this.netto = netto;
	  }

	  public String getEnh() {
			 return enh;
	  }

	  public void setEnh(String enh) {
			 this.enh = enh;
	  }

	  public Date getLevdat() {
			 return levdat;
	  }

	  public void setLevdat(Date levdat) {
			 this.levdat = levdat;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (tableOffert2PK != null ? tableOffert2PK.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableOffert2)) {
					return false;
			 }
			 TableOffert2 other = (TableOffert2) object;
			 if ((this.tableOffert2PK == null && other.tableOffert2PK != null) || (this.tableOffert2PK != null && !this.tableOffert2PK.equals(other.tableOffert2PK))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableOffert2[tableOffert2PK=" + tableOffert2PK + "]";
	  }

}
