/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "kundgrp")
@NamedQueries({
	@NamedQuery(name = "TableKundgrp.findAll", query = "SELECT t FROM TableKundgrp t"),
	@NamedQuery(name = "TableKundgrp.findByGrpnr", query = "SELECT t FROM TableKundgrp t WHERE t.grpnr = :grpnr"),
	@NamedQuery(name = "TableKundgrp.findByBeskrivning", query = "SELECT t FROM TableKundgrp t WHERE t.beskrivning = :beskrivning")})
public class TableKundgrp implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "grpnr")
	private String grpnr;
	@Size(max = 100)
    @Column(name = "beskrivning")
	private String beskrivning;

	public TableKundgrp() {
	}

	public TableKundgrp(String grpnr) {
		this.grpnr = grpnr;
	}

	public String getGrpnr() {
		return grpnr;
	}

	public void setGrpnr(String grpnr) {
		this.grpnr = grpnr;
	}

	public String getBeskrivning() {
		return beskrivning;
	}

	public void setBeskrivning(String beskrivning) {
		this.beskrivning = beskrivning;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (grpnr != null ? grpnr.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKundgrp)) {
			return false;
		}
		TableKundgrp other = (TableKundgrp) object;
		if ((this.grpnr == null && other.grpnr != null) || (this.grpnr != null && !this.grpnr.equals(other.grpnr))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableKundgrp[ grpnr=" + grpnr + " ]";
	}
	
}
