/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.SxShop.client.rpcobject.UtlevList;
import se.saljex.SxShop.client.rpcobject.UtlevRow;

/**
 *
 * @author ulf
 */
public class UtlevListWidget extends SxWidget {
	private ScrollPanel ftScrollPanel = new ScrollPanel();
	private FlexTable ft;
	private int nextRow=0;
//	private ColumnFormatter ftColFormatter = ft.getColumnFormatter();
//	private CellFormatter ftCellFormatter = ft.getCellFormatter();
//	private RowFormatter ftRowFormatter = ft.getRowFormatter();
	private UtlevList previousLoadedUtlevList=null;
	private Label infoFrDat = new Label();
	private Label infoTiDat = new Label();
	private Label infoSokStr = new Label();
	private SokPanel sokPanel  = new SokPanel();

	public UtlevListWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);

		add(sokPanel);
		HorizontalPanel sokInfo = new HorizontalPanel();
		sokInfo.setSpacing(4);
		sokInfo.add(new Label("Datumintervall: "));
		sokInfo.add(infoFrDat);
		sokInfo.add(new Label(" - "));
		sokInfo.add(infoTiDat);
		sokInfo.add(new Label(" Godsmärke: "));
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
		ft.getCellFormatter().addStyleName(0,2, globalData.STYLE_TD_DATUM);
		ft.getCellFormatter().addStyleName(0,3, globalData.STYLE_TD_DATUM);
		ft.getCellFormatter().addStyleName(0,4, globalData.STYLE_TD_MARKE);
		ft.setWidget(0, 0, new Label("Visa"));
		ft.setWidget(0, 1, new Label("Odernr"));
		ft.setWidget(0, 2, new Label("Orderdatum"));
		ft.setWidget(0, 3, new Label("Fakturadatum"));
		ft.setWidget(0, 4, new Label("Märke"));
		ft.getRowFormatter().addStyleName(0, globalData.STYLE_TR_RUBRIK);

	}

	@Override
	protected void loadNextPage() {
		blockTillFinishedLoading=true;
		if (previousLoadedUtlevList!=null) {
			globalData.service.getUtlevList(nextRow, pageSize, previousLoadedUtlevList.frdat, previousLoadedUtlevList.tidat, previousLoadedUtlevList.sokstr, super.callbackLoadPage);
		} else {
			globalData.service.getUtlevList(nextRow, pageSize, null, null, null, super.callbackLoadPage);
		}
	}

	protected void loadNewPage() {
		blockTillFinishedLoading=true;
		globalData.service.getUtlevList(nextRow, pageSize, sokPanel.frDat.getText(), sokPanel.tiDat.getText(), sokPanel.sokStr.getText(), super.callbackLoadPage);
		visaFlerRaderAnchor.setVisible(false);
		initFT();
	}


	private void visaClick(Anchor visaAnchor, int ordernr, int row) {
		if ("Visa".equals(visaAnchor.getText())) {
			visaAnchor.setText("Dölj");
			UtlevInfoWidget ui = new UtlevInfoWidget(globalData, ordernr);
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
			UtlevList ul=(UtlevList)result;
			previousLoadedUtlevList = ul;
			infoFrDat.setText(ul.frdat);
			infoTiDat.setText(ul.tidat);
			infoSokStr.setText(ul.sokstr);
			for (UtlevRow ur : ul.rader) {
				final int finalRow=currentRow;
				final int finalOrdernr = ur.ordernr;
				final Anchor visaAnchor = new Anchor("Visa");
				visaAnchor.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						visaClick(visaAnchor, finalOrdernr, finalRow );
					}
				});
				ft.setWidget(currentRow, 0, visaAnchor);
				ft.setText(currentRow, 1, globalData.numberFormatInt.format(ur.ordernr));
				ft.setText(currentRow, 2, globalData.getDateString(ur.orderdatum));
				ft.setText(currentRow, 3, globalData.getDateString(ur.faktdatum));
				ft.setWidget(currentRow, 4, new Label(ur.marke));
				ft.getCellFormatter().addStyleName(currentRow, 1, globalData.STYLE_TD_IDNR);
				if (currentRowHighlite ) {
					ft.getRowFormatter().addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
				}
				ft.getFlexCellFormatter().setColSpan((currentRow+1), 0, 6);
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

		public SokPanel() {
			setWidth("100%");
			HorizontalPanel sokPanel = new HorizontalPanel();
			add(sokPanel);
			DisclosurePanel alternativPanel = new DisclosurePanel("Alternativ");
			add(alternativPanel);
			HorizontalPanel alternativHP = new HorizontalPanel();
			alternativPanel.add(alternativHP);

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

			alternativHP.add(new Label("Datumintervall: "));
			alternativHP.add(frDat);
			alternativHP.add(new Label(" - "));
			alternativHP.add(tiDat);
			alternativHP.setSpacing(4);
		}



		private void doSok() {
			if (!blockTillFinishedLoading) {
				loadNewPage();
			}
		}
	}
}
