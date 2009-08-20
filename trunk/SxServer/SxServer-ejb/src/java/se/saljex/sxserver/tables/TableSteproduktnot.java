/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "steproduktnot")
@NamedQueries({@NamedQuery(name = "TableSteproduktnot.findAll", query = "SELECT t FROM TableSteproduktnot t"), @NamedQuery(name = "TableSteproduktnot.findById", query = "SELECT t FROM TableSteproduktnot t WHERE t.id = :id"), @NamedQuery(name = "TableSteproduktnot.findBySn", query = "SELECT t FROM TableSteproduktnot t WHERE t.sn = :sn"), @NamedQuery(name = "TableSteproduktnot.findByCrdt", query = "SELECT t FROM TableSteproduktnot t WHERE t.crdt = :crdt"), @NamedQuery(name = "TableSteproduktnot.findByAnvandare", query = "SELECT t FROM TableSteproduktnot t WHERE t.anvandare = :anvandare"), @NamedQuery(name = "TableSteproduktnot.findByArendetyp", query = "SELECT t FROM TableSteproduktnot t WHERE t.arendetyp = :arendetyp"), @NamedQuery(name = "TableSteproduktnot.findByPublicerasomqa", query = "SELECT t FROM TableSteproduktnot t WHERE t.publicerasomqa = :publicerasomqa"), @NamedQuery(name = "TableSteproduktnot.findByFoljuppdatum", query = "SELECT t FROM TableSteproduktnot t WHERE t.foljuppdatum = :foljuppdatum"), @NamedQuery(name = "TableSteproduktnot.findByFraga", query = "SELECT t FROM TableSteproduktnot t WHERE t.fraga = :fraga"), @NamedQuery(name = "TableSteproduktnot.findBySvar", query = "SELECT t FROM TableSteproduktnot t WHERE t.svar = :svar"), @NamedQuery(name = "TableSteproduktnot.findByFilnamn", query = "SELECT t FROM TableSteproduktnot t WHERE t.filnamn = :filnamn"), @NamedQuery(name = "TableSteproduktnot.findByContenttype", query = "SELECT t FROM TableSteproduktnot t WHERE t.contenttype = :contenttype")})
public class TableSteproduktnot implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "sn")
	private String sn;
	@Column(name = "crdt", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date crdt;
	@Column(name = "anvandare")
	private String anvandare;
	@Column(name = "arendetyp")
	private String arendetyp;
	@Column(name = "felorsak")
	private String felorsak;
	@Basic(optional = false)
	@Column(name = "publicerasomqa")
	private short publicerasomqa;
	@Column(name = "foljuppdatum")
	@Temporal(TemporalType.DATE)
	private Date foljuppdatum;
	@Column(name = "fraga")
	private String fraga;
	@Column(name = "svar")
	private String svar;
	@Lob
	@Column(name = "bilaga")
	private byte[] bilaga;
	@Column(name = "filnamn")
	private String filnamn;
	@Column(name = "contenttype")
	private String contenttype;
	@Column(name = "serviceombudkundnr")
	private String serviceombudkundnr;
	@Column(name = "serviceombudnamn")
	private String serviceombudnamn;

	public TableSteproduktnot() {
	}

	public TableSteproduktnot(Integer id) {
		this.id = id;
	}

	public TableSteproduktnot(Integer id, String sn, short publicerasomqa) {
		this.id = id;
		this.sn = sn;
		this.publicerasomqa = publicerasomqa;
	}

	public String getFelorsak() {
		return felorsak;
	}

	public void setFelorsak(String felorsak) {
		this.felorsak = felorsak;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getCrdt() {
		return crdt;
	}

	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}

	public String getAnvandare() {
		return anvandare;
	}

	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public String getArendetyp() {
		return arendetyp;
	}

	public void setArendetyp(String arendetyp) {
		this.arendetyp = arendetyp;
	}

	public short getPublicerasomqa() {
		return publicerasomqa;
	}

	public void setPublicerasomqa(short publicerasomqa) {
		this.publicerasomqa = publicerasomqa;
	}

	public Date getFoljuppdatum() {
		return foljuppdatum;
	}

	public void setFoljuppdatum(Date foljuppdatum) {
		this.foljuppdatum = foljuppdatum;
	}

	public String getFraga() {
		return fraga;
	}

	public void setFraga(String fraga) {
		this.fraga = fraga;
	}

	public String getSvar() {
		return svar;
	}

	public void setSvar(String svar) {
		this.svar = svar;
	}

	public byte[] getBilaga() {
		return bilaga;
	}

	public void setBilaga(byte[] bilaga) {
		this.bilaga = bilaga;
	}

	public String getFilnamn() {
		return filnamn;
	}

	public void setFilnamn(String filnamn) {
		this.filnamn = filnamn;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getServiceombudkundnr() {
		return serviceombudkundnr;
	}

	public void setServiceombudkundnr(String serviceombudkundnr) {
		this.serviceombudkundnr = serviceombudkundnr;
	}

	public String getServiceombudnamn() {
		return serviceombudnamn;
	}

	public void setServiceombudnamn(String serviceombudnamn) {
		this.serviceombudnamn = serviceombudnamn;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableSteproduktnot)) {
			return false;
		}
		TableSteproduktnot other = (TableSteproduktnot) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableSteproduktnot[id=" + id + "]";
	}

}
