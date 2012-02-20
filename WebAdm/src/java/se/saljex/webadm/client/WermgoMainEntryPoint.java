/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.rpcobject.InitialData;
import se.saljex.webadm.client.rpcobject.InloggadAnvandare;
import se.saljex.webadm.client.window.WindowHandler;
import se.saljex.webadm.client.wermgo.WgMenuBar;

/**
 * Main entry point.
 *
 * @author Ulf
 */
public class WermgoMainEntryPoint implements EntryPoint {
	private static VerticalPanel rootVP = new VerticalPanel();
//	private static InloggadAnvandare inloggadAnvandare=null;
	private static InitialData initialData = null;
	private static final WindowHandler windowHandler = new WindowHandler();
	
    /** 
     * Creates a new instance of MainEntryPoint
     */

    public WermgoMainEntryPoint() {
    }
	private static AsyncCallback<InitialData> inititalDataCallback = new AsyncCallback<InitialData>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.hideModalWait();
			Util.showModalMessage("Kan inte läsa initialData. Försök ladda om sidan. Orsak: " + caught.getMessage());
		}

		@Override
		public void onSuccess(InitialData result) {
			Util.hideModalWait();
			setUpInitialData(result);
		}
	};

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
	@Override
    public void onModuleLoad() {

	   RootLayoutPanel.get().add(rootVP);
	   Util.showModalWait();
	   getService().getInitialData(inititalDataCallback);

    }

	private static Command logoutCommand = new Command() {
		@Override
		public void execute() {
			Util.showModalWait();
			Util.getService().logout(logoutCallback);
		}
	};

	private static AsyncCallback logoutCallback = new AsyncCallback() {

		@Override
		public void onFailure(Throwable caught) {
			Util.hideModalWait();
			initialData.inloggadAnvandare=null;
			Util.showModalMessage("Fel vid utloggning: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Object result) {
			Util.hideModalWait();
			initialData.inloggadAnvandare=null;
			showLogin();
		}
	};
	
	private static void setUpInitialData(InitialData data) {
		initialData = data;
		if (initialData.inloggadAnvandare==null)	showLogin();
		else showMain();
	}

	public static GWTServiceAsync getService() {
		return GWT.create(GWTService.class);
	}
	private static void showLogin() {
		rootVP.clear();
		rootVP.add(new LoginWidget(callbackLogin, initialData.foretagNamn));

	}

	private static void showMain() {
		rootVP.clear();
		rootVP.add(new Label("Testtt"));
		rootVP.add(new WgMenuBar(windowHandler, initialData.inloggadAnvandare.arrBehorighet, logoutCommand, initialData.foretagNamn));
		rootVP.add(windowHandler);
	}

	private static AsyncCallback<InloggadAnvandare> callbackLogin = new AsyncCallback<InloggadAnvandare>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.showModalMessage("Inloggningen misslyckades. Fel: " + caught.getMessage());
		}

		@Override
		public void onSuccess(InloggadAnvandare result) {
			initialData.inloggadAnvandare = result;
			showMain();
		}
	};

	public static InloggadAnvandare getInloggadAnvandare() { return initialData.inloggadAnvandare; }
	public static InitialData getInitialData() { return initialData; }


}
