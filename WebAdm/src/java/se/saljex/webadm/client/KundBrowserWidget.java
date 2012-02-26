/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.HasFormUpdater;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Kund;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class KundBrowserWidget extends ListWidget<Kund> {
	public KundBrowserWidget(HasFormUpdater<Kund> formUpdat) {
		super(formUpdat, new PageLoad<Kund>(new Kund(), 10, 100, 1000, null) ,null);
		this.getPageLoad().setSearch("nummer", "0", "namn", SQLTableList.COMPARE_NONE, SQLTableList.SORT_ASCENDING);
	}

	@Override
	public void addListColumns(CellTable<Kund> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S30);
		getCellTable().addColumnStyleName(1, Const.Style_S20);

		TextColumn<Kund> c1 = new TextColumn<Kund>() {
			@Override
			public String getValue(Kund object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Namn");

		TextColumn<Kund> c2 = new TextColumn<Kund>() {
			@Override
			public String getValue(Kund object) {
				return object.adr3;
			} };
		cellTable.addColumn(c2, "Adress");
	}


}
