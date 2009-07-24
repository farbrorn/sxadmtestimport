/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

/**
 *
 * @author ulf
 */
public class SXConstant {

	public static final  String ORDER_STATUS_SPARAD = "Sparad";
	public static final  String ORDER_STATUS_DIREKTLEV = "Direkt";
	public static final  String ORDER_STATUS_SIMPLEORDER = "Simple";
	public static final  String ORDER_STATUS_VANTAR = "Väntar";
	
	public static final  String ORDERHAND_SKAPAD = "Skapad";

	public static final	String SERVJOBB_UPPGIFT_SAND = "sänd";
	public static final	String SERVJOBB_SANDSATT_EPOST = "epost";
	public static final	String SERVJOBB_DOKUMENTTYP_FAKTURA = "faktura";
	public static final	String SERVJOBB_DOKUMENTTYP_BEST = "best";
	public static final	String SERVJOBB_DOKUMENTTYP_OFFERT = "offert";
	public static final	String SERVJOBB_DOKUMENTTYP_ORDERERK = "ordererk";
	
	public static final  String SXREG_ARTNRFRAKT = "ArtNrFrakt";
	public static final  String SXREG_ARTNRFRAKT_DEFAULT = "0000";
	public static final  String SXREG_SERVERANVANDARE = "SXServDefaultAnvandare";
	public static final  String SXREG_SERVERANVANDARE_DEFAULT = "00";
	public static final  String SXREG_SXSERVSMTPUSER = "SxServSMTPUser";
	public static final  String SXREG_SXSERVSMTPUSER_DEFAULT = "";
	public static final  String SXREG_SXSERVSMTPPASSWORD = "SxServSMTPPassword";
	public static final  String SXREG_SXSERVSMTPASSWORD_DEFAULT = "";
	public static final  String SXREG_SXSERVSMTPSERVERPORT = "SxServSMTPServerPort";
	public static final  String SXREG_SXSERVSMTPSERVERPORT_DEFAULT = "25";

	public static final  String SXREG_SXSERVMAILFAKTURAFROMADRESS = "SxServMailFakturaFromAddress";
	public static final  String SXREG_SXSERVMAILFAKTURAFROMADRESS_DEFAULT = "info@saljex.se";
	public static final  String SXREG_SXSERVMAILFAKTURAFROMNAME = "SxServMailFakturaFromName";
	public static final  String SXREG_SXSERVMAILFAKTURAFROMNAME_DEFAULT = "Säljex AB";
	public static final  String SXREG_SXSERVMAILFAKTURASUBJEKTPREFIX = "SxServMailFakturaSubjectPrefix";
	public static final  String SXREG_SXSERVMAILFAKTURASUBJEKTPREFIX_DEFAULT = "Faktura ";
	public static final  String SXREG_SXSERVMAILFAKTURASUBJEKTSUFFIX = "SxServMailFakturaSubjectSuffix";
	public static final  String SXREG_SXSERVMAILFAKTURASUBJEKTSUFFIX_DEFAULT = " från Säljex AB";
	public static final  String SXREG_SXSERVMAILFAKTURABODYPREFIX = "SxServMailFakturaBodyPrefix";
	public static final  String SXREG_SXSERVMAILFAKTURABODYPREFIX_DEFAULT = "Här kommer din faktura!";
	public static final  String SXREG_SXSERVMAILFAKTURABODYSUFFIX = "SxServMailFakturaBodySuffix";
	public static final  String SXREG_SXSERVMAILFAKTURABODYSUFFIX_DEFAULT = "<br/>Med vänlig hälsning</br>Säljex AB";


	public static final  String SXREG_SXSERVMAILOFFERTFROMADRESS = "SxServMailOffertFromAddress";
	public static final  String SXREG_SXSERVMAILOFFERTFROMADRESS_DEFAULT = "info@saljex.se";
	public static final  String SXREG_SXSERVMAILOFFERTFROMNAME = "SxServMailOffertFromName";
	public static final  String SXREG_SXSERVMAILOFFERTFROMNAME_DEFAULT = "Säljex AB";
	public static final  String SXREG_SXSERVMAILOFFERTSUBJEKTPREFIX = "SxServMailOffertSubjectPrefix";
	public static final  String SXREG_SXSERVMAILOFFERTSUBJEKTPREFIX_DEFAULT = "Offert ";
	public static final  String SXREG_SXSERVMAILOFFERTSUBJEKTSUFFIX = "SxServMailOffertSubjectSuffix";
	public static final  String SXREG_SXSERVMAILOFFERTSUBJEKTSUFFIX_DEFAULT = " från Säljex AB";
	public static final  String SXREG_SXSERVMAILOFFERTBODYPREFIX = "SxServMailOffertBodyPrefix";
	public static final  String SXREG_SXSERVMAILOFFERTBODYPREFIX_DEFAULT = "Här kommer din offert!";
	public static final  String SXREG_SXSERVMAILOFFERTBODYSUFFIX = "SxServMailOffertBodySuffix";
	public static final  String SXREG_SXSERVMAILOFFERTBODYSUFFIX_DEFAULT = "<br/>Med vänlig hälsning</br>Säljex AB";

