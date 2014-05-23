/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxlibrary;

/**
 *
 * @author ulf
 */
public class SXConstant {

	public static final  String ORDER_STATUS_SPARAD = "Sparad";
	public static final  String ORDER_STATUS_DIREKTLEV = "Direkt";
	public static final  String ORDER_STATUS_SIMPLEORDER = "Simple";
	public static final  String ORDER_STATUS_VANTAR = "Väntar";
	public static final  String ORDER_STATUS_AVVAKT = "Avvakt";
	public static final  String ORDER_STATUS_OVERFORD = "Överf";
	public static final  String ORDER_STATUS_SAMFAK = "Samfak";
	public static final  String ORDER_STATUS_HAMT = "Hamt";
	public static final  String ORDER_STATUS_FORSKOTT = "Försk";

	
	public static final  String ORDERHAND_SKAPAD = "Skapad";
	public static final  String ORDERHAND_RADERAD = "Raderad";
	public static final  String ORDERHAND_ANDRAD = "Ändrad";
	public static final  String ORDERHAND_FAKTURERAD = "Fakturerad";
	public static final  String ORDERHAND_UTSKRIVEN = "Utskriven";

	public static final  String HANDELSE_SKAPAD = "Skapad";
	public static final  String HANDELSE_RADERAD = "Raderad";
	public static final  String HANDELSE_ANDRAD = "Ändrad";
	
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
	public static final  String SXREG_SXSERVSMTPTRANSPORT = "SxServSMTPTransport";
	public static final  String SXREG_SXSERVSMTPTRANSPORT_DEFAULT = "smtp";

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

	public static final  String SXREG_SXSERVMAILBESTFROMADRESS = "SxServMailBestFromAddress";
	public static final  String SXREG_SXSERVMAILBESTFROMADRESS_DEFAULT = "inkop@saljex.se";
	public static final  String SXREG_SXSERVMAILBESTFROMNAME = "SxServMailBestFromName";
	public static final  String SXREG_SXSERVMAILBESTFROMNAME_DEFAULT = "inkop@saljex.se";


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

	public static final  String SXREG_SXSERVADMINMAIL = "SxServAdminMail";
	public static final  String SXREG_SXSERVADMINMAIL_DEFAULT = "ulf@saljex.se";

	public static final  String SXREG_SXSERVSAMFAKTAKTIVERAD = "SxServSamfaktAktiverad";
	public static final  String SXREG_SXSERVSAMFAKTAKTIVERAD_DEFAULT = "Nej";
	public static final  String SXREG_SXSERVSAMFAKTMINBELOPP = "SxServSamfaktMinBelopp";		//Minsta totala orderbelopp för att tillåta samfakt
	public static final  String SXREG_SXSERVSAMFAKTMINBELOPP_DEFAULT = "1000";
	public static final  String SXREG_SXSERVSAMFAKTMAXDAGAR = "SxServSamfaktMaxDagar";			//Antalet dagar gammal order för att trigga samfakt utan beloppsgräns
	public static final  String SXREG_SXSERVSAMFAKTMAXDAGAR_DEFAULT = "21";


	public static final	String SXREG_WORDER_SPARRAD_ORDER_SUBJECT = "sxServWorderSparradOrderSubject";
	public static final	String SXREG_WORDER_SPARRAD_ORDER_SUBJECT_DEFAULT = "Viktig information om order i Säljex webb-shop";
	public static final	String SXREG_WORDER_SPARRAD_ORDER_BODY = "sxServWorderSparradOrderBody";
	public static final	String SXREG_WORDER_SPARRAD_ORDER_BODY_DEFAULT = "Hej!<br/>Vi har mottagit en order i vår webb-shop som vi inte kan behandla därför att kreditgränsen är överskriden.<br/><br/>Var vänlig kontakta oss så vi kan reda ut ev. missförstånd.<br/><br/><br/>Med vänlig hälsning<br/>Säljex AB";


