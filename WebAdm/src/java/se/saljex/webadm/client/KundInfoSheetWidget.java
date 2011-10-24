/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundInfoSheetWidget extends TabLayoutPanel implements HasData2Form<Kund>{

	TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2, Unit.EM);
	Kund currKund=null;
	KundFormWidget kundForm;
	KundresListWidget kundresListWidget = new KundresListWidget(null, null);
	OffertListaWidget offertListWidget = new OffertListaWidget(false);
	OrderListaWidget orderListWidget = new OrderListaWidget(false);
	KundStatistikWidget kundStatistikWidget = new KundStatistikWidget();
	FakturaListWidget fakturaListWidget = new FakturaListWidget(false);
	UtlevListWidget utlevListWidget = new UtlevListWidget(false);

	private PageLoad<Kund> pageLoad;
	public KundInfoSheetWidget() {
		this(null);
	}

	public KundInfoSheetWidget(PageLoad<Kund> pageLoad) {
		super(2, Unit.EM);
		this.pageLoad = pageLoad;
		kundForm = new KundFormWidget(pageLoad);
		addData2FormWidget(kundForm, kundForm, "Kundinfo");
		addData2FormWidget(kundresListWidget, kundresListWidget, "Reskontra");
		addData2FormWidget(orderListWidget, orderListWidget, "Order");
		addData2FormWidget(offertListWidget, offertListWidget, "Offerter");
		addData2FormWidget(fakturaListWidget, fakturaListWidget, "Fakturor");
		addData2FormWidget(utlevListWidget, utlevListWidget, "Utleveranser");
		addData2FormWidget(kundStatistikWidget, kundStatistikWidget, "Statistik");

		this.addSelectionHandler(new SelectionHandler<Integer>() {
					@Override
					public void onSelection(SelectionEvent<Integer> event) {
						updateTab(event.getSelectedItem(), currKund);
					}
		})	;

	}


	public final void addData2FormWidget(Widget widget, HasData2Form<Kund> data2FormWidget, String title) {
		add(new ScrollContainer(widget, data2FormWidget), title);
	}

	@Override
	public void data2Form(Kund data) {
		currKund = data;
		updateTab(this.getSelectedIndex(), data);

//		kundForm.data2Form(data);
//		offertListWidget.setSearch("kundnr", data.nummer, "offertnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
//		kundresListWidget.getPageLoad().setSearch("kundnr", data.nummer, "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
//		kundStatistikWidget.setKund(data.nummer);
	}

	private void updateTab(int index, Kund data) {
		//if (this.getTabWidget(index) instanceof ScrollContainer) {

			((ScrollContainer)this.getWidget(index)).data2Form(data);
		//}

	}

	class ScrollContainer extends ScrollPanel  {
		public Kund kund=null;
		private HasData2Form<Kund> data2FormWidget;

		public ScrollContainer(Widget widget, HasData2Form<Kund> data2FormWidget) {
			super(widget);
			this.data2FormWidget = data2FormWidget;
		}

		public void data2Form(Kund kund) {
			if (this.kund!=kund) {
				if (data2FormWidget!=null) data2FormWidget.data2Form(kund);
				this.kund = kund;
			}
		}

		
	}


}
