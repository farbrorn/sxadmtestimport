/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import se.saljex.SxShop.client.rpcobject.OrderHeader;
import se.saljex.SxShop.client.rpcobject.OrderHeaderList;

/**
 *
 * @author ulf
 */
public class OrderListaWidget extends SxWidget {
	private FlexTable ft = new FlexTable();
	private ColumnFormatter ftColFormatter = ft.getColumnFormatter();
	private CellFormatter ftCellFormatter = ft.getCellFormatter();
	private RowFormatter ftRowFormatter = ft.getRowFormatter();

	public OrderListaWidget(GlobalData globalData, String headerText) {
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
		ft.setWidget(0, 1, new Label("Ordernr"));
		ft.setWidget(0, 2, new Label("Datum"));
		ft.setWidget(0, 3, new Label("Status"));
		ft.setWidget(0, 4, new Label("Märke"));
		ft.setWidget(0, 5, new Label(" "));
		ftRowFormatter.addStyleName(0, globalData.STYLE_TR_RUBRIK);
		globalData.service.getOrderHeaders(callbackLoadPage);

	}

	@Override
	protected void pageLoaded(Object result) {
		OrderHeaderList ohl=(OrderHeaderList)result;
		if (ohl.rader==null) {
			setError("Det finns inga order");
		} else {
			clearError();
			for (OrderHeader oh : ohl.rader) {
				final int finalRow=currentRow;
				final OrderHeader finalOrderHeader = oh;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalOrderHeader, finalRow );
					}
				});
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setText(currentRow, 1, globalData.numberFormatInt.format(oh.ordernr));
				ft.setText(currentRow, 2, globalData.getDateString(oh.datum));
				ft.setWidget(currentRow, 3, new Label(oh.status));
				ft.setWidget(currentRow, 4, new Label(oh.marke));
				if (oh.isDeletable) {
					final int finalOrdernr = oh.ordernr;
					PushButton btn = new PushButton(new Image(globalData.IMG_BTN_TRASH));
					btn.setStylePrimaryName(globalData.STYLE_PUSHBUTTON);
					btn.setTitle("Annullera");
					btn.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							clickDeleteOrder(finalOrdernr);
						}
					});
					ft.setWidget(currentRow, 5, btn);
					ftCellFormatter.addStyleName(currentRow, 5, globalData.STYLE_TD_TABORT);
				}
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

	private void clickDeleteOrder(final int ordernr) {
		new SxPopUpPanel("Annullera?", new Label("Vill du annullera ordern?"), true, true, new OkAvbrytHandler() {
			public void onOk() {
				globalData.service.deleteOrder(ordernr, callbackDeleteOrder);
			}

			public void onAvbryt() {
			}
		});
		
	}

	AsyncCallback callbackDeleteOrder = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			setError(caught.getMessage());
		}

		public void onSuccess(Object result) {
			clearError();
			fillFt();
		}
	};

	
	private void visaClick(Anchor visaAnchor, OrderHeader orderHeader, int row) {
		clearError();
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			OrderInfoWidget oi = new OrderInfoWidget(globalData, orderHeader.ordernr);
			oi.addStyleName(globalData.STYLE_DROPDOWNWIDGET);
			ft.setWidget(row+1, 0, oi);
		} else {
			visaAnchor.setText("Visa");
			ft.clearCell(row+1, 0);
		}
	}

}
