/*
 * MainEntryPoint.java
 *
 * Created on den 2 december 2009, 18:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import com.google.gwt.core.client.GWT;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import java.util.Date;
import se.saljex.SxShop.client.rpcobject.Anvandare;


/**
 *
 * @author ulf
 */
public class MainEntryPoint implements EntryPoint, ResizeHandler {
	
	/** Creates a new instance of MainEntryPoint */
	public MainEntryPoint() {
	}
	
	/**
	 * The entry point method, called automatically by loading a module
	 * that declares an implementing class as an entry-point
	 */
//		VerticalPanel vp = new VerticalPanel();
		Anchor katalogAnchor = new Anchor("Handla");
		Anchor kontoAnchor = new Anchor("Mitt konto");
		KontoPanel kontoPanel = null;

		DockPanel dp = new DockPanel();
		final GlobalData globalData = new GlobalData();
		HorizontalPanel headerPanel = new HorizontalPanel();
		HorizontalPanel contentPanel = new HorizontalPanel();
		ArtikelPanel artikelPanel;

		TextBox anvandare = new TextBox();
		PasswordTextBox losen = new PasswordTextBox();
		CheckBox stayLoggedIn = new CheckBox("Kom ihåg mig");
		Grid anvandareGrid  = new Grid(2, 1);
		HorizontalPanel logInPanel = new HorizontalPanel();
		Label anvandarStrang = new Label();
		Anchor logInAnchor = new Anchor("Logga in");
		Anchor logOutAnchor = new Anchor("Logga ut");
		Anchor glomtLosenAnchor = new Anchor("Glömt lösen");
		private final static String INLOGGADSOM=new String("Inloggad som ");
		private final static String COOKIEAUTOINLOGANVANDARE="autokundloginnamn";
		private final static String COOKIEAUTOINLOGID="autokundloginid";
		//The timeout before a cookie expires, in milliseconds. Det verkar inte gå att sätta längre giltighetstid än ca 20 dagar.
		private static final int COOKIE_TIMEOUT = 1000 * 60 * 60 * 24 * 20;

	final AsyncCallback callbackGetAnvandare = new AsyncCallback() {
		public void onSuccess(Object result) {
			setUpAnvandareWidget((Anvandare)result);
			losen.setText("");
			anvandare.setText("");
		}

		public void onFailure(Throwable caught) {
			anvandarStrang.setText(caught.getMessage() != null ? caught.getMessage() : "Okänt fel: " + caught.toString());
			anvandareGrid.setWidget(1, 0, null);
			contentPanel.clear();
			contentPanel.add(artikelPanel);
			kontoAnchor.setVisible(false);
		}
	};


	private void setUpAnvandareWidget(Anvandare anvandare) {
			globalData.anvandare=anvandare;
			if (globalData.anvandare.gastlogin) {
				anvandarStrang.setText(INLOGGADSOM+"gäst");
				anvandareGrid.setWidget(1, 0, logInPanel);
				Cookies.removeCookie(COOKIEAUTOINLOGANVANDARE);
				Cookies.removeCookie(COOKIEAUTOINLOGID);
				contentPanel.clear();
				contentPanel.add(artikelPanel);
				kontoAnchor.setVisible(false);
			} else {
				anvandarStrang.setText(INLOGGADSOM+globalData.anvandare.kontaktnamn+", "+globalData.anvandare.kundnamn);
				anvandareGrid.setWidget(1, 0, logOutAnchor);
				Date cookieExpire = new Date((new Date()).getTime() + COOKIE_TIMEOUT);
				Cookies.setCookie(COOKIEAUTOINLOGANVANDARE, globalData.anvandare.loginnamn, cookieExpire );
				Cookies.setCookie(COOKIEAUTOINLOGID, globalData.anvandare.autoLoginId, cookieExpire);
				kontoAnchor.setVisible(true);
			}
		
	}

