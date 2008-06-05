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
@Table(name = "FAKTDAT")
@NamedQueries({@NamedQuery(name = "TableFaktdat.findAll", query = "SELECT t FROM TableFaktdat t"), @NamedQuery(name = "TableFaktdat.updateBestnrBy1", query = "UPDATE TableFaktdat t SET t.bestnr = t.bestnr+1 where t.bestnr = :bestnr and t.ft = :ft")})
public class TableFaktdat implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Column(name = "BESTNR", nullable = false)
	  private int bestnr;
	  @Column(name = "LEVLOPNR", nullable = false)
	  private int levlopnr;
	  @Column(name = "RETURNR", nullable = false)
	  private int returnr;
	  @Column(name = "ANMNR", nullable = false)
	  private int anmnr;
	  @Column(name = "LEVRETURNR", nullable = false)
	  private int levreturnr;
	  @Column(name = "DOKUMENTNR", nullable = false)
	  private int dokumentnr;
	  @Column(name = "OFFERTNR", nullable = false)
	  private int offertnr;
	  @Column(name = "DATUM", nullable = false)
	  @Temporal(TemporalType.DATE)
	  private Date datum;
	  @Column(name = "VERSION", nullable = false)
	  private int version;
	  @Id
	  @Column(name = "FT", nullable = false)
	  private Integer ft;

	  public TableFaktdat() {
	  }


	  public int getBestnr() {
			 return bestnr;
	  }

	  public void setBestnr(int bestnr) {
			 this.bestnr = bestnr;
	  }

	  public int getLevlopnr() {
			 return levlopnr;
	  }

	  public void setLevlopnr(int levlopnr) {
			 this.levlopnr = levlopnr;
	  }

	  public int getReturnr() {
			 return returnr;
	  }

	  public void setReturnr(int returnr) {
			 this.returnr = returnr;
	  }

	  public int getAnmnr() {
			 return anmnr;
	  }

	  public void setAnmnr(int anmnr) {
			 this.anmnr = anmnr;
	  }

	  public int getLevreturnr() {
			 return levreturnr;
	  }

	  public void setLevreturnr(int levreturnr) {
			 this.levreturnr = levreturnr;
	  }

	  public int getDokumentnr() {
			 return dokumentnr;
	  }

	  public void setDokumentnr(int dokumentnr) {
			 this.dokumentnr = dokumentnr;
	  }

	  public int getOffertnr() {
			 return offertnr;
	  }

	  public void setOffertnr(int offertnr) {
			 this.offertnr = offertnr;
	  }

	  public Date getDatum() {
			 return datum;
	  }

	  public void setDatum(Date datum) {
			 this.datum = datum;
	  }

	  public int getVersion() {
			 return version;
	  }

	  public void setVersion(int version) {
			 this.version = version;
	  }

	  public Integer getFt() {
			 return ft;
	  }

	  public void setFt(Integer ft) {
			 this.ft = ft;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (ft != null ? ft.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableFaktdat)) {
					return false;
			 }
			 TableFaktdat other = (TableFaktdat) object;
			 if ((this.ft == null && other.ft != null) || (this.ft != null && !this.ft.equals(other.ft))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableFaktdat[ft=" + ft + "]";
	  }

}
