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
import se.saljex.SxShop.client.rpcobject.OffertInfo;
import se.saljex.SxShop.client.rpcobject.OffertRow;

/**
 *
 * @author ulf
 */
public class OffertInfoWidget extends VerticalPanel{

		final GlobalData globalData;
		public OffertInfoWidget(final GlobalData globalData, int offertnr) {
			this.globalData=globalData;
			globalData.service.getOffertInfo(offertnr, callbackOffertInfo);
			setSpacing(0);
		}

		AsyncCallback callbackOffertInfo  = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			add(new Label("Fel:" + caught.getMessage()));
		}

		public void onSuccess(Object result) {
			OffertInfo offertInfo = (OffertInfo)result;
			if (offertInfo==null) {
				add(new Label("Angiven offert saknas."));
			} else {
				if (offertInfo.artikelrader!=null) add(getRaderWidget(offertInfo.artikelrader));
			}
		}
	};


	private Widget getRaderWidget(ArrayList<OffertRow> a) {
		FlexTable ft = new FlexTable();
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		FlexTable.FlexCellFormatter cf = ft.getFlexCellFormatter();
		int row = 1;
		ft.getCellFormatter().addStyleName(0, 2, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 4, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 5, globalData.STYLE_TD_RAB);
		ft.getCellFormatter().addStyleName(0, 4, globalData.STYLE_TD_PRIS);
		ft.setText(0, 0, "Art.Nr");
		ft.setText(0, 1, "Benämning");
		ft.setText(0, 2, "Antal");
		ft.setText(0, 3, "Enh");
		ft.setText(0, 4, "Pris");
		ft.setText(0, 5, "%");
		ft.setText(0, 6, "Summa");
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

		for (OffertRow r : a) {
			if (r.text!=null && !r.text.isEmpty() && (r.artnr==null || r.artnr.isEmpty())) {
				ft.setWidget(row, 0, new Label(r.text));
				cf.setColSpan(row, 0, 6);
			} else {
				ft.getCellFormatter().addStyleName(row, 2, globalData.STYLE_TD_PRIS);
				ft.getCellFormatter().addStyleName(row, 4, globalData.STYLE_TD_PRIS);
				ft.getCellFormatter().addStyleName(row, 5, globalData.STYLE_TD_RAB);
				ft.getCellFormatter().addStyleName(row, 6, globalData.STYLE_TD_PRIS);
				ft.setWidget(row, 0, new Label(r.artnr));
				ft.setWidget(row, 1, new Label(r.namn));
				ft.setText(row, 2, globalData.numberFormat.format(r.antal));
				ft.setWidget(row, 3, new Label(r.enh));
				ft.setText(row, 4, globalData.numberFormat.format(r.pris));
				ft.setText(row, 5, globalData.numberFormat.format(r.rab));
				ft.setText(row, 6, globalData.numberFormat.format(r.summa));
			}
			row++;
		}
		return ft;
	}

}
