/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.FlowPanel;
import se.saljex.webadm.client.rpcobject.Artikel;

/**
 *
 * @author Ulf
 */
public class ArtikelInfoMainWidget extends FlowPanel implements HasFormUpdater<Artikel>{

	ArtikelInfoSheetWidget sheet;
	ArtikelBrowserWithSokWidget kunList = new ArtikelBrowserWithSokWidget(this);

	public ArtikelInfoMainWidget() {
		sheet = new ArtikelInfoSheetWidget(kunList.getBrowserWidget().getPageLoad());
		add(kunList);
		sheet.setWidth("74%");
		kunList.setWidth("24%");
		sheet.setHeight("100%");
		kunList.setHeight("100%");
		sheet.addStyleName("sx-float-left");
		kunList.addStyleName("sx-float-left");
		add(sheet);
		sheet.getForm().addTableRowUpdatedListener(kunList.getBrowserWidget());
	}

	@Override
	public void data2Form(Artikel data) {
		sheet.data2Form(data);
	}


	@Override
	public Artikel form2Data() {
		return form2Data();
	}




}
