/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.HasFormUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import se.saljex.webadm.client.common.rpcobject.Artikel;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class ArtikelBrowserWithSokWidget extends FlowPanel {
	ArtikelBrowserWidget browseWidget;
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

	public ArtikelBrowserWithSokWidget(HasFormUpdater<Artikel> formUpdat) {
		super();


		sokInput.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.isDownArrow() || event.isUpArrow()) {
					browseWidget.getCellTable().setFocus(true);
				} else	sok();
			}
		});
		browseWidget = new ArtikelBrowserWidget(formUpdat);
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

	public ArtikelBrowserWidget getBrowserWidget() { return browseWidget; }

	private void sok() {
		browseWidget.getPageLoad().setSearch("nummer", sokInput.getText(), "nummer", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_ASCENDING);
	}


}

