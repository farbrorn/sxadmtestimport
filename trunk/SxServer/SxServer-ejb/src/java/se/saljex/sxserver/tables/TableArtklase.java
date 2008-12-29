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
@Table(name = "ARTKLASE")
@NamedQueries({@NamedQuery(name = "TableArtklase.findAll", query = "SELECT t FROM TableArtklase t") })
public class TableArtklase implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @Id
	  @Column(name = "KLASID", nullable = false)
	  private Integer klasid;
	  @Column(name = "RUBRIK")
	  private String rubrik;
	  @Column(name = "TEXT")
	  private String text;
	  @Column(name = "INFOURL")
	  private String infourl;
	  @Column(name = "FRAKTVILLKOR")
	  private String fraktvillkor;
	  @Column(name = "HTML")
	  private String html;

	  public TableArtklase() {
	  }

	  public TableArtklase(Integer klasid) {
			 this.klasid = klasid;
	  }

	  public Integer getKlasid() {
			 return klasid;
	  }

	  public void setKlasid(Integer klasid) {
			 this.klasid = klasid;
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

	  public String getFraktvillkor() {
			 return fraktvillkor;
	  }

	  public void setFraktvillkor(String fraktvillkor) {
			 this.fraktvillkor = fraktvillkor;
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
			 hash += (klasid != null ? klasid.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableArtklase)) {
					return false;
			 }
			 TableArtklase other = (TableArtklase) object;
			 if ((this.klasid == null && other.klasid != null) || (this.klasid != null && !this.klasid.equals(other.klasid))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableArtklase[klasid=" + klasid + "]";
	  }

}
