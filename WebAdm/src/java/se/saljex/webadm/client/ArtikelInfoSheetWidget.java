/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.rpcobject.Artikel;

/**
 *
 * @author Ulf
 */
public class ArtikelInfoSheetWidget extends TabLayoutPanel implements HasData2Form<Artikel>{

	TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2, Unit.EM);
	Artikel currTableRow=null;
	ArtikelFormWidget formWidget;
//	OffertListaWidget offertListWidget = new OffertListaWidget(false);
//	OrderListaWidget orderListWidget = new OrderListaWidget(false);

	private PageLoad<Artikel> pageLoad;
	public ArtikelInfoSheetWidget() {
		this(null);
	}

	public ArtikelInfoSheetWidget(PageLoad<Artikel> pageLoad) {
		super(2, Unit.EM);
		this.pageLoad = pageLoad;
		formWidget = new ArtikelFormWidget(pageLoad);
		addData2FormWidget(formWidget, formWidget, "Artikelinfo");
//		addData2FormWidget(orderListWidget, orderListWidget, "Order");
//		addData2FormWidget(offertListWidget, offertListWidget, "Offerter");

		this.addSelectionHandler(new SelectionHandler<Integer>() {
					@Override
					public void onSelection(SelectionEvent<Integer> event) {
						updateTab(event.getSelectedItem(), currTableRow);
					}
		})	;

	}


	public final void addData2FormWidget(Widget widget, HasData2Form<Artikel> data2FormWidget, String title) {
		add(new ScrollContainer(widget, data2FormWidget), title);
	}

	@Override
	public void data2Form(Artikel data) {
		currTableRow = data;
		updateTab(this.getSelectedIndex(), data);
	}

	private void updateTab(int index, Artikel data) {
		//if (this.getTabWidget(index) instanceof ScrollContainer) {

			((ScrollContainer)this.getWidget(index)).data2Form(data);
		//}

	}

	class ScrollContainer extends ScrollPanel  {
		public Artikel row=null;
		private HasData2Form<Artikel> data2FormWidget;

		public ScrollContainer(Widget widget, HasData2Form<Artikel> data2FormWidget) {
			super(widget);
			this.data2FormWidget = data2FormWidget;
		}

		public void data2Form(Artikel row) {
			if (this.row!=row) {
				if (data2FormWidget!=null) data2FormWidget.data2Form(row);
				this.row = row;
			}
		}


	}


}
