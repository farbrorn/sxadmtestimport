/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */

// Håller ordning på inutwidgetar och lägger till lyssnare för enter, oil upp och pil ner för att navigera mellan dem
public class FormNavigator {
	private ArrayList<FocusWidget> focusWidgets = new ArrayList<FocusWidget>();
	
	public void add(final FocusWidget widget) {
		focusWidgets.add(widget);
		if (widget instanceof TextBoxBase) {
			widget.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					int keycode = event.getNativeKeyCode();
					if (!event.isAnyModifierKeyDown()) {
						if (keycode==KeyCodes.KEY_DOWN || keycode==KeyCodes.KEY_ENTER) {
							event.preventDefault();
							setFocusNext(widget);
						} else if (keycode==KeyCodes.KEY_UP) {
							event.preventDefault();
							setFocusPrevious(widget);
						}
					}
				}
			});
		} else if (widget instanceof ButtonBase || widget instanceof SimpleCheckBox) {
			widget.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					int keycode = event.getNativeKeyCode();
					if (!event.isAnyModifierKeyDown()) {
						if (keycode==KeyCodes.KEY_DOWN ) {
							event.preventDefault();
							setFocusNext(widget);
						} else if (keycode==KeyCodes.KEY_UP) {
							event.preventDefault();
							setFocusPrevious(widget);
						}
					}
				}
			});

		}
	}

	public void setFocusNext(FocusWidget curr) {
		int currPos = focusWidgets.indexOf(curr);
		if (currPos>=focusWidgets.size()-1) currPos=0; else currPos++;
		focusWidgets.get(currPos).setFocus(true);
	}
	public void setFocusPrevious(FocusWidget curr) {
		int currPos = focusWidgets.indexOf(curr);
		if (currPos<=0) currPos=focusWidgets.size()-1; else currPos--;
		focusWidgets.get(currPos).setFocus(true);
	}

}
