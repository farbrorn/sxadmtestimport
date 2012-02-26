/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.HasShowMessage;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.HasFormUpdater;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Kund;
import se.saljex.webadm.client.common.rpcobject.Kundres;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class KundresListWidget extends ListWidget<Kundres> implements HasData2Form<Kund> {
	public KundresListWidget(HasFormUpdater<Kundres> formUpdat, HasShowMessage showError) {

		super(formUpdat, new PageLoad<Kundres>(new Kundres(), 3, 50, 100, null), showError);
	}

	@Override
	public void addListColumns(CellTable<Kundres> cellTable) {
		int cn = 0;

		getCellTable().addColumnStyleName(1, Const.Style_S15);
		getCellTable().addColumnStyleName(2, Const.Style_S15);
		getCellTable().addColumnStyleName(3, Const.Style_N10);
		getCellTable().addColumnStyleName(4, Const.Style_S15);


		TextColumn<Kundres> c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return object.faktnr.toString();
			}
		};
		cellTable.addColumn(c1, "Faktura");
		getCellTable().addColumnStyleName(cn++, Const.Style_S10);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return object.datum.toString();
			}
		};
		cellTable.addColumn(c1, "Datum");
		getCellTable().addColumnStyleName(cn++, Const.Style_S15);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return Util.formatDate(object.falldat);
			}
		};
		cellTable.addColumn(c1, "Falldatum");
		getCellTable().addColumnStyleName(cn++, Const.Style_S15);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return Util.format2Dec(object.tot);
			}
		};
		cellTable.addColumn(c1, "Belopp");
		getCellTable().addColumnStyleName(cn++, Const.Style_N10);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return Util.formatDate(object.pdat1);
			}
		};
		cellTable.addColumn(c1, "Påminnelse1");
		getCellTable().addColumnStyleName(cn++, Const.Style_S15);
	}

	@Override
	public void data2Form(Kund data) {
		getPageLoad().setSearch("kundnr", data.nummer, "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}



}
