/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

/**
 *
 * @author Ulf
 */
public class ArtikelPanel extends VerticalPanel{
	private final Label errorLabel = new Label();
	protected final FlexTable table = new FlexTable();
	private final RowFormatter rowFormatter = table.getRowFormatter();
	private final CellFormatter cellFormatter = table.getCellFormatter();
	private final FlexTable.FlexCellFormatter flexCellFormatter = table.getFlexCellFormatter();
	private final Label hArtnr = new Label("Code");
	private final Label hNamn = new Label("Name");
	private final Label hBestnr = new Label("Order code");
	private final Label hEnhet = new Label("Unit");
	private final Label hUtpris = new Label("Price");
	private final Label hIlager = new Label("In stock");
	private final Label hBestpunkt = new Label("Min stock");
	private final Label hMaxlager = new Label("Max stock");
	private final Label hIorder = new Label("Cust order");
	private final Label hBest = new Label("Purchase order");

	private static final int cExpand=0;
	private static final int cArtnr=1;
	private static final int cNamn=cArtnr+1;
	private static final int cUtpris=cNamn+1;
	private static final int cIlager=cUtpris+1;
	private static final int cBestpunkt=cIlager+1;
	private static final int cMaxlager=cBestpunkt+1;
	private static final int cIorder=cMaxlager+1;
	private static final int cBest=cIorder+1;


	public ArtikelPanel() {
		super();
		add(errorLabel);
		add(table);
		table.addStyleName("sx-table-list");

		doLoadArtikel();
	}


	public void loadArtikel() {
		doLoadArtikel();
	}

	private void doLoadArtikel() {
		clearError();
		initTable();
		((GWTServiceAsync)GWT.create(GWTService.class)).getArtikelList(callback);
	}

	private void initTable() {
		table.setVisible(false);
		table.removeAllRows();
		table.setWidget(0, cArtnr, hArtnr);
		table.setWidget(0, cNamn, hNamn);
		table.setWidget(0, cUtpris, hUtpris);
		table.setWidget(0, cIlager, hIlager);
		table.setWidget(0, cBestpunkt, hBestpunkt);
		table.setWidget(0, cMaxlager, hMaxlager);
		table.setWidget(0, cIorder, hIorder);
		table.setWidget(0, cBest, hBest);
		cellFormatter.addStyleName(0, cArtnr, "sx-tb-s15");
		cellFormatter.addStyleName(0, cNamn, "sx-tb-s30");
		cellFormatter.addStyleName(0, cUtpris, "sx-tb-n10");
		cellFormatter.addStyleName(0, cIlager, "sx-tb-n10");
		cellFormatter.addStyleName(0, cBestpunkt, "sx-tb-n10");
		cellFormatter.addStyleName(0, cMaxlager, "sx-tb-n10");
		cellFormatter.addStyleName(0, cIorder, "sx-tb-n10");
		cellFormatter.addStyleName(0, cBest, "sx-tb-n10");
		rowFormatter.addStyleName(0, "sx-table-rubrik");
	}

	final AsyncCallback<ArtikelList> callback = new AsyncCallback<ArtikelList>() {
		public void onSuccess(ArtikelList result) {
			doFillTable(result);
		}

		public void onFailure(Throwable caught) {
			setError("Serverfel: " + caught.getMessage());
		}
	};

	private void showInfo(final int row, final String artnr) {
		if (table.getWidget(row+1, 0) == null) { 
			table.setWidget(row+1, 0, new ArtikelInfoPanel(artnr));
//			table.setWidget(row+1, 0, new Label("sss"));
		} else {
			table.clearCell(row+1, 0);
		}
	}

	private void doFillTable(ArtikelList artikelList) {
		int cn=0;
		boolean odd=true;
		Anchor expandAnchor;
		for (Artikel a : artikelList.artiklar) {
			cn++;
			odd=!odd;
			expandAnchor = new Anchor("Info");
			final int finalCn = cn;
			final String finalArtnr=a.artnr;
			expandAnchor.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					showInfo(finalCn,finalArtnr);
				}
			});

			table.setWidget(cn, cExpand, expandAnchor);
			table.setText(cn, cArtnr, a.artnr);
			table.setText(cn, cNamn, a.namn);
			table.setText(cn, cUtpris, Util.fmt2.format(a.utpris));
			table.setText(cn, cIlager, ""+a.ilager);
			table.setText(cn, cBestpunkt, ""+a.bestpunkt);
			table.setText(cn, cMaxlager, ""+a.maxlager);
			table.setText(cn, cIorder, ""+a.iorder);
			table.setText(cn, cBest, ""+a.best);
			cellFormatter.addStyleName(cn, cArtnr, "sx-tb-s15");
			cellFormatter.addStyleName(cn, cNamn, "sx-tb-s30");
			cellFormatter.addStyleName(cn, cUtpris, "sx-tb-n15");
			cellFormatter.addStyleName(cn, cIlager, "sx-tb-n15");
			cellFormatter.addStyleName(cn, cBestpunkt, "sx-tb-n15");
			cellFormatter.addStyleName(cn, cMaxlager, "sx-tb-n15");
			cellFormatter.addStyleName(cn, cIorder, "sx-tb-n15");
			cellFormatter.addStyleName(cn, cBest, "sx-tb-n15");
			if (odd) rowFormatter.addStyleName(cn, "sx-bg-odd");
			cn++;
			flexCellFormatter.setColSpan(cn, 0, 8);
		}
		table.setVisible(true);
table.setWidget(0, 0, new Label("blado fill"));

	}
	public void setError(String text) {
		errorLabel.setText(text);
		errorLabel.setVisible(true);
	}
	public void clearError() {
		errorLabel.setVisible(false);
	}


}
