/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.util.ArrayList;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Ulf
 */
public class OrderHandler {
	private EntityManager em;
	private TableOrder1 or1;
	private TableOrder2 or2;
	private TableKund kun;
	private TableArtikel art;

	private ArrayList<TableOrder2> ordreg = new ArrayList<TableOrder2>();  //Holds all rows in the order
	
	public OrderHandler(EntityManager e) {
			em = e;
			or1 = new TableOrder1();
	}

	public OrderHandler(EntityManager e, String kundNr) {
		this(e);
		setKund(kundNr);
	}

	public void addRow(String artnr, Double antal) {
		
		or2 = new TableOrder2();
		art = em.find(TableArtikel.class, artnr);
		if (art == null) { throw new EntityNotFoundException("Kan inte hitta artikel " + artnr + " för order."); }
		or2.setBest(antal);
		or2.setLev(antal);
		or2.setArtnr(art.getNummer());
		or2.setNamn(art.getNamn());
		or2.setKonto(art.getKonto());
		or2.setEnh(art.getEnhet());
		or2.setLevnr(art.getLev());
		
		// Börja ta fram det bästa priset
		
		// Ta fram bästa rabatten
		Double bastaRab = 0.0;
		Double bastaBruttoPris = 0.0;
		Double bastaNettoPris = 0.0;
		TableKunrab kra;
		if (!art.getRabkod().isEmpty()) {	// Om rabkoden är tom så gäller ingen rabatt
			if (!art.getRabkod().equals("NTO")) {	// Vi kan inte ha rabatter för hela NTO-gruppen
				kra = em.find(TableKunrab.class, new TableKunrabPK(kun.getNummer(),art.getRabkod(),""));	// Ta först fram rabatten för huvudgruppen
				if (kra != null) { if (kra.getRab() > bastaRab) { bastaRab = kra.getRab(); } }
			}
			if (!art.getKod1().isEmpty()) {				// KOlla nu om vi har undergrupp, och ta fram rabatten till den
														//  Här tillåter vi att huvudgruppen är av NTO, så vi kan få rabatt på undergrupp till den
				kra = em.find(TableKunrab.class, new TableKunrabPK(kun.getNummer(),art.getRabkod(),art.getKod1()));
				if (kra != null) { if (kra.getRab() > bastaRab) { bastaRab = kra.getRab(); } }
			}
			// Kolla nu hur det är med basrabatten, ändra inte om det är frågan om nettopris
			// Vi tillåter inte basrabatt på NTO-Gruppen
			if (kun.getBasrab() > bastaRab && !art.getRabkod().equals("NTO")) { bastaRab = kun.getBasrab(); }
		}
		// Nu ska vi ha bastaRab laddat och klart

		// Kolla om vi har en kampanj, och i så fall om den gäller för kunde
		final short		KAMPBITELKUND	= 1;
		final short		KAMPBITVVSKUND	= 2;
		final short		KAMPBITVAKUND	= 4;
		final short		KAMPBITGOLVKUND	= 8;
		final short		KAMPBITFASTIGHETSKUND = 16;

		final short		KAMPBITINSTALLATOR	=1;
		final short		KAMPBITBUTIK		= 2;
		final short		KAMPBITINDUSTRI		= 4;
		final short		KAMPBITOEM			= 8;
		final short		KAMPBITGROSSIST		= 16;
		
		//Skapa ett datum i SQL.Date format
		Calendar idag = SXUtil.getTodayDate();  // Returns a calendar with time set  to 0
		// Skapa calendarobjekt med kampanjperioden
		Calendar kampfrdat = Calendar.getInstance();
		kampfrdat.setTime(art.getKampfrdat());
		Calendar kamptidat = Calendar.getInstance();
		kamptidat.setTime(art.getKamptidat());

		boolean kampanj = false;
		
		try {	// Fångar null-pointer ifall något kampanjdatum är felaktigt
			if (idag.compareTo(kampfrdat) >= 0 && idag.compareTo(kamptidat) <= 0) {	// Det finns kampanj på artikeln, nu ska vi kolla om den gäller för kunden
				if (art.getKampkundartgrp() == 0 && art.getKampkundgrp() == 0) {
					kampanj = true;
				} else {
					int q1 = kun.getElkund()*KAMPBITELKUND + kun.getVvskund()*KAMPBITVVSKUND + kun.getVakund()*KAMPBITVAKUND + kun.getGolvkund()*KAMPBITGOLVKUND + kun.getFastighetskund()*KAMPBITFASTIGHETSKUND;
					int q2 = kun.getInstallator()*KAMPBITINSTALLATOR + kun.getButik()*KAMPBITBUTIK + kun.getIndustri()*KAMPBITINDUSTRI + kun.getOem()*KAMPBITOEM + kun.getGrossist()*KAMPBITGROSSIST;
					if ((art.getKampkundartgrp() & q1) > 0 && (art.getKampkundgrp() & q2) > 0) {
						kampanj = true;
					}
				}
			}
		} catch (NullPointerException e) {}
		
		
		// Dax att ta fram bästa bruttopris och nettopris med avseende på antal
		bastaBruttoPris = art.getUtpris();
		bastaNettoPris  = bastaBruttoPris; // Sätter startv'rde för b'sta netto
		
		if (kampanj && art.getKamppris() != 0 && art.getKamppris() < bastaNettoPris) { bastaNettoPris = art.getKamppris(); }
		if (art.getStafAntal1() > 0.0 && antal >= art.getStafAntal1())  {
		    if (art.getStafPris1() != 0.0) {
			    if (art.getStafPris1() < bastaBruttoPris ) { bastaBruttoPris = art.getStafPris1(); }
		    }
		    if (kampanj && art.getKampprisstaf1() != 0.0 && art.getKampprisstaf1() < bastaNettoPris) { bastaNettoPris = art.getKampprisstaf1(); }
		}
		if (art.getStafAntal2() > 0.0 && antal >= art.getStafAntal2())  {
		    if (art.getStafPris2() != 0.0) {
			    if (art.getStafPris2() < bastaBruttoPris ) { bastaBruttoPris = art.getStafPris2(); }
		    }
		    if (kampanj && art.getKampprisstaf2() != 0.0 && art.getKampprisstaf2() < bastaNettoPris) { bastaNettoPris = art.getKampprisstaf2(); }
		}

		// Kolla om det finns ett nettopris som offert
		if (!kun.getNettolst().isEmpty()) {
		    net = em.find(TableNettopri.class, artnr, lista);
		    if (net != null) {
			if (net.getPris() > 0.0 && net.getPris() < bastaNetto) { bastaNetto = net.getPris(); }
		    }
		}

		// Kolla vilket pris som är läögst - brutto-rab eller netto
		if (bastaNettoPris != 0.0 && bastaNettoPris < bastaBruttoPris * (1-bastaRab/100)) {	//Vi har ett nettopris
		    or2.setPris(bastaNettoPris);
		    or2.setRab(0.0);
		} else {	    //Vi har brutto - rabatt
		    or2.setPris(bastaBruttoPris);
		    or2.setRab(bastaRab);
		}
		// Nu ska bästa priset vara satt

		or2.setSumma(or2.getPris() * antal * (1-or2.getRab()));
		or2.setNetto((art.getInpris() * (1-art.getRab()/100) * (1+art.getInpFraktproc()/100)) + art.getInpFrakt() + art.getInpMiljo());
		ordreg.add(or2);
	}
	
	
	
