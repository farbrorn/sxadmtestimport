/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import se.saljex.SxShop.client.rpcobject.ArtSidaKlaseArtikel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

/**
 *
 * @author ulf
 */
public class KopKnapp extends Anchor {

	private ArtSidaKlaseArtikel artikel;
	private KopKnappCallback callback;


	KopKnapp(ArtSidaKlaseArtikel artikel, KopKnappCallback callback) {
		super("Köp");
		this.callback = callback;
		this.artikel = artikel;
		addStyleName("sx-kopknapp");
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {doCallback(); }});
	}

	private void doCallback() {
		callback.kopKnappGetArtikelCallback(artikel);
	}
	public ArtSidaKlaseArtikel getArtikel() { return artikel; }


}
