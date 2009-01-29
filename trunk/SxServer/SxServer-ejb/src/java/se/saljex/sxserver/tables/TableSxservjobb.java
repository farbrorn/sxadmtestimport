/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "SXSERVJOBB")
@NamedQueries({@NamedQuery(name = "TableSxservjobb.findEjSlutforda", query = "SELECT t FROM TableSxservjobb t WHERE t.slutford IS NULL order by t.jobbid")})
public class TableSxservjobb implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "JOBBID", nullable = false)
	private Integer jobbid;
	@Column(name = "UPPGIFT", nullable = false)
	private String uppgift;
	@Column(name = "DOKUMENTTYP")
	private String dokumenttyp;
	@Column(name = "SANDSATT")
	private String sandsatt;
	@Column(name = "EXTERNIDINT")
	private Integer externidint;
	@Column(name = "EXTERNIDSTRING")
	private String externidstring;
	@Column(name = "EPOST")
	private String epost;
	@Column(name = "SMS")
	private String sms;
	@Column(name = "ANVANDARE")
	private String anvandare;
	@Column(name = "SKAPAD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date skapad;
	@Column(name = "SLUTFORD")
	@Temporal(TemporalType.TIMESTAMP)
	private Date slutford;
	@Column(name = "BEARBETAR")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bearbetar;
	@Column(name = "ANTALFORSOK", nullable = false)
	private int antalforsok;

	public TableSxservjobb() {
	}


	public TableSxservjobb(Integer jobbid, String uppgift) {
		this.jobbid = jobbid;
		this.uppgift = uppgift;
	}
	
	public Integer getJobbid() {
		return jobbid;
	}

	public void setJobbid(Integer jobbid) {
		this.jobbid = jobbid;
	}

	public String getUppgift() {
		return uppgift;
	}

	public void setUppgift(String uppgift) {
		this.uppgift = uppgift;
	}

	public String getDokumenttyp() {
		return dokumenttyp;
	}

	public void setDokumenttyp(String dokumenttyp) {
		this.dokumenttyp = dokumenttyp;
	}

	public String getSandsatt() {
		return sandsatt;
	}

	public void setSandsatt(String sandsatt) {
		this.sandsatt = sandsatt;
	}

	public Integer getExternidint() {
		return externidint;
	}

	public void setExternidint(Integer externidint) {
		this.externidint = externidint;
	}

	public String getExternidstring() {
		return externidstring;
	}

	public void setExternidstring(String externidstring) {
		this.externidstring = externidstring;
	}

	public String getEpost() {
		return epost;
	}

	public void setEpost(String epost) {
		this.epost = epost;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getAnvandare() {
		return anvandare;
	}

	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public Date getSkapad() {
		return skapad;
	}

	public void setSkapad(Date skapad) {
		this.skapad = skapad;
	}

	public Date getSlutford() {
		return slutford;
	}

	public void setSlutford(Date slutford) {
		this.slutford = slutford;
	}
	public Date getBearbetar() {
		return bearbetar;
	}

	public void setBearbetar(Date bearbetar) {
		this.bearbetar = bearbetar;
	}
	
	public int getAntalforsok() {
		return antalforsok;
	}

	public void setAntalforsok(int antalforsok) {
		this.antalforsok = antalforsok;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (jobbid != null ? jobbid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSxservjobb)) {
			return false;
		}
		TableSxservjobb other = (TableSxservjobb) object;
		if ((this.jobbid == null && other.jobbid != null) || (this.jobbid != null && !this.jobbid.equals(other.jobbid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableSxservjobb[jobbid=" + jobbid + "]";
	}

}
