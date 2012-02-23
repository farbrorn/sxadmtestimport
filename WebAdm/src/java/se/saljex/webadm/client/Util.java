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
import com.google.gwt.user.client.Window;
import se.saljex.webadm.client.constants.Const;

/**
 *
 * @author Ulf
 */
public class Util {

	public final static NumberFormat fmt0Dec = NumberFormat.getFormat("0");
	public final static NumberFormat fmt1Dec = NumberFormat.getFormat("0.0");
	public final static NumberFormat fmt2Dec = NumberFormat.getFormat("0.00");
	private static GWTServiceAsync service=null;

	private static final PopupPanel waitModalPopup = new PopupPanel(false, true);

	private static DialogBox messageBox=null;
	private static VerticalPanel messageBoxPanel;
	private static Button messageBoxOkBtn;

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
		showModalMessage(widget, true);
	}
	public static void showModalMessage(Widget widget, boolean showOkBtn) {
		if (messageBox==null) { 
			messageBox = new DialogBox(true, true);
			messageBoxPanel = new VerticalPanel();
			messageBoxOkBtn = new Button("OK", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					hideModalMessage();
				}
			});
			messageBoxOkBtn.addStyleName(Const.Style_Margin_1em_Top);
			messageBoxPanel.addStyleName(Const.Style_Padding_05em);
		}
		if (showOkBtn) {
			messageBoxPanel.clear();
			messageBoxPanel.add(widget);
			messageBoxPanel.add(messageBoxOkBtn);
			messageBox.setWidget(messageBoxPanel);
		} else {
			messageBox.setWidget(widget);
		}
		messageBox.center();
		messageBox.show();
	}

	public static void hideModalMessage() {
		messageBox.hide();
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
	
	public static String getUrlPath() {
		String path = Window.Location.getHref();
		int index = path.lastIndexOf('/');
		return path.substring(0, index+1);
	}
	
	public static String getApiUrlPath() {
		return getUrlPath() + "api/";
	}
}
