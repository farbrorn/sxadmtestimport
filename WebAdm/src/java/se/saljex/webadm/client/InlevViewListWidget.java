/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.SxNumberColumn;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Artikel;
import se.saljex.webadm.client.common.rpcobject.InlevView;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;

/**
 *
 * @author Ulf
 */
public class InlevViewListWidget extends ListWidget<InlevView> implements HasData2Form<Artikel>{
	public InlevViewListWidget() {
		super(null, new PageLoad<InlevView>(new InlevView(), 10, 50, 1000, null) ,null);
	}


	@Override
	public void addListColumns(CellTable<InlevView> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S3);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S10);
		getCellTable().addColumnStyleName(3, Const.Style_S13);
		getCellTable().addColumnStyleName(4, Const.Style_S13);
		getCellTable().addColumnStyleName(5, Const.Style_S35);
		getCellTable().addColumnStyleName(6, Const.Style_N10);
		getCellTable().addColumnStyleName(7, Const.Style_S3);


		cellTable.addColumn(new SxNumberColumn<InlevView>(Util.fmt0Dec) {@Override public Number getValue(InlevView object) {return object.lagernr;}}
							, "Lager");
		cellTable.addColumn(new TextColumn<InlevView>() {@Override public String getValue(InlevView object) {return object.bestnr.toString();}}
							, "Bestnr");
		cellTable.addColumn(new TextColumn<InlevView>() {@Override public String getValue(InlevView object) {return Util.formatDate(object.datum);}}
							, "Datum");
		cellTable.addColumn(new TextColumn<InlevView>() {@Override public String getValue(InlevView object) {return object.levnr;}}
							, "Leverantör");
		cellTable.addColumn(new TextColumn<InlevView>() {@Override public String getValue(InlevView object) {return object.artnr;}}
							, "Artikelnr");
		cellTable.addColumn(new TextColumn<InlevView>() {@Override public String getValue(InlevView object) {return object.artnamn;}}
							, "Benämning");
		cellTable.addColumn(new SxNumberColumn<InlevView>(Util.fmt2Dec) {@Override public Number getValue(InlevView object) {return object.antal;}}
							, "Antal");
		cellTable.addColumn(new TextColumn<InlevView>() {@Override public String getValue(InlevView object) {return object.enh;}}
							, "Enh");

	}

	@Override
	public void data2Form(Artikel data) {
		SqlSelectParameters param = new SqlSelectParameters();
		param.addWhereParameter(SQLTableList.BOOL_CONNECTOR_NONE, 0, "artnr", SQLTableList.COMPARE_EQUALS, data.nummer, 0);
		param.addOrderByParameter("id", SQLTableList.SORT_DESCANDING);
		this.getPageLoad().setSearch(param);
	}


}
