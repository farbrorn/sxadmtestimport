/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client;

import se.saljex.webadm.client.window.WindowHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.User;

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

    public MainEntryPoint() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
		ArrayList<String> behorighetList = new ArrayList();
		behorighetList.add("FaktAdmin");
		behorighetList.add("FaktLogin");
		User user = new User();
		user.setUser("UB", "Ulf Berg", "ulf@saljex.se", behorighetList);

		VerticalPanel rootVP = new VerticalPanel();


		rootVP.add(new SxMenuBar(windowHandler, user));

       rootVP.add(windowHandler);

	   RootLayoutPanel.get().add(rootVP);


    }
	public static GWTServiceAsync getService() {
		return GWT.create(GWTService.class);
	}

}
