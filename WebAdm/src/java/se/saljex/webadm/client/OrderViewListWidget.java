/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.SxNumberColumn;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Artikel;
import se.saljex.webadm.client.common.rpcobject.OrderView;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class OrderViewListWidget extends ListWidget<OrderView> implements HasData2Form<Artikel>{
	public OrderViewListWidget() {
		super(null, new PageLoad<OrderView>(new OrderView(), 10, 50, 1000, null) ,null);
	}


	@Override
	public void addListColumns(CellTable<OrderView> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S13);
		getCellTable().addColumnStyleName(3, Const.Style_S35);
		getCellTable().addColumnStyleName(4, Const.Style_N10);
		getCellTable().addColumnStyleName(5, Const.Style_S3);


		cellTable.addColumn(new TextColumn<OrderView>() {@Override public String getValue(OrderView object) {return object.ordernr.toString();}}
							, "Ordernr");
		cellTable.addColumn(new TextColumn<OrderView>() {@Override public String getValue(OrderView object) {return Util.formatDate(object.datum);}}
							, "Datum");
		cellTable.addColumn(new TextColumn<OrderView>() {@Override public String getValue(OrderView object) {return object.artnr;}}
							, "Artikel");
		cellTable.addColumn(new TextColumn<OrderView>() {@Override public String getValue(OrderView object) {return object.artnamn;}}
							, "Benämning");
		cellTable.addColumn(new SxNumberColumn<OrderView>(Util.fmt2Dec) {@Override public Number getValue(OrderView object) {return object.best;}}
							, "Antal");
		cellTable.addColumn(new TextColumn<OrderView>() {@Override public String getValue(OrderView object) {return object.enh;}}
							, "Enh");

	}

	@Override
	public void data2Form(Artikel data) {
		this.getPageLoad().setSearch("artnr", data.nummer, "ordernr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
	}


}
