/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.SerializationException;
import se.saljex.webadm.client.window.WindowHandler;
import se.saljex.webadm.client.window.Window;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.constants.KeyCodes;
import se.saljex.webadm.client.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundForm extends Window implements HasFormUpdater<Kund>{

//	FlexTable mainGrid = new FlexTable();
	VerticalPanel mainVp = new VerticalPanel();
	Grid kundHp = new Grid(1, 2);

	Kund originalSQLTableRow;

	ModalMessageBox modalMessageBox = new ModalMessageBox();

	InfoLabel errorLabel = new InfoLabel();

	Button closBtn = new Button("Stäng detta fönster");
	Label vlabel = new Label();
	DoubleTextBox nt = new DoubleTextBox();
	VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel listPanel = new VerticalPanel();
	ScrollPanel mainScroll = new ScrollPanel(mainPanel);
	ScrollPanel listScroll = new ScrollPanel(listPanel);

	//mainPanel
	FormInputPanel focusForm = new FormInputPanel();
	private FormTextBox nummer = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.nummer = nummer.getSQLTableValue();}
		@Override	void set(Kund table) {	nummer.setSQLTableValue(table.nummer);	}
	});
	private FormTextBox namn = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.namn = namn.getSQLTableValue();}
		@Override	void set(Kund table) {	namn.setSQLTableValue(table.namn);	}
	});
	private FormTextBox adr1 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.adr1 = adr1.getSQLTableValue();}
		@Override	void set(Kund table) {	adr1.setSQLTableValue(table.adr1);	}
	});
	private FormTextBox adr2 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.adr2 = adr2.getSQLTableValue();}
		@Override	void set(Kund table) {	adr2.setSQLTableValue(table.adr2);	}
	});
	private FormTextBox adr3 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.adr3 = adr3.getSQLTableValue();}
		@Override	void set(Kund table) {	adr3.setSQLTableValue(table.adr3);	}
	});
	private FormTextBox lnamn = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.lnamn = lnamn.getSQLTableValue();}
		@Override	void set(Kund table) {	lnamn.setSQLTableValue(table.lnamn);	}
	});
	private FormTextBox ladr2 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ladr2 = ladr2.getSQLTableValue();}
		@Override	void set(Kund table) {	ladr2.setSQLTableValue(table.ladr2);	}
	});
	private FormTextBox ladr3 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ladr3 = ladr3.getSQLTableValue();}
		@Override	void set(Kund table) {	ladr3.setSQLTableValue(table.ladr3);	}
	});
	private FormTextBox ref = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ref = ref.getSQLTableValue();}
		@Override	void set(Kund table) {	ref.setSQLTableValue(table.ref);	}
	});
	private FormTextBox saljare = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.saljare = saljare.getSQLTableValue();}
		@Override	void set(Kund table) {	saljare.setSQLTableValue(table.saljare);	}
	});
	private FormTextBox tel = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.tel = tel.getSQLTableValue();}
		@Override	void set(Kund table) {	tel.setSQLTableValue(table.tel);	}
	});
	private FormTextBox biltel = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.biltel = biltel.getSQLTableValue();}
		@Override	void set(Kund table) {	biltel.setSQLTableValue(table.biltel);	}
	});
	private FormTextBox fax = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.fax = fax.getSQLTableValue();}
		@Override	void set(Kund table) {	fax.setSQLTableValue(table.fax);	}
	});
	private FormTextBox sokare = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.sokare = sokare.getSQLTableValue();}
		@Override	void set(Kund table) {	sokare.setSQLTableValue(table.sokare);	}
	});
	private FormTextBox email = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.email = email.getSQLTableValue();}
		@Override	void set(Kund table) {	email.setSQLTableValue(table.email);	}
	});
	private FormTextBox hemsida = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.hemsida = hemsida.getSQLTableValue();}
		@Override	void set(Kund table) {	hemsida.setSQLTableValue(table.hemsida);	}
	});
	private FormIntegerTextBox kDag = new FormIntegerTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.k_Dag = kDag.getSQLTableValue();}
		@Override	void set(Kund table) {	kDag.setSQLTableValue(table.k_Dag);	}
	});
	private FormTimeTextBox kTid = new FormTimeTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.k_Tid = kTid.getSQLTableValue();}
		@Override	void set(Kund table) {	kTid.setSQLTableValue(table.k_Tid);	}
	});
	private FormDateTextBox kDatum = new FormDateTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.k_Datum = kDatum.getSQLTableValue();}
		@Override	void set(Kund table) {	kDatum.setSQLTableValue(table.k_Datum);	}
	});
	private FormTextBox regnr = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.regnr = regnr.getSQLTableValue();}
		@Override	void set(Kund table) {	regnr.setSQLTableValue(table.regnr);	}
	});
	private FormCheckBox rantfakt = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.rantfakt = rantfakt.getSQLTableValue();}
		@Override	void set(Kund table) {	rantfakt.setSQLTableValue(table.rantfakt);	}
	});
	private FormCheckBox faktor = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.faktor = faktor.getSQLTableValue();}
		@Override	void set(Kund table) {	faktor.setSQLTableValue(table.faktor);	}
	});
	private FormDoubleTextBox kgrans = new FormDoubleTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.kgrans = kgrans.getSQLTableValue();}
		@Override	void set(Kund table) {	kgrans.setSQLTableValue(table.kgrans);	}
	});
	private FormIntegerTextBox ktid = new FormIntegerTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ktid = ktid.getSQLTableValue().shortValue();}
		@Override	void set(Kund table) {	ktid.setSQLTableValue(new Integer(table.ktid));	}
	});
	private FormTextBox nettolst = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.nettolst = nettolst.getSQLTableValue();}
		@Override	void set(Kund table) {	nettolst.setSQLTableValue(table.nettolst);	}
	});
	private FormIntegerTextBox bonus = new FormIntegerTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.bonus = bonus.getSQLTableValue().shortValue();}
		@Override	void set(Kund table) {	bonus.setSQLTableValue(new Integer(table.bonus));	}
	});
	private FormCheckBox elkund = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.elkund = elkund.getSQLTableValue();}
		@Override	void set(Kund table) {	elkund.setSQLTableValue(table.elkund);	}
	});
	private FormCheckBox vvskund = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.vvskund = vvskund.getSQLTableValue();}
		@Override	void set(Kund table) {	vvskund.setSQLTableValue(table.vvskund);	}
	});
	private FormCheckBox ovrigkund = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ovrigkund = ovrigkund.getSQLTableValue();}
		@Override	void set(Kund table) {	ovrigkund.setSQLTableValue(table.ovrigkund);	}
	});
	private FormCheckBox installator = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.installator = installator.getSQLTableValue();}
		@Override	void set(Kund table) {	installator.setSQLTableValue(table.installator);	}
	});
	private FormCheckBox butik = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.butik = butik.getSQLTableValue();}
		@Override	void set(Kund table) {	butik.setSQLTableValue(table.butik);	}
	});
	private FormCheckBox industri = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.industri = industri.getSQLTableValue();}
		@Override	void set(Kund table) {	industri.setSQLTableValue(table.industri);	}
	});
	private FormCheckBox oem = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.oem = oem.getSQLTableValue();}
		@Override	void set(Kund table) {	oem.setSQLTableValue(table.oem);	}
	});
	private FormCheckBox grossist = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.grossist = grossist.getSQLTableValue();}
		@Override	void set(Kund table) {	grossist.setSQLTableValue(table.grossist);	}
	});
	private FormTextBox levvillkor = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.levvillkor = levvillkor.getSQLTableValue();}
		@Override	void set(Kund table) {	levvillkor.setSQLTableValue(table.levvillkor);	}
	});
	private FormCheckBox mottagarfrakt = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.mottagarfrakt = mottagarfrakt.getSQLTableValue();}
		@Override	void set(Kund table) {	mottagarfrakt.setSQLTableValue(table.mottagarfrakt);	}
	});
	private FormTextBox fraktbolag = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.fraktbolag = fraktbolag.getSQLTableValue();}
		@Override	void set(Kund table) {	fraktbolag.setSQLTableValue(table.fraktbolag);	}
	});
	private FormTextBox fraktkundnr = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.fraktkundnr = fraktkundnr.getSQLTableValue();}
		@Override	void set(Kund table) {	fraktkundnr.setSQLTableValue(table.fraktkundnr);	}
	});
	private FormDoubleTextBox fraktfrigrans = new FormDoubleTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.fraktfrigrans = fraktfrigrans.getSQLTableValue();}
		@Override	void set(Kund table) {	fraktfrigrans.setSQLTableValue(table.fraktfrigrans);	}
	});
	private FormTextBox ant1 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ant1 = ant1.getSQLTableValue();}
		@Override	void set(Kund table) {	ant1.setSQLTableValue(table.ant1);	}
	});
	private FormTextBox ant2 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ant2 = ant2.getSQLTableValue();}
		@Override	void set(Kund table) {	ant2.setSQLTableValue(table.ant2);	}
	});
	private FormTextBox ant3 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ant3 = ant3.getSQLTableValue();}
		@Override	void set(Kund table) {	ant3.setSQLTableValue(table.ant3);	}
	});
	private FormIntegerTextBox distrikt = new FormIntegerTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.distrikt = distrikt.getSQLTableValue().shortValue();}
		@Override	void set(Kund table) {	distrikt.setSQLTableValue(new Integer(table.distrikt));	}
	});
	private FormCheckBox vakund = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.vakund = vakund.getSQLTableValue();}
		@Override	void set(Kund table) {	vakund.setSQLTableValue(table.vakund);	}
	});
	private FormCheckBox fastighetskund = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.fastighetskund = fastighetskund.getSQLTableValue();}
		@Override	void set(Kund table) {	fastighetskund.setSQLTableValue(table.fastighetskund);	}
	});
	private FormDoubleTextBox basrab = new FormDoubleTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.basrab = basrab.getSQLTableValue();}
		@Override	void set(Kund table) {	basrab.setSQLTableValue(table.basrab);	}
	});
	private FormCheckBox golvkund = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.golvkund = golvkund.getSQLTableValue();}
		@Override	void set(Kund table) {	golvkund.setSQLTableValue(table.golvkund);	}
	});
	private FormCheckBox ejfakturerbar = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.ejfakturerbar = ejfakturerbar.getSQLTableValue();}
		@Override	void set(Kund table) {	ejfakturerbar.setSQLTableValue(table.ejfakturerbar);	}
	});
	private FormCheckBox skrivfakturarskenr = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.skrivfakturarskenr = skrivfakturarskenr.getSQLTableValue();}
		@Override	void set(Kund table) {	skrivfakturarskenr.setSQLTableValue(table.skrivfakturarskenr);	}
	});
	private FormCheckBox sarfaktura = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.sarfaktura = sarfaktura.getSQLTableValue();}
		@Override	void set(Kund table) {	sarfaktura.setSQLTableValue(table.sarfaktura);	}
	});
	private FormCheckBox momsfri = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.momsfri = momsfri.getSQLTableValue();}
		@Override	void set(Kund table) {	momsfri.setSQLTableValue(table.momsfri);	}
	});
	private FormDoubleTextBox kgransforfall30 = new FormDoubleTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.kgransforfall30 = kgransforfall30.getSQLTableValue();}
		@Override	void set(Kund table) {	kgransforfall30.setSQLTableValue(table.kgransforfall30);	}
	});
	private FormCheckBox kravordermarke = new FormCheckBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.kravordermarke = kravordermarke.getSQLTableValue();}
		@Override	void set(Kund table) {	kravordermarke.setSQLTableValue(table.kravordermarke);	}
	});
	private FormTextBox linjenr1 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.linjenr1 = linjenr1.getSQLTableValue();}
		@Override	void set(Kund table) {	linjenr1.setSQLTableValue(table.linjenr1);	}
	});
	private FormTextBox linjenr2 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.linjenr2 = linjenr2.getSQLTableValue();}
		@Override	void set(Kund table) {	linjenr2.setSQLTableValue(table.linjenr2);	}
	});
	private FormTextBox linjenr3 = new FormTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.linjenr3 = linjenr3.getSQLTableValue();}
		@Override	void set(Kund table) {	linjenr3.setSQLTableValue(table.linjenr3);	}
	});
	private FormCheckBox skickafakturaepost = new FormCheckBox("Skicka faktura som e-post", new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.skickafakturaepost = skickafakturaepost.getSQLTableValue();}
		@Override	void set(Kund table) {	skickafakturaepost.setSQLTableValue(table.skickafakturaepost);	}
	});
	private FormDoubleTextBox samfakgrans = new FormDoubleTextBox(new FormWidgetGetSet<Kund>() {
		@Override	void get(Kund table) {	table.samfakgrans = samfakgrans.getSQLTableValue();}
		@Override	void set(Kund table) {	samfakgrans.setSQLTableValue(table.samfakgrans);	}
	});


	//listPanel

	private Button saveBtn = new Button("Spara");

	private final KundListWidget kundListWidget  = new KundListWidget(this, errorLabel);


	final AsyncCallback callbackKund = new AsyncCallback<Kund>() {
		@Override
		public void onSuccess(Kund result) {
			data2Form(result);
		}

		@Override
		public void onFailure(Throwable caught) {
			errorLabel.showErr(caught.getMessage());
		}
	};


	final KeyDownHandler standardKeyDownHandler = new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int code=event.getNativeKeyCode();
				if (code==KeyCodes.F7 && !event.isAnyModifierKeyDown()) kundListWidget.getPageLoad().previous();
				else if(code == KeyCodes.F8 && !event.isAnyModifierKeyDown()) kundListWidget.getPageLoad().next();

			}
		};


