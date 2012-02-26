/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import java.util.List;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.*;
import se.saljex.webadm.client.common.rpcobject.Order1Utlev1Combo;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;

/**
 *
 * @author Ulf
 */
public class OverforOrderWidget<T extends Order1Utlev1Combo> extends ListWidget<T>{

	HasData2Form<T> formUpdat;



	MessagePopupPanel msg = new MessagePopupPanel(true);
	Order2ListWidget order2ListWidget= new Order2ListWidget();
	OrderInfoWidget orderInfoWidget = new OrderInfoWidget();
	FlowPanel orderInfoPanel = new FlowPanel();
	ScrollPanel orderInfoScroll = new ScrollPanel(orderInfoWidget);
	ScrollPanel orderListScroll = new ScrollPanel(order2ListWidget);

	public OverforOrderWidget(T tableObject) {
		super(null, new PageLoad<T>(tableObject, 30, 100, 1000, null) ,null);
		SqlSelectParameters s = new SqlSelectParameters();
		s.addOrderByParameter("ordernr", SQLTableList.SORT_DESCANDING);
		setSearch(s);
		orderInfoPanel.add(orderInfoScroll);
		orderInfoPanel.add(orderListScroll);
		orderInfoPanel.setWidth("65em");
		orderListScroll.addStyleName(Const.Style_Margin_1em_Top);

		orderInfoPanel.setHeight(Window.getClientHeight()*0.8 + "px");
		orderInfoScroll.setHeight("40%");
		orderInfoScroll.setWidth("65em");
		orderListScroll.setHeight("50%");
		orderListScroll.setWidth("65em");
	}

	@Override
	public void addListColumns(CellTable<T> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S10);
		getCellTable().addColumnStyleName(3, Const.Style_S10);

		cellTable.addColumn(new SxNumberColumn<T>(Util.fmt0Dec) {@Override public Number getValue(T object) {return object.ordernr;}}
							, "Order");

		cellTable.addColumn(new TextColumn<T>() {@Override public String getValue(T object) {return Util.formatDate(object.datum);}}
							, "Orderdatum");
		cellTable.addColumn(new TextColumn<T>() {@Override public String getValue(T object) {return object.status;}}
							, "Status");


		ActionCell<T> ac = new ActionCell<T>("Överför", new ActionCell.Delegate<T>() {
			@Override
			public void execute(T object) {
				if (Const.ORDER_STATUS_OVERFORD.equals(object.status)) {
					Util.showModalMessage("Ordern är redan överförd");
				} else {
					final T objectFinal = object;
					msg.showWithOkAvbryt("Överför order " + object.ordernr + "?", new MessagePopuCallback() {
						@Override
						public void btnClicked(int btnId) {
							if (btnId==0) doOverfor(objectFinal);
						}
					});
				}
			}
		});

		cellTable.addColumn(new Column<T, T>(ac) {
			@Override
			public T getValue(T object) {
				return object;
			}
			@Override
			public void render(T u, ProvidesKey<T> p , SafeHtmlBuilder h) {
				if (!Const.ORDER_STATUS_OVERFORD.equals(u.status)) {
					super.render(u, p, h);
				}
			}
		});


		ActionCell<T> acInfo = new ActionCell<T>("Info", new ActionCell.Delegate<T>() {
			@Override
			public void execute(T object) {
//					final T objectFinal = object;
					order2ListWidget.loadOrderNr(object.ordernr);
					orderInfoWidget.showOrder(object.ordernr);
					msg.showWidgetWithOkAvbryt(orderInfoPanel);
			}
		});

		cellTable.addColumn(new Column<T, T>(acInfo) {
			@Override
			public T getValue(T object) {
				return object;
			}
			@Override
			public void render(T u, ProvidesKey<T> p , SafeHtmlBuilder h) {
				super.render(u, p, h);
			}
		});


		cellTable.addColumn(new TextColumn<T>() {@Override public String getValue(T object) {return object.namn;}}
							, "Kund");
		cellTable.addColumn(new TextColumn<T>() {@Override public String getValue(T object) {return object.marke;}}
							, "Märke");

	}

	private AsyncCallback<Integer> overforCallback = new AsyncCallback<Integer>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.hideModalWait();
			Util.showModalMessage("Fel vid överföring: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Integer result) {
			Util.hideModalWait();
			updateRow(result);
		}
	};

	private void doOverfor(T object) {
//		Util.showModalMessage("Order " + object==null ? "null" : ""+object.ordernr);
		Util.showModalWait();
		Util.getService().overforOrder(((T)object).ordernr, Util.getAnvandareKort(), (short)0, overforCallback);
	}

	private void updateRow(Integer ordernr) {
		if (ordernr!=null) {
			ListDataProvider<T> liProvider = this.getListDataProvider();
			List<T> list = liProvider.getList();
			for (T u : list) {
				if (ordernr.equals(u.ordernr)) {
					u.status =  Const.ORDER_STATUS_OVERFORD;
					break;
				}
			}
			liProvider.refresh();
		}
	}

}
