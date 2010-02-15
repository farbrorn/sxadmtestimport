/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import se.saljex.SxShop.client.rpcobject.VaruKorgRad;
import se.saljex.SxShop.client.rpcobject.SokResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class ArtikelPanel extends DockPanel implements SxResizePanel{
		final GlobalData globalData;
		VantaDialogBox vantaDialogBox = new VantaDialogBox(); //Modal dialog

		DisclosurePanel varuKorgPanel=new DisclosurePanel("Varukorg");
		//VerticalSplitPanel mainSplitPanel = new VerticalSplitPanel();
		ScrollPanel varukorgScrollPanel=new ScrollPanel();
		//ArrayList<VaruKorgRad> varuKorg= new ArrayList();
		ScrollPanel mainScrollPanel = new ScrollPanel();
		ScrollPanel tradScrollPanel = new ScrollPanel();
		VerticalPanel leftPanel = new VerticalPanel();
//		HorizontalPanel topMenuPanel = new HorizontalPanel();
		TextBox sokStr = new TextBox();
		Button sokKnapp = new Button("Sök", new ClickHandler() {
			public void onClick(ClickEvent event) {
						globalData.service.getSokResult(sokStr.getValue(), 100, callbackSok);
			}
			});
		ArtikelMainPanel artSidaPanel;
		ArtikelVarukorg artikelVarukorg;
		ArtikelTrad at;
		//private SxShopRPCAsync service;

		public ArtikelPanel(final GlobalData globalData) {
			this.globalData = globalData;
			sokKnapp.addStyleName(globalData.STYLE_SOKKNAPP);
			sokStr.addStyleName(globalData.STYLE_SOKINPUT);
			HorizontalPanel sokPanel = new HorizontalPanel();
			sokPanel.setSpacing(4);
			sokPanel.add(sokStr);
			sokPanel.add(sokKnapp);
//			topMenuPanel.add(sokPanel);

//			topMenuPanel.addStyleName("sx-submenutop");
			artSidaPanel = new ArtikelMainPanel(globalData,this);
			artikelVarukorg = new ArtikelVarukorg(globalData,this);
			//artSidaPanel.setVarukorg(artikelVarukorg);
			at = new ArtikelTrad(globalData);
			mainScrollPanel.setSize("100%", "100%");
//			mainScrollPanel.addStyleName(globalData.STYLE_MAINPANEL);
			mainScrollPanel.add(artSidaPanel);
			tradScrollPanel.addStyleName("sx-arttrad");
			tradScrollPanel.setHeight("100%");
//			leftPanel.add(new Label("Varukorg"));
//			leftPanel.add(varukorgListBox);
//			at.addStyleName("sx-arttrad");
//			leftPanel.addStyleName("sx-arttrad");
			leftPanel.add(sokPanel);
			leftPanel.add(at);
			tradScrollPanel.add(leftPanel);
			at.addSelectionHandler(new SelectionHandler<TreeItem>() {
					public void onSelection(SelectionEvent<TreeItem> event) {
						TreeItem item = event.getSelectedItem();
						ArtTradUserObject userObject = (ArtTradUserObject)item.getUserObject();
						if (userObject!=null && userObject.isKampanjNod) {
							globalData.service.getKampanjartiklar(callbackKampanj);
						} else {
							TradCallbackHandler tch = new TradCallbackHandler(item, artSidaPanel);
							globalData.service.getArtSida(userObject.artGrupp.grpid, tch.callbackFillGrupp);
						}
					}
				});
			//varukorgScrollPanel.setSize("", "100px");
			//varukorgScrollPanel.add(artikelVarukorg);
			//	artikelVarukorg.setHeight("100px");
//			mainSplitPanel.setTopWidget(varukorgScrollPanel);
//			mainSplitPanel.setBottomWidget(mainScrollPanel);
//			mainSplitPanel.setSplitPosition("50%");
			//varuKorgPanel.add(varukorgScrollPanel);
			//varuKorgPanel.setSize("100%", "100px");
			varuKorgPanel.add(artikelVarukorg);
			//artikelVarukorg.setHeight("100px");
			artikelVarukorg.ftScrollPanel.setHeight("100px");
			//add(topMenuPanel, DockPanel.NORTH);
			add(tradScrollPanel, DockPanel.WEST);
			add(varuKorgPanel, DockPanel.NORTH);
			add(mainScrollPanel, DockPanel.CENTER);

			sokStr.setFocus(true);
			varuKorgPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {	public void onOpen(OpenEvent<DisclosurePanel> event) {
				artikelVarukorg.getSokTextBox().setFocus(true);
				resize(Window.getClientWidth(), Window.getClientHeight());
			}	});
			varuKorgPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {	public void onClose(CloseEvent<DisclosurePanel> event) {
				sokStr.setFocus(true);
				resize(Window.getClientWidth(), Window.getClientHeight());
			}	});


		}

		public void hideVarukorgPanel() { varuKorgPanel.setVisible(false); }
		public void showVarukorgPanel() { varuKorgPanel.setVisible(true); }
		public void updateVarukorg(ArrayList<VaruKorgRad> varuKorg) {
			//this.varuKorg=varuKorg;
			artikelVarukorg.setVarukorg(varuKorg);
		}

		public void printSokResult(SokResult sokResult) {
			artSidaPanel.clear();
			artSidaPanel.fill(sokResult);
		}
		public void printError(String s) {
			artSidaPanel.fillError(s);
		}
		public void fillWidget(Widget w) {
			artSidaPanel.fillWidget(w);
		}
		
//		public SxShopRPCAsync getService() { return service; }

		public Widget getThisWidget() { return (Widget)this;}
		public void resize(int windowWidth, int windowHeight) {

			int scrollHeight = windowHeight - tradScrollPanel.getAbsoluteTop() - 9;
			if (scrollHeight < 1) scrollHeight = 1;
			tradScrollPanel.setHeight(scrollHeight+"px");

			int scrollWidth = windowWidth - mainScrollPanel.getAbsoluteLeft() - 9;
			if (scrollWidth < 1) scrollWidth = 1;
			scrollHeight = windowHeight - mainScrollPanel.getAbsoluteTop() - 9;
			if (scrollHeight < 1) scrollHeight = 1;
			mainScrollPanel.setPixelSize(scrollWidth, scrollHeight);

			//if (mainScrollPanel.getOffsetWidth() < at.getOffsetWidth()) {
				//at.setWidth("50%");
				//mainScrollPanel.setWidth("50%");
			//}
		}

	final AsyncCallback callbackKampanj = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			printError("Fel " + caught.getMessage());
		}

		public void onSuccess(Object result) {
			printSokResult((SokResult)result);
		}
	};

	final AsyncCallback callbackSok = new AsyncCallback() {
		public void onSuccess(Object result) {
			artSidaPanel.clear();
			artSidaPanel.fill((SokResult)result);
		}

		public void onFailure(Throwable caught) {
			artSidaPanel.fillError("Fel " + caught.toString());
		}
	};

}
