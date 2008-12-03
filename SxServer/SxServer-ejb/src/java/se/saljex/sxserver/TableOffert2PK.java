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
public class TableOffert2PK implements Serializable {
	  @Column(name = "OFFERTNR", nullable = false)
	  private int offertnr;
	  @Column(name = "POS", nullable = false)
	  private short pos;

	  public TableOffert2PK() {
	  }

	  public TableOffert2PK(int offertnr, short pos) {
			 this.offertnr = offertnr;
			 this.pos = pos;
	  }

	  public int getOffertnr() {
			 return offertnr;
	  }

	  public void setOffertnr(int offertnr) {
			 this.offertnr = offertnr;
	  }

	  public short getPos() {
			 return pos;
	  }

	  public void setPos(short pos) {
			 this.pos = pos;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (int) offertnr;
			 hash += (int) pos;
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableOffert2PK)) {
					return false;
			 }
			 TableOffert2PK other = (TableOffert2PK) object;
			 if (this.offertnr != other.offertnr) {
					return false;
			 }
			 if (this.pos != other.pos) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableOffert2PK[offertnr=" + offertnr + ", pos=" + pos + "]";
	  }

}
