/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.LoadEvent;
import se.saljex.SxShop.client.rpcobject.SokResultKlase;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlaseArtikel;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlase;
import se.saljex.SxShop.client.rpcobject.ArtSida;
import se.saljex.SxShop.client.rpcobject.SokResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class ArtikelMainPanel extends VerticalPanel implements KopKnappCallback{

	private final GlobalData globalData;
	private NumberFormat numberFormat = NumberFormat.getFormat("0.00");
	private NumberFormat numberFormatInt = NumberFormat.getFormat("0");

	private static final int SMALL_IMMAGE_HEIGHT = 20;

	private ArtikelPanel artikelPanel;
	VantaDialogBox vantaDialogBox = new VantaDialogBox(); //Modal dialog
	KopDialogBox kopDialogBox;

	public ArtikelMainPanel(final GlobalData globalData, ArtikelPanel artikelPanel) {
		this.globalData=globalData;
		addStyleName(globalData.STYLE_MAINPANEL);
		this.artikelPanel = artikelPanel;
		kopDialogBox=new KopDialogBox(artikelPanel);
	}
/*
	public void setVarukorg(ArtikelVarukorg varukorg) {
		this.varukorg=varukorg;
	}
	public void showVarukorg() {
		if (varukorg!=null) varukorg.setVisible(true);
	}
	public void hideVarukorg() {
		if (varukorg!=null) varukorg.setVisible(false);
	}
	public boolean isVarukorgVisible() {
		if (varukorg!=null) return varukorg.isVisible(); else return false;
	}
	private void addVarukorgIfVisible() {
		if (isVarukorgVisible()) add(varukorg);
	}
*/
	public void fillError(String err) {
		clear();
//		addVarukorgIfVisible();
		add(new Label(err));
	}

	public void fillWidget (Widget w) {
		clear();
//		addVarukorgIfVisible();
		add(w);
	}

	public void fill(final SokResult sokResult) {
		clear();
//		addVarukorgIfVisible();
		add(new Label("Sökresultat för " + sokResult.sokStr));
		for (SokResultKlase sk : sokResult.sokResultKlasar) {
			printKlase(sk.artSidaKlase);
		}
		if (sokResult.merRaderFinns) {
			Anchor a=new Anchor("Fler rader finns i sökningen. Klicka här för att visa alla.");
			a.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
						globalData.service.getSokResult(sokResult.sokStr, 100, artikelPanel.callbackSok);
			}
			});
			add(a);
		}
	}

	public void fill(ArtSida artSida) {
		fill(artSida, null);
	}
	public void fill(ArtSida artSida, Integer filterKlasid) {
			clear();
//			addVarukorgIfVisible();

			String namn;
			Grid klaseTextGrid;

			final String GRPRUBRIK = "sx-huvudrubrik";
			final String KLASERUBRIK = "sx-klaserubrik";
			Label l;

			l = new Label(artSida.rubrik);
			l.addStyleName(GRPRUBRIK);
			add(l);

			l = new HTML(artSida.text);
			add(l);

			l = new Label(artSida.infourl);
			add(l);

			for (ArtSidaKlase ask : artSida.klasar) {
				if (filterKlasid==null || filterKlasid.equals(ask.klasid)) {
					printKlase(ask);
/*					FlexTable ft;
					ft = new FlexTable();
					FlexTable.FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
					FlexTable.RowFormatter rowFormatter=ft.getRowFormatter();
					//ft.setWidth("100%");
					ft.setCellPadding(1);
					ft.setCellSpacing(0);
					ft.setWidget(0, 0, new Label("Art.nr"));
					ft.setWidget(0, 1, new Label("Benämning"));
					ft.setWidget(0, 2, new Label("Pris"));
					ft.setWidget(0, 3, new Label("Enh"));
					ft.setWidget(0, 4, new Label("Mängdpris"));
					ft.setWidget(0, 5, new Label("Mängd"));
					ft.setWidget(0, 6, new Label("Rab"));
					ft.setWidget(0, 7, new Label(" "));
					rowFormatter.addStyleName(0, "sx-tablerubrik");
					cellFormatter.addStyleName(0, 0, "sx-tb-artnr");
					cellFormatter.addStyleName(0, 1, "sx-tb-benamning");
					cellFormatter.addStyleName(0, 2, "sx-tb-pris");
					cellFormatter.addStyleName(0, 3, "sx-tb-enhet");
					cellFormatter.addStyleName(0, 4, "sx-tb-pris");
					cellFormatter.addStyleName(0, 5, "sx-tb-mangd");
					cellFormatter.addStyleName(0, 6, "sx-tb-rab");
					cellFormatter.addStyleName(0, 7, "sx-tb-kop");

					int rowCn=1;

					l = new Label(ask.rubrik);
					l.addStyleName(KLASERUBRIK);
					add(l);
					l = new Label(ask.text);
					add(l);
					l = new Label(ask.infourl);
					add(l);
					for (ArtSidaKlaseArtikel aska : ask.artiklar) {
						ft.setWidget(rowCn, 0,new Label(aska.nummer));
						if (aska.katnamn==null || aska.katnamn.isEmpty()) namn=aska.namn; else namn=aska.katnamn;
						ft.setWidget(rowCn, 1,new Label(namn));
						ft.setWidget(rowCn, 2,new Label(numberFormat.format(aska.utpris)));
						ft.setWidget(rowCn, 3, new Label(aska.enhet));
						if (aska.staf_pris1.equals(0.0)) {
							ft.setHTML(rowCn, 4,"");
							ft.setHTML(rowCn, 5,"");
						} else {
							ft.setWidget(rowCn, 4,new Label(numberFormat.format(aska.staf_pris1)));
							ft.setWidget(rowCn, 5,new Label(numberFormatInt.format(aska.staf_antal1)+ " " + aska.enhet));
						}
						ft.setWidget(rowCn, 6, new Label(aska.rabkod));
						cellFormatter.addStyleName(rowCn, 2, "sx-tb-pris");
						cellFormatter.addStyleName(rowCn, 4, "sx-tb-pris");
						KopKnapp kopKnapp = new KopKnapp(aska,this);
						ft.setWidget(rowCn, 7,kopKnapp );
						if (rowCn%2 > 0 ) rowFormatter.addStyleName(rowCn, "");
						rowCn++;

					}
					add(ft); */
				}
			}
	}


