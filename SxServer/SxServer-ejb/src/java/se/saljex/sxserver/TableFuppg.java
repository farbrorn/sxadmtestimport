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
@Table(name = "FUPPG")
@NamedQueries({@NamedQuery(name = "TableFuppg.find", query = "SELECT t FROM TableFuppg t")})
public class TableFuppg implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "DROJ", nullable = false)
	private double droj;
	@Column(name = "RANTKTID", nullable = false)
	private int rantktid;
	@Column(name = "TXT1")
	private String txt1;
	@Column(name = "TXT2")
	private String txt2;
	@Column(name = "TXT3")
	private String txt3;
	@Column(name = "TXT4")
	private String txt4;
	@Column(name = "TXT5")
	private String txt5;
	@Column(name = "KRANGEL", nullable = false)
	private short krangel;
	@Column(name = "SYSDAT", nullable = false)
	private short sysdat;
	@Column(name = "BONUS", nullable = false)
	private short bonus;
	@Column(name = "BONUSPROC1", nullable = false)
	private short bonusproc1;
	@Column(name = "BONUSPROC2", nullable = false)
	private short bonusproc2;
	@Column(name = "BON_TID", nullable = false)
	private short bonTid;
	@Id
	@Column(name = "NAMN", nullable = false)
	private String namn;
	@Column(name = "FIKON")
	private Double fikon;
	@Column(name = "ADR1")
	private String adr1;
	@Column(name = "ADR2")
	private String adr2;
	@Column(name = "ADR3")
	private String adr3;
	@Column(name = "LEVADR1")
	private String levadr1;
	@Column(name = "LEVADR2")
	private String levadr2;
	@Column(name = "LEVADR3")
	private String levadr3;
	@Column(name = "POSTGIRO")
	private String postgiro;
	@Column(name = "BANKGIRO")
	private String bankgiro;
	@Column(name = "REGNR")
	private String regnr;
	@Column(name = "SATE")
	private String sate;
	@Column(name = "TEL")
	private String tel;
	@Column(name = "FAX")
	private String fax;
	@Column(name = "MOBIL")
	private String mobil;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "HEMSIDA")
	private String hemsida;
	@Column(name = "BEST_FRAKTBOLAG1")
	private String bestFraktbolag1;
	@Column(name = "BEST_FRAKTBOLAG2")
	private String bestFraktbolag2;
	@Column(name = "BEST_FRAKTBOLAG3")
	private String bestFraktbolag3;
	@Column(name = "BEST_PALLREG1")
	private String bestPallreg1;
	@Column(name = "BEST_PALLREG2")
	private String bestPallreg2;
	@Column(name = "BEST_PALLREG3")
	private String bestPallreg3;
	@Column(name = "MOMS1", nullable = false)
	private double moms1;
	@Column(name = "MOMS2", nullable = false)
	private double moms2;
	@Column(name = "MOMS3", nullable = false)
	private double moms3;
	@Column(name = "MOMS1K", nullable = false)
	private String moms1k;
	@Column(name = "MOMS2K", nullable = false)
	private String moms2k;
	@Column(name = "MOMS3K", nullable = false)
	private String moms3k;
	@Column(name = "MOMS1K2", nullable = false)
	private String moms1k2;
	@Column(name = "MOMS2K2", nullable = false)
	private String moms2k2;
	@Column(name = "MOMS3K2", nullable = false)
	private String moms3k2;
	@Column(name = "VARUK", nullable = false)
	private String varuk;
	@Column(name = "LEVK", nullable = false)
	private String levk;
	@Column(name = "KUNDFK", nullable = false)
	private String kundfk;
	@Column(name = "KASSA", nullable = false)
	private String kassa;
	@Column(name = "BANK", nullable = false)
	private String bank;
	@Column(name = "POST", nullable = false)
	private String post;
	@Column(name = "ORUTK", nullable = false)
	private String orutk;
	@Column(name = "RANTAK", nullable = false)
	private String rantak;
	@Column(name = "BON_TEXT1")
	private String bonText1;
	@Column(name = "BON_TEXT2")
	private String bonText2;
	@Column(name = "BON_TEXT3")
	private String bonText3;
	@Column(name = "BON_TEXT4")
	private String bonText4;
	@Column(name = "BON_TEXT5")
	private String bonText5;
	@Column(name = "EB_TEXT1")
	private String ebText1;
	@Column(name = "EB_TEXT2")
	private String ebText2;
	@Column(name = "EB_TEXT3")
	private String ebText3;
	@Column(name = "EB_TEXT4")
	private String ebText4;
	@Column(name = "EB_TEXT5")
	private String ebText5;
	@Column(name = "TEMP_TEXT1")
	private String tempText1;
	@Column(name = "TEMP_TEXT2")
	private String tempText2;
	@Column(name = "TEMP_TEXT3")
	private String tempText3;
	@Column(name = "TEMP_TEXT4")
	private String tempText4;
	@Column(name = "TEMP_TEXT5")
	private String tempText5;
	@Column(name = "TEMP_TEXT")
	private Short tempText;
	@Column(name = "FAK_B_TEXT1")
	private String fakBText1;
	@Column(name = "FAK_B_TEXT2")
	private String fakBText2;
	@Column(name = "FAK_B_TEXT3")
	private String fakBText3;
	@Column(name = "FAK_B_TEXT4")
	private String fakBText4;
	@Column(name = "FAK_B_TEXT5")
	private String fakBText5;
	@Column(name = "FAKTORTEXT1")
	private String faktortext1;
	@Column(name = "FAKTORTEXT2")
	private String faktortext2;
	@Column(name = "FAKTORTEXT3")
	private String faktortext3;
	@Column(name = "PRN_ORDER")
	private String prnOrder;
	@Column(name = "PRN_FAKTURA")
	private String prnFaktura;
	@Column(name = "PRN_BANK")
	private String prnBank;
	@Column(name = "PRN_POST")
	private String prnPost;
	@Column(name = "PRN_OVRIGT")
	private String prnOvrigt;
	@Column(name = "PRN_FAX")
	private String prnFax;
	@Column(name = "PRN_EMAIL")
	private String prnEmail;
	@Column(name = "PRN_ORDERKOPIOR")
	private Short prnOrderkopior;
	@Column(name = "PRN_FAKTURAKOPIOR")
	private Short prnFakturakopior;
	@Column(name = "PRN_POSTKOPIOR")
	private Short prnPostkopior;
	@Column(name = "PRN_BANKKOPIOR")
	private Short prnBankkopior;
	@Column(name = "PRN_OVRIGTKOPIOR")
	private Short prnOvrigtkopior;
	@Column(name = "PRN_FAXKOPIOR")
	private Short prnFaxkopior;
	@Column(name = "PRN_EMAILKOPIOR")
	private Short prnEmailkopior;
	@Column(name = "POMINDAGAR")
	private Short pomindagar;
	@Column(name = "BEST_TEXT1")
	private String bestText1;
	@Column(name = "BEST_TEXT2")
	private String bestText2;
	@Column(name = "BEST_TEXT3")
	private String bestText3;
	@Column(name = "PGKUNDNR")
	private String pgkundnr;
	@Column(name = "MINRANTA")
	private Double minranta;
	@Column(name = "PANTSATTFAKTUROR")
	private Short pantsattfakturor;
	@Column(name = "PANTSATTTEXT1")
	private String pantsatttext1;
	@Column(name = "POMINGANGER")
	private Short pominganger;
	@Column(name = "PANTSATTTEXT2")
	private String pantsatttext2;
	@Column(name = "PANTSATTTEXT3")
	private String pantsatttext3;
	@Column(name = "FAK_OBSTEXT1")
	private String fakObstext1;

	public TableFuppg() {
	}

	public TableFuppg(String namn) {
		this.namn = namn;
	}

	public TableFuppg(String namn, double droj, int rantktid, short krangel, short sysdat, short bonus, short bonusproc1, short bonusproc2, short bonTid, double moms1, double moms2, double moms3, String moms1k, String moms2k, String moms3k, String moms1k2, String moms2k2, String moms3k2, String varuk, String levk, String kundfk, String kassa, String bank, String post, String orutk, String rantak) {
		this.namn = namn;
		this.droj = droj;
		this.rantktid = rantktid;
		this.krangel = krangel;
		this.sysdat = sysdat;
		this.bonus = bonus;
		this.bonusproc1 = bonusproc1;
		this.bonusproc2 = bonusproc2;
		this.bonTid = bonTid;
		this.moms1 = moms1;
		this.moms2 = moms2;
		this.moms3 = moms3;
		this.moms1k = moms1k;
		this.moms2k = moms2k;
		this.moms3k = moms3k;
		this.moms1k2 = moms1k2;
		this.moms2k2 = moms2k2;
		this.moms3k2 = moms3k2;
		this.varuk = varuk;
		this.levk = levk;
		this.kundfk = kundfk;
		this.kassa = kassa;
		this.bank = bank;
		this.post = post;
		this.orutk = orutk;
		this.rantak = rantak;
	}

	public double getDroj() {
		return droj;
	}

	public void setDroj(double droj) {
		this.droj = droj;
	}

	public int getRantktid() {
		return rantktid;
	}

	public void setRantktid(int rantktid) {
		this.rantktid = rantktid;
	}

	public String getTxt1() {
		return txt1;
	}

	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}

	public String getTxt2() {
		return txt2;
	}

	public void setTxt2(String txt2) {
		this.txt2 = txt2;
	}

	public String getTxt3() {
		return txt3;
	}

	public void setTxt3(String txt3) {
		this.txt3 = txt3;
	}

	public String getTxt4() {
		return txt4;
	}

	public void setTxt4(String txt4) {
		this.txt4 = txt4;
	}

	public String getTxt5() {
		return txt5;
	}

	public void setTxt5(String txt5) {
		this.txt5 = txt5;
	}

	public short getKrangel() {
		return krangel;
	}

	public void setKrangel(short krangel) {
		this.krangel = krangel;
	}

	public short getSysdat() {
		return sysdat;
	}

	public void setSysdat(short sysdat) {
		this.sysdat = sysdat;
	}

	public short getBonus() {
		return bonus;
	}

	public void setBonus(short bonus) {
		this.bonus = bonus;
	}

	public short getBonusproc1() {
		return bonusproc1;
	}

	public void setBonusproc1(short bonusproc1) {
		this.bonusproc1 = bonusproc1;
	}

	public short getBonusproc2() {
		return bonusproc2;
	}

	public void setBonusproc2(short bonusproc2) {
		this.bonusproc2 = bonusproc2;
	}

	public short getBonTid() {
		return bonTid;
	}

	public void setBonTid(short bonTid) {
		this.bonTid = bonTid;
	}

	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}

	public Double getFikon() {
		return fikon;
	}

	public void setFikon(Double fikon) {
		this.fikon = fikon;
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

	public String getLevadr1() {
		return levadr1;
	}

	public void setLevadr1(String levadr1) {
		this.levadr1 = levadr1;
	}

	public String getLevadr2() {
		return levadr2;
	}

	public void setLevadr2(String levadr2) {
		this.levadr2 = levadr2;
	}

	public String getLevadr3() {
		return levadr3;
	}

	public void setLevadr3(String levadr3) {
		this.levadr3 = levadr3;
	}

	public String getPostgiro() {
		return postgiro;
	}

	public void setPostgiro(String postgiro) {
		this.postgiro = postgiro;
	}

	public String getBankgiro() {
		return bankgiro;
	}

	public void setBankgiro(String bankgiro) {
		this.bankgiro = bankgiro;
	}

	public String getRegnr() {
		return regnr;
	}

	public void setRegnr(String regnr) {
		this.regnr = regnr;
	}

	public String getSate() {
		return sate;
	}

	public void setSate(String sate) {
		this.sate = sate;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHemsida() {
		return hemsida;
	}

	public void setHemsida(String hemsida) {
		this.hemsida = hemsida;
	}

	public String getBestFraktbolag1() {
		return bestFraktbolag1;
	}

	public void setBestFraktbolag1(String bestFraktbolag1) {
		this.bestFraktbolag1 = bestFraktbolag1;
	}

	public String getBestFraktbolag2() {
		return bestFraktbolag2;
	}

	public void setBestFraktbolag2(String bestFraktbolag2) {
		this.bestFraktbolag2 = bestFraktbolag2;
	}

	public String getBestFraktbolag3() {
		return bestFraktbolag3;
	}

	public void setBestFraktbolag3(String bestFraktbolag3) {
		this.bestFraktbolag3 = bestFraktbolag3;
	}

	public String getBestPallreg1() {
		return bestPallreg1;
	}

	public void setBestPallreg1(String bestPallreg1) {
		this.bestPallreg1 = bestPallreg1;
	}

	public String getBestPallreg2() {
		return bestPallreg2;
	}

	public void setBestPallreg2(String bestPallreg2) {
		this.bestPallreg2 = bestPallreg2;
	}

	public String getBestPallreg3() {
		return bestPallreg3;
	}

	public void setBestPallreg3(String bestPallreg3) {
		this.bestPallreg3 = bestPallreg3;
	}

	public double getMoms1() {
		return moms1;
	}

	public void setMoms1(double moms1) {
		this.moms1 = moms1;
	}

	public double getMoms2() {
		return moms2;
	}

	public void setMoms2(double moms2) {
		this.moms2 = moms2;
	}

	public double getMoms3() {
		return moms3;
	}

	public void setMoms3(double moms3) {
		this.moms3 = moms3;
	}

	public String getMoms1k() {
		return moms1k;
	}

	public void setMoms1k(String moms1k) {
		this.moms1k = moms1k;
	}

	public String getMoms2k() {
		return moms2k;
	}

	public void setMoms2k(String moms2k) {
		this.moms2k = moms2k;
	}

	public String getMoms3k() {
		return moms3k;
	}

	public void setMoms3k(String moms3k) {
		this.moms3k = moms3k;
	}

	public String getMoms1k2() {
		return moms1k2;
	}

	public void setMoms1k2(String moms1k2) {
		this.moms1k2 = moms1k2;
	}

	public String getMoms2k2() {
		return moms2k2;
	}

	public void setMoms2k2(String moms2k2) {
		this.moms2k2 = moms2k2;
	}

	public String getMoms3k2() {
		return moms3k2;
	}

	public void setMoms3k2(String moms3k2) {
		this.moms3k2 = moms3k2;
	}

	public String getVaruk() {
		return varuk;
	}

	public void setVaruk(String varuk) {
		this.varuk = varuk;
	}

	public String getLevk() {
		return levk;
	}

	public void setLevk(String levk) {
		this.levk = levk;
	}

	public String getKundfk() {
		return kundfk;
	}

	public void setKundfk(String kundfk) {
		this.kundfk = kundfk;
	}

	public String getKassa() {
		return kassa;
	}

	public void setKassa(String kassa) {
		this.kassa = kassa;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getOrutk() {
		return orutk;
	}

	public void setOrutk(String orutk) {
		this.orutk = orutk;
	}

	public String getRantak() {
		return rantak;
	}

	public void setRantak(String rantak) {
		this.rantak = rantak;
	}

	public String getBonText1() {
		return bonText1;
	}

	public void setBonText1(String bonText1) {
		this.bonText1 = bonText1;
	}

	public String getBonText2() {
		return bonText2;
	}

	public void setBonText2(String bonText2) {
		this.bonText2 = bonText2;
	}

	public String getBonText3() {
		return bonText3;
	}

	public void setBonText3(String bonText3) {
		this.bonText3 = bonText3;
	}

	public String getBonText4() {
		return bonText4;
	}

	public void setBonText4(String bonText4) {
		this.bonText4 = bonText4;
	}

	public String getBonText5() {
		return bonText5;
	}

	public void setBonText5(String bonText5) {
		this.bonText5 = bonText5;
	}

	public String getEbText1() {
		return ebText1;
	}

	public void setEbText1(String ebText1) {
		this.ebText1 = ebText1;
	}

	public String getEbText2() {
		return ebText2;
	}

	public void setEbText2(String ebText2) {
		this.ebText2 = ebText2;
	}

	public String getEbText3() {
		return ebText3;
	}

	public void setEbText3(String ebText3) {
		this.ebText3 = ebText3;
	}

	public String getEbText4() {
		return ebText4;
	}

	public void setEbText4(String ebText4) {
		this.ebText4 = ebText4;
	}

	public String getEbText5() {
		return ebText5;
	}

	public void setEbText5(String ebText5) {
		this.ebText5 = ebText5;
	}

	public String getTempText1() {
		return tempText1;
	}

	public void setTempText1(String tempText1) {
		this.tempText1 = tempText1;
	}

	public String getTempText2() {
		return tempText2;
	}

	public void setTempText2(String tempText2) {
		this.tempText2 = tempText2;
	}

	public String getTempText3() {
		return tempText3;
	}

	public void setTempText3(String tempText3) {
		this.tempText3 = tempText3;
	}

	public String getTempText4() {
		return tempText4;
	}

	public void setTempText4(String tempText4) {
		this.tempText4 = tempText4;
	}

	public String getTempText5() {
		return tempText5;
	}

	public void setTempText5(String tempText5) {
		this.tempText5 = tempText5;
	}

	public Short getTempText() {
		return tempText;
	}

	public void setTempText(Short tempText) {
		this.tempText = tempText;
	}

	public String getFakBText1() {
		return fakBText1;
	}

	public void setFakBText1(String fakBText1) {
		this.fakBText1 = fakBText1;
	}

	public String getFakBText2() {
		return fakBText2;
	}

	public void setFakBText2(String fakBText2) {
		this.fakBText2 = fakBText2;
	}

	public String getFakBText3() {
		return fakBText3;
	}

	public void setFakBText3(String fakBText3) {
		this.fakBText3 = fakBText3;
	}

	public String getFakBText4() {
		return fakBText4;
	}

	public void setFakBText4(String fakBText4) {
		this.fakBText4 = fakBText4;
	}

	public String getFakBText5() {
		return fakBText5;
	}

	public void setFakBText5(String fakBText5) {
		this.fakBText5 = fakBText5;
	}

	public String getFaktortext1() {
		return faktortext1;
	}

	public void setFaktortext1(String faktortext1) {
		this.faktortext1 = faktortext1;
	}

	public String getFaktortext2() {
		return faktortext2;
	}

	public void setFaktortext2(String faktortext2) {
		this.faktortext2 = faktortext2;
	}

	public String getFaktortext3() {
		return faktortext3;
	}

	public void setFaktortext3(String faktortext3) {
		this.faktortext3 = faktortext3;
	}

	public String getPrnOrder() {
		return prnOrder;
	}

	public void setPrnOrder(String prnOrder) {
		this.prnOrder = prnOrder;
	}

	public String getPrnFaktura() {
		return prnFaktura;
	}

	public void setPrnFaktura(String prnFaktura) {
		this.prnFaktura = prnFaktura;
	}

	public String getPrnBank() {
		return prnBank;
	}

	public void setPrnBank(String prnBank) {
		this.prnBank = prnBank;
	}

	public String getPrnPost() {
		return prnPost;
	}

	public void setPrnPost(String prnPost) {
		this.prnPost = prnPost;
	}

	public String getPrnOvrigt() {
		return prnOvrigt;
	}

	public void setPrnOvrigt(String prnOvrigt) {
		this.prnOvrigt = prnOvrigt;
	}

	public String getPrnFax() {
		return prnFax;
	}

	public void setPrnFax(String prnFax) {
		this.prnFax = prnFax;
	}

	public String getPrnEmail() {
		return prnEmail;
	}

	public void setPrnEmail(String prnEmail) {
		this.prnEmail = prnEmail;
	}

	public Short getPrnOrderkopior() {
		return prnOrderkopior;
	}

	public void setPrnOrderkopior(Short prnOrderkopior) {
		this.prnOrderkopior = prnOrderkopior;
	}

	public Short getPrnFakturakopior() {
		return prnFakturakopior;
	}

	public void setPrnFakturakopior(Short prnFakturakopior) {
		this.prnFakturakopior = prnFakturakopior;
	}

	public Short getPrnPostkopior() {
		return prnPostkopior;
	}

	public void setPrnPostkopior(Short prnPostkopior) {
		this.prnPostkopior = prnPostkopior;
	}

	public Short getPrnBankkopior() {
		return prnBankkopior;
	}

	public void setPrnBankkopior(Short prnBankkopior) {
		this.prnBankkopior = prnBankkopior;
	}

	public Short getPrnOvrigtkopior() {
		return prnOvrigtkopior;
	}

	public void setPrnOvrigtkopior(Short prnOvrigtkopior) {
		this.prnOvrigtkopior = prnOvrigtkopior;
	}

	public Short getPrnFaxkopior() {
		return prnFaxkopior;
	}

	public void setPrnFaxkopior(Short prnFaxkopior) {
		this.prnFaxkopior = prnFaxkopior;
	}

	public Short getPrnEmailkopior() {
		return prnEmailkopior;
	}

	public void setPrnEmailkopior(Short prnEmailkopior) {
		this.prnEmailkopior = prnEmailkopior;
	}

	public Short getPomindagar() {
		return pomindagar;
	}

	public void setPomindagar(Short pomindagar) {
		this.pomindagar = pomindagar;
	}

	public String getBestText1() {
		return bestText1;
	}

	public void setBestText1(String bestText1) {
		this.bestText1 = bestText1;
	}

	public String getBestText2() {
		return bestText2;
	}

	public void setBestText2(String bestText2) {
		this.bestText2 = bestText2;
	}

	public String getBestText3() {
		return bestText3;
	}

	public void setBestText3(String bestText3) {
		this.bestText3 = bestText3;
	}

	public String getPgkundnr() {
		return pgkundnr;
	}

	public void setPgkundnr(String pgkundnr) {
		this.pgkundnr = pgkundnr;
	}

	public Double getMinranta() {
		return minranta;
	}

	public void setMinranta(Double minranta) {
		this.minranta = minranta;
	}

	public Short getPantsattfakturor() {
		return pantsattfakturor;
	}

	public void setPantsattfakturor(Short pantsattfakturor) {
		this.pantsattfakturor = pantsattfakturor;
	}

	public String getPantsatttext1() {
		return pantsatttext1;
	}

	public void setPantsatttext1(String pantsatttext1) {
		this.pantsatttext1 = pantsatttext1;
	}

	public Short getPominganger() {
		return pominganger;
	}

	public void setPominganger(Short pominganger) {
		this.pominganger = pominganger;
	}

	public String getPantsatttext2() {
		return pantsatttext2;
	}

	public void setPantsatttext2(String pantsatttext2) {
		this.pantsatttext2 = pantsatttext2;
	}

	public String getPantsatttext3() {
		return pantsatttext3;
	}

	public void setPantsatttext3(String pantsatttext3) {
		this.pantsatttext3 = pantsatttext3;
	}

	public String getFakObstext1() {
		return fakObstext1;
	}

	public void setFakObstext1(String fakObstext1) {
		this.fakObstext1 = fakObstext1;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (namn != null ? namn.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TableFuppg)) {
			return false;
		}
		TableFuppg other = (TableFuppg) object;
		if ((this.namn == null && other.namn != null) || (this.namn != null && !this.namn.equals(other.namn))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "se.saljex.sxserver.TableFuppg[namn=" + namn + "]";
	}

}
