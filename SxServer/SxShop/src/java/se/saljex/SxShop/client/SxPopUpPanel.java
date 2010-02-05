/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
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
	final Button btnOk = new Button("OK");
	public SxPopUpPanel(String titel, Widget innehall) {
		super(false,true);
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		
		setText(titel);
		vp.add(innehall);
		vp.add(btnOk);
		add(vp);
		center();
		show();
	}

}
