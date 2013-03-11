/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.rapporter;

import java.sql.Date;

/**
 *
 * @author Ulf
 */
public class KatalogArtikel {
		private int sortOrder;
		private String artnr;
		private String katalogtext;
		private String enhet;
		private String rabkod;
		private String bildArtNr;
		private Double pris;
		private Double prisStaf1;
		private Double prisStaf2;
		private Double antalStaf1;
		private Double antalStaf2;
		private Double forpackning;
		private Double minSaljpack;
		private Integer fraktvillkor;
		private java.sql.Date utgattdatum;

	public int getSortOrder() {
		return sortOrder;
	}

	public Date getUtgattdatum() {
		return utgattdatum;
	}

	public void setUtgattdatum(Date utgattdatum) {
		this.utgattdatum = utgattdatum;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Double getAntalStaf1() {
		return antalStaf1;
	}

	public void setAntalStaf1(Double antalStaf1) {
		this.antalStaf1 = antalStaf1;
	}

	public Double getAntalStaf2() {
		return antalStaf2;
	}

	public void setAntalStaf2(Double antalStaf2) {
		this.antalStaf2 = antalStaf2;
	}

	public String getArtnr() {
		return artnr;
	}

	public void setArtnr(String artnr) {
		this.artnr = artnr;
	}

	public String getBildArtNr() {
		return bildArtNr;
	}

	public void setBildArtNr(String bildUrl) {
		this.bildArtNr = bildUrl;
	}

	public String getEnhet() {
		return enhet;
	}

	public void setEnhet(String enhet) {
		this.enhet = enhet;
	}

	public Double getForpackning() {
		return forpackning;
	}

	public void setForpackning(Double forpackning) {
		this.forpackning = forpackning;
	}

	public Integer getFraktvillkor() {
		return fraktvillkor;
	}

	public void setFraktvillkor(Integer fraktvillkor) {
		this.fraktvillkor = fraktvillkor;
	}

	public String getKatalogtext() {
		return katalogtext;
	}

	public void setKatalogtext(String katalogtext) {
		this.katalogtext = katalogtext;
	}

	public Double getMinSaljpack() {
		return minSaljpack;
	}

	public void setMinSaljpack(Double minSaljpack) {
		this.minSaljpack = minSaljpack;
	}

	public Double getPris() {
		return pris;
	}

	public void setPris(Double pris) {
		this.pris = pris;
	}

	public Double getPrisStaf1() {
		return prisStaf1;
	}

	public void setPrisStaf1(Double prisStaf1) {
		this.prisStaf1 = prisStaf1;
	}

	public Double getPrisStaf2() {
		return prisStaf2;
	}

	public void setPrisStaf2(Double prisStaf2) {
		this.prisStaf2 = prisStaf2;
	}

	public String getRabkod() {
		return rabkod;
	}

	public void setRabkod(String rabkod) {
		this.rabkod = rabkod;
	}

		
}
