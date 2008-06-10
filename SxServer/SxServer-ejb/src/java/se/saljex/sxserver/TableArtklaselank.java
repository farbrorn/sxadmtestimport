/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

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
@Table(name = "ARTKLASELANK")
@NamedQueries({@NamedQuery(name = "TableArtklaselank.findByKlasid", query = "SELECT t FROM TableArtklaselank t WHERE t.tableArtklaselankPK.klasid = :klasid"), @NamedQuery(name = "TableArtklaselank.findByArtnr", query = "SELECT t FROM TableArtklaselank t WHERE t.tableArtklaselankPK.artnr = :artnr"), @NamedQuery(name = "TableArtklaselank.findBySortorder", query = "SELECT t FROM TableArtklaselank t WHERE t.sortorder = :sortorder")})
public class TableArtklaselank implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @EmbeddedId
	  protected TableArtklaselankPK tableArtklaselankPK;
	  @Column(name = "SORTORDER")
	  private Integer sortorder;

	  public TableArtklaselank() {
	  }

	  public TableArtklaselank(TableArtklaselankPK tableArtklaselankPK) {
			 this.tableArtklaselankPK = tableArtklaselankPK;
	  }

	  public TableArtklaselank(int klasid, String artnr) {
			 this.tableArtklaselankPK = new TableArtklaselankPK(klasid, artnr);
	  }

	  public TableArtklaselankPK getTableArtklaselankPK() {
			 return tableArtklaselankPK;
	  }

	  public void setTableArtklaselankPK(TableArtklaselankPK tableArtklaselankPK) {
			 this.tableArtklaselankPK = tableArtklaselankPK;
	  }

	  public Integer getSortorder() {
			 return sortorder;
	  }

	  public void setSortorder(Integer sortorder) {
			 this.sortorder = sortorder;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (tableArtklaselankPK != null ? tableArtklaselankPK.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableArtklaselank)) {
					return false;
			 }
			 TableArtklaselank other = (TableArtklaselank) object;
			 if ((this.tableArtklaselankPK == null && other.tableArtklaselankPK != null) || (this.tableArtklaselankPK != null && !this.tableArtklaselankPK.equals(other.tableArtklaselankPK))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableArtklaselank[tableArtklaselankPK=" + tableArtklaselankPK + "]";
	  }

}
