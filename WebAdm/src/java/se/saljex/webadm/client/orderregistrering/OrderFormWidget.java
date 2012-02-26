/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import se.saljex.webadm.client.common.DebugMessagePanel;
import se.saljex.webadm.client.common.DoubleTextBox;
import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.PageLoadCallback;
import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;
import se.saljex.webadm.client.common.rpcobject.VArtKundOrder;
import se.saljex.webadm.server.SQLTableGetList;

/**
 *
 * @author Ulf
 */
public class OrderFormWidget extends FlowPanel{

	Button btnUp = new Button("Upp", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			upRow();
		}
	});
	Button btnDown = new Button("Ner", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			downRow();
		}
	});

	PageLoad<VArtKundOrder> pageLoadArtikel = new PageLoad<VArtKundOrder>(new VArtKundOrder(), 3, 10, 1000, new PageLoadCallback<VArtKundOrder>() {

		@Override
		public void onRowUpdate(VArtKundOrder row) {
			doDispArt(row);
		}

		@Override
		public void onBufferUpdate(List<VArtKundOrder> bufferList) {
		}

		@Override
		public void onFailure(Throwable caught) {
			DebugMessagePanel.addMessage("Fel vid kommunikation: " + caught.getMessage());
		}
	});


	FlexTable ft = new FlexTable();

	TextBox artnr = new TextBox();
	TextBox namn = new TextBox();
	DoubleTextBox antal = new DoubleTextBox();

	private static final String INPUT_HEIGHT="1.2em";
	private static final String WIDTH_S13 = "100px";
	private static final String WIDTH_S35 = "300px";
	private static final String WIDTH_S3 = "30px";
	private static final String WIDTH_S5 = "50px";

	public static final int COL_ARTNR = 0;
	public static final int COL_NAMN = 1;
	public static final int COL_ANTAL = 2;
	public static final int COL_ENH = 3;
	public static final int COL_PRIS = 4;
	public static final int COL_RAB = 5;
	public static final int COL_SUMMA = 6;

	private Integer currRow = null;
	private Integer currCol = null;
	private Widget savedWidget = null;
	private OrderRad tempOrderRad = null;

	Grid g = new Grid();

	ArrayList<OrderRad> rader = new ArrayList<OrderRad>();
	OrderRad rad;

	public OrderFormWidget() {
		add(ft);
		add(btnUp);
		add(btnDown);
		rader.add(new OrderRad("1","Rad 1"));
		rader.add(new OrderRad("2","Rad 2"));
		rader.add(new OrderRad("3","Rad 3"));
		printOrderRaderHeader();
		printRows();

		artnr.setSize(WIDTH_S13, INPUT_HEIGHT);
		namn.setSize(WIDTH_S35,INPUT_HEIGHT);
		antal.setSize(WIDTH_S13,INPUT_HEIGHT);
		artnr.setMaxLength(13);
		namn.setMaxLength(35);
		antal.setMaxLength(12);

		artnr.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN) {
					downRow();
				} else if (keycode==KeyCodes.KEY_UP) {
					upRow();
				} else if (keycode==KeyCodes.KEY_ENTER) {
					doArtnrEnter();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F9) {
					SqlSelectParameters sp = new SqlSelectParameters();
					sp.addWhereParameter("kundnr", SQLTableList.COMPARE_EQUALS, "0555");
					sp.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, "lagernr", SQLTableList.COMPARE_EQUALS, "0");
					sp.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, "artnr", SQLTableList.COMPARE_GREATER_EQUALS, artnr.getValue());
					pageLoadArtikel.setSearch(sp);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F8) {
					pageLoadArtikel.next();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F7) {
					pageLoadArtikel.previous();
				}
			}
		});




		ft.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				HTMLTable.Cell c = ft.getCellForEvent(event);
				if (c!=null) {
					if (c.getRowIndex()>0){
						if (currRow==null) currRow = 0;
						if (currRow!=c.getRowIndex()) {
							selectRow(c.getRowIndex());
						}
					}
				}
			}
		});
	}

	private void doArtnrEnter() {
		// Get artikel
		currCol=COL_ANTAL;
		getOrderRad(currRow).artnr=artnr.getValue();
		printRow(currRow);
		showInput(currRow,currCol,getOrderRad(currRow));
	}


	private void showInput(int row, int col, OrderRad or) {
		savedWidget = ft.getWidget(row, col);

		if (col==COL_NAMN) {
			namn.setValue(or.artnr);
			ft.setWidget(row, col, namn);
			namn.setFocus(true);
		} else if(col == COL_ANTAL) {
			antal.setValue(or.antal);
			ft.setWidget(row, col, namn);
			antal.setFocus(true);
		} else {		//COL_ARTNR
			artnr.setValue(or.artnr);
			ft.setWidget(row, col, artnr);
			artnr.setFocus(true);
		}
	}

	private void downRow() {
		if (currRow==null) currRow=1;
		else { 
			printRow(currRow);
			currRow++;
		}
		if (currRow >= rader.size()+2) currRow = rader.size();
		if (currRow >= rader.size()+1) { //Lägg till tom rad
			rader.add(new OrderRad());
		}
		selectRow(currRow);
	}
	
	private void upRow() {
		if (currRow==null) currRow=1;
		else {
			printRow(currRow);
			currRow--;
		}
		if (currRow< 1) currRow=1;
		if (currRow >= rader.size()) currRow = rader.size();
		if (currRow >= rader.size()) { //Lägg till tomrad
			rader.add(new OrderRad());
		}
		selectRow(currRow);			
	}

	//Först rad = 1
	private void selectRow(int row) {
		if (currRow!=null) printRow(currRow);
		if (row <= rader.size()) {
			currCol = COL_ARTNR;
			currRow=row;
			showInput(row, currCol, getOrderRad(row));
			tempOrderRad = new OrderRad(getOrderRad(row));
			artnr.setFocus(true);
		}
	}


	private void printRow(int row, OrderRad orderRad) {
			ft.setWidget(row, COL_ARTNR, new Label(orderRad.artnr));
			ft.setWidget(row, COL_NAMN, new Label(orderRad.namn));
			ft.setWidget(row, COL_ANTAL, new Label(Double.toString(orderRad.antal)));
	}

	private void doDispArt(VArtKundOrder art) {
			//ft.setWidget(currRow, COL_ARTNR, new Label(orderRad.artnr));
			ft.setWidget(currRow, COL_NAMN, new Label(art.artnamn));
			artnr.setValue(art.artnr);
	}

	private void printRow(int row) {
		if (row < 1 || row > rader.size()) {
			DebugMessagePanel.addMessage("Felaktig rad vid printRow. Rad: " + row + " Antal rader: " + rader.size());
		} else {
			printRow(row, getOrderRad(row));
		}
	}

	private void printRows() {
		//ft = new FlexTable();
		ft.clear();
		for (int cn = 1; cn <= rader.size(); cn++) {
			printRow(cn);
		}
	}

	//Hämtar orderrad med första raden == 1
	private OrderRad getOrderRad(int row) {
		return rader.get(row-1);
	}

	private void printOrderRaderHeader() {
		ft.setWidget(0, COL_ARTNR, new HTML("Artikelnr"));
		ft.setWidget(0, COL_NAMN, new HTML("Benämning"));
		ft.setWidget(0, COL_PRIS, new HTML("Pris"));
		ft.setWidget(0, COL_RAB, new HTML("%"));
		ft.setWidget(0, COL_ANTAL, new HTML("Antal"));
		ft.setWidget(0, COL_ENH, new HTML("Enh"));
		ft.setWidget(0, COL_SUMMA, new HTML("Summa"));

		ColumnFormatter cf = ft.getColumnFormatter();
		cf.setWidth(COL_ARTNR, WIDTH_S13);
		cf.setWidth(COL_NAMN, WIDTH_S35);
		cf.setWidth(COL_PRIS, WIDTH_S13);
		cf.setWidth(COL_RAB, WIDTH_S5);
		cf.setWidth(COL_ANTAL, WIDTH_S13);
		cf.setWidth(COL_ENH, WIDTH_S3);
		cf.setWidth(COL_SUMMA, WIDTH_S13);
	}


	AsyncCallback<Object> getArtikelCallback = new AsyncCallback<Object>() {

		@Override
		public void onFailure(Throwable caught) {
			Util.hideModalWait();
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public void onSuccess(Object result) {
			Util.hideModalWait();
			throw new UnsupportedOperationException("Not supported yet.");
		}
	};
}
