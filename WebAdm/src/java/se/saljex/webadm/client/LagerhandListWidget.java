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
import se.saljex.webadm.client.common.rpcobject.Lagerhand;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;

/**
 *
 * @author Ulf
 */
public class LagerhandListWidget extends ListWidget<Lagerhand> implements HasData2Form<Artikel>{
	public LagerhandListWidget() {
		super(null, new PageLoad<Lagerhand>(new Lagerhand(), 10, 50, 1000, null) ,null);
	}


	@Override
	public void addListColumns(CellTable<Lagerhand> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S3);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S10);
		getCellTable().addColumnStyleName(3, Const.Style_S3);
		getCellTable().addColumnStyleName(4, Const.Style_S20);
		getCellTable().addColumnStyleName(5, Const.Style_N10);
		getCellTable().addColumnStyleName(6, Const.Style_N10);

		cellTable.addColumn(new SxNumberColumn<Lagerhand>(Util.fmt0Dec) {@Override public Number getValue(Lagerhand object) {return object.lagernr;}}
							, "Lager");
		cellTable.addColumn(new TextColumn<Lagerhand>() {@Override public String getValue(Lagerhand object) {return Util.formatDate(object.datum);}}
							, "Datum");
		cellTable.addColumn(new TextColumn<Lagerhand>() {@Override public String getValue(Lagerhand object) {return Util.formatDate(object.tid);}}
							, "Tid");

		cellTable.addColumn(new TextColumn<Lagerhand>() {@Override public String getValue(Lagerhand object) {return object.anvandare;}}
							, "Användare");
		cellTable.addColumn(new TextColumn<Lagerhand>() {@Override public String getValue(Lagerhand object) {return object.handelse;}}
							, "Händelse");

		cellTable.addColumn(new SxNumberColumn<Lagerhand>(Util.fmt2Dec) {@Override public Number getValue(Lagerhand object) {return object.forandring;}}
							, "Förändring");
		cellTable.addColumn(new SxNumberColumn<Lagerhand>(Util.fmt2Dec) {@Override public Number getValue(Lagerhand object) {return object.nyttilager;}}
							, "Nytt saldo");

	}

	@Override
	public void data2Form(Artikel data) {
		SqlSelectParameters s = new SqlSelectParameters();
		s.addWhereParameter(SQLTableList.BOOL_CONNECTOR_NONE, 0, "artnr", SQLTableList.COMPARE_EQUALS, data.nummer, 0);
		s.addOrderByParameter("datum", SQLTableList.SORT_DESCANDING);
		s.addOrderByParameter("tid", SQLTableList.SORT_DESCANDING);
		this.getPageLoad().setSearch(s);
	}


}
