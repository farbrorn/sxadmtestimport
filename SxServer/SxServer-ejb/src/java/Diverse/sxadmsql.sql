
CREATE OR REPLACE FUNCTION public."year"(timestamp)
  RETURNS double precision AS
 'select extract (year from $1)'
 language sql
 returns null on null input;

CREATE OR REPLACE FUNCTION public."month"(timestamp)
  RETURNS double precision AS
 'select extract (month from $1)'
 language sql
 returns null on null input;

CREATE OR REPLACE FUNCTION public."day"(timestamp)
  RETURNS double precision AS
 'select extract (day from $1)'
 language sql
 returns null on null input;

CREATE OR REPLACE FUNCTION public."ucase"(varchar)
  RETURNS varchar AS
 'select upper ($1)'
 language sql
 returns null on null input;




create table RAPPCOLUMNS (
RAPPID integer not null ,
COL integer not null ,
SQLLABEL varchar(512)  ,
LABEL varchar(512)  ,
GROUPBY smallint default      0  not null ,
DECIMALER integer  ,
HIDDEN smallint default      0  not null ,
GROUPBYHEADERTEXT varchar(1024)  ,
GROUPBYFOOTERTEXT varchar(1024)  ,
primary key (RAPPID , COL));


create table RAPPHUVUD (
RAPPID integer not null ,
BEHORIGHET varchar(10)  ,
KATEGORI varchar(30)  ,
UNDERGRUPP varchar(30)  ,
KORTBESKRIVNING varchar(100)  ,
REPORTRUBRIK varchar(1024)  ,
SQLFROM varchar(1024)  ,
ISDISTINCT smallint default      0  not null ,
CRTIME timestamp default CURRENT_TIMESTAMP   ,
primary key (RAPPID));


create table RAPPPROPS (
RAPPID integer not null ,
RAD integer not null ,
TYPE varchar(10) not null ,
SUMCOLUMN integer  ,
RESETCOLUMN integer  ,
SUMTYPE varchar(10)  ,
SUMTEXT varchar(512)  ,
WHEREPOS integer  ,
JAVATYPE varchar(10)  ,
NAME varchar(30)  ,
LABEL varchar(512)  ,
HIDDEN smallint  ,
DEFAULTVALUE varchar(128)  ,
primary key (RAPPID , RAD));




create table kundkontakt (
kontaktid integer not null, 
kundnr varchar(20) not null,
namn varchar(30),
tel varchar(30),
mobil varchar(30),
fax varchar(30),
adr1 varchar(30),
adr2 varchar(30),
adr3 varchar(30),
epost varchar(120),
ekonomi smallint default 0,
info smallint default 0,
primary key(kontaktid));

create table kundlogin (
loginnamn varchar(30) not null,
kontaktid integer not null unique,
loginlosen varchar(20) not null,
primary key (loginnamn));







create table ANVBEHORIGHET (
ANVANDARE varchar(30) not null ,
BEHORIGHET varchar(20) not null ,
primary key (ANVANDARE , BEHORIGHET));


create table ANVIS (
ANVISNR varchar(10) not null ,
ANVISNING varchar(254)  ,
primary key (ANVISNR));


create table ARTANNONS (
ANNONSID integer not null ,
RUBRIK varchar(60)  ,
TEXT varchar(4000)  ,
GRPID integer default           0   ,
INFOURL varchar(120)  ,
BILD varchar(120)  ,
primary key (ANNONSID));


create table ARTANNONSLANK (
GRPID integer not null ,
ANNONSID integer not null ,
SORTORDER integer default           0   ,
primary key (GRPID , ANNONSID));


create table ARTGRP (
GRPID integer not null ,
PREVGRPID integer  ,
RUBRIK varchar(60)  ,
TEXT varchar(4000)  ,
INFOURL varchar(120)  ,
SORTORDER integer default           0   ,
HTML varchar(2000)  ,
primary key (GRPID));


create table ARTGRPLANK (
GRPID integer not null ,
KLASID integer not null ,
SORTORDER integer default           0   ,
primary key (GRPID , KLASID));


create table ARTGRUPP (
NUMMER varchar(10)  ,
GRUPP varchar(10) );


create table ARTHAND (
ARTNR varchar(13) not null ,
DATUM date not null ,
TID time not null ,
ANVANDARE varchar(3)  ,
HANDELSE varchar(20)  ,
ANTECKNING varchar(254)  ,
primary key (ARTNR , DATUM , TID));


create table ARTIKEL (
NUMMER varchar(13) not null ,
NAMN varchar(35)  ,
LEV varchar(20) not null ,
BESTNR varchar(30) not null ,
ENHET varchar(3)  ,
UTPRIS float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS1 float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS2 float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS1_DAT date  ,
STAF_PRIS2_DAT date  ,
STAF_ANTAL1 float(16) default  0.000000000000000E+00  not null ,
STAF_ANTAL2 float(16) default  0.000000000000000E+00  not null ,
INPRIS float(16) default  0.000000000000000E+00  not null ,
RAB float(16) default  0.000000000000000E+00  not null ,
UTRAB float(16) default  0.000000000000000E+00  not null ,
INP_PRIS float(16) default  0.000000000000000E+00  not null ,
INP_RAB float(16) default  0.000000000000000E+00  not null ,
INP_FRAKTPROC float(16) default  0.000000000000000E+00  not null ,
INP_VALUTA varchar(3)  ,
INP_DATUM date  ,
KONTO varchar(6)  ,
RABKOD varchar(4)  ,
KOD1 varchar(4)  ,
PRISDATUM date  ,
INPDAT date  ,
TBIDRAG float(16) not null ,
REFNR varchar(20)  ,
VIKT float(16) default  0.000000000000000E+00  not null ,
VOLYM float(16) default  0.000000000000000E+00  not null ,
RORDAT date  ,
STRUKTNR varchar(20)  ,
FORPACK float(16) default  0.000000000000000E+00  not null ,
KOP_PACK float(16) default  0.000000000000000E+00  not null ,
KAMPFRDAT date  ,
KAMPTIDAT date  ,
KAMPPRIS float(16) default  0.000000000000000E+00   ,
KAMPPRISSTAF1 float(16) default  0.000000000000000E+00   ,
KAMPPRISSTAF2 float(16) default  0.000000000000000E+00   ,
INP_MILJO float(16) default  0.000000000000000E+00  not null ,
INP_FRAKT float(16) default  0.000000000000000E+00  not null ,
ANVISNR varchar(10)  ,
STAF_PRIS1NY float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS2NY float(16) default  0.000000000000000E+00  not null ,
INPRISNY float(16) default  0.000000000000000E+00  not null ,
INPRISNYDAT date  ,
INPRISNYRAB float(16) default  0.000000000000000E+00  not null ,
UTPRISNY float(16) not null ,
UTPRISNYAVBOKDAT date  ,
UTPRISNYDAT date  ,
RSK varchar(7)  ,
ENUMMER varchar(7)  ,
KAMPKUNDARTGRP smallint default      0  not null ,
KAMPKUNDGRP smallint default      0  not null ,
CN8 varchar(8)  ,
FRAKTVILLKOR smallint default      0   ,
DAGSPRIS smallint default      0   ,
HINDRAEXPORT smallint default      0   ,
UTGATTDATUM date  ,
MINSALJPACK float(16) default  0.000000000000000E+00   ,
STORPACK float(16) default  0.000000000000000E+00   ,
PRISGILTIGHETSTID integer default         730   ,
ONSKATTB integer default           0   ,
ONSKATTBSTAF1 integer default           0   ,
ONSKATTBSTAF2 integer default           0   ,
SALDA float(16) default  0.000000000000000E+00   ,
DIREKTLEV smallint default      0   ,
KATNAMN varchar(35)  ,
BILDARTNR varchar(13)  ,
PLOCKINSTRUKTION varchar(512)  ,
primary key (NUMMER));


create table ARTKLASE (
KLASID integer not null ,
RUBRIK varchar(60)  ,
TEXT varchar(4000)  ,
INFOURL varchar(120)  ,
FRAKTVILLKOR varchar(120)  ,
HTML varchar(2000)  ,
primary key (KLASID));


create table ARTKLASELANK (
KLASID integer not null ,
ARTNR varchar(13) not null ,
SORTORDER integer default           0   ,
primary key (KLASID , ARTNR));


create table ARTSEK (
LEVNR varchar(20) not null ,
ARTNR varchar(30) not null ,
REFNR varchar(30)  ,
NAMN varchar(35)  ,
BESKRIVNING varchar(35)  ,
ENH varchar(3)  ,
PRIS float(16) default  0.000000000000000E+00  not null ,
RABKOD varchar(10)  ,
DATUM date  ,
GILTIGTTILL date  ,
ID integer default           0   ,
RAB float(16) default  0.000000000000000E+00   ,
ENUMMER varchar(7)  ,
RSK varchar(7)  ,
primary key (LEVNR , ARTNR));


create table ARTSTAT (
ARTNR varchar(13) not null ,
AR smallint not null ,
MAN smallint not null ,
SALDA float(16) not null ,
TBIDRAG float(16) not null ,
primary key (ARTNR , AR , MAN));


create table ARTSTRUKT (
NUMMER varchar(20) not null ,
ARTNR varchar(13) not null ,
NAMN varchar(35)  ,
PRIS float(16) default  0.000000000000000E+00  not null ,
RAB float(16) default  0.000000000000000E+00  not null ,
ENH varchar(3)  ,
ANTAL float(16) default  0.000000000000000E+00  not null ,
NETTO float(16) default  0.000000000000000E+00  not null ,
DATUM date  ,
primary key (NUMMER , ARTNR));


create table BEHORIGHET (
BEHORIGHET varchar(20) not null ,
BESKRIVNING varchar(60)  ,
primary key (BEHORIGHET));


create table BEST1 (
BESTNR integer not null ,
LEVNR varchar(20) not null ,
LEVNAMN varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
VAR_REF varchar(30)  ,
ER_REF varchar(30)  ,
LEVERANS varchar(30)  ,
MARKE varchar(30)  ,
DATUM date  ,
SUMMA float(16) default  0.000000000000000E+00  not null ,
BEKRDAT date  ,
FRAKTFRITT float(16) default  0.000000000000000E+00  not null ,
MOTTAGARFRAKT smallint default      0  not null ,
LEVVILLKOR1 varchar(60)  ,
LEVVILLKOR2 varchar(60)  ,
LEVVILLKOR3 varchar(60)  ,
BESTEJPRIS smallint default      0  not null ,
LAGERNR smallint not null ,
LEVADR0 varchar(30)  ,
ORDERNR integer default           0  not null ,
AUTOBESTALLD smallint default      0  not null ,
SKICKASOM varchar(5)  ,
STATUS varchar(10)  ,
MEDDELANDE varchar(1000)  ,
SAKERHETSKOD integer default           0   ,
ANTALFELINLOGGNINGAR integer default           0   ,
SXSERVSANDFORSOK smallint default      0   ,
PAMINDAT date  ,
ANTALPAMIN integer default           0   ,
primary key (BESTNR));


