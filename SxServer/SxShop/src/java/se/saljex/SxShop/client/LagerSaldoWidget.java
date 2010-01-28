/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import se.saljex.SxShop.client.rpcobject.LagerSaldo;
import se.saljex.SxShop.client.rpcobject.LagerSaldoRad;

/**
 *
 * @author ulf
 */
public class LagerSaldoWidget extends FlexTable{

	private final RowFormatter rowFormatter;
	private final GlobalData globalData;
	public LagerSaldoWidget(final GlobalData globalData, String artnr) {
		this.globalData = globalData;
		rowFormatter = this.getRowFormatter();
		globalData.service.getLagerSaldon(artnr, callbackSaldo);
		setCellPadding(1);
		setCellSpacing(0);
		setWidget(0, 0, new Label("Hämtar data"));

	}

	final AsyncCallback callbackSaldo = new AsyncCallback() {
		public void onSuccess(Object result) {
			LagerSaldo l = (LagerSaldo)result;
			setWidget(0, 0, new Label("Lager"));
			setWidget(0, 1, new Label("Tillgängliga"));
			rowFormatter.addStyleName(0, "sx-tablerubrik");

			int radCn=1;
			for (LagerSaldoRad lr : l.lagerSaldoRader) {
				setWidget(radCn, 0, new Label(lr.lagerNamn));
				setWidget(radCn, 1, new Label(globalData.numberFormat.format(lr.tillgangliga)));
				if (radCn%2 > 0 ) rowFormatter.addStyleName(radCn, globalData.STYLE_TR_ODDROW);
				radCn++;
			}

		}

		public void onFailure(Throwable caught) {
			if (caught.getMessage()==null)
			setWidget(0, 0, new Label("Logga in för att se lagersaldon."));
			else setWidget(0, 0, new Label(caught.getMessage()));
		}
	};

}
