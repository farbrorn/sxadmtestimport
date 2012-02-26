/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.window;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import se.saljex.webadm.client.MainEntryPoint;

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
		init();
	}

	protected abstract void init();
	
	protected void add(String behorighet, MenuBar menu, String text, Command cmd) {
		if (behorighet==null || MainEntryPoint.isBehorig(behorighet)) {
			menu.addItem(text, cmd);
		}
	}
	
	protected void add(MenuBar menu, String text, Command cmd) {
		add(null, menu, text, cmd);
	}


}
