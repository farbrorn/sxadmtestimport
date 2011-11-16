/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.rpcobject.InloggadAnvandare;

/**
 *
 * @author Ulf
 */
public class LoginWidget extends VerticalPanel{

	AsyncCallback<InloggadAnvandare> callback;
	TextBox anvandare = new TextBox();
	PasswordTextBox losen = new PasswordTextBox();
	Button okBtn = new Button("Logga in", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			Util.getService().logIn(anvandare.getValue(), losen.getValue(), callback);
		}
	});

	public LoginWidget(AsyncCallback<InloggadAnvandare> callback) {
		this.callback=callback;

		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, new Label("Användare"));
		grid.setWidget(1, 0, new Label("Användare"));
		anvandare.setFocus(true);
		grid.setWidget(0, 1, anvandare);
		grid.setWidget(1, 1, losen);

		add(new Label("Logga in"));
		add(grid);
		add(okBtn);
	}


}
