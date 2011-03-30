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
public class ArtikelInfoPanel extends VerticalPanel{
	private FlexTable ft;
	private String artnr;
	private Label rubrik;

	public ArtikelInfoPanel(String artnr) {
		super();
		this.artnr=artnr;
		add (new Label(artnr));
		doLoad();
	}

	private void doLoad() {
		((GWTServiceAsync)GWT.create(GWTService.class)).getArtikelInfo(artnr, callback);
	}

	final AsyncCallback<ArtikelInfo> callback = new AsyncCallback<ArtikelInfo>() {
		public void onSuccess(ArtikelInfo result) {
			doFillOrder(result);
			doFillBest(result);
			doFillStatistik(result);
		}

		public void onFailure(Throwable caught) {
			doFillError(caught);
		}
	};

	private void doFillError(Throwable caught) {
		add(new Label("Serverfel: " + caught.getMessage() + caught.toString()));
	}

	private void doFillOrder(ArtikelInfo a) {

		rubrik = new Label("Customer orders");
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
		ft.setText(0, 2, "Customer");
		ft.getCellFormatter().addStyleName(0, 3, "sx-tb-n5");
		ft.setText(0, 3, "Qty");
		ft.getRowFormatter().addStyleName(0, "sx-table-rubrik");

		for (ArtikelOrder ao : a.artikelOrderList) {
			cn++;
			odd=!odd;
			ft.setText(cn, 0, ""+ao.ordernr);
			ft.setText(cn, 1, DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(ao.datum));
			ft.setText(cn, 2, ao.kundnamn);
			ft.setText(cn, 3, ""+ao.antal);
			ft.getCellFormatter().addStyleName(cn, 3, "sx-tb-n5");
		}


	}

	private void doFillBest(ArtikelInfo a) {
		rubrik = new Label("Purchase orders");
		rubrik.addStyleName("sx-bold");
		add(rubrik);
		ft = new FlexTable();
		add(ft);
		int cn=0;
		boolean odd=true;
		ft.getCellFormatter().addStyleName(0, 0, "sx-tb-s5");
		ft.setText(0, 0, "P/O");
		ft.getCellFormatter().addStyleName(0, 0, "sx-tb-s10");
		ft.setText(0, 1, "Date");
		ft.getCellFormatter().addStyleName(0, 0, "sx-tb-n5");
		ft.setText(0, 2, "Qty");
		ft.getRowFormatter().addStyleName(0, "sx-table-rubrik");

		for (ArtikelBest ao : a.artikelBestList) {
			cn++;
			odd=!odd;
			ft.setText(cn, 0, ""+ao.bestnr);
			ft.setText(cn, 1, DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT).format(ao.datum));
			ft.setText(cn, 2, ""+ao.antal);
			ft.getCellFormatter().addStyleName(cn, 2, "sx-tb-n5");
			if (odd) ft.getRowFormatter().addStyleName(cn, "sx-bg-odd");
		}

	}

	private void doFillStatistik(ArtikelInfo a) {
		rubrik = new Label("Statistics");
		rubrik.addStyleName("sx-bold");
		add(rubrik);
		add(new Label("Sales qty / month"));
		ft = new FlexTable();
		add(ft);
		int cn=0;
		final int manStartCol=2;
		boolean odd=true;
		cn = manStartCol;
		ft.getCellFormatter().addStyleName(0, 0, "sx-tb-n5");
		ft.setText(0, 0, "Year");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Jan");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Feb");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Mar");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Apr");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "May");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Jun");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Jul");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Aug");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Sep");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Oct");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Nov");
		ft.getCellFormatter().addStyleName(0, cn, "sx-tb-n5");
		ft.setText(0, cn++, "Dec");
		ft.getRowFormatter().addStyleName(0, "sx-table-rubrik");

		cn=1;
		for (ArtikelStatistik ao : a.artikelStatistikList) {
			odd=!odd;
			ft.setText(cn, 0, ""+ao.ar);
			ft.setText(cn, 1, "Qty");
			ft.setText(cn+1, 1, "Sales value");
			for (ArtikelStatistikDetalj ad : ao.artikelStatistikDetalj) {
				if (ad.man >0 && ad.man <=12) {
					ft.setText(cn, manStartCol+ad.man-1, ""+ad.antal);
					ft.setText(cn+1, manStartCol+ad.man-1, Util.fmt0.format(ad.summa));
					ft.getCellFormatter().addStyleName(cn, manStartCol+ad.man-1, "sx-tb-n5");
					ft.getCellFormatter().addStyleName(cn+1, manStartCol+ad.man-1, "sx-tb-n5");
				}
			}
			if (odd) {
				ft.getRowFormatter().addStyleName(cn, "sx-bg-odd");
				ft.getRowFormatter().addStyleName(cn+1, "sx-bg-odd");
			}
			cn=cn+2;
		}

	}

}
