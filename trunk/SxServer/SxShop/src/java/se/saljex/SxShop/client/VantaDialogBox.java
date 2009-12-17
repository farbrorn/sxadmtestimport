/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author ulf
 */
public class VantaDialogBox extends DialogBox {
	public VantaDialogBox() {
		super(false,true); //Modal
		add(new Label("Vänta..."));
	}

}
