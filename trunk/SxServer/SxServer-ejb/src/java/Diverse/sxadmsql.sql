// fUNCTIONS - DATE, YEAR, MONTH, UCASE

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

CREATE OR REPLACE FUNCTION public."ucase"(va567890+´+1234567890+rchar)
  RETURNS varchar AS
 'select upper ($1)'
 language sql
 returns null on null input;



create table kundkontakt (
kontaktid integer not null, clara



mamma









vra2134
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
