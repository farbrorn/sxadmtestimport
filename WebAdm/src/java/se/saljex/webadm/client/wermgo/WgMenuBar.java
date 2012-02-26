/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.wermgo;

import se.saljex.webadm.client.*;
import se.saljex.webadm.client.common.window.WindowHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import java.util.ArrayList;
import se.saljex.webadm.client.orderregistrering.OrderMain;

/**
 *
 * @author Ulf
 */
public class WgMenuBar extends MenuBar {

	private final MenuBar menuArkiv = new MenuBar(true);
	private final MenuBar menuRegistrera = new MenuBar(true);
	private final MenuBar menuUnderhall = new MenuBar(true);
	private final MenuBar menuFragor = new MenuBar(true);
	private final MenuBar menuSystemAdmin = new MenuBar(true);
	private final MenuBar menuInfoString = new MenuBar(true);
//	private final User user;
	private ArrayList<String> arrBehorighet=null;

	private final WindowHandler windowHandler;

	private String infoString;

	private final Command logoutCommand;

	public WgMenuBar(WindowHandler windowHandler, ArrayList<String> arrBehorighet, Command logoutCommand, String infoString) {
		super();
		this.infoString = infoString;
		this.logoutCommand = logoutCommand;
		this.arrBehorighet=arrBehorighet;
//		this.user=user;
		this.windowHandler=windowHandler;
		init();
	}

	private void init() {
		//Arkiv
		add(menuArkiv, "Välkommen", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new WelcomeWidget(), "Välkommen");	}	});
		add(menuArkiv, "Logga Ut", logoutCommand);


		//Underhåll
		add(menuUnderhall, "Offerter", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OffertListaWidget(true, true), "Offrerter");	}	});

		//Lägg till huvudmenybaren
		addItem("Arkiv", menuArkiv);
		addItem("Underhåll", menuUnderhall);
		if (infoString!=null) addItem(infoString, menuInfoString);
	}
	private void add(String behorighet, MenuBar menu, String text, Command cmd) {
		if (behorighet==null || (arrBehorighet!= null && arrBehorighet.contains(behorighet))) {
			menu.addItem(text, cmd);
		}
	}
	private void add(MenuBar menu, String text, Command cmd) {
		add(null, menu, text, cmd);
	}


}
