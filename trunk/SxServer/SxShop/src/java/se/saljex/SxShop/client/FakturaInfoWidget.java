/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.BetalningRow;
import se.saljex.SxShop.client.rpcobject.FakturaHeader;
import se.saljex.SxShop.client.rpcobject.FakturaInfo;
import se.saljex.SxShop.client.rpcobject.FakturaRow;

/**
 *
 * @author ulf
 */
	public class FakturaInfoWidget extends VerticalPanel {
		final GlobalData globalData;
		private int faktnr;
		public FakturaInfoWidget(final GlobalData globalData, int faktnr) {
			this.globalData=globalData;
			this.faktnr=faktnr;
			globalData.service.getFakturaInfo(faktnr, callbackFakturaInfo);
			setSpacing(0);

		}

		AsyncCallback callbackFakturaInfo  = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			add(new Label("Fel:" + caught.getMessage()));
		}

		public void onSuccess(Object result) {
			FakturaInfo fakturaInfo = (FakturaInfo)result;
			if (fakturaInfo==null) {
				add(new Label("Angiven faktura saknas."));
			} else {
				if (fakturaInfo.rows!=null) add(getRaderWidget(fakturaInfo.rows));
				if (fakturaInfo.betalningar!=null) add(getBetalningWidget(fakturaInfo.betalningar));
			}
		}
	};

	private Widget getBetalningWidget(ArrayList<BetalningRow> a) {
		DisclosurePanel dp = new DisclosurePanel("Visa betalningar");
		FlexTable ft = new FlexTable();
		dp.setWidth("100%");
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		int row=1;
		if (a.size() < 1) {
			ft.setText(0, 0, "Det finns inga betalningar");
		} else {
			ft.getCellFormatter().addStyleName(0, 0, globalData.STYLE_TD_DATUM);
			ft.getCellFormatter().addStyleName(0, 1, globalData.STYLE_TD_PRIS);
			ft.getCellFormatter().addStyleName(0, 2, globalData.STYLE_TD_S15);
			ft.setText(0, 0, "Datum");
			ft.setText(0, 1, "Belopp");
			ft.setText(0, 2, "Betalsätt");
			ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);
			for (BetalningRow fb : a) {
				ft.getCellFormatter().addStyleName(row, 1, globalData.STYLE_TD_PRIS);
				ft.setText(row, 0, globalData.getDateString(fb.betdat));
				ft.setText(row, 1, globalData.numberFormat.format(fb.summa));
				ft.setWidget(row, 2, new Label(fb.betsatt));
				row++;
			}
		}

		dp.add(ft);
		return dp;
	}

	private Widget getRaderWidget(ArrayList<FakturaRow> a) {
		FlexTable ft = new FlexTable();
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		FlexTable.FlexCellFormatter cf = ft.getFlexCellFormatter();
		int row = 1;
		ft.getCellFormatter().addStyleName(0, 2, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 4, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 5, globalData.STYLE_TD_RAB);
		ft.getCellFormatter().addStyleName(0, 6, globalData.STYLE_TD_PRIS);
		ft.setText(0, 0, "Art.Nr");
		ft.setText(0, 1, "Benämning");
		ft.setText(0, 2, "Antal");
		ft.setText(0, 3, "Enh");
		ft.setText(0, 4, "Pris");
		ft.setText(0, 5, "%");
		ft.setText(0, 6, "Summa");
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

		for (FakturaRow r : a) {
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
				ft.setText(row, 2, globalData.numberFormat.format(r.lev));
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
