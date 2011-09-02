/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.window.WindowHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import se.saljex.webadm.client.rpcobject.User;

/**
 *
 * @author Ulf
 */
public class SxMenuBar extends MenuBar {

	private final MenuBar menuArkiv = new MenuBar(true);
	private final MenuBar menuRegistrera = new MenuBar(true);
	private final MenuBar menuUnderhall = new MenuBar(true);
	private final MenuBar menuFragor = new MenuBar(true);
	private final MenuBar menuSystemAdmin = new MenuBar(true);
	private final User user;
	private final WindowHandler windowHandler;

	public SxMenuBar(WindowHandler windowHandler, User user) {
		super();
		this.user=user;
		this.windowHandler=windowHandler;
		init();
	}

	private void init() {
		//Arkiv
		add(menuArkiv, "Tomt", new Command() {	@Override	public void execute() {		}	});

		//Registrera
		add(menuRegistrera, "Tomt", new Command() {	@Override	public void execute() {		}	});

		//Underhåll
		add(menuUnderhall, "Artikel", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new WelcomeWindow(windowHandler, "Välkommen"));	}	});
		add(menuUnderhall,"Kund", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new KundForm(windowHandler, "Kunder"));	}	});
		add(menuUnderhall,"Kundlista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new KundInfoMainWidget(), "Kund");	}	});
		add(menuUnderhall,"Offertlista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OffertListaWidget(), "Offert");	}	});

		//Frågor
		add(menuFragor, "Tomt", new Command() {	@Override	public void execute() {		}	});

		//SystemAdmin
		add(menuSystemAdmin, "Tomt", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new ServerStatus(), "Välkommen");	}	});

		//Lägg till huvudmenybaren
		addItem("Arkiv", menuArkiv);
		addItem("Registrera", menuRegistrera);
		if (user.isBehorig(user.FaktArtAndraPris)) {
			addItem("Underhåll", menuUnderhall);
		}
		addItem("Frågor", menuFragor);
		if (user.isBehorig(user.FaktAdmin)) {
			addItem("System adm", menuSystemAdmin);
		}
	}
	private void add(String behorighet, MenuBar menu, String text, Command cmd) {
		if (behorighet==null || user.isBehorig(behorighet)) {
			menu.addItem(text, cmd);
		}
	}
	private void add(MenuBar menu, String text, Command cmd) {
		add(null, menu, text, cmd);
	}


}
