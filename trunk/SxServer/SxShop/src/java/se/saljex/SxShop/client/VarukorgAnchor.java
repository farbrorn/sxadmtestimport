/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

/**
 *
 * @author ulf
 */
public class VarukorgAnchor extends Anchor {

	public int row;
	public int col;
	public ArtikelVarukorg.VarukorgRadWidgets varukorgRadWidgets;
	private VarukorgCallbackInterface callback;

	VarukorgAnchor(int row, int col, String text, ArtikelVarukorg.VarukorgRadWidgets varukorgRadWidgets, VarukorgCallbackInterface callback) {
		super(text);
		this.row=row;
		this.col=col;
		this.callback = callback;
		this.varukorgRadWidgets=varukorgRadWidgets;
		addStyleName("sx-anchor");

		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doCallback();
			}
		});
	}

	private void doCallback() {
		callback.varukorgAnchorClickCallback(this);
	}


}
