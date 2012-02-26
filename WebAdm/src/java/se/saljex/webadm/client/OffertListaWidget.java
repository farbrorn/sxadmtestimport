/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.SendEpostButton;
import se.saljex.webadm.client.common.SendEpostButtonOffert;
import se.saljex.webadm.client.common.SendEpostInterface;
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
public class OffertListaWidget extends FlowPanel implements HasData2Form<Kund>{
	Offert2ListWidget o2 = new Offert2ListWidget();
	Offert1ListWidget o1 = new Offert1ListWidget(o2);

	FlowPanel o2Widget = new FlowPanel();



	FlowPanel sokPanel;
	Label sokLabel;
	TextBox sokInput;
	String previousSok=null;


	SendEpostButton  epostBtn = new SendEpostButtonOffert(MainEntryPoint.getInitialData().inloggadAnvandare.anvandareKort, new SendEpostInterface() {
		@Override	public String getKundnr() {	return o1.getSelectedObject().kundnr;	}
		@Override	public Integer getId() { return o1.getSelectedObject().offertnr;		}
	});

	public OffertListaWidget() {
		this(true, false);
	}
	public OffertListaWidget(boolean loadInitialData) {
		this(loadInitialData, false);
	}

	public OffertListaWidget(boolean loadInitialData, boolean showSok) {
		if(loadInitialData) o1.setSearch("kundnr", "0", "offertnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);

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


		o2Widget.add(epostBtn);
		o2Widget.add(o2);

		add(o1);
		add(o2Widget);

	}

	private void sok() {
		if (Util.isEmpty(sokInput.getText())) o1.setSearch("kundnr", "0", "offertnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);
		else o1.getPageLoad().setSearch("offertnr", sokInput.getText(), "offertnr", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_DESCANDING);
	}

	@Override
	public void data2Form(Kund data) {
		setSearch("kundnr", data.nummer, "offertnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}



	protected Offert1ListWidget getO1() { return o1; }

	public void setSearch(String sokField, String sokString, String sortField, int sokTyp, int sortOrder) {
		o2.getPageLoad().getBufferList().clear();	//Rensar innehåll så det inte visas felaktig vid uppdatering av O1 utan sökresultat
		o1.getPageLoad().setSearch(sokField, sokString, sortField, sokTyp, sortOrder);
	}


}
