/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Artikel;
import se.saljex.webadm.client.rpcobject.Lager;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.SqlSelectParameters;

/**
 *
 * @author Ulf
 */
public class LagerListWidget extends ListWidget<Lager> implements HasData2Form<Artikel>{
	public LagerListWidget() {
		super(null, new PageLoad<Lager>(new Lager(), 0, 100, 1000, null) ,null);
	}


	@Override
	void addListColumns(CellTable<Lager> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S3);
		getCellTable().addColumnStyleName(1, Const.Style_N10);
		getCellTable().addColumnStyleName(2, Const.Style_N10);
		getCellTable().addColumnStyleName(3, Const.Style_N10);
		getCellTable().addColumnStyleName(4, Const.Style_N10);
		getCellTable().addColumnStyleName(5, Const.Style_N10);
		getCellTable().addColumnStyleName(6, Const.Style_S20);

		cellTable.addColumn(new SxNumberColumn<Lager>(Util.fmt0Dec) {@Override public Number getValue(Lager object) {return object.lagernr;}}
							, "Lagernr");
		cellTable.addColumn(new SxNumberColumn<Lager>(Util.fmt2Dec) {@Override public Number getValue(Lager object) {return object.ilager;}}
							, "Ilager");
		cellTable.addColumn(new SxNumberColumn<Lager>(Util.fmt2Dec) {@Override public Number getValue(Lager object) {return object.bestpunkt;}}
							, "Bestpunkt");
		cellTable.addColumn(new SxNumberColumn<Lager>(Util.fmt2Dec) {@Override public Number getValue(Lager object) {return object.maxlager;}}
							, "Maxlager");
		cellTable.addColumn(new SxNumberColumn<Lager>(Util.fmt2Dec) {@Override public Number getValue(Lager object) {return object.best;}}
							, "Beställda");
		cellTable.addColumn(new SxNumberColumn<Lager>(Util.fmt2Dec) {@Override public Number getValue(Lager object) {return object.iorder;}}
							, "IOrder");
		cellTable.addColumn(new TextColumn<Lager>() {@Override public String getValue(Lager object) {return object.lagerplats;}}
							, "Lagerplats");


	}

	@Override
	public void data2Form(Artikel data) {
		SqlSelectParameters s = new SqlSelectParameters();
		s.addWhereParameter(SQLTableList.BOOL_CONNECTOR_NONE, 0, "artnr", SQLTableList.COMPARE_EQUALS, data.nummer, 0);
		s.addOrderByParameter("lagernr", SQLTableList.SORT_ASCENDING);
		this.getPageLoad().setSearch(s);
	}


}
