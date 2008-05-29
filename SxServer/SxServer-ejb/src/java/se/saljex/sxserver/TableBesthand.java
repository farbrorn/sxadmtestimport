/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "BESTHAND")
@NamedQueries({})
public class TableBesthand implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableBesthandPK tableBesthandPK;
	@Column(name = "ANVANDARE")
	private String anvandare;
	@Column(name = "HANDELSE")
	private String handelse;
	@Column(name = "INLEVID", nullable = false)
	private int inlevid;

	public TableBesthand() {
	}


	public TableBesthand(int bestnr, String anvandare, String handelse, int inlevid) {
		Date d = new Date();
		this.tableBesthandPK = new TableBesthandPK(bestnr, d, d);
		this.anvandare = anvandare;
		this.handelse = handelse;
		this.inlevid = inlevid;
	}

	
	
	public TableBesthandPK getTableBesthandPK() {
		return tableBesthandPK;
	}

	public void setTableBesthandPK(TableBesthandPK tableBesthandPK) {
		this.tableBesthandPK = tableBesthandPK;
	}

	public String getAnvandare() {
		return anvandare;
	}

	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public String getHandelse() {
		return handelse;
	}

	public void setHandelse(String handelse) {
		this.handelse = handelse;
	}

	public int getInlevid() {
		return inlevid;
	}

	public void setInlevid(int inlevid) {
		this.inlevid = inlevid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableBesthandPK != null ? tableBesthandPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableBesthand)) {
			return false;
		}
		TableBesthand other = (TableBesthand) object;
		if ((this.tableBesthandPK == null && other.tableBesthandPK != null) || (this.tableBesthandPK != null && !this.tableBesthandPK.equals(other.tableBesthandPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableBesthand[tableBesthandPK=" + tableBesthandPK + "]";
	}

}
