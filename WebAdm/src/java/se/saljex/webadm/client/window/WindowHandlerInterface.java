/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.window;

import se.saljex.webadm.client.window.Window;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public interface WindowHandlerInterface {

	public void closeWindow(Window window);
	public void setWindowActive(Window window);
	public void addWindow(Widget widget, String title);
	public void addWindow(Window window);
}

