/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import se.saljex.SxShop.client.rpcobject.OffertHeader;
import se.saljex.SxShop.client.rpcobject.OffertHeaderList;

/**
 *
 * @author ulf
 */
public class OffertListWidget extends SxWidget {
	private FlexTable ft = new FlexTable();
	private int nextRow=0;

	public OffertListWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);

		add(ft);
		add(visaFlerRaderAnchor);
		initFT();
		loadNextPage();
	}

	public void initFT() {
		nextRow=0;
		currentRow=1;
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.addStyleName(globalData.STYLE_TABLE_INFO);

		ft.getCellFormatter().addStyleName(0,0, globalData.STYLE_TD_ACTION);
		ft.getCellFormatter().addStyleName(0,1, globalData.STYLE_TD_ACTION);
		ft.getCellFormatter().addStyleName(0,2, globalData.STYLE_TD_IDNR);
		ft.getCellFormatter().addStyleName(0,3, globalData.STYLE_TD_DATUM);
		ft.getCellFormatter().addStyleName(0,4, globalData.STYLE_TD_MARKEINFO);
		ft.setWidget(0, 0, new Label("Visa"));
		ft.setWidget(0, 1, new Label("PDF"));
		ft.setWidget(0, 2, new Label("Offertnr"));
		ft.setWidget(0, 3, new Label("Datum"));
		ft.setWidget(0, 4, new Label("Märke"));
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

	}

	@Override
	protected void loadNextPage() {
		blockTillFinishedLoading=true;
		globalData.service.getOffertHeaders(nextRow, pageSize, super.callbackLoadPage);
	}



	private void visaClick(Anchor visaAnchor, int ordernr, int row) {
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			UtlevInfoWidget ui = new UtlevInfoWidget(globalData, ordernr);
			ui.addStyleName(globalData.STYLE_DROPDOWNWIDGET);
			ft.setWidget(row+1, 0, ui);
		} else {
			//ftRowFormatter.removeStyleName(row, globalData.STYLE_TR_HIGHLITE);
			visaAnchor.setText("Visa");
			ft.clearCell(row+1, 0);
		}
	}

	@Override
	protected void pageLoaded(Object result) {
			OffertHeaderList ol=(OffertHeaderList)result;
			for (OffertHeader oh : ol.rader) {
				final int finalRow=currentRow;
				final int finalOffertnr = oh.offertnr;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalOffertnr, finalRow );
					}
				});
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setText(currentRow, 2, globalData.numberFormatInt.format(oh.offertnr));
				ft.setText(currentRow, 3, globalData.getDateString(oh.datum));
				ft.setWidget(currentRow, 4, new Label(oh.marke));
				ft.getCellFormatter().addStyleName(currentRow, 1, globalData.STYLE_TD_IDNR);
				if (currentRowHighlite ) {
					ft.getRowFormatter().addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
				}
				ft.getFlexCellFormatter().setColSpan((currentRow+1), 0, 5);
				currentRow = currentRow+2;
				currentRowHighlite=!currentRowHighlite;
			}
			if (ol.hasMoreRows) {
				visaFlerRaderAnchor.setVisible(true);
			}

			nextRow=ol.nextRow;
	}

}
