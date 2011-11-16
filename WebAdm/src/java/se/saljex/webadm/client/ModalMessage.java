/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class ModalMessage {

	private static DialogBox messageBox = null;
	private static Button okBtn = null;
	private static Grid grid = null;

	public static void show(String meddelande) {
		show(new Label(meddelande));
	}
	public static void show(Widget widget) {
		if (messageBox==null) {
			messageBox = new DialogBox(false, true);
			grid = new Grid(2, 1);
			okBtn = new Button("OK", new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					messageBox.hide();
				}
			});
			grid.setWidget(1, 0, okBtn);
			messageBox.setWidget(grid);
		}
		grid.setWidget(0, 0, widget);
		messageBox.center();
		messageBox.show();
	}

}
