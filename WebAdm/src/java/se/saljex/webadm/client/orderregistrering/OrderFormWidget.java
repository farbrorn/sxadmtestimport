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
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.*;
import se.saljex.webadm.client.common.rpcobject.ArtikelSuggestion;
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

	private static enum SOKFIELD {
		ARTNR, NAMN
	}
	
	private final SelectArtikel selectArtikel = new SelectArtikel(new SelectCallback<ArtikelSuggestion>() {

		@Override
		public void onSelect(ArtikelSuggestion object) {
			artnr.setValue(object.nummer);
			doArtnrEnter();
		}

		@Override
		public void onCancel() {
			showInput(currCol);

		}
	});
	
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
	
	FlowPanel infoPanel = new FlowPanel();
	FlowPanel mainFormPanel = new FlowPanel();
	ScrollPanel formScroll = new ScrollPanel(mainFormPanel);
	ScrollPanel infoScroll = new ScrollPanel(infoPanel);

	private final Label tillgangligaLabel = new Label("Tillgängliga:");
	private final Label tillgangligaValue = new Label();
	private final Label ilagerLabel = new Label("I lager:");
	private final Label ilagerValue = new Label();
	private final Label ibestLabel = new Label("Beställda:");
	private final Label ibestValue = new Label();
	private final Label iorderLabel = new Label("I order:");
	private final Label iorderValue = new Label();
	

	private static final String INPUT_HEIGHT="1.2em";
	private static final String WIDTH_S13 = "7em";
	private static final String WIDTH_S10 = "6em";
	private static final String WIDTH_S35 = "22em";
	private static final String WIDTH_S3 = "3em";
	private static final String WIDTH_S5 = "5em";

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
	
	private OrderFormCallback formCallback;
	
	Grid g = new Grid();

	ArrayList<Rad> rader = new ArrayList<Rad>();
	OrderRad rad;

	public OrderFormWidget(OrderFormCallback formCallback) {
		this.formCallback = formCallback;
		this.addStyleName(Const.Style_Orderreg_Formarea);

		setupInfoPanel();
		
		formScroll.addStyleName(Const.Style_Orderreg_Form);
		infoScroll.addStyleName(Const.Style_Orderreg_Infopanel);
		
		

		mainFormPanel.add(ft);
		mainFormPanel.add(btnUp);
		mainFormPanel.add(btnDown);
		add(formScroll);
		add(infoScroll);
		rader.add(new Rad(new OrderRad("1","Rad 1")));
		rader.add(new Rad(new OrderRad("2","Rad 2")));
		rader.add(new Rad(new OrderRad("3","Rad 3")));
		printOrderRaderHeader();
		printRows();

		artnr.setSize(WIDTH_S13, INPUT_HEIGHT);
		namn.setSize(WIDTH_S35,INPUT_HEIGHT);
		antal.setSize(WIDTH_S10,INPUT_HEIGHT);
		enh.setSize(WIDTH_S3,INPUT_HEIGHT);
		pris.setSize(WIDTH_S10,INPUT_HEIGHT);
		rab.setSize(WIDTH_S3,INPUT_HEIGHT);
		summa.setSize(WIDTH_S13,INPUT_HEIGHT);
		//artnr.setMaxLength(13);
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
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F2) {
					deleteRow(currRow);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F12) {
					selectArtikel.show(artnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.ARTNR);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F5) {
					selectArtikel.show(artnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.SUPERSOK);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F9) {
					doSetSelectParam(selectparamSok, kundnr, artnr.getValue(), lagernr, SQLTableList.COMPARE_GREATER_EQUALS, SOKFIELD.ARTNR);
					pageLoadArtikelSok.setSearch(selectparamSok);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F8) {
					pageLoadArtikelSok.next();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F7) {
					pageLoadArtikelSok.previous();
				}
			}
		});
		
		namn.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN) {
					downRow();
				} else if (keycode==KeyCodes.KEY_UP) {
					upRow();
				} else if (keycode==KeyCodes.KEY_ENTER) {
					doNamnEnter();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F12) {
					selectArtikel.show(namn.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.NAMN);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F5) {
					selectArtikel.show(namn.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.SUPERSOK);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F9) {
					doSetSelectParam(selectparamSok, kundnr, namn.getValue(), lagernr, SQLTableList.COMPARE_GREATER_EQUALS, SOKFIELD.NAMN);
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

		enh.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN) {
					downRow();
				} else if (keycode==KeyCodes.KEY_UP) {
					upRow();
				} else if (keycode==KeyCodes.KEY_ENTER) {
					doEnhEnter();
				}
			}
		});
		
		pris.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN) {
					downRow();
				} else if (keycode==KeyCodes.KEY_UP) {
					upRow();
				} else if (keycode==KeyCodes.KEY_ENTER) {
					doPrisEnter();
				}
			}
		});
		rab.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_DOWN) {
					downRow();
				} else if (keycode==KeyCodes.KEY_UP) {
					upRow();
				} else if (keycode==KeyCodes.KEY_ENTER) {
					doRabEnter();
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

	private void setupInfoPanel() {
		FlowPanel p = new FlowPanel();
		tillgangligaValue.addStyleName(Const.Style_Orderreg_Infopanel_Value);
		tillgangligaValue.addStyleName(Const.Style_Bold);
		tillgangligaLabel.addStyleName(Const.Style_Orderreg_Infopanel_Label);
		p.add(tillgangligaLabel);
		p.add(tillgangligaValue);
		infoPanel.add(p);
		
		p = new FlowPanel();
		ilagerValue.addStyleName(Const.Style_Orderreg_Infopanel_Value);
		ilagerLabel.addStyleName(Const.Style_Orderreg_Infopanel_Label);
		p.add(ilagerLabel);
		p.add(ilagerValue);
		infoPanel.add(p);
		
		p = new FlowPanel();
		iorderValue.addStyleName(Const.Style_Orderreg_Infopanel_Value);
		iorderLabel.addStyleName(Const.Style_Orderreg_Infopanel_Label);
		p.add(iorderLabel);
		p.add(iorderValue);
		infoPanel.add(p);
		
		p = new FlowPanel();
		ibestValue.addStyleName(Const.Style_Orderreg_Infopanel_Value);
		ibestLabel.addStyleName(Const.Style_Orderreg_Infopanel_Label);
		p.add(ibestLabel);
		p.add(ibestValue);
		infoPanel.add(p);	
	}
	
	private void setInfoPanel(VArtKundOrder o) {
		if (o!=null) {
			tillgangligaValue.setText(Util.format0Dec(o.ilager-o.iorder));
			iorderValue.setText(Util.format0Dec(o.iorder));
			ilagerValue.setText(Util.format0Dec(o.ilager));
			ibestValue.setText(Util.format0Dec(o.best));
		}
	}
	
	private void doSetSelectParam(SqlSelectParameters selectParam, String kundnr, String sokString, int lagernr, int compareType, SOKFIELD sokField) {
		selectParam.clearOrderByParameters();
		selectParam.clearWhereParameters();
		selectParam.addWhereParameter("kundnr", SQLTableList.COMPARE_EQUALS, kundnr);
		selectParam.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, "lagernr", SQLTableList.COMPARE_EQUALS, Integer.toString(lagernr));
		String soknamn;
		if (sokField==SOKFIELD.NAMN) soknamn = "artnamn"; else soknamn = "artnr";
		selectParam.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, soknamn, compareType, sokString);	
	}
	
	private boolean isArtnrStjarna() {
		if (Util.isEmpty(artnr.getValue())) return false;
		else return artnr.getValue().startsWith("*");
	}
	
	private void doArtnrEnter() {
		// Get artikel
		if (Util.isEmpty(artnr.getValue())) {
			showInput(COL_NAMN);
		} else if (isArtnrStjarna()) {
			showInput(COL_NAMN);
		} else {
			doSetSelectParam(selectparamGet, kundnr, artnr.getValue(), lagernr, SQLTableList.COMPARE_EQUALS, SOKFIELD.ARTNR);
			pageLoadArtikelGet.setSearch(selectparamGet);		
		}
	}
	
	private void doNamnEnter() {
		// Get artikel
		if (Util.isEmpty(artnr.getValue())) {
			showInput(COL_ARTNR);
		} else if (isArtnrStjarna()) {
			showInput(COL_ANTAL);
		} else {
			doSetSelectParam(selectparamGet, kundnr, namn.getValue(), lagernr, SQLTableList.COMPARE_EQUALS, SOKFIELD.NAMN);
			pageLoadArtikelGet.setSearch(selectparamGet);		
		}
	}
	
	private void doAntalEnter() {
		if (currVArtKundOrder!=null && currVArtKundOrder.artnr!=null && currVArtKundOrder.artnr.equals(artnr.getValue())) {
			setCalcPris(currVArtKundOrder, antal.getValue());
		}
		if (isArtnrStjarna()) {
			showInput(COL_ENH);
		} else {
			inputrad2OrderRad(getOrderRad(currRow));
			downRow();
		}
	}
	
	private void doEnhEnter() {
		showInput(COL_PRIS);
	}
	private void doPrisEnter() {
		showInput(COL_RAB);
	}
	private void doRabEnter() {
		summa.setValue(pris.getValue()*antal.getValue()*(1-rab.getValue()/100));
		inputrad2OrderRad(getOrderRad(currRow));
		downRow();
	}

	private void inputrad2OrderRad(Rad rad) {
		rad.orderRad.artnr = artnr.getValue();
		rad.orderRad.namn = namn.getValue();
		rad.orderRad.antal = antal.getValue();
		rad.orderRad.enh = enh.getValue();
		rad.orderRad.pris = pris.getValue();
		rad.orderRad.rab = rab.getValue();
		rad.orderRad.summa = summa.getValue();
		
		if (artnr.getValue()!=null && artnr.getValue().startsWith("*")) {		//Stjärnrad
			rad.orderRad.konto = "*";
			rad.orderRad.levnr = "*";
			rad.orderRad.netto=0;
			rad.orderRad.stjAutobestall=0;
			rad.orderRad.stjFinnsILager = 0;
			rad.orderRad.textrad=null;
		} else {																//Artikelrad
			rad.orderRad.konto = currVArtKundOrder.konto;
			rad.orderRad.levnr = currVArtKundOrder.lev;
			rad.orderRad.netto=currVArtKundOrder.calc_kostprisin;
			rad.orderRad.stjAutobestall=0;
			rad.orderRad.stjFinnsILager = 0;
			rad.orderRad.textrad=null;
		}
		
		rad.vArtKundOrder = currVArtKundOrder;
		calcOrderSumma();
	}

	private double calcOrderSumma() {
		double summa=0;
		for (Rad rad : rader) {
			summa += rad.orderRad.summa;
		}
		formCallback.onNewOrdersumma(summa);
		return summa;
	}
	
	private void showInputBoxes(int row) {
		OrderRad or = getOrderRad(row).orderRad;
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
		} else if(col == COL_ENH) {
			enh.setEnabled(true);
			enh.setFocus(true);
		} else if(col == COL_PRIS) {
			pris.setEnabled(true);
			pris.setFocus(true);
		} else if(col == COL_RAB) {
			rab.setEnabled(true);
			rab.setFocus(true);
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
			rader.add(new Rad(new OrderRad()));
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
			rader.add(new Rad(new OrderRad()));
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
		setCalcPris(art, 0);
		setInfoPanel(art);
	}
	
	private void setCalcPris(VArtKundOrder art, double antal) {
		//Börja med baspriset
		double lagstaBrutto = art.utpris;
		double lagstaNetto = art.nettopris;
		
		
		if (art.calc_kampanjgaller > 0 && art.kamppris > 0 && art.kamppris < lagstaNetto) lagstaNetto=art.kamppris;
		
		//Kolla staf1
		if (art.staf_antal1 > 0 && antal >= art.staf_antal1 && art.staf_pris1 != 0.0) {
			if (art.staf_pris1 < lagstaBrutto) lagstaBrutto = art.staf_pris1;
			if (art.calc_kampanjgaller > 0 && art.kampprisstaf1 > 0 && art.kampprisstaf1 < lagstaNetto) lagstaNetto = art.kampprisstaf1;
		}
		//Kolla staf2
		if (art.staf_antal2 > 0 && antal >= art.staf_antal2 && art.staf_pris2 != 0.0) {
			if (art.staf_pris2 < lagstaBrutto) lagstaBrutto = art.staf_pris2;
			if (art.calc_kampanjgaller > 0 && art.kampprisstaf2 > 0 && art.kampprisstaf2< lagstaNetto) lagstaNetto = art.kampprisstaf2;
		}
		
		if (lagstaNetto != 0 && lagstaNetto < lagstaBrutto*(1-art.calc_hogstarab/100)) {	//Är nettopriset lägre?
			pris.setValue(0.0);
			rab.setValue(0.0);			
			summa.setValue(lagstaNetto*antal);
		} else { //Bruttopriset gäller
			pris.setValue(lagstaBrutto);
			rab.setValue(art.calc_hogstarab);
			summa.setValue(lagstaBrutto*(1-art.calc_hogstarab/100)*antal);
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
			printRow(row, getOrderRad(row).orderRad);
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
	private Rad getOrderRad(int row) {
		return rader.get(row-1);
	}

	
	private void deleteRow(int row) {
		if (row > 0 && rader.size() > row-1) {
			rader.remove(row-1);
			ft.removeRow(row);
			if (currRow>rader.size()) currRow=rader.size();
			if (rader.size()==0) {
				rader.add(new Rad(new OrderRad()));
				currRow=1;
			}
			selectRow(currRow);
		}
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
	
	public ArrayList<Rad> getRader() { return rader; }
	
	public class Rad {
		
		public Rad(OrderRad orderRad) {
			this.orderRad = orderRad;
		}
		OrderRad orderRad = null;
		VArtKundOrder vArtKundOrder = null;
	}
}
