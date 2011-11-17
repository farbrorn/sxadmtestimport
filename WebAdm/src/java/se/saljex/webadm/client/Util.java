/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.KeyCodes;

/**
 *
 * @author Ulf
 */
public class Util {

	public final static NumberFormat fmt0Dec = NumberFormat.getFormat("0");
	public final static NumberFormat fmt1Dec = NumberFormat.getFormat("0.0");
	public final static NumberFormat fmt2Dec = NumberFormat.getFormat("0.00");
	private static GWTServiceAsync service=null;

	private static final DialogBox waitModalPopup = new DialogBox(false, true);

	private static PopupPanel messageBox=null;

	public static void showModalWait() {
		if (waitModalPopup.getWidget()==null) waitModalPopup.setWidget(new Label("Arbetar..."));
		waitModalPopup.center();
		waitModalPopup.show();
	}
	
	public static void hideModalWait() {
		waitModalPopup.hide();
	}


	public static GWTServiceAsync getService() {
		if (service==null) service = GWT.create(GWTService.class);
		return service;
	}

	public static String getAnvandareKort() {
		return "x";
	}

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
		return date==null ? "" : date.toString();
	}
	public static String formatDate(java.util.Date date) {
		return date==null ? "" : date.toString();
	}


	public static void showModalMessage(String text) {
		showModalMessage(new Label(text));
	}
	public static void showModalMessage(Widget widget) {
		if (messageBox==null) messageBox = new PopupPanel(true, true);
		messageBox.setWidget(widget);
		messageBox.center();
		messageBox.show();
	}

	public static boolean isEmpty(String s) {
		if (s==null) return true;
		if (s.isEmpty()) return true;
		return false;
	}

	public static String noNull(String s) {
		if (s==null) return "";
		return s;
	}
}
