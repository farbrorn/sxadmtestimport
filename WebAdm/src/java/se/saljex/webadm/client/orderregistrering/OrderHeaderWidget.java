/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.*;
import se.saljex.webadm.client.common.rpcobject.*;

/**
 *
 * @author Ulf
 */
public class OrderHeaderWidget extends FlowPanel {
	private static enum SOKFIELD {
		KUNDNR, NAMN
	}
	
	private boolean annanLevAdr = false;
	private final TextBox kundnr = new TextBox();
	private final TextBox kundnamn = new TextBox();
	private final TextBox kundadr1 = new TextBox();
	private final TextBox kundadr2 = new TextBox();
	private final TextBox kundadr3 = new TextBox();
	private final TextBox linjenr1 = new TextBox();
	private final TextBox linjenr2 = new TextBox();
	private final TextBox linjenr3 = new TextBox();
	private final TextBox marke = new TextBox();
	private final TextBox ordermeddelande = new TextBox();
	private final TextBox referens = new TextBox();
	private final TextBox saljare = new TextBox();
	private final IntegerTextBox lagernr = new IntegerTextBox();
	private final TextBox levAdr1 = new TextBox();
	private final TextBox levAdr2 = new TextBox();
	private final TextBox levAdr3 = new TextBox();
	private final TextBox fraktkundnr = new TextBox();
		
	private final IntegerTextBox ktid = new IntegerTextBox();
	
	private final CheckBox forskatt = new CheckBox("Förskottsbetalning");
	private final CheckBox forskattBetald = new CheckBox("Förskott betalt");
	private final CheckBox mottagarfrakt = new CheckBox("Mottagarfrakt");
	
	private final TextBox betalsatt = new TextBox();
	private final DateTextBox doljdatum = new DateTextBox();
	private final CheckBox faktor = new CheckBox("Faktoring");
	private final TextBox fraktbolag = new TextBox();
	private final DoubleTextBox fraktfrigrans = new DoubleTextBox();
	private final IntegerTextBox kundordernr = new IntegerTextBox();
	public final DateTextBox levdat = new DateTextBox();
	private final TextBox levvillkor = new TextBox();
	private final DateTextBox tidigastfaktdatum = new DateTextBox();
	private short veckolevdag=0;
	private int wordernr=0;
	private int ordernr=0;
	private short dellev=0;
	private String status = "";
	
	ListBox moms  = new ListBox(false);
	ListBox bonus  = new ListBox(false);

