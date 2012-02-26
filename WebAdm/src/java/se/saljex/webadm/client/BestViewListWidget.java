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
import se.saljex.webadm.client.common.rpcobject.BestView;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;

/**
 *
 * @author Ulf
 */
public class BestViewListWidget extends ListWidget<BestView> implements HasData2Form<Artikel>{
	public BestViewListWidget() {
		super(null, new PageLoad<BestView>(new BestView(), 0, 100, 1000, null) ,null);
	}


	@Override
	public void addListColumns(CellTable<BestView> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S3);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S10);
		getCellTable().addColumnStyleName(3, Const.Style_S13);
		getCellTable().addColumnStyleName(4, Const.Style_S13);
		getCellTable().addColumnStyleName(5, Const.Style_S35);
		getCellTable().addColumnStyleName(6, Const.Style_N10);
		getCellTable().addColumnStyleName(7, Const.Style_S3);


		cellTable.addColumn(new SxNumberColumn<BestView>(Util.fmt0Dec) {@Override public Number getValue(BestView object) {return object.lagernr;}}
							, "Lager");
		cellTable.addColumn(new TextColumn<BestView>() {@Override public String getValue(BestView object) {return object.bestnr.toString();}}
							, "Bestnr");
		cellTable.addColumn(new TextColumn<BestView>() {@Override public String getValue(BestView object) {return Util.formatDate(object.datum);}}
							, "Datum");
		cellTable.addColumn(new TextColumn<BestView>() {@Override public String getValue(BestView object) {return object.levnr;}}
							, "Leverantör");
		cellTable.addColumn(new TextColumn<BestView>() {@Override public String getValue(BestView object) {return object.artnr;}}
							, "Artikelnr");
		cellTable.addColumn(new TextColumn<BestView>() {@Override public String getValue(BestView object) {return object.artnamn;}}
							, "Benämning");
		cellTable.addColumn(new SxNumberColumn<BestView>(Util.fmt2Dec) {@Override public Number getValue(BestView object) {return object.best;}}
							, "Antal");
		cellTable.addColumn(new TextColumn<BestView>() {@Override public String getValue(BestView object) {return object.enh;}}
							, "Enh");

	}

	@Override
	public void data2Form(Artikel data) {
		SqlSelectParameters param = new SqlSelectParameters();
		param.addWhereParameter(SQLTableList.BOOL_CONNECTOR_NONE, 0, "artnr", SQLTableList.COMPARE_EQUALS, data.nummer, 0);
		param.addOrderByParameter("bestnr", SQLTableList.SORT_DESCANDING);
		this.getPageLoad().setSearch(param);
	}


}
