/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author Ulf
 */
public class KundInfoPanel extends VerticalPanel{
	private FlexTable ft;
	private String kundnr;
	private Label rubrik;

	public KundInfoPanel(String kundnr) {
		super();
		this.kundnr=kundnr;
		add (new Label(kundnr));
		doLoad();
	}

	private void doLoad() {
		((GWTServiceAsync)GWT.create(GWTService.class)).getKundInfo(kundnr, callback);
	}

	final AsyncCallback<KundInfo> callback = new AsyncCallback<KundInfo>() {
		public void onSuccess(KundInfo result) {
			doFillOrder(result);
			doFillFaktura(result);
		}

		public void onFailure(Throwable caught) {
			doFillError(caught);
		}
	};

	private void doFillError(Throwable caught) {
		add(new Label("Serverfel: " + caught.getMessage() + caught.toString()));
	}

	private void doFillOrder(KundInfo k) {
		rubrik = new Label("Orders");
		rubrik.addStyleName("sx-bold");
		add(rubrik);
		ft = new FlexTable();
		add(ft);
		int cn=0;
		boolean odd=true;
		ft.getCellFormatter().addStyleName(0, 0, "sx-tb-s5");
		ft.setText(0, 0, "Order#");
		ft.getCellFormatter().addStyleName(0, 1, "sx-tb-s10");
		ft.setText(0, 1, "Date");
		ft.getCellFormatter().addStyleName(0, 2, "sx-tb-s30");
		ft.setText(0, 2, "Item#");
		ft.getCellFormatter().addStyleName(0, 3, "sx-tb-n5");
		ft.setText(0, 3, "Item");
		ft.getCellFormatter().addStyleName(0, 4, "sx-tb-n5");
		ft.setText(0, 4, "Qty");
		ft.getCellFormatter().addStyleName(0, 5, "sx-tb-n5");
		ft.setText(0, 5, "Price");
		ft.getCellFormatter().addStyleName(0, 6, "sx-tb-n5");
		ft.setText(0, 6, "%");
		ft.getRowFormatter().addStyleName(0, "sx-table-rubrik");


		for (KundOrder ko : k.orderRader.order) {
			cn++;
			odd=!odd;
			ft.setText(cn, 0, ""+ko.ordernr);
			ft.setText(cn, 1, DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(ko.datum));
			ft.setText(cn, 2, ko.artnr);
			ft.setText(cn, 3, ko.artnamn);
			ft.setText(cn, 4, ""+ko.antal);
			ft.setText(cn, 5, Util.fmt2.format(ko.pris));
			ft.setText(cn, 6, Util.fmt2.format(ko.rab));

			ft.getCellFormatter().addStyleName(cn, 3, "sx-tb-n5");
			ft.getCellFormatter().addStyleName(cn, 4, "sx-tb-n5");
			ft.getCellFormatter().addStyleName(cn, 5, "sx-tb-n5");
			ft.getCellFormatter().addStyleName(cn, 6, "sx-tb-n5");
			if (odd) ft.getRowFormatter().addStyleName(cn, "sx-bg-odd");
		}
	}



	private void doFillFaktura(KundInfo k) {
		rubrik = new Label("Invoice");
		rubrik.addStyleName("sx-bold");
		add(rubrik);
		ft = new FlexTable();
		add(ft);
		int cn=0;
		boolean odd=true;
		ft.getCellFormatter().addStyleName(0, 1, "sx-tb-s30");
		ft.setText(0, 1, "Invoice#");
		ft.getCellFormatter().addStyleName(0, 2, "sx-tb-s30");
		ft.setText(0, 2, "Date");
		ft.getCellFormatter().addStyleName(0, 3, "sx-tb-s30");
		ft.setText(0, 3, "Invoice total");
		ft.getRowFormatter().addStyleName(0, "sx-table-rubrik");

		Anchor expandAnchor;

		for (KundFaktura1 ko : k.fakturor.fakturor) {
			cn++;
			odd=!odd;

			expandAnchor = new Anchor("Info");
			final int finalCn = cn;
			final int finalFaktnr=ko.faktnr;
			expandAnchor.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					showFaktura2(finalCn,finalFaktnr);
				}
			});

			ft.setWidget(cn, 0, expandAnchor);

			ft.setText(cn, 1, ""+ko.faktnr);
			ft.setText(cn, 2, DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(ko.datum));
			ft.setText(cn, 3, Util.fmt2.format(ko.t_attebtala));

			ft.getCellFormatter().addStyleName(cn, 3, "sx-tb-n10");
			if (odd) ft.getRowFormatter().addStyleName(cn, "sx-bg-odd");

			cn++;
			ft.getFlexCellFormatter().setColSpan(cn, 0, 4);

		}
		if (k.fakturor.flerFakturorFinns) {
			add(new Label("There are more invoices, but only " + k.fakturor.maxAntalFakturor + " are shown."));
		}


	}

	private void showFaktura2(final int row, final int faktnr) {
		if (ft.getWidget(row+1, 0) == null) {
			ft.setWidget(row+1, 0, new Faktura2Panel(faktnr));
		} else {
			ft.clearCell(row+1, 0);
		}
	}



}
