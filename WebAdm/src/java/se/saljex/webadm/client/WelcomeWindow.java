/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.window.WindowHandler;
import se.saljex.webadm.client.common.window.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.common.DoubleTextBox;

/**
 *
 * @author Ulf
 */
public class WelcomeWindow extends Window {

	VerticalPanel vp = new VerticalPanel();
	HTML page = new HTML("<h1>Välkommen till Säljex</h1>Detta är en liten testsida.<p/>Läs mer på <i>www.saljex.se</i>");
	Button closBtn = new Button("Stäng detta fönster");
	Label vlabel = new Label();
	DoubleTextBox nt = new DoubleTextBox();

	public WelcomeWindow(WindowHandler windowHandler, String title) {
		super(windowHandler, title);
		windowWidget = vp;
		initWindow();

	}

	private void initWindow() {
		vp.add(page);
		vp.add(closBtn);
		closBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//closeWindow();
			   vlabel.setText(nt.getValue().toString());
			}
		});
		nt.setValue(123.1);
		vp.add(nt);
		vp.add(vlabel);

	}

}