//	private final PageLoadKund pageLoadKund = new PageLoadKund(3, 20, callbackKund, callbackShowBuffer);


	public KundForm(WindowHandler windowHandler, String title) {
		super(windowHandler, title);
		windowWidget = mainVp;
		initWindow();

		MainEntryPoint.getService().getKund("055513915", callback);

		DialogBox b1 = new DialogBox(false, false);
	}

	private void initWindow() {

		saveBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				save();
			}
		});


		errorLabel.showErr("Tomt");

		mainScroll.setHeight("100%");
		listScroll.setHeight((com.google.gwt.user.client.Window.getClientHeight()-listScroll.getAbsoluteTop()) + "px");



		mainVp.setWidth("100%");
		mainVp.add(errorLabel);
		mainVp.add(saveBtn);
		mainVp.add(kundHp);
		kundHp.setWidth("100%");
		//kundHp.setHeight("100%");
		kundHp.setWidget(0,0,mainScroll);
		kundHp.setWidget(0,1,listScroll);
		kundHp.getCellFormatter().setWidth(0, 1, "25%");

		//mainPanel

		FlexTable inputTable = new FlexTable();
		inputTable.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP);
		FlexTable groupPanel;
		groupPanel = new FlexTable();
		groupPanel.setCellPadding(0);
		nummer.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int code=event.getNativeKeyCode();
				if(code == KeyCodes.F9) kundListWidget.getPageLoad().setSearch("nummer", nummer.getValue(), false);
				else if(code == KeyCodes.F5) kundListWidget.getPageLoad().setSearch("nummer", nummer.getValue(), true);
			}
		});
		namn.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int code=event.getNativeKeyCode();
				if(code == KeyCodes.F9) kundListWidget.getPageLoad().setSearch("namn", namn.getValue(), false);
				else if(code == KeyCodes.F5) kundListWidget.getPageLoad().setSearch("namn", namn.getValue(), true);
			}
		});


		inputTable.setWidget(0, 0, groupPanel);
		addInputWithStandardKeyDownHandler(groupPanel, "Nummer", nummer);
		addInputWithStandardKeyDownHandler(groupPanel, "Namn", namn);
		addInputWithStandardKeyDownHandler(groupPanel, "Adress", adr1);
		addInputWithStandardKeyDownHandler(groupPanel, "", adr2);
		addInputWithStandardKeyDownHandler(groupPanel, "", adr3);
		addInputWithStandardKeyDownHandler(groupPanel, "Lev.adress", lnamn);
		addInputWithStandardKeyDownHandler(groupPanel, "", ladr2);
		addInputWithStandardKeyDownHandler(groupPanel, "", ladr3);
		addInputWithStandardKeyDownHandler(groupPanel, "Referens", ref);
		addInputWithStandardKeyDownHandler(groupPanel, "Säljare", saljare);
		addInputWithStandardKeyDownHandler(groupPanel, "Tel", tel);
		addInputWithStandardKeyDownHandler(groupPanel, "Mobil", biltel);
		addInputWithStandardKeyDownHandler(groupPanel, "Fax", fax);
		addInputWithStandardKeyDownHandler(groupPanel, "Sökare", sokare);
		addInputWithStandardKeyDownHandler(groupPanel, "E-Post", email);
		addInputWithStandardKeyDownHandler(groupPanel, "Hemsida", hemsida);
		addInputWithStandardKeyDownHandler(groupPanel, "Org.nr", regnr);
		addInputWithStandardKeyDownHandler(groupPanel, "Anteckning", ant1);
		addInputWithStandardKeyDownHandler(groupPanel, "", ant2);
		addInputWithStandardKeyDownHandler(groupPanel, "", ant3);

		groupPanel = new FlexTable();
		inputTable.setWidget(0, 1, groupPanel);
		groupPanel.setCellPadding(0);
		addInputWithStandardKeyDownHandler(groupPanel, "Fakturera ränta", rantfakt);
		addInputWithStandardKeyDownHandler(groupPanel, "Bonus", bonus);
		addInputWithStandardKeyDownHandler(groupPanel, "Faktoring", faktor);
		addInputWithStandardKeyDownHandler(groupPanel, "Kreditgräns", kgrans);
		addInputWithStandardKeyDownHandler(groupPanel, "Kredittid", ktid);
		addInputWithStandardKeyDownHandler(groupPanel, "Nettolista", nettolst);

		addInputWithStandardKeyDownHandler(groupPanel, "Elkund", elkund);
		addInputWithStandardKeyDownHandler(groupPanel, "VVSkund", vvskund);
		addInputWithStandardKeyDownHandler(groupPanel, "Vakund", vakund);
		addInputWithStandardKeyDownHandler(groupPanel, "Golvkund", golvkund);
		addInputWithStandardKeyDownHandler(groupPanel, "Fastighetskund", fastighetskund);
		addInputWithStandardKeyDownHandler(groupPanel, "Övrigkund", ovrigkund);
		addInputWithStandardKeyDownHandler(groupPanel, "Installator", installator);
		addInputWithStandardKeyDownHandler(groupPanel, "Butik", butik);
		addInputWithStandardKeyDownHandler(groupPanel, "Industri", industri);
		addInputWithStandardKeyDownHandler(groupPanel, "Oem", oem);
		addInputWithStandardKeyDownHandler(groupPanel, "Grossist", grossist);

		addInputWithStandardKeyDownHandler(groupPanel, "Basrabatt", basrab);
		
		addInputWithStandardKeyDownHandler(groupPanel, "Minsta ordervärde för samfakturering", samfakgrans);
		addInputWithStandardKeyDownHandler(groupPanel, "Kreditgräns förfallet 30 dagar", kgransforfall30);
		addInputWithStandardKeyDownHandler(groupPanel, "Distrikt", distrikt);
		addInputWithStandardKeyDownHandler(groupPanel, "Linjenr1", linjenr1);
		addInputWithStandardKeyDownHandler(groupPanel, "Linjenr2", linjenr2);
		addInputWithStandardKeyDownHandler(groupPanel, "Linjenr3", linjenr3);

		groupPanel = new FlexTable();
		inputTable.setWidget(0, 2, groupPanel);
		groupPanel.setCellPadding(0);
		addInputWithStandardKeyDownHandler(groupPanel, "Kontaktdag", kDag);
		addInputWithStandardKeyDownHandler(groupPanel, "Kontakttid", kTid);
		addInputWithStandardKeyDownHandler(groupPanel, "Kontaktdatum", kDatum);


		addInputWithStandardKeyDownHandler(groupPanel, "Levvillkor", levvillkor);
		addInputWithStandardKeyDownHandler(groupPanel, "Mottagarfrakt", mottagarfrakt);
		addInputWithStandardKeyDownHandler(groupPanel, "Fraktbolag", fraktbolag);
		addInputWithStandardKeyDownHandler(groupPanel, "Frakt kundnr", fraktkundnr);
		addInputWithStandardKeyDownHandler(groupPanel, "Fraktfrigräns", fraktfrigrans);


		addInputWithStandardKeyDownHandler(groupPanel, "EjFakturerbar", ejfakturerbar);
		addInputWithStandardKeyDownHandler(groupPanel, "Skriv e-nr, RSK på faktura", skrivfakturarskenr);
		addInputWithStandardKeyDownHandler(groupPanel, "Särfaktura (en faktura per order)", sarfaktura);
		addInputWithStandardKeyDownHandler(groupPanel, "Momsfri (export)", momsfri);
		addInputWithStandardKeyDownHandler(groupPanel, "Kräv ordermarke", kravordermarke);
		addInputWithStandardKeyDownHandler(groupPanel, "Skicka faktura som epost", skickafakturaepost);



		mainPanel.add(inputTable);

		//ListPanel


		listPanel.add(kundListWidget);


		listPanel.add(new Label("Kubndlista"));


	}

	private void addInput(FlexTable ft, String label, FormWidgetInterface widget) {
		int row = ft.getRowCount();
		ft.setWidget(row, 0, new Label(label));
		ft.setWidget(row, 1, widget.asWidget());
		focusForm.addFocusWidget(widget);

	}

	private void addInputWithStandardKeyDownHandler(FlexTable ft, String label, FormWidgetInterface widget) {
		addInput(ft, label, widget);
		widget.addKeyDownHandler(standardKeyDownHandler);
	}

	public void hh(ValueBoxBase d) {
		d.getValue();
	}

	public void save() {
		Kund kund = form2Data();
		MainEntryPoint.getService().putKund(kund, originalSQLTableRow, callbackSave);
		modalMessageBox.show("Sparar...");
	}

	@Override
	public Kund form2Data() {
		Kund kund = new Kund(originalSQLTableRow);
		focusForm.get(kund);
		errorLabel.showErr("Industri ny: " + kund.industri + " old: " + originalSQLTableRow.industri + " check: " + industri.getValue());
		return kund;

	}

	@Override
	public void data2Form(Kund kund) {
		if (kund==null) {
			originalSQLTableRow=null;
			focusForm.set(new Kund());
		} else {
			focusForm.set(kund);
			originalSQLTableRow = new Kund(kund);
		}
		/*
		nummer.setValue(kund.nummer);
		namn.setValue(kund.namn);
		adr1.setValue(kund.adr1);
		adr2.setValue(kund.adr2);
		adr3.setValue(kund.adr3);
		lnamn.setValue(kund.lnamn);
		ladr2.setValue(kund.ladr2);
		ladr3.setValue(kund.ladr3);
		ref.setValue(kund.ref);
		saljare.setValue(kund.saljare);
		tel.setValue(kund.tel);
		biltel.setValue(kund.biltel);
		fax.setValue(kund.fax);
		sokare.setValue(kund.sokare);
		email.setValue(kund.email);
		hemsida.setValue(kund.hemsida);
		kDag.setValue(new Integer(kund.k_Dag));
		kTid.setValue(kund.k_Tid);
		kDatum.setValue(kund.k_Datum);
		regnr.setValue(kund.regnr);
		rantfakt.setValue(kund.rantfakt==0 ? false : true);
		faktor.setValue(kund.faktor==0 ? false : true);
		kgrans.setValue(kund.kgrans);
		ktid.setValue(new Integer(kund.ktid));
		nettolst.setValue(kund.nettolst);
		bonus.setValue(new Integer(kund.bonus));
		elkund.setValue(kund.elkund==0 ? false : true);
		vvskund.setValue(kund.vvskund==0 ? false : true);
		ovrigkund.setValue(kund.ovrigkund==0 ? false : true);
		installator.setValue(kund.installator==0 ? false : true);
		butik.setValue(kund.butik==0 ? false : true);
		industri.setValue(kund.industri==0 ? false : true);
		oem.setValue(kund.oem==0 ? false : true);
		grossist.setValue(kund.grossist==0 ? false : true);
		levvillkor.setValue(kund.levvillkor);
		mottagarfrakt.setValue(kund.mottagarfrakt==0 ? false : true);
		fraktbolag.setValue(kund.fraktbolag);
		fraktkundnr.setValue(kund.fraktkundnr);
		fraktfrigrans.setValue(kund.fraktfrigrans);
		ant1.setValue(kund.ant1);
		ant2.setValue(kund.ant2);
		ant3.setValue(kund.ant3);
		distrikt.setValue(new Integer(kund.distrikt));
		vakund.setValue(kund.vakund==0 ? false : true);
		fastighetskund.setValue(kund.fastighetskund==0 ? false : true);
		basrab.setValue(kund.basrab);
		golvkund.setValue(kund.golvkund==0 ? false : true);
		ejfakturerbar.setValue(kund.ejfakturerbar==0 ? false : true);
		skrivfakturarskenr.setValue(kund.skrivfakturarskenr==0 ? false : true);
		sarfaktura.setValue(kund.sarfaktura==0 ? false : true);
		momsfri.setValue(kund.momsfri==0 ? false : true);
		kgransforfall30.setValue(kund.kgransforfall30);
		kravordermarke.setValue(kund.kravordermarke==0 ? false : true);
		linjenr1.setValue(kund.linjenr1);
		linjenr2.setValue(kund.linjenr2);
		linjenr3.setValue(kund.linjenr3);
		skickafakturaepost.setValue(kund.skickafakturaepost==0 ? false : true);
		samfakgrans.setValue(kund.samfakgrans);
		 * */

	}

	final AsyncCallback callback = new AsyncCallback<Kund>() {
		@Override
		public void onSuccess(Kund result) {
			data2Form(result);
		}

		@Override
	public void onFailure(Throwable caught) {

		}
	};

	final AsyncCallback callbackSave = new AsyncCallback<Kund>() {
		@Override
		public void onSuccess(Kund result) {
			data2Form(null);
			kundListWidget.getPageLoad().getBufferList().clear();
			modalMessageBox.hide();
		}

		@Override
		public void onFailure(Throwable caught) {
			modalMessageBox.show("Felmeddelande: " + caught.getMessage(), true);
		}
	};



}

