/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.Offert1;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class OffertListaWidget extends FlowPanel implements HasData2Form<Kund>{
	Offert2ListWidget o2 = new Offert2ListWidget();
	Offert1ListWidget o1 = new Offert1ListWidget(o2);

	FlowPanel o2Widget = new FlowPanel();

	MessagePopupPanel messagePanel = new MessagePopupPanel(true);

	KundEpostSelectWidget epostSelect;


	Button epostBtn = new Button("Sänd som e-post", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			showEpostSelect();
		}
	});


	public OffertListaWidget() {
		this(true);
	}

	public OffertListaWidget(boolean loadInitialData) {
		if(loadInitialData) o1.setSearch("kundnr", "0", "offertnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);

		this.setHeight("100%");
		o1.setHeight("45%");
		o2.setHeight("45%");
		o1.setWidth("65em");
		o2.setWidth("65em");
		o1.addStyleName(Const.Style_BoxedScroll);
		o2.addStyleName(Const.Style_BoxedScroll);

		o2Widget.add(o2);
		o2Widget.add(epostBtn);

		add(o1);
		add(o2Widget);

	}

	@Override
	public void data2Form(Kund data) {
		setSearch("kundnr", data.nummer, "offertnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}


	private void showEpostSelect() {

		epostSelect = new KundEpostSelectWidget(o1.getSelectedObject().kundnr, new SendOffertEpostHandler("00", o1.getSelectedObject().offertnr, messagePanel), new HasRequestCompleted() {

			@Override
			public void requestCompleted(String info) {		}

			@Override
			public void requestCancelled(String info) {				}
		});

		epostSelect.center();
		epostSelect.show();
	}


	public void setSearch(String sokField, String sokString, String sortField, int sokTyp, int sortOrder) {
		o2.getPageLoad().getBufferList().clear();	//Rensar innehåll så det inte visas felaktig vid uppdatering av O1 utan sökresultat
		o1.getPageLoad().setSearch(sokField, sokString, sortField, sokTyp, sortOrder);
	}


}
