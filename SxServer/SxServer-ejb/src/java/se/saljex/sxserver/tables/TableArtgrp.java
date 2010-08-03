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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
 
/**
 *
 * @author ulf
 */
@Entity
@Table(name = "ARTGRP")
@NamedQueries({@NamedQuery(name = "TableArtgrp.findAll", query = "SELECT t FROM TableArtgrp t")})
public class TableArtgrp implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @Column(name = "GRPID", nullable = false)
	  private Integer grpid;
	  @Column(name = "PREVGRPID", nullable = false)
	  private int prevgrpid;
	  @Column(name = "RUBRIK")
	  private String rubrik;
	  @Column(name = "TEXT")
	  private String text;
	  @Column(name = "INFOURL")
	  private String infourl;
	  @Column(name = "SORTORDER", nullable = false)
	  private int sortorder;
	  @Column(name = "HTML")
	  private String html;

	  public TableArtgrp() {
	  }

	  public TableArtgrp(Integer grpid) {
			 this.grpid = grpid;
	  }

	  public Integer getGrpid() {
			 return grpid;
	  }

	  public void setGrpid(Integer grpid) {
			 this.grpid = grpid;
	  }

	  public int getPrevgrpid() {
			 return prevgrpid;
	  }

	  public void setPrevgrpid(int prevgrpid) {
			 this.prevgrpid = prevgrpid;
	  }

	  public String getRubrik() {
			 return rubrik;
	  }

	  public void setRubrik(String rubrik) {
			 this.rubrik = rubrik;
	  }

	  public String getText() {
			 return text;
	  }

	  public void setText(String text) {
			 this.text = text;
	  }

	  public String getInfourl() {
			 return infourl;
	  }

	  public void setInfourl(String infourl) {
			 this.infourl = infourl;
	  }

	  public int getSortorder() {
			 return sortorder;
	  }

	  public void setSortorder(int sortorder) {
			 this.sortorder = sortorder;
	  }

	  public String getHtml() {
			 return html;
	  }

	  public void setHtml(String html) {
			 this.html = html;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (grpid != null ? grpid.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableArtgrp)) {
					return false;
			 }
			 TableArtgrp other = (TableArtgrp) object;
			 if ((this.grpid == null && other.grpid != null) || (this.grpid != null && !this.grpid.equals(other.grpid))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableArtgrp[grpid=" + grpid + "]";
	  }

}
