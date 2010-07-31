/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlase;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlaseArtikel;

/**
 *
 * @author ulf
 */
public class ArtikelInfoDialogBox extends DialogBox{
	GlobalData globalData=null;
	ArtSidaKlaseArtikel aska;
	ScrollPanel sp = new ScrollPanel();
	VerticalPanel vp = new VerticalPanel();
	FlexTable infoTable = new FlexTable();

	public static final String PROMPSTYLE="sx-prompt";


	public ArtikelInfoDialogBox(GlobalData globalData, ArtSidaKlaseArtikel aska, ArtSidaKlase ask) {
		super(false,true);
		this.globalData=globalData;
		setText("Artikelinfo");
		HorizontalPanel rubrikHp = new HorizontalPanel();
		Image iconInfo = new Image(globalData.IconInfoBigURL);
		rubrikHp.add(iconInfo);
		Label rubrikLabel = new Label(ask.rubrik);
		rubrikLabel.addStyleName("sx-klaserubrik");
		rubrikHp.add(rubrikLabel);
		vp.add(rubrikHp);
		final Image artImage = new Image(globalData.smallArtURL + aska.bildurl + globalData.ImageSuffix);
		artImage.addErrorHandler(new ErrorHandler() { public void onError(ErrorEvent event) { artImage.removeFromParent();			}		});
		artImage.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				int h = artImage.getHeight();
				int w = artImage.getWidth();
				if (h > 100) {
					artImage.setHeight("200px");
					artImage.setWidth(Math.round(200*w/h)+"px");
					center();
				}
			} });

		vp.add(artImage);
		vp.add(new HTML(ask.text));
		if (ask.infourl != null && !ask.infourl.isEmpty()) vp.add(new Anchor("Mer info", ask.infourl));
		int radCn=0;
		Label l;
		l=new Label("Art.nr:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(aska.nummer));
		radCn++;
		l=new Label("Benämning:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(aska.namn));
		radCn++;
		l=new Label("Pris:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(globalData.numberFormat.format(aska.utpris) + " /" + aska.enhet));
		radCn++;
		l=new Label("Mängdpris:");
		infoTable.setWidget(radCn, 0, l);
		l.addStyleName(PROMPSTYLE);
		if (!((Double)0.0).equals(aska.staf_pris1)) {
			infoTable.setWidget(radCn, 1, new Label(globalData.numberFormat.format(aska.staf_pris1) + " /" + aska.enhet + " vid " + globalData.numberFormat.format(aska.staf_antal1) + " " + aska.enhet));
		}
		radCn++;

		l=new Label("Prisdatum:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(DateTimeFormat.getMediumDateFormat().format(aska.prisdatum )));
		radCn++;

		l=new Label("Rabattgrupp:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(aska.rabkod + (aska.kod1!=null && !aska.kod1.isEmpty() ? " - " + aska.kod1 : "")));
		radCn++;
		l=new Label("Förpackning:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(globalData.numberFormat.format(aska.forpack)));
		radCn++;
		l=new Label("Minsta odelbara enhet:");
		l.addStyleName(PROMPSTYLE);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new Label(globalData.numberFormat.format(aska.minsaljpack) + " " + aska.enhet));
		radCn++;
		l=new Label("Prel. Lagersaldo:");
		l.addStyleName(PROMPSTYLE);
		infoTable.getCellFormatter().setVerticalAlignment(radCn, 0, HasVerticalAlignment.ALIGN_TOP);
		infoTable.setWidget(radCn, 0, l);
		infoTable.setWidget(radCn, 1, new LagerSaldoWidget(globalData, aska.nummer));
		radCn++;


		vp.add(infoTable);
		Button btnOk = new Button("OK");
		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		vp.add(btnOk);
		sp.add(vp);
		add(sp);
		if (Window.getClientWidth() > 410 )  sp.setWidth("400px"); else sp.setWidth((Window.getClientWidth()-10)+"px");
		if (Window.getClientHeight() > 510 )  sp.setHeight("500px"); else sp.setHeight((Window.getClientHeight()-10)+"px");
		center();
	}

}
