/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author Ulf
 */
public class ErrorLabel extends Label implements HasShowError{

	public ErrorLabel() {
		super();
		addStyleName("sx-errorlabel");
		setVisible(false);
	}

	@Override
	public void showErr(String err) {
		setText(err);
		setVisible(true);
	}

	public void clearErr() {
		setVisible(false);
		setText("");
	}

}
