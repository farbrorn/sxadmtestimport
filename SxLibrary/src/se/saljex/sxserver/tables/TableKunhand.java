/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.tables;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "kunhand")
@NamedQueries({
	@NamedQuery(name = "TableKunhand.findAll", query = "SELECT t FROM TableKunhand t"),
	@NamedQuery(name = "TableKunhand.findByKundnr", query = "SELECT t FROM TableKunhand t WHERE t.tableKunhandPK.kundnr = :kundnr")
})
public class TableKunhand implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableKunhandPK tableKunhandPK;
	@Size(max = 3)
    @Column(name = "anvandare")
	private String anvandare;
	@Size(max = 20)
    @Column(name = "handelse")
	private String handelse;
	@Size(max = 254)
    @Column(name = "anteckning")
	private String anteckning;

	public TableKunhand() {
	}


	public TableKunhand(String kundnr, Date datum, Date tid) {
		this.tableKunhandPK = new TableKunhandPK(kundnr, datum, tid);
	}

	public TableKunhandPK getTableKunhandPK() {
		return tableKunhandPK;
	}

	public void setTableKunhandPK(TableKunhandPK tableKunhandPK) {
		this.tableKunhandPK = tableKunhandPK;
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

	public String getAnteckning() {
		return anteckning;
	}

	public void setAnteckning(String anteckning) {
		this.anteckning = anteckning;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableKunhandPK != null ? tableKunhandPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableKunhand)) {
			return false;
		}
		TableKunhand other = (TableKunhand) object;
		if ((this.tableKunhandPK == null && other.tableKunhandPK != null) || (this.tableKunhandPK != null && !this.tableKunhandPK.equals(other.tableKunhandPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableKunhand[ tableKunhandPK=" + tableKunhandPK + " ]";
	}
	
}