	private final Button btnAnnanLevAdr = new Button("Ändra levadress", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			enableLevAdr();
		}
	});


		private final TabLayoutPanel tabPanel = new TabLayoutPanel(1.4, Style.Unit.EM);

		ArrayList<DataBinder> dataBinderArr = new ArrayList<DataBinder>();

		
	PageLoadCallback<Kund> pageLoadCallbackSok = new PageLoadCallback<Kund>() {

		@Override
		public void onRowUpdate(Kund row) {
			setInputData(row);
		}

		@Override
		public void onBufferUpdate(List<Kund> bufferList) {
		}

		@Override
		public void onFailure(Throwable caught) {
			DebugMessagePanel.addMessage("Fel vid kommunikation: " + caught.getMessage());
		}
	};

	PageLoadCallback<Kund> pageLoadCallbackGet = new PageLoadCallback<Kund>() {

		@Override
		public void onRowUpdate(Kund row) {
			setInputData(row);
		}

		@Override
		public void onBufferUpdate(List<Kund> bufferList) {
		}

		@Override
		public void onFailure(Throwable caught) {
			DebugMessagePanel.addMessage("Fel vid kommunikation: " + caught.getMessage());
			clearInputDataKund();
		}
	};
	
	
	private final SelectKund selectKund = new SelectKund(new SelectCallback<KundSuggestion>() {

		@Override
		public void onSelect(KundSuggestion object) {
			kundnr.setValue(object.nummer);
			doKundnrEnter();
		}

		@Override
		public void onCancel() {

		}
	});
	
	
	private final SqlSelectParameters selectparamSok = new SqlSelectParameters();
	private final SqlSelectParameters selectparamGet = new SqlSelectParameters();
		
	Kund tempKund = new Kund();
	PageLoad<Kund> pageLoadKundSok = new PageLoad<Kund>(tempKund, 3, 10, 1000, pageLoadCallbackSok);
	PageLoad<Kund> pageLoadKundGet = new PageLoad<Kund>(tempKund, 1, 1, 1, pageLoadCallbackGet);
		
		
	public OrderHeaderWidget() {
		addDataBinders();
		moms.addItem("Momsfritt");
		moms.addItem("Moms 1");
		moms.addItem("Moms 2");
		moms.addItem("Moms 3");
		
		bonus.addItem("Ingen");
		bonus.addItem("Normal");
		bonus.addItem("Samlad");
		
		
		add(createKundWidget());
		
		
		tabPanel.setWidth("45%");
		tabPanel.setHeight("100%");
		tabPanel.addStyleName(Const.Style_FloatLeft);
		tabPanel.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
		tabPanel.add(createLevAdrPanel(), "Leverans");
		tabPanel.add(createFakturaPanel(), "Faktura");
		tabPanel.add(createFraktPanel(), "Frakt");
		tabPanel.add(createForskattPanel(), "Förskott");
		tabPanel.add(createTestPanel(),"Test");
		add(tabPanel);
		kundnr.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_ENTER) {
					doKundnrEnter();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F12) {
					selectKund.show(kundnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectKund.SEARCH_FIELD.KUNDNR);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F5) {
					selectKund.show(kundnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectKund.SEARCH_FIELD.SUPERSOK);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F9) {
					doSetSelectParam(selectparamSok, kundnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SOKFIELD.KUNDNR);
					pageLoadKundSok.setSearch(selectparamSok);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F8) {
					pageLoadKundSok.next();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F7) {
					pageLoadKundSok.previous();
				}
			}
		});
		kundnamn.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F9) {
					doSetSelectParam(selectparamSok, kundnamn.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SOKFIELD.NAMN);
					pageLoadKundSok.setSearch(selectparamSok);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F12) {
					selectKund.show(kundnamn.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectKund.SEARCH_FIELD.KUNDNR);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F5) {
					selectKund.show(kundnamn.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectKund.SEARCH_FIELD.SUPERSOK);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F8) {
					pageLoadKundSok.next();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F7) {
					pageLoadKundSok.previous();
				}
			}
		});
		initVars();				//Utföres efter initiering

	}
	
	private void doSetSelectParam(SqlSelectParameters selectParam, String sokString, int compareType, SOKFIELD sokField) {
		selectParam.clearOrderByParameters();
		selectParam.clearWhereParameters();
		String soknamn;
		if (sokField==SOKFIELD.NAMN) soknamn = "namn"; else soknamn = "nummer";
		selectParam.addWhereParameter( soknamn, compareType, sokString);	
	}
	
	private void doKundnrEnter() {
		// Get artikel
		if (!Util.isEmpty(kundnr.getValue())) {
			doSetSelectParam(selectparamGet, kundnr.getValue(), SQLTableList.COMPARE_EQUALS, SOKFIELD.KUNDNR);
			pageLoadKundGet.setSearch(selectparamGet);		
		} else {
			clearInputDataKund();
		}
	}
	
	private void initVars() {
		clearHasData();
		moms.setSelectedIndex(1);
	}
	
	private Panel createKundWidget() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("100%");
		sp.setWidth("45%");
		FormNavigator navigator = new FormNavigator();
		addInput(fp, "Kundnr", kundnr, "8em", "14em", navigator);
		addInput(fp, "Namn", kundnamn, "8em", "20em", navigator);
		addInput(fp, "Adress", kundadr1, "8em", "20em", navigator);
		addInput(fp, ".", kundadr2, "8em", "20em", navigator);
		addInput(fp, ".", kundadr3, "8em", "20em", navigator);
		return sp;
	}
	
	private Panel createLevAdrPanel() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("95%");
		sp.setWidth("95%");
		
		FormNavigator navigator = new FormNavigator();
		addInput(fp, "LevAdress", levAdr1, "8em", "14em", navigator);
		addInput(fp, ".", levAdr2, "8em", "20em", navigator);
		addInput(fp, ".", levAdr3, "8em", "20em", navigator);
		addInput(fp, "Märke", marke, "8em", "20em", navigator);
		addInput(fp, "", btnAnnanLevAdr, "0em", "6em", navigator);
		addInput(fp, "Kundordernr", kundordernr, "8em", "7em", navigator);
		addInput(fp, "Leveransdatum", levdat, "8em", "7em", navigator);
		return sp;		
	}
	
	private Panel createFraktPanel() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("95%");
		sp.setWidth("95%");
			
		FormNavigator navigator = new FormNavigator();
		addInput(fp, "Fraktkundnr", fraktkundnr, "8em", "20em", null);
		addInput(fp, null, mottagarfrakt, "8em", "14em", navigator);
		addInput(fp, "Fraktbolag", fraktbolag, "8em", "20em", navigator);
		addInput(fp, "Fraktfrigräns",fraktfrigrans , "8em", "7em", navigator);
		addInput(fp, "Leveransvillkor", levvillkor, "8em", "20em", navigator);
		return sp;		
	}
	private Panel createForskattPanel() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("95%");
		sp.setWidth("95%");
			
		FormNavigator navigator = new FormNavigator();
		addInput(fp, null, forskatt, "8em", "14em", navigator);
		addInput(fp, null, forskattBetald, "8em", "14em", navigator);
		addInput(fp, "Betalsätt", betalsatt, "8em", "20em", navigator);
		return sp;		
	}
	
	private Panel createFakturaPanel() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("95%");
		sp.setWidth("95%");
		
		FormNavigator navigator = new FormNavigator();
		
		addInput(fp, "Kredittid", ktid, "8em", "4em", navigator);
		addInput(fp, "Er ref", referens, "8em", "20em", navigator);
		addInput(fp, "Vår ref", saljare, "8em", "20em", navigator);
		addInput(fp, "Moms", moms, "8em", "7em", null);
		addInput(fp, "Bonus", bonus, "8em", "7em", null);
		addInput(fp, null, faktor, "8em", "14em", navigator);
		addInput(fp, "Tidigaste faktura", tidigastfaktdatum, "8em", "7em", navigator);

		
		return sp;		
	}
	
	private Panel createTestPanel() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("100%");
		sp.setWidth("100%");
		
		fp.add(new Label("Tesss"));
		
		FormNavigator navigator = new FormNavigator();
		return sp;		
	}
	
	
	private void addInput(Panel panel, String Label, FocusWidget widget, String labelWidth, String widgetWidth, FormNavigator navigator) {
		panel.add(new FlowLabelWidgetPar(Label, widget, labelWidth, widgetWidth));
		if (navigator!=null) navigator.add(widget);
	}

	public Order1 toOrder1() throws ParseException{
		return hasData2Object();
	}
	

	//Sätter de inputar där data kommer från kund i databasen
	private void setInputData(Kund k) {
		kundnr.setValue(k.nummer);
		kundnamn.setValue(k.namn);
		kundadr1.setValue(k.adr1);
		kundadr2.setValue(k.adr2);
		kundadr3.setValue(k.adr3);
		linjenr1.setValue(k.linjenr1);
		linjenr2.setValue(k.linjenr2);
		linjenr3.setValue(k.linjenr3);
		referens.setValue(k.ref);
		saljare.setValue(k.saljare);
		levAdr1.setValue(k.lnamn);
		levAdr2.setValue(k.ladr2);
		levAdr3.setValue(k.ladr3);
		bonus.setSelectedIndex(k.bonus<=2 ? k.bonus : 0);
		fraktkundnr.setValue(k.fraktkundnr);
		mottagarfrakt.setValue(k.mottagarfrakt!=0);
		faktor.setValue(k.faktor!=0);
		fraktbolag.setValue(k.fraktbolag);
		fraktfrigrans.setValue(k.fraktfrigrans);
		levvillkor.setValue(k.levvillkor);
				
		disableNormalDisabledFields();
	}
	
	private void disableNormalDisabledFields() {
		levAdr1.setEnabled(false);
		levAdr2.setEnabled(false);
		levAdr3.setEnabled(false);
		annanLevAdr=false;
	}
	private void enableLevAdr() {
		levAdr1.setEnabled(true);
		levAdr2.setEnabled(true);
		levAdr3.setEnabled(true);
		annanLevAdr=true;
	}
	
	//Rensar de inputar där data kommer från kund i databasen
	private void clearInputDataKund() {
		kundnr.setValue("");
		kundnamn.setValue("");
		kundadr1.setValue("");
		kundadr2.setValue("");
		kundadr3.setValue("");
		linjenr1.setValue("");
		linjenr2.setValue("");
		linjenr3.setValue("");
		referens.setValue("");
		saljare.setValue("");
		levAdr1.setValue("");
		levAdr2.setValue("");
		levAdr3.setValue("");
		bonus.setSelectedIndex(0);

		mottagarfrakt.setValue(false);
		faktor.setValue(false);
		fraktbolag.setValue("");
		fraktfrigrans.setValue(0.0);
		levvillkor.setValue("");
		
		disableNormalDisabledFields();
	}
	
	
	private void addDataBinders() {
/*	private final TextBox kundnr = new TextBox();
		private final TextBox marke = new TextBox();
		private final TextBox ordermeddelande = new TextBox();
		private final TextBox referens = new TextBox();
		private final TextBox saljare = new TextBox();
		private final IntegerTextBox lagernr = new IntegerTextBox();
		private final TextBox levAdr1 = new TextBox();
		private final TextBox levAdr2 = new TextBox();
		private final TextBox levAdr3 = new TextBox();
		private final Button btnAnnanLevAdr = new Button("Ändra levadress", new ClickHandler() {
		* */
		
		dataBinderArr.add(new DataBinder<TextBox, Order1>(kundnr) {
			@Override	public void hasData2Object(Order1 o) {o.kundnr = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.kundnr);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(kundnamn) {
			@Override	public void hasData2Object(Order1 o) {o.namn = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.namn);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(kundadr1) {
			@Override	public void hasData2Object(Order1 o) {o.adr1 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.adr1);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(kundadr2) {
			@Override	public void hasData2Object(Order1 o) {o.adr2 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.adr2);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(kundadr3) {
			@Override	public void hasData2Object(Order1 o) {o.adr3 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.adr3);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(linjenr1) {
			@Override	public void hasData2Object(Order1 o) {o.linjenr1 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.linjenr1);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(linjenr2) {
			@Override	public void hasData2Object(Order1 o) {o.linjenr2 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.linjenr2);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(linjenr3) {
			@Override	public void hasData2Object(Order1 o) {o.linjenr3 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.linjenr3);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(marke) {
			@Override	public void hasData2Object(Order1 o) {o.marke = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.marke);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(ordermeddelande) {
			@Override	public void hasData2Object(Order1 o) {o.ordermeddelande = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.ordermeddelande);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(referens) {
			@Override	public void hasData2Object(Order1 o) {o.referens = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.referens);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(saljare) {
			@Override	public void hasData2Object(Order1 o) {o.saljare = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.saljare);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(levAdr1) {
			@Override	public void hasData2Object(Order1 o) {o.levadr1 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levadr1);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(levAdr2) {
			@Override	public void hasData2Object(Order1 o) {o.levadr2 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levadr2);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(levAdr3) {
			@Override	public void hasData2Object(Order1 o) {o.levadr3 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levadr3);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(fraktkundnr) {
			@Override	public void hasData2Object(Order1 o) {o.fraktkundnr = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.fraktkundnr);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<IntegerTextBox, Order1>(lagernr) {
			@Override	public void hasData2Object(Order1 o) throws ParseException {o.lagernr = v.getValueOrThrow().shortValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(((Short)o.lagernr).intValue());	}
			@Override	public void clearHasData() {	v.setValue(0);		}
		});
		dataBinderArr.add(new DataBinder<IntegerTextBox, Order1>(ktid) {
			@Override	public void hasData2Object(Order1 o) throws ParseException {o.ktid = v.getValueOrThrow().shortValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(((Short)o.ktid).intValue());	}
			@Override	public void clearHasData() {	v.setValue(0);		}
		});
		dataBinderArr.add(new DataBinder<CheckBox, Order1>(forskatt) {
			@Override	public void hasData2Object(Order1 o) {o.forskatt = v.getValue() ? (short)1 : (short)0 ;		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.forskatt!=0);	}
			@Override	public void clearHasData() {	v.setValue(false);		}
		});
		dataBinderArr.add(new DataBinder<CheckBox, Order1>(forskattBetald) {
			@Override	public void hasData2Object(Order1 o) {o.forskattbetald = v.getValue() ? (short)1 : (short)0 ;		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.forskattbetald!=0);	}
			@Override	public void clearHasData() {	v.setValue(false);		}
		});
		dataBinderArr.add(new DataBinder<CheckBox, Order1>(mottagarfrakt) {
			@Override	public void hasData2Object(Order1 o) {o.mottagarfrakt = v.getValue() ? (short)1 : (short)0 ;		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.mottagarfrakt!=0);	}
			@Override	public void clearHasData() {	v.setValue(false);		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(betalsatt) {
			@Override	public void hasData2Object(Order1 o) {o.betalsatt = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.betalsatt);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<DateTextBox, Order1>(doljdatum) {
			@Override	public void hasData2Object(Order1 o) {o.doljdatum = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.doljdatum);	}
			@Override	public void clearHasData() {	v.setValue(null);		}
		});
		dataBinderArr.add(new DataBinder<CheckBox, Order1>(faktor) {
			@Override	public void hasData2Object(Order1 o) {o.faktor = v.getValue() ? (short)1 : (short)0 ;		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.faktor!=0);	}
			@Override	public void clearHasData() {	v.setValue(false);		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(fraktbolag) {
			@Override	public void hasData2Object(Order1 o) {o.fraktbolag = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.fraktbolag);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<DoubleTextBox, Order1>(fraktfrigrans) {
			@Override	public void hasData2Object(Order1 o) {o.fraktfrigrans = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.fraktfrigrans);	}
			@Override	public void clearHasData() {	v.setValue(0.0);		}
		});
		dataBinderArr.add(new DataBinder<IntegerTextBox, Order1>(kundordernr) {
			@Override	public void hasData2Object(Order1 o) throws ParseException {o.kundordernr = v.getValueOrThrow();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.kundordernr);	}
			@Override	public void clearHasData() {	v.setValue(0);		}
		});
		dataBinderArr.add(new DataBinder<DateTextBox, Order1>(levdat) {
			@Override	public void hasData2Object(Order1 o) throws ParseException {o.levdat = v.getValueOrThrow();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levdat);	}
			@Override	public void clearHasData() {	v.setValue(null);		}
		});
		dataBinderArr.add(new DataBinder<DateTextBox, Order1>(tidigastfaktdatum) {
			@Override	public void hasData2Object(Order1 o) throws ParseException {o.tidigastfaktdatum = v.getValueOrThrow();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.tidigastfaktdatum);	}
			@Override	public void clearHasData() {	v.setValue(null);		}
		});
		dataBinderArr.add(new DataBinder<TextBox, Order1>(levvillkor) {
			@Override	public void hasData2Object(Order1 o) {o.levvillkor = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levvillkor);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});

		
	}
	
	public Order1 hasData2Object() throws ParseException{
		Order1 o = new Order1();
		for (DataBinder d : dataBinderArr) {
			d.hasData2Object(o);
		}
		o.moms = (short)moms.getSelectedIndex();
		o.bonus = (short)bonus.getSelectedIndex();
		disableNormalDisabledFields();
		o.annanlevadress = annanLevAdr ? (short)1 : (short)0;
		o.veckolevdag = veckolevdag;
		o.wordernr = wordernr;
		o.ordernr = ordernr ;
		o.dellev = dellev;
		o.status= status;
		return o;
	}
	public void object2HasData(Order1 o) {
		for (DataBinder d : dataBinderArr) {
			d.object2HasData(o);
		}
		if (o.moms<=3) 	moms.setSelectedIndex(o.moms); else moms.setSelectedIndex(1);
		if (o.bonus<=2) bonus.setSelectedIndex(o.bonus); else bonus.setSelectedIndex(0);
		annanLevAdr = o.annanlevadress!=0;
		if (annanLevAdr) enableLevAdr();
		veckolevdag = o.veckolevdag;
		wordernr = o.wordernr;
		ordernr = o.ordernr;
		dellev = o.dellev;
		status= o.status;
	}
	public void clearHasData() {
		for (DataBinder d : dataBinderArr) {
			d.clearHasData();
		}
		disableNormalDisabledFields();
		moms.setSelectedIndex(1);
		bonus.setSelectedIndex(0);
		annanLevAdr=false;
		veckolevdag = 0;
		wordernr = 0;
		ordernr = 0;
		dellev = 0;
		status= "";
	}
	


}
