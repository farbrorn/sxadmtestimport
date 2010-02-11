/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.SxShop.client.rpcobject.AnvandareUppgifter;

/**
 *
 * @author ulf
 */
public class KontoAnvandareUppgifterWidget extends SxWidget{
	private FlexTable ft = new FlexTable();
	TextBox tbNamn = new TextBox();
	TextBox tbTel = new TextBox();
	TextBox tbMobil = new TextBox();
	TextBox tbFax = new TextBox();
	TextBox tbEpost = new TextBox();
	TextBox tbAdr1 = new TextBox();
	TextBox tbAdr2 = new TextBox();
	TextBox tbAdr3 = new TextBox();
	CheckBox cbEkonomiFlagga = new CheckBox("Jag tar emot ekonomisk information som t.ex. fakturor.");
	CheckBox cbInfoFlagga = new CheckBox("Jag tar emot allmän information");
	VerticalPanel infoVp = new VerticalPanel();
	Button btnSpara = new Button("Spara");
	Anchor aAndraLosen = new Anchor("Ändra lösenord");


	public KontoAnvandareUppgifterWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);
		ft.setWidth("100%");
		infoVp.add(new Label("Observera att uppgifterna är dina personliga kontaktuppgifter."));
		infoVp.add(new Label("Faktura och leveransadress kan inte ändras här."));
		add(infoVp);
		add(ft);
		add(btnSpara);
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.addStyleName(globalData.STYLE_TABLE_INFO);
		btnSpara.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sparaClick();
			}
		});
		aAndraLosen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				andraLosen();
			}
		});
		tbNamn.addStyleName(globalData.STYLE_INPUTDATA);
		tbTel.addStyleName(globalData.STYLE_INPUTDATA);
		tbMobil.addStyleName(globalData.STYLE_INPUTDATA);
		tbFax.addStyleName(globalData.STYLE_INPUTDATA);
		tbEpost.addStyleName(globalData.STYLE_INPUTDATA);
		tbAdr1.addStyleName(globalData.STYLE_INPUTDATA);
		tbAdr2.addStyleName(globalData.STYLE_INPUTDATA);
		tbAdr3.addStyleName(globalData.STYLE_INPUTDATA);
		fillFt();
	}

	private void fillFt() {
		currentRow=1;
		ft.clear();
		globalData.service.getAnvandareUppgifter(callbackLoadPage);
	}

	@Override
	protected void pageLoaded(Object result) {
		AnvandareUppgifter a=(AnvandareUppgifter)result;
		if (a==null) {
			setError("Det finns inga data");
			btnSpara.setVisible(false);
		} else {
			clearError();
			btnSpara.setVisible(true);
			tbNamn.setValue(a.kontaktNamn);
			tbTel.setValue(a.kontaktTel);
			tbMobil.setValue(a.kontaktMobil);
			tbFax.setValue(a.kontaktFax);
			tbEpost.setValue(a.kontaktEpost);
			tbAdr1.setValue(a.kontaktAdr1);
			tbAdr2.setValue(a.kontaktAdr2);
			tbAdr3.setValue(a.kontaktAdr3);
			cbEkonomiFlagga.setValue(a.kontaktEkonomiFlagga);
			cbInfoFlagga.setValue(a.kontaktInfoFlagga);
			ft.getCellFormatter().addStyleName(0, 0, globalData.STYLE_PROMPT);
			ft.getCellFormatter().addStyleName(1, 0, globalData.STYLE_PROMPT);
			ft.getCellFormatter().addStyleName(2, 0, globalData.STYLE_PROMPT);
			ft.getCellFormatter().addStyleName(3, 0, globalData.STYLE_PROMPT);
			ft.getCellFormatter().addStyleName(4, 0, globalData.STYLE_PROMPT);
			ft.getCellFormatter().addStyleName(5, 0, globalData.STYLE_PROMPT);
			ft.setText(0, 0, "Namn:");
			ft.setText(1, 0, "Tel:");
			ft.setText(2, 0, "Mobil:");
			ft.setText(3, 0, "Fax:");
			ft.setText(4, 0, "Epost:");
			ft.setText(5, 0, "Adress:");
			ft.setWidget(0, 1, tbNamn);
			ft.setWidget(1, 1, tbTel);
			ft.setWidget(2, 1, tbMobil);
			ft.setWidget(3, 1, tbFax);
			ft.setWidget(4, 1, tbEpost);
			ft.setWidget(5, 1, tbAdr1);
			ft.setWidget(6, 1, tbAdr2);
			ft.setWidget(7, 1, tbAdr3);
			ft.setWidget(8, 1, cbEkonomiFlagga);
			ft.setWidget(9, 1, cbInfoFlagga);
			ft.setWidget(10, 1, aAndraLosen);
		}

	}

	private void sparaClick() {
		AnvandareUppgifter a = new AnvandareUppgifter();
		a.kontaktNamn = tbNamn.getValue();
		a.kontaktTel = tbTel.getValue();
		a.kontaktMobil = tbMobil.getValue();
		a.kontaktFax = tbFax.getValue();
		a.kontaktEpost = tbEpost.getValue();
		a.kontaktAdr1 = tbAdr1.getValue();
		a.kontaktAdr2 = tbAdr2.getValue();
		a.kontaktAdr3 = tbAdr3.getValue();
		a.kontaktEkonomiFlagga = cbEkonomiFlagga.getValue();
		a.kontaktInfoFlagga = cbInfoFlagga.getValue();
		globalData.service.updateAnvandareUppgifter(a, callbackUpdate);
	}

	AsyncCallback callbackUpdate = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			setError(caught.getMessage());
		}

		public void onSuccess(Object result) {
			new SxPopUpPanel("Uppgifterna sparade", new Label(""), true, false);
		}
	};

	private void andraLosen() {
		final DialogBox andraLosen = new DialogBox(false, true);
		final FlexTable lft = new FlexTable();
		final PasswordTextBox nyttLosen = new PasswordTextBox();
		final PasswordTextBox upprepaLosen = new PasswordTextBox();
		final PasswordTextBox gammaltLosen = new PasswordTextBox();
		final Button btnSparaLosen = new Button("OK");
		final Button btnAvbrytSparaLosen = new Button("Avbryt");
		final Label errLabel = new Label();
		
		errLabel.addStyleName(globalData.STYLE_ERROR);
		andraLosen.setWidget(lft);
		andraLosen.setText("Ändra lösenord");
		lft.setWidget(0, 0, errLabel);
		lft.getFlexCellFormatter().setColSpan(0, 0, 2);
		lft.setText(1, 0, "Nytt lösenord");
		lft.setText(2, 0, "Upprepa lösenord");
		lft.setText(3, 0, "Gammalt lösenord");
		lft.setWidget(1, 1, nyttLosen);
		lft.setWidget(2, 1, upprepaLosen);
		lft.setWidget(3, 1, gammaltLosen);
		lft.setWidget(4, 0, btnSparaLosen);
		lft.setWidget(4, 1, btnAvbrytSparaLosen);
		lft.getCellFormatter().addStyleName(0, 0, globalData.STYLE_PROMPT);
		lft.getCellFormatter().addStyleName(1, 0, globalData.STYLE_PROMPT);
		lft.getCellFormatter().addStyleName(2, 0, globalData.STYLE_PROMPT);
		btnSparaLosen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				globalData.service.updateLosen(nyttLosen.getValue(), upprepaLosen.getValue(), gammaltLosen.getValue(), new AsyncCallback() {
					public void onFailure(Throwable caught) {
						errLabel.setText("Fel: " + caught.getMessage());
					}

					public void onSuccess(Object result) {
						errLabel.setText("");
						new SxPopUpPanel("Lösenordet ändrat", new Label(""), true, false);
						andraLosen.hide();
					}
				});
			};
		});

		btnAvbrytSparaLosen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				andraLosen.hide();
			}
		});

		andraLosen.center();
		andraLosen.show();

	}

}
