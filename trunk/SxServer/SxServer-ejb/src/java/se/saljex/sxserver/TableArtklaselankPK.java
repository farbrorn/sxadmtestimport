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
public class TableArtklaselankPK implements Serializable {
	  @Column(name = "KLASID", nullable = false)
	  private int klasid;
	  @Column(name = "ARTNR", nullable = false)
	  private String artnr;

	  public TableArtklaselankPK() {
	  }

	  public TableArtklaselankPK(int klasid, String artnr) {
			 this.klasid = klasid;
			 this.artnr = artnr;
	  }

	  public int getKlasid() {
			 return klasid;
	  }

	  public void setKlasid(int klasid) {
			 this.klasid = klasid;
	  }

	  public String getArtnr() {
			 return artnr;
	  }

	  public void setArtnr(String artnr) {
			 this.artnr = artnr;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (int) klasid;
			 hash += (artnr != null ? artnr.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableArtklaselankPK)) {
					return false;
			 }
			 TableArtklaselankPK other = (TableArtklaselankPK) object;
			 if (this.klasid != other.klasid) {
					return false;
			 }
			 if ((this.artnr == null && other.artnr != null) || (this.artnr != null && !this.artnr.equals(other.artnr))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableArtklaselankPK[klasid=" + klasid + ", artnr=" + artnr + "]";
	  }

}
