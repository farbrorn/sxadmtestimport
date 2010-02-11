/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author ulf
 */
public class KontoPanel extends DockPanel implements SxResizePanel{
	final GlobalData globalData;
	ScrollPanel leftScroll = new ScrollPanel(new Meny());
	VerticalPanel centerPanel = new VerticalPanel();
	ScrollPanel centerScroll = new ScrollPanel();

	public KontoPanel(final GlobalData globalData) {
		this.globalData = globalData;
		leftScroll.addStyleName("sx-kontoleftpanel");
		//centerScroll.addStyleName(globalData.STYLE_MAINPANEL);
		centerPanel.addStyleName(globalData.STYLE_MAINPANEL);
		centerScroll.add(centerPanel);
		add(leftScroll,DockPanel.WEST);
		add(centerScroll, DockPanel.CENTER);
	}

	public Widget getThisWidget() { return (Widget)this;}

	public void resize(int windowWidth, int windowHeight) {
		int scrollHeight = windowHeight - leftScroll.getAbsoluteTop() - 9;
		if (scrollHeight < 1) scrollHeight = 1;
		leftScroll.setHeight(scrollHeight+"px");

		int scrollWidth = windowWidth - centerScroll.getAbsoluteLeft() - 9;
		if (scrollWidth < 1) scrollWidth = 1;
		scrollHeight = windowHeight - centerScroll.getAbsoluteTop() - 9;
		if (scrollHeight < 1) scrollHeight = 1;
		centerScroll.setPixelSize(scrollWidth, scrollHeight);
	}




	private class Meny extends VerticalPanel {
		public Meny() {
			setSpacing(4);
			//setHeight("100%");
			Label l;
			l = new Label("Mitt konto");
			l.addStyleName(globalData.STYLE_HUVUDRUBRIK);
			add((new MenyRad(l)).getMenyWidget());

			l = new Label("Order");
			l.addStyleName(globalData.STYLE_MENYUNDERRUBRIK);
			add((new MenyRad(l)).getMenyWidget());

			add(new MenyRad("Inneliggande order"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new OrderListaWidget(globalData,"Inneliggande order");}}
					  ).getMenyWidget());
			add(new MenyRad("Levererade order"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new UtlevListWidget(globalData,"Levererade order");}}
					  ).getMenyWidget());
			add(new MenyRad("Offerter"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new OffertListWidget(globalData,"Offerter");}}
					  ).getMenyWidget());


			l = new Label("Ekonomi");
			l.addStyleName(globalData.STYLE_MENYUNDERRUBRIK);
			add((new MenyRad(l)).getMenyWidget());

			add(new MenyRad("Fakturor"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new FakturaListaWidget(globalData,"Fakturor");}}
					  ).getMenyWidget());
			add(new MenyRad("Obetalda fakturor"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new KundresListaWidget(globalData,"Obetalda fakturor");}}
					  ).getMenyWidget());
			add(new MenyRad("Betalningar"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new BetalningListaWidget(globalData,"Betalningar");}}
					  ).getMenyWidget());

			l = new Label("Statistik");
			l.addStyleName(globalData.STYLE_MENYUNDERRUBRIK);
			add((new MenyRad(l)).getMenyWidget());

			add(new MenyRad("Statistik"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new StatInkopWidget(globalData,"Statistik");}}
					  ).getMenyWidget());
			add(new MenyRad("Köpta produkter"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new StatArtikelWidget(globalData,"Köpta produkter");}}
					  ).getMenyWidget());

			l = new Label("Konto");
			l.addStyleName(globalData.STYLE_MENYUNDERRUBRIK);
			add((new MenyRad(l)).getMenyWidget());
			add(new MenyRad("Inställningar"
					  , new SxWidget.SxWidgetConstructor() { public SxWidget getWidget() {return new KontoAnvandareUppgifterWidget(globalData,"Inställningar");}}
					  ).getMenyWidget());

		}
	}


	class MenyRad {
		private SxWidget centerWidget=null;
		private Widget menyWidget=null;
		private SxWidget.SxWidgetConstructor centerWidgetConstructor=null;
		//Ett menyval med angiven text.
		public MenyRad(String menyText, SxWidget.SxWidgetConstructor centerWidgetConstructor) {
			this.centerWidgetConstructor=centerWidgetConstructor;
			menyWidget = new Anchor(menyText);
			((Anchor)menyWidget).addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					centerPanel.clear();
					centerPanel.add(getCenterWidget());
				}
			});
		}
		//En widget som visas i menyn
		public MenyRad(Widget menyWidget) {
			this.menyWidget=menyWidget;
		}

		public Widget getCenterWidget() {
			if (centerWidget==null && centerWidgetConstructor!=null) centerWidget = centerWidgetConstructor.getWidget();
			return centerWidget;
		}

		public Widget getMenyWidget() {
			return menyWidget;
		}

		public boolean isMenyVal() { return centerWidget==null; }
	}

}
