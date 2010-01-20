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
import se.saljex.SxShop.client.rpcobject.SokResultKlase;

/**
 *
 * @author ulf
 */
public class ArtikelPanel extends DockPanel {
		final GlobalData globalData;
		VantaDialogBox vantaDialogBox = new VantaDialogBox(); //Modal dialog

		DisclosurePanel varuKorgPanel=new DisclosurePanel("Varukorg");
		//VerticalSplitPanel mainSplitPanel = new VerticalSplitPanel();
		ScrollPanel varukorgScrollPanel=new ScrollPanel();
		ArrayList<VaruKorgRad> varuKorg= new ArrayList();
		ScrollPanel mainScrollPanel = new ScrollPanel();
		ScrollPanel tradScrollPanel = new ScrollPanel();
		VerticalPanel leftPanel = new VerticalPanel();
		HorizontalPanel topMenuPanel = new HorizontalPanel();
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
			sokKnapp.addStyleName("sx-sokknapp");
			HorizontalPanel sokPanel = new HorizontalPanel();
			sokPanel.setSpacing(4);
			sokPanel.add(sokStr);
			sokPanel.add(sokKnapp);
			topMenuPanel.add(sokPanel);

			topMenuPanel.addStyleName("sx-submenutop");
			artSidaPanel = new ArtikelMainPanel(globalData,this);
			artikelVarukorg = new ArtikelVarukorg(globalData,this);
			//artSidaPanel.setVarukorg(artikelVarukorg);
			at = new ArtikelTrad(globalData);
			mainScrollPanel.setSize("100%", "100%");
			mainScrollPanel.addStyleName("sx-mainpanel");
			mainScrollPanel.add(artSidaPanel);
			tradScrollPanel.addStyleName("sx-arttrad");
//			leftPanel.add(new Label("Varukorg"));
//			leftPanel.add(varukorgListBox);
			leftPanel.add(at);
			tradScrollPanel.add(leftPanel);
			at.addSelectionHandler(new SelectionHandler<TreeItem>() {
					public void onSelection(SelectionEvent<TreeItem> event) {
						TreeItem item = event.getSelectedItem();
						TradCallbackHandler tch = new TradCallbackHandler(item, artSidaPanel);
						ArtTradUserObject userObject = (ArtTradUserObject)item.getUserObject();
						globalData.service.getArtSida(userObject.artGrupp.grpid, tch.callbackFillGrupp);
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
			artikelVarukorg.ftScrollPanel.setHeight("80px");
			add(topMenuPanel, DockPanel.NORTH);
			add(tradScrollPanel, DockPanel.WEST);
			add(varuKorgPanel, DockPanel.NORTH);
			add(mainScrollPanel, DockPanel.CENTER);

			sokStr.setFocus(true);
			varuKorgPanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {	public void onOpen(OpenEvent<DisclosurePanel> event) { artikelVarukorg.getSokTextBox().setFocus(true);	}	});
			varuKorgPanel.addCloseHandler(new CloseHandler<DisclosurePanel>() {	public void onClose(CloseEvent<DisclosurePanel> event) {	sokStr.setFocus(true);	}	});


		}
		
		public void updateVarukorg(ArrayList<VaruKorgRad> varuKorg) {
			this.varuKorg=varuKorg;
			artikelVarukorg.setVarukorg(varuKorg);
		}

		public void printSokResult(SokResult sokResult) {
			artSidaPanel.fill(sokResult);
		}
		public void printError(String s) {
			artSidaPanel.fillError(s);
		}
		public void fillWidget(Widget w) {
			artSidaPanel.fillWidget(w);
		}
		
//		public SxShopRPCAsync getService() { return service; }

		public void resize(int windowWidth, int windowHeight) {

			int scrollHeight = windowHeight - at.getAbsoluteTop() - 9;
			if (scrollHeight < 1) scrollHeight = 1;
			at.setPixelSize(at.getOffsetWidth(), scrollHeight);

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

	final AsyncCallback callbackSok = new AsyncCallback() {
		public void onSuccess(Object result) {
			SokResult sokResult = (SokResult)result;
			for (SokResultKlase rk : sokResult.sokResultKlasar) {
			}
			//sokResult.sokResultKlasar.get(1).artSidaKlase.text =
			artSidaPanel.fill((SokResult)result);
		}

		public void onFailure(Throwable caught) {
			artSidaPanel.fillError("Fel " + caught.toString());
		}
	};

}
