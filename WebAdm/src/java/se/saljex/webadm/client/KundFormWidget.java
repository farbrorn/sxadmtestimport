/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.constants.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.server.SQLTableHandler;

/**
 *
 * @author Ulf
 */
public class KundFormWidget extends FlowPanel implements HasFormUpdater<Kund>{

	private PageLoad<Kund> pageLoad;
	public KundFormWidget() {
		this(null);
	}
	public KundFormWidget(PageLoad<Kund> pageLoad) {
		this.pageLoad = pageLoad;
		init();
	}



//	FlexTable mainGrid = new FlexTable();
	VerticalPanel mainVp = new VerticalPanel();
	Grid kundHp = new Grid(1, 2);

	Kund originalSQLTableRow;


	Button closBtn = new Button("Stäng detta fönster");
	Label vlabel = new Label();
	DoubleTextBox nt = new DoubleTextBox();
	VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel listPanel = new VerticalPanel();
	ScrollPanel mainScroll = new ScrollPanel(mainPanel);
	ScrollPanel listScroll = new ScrollPanel(listPanel);

	//mainPanel
	FormInputPanel<Kund> focusForm = new FormInputPanel();
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

	final KeyDownHandler standardKeyDownHandler = new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int code=event.getNativeKeyCode();
				if (pageLoad!=null) {
					if (code==KeyCodes.F7 && !event.isAnyModifierKeyDown()) pageLoad.previous();
					else if(code == KeyCodes.F8 && !event.isAnyModifierKeyDown()) pageLoad.next();
				}
			}
		};


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

	private void init() {
		FlexTable inputTable = new FlexTable();
		inputTable.getRowFormatter().setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP);
		FlexTable groupPanel;
		groupPanel = new FlexTable();
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

		add(inputTable);

		nummer.addKeyDownHandler(new KeyDownHandler() {	@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("nummer", nummer.getValue(), event);	}	});
		namn.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("namn", namn.getValue(), event);		}	});
		adr1.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("adr1", adr1.getValue(), event);		}	});
		adr2.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("adr2", adr2.getValue(), event);		}	});
		adr3.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("adr3", adr3.getValue(), event);		}	});
		lnamn.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("lnamn", lnamn.getValue(), event);		}	});
		ladr2.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("ladr2", ladr2.getValue(), event);		}	});
		ladr3.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("ladr3", ladr3.getValue(), event);		}	});
		ref.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("ref", ref.getValue(), event);		}	});
		saljare.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("saljare", saljare.getValue(), event);		}	});
		tel.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("tel", tel.getValue(), event);		}	});
		biltel.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("biltel", biltel.getValue(), event);		}	});
		email.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("email", email.getValue(), event);		}	});
		hemsida.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("hemsida", hemsida.getValue(), event);		}	});
		regnr.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("regnr", regnr.getValue(), event);		}	});
		nettolst.addKeyDownHandler(new KeyDownHandler() {@Override	public void onKeyDown(KeyDownEvent event) {	doSetKeyCodeSearch("nettolst", nettolst.getValue(), event);		}	});
	}

	private void doSetKeyCodeSearch(String field, String value, KeyDownEvent event) {
		if (pageLoad!=null) {
			int code=event.getNativeKeyCode();
			if (code==KeyCodes.F9 && !event.isAnyModifierKeyDown()) pageLoad.setSearch(field, value, field, SQLTableList.COMPARE_GREATER_EQUALS , SQLTableList.SORT_ASCENDING);
			else if(code == KeyCodes.F5 && !event.isAnyModifierKeyDown()) pageLoad.setSearch(field, value, "nummer", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_ASCENDING);
		}
	}

	@Override
	public Kund form2Data() {
		Kund kund = new Kund(originalSQLTableRow);
		focusForm.get(kund);
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
	}


}
