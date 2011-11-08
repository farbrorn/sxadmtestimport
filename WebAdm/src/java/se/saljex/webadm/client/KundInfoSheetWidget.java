/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundInfoSheetWidget extends InfoSheetWidget<Kund>{
		KundFormWidget kundForm;


	public KundInfoSheetWidget() {
		super();
	}

	public KundInfoSheetWidget(PageLoad<Kund> pageLoad) {
		super(pageLoad);
	}

	public KundFormWidget getForm() { return kundForm; }


	@Override
	protected void setup() {
		KundresListWidget kundresListWidget = new KundresListWidget(null, null);
		OffertListaWidget offertListWidget = new OffertListaWidget(false);
		OrderListaWidget orderListWidget = new OrderListaWidget(false);
		KundStatistikWidget kundStatistikWidget = new KundStatistikWidget();
		FakturaListWidget fakturaListWidget = new FakturaListWidget(false);
		UtlevListWidget utlevListWidget = new UtlevListWidget(false);
		kundForm = new KundFormWidget(pageLoad);
		addData2FormWidget(kundForm, kundForm, "Kundinfo");
		addData2FormWidget(kundresListWidget, kundresListWidget, "Reskontra");
		addData2FormWidget(orderListWidget, orderListWidget, "Order");
		addData2FormWidget(offertListWidget, offertListWidget, "Offerter");
		addData2FormWidget(fakturaListWidget, fakturaListWidget, "Fakturor");
		addData2FormWidget(utlevListWidget, utlevListWidget, "Utleveranser");
		addData2FormWidget(kundStatistikWidget, kundStatistikWidget, "Statistik");
	}


}