public void printKlase(final ArtSidaKlase ask) {
					String namn;

					final String GRPRUBRIK = "sx-huvudrubrik";
					final String KLASERUBRIK = "sx-klaserubrik";
					Label l;
					FlexTable ft;
					ft = new FlexTable();
					FlexTable.FlexCellFormatter cellFormatter = ft.getFlexCellFormatter();
					FlexTable.RowFormatter rowFormatter=ft.getRowFormatter();
					//ft.setWidth("100%");
					ft.setCellPadding(1);
					ft.setCellSpacing(0);
					ft.setWidget(0, 0, new Label("Art.nr"));
					ft.setWidget(0, 1, new Label("Benämning"));
					ft.setWidget(0, 2, new Label("Pris"));
					ft.setWidget(0, 3, new Label("Enh"));
					ft.setWidget(0, 4, new Label("Mängdpris"));
					ft.setWidget(0, 5, new Label("Mängd"));
					ft.setWidget(0, 6, new Label("Rab"));
					ft.setWidget(0, 7, new Label(" "));
					ft.setWidget(0, 8, new Label(" "));
					ft.setWidget(0, 9, new Label(" "));
					rowFormatter.addStyleName(0, "sx-tablerubrik");
					cellFormatter.addStyleName(0, 0, "sx-tb-artnr");
					cellFormatter.addStyleName(0, 1, "sx-tb-benamning");
					cellFormatter.addStyleName(0, 2, "sx-tb-pris");
					cellFormatter.addStyleName(0, 3, "sx-tb-enhet");
					cellFormatter.addStyleName(0, 4, "sx-tb-pris");
					cellFormatter.addStyleName(0, 5, "sx-tb-mangd");
					cellFormatter.addStyleName(0, 6, "sx-tb-rab");
					cellFormatter.addStyleName(0, 7, "sx-tb-kop");
					cellFormatter.addStyleName(0, 8, "sx-tb-info");
					cellFormatter.addStyleName(0, 9, "sx-tb-bild");

					int rowCn=1;

					l = new Label(ask.rubrik);
					l.addStyleName(KLASERUBRIK);
					add(l);
					if (ask.platsText!=null) {	//Visar sökvägen där klasen hittades vid sökning
						l = new Label(ask.platsText);
						add(l);
					}
					l = new HTML(ask.text);
					add(l);
					l = new Label(ask.infourl);
					add(l);
					for (final ArtSidaKlaseArtikel aska : ask.artiklar) {
						ft.setWidget(rowCn, 0,new Label(aska.nummer));
						if (aska.katnamn==null || aska.katnamn.isEmpty()) namn=aska.namn; else namn=aska.katnamn;
						ft.setWidget(rowCn, 1,new Label(namn));
						ft.setWidget(rowCn, 2,new Label(numberFormat.format(aska.utpris)));
						ft.setWidget(rowCn, 3, new Label(aska.enhet));
						if (aska.staf_pris1.equals(0.0)) {
							ft.setHTML(rowCn, 4,"");
							ft.setHTML(rowCn, 5,"");
						} else {
							ft.setWidget(rowCn, 4,new Label(numberFormat.format(aska.staf_pris1)));
							ft.setWidget(rowCn, 5,new Label(numberFormatInt.format(aska.staf_antal1)+ " " + aska.enhet));
						}
						ft.setWidget(rowCn, 6, new Label(aska.rabkod));
						cellFormatter.addStyleName(rowCn, 2, "sx-tb-pris");
						cellFormatter.addStyleName(rowCn, 4, "sx-tb-pris");
						KopKnapp kopKnapp = new KopKnapp(aska,this);
						ft.setWidget(rowCn, 7,kopKnapp );

						final Image infoImage = new Image(globalData.IconInfoSmallURL);
						infoImage.addStyleName("sx-clickicon");
						infoImage.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								ArtikelInfoDialogBox artikelInfoPanel = new ArtikelInfoDialogBox(globalData, aska, ask);
								artikelInfoPanel.show();
							}
						});
						ft.setWidget(rowCn, 8, infoImage);

						final Image image = new Image(globalData.smallArtURL + aska.bildurl + globalData.ImageSuffix);
						image.setHeight(SMALL_IMMAGE_HEIGHT + "px");
						image.addLoadHandler(new LoadHandler() {
							public void onLoad(LoadEvent event) {
								int h = image.getHeight();
								int w = image.getWidth();
								if (w > SMALL_IMMAGE_HEIGHT) {
									image.setHeight(Math.round(SMALL_IMMAGE_HEIGHT * h/w)+ "px");
									image.setWidth(SMALL_IMMAGE_HEIGHT+"px");
								}
							}
						});
						image.addErrorHandler(new ErrorHandler() {

							public void onError(ErrorEvent event) {
								image.removeFromParent();
							}
						});


						ft.setWidget(rowCn, 9, image );

						if (rowCn%2 > 0 ) rowFormatter.addStyleName(rowCn, globalData.STYLE_TR_ODDROW);
						rowCn++;

					}
					add(ft);

}


