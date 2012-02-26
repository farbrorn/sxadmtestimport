/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import se.saljex.webadm.client.common.window.AbstractSxMenuBar;
import se.saljex.webadm.client.common.window.WindowHandler;
import se.saljex.webadm.client.orderregistrering.OrderMain;

/**
 *
 * @author Ulf
 */
public class SxMenuBar extends AbstractSxMenuBar {

	private final MenuBar menuArkiv = new MenuBar(true);
	private final MenuBar menuRegistrera = new MenuBar(true);
	private final MenuBar menuUnderhall = new MenuBar(true);
	private final MenuBar menuFragor = new MenuBar(true);
	private final MenuBar menuSystemAdmin = new MenuBar(true);
	private final MenuBar menuInfoString = new MenuBar(true);

	public SxMenuBar(WindowHandler windowHandler, Command logoutCommand, String infoString) {
		super(windowHandler, logoutCommand, infoString);
	}

	@Override
	protected void init() {
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


}
