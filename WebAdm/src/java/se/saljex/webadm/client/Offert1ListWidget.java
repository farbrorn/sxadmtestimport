/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Offert1;

/**
 *
 * @author Ulf
 */
public class Offert1ListWidget extends ListWidget<Offert1> {

	public Offert1ListWidget(HasData2Form<Offert1> formUpdat) {
		super(formUpdat, new PageLoad<Offert1>(new Offert1(), 10, 100, 1000, null) ,null);
	}

	@Override
	public void addListColumns(CellTable<Offert1> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S30);
//		getCellTable().addColumnStyleName(2, Const.Style_S30);

		TextColumn<Offert1> c1 = new TextColumn<Offert1>() {
			@Override
			public String getValue(Offert1 object) {
				return object.offertnr.toString();
			}
		};
		cellTable.addColumn(c1, "Offert");

		c1 = new TextColumn<Offert1>() {
			@Override
			public String getValue(Offert1 object) {
				return Util.formatDate(object.datum);
			}
		};
		cellTable.addColumn(c1, "Datum");

		c1 = new TextColumn<Offert1>() {
			@Override
			public String getValue(Offert1 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Kund");

		c1 = new TextColumn<Offert1>() {
			@Override
			public String getValue(Offert1 object) {
				return object.marke;
			}
		};
		cellTable.addColumn(c1, "Märke");
	}




}
