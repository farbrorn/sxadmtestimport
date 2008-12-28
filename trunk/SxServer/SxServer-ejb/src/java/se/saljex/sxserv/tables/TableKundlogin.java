/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserv.tables;

import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "KUNDLOGIN")
@NamedQueries({@NamedQuery(name = "TableKundlogin.findByLoginnamn", query = "SELECT t FROM TableKundlogin t WHERE t.loginnamn = :loginnamn"), @NamedQuery(name = "TableKundlogin.findByKontaktid", query = "SELECT t FROM TableKundlogin t WHERE t.kontaktid = :kontaktid")})
public class TableKundlogin implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "LOGINNAMN")
	private String loginnamn;
	@Id
	@Basic(optional = false)
	@Column(name = "KONTAKTID")
	private Integer kontaktid;
	@Basic(optional = false)
	@Column(name = "LOGINLOSEN")
	private String loginlosen;

	public TableKundlogin() {
	}

	public TableKundlogin(Integer kontaktid, String loginnamn, String loginlosen) {
		this.kontaktid = kontaktid;
		this.loginnamn = loginnamn;
		this.loginlosen = loginlosen;
	}

	public String getLoginnamn() {
		return loginnamn;
	}

	public void setLoginnamn(String loginnamn) {
		this.loginnamn = loginnamn;
	}

	public Integer getKontaktid() {
		return kontaktid;
	}

	public void setKontaktid(Integer kontaktid) {
		this.kontaktid = kontaktid;
	}

	public String getLoginlosen() {
		return loginlosen;
	}

	public void setLoginlosen(String loginlosen) {
		this.loginlosen = loginlosen;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kontaktid != null ? kontaktid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKundlogin)) {
			return false;
		}
		TableKundlogin other = (TableKundlogin) object;
		if ((this.kontaktid == null && other.kontaktid != null) || (this.kontaktid != null && !this.kontaktid.equals(other.kontaktid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableKundlogin[kontaktid=" + kontaktid + "]";
	}

}