	public void setKund(String kundNr) {
		// Hämta kund och sätt standardvärden för or1
		kun = em.find(TableKund.class, kundNr);
		if (kun == null) { throw new EntityNotFoundException("Kan inte hitta kund " + kundNr + " för ny order."); }
		kun2Or1();
		
	}
	
	private void kun2Or1() {
		or1.setKundnr(kun.getNummer());
		or1.setNamn(kun.getNamn());
		or1.setAdr1(kun.getAdr1());
		or1.setAdr2(kun.getAdr2());
		or1.setAdr3(kun.getAdr3());
		setLevAdr(kun.getLnamn(), kun.getLadr2(), kun.getLadr3());

		//lägga till info sist i Säljar-strängen
		or1.setSaljare(java.lang.String.format( "%-30s%3s", kun.getSaljare(), "/00" ));
		
		or1.setReferens(kun.getRef());
		setKreditTid(kun.getKtid());
		or1.setBonus(kun.getBonus());
		or1.setFaktor(kun.getFaktor());
		or1.setLevvillkor(kun.getLevvillkor());
		or1.setMottagarfrakt(kun.getMottagarfrakt());
		or1.setFraktkundnr(kun.getFraktkundnr());
		or1.setFraktbolag(kun.getFraktbolag());
		or1.setFraktfrigrans(kun.getFraktfrigrans());
		if (kun.getMomsfri() > 0) { 
			or1.setMoms((short)0);
		} else {
			or1.setMoms((short)1);
		}
		or1.setLinjenr1(kun.getLinjenr1());
		or1.setLinjenr2(kun.getLinjenr2());
		or1.setLinjenr3(kun.getLinjenr3());		
	}

	
	public void setMarke(String m) {
		or1.setMarke(m);
	}
	
	public void setLevAdr(String adr1, String adr2, String adr3) {
		or1.setLevadr1(adr1);
		or1.setLevadr2(adr2);
		or1.setLevadr3(adr3);
	}
	
	public void setAnnanLevAdr(String adr1, String adr2, String adr3) {
		//Sätter levadress, samt flaggan för att levadress har ändrats
		setLevAdr(adr1, adr2, adr3);
		or1.setAnnanlevadress((short)1);
	}

	
	public void setKreditTid(short ktid) {
		or1.setKtid(ktid);
	}
	
	
	}
	
	


