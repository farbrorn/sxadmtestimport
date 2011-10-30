/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.rpcobject.Artikel;

/**
 *
 * @author Ulf
 */
public class ArtikelInfoSheetWidget extends InfoSheetWidget<Artikel>{


	public ArtikelInfoSheetWidget()  {
		super();
	}
	public ArtikelInfoSheetWidget(PageLoad<Artikel> pageLoad)  {
		super(pageLoad);
	}
	
	protected void setup() {
		ArtikelFormWidget formWidget;
	//	OffertListaWidget offertListWidget = new OffertListaWidget(false);
	//	OrderListaWidget orderListWidget = new OrderListaWidget(false);
		formWidget = new ArtikelFormWidget(pageLoad);
		addData2FormWidget(formWidget, formWidget, "Artikelinfo");
//		addData2FormWidget(orderListWidget, orderListWidget, "Order");
//		addData2FormWidget(offertListWidget, offertListWidget, "Offerter");

	}

}
