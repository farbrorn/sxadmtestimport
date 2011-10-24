/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Offert1;
import se.saljex.webadm.client.rpcobject.Order1;

/**
 *
 * @author Ulf
 */
public class Order1ListWidget extends ListWidget<Order1> {

	public Order1ListWidget(HasData2Form<Order1> formUpdat) {
		super(formUpdat, new PageLoad<Order1>(new Order1(), 10, 100, 1000, null) ,null);
	}

	@Override
	void addListColumns(CellTable<Order1> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S30);
		getCellTable().addColumnStyleName(3, Const.Style_S10);

		TextColumn<Order1> c1 = new TextColumn<Order1>() {
			@Override
			public String getValue(Order1 object) {
				return object.ordernr.toString();
			}
		};
		cellTable.addColumn(c1, "Offert");

		c1 = new TextColumn<Order1>() {
			@Override
			public String getValue(Order1 object) {
				return Util.formatDate(object.datum);
			}
		};
		cellTable.addColumn(c1, "Datum");

		c1 = new TextColumn<Order1>() {
			@Override
			public String getValue(Order1 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Kund");

		c1 = new TextColumn<Order1>() {
			@Override
			public String getValue(Order1 object) {
				return object.marke;
			}
		};
		cellTable.addColumn(c1, "Märke");

		c1 = new TextColumn<Order1>() {
			@Override
			public String getValue(Order1 object) {
				return object.status;
			}
		};
		cellTable.addColumn(c1, "Status");
	}




}
