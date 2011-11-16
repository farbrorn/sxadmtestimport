/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import java.util.List;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.SqlSelectParameters;
import se.saljex.webadm.client.rpcobject.Utlev1;

/**
 *
 * @author Ulf
 */
public class OverforUtlevWidget extends ListWidget<Utlev1>{

	HasData2Form<Utlev1> formUpdat;

	public OverforUtlevWidget() {
		super(null, new PageLoad<Utlev1>(new Utlev1(), 30, 100, 1000, null) ,null);
		SqlSelectParameters s = new SqlSelectParameters();
		s.addOrderByParameter("faktnr", SQLTableList.SORT_DESCANDING);
		setSearch(s);
	}

	@Override
	void addListColumns(CellTable<Utlev1> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S10);
		getCellTable().addColumnStyleName(1, Const.Style_S10);
		getCellTable().addColumnStyleName(2, Const.Style_S10);
		getCellTable().addColumnStyleName(3, Const.Style_S10);

		cellTable.addColumn(new SxNumberColumn<Utlev1>(Util.fmt0Dec) {@Override public Number getValue(Utlev1 object) {return object.ordernr;}}
							, "Order");

		cellTable.addColumn(new TextColumn<Utlev1>() {@Override public String getValue(Utlev1 object) {return Util.formatDate(object.datum);}}
							, "Orderdatum");
		cellTable.addColumn(new TextColumn<Utlev1>() {@Override public String getValue(Utlev1 object) {return object.status;}}
							, "Status");


		ActionCell<Utlev1> ac = new ActionCell<Utlev1>("Överför", new ActionCell.Delegate<Utlev1>() {
			@Override
			public void execute(Utlev1 object) {
				if (Const.ORDER_STATUS_OVERFORD.equals(object.status)) {
					ModalMessage.show("Ordern är redan överförd");
				} else {
					Util.showModalWait();
					Util.getService().overforOrder(object.ordernr, Util.getAnvandareKort(), (short)0, overforCallback);
				}
			}
		});
		cellTable.addColumn(new Column<Utlev1, Utlev1>(ac) {
			@Override
			public Utlev1 getValue(Utlev1 object) {
			return object;}
			@Override
			public void render(Utlev1 u, ProvidesKey p , SafeHtmlBuilder h) {
				if (!Const.ORDER_STATUS_OVERFORD.equals(u.status)) {
					super.render(u, p, h);
				}
			}

		});

		cellTable.addColumn(new TextColumn<Utlev1>() {@Override public String getValue(Utlev1 object) {return object.namn;}}
							, "Kund");
		cellTable.addColumn(new TextColumn<Utlev1>() {@Override public String getValue(Utlev1 object) {return object.marke;}}
							, "Märke");

	}

	private AsyncCallback<Integer> overforCallback = new AsyncCallback<Integer>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.hideModalWait();
			ModalMessage.show("Fel vid överföring: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Integer result) {
			Util.hideModalWait();
			updateRow(result);
		}
	};

	private void updateRow(Integer ordernr) {
		if (ordernr!=null) {
			ListDataProvider<Utlev1> liProvider = this.getListDataProvider();
			List<Utlev1> list = liProvider.getList();
			for (Utlev1 u : list) {
				if (ordernr.equals(u.ordernr)) {
					u.status =  Const.ORDER_STATUS_OVERFORD;
					break;
				}
			}
			liProvider.refresh();
		}
	}

}