/*
                      MAP
GetOrdFromOS   PROCEDURE(),LONG              !Returnerar TRU om ORD är laddad med nya orderdel
Art_2_OS        PROCEDURE
OS_2_Ord        PROCEDURE
Set_Pris         PROCEDURE(real)                !Antal
Get_OrderNr       PROCEDURE(),BYTE
Get_BEstNr        PROCEDURE()
Spara_Order2         PROCEDURE(),BYTE
SparaDirektBest      PROCEDURE(STRING),BYTE
GallerKampanj        PROCEDURE(),BYTE
CheckArtAntal      PROCEDURE(REAL,BYTE = 1),REAL   !Föreslaget antal, tystläge
SendWebSQL     PROCEDURE(String),BYTE,PROC
AddOErr        PROCEDURE(string)
GetArtikelLager PROCEDURE(STRING,BYTE)
CheckKreditVardig    PROCEDURE(),BYTE
                     END

KampBitElKund           EQUATE(1)
KampBitVVSKund          EQUATE(2)
KampBitVAKund           EQUATE(4)
KampBitGolvKund         EQUATE(8)
KampBitFastighetskund   EQUATE(16)

KampBitInstallator      EQUATE(1)
KampBitButik            EQUATE(2)
KampBitIndustri         EQUATE(4)
KampBitOEM              EQUATE(8)
KampBitGrossist         EQUATE(16)

!VeckoLevDagArr  BYTE,DIM(7)

qwo1  QUEUE,PRE(qwo1)
record   group
wordernr LONG
kundnr   like(KUN:Nummer)
marke    like(OR1:Marke)
lagernr  LIKE(OR1:LagerNr)
antalrader  LONG
KreditSparr BYTE
 . .


ORDREG      QUEUE,PRE(ORD)
RECORD  GROUP
Nummer   LIKE(art:nummer)
Namn     LIKE(ART:Namn)
LevNr    LIKE(LEV:Nummer)
FraktVillkor   LIKE(art:Fraktvillkor)
Antal    REAL
Enh      LIKE(ART:Enhet)
Pris     REAL
Rab      REAL
Summa    REAL
Netto    REAL
Konto    CSTRING(7)
Lager    REAL
Lagerplats LIKE(LAG:Lagerplats)
OrderNr     LONG
DirektLev   BYTE
        . .


OS          QUEUE,PRE(OS)                 !Håller hela weborderinnehållet, därefter flyttas det till ORD för att spara order
RECORD  GROUP
Nummer   LIKE(art:nummer)
Namn     LIKE(ART:Namn)
LevNr    LIKE(LEV:Nummer)
FraktVillkor   LIKE(art:Fraktvillkor)
Antal    REAL
Enh      LIKE(ART:Enhet)
Pris     REAL
Rab      REAL
Summa    REAL
Netto    REAL
Konto    CSTRING(7)
Lager    REAL
Lagerplats LIKE(LAG:Lagerplats)
OrderNr     LONG
DirektLev   BYTE
        . .



Err   BYTE
ordercn  LONG


AntalRader  LONG

savmarke  like(OR1:Marke)
OrderSumma  REAL

seowner  cstring(61)

BestBehov   BYTE

 CODE

 relate:artikel.open()
 relate:lager.open()
 relate:kund.open()
 relate:order1.open()
 relate:order2.open()
 relate:orderhand.open()
 relate:fdordernr.open()
 relate:nettopri.open()
 relate:kunrab.open()
 relate:best1.open()
 relate:best2.open()
 relate:faktdat.open()
 relate:lev.open()
 relate:sxreg.open()


 AddQInfo('Startar sök efter ny WebOrder',FALSE)
 FREE(qwo1)
 IF SXR:Get(SEOwner,'SxServWebODBCOwnerStr','sxadm,?,?')
    Err = TRUE
    AddOErr('SXR:Get WebODBCOwnerStr')
 .
 SaljexSEOwner = CLIP(SEOwner)

 IF NOT Err
    open(websqlfile)
    IF ErrorCode()
    message(Error() & ' - ' & FileError())
       AddOErr('open(websqlfile)')
       Err = TRUE
    .
    IF NOT Err
       IF SendWebSQL('select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where status = ''Skickad''')
          Err = TRUE
          AddOErr('Select from weborder')
       .
    .
 .

 IF NOT Err
    LOOP
       CLEAR(qwo1:record)
       NEXT(WebSQLFile)
       IF ErrorCode()
          IF ErrorCode() <> 33  !Record not availab le
             Err = TRUE
             AddOErr('NEXT(WebSQLFile')
          .
          BREAK
       .
       qwo1:wordernr = websqlfile.f1
       qwo1:KundNr = CLIP(websqlfile.f2)
       qwo1:Marke = CLIP(websqlfile.f3)
       qwo1:LagerNr = websqlfile.f4
       qwo1:AntalRader = websqlfile.f5
       qwo1:KreditSparr = websqlfile.f6
       ADD(qwo1)
    .
 .

 CLOSE(WebSQLFile)

 IF NOT Err AND RECORDS(qwo1)
    LOOP 
       Err = FALSE
       CLEAR(OR1:Record)
       GET(qwo1,1)
       IF ErrorCode() THEN BREAK.

       Logout(5,order1)
       IF ErrorCode()
          Err = TRUE
          AddOErr('Logout')
       .
       IF NOT Err
          OR1:wordernr = qwo1:wordernr
          KUN:Nummer = qwo1:kundnr
          OR1:Marke = qwo1:marke
          SavMarke = qwo1:marke
          OR1:LagerNr = qwo1:lagernr
          OR1:Datum = TODAY()

          AntalRader = qwo1:AntalRader
          GET(Kund,KUN:K_Nummer)
          IF ErrorCode()
             Err = TRUE
             AddOErr('Kunden saknas ' & qwo1:kundnr)
          ELSE
             DO KUN_2_OR1
             DO LasWebOrder2
             IF NOT Err
                IF CheckKreditVardig()
                   LOOP WHILE GetOrdFromOS()       !OR1:Status sätts av GetOrdFromOS
                      IF Get_Ordernr()
                         AddOErr('Get_ordernr')
                         Err = TRUE
                      ELSE
                         IF OR1:Status = 'Direkt'
                            IF SparaDirektBest(LEV:Nummer)
                               Err = TRUE
                               AddOErr('SparaDirektBest()')
                            ELSE
                               OR1:DirektLevNr = BE1:BestNr
                               OR1:Marke = CLIP(SavMarke) & ' ' & BE1:BEstNr
                               OR1:Status = 'Väntar'        !Både order och beställning blir flaggade som avvaktande för godk'nnande
                            .
                         ELSE
                            OR1:DirektLevNr = 0
                            OR1:Marke = SavMarke
                         .

                         IF NOT Err
                            IF Spara_Order2()
                               AddOErr('Spara order')
                               Err = TRUE
                            ELSE
                               AddQInfo('WebOrder ' & OR1:wordernr & ' sparad som ' & OR1:OrderNr,TRUE)
                            .
                         .
                      .
                   .

                   IF NOT Err
                      OPEN(WebSQLFile)
                      IF SendWebSQL('update weborder1 set status = ''Mottagen'', kreditsparr = 0, MottagenDatum = ''' & FORMAT(Today(),@D17) & ''' where wordernr = ' & OR1:wordernr)
                         AddOErr('update weborder1')
                         Err = TRUE
                      .
                      CLOSE(WebSQLFile)
                   .
                ELSE   !Kunden är inte kreditvärdig
                   Err = TRUE    !Signalera att vi inte sparar något
                   IF NOT qwo1:Kreditsparr      !Om den tidigare har varit kreditspärrad ska vi inte updatera och meddela på nytt
                      AddQInfo('Kund: ' & KUN:Nummer & ' stoppad pga kreditgräns',TRUE)
                      OPEN(WebSQLFile)
                      IF SendWebSQL('update weborder1 set kreditsparr=1 where wordernr = ' & OR1:wordernr)
                        Err = TRUE
                        AddOErr('update weborder')
                      .
                      CLOSE(WebSQLFile)
                   .
                .
             .
          .
       .
       IF NOT Err
          commit()
          IF ErrorCode()
             Err = TRUE
             AddOErr('Commit')
          .
       .
       IF Err
         ROLLBACK
       .
       DELETE(qwo1)        !Ta bort den och fortsätt med nästa
    .
 .


 AddQInfo('Sök efter ny WebOrder utförd',FALSE)

 relate:artikel.close()
 relate:lager.close()
 relate:kund.close()
 relate:order1.close()
 relate:order2.close()
 relate:orderhand.close()
 relate:fdordernr.close()
 relate:nettopri.close()
 relate:kunrab.close()
 relate:best1.close()
 relate:best2.close()
 relate:faktdat.close()
 relate:lev.close()
 relate:sxreg.close()





LasWebOrder2   ROUTINE
 DATA
FraktTillkommer BYTE
 CODE
 FraktTillkommer = 0
 FREE(os)
 OrderSumma = 0

 open(websqlfile)
 IF SendWebSQL('select artnr, antal from weborder2 where wordernr = ' & OR1:wordernr)
    AddOErr('Select from weborder2')
    Err = TRUE
 ELSE
    LOOP
       CLEAR(OS:Record)
       NEXT(WebSQLFile)
       IF ErrorCode()
          IF ErrorCode() = 33  !Record not availab le
             BREAK
          ELSE
             AddOerr('Next(WebSQLFile)')
             Err = TRUE
             BREAK
          .
       ELSE
          ART:Nummer =  CLIP(WebSQLFile.f1)
          GET(Artikel,ART:K_Nummer)
          IF ErrorCode()
             AddOerr('GET(artikel)')
             Err = TRUE
             BREAK
          .
          Art_2_OS()
          OS:Antal = WebSQLFile.f2
          OS:Antal = CheckArtAntal(OS:Antal)
          Set_Pris(OS:Antal)
          OS:Summa = ROUND(ROUND(OS:Pris,0.01) * OS:Antal * (1-OS:Rab/100),0.01)
          OrderSumma += OS:Summa
          ADD(OS)
       .
    .


 .
 close(websqlfile)








KUN_2_OR1   ROUTINE
  OR1:Kundnr = KUN:Nummer
  OR1:Namn = KUN:Namn
  OR1:Adr1 = KUN:Adr1
  OR1:Adr2 = KUN:Adr2
  OR1:Adr3 = KUN:Adr3
  OR1:LevAdr1 = KUN:LNamn
  OR1:LevAdr2 = KUN:LAdr2
  OR1:Levadr3 = KUN:LAdr3
  OR1:Saljare = Format(KUN:Saljare,@S30) & '/00'
  OR1:Referens = KUN:Ref
  OR1:KTid = KUN:KTid
  OR1:Bonus = KUN:Bonus
  OR1:Faktor = KUN:Faktor
  OR1:LevVillkor = KUN:LevVillkor
  OR1:Mottagarfrakt = KUN:Mottagarfrakt
  OR1:FraktKundNr = KUN:FraktKundNr
  OR1:Fraktbolag = KUN:Fraktbolag
  OR1:Fraktfrigrans = KUN:Fraktfrigrans
  OR1:Moms = 1
  IF KUN:Momsfri THEN OR1:Moms = 0.
  !OR1:VeckoLevDag = KUN:VeckoLevDag
  OR1:LinjeNr1 = KUN:LinjeNr1
  OR1:LinjeNr2 = KUN:LinjeNr2
  OR1:LinjeNr3 = KUN:LinjeNr3






GetOrdFromOS   PROCEDURE()
FraktTillkommer BYTE
cn          LONG
TempLevNr   LIKE(LEV:Nummer)

 CODE
 FraktTillkommer = 0

 TempLevNr = ''
 FREE(Ordreg)

 SORT(OS,ORD:LevNr)
 LOOP CN = 1 TO RECORDS(OS)      !Nu ska vi se om det finns någon leverantör med direktlevartikel
    GET(OS,cn)
    IF OS:DirektLev
       TempLevNr = OS:LevNr
       BREAK
    .
 .

 LEV:Nummer = TempLevNr          !Skicka med vilken leverantör det rör sig om i denna globala variabel

 cn = 1
 BestBehov = FALSE
 LOOP    !Nu loopar vi igenom och filtrerar ifall det fanns leverantören med direktlev, de rader vi hittar raderas
    GET(OS,cn)
    IF ErrorCode() THEN BREAK.
    IF TempLevNr AND OS:LevNr <> TempLevNr
       cn += 1                   !Vi lämnar kvar denna rad, och fortsätter framåt i kön
       CYCLE
    .
    IF (OS:Fraktvillkor = 1 AND KUN:Distrikt <> 1) OR OS:Fraktvillkor = 2           !(Fritt turbil AND Kunden utanför turbilsdistriktet) OR Fritt lev.lager
       FraktTillkommer = TRUE
    .
    IF NOT TempLevNr AND (LAG:ILager-LAG:IOrder-OS:Antal+LAG:Best < 0) !Vi måste beställa, men meddela bara om det inte är direktleverans
       BestBehov = TRUE
    .
    OS_2_ORD()
    ADD(Ordreg)
    DELETE(OS)       !När raden raderas, kommer nästa rad fram vid GET utan att cn behöver ökas
 .
 IF BestBehov
    SendAdmMessage('Vi behöver beställa grejer till Webborder!','Kund: ' & OR1:KundNr & ' Lev ' & ART:Lev)
 .



 IF FraktTillkommer
    CLEAR(ORD:Record)
    ORD:Nummer = '0000' !Frakt
    ORD:NAMN = 'FRAKT'
    ORD:LevNr = ''
    ORD:ENH = 'ST'
    ORD:Lagerplats = 'ZZ'
    ADD(Ordreg)
 .
 IF TempLevNr                 !Vi har en direktleverans
    OR1:Status = 'Direkt'
 ELSE
    OR1:Status = 'Sparad'
 .

 SORT(Ordreg,ORD:Lagerplats,ORD:Nummer)

 RETURN(Records(Ordreg))




SendWebSQL   PROCEDURE(SQLText)
 CODE
 RETURN(SendSQL(websqlfile,SQLText))











ART_2_OS    PROCEDURE
 CODE
   GetArtikelLager(ART:Nummer, OR1:LagerNr)
   OS:NUMMER = ART:NUMMER
   OS:NAMN = ART:NAMN
   OS:LevNr = ART:Lev
   OS:ENH = ART:ENHET
   OS:PRIS = ART:UTPRIS
   OS:NETTO = (ART:INPRIS * (1-ART:RAB/100) * (1+ART:INP_FraktProc/100)) + ART:INP_Frakt + ART:INP_Miljo
   OS:Konto = ART:Konto
   OS:LagerPlats = LAG:LagerPlats
   OS:FraktVillkor = art:Fraktvillkor
   OS:DirektLev = ART:Direktlev


OS_2_Ord PROCEDURE
 CODE
 ORD:Record :=: OS:Record


Set_Pris PROCEDURE (Antal)

Lagsta_Pris   REAL
Lagsta_Rab    REAL
Sav_Rab       REAL      !Håller reda på rabattsatsen vi fick från Set_Rab så den kan återanvändas även vid kampanjpriser
D_Pris        REAL
D_Antal       REAL
D_Rab         REAL
 CODE
    D_Antal = Antal         !Rutinen är från början skriven för att hantera D_Antal, därför sätter vi D_Antal med en gång
    DO Set_Rab
    Sav_Rab = D_Rab
    D_Pris = ART:Utpris
    Lagsta_Pris = D_Pris
    Lagsta_Rab = D_Rab
    IF GallerKampanj()
       IF ART:KampPris                !Sätt bara kampanjpris om det är angivet - annars ordinarie
          D_Pris = ART:KampPris
          D_Rab = 0
          DO SetLagsta
       .
    .

    IF ART:Staf_Antal1 AND D_Antal >= ART:Staf_Antal1          !Har vi stafflingspriser
       D_Pris = ART:Staf_Pris1
       D_Rab = Sav_Rab
       DO SetLagsta
                                                        !Kolla om vi har kampanjpriser
       IF GallerKampanj()
          IF ART:KampPrisStaf1
             D_Pris = ART:KampPrisStaf1
             D_Rab = 0
             DO SetLagsta
          ELSIF ART:KampPris
             D_Pris = ART:KampPris
             D_Rab = 0
             DO SetLagsta
          .
       .
    .
    IF ART:Staf_Antal2 AND D_Antal >= ART:Staf_Antal2
       D_Pris = ART:Staf_Pris2
       D_Rab = Sav_Rab
       DO SetLagsta
                                                       !Kolla om vi har kampanjpriser
       IF GallerKampanj()
          IF ART:KampPrisStaf2
             D_Pris = ART:KampPrisStaf2
             D_Rab = 0
             DO SetLagsta
          ELSIF ART:KampPris
             D_Pris = ART:KampPris
             D_Rab = 0
             DO SetLagsta
          .
       .
    .
    IF KUN:NettoLst
       DO SetNettoPris
       DO SetLagsta
    .

    D_Pris = Lagsta_Pris
    D_Rab = Lagsta_Rab
    OS:Pris = Lagsta_Pris
    OS:Rab = Lagsta_Rab

SetLagsta   ROUTINE
 IF D_Pris * (1-D_Rab/100) < Lagsta_Pris * (1-Lagsta_Rab/100)
    Lagsta_Pris = D_Pris
    Lagsta_Rab = D_Rab
 .

Set_Rab     ROUTINE
   D_RAB = 0
   IF NOT KUN:Nummer OR NOT ART:RabKod OR ART:RABKOD = 'NTO' THEN EXIT.   !Om ingen rabatt är angiven ska vi inte göra något

   KRA:KundNr = KUN:Nummer
   KRA:RabKod = ART:Rabkod
   KRA:Kod1 = ''
   GET(KunRab,KRA:K_NRKOD)
   IF NOT ErrorCode()
      IF KRA:Rab > D_Rab THEN D_Rab = KRA:Rab.
   .
   IF ART:Kod1
      KRA:KundNr = KUN:Nummer
      KRA:RabKod = ART:Rabkod
      KRA:Kod1 = ART:Kod1
      GET(KunRab,KRA:K_NRKOD)
      IF NOT ErrorCode()
         IF KRA:Rab > D_Rab THEN D_Rab = KRA:Rab.
      .
   .

   IF KUN:Basrab > D_Rab THEN D_Rab = KUN:Basrab.
                        !Sätt först Rabattgruppskoden och sedan basrabatten längst ned


SetNettoPris     ROUTINE
  NET:Lista = KUN:NettoLst
  NET:ArtNr = ART:Nummer
  GET(Nettopri,NET:K_LiArt)
  IF NOT ErrorCode()
     D_Pris = NET:Pris
     D_Rab = 0
!    IF NET:Valuta
!       VAL:Valuta = NET:Valuta
!       GET(Valuta,VAL:K_Valuta)
!       IF NOT ErrorCode()
!          D_Pris = ORD:Pris * VAL:Kurs
!       ELSE
!          IF Disp_Medd('Värde för valuta: ' & FORMAT(NET:Valuta,@S3) & ' saknas!','Lägg till i valutaregistret','och kontrollera priset',TRUE,FALSE,FALSE).
!       .
!    .
  .



GallerKampanj   PROCEDURE           !Tar reda på om kampanj gäller för aktuell kund
g1      SHORT
g2      SHORT
ret     BYTE

 CODE
 ret = FALSE
 IF Today() >= ART:KampFrDat AND Today() <= ART:KampTiDat
    IF ART:KampKundArtGrp = 0 AND ART:KampKundGrp = 0
       ret = TRUE        !Har vi inte angivit grupp gäller det för alla
    ELSE
       g1 = KUN:Elkund*KampBitElkund + KUN:VVSkund*KampBitVVSKund + KUN:VAKund*KampBitVAKund + KUN:Golvkund*KampBitGolvKund + KUN:Fastighetskund*KampBitFastighetskund
       g2 = KUN:Installator*KampBitInstallator + KUN:Butik*KampBitButik + KUN:Industri*KampBitIndustri + KUN:OEM*KampBitOEM + KUN:Grossist*KampBitGrossist
       IF BAND(ART:KampKundArtGrp,g1) AND BAND(ART:KampKundGrp,g2) THEN ret = TRUE.
    .
 .
 RETURN(ret)



Get_Ordernr     PROCEDURE                 !Hämta ett färskt ordernr, och öka
ChangedCN   BYTE(0)
RetErr      BYTE(0)
 CODE
  LOOP
     SET(fdordernr)
     WATCH(fdordernr)
     NEXT(fdordernr)

     OR1:Ordernr = FDO:Ordernr
     OR1:Dellev = 1
     FDO:Ordernr += 1
     PUT(fdordernr)
     IF ErrorCode()
        CASE ErrorCode()
        OF RecordChangedErr
           ChangedCN += 1
           IF ChangedCN >= 5
              RetErr = TRUE
              OR1:OrderNr = 0
              AddOErr('Skriva till FDORDERNR. Posten ändrad av annan användare')
           ELSE
              CYCLE
           .
        ELSE
           RetErr = TRUE
           OR1:OrderNr = 0
           AddOErr('Skriva till FDORDERNR')
        .
     .
     BREAK
  .
  RETURN(RetErr)




Spara_Order2    PROCEDURE()
cn      LONG
RetErr  BYTE

 CODE
     RetErr = FALSE
     IF OR1:OrderNr = 0
        AddOErr('Fel vid tilldelning av ordernr. Kan inte spara.')
        RetErr = TRUE
        RETURN(RetErr)
     .

     CN = 0
     LOOP
        CN += 1
        GET(ORDREG,CN)
        IF ERRORCODE() THEN BREAK.
        OR2:POS = CN
        OR2:PrisNr = 1
        OR2:OrderNr = OR1:OrderNr
        OR2:Dellev = OR1:Dellev
        OR2:ArtNr = ORD:Nummer
        OR2:Namn = ORD:Namn
        OR2:Best = ORD:Antal
        OR2:Lev = ORD:Antal
        OR2:Best = ORD:Antal
        OR2:Pris = ORD:Pris
        OR2:Rab = ORD:Rab
        OR2:Summa = ORD:Summa
        OR2:Konto = ORD:Konto
        OR2:Netto = ORD:Netto
        OR2:Enh = ORD:Enh
        OR2:LevNr = ORD:LevNr

        ADD(ORDER2)
        IF ERRORCODE()
           AddOErr('ADD(ORDER2) Spara order ORDER2 - Försök igen!')
           RetErr = TRUE
           BREAK
        .
        IF ORD:Nummer AND ORD:Nummer[1] <> '*' 
           GET(Artikel,0)
           ART:Nummer = CLIP(ORD:Nummer)
           IF Duplicate(ART:K_Nummer)        !Endast om posten finns
              IF SQLUpdateLager(ORD:Nummer,OR1:LagerNr,'iorder = iorder+ ' & ORD:Antal)
                 RetErr = TRUE
                 BREAK
              .
           .
        .
     .
     IF NOT RetErr
        SetNull(Or1:LastAv)         !Rensa ev. låst flagga
        SetNull(OR1:LastDatum)
        SetNull(OR1:LastTid)
        ADD(Order1)
        IF ErrorCOde() THEN
           RetErr = TRUE
           AddOErr('Add(Order1)')
        .
        IF ORH:Add(OR1:OrderNr, '00', 'Skapad')
           RetErr = TRUE
        .
     .
     RETURN(RetErr)








SparaDirektBest   PROCEDURE(LevNr)
RetErr   BYTE(0)
cn       long
 CODE
    CLEAR(BE1:Record)
    Get_BestNr
    IF BE1:BestNr = 0
       RetErr = TRUE
    .
    LEV:Nummer = LevNR
    GET(Lev,LEV:K_Nummer)
    IF ErrorCode()
       AddOErr('Leverantör saknas ' & LevNr)
       RetErr = TRUE
    .
    IF Not RetErr
       BE1:Levnr = LEV:Nummer
       BE1:LevNamn = LEV:Namn
       BE1:Er_Ref = LEV:Ref
       IF LEV:LevVillkor1
          BE1:LevVillkor1 = LEV:LevVillkor1
          BE1:LevVillkor2 = LEV:LevVillkor2
          BE1:LevVillkor3 = LEV:LevVillkor3
       ELSE
          BE1:LevVillkor1 = FUP:Best_Text1
          BE1:LevVillkor2 = FUP:Best_Text2
          BE1:LevVillkor3 = FUP:Best_Text3
       .
       BE1:BestEjPris = LEV:BestEjPris
       BE1:Mottagarfrakt = LEV:Mottagarfrakt
       BE1:Fraktfritt = LEV:Fraktfritt
       BE1:Var_Ref = 'Automatisk beställning'
       BE1:LevAdr0 = OR1:LevAdr1
       BE1:LevAdr1 = OR1:LevAdr2
       BE1:LevAdr2 = OR1:LevAdr3
       BE1:Marke = OR1:Marke
       !BE1:Leverans = FORMAT(OR1:LevDat,@D17)
       BE1:Datum = OR1:Datum
       BE1:LagerNr =     OR1:LagerNr
       BE1:AutoBestalld = FALSE     !Avser leverans mellan filialer

       BE1:OrderNr = OR1:OrderNr
       BE1:Status = 'Väntar'               !Både order och beställning blir flaggade som avvaktande för godk'nnande
       BE1:Sakerhetskod = RANDOM(1000,9999)
       IF ValidateEPostAdress(LEV:Emailorder1)
          BE1:SkickaSom = 'epost'
       ELSE
          BE1:SkickaSom = ''
       .

       LOOP cn = 1 TO RECORDS(Ordreg)
          GET(Ordreg,cn)
          ART:Nummer = ORD:Nummer
          GET(Artikel,ART:K_Nummer)
          IF ErrorCode()
             CLEAR(ART:Record)
          .
          CLEAR(BE2:Record)
          BE2:BestNr = BE1:BestNr
          BE2:Rad = cn
          BE2:ArtNr = ORD:Nummer
          BE2:ArtNamn = ORD:Namn
          BE2:BArtNr = ART:BestNr
          BE2:Best = ORD:Antal
          BE2:Pris = ART:Inpris
          BE2:Rab =  ART:Rab
          BE2:Summa = BE2:Pris * (1-BE2:Rab/100) * BE2:Best
          BE2:Enh = ORD:Enh
          BE1:Summa += BE2:Summa
          ADD(Best2)
          IF ErrorCode()
             AddOErr('Fel: Add(Best2)')
             RetErr = TRUE
             BREAK
          .
          IF OR2:ArtNr AND SUB(OR2:ArtNr,1,1) <> '*'
             GET(Artikel,0)
             ART:Nummer = OR2:ArtNr
             IF Duplicate(ART:K_Nummer)
                IF SQLUpdateLager(OR2:ArtNr,BE1:LagerNr,'best = best+ ' & OR2:best)
                   AddOErr('update lager')
                   Err = TRUE
                   BREAK
                .
             .
          .
       .
       ADD(Best1)
       IF ErrorCode()
          RetErr = TRUE
       .
       IF BEH:Add(BE1:BestNr,'Skapad automatiskt')
          RetErr = TRUE
       .
    .



 RETURN(RetErr)














Get_Bestnr     PROCEDURE                 !Hämta ett färskt bestnr, och öka
 CODE
  LOOP
     SET(Faktdat)
     WATCH(Faktdat)
     NEXT(Faktdat)
     BE1:Bestnr = FDT:Bestnr
     FDT:Bestnr += 1
     PUT(Faktdat)
     IF ErrorCode()
        CASE ErrorCode()
        OF RecordChangedErr
           CYCLE
        ELSE
           AddOErr('PUT(FaktDat)')
           BE1:BestNr = 0
        .
     .
     BREAK
  .




AddOerr  PROCEDURE(str)
CODE
 AddQinfo('Fel: ' & str,TRUE)



CheckArtAntal  PROCEDURE(InAntal,TystLage)
! Kontrollerar antalet mot minsta försäljningsförpackning, och erbjuder användaren möjlighet att justera
! Artikeln måste vara laddad

RetAntal   REAL

 CODE
 RetAntal = InAntal
 IF ART:MinSaljPack > 0 AND InAntal
    IF InAntal % ART:MinSaljpack        !Vi har udda
       DO CalcAntal
       IF NOT TystLage
          IF Message('Antalet stämmer inte mot Minsta försäljningsförpackning.|Artikel: ' & ART:Nummer & ' ' & ART:Namn & '|Antal: '|
                     & InAntal & '|Minsta förpackning: ' & ART:MinSaljPack & '|Justerat antal: ' & RetAntal |
                     & '| |Vill du ändra antalet?','Fel antal',,'Ja|Nej',1) <> 1
             RetAntal = InAntal
          .
       .
    .
 .
 RETURN(RetAntal)

CalcAntal   ROUTINE
 IF ART:MinSaljPack >= 50
    IF InAntal >= 25
       RetAntal = (INT(InAntal / ART:MinSaljPack) + 1) * ART:MinSaljPack            !Avrunda till närmast större förpackning
    ELSE
       RetAntal = InAntal * ART:MinSaljPack
    .
 ELSE
    RetAntal = InAntal * ART:MinSaljPack
 .


GetArtikelLager PROCEDURE(ArtNr,LagerNr)
 CODE
 LAG:ArtNr = ArtNr
 LAG:LagerNr = LagerNr
 GET(Lager,LAG:K_ArtNrLagerNr)
 IF ErrorCode()
    CLEAR(LAG:Record)
 .







CheckKreditVardig   PROCEDURE           !Returnerar kreditkod

RetVarde BYTE 

 CODE
    RetVarde = TRUE
    LOOP            !Används bara som hållare för att enkelt ta sig ur strukturen med break
       GetSQLFile('select sum(tot) from kundres where kundnr = ''' & CLIP(KUN:Nummer) & ''' and (falldat < ''' & FORMAT(Today()-60,@D17) & ''' or tot < 0)')
       IF SQLFile.F1 > 1000
          RetVarde = FALSE
          BREAK
       .
       GetSQLFile('select sum(tot) from kundres where kundnr = ''' & CLIP(KUN:Nummer) & ''' and (falldat < ''' & FORMAT(Today()-30,@D17) & ''' or tot < 0)')
       IF SQLFile.F1 > KUN:KGransForfall30
          RetVarde = FALSE
          BREAK
       .
       IF KUN:KGrans > 0
          GetSQLFile('select sum(tot) from kundres where kundnr = ''' & CLIP(KUN:Nummer) & '''')
          IF SQLFile.f1 > KUN:KGrans - (OrderSumma*0.95)  !Vi minskar kreditgränsen med aktuell order, och tillåter 5% marginal
             RetVarde = FALSE
             BREAK
          .
       .
       BREAK            !Avsluta alltid loopen
    .


    RETURN(RetVarde)


OMIT('***CODE I DATABLOCKET***')

  
*/ 