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
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.Anvandare;
import se.saljex.SxShop.client.rpcobject.VaruKorgRad;


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
		DockPanel dp = new DockPanel();
		HorizontalPanel headerPanel = new HorizontalPanel();
		HorizontalPanel contentPanel = new HorizontalPanel();
		ArtikelPanel artikelPanel = new ArtikelPanel(getService());

		Anvandare anvandare=null;
		Label anvandarStrang = new Label();
		private final static String INLOGGADSOM=new String("Inloggad som ");

	final AsyncCallback callbackGetAnvandare = new AsyncCallback() {
		public void onSuccess(Object result) {
			anvandare=(Anvandare)result;
			if (anvandare.gastlogin) {
				anvandarStrang.setText(INLOGGADSOM+"gäst");
			} else {
				anvandarStrang.setText(INLOGGADSOM+anvandare.kontaktnamn+", "+anvandare.kundnamn);
			}
		}

		public void onFailure(Throwable caught) {
			anvandarStrang.setText("Fel vi kommunikation. " + caught.toString());
		}
	};


	public void onModuleLoad() {
		getService().getInloggadAnvandare(callbackGetAnvandare);
		Window.enableScrolling(false);
		Window.setMargin("0px");


		
		headerPanel.setSize("100%", "40px");
		headerPanel.add(anvandarStrang);

//		mainPanel.add(new Label("Teststräng"));
//		mainPanel.add(new Label("tespen-"));
//		mainPanel.setSize("100%", "100%");


		dp.setBorderWidth(1);
		dp.setSize("100%", "100%");
		dp.add(headerPanel,DockPanel.NORTH);
		//headerPanel.setHeight("50px");
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

		onWindowResized(Window.getClientWidth(), Window.getClientHeight());


	
		RootPanel.get().add(dp);

		//RootPanel.get().add(label);




	}




public void onResize(ResizeEvent event) {
    onWindowResized(event.getWidth(), event.getHeight());
  }

  public void onWindowResized(int windowWidth, int windowHeight) {
/*    int scrollWidth = windowWidth - contentPanel.getAbsoluteLeft() - 9;
    if (scrollWidth < 1) {
      scrollWidth = 1;
    }

    int scrollHeight = windowHeight - contentPanel.getAbsoluteTop() - 9;
    if (scrollHeight < 1) {
      scrollHeight = 1;
    }

    contentPanel.setPixelSize(scrollWidth, scrollHeight);
*/
	  artikelPanel.resize(windowWidth, windowHeight);

//    at.adjustSize(width, height);
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
