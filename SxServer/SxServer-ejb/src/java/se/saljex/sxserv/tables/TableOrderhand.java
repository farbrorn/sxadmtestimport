/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "ORDERHAND")
@NamedQueries({})
public class TableOrderhand implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableOrderhandPK tableOrderhandPK;
	@Column(name = "ANVANDARE")
	private String anvandare;
	@Column(name = "HANDELSE")
	private String handelse;
	@Column(name = "TRANSPORTOR")
	private String transportor;
	@Column(name = "FRAKTSEDELNR")
	private String fraktsedelnr;
	@Column(name = "NYORDERNR", nullable = false)
	private int nyordernr;
	@Column(name = "ANTALKOLLI")
	private Integer antalkolli;
	@Column(name = "KOLLISLAG")
	private String kollislag;
	@Column(name = "TOTALVIKT")
	private Integer totalvikt;
	@Column(name = "SERVERDATUM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date serverdatum;

	public TableOrderhand() {
	}

	public TableOrderhand(int ordernr, String anvandare, String handelse) {
		Date d = new Date();
		this.tableOrderhandPK = new TableOrderhandPK(ordernr, d, d);
		this.anvandare = anvandare;
		this.handelse = handelse;
	}

	
	public TableOrderhand(TableOrderhandPK tableOrderhandPK) {
		this.tableOrderhandPK = tableOrderhandPK;
	}


	public TableOrderhand(int ordernr, Date datum, Date tid) {
		this.tableOrderhandPK = new TableOrderhandPK(ordernr, datum, tid);
	}

	public TableOrderhandPK getTableOrderhandPK() {
		return tableOrderhandPK;
	}

	public void setTableOrderhandPK(TableOrderhandPK tableOrderhandPK) {
		this.tableOrderhandPK = tableOrderhandPK;
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

	public String getTransportor() {
		return transportor;
	}

	public void setTransportor(String transportor) {
		this.transportor = transportor;
	}

	public String getFraktsedelnr() {
		return fraktsedelnr;
	}

	public void setFraktsedelnr(String fraktsedelnr) {
		this.fraktsedelnr = fraktsedelnr;
	}

	public int getNyordernr() {
		return nyordernr;
	}

	public void setNyordernr(int nyordernr) {
		this.nyordernr = nyordernr;
	}

	public Integer getAntalkolli() {
		return antalkolli;
	}

	public void setAntalkolli(Integer antalkolli) {
		this.antalkolli = antalkolli;
	}

	public String getKollislag() {
		return kollislag;
	}

	public void setKollislag(String kollislag) {
		this.kollislag = kollislag;
	}

	public Integer getTotalvikt() {
		return totalvikt;
	}

	public void setTotalvikt(Integer totalvikt) {
		this.totalvikt = totalvikt;
	}

	public Date getServerdatum() {
		return serverdatum;
	}

	public void setServerdatum(Date serverdatum) {
		this.serverdatum = serverdatum;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableOrderhandPK != null ? tableOrderhandPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableOrderhand)) {
			return false;
		}
		TableOrderhand other = (TableOrderhand) object;
		if ((this.tableOrderhandPK == null && other.tableOrderhandPK != null) || (this.tableOrderhandPK != null && !this.tableOrderhandPK.equals(other.tableOrderhandPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableOrderhand[tableOrderhandPK=" + tableOrderhandPK + "]";
	}

}