	public static final  String SXREG_SXSERVMAILFROMADRESS = "SxServMailFromAddress";
	public static final  String SXREG_SXSERVMAILFROMADRESS_DEFAULT = "info@saljex.se";
	public static final  String SXREG_SXSERVMAILFROMNAME = "SxServMailFromName";
	public static final  String SXREG_SXSERVMAILFROMNAME_DEFAULT = "Säljex AB";

	public static final	String SXREG_WORDER_SPARRAD_ORDER_SUBJECT = "sxServWorderSparradOrderSubject";
	public static final	String SXREG_WORDER_SPARRAD_ORDER_SUBJECT_DEFAULT = "Viktig information om order i Säljex webb-shop";
	public static final	String SXREG_WORDER_SPARRAD_ORDER_BODY = "sxServWorderSparradOrderBody";
	public static final	String SXREG_WORDER_SPARRAD_ORDER_BODY_DEFAULT = "Hej!<br/>Vi har mottagit en order i vår webb-shop som vi inte kan behandla därför att kreditgränsen är överskriden.<br/><br/>Var vänlig kontakta oss så vi kan reda ut ev. missförstånd.<br/><br/><br/>Med vänlig hälsning<br/>Säljex AB";

	public static final String BEST_SKICKASOM_EPOST = "epost";
	public static final String BEST_STATUS_SKAPAD = "Skapad";
	public static final String BEST_STATUS_VANTAR = "Väntar";
	public static final String BEST_STATUS_FEL = "Fel";
	public static final String BEST_STATUS_MOTTAGEN = "Mottagen";
	public static final String BEST_STATUS_SKICKAD = "Skickad";

	public static final  String BESTHAND_SKAPAD = "Skapad";
	public static final  String BESTHAND_MOTTAGEN = "Mottagen";
	public static final  String BESTHAND_LEVERANSBESKED = "Levbesked";
	public static final  String BESTHAND_FELINLOGGNING = "Fel inloggning";

	public static final  String EMAIL_VID_TESTLAGE = "ulf.hemma@gmail.com";  // Emailadress som ersätter automatiska utskicks epostadress vid körning i testläge
	public static final int			BEST_PAMIN_DAGAR_EFTER_SAND=2;					// Antal dagar från att fakturan är skickadd innan första påminnelsen går ut
	public static final int			BEST_PAMIN_DAGAR_MELLAN=2;					// Antal dagarmellan påminnelser
	
	public static final short		KAMPBIT_ELKUND	= 1;
	public static	final short		KAMPBIT_VVSKUND	= 2;
	public static	final short		KAMPBIT_VAKUND	= 4;
	public static	final short		KAMPBIT_GOLVKUND	= 8;
	public static	final short		KAMPBIT_FASTIGHETSKUND = 16;

	public static final short		KAMPBIT_INSTALLATOR	=1;
	public static	final short		KAMPBIT_BUTIK		= 2;
	public static	final short		KAMPBIT_INDUSTRI		= 4;
	public static	final short		KAMPBIT_OEM			= 8;
	public static	final short		KAMPBIT_GROSSIST		= 16;

	public static final String		BEHORIGHET_INTRA_LOGIN		= "IntraLogin";
	public static final String		BEHORIGHET_INTRA_ADMIN		= "IntraAdmin";
	public static final String		BEHORIGHET_INTRA_SUPERUSER	= "IntraSuperuser";
	public static final String		BEHORIGHET_EKONOMI			= "Ekonomi";
	public static final String		BEHORIGHET_STE_TEKNIK			= "STETeknik";

	public static final int DEFAULT_FT = 0; // Förvalt företag vid t.ex. tablefaktdat. Kommer kanske att ändra namn senare. ft är reserverat för att hålla reda på olika företag i samma databas, 
	
}
