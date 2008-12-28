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
@Table(name = "STJARNRAD")
@NamedQueries({@NamedQuery(name = "TableStjarnrad.getMaxId", query = "SELECT max(s.stjid) FROM TableStjarnrad s")})
public class TableStjarnrad implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @Column(name = "STJID", nullable = false)
	  private Integer stjid;
	  @Column(name = "ARTNR")
	  private String artnr;
	  @Column(name = "LEVNR")
	  private String levnr;
	  @Column(name = "KUNDNR")
	  private String kundnr;
	  @Column(name = "NAMN")
	  private String namn;
	  @Column(name = "LAGERNR", nullable = false)
	  private short lagernr;
	  @Column(name = "ANTAL", nullable = false)
	  private double antal;
	  @Column(name = "ENH")
	  private String enh;
	  @Column(name = "INPRIS", nullable = false)
	  private double inpris;
	  @Column(name = "INRAB", nullable = false)
	  private double inrab;
	  @Column(name = "REGDATUM")
	  @Temporal(TemporalType.DATE)
	  private Date regdatum;
	  @Column(name = "AUTOBESTALL", nullable = false)
	  private short autobestall;
	  @Column(name = "BESTDAT")
	  @Temporal(TemporalType.DATE)
	  private Date bestdat;
	  @Column(name = "BESTNR")
	  private Integer bestnr;
	  @Column(name = "ANVANDARE")
	  private String anvandare;
	  @Column(name = "FINNSILAGER", nullable = false)
	  private short finnsilager;
	  @Column(name = "INKOMDATUM")
	  @Temporal(TemporalType.DATE)
	  private Date inkomdatum;
	  @Column(name = "FAKTURANR")
	  private Integer fakturanr;

	  public TableStjarnrad() {
		  
	  }

	  public TableStjarnrad(Integer stjid) {
			 this.stjid = stjid;
	  }


	  public Integer getStjid() {
			 return stjid;
	  }

	  public void setStjid(Integer stjid) {
			 this.stjid = stjid;
	  }

	  public String getArtnr() {
			 return artnr;
	  }

	  public void setArtnr(String artnr) {
			 this.artnr = artnr;
	  }

	  public String getLevnr() {
			 return levnr;
	  }

	  public void setLevnr(String levnr) {
			 this.levnr = levnr;
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

	  public short getLagernr() {
			 return lagernr;
	  }

	  public void setLagernr(short lagernr) {
			 this.lagernr = lagernr;
	  }

	  public double getAntal() {
			 return antal;
	  }

	  public void setAntal(double antal) {
			 this.antal = antal;
	  }

	  public String getEnh() {
			 return enh;
	  }

	  public void setEnh(String enh) {
			 this.enh = enh;
	  }

	  public double getInpris() {
			 return inpris;
	  }

	  public void setInpris(double inpris) {
			 this.inpris = inpris;
	  }

	  public double getInrab() {
			 return inrab;
	  }

	  public void setInrab(double inrab) {
			 this.inrab = inrab;
	  }

	  public Date getRegdatum() {
			 return regdatum;
	  }

	  public void setRegdatum(Date regdatum) {
			 this.regdatum = regdatum;
	  }

	  public short getAutobestall() {
			 return autobestall;
	  }

	  public void setAutobestall(short autobestall) {
			 this.autobestall = autobestall;
	  }

	  public Date getBestdat() {
			 return bestdat;
	  }

	  public void setBestdat(Date bestdat) {
			 this.bestdat = bestdat;
	  }

	  public Integer getBestnr() {
			 return bestnr;
	  }

	  public void setBestnr(Integer bestnr) {
			 this.bestnr = bestnr;
	  }

	  public String getAnvandare() {
			 return anvandare;
	  }

	  public void setAnvandare(String anvandare) {
			 this.anvandare = anvandare;
	  }

	  public short getFinnsilager() {
			 return finnsilager;
	  }

	  public void setFinnsilager(short finnsilager) {
			 this.finnsilager = finnsilager;
	  }

	  public Date getInkomdatum() {
			 return inkomdatum;
	  }

	  public void setInkomdatum(Date inkomdatum) {
			 this.inkomdatum = inkomdatum;
	  }

	  public Integer getFakturanr() {
			 return fakturanr;
	  }

	  public void setFakturanr(Integer fakturanr) {
			 this.fakturanr = fakturanr;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (stjid != null ? stjid.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableStjarnrad)) {
					return false;
			 }
			 TableStjarnrad other = (TableStjarnrad) object;
			 if ((this.stjid == null && other.stjid != null) || (this.stjid != null && !this.stjid.equals(other.stjid))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableStjarnrad[stjid=" + stjid + "]";
	  }

}
