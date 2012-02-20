/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

/**
 *
 * @author Ulf
 */
public abstract class SendEpostButton extends Button {

	protected SendEpostInterface sendEpostInerface;
	protected String anvandare;

	public SendEpostButton(String anvandare, SendEpostInterface sendEpostInerface) {
		super("E-post");
		this.sendEpostInerface = sendEpostInerface;
		this.anvandare = anvandare;
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				doClick();
			}
		});
	}

	protected abstract void doClick();

	protected void showEpostSelect(SendEpostHandler sendEpostHandler) {
		KundEpostSelectWidget epostSelect = new KundEpostSelectWidget(sendEpostInerface.getKundnr(), sendEpostHandler);
		epostSelect.center();
		epostSelect.show();
	}

}
