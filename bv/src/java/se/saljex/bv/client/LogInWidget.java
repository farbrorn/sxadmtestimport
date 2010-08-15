/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author ulf
 */
public class LogInWidget extends DialogBox{
	VerticalPanel vp = new VerticalPanel();
	Label anvandareLabel = new Label("Användare:");
	Label losenLabel = new Label("Lösen:");

	TextBox anvandareInput = new TextBox();
	PasswordTextBox losenInput = new PasswordTextBox();
	Button loginButton = new Button("Login");
	Button stangButton = new Button("Stäng");
	Label infoLabel  = new Label();

	public LogInWidget() {

		stangButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		loginButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				OverforOrderPanel.getService().logIn(anvandareInput.getValue(), losenInput.getValue(), new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						infoLabel.setText(caught.getMessage());
					}

					public void onSuccess(String result) {
						infoLabel.setText(result);
					}
				});
			}
		});

		vp.add(infoLabel);
		vp.add(anvandareLabel);
		vp.add(anvandareInput);
		vp.add(losenLabel);
		vp.add(losenInput);
		vp.add(loginButton);
		vp.add(stangButton);
		add(vp);
		this.setModal(true);
		this.getElement().getStyle().setProperty("background", "white");


	}

}
