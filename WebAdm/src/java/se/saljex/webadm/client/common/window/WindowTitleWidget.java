/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.window;

import se.saljex.webadm.client.common.window.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class WindowTitleWidget extends HorizontalPanel {
	private final Window window;
	private Label titleAnchor;
	public WindowTitleWidget(final Window window) {
		super();
		this.window = window;
		
		titleAnchor = new Label(window.getWindowTitle());
		add(titleAnchor);
		titleAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				window.setWindowActive();
			}
		});
		
		addCloseBtn();
	}
	
	public void addCloseBtn() {
		Label closeLabel = new Label("X");
		add(closeLabel);
		
		closeLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				window.closeWindow();
			}
		});
	}


	public Window getWindow() { return window;}
	public void setActive(boolean active) {
		if (active) {
			this.setStyleName("w-tabtitle-active");
		} else {
			this.setStyleName("w-tabtitle-passive");
		}
	}

}
