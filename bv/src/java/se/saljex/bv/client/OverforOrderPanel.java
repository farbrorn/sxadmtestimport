/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author ulf
 */
public class OverforOrderPanel extends VerticalPanel{
	FlexTable orderTable = new FlexTable();

	public OverforOrderPanel() {
		add(orderTable);
		getService().getOrder1ListForOverforing(callbackOrder1List);
	}


	final AsyncCallback<Order1List> callbackOrder1List = new AsyncCallback<Order1List>() {
		public void onSuccess(Order1List result) {
			fillOrderTable(result);
		}

		public void onFailure(Throwable caught) {
			add( new Label("Fel: " + caught.getMessage()));
		}
  };

	final AsyncCallback<OverforBVOrderResponse> callbackOverforBvOrder = new AsyncCallback<OverforBVOrderResponse>() {
		public void onSuccess(OverforBVOrderResponse result) {
			add (new Label("Överfört order " + result.bvOrdernr + " till Säljex order " + result.sxOrdernr));
		}

		public void onFailure(Throwable caught) {
			add( new Label("Fel Överför order: " + caught.getMessage()));
		}
  };


	private void fillOrderTable(Order1List order1List) {
		orderTable.removeAllRows();
		orderTable.setWidget(0, 0, new Label("kolumn"));
		int row=1;
		for (Order1 order1 : order1List.orderLista) {
			orderTable.setWidget(row, 0, new Label(""+order1.ordernr));
			orderTable.setWidget(row, 1, new Label(order1.kundnr));
			orderTable.setWidget(row, 2, new Label(order1.Status));
			final Button btnOverfor = new Button("Överför");
			final int ordernr=order1.ordernr;
			final int btnRow = row;
			btnOverfor.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					doBtnOverfor(btnOverfor, ordernr, btnRow);
				}
			});
			orderTable.setWidget(btnRow, 3, btnOverfor);
			row++;

		}
	}

	private void doBtnOverfor(Button btn, int ordernr, int row) {
		btn.setEnabled(false);
		orderTable.clearCell(row, 2);
		getService().overforBVOrder(ordernr, (short)0, callbackOverforBvOrder);
		orderTable.setWidget(row, 2, new Label("Överförd"));
	}

	public static GWTServiceAsync getService() {
		return GWT.create(GWTService.class);
	}


}
