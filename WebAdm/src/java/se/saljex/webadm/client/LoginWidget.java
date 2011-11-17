/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.InloggadAnvandare;

/**
 *
 * @author Ulf
 */
public class LoginWidget extends VerticalPanel{

	AsyncCallback<InloggadAnvandare> callback;
	TextBox anvandare = new TextBox();
	PasswordTextBox losen = new PasswordTextBox();
	AsyncCallback<InloggadAnvandare> thisCallback = new AsyncCallback<InloggadAnvandare>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.hideModalWait();
			callback.onFailure(caught);
		}

		@Override
		public void onSuccess(InloggadAnvandare result) {
			Util.hideModalWait();
			callback.onSuccess(result);
		}
	};

	Button okBtn = new Button("Logga in", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			Util.showModalWait();
			Util.getService().logIn(anvandare.getValue(), losen.getValue(), thisCallback);
		}
	});

	FormNavigator nav = new FormNavigator();

	public LoginWidget(AsyncCallback<InloggadAnvandare> callback, String foretagNamn) {
		this.callback=callback;


		nav.add(anvandare);
		nav.add(losen);
		nav.add(okBtn);

		Grid grid = new Grid(2, 2);
		grid.setWidget(0, 0, new Label("Användare"));
		grid.setWidget(1, 0, new Label("Användare"));
		grid.setWidget(0, 1, anvandare);
		grid.setWidget(1, 1, losen);

		Label namn = new Label("Välkommen  till " + foretagNamn);
		namn.addStyleName(Const.Style_Bolder);
		namn.addStyleName(Const.Style_Blue);
		namn.addStyleName(Const.Style_TextLarge);
		add(namn);
		Label header = new Label("Logga in");
		header.addStyleName(Const.Style_Bold);
		add(header);
		add(grid);
		add(okBtn);

		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute () {
				anvandare.setFocus(true);
			}
		});

		
	}


}
