/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import java.util.Date;
import se.saljex.SxShop.client.rpcobject.Anvandare;

/**
 *
 * @author ulf
 */
public class GlobalData {
	public SxShopRPCAsync service=null;
	public Anvandare anvandare=null;
	public final String smallArtURL="http://www.saljex.se/pics/";
	public boolean isLoggedIn() {
		return !anvandare.gastlogin;
	}

	public final String IconInfoSmallURL = "info16.png";
	public final String IconInfoBigURL = "info32.png";
	public final String ImageSuffix = ".png";
	public final int SmallImageHeight = 30;

	public final NumberFormat numberFormat = NumberFormat.getFormat("0.00");
	public final NumberFormat numberFormatInt = NumberFormat.getFormat("0");

	public  final String IMG_BTN_TICK = "kn-tick.png";
	public  final String IMG_BTN_TRASH = "kn-trash.png";
	public  final String IMG_LOGO = "logo.png";


	public  final String STYLE_ANTALTEXTBOX = "sx-antaltextbox";
	public  final String STYLE_PUSHBUTTON = "sx-pushbutton";
	public  final String STYLE_ERROR = "sx-feltext";
	public  final String STYLE_HUVUDRUBRIK = "sx-huvudrubrik";
	public  final String STYLE_UNDERRUBRIK = "sx-underrubrik";
	public  final String STYLE_MENYUNDERRUBRIK = "sx-menyunderrubrik";
	public  final String STYLE_KLASERUBRIK = "sx-klaserubrik";
	public  final String STYLE_MAINPANEL = "sx-mainpanel";
	public  final String STYLE_PROMPT = "sx-prompt";
	public  final String STYLE_HORIZONTALPANEL = "sx-horizontalpanel";
	public  final String STYLE_MARGINRIGHT4 = "sx-marginright4";
	public  final String STYLE_SOKKNAPP = "sx-sokknapp";
	public  final String STYLE_SOKINPUT = "sx-sokinput";
	public  final String STYLE_INPUTDATA = "sx-inputdata";
	public  final String STYLE_HEADERPANEL = "sx-headerpanel";
	public  final String STYLE_HEADERSCROLLPANEL = "sx-headerscrollpanel";
	public  final String STYLE_TR_RUBRIK = "sx-tablerubrik";
	public  final String STYLE_TR_ODDROW = "sx-tr-oddrow";
	public  final String STYLE_TD_DROPDOWNWIDGET = "sx-tb-dropdownwidget";
	public  final String STYLE_TR_HIGHLITE = "sx-tr-highlite";
	public  final String STYLE_TD_ARTNR = "sx-tb-artnr";
	public  final String STYLE_TD_BENAMNING = "sx-tb-benamning";
	public  final String STYLE_TD_RAB = "sx-tb-rab";
	public  final String STYLE_TD_ENHET = "sx-tb-enhet";
	public  final String STYLE_TD_PRIS = "sx-tb-pris";
	public  final String STYLE_TD_MANGD = "sx-tb-mangd";
	public  final String STYLE_TD_IDNR = "sx-tb-idnr";
	public  final String STYLE_TD_DATUM = "sx-tb-datum";
	public  final String STYLE_TD_MARKE = "sx-tb-marke";
	public  final String STYLE_TD_MARKEINFO = "sx-tb-markeinfo";
	public  final String STYLE_TD_ACTION = "sx-tb-action";
	public  final String STYLE_TD_S5 = "sx-tb-s5";
	public  final String STYLE_TD_S10 = "sx-tb-s10";
	public  final String STYLE_TD_S15 = "sx-tb-s15";
	public  final String STYLE_TD_S20 = "sx-tb-s20";
	public  final String STYLE_TD_S30 = "sx-tb-s30";
	public  final String STYLE_TD_N5 = "sx-tb-n5";
	public  final String STYLE_TD_ANDRA = "sx-tb-andra";
	public  final String STYLE_TD_TABORT = "sx-tb-tabort";
	public  final String STYLE_TABLE_INFO = "sx-tableinfo";
	public  final String STYLE_DROPDOWNWIDGET = "sx-dropdownwidget";

	public String getDateString(Date datum) {
		return DateTimeFormat.getFormat("yyyy-MM-dd").format(datum);
	}
}
