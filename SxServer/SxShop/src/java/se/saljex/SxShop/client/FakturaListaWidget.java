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
import se.saljex.SxShop.client.rpcobject.FakturaHeader;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderList;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderOrderMarke;

/**
 *
 * @author ulf
 */
public class FakturaListaWidget extends SxWidget {
	private FlexTable ft = new FlexTable();
	private int nextRow=0;
	private ColumnFormatter ftColFormatter = ft.getColumnFormatter();
	private CellFormatter ftCellFormatter = ft.getCellFormatter();
	private RowFormatter ftRowFormatter = ft.getRowFormatter();

	public FakturaListaWidget(GlobalData globalData, String headerText) {
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
		ft.setWidget(0, 3, new Label("Datum"));
		ft.setWidget(0, 4, new Label("Belopp"));
		ft.setWidget(0, 5, new Label("Order/Märke"));
		ftRowFormatter.addStyleName(0, globalData.STYLE_TR_RUBRIK);
		loadNextPage();
	}

	@Override
	protected void loadNextPage() {
			globalData.service.getFakturaHeaders(nextRow, pageSize, super.callbackLoadPage);
	}


	private void visaClick(Anchor visaAnchor, FakturaHeader fakturaHeader, int row) {
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			FakturaInfoWidget fi = new FakturaInfoWidget(globalData, fakturaHeader.faktnr);
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
			FakturaHeaderList fhl=(FakturaHeaderList)result;
			for (FakturaHeader fh : fhl.rader) {
				final int finalRow=currentRow;
				final FakturaHeader finalFakturaHeader = fh;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalFakturaHeader, finalRow );
					}
				});
				Anchor pdfAnchor = new Anchor("Pdf", "getdoc?doctype=faktura&docid=" + fh.faktnr, "_BLANK");
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setWidget(currentRow, 1, pdfAnchor);
				ft.setText(currentRow, 2, globalData.numberFormatInt.format(fh.faktnr));
				ft.setText(currentRow, 3, globalData.getDateString(fh.datum));
				ft.setText(currentRow, 4, (globalData.numberFormat.format(fh.t_attbetala)));
				if (currentRowHighlite ) {
					ftRowFormatter.addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
//					ftRowFormatter.addStyleName(currentRow+1, globalData.STYLE_TR_ODDROW);
				}
				//ftRowFormatter.addStyleName(currentRow+1, globalData.STYLE_TR_DROPDOWNWIDGET);
				ftCellFormatter.addStyleName(currentRow, 2, globalData.STYLE_TD_IDNR);
				int oCn=0;
				FlexTable fto = new FlexTable();
				fto.getColumnFormatter().addStyleName(0, globalData.STYLE_TD_IDNR);
				fto.getColumnFormatter().addStyleName(1, globalData.STYLE_TD_MARKE);
				for (FakturaHeaderOrderMarke fo : fh.orderMarken) {
					fto.setText(oCn, 0, fo.ordernr+"");
					fto.setText(oCn, 1, fo.marke);
					oCn++;
				}
				ft.setWidget(currentRow, 5, fto);
				ft.getFlexCellFormatter().setColSpan((currentRow+1), 0, 6);
//				ft.getCellFormatter().setStylePrimaryName(currentRow+1, 0, globalData.STYLE_TD_DROPDOWNWIDGET);
//				ft.getRowFormatter().addStyleName(currentRow+1, globalData.STYLE_TD_DROPDOWNWIDGET);
				//ftRowFormatter.setStylePrimaryName(currentRow+1, globalData.STYLE_TD_DROPDOWNWIDGET);
				currentRow = currentRow+2;
				currentRowHighlite=!currentRowHighlite;
			}
			if (fhl.hasMoreRows) {
				visaFlerRaderAnchor.setVisible(true);
			}

			nextRow=fhl.nextRow;

	}

}