create table BEST2 (
BESTNR integer not null ,
RAD smallint not null ,
ENH varchar(3)  ,
ARTNR varchar(13) not null ,
ARTNAMN varchar(35)  ,
BARTNR varchar(30)  ,
BEST float(16) default  0.000000000000000E+00  not null ,
PRIS float(16) default  0.000000000000000E+00  not null ,
RAB float(16) default  0.000000000000000E+00  not null ,
SUMMA float(16) default  0.000000000000000E+00  not null ,
TEXT varchar(60)  ,
BEKRDAT date  ,
INP_MILJO float(16) default  0.000000000000000E+00  not null ,
INP_FRAKT float(16) default  0.000000000000000E+00  not null ,
INP_FRAKTPROC float(16) default  0.000000000000000E+00  not null ,
STJID integer default           0  not null ,
primary key (BESTNR , RAD));


create table BESTHAND (
BESTNR integer not null ,
DATUM date not null ,
TID time not null ,
ANVANDARE varchar(3)  ,
HANDELSE varchar(40)  ,
INLEVID integer default           0  not null ,
primary key (BESTNR , DATUM , TID));


create table BETJOUR (
RANTFAKT smallint  ,
FAKTNR integer not null ,
KUNDNR varchar(20) not null ,
NAMN varchar(30)  ,
BET float(16) not null ,
BETDAT date not null ,
BETSATT char(1) not null ,
BONSUMMA float(16) not null ,
TALLOPNR integer not null ,
TALDATUM date not null ,
AR smallint not null ,
MAN smallint not null ,
PANTSATT smallint default      0   ,
BETSATTKONTO smallint default      0   ,
INKASSOSTATUS varchar(10)  ,
FELBETTYP varchar(10)  ,
FELBETAVBOKADDATUM date  ,
primary key (FAKTNR , BETSATT , TALDATUM , TALLOPNR));


create table BILDER (
NAMN varchar(100) not null ,
TYP varchar(10)  ,
BILDDATA bytea  ,
primary key (NAMN));


create table BOKAR (
FT smallint not null ,
AR smallint not null ,
OPPNATDATUM date  ,
AVSLUTATDATUM date  ,
primary key (FT , AR));


create table BOKBUDGET (
FT smallint not null ,
AR smallint not null ,
KONTONR varchar(6) not null ,
KST varchar(2) not null ,
SUMMA decimal(12,2) default           0.00   ,
primary key (FT , AR , KONTONR , KST));


create table BOKDAT (
FT smallint not null ,
NAMN varchar(30)  ,
STARTMAN smallint default      1   ,
SNABB1TEXT varchar(10)  ,
SNABB1KONTODEBET varchar(6)  ,
SNABB1KONTOKREDIT varchar(6)  ,
SNABB1PROC decimal(5,2) default    0.00   ,
SNABB2TEXT varchar(10)  ,
SNABB2KONTODEBET varchar(6)  ,
SNABB2KONTOKREDIT varchar(6)  ,
SNABB2PROC decimal(5,2) default    0.00   ,
SNABB3TEXT varchar(10)  ,
SNABB3KONTODEBET varchar(6)  ,
SNABB3KONTOKREDIT varchar(6)  ,
SNABB3PROC decimal(5,2) default    0.00   ,
SNABB4TEXT varchar(10)  ,
SNABB4KONTODEBET varchar(6)  ,
SNABB4KONTOKREDIT varchar(6)  ,
SNABB4PROC decimal(5,2) default    0.00   ,
SNABB5TEXT varchar(10)  ,
SNABB5KONTODEBET varchar(6)  ,
SNABB5KONTOKREDIT varchar(6)  ,
SNABB5PROC decimal(5,2) default    0.00   ,
ORGNR varchar(20)  ,
primary key (FT));


create table BOKKST (
FT smallint not null ,
KST varchar(2) not null ,
NAMN varchar(30)  ,
primary key (FT , KST));


create table BOKORD (
KONTO varchar(6) not null ,
FAKTNR integer not null ,
TYP varchar(1) not null ,
SUMMA float(16) not null ,
KUNDNR varchar(20)  ,
NAMN varchar(30)  ,
DATUM date not null ,
AR smallint not null ,
MAN smallint not null ,
primary key (FAKTNR , KONTO , DATUM , TYP));


create table BOKVER1 (
FT smallint not null ,
AR smallint not null ,
VER integer not null ,
PER smallint default      0   ,
DATUM date  ,
NOTERING varchar(60)  ,
primary key (FT , AR , VER));


create table BOKVER2 (
FT smallint not null ,
AR smallint not null ,
VER integer not null ,
RAD smallint not null ,
PER smallint default      0   ,
KONTONR varchar(6)  ,
KST varchar(2)  ,
DEBET decimal(12,2) default           0.00   ,
KREDIT decimal(12,2) default           0.00   ,
primary key (FT , AR , VER , RAD));


create table BONUS (
KUND varchar(20) not null ,
DATUM date  ,
FAKTURA integer not null ,
BONUS float(16) not null ,
ID smallint default      0  not null ,
primary key (FAKTURA , ID));


create table BONUSBET (
KUND varchar(20) not null ,
UTDATUM date  ,
FAKTURA integer not null ,
BONUS float(16) default  0.000000000000000E+00   ,
UTFAKTURA integer default           0   ,
ID smallint not null ,
primary key (KUND , FAKTURA , ID));


create table DISTRIKT (
DISTRIKT smallint not null ,
NAMN varchar(10)  ,
primary key (DISTRIKT));


create table FAKTDAT (
BESTNR integer not null ,
LEVLOPNR integer not null ,
RETURNR integer not null ,
ANMNR integer not null ,
LEVRETURNR integer not null ,
DOKUMENTNR integer not null ,
OFFERTNR integer not null ,
DATUM date not null ,
VERSION integer not null ,
FT integer default           0  not null ,
primary key (FT));



/* Tabellen ska inte använsdas, ligger bara kvar sedan gammalt */
create table FAKTJOUR (
RANTFAKT smallint default      0   ,
FAKTNR integer not null ,
DATUM date not null ,
TOT float(16) not null ,
NTOT float(16) not null ,
NETTO float(16) not null ,
KUNDNR varchar(20)  ,
NAMN varchar(30)  ,
FAKTOR smallint not null ,
AR smallint not null ,
MAN smallint not null );


create table FAKTORFE (
FAKTNR integer not null ,
DATUM date not null ,
TOT float(16) not null ,
KUNDNR varchar(20) not null ,
NAMN varchar(30)  ,
FALLDAT date  ,
TYP varchar(10)  ,
AVBOKADDATUM date  ,
primary key (FAKTNR , DATUM));


create table FAKTORUT (
FAKTNR integer not null ,
DATUM date  ,
TOT float(16) not null ,
KUNDNR varchar(20) not null ,
NAMN varchar(30)  ,
FALLDAT date  ,
primary key (FAKTNR));


create table FAKTPANT (
BANK varchar(5) not null ,
ID integer not null ,
DATUM date  ,
TID time  ,
ANVANDARE varchar(3)  ,
FRFAKTNR integer not null ,
TIFAKTNR integer not null ,
ANTALDEBET integer default           0   ,
ANTALKREDIT integer default           0   ,
BELOPPDEBET float(16) default  0.000000000000000E+00   ,
BELOPPKREDIT float(16) default  0.000000000000000E+00   ,
primary key (BANK , ID));


create table FAKTURA1 (
FAKTNR integer not null ,
KUNDNR varchar(20) not null ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
DATUM date not null ,
SALJARE varchar(34)  ,
REFERENS varchar(30)  ,
MARKE varchar(30)  ,
MOMS smallint default      0  not null ,
KTID smallint default      0  not null ,
RANTA float(16) default  0.000000000000000E+00  not null ,
BONUS smallint default      0  not null ,
MOTTAGARFRAKT smallint not null ,
LEVVILLKOR varchar(30)  ,
FRAKTKUNDNR varchar(30)  ,
FRAKTBOLAG varchar(30)  ,
FRAKTFRIGRANS float(16) default  0.000000000000000E+00  not null ,
LEVDAT date  ,
ORDERDAT date  ,
ORDERNR integer default           0  not null ,
FAKTOR smallint default      0  not null ,
TEXT1 varchar(40)  ,
TEXT2 varchar(40)  ,
TEXT3 varchar(40)  ,
TEXT4 varchar(40)  ,
TEXT5 varchar(40)  ,
FAKTORTEXT1 varchar(60)  ,
FAKTORTEXT2 varchar(60)  ,
FAKTORTEXT3 varchar(60)  ,
RANTFAKT smallint default      0  not null ,
T_NETTO float(16) default  0.000000000000000E+00  not null ,
T_MOMS float(16) default  0.000000000000000E+00  not null ,
T_ORUT float(16) default  0.000000000000000E+00  not null ,
T_ATTBETALA float(16) default  0.000000000000000E+00  not null ,
T_INNETTO float(16) default  0.000000000000000E+00  not null ,
LAGERNR smallint not null ,
DIREKTLEVNR integer default           0  not null ,
MOMSPROC float(16) default  0.000000000000000E+00  not null ,
INKASSOSTATUS varchar(10)  ,
INKASSODATUM date  ,
primary key (FAKTNR));


create table FAKTURA2 (
FAKTNR integer not null ,
POS smallint not null ,
PRISNR smallint default      0  not null ,
ARTNR varchar(13)  ,
RAB float(16) not null ,
LEV float(16) default  0.000000000000000E+00  not null ,
TEXT varchar(60)  ,
PRIS float(16) default  0.000000000000000E+00  not null ,
SUMMA float(16) default  0.000000000000000E+00  not null ,
KONTO varchar(6)  ,
NETTO float(16) default  0.000000000000000E+00  not null ,
ENH varchar(3)  ,
NAMN varchar(35)  ,
BON_NR integer default           0   ,
ORDERNR integer not null ,
RANTAFAKTURANR integer default           0   ,
RANTAFALLDATUM date  ,
RANTABETALDATUM date  ,
RANTABETALBELOPP float(16) default  0.000000000000000E+00   ,
RANTAPROC float(16) default  0.000000000000000E+00   ,
STJID integer default           0   ,
primary key (FAKTNR , POS));


create table FDORDERNR (
ORDERNR integer not null ,
primary key (ORDERNR));


create table FRAKTVILLKOR (
ID smallint not null ,
NAMN varchar(30)  ,
BESKRIVNING varchar(120)  ,
primary key (ID));


