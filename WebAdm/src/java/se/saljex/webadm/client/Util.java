/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class Util {

	private final static NumberFormat fmt0Dec = NumberFormat.getFormat("0");
	private final static NumberFormat fmt1Dec = NumberFormat.getFormat("0.0");
	private final static NumberFormat fmt2Dec = NumberFormat.getFormat("0.00");

	public static String format0Dec(Double value) {
		if (value==null)  return "";
		return fmt0Dec.format(value);
	}
	public static String format1Dec(Double value) {
		if (value==null)  return "";
		return fmt1Dec.format(value);
	}
	public static String format2Dec(Double value) {
		if (value==null)  return "";
		return fmt2Dec.format(value);
	}

	public static String formatDate(java.sql.Date date) {
		return date.toString();
	}
	public static String formatDate(java.util.Date date) {
		return date.toString();
	}


}
