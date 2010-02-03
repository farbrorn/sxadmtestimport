/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.FakturaInfo;
import se.saljex.SxShop.client.rpcobject.StatArtikelFakturaRow;

/**
 *
 * @author ulf
 */
public class StatArtikelInfoWidget extends VerticalPanel {
		final GlobalData globalData;
		public StatArtikelInfoWidget(final GlobalData globalData, String artnr, String frdat, String tidat) {
			this.globalData = globalData;
			globalData.service.getStatArtikelFakturaRows(artnr, frdat, tidat, callbackArtikelFakturaRows);
			setSpacing(0);
		}

		AsyncCallback callbackArtikelFakturaRows  = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			add(new Label("Fel:" + caught.getMessage()));
		}

		public void onSuccess(Object result) {
			ArrayList<StatArtikelFakturaRow> info = (ArrayList<StatArtikelFakturaRow>)result;
			if (info==null || info.isEmpty()) {
				add(new Label("Uppgifter saknas"));
			} else {
				add(getRaderWidget(info));
			}
		}
	};


	private Widget getRaderWidget(ArrayList<StatArtikelFakturaRow> a) {
		FlexTable ft = new FlexTable();
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		FlexTable.FlexCellFormatter cf = ft.getFlexCellFormatter();
		int row = 1;
		ft.setText(0, 0, "Faktura");
		ft.setText(0, 1, "Datum");
		ft.setText(0, 2, "Benämning");
		ft.setText(0, 3, "Antal");
		ft.setText(0, 4, "Enh");
		ft.setText(0, 5, "Pris");
		ft.setText(0, 6, "%");
		ft.setText(0, 7, "Summa");
		ft.getCellFormatter().addStyleName(0, 0, globalData.STYLE_TD_IDNR);
		ft.getCellFormatter().addStyleName(0, 1, globalData.STYLE_TD_DATUM);
		ft.getCellFormatter().addStyleName(0, 2, globalData.STYLE_TD_BENAMNING);
		ft.getCellFormatter().addStyleName(0, 3, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 4, globalData.STYLE_TD_ENHET);
		ft.getCellFormatter().addStyleName(0, 5, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 6, globalData.STYLE_TD_RAB);
		ft.getCellFormatter().addStyleName(0, 7, globalData.STYLE_TD_PRIS);
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

		for (StatArtikelFakturaRow r : a) {
			ft.setText(row, 0, globalData.numberFormatInt.format(r.faktnr));
			ft.setText(row, 1, globalData.getDateString(r.datum));
			ft.setWidget(row, 2, new Label(r.namn));
			ft.setText(row, 3, globalData.numberFormat.format(r.lev));
			ft.setWidget(row, 4, new Label(r.enh));
			ft.setText(row, 5, globalData.numberFormat.format(r.pris));
			ft.setText(row, 6, globalData.numberFormat.format(r.rab));
			ft.setText(row, 7, globalData.numberFormat.format(r.summa));
			ft.getCellFormatter().addStyleName(row, 3, globalData.STYLE_TD_PRIS);
			ft.getCellFormatter().addStyleName(row, 5, globalData.STYLE_TD_PRIS);
			ft.getCellFormatter().addStyleName(row, 6, globalData.STYLE_TD_RAB);
			ft.getCellFormatter().addStyleName(row, 7, globalData.STYLE_TD_PRIS);
			row++;
		}
		return ft;
	}

}