create table FUPPG (
DROJ float(16) not null ,
RANTKTID integer not null ,
TXT1 varchar(60)  ,
TXT2 varchar(60)  ,
TXT3 varchar(60)  ,
TXT4 varchar(60)  ,
TXT5 varchar(60)  ,
KRANGEL smallint not null ,
SYSDAT smallint not null ,
BONUS smallint not null ,
BONUSPROC1 smallint not null ,
BONUSPROC2 smallint not null ,
BON_TID smallint not null ,
NAMN varchar(30) not null ,
FIKON float(16)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
POSTGIRO varchar(12)  ,
BANKGIRO varchar(12)  ,
REGNR varchar(30)  ,
SATE varchar(30)  ,
TEL varchar(30)  ,
FAX varchar(30)  ,
MOBIL varchar(30)  ,
EMAIL varchar(30)  ,
HEMSIDA varchar(30)  ,
BEST_FRAKTBOLAG1 varchar(30)  ,
BEST_FRAKTBOLAG2 varchar(30)  ,
BEST_FRAKTBOLAG3 varchar(30)  ,
BEST_PALLREG1 varchar(30)  ,
BEST_PALLREG2 varchar(30)  ,
BEST_PALLREG3 varchar(30)  ,
MOMS1 float(16) not null ,
MOMS2 float(16) not null ,
MOMS3 float(16) not null ,
MOMS1K varchar(6) not null ,
MOMS2K varchar(6) not null ,
MOMS3K varchar(6) not null ,
MOMS1K2 varchar(6) not null ,
MOMS2K2 varchar(6) not null ,
MOMS3K2 varchar(6) not null ,
VARUK varchar(6) not null ,
LEVK varchar(6) not null ,
KUNDFK varchar(6) not null ,
KASSA varchar(6) not null ,
BANK varchar(6) not null ,
POST varchar(6) not null ,
ORUTK varchar(6) not null ,
RANTAK varchar(6) not null ,
BON_TEXT1 varchar(40)  ,
BON_TEXT2 varchar(40)  ,
BON_TEXT3 varchar(40)  ,
BON_TEXT4 varchar(40)  ,
BON_TEXT5 varchar(40)  ,
EB_TEXT1 varchar(40)  ,
EB_TEXT2 varchar(40)  ,
EB_TEXT3 varchar(40)  ,
EB_TEXT4 varchar(40)  ,
EB_TEXT5 varchar(40)  ,
TEMP_TEXT1 varchar(40)  ,
TEMP_TEXT2 varchar(40)  ,
TEMP_TEXT3 varchar(40)  ,
TEMP_TEXT4 varchar(40)  ,
TEMP_TEXT5 varchar(40)  ,
TEMP_TEXT smallint default      0   ,
FAK_B_TEXT1 varchar(80)  ,
FAK_B_TEXT2 varchar(80)  ,
FAK_B_TEXT3 varchar(80)  ,
FAK_B_TEXT4 varchar(80)  ,
FAK_B_TEXT5 varchar(80)  ,
FAKTORTEXT1 varchar(60)  ,
FAKTORTEXT2 varchar(60)  ,
FAKTORTEXT3 varchar(60)  ,
PRN_ORDER varchar(32)  ,
PRN_FAKTURA varchar(32)  ,
PRN_BANK varchar(32)  ,
PRN_POST varchar(32)  ,
PRN_OVRIGT varchar(32)  ,
PRN_FAX varchar(32)  ,
PRN_EMAIL varchar(32)  ,
PRN_ORDERKOPIOR smallint  ,
PRN_FAKTURAKOPIOR smallint  ,
PRN_POSTKOPIOR smallint  ,
PRN_BANKKOPIOR smallint  ,
PRN_OVRIGTKOPIOR smallint  ,
PRN_FAXKOPIOR smallint  ,
PRN_EMAILKOPIOR smallint  ,
POMINDAGAR smallint  ,
BEST_TEXT1 varchar(60)  ,
BEST_TEXT2 varchar(60)  ,
BEST_TEXT3 varchar(60)  ,
PGKUNDNR varchar(5)  ,
MINRANTA float(16) default  0.000000000000000E+00   ,
PANTSATTFAKTUROR smallint default      0   ,
PANTSATTTEXT1 varchar(60)  ,
POMINGANGER smallint default      0   ,
PANTSATTTEXT2 varchar(60)  ,
PANTSATTTEXT3 varchar(60)  ,
FAK_OBSTEXT1 varchar(60)  ,
primary key (NAMN));


create table INLEV1 (
ID integer not null ,
DATUM date  ,
BESTNR integer not null ,
LEVNR varchar(20) not null ,
MARKE varchar(30)  ,
PRISKOLLDAT date  ,
LAGERNR smallint not null ,
ORDERNR integer not null ,
LEVNAMN varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
LEVADR0 varchar(30)  ,
primary key (ID));


create table INLEV2 (
ID integer not null ,
RAD smallint not null ,
ARTNR varchar(13) not null ,
ARTNAMN varchar(35)  ,
ANTAL float(16) not null ,
ENH varchar(3)  ,
PRIS float(16) not null ,
RAB float(16) not null ,
TEXT varchar(60)  ,
STJID integer not null ,
primary key (ID , RAD));


create table KALENDER (
F_DAT date not null ,
T_DAT date  ,
F_TID time not null ,
T_TID time  ,
KMEMO varchar(20)  ,
primary key (F_DAT , F_TID));


create table KONTOREG (
KONTO varchar(6) not null ,
NAMN varchar(30) not null ,
BEGARKST smallint default      0   ,
primary key (KONTO));


create table KUND (
NUMMER varchar(20) not null ,
NAMN varchar(30) not null ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LNAMN varchar(30)  ,
LADR2 varchar(30)  ,
LADR3 varchar(30)  ,
REF varchar(30)  ,
SALJARE varchar(30) not null ,
TEL varchar(30)  ,
BILTEL varchar(30)  ,
FAX varchar(30)  ,
SOKARE varchar(30)  ,
EMAIL varchar(60)  ,
HEMSIDA varchar(60)  ,
GRUPP varchar(4)  ,
GRUPP2 varchar(4)  ,
K_DAG smallint default      0  not null ,
K_TID time  ,
K_DATUM date  ,
REGNR varchar(20)  ,
RANTFAKT smallint default      0  not null ,
FAKTOR smallint default      0  not null ,
KGRANS float(16) default  0.000000000000000E+00  not null ,
TOT float(16) default  0.000000000000000E+00  not null ,
NTOT float(16) default  0.000000000000000E+00  not null ,
OBET float(16) default  0.000000000000000E+00  not null ,
NETTO float(16) default  0.000000000000000E+00  not null ,
BTID integer default           0  not null ,
FAKTUROR integer default           0  not null ,
TBIDRAG float(16) default  0.000000000000000E+00  not null ,
KTID smallint default      0  not null ,
FAKTDAT date  ,
NETTOLST varchar(10)  ,
BONUS smallint default      0  not null ,
ELKUND smallint default      0  not null ,
VVSKUND smallint default      0  not null ,
OVRIGKUND smallint default      0  not null ,
INSTALLATOR smallint default      0  not null ,
BUTIK smallint default      0  not null ,
INDUSTRI smallint default      0  not null ,
OEM smallint default      0  not null ,
GROSSIST smallint default      0  not null ,
FLAGGA1 smallint default      0  not null ,
FLAGGA2 smallint default      0  not null ,
FLAGGA3 smallint default      0  not null ,
FLAGGA4 smallint default      0  not null ,
FLAGGA5 smallint default      0  not null ,
FLAGGA6 smallint default      0  not null ,
FLAGGA7 smallint default      0  not null ,
FLAGGA8 smallint default      0  not null ,
FLAGGA9 smallint default      0  not null ,
FLAGGA10 smallint default      0  not null ,
LEVVILLKOR varchar(30)  ,
MOTTAGARFRAKT smallint default      0  not null ,
FRAKTBOLAG varchar(30)  ,
FRAKTKUNDNR varchar(20)  ,
FRAKTFRIGRANS float(16) default  0.000000000000000E+00  not null ,
FAKTORINGDAGAR smallint default      0  not null ,
ANT1 varchar(60)  ,
ANT2 varchar(60)  ,
ANT3 varchar(60)  ,
DISTRIKT smallint default      0  not null ,
VAKUND smallint default      0  not null ,
FASTIGHETSKUND smallint default      0  not null ,
BASRAB float(16) default  0.000000000000000E+00  not null ,
REGDAT date  ,
GOLVKUND smallint default      0  not null ,
EJFAKTURERBAR smallint default      0  not null ,
SKRIVFAKTURARSKENR smallint default      0  not null ,
SARFAKTURA smallint default      0  not null ,
MOMSFRI smallint default      0  not null ,
VECKOLEVDAG smallint default      0  not null ,
KGRANSFORFALL30 float(16) default  0.000000000000000E+00   ,
KRAVORDERMARKE smallint default      0   ,
LINJENR1 varchar(3)  ,
LINJENR2 varchar(3)  ,
LINJENR3 varchar(3)  ,
primary key (NUMMER));


create table KUNDAKTINFO (
ID integer not null ,
TYP varchar(10)  ,
AKTNR varchar(20)  ,
RUBRIK varchar(100)  ,
TXT varchar(2000)  ,
CRTS timestamp default CURRENT_TIMESTAMP   ,
primary key (ID));


create table KUNDAKTIVITET (
AKTNR varchar(20) not null ,
DATUM date  ,
BESKRIVNING varchar(100)  ,
LEVVALKOMSTMAIL varchar(2000)  ,
primary key (AKTNR));


create table KUNDAKTLANK (
KUNDNR varchar(20) not null ,
NAMN varchar(30)  ,
AKTNR varchar(20) not null ,
ANTAL integer  ,
PRELIMINAR smallint default      0   ,
DATUM date  ,
INFOTEXT varchar(100)  ,
primary key (KUNDNR , AKTNR));


create table KUNDAKTLEVLANK (
AKTNR varchar(20) not null ,
LKID integer default           0  not null ,
STATUS varchar(10)  ,
CRTS timestamp default CURRENT_TIMESTAMP   ,
primary key (AKTNR , LKID));


create table KUNDGRP (
GRPNR varchar(20) not null ,
BESKRIVNING varchar(100)  ,
primary key (GRPNR));


create table KUNDGRPLANK (
KUNDNR varchar(20) not null ,
GRPNR varchar(20) not null ,
primary key (KUNDNR , GRPNR));


create table KUNDRES (
RANTFAKT smallint not null ,
FAKTNR integer not null ,
KUNDNR varchar(20) not null ,
NAMN varchar(30) not null ,
TOT float(16) not null ,
NETTO float(16) not null ,
DATUM date  ,
FALLDAT date not null ,
PDAT1 date  ,
PDAT2 date  ,
PDAT3 date  ,
FAKTOR smallint not null ,
FAKTORDAT date  ,
BONUS smallint not null ,
MEDELMOMSPROC float(16) not null ,
INKASSODATUM date  ,
ANTALPAMINNELSER integer default           0   ,
INKASSOSTATUS varchar(10)  ,
PANTSATT smallint default      0   ,
primary key (FAKTNR));


create table KUNHAND (
KUNDNR varchar(20) not null ,
DATUM date not null ,
TID time not null ,
ANVANDARE varchar(3)  ,
HANDELSE varchar(20)  ,
ANTECKNING varchar(254)  ,
primary key (KUNDNR , DATUM , TID));


create table KUNRAB (
KUNDNR varchar(20) not null ,
RABKOD varchar(4) not null ,
KOD1 varchar(4) not null ,
RAB float(16) not null ,
DATUM date  ,
primary key (KUNDNR , RABKOD , KOD1));


create table KUNSTAT (
KUNDNR varchar(20) not null ,
AR smallint not null ,
MAN smallint not null ,
TOT float(16) not null ,
TBIDRAG float(16) not null ,
BTID integer not null ,
FAKTUROR integer not null ,
BETAL smallint not null ,
TOTBET float(16) not null ,
RANTA float(16) not null ,
primary key (KUNDNR , AR , MAN));


create table LAGER (
ARTNR varchar(13) not null ,
LAGERNR smallint not null ,
ILAGER float(16) not null ,
BESTPUNKT float(16) not null ,
MAXLAGER float(16) not null ,
BEST float(16) not null ,
IORDER float(16) not null ,
LAGERPLATS varchar(20)  ,
HINDRAFILIALBEST smallint default      0   ,
primary key (LAGERNR , ARTNR));


