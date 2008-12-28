/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "FDORDERNR")
@NamedQueries({@NamedQuery(name = "TableFdordernr.findAll", query = "SELECT f FROM TableFdordernr f"),@NamedQuery(name = "TableFdordernr.updateOrdernrBy1", query =  "UPDATE TableFdordernr f SET f.ordernr = f.ordernr+1 where f.ordernr = :ordernr")})
public class TableFdordernr implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
		@Column(name = "ORDERNR", nullable = false)
		private Integer ordernr;

	public void setOrdernr(Integer ordernr) {
		this.ordernr = ordernr;
	}
	public Integer getOrdernr() {
		return ordernr;
	}
		
	  
	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (ordernr != null ? ordernr.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableFdordernr)) {
					return false;
			 }
			 TableFdordernr other = (TableFdordernr) object;
			 if ((this.ordernr == null && other.ordernr != null) || (this.ordernr != null && !this.ordernr.equals(other.ordernr))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableFdordernr[id=" + ordernr + "]";
	  }

}
