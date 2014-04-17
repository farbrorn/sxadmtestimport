/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.safehtml.shared.SafeHtml;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlase;
import se.saljex.SxShop.client.rpcobject.ArtSida;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
/**
 *
 * @author ulf
 */
public class TradCallbackHandler {
TreeItem item;
ArtikelMainPanel artSidaPanel;

		public TradCallbackHandler(TreeItem item, ArtikelMainPanel artSidaPanel) {
			this.item=item;
			this.artSidaPanel=artSidaPanel;
		}

		final AsyncCallback callbackFillGrupp = new AsyncCallback() {
		public void onSuccess(Object result) {
			TreeItem newItem;
			ArtTradUserObject newUserObject;
			ArtSida artSida = (ArtSida)result;
			ArtTradUserObject userObject = (ArtTradUserObject)item.getUserObject();
			if (item.getChildCount() < 1 && userObject.artSidaKlase==null){  //Vi har en tom nod och ska se om det finns klasar att fylla med
				for( ArtSidaKlase k : artSida.klasar) {
                                    final String fs = k.rubrik;
					newItem = new TreeItem(new SafeHtml() {  @Override public String asString() { return fs; } });
					newItem.addStyleName("sx-arttrad-klase");
					newUserObject = new ArtTradUserObject(userObject.artGrupp, k);
					newItem.setUserObject(newUserObject);
					item.addItem(newItem);
				}
			}
			artSidaPanel.clear();
			artSidaPanel.fillSokVag(item);
			artSidaPanel.fillRubrik(userObject.artGrupp.rubrik);
			if (item.getChildCount() > 0) {			//Vi har childenoder och visar dem
				artSidaPanel.fillTreeNodes(item);
			}
			if (userObject.artSidaKlase==null) { //Vanlig sida
				artSidaPanel.fill(artSida);
			} else { //Filtrerad på en klase
				artSidaPanel.fill(artSida,userObject.artSidaKlase.klasid);
			}
			
		}

		public void onFailure(Throwable caught) {
			artSidaPanel.fillError("Fel vid kommunikation med databasen.");
		}
	};

}