create table LAGERHAND (
ARTNR varchar(13) not null ,
LAGERNR smallint not null ,
DATUM date not null ,
TID time not null ,
ANVANDARE varchar(3)  ,
HANDELSE varchar(20)  ,
ORDERNR integer default           0   ,
STJID integer default           0   ,
GAMMALTILAGER float(16) default  0.000000000000000E+00   ,
NYTTILAGER float(16) default  0.000000000000000E+00   ,
FORANDRING float(16) default  0.000000000000000E+00   ,
BESTNR integer default           0   ,
primary key (ARTNR , LAGERNR , DATUM , TID));


create table LAGERID (
LAGERNR smallint not null ,
BNAMN varchar(30)  ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
TEL varchar(30)  ,
FAX varchar(30)  ,
EMAIL varchar(60)  ,
CHECKMEDDELIDLETIME integer not null ,
SKRIVFOLJESEDEL smallint not null ,
MAXLAGERVARDE float(16) default  0.000000000000000E+00   ,
primary key (LAGERNR));


create table LEV (
NUMMER varchar(20) not null ,
NAMN varchar(30) not null ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
TEL varchar(30)  ,
BILTEL varchar(30)  ,
FAX varchar(30)  ,
HEMSIDA varchar(60)  ,
EMAIL varchar(60)  ,
INKOPARE varchar(30) not null ,
REF varchar(30)  ,
REFADR1 varchar(30)  ,
REFADR2 varchar(30)  ,
REFADR3 varchar(30)  ,
REFTEL varchar(30)  ,
REFBILTEL varchar(30)  ,
REFFAX varchar(30)  ,
REFHEMSIDA varchar(60)  ,
REFEMAIL varchar(60)  ,
KTID smallint default      0  not null ,
FRAKTFRITT float(16) default  0.000000000000000E+00  not null ,
MOTTAGARFRAKT smallint default      0  not null ,
LEVVILLKOR1 varchar(60)  ,
LEVVILLKOR2 varchar(60)  ,
LEVVILLKOR3 varchar(60)  ,
LEVBESTMEDD1 varchar(60)  ,
LEVBESTMEDD2 varchar(60)  ,
LEVBESTMEDD3 varchar(60)  ,
BESTEJPRIS smallint default      0  not null ,
VALUTA varchar(3)  ,
TOT float(16) default  0.000000000000000E+00  not null ,
OBET float(16) default  0.000000000000000E+00  not null ,
POST varchar(10)  ,
BANK varchar(10)  ,
KNUMMER varchar(20)  ,
FRAKT float(16) default  0.000000000000000E+00  not null ,
ANT1 varchar(60)  ,
ANT2 varchar(60)  ,
ANT3 varchar(60)  ,
ANT varchar(254)  ,
LANDSKOD varchar(2)  ,
EMAILORDER1 varchar(60)  ,
EMAILORDER2 varchar(60)  ,
primary key (NUMMER));


create table LEVANM (
ANMNR integer not null ,
LEVNR varchar(20) not null ,
DATUM date not null ,
VARREF varchar(30)  ,
ERREF varchar(30)  ,
FAKTURANR varchar(20)  ,
ATGARD varchar(30)  ,
TEXT1 varchar(80)  ,
TEXT2 varchar(80)  ,
TEXT3 varchar(80)  ,
TEXT4 varchar(80)  ,
TEXT5 varchar(80)  ,
primary key (ANMNR));


create table LEVBET (
LOPNR integer not null ,
LEV varchar(20)  ,
SUMMA float(16) not null ,
DATUM date not null ,
BETSAT varchar(1)  ,
AR smallint not null ,
MAN smallint not null ,
ANVANDARE varchar(3)  ,
primary key (LOPNR , DATUM));


create table LEVBOK (
LOPNR integer not null ,
DATUM date not null ,
TYP varchar(1) not null ,
LEV varchar(20)  ,
KONTO varchar(6) not null ,
SUMMA float(16) not null ,
AR smallint not null ,
MAN smallint not null ,
primary key (LOPNR , KONTO , DATUM , TYP));


create table LEVFAKT (
LEV varchar(20) not null ,
LOPNR integer not null ,
DATUM date not null ,
FALLDAT date  ,
SUMMA float(16) not null ,
FAKTURANR varchar(10)  ,
KONTO char(6)  ,
BETSUMMA float(16) not null ,
BETDAT date  ,
BETSAT char(1)  ,
ANT1 varchar(60)  ,
ANT2 varchar(60)  ,
ANT3 varchar(60)  ,
OCRNR varchar(27)  ,
BOKDATUM date  ,
REGDATUM date  ,
ANVANDARE varchar(3)  ,
primary key (LOPNR));


create table LEVFAKTBESTLANK (
LOPNR integer default           0  not null ,
BESTNR integer default           0   ,
INLEVID integer default           0  not null ,
AVSTAMD smallint default      0   ,
primary key (LOPNR , INLEVID));


create table LEVFAKTHAND (
LOPNR integer default           0  not null ,
HANDELSE varchar(20)  ,
ANVANDARE varchar(3)  ,
DATUM date not null ,
TID time not null ,
ANTECKNING varchar(400)  ,
primary key (LOPNR , DATUM , TID));


create table LEVJOUR (
LOPNR integer not null ,
LEV varchar(20)  ,
SUMMA float(16) not null ,
DATUM date not null ,
AR smallint not null ,
MAN smallint not null ,
primary key (LOPNR));


create table LEVKONTAKT (
LKID integer not null ,
LEVNR varchar(20)  ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
POSTNR varchar(10)  ,
ORT varchar(30)  ,
TEL varchar(30)  ,
MOBIL varchar(30)  ,
FAX varchar(30)  ,
EPOST varchar(120)  ,
CRTS timestamp default CURRENT_TIMESTAMP   ,
LOSEN varchar(20)  ,
primary key (LKID));


create table LEVRAB (
LEVNR varchar(20) not null ,
RABKOD varchar(10) not null ,
RAB float(16) default  0.000000000000000E+00   ,
DATUM date  ,
primary key (LEVNR , RABKOD));


create table LEVRES (
LEV varchar(20) not null ,
LOPNR integer not null ,
DATUM date  ,
FALLDAT date not null ,
SUMMA float(16) not null ,
FAKTURANR varchar(10)  ,
OCRNR varchar(27)  ,
SPARR smallint default      0   ,
primary key (LOPNR));


create table LEVSTAT (
LEVNR varchar(20) not null ,
AR smallint not null ,
MAN smallint not null ,
FTOT float(16) not null ,
FTBIDRAG float(16) not null ,
TOT float(16) not null ,
primary key (LEVNR , AR , MAN));


create table LONANST (
ANSTNR varchar(10) not null ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
POSTNR varchar(5)  ,
ORT varchar(25)  ,
TEL varchar(30)  ,
MOBIL varchar(30)  ,
PERSONNR varchar(13)  ,
SKATTETABELL smallint default      0   ,
SKATTEKOLUMN smallint default      0   ,
MINSKATTPROC decimal(4,2) default   0.00   ,
EXTRASKATT decimal(9,2) default        0.00   ,
SOCID integer default           0   ,
AVTALSID integer default           0   ,
ANSTALLPROC decimal(5,2) default    0.00   ,
LONPROC decimal(5,2) default    0.00   ,
BANK varchar(30)  ,
BANKKONTO varchar(30)  ,
KSTALLE smallint default      0   ,
ANSTTYP varchar(10)  ,
ANSTDATUM date  ,
ANSTTYPDATUM date  ,
primary key (ANSTNR));


create table LONAVTAL (
AVTALSID integer not null ,
NAMN varchar(30)  ,
LONTYP varchar(20)  ,
LON decimal(9,2) default        0.00   ,
LONTYPNR varchar(10)  ,
OTTILLAGG decimal(5,2) default    0.00   ,
OBTILLAGG decimal(5,2) default    0.00   ,
SEMESTERDAGAR decimal(5,2) default    0.00   ,
SEMESTERPROC decimal(5,2) default    0.00   ,
SEMESTERLONTYPNR varchar(10)  ,
primary key (AVTALSID));


create table LONDAT (
DAGARPERMAN decimal(4,2) default   0.00  not null ,
TIMMARPERDAG decimal(4,2) default   0.00   ,
SOCPROC1 decimal(4,2) default   0.00   ,
SOCPROC2 decimal(4,2) default   0.00   ,
SOCPROC3 decimal(4,2) default   0.00   ,
SOCPROC4 decimal(4,2) default   0.00   ,
SKATTETYPNR varchar(10)  ,
SJUKLON1PROC decimal(4,2) default   0.00   ,
SJUKLON1TYPNR varchar(10)  ,
SJUKLON2PROC decimal(4,2) default   0.00   ,
SJUKLON2TYPNR varchar(10)  ,
SJUKLON3PROC decimal(4,2) default   0.00   ,
SJUKLON3TYPNR varchar(10)  ,
SJUKKARENSDAGAR smallint default      0   ,
KONTONRLONUTBET varchar(6)  ,
primary key (DAGARPERMAN));


create table LONPERIOD (
AR smallint not null ,
MAN smallint not null ,
AVSLUTADDATUM date  ,
primary key (AR , MAN));


create table LONSKATTETABELL (
LONPERIOD smallint default      0  not null ,
TABELL smallint default      0  not null ,
FRAN integer default           0  not null ,
TILL integer default           0   ,
INLASTDATUM date  ,
KOLUMN1 integer default           0   ,
KOLUMN2 integer default           0   ,
KOLUMN3 integer default           0   ,
KOLUMN4 integer default           0   ,
KOLUMN5 integer default           0   ,
KOLUMNTYP varchar(1)  ,
primary key (LONPERIOD , TABELL , FRAN));


create table LONTID (
ANSTNR varchar(10) not null ,
DATUM date not null ,
ORSAK varchar(10)  ,
TIMMAR decimal(3,1) default   0.0   ,
AVBOKADDATUM date  ,
AVBOKADAR smallint default      0   ,
AVBOKADMAN smallint default      0   ,
SJUKERSATTNING smallint default      0   ,
SJUKPERIODSTART smallint default      0   ,
FRANVARO smallint default      0   ,
SEMESTERAR smallint default      0   ,
OVERTIDPROC decimal(5,2) default    0.00   ,
SJUKERSATTNINGPROC decimal(5,2) default    0.00   ,
primary key (ANSTNR , DATUM));


create table LONTYP (
LONTYPNR varchar(10) not null ,
NAMN varchar(30)  ,
ENHET varchar(3)  ,
KONTOTYP varchar(10) not null ,
NORMALPRIS decimal(9,2) default        0.00   ,
KONTONR varchar(4)  ,
primary key (LONTYPNR));


create table LONUT1 (
ANSTNR varchar(10) not null ,
AR smallint not null ,
MAN smallint not null ,
NOTERING varchar(200)  ,
GODKANNDDATUM date  ,
UTSKRIVENDATUM date  ,
primary key (ANSTNR , AR , MAN));


create table LONUT2 (
ANSTNR varchar(10) not null ,
AR smallint not null ,
MAN smallint not null ,
RAD integer not null ,
LONTYPNR varchar(10)  ,
LONTYPNAMN varchar(30)  ,
ANTAL decimal(6,1) default      0.0   ,
ENHET varchar(3)  ,
SKATTPLIKT smallint default      0   ,
SOCPROC decimal(4,2) default   0.00   ,
INFO varchar(60)  ,
KONTOTYP varchar(10) not null ,
KSTALLE smallint default      0   ,
KONTONR varchar(4)  ,
APRIS decimal(9,2) default        0.00   ,
SUMMA decimal(9,2) default        0.00   ,
primary key (ANSTNR , AR , MAN , RAD));




