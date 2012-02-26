/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author Ulf
 */
public class ModalMessageBox extends DialogBox{

	HorizontalPanel hp = new HorizontalPanel();
	Button okBtn = new Button("OK");
	Label label = new Label();

	public ModalMessageBox() {
		super(false, true);

		okBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		hp.add(label);
		hp.add(okBtn);
		this.setWidget(hp);
	}

	public void show(String text, boolean showOkButton) {
		label.setText(text);
		okBtn.setVisible(showOkButton);
		this.center();
		this.show();
	}
	public void show(String text) {
		show(text,false);
	}



}
