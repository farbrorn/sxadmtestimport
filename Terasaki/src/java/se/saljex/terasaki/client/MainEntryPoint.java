/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.terasaki.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Main entry point.
 *
 * @author Ulf
 */
public class MainEntryPoint implements EntryPoint {

	Panel holderPanel = new VerticalPanel();
	HorizontalPanel topPanel = new HorizontalPanel();
	Panel mainPanel = new VerticalPanel();

	Panel artikelPanel;
	Panel kundPanel;

	Anchor mArtikel = new Anchor("Products");
	Anchor mKund = new Anchor("Customers");
	Anchor mLogout = new Anchor("Logout");

	TextBox user = new TextBox();
	PasswordTextBox password = new PasswordTextBox();

    /** 
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
		getService().isLoggedIn(callbackIsLoggedIn);

    }

	private void showLogin() {
		showLogin(null);


	}
	private void showLogin(String errorText) {
		Button bLogin = new Button("Login");
		bLogin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});
		RootPanel.get().clear();
		VerticalPanel p = new VerticalPanel();
		if (errorText!=null) {
			Label errLabel =  new Label(errorText);
			p.add(errLabel);
		}
		p.add(new Label("Terasaki"));
		p.add(new Label("Please sign in"));
		p.add(new Label("Username:"));
		p.add(user);
		p.add(new Label("Password:"));
		p.add(password);
		p.add(bLogin);
		RootPanel.get().add(p);

	}

	private void doLogin() {
		getService().logIn(user.getValue(), password.getValue(), callbackLogIn);
	}

	private void showMain() {
		RootPanel.get().clear();
		artikelPanel = new ArtikelPanel();
		holderPanel.add(topPanel);
		holderPanel.add(mainPanel);

		mainPanel.clear();
		mainPanel.add(artikelPanel);

		Label terasakiLabel = new Label("Terasaki");
		terasakiLabel.addStyleName("sx-logo");
		topPanel.add(terasakiLabel);

		topPanel.add(mArtikel);
		topPanel.add(mKund);
		topPanel.add(mLogout);
		topPanel.setSpacing(15);

		mArtikel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
				artikelPanel = new ArtikelPanel();
				mainPanel.add(artikelPanel);
			}
		});
		mKund.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainPanel.clear();
				kundPanel = new KundPanel();
				mainPanel.add(kundPanel);
			}
		});
		mLogout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getService().logOut(callbackLogOut);
			}
		});

        RootPanel.get().add(holderPanel);

	}

	final AsyncCallback<Boolean> callbackIsLoggedIn = new AsyncCallback<Boolean>() {
		public void onSuccess(Boolean result) {
			if (result) showMain(); else showLogin();
		}

		public void onFailure(Throwable caught) {
			showLogin("Server error");
		}
	};

	final AsyncCallback<Boolean> callbackLogIn = new AsyncCallback<Boolean>() {
		public void onSuccess(Boolean result) {
			if (result) showMain(); else showLogin("Incorrect login");
		}

		public void onFailure(Throwable caught) {
			showLogin("Incorrect login.");
		}
	};

	final AsyncCallback callbackLogOut = new AsyncCallback() {
		public void onSuccess(Object result) {
			showLogin("You are logged out");
		}

		public void onFailure(Throwable caught) {
			showLogin("Server error");
		}
	};

	public static GWTServiceAsync getService() {
		// Create the client proxy. Note that although you are creating the
		// service interface proper, you cast the result to the asynchronous
		// version of the interface. The cast is always safe because the
		// generated proxy implements the asynchronous interface automatically.

		return GWT.create(GWTService.class);
	}

}
