/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public class FormInputPanel<T extends IsSQLTable> {

	private ArrayList<FormWidgetInterface> focusWidgets = new ArrayList();
	public FormInputPanel() {
	}

	public void addFocusWidget(final FormWidgetInterface focusWidget) {

		focusWidgets.add(focusWidget);

		focusWidget.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN || keycode==KeyCodes.KEY_ENTER) {
					setFocusNext(focusWidget);
				} else if (keycode==KeyCodes.KEY_UP) {
					setFocusPrevious(focusWidget);
				}
			}
		});


	}

	public void set(T value) {
		for (FormWidgetInterface w : focusWidgets) {
			w.getFormWidgetGetSet().set(value);
		}
	}
	public void get(T value) {
		for (FormWidgetInterface w : focusWidgets) {
			w.getFormWidgetGetSet().get(value);
		}
	}

	
	public void setFocusNext(FormWidgetInterface curr) {
		int currPos = focusWidgets.indexOf(curr);
		if (currPos>=focusWidgets.size()-1) currPos=0; else currPos++;
		focusWidgets.get(currPos).setFocus(true);
	}
	public void setFocusPrevious(FormWidgetInterface curr) {
		int currPos = focusWidgets.indexOf(curr);
		if (currPos<=0) currPos=focusWidgets.size()-1; else currPos--;
		focusWidgets.get(currPos).setFocus(true);
	}


}
