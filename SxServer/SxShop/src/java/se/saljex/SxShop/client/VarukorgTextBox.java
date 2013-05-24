/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
  */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextBox;

/**
 *
 * @author ulf
 */
public class VarukorgTextBox extends TextBox {

	public int row;
	public int col;
	public ArtikelVarukorg.VarukorgRadWidgets varukorgRadWidgets;
	private VarukorgCallbackInterface callback;

	VarukorgTextBox(int row, int col, String text, ArtikelVarukorg.VarukorgRadWidgets varukorgRadWidgets, VarukorgCallbackInterface callback) {
		super();
		setValue(text);
		this.row=row;
		this.col=col;
		this.callback = callback;
		this.varukorgRadWidgets=varukorgRadWidgets; 
//		addStyleName("sx-kopknapp");
		
		addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {doCallback(); }

		});
//		addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {doCallback(); }});
	}

	private void doCallback() {
		callback.varukorgTextBoxChangeCallback(this);
	}

}
