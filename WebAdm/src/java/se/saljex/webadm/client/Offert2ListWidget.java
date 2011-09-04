/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.Offert1;
import se.saljex.webadm.client.rpcobject.Offert2;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class Offert2ListWidget extends ListWidget<Offert2> implements HasData2Form<Offert1>{
	public Offert2ListWidget() {
		super(null, new PageLoadOffert2(0, 100, 1000, null) ,null);
	}

	public void loadOffertNr(Integer offertnr) {
		if (offertnr!=null) {
			this.getPageLoad().setSearch("offertnr", offertnr.toString(), "offertnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
		}
	}

	@Override
	void addListColumns(CellTable<Offert2> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S13);
		getCellTable().addColumnStyleName(1, Const.Style_S35);
		getCellTable().addColumnStyleName(2, Const.Style_N10);
		getCellTable().addColumnStyleName(3, Const.Style_S3);
		getCellTable().addColumnStyleName(4, Const.Style_N10);
		getCellTable().addColumnStyleName(5, Const.Style_N3);
		getCellTable().addColumnStyleName(6, Const.Style_AlignRight);

		SxNumberColumn<Offert2> c3;

		TextColumn<Offert2> c1 = new TextColumn<Offert2>() {
			@Override
			public String getValue(Offert2 object) {
				return object.artnr;
			}
		};
		cellTable.addColumn(c1, "Artnr");

		c1 = new TextColumn<Offert2>() {
			@Override
			public String getValue(Offert2 object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Benämning");

		Column<Offert2, String> c2;

		c3 = new SxNumberColumn<Offert2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Offert2 object) {
				return object.lev;
			}
		};
		cellTable.addColumn(c3, "Antal");

		c1 = new TextColumn<Offert2>() {
			@Override
			public String getValue(Offert2 object) {
				return object.enh;
			}
		};
		cellTable.addColumn(c1, "Enhet");

		c3 = new SxNumberColumn<Offert2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Offert2 object) {
				return object.pris;
			}
		};
		cellTable.addColumn(c3, "Pris");

		c3 = new SxNumberColumn<Offert2>(Util.fmt0Dec) {
			@Override
			public Number getValue(Offert2 object) {
				return object.rab;
			}
		};
		cellTable.addColumn(c3, "%");

		c3 = new SxNumberColumn<Offert2>(Util.fmt2Dec) {
			@Override
			public Number getValue(Offert2 object) {
				return object.summa;
			}
		};
		cellTable.addColumn(c3, "Summa");

	}

	@Override
	public void data2Form(Offert1 data) {
		loadOffertNr(data.offertnr);
	}



}
