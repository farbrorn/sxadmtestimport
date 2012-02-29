/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.*;
import java.util.ArrayList;
import java.util.List;
import se.saljex.webadm.client.common.*;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;
import se.saljex.webadm.client.common.rpcobject.VArtKundOrder;

/**
 *
 * @author Ulf
 */
public class OrderFormWidget extends FlowPanel{
	private final SqlSelectParameters selectparamSok = new SqlSelectParameters();
	private final SqlSelectParameters selectparamGet = new SqlSelectParameters();

	
	//Temporära konstanter
	private static final String kundnr = "0555";
	private static final int lagernr = 0;
	
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



	PageLoadCallback<VArtKundOrder> pageLoadCallbackSok = new PageLoadCallback<VArtKundOrder>() {

		@Override
		public void onRowUpdate(VArtKundOrder row) {
			setInputValues(row);
		}

		@Override
		public void onBufferUpdate(List<VArtKundOrder> bufferList) {
		}

		@Override
		public void onFailure(Throwable caught) {
			DebugMessagePanel.addMessage("Fel vid kommunikation: " + caught.getMessage());
		}
	};

	PageLoadCallback<VArtKundOrder> pageLoadCallbackGet = new PageLoadCallback<VArtKundOrder>() {

		@Override
		public void onRowUpdate(VArtKundOrder row) {
			doGetArt(row);
		}

		@Override
		public void onBufferUpdate(List<VArtKundOrder> bufferList) {
		}

		@Override
		public void onFailure(Throwable caught) {
			DebugMessagePanel.addMessage("Fel vid kommunikation: " + caught.getMessage());
		}
	};
	VArtKundOrder tempVArtKundOrder = new VArtKundOrder();
	PageLoad<VArtKundOrder> pageLoadArtikelSok = new PageLoad<VArtKundOrder>(tempVArtKundOrder, 3, 10, 1000, pageLoadCallbackSok);
	PageLoad<VArtKundOrder> pageLoadArtikelGet = new PageLoad<VArtKundOrder>(tempVArtKundOrder, 1, 1, 1, pageLoadCallbackGet);
	
	
	FlexTable ft = new FlexTable();

	TextBox artnr = new TextBox();
	TextBox namn = new TextBox();
	DoubleTextBox antal = new DoubleTextBox();
	TextBox enh = new TextBox();
	DoubleTextBox pris = new DoubleTextBox();
	DoubleTextBox rab = new DoubleTextBox();
	DoubleTextBox summa = new DoubleTextBox();

	private static final String INPUT_HEIGHT="1.2em";
	private static final String WIDTH_S13 = "100px";
	private static final String WIDTH_S10 = "130px";
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