public void kopKnappGetArtikelCallback(final ArtSidaKlaseArtikel artikel) {
	//Anropas när en köpknapp trycks.
	kopDialogBox.setArtikel(artikel);
	kopDialogBox.center();
	kopDialogBox.setFocusAntal();
}




public class KopDialogBox extends DialogBox {
		Image cartImage = new Image("cart48.png");
		FlexTable artFlexTable = new FlexTable();
		ArtSidaKlaseArtikel artikel=null;
		Label artNr= new Label();
		Label artNamn= new Label();
		final TextBox antal = new TextBox();
		Label minsaljpack= new Label();
		Label forpack= new Label();
		Label utpris= new Label();
		Label staf_pris1= new Label();
		Label prisdatum= new Label();
		Label rab= new Label();
		Label errortext= new Label();
		Grid grid = new Grid(7, 2);
		Button btnOk = new Button("OK", new ClickHandler() {
			public void onClick(ClickEvent event) {
				kopDialogBoxHandleOK();
			}
			});
		Button btnAvbryt = new Button("Avbryt", new ClickHandler() {
			public void onClick(ClickEvent event) {
				kopDialogBox.hide();
			}
			});

	public KopDialogBox(ArtikelPanel artikelPanel) {
		super(false,true);

		this.setText("Köp produkt");
		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();

		errortext.addStyleName("sx-feltext");
		vp.add(errortext);
		artFlexTable.setWidget(0, 0, cartImage);
		artFlexTable.getFlexCellFormatter().setRowSpan(0, 0, 2);
		artFlexTable.setWidget(0, 1, artNr);
		artFlexTable.setWidget(1, 0, artNamn);
		vp.add(artFlexTable);
		hp.add(new Label("Antal: "));
		hp.add(antal);
		vp.add(hp);

		grid.setText(0, 0, "Förpackning");
		grid.setWidget(0, 1, forpack);
		grid.setText(1, 0, "Minsta odelbara enhet");
		grid.setWidget(1, 1, minsaljpack);
		grid.setText(2, 0, "Pris");
		grid.setWidget(2, 1, utpris);
		grid.setText(3, 0, "Mängdpris");
		grid.setWidget(3, 1, staf_pris1);
		grid.setText(4, 0, "Prisdatum");
		grid.setWidget(4, 1, prisdatum);
		grid.setText(5, 0, "Rabattgrupp");
		grid.setWidget(5, 1, rab);
		vp.add(grid);
		hp = new HorizontalPanel();
		hp.add(btnAvbryt);
		hp.add(btnOk);
		vp.add(hp);

		antal.addKeyDownHandler(new KeyDownHandler() { public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode()==KeyCodes.KEY_ENTER) btnOk.click();
			else if (event.getNativeKeyCode()==KeyCodes.KEY_ESCAPE) btnAvbryt.click();
		}	});


		add(vp);
