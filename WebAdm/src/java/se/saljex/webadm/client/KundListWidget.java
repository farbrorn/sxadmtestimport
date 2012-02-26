/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.HasShowMessage;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.HasFormUpdater;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import se.saljex.webadm.client.common.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundListWidget extends ListWidget<Kund> {
	public KundListWidget(HasFormUpdater<Kund> formUpdat, HasShowMessage showError) {
		super(null, new PageLoad<Kund>(new Kund(), 3, 50, 100, null), showError);
	}

	@Override
	public void addListColumns(CellTable<Kund> cellTable) {

		TextColumn<Kund> c1 = new TextColumn<Kund>() {

			@Override
			public String getValue(Kund object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Namn");
	}


}
