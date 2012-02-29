/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import se.saljex.webadm.client.MainEntryPoint;
import se.saljex.webadm.client.common.window.WindowHandler;

/**
 *
 * @author Ulf
 */
public abstract class AbstractSxMenuBar extends MenuBar {


	protected final WindowHandler windowHandler;
	protected String infoString;
	protected final Command logoutCommand;

	public AbstractSxMenuBar(WindowHandler windowHandler, Command logoutCommand, String infoString) {
		super();
		this.infoString = infoString;
		this.logoutCommand = logoutCommand;
		this.windowHandler=windowHandler;
	}

	
	protected void add(String behorighet, MenuBar menu, String text, Command cmd) {
		if (MainEntryPoint.isBehorig(behorighet)) {
			menu.addItem(text, cmd);
		}
	}
	
	protected void add(MenuBar menu, String text, Command cmd) {
		add(null, menu, text, cmd);
	}


}
