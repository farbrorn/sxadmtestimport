/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class KundBrowserWithSokWidget extends FlowPanel {
	KundBrowserWidget browseWidget;
	FlowPanel sokPanel = new FlowPanel();
	Label sokLabel = new Label("Sök:");
	TextBox sokInput = new TextBox();
	String previousSok=null;

	Button sokBtn = new Button("Sök", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			sok();
		}
	});

	public KundBrowserWithSokWidget(HasFormUpdater<Kund> formUpdat) {
		super();


		sokInput.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.isDownArrow() || event.isUpArrow()) {
					browseWidget.getCellTable().setFocus(true);
				} else	sok();
			}
		});
		browseWidget = new KundBrowserWidget(formUpdat);
		//browseWidget.setHeight("100%");

		sokPanel.add(sokLabel);
		sokPanel.add(sokInput);
		sokPanel.add(sokBtn);
		sokLabel.addStyleName("sx-float-left");
		sokPanel.setHeight("6%");
		browseWidget.setHeight("94%");
		add(sokPanel);
		add(browseWidget);
	}

	public KundBrowserWidget getBrowserWidget() { return browseWidget; }
	
	private void sok() {
		browseWidget.getPageLoad().setSearch("nummer", sokInput.getText(), "namn", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_ASCENDING);
	}


}

