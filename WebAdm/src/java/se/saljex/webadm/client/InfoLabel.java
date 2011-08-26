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
public class InfoLabel extends Label implements HasShowError, HasShowInfo{


	public static final String ERRORSTYLENAME="sx-errorlabel";
	public static final String INFOSTYLENAME="sx-infolabel";

	public InfoLabel() {
		super();
		setVisible(false);
	}

	@Override
	public void showErr(String err) {
		removeStyleName(INFOSTYLENAME);
		addStyleName(ERRORSTYLENAME);
		setText(err);
		setVisible(true);
	}

	@Override
	public void showInfo(String info) {
		removeStyleName(ERRORSTYLENAME);
		addStyleName(INFOSTYLENAME);
		setText(info);
		setVisible(true);
	}



	public void clear() {
		setVisible(false);
		setText("");
	}

}
