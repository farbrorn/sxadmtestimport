/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;
import java.util.ArrayList;
import java.util.List;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.*;
import se.saljex.webadm.client.common.rpcobject.Kund;
import se.saljex.webadm.client.common.rpcobject.Order1;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;

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
		private final Button btnAnnanLevAdr = new Button("Ändra levadress", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			enableLevAdr();
		}
	});


		private final TabLayoutPanel tabPanel = new TabLayoutPanel(1.8, Style.Unit.EM);

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
	private final SqlSelectParameters selectparamSok = new SqlSelectParameters();
	private final SqlSelectParameters selectparamGet = new SqlSelectParameters();
		
	Kund tempKund = new Kund();
	PageLoad<Kund> pageLoadKundSok = new PageLoad<Kund>(tempKund, 3, 10, 1000, pageLoadCallbackSok);
	PageLoad<Kund> pageLoadKundGet = new PageLoad<Kund>(tempKund, 1, 1, 1, pageLoadCallbackGet);
		
		
	public OrderHeaderWidget() {
		addDataBinders();
		initVars();
		add(createKundWidget());
		
		tabPanel.setWidth("45%");
		tabPanel.setHeight("100%");
		tabPanel.addStyleName(Const.Style_FloatLeft);
		tabPanel.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
		tabPanel.add(createLevAdrPanel(), "Leverans");
		tabPanel.add(createTestPanel(),"Test");
		add(tabPanel);
		kundnr.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_ENTER) {
					doKundnrEnter();
//				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F12) {
//					selectArtikel.show(artnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.ARTNR);
//				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F5) {
//					selectArtikel.show(artnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.SUPERSOK);
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
//				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F12) {
//					selectArtikel.show(artnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.ARTNR);
//				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F5) {
//					selectArtikel.show(artnr.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SelectArtikel.SEARCH_FIELD.SUPERSOK);
				if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F9) {
					doSetSelectParam(selectparamSok, kundnamn.getValue(), SQLTableList.COMPARE_GREATER_EQUALS, SOKFIELD.NAMN);
					pageLoadKundSok.setSearch(selectparamSok);
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F8) {
					pageLoadKundSok.next();
				} else if (keycode==se.saljex.webadm.client.commmon.constants.KeyCodes.F7) {
					pageLoadKundSok.previous();
				}
			}
		});

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
		addInput(fp, "Märke", marke, "8em", "20em", navigator);
		addInput(fp, "Referens", referens, "8em", "20em", navigator);
		addInput(fp, "Säljare", saljare, "8em", "20em", navigator);
		return sp;
	}
	
	private Panel createLevAdrPanel() {
		FlowPanel fp = new FlowPanel();
		ScrollPanel sp = new ScrollPanel(fp);
		sp.addStyleName(Const.Style_FloatLeft);
		sp.setHeight("100%");
		sp.setWidth("100%");
		
//		fp.add(new Label("Tesss"));
		
		FormNavigator navigator = new FormNavigator();
		addInput(fp, "LevAdress", levAdr1, "8em", "14em", navigator);
		addInput(fp, ".", levAdr2, "8em", "20em", navigator);
		addInput(fp, ".", levAdr3, "8em", "20em", navigator);
//		fp.add(btnAnnanLevAdr);
		addInput(fp, "", btnAnnanLevAdr, "0em", "6em", navigator);
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

	public Order1 toOrder1() {
		Order1 o1 = new Order1();
		hasData2Object(o1);
		o1.annanlevadress=annanLevAdr ? (short)1 : (short)0;
		o1.betalsatt="";
		o1.bonus=0;
		o1.doljdatum=null;
		o1.faktor=0;
		o1.forskatt=0;
		o1.forskattbetald=0;
		o1.fraktbolag="";
		o1.fraktfrigrans=0;
		o1.fraktkundnr="";
		o1.ktid=0;
		o1.kundordernr = 0;
		o1.levdat=null;
		o1.levvillkor="";
		o1.moms=1;
		o1.mottagarfrakt=0;
		o1.tidigastfaktdatum=null;
		o1.veckolevdag=0;
		o1.wordernr=0;
		o1.ordernr=0;
		o1.dellev=0;
		o1.status="";
		return o1;
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
		
		dataBinderArr.add(new DataBinder<String, Order1>(kundnr) {
			@Override	public void hasData2Object(Order1 o) {o.kundnr = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.kundnr);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(kundnamn) {
			@Override	public void hasData2Object(Order1 o) {o.namn = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.namn);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(kundadr1) {
			@Override	public void hasData2Object(Order1 o) {o.adr1 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.adr1);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(kundadr2) {
			@Override	public void hasData2Object(Order1 o) {o.adr2 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.adr2);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(kundadr3) {
			@Override	public void hasData2Object(Order1 o) {o.adr3 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.adr3);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(linjenr1) {
			@Override	public void hasData2Object(Order1 o) {o.linjenr1 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.linjenr1);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(linjenr2) {
			@Override	public void hasData2Object(Order1 o) {o.linjenr2 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.linjenr2);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(linjenr3) {
			@Override	public void hasData2Object(Order1 o) {o.linjenr3 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.linjenr3);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(marke) {
			@Override	public void hasData2Object(Order1 o) {o.marke = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.marke);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(ordermeddelande) {
			@Override	public void hasData2Object(Order1 o) {o.ordermeddelande = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.ordermeddelande);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(referens) {
			@Override	public void hasData2Object(Order1 o) {o.referens = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.referens);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(saljare) {
			@Override	public void hasData2Object(Order1 o) {o.saljare = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.saljare);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(levAdr1) {
			@Override	public void hasData2Object(Order1 o) {o.levadr1 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levadr1);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(levAdr2) {
			@Override	public void hasData2Object(Order1 o) {o.levadr2 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levadr2);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<String, Order1>(levAdr3) {
			@Override	public void hasData2Object(Order1 o) {o.levadr3 = v.getValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(o.levadr3);	}
			@Override	public void clearHasData() {	v.setValue("");		}
		});
		dataBinderArr.add(new DataBinder<Integer, Order1>(lagernr) {
			@Override	public void hasData2Object(Order1 o) {o.lagernr = v.getValue().shortValue();		}
			@Override	public void object2HasData(Order1 o) {	v.setValue(((Short)o.lagernr).intValue());	}
			@Override	public void clearHasData() {	v.setValue(0);		}
		});
	}
	
	public void hasData2Object(Order1 o) {
		for (DataBinder d : dataBinderArr) {
			d.hasData2Object(o);
		}
		disableNormalDisabledFields();
		if (o.annanlevadress!=0) enableLevAdr();
	}
	public void object2HasData(Order1 o) {
		for (DataBinder d : dataBinderArr) {
			d.object2HasData(o);
		}
	}
	public void clearHasData() {
		for (DataBinder d : dataBinderArr) {
			d.clearHasData();
		}
		disableNormalDisabledFields();
	}

}
