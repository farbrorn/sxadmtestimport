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
	
	public static final  String SXREG_ARTNRFRAKT = "ArtNrFrakt";
	public static final  String SXREG_ARTNRFRAKT_DEFAULT = "0000";
	public static final  String SXREG_SERVERANVANDARE = "SXServDefaultAnvandare";
	public static final  String SXREG_SERVERANVANDARE_DEFAULT = "00";
	
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

	
	public static final int DEFAULT_FT = 0; // Förvalt företag vid t.ex. tablefaktdat. Kommer kanske att ändra namn senare. ft är reserverat för att hålla reda på olika företag i samma databas, 
	
}
