/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Artikel;
import se.saljex.webadm.client.rpcobject.FakturaView;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class FakturaViewListWidget extends ListWidget<FakturaView> implements HasData2Form<Artikel>{
	public FakturaViewListWidget() {
		super(null, new PageLoad<FakturaView>(new FakturaView(), 10, 50, 1000, null) ,null);
	}


	@Override
	void addListColumns(CellTable<FakturaView> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S13);
		getCellTable().addColumnStyleName(3, Const.Style_S35);
		getCellTable().addColumnStyleName(4, Const.Style_N10);
		getCellTable().addColumnStyleName(5, Const.Style_S3);
		getCellTable().addColumnStyleName(6, Const.Style_N10);
		getCellTable().addColumnStyleName(7, Const.Style_N5);
		getCellTable().addColumnStyleName(8, Const.Style_N10);


		cellTable.addColumn(new TextColumn<FakturaView>() {@Override public String getValue(FakturaView object) {return object.faktnr.toString();}}
							, "Fakturanr");
		cellTable.addColumn(new TextColumn<FakturaView>() {@Override public String getValue(FakturaView object) {return Util.formatDate(object.datum);}}
							, "Datum");
		cellTable.addColumn(new TextColumn<FakturaView>() {@Override public String getValue(FakturaView object) {return object.artnr;}}
							, "Artikel");
		cellTable.addColumn(new TextColumn<FakturaView>() {@Override public String getValue(FakturaView object) {return object.artnamn;}}
							, "Benämning");
		cellTable.addColumn(new SxNumberColumn<FakturaView>(Util.fmt2Dec) {@Override public Number getValue(FakturaView object) {return object.lev;}}
							, "Antal");
		cellTable.addColumn(new TextColumn<FakturaView>() {@Override public String getValue(FakturaView object) {return object.enh;}}
							, "Enh");
		cellTable.addColumn(new SxNumberColumn<FakturaView>(Util.fmt2Dec) {@Override public Number getValue(FakturaView object) {return object.pris;}}
							, "Pris");
		cellTable.addColumn(new SxNumberColumn<FakturaView>(Util.fmt2Dec) {@Override public Number getValue(FakturaView object) {return object.rab;}}
							, "%");
		cellTable.addColumn(new SxNumberColumn<FakturaView>(Util.fmt2Dec) {@Override public Number getValue(FakturaView object) {return object.summa;}}
							, "Summa");

	}

	@Override
	public void data2Form(Artikel data) {
		this.getPageLoad().setSearch("artnr", data.nummer, "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}


}
