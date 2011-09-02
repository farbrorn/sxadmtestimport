/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class KundInfoSheetWidget extends TabLayoutPanel implements HasFormUpdater<Kund>{

	KundFormWidget kundForm = new KundFormWidget();
	KundresListWidget kundresListWidget = new KundresListWidget(null, null);
	OffertListaWidget offertListWidget = new OffertListaWidget(false);

	public KundInfoSheetWidget() {
		super(2, Unit.EM);

		this.add(new ScrollPanel(kundForm), "Kundinfo");
		this.add(new ScrollPanel(kundresListWidget), "Reskontra");
		this.add(new ScrollPanel(offertListWidget), "Offert");

		this.addSelectionHandler(new SelectionHandler<Integer>() {
					@Override
					public void onSelection(SelectionEvent<Integer> event) {
//						selectTab(event.getSelectedItem());
					}
		})	;

	}

	ArrayList<HasFormUpdater<Kund>> widgetList = new ArrayList<HasFormUpdater<Kund>>();







	public void addHasUpdateWidget(HasFormUpdater<Kund> updateFormWidget, String title) {
		widgetList.add(updateFormWidget);


	}

	@Override
	public void data2Form(Kund data) {
		kundForm.data2Form(data);
		offertListWidget.setSearch("kundnr", data.nummer, "offertnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
		kundresListWidget.getPageLoad().setSearch("kundnr", data.nummer, "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}


	@Override
	public Kund form2Data() {
		return null;
	}


}
