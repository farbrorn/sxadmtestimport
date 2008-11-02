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
@Table(name = "SALJARE")
@NamedQueries({@NamedQuery(name = "TableSaljare.findByForkortning", query = "SELECT b FROM TableSaljare b WHERE b.forkortning = :forkortning order by b.namn")})
public class TableSaljare implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Column(name = "NAMN", nullable = false)
	  private String namn;
	  @Id
	  @Column(name = "FORKORTNING")
	  private String forkortning;
	  @Column(name = "ADR1")
	  private String adr1;
	  @Column(name = "ADR2")
	  private String adr2;
	  @Column(name = "ADR3")
	  private String adr3;
	  @Column(name = "TEL")
	  private String tel;
	  @Column(name = "MOBIL")
	  private String mobil;
	  @Column(name = "FAX")
	  private String fax;
	  @Column(name = "TOTALT", nullable = false)
	  private double totalt;
	  @Column(name = "TBIDRAG", nullable = false)
	  private double tbidrag;
	  @Column(name = "ANT1")
	  private String ant1;
	  @Column(name = "ANT2")
	  private String ant2;
	  @Column(name = "ANT3")
	  private String ant3;
	  @Column(name = "BEHORIGHET", nullable = false)
	  private short behorighet;
	  @Column(name = "LOSEN")
	  private String losen;
	  @Column(name = "LAGERNR")
	  private Short lagernr;
	  @Column(name = "LOSENGILTIGTDATUM")
	  @Temporal(TemporalType.DATE)
	  private Date losengiltigtdatum;

	  public TableSaljare() {
	  }


	  public String getNamn() {
			 return namn;
	  }

	  public void setNamn(String namn) {
			 this.namn = namn;
	  }

	  public String getForkortning() {
			 return forkortning;
	  }

	  public void setForkortning(String forkortning) {
			 this.forkortning = forkortning;
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

	  public String getTel() {
			 return tel;
	  }

	  public void setTel(String tel) {
			 this.tel = tel;
	  }

	  public String getMobil() {
			 return mobil;
	  }

	  public void setMobil(String mobil) {
			 this.mobil = mobil;
	  }

	  public String getFax() {
			 return fax;
	  }

	  public void setFax(String fax) {
			 this.fax = fax;
	  }

	  public double getTotalt() {
			 return totalt;
	  }

	  public void setTotalt(double totalt) {
			 this.totalt = totalt;
	  }

	  public double getTbidrag() {
			 return tbidrag;
	  }

	  public void setTbidrag(double tbidrag) {
			 this.tbidrag = tbidrag;
	  }

	  public String getAnt1() {
			 return ant1;
	  }

	  public void setAnt1(String ant1) {
			 this.ant1 = ant1;
	  }

	  public String getAnt2() {
			 return ant2;
	  }

	  public void setAnt2(String ant2) {
			 this.ant2 = ant2;
	  }

	  public String getAnt3() {
			 return ant3;
	  }

	  public void setAnt3(String ant3) {
			 this.ant3 = ant3;
	  }

	  public short getBehorighet() {
			 return behorighet;
	  }

	  public void setBehorighet(short behorighet) {
			 this.behorighet = behorighet;
	  }

	  public String getLosen() {
			 return losen;
	  }

	  public void setLosen(String losen) {
			 this.losen = losen;
	  }

	  public Short getLagernr() {
			 return lagernr;
	  }

	  public void setLagernr(Short lagernr) {
			 this.lagernr = lagernr;
	  }

	  public Date getLosengiltigtdatum() {
			 return losengiltigtdatum;
	  }

	  public void setLosengiltigtdatum(Date losengiltigtdatum) {
			 this.losengiltigtdatum = losengiltigtdatum;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (forkortning != null ? forkortning.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableSaljare)) {
					return false;
			 }
			 TableSaljare other = (TableSaljare) object;
			 if ((this.forkortning == null && other.forkortning != null) || (this.forkortning != null && !this.forkortning.equals(other.forkortning))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableSaljare[forkortning=" + forkortning + "]";
	  }

}
