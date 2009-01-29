/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.tables;

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
@Table(name = "ORDER2")
@NamedQueries({@NamedQuery(name = "TableOrder2.findByOrdernr", query = "SELECT o FROM TableOrder2 o where o.tableOrder2PK.ordernr = :ordernr order by o.tableOrder2PK.pos"), @NamedQuery(name = "TableOrder2.deleteByOrdernr", query = "DELETE FROM TableOrder2 o where o.tableOrder2PK.ordernr = :ordernr")})
public class TableOrder2 implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected TableOrder2PK tableOrder2PK;
	@Column(name = "PRISNR", nullable = false)
	private short prisnr;
	@Column(name = "DELLEV", nullable = false)
	private short dellev;
	@Column(name = "ARTNR", nullable = false)
	private String artnr;
	@Column(name = "NAMN")
	private String namn;
	@Column(name = "LEVNR")
	private String levnr;
	@Column(name = "BEST", nullable = false)
	private double best;
	@Column(name = "RAB", nullable = false)
	private double rab;
	@Column(name = "LEV", nullable = false)
	private double lev;
	@Column(name = "TEXT")
	private String text;
	@Column(name = "PRIS", nullable = false)
	private double pris;
	@Column(name = "SUMMA", nullable = false)
	private double summa;
	@Column(name = "KONTO")
	private String konto;
	@Column(name = "NETTO", nullable = false)
	private double netto;
	@Column(name = "ENH")
	private String enh;
	@Column(name = "LEVDAT")
	@Temporal(TemporalType.DATE)
	private Date levdat;
	@Column(name = "UTSKRIVENDATUM")
	@Temporal(TemporalType.DATE)
	private Date utskrivendatum;
	@Column(name = "UTSKRIVENTID")
	@Temporal(TemporalType.TIME)
	private Date utskriventid;
	@Column(name = "STJID", nullable = false)
	private int stjid;

	public TableOrder2() {
	}

	public TableOrder2(int ordernr, short pos) {
		this.tableOrder2PK = new TableOrder2PK(ordernr, pos);
	}

	public TableOrder2PK getTableOrder2PK() {
		return tableOrder2PK;
	}

	public void setTableOrder2PK(TableOrder2PK tableOrder2PK) {
		this.tableOrder2PK = tableOrder2PK;
	}

	public short getPrisnr() {
		return prisnr;
	}

	public void setPrisnr(short prisnr) {
		this.prisnr = prisnr;
	}

	public short getDellev() {
		return dellev;
	}

	public void setDellev(short dellev) {
		this.dellev = dellev;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public String getLevnr() {
		return levnr;
	}

	public void setLevnr(String levnr) {
		this.levnr = levnr;
	}

	public double getBest() {
		return best;
	}

	public void setBest(double best) {
		this.best = best;
	}

	public double getRab() {
		return rab;
	}

	public void setRab(double rab) {
		this.rab = rab;
	}

	public double getLev() {
		return lev;
	}

	public void setLev(double lev) {
		this.lev = lev;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public double getSumma() {
		return summa;
	}

	public void setSumma(double summa) {
		this.summa = summa;
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

	public String getEnh() {
		return enh;
	}

	public void setEnh(String enh) {
		this.enh = enh;
	}

	public Date getLevdat() {
		return levdat;
	}

	public void setLevdat(Date levdat) {
		this.levdat = levdat;
	}

	public Date getUtskrivendatum() {
		return utskrivendatum;
	}

	public void setUtskrivendatum(Date utskrivendatum) {
		this.utskrivendatum = utskrivendatum;
	}

	public Date getUtskriventid() {
		return utskriventid;
	}

	public void setUtskriventid(Date utskriventid) {
		this.utskriventid = utskriventid;
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
		hash += (tableOrder2PK != null ? tableOrder2PK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableOrder2)) {
			return false;
		}
		TableOrder2 other = (TableOrder2) object;
		if ((this.tableOrder2PK == null && other.tableOrder2PK != null) || (this.tableOrder2PK != null && !this.tableOrder2PK.equals(other.tableOrder2PK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableOrder2[tableOrder2PK=" + tableOrder2PK + "]";
	}

}
