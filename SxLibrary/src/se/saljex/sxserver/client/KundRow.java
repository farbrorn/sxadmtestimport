package se.saljex.sxserver.client;

import java.io.Serializable;
import javax.persistence.*;



/**
 *
 * @author Ulf
 */
@Entity
@Table(name = "KUND")
@NamedQueries({})
public class KundRow implements Serializable {
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
	@Column(name = "LNAMN")
	private String lnamn;
	@Column(name = "LADR2")
	private String ladr2;
	@Column(name = "LADR3")
	private String ladr3;
	@Column(name = "REF")
	private String ref;
	@Column(name = "SALJARE", nullable = false)
	private String saljare;
	@Column(name = "TEL")
	private String tel;
	@Column(name = "BILTEL")
	private String biltel;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "EMAIL")
	private String email;

	public KundRow() {
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

	public String getLnamn() {
		return lnamn;
	}

	public void setLnamn(String lnamn) {
		this.lnamn = lnamn;
	}

	public String getLadr2() {
		return ladr2;
	}

	public void setLadr2(String ladr2) {
		this.ladr2 = ladr2;
	}

	public String getLadr3() {
		return ladr3;
	}

	public void setLadr3(String ladr3) {
		this.ladr3 = ladr3;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getSaljare() {
		return saljare;
	}

	public void setSaljare(String saljare) {
		this.saljare = saljare;
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




}
