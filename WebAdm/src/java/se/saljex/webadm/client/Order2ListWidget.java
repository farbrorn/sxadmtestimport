/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Order1;
import se.saljex.webadm.client.rpcobject.Order2;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class Order2ListWidget extends ListWidget<Order2> implements HasData2Form<Order1>{
	public Order2ListWidget() {
		super(null, new PageLoad<Order2>(new Order2(), 0, 100, 1000, null) ,null);
	}

	public void loadOrderNr(Integer ordernr) {
		if (ordernr!=null) {
			this.getPageLoad().setSearch("ordernr", ordernr.toString(), "ordernr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
		}
	}

	@Override
	void addListColumns(CellTable<Order2> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S13);
		getCellTable().addColumnStyleName(1, Const.Style_S35);
		getCellTable().addColumnStyleName(2, Const.Style_N10);
		getCellTable().addColumnStyleName(3, Const.Style_S3);
		getCellTable().addColumnStyleName(4, Const.Style_N10);
		getCellTable().addColumnStyleName(5, Const.Style_N3);
		getCellTable().addColumnStyleName(6, Const.Style_AlignRight);

		SxNumberColumn<Order2> c3;

		TextColumn<Order2> c1 = new TextColumn<Order2>() {
			@Override
			public String getValue(Order2 object) {
				return object.artnr;
			}
		};
		cellTable.addColumn(c1, "Artnr");

		c1 = new TextColumn<Order2>() {
			@Override
			public String getValue(Order2 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Benämning");

		Column<Order2, String> c2;

		c3 = new SxNumberColumn<Order2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Order2 object) {
				return object.lev;
			}
		};
		cellTable.addColumn(c3, "Antal");

		c1 = new TextColumn<Order2>() {
			@Override
			public String getValue(Order2 object) {
				return object.enh;
			}
		};
		cellTable.addColumn(c1, "Enhet");

		c3 = new SxNumberColumn<Order2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Order2 object) {
				return object.pris;
			}
		};
		cellTable.addColumn(c3, "Pris");

		c3 = new SxNumberColumn<Order2>(Util.fmt0Dec) {
			@Override
			public Number getValue(Order2 object) {
				return object.rab;
			}
		};
		cellTable.addColumn(c3, "%");

		c3 = new SxNumberColumn<Order2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Order2 object) {
				return object.summa;
			}
		};
		cellTable.addColumn(c3, "Summa");

	}

	@Override
	public void data2Form(Order1 data) {
		loadOrderNr(data.ordernr);
	}



}
