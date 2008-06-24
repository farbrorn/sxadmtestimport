/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Embeddable
public class TableBetjourPK implements Serializable {
	  @Column(name = "FAKTNR", nullable = false)
	  private int faktnr;
	  @Column(name = "BETSATT", nullable = false)
	  private char betsatt;
	  @Column(name = "TALLOPNR", nullable = false)
	  private int tallopnr;
	  @Column(name = "TALDATUM", nullable = false)
	  @Temporal(TemporalType.DATE)
	  private Date taldatum;

	  public TableBetjourPK() {
	  }

	  public TableBetjourPK(int faktnr, char betsatt, int tallopnr, Date taldatum) {
			 this.faktnr = faktnr;
			 this.betsatt = betsatt;
			 this.tallopnr = tallopnr;
			 this.taldatum = taldatum;
	  }

	  public int getFaktnr() {
			 return faktnr;
	  }

	  public void setFaktnr(int faktnr) {
			 this.faktnr = faktnr;
	  }

	  public char getBetsatt() {
			 return betsatt;
	  }

	  public void setBetsatt(char betsatt) {
			 this.betsatt = betsatt;
	  }

	  public int getTallopnr() {
			 return tallopnr;
	  }

	  public void setTallopnr(int tallopnr) {
			 this.tallopnr = tallopnr;
	  }

	  public Date getTaldatum() {
			 return taldatum;
	  }

	  public void setTaldatum(Date taldatum) {
			 this.taldatum = taldatum;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (int) faktnr;
			 hash += (int) betsatt;
			 hash += (int) tallopnr;
			 hash += (taldatum != null ? taldatum.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableBetjourPK)) {
					return false;
			 }
			 TableBetjourPK other = (TableBetjourPK) object;
			 if (this.faktnr != other.faktnr) {
					return false;
			 }
			 if (this.betsatt != other.betsatt) {
					return false;
			 }
			 if (this.tallopnr != other.tallopnr) {
					return false;
			 }
			 if ((this.taldatum == null && other.taldatum != null) || (this.taldatum != null && !this.taldatum.equals(other.taldatum))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableBetjourPK[faktnr=" + faktnr + ", betsatt=" + betsatt + ", tallopnr=" + tallopnr + ", taldatum=" + taldatum + "]";
	  }

}
