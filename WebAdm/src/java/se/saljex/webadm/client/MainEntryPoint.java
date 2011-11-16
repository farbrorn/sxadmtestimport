/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client;

import se.saljex.webadm.client.window.WindowHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.rpcobject.InloggadAnvandare;

/**
 * Main entry point.
 *
 * @author Ulf
 */
public class MainEntryPoint implements EntryPoint {
    /** 
     * Creates a new instance of MainEntryPoint
     */
	private final WindowHandler windowHandler = new WindowHandler();
	VerticalPanel rootVP = new VerticalPanel();
	private static InloggadAnvandare inloggadAnvandare=null;

    public MainEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
//		ArrayList<String> behorighetList = new ArrayList();
//		behorighetList.add("FaktAdmin");
//		behorighetList.add("FaktLogin");
//		User user = new User();
//		user.setUser("UB", "Ulf Berg", "ulf@saljex.se", behorighetList);

	   RootLayoutPanel.get().add(rootVP);
	   showLogin();
    }

	public static GWTServiceAsync getService() {
		return GWT.create(GWTService.class);
	}

	private void showLogin() {
		rootVP.clear();
		rootVP.add(new LoginWidget(null));

	}

	private void showMain() {
		rootVP.clear();
		rootVP.add(new SxMenuBar(windowHandler, inloggadAnvandare.arrBehorighet));
		rootVP.add(windowHandler);
	}

	AsyncCallback<InloggadAnvandare> callbackLogin = new AsyncCallback<InloggadAnvandare>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.showModalMessage("Inloggningen misslyckades.");
		}

		@Override
		public void onSuccess(InloggadAnvandare result) {
			inloggadAnvandare = result;
			showMain();
		}
	};

	public static InloggadAnvandare getInloggadAnvandare() { return inloggadAnvandare; }

}
