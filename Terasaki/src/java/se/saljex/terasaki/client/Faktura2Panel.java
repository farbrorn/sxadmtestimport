/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author Ulf
 */
public class Faktura2Panel extends VerticalPanel{
	private FlexTable ft;
	private int faktnr;

	public Faktura2Panel(int faktnr) {
		super();
		this.faktnr=faktnr;
		doLoad();
	}

	private void doLoad() {
		((GWTServiceAsync)GWT.create(GWTService.class)).getFaktura2(faktnr, callback);
	}

	final AsyncCallback<Faktura2List> callback = new AsyncCallback<Faktura2List>() {
		public void onSuccess(Faktura2List result) {
			doFillFaktura2(result);
		}

		public void onFailure(Throwable caught) {
			doFillError(caught);
		}
	};

	private void doFillError(Throwable caught) {
		add(new Label("Serverfel: " + caught.getMessage() + caught.toString()));
	}


	private void doFillFaktura2(Faktura2List f) {
		ft = new FlexTable();
		add(ft);
		int cn=0;
		boolean odd=true;

		ft.setText(0, 0, "Item code");
		ft.setText(0, 1, "Item");
		ft.setText(0, 2, "Qty");
		ft.setText(0, 3, "Price");
		ft.setText(0, 4, "%");
		ft.getCellFormatter().addStyleName(0, 2, "sx-tb-n10");
		ft.getCellFormatter().addStyleName(0, 3, "sx-tb-n10");
		ft.getCellFormatter().addStyleName(0, 4, "sx-tb-n5");
		ft.getRowFormatter().addStyleName(0, "sx-table-rubrik");


		for (Faktura2 f2 : f.rader) {
			cn++;
			odd=!odd;
			ft.setText(cn, 0, f2.artnr);
			ft.setText(cn, 1, f2.namn);
			ft.setText(cn, 2, Util.fmt0.format(f2.antal));
			ft.setText(cn, 3, Util.fmt0.format(f2.pris));
			ft.setText(cn, 4, Util.fmt0.format(f2.rab));

			ft.getCellFormatter().addStyleName(cn, 2, "sx-tb-n10");
			ft.getCellFormatter().addStyleName(cn, 3, "sx-tb-n10");
			ft.getCellFormatter().addStyleName(cn, 4, "sx-tb-n5");
			if (odd) ft.getRowFormatter().addStyleName(cn, "sx-bg-odd");

		}


	}


}
