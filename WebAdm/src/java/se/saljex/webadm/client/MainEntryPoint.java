/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client;

import se.saljex.webadm.client.common.Util;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.webadm.client.common.GWTService;
import se.saljex.webadm.client.common.GWTServiceAsync;
import se.saljex.webadm.client.common.LoginWidget;
import se.saljex.webadm.client.common.rpcobject.InitialData;
import se.saljex.webadm.client.common.rpcobject.InloggadAnvandare;
import se.saljex.webadm.client.common.window.WindowHandler;
import se.saljex.webadm.client.wermgo.WgMenuBar;

/**
 * Main entry point.
 *
 * @author Ulf
 */
public class MainEntryPoint implements EntryPoint {
    /** 
     * Creates a new instance of MainEntryPoint
     */
	private static final WindowHandler windowHandler = new WindowHandler();
	private static VerticalPanel rootVP = new VerticalPanel();
	private static InitialData initialData = null;

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

    public MainEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
	@Override
    public void onModuleLoad() {

	   RootLayoutPanel.get().add(rootVP);
	   Util.showModalWait();
	   Util.getService().getInitialData(inititalDataCallback);
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
		if ("wermgo".equals(Window.Location.getParameter("modul"))) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Code download failed");
				}

				@Override
				public void onSuccess() {
					rootVP.add(new WgMenuBar(windowHandler, logoutCommand, "Wermgo modul"));
					rootVP.add(windowHandler);			
				}
			});
			
		} else {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Code download failed");
				}

				@Override
				public void onSuccess() {
					rootVP.add(new SxMenuBar(windowHandler, logoutCommand, initialData.foretagNamn));
					rootVP.add(windowHandler);
					windowHandler.addWindow(new WelcomeWidget(), "Välkommen");			
				}
			});
		}
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
	public static ArrayList<String> getArrBehorighet() { return initialData.inloggadAnvandare.arrBehorighet; }
	public static boolean isBehorig(String behorighet) { return (getArrBehorighet()!=null && behorighet!=null && getArrBehorighet().contains(behorighet)); }
}
