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
@Table(name = "MEDDEL")
@NamedQueries({})
public class TableMeddel implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableMeddelPK tableMeddelPK;
	@Column(name = "AVSANDARE")
	private String avsandare;
	@Column(name = "TEXT1")
	private String text1;
	@Column(name = "TEXT2")
	private String text2;

	public TableMeddel() {
	}


	public TableMeddel(String saljare, short avlast, Date datum, Date tid, String avsandare, String text1, String text2) {
		this.tableMeddelPK = new TableMeddelPK(saljare, avlast, datum, tid);
		this.avsandare = avsandare;
		this.text1 = text1;
		this.text2 = text2;
	}

	public TableMeddel(String saljare, String text1, String text2) {
		this.tableMeddelPK = new TableMeddelPK(saljare, (short)0, new Date(), new Date());
		this.avsandare = "Server";
		this.text1 = text1;
		this.text2 = text2;
	}
	
	
	public TableMeddelPK getTableMeddelPK() {
		return tableMeddelPK;
	}

	public void setTableMeddelPK(TableMeddelPK tableMeddelPK) {
		this.tableMeddelPK = tableMeddelPK;
	}

	public String getAvsandare() {
		return avsandare;
	}

	public void setAvsandare(String avsandare) {
		this.avsandare = avsandare;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableMeddelPK != null ? tableMeddelPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableMeddel)) {
			return false;
		}
		TableMeddel other = (TableMeddel) object;
		if ((this.tableMeddelPK == null && other.tableMeddelPK != null) || (this.tableMeddelPK != null && !this.tableMeddelPK.equals(other.tableMeddelPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableMeddel[tableMeddelPK=" + tableMeddelPK + "]";
	}

}
