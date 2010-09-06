/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;

/**
 *
 * @author ulf
 */
public class OverforOrderPanel extends VerticalPanel{
	FlexTable orderTable = new FlexTable();
	DockPanel splitPanel = new DockPanel();
	boolean remoteServiceBlocked=false;

	HorizontalPanel filterPanel = new HorizontalPanel();

	VerticalPanel rightPanel = new VerticalPanel();

  public final static int COL_ORDERNR=0;
  public final static int COL_LEVORDERNR=1;
  public final static int COL_DATUM=2;
  public final static int COL_KUNDNAMN=3;
  public final static int COL_STATUS=4;
  public final static int COL_SUMMAINKMOMS=5;
  public final static int COL_BTN_OVERFOR=6;
  public final static int COL_BTN_INFO=7;
  public final static int COL_BTN_FORSKATT=8;

	public static final String RADIOFILTERGROUP = "ft";
	private final RadioButton rbFilterAlla = new RadioButton(RADIOFILTERGROUP, "Alla");
	private final RadioButton rbFilterForOverforing = new RadioButton(RADIOFILTERGROUP, "För överföring");
	private final RadioButton rbFilterAvvaktande = new RadioButton(RADIOFILTERGROUP, "Avvaktande");
	private final RadioButton rbFilterOverfforda = new RadioButton(RADIOFILTERGROUP, "Överförda");
	private final RadioButton rbFilterForskott = new RadioButton(RADIOFILTERGROUP, "Förskott");

	private LogInWidget loginWidget;
	ClickHandler clickHandlerGetOrder1List = new ClickHandler() {
		public void onClick(ClickEvent event) {
			startGetOrder1List();
		}
	};

	private final Button btnRefreshOrder1 = new Button("Uppdatera", clickHandlerGetOrder1List );

	public final NumberFormat numberFormat = NumberFormat.getFormat("0.00");
	public final NumberFormat numberFormatInt = NumberFormat.getFormat("0");

	private Label errorLabel = new Label("Inga fel");



	public OverforOrderPanel() {
		rbFilterAlla.setValue(true);
		filterPanel.add(new Label("Filtrera: "));
		rbFilterAlla.addClickHandler(clickHandlerGetOrder1List);
		rbFilterAvvaktande.addClickHandler(clickHandlerGetOrder1List);
		rbFilterForOverforing.addClickHandler(clickHandlerGetOrder1List);
		rbFilterForskott.addClickHandler(clickHandlerGetOrder1List);
		rbFilterOverfforda.addClickHandler(clickHandlerGetOrder1List);
		filterPanel.add(rbFilterAlla);
		filterPanel.add(rbFilterForskott);
		filterPanel.add(rbFilterForOverforing);
		filterPanel.add(rbFilterOverfforda);
		filterPanel.add(rbFilterAvvaktande);
		
		errorLabel.getElement().getStyle().setProperty("color", "red");
		VerticalPanel leftPanel = new VerticalPanel();
		leftPanel.add(errorLabel);
		leftPanel.add(filterPanel);
		leftPanel.add(btnRefreshOrder1);
		leftPanel.add(orderTable);
		splitPanel.add(leftPanel, DockPanel.WEST);

		rightPanel.add(new Label("Info"));
		splitPanel.add(rightPanel, DockPanel.EAST);

		add(splitPanel);
//		add(leftPanel);
		startGetOrder1List();
	}

	private void setErrorLabel(String err) { errorLabel.setText(err);	}
	private void clearErrorLabel() { errorLabel.setText(""); }

	private void startGetOrder1List() {
		if (remoteServiceBlocked) return;
		remoteServiceBlocked=true;
		orderTable.removeAllRows();
		int filter;
		if (rbFilterAvvaktande.getValue()) filter=GWTService.FILTER_AVVAKTANDE;
		else if (rbFilterForOverforing.getValue()) filter=GWTService.FILTER_FOROVERFORING;
		else if (rbFilterForskott.getValue()) filter=GWTService.FILTER_FORSKOTT;
		else if (rbFilterOverfforda.getValue()) filter=GWTService.FILTER_OVERFORDA;
		else filter=GWTService.FILTER_ALLA;
		getService().getBvOrder1List(filter, callbackOrder1List);
	}

	final AsyncCallback<Order1List> callbackOrder1List = new AsyncCallback<Order1List>() {
		public void onSuccess(Order1List result) {
			clearErrorLabel();
			fillOrderTable(result);
			remoteServiceBlocked=false;
		}

		public void onFailure(Throwable caught) {
			remoteServiceBlocked=false;
			setErrorLabel("Fel: " + caught.getMessage());
			loginWidget = new LogInWidget();
			loginWidget.show();
		}
  };