	private VArtKundOrder currVArtKundOrder = null;
	
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
		enh.setSize(WIDTH_S3,INPUT_HEIGHT);
		pris.setSize(WIDTH_S10,INPUT_HEIGHT);
		rab.setSize(WIDTH_S3,INPUT_HEIGHT);
		summa.setSize(WIDTH_S13,INPUT_HEIGHT);
		artnr.setMaxLength(13);
		namn.setMaxLength(35);
		antal.setMaxLength(12);
		enh.setMaxLength(3);
		pris.setMaxLength(12);
		rab.setMaxLength(2);
		summa.setMaxLength(13);

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
					doSetSelectParam(selectparamSok, kundnr, artnr.getValue(), lagernr, SQLTableList.COMPARE_GREATER_EQUALS);
					pageLoadArtikelSok.setSearch(selectparamSok);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F8) {
					pageLoadArtikelSok.next();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F7) {
					pageLoadArtikelSok.previous();
				}
			}
		});

		antal.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN) {
					downRow();
				} else if (keycode==KeyCodes.KEY_UP) {
					upRow();
				} else if (keycode==KeyCodes.KEY_ENTER) {
					doAntalEnter();
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
		
		selectRow(1);
	}

	private void doSetSelectParam(SqlSelectParameters selectParam, String kundnr, String artnr, int lagernr, int compareType) {
		selectParam.clearOrderByParameters();
		selectParam.clearWhereParameters();
		selectParam.addWhereParameter("kundnr", SQLTableList.COMPARE_EQUALS, kundnr);
		selectParam.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, "lagernr", SQLTableList.COMPARE_EQUALS, Integer.toString(lagernr));
		selectParam.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, "artnr", compareType, artnr);	
	}
	
	private void doArtnrEnter() {
		// Get artikel
		doSetSelectParam(selectparamGet, kundnr, artnr.getValue(), lagernr, SQLTableList.COMPARE_EQUALS);
		pageLoadArtikelGet.setSearch(selectparamSok);		
	}
	
	private void doAntalEnter() {
		OrderRad or = getOrderRad(currRow);
		if (or.vArtKundOrder!=null && or.vArtKundOrder.artnr!=null && or.vArtKundOrder.artnr.equals(artnr.getValue())) {
			setCalcPris(or.vArtKundOrder, antal.getValue());
		}
		inputrad2OrderRad(or);
		downRow();
	}

	private void inputrad2OrderRad(OrderRad or) {
		or.artnr = artnr.getValue();
		or.namn = namn.getValue();
		or.antal = antal.getValue();
		or.enh = enh.getValue();
		or.pris = pris.getValue();
		or.rab = rab.getValue();
		or.summa = summa.getValue();
		or.vArtKundOrder = currVArtKundOrder;
	}

	private void showInputBoxes(int row) {
		OrderRad or = getOrderRad(row);
		artnr.setValue(or.artnr);
		namn.setValue(or.namn);
		antal.setValue(or.antal);
		enh.setValue(or.enh);
		pris.setValue(or.pris);
		rab.setValue(or.rab);
		summa.setValue(or.summa);
		ft.setWidget(row, COL_ARTNR, artnr);
		ft.setWidget(row, COL_NAMN, namn);
		ft.setWidget(row, COL_ANTAL, antal);
		ft.setWidget(row, COL_ENH, enh);
		ft.setWidget(row, COL_PRIS, pris);
		ft.setWidget(row, COL_RAB, rab);
		ft.setWidget(row, COL_SUMMA, summa);
		disableAllInputBoxes();
	}
	
	public void disableAllInputBoxes() {
		artnr.setEnabled(false);
		namn.setEnabled(false);
		antal.setEnabled(false);
		enh.setEnabled(false);
		pris.setEnabled(false);
		rab.setEnabled(false);
		summa.setEnabled(false);
		
	}
	
	private void showInput(int col) {
		disableAllInputBoxes();
		if (col==COL_NAMN) {
			namn.setEnabled(true);
			namn.setFocus(true);
		} else if(col == COL_ANTAL) {
			antal.setEnabled(true);
			antal.setFocus(true);
		} else {		//COL_ARTNR
			artnr.setEnabled(true);
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
			currVArtKundOrder = getOrderRad(row).vArtKundOrder;
			currRow=row;
			showInputBoxes(row);
			showInput(currCol);
		}
	}



	
	private void doGetArt(VArtKundOrder art) {
		if (art!=null) {
			currCol=COL_ANTAL;
			setInputValues(art);
			showInput(currCol);
			currVArtKundOrder = art;
		}
	}

	private void setInputValues(VArtKundOrder art) {
		artnr.setValue(art.artnr);
		namn.setValue(art.artnamn);
		enh.setValue(art.enhet);
		pris.setValue(art.utpris);
		setCalcPris(art, 1);
		
	}
	
	private void setCalcPris(VArtKundOrder art, double antal) {
		//Börja med baspriset
		double lagstaBrutto = art.utpris;
		double hogstaRab = art.undergrupprab > 0.0 ? art.undergrupprab : art.gruppbasrab;
		double lagstaNetto = 0;
		
		
		//Kolla staf1
		if (art.staf_antal1 > 0 && antal >= art.staf_antal1 && art.staf_pris1 != 0.0) {
			if (art.staf_pris1 < lagstaBrutto) lagstaBrutto = art.staf_pris1;
		}
		//Kolla staf2
		if (art.staf_antal2 > 0 && antal >= art.staf_antal2 && art.staf_pris2 != 0.0) {
			if (art.staf_pris2 < lagstaBrutto) lagstaBrutto = art.staf_pris2;
		}
		
		if (lagstaNetto != 0 && lagstaNetto < lagstaBrutto*(1-hogstaRab/100)) {	//Är nettopriset lägre?
			pris.setValue(0.0);
			rab.setValue(0.0);			
			summa.setValue(lagstaNetto*antal);
		} else { //Bruttopriset gäller
			pris.setValue(lagstaBrutto);
			rab.setValue(hogstaRab);
			summa.setValue(lagstaBrutto*(1-hogstaRab/100)*antal);
		}
		
	}
	
	private void printRow(int row, OrderRad orderRad) {
			ft.setWidget(row, COL_ARTNR, new Label(orderRad.artnr));
			ft.setWidget(row, COL_NAMN, new Label(orderRad.namn));
			ft.setWidget(row, COL_ANTAL, new Label(Double.toString(orderRad.antal)));
			ft.setWidget(row, COL_ENH, new Label(orderRad.enh));
			ft.setWidget(row, COL_PRIS, new Label(Double.toString(orderRad.pris)));
			ft.setWidget(row, COL_RAB, new Label(Double.toString(orderRad.rab)));
			ft.setWidget(row, COL_SUMMA, new Label(Double.toString(orderRad.summa)));
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