create table MASSUTSTALL (
MASSID varchar(20) not null ,
EPOST varchar(120) not null ,
FORETAG varchar(40)  ,
KONTAKT varchar(40)  ,
LEVNR varchar(20)  ,
primary key (MASSID , EPOST));


create table MEDDEL (
SALJARE varchar(30) not null ,
AVLAST smallint not null ,
DATUM date not null ,
TID time not null ,
AVSANDARE varchar(30)  ,
TEXT1 varchar(60)  ,
TEXT2 varchar(60)  ,
primary key (SALJARE , AVLAST , DATUM , TID));


create table NETTOPRI (
LISTA varchar(10) not null ,
ARTNR varchar(13) not null ,
PRIS float(16) not null ,
VALUTA varchar(3)  ,
DATUM date  ,
primary key (LISTA , ARTNR));


create table OFFERT1 (
OFFERTNR integer not null ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
SALJARE varchar(30)  ,
REFERENS varchar(30)  ,
KUNDNR varchar(20) not null ,
MARKE varchar(30)  ,
DATUM date  ,
MOMS smallint not null ,
STATUS varchar(6)  ,
KTID smallint not null ,
BONUS smallint not null ,
FAKTOR smallint not null ,
LEVDAT date  ,
LEVVILLKOR varchar(30)  ,
MOTTAGARFRAKT smallint not null ,
FRAKTKUNDNR varchar(30)  ,
FRAKTBOLAG varchar(30)  ,
FRAKTFRIGRANS float(16) not null ,
SKRIVEJPRIS smallint not null ,
LAGERNR smallint not null ,
ANNANLEVADRESS smallint default      0   ,
ORDERMEDDELANDE varchar(80)  ,
primary key (OFFERTNR));


create table OFFERT2 (
OFFERTNR integer not null ,
POS smallint not null ,
PRISNR smallint not null ,
ARTNR varchar(13) not null ,
NAMN varchar(35)  ,
LEVNR varchar(20)  ,
BEST float(16) not null ,
RAB float(16) not null ,
LEV float(16) not null ,
TEXT varchar(60)  ,
PRIS float(16) not null ,
SUMMA float(16) not null ,
KONTO varchar(6)  ,
NETTO float(16) not null ,
ENH varchar(3)  ,
LEVDAT date  ,
primary key (OFFERTNR , POS));


create table ORDER1 (
ORDERNR integer not null ,
DELLEV smallint default      0   ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
SALJARE varchar(34)  ,
REFERENS varchar(30)  ,
KUNDNR varchar(20) not null ,
MARKE varchar(30)  ,
DATUM date  ,
MOMS smallint default      0  not null ,
STATUS varchar(6) not null ,
KTID smallint default      0  not null ,
BONUS smallint default      0  not null ,
FAKTOR smallint default      0  not null ,
LEVDAT date  ,
LEVVILLKOR varchar(30)  ,
MOTTAGARFRAKT smallint default      0  not null ,
FRAKTKUNDNR varchar(30)  ,
FRAKTBOLAG varchar(30)  ,
FRAKTFRIGRANS float(16) default  0.000000000000000E+00  not null ,
LAGERNR smallint not null ,
DIREKTLEVNR integer default           0  not null ,
RETURORDER smallint default      0  not null ,
LASTAV varchar(3)  ,
LASTDATUM date  ,
LASTTID time  ,
TID time  ,
VECKOLEVDAG smallint default      0  not null ,
DOLJDATUM date  ,
TILLANNANFILIAL smallint default      0  not null ,
UTLEVBOKAD smallint default      0   ,
ANNANLEVADRESS smallint default      0   ,
ORDERMEDDELANDE varchar(80)  ,
TIDIGASTFAKTDATUM date  ,
WORDERNR integer default           0   ,
LINJENR1 varchar(3)  ,
LINJENR2 varchar(3)  ,
LINJENR3 varchar(3)  ,
primary key (ORDERNR));


create table ORDER2 (
ORDERNR integer not null ,
POS smallint not null ,
PRISNR smallint  ,
DELLEV smallint  ,
ARTNR varchar(13) not null ,
NAMN varchar(35)  ,
LEVNR varchar(20)  ,
BEST float(20) default  0.0000000000000000000E+00   ,
RAB float(20) default  0.0000000000000000000E+00   ,
LEV float(20) default  0.0000000000000000000E+00   ,
TEXT varchar(60)  ,
PRIS float(20) default  0.0000000000000000000E+00   ,
SUMMA float(20) default  0.0000000000000000000E+00   ,
KONTO varchar(6)  ,
NETTO float(20) default  0.0000000000000000000E+00   ,
ENH varchar(3)  ,
LEVDAT date  ,
UTSKRIVENDATUM date  ,
UTSKRIVENTID time  ,
STJID integer default           0   ,
primary key (ORDERNR , POS));


create table ORDERHAND (
ORDERNR integer not null ,
DATUM date not null ,
TID time not null ,
ANVANDARE varchar(3)  ,
HANDELSE varchar(20)  ,
TRANSPORTOR varchar(20)  ,
FRAKTSEDELNR varchar(20)  ,
NYORDERNR integer default           0  not null ,
ANTALKOLLI integer default           0   ,
KOLLISLAG varchar(20)  ,
TOTALVIKT integer default           0   ,
SERVERDATUM timestamp default CURRENT_TIMESTAMP   ,
primary key (ORDERNR , DATUM , TID));


create table RABKODER (
RABKOD varchar(4) not null ,
KOD1 varchar(4) not null ,
BESKRIVNING varchar(60)  ,
primary key (RABKOD , KOD1));


create table RABMALLAR (
NR varchar(20) not null ,
BESKRIVNING varchar(60)  ,
BASRAB float(16) default  0.000000000000000E+00   ,
primary key (NR));


create table RANTA (
KUNDNR varchar(20) not null ,
FAKTNR integer not null ,
BETDAT date not null ,
FALLDAT date  ,
TOT float(16) not null ,
RANTA float(16) not null ,
DAGAR integer not null ,
RANTAPROC float(16) not null ,
primary key (KUNDNR , FAKTNR , BETDAT));


create table RAPFILTER (
RAPTYP varchar(10) not null ,
BESKRIVNING varchar(60) not null ,
SQLFILTER varchar(250)  ,
SQLORDER varchar(100)  ,
RAPNOT varchar(60)  ,
SKRIVDETALJER smallint default      0   ,
SLAUPPLAGER smallint default      0   ,
primary key (RAPTYP , BESKRIVNING));


create table RETUR (
ID integer not null ,
LAGERNR integer not null ,
KUNDNR varchar(20)  ,
KUNDNAMN varchar(30)  ,
LEVNR varchar(20)  ,
ARTNR varchar(13)  ,
ARTNAMN varchar(35)  ,
ANTAL float(16) default  0.000000000000000E+00   ,
TRASIG varchar(3)  ,
KREDITERAKUND smallint default      0   ,
FELBESKRIVNING varchar(254)  ,
REGDATUM date  ,
REGANVANDARE varchar(3)  ,
ANKOMDATUM date  ,
ANKOMANVANDARE varchar(3)  ,
ANKOMMEN smallint default      0   ,
LEVRETURDATUM date  ,
LEVRETURANVANDARE varchar(3)  ,
LEVRETURNERAD smallint default      0   ,
LEVRETURNR varchar(30)  ,
LEVSVARDATUM date  ,
LEVSVARANVANDARE varchar(3)  ,
LEVSVARNOT varchar(254)  ,
AVBOKADDATUM date  ,
AVBOKADANVANDARE varchar(3)  ,
AVBOKAD smallint default      0   ,
LEVSVARGARANTIOK varchar(3)  ,
primary key (ID));


create table RORDER (
KUNDNR varchar(20) not null ,
ARTNR varchar(13) not null ,
ID smallint not null ,
NAMN varchar(35)  ,
PRIS float(16) not null ,
RAB float(16) not null ,
REST float(16) not null ,
ENH varchar(3)  ,
KONTO varchar(6)  ,
NETTO float(16) not null ,
MARKE varchar(30)  ,
LEVNR varchar(20) not null ,
LEVDAT date  ,
LEVBESTDAT date  ,
LAGERNR smallint not null ,
STJID integer not null ,
primary key (KUNDNR , ARTNR , ID));


create table RTFMEMO (
KONTAKTTYP varchar(1) not null ,
KONTAKTNR varchar(20) not null ,
TILLHORIGHET varchar(1) not null ,
LOPNR integer default           0  not null ,
SKAPADDATUM date default CURRENT_DATE   ,
ANDRADDATUM date  ,
MEMOTEXT varchar(4095)  ,
primary key (KONTAKTTYP , KONTAKTNR , TILLHORIGHET , LOPNR));


create table SALJARE (
NAMN varchar(30) not null ,
FORKORTNING varchar(3)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
TEL varchar(30)  ,
MOBIL varchar(30)  ,
FAX varchar(30)  ,
TOTALT float(16) not null ,
TBIDRAG float(16) not null ,
ANT1 varchar(60)  ,
ANT2 varchar(60)  ,
ANT3 varchar(60)  ,
BEHORIGHET smallint not null ,
LOSEN varchar(10)  ,
LAGERNR smallint default      0   ,
LOSENGILTIGTDATUM date  ,
primary key (NAMN));


create table SBEST (
LEVNR varchar(20) not null ,
ARTNR varchar(13) not null ,
KUNDNR varchar(20) not null ,
DATUM date not null ,
NAMN varchar(35)  ,
ANTAL float(16) not null ,
ENH varchar(3)  ,
PRIS float(16) not null ,
RAB float(16) not null ,
BESTDAT date  ,
REF varchar(3) not null ,
LAGERNR smallint not null ,
primary key (LEVNR , ARTNR , KUNDNR , DATUM));


create table SLJSTAT (
SALJARE varchar(30) not null ,
AR smallint not null ,
MAN smallint not null ,
TOTALT float(16) not null ,
TBIDRAG float(16) not null ,
primary key (SALJARE , AR , MAN));


/* Används endast som uppkopplingstabell för SQL-funktioner i Clarion */
create table SQLFILE (
F1 varchar(20));


create table STATISTIK (
AR smallint not null ,
MAN smallint not null ,
FAK_NETTO float(16) not null ,
FAK_MOMS float(16) not null ,
FAK_ATTBETALA float(16) not null ,
FAK_INNETTO float(16) not null ,
FAK_ANTAL smallint not null ,
FAK_RANTATOT float(16) not null ,
FAK_RANTAANTAL smallint not null ,
KUN_BETALT float(16) not null ,
KUN_BETALTANT smallint not null ,
KUN_BRHOG float(16) not null ,
KUN_BRHOGANT smallint not null ,
KUN_BRLAG float(16) not null ,
KUN_BRLAGANT smallint not null ,
KUN_BEJREG float(16) not null ,
KUN_BEJREGANT smallint not null ,
KUN_RANKOST float(16) not null ,
KUN_RANKOSTANT smallint not null ,
LEV_ATTBET float(16) not null ,
LEV_ATTBETANT smallint not null ,
LEV_RANTA float(16) not null ,
LEV_RANTAANTAL smallint not null ,
LEV_VARU float(16) not null ,
LEV_VARUANTAL smallint not null ,
LEV_BETALT float(16) not null ,
LEV_BETALTANT smallint not null ,
primary key (AR , MAN));


