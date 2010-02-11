/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.OrderInfo;
import se.saljex.SxShop.client.rpcobject.OrderRow;

/**
 *
 * @author ulf
 */
public class OrderInfoWidget extends VerticalPanel{
		final Label errorLabel = new Label();
		final GlobalData globalData;
		VerticalPanel raderPanel = new VerticalPanel();
		private int ordernr;
		public OrderInfoWidget(final GlobalData globalData, int ordernr) {
			errorLabel.addStyleName(globalData.STYLE_ERROR);
			errorLabel.setVisible(false);
			this.globalData=globalData;
			this.ordernr=ordernr;
			add(errorLabel);
			add(raderPanel);
			globalData.service.getOrderInfo(ordernr, callbackOrderInfo);
			setSpacing(0);
		}

		AsyncCallback callbackOrderInfo  = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			setError( caught.getMessage());
		}

		public void onSuccess(Object result) {
			OrderInfo orderInfo = (OrderInfo)result;
			if (orderInfo==null) {
				setRaderWidget(null);
				setError("Angiven order saknas.");
			} else {
				if (orderInfo.rows!=null) setRaderWidget(getRaderWidget(orderInfo.rows));
			}
		}
	};

	private void setRaderWidget(Widget w) {
		raderPanel.clear();
		raderPanel.add(w);
	}

	private Widget getRaderWidget(ArrayList<OrderRow> a) {
		FlexTable ft = new FlexTable();
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		FlexTable.FlexCellFormatter cf = ft.getFlexCellFormatter();
		int row = 1;
		ft.getCellFormatter().addStyleName(0, 0, globalData.STYLE_TD_ARTNR);
		ft.getCellFormatter().addStyleName(0, 1, globalData.STYLE_TD_BENAMNING);
		ft.getCellFormatter().addStyleName(0, 2, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 3, globalData.STYLE_TD_ENHET);
		ft.getCellFormatter().addStyleName(0, 4, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 5, globalData.STYLE_TD_RAB);
		ft.getCellFormatter().addStyleName(0, 6, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0, 7, globalData.STYLE_TD_MANGD);
		ft.getCellFormatter().addStyleName(0, 8, globalData.STYLE_TD_TABORT);
		ft.getCellFormatter().addStyleName(0, 9, globalData.STYLE_TD_TABORT);
		ft.setText(0, 0, "Art.Nr");
		ft.setText(0, 1, "Benämning");
		ft.setText(0, 2, "Antal");
		ft.setText(0, 3, "Enh");
		ft.setText(0, 4, "Pris");
		ft.setText(0, 5, "%");
		ft.setText(0, 6, "Tillgängliga");
		ft.setText(0, 7, " ");
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

		for (OrderRow r : a) {
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
				ft.setText(row, 6, globalData.numberFormat.format(r.tillgangligtAntal));
				if (r.isChangeable) {
					final TextBox nyttAntal = new TextBox();
					final int finalOrdernr = r.ordernr;
					final int finalPos = r.pos;
					nyttAntal.setText(globalData.numberFormat.format(r.antal));
					nyttAntal.addStyleName(globalData.STYLE_ANTALTEXTBOX);
					ft.setWidget(row, 7, nyttAntal);

					PushButton btnTick = new PushButton(new Image(globalData.IMG_BTN_TICK));
					btnTick.setStylePrimaryName(globalData.STYLE_PUSHBUTTON);
					btnTick.setTitle("Ändra antal");
					btnTick.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							clickChangeRow(finalOrdernr, finalPos, nyttAntal.getText());
						}
					});
					ft.setWidget(row, 8, btnTick);

					PushButton btnTrash = new PushButton(new Image(globalData.IMG_BTN_TRASH));
					btnTrash.setStylePrimaryName(globalData.STYLE_PUSHBUTTON);
					btnTrash.setTitle("Ta bort rad");
					btnTrash.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							new SxPopUpPanel("Annullera?", new Label("Vill du annullera ordern?"), true, true, new OkAvbrytHandler() {
								public void onOk() {
									clickChangeRow(finalOrdernr, finalPos, "0");
								}

								public void onAvbryt() {	}
							});
						}
					});
					ft.setWidget(row, 9, btnTrash);
				}
			}
			row++;
		}
		return ft;
	}

	public void clickChangeRow(int ordernr, int pos, String nyttAntal) {
		globalData.service.changeOrderRow(ordernr, pos, nyttAntal, callbackAndraAntal);
	}

	AsyncCallback callbackAndraAntal = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			setError(caught.toString());
		}

		public void onSuccess(Object result) {
			globalData.service.getOrderInfo(ordernr, callbackOrderInfo);
			clearError();
		}
	};

	private void setError(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}
	private void clearError() {
		errorLabel.setVisible(false);
	}
}
