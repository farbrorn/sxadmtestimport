/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Faktura2;
import se.saljex.webadm.client.rpcobject.Order2;
import se.saljex.webadm.client.rpcobject.Orderhand;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class OrderhandListWidget extends ListWidget<Orderhand> implements HasData2Form<Integer>{
	public OrderhandListWidget() {
		super(null, new PageLoad<Orderhand>(new Orderhand(), 0, 100, 1000, null) ,null);
	}


	public void load(Integer nr) {
		if (nr!=null) {
			this.getPageLoad().setSearch("ordernr", nr.toString(), "ordernr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
		}
	}

	@Override
	void addListColumns(CellTable<Orderhand> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S13);
		getCellTable().addColumnStyleName(1, Const.Style_S5);
//		getCellTable().addColumnStyleName(2, Const.Style_S40);

		SxNumberColumn<Faktura2> c3;

		TextColumn<Orderhand> c1 = new TextColumn<Orderhand>() {
			@Override
			public String getValue(Orderhand object) {
				return object.serverdatum.toString();
			}
		};
		cellTable.addColumn(c1, "Datum");

		c1 = new TextColumn<Orderhand>() {
			@Override
			public String getValue(Orderhand object) {
				return object.anvandare;
			}
		};
		cellTable.addColumn(c1, "Användare");

		Column<Order2, String> c2;


		c1 = new TextColumn<Orderhand>() {
			@Override
			public String getValue(Orderhand object) {
				String ret = object.handelse;
				if (!Util.isEmpty(object.fraktsedelnr)) ret = ret + " Fraktsedelnr: " + object.fraktsedelnr + " " + object.antalkolli;
				return object.handelse;
			}
		};
		cellTable.addColumn(c1, "Händelse");


	}

	@Override
	public void data2Form(Integer data) {
		load(data);
	}



}
