/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

/**
 *
 * @author ulf
 */
public class VarukorgAnchor extends PushButton {

	public int row;
	public int col;
	public ArtikelVarukorg.VarukorgRadWidgets varukorgRadWidgets;
	private VarukorgCallbackInterface callback;
	public static final int BTN_ANDRA=0;
	public static final int BTN_TABORT=1;
	//public static final Image tickImage = new Image("kn-tick.png");
	//public static final Image trashImage = new Image("kn-trash.png");

	VarukorgAnchor(int row, int col, int typ, ArtikelVarukorg.VarukorgRadWidgets varukorgRadWidgets, VarukorgCallbackInterface callback) {
		super(typ==BTN_ANDRA ?  new Image("kn-tick.png") : new Image("kn-trash.png"));
		this.setTitle(typ==BTN_ANDRA ? "Uppdatera antal" : "Ta bort rad");
		this.row=row;
		this.col=col;
		this.callback = callback;
		this.varukorgRadWidgets=varukorgRadWidgets;
		//addStyleName("sx-anchor");
		setStylePrimaryName("sx-pushbutton");

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
