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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "BETJOUR")
@NamedQueries({@NamedQuery(name = "TableBetjour.findByRantfakt", query = "SELECT t FROM TableBetjour t WHERE t.rantfakt = :rantfakt"), @NamedQuery(name = "TableBetjour.findByFaktnr", query = "SELECT t FROM TableBetjour t WHERE t.tableBetjourPK.faktnr = :faktnr"), @NamedQuery(name = "TableBetjour.findByKundnr", query = "SELECT t FROM TableBetjour t WHERE t.kundnr = :kundnr"), @NamedQuery(name = "TableBetjour.findByNamn", query = "SELECT t FROM TableBetjour t WHERE t.namn = :namn"), @NamedQuery(name = "TableBetjour.findByBet", query = "SELECT t FROM TableBetjour t WHERE t.bet = :bet"), @NamedQuery(name = "TableBetjour.findByBetdat", query = "SELECT t FROM TableBetjour t WHERE t.betdat = :betdat"), @NamedQuery(name = "TableBetjour.findByBetsatt", query = "SELECT t FROM TableBetjour t WHERE t.tableBetjourPK.betsatt = :betsatt"), @NamedQuery(name = "TableBetjour.findByBonsumma", query = "SELECT t FROM TableBetjour t WHERE t.bonsumma = :bonsumma"), @NamedQuery(name = "TableBetjour.findByTallopnr", query = "SELECT t FROM TableBetjour t WHERE t.tableBetjourPK.tallopnr = :tallopnr"), @NamedQuery(name = "TableBetjour.findByTaldatum", query = "SELECT t FROM TableBetjour t WHERE t.tableBetjourPK.taldatum = :taldatum"), @NamedQuery(name = "TableBetjour.findByAr", query = "SELECT t FROM TableBetjour t WHERE t.ar = :ar"), @NamedQuery(name = "TableBetjour.findByMan", query = "SELECT t FROM TableBetjour t WHERE t.man = :man"), @NamedQuery(name = "TableBetjour.findByPantsatt", query = "SELECT t FROM TableBetjour t WHERE t.pantsatt = :pantsatt"), @NamedQuery(name = "TableBetjour.findByBetsattkonto", query = "SELECT t FROM TableBetjour t WHERE t.betsattkonto = :betsattkonto"), @NamedQuery(name = "TableBetjour.findByInkassostatus", query = "SELECT t FROM TableBetjour t WHERE t.inkassostatus = :inkassostatus"), @NamedQuery(name = "TableBetjour.findByFelbettyp", query = "SELECT t FROM TableBetjour t WHERE t.felbettyp = :felbettyp"), @NamedQuery(name = "TableBetjour.findByFelbetavbokaddatum", query = "SELECT t FROM TableBetjour t WHERE t.felbetavbokaddatum = :felbetavbokaddatum")})
public class TableBetjour implements Serializable {
	  private static final long serialVersionUID = 1L;
	  @EmbeddedId
	  protected TableBetjourPK tableBetjourPK;
	  @Column(name = "RANTFAKT")
	  private Short rantfakt;
	  @Column(name = "KUNDNR", nullable = false)
	  private String kundnr;
	  @Column(name = "NAMN")
	  private String namn;
	  @Column(name = "BET", nullable = false)
	  private double bet;
	  @Column(name = "BETDAT", nullable = false)
	  @Temporal(TemporalType.DATE)
	  private Date betdat;
	  @Column(name = "BONSUMMA", nullable = false)
	  private double bonsumma;
	  @Column(name = "AR", nullable = false)
	  private short ar;
	  @Column(name = "MAN", nullable = false)
	  private short man;
	  @Column(name = "PANTSATT")
	  private Short pantsatt;
	  @Column(name = "BETSATTKONTO")
	  private Short betsattkonto;
	  @Column(name = "INKASSOSTATUS")
	  private String inkassostatus;
	  @Column(name = "FELBETTYP")
	  private String felbettyp;
	  @Column(name = "FELBETAVBOKADDATUM")
	  @Temporal(TemporalType.DATE)
	  private Date felbetavbokaddatum;

