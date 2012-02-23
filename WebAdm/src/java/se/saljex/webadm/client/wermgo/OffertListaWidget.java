/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.wermgo;

import se.saljex.webadm.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.HtmlMail;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

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


//	SendEpostButton  epostBtn = new SendEpostButtonOffert(MainEntryPoint.getInitialData().inloggadAnvandare.anvandareKort, new SendEpostInterface() {
//		@Override	public String getKundnr() {	return o1.getSelectedObject().kundnr;	}
//		@Override	public Integer getId() { return o1.getSelectedObject().offertnr;		}
//	});
	Button epostBtn = new Button("E-Post med Wermgo inkl moms", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			String url = Util.getApiUrlPath() + "getoffert?offertnr=" + o1.getSelectedObject().offertnr +
					"&inkmoms=true" +
					"&logourl=" + URL.encode("http://www.wermgo.se/_/rsrc/1317744750781/config/customLogo.gif?revision=1") +
					"&headerhtml=" + URL.encode("<p style=\"font-weight: bold; margin-bottom: 1em;\">Hej</p><p style=\"margin-bottom: 2em;\">Här kommer din Wermgooffert!</p>") +
					"&meddelandehtml=" + URL.encodeQueryString("Förslag till materiel. Fraktkostnad tillkommer.") +
					"&footerhtml=" + URL.encode("");
			Window.open(url, "_blank", null);
			
/*			Util.getService().getHtmlOffert(o1.getSelectedObject().offertnr, true, "http://www.wermgo.se/_/rsrc/1317744750781/config/customLogo.gif?revision=1", 
						"<p style=\"font-weight: bold; margin-bottom: 1em;\">Hej</p><p style=\"margin-bottom: 2em;\">Här kommer din Wermgooffert!</p>", "Materielet i offerten är endast ett förslag. Fraktkostnad tillkommer.", "Med vänlig hälsning<br>Wermgo AB", new AsyncCallback<HtmlMail>() {

				@Override
				public void onFailure(Throwable caught) {
					Util.showModalMessage("Fel: "  + caught.getMessage());
				}

				@Override
				public void onSuccess(HtmlMail result) {
					Util.showModalMessage(new HTML(result.html));
				}
			});
			* */
		}
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

//private static native String getURL() /*-{
//return $wnd.location.href;
//}-*/;


}
