/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.FakturaRow;
import se.saljex.SxShop.client.rpcobject.UtlevInfo;
import se.saljex.SxShop.client.rpcobject.UtlevRow;

/**
 *
 * @author ulf
 */
public class UtlevInfoWidget extends VerticalPanel{
		final GlobalData globalData;
		private int ordernr;
		public UtlevInfoWidget(final GlobalData globalData, int ordernr) {
			this.globalData=globalData;
			this.ordernr=ordernr;
			globalData.service.getUtlevInfo(ordernr, callbackUtlevInfo);
			setSpacing(0);

		}

		AsyncCallback callbackUtlevInfo  = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			add(new Label("Fel:" + caught.getMessage()));
		}

		public void onSuccess(Object result) {
			UtlevInfo utlevInfo = (UtlevInfo)result;
			if (utlevInfo==null) {
				add(new Label("Angiven utleverans saknas."));
			} else {
				if (utlevInfo.utlev!=null) add(getInfoWidget(utlevInfo.utlev));
				if (utlevInfo.artikelrader!=null) add(getRaderWidget(utlevInfo.artikelrader));
			}
		}
	};


	private Widget getInfoWidget(UtlevRow ur) {
		FlexTable ft = new FlexTable();
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		FlexTable.FlexCellFormatter cf = ft.getFlexCellFormatter();
		ft.setText(0, 0, "Fakturanr:");
		ft.setText(1, 0, "Leveransadress:");
		cf.addStyleName(0, 0, globalData.STYLE_PROMPT);
		cf.addStyleName(1, 0, globalData.STYLE_PROMPT);
		ft.setText(0, 1, globalData.numberFormatInt.format(ur.faktnr));
		ft.setWidget(1, 1, new Label(ur.lavadr1));
		ft.setWidget(2, 1, new Label(ur.lavadr2));
		ft.setWidget(3, 1, new Label(ur.lavadr3));
		return ft;
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
		ft.getCellFormatter().addStyleName(0, 4, globalData.STYLE_TD_PRIS);
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
