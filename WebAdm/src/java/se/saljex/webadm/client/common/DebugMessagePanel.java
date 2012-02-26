/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class DebugMessagePanel  {

	private static Button hideBtn;
	private static VerticalPanel mainPanel;
	private static ScrollPanel scrollPanel;
	private static PopupPanel popupPanel=null;
	private static VerticalPanel messagePanel;

	private static void setUpWidgets() {
		if (popupPanel==null) {
			mainPanel = new VerticalPanel();
			scrollPanel = new ScrollPanel();
			popupPanel = new PopupPanel();
			messagePanel = new VerticalPanel();
			scrollPanel.setSize("10em", "30em");

			hideBtn=new Button("Dölj", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					popupPanel.hide();
				}
			});

			mainPanel.add(scrollPanel);
			mainPanel.add(hideBtn);
			scrollPanel.add(messagePanel);
			popupPanel.add(mainPanel);
		}
		popupPanel.center();
		popupPanel.show();
	}

	public static void addMessage(String s) {
		addMessage(new Label(s));
	}

	public static void addMessage(Widget widget) {
		setUpWidgets();
		messagePanel.add(widget);
		scrollPanel.scrollToBottom();
	}

}
