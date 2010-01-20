
package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.SokResult;
import se.saljex.SxShop.client.rpcobject.VaruKorgRad;

/**
 *
 * @author ulf
 */
public class ArtikelVarukorg extends VerticalPanel implements VarukorgCallbackInterface {
	public static final int COL_ARTNR=0;
	public static final int COL_NAMN=1;
	public static final int COL_ANTAL=2;
	public static final int COL_ENHET=3;
	public static final int COL_PRIS=4;
	public static final int COL_RAB=5;
	public static final int COL_NYTTANTAL=6;
	public static final int COL_ANDRA=7;
	public static final int COL_TABORT=8;


	private final GlobalData globalData;

	ArtikelCheckout artikelCheckout;
	Label errortext = new Label();
	TextBox nyArtnr=new TextBox();
//	Label nyNamn=new Label();
//	Label nyEnhet=new Label();
//	Label nyPris=new Label();
//	Label nyRab=new Label();
//	Button nyOk=new Button("OK");
//	Widget savedCellWidget;
	int savedCellWidgetRow;
	int savedCellWidgetCol;
	ArtikelPanel artikelPanel;
	FlexTable ft = new FlexTable();
	FlexTable.FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
	FlexTable.RowFormatter rowFormatter=ft.getRowFormatter();
	ScrollPanel ftScrollPanel=new ScrollPanel(ft);
	HorizontalPanel actionPanel = new HorizontalPanel();
	Button checkoutButton = new Button("Till kassan");


	ArrayList<VarukorgRadWidgets> varukorgRader = new ArrayList();
	VarukorgRadWidgets currentRow;

	final AsyncCallback callbackGetVarukorg = new AsyncCallback() {
		public void onSuccess(Object result) {
			artikelPanel.updateVarukorg((ArrayList<VaruKorgRad>)result);
		}

		public void onFailure(Throwable caught) {

		}
	};

	final AsyncCallback callbackVarukorgAndra = new AsyncCallback() {
		public void onSuccess(Object result) {
			artikelPanel.vantaDialogBox.hide();
			artikelPanel.updateVarukorg((ArrayList)result);
		}

		public void onFailure(Throwable caught) {
			artikelPanel.vantaDialogBox.hide();
			errortext.setText("Fel: " + caught.toString());
			errortext.setVisible(true);
		}
	};

	final AsyncCallback callbackVarukorgTabort = new AsyncCallback() {
		public void onSuccess(Object result) {
			artikelPanel.vantaDialogBox.hide();
			artikelPanel.updateVarukorg((ArrayList)result);
		}

		public void onFailure(Throwable caught) {
			artikelPanel.vantaDialogBox.hide();
			errortext.setText("Fel: " + caught.toString());
			errortext.setVisible(true);
		}
	};


	public ArtikelVarukorg(final GlobalData globalData, ArtikelPanel artikelPanel) {

		this.globalData=globalData;
		artikelCheckout=new ArtikelCheckout(globalData, artikelPanel);

		this.artikelPanel=artikelPanel;
		errortext.addStyleName("sx-feltext");
		setSpacing(4);


		nyArtnr.addStyleName("sx-vkartnr");
		nyArtnr.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				nyArtnrOnKeyUp();
			}	});

		add(errortext);
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.setHTML(0, COL_ARTNR, "Art.nr");
		ft.setHTML(0, COL_NAMN, "Benämning");
		ft.setHTML(0, COL_ANTAL, "Antal");
		ft.setHTML(0, COL_NYTTANTAL, "Ändra antal");
		ft.setHTML(0, COL_ENHET, "Enh");
		ft.setHTML(0, COL_PRIS, "Pris");
		ft.setHTML(0, COL_RAB, "Rab");
