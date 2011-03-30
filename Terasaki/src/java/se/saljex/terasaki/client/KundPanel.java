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
public class KundPanel extends VerticalPanel{
	private final Label errorLabel = new Label();
	protected final FlexTable table = new FlexTable();
	private final RowFormatter rowFormatter = table.getRowFormatter();
	private final CellFormatter cellFormatter = table.getCellFormatter();
	private final FlexTable.FlexCellFormatter flexCellFormatter = table.getFlexCellFormatter();

	private static final int cExpand=0;
	private static final int cKundnr=1;
	private static final int cNamn=cKundnr+1;
	private static final int cRef=cNamn+1;
	private static final int cAdr1=cRef+1;
	private static final int cTel=cAdr1+1;
	private static final int cBiltel=cTel+1;
	private static final int cOms=cBiltel+1;


	public KundPanel() {
		super();
		add(errorLabel);
		add(table);
		table.addStyleName("sx-table-list");

		doLoadKund();
	}


	public void loadKund() {
		doLoadKund();
	}

	private void doLoadKund() {
		clearError();
		initTable();
		((GWTServiceAsync)GWT.create(GWTService.class)).getKundList(callback);
	}

	private void initTable() {
		table.setVisible(false);
		table.removeAllRows();
		table.setText(0, cKundnr, "Cust#");
		table.setText(0, cNamn, "Name");
		table.setText(0, cRef, "Contact");
		table.setText(0, cAdr1, "Address");
		table.setText(0, cTel, "Tel");
		table.setText(0, cBiltel, "Mobile");
		table.setText(0, cOms, "Turnover");
		cellFormatter.addStyleName(0, cKundnr, "sx-tb-s15");
		cellFormatter.addStyleName(0, cNamn, "sx-tb-s30");
		cellFormatter.addStyleName(0, cRef, "sx-tb-s30");
		cellFormatter.addStyleName(0, cAdr1, "sx-tb-s30");
		cellFormatter.addStyleName(0, cTel, "sx-tb-s30");
		cellFormatter.addStyleName(0, cBiltel, "sx-tb-s30");

		cellFormatter.addStyleName(0, cOms, "sx-tb-n10");

		rowFormatter.addStyleName(0, "sx-table-rubrik");
	}

	final AsyncCallback<KundList> callback = new AsyncCallback<KundList>() {
		public void onSuccess(KundList result) {
			doFillTable(result);
		}

		public void onFailure(Throwable caught) {
			setError("Serverfel: " + caught.getMessage());
		}
	};

	private void showInfo(final int row, final String kundnr) {
		if (table.getWidget(row+1, 0) == null) {
			table.setWidget(row+1, 0, new KundInfoPanel(kundnr));
//			table.setWidget(row+1, 0, new Label("sss"));
		} else {
			table.clearCell(row+1, 0);
		}
	}

	private void doFillTable(KundList kundList) {
		int cn=0;
		boolean odd=true;
		Anchor expandAnchor;
		for (Kund a : kundList.kunder) {
			cn++;
			odd=!odd;
			expandAnchor = new Anchor("Info");
			final int finalCn = cn;
			final String finalKundnr=a.kundnr;
			expandAnchor.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					showInfo(finalCn,finalKundnr);
				}
			});

			table.setWidget(cn, cExpand, expandAnchor);
			table.setText(cn, cKundnr, a.kundnr);
			table.setText(cn, cNamn, a.namn);
			table.setText(cn, cRef, a.ref);
			table.setText(cn, cTel, a.tel);
			table.setText(cn, cBiltel, a.biltel);
			table.setText(cn, cOms, Util.fmt0.format(a.omsattning));
			cellFormatter.addStyleName(cn, cKundnr, "sx-tb-s15");
			cellFormatter.addStyleName(cn, cNamn, "sx-tb-s30");
			cellFormatter.addStyleName(cn, cRef, "sx-tb-s30");
			cellFormatter.addStyleName(cn, cAdr1, "sx-tb-s30");
			cellFormatter.addStyleName(cn, cTel, "sx-tb-s30");
			cellFormatter.addStyleName(cn, cBiltel, "sx-tb-s30");
			cellFormatter.addStyleName(0, cOms, "sx-tb-n10");

			if (odd) rowFormatter.addStyleName(cn, "sx-bg-odd");
			cn++;
			flexCellFormatter.setColSpan(cn, 0, 8);
		}
		table.setVisible(true);

	}
	public void setError(String text) {
		errorLabel.setText(text);
		errorLabel.setVisible(true);
	}
	public void clearError() {
		errorLabel.setVisible(false);
	}


}
