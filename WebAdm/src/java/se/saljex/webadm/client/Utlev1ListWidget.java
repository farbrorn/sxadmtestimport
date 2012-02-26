/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Utlev1;

/**
 *
 * @author Ulf
 */
public class Utlev1ListWidget extends ListWidget<Utlev1> {

	public Utlev1ListWidget(HasData2Form<Utlev1> formUpdat) {
		super(formUpdat, new PageLoad<Utlev1>(new Utlev1(), 10, 50, 1000, null) ,null);
	}

	@Override
	public void addListColumns(CellTable<Utlev1> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S30);

		TextColumn<Utlev1> c1 = new TextColumn<Utlev1>() {
			@Override
			public String getValue(Utlev1 object) {
				return object.ordernr.toString();
			}
		};
		cellTable.addColumn(c1, "Order");

		c1 = new TextColumn<Utlev1>() {
			@Override
			public String getValue(Utlev1 object) {
				return Util.formatDate(object.datum);
			}
		};
		cellTable.addColumn(c1, "Datum");

		c1 = new TextColumn<Utlev1>() {
			@Override
			public String getValue(Utlev1 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Kund");

		c1 = new TextColumn<Utlev1>() {
			@Override
			public String getValue(Utlev1 object) {
				return object.marke;
			}
		};
		cellTable.addColumn(c1, "Märke");

	}




}