	final AsyncCallback<OverforBVOrderResp> callbackOverforBvOrder = new AsyncCallback<OverforBVOrderResp>() {
		public void onSuccess(OverforBVOrderResp result) {
			clearErrorLabel();
			if (result.overfordOK) {
				Integer ordernr=null;
				try {
					ordernr = ((SxIntegerLabel)orderTable.getWidget(result.callerId, COL_ORDERNR)).getIntValue();
				} catch ( Exception e) {}
				if (ordernr!=null && ordernr.equals(result.bvOrdernr)) {
					orderTable.setWidget(result.callerId, COL_STATUS, new Label("Överf"));
					orderTable.clearCell(result.callerId, COL_BTN_OVERFOR);
					orderTable.setWidget(result.callerId, COL_LEVORDERNR, new SxIntegerLabel(result.sxOrdernr));
					remoteServiceBlocked=false;
				} else {
					setErrorLabel("Order saknas i listan men överförd ok");
				}
			} else {
				setErrorLabel("Fel vid överföringen: " + result.error);
			}
		}

		public void onFailure(Throwable caught) {
			remoteServiceBlocked=false;
			setErrorLabel("Fel Överför order: " + caught.getMessage());
		}
  };


	private void fillOrderTable(Order1List order1List) {
		orderTable.removeAllRows();
		orderTable.setWidget(0, COL_ORDERNR, new Label("Ordernr"));
		orderTable.setWidget(0, COL_LEVORDERNR, new Label("Lev Ordernr"));
		orderTable.setWidget(0, COL_DATUM, new Label("Datum"));
		orderTable.setWidget(0, COL_KUNDNAMN, new Label("Kund"));
		orderTable.setWidget(0, COL_STATUS, new Label("Status"));
		orderTable.setWidget(0, COL_SUMMAINKMOMS, new Label("Summa ink. moms"));
		int row=1;
		for (Order1 order1 : order1List.orderLista) {
			orderTable.setWidget(row, COL_ORDERNR, new SxIntegerLabel(order1.ordernr));
			orderTable.setWidget(row, COL_LEVORDERNR, new SxIntegerLabel(null));
			orderTable.setWidget(row, COL_DATUM, new Label(""+getDateString(order1.datum)));
			orderTable.setWidget(row, COL_KUNDNAMN, new Label(order1.namn));
			orderTable.setWidget(row, COL_STATUS, new Label(order1.status));
			orderTable.setWidget(row, COL_SUMMAINKMOMS, new Label(numberFormat.format(order1.summaInkMoms)));
			if (order1.isOverforbar) {
				final Button btnOverfor = new Button("Överför");
				final int ordernr=order1.ordernr;
				final int btnRow = row;
				btnOverfor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						doBtnOverfor(btnOverfor, ordernr, btnRow);
					}
				});
				orderTable.setWidget(btnRow, COL_BTN_OVERFOR, btnOverfor);
			}


