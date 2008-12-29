/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "LAGERID")
@NamedQueries({})
public class TableLagerid implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @Column(name = "LAGERNR", nullable = false)
	  private Short lagernr;
	  @Column(name = "BNAMN")
	  private String bnamn;
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
	  @Column(name = "TEL")
	  private String tel;
	  @Column(name = "FAX")
	  private String fax;
	  @Column(name = "EMAIL")
	  private String email;
	  @Column(name = "CHECKMEDDELIDLETIME", nullable = false)
	  private int checkmeddelidletime;
	  @Column(name = "SKRIVFOLJESEDEL", nullable = false)
	  private short skrivfoljesedel;
	  @Column(name = "MAXLAGERVARDE")
	  private Double maxlagervarde;

	  public TableLagerid() {
	  }

	  public Short getLagernr() {
			 return lagernr;
	  }

	  public void setLagernr(Short lagernr) {
			 this.lagernr = lagernr;
	  }

	  public String getBnamn() {
			 return bnamn;
	  }

	  public void setBnamn(String bnamn) {
			 this.bnamn = bnamn;
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

	  public String getTel() {
			 return tel;
	  }

	  public void setTel(String tel) {
			 this.tel = tel;
	  }

	  public String getFax() {
			 return fax;
	  }

	  public void setFax(String fax) {
			 this.fax = fax;
	  }

	  public String getEmail() {
			 return email;
	  }

	  public void setEmail(String email) {
			 this.email = email;
	  }

	  public int getCheckmeddelidletime() {
			 return checkmeddelidletime;
	  }

	  public void setCheckmeddelidletime(int checkmeddelidletime) {
			 this.checkmeddelidletime = checkmeddelidletime;
	  }

	  public short getSkrivfoljesedel() {
			 return skrivfoljesedel;
	  }

	  public void setSkrivfoljesedel(short skrivfoljesedel) {
			 this.skrivfoljesedel = skrivfoljesedel;
	  }

	  public Double getMaxlagervarde() {
			 return maxlagervarde;
	  }

	  public void setMaxlagervarde(Double maxlagervarde) {
			 this.maxlagervarde = maxlagervarde;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (lagernr != null ? lagernr.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableLagerid)) {
					return false;
			 }
			 TableLagerid other = (TableLagerid) object;
			 if ((this.lagernr == null && other.lagernr != null) || (this.lagernr != null && !this.lagernr.equals(other.lagernr))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableLagerid[lagernr=" + lagernr + "]";
	  }

}
