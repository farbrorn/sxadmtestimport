/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ulf
 */
@Entity
@Table(name = "statistik")
@NamedQueries({
	@NamedQuery(name = "TableStatistik.findAll", query = "SELECT t FROM TableStatistik t")})
public class TableStatistik implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableStatistikPK tableStatistikPK;
	@Basic(optional = false)
   @Column(name = "fak_netto")
	private double fakNetto;
	@Basic(optional = false)
   @Column(name = "fak_moms")
	private double fakMoms;
	@Basic(optional = false)
   @Column(name = "fak_attbetala")
	private double fakAttbetala;
	@Basic(optional = false)
   @Column(name = "fak_innetto")
	private double fakInnetto;
	@Basic(optional = false)
   @Column(name = "fak_antal")
	private short fakAntal;
	@Basic(optional = false)
   @Column(name = "fak_rantatot")
	private double fakRantatot;
	@Basic(optional = false)
   @Column(name = "fak_rantaantal")
	private short fakRantaantal;
	@Basic(optional = false)
   @Column(name = "kun_betalt")
	private double kunBetalt;
	@Basic(optional = false)
   @Column(name = "kun_betaltant")
	private short kunBetaltant;
	@Basic(optional = false)
   @Column(name = "kun_brhog")
	private double kunBrhog;
	@Basic(optional = false)
   @Column(name = "kun_brhogant")
	private short kunBrhogant;
	@Basic(optional = false)
   @Column(name = "kun_brlag")
	private double kunBrlag;
	@Basic(optional = false)
   @Column(name = "kun_brlagant")
	private short kunBrlagant;
	@Basic(optional = false)
   @Column(name = "kun_bejreg")
	private double kunBejreg;
	@Basic(optional = false)
   @Column(name = "kun_bejregant")
	private short kunBejregant;
	@Basic(optional = false)
   @Column(name = "kun_rankost")
	private double kunRankost;
	@Basic(optional = false)
   @Column(name = "kun_rankostant")
	private short kunRankostant;
	@Basic(optional = false)
   @Column(name = "lev_attbet")
	private double levAttbet;
	@Basic(optional = false)
   @Column(name = "lev_attbetant")
	private short levAttbetant;
	@Basic(optional = false)
   @Column(name = "lev_ranta")
	private double levRanta;
	@Basic(optional = false)
   @Column(name = "lev_rantaantal")
	private short levRantaantal;
	@Basic(optional = false)
   @Column(name = "lev_varu")
	private double levVaru;
	@Basic(optional = false)
   @Column(name = "lev_varuantal")
	private short levVaruantal;
	@Basic(optional = false)
   @Column(name = "lev_betalt")
	private double levBetalt;
	@Basic(optional = false)
   @Column(name = "lev_betaltant")
	private short levBetaltant;

	public TableStatistik() {
	}

	public TableStatistik(TableStatistikPK tableStatistikPK) {
		this.tableStatistikPK = tableStatistikPK;
	}


	public TableStatistik(short ar, short man) {
		this.tableStatistikPK = new TableStatistikPK(ar, man);
	}

	public TableStatistikPK getTableStatistikPK() {
		return tableStatistikPK;
	}

	public void setTableStatistikPK(TableStatistikPK tableStatistikPK) {
		this.tableStatistikPK = tableStatistikPK;
	}

	public double getFakNetto() {
		return fakNetto;
	}

	public void setFakNetto(double fakNetto) {
		this.fakNetto = fakNetto;
	}

	public double getFakMoms() {
		return fakMoms;
	}

	public void setFakMoms(double fakMoms) {
		this.fakMoms = fakMoms;
	}

	public double getFakAttbetala() {
		return fakAttbetala;
	}

	public void setFakAttbetala(double fakAttbetala) {
		this.fakAttbetala = fakAttbetala;
	}

	public double getFakInnetto() {
		return fakInnetto;
	}

	public void setFakInnetto(double fakInnetto) {
		this.fakInnetto = fakInnetto;
	}

	public short getFakAntal() {
		return fakAntal;
	}

	public void setFakAntal(short fakAntal) {
		this.fakAntal = fakAntal;
	}

	public double getFakRantatot() {
		return fakRantatot;
	}

	public void setFakRantatot(double fakRantatot) {
		this.fakRantatot = fakRantatot;
	}

	public short getFakRantaantal() {
		return fakRantaantal;
	}

	public void setFakRantaantal(short fakRantaantal) {
		this.fakRantaantal = fakRantaantal;
	}

	public double getKunBetalt() {
		return kunBetalt;
	}

	public void setKunBetalt(double kunBetalt) {
		this.kunBetalt = kunBetalt;
	}

	public short getKunBetaltant() {
		return kunBetaltant;
	}

	public void setKunBetaltant(short kunBetaltant) {
		this.kunBetaltant = kunBetaltant;
	}

	public double getKunBrhog() {
		return kunBrhog;
	}

	public void setKunBrhog(double kunBrhog) {
		this.kunBrhog = kunBrhog;
	}

	public short getKunBrhogant() {
		return kunBrhogant;
	}

	public void setKunBrhogant(short kunBrhogant) {
		this.kunBrhogant = kunBrhogant;
	}

	public double getKunBrlag() {
		return kunBrlag;
	}

	public void setKunBrlag(double kunBrlag) {
		this.kunBrlag = kunBrlag;
	}

	public short getKunBrlagant() {
		return kunBrlagant;
	}

	public void setKunBrlagant(short kunBrlagant) {
		this.kunBrlagant = kunBrlagant;
	}

	public double getKunBejreg() {
		return kunBejreg;
	}

	public void setKunBejreg(double kunBejreg) {
		this.kunBejreg = kunBejreg;
	}

	public short getKunBejregant() {
		return kunBejregant;
	}

	public void setKunBejregant(short kunBejregant) {
		this.kunBejregant = kunBejregant;
	}

	public double getKunRankost() {
		return kunRankost;
	}

	public void setKunRankost(double kunRankost) {
		this.kunRankost = kunRankost;
	}

	public short getKunRankostant() {
		return kunRankostant;
	}

	public void setKunRankostant(short kunRankostant) {
		this.kunRankostant = kunRankostant;
	}

	public double getLevAttbet() {
		return levAttbet;
	}

	public void setLevAttbet(double levAttbet) {
		this.levAttbet = levAttbet;
	}

	public short getLevAttbetant() {
		return levAttbetant;
	}

	public void setLevAttbetant(short levAttbetant) {
		this.levAttbetant = levAttbetant;
	}

	public double getLevRanta() {
		return levRanta;
	}

	public void setLevRanta(double levRanta) {
		this.levRanta = levRanta;
	}

	public short getLevRantaantal() {
		return levRantaantal;
	}

	public void setLevRantaantal(short levRantaantal) {
		this.levRantaantal = levRantaantal;
	}

	public double getLevVaru() {
		return levVaru;
	}

	public void setLevVaru(double levVaru) {
		this.levVaru = levVaru;
	}

	public short getLevVaruantal() {
		return levVaruantal;
	}

	public void setLevVaruantal(short levVaruantal) {
		this.levVaruantal = levVaruantal;
	}

	public double getLevBetalt() {
		return levBetalt;
	}

	public void setLevBetalt(double levBetalt) {
		this.levBetalt = levBetalt;
	}

	public short getLevBetaltant() {
		return levBetaltant;
	}

	public void setLevBetaltant(short levBetaltant) {
		this.levBetaltant = levBetaltant;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (tableStatistikPK != null ? tableStatistikPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableStatistik)) {
			return false;
		}
		TableStatistik other = (TableStatistik) object;
		if ((this.tableStatistikPK == null && other.tableStatistikPK != null) || (this.tableStatistikPK != null && !this.tableStatistikPK.equals(other.tableStatistikPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.tables.TableStatistik[tableStatistikPK=" + tableStatistikPK + "]";
	}

}
