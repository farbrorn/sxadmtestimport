/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class GlomtLosen extends DialogBox {
		final GlobalData globalData;
		final TextBox anvandare;
		VerticalPanel vp = new VerticalPanel();
		Button btnSkicka = new Button("Skicka", new ClickHandler() {
			public void onClick(ClickEvent event) {
				globalData.service.skickaInloggningsuppgifter(anvandare.getText(), callbackLosen);
			}
			});
		Button btnAvbryt = new Button("Avbryt", new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
			});

	public GlomtLosen(final GlobalData globalData) {
		super(false,true);
		this.globalData = globalData;
		setText("Glömt lösenord");
		vp.add(new HTML("Ange användarnamn eller e-postadress och klicka på Skicka<br>så skickas dina inloggningsuppgifter till din e-postadress.<br>"));
//		vp.add(new Label("Ange användarnamn eller e-postadress och klicka på Skicka"));
//		vp.add(new Label("så skickas dina inloggningsuppgifter till din e-postadress."));
		HorizontalPanel hpinput = new HorizontalPanel();
		anvandare = new TextBox();
		hpinput.add(new Label("Användarnamn eller e-postadress:"));
		hpinput.add(anvandare);
		vp.add(hpinput);
		HorizontalPanel hpknapp = new HorizontalPanel();
		hpknapp.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		hpknapp.add(btnSkicka);
		hpknapp.add(btnAvbryt);
		vp.add(hpknapp);
		add(vp);
		center();

	}

	final AsyncCallback callbackLosen = new AsyncCallback() {
		public void onSuccess(Object result) {
			vp.clear();
			vp.add(new Label("Vi har skickat dina användaruppgifter."));
			btnAvbryt.setText("OK");
			vp.add(btnAvbryt);
		}

		public void onFailure(Throwable caught) {
			vp.clear();
			vp.add(new Label("Vi kunde inte hitta dina inloggningsuppgifter."));
			vp.add(new Label("Vänligen försök igen, eller kontakta oss."));
			btnAvbryt.setText("OK");
			vp.add(btnAvbryt);
		}
	};

}
