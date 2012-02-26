/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.SendEpostButton;
import se.saljex.webadm.client.common.SendEpostInterface;
import se.saljex.webadm.client.common.SendEpostButtonFaktura;
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
public class FakturaListWidget extends FlowPanel implements HasData2Form<Kund>{
	Faktura2ListWidget o2 = new Faktura2ListWidget();
	Faktura1ListWidget o1 = new Faktura1ListWidget(o2);

	FlowPanel o2Widget = new FlowPanel();

	FlowPanel sokPanel;
	Label sokLabel;
	TextBox sokInput;
	String previousSok=null;


	SendEpostButton  epostBtn = new SendEpostButtonFaktura(MainEntryPoint.getInitialData().inloggadAnvandare.anvandareKort, new SendEpostInterface() {
		@Override	public String getKundnr() {	return o1.getSelectedObject().kundnr;	}
		@Override	public Integer getId() { return o1.getSelectedObject().faktnr;		}
	});

	public FakturaListWidget() {
		this(true, false);
	}
	public FakturaListWidget(boolean loadInitialData) {
		this(loadInitialData, false);
	}

	public FakturaListWidget(boolean loadInitialData, boolean showSok) {
		if(loadInitialData) o1.setSearch("kundnr", "0", "faktnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);

		this.setHeight("100%");
		o1.setHeight("45%");
		o2.setHeight("45%");
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
		add(epostBtn);
		add(o2Widget);

	}

	private void sok() {
		if (Util.isEmpty(sokInput.getText())) o1.setSearch("kundnr", "0", "faktnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);
		else o1.getPageLoad().setSearch("faktnr", sokInput.getText(), "faktnr", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_DESCANDING);
	}

	@Override
	public void data2Form(Kund data) {
		setSearch("kundnr", data.nummer, "faktnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_DESCANDING);
	}

	public void setSearch(String sokField, String sokString, String sortField, int sokTyp, int sortOrder) {
		o2.getPageLoad().getBufferList().clear();	//Rensar innehåll så det inte visas felaktig vid uppdatering av O1 utan sökresultat
		o1.getPageLoad().setSearch(sokField, sokString, sortField, sokTyp, sortOrder);
	}


}
