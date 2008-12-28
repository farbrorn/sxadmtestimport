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
 * @author ulf
 */
@Entity
@Table(name = "RORDER")
@NamedQueries({@NamedQuery(name = "TableRorder.findByKundnr", query = "SELECT t FROM TableRorder t WHERE t.tableRorderPK.kundnr = :kundnr order by t.tableRorderPK.artnr"), 
@NamedQuery(name = "TableRorder.findAll", query = "SELECT t FROM TableRorder t order by t.tableRorderPK.artnr")
})
public class TableRorder implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @EmbeddedId
	  protected TableRorderPK tableRorderPK;
	  @Column(name = "NAMN")
	  private String namn;
	  @Column(name = "PRIS", nullable = false)
	  private double pris;
	  @Column(name = "RAB", nullable = false)
	  private double rab;
	  @Column(name = "REST", nullable = false)
	  private double rest;
	  @Column(name = "ENH")
	  private String enh;
	  @Column(name = "KONTO")
	  private String konto;
	  @Column(name = "NETTO", nullable = false)
	  private double netto;
	  @Column(name = "MARKE")
	  private String marke;
	  @Column(name = "LEVNR", nullable = false)
	  private String levnr;
	  @Column(name = "LEVDAT")
	  @Temporal(TemporalType.DATE)
	  private Date levdat;
	  @Column(name = "LEVBESTDAT")
	  @Temporal(TemporalType.DATE)
	  private Date levbestdat;
	  @Column(name = "LAGERNR", nullable = false)
	  private short lagernr;
	  @Column(name = "STJID", nullable = false)
	  private int stjid;

	  public TableRorder() {
	  }

	  public TableRorder(TableRorderPK tableRorderPK) {
			 this.tableRorderPK = tableRorderPK;
	  }


	  public TableRorderPK getTableRorderPK() {
			 return tableRorderPK;
	  }

	  public void setTableRorderPK(TableRorderPK tableRorderPK) {
			 this.tableRorderPK = tableRorderPK;
	  }

	  public String getNamn() {
			 return namn;
	  }

	  public void setNamn(String namn) {
			 this.namn = namn;
	  }

	  public double getPris() {
			 return pris;
	  }

	  public void setPris(double pris) {
			 this.pris = pris;
	  }

	  public double getRab() {
			 return rab;
	  }

	  public void setRab(double rab) {
			 this.rab = rab;
	  }

	  public double getRest() {
			 return rest;
	  }

	  public void setRest(double rest) {
			 this.rest = rest;
	  }

	  public String getEnh() {
			 return enh;
	  }

	  public void setEnh(String enh) {
			 this.enh = enh;
	  }

	  public String getKonto() {
			 return konto;
	  }

	  public void setKonto(String konto) {
			 this.konto = konto;
	  }

	  public double getNetto() {
			 return netto;
	  }

	  public void setNetto(double netto) {
			 this.netto = netto;
	  }

	  public String getMarke() {
			 return marke;
	  }

	  public void setMarke(String marke) {
			 this.marke = marke;
	  }

	  public String getLevnr() {
			 return levnr;
	  }

	  public void setLevnr(String levnr) {
			 this.levnr = levnr;
	  }

	  public Date getLevdat() {
			 return levdat;
	  }

	  public void setLevdat(Date levdat) {
			 this.levdat = levdat;
	  }

	  public Date getLevbestdat() {
			 return levbestdat;
	  }

	  public void setLevbestdat(Date levbestdat) {
			 this.levbestdat = levbestdat;
	  }

	  public short getLagernr() {
			 return lagernr;
	  }

	  public void setLagernr(short lagernr) {
			 this.lagernr = lagernr;
	  }

	  public int getStjid() {
			 return stjid;
	  }

	  public void setStjid(int stjid) {
			 this.stjid = stjid;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (tableRorderPK != null ? tableRorderPK.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableRorder)) {
					return false;
			 }
			 TableRorder other = (TableRorder) object;
			 if ((this.tableRorderPK == null && other.tableRorderPK != null) || (this.tableRorderPK != null && !this.tableRorderPK.equals(other.tableRorderPK))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableRorder[tableRorderPK=" + tableRorderPK + "]";
	  }

}