//		center();

	}


	public void setFocusAntal() {
		antal.setSelectionRange(0, antal.getValue().length());
		antal.setFocus(true); 
	}

	public void setArtikel(ArtSidaKlaseArtikel artikel) {
		this.artikel=artikel;
		artNr.setText(artikel.nummer);
		artNamn.setText(artikel.namn);
		antal.setValue(numberFormat.format(artikel.forpack));
		forpack.setText(numberFormat.format(artikel.forpack)+ " " + artikel.enhet);
		minsaljpack.setText(numberFormat.format(artikel.minsaljpack) + " " + artikel.enhet);
		utpris.setText(numberFormat.format(artikel.utpris) + "/" + artikel.enhet);
		if (!((Double)0.0).equals(artikel.staf_pris1)) {
			staf_pris1.setText(numberFormat.format(artikel.staf_pris1) + " /" + artikel.enhet + " vid " + numberFormatInt.format(artikel.staf_antal1) + " " + artikel.enhet);
		} else {
			staf_pris1.setText("");
		}
		prisdatum.setText(globalData.getDateString(artikel.prisdatum ));
		rab.setText(artikel.rabkod + (artikel.kod1!=null && !artikel.kod1.isEmpty() ? " - " + artikel.kod1 : ""));
		errortext.setText("");
	}

	public void setErrorText(String err) {
		errortext.setText(err);
	}

	private void kopDialogBoxHandleOK() {
		setErrorText("");
		Double antalDouble=null;
		try {
			antalDouble = new Double(antal.getValue());
		} catch (NumberFormatException e) { }
		if (antalDouble!=null && antalDouble.compareTo(0.0)>0) {
			if (artikel.minsaljpack!=null && artikel.minsaljpack > 0 && antalDouble % artikel.minsaljpack >0){
				setErrorText("Antalet går ej jämt upp i minsta odelbara förpackning. Kontrollera antal och enhet.");
			} else {
				vantaDialogBox.show();
				globalData.service.addVaruKorg(artikel.nummer, antalDouble, callbackVaruKorg );
			}
		} else {
			setErrorText("Felaktigt antal.");
		}

	}

	final AsyncCallback callbackVaruKorg = new AsyncCallback() {
		public void onSuccess(Object result) {
			artikelPanel.updateVarukorg((ArrayList)result);
			vantaDialogBox.hide();
			kopDialogBox.hide();
		}

		public void onFailure(Throwable caught) {
			vantaDialogBox.hide();
			setErrorText("Fel: " + caught.toString());
		}
	};


	}


}