//			if (order1.forskatt && !order1.forskattBetalt) {
			if (true) {
				final Button btnForskatt = new Button("Förskott");
				final int ordernr = order1.ordernr;
				final double summaInkMoms = order1.summaInkMoms;
				final int btnRow  = row;
				btnForskatt.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						doBtnForskatt(btnForskatt, ordernr, summaInkMoms, btnRow);
					}
				});
				orderTable.setWidget(btnRow, COL_BTN_FORSKATT, btnForskatt);
			}
			final Button btnInfo = new Button("Info");
			final int ordernr=order1.ordernr;
			btnInfo.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					doBtnInfo(ordernr);
				}
			});
			orderTable.setWidget(row, COL_BTN_INFO, btnInfo);
			row++;

		}
	}

	private void doBtnForskatt(Button btn, int ordernr, double belopp, int row) {
		BetalaForskatPopUp forskattPopUp = new BetalaForskatPopUp(ordernr, belopp);
		forskattPopUp.show();
	}

	private void doBtnOverfor(Button btn, int ordernr, int row) {
		if (remoteServiceBlocked) return;
		btn.setEnabled(false);
		remoteServiceBlocked=true;
		getService().overforBVOrder(ordernr, (short)0, row,  callbackOverforBvOrder);
	}
	private void doBtnInfo(int ordernr) {
		getService().getBvOrderLookup(ordernr, callbackInfo);
	}
	final AsyncCallback<OrderLookupResp> callbackInfo = new AsyncCallback<OrderLookupResp>() {
		public void onSuccess(OrderLookupResp result) {
			Label infoLabel;
			clearErrorLabel();
			if (result.errorMessage==null) {
				rightPanel.clear();
				if (result.bvOrder!=null) {
					infoLabel = new Label("Vår order");
					infoLabel.getElement().getStyle().setProperty("color", "blue");
					rightPanel.add(infoLabel);
					if (result.bvOrder.order1!=null) {
						FlexTable bvOrderHeaderTable = new FlexTable();
						bvOrderHeaderTable.setWidget(0, 0, new Label("Ordernr: "+result.bvOrder.order1.ordernr));
						bvOrderHeaderTable.setWidget(0, 1, new Label("Datum: "+getDateString(result.bvOrder.order1.datum)));
						bvOrderHeaderTable.setWidget(1, 0, new Label("Kund: "+result.bvOrder.order1.kundnr));
						bvOrderHeaderTable.setWidget(1, 1, new Label(result.bvOrder.order1.namn));
						rightPanel.add(bvOrderHeaderTable);
					}
					if (result.bvOrder.order2List!=null) {
						FlexTable bvOrder2Table = new FlexTable();
						int row=0;
						for (Order2 o2 : result.bvOrder.order2List) {
							bvOrder2Table.setWidget(row, 0, new Label(o2.artnr));
							bvOrder2Table.setWidget(row, 1, new Label(o2.namn));
							bvOrder2Table.setWidget(row, 2, new Label(numberFormat.format(o2.antal)));
							bvOrder2Table.setWidget(row, 3, new Label(o2.enh));
							bvOrder2Table.setWidget(row, 4, new Label(numberFormat.format(o2.pris)));
							bvOrder2Table.setWidget(row, 5, new Label(numberFormat.format(o2.rab)));
							bvOrder2Table.setWidget(row, 6, new Label(numberFormat.format(o2.lagerTillgangliga)));
							bvOrder2Table.setWidget(row, 7, new Label(numberFormat.format(o2.lagerTillgangligaFilialer)));
							row++;
						}
						rightPanel.add(bvOrder2Table);
					}
					infoLabel = new Label("Händelser");
					infoLabel.getElement().getStyle().setProperty("color", "blue");
					rightPanel.add(infoLabel);
					if (result.bvOrder.orderHandList!=null) {
						FlexTable table = new FlexTable();
						int row=0;
						for(OrderHand orderHand : result.bvOrder.orderHandList) {
							table.setWidget(row, 0, new Label(getDateTimeString(orderHand.datum)));
							table.setWidget(row, 1, new Label(orderHand.anvandare));
							table.setWidget(row, 2, new Label (orderHand.handelse));
							table.setWidget(row, 3, new Label(orderHand.transportor));
							table.setWidget(row, 4, new Label(orderHand.fraktsedelnr));
							row++;
						}
						rightPanel.add(table);
					}
				}

				infoLabel = new Label("Leverantörs order");
				infoLabel.getElement().getStyle().setProperty("color", "blue");
				rightPanel.add(infoLabel);
				if (result.sxOrderList!=null) {
					for(Order order : result.sxOrderList) {
						if(order.order1!=null) {
							FlexTable bvOrderHeaderTable = new FlexTable();
							bvOrderHeaderTable.setWidget(0, 0, new Label("Ordernr: "+order.order1.ordernr));
							bvOrderHeaderTable.setWidget(0, 1, new Label("Datum: "+getDateString(order.order1.datum)));
							bvOrderHeaderTable.setWidget(1, 0, new Label("Kund: "+order.order1.kundnr));
							bvOrderHeaderTable.setWidget(1, 1, new Label(order.order1.namn));
							rightPanel.add(bvOrderHeaderTable);
						}
						if (order.order2List!=null) {
							FlexTable bvOrder2Table = new FlexTable();
							int row=0;
							for (Order2 o2 : order.order2List) {
								bvOrder2Table.setWidget(row, 0, new Label(o2.artnr));
								bvOrder2Table.setWidget(row, 1, new Label(o2.namn));
								bvOrder2Table.setWidget(row, 2, new Label(numberFormat.format(o2.antal)));
								bvOrder2Table.setWidget(row, 3, new Label(o2.enh));
								bvOrder2Table.setWidget(row, 4, new Label(numberFormat.format(o2.pris)));
								bvOrder2Table.setWidget(row, 5, new Label(numberFormat.format(o2.rab)));
								bvOrder2Table.setWidget(row, 6, new Label(numberFormat.format(o2.lagerTillgangliga)));
								bvOrder2Table.setWidget(row, 7, new Label(numberFormat.format(o2.lagerTillgangligaFilialer)));
								row++;
							}
							rightPanel.add(bvOrder2Table);
						}
						infoLabel = new Label("Händelser");
						infoLabel.getElement().getStyle().setProperty("color", "blue");
						rightPanel.add(infoLabel);
						if (order.orderHandList!=null) {
							FlexTable table = new FlexTable();
							int row=0;
							for(OrderHand orderHand : order.orderHandList) {
								table.setWidget(row, 0, new Label(getDateTimeString(orderHand.datum)));
								table.setWidget(row, 1, new Label(orderHand.anvandare));
								table.setWidget(row, 2, new Label (orderHand.handelse));
								table.setWidget(row, 3, new Label(orderHand.transportor));
								table.setWidget(row, 4, new Label(orderHand.fraktsedelnr));
								row++;
							}
							rightPanel.add(table);
						}
					}
				}
			} else {
				setErrorLabel("Fel vid hämta orderinfo: " + result.errorMessage);
			}
		}
		public void onFailure(Throwable caught) {
			setErrorLabel("Fel vid hämta orderinfo: " + caught.getMessage());
		}
	};

	private String getDateTimeString(java.util.Date date) {
		return DateTimeFormat.getFormat("yyy-MM-dd HH:mm:ss").format(date);
	}
	private String getDateString(java.util.Date date) {
		return DateTimeFormat.getFormat("yyy-MM-dd").format(date);
	}
	public static GWTServiceAsync getService() {
		return GWT.create(GWTService.class);
	}


}