create table STJARNRAD (
STJID integer not null ,
ARTNR varchar(20)  ,
LEVNR varchar(20)  ,
KUNDNR varchar(20)  ,
NAMN varchar(35)  ,
LAGERNR smallint not null ,
ANTAL float(16) not null ,
ENH varchar(3)  ,
INPRIS float(16) not null ,
INRAB float(16) not null ,
REGDATUM date  ,
AUTOBESTALL smallint not null ,
BESTDAT date  ,
BESTNR integer default           0   ,
ANVANDARE varchar(3)  ,
FINNSILAGER smallint not null ,
INKOMDATUM date  ,
FAKTURANR integer default           0   ,
primary key (STJID));


create table SXDEBUG (
ANVANDARE varchar(30)  ,
KLIENTDATUM date  ,
KLIENTTID varchar(20)  ,
INFO varchar(100)  ,
CNTART integer  ,
CNTKUN integer  ,
CNTFDO integer  ,
SERVERDATUM timestamp default CURRENT_TIMESTAMP);


create table SXLOG (
MODUL varchar(20)  ,
DATORNAMN varchar(30)  ,
ANVANDARE varchar(120)  ,
HANDELSE varchar(60)  ,
SERVERDATUM timestamp default CURRENT_TIMESTAMP  not null ,
IP varchar(15)  ,
FUNKTION varchar(20)  ,
primary key (SERVERDATUM));


create table SXREG (
ID varchar(40) not null ,
VARDE varchar(254)  ,
primary key (ID));


create table SXSERVJOBB (
JOBBID integer not null ,
UPPGIFT varchar(20) not null ,
DOKUMENTTYP varchar(20)  ,
SANDSATT varchar(20)  ,
EXTERNIDINT integer  ,
EXTERNIDSTRING varchar(100)  ,
EPOST varchar(512)  ,
SMS varchar(100)  ,
SKAPAD timestamp default CURRENT_TIMESTAMP   ,
SLUTFORD timestamp  ,
BEARBETAR timestamp  ,
ANTALFORSOK integer default           0  not null ,
ANVANDARE varchar(3)  ,
primary key (JOBBID));


create table TEL (
TEL varchar(20) not null ,
NAMN varchar(30)  ,
KUNDNR varchar(20)  ,
AUTOFLAGGA smallint not null ,
primary key (TEL));


create table TRANSPORTOR (
NAMN varchar(20) not null ,
UTLEVPRIORITET smallint default    100   ,
primary key (NAMN));


create table TURLINJE (
LINJENR varchar(3) not null ,
BESKRIVNING varchar(100)  ,
D1 smallint default      0   ,
D2 smallint default      0   ,
D3 smallint default      0   ,
D4 smallint default      0   ,
D5 smallint default      0   ,
AVGANGTID time not null ,
SOD1 smallint default      0   ,
SOD2 smallint default      0   ,
SOD3 smallint default      0   ,
SOD4 smallint default      0   ,
SOD5 smallint default      0   ,
SISTAORDERTID time not null ,
NAMN varchar(15)  ,
FRANALLAFILIALER smallint default      0   ,
FRANFILIAL smallint default      0   ,
primary key (LINJENR));


create table UTLEV1 (
ORDERNR integer not null ,
DELLEV smallint  ,
NAMN varchar(30)  ,
ADR1 varchar(30)  ,
ADR2 varchar(30)  ,
ADR3 varchar(30)  ,
LEVADR1 varchar(30)  ,
LEVADR2 varchar(30)  ,
LEVADR3 varchar(30)  ,
SALJARE varchar(34)  ,
REFERENS varchar(30)  ,
KUNDNR varchar(20) not null ,
MARKE varchar(30)  ,
DATUM date  ,
MOMS smallint not null ,
STATUS varchar(6) not null ,
KTID smallint not null ,
BONUS smallint not null ,
FAKTOR smallint not null ,
LEVDAT date  ,
LEVVILLKOR varchar(30)  ,
MOTTAGARFRAKT smallint not null ,
FRAKTKUNDNR varchar(30)  ,
FRAKTBOLAG varchar(30)  ,
FRAKTFRIGRANS float(16) not null ,
LAGERNR smallint not null ,
RETURORDER smallint not null ,
DIREKTLEVNR integer not null ,
TID time  ,
FAKTNR integer not null ,
VECKOLEVDAG smallint not null ,
ANNANLEVADRESS smallint default      0   ,
ORDERMEDDELANDE varchar(80)  ,
WORDERNR integer default           0   ,
LINJENR1 varchar(3)  ,
LINJENR2 varchar(3)  ,
LINJENR3 varchar(3)  ,
primary key (ORDERNR));


create table VALUTA (
VALUTA varchar(3) not null ,
KURS float(16) not null ,
DATUM date  ,
primary key (VALUTA));


create table WEBORDER1 (
WORDERNR integer not null ,
KUNDNR varchar(20) not null ,
LOGINNAMN varchar(30) not null ,
EPOST varchar(60) not null ,
MARKE varchar(30)  ,
DATUM date  ,
STATUS varchar(10)  ,
LEVDAT date  ,
LAGERNR smallint default      0   ,
SKAPADTIMESTAMP timestamp default CURRENT_TIMESTAMP   ,
ANTALRADER integer default           0   ,
primary key (WORDERNR));


create table WEBORDER2 (
WORDERNR integer not null ,
POS smallint not null ,
ARTNR varchar(13) not null ,
ANTAL float(16) default  0.000000000000000E+00   ,
primary key (WORDERNR , POS));


create table WEBUSER (
LOGINNAMN varchar(30) not null ,
NAMN varchar(30) not null ,
FORETAG varchar(30) not null ,
EPOST varchar(60) not null ,
LOSEN varchar(30) not null ,
KUNDNR varchar(20) not null ,
AKTIVT integer default           0   ,
primary key (LOGINNAMN));


create table WARTGRP (
GRPID integer not null ,
PREVGRPID integer  ,
RUBRIK varchar(60)  ,
TEXT varchar(4000)  ,
INFOURL varchar(120)  ,
SORTORDER integer default           0   ,
HTML varchar(2000)  ,
primary key (GRPID));


create table WARTGRPLANK (
GRPID integer not null ,
KLASID integer not null ,
SORTORDER integer default           0   ,
primary key (GRPID , KLASID));


create table WARTIKEL (
NUMMER varchar(13) not null ,
NAMN varchar(35)  ,
LEV varchar(20) not null ,
BESTNR varchar(30) not null ,
ENHET varchar(3)  ,
UTPRIS float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS1 float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS2 float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS1_DAT date  ,
STAF_PRIS2_DAT date  ,
STAF_ANTAL1 float(16) default  0.000000000000000E+00  not null ,
STAF_ANTAL2 float(16) default  0.000000000000000E+00  not null ,
INPRIS float(16) default  0.000000000000000E+00  not null ,
RAB float(16) default  0.000000000000000E+00  not null ,
UTRAB float(16) default  0.000000000000000E+00  not null ,
INP_PRIS float(16) default  0.000000000000000E+00  not null ,
INP_RAB float(16) default  0.000000000000000E+00  not null ,
INP_FRAKTPROC float(16) default  0.000000000000000E+00  not null ,
INP_VALUTA varchar(3)  ,
INP_DATUM date  ,
KONTO varchar(6)  ,
RABKOD varchar(4)  ,
KOD1 varchar(4)  ,
PRISDATUM date  ,
INPDAT date  ,
TBIDRAG float(16) not null ,
REFNR varchar(20)  ,
VIKT float(16) default  0.000000000000000E+00  not null ,
VOLYM float(16) default  0.000000000000000E+00  not null ,
RORDAT date  ,
STRUKTNR varchar(20)  ,
FORPACK float(16) default  0.000000000000000E+00  not null ,
KOP_PACK float(16) default  0.000000000000000E+00  not null ,
KAMPFRDAT date  ,
KAMPTIDAT date  ,
KAMPPRIS float(16) default  0.000000000000000E+00   ,
KAMPPRISSTAF1 float(16) default  0.000000000000000E+00   ,
KAMPPRISSTAF2 float(16) default  0.000000000000000E+00   ,
INP_MILJO float(16) default  0.000000000000000E+00  not null ,
INP_FRAKT float(16) default  0.000000000000000E+00  not null ,
ANVISNR varchar(10)  ,
STAF_PRIS1NY float(16) default  0.000000000000000E+00  not null ,
STAF_PRIS2NY float(16) default  0.000000000000000E+00  not null ,
INPRISNY float(16) default  0.000000000000000E+00  not null ,
INPRISNYDAT date  ,
INPRISNYRAB float(16) default  0.000000000000000E+00  not null ,
UTPRISNY float(16) not null ,
UTPRISNYAVBOKDAT date  ,
UTPRISNYDAT date  ,
RSK varchar(7)  ,
ENUMMER varchar(7)  ,
KAMPKUNDARTGRP smallint default      0  not null ,
KAMPKUNDGRP smallint default      0  not null ,
CN8 varchar(8)  ,
FRAKTVILLKOR smallint default      0   ,
DAGSPRIS smallint default      0   ,
HINDRAEXPORT smallint default      0   ,
UTGATTDATUM date  ,
MINSALJPACK float(16) default  0.000000000000000E+00   ,
STORPACK float(16) default  0.000000000000000E+00   ,
PRISGILTIGHETSTID integer default         730   ,
ONSKATTB integer default           0   ,
ONSKATTBSTAF1 integer default           0   ,
ONSKATTBSTAF2 integer default           0   ,
SALDA float(16) default  0.000000000000000E+00   ,
DIREKTLEV smallint default      0   ,
KATNAMN varchar(35)  ,
BILDARTNR varchar(13)  ,
PLOCKINSTRUKTION varchar(512)  ,
primary key (NUMMER));


create table WARTKLASE (
KLASID integer not null ,
RUBRIK varchar(60)  ,
TEXT varchar(4000)  ,
INFOURL varchar(120)  ,
FRAKTVILLKOR varchar(120)  ,
HTML varchar(2000)  ,
primary key (KLASID));


create table WARTKLASELANK (
KLASID integer not null ,
ARTNR varchar(13) not null ,
SORTORDER integer default           0   ,
primary key (KLASID , ARTNR));


create table WFRAKTVILLKOR (
ID smallint not null ,
NAMN varchar(30)  ,
BESKRIVNING varchar(120)  ,
primary key (ID));



/* Ändringar 2008-01-18 */
alter table best1 add column sanddat date;
alter table rapphuvud add column jspfilename varchar(60);
alter table rapphuvud alter behorighet type varchar(20);

/* En del grejer för att säkerställa att vi inte får nullvärden som kan ställa till det i dagens order */
alter table order1 alter utlevbokad set not null ;
alter table order1 alter wordernr set not null ;
alter table order1 alter annanlevadress set not null;
alter table order1 alter linjenr1 set default '';
alter table order1 alter linjenr2 set default '';
alter table order1 alter linjenr3 set default '';
alter table order1 alter linjenr1 set not null;
alter table order1 alter linjenr2 set not null;
alter table order1 alter linjenr3 set not null;

/* 2009-01-29 */
alter table order2 alter prisnr set not null;
alter table order2 alter prisnr set default 0;
alter table order2 alter dellev set not null;
alter table order2 alter dellev set default 0;
alter table order2 alter prisnr set not null;
alter table order2 alter rab set not null;
alter table order2 alter lev set not null;
alter table order2 alter pris set not null;
alter table order2 alter summa set not null;
alter table order2 alter netto set not null;
alter table order2 alter stjid set not null;