	public static final	String SXREG_OVERFOR_ORDER_KUNDNR = "sxServOverforOrderKundnr";
	public static final	String SXREG_OVERFOR_ORDER_KUNDNR_DEFAULT = "1";
	public static final  String SXREG_OVERFOR_ORDER_ALLOWUSEBESTNR = "SxServOverforOrderAllowUseBestnr";
	public static final  String SXREG_OVERFOR_ORDER_ALLOWUSEBESTNR_DEFAULT = "true";
	public static final	String SXREG_TESTLAGE = "SxServTestlage";
	public static final	String SXREG_TESTLAGE_DEFAULT = "Ja";


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
	public static final int			BEST_PAMIN_DAGAR_EFTER_SAND=10000; 					// Antal dagar från att fakturan är skickadd innan första påminnelsen går ut
	public static final int			BEST_PAMIN_DAGAR_MELLAN=10000;					// Antal dagarmellan påminnelser
	
	
	//Ändra inte kampbitarnas konstanta värde - de används på fler ställen, t.ex. i SQL VARTKUNDORDER
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

	
	public static final String		BEHORIGHET_BOK_LOGIN			= "BokLogin";
	public static final String		BEHORIGHET_EKONOMI			= "Ekonomi";
	public static final String		BEHORIGHET_FAKT_ADMIN			= "FaktAdmin";
	public static final String		BEHORIGHET_FAKT_FAKTURANEGTB			= "FaktFakturaNegTB";
	public static final String		BEHORIGHET_FAKT_ART_ANDRA			= "FaktArtAndra";
	public static final String		BEHORIGHET_FAKT_ART_ANDRA_PRIS			= "FaktArtAndraPris";
	public static final String		BEHORIGHET_FAKT_ART_NY			= "FaktArtNy";
	public static final String		BEHORIGHET_FAKT_ART_RADERA			= "FaktArtRadera";
	public static final String		BEHORIGHET_FAKT_ART_SE_INPRIS			= "FaktArtSeInpris";
	public static final String		BEHORIGHET_FAKT_BEST		= "FaktBest";
	public static final String		BEHORIGHET_FAKT_BEST_EJBEHOV			= "FaktBestEjBehov";
	public static final String		BEHORIGHET_FAKT_BEST_LAGERVARDE			= "FaktBestLagervärde";
	public static final String		BEHORIGHET_FAKT_FAKTURA			= "FaktFaktura";
	public static final String		BEHORIGHET_FAKT_FAKTURA_NEG_TB			= "FaktFakturaNegTB";
	public static final String		BEHORIGHET_FAKT_INLEV			= "FaktInlev";	
	public static final String		BEHORIGHET_FAKT_KUND_ANDRA			= "FaktKundAndra";
	public static final String		BEHORIGHET_FAKT_KUND_ANDRA_KGRANS			= "FaktKundAndraKGrans";
	public static final String		BEHORIGHET_FAKT_KUND_NY			= "FaktKundNy";
	public static final String		BEHORIGHET_FAKT_KUND_RADERA			= "FaktKundRadera";
	public static final String		BEHORIGHET_FAKT_KUNDRES			= "FaktKundres";
	public static final String		BEHORIGHET_FAKT_LEV_ANDRA			= "FaktLevAndra";
	public static final String		BEHORIGHET_FAKT_LEV_NY			= "FaktLevNy";
	public static final String		BEHORIGHET_FAKT_LEV_RADERA			= "FaktLevRadera";
	public static final String		BEHORIGHET_FAKT_LEV_SE_FAKTUROR			= "FaktLevSeFakturor";
	public static final String		BEHORIGHET_FAKT_LEVRES			= "FaktLevRes";
	public static final String		BEHORIGHET_FAKT_LOGIN		= "FaktLogin";
	public static final String		BEHORIGHET_FAKT_ORDER_KGRANS			= "FaktOrderKGrans";
	public static final String		BEHORIGHET_FAKT_SAMFAKT		= "FaktSamfakt";
	public static final String		BEHORIGHET_INTRA_ADMIN		= "IntraAdmin";
	public static final String		BEHORIGHET_INTRA_LOGIN		= "IntraLogin";
	public static final String		BEHORIGHET_INTRA_SUPERUSER	= "IntraSuperuser";
	public static final String		BEHORIGHET_LON_LOGIN			= "LonLogin";
	public static final String		BEHORIGHET_STE_TEKNIK			= "STETeknik";

	public static final int DEFAULT_FT = 0; // Förvalt företag vid t.ex. tablefaktdat. Kommer kanske att ändra namn senare. ft är reserverat för att hålla reda på olika företag i samma databas, 
	
}
