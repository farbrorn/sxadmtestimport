/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.window;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class Window  implements WindowInterface{
	final protected  WindowHandler windowHandler;
	protected  String windowTitle;
	protected  Widget windowWidget;
	public Window (WindowHandler windowHandler, Widget widget, String title) {
		this.windowHandler = windowHandler;
		this.windowWidget = widget;
		this.windowTitle = title;

	}
	public Window (WindowHandler windowHandler, String title) {
		this.windowHandler = windowHandler;
		this.windowWidget = null;
		this.windowTitle = title;

	}


	@Override
	public void closeWindow() {
		windowHandler.closeWindow(this);
	}

	@Override
	public void setWindowActive() {
		windowHandler.setWindowActive(this);
	}

	public String getWindowTitle() { return windowTitle; }
	public Widget getWindowWidget() {return windowWidget; }

}