alter table best1 alter sakerhetskod set not null;
alter table best1 alter antalfelinloggningar set not null;
alter table best1 alter sxservsandforsok set not null;
alter table best1 alter antalpamin set not null;

alter table orderhand alter antalkolli set not null;
alter table orderhand alter totalvikt set not null;

alter table faktura1 alter mottagarfrakt set default 0;

alter table faktura2 alter rab set default 0;
alter table faktura2 alter bon_nr set not null;
alter table faktura2 alter ordernr set default 0;
alter table faktura2 alter rantafakturanr set not null;
alter table faktura2 alter stjid set not null;
alter table faktura2 alter rantabetalbelopp set not null;
alter table faktura2 alter rantaproc set not null;

alter table kundres alter antalpaminnelser set not null;
alter table kundres alter pantsatt set not null;

alter table lager alter hindrafilialbest set not null;

alter table lagerid alter maxlagervarde set not null;

alter table offert1 alter annanlevadress set not null;

alter table saljare alter lagernr set not null;

alter table stjarnrad alter bestnr set not null;
alter table stjarnrad alter fakturanr set not null;

alter table sxservjobb alter antalforsok  set not null;

alter table artgrp alter prevgrpid set not null;
alter table artgrp alter sortorder set not null;

alter table artgrplank alter sortorder set not null;

alter table artklaselank alter sortorder set not null;

/* 2009-01-31 */
update artikel set kamppris = 0 where kamppris is null;
update artikel set kampprisstaf1 = 0 where kampprisstaf1 is null;
update artikel set kampprisstaf2 = 0 where kampprisstaf2 is null;
alter table artikel alter kamppris set not null;
alter table artikel alter kampprisstaf1 set not null;
alter table artikel alter kampprisstaf2 set not null;
alter table artikel alter fraktvillkor set not null;
alter table artikel alter dagspris set not null;
alter table artikel alter hindraexport set not null;
alter table artikel alter prisgiltighetstid set not null;
alter table artikel alter onskattb set not null;
alter table artikel alter onskattbstaf1 set not null;
alter table artikel alter onskattbstaf2 set not null;
alter table artikel alter salda set not null;
alter table artikel alter direktlev set not null;
alter table artikel alter storpack set not null;
alter table artikel alter minsaljpack set not null;

alter table artikel alter utprisny set default 0;


CREATE OR REPLACE FUNCTION "adddate"(date, integer)  RETURNS date AS
'select $1 + $2'
  LANGUAGE 'sql' VOLATILE STRICT
  COST 100;



CREATE OR REPLACE FUNCTION "subdate"(date, integer)  RETURNS date AS
'select $1 - $2'
  LANGUAGE 'sql' VOLATILE STRICT
  COST 100;


create or replace view vartkundorder as
select
k.nummer as kundnr,
a.nummer as artnr,
a.namn as artnamn,
a.lev as lev,
a.bestnr as bestnr,
a.rsk as rsk,
a.enummer as enummer,
a.refnr as refnr,
a.plockinstruktion as plockinstruktion,
a.enhet as enhet,
a.utpris as utpris,
a.staf_pris1 as staf_pris1,
a.staf_pris2 as staf_pris2,
a.staf_antal1 as staf_antal1,
a.staf_antal2 as staf_antal2,
a.rab as rab,
a.rabkod as rabkod,
a.kod1 as kod1,
a.inpris as inpris,
a.prisdatum as prisdatum,
a.prisgiltighetstid,
a.konto as konto,
a.struktnr as struktnr,
a.forpack as forpack,
a.kop_pack as kop_pack,
a.kampfrdat as kampfrdat,
a.kamptidat as kamptidat,
a.kamppris as kamppris,
a.kampprisstaf1 as kampprisstaf1,
a.kampprisstaf2 as kampprisstaf2,
a.inp_miljo as inp_miljo,
a.inp_frakt as inp_frakt,
a.inp_fraktproc as inp_fraktproc,
a.kampkundartgrp as kampkundartgrp,
a.kampkundgrp as kampkundgrp,
a.fraktvillkor as fraktvillkor,
a.dagspris as dagspris,
a.minsaljpack as minsaljpack,
a.anvisnr as anvisnr,
a.utgattdatum as utgattdatum,
lid.lagernr as lagernr,
coalesce(l.ilager,0) as ilager,
coalesce(l.iorder,0) as iorder,
coalesce(l.best,0) as best,
l.lagerplats as lagerplats,
coalesce(k.basrab,0) as basrab,
coalesce(r2.rab,0) as gruppbasrab,
coalesce(r.rab,0) as undergrupprab,
n.lista as nettolst,
coalesce(n.pris,0) as nettopris,
an.anvisning as anvisning,
cast (greatest(coalesce(k.basrab,0), coalesce(r2.rab,0), coalesce(r.rab,0)) as real) as calc_hogstarab,
cast (a.inpris + a.inp_miljo + a.inp_frakt + (a.inpris*a.inp_fraktproc/100) as real) as calc_kostprisin,
case when (k.elkund*1+k.vvskund*2+k.vakund*4+k.golvkund*8+k.fastighetskund*16) & a.kampkundartgrp > 0 and (k.installator*1+k.butik*2+k.industri*4+k.oem*8+k.grossist*16) & a.kampkundgrp > 0  and now() > a.kampfrdat and now() <= a.kamptidat then 1 else 0 end as calc_kampanjgaller


from
artikel a
join lagerid lid on 1=1
left outer join lager l on l.lagernr = lid.lagernr and l.artnr =a.nummer
left outer join kund k on 1=1
left outer join kunrab r2 on r2.kundnr = k.nummer and coalesce(r2.rabkod,'') = coalesce(a.rabkod,'') and coalesce(r2.kod1,'') = ''
left outer join kunrab r on r.kundnr = k.nummer and coalesce(r.rabkod,'') = coalesce(a.rabkod,'') and coalesce(r.kod1,'') = coalesce(a.kod1,'')
left outer join nettopri n on n.lista = k.nettolst and n.artnr = a.nummer
left outer join anvis an on an.anvisnr = a.nummer;

/* 2009-02-19 */
insert into behorighet (behorighet, beskrivning) values ('IntraLogin','Kan logga in på intranätet');
insert into behorighet (behorighet, beskrivning) values ('IntraAdmin','Administrativa funktioner på intranätet');
insert into behorighet (behorighet, beskrivning) values ('IntraSuperuser','Superuser på intranätet');


insert into behorighet (behorighet, beskrivning) values ('Ekonomi','Ekonomiska funktioner och rapporter');
create table intrakanaler (kanalid integer not null, rubrik varchar(30) not null, beskrivning varchar(512), showonstartpage smallint not null default 0, primary key (kanalid));
create table intrainlagg (inlaggid integer not null, kanalid integer not null, rubrik varchar(30) not null, ingress varchar(512), brodtext varchar(4096), filename varchar(100), contenttype varchar(100), originalfilename varchar(100), visatill date, anvandarekort varchar(3), crtime timestamp default current_timestamp , primary key (inlaggid));

/* 2009-07-13 */

create table steprodukt (
sn varchar(30),
crdt timestamp default CURRENT_TIMESTAMP,
instdatum date,
anvandare varchar(3),
artnr varchar(13),
modell varchar(30),
namn varchar(30),
adr1 varchar(30),
adr2 varchar(30),
adr3 varchar(30),
referens varchar(30),
tel varchar(30),
mobil varchar(30),
epost varchar(128),
installatornamn varchar(30),
installatorkundnr varchar(30),
faktnr integer,
primary key (sn)
);

create table steproduktnot (
id integer,
sn varchar(30) not null,
crdt timestamp default CURRENT_TIMESTAMP,
anvandare varchar(3),
arendetyp varchar(60),
felorsak varchar(60),
publicerasomqa smallint not null default 0,
foljuppdatum date,
fraga varchar(8192),
svar varchar(8192),
bilaga bytea,
filnamn varchar(254),
contenttype varchar(254),
serviceombudkundnr varchar(30),
serviceombudnamn varchar(30),
primary key (id)
);

insert into behorighet (behorighet, beskrivning) values ('STETeknik','Stiebel Eltron Teknik');

create table stepumpartnr (
artnr varchar(13),
primary key(artnr)
);

insert into sxreg (id,varde) values ('BildLogoSteService','SteserviceLogo');

insert into behorighet (behorighet, beskrivning) values ('BokLogin','Kan logga in till bokföringen');
insert into behorighet (behorighet, beskrivning) values ('LonLogin','Kan logga in till löneprogram');

alter table kund add column skickafakturaepost  smallint default      0  not null;

/* Ändringar 2009-12-16 */
create table varukorg (
kontaktid integer not null,
typ varchar(3) not null, /*Kan vara 'VK' för varukorg, 'FAV' för favoriter */
artnr varchar(13) not null ,
antal float(20) default  0.0000000000000000000E+00   ,
primary key (kontaktid, typ, artnr));

/* Ändringar 2010-01-20 */
alter table kundlogin add autologinid varchar(100);
alter table kundlogin add autologinexpire date;
alter table intrainlagg alter ingress type varchar;
alter table intrainlagg alter brodtext type varchar;

create table kundloginhandelse (
loginnamn varchar(30),
handelse varchar,
ip varchar(100),
cr timestamp default current_timestamp
)

/* Ändringar 2010-06-15 */
alter table artikel add inp_enh varchar(3);	/*Leverantörens enhet */
alter table artikel add inp_enhetsfaktor numeric(12,6) not null default 0.0;	/* Hur många av leverantörens enheter som motsvarar försäljningsenheten  */
																											/*ex. leverantör har 6 m rör och säljer som m, men vi har enhet st så blir faktorn 6 */
/* Ändringar 2010-08-01 */
alter table order1 add kundordernr integer not null default 0;
alter table order1 add forskatt smallint not null default 0;
alter table order1 add forskattbetald smallint not null default 0;
alter table order1 add betalsatt varchar(20);

alter table utlev1 add kundordernr integer not null default 0;
alter table utlev1 add forskatt smallint not null default 0;
alter table utlev1 add forskattbetald smallint not null default 0;
alter table utlev1 add betalsatt varchar(20);

/* Ändringar 2011-02-18 */
alter table kund add samfakgrans float not null default 0; /* Minsta belopp för att automatisk samfaktura ska gå ut */

/* Ändringar 2011-11-06 */
create view orderview as (
select
o1.ordernr, o1.dellev, o1.namn as kundnamn, adr1, adr2, adr3, levadr1, levadr2, levadr3, saljare, kundnr,
marke, datum, moms, status, ktid, bonus, faktor, o1.levdat as orderlevdat, levvillkor, mottagarfrakt, fraktkundnr,
fraktbolag, fraktfrigrans, lagernr, direktlevnr, returorder, lastav, lastdatum, lasttid, tid,
veckolevdag, doljdatum, tillannanfilial, utlevbokad, annanlevadress, ordermeddelande, tidigastfaktdatum,
wordernr, linjenr1, linjenr2, linjenr3, kundordernr, forskatt, forskattbetald, betalsatt, pos, prisnr,
artnr, o2.namn as artnamn, levnr, best, rab, lev, text, pris, summa, konto, netto, enh, o2.levdat as radlevdat,
utskrivendatum, utskriventid, stjid
from order1 o1 left outer join order2 o2 on o1.ordernr=o2.ordernr);

