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
import se.saljex.SxShop.client.rpcobject.BetalningList;
import se.saljex.SxShop.client.rpcobject.BetalningRow;
import se.saljex.SxShop.client.rpcobject.FakturaHeader;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderList;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderOrderMarke;

/**
 *
 * @author ulf
 */
public class BetalningListaWidget extends SxWidget {
	private FlexTable ft = new FlexTable();
	private int nextRow=0;
	private ColumnFormatter ftColFormatter = ft.getColumnFormatter();
	private CellFormatter ftCellFormatter = ft.getCellFormatter();
	private RowFormatter ftRowFormatter = ft.getRowFormatter();

	public BetalningListaWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);
		//addStyleName(globalData.STYLE_MAINPANEL);

		ft.setWidth("100%");
		add(ft);
		add(visaFlerRaderAnchor);
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.addStyleName(globalData.STYLE_TABLE_INFO);
		ftCellFormatter.addStyleName(0,0, globalData.STYLE_TD_ACTION);
		ftCellFormatter.addStyleName(0,1, globalData.STYLE_TD_ACTION);
		ftCellFormatter.addStyleName(0,2, globalData.STYLE_TD_IDNR);
		ftCellFormatter.addStyleName(0,3, globalData.STYLE_TD_DATUM);
		ftCellFormatter.addStyleName(0,4, globalData.STYLE_TD_PRIS);
		ftCellFormatter.addStyleName(0,5, globalData.STYLE_TD_MARKEINFO);
		ft.setWidget(0, 0, new Label("Visa"));
		ft.setWidget(0, 1, new Label("PDF"));
		ft.setWidget(0, 2, new Label("Fakturanr"));
		ft.setWidget(0, 3, new Label("Bet.Datum"));
		ft.setWidget(0, 4, new Label("Belopp"));
		ft.setWidget(0, 5, new Label("Betalsätt"));
		ftRowFormatter.addStyleName(0, globalData.STYLE_TR_RUBRIK);
		loadNextPage();
	}

	@Override
	protected void loadNextPage() {
			globalData.service.getBetalningList(nextRow, pageSize, super.callbackLoadPage);
	}


	private void visaClick(Anchor visaAnchor, int faktnr, int row) {
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			FakturaInfoWidget fi = new FakturaInfoWidget(globalData, faktnr);
			fi.addStyleName(globalData.STYLE_DROPDOWNWIDGET);
			ft.setWidget(row+1, 0, fi);
		} else {
			//ftRowFormatter.removeStyleName(row, globalData.STYLE_TR_HIGHLITE);
			visaAnchor.setText("Visa");
			ft.clearCell(row+1, 0);
		}
	}

	@Override
	protected void pageLoaded(Object result) {
			BetalningList bl=(BetalningList)result;
			for (BetalningRow br : bl.rader) {
				final int finalRow=currentRow;
				final int finalFaktnr = br.faktnr;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalFaktnr, finalRow );
					}
				});
				Anchor pdfAnchor = new Anchor("Pdf", "getdoc?doctype=faktura&docid=" + br.faktnr, "_BLANK");
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setWidget(currentRow, 1, pdfAnchor);
				ft.setText(currentRow, 2, globalData.numberFormatInt.format(br.faktnr));
				ft.setText(currentRow, 3, globalData.getDateString(br.betdat));
				ft.setText(currentRow, 4, (globalData.numberFormat.format(br.summa)));
				ft.setWidget(currentRow, 5, new Label(br.betsatt));
				ftCellFormatter.addStyleName(currentRow, 2, globalData.STYLE_TD_IDNR);
				if (currentRowHighlite ) {
					ftRowFormatter.addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
				}
				ft.getFlexCellFormatter().setColSpan((currentRow+1), 0, 6);
				currentRow = currentRow+2;
				currentRowHighlite=!currentRowHighlite;
			}
			if (bl.hasMoreRows) {
				visaFlerRaderAnchor.setVisible(true);
			}

			nextRow=bl.nextRow;
	}

}
