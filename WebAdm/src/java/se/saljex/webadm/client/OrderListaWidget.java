/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.KundEpostSelectWidget;
import se.saljex.webadm.client.common.MessagePopupPanel;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.rpcobject.Kund;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class OrderListaWidget extends FlowPanel implements HasData2Form<Kund>{
	Order2ListWidget o2 = new Order2ListWidget();
	Order1ListWidget o1 = new Order1ListWidget(o2);

	FlowPanel o2Widget = new FlowPanel();

	MessagePopupPanel messagePanel = new MessagePopupPanel(true);

	KundEpostSelectWidget epostSelect;

	FlowPanel sokPanel;
	Label sokLabel;
	TextBox sokInput;
	String previousSok=null;

/*
	Button epostBtn = new Button("Sänd som e-post", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			showEpostSelect();
		}
	});
*/
	Button btnInfo = new Button("Info", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			showInfo();
		}
	});


	public OrderListaWidget() {
		this(true, false);
	}
	public OrderListaWidget(boolean loadInitialData) {
		this(loadInitialData, false);
	}

	public OrderListaWidget(boolean loadInitialData, boolean showSok) {
		if(loadInitialData) o1.setSearch("kundnr", "0", "ordernr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);

		this.setHeight("100%");
		o1.setHeight("40%");
		o2.setHeight("40%");
		o1.setWidth("65em");
		o2.setWidth("65em");
		o1.addStyleName(Const.Style_BoxedScroll);
		o2.addStyleName(Const.Style_BoxedScroll);

		if (showSok) {
			sokPanel = new FlowPanel();
			sokLabel = new Label("Sök:");
			sokInput = new TextBox();
			Button sokBtn = new Button("Sök", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					sok();
				}
			});
			sokInput.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.isDownArrow() || event.isUpArrow()) {
						o1.getCellTable().setFocus(true);
					} else	sok();
				}
			});
			sokPanel.add(sokLabel);
			sokPanel.add(sokInput);
			sokPanel.add(sokBtn);
			sokLabel.addStyleName("sx-float-left");
			add(sokPanel);
		}


		o2Widget.add(o2);
//		o2Widget.add(epostBtn);

		add(o1);
		add(o2Widget);
		add(btnInfo);
	}

	private void sok() {
		if (Util.isEmpty(sokInput.getText())) o1.setSearch("kundnr", "0", "ordernr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);
		else o1.getPageLoad().setSearch("ordernr", sokInput.getText(), "ordernr", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_DESCANDING);
	}

	@Override
	public void data2Form(Kund data) {
		setSearch("kundnr", data.nummer, "ordernr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}


/*	private void showEpostSelect() {

		epostSelect = new KundEpostSelectWidget(o1.getSelectedObject().kundnr, new SendOffertEpostHandler("00", o1.getSelectedObject().offertnr, messagePanel), new HasRequestCompleted() {

			@Override
			public void requestCompleted(String info) {		}

			@Override
			public void requestCancelled(String info) {				}
		});

		epostSelect.center();
		epostSelect.show();
	}
*/
	protected Order1ListWidget getO1() { return o1; }

	private void showInfo() {
		MessagePopupPanel m = new MessagePopupPanel(true);
		m.setHeight("70%");
		OrderInfoWidget oi = new OrderInfoWidget();
		oi.showOrder(o1.getSelectedObject());
		m.showWidget(oi.getWidget());
	}

	public void setSearch(String sokField, String sokString, String sortField, int sokTyp, int sortOrder) {
		o2.getPageLoad().getBufferList().clear();	//Rensar innehåll så det inte visas felaktig vid uppdatering av O1 utan sökresultat
		o1.getPageLoad().setSearch(sokField, sokString, sortField, sokTyp, sortOrder);
	}


}
