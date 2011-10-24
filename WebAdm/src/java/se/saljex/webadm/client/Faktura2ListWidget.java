/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Faktura1;
import se.saljex.webadm.client.rpcobject.Faktura2;
import se.saljex.webadm.client.rpcobject.Order1;
import se.saljex.webadm.client.rpcobject.Order2;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.Utlev1;

/**
 *
 * @author Ulf
 */
public class Faktura2ListWidget extends ListWidget<Faktura2> implements HasData2Form<Faktura1>{
	public Faktura2ListWidget() {
		super(null, new PageLoad<Faktura2>(new Faktura2(), 0, 100, 1000, null) ,null);
	}

	public Faktura2ListWidget(HasData2Form<Faktura2> formUpdat, final PageLoad<Faktura2> pageLoad, HasShowMessage showError) {
		super(formUpdat, pageLoad, showError);
	}

	public void loadFakturaNr(Integer nr) {
		if (nr!=null) {
			this.getPageLoad().setSearch("faktnr", nr.toString(), "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
		}
	}

	@Override
	void addListColumns(CellTable<Faktura2> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S13);
		getCellTable().addColumnStyleName(1, Const.Style_S35);
		getCellTable().addColumnStyleName(2, Const.Style_N10);
		getCellTable().addColumnStyleName(3, Const.Style_S3);
		getCellTable().addColumnStyleName(4, Const.Style_N10);
		getCellTable().addColumnStyleName(5, Const.Style_N3);
		getCellTable().addColumnStyleName(6, Const.Style_AlignRight);

		SxNumberColumn<Faktura2> c3;

		TextColumn<Faktura2> c1 = new TextColumn<Faktura2>() {
			@Override
			public String getValue(Faktura2 object) {
				return object.artnr;
			}
		};
		cellTable.addColumn(c1, "Artnr");

		c1 = new TextColumn<Faktura2>() {
			@Override
			public String getValue(Faktura2 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Benämning");

		Column<Order2, String> c2;

		c3 = new SxNumberColumn<Faktura2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Faktura2 object) {
				return object.lev;
			}
		};
		cellTable.addColumn(c3, "Antal");

		c1 = new TextColumn<Faktura2>() {
			@Override
			public String getValue(Faktura2 object) {
				return object.enh;
			}
		};
		cellTable.addColumn(c1, "Enhet");

		c3 = new SxNumberColumn<Faktura2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Faktura2 object) {
				return object.pris;
			}
		};
		cellTable.addColumn(c3, "Pris");

		c3 = new SxNumberColumn<Faktura2>(Util.fmt0Dec) {
			@Override
			public Number getValue(Faktura2 object) {
				return object.rab;
			}
		};
		cellTable.addColumn(c3, "%");

		c3 = new SxNumberColumn<Faktura2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Faktura2 object) {
				return object.summa;
			}
		};
		cellTable.addColumn(c3, "Summa");

	}

	@Override
	public void data2Form(Faktura1 data) {
		loadFakturaNr(data.faktnr);
	}



}
