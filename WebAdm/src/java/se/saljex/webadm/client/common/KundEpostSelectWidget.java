/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import se.saljex.webadm.client.common.HasSendEpost;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.webadm.client.MainEntryPoint;
import se.saljex.webadm.client.common.rpcobject.Epost;

/**
 *
 * @author Ulf
 */
public class KundEpostSelectWidget  extends PopupPanel{
	HasSendEpost callbackSandEpost;
//	HasRequestCompleted requestCompleted;
	VerticalPanel mainPanel = new VerticalPanel();
	FlexTable listTable = new FlexTable();
	HorizontalPanel btnPanel = new HorizontalPanel();

	TextBox extraAdress = new TextBox();

	Button sandBtn = new Button("Skicka till angivna adresser", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			sendEpost();
		}
	});

	Button avbrytBtn = new Button("Avbryt", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
//			if (requestCompleted!=null) requestCompleted.requestCancelled(null);
			closePanel();
		}
	});

	ArrayList<EpostRad> epostRader= new ArrayList<EpostRad>();

	AsyncCallback<ArrayList<Epost>> callbackGetEpost = new AsyncCallback<ArrayList<Epost>>() {
		@Override
		public void onFailure(Throwable caught) {
			showError(caught.getMessage());
		}

		@Override
		public void onSuccess(ArrayList<Epost> result) {
			showList(result);
		}
	};

	public KundEpostSelectWidget(String Kundnr, HasSendEpost callbackSandEpost) {
		this.callbackSandEpost = callbackSandEpost;
//		this.requestCompleted=requestCompleted;
		MainEntryPoint.getService().getKundEpostLista(Kundnr, callbackGetEpost);
		setUpWindow();

	}

	private void setUpWindow() {
		this.setModal(true);
		this.setHeight("200px");
		mainPanel.add(new Label("Välj E-postmottagare"));
		listTable.getColumnFormatter().setWidth(0, "20px");
		listTable.getColumnFormatter().setWidth(1, "300px");
		listTable.getColumnFormatter().setWidth(2, "150px");

		ScrollPanel scrollPanel = new ScrollPanel(listTable);
		mainPanel.add(scrollPanel);

		extraAdress.setWidth("240px");
		HorizontalPanel epostHp = new HorizontalPanel();
		epostHp.add(new Label("Extra adress:"));
		epostHp.add(extraAdress);
		mainPanel.add(epostHp);

		btnPanel.setSpacing(20);
		btnPanel.add(sandBtn);
		btnPanel.add(avbrytBtn);

		mainPanel.add(btnPanel);
		this.add(mainPanel);

	}
	private void showList(ArrayList<Epost> result) {
		int cn=0;
		EpostRad epostRad;
		CheckBox checkBox;


		for (Epost e : result) {
			checkBox = new CheckBox();
			listTable.getFlexCellFormatter().setColSpan(cn, 1, 2);
			listTable.setWidget(cn, 0, checkBox);
			listTable.setWidget(cn, 1, new Label(e.epost));
			cn++;
			listTable.setWidget(cn, 1, new Label(e.namn));
			listTable.setWidget(cn, 2, new Label(e.typ));

			cn++;
			epostRad = new EpostRad();
			epostRad.epost = e;
			epostRad.checkBox = checkBox;
			epostRader.add(epostRad);
		}

	}

	private void showError(String errorText) {
		listTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		listTable.setWidget(0, 0, new Label("Fel vid kommunikaiton: " + errorText));
	}

	private void closePanel() {
		this.hide();
	}

	private void sendEpost() {
		sandBtn.setEnabled(false);
		avbrytBtn.setEnabled(false);

		String r = extraAdress.getValue();
		for (EpostRad rad : epostRader) {
			if (rad.checkBox.getValue() && rad.epost.epost!= null) {
				if (!rad.epost.epost.isEmpty()) {
					if (!r.isEmpty()) r = r + ",";
					r = r + rad.epost.epost;
				}
			}
		}

		callbackSandEpost.sendEpost(r);
//		if (requestCompleted!=null) requestCompleted.requestCompleted(null);
		closePanel();
	}

	class EpostRad {
		public Epost epost;
		public CheckBox checkBox;
	}
}
