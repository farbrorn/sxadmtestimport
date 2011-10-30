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
import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public abstract class InfoSheetWidget<T extends IsSQLTable> extends TabLayoutPanel implements HasData2Form<T>{

	TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2, Unit.EM);
	T currTableRow=null;

	protected  PageLoad<T> pageLoad;
	public InfoSheetWidget() {
		this(null);
	}

	public InfoSheetWidget(PageLoad<T> pageLoad) {
		super(2, Unit.EM);
		this.pageLoad = pageLoad;

		setup();
		this.addSelectionHandler(new SelectionHandler<Integer>() {
					@Override
					public void onSelection(SelectionEvent<Integer> event) {
						updateTab(event.getSelectedItem(), currTableRow);
					}
		})	;

	}

	protected abstract void setup();

	public final void addData2FormWidget(Widget widget, HasData2Form<T> data2FormWidget, String title) {
		ScrollContainer<T> scrollContainer = new ScrollContainer<T>(widget, data2FormWidget);
		add(scrollContainer, title);
	}

	@Override
	public void data2Form(T data) {
		currTableRow = data;
		updateTab(this.getSelectedIndex(), data);
	}

	private void updateTab(int index, T data) {
		//if (this.getTabWidget(index) instanceof ScrollContainer) {

			((ScrollContainer<T>)this.getWidget(index)).data2Form(data);
		//}

	}

	class ScrollContainer<T extends IsSQLTable> extends ScrollPanel  {
		public T row=null;
		private HasData2Form<T> data2FormWidget;

		public ScrollContainer(Widget widget, HasData2Form<T> data2FormWidget) {
			super(widget);
			this.data2FormWidget = data2FormWidget;
		}

		public void data2Form(T row) {
			if (this.row!=row) {
				if (data2FormWidget!=null) data2FormWidget.data2Form(row);
				this.row = row;
			}
		}


	}


}
