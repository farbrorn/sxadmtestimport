/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

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
@Table(name = "ARTIKEL")
@NamedQueries({@NamedQuery(name = "TableArtikel.findByNummer", query = "SELECT t FROM TableArtikel t WHERE t.nummer = :nummer"), @NamedQuery(name = "TableArtikel.findByNamn", query = "SELECT t FROM TableArtikel t WHERE t.namn = :namn"), @NamedQuery(name = "TableArtikel.findByLev", query = "SELECT t FROM TableArtikel t WHERE t.lev = :lev"), @NamedQuery(name = "TableArtikel.findByBestnr", query = "SELECT t FROM TableArtikel t WHERE t.bestnr = :bestnr"), @NamedQuery(name = "TableArtikel.findByEnhet", query = "SELECT t FROM TableArtikel t WHERE t.enhet = :enhet"), @NamedQuery(name = "TableArtikel.findByUtpris", query = "SELECT t FROM TableArtikel t WHERE t.utpris = :utpris"), @NamedQuery(name = "TableArtikel.findByStafPris1", query = "SELECT t FROM TableArtikel t WHERE t.stafPris1 = :stafPris1"), @NamedQuery(name = "TableArtikel.findByStafPris2", query = "SELECT t FROM TableArtikel t WHERE t.stafPris2 = :stafPris2"), @NamedQuery(name = "TableArtikel.findByStafPris1Dat", query = "SELECT t FROM TableArtikel t WHERE t.stafPris1Dat = :stafPris1Dat"), @NamedQuery(name = "TableArtikel.findByStafPris2Dat", query = "SELECT t FROM TableArtikel t WHERE t.stafPris2Dat = :stafPris2Dat"), @NamedQuery(name = "TableArtikel.findByStafAntal1", query = "SELECT t FROM TableArtikel t WHERE t.stafAntal1 = :stafAntal1"), @NamedQuery(name = "TableArtikel.findByStafAntal2", query = "SELECT t FROM TableArtikel t WHERE t.stafAntal2 = :stafAntal2"), @NamedQuery(name = "TableArtikel.findByInpris", query = "SELECT t FROM TableArtikel t WHERE t.inpris = :inpris"), @NamedQuery(name = "TableArtikel.findByRab", query = "SELECT t FROM TableArtikel t WHERE t.rab = :rab"), @NamedQuery(name = "TableArtikel.findByUtrab", query = "SELECT t FROM TableArtikel t WHERE t.utrab = :utrab"), @NamedQuery(name = "TableArtikel.findByInpPris", query = "SELECT t FROM TableArtikel t WHERE t.inpPris = :inpPris"), @NamedQuery(name = "TableArtikel.findByInpRab", query = "SELECT t FROM TableArtikel t WHERE t.inpRab = :inpRab"), @NamedQuery(name = "TableArtikel.findByInpFraktproc", query = "SELECT t FROM TableArtikel t WHERE t.inpFraktproc = :inpFraktproc"), @NamedQuery(name = "TableArtikel.findByInpValuta", query = "SELECT t FROM TableArtikel t WHERE t.inpValuta = :inpValuta"), @NamedQuery(name = "TableArtikel.findByInpDatum", query = "SELECT t FROM TableArtikel t WHERE t.inpDatum = :inpDatum"), @NamedQuery(name = "TableArtikel.findByKonto", query = "SELECT t FROM TableArtikel t WHERE t.konto = :konto"), @NamedQuery(name = "TableArtikel.findByRabkod", query = "SELECT t FROM TableArtikel t WHERE t.rabkod = :rabkod"), @NamedQuery(name = "TableArtikel.findByKod1", query = "SELECT t FROM TableArtikel t WHERE t.kod1 = :kod1"), @NamedQuery(name = "TableArtikel.findByPrisdatum", query = "SELECT t FROM TableArtikel t WHERE t.prisdatum = :prisdatum"), @NamedQuery(name = "TableArtikel.findByInpdat", query = "SELECT t FROM TableArtikel t WHERE t.inpdat = :inpdat"), @NamedQuery(name = "TableArtikel.findByTbidrag", query = "SELECT t FROM TableArtikel t WHERE t.tbidrag = :tbidrag"), @NamedQuery(name = "TableArtikel.findByRefnr", query = "SELECT t FROM TableArtikel t WHERE t.refnr = :refnr"), @NamedQuery(name = "TableArtikel.findByVikt", query = "SELECT t FROM TableArtikel t WHERE t.vikt = :vikt"), @NamedQuery(name = "TableArtikel.findByVolym", query = "SELECT t FROM TableArtikel t WHERE t.volym = :volym"), @NamedQuery(name = "TableArtikel.findByRordat", query = "SELECT t FROM TableArtikel t WHERE t.rordat = :rordat"), @NamedQuery(name = "TableArtikel.findByStruktnr", query = "SELECT t FROM TableArtikel t WHERE t.struktnr = :struktnr"), @NamedQuery(name = "TableArtikel.findByForpack", query = "SELECT t FROM TableArtikel t WHERE t.forpack = :forpack"), @NamedQuery(name = "TableArtikel.findByKopPack", query = "SELECT t FROM TableArtikel t WHERE t.kopPack = :kopPack"), @NamedQuery(name = "TableArtikel.findByKampfrdat", query = "SELECT t FROM TableArtikel t WHERE t.kampfrdat = :kampfrdat"), @NamedQuery(name = "TableArtikel.findByKamptidat", query = "SELECT t FROM TableArtikel t WHERE t.kamptidat = :kamptidat"), @NamedQuery(name = "TableArtikel.findByKamppris", query = "SELECT t FROM TableArtikel t WHERE t.kamppris = :kamppris"), @NamedQuery(name = "TableArtikel.findByKampprisstaf1", query = "SELECT t FROM TableArtikel t WHERE t.kampprisstaf1 = :kampprisstaf1"), @NamedQuery(name = "TableArtikel.findByKampprisstaf2", query = "SELECT t FROM TableArtikel t WHERE t.kampprisstaf2 = :kampprisstaf2"), @NamedQuery(name = "TableArtikel.findByInpMiljo", query = "SELECT t FROM TableArtikel t WHERE t.inpMiljo = :inpMiljo"), @NamedQuery(name = "TableArtikel.findByInpFrakt", query = "SELECT t FROM TableArtikel t WHERE t.inpFrakt = :inpFrakt"), @NamedQuery(name = "TableArtikel.findByAnvisnr", query = "SELECT t FROM TableArtikel t WHERE t.anvisnr = :anvisnr"), @NamedQuery(name = "TableArtikel.findByStafPris1ny", query = "SELECT t FROM TableArtikel t WHERE t.stafPris1ny = :stafPris1ny"), @NamedQuery(name = "TableArtikel.findByStafPris2ny", query = "SELECT t FROM TableArtikel t WHERE t.stafPris2ny = :stafPris2ny"), @NamedQuery(name = "TableArtikel.findByInprisny", query = "SELECT t FROM TableArtikel t WHERE t.inprisny = :inprisny"), @NamedQuery(name = "TableArtikel.findByInprisnydat", query = "SELECT t FROM TableArtikel t WHERE t.inprisnydat = :inprisnydat"), @NamedQuery(name = "TableArtikel.findByInprisnyrab", query = "SELECT t FROM TableArtikel t WHERE t.inprisnyrab = :inprisnyrab"), @NamedQuery(name = "TableArtikel.findByUtprisny", query = "SELECT t FROM TableArtikel t WHERE t.utprisny = :utprisny"), @NamedQuery(name = "TableArtikel.findByUtprisnyavbokdat", query = "SELECT t FROM TableArtikel t WHERE t.utprisnyavbokdat = :utprisnyavbokdat"), @NamedQuery(name = "TableArtikel.findByUtprisnydat", query = "SELECT t FROM TableArtikel t WHERE t.utprisnydat = :utprisnydat"), @NamedQuery(name = "TableArtikel.findByRsk", query = "SELECT t FROM TableArtikel t WHERE t.rsk = :rsk"), @NamedQuery(name = "TableArtikel.findByEnummer", query = "SELECT t FROM TableArtikel t WHERE t.enummer = :enummer"), @NamedQuery(name = "TableArtikel.findByKampkundartgrp", query = "SELECT t FROM TableArtikel t WHERE t.kampkundartgrp = :kampkundartgrp"), @NamedQuery(name = "TableArtikel.findByKampkundgrp", query = "SELECT t FROM TableArtikel t WHERE t.kampkundgrp = :kampkundgrp"), @NamedQuery(name = "TableArtikel.findByCn8", query = "SELECT t FROM TableArtikel t WHERE t.cn8 = :cn8"), @NamedQuery(name = "TableArtikel.findByFraktvillkor", query = "SELECT t FROM TableArtikel t WHERE t.fraktvillkor = :fraktvillkor"), @NamedQuery(name = "TableArtikel.findByDagspris", query = "SELECT t FROM TableArtikel t WHERE t.dagspris = :dagspris"), @NamedQuery(name = "TableArtikel.findByHindraexport", query = "SELECT t FROM TableArtikel t WHERE t.hindraexport = :hindraexport"), @NamedQuery(name = "TableArtikel.findByUtgattdatum", query = "SELECT t FROM TableArtikel t WHERE t.utgattdatum = :utgattdatum"), @NamedQuery(name = "TableArtikel.findByMinsaljpack", query = "SELECT t FROM TableArtikel t WHERE t.minsaljpack = :minsaljpack"), @NamedQuery(name = "TableArtikel.findByStorpack", query = "SELECT t FROM TableArtikel t WHERE t.storpack = :storpack"), @NamedQuery(name = "TableArtikel.findByPrisgiltighetstid", query = "SELECT t FROM TableArtikel t WHERE t.prisgiltighetstid = :prisgiltighetstid"), @NamedQuery(name = "TableArtikel.findByOnskattb", query = "SELECT t FROM TableArtikel t WHERE t.onskattb = :onskattb"), @NamedQuery(name = "TableArtikel.findByOnskattbstaf1", query = "SELECT t FROM TableArtikel t WHERE t.onskattbstaf1 = :onskattbstaf1"), @NamedQuery(name = "TableArtikel.findByOnskattbstaf2", query = "SELECT t FROM TableArtikel t WHERE t.onskattbstaf2 = :onskattbstaf2"), @NamedQuery(name = "TableArtikel.findBySalda", query = "SELECT t FROM TableArtikel t WHERE t.salda = :salda"), @NamedQuery(name = "TableArtikel.findByDirektlev", query = "SELECT t FROM TableArtikel t WHERE t.direktlev = :direktlev"), @NamedQuery(name = "TableArtikel.findByKatnamn", query = "SELECT t FROM TableArtikel t WHERE t.katnamn = :katnamn"), @NamedQuery(name = "TableArtikel.findByBildartnr", query = "SELECT t FROM TableArtikel t WHERE t.bildartnr = :bildartnr")})
public class TableArtikel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "NUMMER", nullable = false)
	private String nummer;
	@Column(name = "NAMN")
	private String namn;
	@Column(name = "LEV", nullable = false)
	private String lev;
	@Column(name = "BESTNR", nullable = false)
	private String bestnr;
	@Column(name = "ENHET")
	private String enhet;
	@Column(name = "UTPRIS", nullable = false)
	private double utpris;
	@Column(name = "STAF_PRIS1", nullable = false)
	private double stafPris1;
	@Column(name = "STAF_PRIS2", nullable = false)
	private double stafPris2;
	@Column(name = "STAF_PRIS1_DAT")
	@Temporal(TemporalType.DATE)
	private Date stafPris1Dat;
	@Column(name = "STAF_PRIS2_DAT")
	@Temporal(TemporalType.DATE)
	private Date stafPris2Dat;
	@Column(name = "STAF_ANTAL1", nullable = false)
	private double stafAntal1;
	@Column(name = "STAF_ANTAL2", nullable = false)
	private double stafAntal2;
	@Column(name = "INPRIS", nullable = false)
	private double inpris;
	@Column(name = "RAB", nullable = false)
	private double rab;
	@Column(name = "UTRAB", nullable = false)
	private double utrab;
	@Column(name = "INP_PRIS", nullable = false)
	private double inpPris;
	@Column(name = "INP_RAB", nullable = false)
	private double inpRab;
	@Column(name = "INP_FRAKTPROC", nullable = false)
	private double inpFraktproc;
	@Column(name = "INP_VALUTA")
	private String inpValuta;
	@Column(name = "INP_DATUM")
	@Temporal(TemporalType.DATE)
	private Date inpDatum;
	@Column(name = "KONTO")
	private String konto;
	@Column(name = "RABKOD")
	private String rabkod;
	@Column(name = "KOD1")
	private String kod1;
	@Column(name = "PRISDATUM")
	@Temporal(TemporalType.DATE)
	private Date prisdatum;
	@Column(name = "INPDAT")
	@Temporal(TemporalType.DATE)
	private Date inpdat;
	@Column(name = "TBIDRAG", nullable = false)
	private double tbidrag;
	@Column(name = "REFNR")
	private String refnr;
	@Column(name = "VIKT", nullable = false)
	private double vikt;
	@Column(name = "VOLYM", nullable = false)
	private double volym;
	@Column(name = "RORDAT")
	@Temporal(TemporalType.DATE)
	private Date rordat;
	@Column(name = "STRUKTNR")
	private String struktnr;
	@Column(name = "FORPACK", nullable = false)
	private double forpack;
	@Column(name = "KOP_PACK", nullable = false)
	private double kopPack;
	@Column(name = "KAMPFRDAT")
	@Temporal(TemporalType.DATE)
	private Date kampfrdat;
	@Column(name = "KAMPTIDAT")
	@Temporal(TemporalType.DATE)
	private Date kamptidat;
	@Column(name = "KAMPPRIS")
	private Double kamppris;
	@Column(name = "KAMPPRISSTAF1")
	private Double kampprisstaf1;
	@Column(name = "KAMPPRISSTAF2")
	private Double kampprisstaf2;
	@Column(name = "INP_MILJO", nullable = false)
	private double inpMiljo;
	@Column(name = "INP_FRAKT", nullable = false)
	private double inpFrakt;
	@Column(name = "ANVISNR")
	private String anvisnr;
	@Column(name = "STAF_PRIS1NY", nullable = false)
	private double stafPris1ny;
	@Column(name = "STAF_PRIS2NY", nullable = false)
	private double stafPris2ny;
	@Column(name = "INPRISNY", nullable = false)
	private double inprisny;
	@Column(name = "INPRISNYDAT")
	@Temporal(TemporalType.DATE)
	private Date inprisnydat;
	@Column(name = "INPRISNYRAB", nullable = false)
	private double inprisnyrab;
	@Column(name = "UTPRISNY", nullable = false)
	private double utprisny;
	@Column(name = "UTPRISNYAVBOKDAT")
	@Temporal(TemporalType.DATE)
	private Date utprisnyavbokdat;
	@Column(name = "UTPRISNYDAT")
	@Temporal(TemporalType.DATE)
	private Date utprisnydat;
	@Column(name = "RSK")
	private String rsk;
	@Column(name = "ENUMMER")
	private String enummer;
	@Column(name = "KAMPKUNDARTGRP", nullable = false)
	private short kampkundartgrp;
	@Column(name = "KAMPKUNDGRP", nullable = false)
	private short kampkundgrp;
	@Column(name = "CN8")
	private String cn8;
	@Column(name = "FRAKTVILLKOR")
	private Short fraktvillkor;
	@Column(name = "DAGSPRIS")
	private Short dagspris;
	@Column(name = "HINDRAEXPORT")
	private Short hindraexport;
	@Column(name = "UTGATTDATUM")
	@Temporal(TemporalType.DATE)
	private Date utgattdatum;
	@Column(name = "MINSALJPACK")
	private Double minsaljpack;
	@Column(name = "STORPACK")
	private Double storpack;
	@Column(name = "PRISGILTIGHETSTID")
	private Integer prisgiltighetstid;
	@Column(name = "ONSKATTB")
	private Integer onskattb;
	@Column(name = "ONSKATTBSTAF1")
	private Integer onskattbstaf1;
	@Column(name = "ONSKATTBSTAF2")
	private Integer onskattbstaf2;
	@Column(name = "SALDA")
	private Double salda;
	@Column(name = "DIREKTLEV")
	private Short direktlev;
	@Column(name = "KATNAMN")
	private String katnamn;
	@Column(name = "BILDARTNR")
	private String bildartnr;

	public TableArtikel() {
	}

	public TableArtikel(String nummer) {
		this.nummer = nummer;
	}

	public TableArtikel(String nummer, String lev, String bestnr, double utpris, double stafPris1, double stafPris2, double stafAntal1, double stafAntal2, double inpris, double rab, double utrab, double inpPris, double inpRab, double inpFraktproc, double tbidrag, double vikt, double volym, double forpack, double kopPack, double inpMiljo, double inpFrakt, double stafPris1ny, double stafPris2ny, double inprisny, double inprisnyrab, double utprisny, short kampkundartgrp, short kampkundgrp) {
		this.nummer = nummer;
		this.lev = lev;
		this.bestnr = bestnr;
		this.utpris = utpris;
		this.stafPris1 = stafPris1;
		this.stafPris2 = stafPris2;
		this.stafAntal1 = stafAntal1;
		this.stafAntal2 = stafAntal2;
		this.inpris = inpris;
		this.rab = rab;
		this.utrab = utrab;
		this.inpPris = inpPris;
		this.inpRab = inpRab;
		this.inpFraktproc = inpFraktproc;
		this.tbidrag = tbidrag;
		this.vikt = vikt;
		this.volym = volym;
		this.forpack = forpack;
		this.kopPack = kopPack;
		this.inpMiljo = inpMiljo;
		this.inpFrakt = inpFrakt;
		this.stafPris1ny = stafPris1ny;
		this.stafPris2ny = stafPris2ny;
		this.inprisny = inprisny;
		this.inprisnyrab = inprisnyrab;
		this.utprisny = utprisny;
		this.kampkundartgrp = kampkundartgrp;
		this.kampkundgrp = kampkundgrp;
	}

	public String getNummer() {
		return nummer;
	}

	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}

	public String getBestnr() {
		return bestnr;
	}

	public void setBestnr(String bestnr) {
		this.bestnr = bestnr;
	}

	public String getEnhet() {
		return enhet;
	}

	public void setEnhet(String enhet) {
		this.enhet = enhet;
	}

	public double getUtpris() {
		return utpris;
	}

	public void setUtpris(double utpris) {
		this.utpris = utpris;
	}

	public double getStafPris1() {
		return stafPris1;
	}

	public void setStafPris1(double stafPris1) {
		this.stafPris1 = stafPris1;
	}

	public double getStafPris2() {
		return stafPris2;
	}

	public void setStafPris2(double stafPris2) {
		this.stafPris2 = stafPris2;
	}

	public Date getStafPris1Dat() {
		return stafPris1Dat;
	}

	public void setStafPris1Dat(Date stafPris1Dat) {
		this.stafPris1Dat = stafPris1Dat;
	}

	public Date getStafPris2Dat() {
		return stafPris2Dat;
	}

	public void setStafPris2Dat(Date stafPris2Dat) {
		this.stafPris2Dat = stafPris2Dat;
	}

	public double getStafAntal1() {
		return stafAntal1;
	}

	public void setStafAntal1(double stafAntal1) {
		this.stafAntal1 = stafAntal1;
	}

	public double getStafAntal2() {
		return stafAntal2;
	}

	public void setStafAntal2(double stafAntal2) {
		this.stafAntal2 = stafAntal2;
	}

	public double getInpris() {
		return inpris;
	}

	public void setInpris(double inpris) {
		this.inpris = inpris;
	}

	public double getRab() {
		return rab;
	}

	public void setRab(double rab) {
		this.rab = rab;
	}

	public double getUtrab() {
		return utrab;
	}

	public void setUtrab(double utrab) {
		this.utrab = utrab;
	}

	public double getInpPris() {
		return inpPris;
	}

	public void setInpPris(double inpPris) {
		this.inpPris = inpPris;
	}

	public double getInpRab() {
		return inpRab;
	}

	public void setInpRab(double inpRab) {
		this.inpRab = inpRab;
	}

	public double getInpFraktproc() {
		return inpFraktproc;
	}

	public void setInpFraktproc(double inpFraktproc) {
		this.inpFraktproc = inpFraktproc;
	}

	public String getInpValuta() {
		return inpValuta;
	}

	public void setInpValuta(String inpValuta) {
		this.inpValuta = inpValuta;
	}

	public Date getInpDatum() {
		return inpDatum;
	}

	public void setInpDatum(Date inpDatum) {
		this.inpDatum = inpDatum;
	}

	public String getKonto() {
		return konto;
	}

	public void setKonto(String konto) {
		this.konto = konto;
	}

	public String getRabkod() {
		return rabkod;
	}

	public void setRabkod(String rabkod) {
		this.rabkod = rabkod;
	}

	public String getKod1() {
		return kod1;
	}

	public void setKod1(String kod1) {
		this.kod1 = kod1;
	}

	public Date getPrisdatum() {
		return prisdatum;
	}

	public void setPrisdatum(Date prisdatum) {
		this.prisdatum = prisdatum;
	}

	public Date getInpdat() {
		return inpdat;
	}

	public void setInpdat(Date inpdat) {
		this.inpdat = inpdat;
	}

	public double getTbidrag() {
		return tbidrag;
	}

	public void setTbidrag(double tbidrag) {
		this.tbidrag = tbidrag;
	}

	public String getRefnr() {
		return refnr;
	}

	public void setRefnr(String refnr) {
		this.refnr = refnr;
	}

	public double getVikt() {
		return vikt;
	}

	public void setVikt(double vikt) {
		this.vikt = vikt;
	}

	public double getVolym() {
		return volym;
	}

	public void setVolym(double volym) {
		this.volym = volym;
	}

	public Date getRordat() {
		return rordat;
	}

	public void setRordat(Date rordat) {
		this.rordat = rordat;
	}

	public String getStruktnr() {
		return struktnr;
	}

	public void setStruktnr(String struktnr) {
		this.struktnr = struktnr;
	}

	public double getForpack() {
		return forpack;
	}

	public void setForpack(double forpack) {
		this.forpack = forpack;
	}

	public double getKopPack() {
		return kopPack;
	}

	public void setKopPack(double kopPack) {
		this.kopPack = kopPack;
	}

	public Date getKampfrdat() {
		return kampfrdat;
	}

	public void setKampfrdat(Date kampfrdat) {
		this.kampfrdat = kampfrdat;
	}

	public Date getKamptidat() {
		return kamptidat;
	}

	public void setKamptidat(Date kamptidat) {
		this.kamptidat = kamptidat;
	}

	public Double getKamppris() {
		return kamppris;
	}

	public void setKamppris(Double kamppris) {
		this.kamppris = kamppris;
	}

	public Double getKampprisstaf1() {
		return kampprisstaf1;
	}

	public void setKampprisstaf1(Double kampprisstaf1) {
		this.kampprisstaf1 = kampprisstaf1;
	}

	public Double getKampprisstaf2() {
		return kampprisstaf2;
	}

	public void setKampprisstaf2(Double kampprisstaf2) {
		this.kampprisstaf2 = kampprisstaf2;
	}

	public double getInpMiljo() {
		return inpMiljo;
	}

	public void setInpMiljo(double inpMiljo) {
		this.inpMiljo = inpMiljo;
	}

	public double getInpFrakt() {
		return inpFrakt;
	}

	public void setInpFrakt(double inpFrakt) {
		this.inpFrakt = inpFrakt;
	}

	public String getAnvisnr() {
		return anvisnr;
	}

	public void setAnvisnr(String anvisnr) {
		this.anvisnr = anvisnr;
	}

	public double getStafPris1ny() {
		return stafPris1ny;
	}

	public void setStafPris1ny(double stafPris1ny) {
		this.stafPris1ny = stafPris1ny;
	}

	public double getStafPris2ny() {
		return stafPris2ny;
	}

	public void setStafPris2ny(double stafPris2ny) {
		this.stafPris2ny = stafPris2ny;
	}

	public double getInprisny() {
		return inprisny;
	}

	public void setInprisny(double inprisny) {
		this.inprisny = inprisny;
	}

	public Date getInprisnydat() {
		return inprisnydat;
	}

	public void setInprisnydat(Date inprisnydat) {
		this.inprisnydat = inprisnydat;
	}

	public double getInprisnyrab() {
		return inprisnyrab;
	}

	public void setInprisnyrab(double inprisnyrab) {
		this.inprisnyrab = inprisnyrab;
	}

	public double getUtprisny() {
		return utprisny;
	}

	public void setUtprisny(double utprisny) {
		this.utprisny = utprisny;
	}

	public Date getUtprisnyavbokdat() {
		return utprisnyavbokdat;
	}

	public void setUtprisnyavbokdat(Date utprisnyavbokdat) {
		this.utprisnyavbokdat = utprisnyavbokdat;
	}

	public Date getUtprisnydat() {
		return utprisnydat;
	}

	public void setUtprisnydat(Date utprisnydat) {
		this.utprisnydat = utprisnydat;
	}

	public String getRsk() {
		return rsk;
	}

	public void setRsk(String rsk) {
		this.rsk = rsk;
	}

	public String getEnummer() {
		return enummer;
	}

	public void setEnummer(String enummer) {
		this.enummer = enummer;
	}

	public short getKampkundartgrp() {
		return kampkundartgrp;
	}

	public void setKampkundartgrp(short kampkundartgrp) {
		this.kampkundartgrp = kampkundartgrp;
	}

	public short getKampkundgrp() {
		return kampkundgrp;
	}

	public void setKampkundgrp(short kampkundgrp) {
		this.kampkundgrp = kampkundgrp;
	}

	public String getCn8() {
		return cn8;
	}

	public void setCn8(String cn8) {
		this.cn8 = cn8;
	}

	public Short getFraktvillkor() {
		return fraktvillkor;
	}

	public void setFraktvillkor(Short fraktvillkor) {
		this.fraktvillkor = fraktvillkor;
	}

	public Short getDagspris() {
		return dagspris;
	}

	public void setDagspris(Short dagspris) {
		this.dagspris = dagspris;
	}

	public Short getHindraexport() {
		return hindraexport;
	}

	public void setHindraexport(Short hindraexport) {
		this.hindraexport = hindraexport;
	}

	public Date getUtgattdatum() {
		return utgattdatum;
	}

	public void setUtgattdatum(Date utgattdatum) {
		this.utgattdatum = utgattdatum;
	}

	public Double getMinsaljpack() {
		return minsaljpack;
	}

	public void setMinsaljpack(Double minsaljpack) {
		this.minsaljpack = minsaljpack;
	}

	public Double getStorpack() {
		return storpack;
	}

	public void setStorpack(Double storpack) {
		this.storpack = storpack;
	}

	public Integer getPrisgiltighetstid() {
		return prisgiltighetstid;
	}

	public void setPrisgiltighetstid(Integer prisgiltighetstid) {
		this.prisgiltighetstid = prisgiltighetstid;
	}

	public Integer getOnskattb() {
		return onskattb;
	}

	public void setOnskattb(Integer onskattb) {
		this.onskattb = onskattb;
	}

	public Integer getOnskattbstaf1() {
		return onskattbstaf1;
	}

	public void setOnskattbstaf1(Integer onskattbstaf1) {
		this.onskattbstaf1 = onskattbstaf1;
	}

	public Integer getOnskattbstaf2() {
		return onskattbstaf2;
	}

	public void setOnskattbstaf2(Integer onskattbstaf2) {
		this.onskattbstaf2 = onskattbstaf2;
	}

	public Double getSalda() {
		return salda;
	}

	public void setSalda(Double salda) {
		this.salda = salda;
	}

	public Short getDirektlev() {
		return direktlev;
	}

	public void setDirektlev(Short direktlev) {
		this.direktlev = direktlev;
	}

	public String getKatnamn() {
		return katnamn;
	}

	public void setKatnamn(String katnamn) {
		this.katnamn = katnamn;
	}

	public String getBildartnr() {
		return bildartnr;
	}

	public void setBildartnr(String bildartnr) {
		this.bildartnr = bildartnr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (nummer != null ? nummer.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableArtikel)) {
			return false;
		}
		TableArtikel other = (TableArtikel) object;
		if ((this.nummer == null && other.nummer != null) || (this.nummer != null && !this.nummer.equals(other.nummer))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableArtikel[nummer=" + nummer + "]";
	}

}
