/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.SxShop.client.rpcobject.StatArtikelList;
import se.saljex.SxShop.client.rpcobject.StatArtikelRow;
import se.saljex.SxShop.client.rpcobject.UtlevList;
import se.saljex.SxShop.client.rpcobject.UtlevRow;

/**
 *
 * @author ulf
 */
public class StatArtikelWidget extends SxWidget {
	private ScrollPanel ftScrollPanel = new ScrollPanel();
	private FlexTable ft;
	private int nextRow=0;
//	private ColumnFormatter ftColFormatter = ft.getColumnFormatter();
//	private CellFormatter ftCellFormatter = ft.getCellFormatter();
//	private RowFormatter ftRowFormatter = ft.getRowFormatter();
	private StatArtikelList previousLoadedList=null;
	private Label infoFrDat = new Label();
	private Label infoTiDat = new Label();
	private Label infoSokStr = new Label();
	private SokPanel sokPanel  = new SokPanel();
	private String radioCurrentSelectionOrderBy=StatArtikelList.ORDER_BY_SUMMA;

	private static final String RADIO_ORDERBY_GROUP_NAME="orderby";

	public StatArtikelWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);

		add(sokPanel);
		HorizontalPanel sokInfo = new HorizontalPanel();
		sokInfo.setSpacing(4);
		sokInfo.add(new Label("Datumintervall: "));
		sokInfo.add(infoFrDat);
		sokInfo.add(new Label(" - "));
		sokInfo.add(infoTiDat);
		sokInfo.add(new Label(" Artikelnummer: "));
		sokInfo.add(infoSokStr);
		add(sokInfo);

		add(ftScrollPanel);
		add(visaFlerRaderAnchor);
		loadNewPage();
	}

	public void initFT() {
		nextRow=0;
		currentRow=1;
		ftScrollPanel.clear();
		ft = new FlexTable();
		ftScrollPanel.add(ft);
		ft.setWidth("100%");
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.addStyleName(globalData.STYLE_TABLE_INFO);
		ft.getCellFormatter().addStyleName(0,0, globalData.STYLE_TD_ACTION);
		ft.getCellFormatter().addStyleName(0,1, globalData.STYLE_TD_IDNR);
		ft.getCellFormatter().addStyleName(0,2, globalData.STYLE_TD_BENAMNING);
		ft.getCellFormatter().addStyleName(0,3, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0,4, globalData.STYLE_TD_ENHET);
		ft.getCellFormatter().addStyleName(0,5, globalData.STYLE_TD_PRIS);
		ft.getCellFormatter().addStyleName(0,6, globalData.STYLE_TD_PRIS);
		ft.setWidget(0, 0, new Label("Visa"));
		ft.setWidget(0, 1, new Label("Art.nr"));
		ft.setWidget(0, 2, new Label("Benämning"));
		ft.setWidget(0, 3, new Label("Antal"));
		ft.setWidget(0, 4, new Label("Enhet"));
		ft.setWidget(0, 5, new Label("Summa"));
		ft.setWidget(0, 6, new Label("Antal köp"));
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

	}

	@Override
	protected void loadNextPage() {
		blockTillFinishedLoading=true;
		if (previousLoadedList!=null) {
			globalData.service.getStatArtikelList(nextRow, pageSize, previousLoadedList.frdat, previousLoadedList.tidat, previousLoadedList.sokstr, previousLoadedList.orderBy, callbackLoadPage);
		} else {
			globalData.service.getStatArtikelList(nextRow, pageSize, null, null, null, null, super.callbackLoadPage);
		}
	}

	protected void loadNewPage() {
		blockTillFinishedLoading=true;
		globalData.service.getStatArtikelList(nextRow, pageSize, sokPanel.frDat.getText(), sokPanel.tiDat.getText(), sokPanel.sokStr.getText(), radioCurrentSelectionOrderBy,super.callbackLoadPage);
		visaFlerRaderAnchor.setVisible(false);
		initFT();
	}


	private void visaClick(Anchor visaAnchor, String artnr, String frdat, String tidat, int row) {
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			StatArtikelInfoWidget ui = new StatArtikelInfoWidget(globalData, artnr, frdat, tidat);
			ui.addStyleName(globalData.STYLE_DROPDOWNWIDGET);
			ft.setWidget(row+1, 0, ui);
		} else {
			//ftRowFormatter.removeStyleName(row, globalData.STYLE_TR_HIGHLITE);
			visaAnchor.setText("Visa");
			ft.clearCell(row+1, 0);
		}
	}

	@Override
	protected void pageLoaded(Object result) {
			StatArtikelList ul=(StatArtikelList)result;
			previousLoadedList = ul;
			infoFrDat.setText(ul.frdat);
			infoTiDat.setText(ul.tidat);
			infoSokStr.setText(ul.sokstr);
			for (StatArtikelRow ur : ul.rader) {
				final int finalRow=currentRow;
				final String finalArtnr = ur.artnr;
				final String finalFrdat = ul.frdat;
				final String finalTidat = ul.tidat;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalArtnr, finalFrdat, finalTidat, finalRow );
					}
				});
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setWidget(currentRow, 1, new Label(ur.artnr));
				ft.setWidget(currentRow, 2, new Label(ur.namn));
				ft.setText(currentRow, 3, globalData.numberFormatInt.format(ur.antal));
				ft.setWidget(currentRow, 4, new Label(ur.enh));
				ft.setText(currentRow, 5, globalData.numberFormatInt.format(ur.summa));
				ft.setText(currentRow, 6, globalData.numberFormatInt.format(ur.koptillfallen));
				ft.getCellFormatter().addStyleName(currentRow, 3, globalData.STYLE_TD_PRIS);
				ft.getCellFormatter().addStyleName(currentRow, 5, globalData.STYLE_TD_PRIS);
				ft.getCellFormatter().addStyleName(currentRow, 6, globalData.STYLE_TD_PRIS);
				if (currentRowHighlite ) {
					ft.getRowFormatter().addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
				}
				ft.getFlexCellFormatter().setColSpan((currentRow+1), 0, 7);
				currentRow = currentRow+2;
				currentRowHighlite=!currentRowHighlite;
			}
			if (ul.hasMoreRows) {
				visaFlerRaderAnchor.setVisible(true);
			}

			nextRow=ul.nextRow;
	}

	private class SokPanel extends VerticalPanel {
		public TextBox sokStr = new TextBox();
		public TextBox frDat = new TextBox();
		public TextBox tiDat = new TextBox();
		public RadioButton radioOrderBySumma = new RadioButton(RADIO_ORDERBY_GROUP_NAME, "Summa");
		public RadioButton radioOrderByAntal = new RadioButton(RADIO_ORDERBY_GROUP_NAME, "Antal");
		public RadioButton radioOrderByArtNr = new RadioButton(RADIO_ORDERBY_GROUP_NAME, "Artikel");
		public RadioButton radioOrderByKoptillfallen = new RadioButton(RADIO_ORDERBY_GROUP_NAME, "Antal köptillfällen");


		public SokPanel() {
			setWidth("100%");
			HorizontalPanel sokPanel = new HorizontalPanel();
			add(sokPanel);
			DisclosurePanel alternativPanel = new DisclosurePanel("Alternativ");
			add(alternativPanel);
			FlexTable alternativFT = new FlexTable();
			HorizontalPanel alternativDatumHP = new HorizontalPanel();
			HorizontalPanel alternativRadioHP = new HorizontalPanel();
			Label l;
			l = new Label("Datumintervall:");
			l.addStyleName(globalData.STYLE_PROMPT);
			alternativFT.setWidget(0, 0, l);
			alternativFT.setWidget(0,1, alternativDatumHP);
			l = new Label("Sortera efter:");
			l.addStyleName(globalData.STYLE_PROMPT);
			alternativFT.setWidget(1, 0, l);
			alternativFT.setWidget(1, 1, alternativRadioHP);
			alternativPanel.add(alternativFT);

			radioOrderBySumma.setValue(true);
			radioOrderBySumma.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) doRadioChange(StatArtikelList.ORDER_BY_SUMMA);
				}	});

			radioOrderByAntal.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) doRadioChange(StatArtikelList.ORDER_BY_ANTAL);
				}	});

			radioOrderByArtNr.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) doRadioChange(StatArtikelList.ORDER_BY_ARTNR);
				}	});

			radioOrderByKoptillfallen.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) doRadioChange(StatArtikelList.ORDER_BY_KOPTILLFALLEN);
				}	});

			sokStr.addKeyUpHandler(new KeyUpHandler() {
				public void onKeyUp(KeyUpEvent event) {
					doSok();
				}	});

			Button btnSok = new Button("Sök");
			btnSok.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					doSok();
				}
			});
			sokPanel.add(new Label("Sök:"));
			sokPanel.add(sokStr);
			sokPanel.add(btnSok);
			sokPanel.setSpacing(4);

			alternativDatumHP.add(frDat);
			alternativDatumHP.add(new Label(" - "));
			alternativDatumHP.add(tiDat);
			//alternativDatumHP.setSpacing(4);

			alternativRadioHP.add(radioOrderBySumma);
			alternativRadioHP.add(radioOrderByAntal);
			alternativRadioHP.add(radioOrderByKoptillfallen);
			alternativRadioHP.add(radioOrderByArtNr);
			//alternativRadioHP.setSpacing(4);
		}


		private void doRadioChange(String orderBy) {
			radioCurrentSelectionOrderBy = orderBy;
			doSok();
		}

		private void doSok() {
			if (!blockTillFinishedLoading) {
				loadNewPage();
			}
		}
	}
}
