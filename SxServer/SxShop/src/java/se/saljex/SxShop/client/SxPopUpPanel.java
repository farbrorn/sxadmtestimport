/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author ulf
 *
 * Visar en popup med ok-knapp
 */
public class SxPopUpPanel extends DialogBox {
	final VerticalPanel vp = new VerticalPanel();
	final HorizontalPanel btnPanel = new HorizontalPanel();
	final Button btnOk = new Button("OK");
	final Button btnAvbryt = new Button("Avbryt");
	OkAvbrytHandler handler;

	public SxPopUpPanel(String titel, Widget innehall, boolean okButton, boolean avbrytButton) {
		this(titel, innehall, okButton, avbrytButton, null);
	}


	public  SxPopUpPanel(String titel, Widget innehall, boolean okButton, boolean avbrytButton, OkAvbrytHandler h) {
		super(false,true);
		if (!okButton && ! avbrytButton) okButton=true;	//Vi måste alltid visa minst en knapp
		if (h==null) {
			handler = new OkAvbrytHandler() {
				public void onOk() {
					hide();
				}

				public void onAvbryt() {
					hide();
				}
			};
		} else {
			this.handler=h;
		}

		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				handler.onOk();
			}
		});
		btnAvbryt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
				handler.onAvbryt();
			}
		});

		setText(titel);
		vp.add(innehall);
		if (okButton) btnPanel.add(btnOk);
		if (avbrytButton) btnPanel.add(btnAvbryt);
		vp.add(btnPanel);
		add(vp);
		center();
		show();
	}

}
