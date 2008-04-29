/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "LEV")
@NamedQueries({@NamedQuery(name = "TableLev.findByNummer", query = "SELECT t FROM TableLev t WHERE t.nummer = :nummer")})
public class TableLev implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "NUMMER", nullable = false)
	private String nummer;
	@Column(name = "NAMN", nullable = false)
	private String namn;
	@Column(name = "ADR1")
	private String adr1;
	@Column(name = "ADR2")
	private String adr2;
	@Column(name = "ADR3")
	private String adr3;
	@Column(name = "TEL")
	private String tel;
	@Column(name = "BILTEL")
	private String biltel;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "HEMSIDA")
	private String hemsida;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "INKOPARE", nullable = false)
	private String inkopare;
	@Column(name = "REF")
	private String ref;
	@Column(name = "REFADR1")
	private String refadr1;
	@Column(name = "REFADR2")
	private String refadr2;
	@Column(name = "REFADR3")
	private String refadr3;
	@Column(name = "REFTEL")
	private String reftel;
	@Column(name = "REFBILTEL")
	private String refbiltel;
	@Column(name = "REFFAX")
	private String reffax;
	@Column(name = "REFHEMSIDA")
	private String refhemsida;
	@Column(name = "REFEMAIL")
	private String refemail;
	@Column(name = "KTID", nullable = false)
	private short ktid;
	@Column(name = "FRAKTFRITT", nullable = false)
	private double fraktfritt;
	@Column(name = "MOTTAGARFRAKT", nullable = false)
	private short mottagarfrakt;
	@Column(name = "LEVVILLKOR1")
	private String levvillkor1;
	@Column(name = "LEVVILLKOR2")
	private String levvillkor2;
	@Column(name = "LEVVILLKOR3")
	private String levvillkor3;
	@Column(name = "LEVBESTMEDD1")
	private String levbestmedd1;
	@Column(name = "LEVBESTMEDD2")
	private String levbestmedd2;
	@Column(name = "LEVBESTMEDD3")
	private String levbestmedd3;
	@Column(name = "BESTEJPRIS", nullable = false)
	private short bestejpris;
	@Column(name = "VALUTA")
	private String valuta;
	@Column(name = "TOT", nullable = false)
	private double tot;
	@Column(name = "OBET", nullable = false)
	private double obet;
	@Column(name = "POST")
	private String post;
	@Column(name = "BANK")
	private String bank;
	@Column(name = "KNUMMER")
	private String knummer;
	@Column(name = "FRAKT", nullable = false)
	private double frakt;
	@Column(name = "ANT1")
	private String ant1;
	@Column(name = "ANT2")
	private String ant2;
	@Column(name = "ANT3")
	private String ant3;
	@Column(name = "ANT")
	private String ant;
	@Column(name = "LANDSKOD")
	private String landskod;
	@Column(name = "EMAILORDER1")
	private String emailorder1;
	@Column(name = "EMAILORDER2")
	private String emailorder2;

	public TableLev() {
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

	public String getAdr1() {
		return adr1;
	}

	public void setAdr1(String adr1) {
		this.adr1 = adr1;
	}

	public String getAdr2() {
		return adr2;
	}

	public void setAdr2(String adr2) {
		this.adr2 = adr2;
	}

	public String getAdr3() {
		return adr3;
	}

	public void setAdr3(String adr3) {
		this.adr3 = adr3;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBiltel() {
		return biltel;
	}

	public void setBiltel(String biltel) {
		this.biltel = biltel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHemsida() {
		return hemsida;
	}

	public void setHemsida(String hemsida) {
		this.hemsida = hemsida;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInkopare() {
		return inkopare;
	}

	public void setInkopare(String inkopare) {
		this.inkopare = inkopare;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRefadr1() {
		return refadr1;
	}

	public void setRefadr1(String refadr1) {
		this.refadr1 = refadr1;
	}

	public String getRefadr2() {
		return refadr2;
	}

	public void setRefadr2(String refadr2) {
		this.refadr2 = refadr2;
	}

	public String getRefadr3() {
		return refadr3;
	}

	public void setRefadr3(String refadr3) {
		this.refadr3 = refadr3;
	}

	public String getReftel() {
		return reftel;
	}

	public void setReftel(String reftel) {
		this.reftel = reftel;
	}

	public String getRefbiltel() {
		return refbiltel;
	}

	public void setRefbiltel(String refbiltel) {
		this.refbiltel = refbiltel;
	}

	public String getReffax() {
		return reffax;
	}

	public void setReffax(String reffax) {
		this.reffax = reffax;
	}

	public String getRefhemsida() {
		return refhemsida;
	}

	public void setRefhemsida(String refhemsida) {
		this.refhemsida = refhemsida;
	}

	public String getRefemail() {
		return refemail;
	}

	public void setRefemail(String refemail) {
		this.refemail = refemail;
	}

	public short getKtid() {
		return ktid;
	}

	public void setKtid(short ktid) {
		this.ktid = ktid;
	}

	public double getFraktfritt() {
		return fraktfritt;
	}

	public void setFraktfritt(double fraktfritt) {
		this.fraktfritt = fraktfritt;
	}

	public short getMottagarfrakt() {
		return mottagarfrakt;
	}

	public void setMottagarfrakt(short mottagarfrakt) {
		this.mottagarfrakt = mottagarfrakt;
	}

	public String getLevvillkor1() {
		return levvillkor1;
	}

	public void setLevvillkor1(String levvillkor1) {
		this.levvillkor1 = levvillkor1;
	}

	public String getLevvillkor2() {
		return levvillkor2;
	}

	public void setLevvillkor2(String levvillkor2) {
		this.levvillkor2 = levvillkor2;
	}

	public String getLevvillkor3() {
		return levvillkor3;
	}

	public void setLevvillkor3(String levvillkor3) {
		this.levvillkor3 = levvillkor3;
	}

	public String getLevbestmedd1() {
		return levbestmedd1;
	}

	public void setLevbestmedd1(String levbestmedd1) {
		this.levbestmedd1 = levbestmedd1;
	}

	public String getLevbestmedd2() {
		return levbestmedd2;
	}

	public void setLevbestmedd2(String levbestmedd2) {
		this.levbestmedd2 = levbestmedd2;
	}

	public String getLevbestmedd3() {
		return levbestmedd3;
	}

	public void setLevbestmedd3(String levbestmedd3) {
		this.levbestmedd3 = levbestmedd3;
	}

	public short getBestejpris() {
		return bestejpris;
	}

	public void setBestejpris(short bestejpris) {
		this.bestejpris = bestejpris;
	}

	public String getValuta() {
		return valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
	}

	public double getTot() {
		return tot;
	}

	public void setTot(double tot) {
		this.tot = tot;
	}

	public double getObet() {
		return obet;
	}

	public void setObet(double obet) {
		this.obet = obet;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getKnummer() {
		return knummer;
	}

	public void setKnummer(String knummer) {
		this.knummer = knummer;
	}

	public double getFrakt() {
		return frakt;
	}

	public void setFrakt(double frakt) {
		this.frakt = frakt;
	}

	public String getAnt1() {
		return ant1;
	}

	public void setAnt1(String ant1) {
		this.ant1 = ant1;
	}

	public String getAnt2() {
		return ant2;
	}

	public void setAnt2(String ant2) {
		this.ant2 = ant2;
	}

	public String getAnt3() {
		return ant3;
	}

	public void setAnt3(String ant3) {
		this.ant3 = ant3;
	}

	public String getAnt() {
		return ant;
	}

	public void setAnt(String ant) {
		this.ant = ant;
	}

	public String getLandskod() {
		return landskod;
	}

	public void setLandskod(String landskod) {
		this.landskod = landskod;
	}

	public String getEmailorder1() {
		return emailorder1;
	}

	public void setEmailorder1(String emailorder1) {
		this.emailorder1 = emailorder1;
	}

	public String getEmailorder2() {
		return emailorder2;
	}

	public void setEmailorder2(String emailorder2) {
		this.emailorder2 = emailorder2;
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
		if (!(object instanceof TableLev)) {
			return false;
		}
		TableLev other = (TableLev) object;
		if ((this.nummer == null && other.nummer != null) || (this.nummer != null && !this.nummer.equals(other.nummer))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableLev[nummer=" + nummer + "]";
	}

}
