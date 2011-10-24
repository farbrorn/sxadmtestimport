/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class UtlevListWidget extends FlowPanel implements HasData2Form<Kund>{
	Faktura2OrderLimitListWidget o2 = new Faktura2OrderLimitListWidget();
	Utlev1ListWidget o1 = new Utlev1ListWidget(o2);

	FlowPanel o2Widget = new FlowPanel();

	MessagePopupPanel messagePanel = new MessagePopupPanel(true);

	KundEpostSelectWidget epostSelect;

	FlowPanel sokPanel;
	Label sokLabel;
	TextBox sokInput;
	String previousSok=null;


	Button btnInfo = new Button("Info", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			showInfo();
		}
	});
	Button btnStjInfo = new Button("Info *-rad", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			showStjInfo();
		}
	});


	public UtlevListWidget() {
		this(true, false);
	}
	public UtlevListWidget(boolean loadInitialData) {
		this(loadInitialData, false);
	}

	public UtlevListWidget(boolean loadInitialData, boolean showSok) {
		if(loadInitialData) o1.setSearch("kundnr", "0", "ordernr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);

		this.setHeight("100%");
		o1.setHeight("40%");
		o2.getListWidget().setHeight("40%");
		o1.setWidth("65em");
		o2.getListWidget().setWidth("65em");
		o1.addStyleName(Const.Style_BoxedScroll);
		o2.getListWidget().addStyleName(Const.Style_BoxedScroll);

		if (showSok) {
			sokPanel = new FlowPanel();
			sokLabel = new Label("Sök:");
			sokInput = new TextBox();
			Button sokBtn = new Button("Sök", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sok();
				}
			});
			sokInput.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.isDownArrow() || event.isUpArrow()) {
						o1.getCellTable().setFocus(true);
					} else	sok();
				}
			});
			sokPanel.add(sokLabel);
			sokPanel.add(sokInput);
			sokPanel.add(sokBtn);
			sokLabel.addStyleName("sx-float-left");
			add(sokPanel);
		}


		o2Widget.add(o2.getListWidget());
		o2Widget.add(btnInfo);
		o2Widget.add(btnStjInfo);

		add(o1);
		add(o2Widget);

	}

	private void sok() {
		if (Util.isEmpty(sokInput.getText())) o1.setSearch("kundnr", "0", "faktnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);
		else o1.getPageLoad().setSearch("faktnr", sokInput.getText(), "faktnr", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_DESCANDING);
	}

	@Override
	public void data2Form(Kund data) {
		setSearch("kundnr", data.nummer, "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}


/*	private void showEpostSelect() {

		epostSelect = new KundEpostSelectWidget(o1.getSelectedObject().kundnr, new SendOffertEpostHandler("00", o1.getSelectedObject().offertnr, messagePanel), new HasRequestCompleted() {

			@Override
			public void requestCompleted(String info) {		}

			@Override
			public void requestCancelled(String info) {				}
		});

		epostSelect.center();
		epostSelect.show();
	}
*/
	private void showInfo() {
		MessagePopupPanel m = new MessagePopupPanel(true);
		m.setHeight("70%");
		OrderInfoWidget oi = new OrderInfoWidget();
		oi.showUtlev(o1.getSelectedObject());
		m.showWidget(oi.getWidget());
	}
	private void showStjInfo() {
		MessagePopupPanel m = new MessagePopupPanel(true);
		m.setHeight("70%");
		StjarnradInfoWidget stj = new StjarnradInfoWidget();
		stj.show(o2.faktura2ListWidget.getSelectedObject().stjid);
		m.showWidget(stj.getWidget());
	}

	public void setSearch(String sokField, String sokString, String sortField, int sokTyp, int sortOrder) {
		o2.getListWidget().getPageLoad().getBufferList().clear();	//Rensar innehåll så det inte visas felaktig vid uppdatering av O1 utan sökresultat
		o1.getPageLoad().setSearch(sokField, sokString, sortField, sokTyp, sortOrder);
	}


}
