/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.KundresRow;

/**
 *
 * @author ulf
 */
public class KundresListaWidget extends SxWidget {
	private FlexTable ft = new FlexTable();
	private CellFormatter ftCellFormatter = ft.getCellFormatter();
	private RowFormatter ftRowFormatter = ft.getRowFormatter();

	public KundresListaWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);
		ft.setWidth("100%");
		add(ft);
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.addStyleName(globalData.STYLE_TABLE_INFO);
		fillFt();
	}

	private void fillFt() {
		currentRow=1;
		ft.clear();
		ftCellFormatter.addStyleName(0,0, globalData.STYLE_TD_ACTION);
		ftCellFormatter.addStyleName(0,1, globalData.STYLE_TD_IDNR);
		ftCellFormatter.addStyleName(0,2, globalData.STYLE_TD_DATUM);
		ftCellFormatter.addStyleName(0,3, globalData.STYLE_TD_S10);
		ftCellFormatter.addStyleName(0,4, globalData.STYLE_TD_S30);
		ftCellFormatter.addStyleName(0,5, globalData.STYLE_TD_TABORT);
		ft.setWidget(0, 0, new Label("Visa"));
		ft.setWidget(0, 1, new Label("Fakturanr"));
		ft.setWidget(0, 2, new Label("Datum"));
		ft.setWidget(0, 3, new Label("Förf.datum"));
		ft.setWidget(0, 4, new Label("Belopp"));
		ft.setWidget(0, 5, new Label(" "));
		ftRowFormatter.addStyleName(0, globalData.STYLE_TR_RUBRIK);
		globalData.service.getKundresLista(callbackLoadPage);
	}

	@Override
	protected void pageLoaded(Object result) {
		ArrayList<KundresRow> kre=(ArrayList<KundresRow>)result;
		if (kre==null) {
			setError("Det finns inga obetalda fakturor");
		} else {
			clearError();
			for (KundresRow r : kre) {
				final int finalRow=currentRow;
				final int finalFaktnr=r.faktnr;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalFaktnr, finalRow );
					}
				});
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setText(currentRow, 1, globalData.numberFormatInt.format(r.faktnr));
				ft.setText(currentRow, 2, globalData.getDateString(r.datum));
				ft.setText(currentRow, 3, globalData.getDateString(r.falldat));
				ft.setText(currentRow, 4, globalData.numberFormat.format(r.tot));
				ft.setWidget(currentRow, 5, new Label(r.inkassodatum!=null ? "Inkassoärende " + globalData.getDateString(r.inkassodatum) : " "));
				if (currentRowHighlite ) {
					ftRowFormatter.addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
				}
				int oCn=0;
				ft.getFlexCellFormatter().setColSpan((currentRow+1), 0, 6);
				currentRow = currentRow+2;
				currentRowHighlite=!currentRowHighlite;
			}
		}

	}

	private void visaClick(Anchor visaAnchor, int faktnr, int row) {
		clearError();
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			FakturaInfoWidget fi = new FakturaInfoWidget(globalData, faktnr);
			fi.addStyleName(globalData.STYLE_DROPDOWNWIDGET);
			ft.setWidget(row+1, 0, fi);
		} else {
			visaAnchor.setText("Visa");
			ft.clearCell(row+1, 0);
		}
	}
}
