/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ulf
 */
@Embeddable
public class TableRorderPK implements Serializable {
	  @Column(name = "KUNDNR", nullable = false)
	  private String kundnr;
	  @Column(name = "ARTNR", nullable = false)
	  private String artnr;
	  @Column(name = "ID", nullable = false)
	  private short id;

	  public TableRorderPK() {
	  }

	  public TableRorderPK(String kundnr, String artnr, short id) {
			 this.kundnr = kundnr;
			 this.artnr = artnr;
			 this.id = id;
	  }

	  public String getKundnr() {
			 return kundnr;
	  }

	  public void setKundnr(String kundnr) {
			 this.kundnr = kundnr;
	  }

	  public String getArtnr() {
			 return artnr;
	  }

	  public void setArtnr(String artnr) {
			 this.artnr = artnr;
	  }

	  public short getId() {
			 return id;
	  }

	  public void setId(short id) {
			 this.id = id;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (kundnr != null ? kundnr.hashCode() : 0);
			 hash += (artnr != null ? artnr.hashCode() : 0);
			 hash += (int) id;
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableRorderPK)) {
					return false;
			 }
			 TableRorderPK other = (TableRorderPK) object;
			 if ((this.kundnr == null && other.kundnr != null) || (this.kundnr != null && !this.kundnr.equals(other.kundnr))) {
					return false;
			 }
			 if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
					return false;
			 }
			 if (this.id != other.id) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableRorderPK[kundnr=" + kundnr + ", artnr=" + artnr + ", id=" + id + "]";
	  }

}
