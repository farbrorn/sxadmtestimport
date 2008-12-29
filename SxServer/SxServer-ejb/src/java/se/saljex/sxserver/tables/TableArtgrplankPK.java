/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ulf
 */
@Embeddable
public class TableArtgrplankPK implements Serializable {
	  @Column(name = "GRPID", nullable = false)
	  private int grpid;
	  @Column(name = "KLASID", nullable = false)
	  private int klasid;

	  public TableArtgrplankPK() {
	  }

	  public TableArtgrplankPK(int grpid, int klasid) {
			 this.grpid = grpid;
			 this.klasid = klasid;
	  }

	  public int getGrpid() {
			 return grpid;
	  }

	  public void setGrpid(int grpid) {
			 this.grpid = grpid;
	  }

	  public int getKlasid() {
			 return klasid;
	  }

	  public void setKlasid(int klasid) {
			 this.klasid = klasid;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (int) grpid;
			 hash += (int) klasid;
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableArtgrplankPK)) {
					return false;
			 }
			 TableArtgrplankPK other = (TableArtgrplankPK) object;
			 if (this.grpid != other.grpid) {
					return false;
			 }
			 if (this.klasid != other.klasid) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableArtgrplankPK[grpid=" + grpid + ", klasid=" + klasid + "]";
	  }

}