create view fakturaview as (
select
f1.faktnr, kundnr, f1.namn as kundnamn, adr1, adr2, adr3, levadr1, levadr2, levadr3,
datum, saljare, referens, marke, moms, ktid, ranta, bonus, faktor, text1, text2, text3, text4, text5,
faktortext1, faktortext2, faktortext3, rantfakt, t_netto, t_moms, t_orut, t_attbetala, t_innetto, lagernr,
 momsproc, inkassostatus, inkassodatum,
pos, prisnr, artnr, rab, lev, text, pris, summa, konto, netto, enh, f2.namn as artnamn, bon_nr,
f2.ordernr,rantafakturanr, rantafalldatum, rantabetaldatum, rantabetalbelopp, rantaproc, stjid
from faktura1 f1 left outer join faktura2 f2 on f1.faktnr=f2.faktnr
);


create view utlevview as (
select
o1.ordernr, o1.dellev, o1.namn as kundnamn, adr1, adr2, adr3, levadr1, levadr2, levadr3, saljare, kundnr,
marke, datum, moms, status, ktid, bonus, faktor, o1.levdat as orderlevdat, levvillkor, mottagarfrakt, fraktkundnr,
fraktbolag, fraktfrigrans, lagernr, direktlevnr, returorder,  tid, o1.faktnr,
veckolevdag, annanlevadress, ordermeddelande,
wordernr, linjenr1, linjenr2, linjenr3, kundordernr, forskatt, forskattbetald, betalsatt,
 pos, prisnr, artnr, rab, lev, text, pris, summa, konto, netto, enh, f2.namn as artnamn, bon_nr,
rantafakturanr, rantafalldatum, rantabetaldatum, rantabetalbelopp, rantaproc, stjid
from utlev1 o1 left outer join faktura2 f2 on f2.ordernr=o1.ordernr);

create view bestview as (
select b1.bestnr, b1.levnr, levnamn, levadr0, levadr1, levadr2, levadr3, var_ref, er_ref, leverans,
marke, datum, b1.summa as totalsumma, b1.bekrdat, bestejpris, b1.lagernr, ordernr, autobestalld, skickasom, status, meddelande,
sakerhetskod, antalfelinloggningar, sxservsandforsok, pamindat, antalpamin, sanddat,
rad, b2.enh, b2.artnr, artnamn, bartnr, best, pris, rab, b2.summa, b2.bekrdat as radbekrdat, inp_miljo, inp_frakt, inp_fraktproc, b2.stjid
from best1 b1 left outer join best2 b2 on b1.bestnr=b2.bestnr
);

create view inlevview as (
select b1.id, priskolldat, b1.bestnr, b1.levnr, levnamn, levadr0, levadr1, levadr2, levadr3,
marke, datum, b1.lagernr, ordernr,
rad, b2.enh, b2.artnr, artnamn, antal, pris, rab, b2.stjid
from inlev1 b1 left outer join inlev2 b2 on b1.id=b2.id
);

--s.artnr as stjartnr, s.namn as stjartnamn, s.kundnr as stjkundnr, s.lagernr as stjlagernr, s.antal as stjantal, s.enh as stjenh,
--s.inpris as stjinpris, s.regdatum as stjregdatum, s.autobestall as stjautobestall, s.bestdat as stjbestdat, s.bestnr as stjbestnr,
--s.anvandare as stjanvandare, s.finnsilager as stjfinnsilager, s.inkomdatum as stjinkomdatum, s.fakturanr as stjfakturanr
--from best1 b1 left outer join best2 b2 on b1.bestnr=b2.bestnr left outer join stjarnrad s on s.stjid=b2.stjid and b2.stjid<>0

alter table artikel add ean varchar;



alter table saljare add epost varchar;
create table loginservice (uuid varchar primary key, anvandare varchar, expiredate date, crdate timestamp not null default current_timestamp) 



create function TimerDailyUserDefined() returns void
as $$
--Här kan egna funktioner läggas till allt eftersom - t.ex. skapa nettoprislistor för olika kunder 
 $$ language sql;
create or replace function TimerDaily() returns void
as $$
select TimerDailyUserDefined();
 $$ language sql;



create function TimerWeeklyUserDefined() returns void
as $$
--Här kan egna funktioner läggas till allt eftersom - t.ex. skapa nettoprislistor för olika kunder 
 $$ language sql;
create or replace function TimerWeekly() returns void 
as $$
select TimerWeeklyUserDefined();
 $$ language sql;



create function TimerMonthlyUserDefined() returns void
as $$
--Här kan egna funktioner läggas till allt eftersom - t.ex. skapa nettoprislistor för olika kunder 
 $$ language sql;
create or replace function TimerMonthly() returns void 
as $$
select TimerMonthlyUserDefined();
 $$ language sql;


--2013-05-23
alter table artikel add jpaversion integer not null default 0;
alter table kund add jpaversion integer not null default 0;
alter table lev add jpaversion integer not null default 0;

--2014-08-08
create table table_audit ( table_name varchar not null, row_id varchar not null, handelse varchar not null, ts timestamp default current_timestamp, 
old_data varchar, new_data varchar, ip varchar, update_column varchar);

CREATE OR REPLACE FUNCTION trigger_log_kund_kgrans() RETURNS TRIGGER AS $_$
BEGIN
	insert into table_audit (table_name, row_id, handelse, old_data, new_data, ip, update_column) values (TG_TABLE_NAME::varchar, new.nummer,  TG_OP::varchar,  case when TG_OP='INSERT' then null else old.kgrans::VARCHAR end, new.kgrans::VARCHAR , inet_client_addr()::VARCHAR, 'kgrans' );
    RETURN NEW;
END $_$ LANGUAGE 'plpgsql';

create trigger trigger_kund_kgrans after insert or update of  kgrans on kund 
for each row execute procedure trigger_log_kund_kgrans();

--2014-12
create table vkorg  ( kontaktid integer not null, 
typ varchar(3)  not null, /*Kan vara 'VK' för varukorg, 'FAV' för favoriter */
klasid integer not null,
artnr varchar(13) not null,
antal integer not null,
ch_timestamp timestamp default current_timestamp,
primary key (kontaktid, typ, klasid, artnr))

create or replace view vkorg_view as (
select v.kontaktid as kontaktid, v.typ as typ, v.klasid as klasid, v.artnr as artnr, v.antal as antal, v.ch_timestamp as ch_timestamp, g.max_ch_timestamp as klase_ch_timestamp, akl.sortorder as akl_sortorder
from 
(
select klasid as klasid, kontaktid as kontaktid, max(ch_timestamp) as max_ch_timestamp
from vkorg where typ='VK'
group by klasid, kontaktid) g
join vkorg v on typ='VK' and v.kontaktid=g.kontaktid and v.klasid=g.klasid
join artklaselank akl on akl.klasid=v.klasid and akl.artnr=v.artnr
order by v.kontaktid, g.max_ch_timestamp, akl.sortorder, akl.artnr
)


create or replace view vbutikart as
select  
ak.klasid as ak_klasid, ak.rubrik as ak_rubrik, ak.text as ak_text, ak.html as ak_html, ak.autosortvikt as ak_autosortvikt,
akl.sortorder as akl_sortorder,
a.*,
lid.lagernr as lid_lagernr, lid.bnamn as lid_bnamn, lid.namn as lid_namn, lid.adr1 as lid_adr1, lid.adr2 as lid_adr2, lid.adr3 as lid_adr3,lid.tel as lid_tel, lid.email as lid_email,
l.lagernr as l_lagernr, coalesce(l.ilager,0) as l_ilager, coalesce(l.bestpunkt,0) as l_bestpunkt, coalesce(l.maxlager,0) as l_maxlager, coalesce(l.best,0) as l_best, coalesce(l.iorder,0) as l_iorder,
k.nummer as k_nummer,
coalesce(k.basrab,0) as kunrab_bas,
coalesce(r.rab,0) as kunrab_grupp,
coalesce(r2.rab,0) as kunrab_undergrupp,
n.lista as n_lista,
coalesce (n.pris,0) as n_pris,

round(least(
	case when a.kamppris > 0 and current_date between a.kampfrdat and a.kamptidat and (k.elkund*1+k.vvskund*2+k.vakund*4+k.golvkund*8+k.fastighetskund*16) & coalesce(a.kampkundartgrp,0) > 0 and
	((k.installator*1+k.butik*2+k.industri*4+k.oem*8+k.grossist*16) & coalesce(a.kampkundgrp,0)) > 0 then 
		a.kamppris 
	else 
		a.utpris * (1-greatest(k.basrab, r2.rab, r.rab)/100) 
	end,
	case when n.pris > 0 then n.pris else a.utpris end
)::numeric,2) as kundnetto_bas,

round(
	case when a.staf_antal1 > 0  then
		least(
			case when a.kampprisstaf1 > 0 and current_date between a.kampfrdat and a.kamptidat and (k.elkund*1+k.vvskund*2+k.vakund*4+k.golvkund*8+k.fastighetskund*16) & coalesce(a.kampkundartgrp,0) > 0 and
			((k.installator*1+k.butik*2+k.industri*4+k.oem*8+k.grossist*16) & coalesce(a.kampkundgrp,0)) > 0  then 
					a.kampprisstaf1 
			else 
				a.staf_pris1  * (1-greatest(k.basrab, r2.rab, r.rab)/100)
			end,
			case when n.pris > 0 then n.pris else a.utpris end
		)

	else
		0
	end::numeric


,2) as kundnetto_staf1,

round(
	case when a.staf_antal2 > 0  then
		least(
			case when a.kampprisstaf2 > 0 and current_date between a.kampfrdat and a.kamptidat and (k.elkund*1+k.vvskund*2+k.vakund*4+k.golvkund*8+k.fastighetskund*16) & coalesce(a.kampkundartgrp,0) > 0 and
			((k.installator*1+k.butik*2+k.industri*4+k.oem*8+k.grossist*16) & coalesce(a.kampkundgrp,0)) > 0  then 
					a.kampprisstaf2 
			else 
				a.staf_pris2  * (1-greatest(k.basrab, r2.rab, r.rab)/100)
			end,
			case when n.pris > 0 then n.pris else a.utpris end
		)

	else
		0
	end::numeric


,2) as kundnetto_staf2

from artklase ak 
join artklaselank akl on akl.klasid=ak.klasid 
join artikel a on a.nummer=akl.artnr 
join lagerid lid on 1=1 
left outer join lager l on l.artnr=a.nummer and l.lagernr=lid.lagernr
left outer join kund k on 1=1
LEFT JOIN kunrab r2 ON r2.kundnr::text = k.nummer::text AND COALESCE(r2.rabkod, ''::character varying)::text = COALESCE(a.rabkod, ''::character varying)::text AND COALESCE(r2.kod1, ''::character varying)::text = ''::text
LEFT JOIN kunrab r ON r.kundnr::text = k.nummer::text AND COALESCE(r.rabkod, ''::character varying)::text = COALESCE(a.rabkod, ''::character varying)::text AND COALESCE(r.kod1, ''::character varying)::text = COALESCE(a.kod1, ''::character varying)::text
LEFT JOIN nettopri n ON n.lista::text = k.nettolst::text AND n.artnr::text = a.nummer::text
;

create table butikautologin (uuid varchar not null,  kontaktid integer, primary key (uuid), expiredate date not null default current_date)
alter table artklase add column autosortvikt integer not null default 0;
alter table artklase add column autoantalorderrader integer not null default 0;
