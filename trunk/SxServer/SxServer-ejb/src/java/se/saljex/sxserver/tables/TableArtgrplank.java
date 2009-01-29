/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "ARTGRPLANK")
@NamedQueries({@NamedQuery(name = "TableArtgrplank.findAll", query = "SELECT t FROM TableArtgrplank t")})
public class TableArtgrplank implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @EmbeddedId
	  protected TableArtgrplankPK tableArtgrplankPK;
	  @Column(name = "SORTORDER", nullable = false)
	  private int sortorder;

	  public TableArtgrplank() {
	  }

	  public TableArtgrplank(TableArtgrplankPK tableArtgrplankPK) {
			 this.tableArtgrplankPK = tableArtgrplankPK;
	  }

	  public TableArtgrplank(int grpid, int klasid) {
			 this.tableArtgrplankPK = new TableArtgrplankPK(grpid, klasid);
	  }

	  public TableArtgrplankPK getTableArtgrplankPK() {
			 return tableArtgrplankPK;
	  }

	  public void setTableArtgrplankPK(TableArtgrplankPK tableArtgrplankPK) {
			 this.tableArtgrplankPK = tableArtgrplankPK;
	  }

	  public int getSortorder() {
			 return sortorder;
	  }

	  public void setSortorder(int sortorder) {
			 this.sortorder = sortorder;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (tableArtgrplankPK != null ? tableArtgrplankPK.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableArtgrplank)) {
					return false;
			 }
			 TableArtgrplank other = (TableArtgrplank) object;
			 if ((this.tableArtgrplankPK == null && other.tableArtgrplankPK != null) || (this.tableArtgrplankPK != null && !this.tableArtgrplankPK.equals(other.tableArtgrplankPK))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableArtgrplank[tableArtgrplankPK=" + tableArtgrplankPK + "]";
	  }

}
