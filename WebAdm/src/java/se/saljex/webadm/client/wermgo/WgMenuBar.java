/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.wermgo;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import se.saljex.webadm.client.common.window.AbstractSxMenuBar;
import se.saljex.webadm.client.common.window.WindowHandler;

/**
 *
 * @author Ulf
 */
public class WgMenuBar extends AbstractSxMenuBar {

	private final MenuBar menuArkiv = new MenuBar(true);
	private final MenuBar menuUnderhall = new MenuBar(true);
	private final MenuBar menuInfoString = new MenuBar(true);


	public WgMenuBar(WindowHandler windowHandler, Command logoutCommand, String infoString) {
		super(windowHandler, logoutCommand, infoString);
	}

	protected void init() {
		//Arkiv
		add(menuArkiv, "Logga Ut", logoutCommand);


		//Underhåll
		add(menuUnderhall, "Offerter", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OffertListaWidget(true, true), "Offrerter");	}	});

		//Lägg till huvudmenybaren
		addItem("Arkiv", menuArkiv);
		addItem("Underhåll", menuUnderhall);
		if (infoString!=null) addItem(infoString, menuInfoString);
	}

}
