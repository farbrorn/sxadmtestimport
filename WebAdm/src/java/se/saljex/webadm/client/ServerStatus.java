/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.GWTServiceAsync;
import se.saljex.webadm.client.common.GWTService;
import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import se.saljex.sxserver.tables.TableArtikel;

/**
 * Example class using the GWTService service.
 *
 * @author Ulf
 */
public class ServerStatus extends VerticalPanel {
      final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                addResponse(result);
            }

            public void onFailure(Throwable caught) {
                addResponse("Exception: " + caught.toString() + " " + caught.getMessage());
            }
        };

	private final VerticalPanel responsePanel = new VerticalPanel();
    private final Button btnUpdateArtikel = new Button("Uppdatera Webartiklar", new ClickHandler() {
		@Override		public void onClick(ClickEvent event) { addResponse("Startar UpdateWebArtikel");        getService().serverUpdateWebArtikel(callback);	}	});
    private final Button btnUpdateArtikelTrad = new Button("Uppdatera Webartikelträd", new ClickHandler() {
		@Override		public void onClick(ClickEvent event) { addResponse("Startar UpdateWebArtikelTräd");        getService().serverUpdateWebArtikelTrad(callback);	}	});
    private final Button btnUpdateLagersaldo = new Button("Uppdatera Lagersaldo", new ClickHandler() {
		@Override		public void onClick(ClickEvent event) { addResponse("Startar UpdateLagersaldon");        getService().serverUpdateLagersaldon(callback);	}	});
    private final Button btnGetStatus = new Button("Status", new ClickHandler() {
		@Override		public void onClick(ClickEvent event) {  addResponse("Startar GetStatus");       getService().serverGetStatus(callback);	}	});
    
    public ServerStatus() {
        add(btnUpdateArtikel);
		add(btnUpdateArtikelTrad);
		add(btnUpdateLagersaldo);
		add(btnGetStatus);
        add(responsePanel);
	}

	private void addResponse(String s) {
		responsePanel.add(new HTML(s,true));
	}

    public static GWTServiceAsync getService() {
        return GWT.create(GWTService.class);
    }


}
