/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class WindowMainPanel extends VerticalPanel{

	final HorizontalPanel tabPanel = new HorizontalPanel();
	TabTitle currentTabTitle = null;
	Widget currentWindowWidget = null;


	public WindowMainPanel() {
		super();
		tabPanel.addStyleName("w-tabpanel");
		add(tabPanel);
		addWindow(new Label("Flik1"), "Fliken 1");
		addWindow(new Label("Flik2"), "Fliken 2");
		addWindow(new Label("Flik3"), "Fliken 3");

	}

	public void addWindow(final Widget widget, String title) {
		WindowHolder window = new WindowHolder(widget, title);
		tabPanel.add(window.getTabTitle());
		setCurrentWindowWidget(widget, window.getTabTitle());
	}

	private void setCurrentWindowWidget(Widget widget, TabTitle tabTitle) {
		if (currentWindowWidget != null) { remove(currentWindowWidget); }
		if (currentTabTitle != null) { currentTabTitle.removeStyleDependentName("selected"); }
		this.currentWindowWidget = widget;
		this.currentTabTitle = tabTitle;
		add(this.currentWindowWidget);
	}

	public void closeWindow(Widget widget, TabTitle tabTitle) {
		if (currentWindowWidget != null) { remove(currentWindowWidget); currentWindowWidget=null; }
		currentTabTitle = null;
		int noTabs = tabPanel.getWidgetCount();
		int index = tabPanel.getWidgetIndex(tabTitle);
		if (index >= 0) {
			tabPanel.remove(tabTitle);
			if (noTabs > 1) {
				if (index > noTabs-2) index=noTabs-2;
			}
			TabTitle t = (TabTitle)tabPanel.getWidget(index);
			setCurrentWindowWidget(t.getWindowholder().getWindowWidget(), t);
		}

	}

	class WindowHolder {
		final private Widget windowWidget;
		final private Anchor closeBtn = new Anchor("X");
		final private Anchor titleAnchor;
		final private TabTitle tabTitle;

		public WindowHolder(final Widget widget, String title) {
			this.windowWidget = widget;
			titleAnchor = new Anchor(title);
			titleAnchor.setStylePrimaryName("w-tabtitle");
			tabTitle = new TabTitle(this);
			titleAnchor.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					setCurrentWindowWidget(widget, tabTitle);
					titleAnchor.setStyleDependentName("selected", true);
				}
			});
			closeBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					closeWindow(widget, tabTitle);
					titleAnchor.setStyleDependentName("selected", true);
				}
			});
		}

		public Anchor getCloseBtn() {
			return closeBtn;
		}

		public TabTitle getTabTitle() {
			return tabTitle;
		}

		public Anchor getTitleAnchor() {
			return titleAnchor;
		}

		public Widget getWindowWidget() {
			return windowWidget;
		}


	}

	class TabTitle extends HorizontalPanel{
		final Anchor titleAnchor;
		final Anchor closeAnchor;
		final WindowHolder windowholder;
		public TabTitle(WindowHolder windowHolder) {
			this.titleAnchor = windowHolder.getTitleAnchor();
			this.closeAnchor = windowHolder.getCloseBtn();
			this.windowholder = windowHolder;
			add(titleAnchor);
			add(closeAnchor);
		}

		public Anchor getCloseAnchor() {
			return closeAnchor;
		}

		public Anchor getTitleAnchor() {
			return titleAnchor;
		}

		public WindowHolder getWindowholder() {
			return windowholder;
		}


	}


}
