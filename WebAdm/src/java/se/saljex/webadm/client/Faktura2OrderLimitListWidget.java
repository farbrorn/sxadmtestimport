/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.rpcobject.Faktura2;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.Utlev1;

/**
 *
 * @author Ulf
 */
public class Faktura2OrderLimitListWidget  implements  HasData2Form<Utlev1> {

	Faktura2ListWidget faktura2ListWidget = new Faktura2ListWidget();
	public Faktura2OrderLimitListWidget() {
		
	}

	public void data2Form(Utlev1 data) {
		if (data!=null) {
			faktura2ListWidget.getPageLoad().setSearch("ordernr", data.ordernr.toString(), "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
		}
	}


	public ListWidget getListWidget() {return faktura2ListWidget;}

}
