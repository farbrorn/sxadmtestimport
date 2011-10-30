/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Artikel;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class ArtikelBrowserWidget extends ListWidget<Artikel> {
	public ArtikelBrowserWidget(HasFormUpdater<Artikel> formUpdat) {
		super(formUpdat, new PageLoad<Artikel>(new Artikel(), 10, 100, 1000, null) ,null);
		this.getPageLoad().setSearch("nummer", "0", "nummer", SQLTableList.COMPARE_NONE, SQLTableList.SORT_ASCENDING);
	}

	@Override
	void addListColumns(CellTable<Artikel> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S20);

		TextColumn<Artikel> c1 = new TextColumn<Artikel>() {
			@Override
			public String getValue(Artikel object) {
				return object.nummer;
			}
		};
		cellTable.addColumn(c1, "Nummer");

		c1 = new TextColumn<Artikel>() {
			@Override
			public String getValue(Artikel object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Namn");

	}


}