	  public TableBetjour() {
	  }

	  public TableBetjour(TableBetjourPK tableBetjourPK) {
			 this.tableBetjourPK = tableBetjourPK;
	  }

	  public TableBetjour(TableBetjourPK tableBetjourPK, String kundnr, double bet, Date betdat, double bonsumma, short ar, short man) {
			 this.tableBetjourPK = tableBetjourPK;
			 this.kundnr = kundnr;
			 this.bet = bet;
			 this.betdat = betdat;
			 this.bonsumma = bonsumma;
			 this.ar = ar;
			 this.man = man;
	  }

	  public TableBetjour(int faktnr, char betsatt, int tallopnr, Date taldatum) {
			 this.tableBetjourPK = new TableBetjourPK(faktnr, betsatt, tallopnr, taldatum);
	  }

	  public TableBetjourPK getTableBetjourPK() {
			 return tableBetjourPK;
	  }

	  public void setTableBetjourPK(TableBetjourPK tableBetjourPK) {
			 this.tableBetjourPK = tableBetjourPK;
	  }

	  public Short getRantfakt() {
			 return rantfakt;
	  }

	  public void setRantfakt(Short rantfakt) {
			 this.rantfakt = rantfakt;
	  }

	  public String getKundnr() {
			 return kundnr;
	  }

	  public void setKundnr(String kundnr) {
			 this.kundnr = kundnr;
	  }

	  public String getNamn() {
			 return namn;
	  }

	  public void setNamn(String namn) {
			 this.namn = namn;
	  }

	  public double getBet() {
			 return bet;
	  }

	  public void setBet(double bet) {
			 this.bet = bet;
	  }

	  public Date getBetdat() {
			 return betdat;
	  }

	  public void setBetdat(Date betdat) {
			 this.betdat = betdat;
	  }

	  public double getBonsumma() {
			 return bonsumma;
	  }

	  public void setBonsumma(double bonsumma) {
			 this.bonsumma = bonsumma;
	  }

	  public short getAr() {
			 return ar;
	  }

	  public void setAr(short ar) {
			 this.ar = ar;
	  }

	  public short getMan() {
			 return man;
	  }

	  public void setMan(short man) {
			 this.man = man;
	  }

	  public Short getPantsatt() {
			 return pantsatt;
	  }

	  public void setPantsatt(Short pantsatt) {
			 this.pantsatt = pantsatt;
	  }

	  public Short getBetsattkonto() {
			 return betsattkonto;
	  }

	  public void setBetsattkonto(Short betsattkonto) {
			 this.betsattkonto = betsattkonto;
	  }

	  public String getInkassostatus() {
			 return inkassostatus;
	  }

	  public void setInkassostatus(String inkassostatus) {
			 this.inkassostatus = inkassostatus;
	  }

	  public String getFelbettyp() {
			 return felbettyp;
	  }

	  public void setFelbettyp(String felbettyp) {
			 this.felbettyp = felbettyp;
	  }

	  public Date getFelbetavbokaddatum() {
			 return felbetavbokaddatum;
	  }

	  public void setFelbetavbokaddatum(Date felbetavbokaddatum) {
			 this.felbetavbokaddatum = felbetavbokaddatum;
	  }

	  @Override
	  public int hashCode() {
			 int hash = 0;
			 hash += (tableBetjourPK != null ? tableBetjourPK.hashCode() : 0);
			 return hash;
	  }

	  @Override
	  public boolean equals(Object object) {
			 // TODO: Warning - this method won't work in the case the id fields are not set
			 if (!(object instanceof TableBetjour)) {
					return false;
			 }
			 TableBetjour other = (TableBetjour) object;
			 if ((this.tableBetjourPK == null && other.tableBetjourPK != null) || (this.tableBetjourPK != null && !this.tableBetjourPK.equals(other.tableBetjourPK))) {
					return false;
			 }
			 return true;
	  }

	  @Override
	  public String toString() {
			 return "se.saljex.sxserver.TableBetjour[tableBetjourPK=" + tableBetjourPK + "]";
	  }

}