/*
nummer.setValue(kund.nummer);
namn.setValue(kund.namn);
adr1.setValue(kund.adr1);
adr2.setValue(kund.adr2);
adr3.setValue(kund.adr3);
lnamn.setValue(kund.lnamn);
ladr2.setValue(kund.ladr2);
ladr3.setValue(kund.ladr3);
ref.setValue(kund.ref);
saljare.setValue(kund.saljare);
tel.setValue(kund.tel);
biltel.setValue(kund.biltel);
fax.setValue(kund.fax);
sokare.setValue(kund.sokare);
email.setValue(kund.email);
hemsida.setValue(kund.hemsida);
grupp.setValue(kund.grupp);
grupp2.setValue(kund.grupp2);
kDag.setValue(kund.kDag);
kTid.setValue(kund.kTid);
kDatum.setValue(kund.kDatum);
regnr.setValue(kund.regnr);
rantfakt.setValue(kund.rantfakt);
faktor.setValue(kund.faktor);
kgrans.setValue(kund.kgrans);
ktid.setValue(kund.ktid);
nettolst.setValue(kund.nettolst);
bonus.setValue(kund.bonus);
elkund.setValue(kund.elkund);
vvskund.setValue(kund.vvskund);
ovrigkund.setValue(kund.ovrigkund);
installator.setValue(kund.installator);
butik.setValue(kund.butik);
industri.setValue(kund.industri);
oem.setValue(kund.oem);
grossist.setValue(kund.grossist);
levvillkor.setValue(kund.levvillkor);
mottagarfrakt.setValue(kund.mottagarfrakt);
fraktbolag.setValue(kund.fraktbolag);
fraktkundnr.setValue(kund.fraktkundnr);
fraktfrigrans.setValue(kund.fraktfrigrans);
ant1.setValue(kund.ant1);
ant2.setValue(kund.ant2);
ant3.setValue(kund.ant3);
distrikt.setValue(kund.distrikt);
vakund.setValue(kund.vakund);
fastighetskund.setValue(kund.fastighetskund);
basrab.setValue(kund.basrab);
golvkund.setValue(kund.golvkund);
ejfakturerbar.setValue(kund.ejfakturerbar);
skrivfakturarskenr.setValue(kund.skrivfakturarskenr);
sarfaktura.setValue(kund.sarfaktura);
momsfri.setValue(kund.momsfri);
veckolevdag.setValue(kund.veckolevdag);
kgransforfall30.setValue(kund.kgransforfall30);
kravordermarke.setValue(kund.kravordermarke);
linjenr1.setValue(kund.linjenr1);
linjenr2.setValue(kund.linjenr2);
linjenr3.setValue(kund.linjenr3);
skickafakturaepost.setValue(kund.skickafakturaepost);
samfakgrans.setValue(kund.samfakgrans);




 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
nummer
namn
adr1
adr2
adr3
lnamn
ladr2
ladr3
ref
saljare
tel
biltel
fax
sokare
email
hemsida
grupp
grupp2
kDag
kTid
kDatum
regnr
rantfakt
faktor
kgrans
ktid
nettolst
bonus
elkund
vvskund
ovrigkund
installator
butik
industri
oem
grossist
levvillkor
mottagarfrakt
fraktbolag
fraktkundnr
fraktfrigrans
ant1
ant2
ant3
distrikt
vakund
fastighetskund
basrab
golvkund
ejfakturerbar
skrivfakturarskenr
sarfaktura
momsfri
veckolevdag
kgransforfall30
kravordermarke
linjenr1
linjenr2
linjenr3
skickafakturaepost
samfakgrans

 *
 */





