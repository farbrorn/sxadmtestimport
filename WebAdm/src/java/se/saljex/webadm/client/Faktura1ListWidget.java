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
import se.saljex.webadm.client.common.rpcobject.Faktura1;
import se.saljex.webadm.client.common.rpcobject.Offert1;
import se.saljex.webadm.client.common.rpcobject.Order1;

/**
 *
 * @author Ulf
 */
public class Faktura1ListWidget extends ListWidget<Faktura1> {

	public Faktura1ListWidget(HasData2Form<Faktura1> formUpdat) {
		super(formUpdat, new PageLoad<Faktura1>(new Faktura1(), 10, 100, 1000, null) ,null);
	}

	@Override
	public void addListColumns(CellTable<Faktura1> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S30);
		getCellTable().addColumnStyleName(3, Const.Style_N15);

		TextColumn<Faktura1> c1 = new TextColumn<Faktura1>() {
			@Override
			public String getValue(Faktura1 object) {
				return object.faktnr.toString();
			}
		};
		cellTable.addColumn(c1, "Faktura");

		c1 = new TextColumn<Faktura1>() {
			@Override
			public String getValue(Faktura1 object) {
				return Util.formatDate(object.datum);
			}
		};
		cellTable.addColumn(c1, "Datum");

		c1 = new TextColumn<Faktura1>() {
			@Override
			public String getValue(Faktura1 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Kund");

		SxNumberColumn<Faktura1> c3;

		c3 = new SxNumberColumn<Faktura1>(Util.fmt2Dec) {
			@Override
			public Double getValue(Faktura1 object) {
				return object.t_Attbetala;
			}
		};
		cellTable.addColumn(c1, "Belopp");

	}




}
