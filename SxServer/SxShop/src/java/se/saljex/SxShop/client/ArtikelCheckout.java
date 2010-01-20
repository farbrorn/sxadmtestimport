/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.NotLoggedInException;
import se.saljex.SxShop.client.rpcobject.SxShopKreditSparrException;

/**
 *
 * @author ulf
 */
public class ArtikelCheckout extends VerticalPanel {
	final GlobalData globalData;
	ArtikelPanel artikelPanel;
	Button skickaOrderButton=new Button("Skicka order", new ClickHandler() {
		public void onClick(ClickEvent event) {
			skickaOrder();
		}
	});
	TextBox markeTextBox = new TextBox();



	public ArtikelCheckout(final GlobalData globalData, ArtikelPanel artikelPanel) {
		this.globalData = globalData;
		this.artikelPanel = artikelPanel;
		Label rubrik = new Label("Skicka order");
		rubrik.addStyleName("sx-huvudrubrik");
		add(rubrik);
		add(new Label("Fyll i önskat godsmärke och klicka på 'Skicka order'"));
		Grid inputGrid = new Grid(1, 2);
		inputGrid.setWidget(0, 0, new Label("Godsmärke:"));
		inputGrid.setWidget(0, 1, markeTextBox);
		add(inputGrid);
		add(skickaOrderButton);
	}

	public void skickaOrder() {
		artikelPanel.vantaDialogBox.show();
		globalData.service.skickaOrder(markeTextBox.getValue(), callbackSkickaOrder);
	}

	final AsyncCallback callbackSkickaOrder = new AsyncCallback() {
		public void onSuccess(Object result) {
			artikelPanel.vantaDialogBox.hide();
			if (result==null) {
				artikelPanel.fillWidget(new Label("Det finns inga artiklar i varukorgen."));
			} else {
				ArrayList<Integer> order = (ArrayList<Integer>)result;
				VerticalPanel vp=new VerticalPanel();
				vp.add(new Label("Sparad på ordernummer:"));
				for (Integer i : order) {
					vp.add(new Label(i.toString()));
				}
				vp.add(new Label("Tack för din order!"));
				artikelPanel.fillWidget(vp);
				artikelPanel.updateVarukorg(null);
			}
		}

		public void onFailure(Throwable caught) {
			artikelPanel.vantaDialogBox.hide();
			try {
				throw (caught);
			} catch (SxShopKreditSparrException ke) {
				artikelPanel.fillWidget(new Label("Kan inte registera order då kreditgränsen äröverstigen eller det finns förfallna fakturor."));
			} catch (NotLoggedInException le) {
				artikelPanel.fillWidget(new Label("Du är inte inloggad."));
			} catch (Throwable e) {
				artikelPanel.fillWidget(new Label("Kunde inte registrera order. " + caught.toString()));
			}
			
		}
	};

}