//		ft.setHTML(0, COL_ANDRA, "Ändra");
//		ft.setHTML(0, COL_TABORT, "Ta bort");
		rowFormatter.addStyleName(0, "sx-tablerubrik");
		cellFormatter.addStyleName(0, COL_ARTNR, "sx-tb-artnr");
		cellFormatter.addStyleName(0, COL_NAMN, "sx-tb-benamning");
		cellFormatter.addStyleName(0, COL_ANTAL, "sx-tb-mangd");
		cellFormatter.addStyleName(0, COL_NYTTANTAL, "sx-tb-mangd");
		cellFormatter.addStyleName(0, COL_ENHET, "sx-tb-enhet");
		cellFormatter.addStyleName(0, COL_PRIS, "sx-tb-pris");
		cellFormatter.addStyleName(0, COL_RAB, "sx-tb-rab");
		cellFormatter.addStyleName(0, COL_ANDRA, "sx-tb-andra");
		cellFormatter.addStyleName(0, COL_TABORT, "sx-tb-tabort");

		checkoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				checkout();
			}
		});


		add(ftScrollPanel);
		actionPanel.setSpacing(4);
		actionPanel.add(new Label("Snabbsök:"));
		actionPanel.add(nyArtnr);
		actionPanel.add(checkoutButton);
		add(actionPanel);
		globalData.service.getVaruKorg(callbackGetVarukorg);
	}

	public TextBox getSokTextBox() { return nyArtnr; }
	
	public void setVarukorg(ArrayList<VaruKorgRad> varukorg) {
		int cn=1;
		varukorgRader.clear();
		ft.clear();
		for (VaruKorgRad rad : varukorg) {
			if (rad!=null){
				currentRow = new VarukorgRadWidgets(cn,rad,this);
				varukorgRader.add(currentRow);
				ft.setWidget(cn, COL_ARTNR, currentRow.artnr);
				ft.setWidget(cn, COL_NAMN, currentRow.namn);
				ft.setWidget(cn, COL_ANTAL, currentRow.antal);
				ft.setWidget(cn, COL_NYTTANTAL, currentRow.nyttAntal);
				ft.setWidget(cn, COL_ENHET, currentRow.enhet);
				ft.setWidget(cn, COL_PRIS, currentRow.pris);
				ft.setWidget(cn, COL_RAB, currentRow.rab);
				ft.setWidget(cn, COL_ANDRA, currentRow.andra);
				ft.setWidget(cn, COL_TABORT, currentRow.tabort);
				cellFormatter.addStyleName(cn, COL_ANTAL, "sx-tb-mangd");
				cellFormatter.addStyleName(cn, COL_PRIS, "sx-tb-pris");
				cellFormatter.addStyleName(cn, COL_RAB, "sx-tb-rab");
				if (cn%2 > 0 ) rowFormatter.addStyleName(cn, "sx-highlite");

				cn++;
				//add(new Label("Rad:" + currentRow.artnr.getText()));
			}
		}
	}

	public void nyArtnrOnKeyUp() {
		globalData.service.getSokArtikel(nyArtnr.getValue(), 10, artikelSokCallback);
	}

	final AsyncCallback artikelSokCallback = new AsyncCallback() {
		public void onSuccess(Object result) {
			fillArtikelSok((SokResult)result);
		}

		public void onFailure(Throwable caught) {
			fillArtikelSokError("Fel " + caught.toString());
		}
	};

	public void fillArtikelSok(SokResult sokResult) {
		artikelPanel.printSokResult(sokResult);
	}

	public void fillArtikelSokError(String s) {
		artikelPanel.printError(s);
	}

	public void varukorgTextBoxChangeCallback(VarukorgTextBox varukorgTextBox) {
/*		double antal;
		errortext.setVisible(false);
		try { 
			antal = Double.parseDouble(varukorgTextBox.getValue());
			artikelPanel.getService().updateVaruKorg(varukorgTextBox.varukorgRadWidgets.artnr.getText(), antal, callbackVarukorgAndra);
			vantaDialogBox.show();
		} catch (NumberFormatException e) {
			errortext.setText("Felaktigt angivet antal.");
			errortext.setVisible(true);
			varukorgTextBox.setFocus(true);
		}
 * */
	}

	public void varukorgAnchorClickCallback(VarukorgAnchor varukorgAnchor) {
		if (varukorgAnchor.col==COL_ANDRA) {
			double antal;
			errortext.setVisible(false);
			try {
				antal = Double.parseDouble(varukorgAnchor.varukorgRadWidgets.nyttAntal.getValue());
				artikelPanel.vantaDialogBox.show();
				globalData.service.updateVaruKorg(varukorgAnchor.varukorgRadWidgets.artnr.getText(), antal, callbackVarukorgAndra);
			} catch (NumberFormatException e) {
				errortext.setText("Felaktigt angivet antal.");
				errortext.setVisible(true);
				varukorgAnchor.varukorgRadWidgets.nyttAntal.setFocus(true);
			}

		} else if (varukorgAnchor.col==COL_TABORT) {
			artikelPanel.vantaDialogBox.show();
			globalData.service.deleteVaruKorg(varukorgAnchor.varukorgRadWidgets.artnr.getText(), callbackVarukorgTabort);
		}

	}

	public void checkout() {
		artikelPanel.fillWidget(artikelCheckout);
	}


	public class VarukorgRadWidgets {
		public Label artnr=null;
		public Label namn=null;
		public Label antal=null;
		public VarukorgTextBox nyttAntal=null;
		public Label pris=null;
		public Label rab=null;
		public Label enhet=null;
		public VarukorgAnchor andra=null;
		public VarukorgAnchor tabort=null;


		public VarukorgRadWidgets(int row, VaruKorgRad varuKorgRad, VarukorgCallbackInterface callback) {
			NumberFormat fmt = NumberFormat.getFormat("0.00");

			artnr = new Label(noNull(varuKorgRad.artnr));
			namn = new Label(noNull(varuKorgRad.namn));
			antal= new Label(noNull(Double.toString(varuKorgRad.antal)));
			nyttAntal = new VarukorgTextBox(row,COL_ANTAL, noNull(Double.toString(varuKorgRad.antal)), this, callback);
			nyttAntal.addStyleName("sx-vktextbox");
			pris = new Label(noNull(fmt.format(varuKorgRad.pris)));
			rab= new Label(noNull(Double.toString(varuKorgRad.rab)));
			enhet = new Label(noNull(varuKorgRad.enhet));
//			andra = new VarukorgAnchor(row, COL_ANDRA, "Ändra", this, callback);
//			tabort = new VarukorgAnchor(row, COL_TABORT, "Ta bort", this, callback);
			andra = new VarukorgAnchor(row, COL_ANDRA, VarukorgAnchor.BTN_ANDRA, this, callback);
			tabort = new VarukorgAnchor(row, COL_TABORT, VarukorgAnchor.BTN_TABORT, this, callback);
		}
		
		private String noNull(String s) { return s==null ? "":s; }
	}
}
