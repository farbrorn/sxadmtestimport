/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.window.WindowHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import java.util.ArrayList;
import se.saljex.webadm.client.orderregistrering.OrderMain;

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
	private final MenuBar menuInfoString = new MenuBar(true);
//	private final User user;
	private ArrayList<String> arrBehorighet=null;

	private final WindowHandler windowHandler;

	private String infoString;

	private final Command logoutCommand;

	public SxMenuBar(WindowHandler windowHandler, ArrayList<String> arrBehorighet, Command logoutCommand, String infoString) {
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

		//Registrera
		add(menuRegistrera, "Order", new Command() {	@Override	public void execute() {windowHandler.addWindow(new OrderMain(), "Registrera Order");	}	});

		//Underhåll
		add(menuUnderhall, "Artikel", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new WelcomeWindow(windowHandler, "Välkommen"));	}	});
//		add(menuUnderhall,"Kund", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new KundForm(windowHandler, "Kunder"));	}	});
		add(menuUnderhall,"Kundlista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new KundInfoMainWidget(), "Kund");	}	});
		add(menuUnderhall,"Artikellista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new ArtikelInfoMainWidget(), "Artikel");	}	});
		add(menuUnderhall,"Orderlista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OrderListaWidget(true,true), "Order");	}	});
		add(menuUnderhall,"Offertlista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OffertListaWidget(true,true), "Offert");	}	});
		add(menuUnderhall,"Fakturalista", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new FakturaListWidget(true,true), "Fakturor");	}	});
		add(menuUnderhall,"Utleveranser", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new UtlevListWidget(true,true), "Utleveranser");	}	});
		add(menuUnderhall,"Överför Utleverans", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OverforUtlevMainWidget(), "Överför Utleverans");	}	});
		add(menuUnderhall,"Överför Order", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new OverforOrderMainWidget(), "Överför Order");	}	});

		//Frågor
		add(menuFragor, "Tomt", new Command() {	@Override	public void execute() {		}	});

		//SystemAdmin
		add(menuSystemAdmin, "Serverstatus", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new ServerStatus(), "Välkommen");	}	});
		add(menuSystemAdmin, "Serverstatus", new Command() {	@Override	public void execute() {	windowHandler.addWindow(new ServerStatus(), "Välkommen");	}	});

		//Lägg till huvudmenybaren
		addItem("Arkiv", menuArkiv);
		addItem("Registrera", menuRegistrera);
		addItem("Underhåll", menuUnderhall);
		addItem("Frågor", menuFragor);
		addItem("System adm", menuSystemAdmin);
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