	public void onModuleLoad() {
		String autoInlogLoginNamn = Cookies.getCookie(COOKIEAUTOINLOGANVANDARE);
		String autoInlogID = Cookies.getCookie(COOKIEAUTOINLOGID);
		anvandare.setPixelSize(80, 18);
		losen.setPixelSize(80, 18);

		globalData.service = getService();
		globalData.service.autoLogin(autoInlogLoginNamn, autoInlogID, callbackGetAnvandare);
		artikelPanel = new ArtikelPanel(globalData);
		Window.enableScrolling(false);
		Window.setMargin("0px");


		Label anvandareLabel = new Label("Användare: ");
		Label losenLabel = new Label("Lösen:");

		kontoAnchor.setVisible(false);

		logInPanel.add(anvandareLabel);
		logInPanel.add(anvandare);
		logInPanel.add(losenLabel);
		logInPanel.add(losen);
		logInPanel.add(stayLoggedIn);
		logInPanel.add(logInAnchor);
		logInPanel.add(glomtLosenAnchor);
		logInPanel.setSpacing(8);
		headerPanel.setSize("100%", "40px");
		logInAnchor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
						globalData.service.logIn(anvandare.getText(), losen.getText(), stayLoggedIn.getValue(), callbackGetAnvandare);
			}});
		logOutAnchor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
						globalData.service.logOut(callbackGetAnvandare);
			}});
		glomtLosenAnchor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
						GlomtLosen glomtLosen = new GlomtLosen(globalData);
						glomtLosen.show();
			}});


		anvandareGrid.setWidget(0, 0, anvandarStrang);
		headerPanel.add(anvandareGrid);
		katalogAnchor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				contentPanel.clear();
				contentPanel.add(artikelPanel);
				onWindowResized();
			}
		});
		kontoAnchor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				contentPanel.clear();
				if (kontoPanel==null) kontoPanel = new KontoPanel(globalData);
				contentPanel.add(kontoPanel);
				onWindowResized();
			}
		});

		headerPanel.add(katalogAnchor);
		headerPanel.add(kontoAnchor);

		dp.setBorderWidth(1);
		dp.setSize("100%", "100%");
		dp.add(headerPanel,DockPanel.NORTH);
		dp.setCellHeight(headerPanel, "40px");

		contentPanel.add(artikelPanel);
		artikelPanel.setSize("100%", "100%");
		dp.add(contentPanel,DockPanel.CENTER);

		Window.addResizeHandler(this);
		// Call the window resized handler to get the initial sizes setup. Doing
		// this in a deferred command causes it to occur after all widgets' sizes
		// have been computed by the browser.
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				onWindowResized(Window.getClientWidth(), Window.getClientHeight());
				}
		});

//		onWindowResized(Window.getClientWidth(), Window.getClientHeight());


	
		RootPanel.get().add(dp);

		//RootPanel.get().add(label);




	}




public void onResize(ResizeEvent event) {
    onWindowResized(event.getWidth(), event.getHeight());
  }

  public void onWindowResized(int windowWidth, int windowHeight) {
	  try {
		((SxResizePanel)contentPanel.getWidget(0)).resize(windowWidth, windowHeight);
	  } catch (ClassCastException e) {}
	  //artikelPanel.resize(windowWidth, windowHeight );
	  //if (kontoPanel!=null) kontoPanel.resize(windowWidth, windowHeight );
  }
  public void onWindowResized() {
	  onWindowResized(Window.getClientWidth(), Window.getClientHeight());
  }


	public static SxShopRPCAsync getService(){
		// Create the client proxy. Note that although you are creating the
		// service interface proper, you cast the result to the asynchronous
		// version of
		// the interface. The cast is always safe because the generated proxy
		// implements the asynchronous interface automatically.
		SxShopRPCAsync service = (SxShopRPCAsync) GWT.create(SxShopRPC.class);
		// Specify the URL at which our service implementation is running.
		// Note that the target URL must reside on the same domain and port from
		// which the host page was served.
		//
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "sxshoprpc";
		endpoint.setServiceEntryPoint(moduleRelativeURL);
		return service;
	}



}
