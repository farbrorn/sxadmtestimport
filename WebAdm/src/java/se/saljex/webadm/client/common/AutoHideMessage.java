/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.common;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import se.saljex.webadm.client.commmon.constants.Const;

/**
 *
 * @author Ulf
 */
public class AutoHideMessage extends PopupPanel{

	private final Label label = new Label();
	private	Timer t = new Timer() {

			@Override
			public void run() {
				hidePanel();
			}
		};
	
	public AutoHideMessage() {
		super();
		this.addStyleName(Const.Style_AutoHideMessage);
		this.add(label);
	}
	
	public void hidePanel() {
		this.hide();
	}
	
	public void showMsg(String text, int delay) {
		label.setText(text);
		this.center();
		this.setPopupPosition(this.getAbsoluteLeft(), 0);
		this.show();
		t.schedule(delay);
	}
	public void showMsg(String text) {
		showMsg(text, 5000);
	}
	
}
